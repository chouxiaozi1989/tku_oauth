package com.tku.tku_oauth.oauth.service.serviceImp;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tku.tku_oauth.oauth.mapper.FamilyInfoMapper;
import com.tku.tku_oauth.oauth.model.FamilyInfo;
import com.tku.tku_oauth.oauth.service.IFamilyInfoService;
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
public class FamilyInfoServiceImpl extends ServiceImpl<FamilyInfoMapper, FamilyInfo> implements IFamilyInfoService {

}
