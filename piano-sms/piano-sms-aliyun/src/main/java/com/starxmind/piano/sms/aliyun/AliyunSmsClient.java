package com.starxmind.piano.sms.aliyun;

import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.aliyun.teaopenapi.models.Config;
import com.starxmind.bass.json.XJson;
import com.starxmind.bass.sugar.Asserts;
import com.starxmind.piano.sms.core.SmsClient;
import com.starxmind.piano.sms.core.SmsException;
import com.starxmind.piano.sms.core.request.SmsRequest;
import lombok.Getter;

public class AliyunSmsClient implements SmsClient {
    private static final String DEFAULT_SMS_HOST = "dysmsapi.aliyuncs.com";

    /**
     * 阿里云短信服务Client
     */
    private Client client;

    @Getter
    private String accessKeyId;

    @Getter
    private String accessKeySecret;

    @Getter
    private String endpoint;

    /**
     * 构造SMS客户端
     *
     * @param accessKeyId     阿里云AK
     * @param accessKeySecret 阿里云SK
     * @throws Exception
     */
    public AliyunSmsClient(String accessKeyId,
                           String accessKeySecret) throws Exception {
        this.accessKeyId = accessKeyId;
        this.accessKeySecret = accessKeySecret;
        this.endpoint = DEFAULT_SMS_HOST;

        this.client = createClient();
    }

    /**
     * 构造SMS客户端
     *
     * @param accessKeyId     阿里云AK
     * @param accessKeySecret 阿里云SK
     * @param endpoint
     * @throws Exception
     */
    public AliyunSmsClient(String accessKeyId,
                           String accessKeySecret,
                           String endpoint) throws Exception {
        this.accessKeyId = accessKeyId;
        this.accessKeySecret = accessKeySecret;
        this.endpoint = endpoint;

        this.client = createClient();
    }

    private Client createClient() throws Exception {
        Config config = new Config()
                .setAccessKeyId(accessKeyId)
                .setAccessKeySecret(accessKeySecret)
                .setEndpoint(endpoint);
        return new Client(config);
    }

    /**
     * 发送短信
     *
     * @param smsRequest 短信发送请求
     */
    @Override
    public void send(SmsRequest smsRequest) {
        SendSmsRequest request = new SendSmsRequest()
                .setSignName(smsRequest.getSignName())
                .setTemplateCode(smsRequest.getTemplateCode())
                .setTemplateParam(XJson.serializeAsString(smsRequest.getTemplateParam()))
                .setPhoneNumbers(smsRequest.getPhoneNumber());
        try {
            SendSmsResponse resp = client.sendSms(request);
            Asserts.equals("OK", resp.body.getCode(), resp.body.getMessage());
        } catch (Exception e) {
            throw new SmsException(e);
        }
    }
}
