package com.tku.tku_oauth.oauth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tku.tku_oauth.oauth.model.FamilyInfo;
import com.tku.tku_oauth.oauth.model.FamilyShareInfo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author CaiYi
 * @since 2020-03-31
 */
@Mapper
@Repository
public interface FamilyInfoMapper extends BaseMapper<FamilyInfo> {
    @Select("select\n" +
            "\tu1.openid,\n" +
            "\tu1.user_name as username,\n" +
            "\tu1.avatarurl as avatar \n" +
            "from\n" +
            "\t(\n" +
            "select\n" +
            "\tf2.subopenid as openid \n" +
            "from\n" +
            "\t(\n" +
            "select\n" +
            "\t@subopenid as p_subopenid,\n" +
            "\t( select @subopenid := group_concat( subopenid ) from family_info where find_in_set( openid, @subopenid ) ) as c_ids,\n" +
            "\t@l := @l + 1 as level \n" +
            "from\n" +
            "\tfamily_info,\n" +
            "\t( select @subopenid := #{openId}, @l := 0 ) b \n" +
            "\t\n" +
            "where\n" +
            "\t@subopenid is not null \n" +
            "\t) f1\n" +
            "\tjoin family_info f2 on find_in_set( f2.subopenid, f1.p_subopenid ) \n" +
            "\t) f3,\n" +
            "\tuser_info u1 \n" +
            "where\n" +
            "\tf3.openid = u1.openid " +
            "union all\n" +
            "SELECT\n" +
            "\tu2.openid,\n" +
            "\tu2.user_name AS userName,\n" +
            "\tu2.avatarUrl AS avatar \n" +
            "from\n" +
            "family_info f3 ,user_info u2 where f3.openid = u2.openid and f3.openid= #{openId} and f3.subopenid =''\n" +
            "order by openid")
    ArrayList<FamilyInfo> getFamilyInfoByOpenid(String openId);

    @Select("select b.openid from oauth.family_info b " +
            "where b.subOpenid = #{subopenId} ")
    ArrayList<FamilyInfo> getFamilyInfoBySubOpenid(String subopenId);


    @Transactional(propagation = Propagation.REQUIRED)
    @Insert({"insert into oauth.family_info (openId,subOpenid) " +
            "values(#{openId},#{subOpenid})"})
    void addFamilyInfo(String openId, String subOpenid);

    @Transactional(propagation = Propagation.REQUIRED)
    @Delete({"delete from oauth.family_info where openId = #{openId} and subOpenid= #{subOpenid}"})
    void deleteFamilyInfo(String openId, String subOpenid);
}
