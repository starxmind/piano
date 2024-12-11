package com.starxmind.piano.wechat.pay;

import com.starxmind.piano.wechat.pay.exceptions.PayException;
import com.starxmind.piano.wechat.pay.req.PrepayReq;
import com.starxmind.piano.wechat.pay.req.RefundReq;
import com.wechat.pay.java.core.Config;
import com.wechat.pay.java.core.RSAAutoCertificateConfig;
import com.wechat.pay.java.service.payments.model.Transaction;
import com.wechat.pay.java.service.refund.RefundService;
import com.wechat.pay.java.service.refund.model.AmountReq;
import com.wechat.pay.java.service.refund.model.CreateRequest;
import com.wechat.pay.java.service.refund.model.QueryByOutRefundNoRequest;
import com.wechat.pay.java.service.refund.model.Refund;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
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
    /**
     * 退款服务
     */
    private final RefundService refundService;

    public WechatPay(String appId, PayConfig payConfig) {
        this.appId = appId;
        this.payConfig = payConfig;
        this.refundService = new RefundService.Builder().config(buildConfig()).build();
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
    public long convertMoney(BigDecimal originMoney) {
        return originMoney.multiply(new BigDecimal(100)).longValue();
    }

    public abstract Object prepay(@Valid PrepayReq prepayReq);

    public abstract Transaction fetchPayResult(@NotBlank String transactionId);

    public abstract Transaction fetchPayResultByOrderNo(@NotBlank String orderNo);

    public abstract void close(String orderNo);

    public Refund refund(@Valid RefundReq refundReq) {
        CreateRequest request = new CreateRequest();

        // 原始单号
        request.setOutTradeNo(refundReq.getOrderNo());
        // 退款单号
        request.setOutRefundNo(refundReq.getRefundNo());
        // 退款原因
        request.setReason(refundReq.getReason());

        // 退款金额
        AmountReq amountReq = new AmountReq();
        //原订单金额
        amountReq.setTotal(convertMoney(refundReq.getTotalMoney()));
        //退款金额(这个金额必须是小于等于原订单金额的，可以分多次退款，但是不能超过原订单金额，根据自己的业务需求定)
        amountReq.setRefund(convertMoney(refundReq.getRefundMoney()));
        amountReq.setCurrency(refundReq.getCurrency());
        request.setAmount(amountReq);
        request.setNotifyUrl(refundReq.getNotifyUrl());

        try {
            return refundService.create(request);
        } catch (com.wechat.pay.java.core.exception.ServiceException ex) {
            throw new PayException(ex.getErrorMessage(), ex);
        } catch (Exception ex) {
            throw new PayException("Error to refund", ex);
        }
//        Asserts.isTrue(status.equals(Status.SUCCESS), "微信退款失败, 状态码: " + status);
    }

    public Refund fetchRefundResult(String refundNo) {
        QueryByOutRefundNoRequest request = new QueryByOutRefundNoRequest();
        request.setOutRefundNo(refundNo);
        return refundService.queryByOutRefundNo(request);
    }

}
