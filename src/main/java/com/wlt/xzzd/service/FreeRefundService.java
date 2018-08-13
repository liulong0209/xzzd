package com.wlt.xzzd.service;

import com.wlt.xzzd.model.FreeRefundOrder;

/**
 * 免单退单接口
 * 
 * @ClassName: FreeRefundService
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author liulong03@chinasoftinc.com
 *
 */
public interface FreeRefundService
{
    /**
     *
     * FreeRefundService.addRecord
     *
     * @Description: 增加记录
     *
     * @param freeRefundOrder
     *            void
     * 
     * @exception Exception
     * 
     * @author liulong03@chinasoftinc.com
     *
     * @date 2018年8月3日-下午4:38:53
     *
     */
    void addOrder(FreeRefundOrder freeRefundOrder);

    /**
     *
     * FreeRefundService.updateRecord
     *
     * @Description: 修改记录
     *
     * @param freeRefundOrder
     *            void
     * 
     * @exception Exception
     * 
     * @author liulong03@chinasoftinc.com
     *
     * @date 2018年8月3日-下午4:39:18
     *
     */
    void updateOrder(FreeRefundOrder freeRefundOrder);
}
