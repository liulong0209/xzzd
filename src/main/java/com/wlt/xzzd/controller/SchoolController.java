package com.wlt.xzzd.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wlt.xzzd.model.School;
import com.wlt.xzzd.model.SchoolTopic;
import com.wlt.xzzd.model.SchoolTopicReply;
import com.wlt.xzzd.model.UserSchool;
import com.wlt.xzzd.service.SchoolService;
import com.wlt.xzzd.service.SchoolTopicReplyService;
import com.wlt.xzzd.service.SchoolTopicService;
import com.wlt.xzzd.util.PagerUtil;

/**
 * 同学校控制层
 * 
 * @ClassName: SchoolController
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author liulong03@chinasoftinc.com
 *
 */
@Controller
@RequestMapping("/api/school")
public class SchoolController
{
    /**
     * 日志记录器
     */
    private Logger LOGGER = LoggerFactory.getLogger(SchoolController.class);

    /**
     * 注入学校业务接口
     */
    @Autowired
    private SchoolService schoolService;

    /**
     * 注入学校话题业务接口
     */
    @Autowired
    private SchoolTopicService schoolTopicService;

    /**
     * 注入话题回复业务接口
     */
    @Autowired
    private SchoolTopicReplyService schoolTopicReplyService;

    /**
     *
     * SchoolController.queryMySchool
     *
     * @Description: 同学校首页查询我的学校
     *
     * @return Object
     * 
     * @exception Exception
     * 
     * @author liulong03@chinasoftinc.com
     *
     * @date 2018年7月5日-下午7:50:04
     *
     */
    @RequestMapping(value = "/queryMySchool", method = RequestMethod.POST)
    @ResponseBody
    public Object queryMySchool(UserSchool userSchool)
    {
        // 查询学校列表
        Map<String, Object> schoolObj = new HashMap<String, Object>();
        // 没有学校id(首页)，根据用户openid查询学校id
        if (userSchool.getSchoolId().equals(0))
        {
            UserSchool us = schoolService.queryUserSchool(userSchool);
            // 没有绑定学校直接返回
            if (null == us)
            {
                return schoolObj;
            }
            userSchool.setSchoolId(us.getSchoolId());
        }

        School school = schoolService.querySchoolById(userSchool.getSchoolId());

        // 查询学校成员数
        int membersNum = schoolService.querySchoolMemberNum(userSchool.getSchoolId());
        // 查询学校话题
        int topicNum = schoolTopicService.queryTopicNum(userSchool.getSchoolId());
        schoolObj.put("school", school);
        schoolObj.put("members", membersNum);
        schoolObj.put("topicNum", topicNum);
        return schoolObj;
    }

    /**
     *
     * SchoolController.querySchoolTopic
     *
     * @Description: 查询学校话题
     *
     * @param pager
     * @param schoolId
     * @return Object
     * 
     * @exception Exception
     * 
     * @author liulong03@chinasoftinc.com
     *
     * @date 2018年7月7日-上午12:26:05
     *
     */
    @RequestMapping(value = "/querySchoolTopic", method = RequestMethod.POST)
    @ResponseBody
    public Object querySchoolTopic(PagerUtil pager, Integer schoolId)
    {
        List<SchoolTopic> topicList = schoolTopicService.queryTopicBySchoolId(pager, schoolId);
        if (!CollectionUtils.isEmpty(topicList))
        {
            // 循环查询话题的回复数量
            for (SchoolTopic schoolTopic : topicList)
            {
                int replyNum = schoolTopicReplyService.countReplyNum(schoolTopic.getId());
                schoolTopic.setReplyNum(replyNum);
            }
        }
        return topicList;
    }

    /**
     *
     * SchoolController.querySchool
     *
     * @Description: 查询学校数据
     *
     * @return Object
     * 
     * @exception Exception
     * 
     * @author liulong03@chinasoftinc.com
     *
     * @date 2018年7月4日-下午4:42:28
     *
     */
    @RequestMapping(value = "/querySchool")
    @ResponseBody
    public Object querySchool()
    {
        // 查询学校列表
        Map<String, Object> data = schoolService.querySchool();

        // 热门学校
        List<School> hotSchool = schoolService.queryHotSchool();
        data.put("hotSchool", hotSchool);
        return data;
    }

    /**
     *
     * SchoolController.bindSchool
     *
     * @Description: 用户绑定学校
     *
     * @param userSchool
     *            void
     * 
     * @exception Exception
     * 
     * @author liulong03@chinasoftinc.com
     *
     * @date 2018年7月5日-下午7:49:24
     *
     */
    @RequestMapping(value = "/bindSchool", method = RequestMethod.POST)
    @ResponseBody
    public void bindSchool(UserSchool userSchool)
    {
        LOGGER.debug("SchoolController->bindSchool->schoolId:{}", userSchool.getSchoolId());
        LOGGER.debug("SchoolController->bindSchool->userOpenId:{}", userSchool.getUserOpenId());

        schoolService.bindSchool(userSchool);
    }

    /**
     *
     * SchoolController.addTopic
     *
     * @Description: 发布话题
     *
     * @param schoolTopic
     *            void
     * 
     * @exception Exception
     * 
     * @author liulong03@chinasoftinc.com
     *
     * @date 2018年7月5日-下午10:58:43
     *
     */
    @RequestMapping(value = "/addTopic", method = RequestMethod.POST)
    @ResponseBody
    public void addTopic(SchoolTopic schoolTopic)
    {
        schoolTopicService.addTopic(schoolTopic);
    }

    /**
     * 
     *
     * SchoolController.queryTopicDetail
     *
     * @Description: 查询话题详情
     *
     * @param topicId
     * @return Object
     * 
     * @exception Exception
     * 
     * @author liulong03@chinasoftinc.com
     *
     * @date 2018年7月8日-下午11:13:24
     *
     */
    @RequestMapping("/queryTopicDetail")
    @ResponseBody
    public Object queryTopicDetail(Integer topicId)
    {
        SchoolTopic topic = schoolTopicService.queryTopicDetail(topicId);
        return topic;
    }

    /**
     *
     * SchoolController.queryTopicReply
     *
     * @Description: 查询话题回复
     *
     * @param pager
     * @param topicId
     * @return Object
     * 
     * @exception Exception
     * 
     * @author liulong03@chinasoftinc.com
     *
     * @date 2018年7月9日-下午12:27:57
     *
     */
    @RequestMapping(value = "/queryTopicReply", method = RequestMethod.POST)
    @ResponseBody
    public Object queryTopicReply(PagerUtil pager, Integer topicId)
    {
        return schoolTopicReplyService.queryReply(pager, topicId);
    }

    /**
     *
     * SchoolController.addTopicReply
     *
     * @Description: 话题回复
     *
     * @param schoolTopicReply
     *            void
     * 
     * @exception Exception
     * 
     * @author liulong03@chinasoftinc.com
     *
     * @date 2018年7月5日-下午10:58:43
     *
     */
    @RequestMapping(value = "/reply", method = RequestMethod.POST)
    @ResponseBody
    public Object reply(SchoolTopicReply schoolTopicReply)
    {
        schoolTopicReplyService.reply(schoolTopicReply);
        // 回复成功后将新增内容查询传递到前台显示
        SchoolTopicReply addresly = schoolTopicReplyService.queryReplyById(schoolTopicReply.getId());
        return addresly;
    }
}
