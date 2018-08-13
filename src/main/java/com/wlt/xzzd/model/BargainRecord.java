package com.wlt.xzzd.model;

import lombok.Data;

/**
 * 砍价记录实体
 * 
 * @ClassName: BargainRecord
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author liulong03@chinasoftinc.com
 *
 */
@Data
public class BargainRecord
{
    /**
     * 砍价人昵称
     */
    private String nickName;

    /**
     * 砍价人头像
     */
    private String avatarUrl;

    /**
     * 砍价金额
     */
    private double cutMoney;
}
