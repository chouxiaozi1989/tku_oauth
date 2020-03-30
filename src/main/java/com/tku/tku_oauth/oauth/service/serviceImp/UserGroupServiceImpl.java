package com.tku.tku_oauth.oauth.service.serviceImp;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tku.tku_oauth.oauth.mapper.UserGroupMapper;
import com.tku.tku_oauth.oauth.model.UserGroup;
import com.tku.tku_oauth.oauth.service.IUserGroupService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author CaiYi
 * @since 2020-03-31
 */
@Service
public class UserGroupServiceImpl extends ServiceImpl<UserGroupMapper, UserGroup> implements IUserGroupService {

}
