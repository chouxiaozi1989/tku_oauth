package com.tku.tku_oauth.oauth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tku.tku_oauth.oauth.model.AppInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

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
public interface AppInfoMapper extends BaseMapper<AppInfo> {
    @Select("select * from oauth.app_info where app_id=#{appid} and app_key=#{key} and yxbz='1'")
    @Results({@Result(column = "app_id", property = "appId"),
            @Result(column = "app_key", property = "appKey"),
            @Result(column = "yxbz", property = "yxbz")})
    AppInfo getByAppidAndKey(String appid, String key);
}
