package com.itcast.shiro.util;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

/**
 * 功能描述：MD5盐值加密工具类
 *
 * @author JIAQI
 * @date 2020/4/13 - 14:35
 */
public class SaltUtil {

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
        String oldPassword = new SimpleHash("MD5", "123456",
                ByteSource.Util.bytes("admin"), 2).toHex();
        System.out.println(SaltUtil.doSalt("123456", "b35df3"));
        System.out.println(oldPassword);
    }
}
