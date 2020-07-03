package com.tku.tku_oauth.oauth.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tku.tku_oauth.oauth.model.PrintHisInfo;

/*
 * 方法简介.
 *
 * @author HSG
 * @date 创建时间 2020/6/17
 * @since V1.0
*/
public interface IPrintHisService extends IService<PrintHisInfo> {

    JSONObject getPrintHisByOpenid(String openid);
}
