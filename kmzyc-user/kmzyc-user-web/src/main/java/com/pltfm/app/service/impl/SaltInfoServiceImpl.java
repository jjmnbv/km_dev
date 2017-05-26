package com.pltfm.app.service.impl;

import java.sql.SQLException;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.kmzyc.b2b.vo.UserBaseInfo;
import com.kmzyc.util.StringUtil;
import com.pltfm.app.dao.SaltInfoDAO;
import com.pltfm.app.service.SaltInfoService;
import com.pltfm.app.vobject.SaltInfo;

import redis.clients.jedis.JedisCluster;

/**
 * 用户密码加盐
 */
@Service(value = "saltInfoService")
public class SaltInfoServiceImpl implements SaltInfoService {

    @Resource
    private SaltInfoDAO saltInfoDAO;
    @Resource(name = "jedisCluster")
    private JedisCluster jedisCluster;
    private static Logger logger = LoggerFactory.getLogger(SaltInfoServiceImpl.class);

    @Override
    public SaltInfo querySaltInfo(UserBaseInfo userInfoVo) {

        if(userInfoVo == null
                || (StringUtil.isEmpty(userInfoVo.getLoginId())
                    && StringUtil.isEmpty(userInfoVo.getLoginAccount())
                    && StringUtil.isEmpty(userInfoVo.getMobile()))){
            return null;
        }
        String key = "querySaltInfo_";
        if(!StringUtil.isEmpty(userInfoVo.getLoginId())){
            key += userInfoVo.getLoginId();
        }else if(!StringUtil.isEmpty(userInfoVo.getLoginAccount())){
            key += userInfoVo.getLoginAccount();
        }else if(!StringUtil.isEmpty(userInfoVo.getMobile())){
            key += userInfoVo.getMobile();
        }

        try {
            if(!StringUtil.isEmpty(userInfoVo.getLoginId()) && jedisCluster.exists(key)){
                return JSONObject.parseObject(jedisCluster.get(key),SaltInfo.class);
            }else{
                SaltInfo saltInfo =  this.saltInfoDAO.querySaltInfo(userInfoVo);

                if(saltInfo != null) {
                    jedisCluster.set(key, JSONObject.toJSONString(saltInfo));
                    jedisCluster.expire(key, 60 * 60 * 24 * 15); //缓存15天

                    return saltInfo;
                }else{
                    return null;
                }
            }
        } catch (SQLException e) {
            logger.error("querySaltInfo 查询用户密码加盐信息异常",e);
        }
        return null;
    }

    @Override
    public void insertSaltInfo(SaltInfo saltInfo){
        try {
            this.saltInfoDAO.insertSaltInfo(saltInfo);
        }catch (Exception e){
            logger.error("insertSaltInfo 插入用户密码加盐信息异常",e);
        }
    }


}
