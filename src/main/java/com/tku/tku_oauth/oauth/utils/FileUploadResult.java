package com.tku.tku_oauth.oauth.utils;

import lombok.Data;

/**
 * 描述：
 *
 * @author HSG
 * @version 1.0
 * @date 2020/6/8 9:33
 */
@Data
public class FileUploadResult {
    // 文件唯一标识
    private String uid;
    // 文件名
    private String name;
    // 状态有：uploading done error removed
    private String status;
    // 服务端响应内容，如：'{"status": "success"}'
    private String response;
}