package com.tku.tku_oauth.oauth.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
public class AccessToken {

    /**
     * token
     */
    private String token;
    /**
     * 失效时间
     */
    private Date expireTime;

    public AccessToken(String token, Date expireTime) {
        this.token = token;
        this.expireTime = expireTime;
    }
}