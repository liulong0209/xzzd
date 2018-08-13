package com.wlt.xzzd.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.wlt.xzzd.dao.SchoolTopicReplyMapper;
import com.wlt.xzzd.model.SchoolTopicReply;
import com.wlt.xzzd.service.SchoolTopicReplyService;
import com.wlt.xzzd.util.PagerUtil;

/**
 * 话题回复业务接口实现
 * 
 * @ClassName: SchoolTopicReplyServiceImpl
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author liulong03@chinasoftinc.com
 *
 */
@Service
@Scope("prototype")
public class SchoolTopicReplyServiceImpl implements SchoolTopicReplyService
{

    /**
     * 注入话题回复dao
     */
    @Autowired
    private SchoolTopicReplyMapper schoolTopicReplyMapper;

    public int countReplyNum(Integer topicId)
    {
        return schoolTopicReplyMapper.countReplyNum(topicId);
    }

    public List<SchoolTopicReply> queryReply(PagerUtil pager, Integer topicId)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("startRow", (pager.getPageNo() - 1) * pager.getPageSize());
        map.put("pageSize", pager.getPageSize());
        map.put("topicId", topicId);

        return schoolTopicReplyMapper.queryReply(map);
    }

    public int reply(SchoolTopicReply schoolTopicReply)
    {

        return schoolTopicReplyMapper.reply(schoolTopicReply);
    }

    public SchoolTopicReply queryReplyById(Integer id)
    {
        return schoolTopicReplyMapper.queryReplyById(id);
    }

}
