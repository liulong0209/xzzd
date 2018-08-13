package com.wlt.xzzd.service;

import java.util.List;

import com.wlt.xzzd.model.SchoolTopicReply;
import com.wlt.xzzd.util.PagerUtil;

/**
 * 话题回复
 * 
 * @ClassName: SchoolToppicReplyService
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author liulong03@chinasoftinc.com
 *
 */
public interface SchoolTopicReplyService
{
    /**
     *
     * SchoolToppicReplyService.countReplyNum
     *
     * @Description: 统计回头回复数量
     *
     * @param topicId
     * @return int
     * 
     * @exception Exception
     * 
     * @author liulong03@chinasoftinc.com
     *
     * @date 2018年7月8日-下午10:15:22
     *
     */
    int countReplyNum(Integer topicId);

    /**
     *
     * SchoolTopicReplyService.queryReply
     *
     * @Description: 查询话题回复
     *
     * @param pager
     * @param topicId
     * @return List<SchoolTopicReply>
     * 
     * @exception Exception
     * 
     * @author liulong03@chinasoftinc.com
     *
     * @date 2018年7月9日-下午12:37:38
     *
     */
    List<SchoolTopicReply> queryReply(PagerUtil pager, Integer topicId);

    /**
     * 话题回复
     *
     * SchoolTopicReplyService.reply
     *
     * @Description: TODO(这里用一句话描述这个方法的作用)
     *
     * @param schoolTopicReply
     * @return int
     * 
     * @exception Exception
     * 
     * @author liulong03@chinasoftinc.com
     *
     * @date 2018年7月9日-下午12:48:28
     *
     */
    int reply(SchoolTopicReply schoolTopicReply);

    /**
     *
     * SchoolTopicReplyService.queryReplyById
     *
     * @Description: 根据id查询回复信息
     *
     * @param id
     * @return SchoolTopicReply
     * 
     * @exception Exception
     * 
     * @author liulong03@chinasoftinc.com
     *
     * @date 2018年7月12日-上午10:35:16
     *
     */
    SchoolTopicReply queryReplyById(Integer id);
}
