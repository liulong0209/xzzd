package com.wlt.xzzd.controller;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.wlt.xzzd.model.FreeOrder;
import com.wlt.xzzd.service.FreeOrderService;
import com.wlt.xzzd.util.PagerUtil;

/**
 * 免单控制器
 * 
 * @ClassName: FreeOrderController
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author liulong03@chinasoftinc.com
 *
 */
@Controller
@RequestMapping("/api/free")
public class FreeOrderController
{
    /**
     * 日志记录器
     */
    private Logger LOGGER = LoggerFactory.getLogger(FreeOrderController.class);

    /**
     * 注入免单接口
     */
    @Autowired
    private FreeOrderService freeOrderService;

    /**
     * 
     * FreeOrderController.queryFreeOrders
     *
     * @Description: 查询用户免单数据
     *
     * @param openId
     * @return Object
     * 
     * @exception Exception
     * 
     * @author liulong03@chinasoftinc.com
     *
     * @date 2018年7月17日-下午6:59:35
     *
     */
    @RequestMapping(value = "/queryOwnerFreeOrders", method = RequestMethod.POST)
    @ResponseBody
    public Object queryOwnerFreeOrders(PagerUtil pager, String openId)
    {
        LOGGER.debug("FreeOrderController->queryOwnerFreeOrders->openId{}", openId);
        return freeOrderService.queryOwnerFreeOrders(pager, openId);
    }

    /**
     * 
     * FreeOrderController.queryJoinFreeOrders
     *
     * @Description: 查询用户参与免单数据
     *
     * @param openId
     * @return Object
     * 
     * @exception Exception
     * 
     * @author liulong03@chinasoftinc.com
     *
     * @date 2018年7月17日-下午6:59:35
     *
     */
    @RequestMapping(value = "/queryJoinFreeOrders", method = RequestMethod.POST)
    @ResponseBody
    public Object queryJoinFreeOrders(PagerUtil pager, String openId)
    {
        LOGGER.debug("FreeOrderController->queryOwnerFreeOrders->openId{}", openId);
        return freeOrderService.queryJoinFreeOrders(pager, openId);
    }

    /**
     *
     * FreeOrderController.queryFreeOrderDetail
     *
     * @Description: 查询免单详情
     *
     * @param orderNo
     * @return Object
     * 
     * @exception Exception
     * 
     * @author liulong03@chinasoftinc.com
     *
     * @date 2018年7月19日-下午9:56:28
     *
     */
    @RequestMapping(value = "/queryFreeOrderDetail", method = RequestMethod.POST)
    @ResponseBody
    public Object queryFreeOrderDetail(String orderNo)
    {
        LOGGER.debug("FreeOrderController->queryFreeOrderDetail->orderNo{}", orderNo);
        JSONObject result = new JSONObject();
        FreeOrder freeOrder = freeOrderService.queryFreeOrderDetail(orderNo);
        result.put("freeOrder", freeOrder);

        int stillNum = freeOrder.getStillNum().intValue();// 还差几人
        // 判断免单状态，小程序前台显示
        if (freeOrder.getState().intValue() == 1)
        {
            // 用户已支付
            if (freeOrder.getActEndTime().after(new Date()) && stillNum > 0)
            {
                // 时间未到，人数不够
                result.put("notice", 0);
                result.put("noticeMsg", "活动进行中");
            }
            else if (stillNum == 0)
            {
                // 人数够了
                result.put("notice", 1);
                result.put("noticeMsg", "待系统抽取免单者");
            }
            else if (freeOrder.getActEndTime().before(new Date()) && stillNum > 0)
            {
                // 时间到了，人数不够
                result.put("notice", 2);
                result.put("noticeMsg", "免单失败  待退款");
            }
        }
        else if (freeOrder.getState().intValue() == 2)
        {
            // 订单结束
            if (stillNum > 0)
            {
                // 时间到了，人数不够
                result.put("notice", 3);
                result.put("noticeMsg", "免单失败  已退款");
            }
            else if (stillNum == 0 && freeOrder.getRefundFlag() == 0)
            {
                // 免单结束，抽中了退款
                result.put("notice", 4);
                result.put("noticeMsg", "免单成功，恭喜你0元得");
            }
            else if (stillNum == 0 && freeOrder.getRefundFlag() == 0)
            {
                // 免单结束，未抽中了退款
                result.put("notice", 5);
                result.put("noticeMsg", "运气差了一点，未抽中退款");
            }
        }
        return result;
    }

}
