package com.tku.tku_oauth.oauth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tku.tku_oauth.oauth.model.CollectHisInfo;
import com.tku.tku_oauth.oauth.model.ResourceInfo;
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
public interface ResourceInfoMapper extends BaseMapper<ResourceInfo> {

    @Select("select a.collectId,a.collectTime,b.resourceId,b" +
            ".resourceName,b.resourceUrl,b.resourceType " +
            "from oauth.collect_history a,oauth.resource_info b " +
            "where a.resourceId = b.resourceId and a.openId =#{openid} ")
    //ArrayList<CollectHisInfo> getCollectHisByOpenid(String openid);


    @Transactional(propagation = Propagation.REQUIRED)
    @Insert({"insert into oauth.resource_info (resourceId,resourceName,resourceUrl,resourceType,resourceCategory,resourceContent) \n" +
            "            values(#{resourceId},#{resourceName},#{resourceUrl},#{resourceType},#{resourceCategory},#{resourceContent})"})
    void addResourceInfo(String resourceId ,String resourceName ,String resourceUrl ,String resourceType ,String resourceCategory ,String resourceContent);

    @Transactional(propagation = Propagation.REQUIRED)
    @Delete({"delete from oauth.resource_info where resourceId = #{resourceId}"})
    void deleteResourceInfo(String resourceId);
}