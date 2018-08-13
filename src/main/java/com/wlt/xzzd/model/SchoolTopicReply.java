package com.wlt.xzzd.model;

import java.util.Date;

import lombok.Data;

/**
 * 查询话题回复
 * 
 * @ClassName: SchoolTopicReply
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author liulong03@chinasoftinc.com
 *
 */
@Data
public class SchoolTopicReply
{
    /**
     * 主键id
     */
    private Integer id;

    /**
     * 所属话题id
     */
    private Integer topicId;

    /**
     * 评论者openid
     */
    private String authorOpenId;

    /**
     * 评论者昵称
     */
    private String authorNickname;

    /**
     * 评论者头像
     */
    private String authorAvatarUrl;

    /**
     * 被评论者openid
     */
    private String originOpenId;

    /**
     * 被评论者昵称
     */
    private String originNickname;

    /**
     * 被评论者头像
     */
    private String originAvatarUrl;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 置顶级别
     */
    private Integer topGrade;

    /**
     * 0 正常 1删除
     */
    private Integer mark;

    /**
     * 评论时间
     */
    private Date replyTime;

    /**
     * 评论时间字符串
     */
    private String replyTimeStr;

}
