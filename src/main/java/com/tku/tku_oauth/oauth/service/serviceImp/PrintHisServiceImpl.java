package com.tku.tku_oauth.oauth.service.serviceImp;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tku.tku_oauth.oauth.mapper.PrintHisMapper;
import com.tku.tku_oauth.oauth.mapper.UserInfoMapper;
import com.tku.tku_oauth.oauth.model.PrintHisInfo;
import com.tku.tku_oauth.oauth.model.UserInfo;
import com.tku.tku_oauth.oauth.service.IPrintHisService;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * 描述：
 *
 * @author HSG
 * @version 1.0
 * @date 2020/6/12 19:53
 */

@Service
public class PrintHisServiceImpl extends ServiceImpl<PrintHisMapper, PrintHisInfo> implements IPrintHisService {


     @Autowired
     private PrintHisMapper printHisMapper;
     @Autowired
     private RedisTemplate redisTemplate;

     public JSONObject getPrintHisByOpenid(String openid) {
          JSONObject phistoryJson = new JSONObject();
          ArrayList arrayList ;
          String errorcode = "0";
          String errortext = "操作成功";
          String printHisStr  = "";
          ArrayList arrayListpHisInfo;
          try {
               arrayList = (ArrayList)redisTemplate.opsForValue().get("printhis" + openid);
               if (arrayList == null) {
                    arrayListpHisInfo = printHisMapper.getPrintHisByOpenid(openid);
                    if (arrayListpHisInfo == null) {
                         errorcode = "1";
                         errortext = "无打印记录";
                    } else {
                         printHisStr = new JSONArray(arrayListpHisInfo).toString();
                         redisTemplate.opsForValue().set("printhis" + openid, arrayListpHisInfo, 7200, TimeUnit.SECONDS);
                    }
               } else {
                    printHisStr = new JSONArray(arrayList).toString();
               }
          } catch (Exception e) {
               errorcode = "1";
               errortext = e.getMessage();
          }

          phistoryJson.put("historyJson",printHisStr);
          phistoryJson.put("errorcode",errorcode);
          phistoryJson.put("errortext",errortext);

          return phistoryJson;
     }
}