package com.wlt.xzzd.service;

import com.wlt.xzzd.model.UserInfo;

/**
 * 用户接口
 * 
 * @ClassName: UserService
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author liulong03@chinasoftinc.com
 *
 */
public interface UserService
{
    /**
     *
     * UserService.addUser
     *
     * @Description: 同步用户信息
     *
     * @param userInfo
     * @return int
     * 
     * @exception Exception
     * 
     * @author liulong03@chinasoftinc.com
     *
     * @date 2018年7月12日-上午11:33:33
     *
     */
    void syncUser(UserInfo userInfo);
}
