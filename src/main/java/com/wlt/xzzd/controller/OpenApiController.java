package com.wlt.xzzd.controller;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.wlt.xzzd.exception.AesException;
import com.wlt.xzzd.model.DialPhoneRecord;
import com.wlt.xzzd.model.UserInfo;
import com.wlt.xzzd.service.DialPhoneRecordService;
import com.wlt.xzzd.service.UserService;
import com.wlt.xzzd.service.WebChatService;
import com.wlt.xzzd.util.AesCbcUtil;
import com.wlt.xzzd.util.SHA1;

/**
 * @ClassName: OpenApiController
 * @Description: 对外提供的api
 * @author liulong03@chinasoftinc.com
 *
 */
@Controller
@RequestMapping("/api")
public class OpenApiController
{
    /**
     * 日志记录器
     */
    private Logger LOGGER = LoggerFactory.getLogger(OpenApiController.class);

    /**
     * 微信服务接口注入
     */
    @Autowired
    private WebChatService WebChatService;

    /**
     * 用户接口注入
     */
    @Autowired
    private UserService userService;

    /**
     * 记录拨打电话注入
     */
    @Autowired
    private DialPhoneRecordService dialPhoneRecordService;

    /**
     *
     * OpenApiController.login
     *
     * @Description: 微信小程序登录
     *
     * @param encryptedData
     *            包括敏感数据在内的完整用户信息的加密数据
     * @param iv
     *            加密算法的初始向量
     * @param code
     *            用户登录凭证
     * @param rawData
     *            不包括敏感信息的原始数据字符串，用于计算签名。
     * @param signature
     *            使用 sha1( rawData + sessionkey ) 得到字符串，用于校验用户信息
     * @return Object
     * 
     * @exception Exception
     * 
     * @author liulong03@chinasoftinc.com
     * @throws AesException
     *
     * @date 2018年7月11日-下午3:08:00
     *
     */
    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Object login(String encryptedData, String iv, String code, String rawData, String signature)
        throws AesException
    {
        LOGGER.debug("OpenApiController->login->encryptedData:{}", encryptedData);
        LOGGER.debug("OpenApiController->login->iv:{}", iv);
        LOGGER.debug("OpenApiController->login->code:{}", code);
        LOGGER.debug("OpenApiController->login->rawData:{}", rawData);
        LOGGER.debug("OpenApiController->login->signature:{}", signature);

        JSONObject json = new JSONObject();
        if (StringUtils.isEmpty(code))
        {
            json.put("status", 0);
            json.put("msg", "code 不能为空");
            return json;
        }
        // 获取用户的openid和session_key 信息
        JSONObject openIdObj = WebChatService.getOpenId(code);

        String sessionKey = (String) openIdObj.get("session_key");
        String signature1;
        try
        {
            // 数据签名校验
            signature1 = SHA1.getSHA1(rawData + sessionKey);
            if (!signature1.equals(signature))
            {
                LOGGER.debug("req signature=" + signature);
                LOGGER.debug("local signature=" + signature1);
                LOGGER.debug("数据签名校验错误");
                throw new AesException(AesException.ValidateSignatureError);
            }
        }
        catch (AesException e)
        {
            LOGGER.debug("sha加密生成签名失败");
            throw new AesException(AesException.ComputeSignatureError);
        }

        // 解析加密的信息
        String decryptStr = AesCbcUtil.decrypt(encryptedData, sessionKey, iv);

        // 转成user对象
        UserInfo userInfo = JSONObject.parseObject(decryptStr, UserInfo.class);
        // 同步微信用户信息
        userService.syncUser(userInfo);

        return userInfo;
    }

    /**
     *
     * OpenApiController.parsePhoneNumber
     *
     * @Description: 解析用户电话号码
     *
     * @param encryptedData
     * @param iv
     * @param code
     * @return
     * @throws AesException
     *             Object
     * 
     * @exception Exception
     * 
     * @author liulong03@chinasoftinc.com
     *
     * @date 2018年7月12日-下午7:44:08
     *
     */
    @ResponseBody
    @RequestMapping(value = "/parsePhoneNumber", method = RequestMethod.POST)
    public Object parsePhoneNumber(String encryptedData, String iv, String code) throws AesException
    {
        LOGGER.debug("OpenApiController->parsePhoneNumber->encryptedData:{}", encryptedData);
        LOGGER.debug("OpenApiController->parsePhoneNumber->iv:{}", iv);
        LOGGER.debug("OpenApiController->parsePhoneNumber->code:{}", code);

        JSONObject json = new JSONObject();
        if (StringUtils.isEmpty(code))
        {
            json.put("status", 0);
            json.put("msg", "code 不能为空");
            return json;
        }

        // 获取用户的openid和session_key 信息
        JSONObject openIdObj = WebChatService.getOpenId(code);

        String sessionKey = (String) openIdObj.get("session_key");

        // 解析加密的信息
        String decryptStr = AesCbcUtil.decrypt(encryptedData, sessionKey, iv);
        // 将字符串转换成对象
        JSONObject phoneObj = JSONObject.parseObject(decryptStr);

        return phoneObj;
    }

    /**
     * 
     * OpenApiController.recordDailPhone
     *
     * @Description: 记录用拨打电话
     *
     * @param phoneNumber
     * @param shopId
     *            void
     * 
     * @exception Exception
     * 
     * @author liulong03@chinasoftinc.com
     *
     * @date 2018年7月12日-下午8:35:11
     *
     */
    @ResponseBody
    @RequestMapping(value = "/recordDailPhone", method = RequestMethod.POST)
    public DialPhoneRecord recordDailPhone(DialPhoneRecord dialPhoneRecord)
    {
        dialPhoneRecordService.recordDailPhone(dialPhoneRecord);

        return dialPhoneRecord;
    }
}
