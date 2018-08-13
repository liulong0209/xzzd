package com.wlt.xzzd.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.wlt.xzzd.model.FreeOrder;
import com.wlt.xzzd.model.FreeRefundOrder;
import com.wlt.xzzd.model.OrderInfo;
import com.wlt.xzzd.service.FreeOrderService;
import com.wlt.xzzd.service.FreeRefundService;
import com.wlt.xzzd.service.WebChatPayService;
import com.wlt.xzzd.util.IPUtil;
import com.wlt.xzzd.util.RestResult;
import com.wlt.xzzd.util.XmlUtil;

/**
 * 微信支付控制器
 * 
 * @ClassName: WebChatPayController
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author liulong03@chinasoftinc.com
 *
 */
@Controller
@RequestMapping("/api/pay")
public class WebChatPayController
{
    /**
     * 日志记录器
     */
    private Logger LOGGER = LoggerFactory.getLogger(WebChatPayController.class);

    /**
     * 注入支付接口
     */
    @Autowired
    private WebChatPayService webChatPayService;

    /**
     * 注入免单接口
     */
    @Autowired
    private FreeOrderService freeOrderService;

    /**
     * 注入免单退款接口
     */
    @Autowired
    private FreeRefundService freeRefundService;

    /**
     * 下单
     *
     * WebChatPayController.preOrder
     *
     * @Description: TODO(这里用一句话描述这个方法的作用)
     *
     * @param request
     * @param orderInfo
     * @param freeOrder
     * @return RestResult
     * 
     * @exception Exception
     * 
     * @author liulong03@chinasoftinc.com
     *
     * @date 2018年7月31日-下午8:56:18
     *
     */
    @RequestMapping(value = "/preOrder", method = RequestMethod.POST)
    @ResponseBody
    public RestResult preOrder(HttpServletRequest request, OrderInfo orderInfo, FreeOrder freeOrder)
    {
        if (null == orderInfo || StringUtils.isEmpty(orderInfo.getOpenid())
            || StringUtils.isEmpty(freeOrder.getParentOrderNo()))
        {
            return RestResult.error("参数不合法");
        }

        // 如果是参与免单的，判断免单是否人数已满，或者结束，或者实际结束
        if (!StringUtils.isEmpty(freeOrder.getParentOrderNo()))
        {
            FreeOrder parentOrder = freeOrderService.queryFreeOrderDetail(freeOrder.getParentOrderNo());
            if (parentOrder.getState().intValue() == 1 && freeOrder.getActEndTime().after(new Date())
                && freeOrder.getStillNum().intValue() == 0)
            {
                return RestResult.error("人数已满");
            }

            if (parentOrder.getState().intValue() == 2
                || (parentOrder.getState().intValue() == 1 && freeOrder.getActEndTime().before(new Date())))
            {
                return RestResult.error("活动已结束");
            }

        }

        LOGGER.info("WebChatPayController->preOrder->openid={}", orderInfo.getOpenid());
        orderInfo.setSpbill_create_ip(IPUtil.getIpAddr(request));
        // 在微信生成订单
        JSONObject result = webChatPayService.preOrder(orderInfo);

        if (null != result)
        {
            // 本地生成订单
            freeOrder.setOrderNo(result.getString("orderNo"));
            freeOrderService.createFreeOrder(freeOrder);
        }

        return RestResult.ok("data", result);
    }

    /**
     * 支付完成通知
     *
     * WebChatPayController.updateOrder
     *
     * @Description: <?xml version="1.0" encoding="utf-8"?>
     * 
     *               <xml> <appid><![CDATA[wx928bcac48ffa2253]]></appid>
     *               <bank_type><![CDATA[CFT]]></bank_type>
     *               <cash_fee><![CDATA[1]]></cash_fee>
     *               <fee_type><![CDATA[CNY]]></fee_type>
     *               <is_subscribe><![CDATA[N]]></is_subscribe>
     *               <mch_id><![CDATA[1505708261]]></mch_id>
     *               <nonce_str><![CDATA[
     *               av7pzhgy8sy1nx7ljgpqm2333yhh9112]]></nonce_str>
     *               <openid><![CDATA[ouyYQ5UiPtxC47dqUJ9QFx7cm-cQ]]></openid>
     *               <out_trade_no
     *               ><![CDATA[20180731231435458319]]></out_trade_no>
     *               <result_code><![CDATA[SUCCESS]]></result_code>
     *               <return_code><![CDATA[SUCCESS]]></return_code>
     *               <sign><![CDATA[4FD75FC7F37E6C280EBFCFAF0B73CDC0]]></sign>
     *               <time_end><![CDATA[20180731231447]]></time_end>
     *               <total_fee>1</total_fee>
     *               <trade_type><![CDATA[JSAPI]]></trade_type>
     *               <transaction_id><
     *               ![CDATA[4200000109201807319747241728]]></transaction_id>
     *               </xml>
     * 
     * 
     *               void
     * 
     * @exception Exception
     * 
     * @author liulong03@chinasoftinc.com
     *
     * @date 2018年7月31日-下午8:56:10
     *
     */
    @RequestMapping(value = "/notify", method = { RequestMethod.POST, RequestMethod.GET })
    public void updateOrder(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        BufferedReader br = new BufferedReader(new InputStreamReader((ServletInputStream) request.getInputStream()));
        String line = null;
        StringBuilder sb = new StringBuilder();
        while ((line = br.readLine()) != null)
        {
            sb.append(line);
        }
        LOGGER.info("WebChatPayController->updateOrder-支付回调结果={}", sb);
        // 解析并给微信发回收到通知确认
        JSONObject returnInfo = XmlUtil.documentToJSONObject(sb.toString());
        String returnCode = returnInfo.getString("return_code");
        String result = "";
        if (returnCode.equals("SUCCESS"))
        {
            String resultCode = returnInfo.getString("result_code");
            if (resultCode.equals("SUCCESS"))
            {
                FreeOrder freeOrder = new FreeOrder();
                freeOrder.setOrderNo(returnInfo.getString("out_trade_no"));
                freeOrder.setWeixinOrderNo(returnInfo.getString("transaction_id"));
                freeOrder.setState(1);// 状态置为已付款
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
                freeOrder.setPayTime(sdf.parse(returnInfo.getString("time_end")));
                freeOrderService.updateFreeOrder(freeOrder);
                result = "<xml><return_code>SUCCESS</return_code><return_msg>OK</return_msg></xml>";
            }
            else
            {
                result = "<xml>" + "<return_code>FAIL</return_code>" + "<return_msg>支付通知失败</return_msg>" + "</xml>";
            }
        }
        else
        {
            result = "<xml>" + "<return_code>FAIL</return_code>" + "<return_msg>支付通知失败</return_msg>" + "</xml>";
        }

        response.getWriter().append(result);

    }

    /**
     *
     * WebChatPayController.refund
     *
     * @Description: 退款
     *
     * @return
     * @throws Exception
     *             JSONObject
     * 
     * @exception Exception
     * 
     * @author liulong03@chinasoftinc.com
     *
     * @date 2018年8月3日-下午4:04:17
     *
     */
    @RequestMapping(value = "/refund", method = RequestMethod.GET)
    @ResponseBody
    public RestResult refund(String orderNo) throws Exception
    {
        try
        {
            LOGGER.info("需要退款的订单编号：{}", orderNo);
            // 查询需要退款的订单
            FreeOrder freeOrder = freeOrderService.queryFreeOrderDetail(orderNo);

            JSONObject result = webChatPayService.refund(freeOrder, "测试退款");
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
        catch (Exception e)
        {
            LOGGER.error("免单退单定时任务调度失败，原因：", e.fillInStackTrace());
            return RestResult.error(e.getMessage());
        }

        return RestResult.ok("退款成功");
    }

    /**
     *
     * WebChatPayController.refundNotify
     *
     * @Description: 退单通知
     *
     * @param request
     * @param response
     * @throws Exception
     *             void
     * 
     * @exception Exception
     * 
     * @author liulong03@chinasoftinc.com
     *
     * @date 2018年8月3日-上午11:33:04
     *
     */
    @RequestMapping(value = "/refund/notify", method = { RequestMethod.POST, RequestMethod.GET })
    public void refundNotify(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        BufferedReader br = new BufferedReader(new InputStreamReader((ServletInputStream) request.getInputStream()));
        String line = null;
        StringBuilder sb = new StringBuilder();
        while ((line = br.readLine()) != null)
        {
            sb.append(line);
        }
        LOGGER.info("WebChatPayController->updateOrder-退单回调结果={}", sb);
        // 解析并给微信发回收到通知确认
        JSONObject returnInfo = XmlUtil.documentToJSONObject(sb.toString());
        String returnCode = returnInfo.getString("return_code");
        if (returnCode.equals("SUCCESS"))
        {
            String resultCode = returnInfo.getString("result_code");
            if (resultCode.equals("SUCCESS"))
            {

                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
                sdf.parse(returnInfo.getString("time_end"));
            }
        }
    }
}
