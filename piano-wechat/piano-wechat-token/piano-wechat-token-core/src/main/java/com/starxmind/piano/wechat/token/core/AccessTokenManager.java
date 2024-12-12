package com.starxmind.piano.wechat.token.core;

import com.starxmind.bass.http.XHttp;
import com.starxmind.bass.sugar.Asserts;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.starxmind.bass.date.DateUtils.ONE_MINUTE;

/**
 * 微信 AccessToken 管理器
 *
 * @author pizzalord
 * @since 1.0
 */
public abstract class AccessTokenManager {
    // 有效时间: 微信是2个小时, 这里定义115分钟, 5分钟的时间用来屏蔽取Token的时间
    protected static final long ACCESS_TOKEN_STORAGE_MINUTES = 115;
    protected static final long ACCESS_TOKEN_STORAGE_TIME = 115 * ONE_MINUTE;

    protected final List<WechatApp> wechatApps;
    protected final XHttp xHttp;
    protected final Map<String, String> wechatMap;

    public AccessTokenManager(List<WechatApp> wechatApps, XHttp XHttp) {
        this.wechatApps = wechatApps;
        this.xHttp = XHttp;
        this.wechatMap = wechatApps.stream().collect(
                Collectors.toMap(WechatApp::getAppId, WechatApp::getSecret));
    }

    /**
     * 获取AccessToken
     *
     * @return 与微信服务交互所需的访问令牌
     */
    public String getAccessToken(String appId) {
        if (isAccessTokenInvalid(appId)) {
            String accessToken = fetchAccessToken(appId);
            saveAccessToken(appId, accessToken);
        }
        return getSavedAccessToken(appId);
    }

    /**
     * 判断AccessToken是否过期
     *
     * @return
     */
    protected abstract boolean isAccessTokenInvalid(String appId);

    private String fetchAccessToken(String appId) {
        String url = String.format("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s",
                appId, wechatMap.get(appId));
        AccessTokenResponse response = xHttp.getForObject(url, AccessTokenResponse.class);
        Asserts.isTrue(response.getErrcode() == 0, response.getErrmsg());
        String accessToken = response.getAccess_token();
        return accessToken;
    }

    /**
     * 保存AccessToken
     *
     * @param accessToken
     */
    protected abstract void saveAccessToken(String appId, String accessToken);

    /**
     * 获取保存的AccessToken
     *
     * @return
     */
    protected abstract String getSavedAccessToken(String appId);

    public String getSecret(String appId) {
        return wechatMap.get(appId);
    }

}
