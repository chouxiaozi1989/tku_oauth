package com.tku.tku_oauth.oauth.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class ApiResult {
    /**
     * 代码
     */
    private String code;
    /**
     * 结果
     */
    private String msg;

    public ApiResult(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}