package com.starxmind.piano.wechat.client;

import com.starxmind.bass.http.XHttp;
import com.starxmind.bass.json.XJson;
import com.starxmind.piano.wechat.client.request.UnlimitedQRCodeRequest;
import com.starxmind.piano.wechat.client.response.WechatCellphoneResponse;
import com.starxmind.piano.wechat.client.response.WechatSessionResponse;
import com.starxmind.piano.wechat.token.core.AccessTokenManager;
import com.starxmind.piano.wechat.token.core.WeChatInfo;

/**
 * 微信Api客户端
 *
 * @author pizzalord
 * @since 1.0
 */
public class WechatClient {
    private WeChatInfo weChatInfo;
    private XHttp XHttp;
    private AccessTokenManager accessTokenManager;

    public WechatClient(WeChatInfo weChatInfo, XHttp XHttp, AccessTokenManager accessTokenManager) {
        this.weChatInfo = weChatInfo;
        this.XHttp = XHttp;
        this.accessTokenManager = accessTokenManager;
    }

    // API-1: 获取session
    public WechatSessionResponse fetchSession(String code) {
        String url = String.format("https://api.weixin.qq.com/sns/jscode2session?appid=%s&secret=%s&js_code=%s&grant_type=authorization_code",
                weChatInfo.getAppId(), weChatInfo.getSecret(), code);
        WechatSessionResponse resp = XHttp.getForObject(url, WechatSessionResponse.class);
        resp.ok();
        return resp;
    }

    // API-2: 获取手机号
    public WechatCellphoneResponse fetchCellphone(String code) {
        String url = String.format(
                "https://api.weixin.qq.com/wxa/business/getuserphonenumber?access_token=%s",
                accessTokenManager.getAccessToken()
        );
        WechatCellphoneResponse resp = XHttp.postForObject(url, null, String.format("{\"code\":\"%s\"}", code), WechatCellphoneResponse.class);
        resp.ok();
        return resp;
    }

    // API-3: 获取小程序码
    public byte[] fetchUnlimitedQRCode(UnlimitedQRCodeRequest request) {
        String url = String.format(
                "https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=%s",
                accessTokenManager.getAccessToken()
        );
        return XHttp.postForBytes(url, null, XJson.serializeAsString(request));
    }
}
