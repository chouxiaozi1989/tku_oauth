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
public class FamilyInfo implements Serializable {

    private Integer familyId;

    private Integer userid;

    private String openid;

    private String familyName;


}
