package com.tku.tku_oauth.oauth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tku.tku_oauth.oauth.model.UserGroup;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

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
public interface UserGroupMapper extends BaseMapper<UserGroup> {

}
