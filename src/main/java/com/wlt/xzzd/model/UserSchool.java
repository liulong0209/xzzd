package com.wlt.xzzd.model;

import java.util.Date;

import lombok.Data;

/**
 * @ClassName: UserSchool
 * @Description: 用户学校
 * @author liulong03@chinasoftinc.com
 *
 */
@Data
public class UserSchool
{
    /**
     * 主键id
     */
    private Integer id;

    /**
     * 学校id
     */
    private Integer schoolId;

    /**
     * 用户openid
     */
    private String userOpenId;

    /**
     * 绑定状态
     */
    private Integer state;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}
