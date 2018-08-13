package com.wlt.xzzd.model;

import java.util.Date;

import lombok.Data;

/**
 * @ClassName: School
 * @Description: 同学校信息
 * @author liulong03@chinasoftinc.com
 *
 */
@Data
public class School
{
    /**
     * 主键id
     */
    private Integer id;

    /**
     * 学校名称
     */
    private String name;

    /**
     * 别名
     */
    private String alias;

    /**
     * 代码
     */
    private String code;

    /**
     * 首字母
     */
    private String initial;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}
