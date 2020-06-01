package com.tku.tku_oauth.oauth.service.serviceImp;

import com.tku.tku_oauth.oauth.model.AccessToken;
import com.tku.tku_oauth.oauth.model.AppInfo;
import com.tku.tku_oauth.oauth.model.TokenInfo;
import com.tku.tku_oauth.oauth.model.UserInfo;
import com.tku.tku_oauth.oauth.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Component
public class TokenServiceImpl implements TokenService {
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public AccessToken saveToken(int tokenType, AppInfo appInfo, UserInfo userInfo) {
        String token = UUID.randomUUID().toString();
        // token有效期为2小时
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.SECOND, 7200);
        Date expireTime = calendar.getTime();
        // 4. 保存token
        ValueOperations<String, TokenInfo> operations = redisTemplate.opsForValue();
        TokenInfo tokenInfo = new TokenInfo();
        tokenInfo.setTokenType(tokenType);
        tokenInfo.setAppInfo(appInfo);
        if (tokenType == 1) {
            tokenInfo.setUserInfo(userInfo);
        }
        operations.set(token, tokenInfo, 7200, TimeUnit.SECONDS);
        return new AccessToken(token, expireTime);
    }

    @Override
    public TokenInfo getTokenInfo(String token) {
        ValueOperations<String, TokenInfo> operations = redisTemplate.opsForValue();
        return operations.get(token);
    }

    @Override
    public AccessToken saveToken(int tokenType, UserInfo userInfo) {
        String token = UUID.randomUUID().toString();
        // token有效期为2小时
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.SECOND, 7200);
        Date expireTime = calendar.getTime();
        // 4. 保存token
        ValueOperations<String, TokenInfo> operations = redisTemplate.opsForValue();
        TokenInfo tokenInfo = new TokenInfo();
        tokenInfo.setTokenType(tokenType);
        if (tokenType == 1) {
            tokenInfo.setUserInfo(userInfo);
        }
        operations.set(token, tokenInfo, 7200, TimeUnit.SECONDS);
        return new AccessToken(token, expireTime);
    }
}
