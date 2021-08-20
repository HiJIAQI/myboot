package com.itcast.pay.utils.pay.wxpay;

/**
 * 常量
 */
public class WXPayConstants {

    public enum SignType {
        MD5, HMACSHA256
    }

    // 交易类型
    public static final String WX_TRADE_TYPE = "JSAPI";
    public static final String PC_TRADE_TYPE = "NATIVE";

    // 签名方式
    public static final String SIGNTYPE = "MD5";

    public static final String FAIL = "FAIL";
    public static final String SUCCESS = "SUCCESS";
    public static final String HMACSHA256 = "HMAC-SHA256";
    public static final String MD5 = "MD5";
    public static final String ALGORITHM = "AES";
    public static final String ALGORITHM_MODE_PADDING = "AES/ECB/PKCS5Padding";

    public static final String FIELD_SIGN = "sign";
    public static final String FIELD_SIGN_TYPE = "sign_type";

    public static final String DOMAIN_API = "https://api.mch.weixin.qq.com";
    public static final String DOMAIN_API2 = "https://api2.mch.weixin.qq.com";
    public static final String DOMAIN_APIHK = "https://apihk.mch.weixin.qq.com";
    public static final String DOMAIN_APIUS = "https://apius.mch.weixin.qq.com";

    public static final String MICROPAY_URL_SUFFIX = "/pay/micropay";

    /*统一下单地址*/
    public static final String UNIFIEDORDER_URL_SUFFIX = "/pay/unifiedorder";
    /*查看订单*/
    public static final String ORDERQUERY_URL_SUFFIX = "/pay/orderquery";
    public static final String REVERSE_URL_SUFFIX = "/secapi/pay/reverse";
    public static final String CLOSEORDER_URL_SUFFIX = "/pay/closeorder";
    /*申请退款地址*/
    public static final String REFUND_URL_SUFFIX = "/secapi/pay/refund";
    /*查询退款*/
    public static final String REFUNDQUERY_URL_SUFFIX = "/pay/refundquery";
    /*企业付款到零钱*/
    public static final String TRANSFERS_URL_SUFFIX = "/mmpaymkttransfers/promotion/transfers";
    /*下载对账单*/
    public static final String DOWNLOADBILL_URL_SUFFIX = "/pay/downloadbill";
    public static final String REPORT_URL_SUFFIX = "/payitil/report";
    public static final String SHORTURL_URL_SUFFIX = "/tools/shorturl";
    public static final String AUTHCODETOOPENID_URL_SUFFIX = "/tools/authcodetoopenid";

}

