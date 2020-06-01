package com.tku.tku_oauth.oauth.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tku.tku_oauth.oauth.model.UserInfo;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author CaiYi
 * @since 2020-03-30
 */
public interface IUserInfoService extends IService<UserInfo> {
    UserInfo getUserById(String userid);

    JSONObject getUserByOpenId(String openid);

    UserInfo login(String userid, String password);

    void UpdateOpenid(String userid, String openid);

    JSONObject jscode2session(String code);

    UserInfo oauth(String userid, String password, String openid, JSONObject userinfo);

    void addUser(String userid,String userName );

    JSONObject addUserInfo(JSONObject jsonUser);

    JSONObject updateUserInfo(JSONObject jsonUser);
}
