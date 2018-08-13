package com.wlt.xzzd.model;

import lombok.Data;

/**
 * 砍价信息表
 * 
 * @ClassName: BargainInfo
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author liulong03@chinasoftinc.com
 *
 */
@Data
public class BargainInfo
{
    /**
     * 主键id
     */
    private Integer bargainId;

    /**
     * 商品id
     */
    private Integer productId;

    /**
     * 商品id
     */
    private String productName;

    /**
     * 商品图片
     */
    private String productImg;

    /**
     * 状态
     */
    private Integer state;

    /**
     * 状态名称
     */
    private String stateName;

    /**
     * 要砍多钱
     */
    private double beCutMoney;

    /**
     * 距砍价成功还有多钱
     */
    private double stillMoney;
}
