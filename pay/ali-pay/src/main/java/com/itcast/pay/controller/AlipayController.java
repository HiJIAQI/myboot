package com.itcast.pay.controller;

import cn.util.Result;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.domain.*;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.response.*;

import com.itcast.pay.utils.zfbpay.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * 功能描述：
 *
 * @date 2019/12/9 - 16:13
 */
@Slf4j
@Controller
@RequestMapping("/alipay")
public class AlipayController {

    /**
     * 当面付——扫码支付
     *
     * @param model
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/alipay")
    public String tradePrecreatePay(Model model) {
        //填充业务参数
        AlipayTradeAppPayModel PayModel = new AlipayTradeAppPayModel();
        /*商户订单号(必填)*/
        PayModel.setOutTradeNo(StringUtils.getOutTradeNo());
        /*订单总金额(单位元 必填)*/
        PayModel.setTotalAmount("1");
        /*订单标题 (必填)*/
        PayModel.setSubject("测试DEMO");
        /*订单允许的最晚付款时间 (选填)*/
        PayModel.setTimeoutExpress("90m");
        /*商户号  (选填)*/
        PayModel.setStoreId("323234234");
        //调取AliPayApi中的预下单接口
        try {

            AlipayTradePrecreateResponse response = AliPayApi.tradePrecreateToResponse(PayModel, AlipayConfig.notify_url);
            model.addAttribute("code_url", response.getQrCode());
            return "/pay";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 电脑网站支付
     * 跳转到支付界面
     * 支付成功后 回调接口
     *
     * @param httpResponse
     * @throws IOException
     */
    @GetMapping("/pay")
    public void pcPay(HttpServletResponse httpResponse) {
        //填充业务参数
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        //商品描述，可以为空
        model.setBody("");
        model.setGoodsType("1");
        model.setOutTradeNo(StringUtils.getOutTradeNo());
        //商品价格总额
        model.setTotalAmount("100000");
        //商品名称
        model.setSubject("测试沙箱环境支付");
        model.setProductCode("FAST_INSTANT_TRADE_PAY");
        try {
            AliPayApi.tradePagePay(httpResponse, model, AlipayConfig.notify_url, AlipayConfig.return_url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 交易查询
     */
    @RequestMapping(value = "/tradequery")
    @ResponseBody
    public Result tradeQuery() {
        try {
            AlipayTradeQueryModel model = new AlipayTradeQueryModel();
            model.setOutTradeNo("121015485915759");
            model.setTradeNo("2019121022001436891000117067");
            AlipayTradeQueryResponse alipayTradeQueryResponse = AliPayApi.tradeQueryToResponse(model);
            return Result.success(JSONObject.parseObject(alipayTradeQueryResponse.getBody()));
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 退款
     */
    @RequestMapping(value = "/traderefund")
    @ResponseBody
    public Result tradeRefund() {
        try {
            AlipayTradeRefundModel model = new AlipayTradeRefundModel();
            /*订单支付时传入的商户订单号(必填)*/
            model.setOutTradeNo("121015485915759");
            /*支付宝交易号(必填)*/
            model.setTradeNo("2019121022001436891000117067");
            /*需要退款的金额(必填)*/
            model.setRefundAmount("1.00");
            /*退款的原因说明(选填)*/
            model.setRefundReason("测试退款接口");
            AlipayTradeRefundResponse alipayTradeRefundResponse = AliPayApi.tradeRefundToResponse(model);
            return Result.success(JSONObject.parseObject(alipayTradeRefundResponse.getBody()));
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 退款查询
     */
    @RequestMapping(value = "/traderefundfastpayrefundquery")
    @ResponseBody
    public Result tradeRefundFastpayRefundQuery() {
        try {
            AlipayTradeFastpayRefundQueryModel model = new AlipayTradeFastpayRefundQueryModel();
            /*支付宝交易号(必填)*/
            model.setTradeNo("2019121022001436891000117067");
            /*订单支付时传入的商户订单号(必填)*/
            model.setOutTradeNo("121015485915759");
            /*本笔退款对应的退款请求号(必填)*/
            model.setOutRequestNo("121015485915759");
            AlipayTradeFastpayRefundQueryResponse alipayTradeFastpayRefundQueryResponse = AliPayApi.tradeFastpayReFundQueryToResponse(model);
            return Result.success(JSONObject.parseObject(alipayTradeFastpayRefundQueryResponse.getBody()));
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 关闭订单
     */
    @RequestMapping(value = "/tradeClose")
    @ResponseBody
    public String tradeClose(@RequestParam("out_trade_no") String outTradeNo, @RequestParam("trade_no") String tradeNo) {
        try {
            AlipayTradeCloseModel model = new AlipayTradeCloseModel();
            model.setOutTradeNo(outTradeNo);
            model.setTradeNo(tradeNo);
            return AliPayApi.tradeCloseToResponse(model).getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 下载对账单
     */
    @RequestMapping(value = "/dataDataserviceBill")
    @ResponseBody
    public Result dataDataserviceBill(@RequestParam("billDate") String billDate) {
        try {
            AlipayDataDataserviceBillDownloadurlQueryModel model = new AlipayDataDataserviceBillDownloadurlQueryModel();
            model.setBillType("trade");
            model.setBillDate(billDate);
            AlipayDataDataserviceBillDownloadurlQueryResponse alipayDataDataserviceBillDownloadurlQueryResponse = AliPayApi.billDownloadUrlQueryToResponse(model);
            return Result.success(JSONObject.parseObject(alipayDataDataserviceBillDownloadurlQueryResponse.getBody()));
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 支付宝商家账户账务明细查询
     */
    @RequestMapping(value = "/databillaccountlogquery")
    @ResponseBody
    public Result dataBillAccountlogQuery() {
        try {
            AlipayDataBillAccountlogQueryModel model = new AlipayDataBillAccountlogQueryModel();
            model.setStartTime("2019-12-08 00:00:00");
            model.setEndTime("2019-12-10 00:00:00");
//            model.setAlipayOrderNo("2019-01-02 00:00:00");
//            model.setMerchantOrderNo();
            model.setPageNo("1");
            model.setPageSize("2000");
            AlipayDataBillAccountlogQueryResponse alipayDataBillAccountlogQueryResponse = AliPayApi.dataBillAccountlogQueryToResponse(model);
            return Result.success(JSONObject.parseObject(alipayDataBillAccountlogQueryResponse.getBody()));
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 异步回调
     * *注意：此方法会被调用两次
     * 一次是扫码的时候
     * 一次是支付成功的时候
     *
     * @param request
     * @return
     */
    @PostMapping(value = "/notify_url")
    @ResponseBody
    public String notifyUrl(HttpServletRequest request) {
        try {
            //将POST请求异步通知中收到的所有参数都存放到 map 中
            Map<String, String> params = StringUtils.toMap(request);
            //校验签名
            if (!AlipaySignature.rsaCheckV1(params, AlipayConfig.alipay_public_key, "UTF-8", "RSA2")) {
                log.error("【支付宝支付异步通知】签名验证失败, response={}", params);
                throw new RuntimeException("【支付宝支付异步通知】签名验证失败");
            }
            //校验交易状态
            String tradeStatus = params.get("trade_status");
            if (!tradeStatus.equals("TRADE_FINISHED") &&
                    !tradeStatus.equals("TRADE_SUCCESS")) {
                throw new RuntimeException("【支付宝支付异步通知】发起支付, trade_status != SUCCESS | FINISHED");
            }
            log.info("【支付成功】");
            //TODO 相应订单业务处理
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "failure";
    }

    /**
     * 电脑网站支付回调页面
     *
     * @return
     */
    @GetMapping("/success")
    @ResponseBody
    public String success() {
        return "支付成功";
    }

    public static void main(String[] args) {
        AlipayTradeFastpayRefundQueryModel model = new AlipayTradeFastpayRefundQueryModel();
        /*订单支付时传入的商户订单号(必填)*/
        model.setOutTradeNo("121017294815759");
        /*支付宝交易号(必填)*/
        model.setTradeNo("2019121022001436891000123787");
        /*需要退款的金额(必填)*/
        //model.setRefundAmount("1.00");
        /*退款的原因说明(选填)*/
        //model.setRefundReason("测试退款接口");
        /*本笔退款对应的退款请求号(必填)*/
        model.setOutRequestNo("121017294815759");
        try {
            AlipayTradeFastpayRefundQueryResponse alipayTradeRefundResponse = AliPayApi.tradeFastpayReFundQueryToResponse(model);
            System.out.println(alipayTradeRefundResponse.getBody());
            System.out.println(alipayTradeRefundResponse.toString());
            System.out.println(alipayTradeRefundResponse.isSuccess());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

