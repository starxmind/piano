package com.starxmind.piano.wechat.pay;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder(toBuilder = true)
@Data
public class PayConfig {
    /**
     * 商户号
     */
    private final String merchantId;
    /**
     * 商户API私钥
     */
    private final String privateKey;
    /**
     * 商户API私钥路径
     */
    private final String privateKeyPath;
    /**
     * 商户证书序列号
     */
    private final String merchantSerialNumber;
    /**
     * 商户APIV3密钥
     */
    private final String apiV3Key;
}
