package com.starxmind.piano.dingtalk;

import com.google.common.collect.ImmutableMap;
import com.starxmind.bass.http.XHttp;
import com.starxmind.bass.json.XJson;
import com.starxmind.bass.security.Base64Utils;
import com.starxmind.bass.security.HmacUtils;
import com.starxmind.bass.sugar.Asserts;
import com.starxmind.bass.sugar.ExpressionUtils;
import com.starxmind.piano.dingtalk.req.MarkdownReq;
import com.starxmind.piano.dingtalk.req.TextReq;
import com.starxmind.piano.dingtalk.req.common.At;
import com.starxmind.piano.dingtalk.req.markdown.Markdown;
import com.starxmind.piano.dingtalk.req.text.Text;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.digest.HmacAlgorithms;
import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

/**
 * 钉钉机器人
 *
 * @author pizzalord
 * @since 1.0
 */
@RequiredArgsConstructor
@Data
public class DingTalkRobot {
    private XHttp XHttp;
    private String accessToken;
    private String secret;

    public DingTalkRobot(XHttp XHttp, String accessToken) {
        this.XHttp = XHttp;
        this.accessToken = accessToken;
    }

    public DingTalkRobot(XHttp XHttp, String accessToken, String secret) {
        this.XHttp = XHttp;
        this.accessToken = accessToken;
        this.secret = secret;
    }

    private String generateWebhookUrl() {
        // 不加签的情况, 获取不带签名的URL
        if (StringUtils.isBlank(this.secret)) {
            Map<String, String> urlVariables = ImmutableMap.of(
                    "accessToken", accessToken
            );
            String webhookUrl = ExpressionUtils.evaluateExpression(DingTalkConstants.WEBHOOK_URL, urlVariables);
            return webhookUrl;
        }

        // 生成签名
        String timestamp = String.valueOf(System.currentTimeMillis());
        String stringToSign = timestamp + "\n" + secret;
        String sign;
        try {
            byte[] signData = HmacUtils.encrypt(stringToSign, secret, HmacAlgorithms.HMAC_SHA_256);
            String base64Str = Base64Utils.encrypt(signData);
            sign = URLEncoder.encode(base64Str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        // 带签名的接口URL
        Map<String, String> urlVariables = ImmutableMap.of(
                "accessToken", accessToken,
                "timestamp", timestamp,
                "sign", sign
        );
        String webhookUrl = ExpressionUtils.evaluateExpression(DingTalkConstants.WEBHOOK_URL_WITH, urlVariables);
        return webhookUrl;
    }

    private void sendMessage(String json) {
        // Call the dingtalk api
        String webhookUrl = generateWebhookUrl();
        Response response = XHttp.postForObject(webhookUrl, null, json, Response.class);
        Asserts.equals(response.getErrcode(), 0, new DingTalkResponse(response.getErrmsg()));
    }

    public void sendTextMessage(String content) {
        // message body
        TextReq textReq = TextReq.builder()
                .text(new Text(content))
                .build();
        String json = XJson.serializeAsString(textReq);
        sendMessage(json);
    }

    public void sendMarkdownMessage(String title, String text) {
        sendMarkdownMessage(title, text, null, null, false);
    }

    public void sendMarkdownMessage(String title, String text, boolean atAll) {
        sendMarkdownMessage(title, text, null, null, atAll);
    }

    public void sendMarkdownMessage(String title, String text,
                                    List<String> atMobiles, List<String> atUserIds) {
        sendMarkdownMessage(title, text, atMobiles, atUserIds, false);
    }

    public void sendMarkdownMessage(String title, String text,
                                    List<String> atMobiles, List<String> atUserIds, boolean atAll) {
        // message body
        MarkdownReq markdownReq = MarkdownReq.builder()
                .markdown(new Markdown(title, text))
                .at(At.builder()
                        .atMobiles(atMobiles)
                        .atUserIds(atUserIds)
                        .isAtAll(atAll)
                        .build())
                .build();
        String json = XJson.serializeAsString(markdownReq);
        sendMessage(json);
    }
}
