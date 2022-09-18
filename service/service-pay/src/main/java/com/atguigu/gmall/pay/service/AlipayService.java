package com.atguigu.gmall.pay.service;

import com.alipay.api.AlipayApiException;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author feng
 * @create 2022-09-16 22:59
 */
public interface AlipayService {
    /**
     * 生成指定订单的支付页
     * @param orderId
     * @return
     */
    String getAlipayPageHtml(Long orderId) throws AlipayApiException;

    /**
     * 支付宝验签
     * @param paramMaps
     * @return
     */
    boolean rsaCheckV1(Map<String, String> paramMaps) throws AlipayApiException;

    /**
     * 发送支付成功消息给订单交换机
     * @param param
     */
    void sendPayedMsg(Map<String, String> param);
}
