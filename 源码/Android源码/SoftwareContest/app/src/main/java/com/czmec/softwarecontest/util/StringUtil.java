package com.czmec.softwarecontest.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串操作类
 * Created by 78537 on 2016-03-05.
 */
public class StringUtil {
    /**
     * 判断是否是正确的ip地址
     * @param ipAddress ip地址
     * @return 是否是ip地址
     */
    public static boolean isIpAddress(String ipAddress){
        String ip = "((?:(?:25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))\\.){3}(?:25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d))))";
        Pattern pattern = Pattern.compile(ip);
        Matcher matcher = pattern.matcher(ipAddress);
        return matcher.matches();
    }


}

