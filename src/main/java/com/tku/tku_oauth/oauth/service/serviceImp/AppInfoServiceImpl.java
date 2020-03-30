package com.tku.tku_oauth.oauth.service.serviceImp;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tku.tku_oauth.oauth.mapper.AppInfoMapper;
import com.tku.tku_oauth.oauth.model.AppInfo;
import com.tku.tku_oauth.oauth.service.IAppInfoService;
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
public class AppInfoServiceImpl extends ServiceImpl<AppInfoMapper, AppInfo> implements IAppInfoService {

}
