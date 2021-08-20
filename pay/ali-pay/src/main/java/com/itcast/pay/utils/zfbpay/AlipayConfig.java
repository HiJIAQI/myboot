package com.itcast.pay.utils.zfbpay;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import lombok.extern.slf4j.Slf4j;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *提示：如何获取安全校验码和合作身份者ID
 *1.用您的签约支付宝账号登录支付宝网站(www.alipay.com)
 *2.点击“商家服务”(https://b.alipay.com/order/myOrder.htm)
 *3.点击“查询合作者身份(PID)”、“查询安全校验码(Key)”
 *安全校验码查看时，输入支付密码后，页面呈灰色的现象，怎么办？
 *解决方法：
 *1、检查浏览器配置，不让浏览器做弹框屏蔽设置
 *2、更换浏览器或电脑，重新登录查询。
 */
@Slf4j
public class AlipayConfig {

    //APP支付宝支付业务：app_id
    public static String app_id = "2016101700706083";
    // 商户的私钥,使用支付宝自带的openssl工具生成。
    public static String private_key = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDbVJvag9uTrp8oSfCRdMqf1wZlwTx2f3xWdpJi/yn0jfr9NpiI9iDINVlwL591Zp2YX7+/g4hcP10Va7BeM2opSQ0n5E2eV6KQuF9KKmLPUHJVdNXl9KDg7nMcunSAjbdEJ5PMM3DfbzXUz2JPvppfl3VaifvsihawhPtMl/yJ+dQ/53N8oUbSpSQkWsNZUiU7EVcgNKPUtgeS3ySDFEWyCYlq6nfJLpymbvIdYRHcctk7NXqKKg3d9BB0xRcGwndjG3ayQ9tmes1Gz53QsuqJW1uZCKCfWc43M3/p/AtiAkXZQEXoVeG2O+KfUyLHmfAFNr9QjxGko0zq2eWUr+FtAgMBAAECggEASk5Y2ugPLPJdE24xZWYW+54CmdiknlTEkBptr8JYuXScksq7VLNmGc4RNfO8Zy7Tt3rA4KY0PWRuKcKpJtDuZ3xLS7eKIFn6RmNgTSUdArVzdsY1bUm6c1n1f8cfjynz69Aov2ayF9B6gVWdLf0aGcabSIg05EdINpdm9+UJ9sgfSuXe4g4VLVOzbPk4Baeh88tpCSF7Qgv6FxAVlZCS+ZrYCEIXqFfgBEnxHhUrtID3l9IMFmAtIDANPCHFDdIVpiu/mYHgB5DkdXGobDvj88Sx1aL7+VypN3oXW9z/j8xLCKQb9TpAtfFc4rHYA9zqrnx98wLpV9Tev6GXhg1xNQKBgQD34As1RAuahhSKaSTuwAnsQBge3xhnp9/T0/1p0vT/V0P8h7pLjSRLm57nbSn+E6AszyAt5SnXiJw58i8hMZzS3Sf/Og3k60ijXqTOKQ1xOH/TtNNgHIK+Gmde1CqQW45yXFWJNz7qAn7hbCoiikxjPqUFsMl0/QArg3ZpYilkcwKBgQDihQroSfvZV4r0JD2rv+B976uCemKe/hGFs0HtigluStbfXygx7OGCAhW/ThpwdGGWICyLfVK5+nktgGsINF8aRqKn3pR3wwFqKNlaehxkUt4qC4/Y9NdVPp9t7tq5Az+whK++Kejt2m6aVCl9EPuE9xjOJEOzG02vyxKljzoKnwKBgQDRyNJzkFVpwFSeGALj9a/wPQatCg+zmsa6ls4AxEQJ3ydxEMZArwGq49iG8UL7AE0t31bSwQK6oanm+OueYkjhxb7WKaIcvmzJp44/1UrFKhY1bPMAguC8V8ho35PufS5/51mVOiaLEvWjf1Hhzb58uEYeHNNDqeR871RGOIfjtQKBgCBlgLraIe/iqlfiewN+TMAWMARYNpSgp6bkjVBIxrKEd5i4xknlpBbw72VN6VAeBPrwnQ5SHSvGG+CbFjO1McjTQ14Y4kkClcg2EWf0QO1EQMvonWkdE1L4wtp5J8UAJvK691uOKOcjr1/ZG5nsfNx2bpDE9P2/sOuscn1FL7rnAoGADUE4nkY1b6NoRzh9soIctHaGB3SbLAYE7jybBagv8Vq4AoRfuHdkxiA9ZYRes6OyRmcgjy1IMFlg/dGqRQLRj5uhVyHguZ7kNfZ5LpiHrxsvUtsI+lfc9ZAlG+KX9qCqiQs7U/H1y5WjyN7oxPrlPOM3zf/UGoq2x1f1o4ptcOA=";
    // 应用的公钥，无需修改该值
    public static String public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA21Sb2oPbk66fKEnwkXTKn9cGZcE8dn98VnaSYv8p9I36/TaYiPYgyDVZcC+fdWadmF+/v4OIXD9dFWuwXjNqKUkNJ+RNnleikLhfSipiz1ByVXTV5fSg4O5zHLp0gI23RCeTzDNw32811M9iT76aX5d1Won77IoWsIT7TJf8ifnUP+dzfKFG0qUkJFrDWVIlOxFXIDSj1LYHkt8kgxRFsgmJaup3yS6cpm7yHWER3HLZOzV6iioN3fQQdMUXBsJ3Yxt2skPbZnrNRs+d0LLqiVtbmQign1nONzN/6fwLYgJF2UBF6FXhtjvin1Mix5nwBTa/UI8RpKNM6tnllK/hbQIDAQAB";
    // 支付宝的公钥，去open.alipay.com对应应用下查看。
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAhJF+MX4W2dXIniSJh6PkregouUK54cnF5Fgt37Xpexylg7muDywUAYF0YQtZVESBuVTKwonRlaLN07qHzz8iSO1FwAHCqYRtxMGDDf+m+fPCdv7ju6UK+1/WN2SFBV3J6oVV21rTi8KRdaeOR1wVV/aIa2MNETJN2UN3M4YjBqhqpZCSCMFxIrl7LtwZZYwldDWdwds/yK1/mpd7qvLD5IR2zai2aPqyevqTHm8GzGNhKk9iq40wZz6mwVvOk7/vaKPCI0Iwf0Kg9JALWmHW3LCQGW4kst11m/vE8UaCwHBkH5vG6J55ZYaYMCi/6a0ThyO1a/KmWfPC9OCbQXiNSwIDAQAB";
    // 字符编码格式 目前支持 gbk 或 utf-8
    public static String input_charset = "UTF-8";

    // 支付宝网关
    public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";
    // 服务器异步通知页面路径 ，设置为自己网站地址，由于只是本地调试 localhost就可以
    public static String notify_url = "http://www.cqkj.nat300.top/alipay/notify_url";
    // 页面跳转同步通知页面路径
    public static String return_url = "http://www.cqkj.nat300.top/alipay/success";

    private AlipayClient alipayClient;

    public AlipayClient getAlipayClient() {
        return alipayClient;
    }

    public void setAlipayClient(AlipayClient alipayClient) {
        this.alipayClient = alipayClient;
    }

    public AlipayConfig() {
        this.alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.private_key, "json", AlipayConfig.input_charset, AlipayConfig.alipay_public_key, "RSA2");
    }

}
