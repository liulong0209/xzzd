package com.wlt.xzzd.util;

import java.security.MessageDigest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wlt.xzzd.exception.AesException;

/**
 * @ClassName: SHA1
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author liulong03@chinasoftinc.com
 *
 */
public class SHA1
{

    private static Logger LOGGER = LoggerFactory.getLogger(SHA1.class);

    /**
     * 用SHA1算法生成安全签名
     * 
     * @param str
     * 
     * @return 安全签名
     * @throws AesException
     */
    public static String getSHA1(String str) throws AesException
    {
        try
        {
            // 指定sha1算法
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            digest.update(str.getBytes());
            // 获取字节数组
            byte messageDigest[] = digest.digest();
            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            // 字节数组转换为 十六进制 数
            for (int i = 0; i < messageDigest.length; i++)
            {
                String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
                if (shaHex.length() < 2)
                {
                    hexString.append(0);
                }
                hexString.append(shaHex);
            }
            return hexString.toString().toLowerCase();

        }
        catch (Exception e)
        {
            LOGGER.error("用SHA1算法生成安全签名,发生错误", e);
            throw new AesException(AesException.ComputeSignatureError);
        }
    }
}
