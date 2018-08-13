package com.wlt.xzzd.service;

import java.util.List;
import java.util.Map;

import com.wlt.xzzd.model.FreeOrder;
import com.wlt.xzzd.util.PagerUtil;

/**
 * 免单接口
 * 
 * @ClassName: FreeOrderService
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author liulong03@chinasoftinc.com
 *
 */
public interface FreeOrderService
{
    /**
     *
     * FreeOrderService.queryOwnerFreeOrders
     *
     * @Description:查询我发起的免单列表
     *
     * @param pager
     * @param openId
     * @return List<FreeOrder>
     * 
     * @exception Exception
     * 
     * @author liulong03@chinasoftinc.com
     *
     * @date 2018年7月17日-下午7:05:38
     *
     */
    List<FreeOrder> queryOwnerFreeOrders(PagerUtil pager, String openId);

    /**
     *
     * FreeOrderService.queryJoinFreeOrders
     *
     * @Description:查询我参与的免单列表
     *
     * @param pager
     * @param openId
     * @return List<FreeOrder>
     * 
     * @exception Exception
     * 
     * @author liulong03@chinasoftinc.com
     *
     * @date 2018年7月17日-下午7:05:38
     *
     */
    List<FreeOrder> queryJoinFreeOrders(PagerUtil pager, String openId);

    /**
     * 查询免单详情
     *
     * FreeOrderService.queryFreeOrderDetail
     *
     * @Description: TODO(这里用一句话描述这个方法的作用)
     *
     * @param orderNo
     * @return FreeOrder
     * 
     * @exception Exception
     * 
     * @author liulong03@chinasoftinc.com
     *
     * @date 2018年7月19日-下午6:36:15
     *
     */
    FreeOrder queryFreeOrderDetail(String orderNo);

    /**
     *
     * FreeOrderService.createFreeOrder
     *
     * @Description: 生成订单
     *
     * @param freeOrder
     *            void
     * 
     * @exception Exception
     * 
     * @author liulong03@chinasoftinc.com
     *
     * @date 2018年7月31日-下午6:57:11
     *
     */
    void createFreeOrder(FreeOrder freeOrder);

    /**
     * 更新订单
     *
     * FreeOrderService.updateFreeOrder
     *
     * @Description: TODO(这里用一句话描述这个方法的作用)
     *
     * @param freeOrder
     *            void
     * 
     * @exception Exception
     * 
     * @author liulong03@chinasoftinc.com
     *
     * @date 2018年7月31日-下午8:46:06
     *
     */
    void updateFreeOrder(FreeOrder freeOrder);

    /**
     *
     * FreeOrderService.queryNeedRefundOrder
     *
     * @Description: 查询需要退单的父订单
     *
     * @return Map<String, List<FreeOrder>>
     * 
     * @exception Exception
     * 
     * @author liulong03@chinasoftinc.com
     *
     * @date 2018年8月6日-下午7:12:26
     *
     */
    Map<String, Object> queryNeedRefundOrder();
}
