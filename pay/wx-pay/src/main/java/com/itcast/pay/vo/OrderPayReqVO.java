package com.itcast.pay.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 功能描述：订单支付请求参数
 * 1.商品订单
 * 2.服务项目订单
 *
 * @author JIAQI
 * @date 2020/12/17 - 9:54
 */
@Data
public class OrderPayReqVO implements Serializable {

    private static final long serialVersionUID = 2858753477244997386L;

    /**
     * 订单id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long orderId;

    /**
     * 订单编号
     */
    private String orderNumber;

    /**
     * 商家id
     */
    private String merchantId;

    /**
     * 店铺名称
     */
    private String orgName;

    /**
     * 商品id
     */
    private Long productId;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 待支付金额(元)
     */
    private BigDecimal actualPaymentAmount;

    /**
     * 回调地址
     */
    private String notifyUrl;

}
