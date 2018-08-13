package com.wlt.xzzd.util;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.alibaba.fastjson.JSON;

/**
 * 返回结果类
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class RestResult extends HashMap<String, Object>
{
    /**
     * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
     */
    private static final long serialVersionUID = 9114756247707417722L;

    /**
     * 状态参数
     */
    private static final String HTTP_STATUS = "code";

    /**
     * 消息参数
     */
    private static final String HTTP_MSG = "msg";

    /**
     * 成功状态码
     */
    private static final String SUCCESS_STATUS = "00000";

    /**
     * 系统失败状态码
     */
    private static final String FAILURE_STATUS = "EC10001";

    /**
     * 状态码
     */
    private String code = SUCCESS_STATUS;

    /**
     * 结果消息
     */
    private String msg = "Success OK";

    /**
     * 构造
     */
    public RestResult()
    {
        put(HTTP_STATUS, this.code);
        put(HTTP_MSG, this.msg);
    }

    /**
     * 构造
     *
     * @param code
     *            String
     * @param msg
     *            String
     */
    public RestResult(String code, String msg)
    {
        this.code = code;
        this.msg = msg;
        put(HTTP_STATUS, this.code);
        put(HTTP_MSG, this.msg);
    }

    /**
     * 构造
     *
     * @param msg
     *            String
     */
    public RestResult(String msg)
    {
        this.msg = msg;
        put(HTTP_STATUS, this.code);
        put(HTTP_MSG, this.msg);
    }

    /**
     * 返回失败信息
     *
     * @param code
     *            String
     * @param msg
     *            String
     *
     * @return RestResult
     */
    public static RestResult error(String code, String msg)
    {
        return new RestResult(code, msg);
    }

    /**
     * 返回基础失败信息
     *
     * @return RestResult
     */
    public static RestResult error()
    {
        return error(FAILURE_STATUS, "Unknown system error");
    }

    /**
     * 返回失败信息
     *
     * @param msg
     *            String
     *
     * @return RestResult
     */
    public static RestResult error(String msg)
    {
        return error(FAILURE_STATUS, msg);
    }

    /**
     * 返回基础成功信息
     *
     * @return RestResult
     */
    public static RestResult ok()
    {
        return new RestResult();
    }

    /**
     * 返回基础成功信息
     *
     * @param msg
     *            String
     *
     * @return RestResult
     */
    public static RestResult ok(String msg)
    {
        return new RestResult(msg);
    }

    /**
     * 返回成功信息
     *
     * @param data
     *            Map
     *
     * @return RestResult
     */
    public static RestResult ok(Map<String, Object> data)
    {
        RestResult restResult = new RestResult();
        restResult.putAll(data);
        return restResult;
    }

    /**
     * 返回成功信息
     *
     * @param key
     *            String
     * @param value
     *            Object
     *
     * @return RestResult
     */
    public static RestResult ok(String key, Object value)
    {
        RestResult restResult = new RestResult();
        restResult.put(key, value);
        return restResult;
    }

    /**
     * 数据
     *
     * @param key
     * @param value
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    public RestResult data(String key, Object value)
    {
        if (!this.containsKey("data"))
        {
            this.put("data", new HashMap<String, Object>());
        }
        ((Map<String, Object>) this.get("data")).put(key, value);
        return this;
    }

    /**
     * 数据
     *
     * @param data
     *            Map<String,Object>
     *
     * @return RestResult
     */
    @SuppressWarnings("unchecked")
    public RestResult dataAll(Map<String, Object> data)
    {
        if (!this.containsKey("data"))
        {
            this.put("data", new HashMap<String, Object>());
        }
        ((Map<String, Object>) this.get("data")).putAll(data);
        return this;
    }

    /**
     * 设置对象
     *
     * @param o
     *            Object
     *
     * @return RestResult
     */
    public RestResult dataObject(Object o)
    {
        this.put("data", JSON.parseObject(JSON.toJSONString(o), Map.class));
        return this;
    }

    /**
     * 移除data数据
     *
     * @param key
     *            键
     *
     * @return com.icss.lighttower.microservice.common.rest.RestResult
     *
     * @date 15:07 2018/7/25
     * @author Administrator
     **/
    public RestResult dataRemove(String key)
    {
        if (this.containsKey("data"))
        {
            Map<String, Object> datas = ((Map<String, Object>) this.get("data"));
            if (datas.containsKey(key))
            {
                datas.remove(key);
            }
        }
        return this;
    }

}
