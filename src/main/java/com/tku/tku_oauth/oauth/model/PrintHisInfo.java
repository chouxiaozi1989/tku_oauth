package com.tku.tku_oauth.oauth.model;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

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
public class PrintHisInfo implements Serializable {

    private String historyId;

    private String resourceName;

    private String resourceType;

    private String printTime;

    private String resourceUrl;

    private String resourceId;

    public JSONObject toJSON() {
        JSONObject res = new JSONObject();
        res.put("historyId", this.historyId);
        res.put("resourceName", this.resourceName);
        res.put("resourceType", this.resourceType);
        res.put("printTime", this.printTime);
        res.put("resourceUrl", this.resourceUrl);
        res.put("resourceId", this.resourceId);
        return res;
    }
}
