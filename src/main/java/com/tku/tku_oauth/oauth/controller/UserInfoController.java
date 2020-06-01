package com.tku.tku_oauth.oauth.controller;

import com.alibaba.fastjson.JSONObject;
import com.tku.tku_oauth.oauth.model.*;
import com.tku.tku_oauth.oauth.service.IUserInfoService;
import com.tku.tku_oauth.oauth.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.print.attribute.standard.RequestingUserName;
import java.util.UUID;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author CaiYi
 * @since 2020-03-30
 */
@RestController
@RequestMapping("/api/UserInfo")
public class UserInfoController {

    @Autowired
    private IUserInfoService userService;

    /*
     * 方法简介.生成openid
     *
     * @author HSG
     * @date 创建时间 2020/5/26
     * @since V1.0
    */
    @PostMapping("/getOpenidByCode")
    public JSONObject getOpenidByCode(@RequestBody JSONObject dataCode) {
        String code = dataCode.getString("code");
        JSONObject res = userService.jscode2session(code);
        return res;
    }

    /*
     * 方法简介.查询人员信息  By openid
     *
     * @author HSG
     * @date 创建时间 2020/5/26
     * @since V1.0
    */
    @PostMapping("/getUserInfoByOpenid")
    public JSONObject getUserInfoByOpenid(@RequestBody JSONObject dataOpenid) {
        String openid = dataOpenid.getString("openid");
        JSONObject jsonUser = userService.getUserByOpenId(openid);
        return jsonUser;
    }

    /*
     * 方法简介.新增人员
     *
     * @author HSG
     * @date 创建时间 2020/5/26
     * @since V1.0
    */
    @PostMapping("/addUserInfo")
    public JSONObject addUserInfo(@RequestBody JSONObject data) {
        JSONObject res = userService.addUserInfo(data);
        return res;
    }

    /*
     * 方法简介.更新人员
     *
     * @author HSG
     * @date 创建时间 2020/5/26
     * @since V1.0
    */
    @RequestMapping(value = "/updateUserInfo")
    public JSONObject updateUserInfo(@RequestBody JSONObject jsonObjectU) {
        JSONObject res = userService.updateUserInfo(jsonObjectU);
        return res;
    }
}