package com.tku.tku_oauth.oauth.model;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/*
 * 方法简介.
 *
 * @author HSG
 * @date 创建时间 2020/6/17
 * @since V1.0
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class FamilyShareInfo implements Serializable {

    private String shareId;

    private String resourceName;

    private String resourceType;

    private String shareTime;

    private String resourceUrl;

    private String resourceId;

    public JSONObject toJSON() {
        JSONObject res = new JSONObject();
        res.put("shareId", this.shareId);
        res.put("resourceName", this.resourceName);
        res.put("resourceType", this.resourceType);
        res.put("shareTime", this.shareTime);
        res.put("resourceUrl", this.resourceUrl);
        res.put("resourceId", this.resourceId);
        return res;
    }
}
