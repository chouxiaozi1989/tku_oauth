package com.tku.tku_oauth.oauth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tku.tku_oauth.oauth.model.CollectHisInfo;
import com.tku.tku_oauth.oauth.model.PrintHisInfo;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
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
public interface CollectHisMapper extends BaseMapper<CollectHisInfo> {

    @Select("select a.collectId,a.collectTime,b.resourceId,b" +
            ".resourceName,b.resourceUrl,b.resourceType " +
            "from oauth.collect_history a,oauth.resource_info b " +
            "where a.resourceId = b.resourceId and a.openId =#{openid} ")
    ArrayList<CollectHisInfo> getCollectHisByOpenid(String openid);


    @Transactional(propagation = Propagation.REQUIRED)
    @Insert({"insert into oauth.collect_history (collectId,collectTime,resourceId,openId) " +
            "values(#{collectId},#{collectTime},#{resourceId},#{openId})"})
    void addCollectionInfo(String collectId,String collectTime,String resourceId,String openId);

    @Transactional(propagation = Propagation.REQUIRED)
    @Delete({"delete from oauth.collect_history where resourceId = #{resourceId} and openId= #{openId}"})
    void deleteCollectionInfo(String resourceId,String openId);
}