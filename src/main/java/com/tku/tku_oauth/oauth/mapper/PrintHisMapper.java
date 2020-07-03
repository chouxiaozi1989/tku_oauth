package com.tku.tku_oauth.oauth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tku.tku_oauth.oauth.model.PrintHisInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 描述：
 *
 * @author HSG
 * @version 1.0
 * @date 2020/6/17 19:03
 */
@Mapper
@Repository
public interface PrintHisMapper extends BaseMapper<PrintHisInfo> {

    @Select("select a.historyId,a.printTime,b.resourceId,b" +
            ".resourceName,b.resourceUrl,b.resourceType " +
            "from oauth.print_history a,oauth.resource_info b " +
            "where a.resourceId = b.resourceId and a.openId =#{openid} ")
    ArrayList<PrintHisInfo> getPrintHisByOpenid(String openid);
}