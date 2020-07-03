package com.tku.tku_oauth.oauth.service.serviceImp;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tku.tku_oauth.oauth.mapper.ResourceInfoMapper;
import com.tku.tku_oauth.oauth.model.ResourceInfo;
import com.tku.tku_oauth.oauth.service.IResourceInfoService;
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
public class ResourceInfoServiceImpl extends ServiceImpl<ResourceInfoMapper, ResourceInfo> implements IResourceInfoService {

     @Autowired
     private ResourceInfoMapper resourceInfoMapper;
     @Autowired
     private RedisTemplate redisTemplate;

     /*
      * 方法简介.
      *
      * @author HSG
      * @date 创建时间 2020/6/20
      * @since V1.0
     */
     /*@Override
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
                    arrayListcHisInfo = resourceInfoMapper.getCollectHisByOpenid(openid);
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
     }*/

     /*
      * 方法简介.
      *
      * @author HSG
      * @date 创建时间 2020/6/20
      * @since V1.0
     */
     @Override
     public JSONObject addResourceInfo(JSONObject jsonObject){
          JSONObject res = new JSONObject();
          String resourceId = "", resourceName="";
          String resourceUrl = "", resourceType="";
          String resourceCategory = "", resourceContent="";
          String time = "";
          ArrayList arrayListcHisInfo;
          String errorcode = "0";
          String errortext = "操作成功";
          try {
               Date date = new Date();
               SimpleDateFormat format2 = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
               time = format2.format(date);
               resourceId = time + UUID.randomUUID().toString().replaceAll("-","");
               resourceName = jsonObject.getString("resourceName");
               resourceUrl = jsonObject.getString("resourceUrl");
               resourceType = jsonObject.getString("resourceType");
               resourceCategory = jsonObject.getString("resourceCategory");
               resourceContent = jsonObject.getString("resourceContent");


               resourceInfoMapper.addResourceInfo(resourceId, resourceName, resourceUrl, resourceType, resourceCategory, resourceContent);
               redisTemplate.opsForValue().set("resourceinfo" + resourceId, jsonObject, 7200, TimeUnit.SECONDS);
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
     public JSONObject deleteResourceInfo(JSONObject jsonObject) {
          JSONObject res = new JSONObject();
          String errorcode = "0";
          String errortext = "操作成功";
          try {
               String resourceId = jsonObject.getString("resourceId");
               resourceInfoMapper.deleteResourceInfo(resourceId);
               redisTemplate.delete("resourceinfo" + resourceId);
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
     *//*
     public void reSaveRedisData(String openId){
          ArrayList arrayListcHisInfo;
          redisTemplate.delete("resourceinfo" + openId);
          arrayListcHisInfo = resourceInfoMapper.getCollectHisByOpenid(openId);
          redisTemplate.opsForValue().set("resourceinfo" + openId, arrayListcHisInfo, 7200, TimeUnit.SECONDS);
     }*/
}
