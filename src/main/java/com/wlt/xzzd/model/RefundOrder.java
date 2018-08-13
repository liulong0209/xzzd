package com.wlt.xzzd.model;

import lombok.Data;

/**
 * 退单实体
 * 
 * @ClassName: RefundOrder
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author liulong03@chinasoftinc.com
 *
 */
@Data
public class RefundOrder
{
    private String appid;// 小程序ID
    private String mch_id;// 商户号
    private String nonce_str;// 随机字符串
    private String sign;// 签名
    private String out_trade_no;// 商户订单号
    private String out_refund_no;// 退单号
    private int total_fee;// 标价金额 ,单位为分
    private int refund_fee;// 退款金额,单位为分
    private String notify_url;// 通知地址
    private String refund_desc;//
}
