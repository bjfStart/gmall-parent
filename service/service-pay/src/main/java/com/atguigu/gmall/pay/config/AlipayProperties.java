package com.atguigu.gmall.pay.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author feng
 * @create 2022-09-16 21:17
 */
@Data
@Component
@ConfigurationProperties(prefix = "app.alipay")
public class AlipayProperties {
    private String gatewayUrl;

    private String appId;

    private String merchantPrivateKey;
    private String charset;
    private String alipayPublicKey;
    private String signType;

    private String returnUrl;
    private String notifyUrl;


}
