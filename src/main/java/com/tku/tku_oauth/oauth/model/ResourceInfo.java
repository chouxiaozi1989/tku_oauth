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
public class ResourceInfo implements Serializable {

    private String resourceId;

    private String resourceName;

    private String resourceUrl;

    private String resourceType;

    private String resourceCategory;

    private String resourceContent;

    public JSONObject toJSON() {
        JSONObject res = new JSONObject();
        res.put("resourceId", this.resourceId);
        res.put("resourceName", this.resourceName);
        res.put("resourceUrl", this.resourceUrl);
        res.put("resourceType", this.resourceType);
        res.put("resourceCategory", this.resourceCategory);
        res.put("resourceContent", this.resourceContent);
        return res;
    }
}
