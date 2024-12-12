package com.starxmind.piano.wechat.pay;

import com.starxmind.piano.wechat.pay.req.PrepayReq;
import com.wechat.pay.java.service.payments.model.Transaction;
import com.wechat.pay.java.service.payments.nativepay.NativePayService;
import com.wechat.pay.java.service.payments.nativepay.model.*;
import lombok.Getter;

import javax.validation.Valid;

@Getter
public class NativeWechatPay extends WechatPay {
    /**
     * 微信原生支付服务
     *
     * @see NativePayService
     */
    private final NativePayService payService;

    public NativeWechatPay(PayConfig payConfig) {
        super(payConfig);
        payService = new NativePayService.Builder().config(buildConfig()).build();
    }

    @Override
    public String prepay(@Valid PrepayReq prepayReq) {
        // 使用自动更新平台证书的RSA配置
        // 一个商户号只能初始化一个配置，否则会因为重复的下载任务报错

        // 构建service
        // request.setXxx(val)设置所需参数，具体参数可见Request定义
        PrepayRequest request = new PrepayRequest();
        Amount amount = new Amount();
        amount.setTotal((int) convertMoney(prepayReq.getTotal()));
        request.setAmount(amount);
        request.setAppid(prepayReq.getAppId());
        request.setMchid(getPayConfig().getMerchantId());
        request.setDescription(prepayReq.getDescription());
        request.setNotifyUrl(prepayReq.getNotifyUrl());
        request.setOutTradeNo(prepayReq.getOrderNo());
        // 调用下单方法，得到应答
        PrepayResponse response = payService.prepay(request);
        // 使用微信扫描 code_url 对应的二维码，即可体验Native支付
        return response.getCodeUrl();
    }

    @Override
    public Transaction fetchPayResult(String transactionId) {
        QueryOrderByIdRequest request = new QueryOrderByIdRequest();
        request.setMchid(getPayConfig().getMerchantId());
        request.setTransactionId(transactionId);
        return payService.queryOrderById(request);
    }

    @Override
    public Transaction fetchPayResultByOrderNo(String orderNo) {
        QueryOrderByOutTradeNoRequest request = new QueryOrderByOutTradeNoRequest();
        request.setMchid(getPayConfig().getMerchantId());
        request.setOutTradeNo(orderNo);
        return payService.queryOrderByOutTradeNo(request);
    }

    @Override
    public void close(String orderNo) {
        CloseOrderRequest request = new CloseOrderRequest();
        request.setMchid(getPayConfig().getMerchantId());
        request.setOutTradeNo(orderNo);
        payService.closeOrder(request);
    }

}
