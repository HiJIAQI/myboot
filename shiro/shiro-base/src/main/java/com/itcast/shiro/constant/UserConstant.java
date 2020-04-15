package com.itcast.shiro.constant;

/**
 * 功能描述：模拟用户
 *
 * @author JIAQI
 * @date 2020/4/13 - 14:06
 */
public enum UserConstant {

    // 明文密码:123456
    USER("admin", "a66abb5684c45962d887564f08346e8d");

    UserConstant(String userName, String passWord) {
        this.userName = userName;
        this.passWord = passWord;
    }

    private String userName;
    private String passWord;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }
}
