package com.starxmind.piano.wechat.pay;

import com.wechat.pay.java.core.Config;
import com.wechat.pay.java.core.RSAAutoCertificateConfig;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import javax.validation.Valid;
import java.math.BigDecimal;

@Getter
public abstract class WechatPay {
    /**
     * 应用ID
     */
    private final String appId;
    /**
     * 支付配置
     */
    private final PayConfig payConfig;

    public WechatPay(String appId, PayConfig payConfig) {
        this.appId = appId;
        this.payConfig = payConfig;
    }

    public Config buildConfig() {
        RSAAutoCertificateConfig.Builder configBuilder = new RSAAutoCertificateConfig.Builder()
                .merchantId(payConfig.getMerchantId())
                .merchantSerialNumber(payConfig.getMerchantSerialNumber())
                .apiV3Key(payConfig.getApiV3Key());

        // Set private key...
        if (StringUtils.isNotBlank(payConfig.getPrivateKey())) {
            configBuilder = configBuilder.privateKey(payConfig.getPrivateKey());
        } else if (StringUtils.isNotBlank(payConfig.getPrivateKeyPath())) {
            configBuilder = configBuilder.privateKeyFromPath(payConfig.getPrivateKeyPath());
        } else {
            throw new RuntimeException("Neither privateKey nor privateKeyPath can be null!");
        }

        return configBuilder
                .build();
    }

    // 微信支付的钱单位是分
    public int convertMoney(BigDecimal originMoney) {
        return originMoney.multiply(new BigDecimal(100)).intValue();
    }

    public abstract String prepay(@Valid PayReq payReq);
}
