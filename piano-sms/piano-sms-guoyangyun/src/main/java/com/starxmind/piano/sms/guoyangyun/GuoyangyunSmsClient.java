package com.starxmind.piano.sms.guoyangyun;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.starxmind.bass.http.StarxHttp;
import com.starxmind.bass.sugar.Asserts;
import com.starxmind.piano.sms.core.SmsClient;
import com.starxmind.piano.sms.core.SmsException;
import com.starxmind.piano.sms.core.request.SmsRequest;
import com.starxmind.piano.sms.guoyangyun.response.GuoyangyunSmsResponse;
import lombok.Getter;

import java.util.Map;

/**
 * 阿里云第三方短信服务
 *
 * @author pizzalord
 * @since 1.0
 */
public class GuoyangyunSmsClient implements SmsClient {
    private final static String SEND_SMS_API = "https://gyytz.market.alicloudapi.com/sms/smsSend";

    @Getter
    private StarxHttp StarxHttp;

    @Getter
    private String appCode;

    private String authorization;

    public GuoyangyunSmsClient(String appCode, StarxHttp StarxHttp) {
        this.appCode = appCode;
        this.authorization = "APPCODE " + appCode;
        this.StarxHttp = StarxHttp;
    }

    /**
     * 文档见 https://market.aliyun.com/products/57126001/cmapi00037415.html?spm=5176.2020520132.101.2.51861a469BFbOn#sku=yuncode31415000012
     *
     * @param smsRequest 短信发送请求
     */
    @Override
    public void send(SmsRequest smsRequest) {
        try {
            GuoyangyunSmsResponse response = StarxHttp.postForObject(
                    SEND_SMS_API,
                    ImmutableMap.of("Authorization", authorization),
                    convertParams(smsRequest),
                    GuoyangyunSmsResponse.class
            );
            Asserts.isTrue("0".equals(response.getCode()), response.getCode() + ":" + response.getMsg());
        } catch (Exception e) {
            throw new SmsException(e.getMessage(), e);
        }
    }

    private Map<String, String> convertParams(SmsRequest smsRequest) {
        Map<String, String> formParams = Maps.newHashMap();
        formParams.put("mobile", smsRequest.getPhoneNumber());
        formParams.put("param", buildParam(smsRequest.getTemplateParam()));
        formParams.put("smsSignId", smsRequest.getSignName());
        formParams.put("templateId", smsRequest.getTemplateCode());
        return formParams;
    }

    /**
     * 参数格式: **code**:1234,**minute**:5
     *
     * @param templateParam 模板参数
     * @return
     */
    private String buildParam(Map<String, Object> templateParam) {
        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, Object> entry : templateParam.entrySet()) {
            sb.append("**").append(entry.getKey()).append("**").append(":").append(entry.getValue()).append(",");
        }
        sb.setLength(sb.length() - 1);
        return sb.toString();
    }
}
