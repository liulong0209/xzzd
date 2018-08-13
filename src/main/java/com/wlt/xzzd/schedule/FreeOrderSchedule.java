package com.wlt.xzzd.schedule;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSONObject;
import com.wlt.xzzd.model.FreeOrder;
import com.wlt.xzzd.model.FreeRefundOrder;
import com.wlt.xzzd.service.FreeOrderService;
import com.wlt.xzzd.service.FreeRefundService;
import com.wlt.xzzd.service.WebChatPayService;

/**
 * 免单处理定时器
 * 
 * @ClassName: FreeOrderSchedule
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author liulong03@chinasoftinc.com
 *
 */
public class FreeOrderSchedule
{
    /**
     * 日志记录器
     */
    private Logger LOGGER = LoggerFactory.getLogger(FreeOrderSchedule.class);

    /**
     * 注入免单接口
     */
    @Autowired
    private FreeOrderService freeOrderService;

    /**
     * 注入支付接口
     */
    @Autowired
    private WebChatPayService webChatPayService;

    /**
     * 注入免单退款接口
     */
    @Autowired
    private FreeRefundService freeRefundService;

    // 定时执行的方法
    @SuppressWarnings("unchecked")
    public void execute()
    {
        try
        {
            LOGGER.info("开始检测免单退款");
            // 查询需要退款的订单
            Map<String, Object> needRefundOrders = freeOrderService.queryNeedRefundOrder();

            // 未抽中退款的订单，在抽中退款订单退款成功后修改其状态
            Map<String, List<String>> unLotteryMap = (Map<String, List<String>>) needRefundOrders.get("unLotteryMap");

            // 免单成功需要退单的订单号
            List<FreeOrder> successOrders = (List<FreeOrder>) needRefundOrders.get("successOrders");
            LOGGER.info("免单成功需要退款的订单：{}", successOrders);
            for (FreeOrder successOrder : successOrders)
            {
                refund(successOrder, "success", unLotteryMap);
            }

            // 免单失败需要退单的订单号
            List<FreeOrder> failOrders = (List<FreeOrder>) needRefundOrders.get("failOrders");
            LOGGER.info("免单失败需要退款的订单：{}", failOrders);
            for (FreeOrder failOrder : failOrders)
            {
                refund(failOrder, "fail", null);
            }

            LOGGER.info("结束检测免单退款");
        }
        catch (Exception e)
        {
            LOGGER.error("免单退单定时任务调度失败，原因：", e.fillInStackTrace());
        }

    }

    private void refund(FreeOrder freeOrder, String flag, Map<String, List<String>> unLotteryMap) throws Exception
    {
        JSONObject result = webChatPayService.refund(freeOrder, "免单成功，抽中退款");
        LOGGER.info("退款结果={}", result);
        if ("SUCCESS".equals(result.getString("return_code")))
        {
            FreeRefundOrder freeRefundOrder = new FreeRefundOrder();
            freeRefundOrder.setRefundOrderNo(result.getString("refundOrderNo"));
            freeRefundOrder.setOriginOrderNo(freeOrder.getOrderNo());
            freeRefundOrder.setRefundFee(freeOrder.getPayMoney());
            String resultCode = result.getString("result_code");
            if (resultCode.equals("SUCCESS"))
            {
                freeRefundOrder.setRefundId(result.getString("refund_id"));
                freeRefundOrder.setRefundFee(result.getBigDecimal("refund_fee").divide(new BigDecimal(100)));
                freeRefundOrder.setResult("1");

                // 退款成功后更改订单状态
                FreeOrder fo = new FreeOrder();
                fo.setOrderNo(freeOrder.getOrderNo());
                fo.setState(2);
                // 免单成功退款，标记被抽中，同时订单状态修改为成功； 免单失败的订单，再修改状态为结束
                if ("sucess".equals(flag))
                {
                    fo.setRefundFlag(0);
                    fo.setResult(1);

                    // 未抽中退款的订单，在抽中退款订单退款成功后修改其状态
                    List<String> unLotterys = unLotteryMap.get(freeOrder.getOrderNo());
                    if (!CollectionUtils.isEmpty(unLotterys))
                    {
                        for (String unLottery : unLotterys)
                        {
                            FreeOrder forder = new FreeOrder();
                            forder.setOrderNo(unLottery);
                            forder.setState(2);
                            forder.setResult(1);
                            freeOrderService.updateFreeOrder(forder);
                        }
                    }

                }
                freeOrderService.updateFreeOrder(fo);
            }
            else
            {
                freeRefundOrder.setResult("0");
                freeRefundOrder.setErrorCode(result.getString("err_code"));
                freeRefundOrder.setErrorCodeDes(result.getString("err_code_des"));
            }

            freeRefundService.addOrder(freeRefundOrder);
        }
    }
}
