package com.tku.tku_oauth.oauth.controller;

import com.alibaba.fastjson.JSONObject;
import com.tku.tku_oauth.oauth.service.ICollectHisService;
import com.tku.tku_oauth.oauth.service.IFamilyShareService;
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
@RequestMapping("/familyshare")
public class FamilyShareController {

    @Autowired
    private IFamilyShareService iFamilyShareService;
    /*
     * 方法简介.根据用户openid查询家庭共享
     *
     * @author HSG
     * @date 创建时间 2020/6/11
     * @since V1.0
    */
    @PostMapping("/getFamilyShareInfoByOpenid")
    public JSONObject getFamilyShareInfoByOpenid(@RequestBody JSONObject data) {
        String openid = data.getString("openid");
        JSONObject res = iFamilyShareService.getFamilyShareInfoByOpenid(openid);
        return res;
    }

    /*
     * 方法简介.添加家庭共享
     *
     * @author HSG
     * @date 创建时间 2020/6/20
     * @since V1.0
    */
    @PostMapping("/addFamilyShareInfo")
    public JSONObject addFamilyShareInfo(@RequestBody JSONObject data) {
        String openid = data.getString("openid");
        String resourceId = data.getString("resourceId");
        JSONObject res = iFamilyShareService.addFamilyShareInfo(openid,resourceId);
        return res;
    }

    /*
     * 方法简介.删除家庭共享信息
     *
     * @author HSG
     * @date 创建时间 2020/6/20
     * @since V1.0
    */
    @PostMapping("/deleteFamilyShareInfo")
    public JSONObject deleteFamilyShareInfo(@RequestBody JSONObject data) {
        String openid = data.getString("openid");
        String resourceId = data.getString("resourceId");
        JSONObject res = iFamilyShareService.deleteFamilyShareInfo(openid,resourceId);
        return res;
    }
}