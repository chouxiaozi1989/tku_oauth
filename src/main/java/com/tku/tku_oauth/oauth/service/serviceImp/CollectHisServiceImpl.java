package com.tku.tku_oauth.oauth.service.serviceImp;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tku.tku_oauth.oauth.mapper.CollectHisMapper;
import com.tku.tku_oauth.oauth.mapper.PrintHisMapper;
import com.tku.tku_oauth.oauth.model.CollectHisInfo;
import com.tku.tku_oauth.oauth.service.ICollectHisService;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 描述：
 *
 * @author HSG
 * @version 1.0
 * @date 2020/6/12 19:53
 */

@Service
public class CollectHisServiceImpl extends ServiceImpl<CollectHisMapper, CollectHisInfo> implements ICollectHisService {

     @Autowired
     private CollectHisMapper collectHisMapper;
     @Autowired
     private RedisTemplate redisTemplate;

     /*
      * 方法简介.
      *
      * @author HSG
      * @date 创建时间 2020/6/20
      * @since V1.0
     */
     @Override
     public JSONObject getCollectHisByOpenid(String openid) {
          JSONObject chistoryJson = new JSONObject();
          ArrayList arrayList ;
          String errorcode = "0";
          String errortext = "操作成功";
          String collectHisStr  = "";
          ArrayList arrayListcHisInfo;
          try {
               arrayList = (ArrayList)redisTemplate.opsForValue().get("collecthis" + openid);
               if (arrayList == null) {
                    arrayListcHisInfo = collectHisMapper.getCollectHisByOpenid(openid);
                    if (arrayListcHisInfo == null) {
                         errorcode = "1";
                         errortext = "无收藏记录";
                    } else {
                         collectHisStr = new JSONArray(arrayListcHisInfo).toString();
                         redisTemplate.opsForValue().set("collecthis" + openid, arrayListcHisInfo, 7200, TimeUnit.SECONDS);
                    }
               } else {
                    collectHisStr = new JSONArray(arrayList).toString();
               }
          } catch (Exception e) {
               errorcode = "1";
               errortext = e.getMessage();
          }

          chistoryJson.put("collectJson",collectHisStr);
          chistoryJson.put("errorcode",errorcode);
          chistoryJson.put("errortext",errortext);

          return chistoryJson;
     }

     /*
      * 方法简介.
      *
      * @author HSG
      * @date 创建时间 2020/6/20
      * @since V1.0
     */
     @Override
     public JSONObject addCollectionInfo(String resourceId,String openId){
          JSONObject res = new JSONObject();
          String collectId = "", collectTime="";
          ArrayList arrayListcHisInfo;
          String errorcode = "0";
          String errortext = "操作成功";
          try {
               Date date = new Date();
               SimpleDateFormat format2 = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
               collectTime = format2.format(date);
               collectId = collectTime+ UUID.randomUUID().toString().replaceAll("-","");
               collectHisMapper.addCollectionInfo(collectId, collectTime, resourceId,openId);
               this.reSaveRedisData(openId);
          } catch (Exception e){
               errorcode = "1";
               errortext = "操作失败：" + e.getMessage();
          }
          res.put("errorcode",errorcode);
          res.put("errortext",errortext);
          return res;
     }

     /*
      * 方法简介.删除收藏
      *
      * @author HSG
      * @date 创建时间 2020/6/20
      * @since V1.0
     */
     @Override
     public JSONObject deleteCollectionInfo(String resourceId, String openId) {
          JSONObject res = new JSONObject();
          String errorcode = "0";
          String errortext = "操作成功";
          try {
               collectHisMapper.deleteCollectionInfo(resourceId, openId);
               this.reSaveRedisData(openId);
          } catch (Exception e) {
               errorcode = "1";
               errortext = "操作失败：" + e.getMessage();
          }
          res.put("errorcode", errorcode);
          res.put("errortext", errortext);
          return res;

     }

     /*
      * 方法简介.重新保存缓存数据
      *
      * @author HSG
      * @date 创建时间 2020/6/20
      * @since V1.0
     */
     public void reSaveRedisData(String openId){
          ArrayList arrayListcHisInfo;
          redisTemplate.delete("collecthis" + openId);
          arrayListcHisInfo = collectHisMapper.getCollectHisByOpenid(openId);
          redisTemplate.opsForValue().set("collecthis" + openId, arrayListcHisInfo, 7200, TimeUnit.SECONDS);
     }
}
