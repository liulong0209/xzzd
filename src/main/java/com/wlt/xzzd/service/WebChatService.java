package com.wlt.xzzd.service;

import com.alibaba.fastjson.JSONObject;

/**
 * 微信相关接口
 * 
 * @ClassName: WebChatService
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author liulong03@chinasoftinc.com
 *
 */
public interface WebChatService
{
    /**
     * 获取AccessToken
     *
     * WebChatService.getAccessToken
     *
     * @Description: TODO(这里用一句话描述这个方法的作用)
     *
     * @param param
     * @return String
     * 
     * @exception Exception
     * 
     * @author liulong03@chinasoftinc.com
     *
     * @date 2018年7月10日-下午12:53:36
     *
     */
    String getAccessToken(String code);

    /**
     * 获取用户的openid
     *
     * WebChatService.getOpenId
     *
     * @Description: TODO(这里用一句话描述这个方法的作用)
     *
     * @param code
     * @return JSONObject
     * 
     * @exception Exception
     * 
     * @author liulong03@chinasoftinc.com
     *
     * @date 2018年7月11日-下午3:14:41
     *
     */
    JSONObject getOpenId(String code);
}
