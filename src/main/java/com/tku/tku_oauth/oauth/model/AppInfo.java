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

    public String toJSONString() {
        JSONObject res = new JSONObject();
        res.put("appId", this.appId);
        res.put("appKey", this.appKey);
        res.put("yxbz", this.yxbz);
        return res.toJSONString();
    }
}
