package com.wlt.xzzd.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.wlt.xzzd.dao.SchoolTopicMapper;
import com.wlt.xzzd.model.SchoolTopic;
import com.wlt.xzzd.service.SchoolTopicService;
import com.wlt.xzzd.util.PagerUtil;

/**
 * @ClassName: SchoolTopicServiceImpl
 * @Description: 学校话题业务实现类
 * @author liulong03@chinasoftinc.com
 *
 */
@Service
@Scope("prototype")
public class SchoolTopicServiceImpl implements SchoolTopicService
{

    /**
     * 注入学校话题持久层接口
     */
    @Autowired
    private SchoolTopicMapper schoolTopicMapper;

    public Integer addTopic(SchoolTopic schoolTopic)
    {
        return schoolTopicMapper.addTopic(schoolTopic);

    }

    public List<SchoolTopic> queryTopicBySchoolId(PagerUtil pager, Integer schoolId)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("startRow", (pager.getPageNo() - 1) * pager.getPageSize());
        map.put("pageSize", pager.getPageSize());
        map.put("schoolId", schoolId);

        return schoolTopicMapper.queryTopicBySchoolId(map);
    }

    public int queryTopicNum(Integer schoolId)
    {
        return schoolTopicMapper.queryTopicNum(schoolId);
    }

    public SchoolTopic queryTopicDetail(Integer topicId)
    {
        return schoolTopicMapper.queryTopicDetail(topicId);
    }

}
