package com.wlt.xzzd.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wlt.xzzd.dao.SchoolMapper;
import com.wlt.xzzd.model.School;
import com.wlt.xzzd.model.UserSchool;
import com.wlt.xzzd.service.SchoolService;

@Service
@Scope("prototype")
public class SchoolServiceImpl implements SchoolService
{
    /**
     * 注入学校dao
     */
    @Resource
    private SchoolMapper schoolMapper;

    public School querySchoolById(Integer id)
    {
        return schoolMapper.querySchoolById(id);
    }

    public Map<String, Object> querySchool()
    {
        Map<String, Object> data = new HashMap<String, Object>();

        List<School> schoolList = schoolMapper.querySchool();
        if (!CollectionUtils.isEmpty(schoolList))
        {
            // 分组后的学校
            Map<String, List<School>> groupSchool = new TreeMap<String, List<School>>();

            // 学校检索的首字母
            List<String> searchLetter = new ArrayList<String>();
            for (School school : schoolList)
            {
                if (!searchLetter.contains(school.getInitial()))
                {
                    searchLetter.add(school.getInitial());

                    groupSchool.put(school.getInitial(), new ArrayList<School>());
                }
            }
            data.put("searchLetter", searchLetter);

            // 对学校信息进行分组
            for (School school : schoolList)
            {
                List<School> s = groupSchool.get(school.getInitial());
                s.add(school);
            }

            // 转换成对象数组
            JSONArray schoolArray = new JSONArray();
            for (Map.Entry<String, List<School>> entry : groupSchool.entrySet())
            {
                JSONObject o = new JSONObject();
                o.put("initial", entry.getKey());
                o.put("schoolInfo", entry.getValue());
                schoolArray.add(o);
            }
            data.put("schoolArray", schoolArray);

        }
        return data;
    }

    public List<School> queryHotSchool()
    {
        return schoolMapper.queryHotSchool();
    }

    public UserSchool queryUserSchool(UserSchool userSchool)
    {
        return schoolMapper.queryUserSchool(userSchool);
    }

    @Transactional(rollbackFor = Exception.class)
    public void bindSchool(UserSchool userSchool)
    {
        // 先将之前绑定的学校标记删除
        schoolMapper.markdelUserSchool(userSchool);

        // 查询用户是否已经绑定过该学校
        UserSchool us = schoolMapper.queryUserSchool(userSchool);
        if (null == us)
        {
            schoolMapper.addUserSchool(userSchool);
        }
        else
        {
            schoolMapper.recoverUserSchool(userSchool);
        }
    }

    public int querySchoolMemberNum(Integer schoolId)
    {
        return schoolMapper.querySchoolMemberNum(schoolId);
    }

}
