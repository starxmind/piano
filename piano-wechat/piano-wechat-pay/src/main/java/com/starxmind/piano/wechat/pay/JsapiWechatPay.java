package com.starxmind.piano.wechat.pay;

import com.google.common.collect.Maps;
import com.starxmind.bass.random.RandomUuidUtils;
import com.starxmind.bass.security.Base64Utils;
import com.starxmind.piano.wechat.pay.req.PrepayReq;
import com.starxmind.piano.wechat.pay.resp.PayPackage;
import com.wechat.pay.java.service.payments.jsapi.JsapiService;
import com.wechat.pay.java.service.payments.jsapi.model.*;
import com.wechat.pay.java.service.payments.model.Transaction;
import com.wechat.pay.java.service.payments.nativepay.NativePayService;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.time.Instant;
import java.util.Base64;
import java.util.Map;

@Getter
public class JsapiWechatPay extends WechatPay {
    /**
     * 微信原生支付服务
     *
     * @see NativePayService
     */
    private final JsapiService payService;

    public JsapiWechatPay(PayConfig payConfig) {
        super(payConfig);
        payService = new JsapiService.Builder().config(buildConfig()).build();
    }

    @Override
    public PayPackage prepay(@Valid PrepayReq prepayReq) {
        PrepayRequest request = new PrepayRequest();
        request.setAppid(prepayReq.getAppId());
        request.setMchid(getPayConfig().getMerchantId());
        request.setOutTradeNo(prepayReq.getOrderNo());
        Amount amount = new Amount();
        amount.setTotal((int) convertMoney(prepayReq.getTotal()));
        request.setAmount(amount);
        request.setDescription(prepayReq.getDescription());
        Payer payer = new Payer();
        payer.setOpenid(prepayReq.getPayerOpenid());
        request.setPayer(payer);
        request.setNotifyUrl(prepayReq.getNotifyUrl());
        PrepayResponse response = payService.prepay(request);
        return getPayPackage(prepayReq.getAppId(), response.getPrepayId());
    }

    private PayPackage getPayPackage(String appId, String prepayId) {
        long timeStamp = Instant.now().getEpochSecond();
        String nonceStr = RandomUuidUtils.gen32UpperCase();
        String prepayPackage = "prepay_id=" + prepayId;

        Map<String, Object> retMap = Maps.newHashMap();
        retMap.put("appId", appId);
        retMap.put("timeStamp", String.valueOf(timeStamp));
        retMap.put("nonceStr", nonceStr);
        retMap.put("package", prepayPackage);
        retMap.put("signType", "RSA");
        retMap.put("paySign", paySign(appId, getPrivateKey(), timeStamp, nonceStr, prepayPackage));
        return new PayPackage(prepayId, retMap);
    }

    private PrivateKey getPrivateKey() {
        PayConfig payConfig = getPayConfig();
        String content;
        if (StringUtils.isNotBlank(payConfig.getPrivateKey())) {
            content = payConfig.getPrivateKey();
        } else {
            try {
                content = new String(Files.readAllBytes(Paths.get(payConfig.getPrivateKeyPath())));
            } catch (IOException e) {
                throw new RuntimeException("Fatal error when read file from private key path: " + payConfig.getPrivateKeyPath());
            }
        }
        try {
            String privateKey = content.replace("-----BEGIN PRIVATE KEY-----", "")
                    .replace("-----END PRIVATE KEY-----", "")
                    .replaceAll("\\s+", "");
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return keyFactory.generatePrivate(new PKCS8EncodedKeySpec(Base64.getMimeDecoder().decode(privateKey)));
        } catch (Exception e) {
            throw new RuntimeException("Fatal error when read file from private key path: " + payConfig.getPrivateKeyPath());
        }
    }

    private String paySign(String appId, PrivateKey privateKey, long timeStamp, String nonceStr, String prepayPackage) {
        try {
            Signature signature = Signature.getInstance("SHA256WithRSA");
            signature.initSign(privateKey);
            String content = String.format("%s\n%s\n%s\n%s\n", appId, timeStamp, nonceStr, prepayPackage);
            signature.update(content.getBytes());
            return Base64Utils.encrypt(signature.sign());
        } catch (Exception e) {
            throw new RuntimeException("Fatal error when sign wechat payment");
        }
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
