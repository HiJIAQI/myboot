package com.itcast.pay.controller;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.response.AlipayTradePrecreateResponse;

import com.itcast.pay.utils.zfbpay.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

/**
 * 功能描述：
 *
 * @date 2019/11/28 - 15:18
 */
@Slf4j
@Controller
@RequestMapping("/zfb")
public class ZfbController {

    public static void main(String[] args) {
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.private_key, "json", AlipayConfig.input_charset, AlipayConfig.alipay_public_key, "RSA2");
        AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();//创建API对应的request类

        Map<String, Object> reqData = new HashMap<>();
        reqData.put("out_trade_no", "tradeprecreate" + StringUtils.getOutTradeNo());
        reqData.put("subject", "小编机器人纠错");
        reqData.put("total_amount", "0.01");
        reqData.put("store_id", "123556");
        reqData.put("timeout_express", "1m");
        //把订单信息转换为json对象的字符串
        request.setBizContent(JSON.toJSONString(reqData));
        //设置回调地址
        request.setNotifyUrl("http://www.cqkj.nat300.com/zfb/notify_url");
        try {
            AlipayTradePrecreateResponse response = alipayClient.execute(request);

            System.out.println(response.getBody());
            System.out.println(response.getCode());
            System.out.println(response.getQrCode());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
