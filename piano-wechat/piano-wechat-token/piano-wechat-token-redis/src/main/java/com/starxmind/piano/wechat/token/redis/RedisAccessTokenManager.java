package com.starxmind.piano.wechat.token.redis;

import com.starxmind.bass.http.StarxHttp;
import com.starxmind.piano.wechat.token.core.AbstractAccessTokenManager;
import com.starxmind.piano.wechat.token.core.WeChatInfo;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;

/**
 * Redis型AccessToken管理器
 *
 * @author pizzalord
 * @since 1.0
 */
public class RedisAccessTokenManager extends AbstractAccessTokenManager {
    private static final String REDIS_KEY_ACCESS_TOKEN = "piano:wechat:access_token";
    private RedissonClient redissonClient;

    public RedisAccessTokenManager(StarxHttp StarxHttp, WeChatInfo weChatInfo, RedissonClient redissonClient) {
        super(StarxHttp, weChatInfo);
        this.redissonClient = redissonClient;
    }

    @Override
    protected boolean isAccessTokenInvalid() {
        RBucket<Object> bucket = redissonClient.getBucket(REDIS_KEY_ACCESS_TOKEN);
        if (!bucket.isExists()) {
            return true;
        }
        long remainTimeToLive = bucket.remainTimeToLive();
        // token生存时间小于请求时间，则重新获取
        return remainTimeToLive < super.ACCESS_TOKEN_REQUEST_TIME;
    }

    @Override
    protected void saveAccessToken(String accessToken) {
        RBucket<Object> bucket = redissonClient.getBucket(REDIS_KEY_ACCESS_TOKEN);
        bucket.set(accessToken);
    }

    @Override
    protected String getSavedAccessToken() {
        RBucket<Object> bucket = redissonClient.getBucket(REDIS_KEY_ACCESS_TOKEN);
        return bucket.get().toString();
    }
}