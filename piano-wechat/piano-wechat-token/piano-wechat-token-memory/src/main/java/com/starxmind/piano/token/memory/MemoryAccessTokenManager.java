package com.starxmind.piano.token.memory;

import com.google.common.collect.Maps;
import com.starxmind.bass.http.XHttp;
import com.starxmind.piano.wechat.token.core.AccessTokenManager;
import com.starxmind.piano.wechat.token.core.WechatApp;

import java.util.List;
import java.util.Map;

/**
 * 内存型AccessToken管理器
 *
 * @author pizzalord
 * @since 1.0
 */
public class MemoryAccessTokenManager extends AccessTokenManager {
    private Map<String, String> accessTokenMap = Maps.newHashMap();
    private Map<String, Long> tokenCreateTimestampMap = Maps.newHashMap();

    public MemoryAccessTokenManager(List<WechatApp> wechatApps, XHttp XHttp) {
        super(wechatApps, XHttp);
    }

    @Override
    protected boolean isAccessTokenInvalid(String appId) {
        Long tokenCreateTimestamp = tokenCreateTimestampMap.get(appId);
        return tokenCreateTimestamp == null ||
                System.currentTimeMillis() - tokenCreateTimestamp > super.ACCESS_TOKEN_STORAGE_TIME;
    }

    @Override
    protected void saveAccessToken(String appId, String accessToken) {
        accessTokenMap.put(appId, accessToken);
        tokenCreateTimestampMap.put(appId, System.currentTimeMillis());
    }

    @Override
    protected String getSavedAccessToken(String appId) {
        return accessTokenMap.get(appId);
    }
}
