package com.tku.tku_oauth.oauth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tku.tku_oauth.oauth.model.FamilyShareInfo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

/**
 * 描述：
 *
 * @author HSG
 * @version 1.0
 * @date 2020/6/17 19:03
 */
@Mapper
@Repository
public interface FamilyShareMapper extends BaseMapper<FamilyShareInfo> {

    @Select("select a.collectId,a.collectTime,b.resourceId,b" +
            ".resourceName,b.resourceUrl,b.resourceType " +
            "from oauth.collect_history a,oauth.resource_info b " +
            "where a.resourceId = b.resourceId and a.openId =#{openid} ")
    @Results({@Result(column = "app_id", property = "appId"),
            @Result(column = "app_key", property = "appKey"),
            @Result(column = "yxbz", property = "yxbz")})
    ArrayList<FamilyShareInfo> getFamilyShareInfoByOpenid(String openid);


    @Transactional(propagation = Propagation.REQUIRED)
    @Insert({"insert into oauth.family_Share (shareId,shareTime,resourceId,openId) " +
            "values(#{shareId},#{shareTime},#{resourceId},#{openId})"})
    void addFamilyShareInfo(String shareId, String shareTime, String resourceId, String openId);

    @Transactional(propagation = Propagation.REQUIRED)
    @Delete({"delete from oauth.family_Share where resourceId = #{resourceId} and openId= #{openId}"})
    void deleteFamilyShareInfo(String resourceId, String openId);
}