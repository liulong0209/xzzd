package com.wlt.xzzd.dao;

import java.util.List;
import java.util.Map;

import com.wlt.xzzd.model.SchoolTopic;

/**
 * @ClassName: SchoolTopicMapper
 * @Description: 学校话题持久层
 * @author liulong03@chinasoftinc.com
 *
 */
public interface SchoolTopicMapper
{
    /**
     * 提交话题
     *
     * SchoolTopicMapper.addTopic
     *
     * @Description: TODO(这里用一句话描述这个方法的作用)
     *
     * @param schoolTopic
     *            Integer
     * 
     * @exception Exception
     * 
     * @author liulong03@chinasoftinc.com
     *
     * @date 2018年7月5日-下午11:00:16
     *
     */

    public Integer addTopic(SchoolTopic schoolTopic);

    /**
     *
     * SchoolTopicMapper.queryTopicBySchoolId
     *
     * @Description: 根据学校ID查询学校话题
     *
     * @param param
     * @return List<SchoolTopic>
     * 
     * @exception Exception
     * 
     * @author liulong03@chinasoftinc.com
     *
     * @date 2018年7月7日-上午12:08:40
     *
     */
    List<SchoolTopic> queryTopicBySchoolId(Map<String, Object> param);

    /**
     *
     * SchoolTopicMapper.queryTopicNum
     *
     * @Description: 查询学校话题数量
     *
     * @param schoolId
     * @return int
     * 
     * @exception Exception
     * 
     * @author liulong03@chinasoftinc.com
     *
     * @date 2018年7月5日-下午10:02:53
     *
     */
    int queryTopicNum(Integer schoolId);

    /**
     * 
     *
     * SchoolTopicMapper.queryTopicDetail
     *
     * @Description: 查询单条话题信息
     *
     * @param topicId
     * @return SchoolTopic
     * 
     * @exception Exception
     * 
     * @author liulong03@chinasoftinc.com
     *
     * @date 2018年7月8日-下午11:06:56
     *
     */
    SchoolTopic queryTopicDetail(Integer topicId);
}
