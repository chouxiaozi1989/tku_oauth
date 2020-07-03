package com.tku.tku_oauth.oauth.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tku.tku_oauth.oauth.model.CollectHisInfo;

/*
 * 方法简介.
 *
 * @author HSG
 * @date 创建时间 2020/6/17
 * @since V1.0
*/
public interface ICollectHisService extends IService<CollectHisInfo> {

    JSONObject getCollectHisByOpenid(String openid);

    JSONObject addCollectionInfo(String resourceId,String openId);

    JSONObject deleteCollectionInfo(String resourceId,String openId);
}
