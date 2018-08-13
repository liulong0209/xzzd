package com.wlt.xzzd.model;

import lombok.Data;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 签名信息
 * 
 * @author zuoliangzhu
 *
 */
@Data
public class SignInfo
{

    private String appId;// 小程序ID
    private String timeStamp;// 时间戳
    private String nonceStr;// 随机串
    @XStreamAlias("package")
    private String repay_id;
    private String signType;// 签名方式
}
