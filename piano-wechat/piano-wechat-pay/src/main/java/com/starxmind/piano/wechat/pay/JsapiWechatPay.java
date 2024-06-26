package com.starxmind.piano.wechat.pay;

import com.wechat.pay.java.service.payments.jsapi.JsapiService;
import com.wechat.pay.java.service.payments.jsapi.model.Amount;
import com.wechat.pay.java.service.payments.jsapi.model.Payer;
import com.wechat.pay.java.service.payments.jsapi.model.PrepayRequest;
import com.wechat.pay.java.service.payments.jsapi.model.PrepayResponse;
import com.wechat.pay.java.service.payments.nativepay.NativePayService;
import lombok.Getter;

import javax.validation.Valid;
import java.math.BigDecimal;

@Getter
public class JsapiWechatPay extends WechatPay {
    /**
     * 微信原生支付服务
     *
     * @see NativePayService
     */
    private final JsapiService payService;

    public JsapiWechatPay(String appId, PayConfig payConfig) {
        super(appId, payConfig);
        payService = new JsapiService.Builder().config(buildConfig()).build();
    }

    @Override
    public String prepay(@Valid PayReq payReq) {
        PrepayRequest request = new PrepayRequest();
        Amount amount = new Amount();
        amount.setTotal(convertMoney(BigDecimal.valueOf(payReq.getTotal())));
        request.setAmount(amount);
        request.setAppid(getAppId());
        request.setMchid(getPayConfig().getMerchantId());
        request.setDescription(payReq.getDescription());
        request.setNotifyUrl(payReq.getNotifyUrl());
        request.setOutTradeNo(payReq.getOrderNo());
        Payer payer = new Payer();
        payer.setOpenid(payReq.getPayerOpenid());
        request.setPayer(payer);
        PrepayResponse response = payService.prepay(request);
        return response.getPrepayId();
    }

}
