package com.wlt.xzzd.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * httpclient工具类
 * 
 * @author liulong
 *
 */
public class HttpClientUtil
{

    /**
     * log
     */
    private static final Log LOGGER = LogFactory.getLog(HttpClientUtil.class);

    /**
     * 请求参数
     */
    private List<NameValuePair> parameters = new ArrayList<NameValuePair>();
    /**
     * 使用post方式
     */
    private boolean usingPost = true;
    /**
     * 使用rest方式
     */
    private boolean byRest = false;
    /**
     * 响应
     */
    private String responseOutString;
    /**
     * 错误消息
     */
    private String errorMessage;

    public static HttpClientUtil getInstance()
    {
        return new HttpClientUtil();
    }

    /**
     * HTTP 错误代号
     */
    private static final Integer HTTP_ERRCOR_404 = 404;
    /**
     * HTTP 错误代号
     */
    private static final Integer HTTP_ERRCOR_200 = 200;

    /**
     * 设置请求参数
     *
     * HttpClientUtil.parameter
     *
     * @Description: 设置请求参数
     *
     * @param key
     *            key
     * @param value
     *            value
     * @return HttpClientUtil
     *
     */
    public HttpClientUtil parameter(String key, String value)
    {
        if (key == null)
        {
            return this;
        }
        this.parameters.add(new NameValuePair(key.trim(), value.trim()));
        LOGGER.debug(new StringBuilder().append("HttpClientUtil key=").append(key.trim()).append(",value=")
            .append(value.trim()).toString());
        return this;
    }

    public String getErrorMessage()
    {
        return this.errorMessage;
    }

    /**
     * 
     *
     * HttpClientUtil.api
     *
     * @Description: 获取HttpClien
     *
     * @param apiPath
     *            api路径
     * @return HttpClientUtil HttpClientUtil
     *
     */
    public HttpClientUtil api(String apiPath)
    {
        this.errorMessage = null;
        long startTime = System.currentTimeMillis();
        try
        {
            StringBuilder url = new StringBuilder();
            url.append(apiPath);
            HttpMethodBase httpMethod = null;
            if (this.byRest)
            {
                int i = 0;
                int len = this.parameters.size();
                while (i < len)
                {
                    url.append("/");
                    NameValuePair kv = this.parameters.get(i);
                    try
                    {
                        if (null == kv.getValue())
                        {
                            url.append(URLEncoder.encode("", "utf-8"));
                        }
                        else
                        {
                            url.append(URLEncoder.encode(kv.getValue(), "utf-8"));
                        }

                    }
                    catch (UnsupportedEncodingException e)
                    {
                        url.append(kv.getValue());
                    }
                    i++;
                }
                httpMethod = new PostMethod(url.toString());
            }
            else if (this.usingPost)
            {
                httpMethod = new PostMethod(url.toString());
                httpMethod.getParams().setParameter("http.protocol.content-charset", "UTF-8");
                ((PostMethod) httpMethod).setRequestBody(this.parameters.toArray(new NameValuePair[0]));
            }
            else
            {
                int i = 0;
                int len = this.parameters.size();
                while (i < len)
                {
                    if (0 == i)
                    {
                        url.append("?");
                    }
                    else
                    {
                        url.append("&");
                    }

                    NameValuePair kv = this.parameters.get(i);
                    url.append(kv.getName()).append("=");
                    try
                    {
                        if (null == kv.getValue())
                        {
                            url.append(URLEncoder.encode("", "utf-8"));
                        }
                        else
                        {
                            url.append(URLEncoder.encode(kv.getValue(), "utf-8"));
                        }
                    }
                    catch (UnsupportedEncodingException e)
                    {
                        url.append(kv.getValue());
                    }
                    i++;
                }
                String apiURL = url.toString();
                LOGGER.debug(new StringBuilder().append("api url:").append(apiURL).toString());
                httpMethod = new GetMethod(apiURL);
            }

            HttpClient httpClient = new HttpClient();
            int statusCode = httpClient.executeMethod(httpMethod);
            String out = httpMethod.getResponseBodyAsString();
            LOGGER.trace(new StringBuilder().append("api out:").append(out).toString());
            if (HTTP_ERRCOR_404 == statusCode)
            {
                throw new Exception(new StringBuilder().append("不能处理的请求:").append(url.toString()).toString());
            }
            if (HTTP_ERRCOR_200 != statusCode)
            {
                throw new Exception(out);
            }
            responseOutString = out;
        }
        catch (Exception e)
        {
            this.errorMessage = e.getMessage();
            LOGGER.error("api error", e);
        }
        finally
        {
            this.parameters.clear();
            LOGGER.debug(new StringBuilder().append("API('").append(apiPath).append("')调用耗时:")
                .append(System.currentTimeMillis() - startTime).toString());
        }
        return this;
    }

    public String call(String apiPath)
    {
        api(apiPath);
        return toString();
    }

    @Override
    public String toString()
    {
        return responseOutString;
    }
}
