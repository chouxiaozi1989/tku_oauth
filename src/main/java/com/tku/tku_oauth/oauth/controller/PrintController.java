package com.tku.tku_oauth.oauth.controller;

import com.alibaba.fastjson.JSONObject;
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
@RequestMapping("/print")
public class PrintController {

    @Autowired
    private PrintHisServiceImpl printHisService;
    /*
     * 方法简介.根据用户openid查询用户的打印记录
     *
     * @author HSG
     * @date 创建时间 2020/6/11
     * @since V1.0
    */
    @PostMapping("/getPrintHisByOpenid")
    public JSONObject getPrintHisByOpenid(@RequestBody JSONObject data) {
        String openid = data.getString("openid");
        JSONObject res = printHisService.getPrintHisByOpenid(openid);
        return res;
    }
}