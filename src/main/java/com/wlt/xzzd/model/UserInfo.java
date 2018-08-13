package com.wlt.xzzd.model;

import lombok.Data;

/**
 * 用户信息实体
 * 
 * @ClassName: UserInfo
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author liulong03@chinasoftinc.com
 *
 */
@Data
public class UserInfo
{
    /**
     * 主键id
     */
    private Integer id;

    /**
     * openid
     */
    private String openId;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 头像地址
     */
    private String avatarUrl;

    /**
     * 性别
     */
    private int gender;

    /**
     * 国家
     */
    private String country;

    /**
     * 省份
     */
    private String province;

    /**
     * 城市
     */
    private String city;

    /**
     * 新建时间
     */
    private String creatTime;
}
