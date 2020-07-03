package com.tku.tku_oauth.oauth.service.serviceImp;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tku.tku_oauth.oauth.mapper.FamilyInfoMapper;
import com.tku.tku_oauth.oauth.mapper.UserInfoMapper;
import com.tku.tku_oauth.oauth.model.FamilyInfo;
import com.tku.tku_oauth.oauth.model.UserInfo;
import com.tku.tku_oauth.oauth.service.IFamilyInfoService;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/*
 * 方法简介.
 *
 * @author HSG
 * @date 创建时间 2020/6/24
 * @since V1.0
*/
@Service
public class FamilyInfoServiceImpl extends ServiceImpl<FamilyInfoMapper, FamilyInfo> implements IFamilyInfoService {

    @Autowired
    private FamilyInfoMapper familyInfoMapper;
    @Autowired
    private RedisTemplate redisTemplate;

    private UserInfoMapper userInfoMapper;
    /*
     * 方法简介.查询家庭成员By openid
     *
     * @author HSG
     * @date 创建时间 2020/6/24
     * @since V1.0
    */
    public JSONObject getFamilyInfoByOpenid(String openId){
        JSONObject chistoryJson = new JSONObject();
        ArrayList arrayList ;
        String errorcode = "0";
        String errortext = "操作成功";
        String collectHisStr  = "";
        ArrayList arrayListcHisInfo;
        try {
            arrayList = (ArrayList)redisTemplate.opsForValue().get("family" + openId);
            if (arrayList == null) {
                arrayListcHisInfo = familyInfoMapper.getFamilyInfoByOpenid(openId);
                if (arrayListcHisInfo == null) {
                    errorcode = "1";
                    errortext = "未查询到家庭成员信息";
                } else {
                    collectHisStr = new JSONArray(arrayListcHisInfo).toString();
                    redisTemplate.opsForValue().set("family" + openId, arrayListcHisInfo, 7200, TimeUnit.SECONDS);
                }
            } else {
                collectHisStr = new JSONArray(arrayList).toString();
            }
        } catch (Exception e) {
            errorcode = "1";
            errortext = e.getMessage();
        }

        chistoryJson.put("familyJson",collectHisStr);
        chistoryJson.put("errorcode",errorcode);
        chistoryJson.put("errortext",errortext);

        return chistoryJson;
    }
    /*
     * 方法简介.查询家庭成员By subopenid
     *
     * @author HSG
     * @date 创建时间 2020/6/24
     * @since V1.0
    */
    public JSONObject getFamilyInfoBySubOpenid(String openId){
        return null;
    }

    /*
     * 方法简介.新增家庭信息
     *
     * @author HSG
     * @date 创建时间 2020/6/24
     * @since V1.0
    */
    public JSONObject addFamilyInfo(String openId, String subOpenid){

        JSONObject res = new JSONObject();
        String errorcode = "0";
        String errortext = "操作成功";
        ArrayList arrayListcHisInfo  = new ArrayList();
        try {
            arrayListcHisInfo = familyInfoMapper.getFamilyInfoBySubOpenid(subOpenid);
            UserInfo userInfo = userInfoMapper.getByOpenId(subOpenid, "1");
            if (arrayListcHisInfo.size() > 0) {
                errorcode = "1";
                errortext = "操作失败,新增家庭成员信息已有所属家庭，不能新增！";
            } else if (userInfo == null) {
                errorcode = "1";
                errortext = "操作失败,新增家庭成员信息不存在，不能新增！";
            } else {
                familyInfoMapper.addFamilyInfo(openId, subOpenid);
                this.reSaveRedisData(openId);
            }

        } catch (Exception e) {
            errorcode = "1";
            errortext = "操作失败：" + e.getMessage();
        }
        res.put("errorcode",errorcode);
        res.put("errortext",errortext);
        return res;
    }

    /*
     * 方法简介.删除家庭信息
     *
     * @author HSG
     * @date 创建时间 2020/6/24
     * @since V1.0
    */
    public JSONObject deleteFamilyInfo(String openId, String subOpenid){

        JSONObject res = new JSONObject();
        String errorcode = "0";
        String errortext = "操作成功";
        try {
            familyInfoMapper.deleteFamilyInfo( openId,subOpenid);
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
        redisTemplate.delete("family" + openId);
        arrayListcHisInfo = familyInfoMapper.getFamilyInfoByOpenid(openId);
        redisTemplate.opsForValue().set("family" + openId, arrayListcHisInfo, 7200, TimeUnit.SECONDS);
    }
}
