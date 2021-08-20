package com.itcast.pay.utils.zfbpay;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayObject;
import com.alipay.api.request.*;
import com.alipay.api.response.*;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 功能描述：支付宝支付相关接口
 *
 * @date 2019/12/10 - 9:34
 */
@Slf4j
public class AliPayApi {

    private static AlipayConfig alipayConfig;

    /**
     * 实例化AlipayClient对象
     * 减少每次调用接口时的资源浪费
     *
     * @return {@link AlipayClient}
     */
    private static AlipayClient getAliPayClient() {
        //若AlipayConfig未被实例化就证明alipayClient为空
        if (alipayConfig == null) {
            log.error("aliPayClient 未被初始化");
            //通过构造方法对alipayClient进行赋值
            alipayConfig = new AlipayConfig();
        }
        return alipayConfig.getAlipayClient();
    }

    /**
     * 统一收单线下交易预创建
     * 适用于：扫码支付等
     *
     * @param model     {@link AlipayObject}
     * @param notifyUrl 异步通知URL
     * @return {@link AlipayTradePrecreateResponse}
     * @throws AlipayApiException 支付宝 Api 异常
     */
    public static AlipayTradePrecreateResponse tradePrecreateToResponse(AlipayObject model, String notifyUrl) throws AlipayApiException {
        AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();
        request.setBizModel(model);
        request.setNotifyUrl(notifyUrl);
        return getAliPayClient().execute(request);
    }

    /**
     * 电脑网站支付
     *
     * @param httpResponse {@link HttpServletResponse}
     * @param model        {@link AlipayObject}
     * @param notifyUrl    异步通知URL
     * @param returnUrl    同步通知URL
     * @throws AlipayApiException 支付宝 Api 异常
     * @throws IOException        IO 异常
     */
    public static void tradePagePay(HttpServletResponse httpResponse, AlipayObject model, String notifyUrl, String returnUrl) throws AlipayApiException, IOException {
        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
        request.setBizModel(model);
        //异步回调URL(post请求)
        request.setNotifyUrl(notifyUrl);
        //回调跳转页面URL(get请求)
        request.setReturnUrl(returnUrl);
        // 调用SDK生成表单
        String form = getAliPayClient().pageExecute(request).getBody();
        httpResponse.setContentType("text/html;charset=utf-8");
        //直接将完整的表单html输出到页面
        httpResponse.getWriter().write(form);
        httpResponse.getWriter().flush();
        httpResponse.getWriter().close();
    }

    /**
     * 统一收单线下交易查询
     *
     * @param model {@link AlipayObject}
     * @return {@link AlipayTradeQueryResponse}
     * @throws AlipayApiException
     */
    public static AlipayTradeQueryResponse tradeQueryToResponse(AlipayObject model) throws AlipayApiException {
        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
        request.setBizModel(model);
        return getAliPayClient().execute(request);
    }

    /**
     * 统一收单交易退款接口
     *
     * @param model {@link AlipayObject}
     * @return {@link AlipayTradeFastpayRefundQueryResponse}
     * @throws AlipayApiException
     */
    public static AlipayTradeRefundResponse tradeRefundToResponse(AlipayObject model) throws AlipayApiException {
        AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
        request.setBizModel(model);
        return getAliPayClient().execute(request);
    }

    /**
     * 统一收单交易退款查询
     *
     * @param model {@link AlipayObject}
     * @return {@link AlipayTradeFastpayRefundQueryRequest}
     * @throws AlipayApiException
     */
    public static AlipayTradeFastpayRefundQueryResponse tradeFastpayReFundQueryToResponse(AlipayObject model) throws AlipayApiException {
        AlipayTradeFastpayRefundQueryRequest request = new AlipayTradeFastpayRefundQueryRequest();
        request.setBizModel(model);
        return getAliPayClient().execute(request);
    }

    /**
     * 统一收单交易撤销接口
     * 支付交易返回失败或支付系统超时，调用该接口撤销交易
     *
     * @param model {@link AlipayObject}
     * @return {@link AlipayTradeCancelResponse}
     * @throws AlipayApiException 支付宝 Api 异常
     */
    public static AlipayTradeCancelResponse tradeCancelToResponse(AlipayObject model)
            throws AlipayApiException {
        AlipayTradeCancelRequest request = new AlipayTradeCancelRequest();
        request.setBizModel(model);
        return getAliPayClient().execute(request);
    }

    /**
     * 统一收单交易关闭接口
     * 用于交易创建后，用户在一定时间内未进行支付，可调用该接口直接将未付款的交易进行关闭
     *
     * @param model {@link AlipayObject}
     * @return {@link AlipayTradeCloseResponse}
     * @throws AlipayApiException 支付宝 Api 异常
     */
    public static AlipayTradeCloseResponse tradeCloseToResponse(AlipayObject model) throws AlipayApiException {
        AlipayTradeCloseRequest request = new AlipayTradeCloseRequest();
        request.setBizModel(model);
        return getAliPayClient().execute(request);
    }

    /**
     * 查询对账单下载地址
     * 为方便商户快速查账，支持商户通过本接口获取商户离线账单下载地址
     *
     * @param model {@link AlipayObject}
     * @return {@link AlipayDataDataserviceBillDownloadurlQueryResponse}
     * @throws AlipayApiException 支付宝 Api 异常
     */
    public static AlipayDataDataserviceBillDownloadurlQueryResponse billDownloadUrlQueryToResponse(AlipayObject model) throws AlipayApiException {
        AlipayDataDataserviceBillDownloadurlQueryRequest request = new AlipayDataDataserviceBillDownloadurlQueryRequest();
        request.setBizModel(model);
        return getAliPayClient().execute(request);
    }

    /**
     * 支付宝商家账户账务明细查询
     *
     * @param model {@link AlipayObject}
     * @return {@link AlipayDataBillAccountlogQueryResponse}
     * @throws AlipayApiException 支付宝 Api 异常
     */
    public static AlipayDataBillAccountlogQueryResponse dataBillAccountlogQueryToResponse(AlipayObject model) throws AlipayApiException {
        AlipayDataBillAccountlogQueryRequest request = new AlipayDataBillAccountlogQueryRequest();
        request.setBizModel(model);
        return getAliPayClient().execute(request);
    }

}
