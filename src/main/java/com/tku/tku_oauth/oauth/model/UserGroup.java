package com.tku.tku_oauth.oauth.model;

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
public class UserGroup implements Serializable {

    private Integer gourpId;

    private String groupName;

    private Integer userId;

    private String openid;


}
