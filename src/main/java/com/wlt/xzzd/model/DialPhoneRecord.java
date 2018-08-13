package com.wlt.xzzd.model;

import java.util.Date;

import lombok.Data;

/**
 * 拨打的电话记录
 * 
 * @ClassName: DialPhoneRecord
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author liulong03@chinasoftinc.com
 *
 */
@Data
public class DialPhoneRecord
{
    /**
     * 主键
     */
    private int id;

    /**
     * 拨打人电话
     */
    private String phoneNumber;

    /**
     * 拨打人姓名
     */
    private String userName;

    /**
     * 商家id
     */
    private int shopId;

    /**
     * 拨打时间
     */
    private Date DialTime;
}
