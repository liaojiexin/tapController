package com.vcmy.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @ClassName: TimeUtils
 * @Description: TODO
 * @version: 1.0
 * @author: liaojiexin
 * @date: 2020/12/16 14:46
 */
public class TimeUtils {

    public static void main(String[] args) {
        String res = "2018-12-18 10:04:59";/** 时间 */
        long s = 1545098699;/** 时间戳 */
        System.out.println("时间戳转换为时间:"+stampToDate(s));/** 时间戳转换为时间 */
        System.out.println("时间转换为时间戳:"+dateToStamp(res));/** 时间转换为时间戳 */
    }

    /*
     * 将时间转换为时间戳
     */
    public static String dateToStamp(String s){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String res = "";
        if (!"".equals(s)) {
            try {
                res = String.valueOf(sdf.parse(s).getTime()/1000);
            } catch (Exception e) {
                System.out.println("传入了null值");
            }
        }else {
            long time = System.currentTimeMillis();
            res = String.valueOf(time/1000);
        }

        return res;
    }


    /*
     * 将时间戳转换为时间类型
     */
    public static Date stampToDate(Long time){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String times = format.format(new Date(time * 1000L));
//	    System.out.println("日期格式---->" + times);

        return new Date(time * 1000L);
    }
}