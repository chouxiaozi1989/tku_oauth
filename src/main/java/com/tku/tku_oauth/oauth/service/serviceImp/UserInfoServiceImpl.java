package com.tku.tku_oauth.oauth.service.serviceImp;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tku.tku_oauth.oauth.mapper.UserInfoMapper;
import com.tku.tku_oauth.oauth.model.UserInfo;
import com.tku.tku_oauth.oauth.service.IUserInfoService;
import com.tku.tku_oauth.oauth.utils.httpUtils;
import jdk.internal.org.objectweb.asm.tree.TryCatchBlockNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.sound.midi.SysexMessage;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author CaiYi
 * @since 2020-03-30
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements IUserInfoService {

    @Autowired
    private UserInfoMapper userMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Value("${weixin.appid}")
    private String appid;

    @Value("${weixin.secret}")
    private String secret;

    @Value("${weixin.grant_type}")
    private String grant_type;

    @Value("${weixin.url_jscode2session}")
    private String url_jscode2session;

    @Override
    public UserInfo getUserById(String userId) {
        return userMapper.getByUserId(userId, "1");
    }

    @Override
    public JSONObject getUserByOpenId(String openid) {


        JSONObject jsonUser = new JSONObject();
        JSONObject resJson = new JSONObject();
        Assert.notNull(openid,"传入的参数openid为空，请检查！");
        String errorcode = "0";//0：操作成功,1：用户不存在
        String errortext = "操作成功";
        try {
            jsonUser = (JSONObject)redisTemplate.opsForValue().get("emp" + openid);
            if (jsonUser == null) {
                UserInfo userInfo =  userMapper.getByOpenId(openid, "1");
                if (userInfo == null) {
                    errorcode = "1";
                    errortext = "用户不存在";
                } else {
                    resJson = userInfo.toJSON();
                }
            } else {
                resJson = jsonUser;
            }
        } catch (Exception e){
            errorcode = "1";
            errortext = e.getMessage();
        }
        resJson.put("errorcode",errorcode);
        resJson.put("errortext",errortext);
        return resJson;
    }

    @Override
    public UserInfo login(String userid, String password) {
        UserInfo userInfo = userMapper.getByUserId(userid, "1");
        //verify password
        if (password.equals(userInfo.getPassword())) {
            return userInfo;
        } else {
            return null;
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void UpdateOpenid(String userid, String openid) {
        userMapper.UpdateOpenid(userid, openid);
    }

    @Override
    public JSONObject jscode2session(String code) {
        String str = "", session_key = "", openid = "";
        JSONObject response = new JSONObject();
        Assert.notNull(code, "传入的参数Code为空！");
        try {
            String urlStr = url_jscode2session + "?appid=" + appid +
                    "&secret=" +
                    secret +
                    "&js_code=" +
                    code +
                    "&grant_type=" +
                    grant_type ;
            URL url = new URL(urlStr);

            str = httpUtils.doHttpRequestGET(url);
            if (!"".equals(str) && null != str) {
                JSONObject res = JSONObject.parseObject(str);

                if (!res.containsKey("openid")) {
                    response = res;
                } else {
                    session_key = res.getString("session_key");
                    openid = res.getString("openid");
                    response.put("openid", openid);
                    response.put("errortext", "解析成功！");
                    response.put("session_key", session_key);
                    response.put("errorcode", "0");
                }
            } else {
                response.put("errortext", "Invoke Weixin API Error!Response null.");
                response.put("errorcode ", "1");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public UserInfo oauth(String userid, String password, String openid, JSONObject userinfo) {
        UserInfo userInfo_local = userMapper.getByUserId(userid, "1");
        Assert.notNull(userInfo_local, "获取用户出错");
        Assert.isTrue(password.equals(userInfo_local.getPassword()), "密码错误");
        //bind local user
        userMapper.UpdateOpenid(userid, openid);

        //update userinfo
        userMapper.UpdateGen(userinfo.getInteger("gender"), userid);
        userMapper.UpdateCountry(userinfo.getString("country"), userid);
        userMapper.UpdateProvince(userinfo.getString("province"), userid);
        userMapper.UpdateCity(userinfo.getString("city"), userid);
        userMapper.UpdateUserName(userinfo.getString("nickName"), userid);
        userMapper.UpdateAvatarUrl(userinfo.getString("avatarUrl"), userid);

        return userMapper.getByUserId(userid, "1");
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void addUser(String userid, String userName) {
        userMapper.addUser(userid, userName);
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    @Cacheable()
    public JSONObject addUserInfo(JSONObject jsonUser) {
        Date date = new Date();
        SimpleDateFormat format2 = new SimpleDateFormat("yyyyMMdd");
        String dateStr = format2.format(date);
        String userid = dateStr+UUID.randomUUID().toString().replaceAll("-","");
        String openid  =  jsonUser.getString("openid");
        //System.out.println(openid);
        JSONObject jsonUserR = getUserByOpenId(openid);

        JSONObject res = new JSONObject();
        if ("0".equals(jsonUserR.getString("errorcode"))) {
            res.put("errorcode","0");
            res.put("errortext","操作成功");
        } else {
            try {
                jsonUser.put("userid",userid);
                String userName  =  jsonUser.getString("userName");
                String gen  =  jsonUser.getString("gen");
                String birthday  =  jsonUser.getString("birthday");
                String city  =  jsonUser.getString("city");
                String grade  =  jsonUser.getString("grade");
                userMapper.addUserInfo(openid,userid, userName,gen,birthday,city,grade);
                redisTemplate.opsForValue().set("emp" + openid, jsonUser, 7200, TimeUnit.SECONDS);
            } catch (Exception e){
                res.put("errorcode","1");
                res.put("errortext","操作失败：" + e.getMessage());
            }
        }

        return res;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public JSONObject updateUserInfo(JSONObject jsonUser) {
        String openid  = jsonUser.getString("openid");
        JSONObject res = new JSONObject();

        if (null == openid) {
            res.put("errorcode","1");
            res.put("errortext","操作失败：openid为空！");
            return res;
        }

        JSONObject jsonUserR = getUserByOpenId(openid);

        if (jsonUserR.getString("errorcode").equals("1")) {
            res.put("errorcode","1");
            res.put("errortext","操作失败：未查询到人员信息！");
        } else {
            try {
                String userName  =  jsonUser.getString("userName");
                String gen  =  jsonUser.getString("gen");
                String birthday  =  jsonUser.getString("birthday");
                String city  =  jsonUser.getString("city");
                String grade  =  jsonUser.getString("grade");
                userMapper.updateUserInfo(openid, userName,gen,birthday,city,grade);
                redisTemplate.delete("emp" + openid);
                jsonUserR = getUserByOpenId(openid);
                redisTemplate.opsForValue().set("emp" + openid,jsonUserR,7200,TimeUnit.SECONDS);
                res.put("errorcode","0");
                res.put("errortext","操作成功");
            } catch (Exception e){
                res.put("errorcode","1");
                res.put("errortext","操作失败:" + e.getMessage());
            }
        }

        return res;
    }
}