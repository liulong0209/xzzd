package com.wlt.xzzd.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.security.KeyStore;

import javax.net.ssl.SSLContext;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.wlt.xzzd.controller.WebChatPayController;
import com.wlt.xzzd.model.FreeOrder;
import com.wlt.xzzd.model.OrderInfo;
import com.wlt.xzzd.model.RefundOrder;
import com.wlt.xzzd.model.SignInfo;
import com.wlt.xzzd.service.WebChatPayService;
import com.wlt.xzzd.util.HttpRequest;
import com.wlt.xzzd.util.OrderHelp;
import com.wlt.xzzd.util.RandomStringGenerator;
import com.wlt.xzzd.util.Signature;
import com.wlt.xzzd.util.XmlUtil;

@Service
public class WebChatPayServiceImp implements WebChatPayService
{
    /**
     * 小程序的appid
     */
    @Value("${MiniProgram.appID}")
    private String APPID;

    /**
     * 小程序商户号
     */
    @Value("${MiniProgram.mchID}")
    private String MCHID;

    /**
     * 小程序商户号API密钥
     */
    @Value("${MiniProgram.mchapikey}")
    private String MCHAPIKEY;

    /**
     * 小程序商户证书
     */
    @Value("${MiniProgram.mch.cert}")
    private String MCHCERT;

    /**
     * 小程序商户证书格式
     */
    @Value("${MiniProgram.mch.cert.format}")
    private String CERTFORMAT;

    /**
     * 小程序支付body
     */
    @Value("${MiniProgram.pay.body}")
    private String body;

    /**
     * 小程序预下单url
     */
    @Value("${MiniProgram.pay.unifiedorder}")
    private String unifiedorderUrl;

    /**
     * 小程序退单url
     */
    @Value("${MiniProgram.pay.refund}")
    private String refundUrl;

    /**
     * 小程序支付结果通知url
     */
    @Value("${MiniProgram.pay.notifyUrl}")
    private String notifyUrl;

    /**
     * 小程序退单结果通知url
     */
    @Value("${MiniProgram.pay.refund.notifyUrl}")
    private String refundNotifyUrl;

    /**
     * 日志记录器
     */
    private Logger LOGGER = LoggerFactory.getLogger(WebChatPayController.class);

    public JSONObject preOrder(OrderInfo orderInfo)
    {
        JSONObject json = null;
        try
        {
            // 1. 下单获取prepay_id
            orderInfo.setAppid(APPID);
            orderInfo.setMch_id(MCHID);
            orderInfo.setNonce_str(RandomStringGenerator.getRandomStringByLength(32));
            orderInfo.setBody(body);
            String orderNo = OrderHelp.generateOrderNo();
            orderInfo.setOut_trade_no(orderNo);
            orderInfo.setNotify_url(notifyUrl);
            orderInfo.setTrade_type("JSAPI");
            orderInfo.setSign_type("MD5");
            // 生成签名
            String sign = Signature.getSign(orderInfo, MCHAPIKEY);
            orderInfo.setSign(sign);
            // 请求下单接口
            HttpClient httpClient = HttpClients.createDefault();
            String result = HttpRequest.sendPost(httpClient, unifiedorderUrl, orderInfo);

            LOGGER.info("WebChatPayServiceImp->preOrder->下单返回={}", result);

            JSONObject returnInfo = XmlUtil.documentToJSONObject(result);

            // 2. 获取prepay_id后二次签名
            SignInfo signInfo = new SignInfo();
            signInfo.setAppId(APPID);
            long time = System.currentTimeMillis() / 1000;
            signInfo.setTimeStamp(String.valueOf(time));
            signInfo.setNonceStr(RandomStringGenerator.getRandomStringByLength(32));
            signInfo.setRepay_id("prepay_id=" + returnInfo.getString("prepay_id"));
            signInfo.setSignType("MD5");
            // 生成签名
            String secondsign = Signature.getSign(signInfo, MCHAPIKEY);

            // 3、返回下单信息到前台
            json = new JSONObject();
            json.put("timeStamp", signInfo.getTimeStamp());
            json.put("nonceStr", signInfo.getNonceStr());
            json.put("package", signInfo.getRepay_id());
            json.put("signType", signInfo.getSignType());
            json.put("paySign", secondsign);
            json.put("orderNo", orderNo);
            LOGGER.info("-------再签名:" + json.toJSONString());
        }
        catch (Exception e)
        {
            LOGGER.error("预下单异常", e);
            return null;
        }
        return json;
    }

    public JSONObject refund(FreeOrder freeOrder, String refundDesc) throws Exception
    {

        // 组装请求退单参数
        RefundOrder refundOrder = new RefundOrder();
        refundOrder.setAppid(APPID);
        refundOrder.setMch_id(MCHID);
        refundOrder.setNonce_str(RandomStringGenerator.getRandomStringByLength(32));

        refundOrder.setOut_trade_no(freeOrder.getOrderNo());
        String refundOrderNo = OrderHelp.generateOrderNo();
        refundOrder.setOut_refund_no(refundOrderNo);
        refundOrder.setTotal_fee(freeOrder.getPayMoney().multiply(new BigDecimal(100)).intValue());
        refundOrder.setRefund_fee(freeOrder.getPayMoney().multiply(new BigDecimal(100)).intValue());
        refundOrder.setRefund_desc(refundDesc);

        refundOrder.setNotify_url(refundNotifyUrl);
        // 生成签名
        String sign = Signature.getSign(refundOrder, MCHAPIKEY);
        refundOrder.setSign(sign);

        KeyStore keyStore = KeyStore.getInstance(CERTFORMAT);
        FileInputStream instream = new FileInputStream(new File(MCHCERT));
        try
        {
            keyStore.load(instream, MCHID.toCharArray());
        }
        finally
        {
            instream.close();
        }

        // Trust own CA and all self-signed certs
        SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, MCHID.toCharArray()).build();
        // Allow TLSv1 protocol only
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, new String[] { "TLSv1" }, null,
            SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
        HttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();

        String result = HttpRequest.sendPost(httpclient, refundUrl, refundOrder);

        JSONObject returnInfo = XmlUtil.documentToJSONObject(result);
        returnInfo.put("refundOrderNo", refundOrderNo);
        return returnInfo;
    }
}
