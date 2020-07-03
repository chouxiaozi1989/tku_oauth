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
public class CollectHisInfo implements Serializable {

    private String collectId;

    private String resourceName;

    private String resourceType;

    private String collectTime;

    private String resourceUrl;

    private String resourceId;

    public JSONObject toJSON() {
        JSONObject res = new JSONObject();
        res.put("collectId", this.collectId);
        res.put("resourceName", this.resourceName);
        res.put("resourceType", this.resourceType);
        res.put("collectTime", this.collectTime);
        res.put("resourceUrl", this.resourceUrl);
        res.put("resourceId", this.resourceId);
        return res;
    }
}
