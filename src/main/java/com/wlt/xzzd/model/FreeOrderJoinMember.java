package com.wlt.xzzd.model;

import java.math.BigDecimal;

import lombok.Data;

/**
 * 免单参与者
 * 
 * @ClassName: FreeOrderJoinMember
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author liulong03@chinasoftinc.com
 *
 */
@Data
public class FreeOrderJoinMember
{
    /**
     * 用户openid
     */
    private String openId;

    /**
     * 昵称
     */
    private String nickame;

    /**
     * 头像
     */
    private String avatarUrl;

    /**
     * 订单id
     */
    private String orderNo;

    /**
     * 父订单id
     */
    private String parentNo;

    /**
     * 身份 0发起者 1参与者
     */
    private Integer identity;

    /**
     * 支付金额
     */
    private BigDecimal payMoney;
}
