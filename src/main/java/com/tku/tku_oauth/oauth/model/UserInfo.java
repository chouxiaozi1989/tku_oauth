package com.tku.tku_oauth.oauth.model;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * <p>
 *
 * </p>
 *
 * @author CaiYi
 * @since 2020-03-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class UserInfo implements Serializable {

    private String userId;

    private String openid;

    private String gen;

    private String country;

    private String province;

    private String city;

    private String password;

    private LocalDate birthday;

    private String yxbz;

    private String email;

    private String userName;

    @TableField("avatarUrl")
    private String avatarUrl;

    private String grade;

    private AccessToken accessToken;

    public JSONObject toJSON() {
        JSONObject res = new JSONObject();
        res.put("userId", this.userId);
        res.put("openid", this.openid);
        res.put("gen", this.gen);
        res.put("country", this.country);
        res.put("province", this.province);
        res.put("city", this.city);
        res.put("password", this.password);
        res.put("birthday", this.birthday);
        res.put("userName", this.userName);
        res.put("email", this.email);
        res.put("avatarUrl", this.avatarUrl);
        res.put("grade", this.grade);
        return res;
    }
}
