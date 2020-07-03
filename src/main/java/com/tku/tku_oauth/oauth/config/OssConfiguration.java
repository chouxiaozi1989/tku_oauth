package com.tku.tku_oauth.oauth.config;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 描述：
 *
 * @author HSG
 * @version 1.0
 * @date 2020/6/1 10:29
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "aliyun")
public class OssConfiguration {
    private String endpoint;
    private String accessKeyId;
    private String accessKeySecret;
    private String bucketName;
    private String urlPrefix;
    private String filehost;

    @Bean
    public OSS oSSClient() {
        return new OSSClient(endpoint, accessKeyId, accessKeySecret);
    }
}

