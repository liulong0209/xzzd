package com.wlt.xzzd.service;

import com.wlt.xzzd.model.DialPhoneRecord;

/**
 * 拨打电话接口
 * 
 * @ClassName: DialPhoneRecordService
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author liulong03@chinasoftinc.com
 *
 */
public interface DialPhoneRecordService
{
    /**
     *
     * DialPhoneRecordService.recordDailPhone
     *
     * @Description: 记录拨打电话
     *
     * @param dialPhoneRecord
     * @return int
     * 
     * @exception Exception
     * 
     * @author liulong03@chinasoftinc.com
     *
     * @date 2018年7月13日-上午11:44:20
     *
     */
    int recordDailPhone(DialPhoneRecord dialPhoneRecord);
}
