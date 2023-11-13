package com.starxmind.piano.token.memory;

import com.starxmind.bass.http.StarxHttp;
import com.starxmind.piano.wechat.token.core.AccessTokenManager;
import com.starxmind.piano.wechat.token.core.WeChatInfo;

/**
 * 内存型AccessToken管理器
 *
 * @author pizzalord
 * @since 1.0
 */
public class MemoryAccessTokenManager extends AccessTokenManager {
    private String accessToken;
    private Long tokenCreateTimestamp;

    public MemoryAccessTokenManager(WeChatInfo weChatInfo, StarxHttp starxHttp) {
        super(weChatInfo, starxHttp);
    }

    @Override
    protected boolean isAccessTokenInvalid() {
        return tokenCreateTimestamp == null ||
                System.currentTimeMillis() - tokenCreateTimestamp > super.ACCESS_TOKEN_STORAGE_TIME;
    }

    @Override
    protected void saveAccessToken(String accessToken) {
        this.accessToken = accessToken;
        this.tokenCreateTimestamp = System.currentTimeMillis();
    }

    @Override
    protected String getSavedAccessToken() {
        return this.accessToken;
    }
}
