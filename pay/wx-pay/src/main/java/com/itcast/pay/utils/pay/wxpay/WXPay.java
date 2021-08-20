package com.itcast.pay.utils.pay.wxpay;

import cn.util.Result;
import cn.util.enums.ResultEnum;
import cn.util.exception.OrderException;
import cn.util.system.SystemUtil;
import com.itcast.pay.config.WxMpConfig;
import com.itcast.pay.utils.Httpclient;
import com.itcast.pay.utils.pay.MoneyUtils;
import com.itcast.pay.utils.pay.wxpay.WXPayConstants.SignType;

import com.itcast.pay.vo.OrderPayReqVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 功能描述：支付API
 *
 * @author JIAQI
 * @date 2019/8/1 - 09:37
 */
@Slf4j
public class WXPay {

    /**
     * 微信端统一下单
     *
     * @param request HttpServletRequest
     * @param appId   公众号Id
     * @param openId  进行支付用户的openId
     */
    public static Result unifiedOrderNew(HttpServletRequest request, String appId, String openId, OrderPayReqVO wxPayReqVO) {

        String URL = WXPayConstants.DOMAIN_API + WXPayConstants.UNIFIEDORDER_URL_SUFFIX;

        try {
            String ipAddress = SystemUtil.getIpAddress(request);
            //拼接统一下单地址参数
            Map<String, String> paraMap = new HashMap<String, String>();
            // 公众APPid
            paraMap.put("appid", appId);
            // 商户号
            paraMap.put("mch_id", WxMpConfig.MCHID);
            // 随机字符串
            paraMap.put("nonce_str", WXPayUtil.generateNonceStr());
            // 商品描述
            paraMap.put("body", wxPayReqVO.getOrgName() + "-" + wxPayReqVO.getProductName());
            // 附加数据
            paraMap.put("attach", wxPayReqVO.getOrderId().toString());
            // 商户订单号
            paraMap.put("out_trade_no", wxPayReqVO.getOrderNumber());
            // 标价金额(单位分)
            paraMap.put("total_fee", MoneyUtils.changeY2F(wxPayReqVO.getActualPaymentAmount().toString()));
            // 终端IP
            paraMap.put("spbill_create_ip", ipAddress);
            // 异步通知回调地址
            paraMap.put("notify_url", wxPayReqVO.getNotifyUrl());
            // 交易类型
            paraMap.put("trade_type", WXPayConstants.WX_TRADE_TYPE);
            // openid
            paraMap.put("openid", openId);
            // 签名需要其它数据信息，最后设置
            String sign = WXPayUtil.generateSignature(paraMap, WxMpConfig.MCHKEY);
            paraMap.put("sign", sign); // 签名
            String xml = WXPayUtil.mapToXml(paraMap); //将所有参数(map)转xml格式

            //请求"统一下单接口"返回预支付id:prepay_id
            String xmlStr = Httpclient.post(URL, xml);

            //以下内容是返回前端页面的json数据
            Map<String, String> map = null;
            //前台需要接收的值
            Map<String, String> payMap = new HashMap<String, String>();
            if (xmlStr.contains("SUCCESS")) {
                map = WXPayUtil.xmlToMap(xmlStr);
                //预支付id
                String prepay_id = (String) map.get("prepay_id");
                // 公众APPid
                payMap.put("appId", appId);
                // 时间戳
                payMap.put("timeStamp", WXPayUtil.getCurrentTimestamp() + "");
                // 随机字符串
                payMap.put("nonceStr", WXPayUtil.generateNonceStr());
                // 订单详情扩展字符串
                payMap.put("package", "prepay_id=" + prepay_id);
                // 签名方式
                payMap.put("signType", WXPayConstants.SIGNTYPE);
                // 换取签名
                String paySign = WXPayUtil.generateSignature(payMap, WxMpConfig.MCHKEY);
                // 签名
                payMap.put("paySign", paySign);
                log.info("【微信端微信支付】统一下单接口调用成功,result:{}", map);
                return Result.success(null, payMap);
            }
            log.error("【微信端微信支付】统一下单接口调用失败,result:{}", xmlStr);
        } catch (Exception e) {
            e.printStackTrace();
            throw new OrderException(ResultEnum.FAILURE);
        }
        return Result.failure(ResultEnum.FAILURE);
    }

    /**
     * 微信端统一下单
     *
     * @param request    HttpServletRequest
     * @param appId      公众号Id
     * @param openId     进行支付用户的openId
     * @param productDes 商品描述
     * @param totalFee   支付金额
     * @param outTradeNo 商户订单号
     * @param attach     附加数据
     * @param notify_url 支付成功后的回调地址
     * @return
     */
    public static Map<String, String> unifiedOrder(HttpServletRequest request, String appId, String openId, String productDes, String totalFee, String outTradeNo, String attach, String notify_url) {
        try {
            final String URL = WXPayConstants.DOMAIN_API + WXPayConstants.UNIFIEDORDER_URL_SUFFIX;
            String ipAddress = SystemUtil.getIpAddress(request);
            //拼接统一下单地址参数
            Map<String, String> paraMap = new HashMap<String, String>();
            // 公众APPid
            paraMap.put("appid", appId);
            // 商户号
            paraMap.put("mch_id", WxMpConfig.MCHID);
            // 随机字符串
            paraMap.put("nonce_str", WXPayUtil.generateNonceStr());
            // 商品描述
            paraMap.put("body", productDes);
            // 附加数据
            if (StringUtils.isNotBlank(attach)) {
                paraMap.put("attach", attach);
            }
            // 商户订单号
            paraMap.put("out_trade_no", outTradeNo);
            // 标价金额(单位分)
            paraMap.put("total_fee", totalFee);
            // 终端IP
            paraMap.put("spbill_create_ip", ipAddress);
            // 异步通知回调地址
            paraMap.put("notify_url", notify_url);
            // 交易类型
            paraMap.put("trade_type", WXPayConstants.WX_TRADE_TYPE);
            // openid
            paraMap.put("openid", openId);
            // 签名需要其它数据信息，最后设置
            String sign = WXPayUtil.generateSignature(paraMap, WxMpConfig.MCHKEY);
            paraMap.put("sign", sign); // 签名
            String xml = WXPayUtil.mapToXml(paraMap);//将所有参数(map)转xml格式
            //发送post请求"统一下单接口"返回预支付id:prepay_id
            String xmlStr = Httpclient.post(URL, xml);
            //以下内容是返回前端页面的json数据
            //预支付id
            String prepay_id = "";
            Map<String, String> map = null;
            //**** 前台需要接收的值
            Map<String, String> payMap = new HashMap<String, String>();

            if (xmlStr.indexOf("SUCCESS") != -1) {

                map = WXPayUtil.xmlToMap(xmlStr);
                prepay_id = (String) map.get("prepay_id");
                // 公众APPid
                payMap.put("appId", appId);
                // 时间戳
                payMap.put("timeStamp", WXPayUtil.getCurrentTimestamp() + "");
                // 随机字符串
                payMap.put("nonceStr", WXPayUtil.generateNonceStr());
                // 订单详情扩展字符串
                payMap.put("package", "prepay_id=" + prepay_id);
                // 签名方式
                payMap.put("signType", WXPayConstants.SIGNTYPE);
                // 换取签名
                String paySign = WXPayUtil.generateSignature(payMap, WxMpConfig.MCHKEY);
                // 签名
                payMap.put("paySign", paySign);
                log.info("【微信端微信支付】统一下单接口调用成功,result:{}", map);
                return payMap;
            } else {
                log.error("【微信端微信支付】统一下单接口调用失败,result:{}", xmlStr);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * PC端统一下单：扫码支付
     *
     * @param request    HttpServletRequest
     * @param appId      公众号Id
     * @param body       商品描述
     * @param outTradeNo 订单号
     * @param totalFee   支付金额
     * @param notifyUrl  异步回调地址
     * @return url
     */
    public static Result pcUnifiedOrder(HttpServletRequest request, String appId, String body, String outTradeNo, String totalFee, String notifyUrl) {
        final String UNIFIED_ORDER_URL = WXPayConstants.DOMAIN_API + WXPayConstants.UNIFIEDORDER_URL_SUFFIX;
        String code_url = null;
        try {
            Map<String, String> reqData = new HashMap<String, String>();
            //公众账号ID
            reqData.put("appid", appId);
            //商户号
            reqData.put("mch_id", WxMpConfig.MCHID);
            //随机字符串
            reqData.put("nonce_str", WXPayUtil.generateNonceStr());
            //商品描述
            reqData.put("body", body);
            //订单号
            reqData.put("out_trade_no", outTradeNo);
            //标价金额 / 分
            reqData.put("total_fee", totalFee);
            //终端IP
            reqData.put("spbill_create_ip", SystemUtil.getIpAddress(request));
//            //交易起始时间
//            final String dateStart = DateUtil.getDate(new Date(), DateStyle.YYYYMMDDHHMMSS);
//            reqData.put("time_start", dateStart);
//            System.out.println(dateStart);
//            //交易结束时间
//            reqData.put("time_expire", DateUtil.addMinute(dateStart, 2));
            //通知地址
            reqData.put("notify_url", notifyUrl);
            //交易类型
            reqData.put("trade_type", WXPayConstants.PC_TRADE_TYPE);
            //签名
            String sign = WXPayUtil.generateSignature(reqData, WxMpConfig.MCHKEY);
            reqData.put("sign", sign);
            //XML转义
            String xml = WXPayUtil.mapToXml(reqData);
            String xmlStr = Httpclient.post(UNIFIED_ORDER_URL, xml);
            final Map<String, String> map = WXPayUtil.xmlToMap(xmlStr);
            if ("SUCCESS".equals(map.get("result_code")) && "OK".equals(map.get("return_msg"))) {
                log.info("【PC端微信支付】统一下单接口调用成功,result:{}", map);
                return Result.success(ResultEnum.SUCCESS, map.get("code_url"));
            } else {
                log.error("【PC端微信支付】统一下单接口调用失败,result:{}", map);
                return Result.failure(ResultEnum.FAILURE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.failure(ResultEnum.FAILURE);
    }

    /**
     * 申请退款
     *
     * @param outTradeNo  商户订单id
     * @param totalFee    已支付金额
     * @param outRefundNo 商户退款单号
     * @param refundFee   所需退款金额
     * @param refundDesc  退款原因
     * @param notifyUrl   退款异步通知回调地址
     */
    public static Map<String, String> wxPayRefund(String outTradeNo, String totalFee, String outRefundNo, String refundFee, String refundDesc, String notifyUrl) {
        String URL = WXPayConstants.DOMAIN_API + WXPayConstants.REFUND_URL_SUFFIX;
        try {
            Map<String, String> params = new HashMap<String, String>();
            // 公众账号ID
            params.put("appid", WxMpConfig.APPID);
            // 商户号
            params.put("mch_id", WxMpConfig.MCHID);
            // 随机字符串
            params.put("nonce_str", WXPayUtil.generateNonceStr());
            // 商户退款单号(商户系统内部的退款单号)
            params.put("out_refund_no", outRefundNo);
            // 商户订单号
            params.put("out_trade_no", outTradeNo);
            // 已支付金额(单位分)
            params.put("total_fee", totalFee);
            // 所需退款金额(单位分)
            params.put("refund_fee", refundFee);
            // 退款原因
            params.put("refund_desc", refundDesc);
            // 退款通知地址
            if (StringUtils.isNotBlank(notifyUrl)) {
                params.put("notify_url", notifyUrl);
            }
            // 签名
            String sign = WXPayUtil.generateSignature(params, WxMpConfig.MCHKEY);
            params.put("sign", sign);
            String requestParams = WXPayUtil.mapToXml(params);
            // 进行退款操作->需要双向证书认证
            String result = Httpclient.postToCertificateVerification(URL, requestParams);
            Map<String, String> refundResult = WXPayUtil.xmlToMap(result);
            log.info("【微信支付】退款申请,result:{}", refundResult);
            return refundResult;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 企业付款到零钱
     *
     * @param partnerTradeNo 商户订单号(只能是字母或者数字，不能包含有其它字符)
     * @param openId         商户appid下，某用户的openid
     * @param amount         企业付款金额，单位为分
     * @param desc           企业付款备注
     */
    public static Map<String, String> transfersToDib(HttpServletRequest request, String partnerTradeNo, String openId, String amount, String desc) {
        String URL = WXPayConstants.DOMAIN_API + WXPayConstants.TRANSFERS_URL_SUFFIX;
        try {
            Map<String, String> params = new HashMap<String, String>();
            // 商户账号appid
            params.put("mch_appid", WxMpConfig.APPID);
            // 商户号
            params.put("mchid", WxMpConfig.MCHID);
            // 微信支付分配的终端设备号
            // params.put("device_info", null);
            // 随机字符串
            params.put("nonce_str", WXPayUtil.generateNonceStr());
            // 商户订单号
            params.put("partner_trade_no", partnerTradeNo);
            // 商户appid下，某用户的openid
            params.put("openid", openId);
            // 校验用户姓名选项 NO_CHECK：不校验真实姓名 FORCE_CHECK：强校验真实姓名
            params.put("check_name", "NO_CHECK");
            // 收款用户姓名
            // params.put("re_user_name", null);
            // 企业付款金额，单位为分
            params.put("amount", amount);
            // 企业付款备注
            params.put("desc", desc);
            // Ip地址
            params.put("spbill_create_ip", SystemUtil.getIpAddress(request));
            // 签名
            String sign = WXPayUtil.generateSignature(params, WxMpConfig.MCHKEY);
            params.put("sign", sign);
            String xml = WXPayUtil.mapToXml(params);
            // 进行付款到零钱操作->需双向证书认证
            String responseResult = Httpclient.postToCertificateVerification(URL, xml);
            Map<String, String> transfersResult = WXPayUtil.xmlToMap(responseResult);
            log.info("【微信支付】企业付款到零钱,result:{}", transfersResult);
            return transfersResult;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 订单查询
     *
     * @param appId      所属公众号id
     * @param outTradeNo 商户订单id
     */
    public static Map<String, String> wxPayOrderQuery(String appId, String outTradeNo) {
        try {
            final String URL = WXPayConstants.DOMAIN_API + WXPayConstants.ORDERQUERY_URL_SUFFIX;
            Map<String, String> map = new HashMap<String, String>();
            map.put("appid", appId);
            map.put("mch_id", WxMpConfig.MCHID);
            map.put("nonce_str", WXPayUtil.generateNonceStr());
            map.put("out_trade_no", outTradeNo);
            String sign = WXPayUtil.generateSignature(map, WxMpConfig.MCHKEY);
            map.put("sign", sign);
            String xml = WXPayUtil.mapToXml(map);
            String result = Httpclient.post(URL, xml);
            Map<String, String> queryResult = WXPayUtil.xmlToMap(result);
            log.info("【微信支付】支付结果,result:{}", queryResult.get("trade_state_desc"));
            return queryResult;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 退款查询
     *
     * @param appId      微信公众号id
     * @param outTradeNo 商户订单id
     */
    public static Map<String, String> wxPayRefundQuery(String appId, String outTradeNo) {
        try {
            final String URL = WXPayConstants.DOMAIN_API + WXPayConstants.REFUNDQUERY_URL_SUFFIX;
            Map<String, String> map = new HashMap<String, String>();
            map.put("appid", appId);
            map.put("mch_id", WxMpConfig.MCHID);
            map.put("nonce_str", WXPayUtil.generateNonceStr());
            map.put("out_trade_no", outTradeNo);
            String sign = WXPayUtil.generateSignature(map, WxMpConfig.MCHKEY);
            map.put("sign", sign);
            String xml = WXPayUtil.mapToXml(map);
            String result = Httpclient.post(URL, xml);
            Map<String, String> refundresult = WXPayUtil.xmlToMap(result);
            log.info("【微信支付】退款查询结果,result:{}", refundresult);
            String state = "FAIL";
            if (Objects.equals(refundresult.get("result_code"), "SUCCESS")) {
                state = refundresult.get("refund_status_0");
                log.info("【微信支付】退款查询结果:", state);
            }
            return refundresult;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 下载对账单
     *
     * @param appId    公众号id
     * @param billDate 对账单日期
     */
    public static Map<String, String> wxPayDownloadBill(String appId, String billDate) {
        try {
            String URL = WXPayConstants.DOMAIN_API + WXPayConstants.DOWNLOADBILL_URL_SUFFIX;
            Map<String, String> map = new HashMap<String, String>();
            /*公众账号ID(必填)*/
            map.put("appid", appId);
            /*商户号(必填)*/
            map.put("mch_id", WxMpConfig.MCHID);
            /*随机字符串(必填)*/
            map.put("nonce_str", WXPayUtil.generateNonceStr());
            /*对账单日期(必填) 格式：yyyyMMdd*/
            map.put("bill_date", billDate);
            map.put("bill_type", "ALL");
            /*签名(必填)*/
            map.put("sign", WXPayUtil.generateSignature(map, WxMpConfig.MCHKEY));
            String xml = WXPayUtil.mapToXml(map);
            String result = Httpclient.post(URL, xml);
            Map<String, String> ret;
            // 出现错误，返回XML数据
            if (result.indexOf("<") == 0) {
                ret = WXPayUtil.xmlToMap(result);
            } else {
                // 正常返回csv数据
                ret = new HashMap<String, String>();
                ret.put("return_code", WXPayConstants.SUCCESS);
                ret.put("return_msg", "ok");
                ret.put("data", result);
            }
            log.info("【微信支付】对账单获取结果:result:{}", result);
            return ret;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 判断xml数据的sign是否有效，必须包含sign字段，否则返回false。
     *
     * @param reqData 向wxpay post的请求数据
     * @return 签名是否有效
     * @throws Exception
     */
    public static boolean isResponseSignatureValid(Map<String, String> reqData) throws Exception {
        // 返回数据的签名方式和请求中给定的签名方式是一致的
        return WXPayUtil.isSignatureValid(reqData, WxMpConfig.MCHKEY, SignType.MD5);
    }

    /**
     * 判断支付结果通知中的sign是否有效
     *
     * @param reqData 向wxpay post的请求数据
     * @return 签名是否有效
     * @throws Exception
     */
    public static boolean isPayResultNotifySignatureValid(Map<String, String> reqData) throws Exception {
        String signTypeInData = reqData.get(WXPayConstants.FIELD_SIGN_TYPE);
        SignType signType;
        if (signTypeInData == null) {
            signType = SignType.MD5;
        } else {
            signTypeInData = signTypeInData.trim();
            if (signTypeInData.length() == 0) {
                signType = SignType.MD5;
            } else if (WXPayConstants.MD5.equals(signTypeInData)) {
                signType = SignType.MD5;
            } else if (WXPayConstants.HMACSHA256.equals(signTypeInData)) {
                signType = SignType.HMACSHA256;
            } else {
                throw new Exception(String.format("Unsupported sign_type: %s", signTypeInData));
            }
        }
        return WXPayUtil.isSignatureValid(reqData, WxMpConfig.MCHKEY, signType);
    }

}
