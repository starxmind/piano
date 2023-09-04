package com.starxmind.piano.sms.core.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * 发送短信请求
 *
 * @author pizzalord
 * @since 1.0
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Data
public class SmsRequest {
    /**
     * 短信签名, 出现于【】中的位置
     * 例如: 【阿里云】您的邮箱（postmaster@xxx.com）申请动态密码登陆，如确认是本人行为，请正确提交以下动态密码：123456
     */
    private String signName;

    /**
     * 模板代码, 通常在短信服务商的模板管理中维护
     */
    private String templateCode;

    /**
     * 模板参数, 用来替换模板消息中的变量
     */
    private Map<String, Object> templateParam;

    /**
     * 目标号码, 用来接收短信的目标号码
     */
    private String phoneNumber;
}
