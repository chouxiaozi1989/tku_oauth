package com.tku.tku_oauth.oauth.controller;

import com.alibaba.fastjson.JSONObject;
import com.tku.tku_oauth.oauth.mapper.AppInfoMapper;
import com.tku.tku_oauth.oauth.model.AccessToken;
import com.tku.tku_oauth.oauth.model.ApiResponse;
import com.tku.tku_oauth.oauth.model.AppInfo;
import com.tku.tku_oauth.oauth.model.UserInfo;
import com.tku.tku_oauth.oauth.service.IUserInfoService;
import com.tku.tku_oauth.oauth.service.TokenService;
import com.tku.tku_oauth.oauth.utils.MD5Util;
import com.tku.tku_oauth.oauth.utils.NotRepeatSubmit;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@SuppressWarnings("ALL")
@Slf4j
@RestController
@RequestMapping("/api/token")
@Api(tags = "token获取相关接口")
public class TokenController {
    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private AppInfoMapper appInfoMapper;

    @Autowired
    private IUserInfoService userinfoService;

    @Autowired
    private TokenService tokenService;

    /**
     * API Token
     *
     * @param sign
     * @return
     */
    @PostMapping("/api_token")
    @ApiOperation("获取api token的接口")
    public ApiResponse apiToken(@RequestHeader("appid") String appId, @RequestHeader("key") String key, @RequestHeader("timestamp") String timestamp, @RequestHeader("sign") String sign) {
        Assert.isTrue(!StringUtils.isEmpty(appId) && !StringUtils.isEmpty(timestamp) && !StringUtils.isEmpty(sign), "参数错误");
        long requestInterval = System.currentTimeMillis() - Long.parseLong(timestamp);
        Assert.isTrue(requestInterval < 5 * 60 * 1000, "请求过期，请重新请求");
        // 1. 根据appId查询数据库获取appSecret
        AppInfo appInfo = appInfoMapper.getByAppidAndKey(appId, key);
        Assert.notNull(appInfo, "app认证失败！");
        // 2. 校验签名
        String signString = timestamp + appInfo.getAppId() + appInfo.getAppKey();
        String signature = MD5Util.encode(signString);
//        log.info(signature);
        Assert.isTrue(signature.equals(sign), "签名错误");
        // 3. 如果正确生成一个token保存到redis中，如果错误返回错误信息
        AccessToken accessToken = tokenService.saveToken(0, appInfo, null);
        return ApiResponse.success(accessToken);
    }

    @NotRepeatSubmit(30000)
    @PostMapping("/user_token")
    public ApiResponse<UserInfo> userToken(@RequestBody JSONObject data, @RequestHeader("token") String token) {
        // 1. 根据用户名查询密码, 并比较密码(密码可以RSA加密一下)
        String userid = data.getString("userid");
        String password = data.getString("password");
        UserInfo userInfo = userinfoService.getUserById(userid);
        Assert.notNull(userInfo, "获取用户出错");
        Assert.isTrue(password.equals(userInfo.getPassword()), "密码错误");
        // 2. 保存Token
        AppInfo appInfo = tokenService.getTokenInfo(token).getAppInfo();//从api_token信息中获取app信息
        AccessToken accessToken = tokenService.saveToken(1, appInfo, userInfo);
        userInfo.setAccessToken(accessToken);
        return ApiResponse.success(userInfo);
    }

    public static void main(String[] args) {
        long timestamp = System.currentTimeMillis();
        System.out.println(timestamp);
        String signString = timestamp + "question_repository" + "sa";
        String sign = MD5Util.encode(signString);
        System.out.println(sign);
        System.out.println("-------------------");
//        signString = "123" + "de46dd3a-9ef9-4af7-b1c3-00e840e12a21" + timestamp + "A1scr6";
//        JSONObject p = new JSONObject(true);
//        p.put("userid", "CY");
//        p.put("password", "sa");
//        signString = p.toJSONString() + "&sa" + "a14746ec-1092-47a1-bad3-7ff54dbaa41c" + timestamp + "A1scr6";
        signString = "password=sa&userid=CY&sa" + "3d545600-4438-434a-b5e8-89e2a5f23bb9" + timestamp + "A1scr6";
        sign = MD5Util.encode(signString);
        System.out.println(sign);
    }
}