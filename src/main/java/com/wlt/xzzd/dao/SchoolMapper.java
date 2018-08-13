package com.wlt.xzzd.dao;

import java.util.List;

import com.wlt.xzzd.model.School;
import com.wlt.xzzd.model.UserSchool;

/**
 * @ClassName: SchoolMapper
 * @Description: 同学校dao
 * @author liulong03@chinasoftinc.com
 *
 */
public interface SchoolMapper
{
    /**
     * 
     *
     * SchoolMapper.querySchoolById
     *
     * @Description: 根据学校id查询学校
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
     * SchoolMapper.querySchool
     *
     * @Description: 查询学校
     *
     * @return List<School>
     * 
     * @exception Exception
     * 
     * @author liulong03@chinasoftinc.com
     *
     * @date 2018年7月4日-下午4:27:10
     *
     */
    List<School> querySchool();

    /**
     *
     * SchoolMapper.queryHotSchool
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
     * SchoolMapper.queryUserSchool
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
     * SchoolMapper.addUserSchool
     *
     * @Description: 增加用户学校数据
     *
     * @param userSchool
     *            void
     * 
     * @exception Exception
     * 
     * @author liulong03@chinasoftinc.com
     *
     * @date 2018年7月5日-下午7:13:29
     *
     */
    void addUserSchool(UserSchool userSchool);

    /**
     *
     * SchoolMapper.updateUserSchool
     *
     * @Description: 标记删除用户学校数据
     *
     * @param userSchool
     *            void
     * 
     * @exception Exception
     * 
     * @author liulong03@chinasoftinc.com
     *
     * @date 2018年7月5日-下午7:17:55
     *
     */
    void markdelUserSchool(UserSchool userSchool);

    /**
     *
     * SchoolMapper.recoverUserSchool
     *
     * @Description: 恢复绑定
     *
     * @param userSchool
     *            void
     * 
     * @exception Exception
     * 
     * @author liulong03@chinasoftinc.com
     *
     * @date 2018年7月5日-下午7:37:16
     *
     */
    void recoverUserSchool(UserSchool userSchool);

    /**
     *
     * SchoolMapper.querySchoolMemberNum
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
