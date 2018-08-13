package com.wlt.xzzd.dao;

import com.wlt.xzzd.model.UserInfo;

/**
 * 用户持久层
 * 
 * @ClassName: UserMapper
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author liulong03@chinasoftinc.com
 *
 */
public interface UserMapper
{
    /**
     *
     * UserMapper.addUser
     *
     * @Description: 新增用户
     *
     * @param userInfo
     * @return int
     * 
     * @exception Exception
     * 
     * @author liulong03@chinasoftinc.com
     *
     * @date 2018年7月12日-上午11:37:08
     *
     */
    int addUser(UserInfo userInfo);

    /**
     *
     * UserMapper.update
     *
     * @Description: 更新用户
     *
     * @param userInfo
     *            void
     * 
     * @exception Exception
     * 
     * @author liulong03@chinasoftinc.com
     *
     * @date 2018年7月12日-上午11:37:36
     *
     */
    void updateUser(UserInfo userInfo);

    /**
     *
     * UserMapper.judgeUserExist
     *
     * @Description: 查询用户
     *
     * @param openId
     * @return UserInfo
     * 
     * @exception Exception
     * 
     * @author liulong03@chinasoftinc.com
     *
     * @date 2018年7月12日-上午11:38:29
     *
     */
    UserInfo queryUser(String openId);
}
