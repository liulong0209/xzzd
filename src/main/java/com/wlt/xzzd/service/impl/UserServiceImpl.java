package com.wlt.xzzd.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wlt.xzzd.dao.UserMapper;
import com.wlt.xzzd.model.UserInfo;
import com.wlt.xzzd.service.UserService;

/**
 * @ClassName: UserServiceImpl
 * @Description: 用户接口实现
 * @author liulong03@chinasoftinc.com
 *
 */
@Service
@Scope("prototype")
public class UserServiceImpl implements UserService
{

    /**
     * 注入用户持久层接口
     */
    @Autowired
    private UserMapper userMapper;

    @Transactional(rollbackFor = Exception.class)
    public void syncUser(UserInfo userInfo)
    {
        UserInfo u = userMapper.queryUser(userInfo.getOpenId());
        if (null == u)
        {
            userMapper.addUser(userInfo);
        }
        else
        {
            userMapper.updateUser(userInfo);
        }
    }
}
