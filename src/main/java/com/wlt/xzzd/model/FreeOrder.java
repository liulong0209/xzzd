package com.wlt.xzzd.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import lombok.Data;

/**
 * 免单实体
 * 
 * @ClassName: FreeOrder
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author liulong03@chinasoftinc.com
 *
 */
@Data
public class FreeOrder
{
    /**
     * 订单编号
     */
    private String orderNo;

    /**
     * 父订单编号
     */
    private String parentOrderNo;

    /**
     * 微信中的订单编号
     */
    private String weixinOrderNo;

    /**
     * 用户openid
     */
    private String openId;

    /**
     * 商品id
     */
    private Integer productId;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 产品现价
     */
    private BigDecimal productCurrentPrice;

    /**
     * 商品图片
     */
    private String productImg;

    /**
     * 支付金额
     */
    private BigDecimal payMoney;

    /**
     * 支付时间
     */
    private Date payTime;

    /**
     * 类别 0发起者 1参与者
     */
    private Integer type;

    /**
     * 抽中退款的标志 0 抽中 1未抽中
     */
    private Integer refundFlag;

    /**
     * 状态
     */
    private Integer state;

    /**
     * 免单结果 0失败 1成功
     */
    private Integer result;

    /**
     * 状态名称
     */
    private String stateName;

    /**
     * 已参与人数
     */
    private Integer joinedNum;

    /**
     * 需要多少人参与
     */
    private Integer needjoinNum;

    /**
     * 还差多少人
     */
    private Integer stillNum;

    /**
     * 免单活动结束时间
     */
    private Date actEndTime;

    /**
     * 订单参与者
     */
    private List<FreeOrderJoinMember> joinMembers;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;
}
