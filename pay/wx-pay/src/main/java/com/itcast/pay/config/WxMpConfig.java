package com.itcast.pay.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * 功能描述：微信公众号相关配置信息
 *
 * @author JIAQI
 * @date 2019/7/15 - 11:13
 */
@Configuration
public class WxMpConfig {

    // 微信公众号 id
    public static String APPID;

    // 微信公众号 appsecret
    public static String APPSECRET;

    // 微信支付商户号
    public static String MCHID;

    // 商户密钥
    public static String MCHKEY;

    // 商户证书路径
    public static String MCHCERTPATH;

    public static String TOKEN;

    public static String REDIRECTURL;

    @Value("${wx.mp.appId}")
    public void setAppID(String appId) {
        WxMpConfig.APPID = appId;
    }

    @Value("${wx.mp.appSecret}")
    public void setAppSecret(String appSecret) {
        WxMpConfig.APPSECRET = appSecret;
    }

    @Value("${wx.mp.mchId}")
    public void setMchId(String mchId) {
        WxMpConfig.MCHID = mchId;
    }

    @Value("${wx.mp.mchKey}")
    public void setMchKey(String mchKey) {
        WxMpConfig.MCHKEY = mchKey;
    }

    @Value("${wx.mp.mchCertPath}")
    public void setKeyPath(String mchCertPath) {
        WxMpConfig.MCHCERTPATH = mchCertPath;
    }

    @Value("${wx.mp.token}")
    public void setToken(String token) {
        WxMpConfig.TOKEN = token;
    }

    @Value("${wx.mp.redirectUrl}")
    public void setRedirectUrl(String redirectUrl) {
        WxMpConfig.REDIRECTURL = redirectUrl;
    }

    public static class Notify {
        // 微信端支付异步通知地址
        public static String SERVICE_ORDER = REDIRECTURL + "wechat/callback/payNotify/service-order";
        public static String MALL_ORDER = REDIRECTURL + "wechat/callback/payNotify/mall-order";
        public static String DEPOSIT = REDIRECTURL + "wechat/callback/payNotify/deposit";
        // 退款异步回调地址
        public static String ORDER_REFUND = REDIRECTURL + "wechat/callback/refundNotify/order";
        public static String DEPOSIT_REFUND = REDIRECTURL + "wechat/callback/refundNotify/deposit";
    }

}