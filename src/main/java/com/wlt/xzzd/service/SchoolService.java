package com.wlt.xzzd.service;

import java.util.List;
import java.util.Map;

import com.wlt.xzzd.model.School;
import com.wlt.xzzd.model.UserSchool;

/**
 * @ClassName: SchoolService
 * @Description: 学校业务接口
 * @author liulong03@chinasoftinc.com
 *
 */
public interface SchoolService
{

    /**
     * 根据学校id查询学校
     *
     * SchoolService.querySchoolById
     *
     * @Description: TODO(这里用一句话描述这个方法的作用)
     *
     * @param id
     * @return School
     * 
     * @exception Exception
     * 
     * @author liulong03@chinasoftinc.com
     *
     * @date 2018年7月5日-下午7:52:38
     *
     */
    School querySchoolById(Integer id);

    /**
     *
     * SchoolService.querySchool
     *
     * @Description: 查询学校
     *
     * @return Map<String, Object>
     * 
     * @exception Exception
     * 
     * @author liulong03@chinasoftinc.com
     *
     * @date 2018年7月4日-下午4:27:10
     *
     */
    Map<String, Object> querySchool();

    /**
     *
     * SchoolService.queryHotSchool
     *
     * @Description: 热门学校，用户选择最多的前6名学校
     *
     * @return List<School>
     * 
     * @exception Exception
     * 
     * @author liulong03@chinasoftinc.com
     *
     * @date 2018年7月5日-下午2:11:56
     *
     */
    List<School> queryHotSchool();

    /**
     *
     * SchoolService.queryUserSchool
     *
     * @Description: 查询用户是否已经绑定过该学校
     *
     * @param userSchool
     * @return UserSchool
     * 
     * @exception Exception
     * 
     * @author liulong03@chinasoftinc.com
     *
     * @date 2018年7月5日-下午7:22:28
     *
     */

    UserSchool queryUserSchool(UserSchool userSchool);

    /**
     *
     * SchoolService.bindSchool
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
     * @date 2018年7月5日-下午7:11:43
     *
     */
    void bindSchool(UserSchool userSchool);

    /**
     *
     * SchoolService.querySchoolMemberNum
     *
     * @Description: 查询学校成员数量
     *
     * @param schoolId
     * @return int
     * 
     * @exception Exception
     * 
     * @author liulong03@chinasoftinc.com
     *
     * @date 2018年7月5日-下午9:38:19
     *
     */
    int querySchoolMemberNum(Integer schoolId);
}
