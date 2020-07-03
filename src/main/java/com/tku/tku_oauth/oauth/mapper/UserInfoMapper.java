package com.tku.tku_oauth.oauth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tku.tku_oauth.oauth.model.UserInfo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author CaiYi
 * @since 2020-03-31
 */
@Mapper
@Repository
public interface UserInfoMapper extends BaseMapper<UserInfo> {
    @Select("select * from oauth.user_info where user_id=#{user_id} and yxbz=#{yxbz}")
    UserInfo getByUserId(String user_id, String yxbz);

    @Select("select * from oauth.user_info where openid=#{openid} and yxbz=#{yxbz}")
    UserInfo getByOpenId(String openid, String yxbz);

    @Transactional(propagation = Propagation.REQUIRED)
    @Insert({"insert into oauth.user_info(user_id,user_name,yxbz) values(#{user_id},#{user_name},'1')"})
    void addUser(String user_id, String username);

    @Transactional(propagation = Propagation.REQUIRED)
    @Update({"update oauth.user_info set openid=#{openid} where user_id=#{user_id}"})
    void UpdateOpenid(String user_id, String openid);

    @Transactional(propagation = Propagation.REQUIRED)
    @Update({"update oauth.user_info set gen=#{gen} where user_id=#{user_id}"})
    void UpdateGen(int gen, String user_id);

    @Transactional(propagation = Propagation.REQUIRED)
    @Update({"update oauth.user_info set gen=#{gen} where user_id=#{user_id}"})
    void UpdateGrade(String grade, String user_id);

    @Transactional(propagation = Propagation.REQUIRED)
    @Update({"update oauth.user_info set country=#{country} where user_id=#{user_id}"})
    void UpdateCountry(String country, String user_id);

    @Transactional(propagation = Propagation.REQUIRED)
    @Update({"update oauth.user_info set province=#{province} where user_id=#{user_id}"})
    void UpdateProvince(String province, String user_id);

    @Transactional(propagation = Propagation.REQUIRED)
    @Update({"update oauth.user_info set city=#{city} where user_id=#{user_id}"})
    void UpdateCity(String city, String user_id);

    @Transactional(propagation = Propagation.REQUIRED)
    @Update({"update oauth.user_info set birthday=#{birthday} where user_id=#{user_id}"})
    void UpdateBirthday(String birthday, String user_id);

    @Transactional(propagation = Propagation.REQUIRED)
    @Update({"update oauth.user_info set user_name=#{user_name} where user_id=#{user_id}"})
    void UpdateUserName(String user_name, String user_id);

    @Transactional(propagation = Propagation.REQUIRED)
    @Update({"update oauth.user_info set email=#{email} where user_id=#{user_id}"})
    void UpdateEmail(String email, String user_id);

    @Transactional(propagation = Propagation.REQUIRED)
    @Update({"update oauth.user_info set avatarUrl=#{avatarUrl} where user_id=#{user_id}"})
    void UpdateAvatarUrl(String avatarUrl, String user_id);

    @Transactional(propagation = Propagation.REQUIRED)
    @Delete("update oauth.user_info set yxbz='0' where user_id=#{user_id}")
    void delete(String user_id);

    @Transactional(propagation = Propagation.REQUIRED)
    @Insert({"insert into oauth.user_info(openid,user_id,user_name,gen,birthday,city,grade,yxbz,avatarUrl,country, province)" +
            " values(#{openid},#{user_id},#{userName},#{gen},#{birthday},#{city},#{grade},'1',#{avatarUrl},#{country},#{province})"})
    void addUserInfo(String openid, String user_id, String userName, String gen, String birthday, String city, String grade,String avatarUrl,String country,String province );

    @Transactional(propagation = Propagation.REQUIRED)
    @Update({"update oauth.user_info set user_name=#{userName},gen=#{gen},country=#{country},province=#{province} where openid=#{openid}"})
    void updateUserInfo(String openid, String userName, String gen,String country,String province);

}
