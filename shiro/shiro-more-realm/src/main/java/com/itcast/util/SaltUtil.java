package com.itcast.util;

import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Md5Hash;

/**
 * 功能描述：MD5盐值加密工具类
 *
 * @author JIAQI
 * @date 2020/4/13 - 14:35
 */
public class SaltUtil {

    /**
     * 生成随机盐
     */
    public static String randomSalt() {
        // 一个Byte占两个字节，此处生成的3字节，字符串长度为6
        SecureRandomNumberGenerator secureRandom = new SecureRandomNumberGenerator();
        String hex = secureRandom.nextBytes(3).toHex();
        return hex;
    }

    /**
     * 进行加盐操作
     *
     * @param password 密码
     * @param salt     盐值
     * @return 字符串
     */
    public static String doSalt(String password, String salt) {
        return String.valueOf(new Md5Hash(password, salt));
    }

    public static void main(String[] args) {
//        System.out.println(randomSalt());
//        String oldPassword = new SimpleHash("MD5", "123456",
//                ByteSource.Util.bytes("admin"), 2).toHex();
//        System.out.println(SaltUtil.doSalt("123456", "admin"));
//        System.out.println(oldPassword);
    }
}
