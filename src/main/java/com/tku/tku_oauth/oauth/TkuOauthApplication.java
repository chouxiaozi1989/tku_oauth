package com.tku.tku_oauth.oauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@Configuration
public class TkuOauthApplication {

    public static void main(String[] args) {
        SpringApplication.run(TkuOauthApplication.class, args);
    }

}
