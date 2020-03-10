package springboot06mybatis.utils;

import org.springframework.util.DigestUtils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * ClassName:    MD5Utils
 * Package:    springboot06mybatis.utils
 * Description:
 * Datetime:    2020/3/10   15:29
 * Author:   hewson.chen@foxmail.com
 */
public class MD5Utils {
    public static String getMD5Code(String password) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        // 确定计算方法
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        Base64.Encoder base64Encoder = Base64.getEncoder();
        // 加密字符串
        return base64Encoder.encodeToString(md5.digest(password.getBytes("utf-8")));
    }
}
