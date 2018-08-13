package com.wlt.xzzd.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * 订单帮助类
 * 
 * @ClassName: OrderHelp
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author liulong03@chinasoftinc.com
 *
 */
public class OrderHelp
{

    /**
     * 生成订单编号 （当前系统时间加随机数）
     *
     * OrderHelp.generateOrderNo
     *
     * @Description: TODO(这里用一句话描述这个方法的作用)
     *
     * @return String
     * 
     * @exception Exception
     * 
     * @author liulong03@chinasoftinc.com
     *
     * @date 2018年7月31日-下午4:53:34
     *
     */
    public static String generateOrderNo()
    {
        StringBuilder orderNo = new StringBuilder();
        SimpleDateFormat sd = new SimpleDateFormat("yyyyMMddHHmmss");
        orderNo.append(sd.format(new Date()));
        String base = "0123456789";
        Random random = new Random();
        for (int i = 0; i < 6; i++)
        {
            int number = random.nextInt(base.length());
            orderNo.append(base.charAt(number));
        }
        return orderNo.toString();
    }
}
