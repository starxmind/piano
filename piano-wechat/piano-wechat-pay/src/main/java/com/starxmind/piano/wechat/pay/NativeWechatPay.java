package com.starxmind.piano.wechat.pay;

import com.wechat.pay.java.service.payments.nativepay.NativePayService;
import com.wechat.pay.java.service.payments.nativepay.model.Amount;
import com.wechat.pay.java.service.payments.nativepay.model.PrepayRequest;
import com.wechat.pay.java.service.payments.nativepay.model.PrepayResponse;
import lombok.Getter;

import javax.validation.Valid;
import java.math.BigDecimal;

@Getter
public class NativeWechatPay extends WechatPay {
    /**
     * 微信原生支付服务
     *
     * @see NativePayService
     */
    private final NativePayService payService;

    public NativeWechatPay(String appId, PayConfig payConfig) {
        super(appId, payConfig);
        payService = new NativePayService.Builder().config(buildConfig()).build();
    }

    @Override
    public String prepay(@Valid PayReq payReq) {
        // 使用自动更新平台证书的RSA配置
        // 一个商户号只能初始化一个配置，否则会因为重复的下载任务报错

        // 构建service
        // request.setXxx(val)设置所需参数，具体参数可见Request定义
        PrepayRequest request = new PrepayRequest();
        Amount amount = new Amount();
        amount.setTotal(convertMoney(BigDecimal.valueOf(payReq.getTotal())));
        request.setAmount(amount);
        request.setAppid(getAppId());
        request.setMchid(getPayConfig().getMerchantId());
        request.setDescription(payReq.getDescription());
        request.setNotifyUrl(payReq.getNotifyUrl());
        request.setOutTradeNo(payReq.getOrderNo());
        // 调用下单方法，得到应答
        PrepayResponse response = payService.prepay(request);
        // 使用微信扫描 code_url 对应的二维码，即可体验Native支付
        return response.getCodeUrl();
    }

}
