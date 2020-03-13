package springboot06mybatis.utils;

import java.io.UnsupportedEncodingException;

/**
 * ClassName:    String2utf_8
 * Package:    springboot06mybatis.utils
 * Description: 字符串编码转为utf-8
 * Datetime:    2020/3/10   16:20
 * Author:   hewson.chen@foxmail.com
 */
public class String2utf_8 {

    public static String setString2utf_8(String password) throws UnsupportedEncodingException {
        return new String(password.getBytes(),"UTF-8");
    }
}
