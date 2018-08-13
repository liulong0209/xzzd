package com.wlt.xzzd.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.wlt.xzzd.dao.FreeRefundOrderMapper;
import com.wlt.xzzd.model.FreeRefundOrder;
import com.wlt.xzzd.service.FreeRefundService;

/**
 * 免单退单接口实现
 * 
 * @ClassName: FreeRefundServiceImpl
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author liulong03@chinasoftinc.com
 *
 */
@Service
@Scope("prototype")
public class FreeRefundServiceImpl implements FreeRefundService
{
    @Autowired
    private FreeRefundOrderMapper freeRefundOrderMapper;

    public void addOrder(FreeRefundOrder freeRefundOrder)
    {
        freeRefundOrderMapper.addOrder(freeRefundOrder);
    }

    public void updateOrder(FreeRefundOrder freeRefundOrder)
    {
        freeRefundOrderMapper.updateOrder(freeRefundOrder);
    }

}
