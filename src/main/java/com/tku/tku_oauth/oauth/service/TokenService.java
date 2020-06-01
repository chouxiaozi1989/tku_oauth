package com.tku.tku_oauth.oauth.service;

import com.tku.tku_oauth.oauth.model.AccessToken;
import com.tku.tku_oauth.oauth.model.AppInfo;
import com.tku.tku_oauth.oauth.model.TokenInfo;
import com.tku.tku_oauth.oauth.model.UserInfo;
import org.springframework.stereotype.Service;

@Service
public interface TokenService {
    public AccessToken saveToken(int tokenType, AppInfo appInfo, UserInfo userInfo);

    public AccessToken saveToken(int tokenType, UserInfo userInfo);

    public TokenInfo getTokenInfo(String token);
}