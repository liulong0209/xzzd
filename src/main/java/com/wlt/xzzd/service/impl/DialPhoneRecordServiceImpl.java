package com.wlt.xzzd.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.wlt.xzzd.dao.DialPhoneRecordMapper;
import com.wlt.xzzd.model.DialPhoneRecord;
import com.wlt.xzzd.service.DialPhoneRecordService;

/**
 * 拨打电话接口实现
 * 
 * @ClassName: DialPhoneRecordServiceImpl
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author liulong03@chinasoftinc.com
 *
 */
@Service
@Scope("prototype")
public class DialPhoneRecordServiceImpl implements DialPhoneRecordService
{

    /**
     * 注入记录拨打电话dao
     */
    @Autowired
    private DialPhoneRecordMapper dialPhoneRecordMapper;

    public int recordDailPhone(DialPhoneRecord dialPhoneRecord)
    {
        return dialPhoneRecordMapper.recordDailPhone(dialPhoneRecord);
    }

}
