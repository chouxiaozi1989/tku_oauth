package com.tku.tku_oauth.oauth.model;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

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
public class AppInfo implements Serializable {

    private String appId;

    private String appKey;

    private String yxbz;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getYxbz() {
        return yxbz;
    }

    public void setYxbz(String yxbz) {
        this.yxbz = yxbz;
    }

    public String toJSONString() {
        JSONObject res = new JSONObject();
        res.put("appId", this.appId);
        res.put("appKey", this.appKey);
        res.put("yxbz", this.yxbz);
        return res.toJSONString();
    }
}
