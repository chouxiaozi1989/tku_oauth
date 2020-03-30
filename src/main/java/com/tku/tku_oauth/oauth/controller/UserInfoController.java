package com.tku.tku_oauth.oauth.controller;


import com.alibaba.fastjson.JSONObject;
import com.tku.tku_oauth.oauth.model.*;
import com.tku.tku_oauth.oauth.service.IUserInfoService;
import com.tku.tku_oauth.oauth.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author CaiYi
 * @since 2020-03-30
 */
@Controller
@RequestMapping("/api/UserInfo")
public class UserInfoController {
    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private IUserInfoService userService;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/wxlogin")
    public ApiResponse wxlogin(String code, @RequestHeader("token") String token) {
        JSONObject res = userService.jscode2session(code);
        if (res.getBoolean("isBind")) {//已经绑定,生成token并返回登录
            UserInfo UserInfo = (UserInfo) res.get("UserInfo");
            Assert.notNull(UserInfo, "获取用户出错");
            // 2. 保存Token
            AppInfo appInfo = tokenService.getTokenInfo(token).getAppInfo();//从api_token信息中获取app信息
            AccessToken accessToken = tokenService.saveToken(1, appInfo, UserInfo);
            UserInfo.setAccessToken(accessToken);
            res.put("UserInfo", UserInfo);
            return ApiResponse.success(res);
        } else {//没有绑定
            res.put("isLogin", false);
            return ApiResponse.success(res);
        }
    }

    @PostMapping("/localAccountLogin")
    public ApiResponse localAccountLogin(@RequestBody JSONObject data, @RequestHeader("token") String token) {
        UserInfo user = userService.login(data.getString("userid"), data.getString("password"));
        JSONObject res = new JSONObject();
        if (user != null) {
            //判断是否已经绑定
            if (null != user.getOpenid() && !"".equals(user.getOpenid())) {//已经绑定
                user.setPassword("");//清除密码字段
                AppInfo appInfo = tokenService.getTokenInfo(token).getAppInfo();//从api_token信息中获取app信息
                AccessToken accessToken = tokenService.saveToken(1, appInfo, user);
                user.setAccessToken(accessToken);

                res.put("isLogin", true);
                res.put("UserInfo", user);
            } else {
                res.put("isLogin", false);
                res.put("isBind", false);
            }
        } else {
            res.put("isLogin", false);
            res.put("errMsg", "Invalid Password or Account");
        }
        return ApiResponse.success(res);
    }

    @PostMapping("/oauth")
    public ApiResponse oauth(@RequestBody JSONObject userObj, @RequestHeader("token") String token) {
        String userid = userObj.getString("userid");
        String password = userObj.getString("password");
        String openid = userObj.getString("openid");
        UserInfo user = userService.oauth(userid, password, openid, JSONObject.parseObject(userObj.getString("UserInfo")));
        Assert.notNull(user, "获取用户出错");
        JSONObject res = new JSONObject();
        if (user != null) {
            AppInfo appInfo = tokenService.getTokenInfo(token).getAppInfo();//从api_token信息中获取app信息
            AccessToken accessToken = tokenService.saveToken(1, appInfo, user);
            user.setAccessToken(accessToken);

            res.put("isLogin", true);
            res.put("UserInfo", user);
        } else {
            res.put("isLogin", false);
        }
        return ApiResponse.success(res);
    }

    @GetMapping("/getUserByToken")
    public ApiResponse<UserInfo> getUserByToken(@RequestHeader("token") String token) {
        ValueOperations<String, TokenInfo> tokenRedis = redisTemplate.opsForValue();
        TokenInfo tokenInfo = tokenRedis.get(token);
        Assert.notNull(tokenInfo, "token错误");
        UserInfo user = tokenInfo.getUserInfo();
        Assert.notNull(user, "查询用户信息出错");
        return ApiResponse.success(user);
    }
}