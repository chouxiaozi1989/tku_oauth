package com.tku.tku_oauth.oauth.controller;

import com.alibaba.fastjson.JSONObject;
import com.tku.tku_oauth.oauth.service.ICollectHisService;
import com.tku.tku_oauth.oauth.service.serviceImp.PrintHisServiceImpl;
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
    @RequestMapping("/collect")
public class CollectController {

    @Autowired
    private ICollectHisService collectHisService;
    /*
     * 方法简介.根据用户openid查询用户的收藏记录
     *
     * @author HSG
     * @date 创建时间 2020/6/11
     * @since V1.0
    */
    @PostMapping("/getCollectHisByOpenid")
    public JSONObject getCollectHisByOpenid(@RequestBody JSONObject data) {
        String openid = data.getString("openid");
        JSONObject res = collectHisService.getCollectHisByOpenid(openid);
        return res;
    }

    /*
     * 方法简介.添加收藏
     *
     * @author HSG
     * @date 创建时间 2020/6/20
     * @since V1.0
    */
    @PostMapping("/addCollectionInfo")
    public JSONObject addCollectionInfo(@RequestBody JSONObject data) {
        String openid = data.getString("openid");
        String resourceId = data.getString("resourceId");
        JSONObject res = collectHisService.addCollectionInfo(openid,resourceId);
        return res;
    }

    /*
     * 方法简介.删除用户收藏信息
     *
     * @author HSG
     * @date 创建时间 2020/6/20
     * @since V1.0
    */
    @PostMapping("/deleteCollectionInfo")
    public JSONObject deleteCollectionInfo(@RequestBody JSONObject data) {
        String openid = data.getString("openid");
        String resourceId = data.getString("resourceId");
        JSONObject res = collectHisService.deleteCollectionInfo(openid,resourceId);
        return res;
    }
}