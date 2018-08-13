package com.wlt.xzzd.service;

import com.alibaba.fastjson.JSONObject;
import com.wlt.xzzd.model.FreeOrder;
import com.wlt.xzzd.model.OrderInfo;

/**
 * 微信支付接口
 * 
 * @ClassName: WebChatPayService
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author liulong03@chinasoftinc.com
 *
 */
public interface WebChatPayService
{
    /**
     * 预下单 WebChatPayService.preOrder
     *
     * @Description: TODO(这里用一句话描述这个方法的作用)
     *
     * @param orderInfo
     * @return JsonObject
     * 
     * @exception Exception
     * 
     * @author liulong03@chinasoftinc.com
     *
     * @date 2018年7月31日-下午12:42:01
     *
     */
    JSONObject preOrder(OrderInfo orderInfo);

    /**
     *
     * WebChatPayService.refund
     *
     * @Description: 退单
     *
     * @param freeOrder
     * @param refundDesc
     * @return
     * @throws Exception
     *             JSONObject
     * 
     * @exception Exception
     * 
     * @author liulong03@chinasoftinc.com
     *
     * @date 2018年8月3日-上午11:28:31
     *
     */
    JSONObject refund(FreeOrder freeOrder, String refundDesc) throws Exception;
}
