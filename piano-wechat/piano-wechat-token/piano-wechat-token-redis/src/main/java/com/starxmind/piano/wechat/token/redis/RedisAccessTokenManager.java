package com.starxmind.piano.wechat.token.redis;

import com.starxmind.bass.http.XHttp;
import com.starxmind.piano.wechat.token.core.AccessTokenManager;
import com.starxmind.piano.wechat.token.core.WechatApp;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Redis型AccessToken管理器
 *
 * @author pizzalord
 * @since 1.0
 */
public class RedisAccessTokenManager extends AccessTokenManager {
    private static final String REDIS_KEY_ACCESS_TOKEN = "piano:wechat:access_token:";
    private final RedissonClient redissonClient;

    public RedisAccessTokenManager(List<WechatApp> wechatApps, XHttp XHttp, RedissonClient redissonClient) {
        super(wechatApps, XHttp);
        this.redissonClient = redissonClient;
    }

    @Override
    protected boolean isAccessTokenInvalid(String appId) {
        RBucket<Object> bucket = redissonClient.getBucket(getKey(appId));
        return !bucket.isExists();
    }

    @Override
    protected void saveAccessToken(String appId, String accessToken) {
        RBucket<Object> bucket = redissonClient.getBucket(getKey(appId));
        bucket.set(accessToken, super.ACCESS_TOKEN_STORAGE_MINUTES, TimeUnit.MINUTES);
    }

    @Override
    protected String getSavedAccessToken(String appId) {
        RBucket<Object> bucket = redissonClient.getBucket(getKey(appId));
        return bucket.get().toString();
    }

    private String getKey(String appId) {
        return REDIS_KEY_ACCESS_TOKEN + appId;
    }

}
