package com.tku.tku_oauth.oauth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tku.tku_oauth.oauth.model.UserInfo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

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
    @Select("select * from oauth.userinfo where userid=#{userid} and yxbz=#{yxbz}")
    UserInfo getByUserId(String userid, String yxbz);

    @Select("select * from oauth.userinfo where openid=#{openid} and yxbz=#{yxbz}")
    UserInfo getByOpenId(String openid, String yxbz);

    @Insert({"insert into oauth.userinfo(userid,username,yxbz) values(#{userid},#{username},'1')"})
    void addUser(String userid, String username);

    @Update({"update oauth.userinfo set openid=#{openid} where userid=#{userid}"})
    void UpdateOpenid(String userid, String openid);

    @Update({"update oauth.userinfo set gen=#{gen} where userid=#{userid}"})
    void UpdateGen(int gen, String userid);

    @Update({"update oauth.userinfo set country=#{country} where userid=#{userid}"})
    void UpdateCountry(String country, String userid);

    @Update({"update oauth.userinfo set province=#{province} where userid=#{userid}"})
    void UpdateProvince(String province, String userid);

    @Update({"update oauth.userinfo set city=#{city} where userid=#{userid}"})
    void UpdateCity(String city, String userid);

    @Update({"update oauth.userinfo set birthday=#{birthday} where userid=#{userid}"})
    void UpdateBirthday(Date birthday, String userid);

    @Update({"update oauth.userinfo set username=#{username} where userid=#{userid}"})
    void UpdateUserName(String username, String userid);

    @Update({"update oauth.userinfo set email=#{email} where userid=#{userid}"})
    void UpdateEmail(String email, String userid);

    @Update({"update oauth.userinfo set avatarurl=#{avatarUrl} where userid=#{userid}"})
    void UpdateAvatarUrl(String avatarUrl, String userid);

    @Delete("update oauth.userinfo set yxbz='0' where userid=#{userid}")
    void delete(String userid);
}
