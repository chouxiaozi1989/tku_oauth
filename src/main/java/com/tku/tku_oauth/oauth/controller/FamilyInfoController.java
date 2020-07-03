package com.tku.tku_oauth.oauth.controller;

import com.alibaba.fastjson.JSONObject;
import com.tku.tku_oauth.oauth.service.IFamilyInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 描述：
 *
 * @author HSG
 * @version 1.0
 * @date 2020/6/11 16:37
 */

@RestController
@RequestMapping("/family")
public class FamilyInfoController {

    @Autowired
    private IFamilyInfoService iFamilyInfoService;
    /*
     * 方法简介.根据用户openid查询家庭信息
     *
     * @author HSG
     * @date 创建时间 2020/6/11
     * @since V1.0
    */
    @PostMapping("/getFamilyInfoByOpenid")
    public JSONObject getFamilyInfoByOpenid(@RequestBody JSONObject data) {
        String openid = data.getString("openid");
        JSONObject res = iFamilyInfoService.getFamilyInfoByOpenid(openid);
        return res;
    }

    public JSONObject getFamilyInfoBySubOpenid(@RequestBody JSONObject data) {
        String subopenid = data.getString("subopenid");
        JSONObject res = iFamilyInfoService.getFamilyInfoBySubOpenid(subopenid);
        return res;
    }

    /*
     * 方法简介.添加家庭信息
     *
     * @author HSG
     * @date 创建时间 2020/6/20
     * @since V1.0
    */
    @PostMapping("/addFamilyInfo")
    public JSONObject addFamilyInfo(@RequestBody JSONObject data) {
        String openid = data.getString("openid");
        String subOpenid = data.getString("subOpenid");
        JSONObject res = iFamilyInfoService.addFamilyInfo(openid,subOpenid);
        return res;
    }

    /*
     * 方法简介.删除家庭信息
     *
     * @author HSG
     * @date 创建时间 2020/6/20
     * @since V1.0
    */
    @PostMapping("/deleteFamilyInfo")
    public JSONObject deleteFamilyInfo(@RequestBody JSONObject data) {
        String openid = data.getString("openid");
        String subOpenid = data.getString("subOpenid");
        JSONObject res = iFamilyInfoService.deleteFamilyInfo(openid,subOpenid);
        return res;
    }
}