package com.wlt.xzzd.model;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

/**
 * 免单退单实体
 * 
 * @ClassName: FreeRefundOrder
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author liulong03@chinasoftinc.com
 *
 */
@Data
public class FreeRefundOrder
{
    /**
     * 商户退款单号
     */
    private String refundOrderNo;

    /**
     * 微信退款单号
     */
    private String refundId;

    /**
     * 源免单订单号
     */
    private String originOrderNo;

    /**
     * 退款金额
     */
    private BigDecimal refundFee;

    /**
     * 退款时间
     */
    private Date refundTime;

    /**
     * 退款结果0 失败 1成功'
     */
    private String result;

    /**
     * 退款失败错误代码
     */
    private String errorCode;

    /**
     * 退款错误代码描述
     */
    private String errorCodeDes;

    /**
     * 
     */
    private Date createTime;

    /**
     * 
     */
    private Date updateTime;
}
