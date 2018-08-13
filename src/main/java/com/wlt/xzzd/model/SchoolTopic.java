package com.wlt.xzzd.model;

import java.util.Date;

import lombok.Data;

/**
 * 学校话题实体
 * 
 * @ClassName: SchoolTopic
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author liulong03@chinasoftinc.com
 *
 */
@Data
public class SchoolTopic
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
     * 作者openid
     */
    private String authorOpenId;

    /**
     * 作者昵称
     */
    private String authorNickname;

    /**
     * 作者头像
     */
    private String authorAvatarUrl;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 标记 0正常 1删除
     */
    private Integer mark;

    /**
     * 置顶级别
     */
    private Integer topGrade;

    /**
     * 发布时间
     */
    private Date publishTime;

    /**
     * 发布时间字符串
     */
    private String publishTimeStr;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 回复数
     * 
     */
    private Integer replyNum;

    /**
     * 领域id
     */
    private Integer field;
}
