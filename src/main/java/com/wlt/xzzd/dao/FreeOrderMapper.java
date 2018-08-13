package com.wlt.xzzd.dao;

import java.util.List;
import java.util.Map;

import com.wlt.xzzd.model.FreeOrder;
import com.wlt.xzzd.model.FreeOrderJoinMember;

/**
 * 免单持久层接口
 * 
 * @ClassName: FreeOrderMapper
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author liulong03@chinasoftinc.com
 *
 */
public interface FreeOrderMapper
{
    /**
     * 查询我发起的免单
     *
     * FreeOrderMapper.queryOwnerFreeOrders
     *
     * @Description: TODO(这里用一句话描述这个方法的作用)
     *
     * @param param
     * @return List<FreeOrder>
     * 
     * @exception Exception
     * 
     * @author liulong03@chinasoftinc.com
     *
     * @date 2018年7月18日-下午7:38:55
     *
     */
    List<FreeOrder> queryOwnerFreeOrders(Map<String, Object> param);

    /**
     * 查询我参与的免单
     *
     * FreeOrderMapper.queryJoinFreeOrders
     *
     * @Description: TODO(这里用一句话描述这个方法的作用)
     *
     * @param param
     * @return List<FreeOrder>
     * 
     * @exception Exception
     * 
     * @author liulong03@chinasoftinc.com
     *
     * @date 2018年7月18日-下午7:38:55
     *
     */
    List<FreeOrder> queryJoinFreeOrders(Map<String, Object> param);

    /**
     *
     * FreeOrderMapper.countJoinedNum
     *
     * @Description: 查询参与人数
     *
     * @param orderNo
     * @return int
     * 
     * @exception Exception
     * 
     * @author liulong03@chinasoftinc.com
     *
     * @date 2018年7月18日-下午7:59:38
     *
     */
    int countJoinedNum(String orderNo);

    /**
     * 根据订单编号查询订单
     *
     * FreeOrderMapper.queryFreeOrderByOrderNo
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
     * @date 2018年7月26日-下午12:38:05
     *
     */
    FreeOrder queryFreeOrderByOrderNo(String orderNo);

    /**
     * 查询免单参与者
     *
     * FreeOrderMapper.queryJoinMembers
     *
     * @Description: TODO(这里用一句话描述这个方法的作用)
     *
     * @param orderNo
     * @return List<FreeOrderJoinMember>
     * 
     * @exception Exception
     * 
     * @author liulong03@chinasoftinc.com
     *
     * @date 2018年7月19日-下午11:02:24
     *
     */
    List<FreeOrderJoinMember> queryJoinMembers(String orderNo);

    /**
     *
     * FreeOrderMapper.createFreeOrder
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
     * FreeOrderMapper.updateFreeOrder
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
     * FreeOrderMapper.queryUnFinishTopOrder
     *
     * @Description:获取未完成的顶级订单
     *
     * @return List<FreeOrder>
     * 
     * @exception Exception
     * 
     * @author liulong03@chinasoftinc.com
     *
     * @date 2018年8月6日-下午7:27:38
     *
     */
    List<FreeOrder> queryUnFinishTopOrder();
}
