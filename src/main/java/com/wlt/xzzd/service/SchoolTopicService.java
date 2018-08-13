package com.wlt.xzzd.service;

import java.util.List;

import com.wlt.xzzd.model.SchoolTopic;
import com.wlt.xzzd.util.PagerUtil;

/**
 * @ClassName: SchoolTopicService
 * @Description: 学校话题业务类
 * @author liulong03@chinasoftinc.com
 *
 */
public interface SchoolTopicService
{
    /**
     * 提交话题
     *
     * SchoolTopicService.addTopic
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

    Integer addTopic(SchoolTopic schoolTopic);

    /**
     *
     * SchoolTopicService.queryTopicBySchoolId
     *
     * @Description: 根据学校ID查询学校话题
     *
     * @param pager
     * @param schoolId
     * @return List<SchoolTopic>
     * 
     * @exception Exception
     * 
     * @author liulong03@chinasoftinc.com
     *
     * @date 2018年7月7日-上午12:08:40
     *
     */
    List<SchoolTopic> queryTopicBySchoolId(PagerUtil pager, Integer schoolId);

    /**
     *
     * SchoolTopicService.queryTopicNum
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
     * SchoolTopicService.queryTopicDetail
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
