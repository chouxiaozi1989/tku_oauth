package com.tku.tku_oauth.oauth.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tku.tku_oauth.oauth.model.CollectHisInfo;
import com.tku.tku_oauth.oauth.model.ResourceInfo;

/*
 * 方法简介.
 *
 * @author HSG
 * @date 创建时间 2020/6/17
 * @since V1.0
*/
public interface IResourceInfoService extends IService<ResourceInfo> {

    //JSONObject getCollectHisByOpenid(String openid);

    JSONObject addResourceInfo(JSONObject jsonObject);

    JSONObject deleteResourceInfo(JSONObject jsonObject);
}
