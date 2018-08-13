package com.wlt.xzzd.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.wlt.xzzd.service.WebChatService;
import com.wlt.xzzd.util.HttpClientUtil;

/**
 * 微信接口实现
 * 
 * @ClassName: WebChatServiceImpl
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author liulong03@chinasoftinc.com
 *
 */
@Service
@Scope("prototype")
public class WebChatServiceImpl implements WebChatService
{

    /**
     * 小程序的appid
     */
    @Value("${MiniProgram.appID}")
    private String APPID;

    /**
     * 小程序的appsecret
     */
    @Value("${MiniProgram.appsecret}")
    private String APPSECRET;

    @Cacheable(value = "accessTokenCache")
    public String getAccessToken(String code)
    {
        String getOpenIdUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=wx94b85aa2fcba5ecd&secret=5f34633b4d9aee392de1f5ff995206b5&code="
            + code + "&grant_type=authorization_code";
        HttpClientUtil client = HttpClientUtil.getInstance();
        String result = client.call(getOpenIdUrl);
        return result;
    }

    public JSONObject getOpenId(String code)
    {
        // 根据code从微信服务器获取openid和session_key
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid=" + APPID + "&secret=" + APPSECRET
            + "&js_code=" + code + "&grant_type=authorization_code";
        HttpClientUtil client = HttpClientUtil.getInstance();
        String result = client.call(url);
        // 解析openid和session_key
        JSONObject oppidObj = JSONObject.parseObject(result);

        return oppidObj;
    }
}
