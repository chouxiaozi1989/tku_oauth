package com.tku.tku_oauth.oauth.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tku.tku_oauth.oauth.model.FamilyInfo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author CaiYi
 * @since 2020-03-31
 */
public interface IFamilyInfoService extends IService<FamilyInfo> {
    JSONObject getFamilyInfoByOpenid(String openId);

    JSONObject getFamilyInfoBySubOpenid(String subopenId);

    JSONObject addFamilyInfo(String openId, String subOpenid);

    JSONObject deleteFamilyInfo(String openId, String subOpenid);
}
