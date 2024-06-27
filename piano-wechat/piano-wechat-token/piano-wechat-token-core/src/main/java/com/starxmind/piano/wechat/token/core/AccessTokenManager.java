package com.starxmind.piano.wechat.token.core;

import com.starxmind.bass.date.DateUtils;
import com.starxmind.bass.http.XHttp;
import com.starxmind.bass.sugar.Asserts;
import lombok.RequiredArgsConstructor;

/**
 * 微信 AccessToken 管理器
 *
 * @author pizzalord
 * @since 1.0
 */
@RequiredArgsConstructor
public abstract class AccessTokenManager {
    protected static final long ACCESS_TOKEN_EXPIRATION_TIME = 2 * DateUtils.ONE_HOUR;
    protected static final long ACCESS_TOKEN_REQUEST_TIME = 5 * DateUtils.ONE_MINUTE;
    // 有效时间: 微信是2个小时, 这里定义115分钟, 5分钟的时间用来屏蔽取Token的时间
    protected static final long ACCESS_TOKEN_STORAGE_TIME = ACCESS_TOKEN_EXPIRATION_TIME - ACCESS_TOKEN_REQUEST_TIME;

    private final WeChatInfo weChatInfo;
    private final XHttp XHttp;

    /**
     * 获取AccessToken
     *
     * @return 与微信服务交互所需的访问令牌
     */
    public String getAccessToken() {
        if (isAccessTokenInvalid()) {
            String accessToken = fetchAccessToken();
            saveAccessToken(accessToken);
        }
        return getSavedAccessToken();
    }

    /**
     * 判断AccessToken是否过期
     *
     * @return
     */
    protected abstract boolean isAccessTokenInvalid();

    private String fetchAccessToken() {
        String url = String.format("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s",
                weChatInfo.getAppId(), weChatInfo.getSecret());
        AccessTokenResponse response = XHttp.getForObject(url, AccessTokenResponse.class);
        Asserts.isTrue(response.getErrcode() == 0, response.getErrmsg());
        String accessToken = response.getAccess_token();
        return accessToken;
    }

    /**
     * 保存AccessToken
     *
     * @param accessToken
     */
    protected abstract void saveAccessToken(String accessToken);

    /**
     * 获取保存的AccessToken
     *
     * @return
     */
    protected abstract String getSavedAccessToken();
}
