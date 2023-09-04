package com.starxmind.piano.wechat.client;

import com.starxmind.bass.http.StarxHttp;
import com.starxmind.piano.wechat.client.response.WechatCellphoneResponse;
import com.starxmind.piano.wechat.client.response.WechatSessionResponse;
import com.starxmind.piano.wechat.token.core.AbstractAccessTokenManager;
import com.starxmind.piano.wechat.token.core.WeChatInfo;

/**
 * 微信Api客户端
 *
 * @author pizzalord
 * @since 1.0
 */
public class WechatClient {
    private WeChatInfo weChatInfo;
    private StarxHttp StarxHttp;
    private AbstractAccessTokenManager accessTokenManager;

    public WechatClient(WeChatInfo weChatInfo, StarxHttp StarxHttp, AbstractAccessTokenManager accessTokenManager) {
        this.weChatInfo = weChatInfo;
        this.StarxHttp = StarxHttp;
        this.accessTokenManager = accessTokenManager;
    }

    // API-1: 获取session
    public WechatSessionResponse fetchSession(String code) {
        String url = String.format("https://api.weixin.qq.com/sns/jscode2session?appid=%s&secret=%s&js_code=%s&grant_type=authorization_code",
                weChatInfo.getAppId(), weChatInfo.getSecret(), code);
        WechatSessionResponse resp = StarxHttp.getForObject(url, WechatSessionResponse.class);
        resp.ok();
        return resp;
    }

    // API-2: 获取手机号
    public WechatCellphoneResponse fetchCellphone(String code) {
        String url = String.format(
                "https://api.weixin.qq.com/wxa/business/getuserphonenumber?access_token=%s",
                accessTokenManager.getAccessToken()
        );
        WechatCellphoneResponse resp = StarxHttp.postForObject(url, null, String.format("{\"code\":\"%s\"}", code), WechatCellphoneResponse.class);
        resp.ok();
        return resp;
    }
}
