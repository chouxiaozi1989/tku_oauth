package com.tku.tku_oauth.oauth.service.serviceImp;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tku.tku_oauth.oauth.mapper.FamilyShareMapper;
import com.tku.tku_oauth.oauth.model.FamilyShareInfo;
import com.tku.tku_oauth.oauth.service.IFamilyShareService;
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
public class FamilyShareServiceImpl extends ServiceImpl<FamilyShareMapper, FamilyShareInfo> implements IFamilyShareService {

     @Autowired
     private FamilyShareMapper familyShareMapper;
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
     public JSONObject getFamilyShareInfoByOpenid(String openid) {
          JSONObject chistoryJson = new JSONObject();
          ArrayList arrayList ;
          String errorcode = "0";
          String errortext = "操作成功";
          String collectHisStr  = "";
          ArrayList arrayListcHisInfo;
          try {
               arrayList = (ArrayList)redisTemplate.opsForValue().get("familyshare" + openid);
               if (arrayList == null) {
                    arrayListcHisInfo = familyShareMapper.getFamilyShareInfoByOpenid(openid);
                    if (arrayListcHisInfo == null) {
                         errorcode = "1";
                         errortext = "未查询到家庭共享信息";
                    } else {
                         collectHisStr = new JSONArray(arrayListcHisInfo).toString();
                         redisTemplate.opsForValue().set("familyshare" + openid, arrayListcHisInfo, 7200, TimeUnit.SECONDS);
                    }
               } else {
                    collectHisStr = new JSONArray(arrayList).toString();
               }
          } catch (Exception e) {
               errorcode = "1";
               errortext = e.getMessage();
          }

          chistoryJson.put("familyShareJson",collectHisStr);
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
     public JSONObject addFamilyShareInfo(String resourceId,String openId){
          JSONObject res = new JSONObject();
          String shareId = "", shareTime="";
          String errorcode = "0";
          String errortext = "操作成功";
          try {
               Date date = new Date();
               SimpleDateFormat format2 = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
               shareTime = format2.format(date);
               shareId = shareTime+ UUID.randomUUID().toString().replaceAll("-","");
               familyShareMapper.addFamilyShareInfo(shareId, shareTime, resourceId,openId);
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
     public JSONObject deleteFamilyShareInfo(String resourceId, String openId) {
          JSONObject res = new JSONObject();
          String errorcode = "0";
          String errortext = "操作成功";
          try {
               familyShareMapper.deleteFamilyShareInfo(resourceId, openId);
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
          redisTemplate.delete("familyshare" + openId);
          arrayListcHisInfo = familyShareMapper.getFamilyShareInfoByOpenid(openId);
          redisTemplate.opsForValue().set("familyshare" + openId, arrayListcHisInfo, 7200, TimeUnit.SECONDS);
     }
}
