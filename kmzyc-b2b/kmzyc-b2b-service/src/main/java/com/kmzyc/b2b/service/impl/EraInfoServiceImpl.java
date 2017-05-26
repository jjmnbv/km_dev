package com.kmzyc.b2b.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kmzyc.b2b.dao.EraInfoDAO;
import com.kmzyc.b2b.service.EraInfoService;
import com.kmzyc.b2b.service.LoginService;
import com.kmzyc.b2b.vo.EraInfo;
import com.kmzyc.framework.exception.ServiceException;
import com.kmzyc.user.remote.service.CustomerRemoteService;
import com.kmzyc.user.remote.service.EraInfoRemoteService;

/**
 * 
 * @author houlin 时代会员信息serviceimp
 */
@Service
public class EraInfoServiceImpl implements EraInfoService {

    private static Logger logger = LoggerFactory.getLogger(EraInfoServiceImpl.class);

    @Resource
    private EraInfoDAO eraInfoDAO;

    @Resource
    private LoginService loginService;

    @Resource
    private EraInfoRemoteService eraInfoRemoteService;

    @Resource
    private CustomerRemoteService customerRemoteService;

    /***
     * 根据loginId 查询是否是时代会员
     * 
     * @throws SQLException
     */
    @Override
    public EraInfo selectEranInfoById(Map<String, Object> mapCondition) throws SQLException {

        return eraInfoDAO.findEraInfoById(mapCondition);
    }

    /***
     * 根据loginId 查询是否是时代会员 带出直销用户信息和用户手机号机和邮件
     * 
     * @throws SQLException
     */
    @Override
    public EraInfo selectEranInfoAndLoginInfoById(Map<String, Object> mapCondition)
            throws SQLException {

        return eraInfoDAO.selectEranInfoAndLoginInfoById(mapCondition);
    }

    /**
     * 查询时代会员
     * 
     * @param loginId
     * @return
     * @throws ServiceException
     */
    @Override
    public EraInfo queryEraInfoByLoginId(Long loginId) throws ServiceException {
        try {

            return eraInfoDAO.queryEraInfoByLoginId(loginId);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    /**
     * 根据登录ID查询时代个人信息
     * 
     * @param loginId
     * @return
     * @throws Exception
     */
    @Override
    public EraInfo queryEraPersonalInfo(Long loginId) throws Exception {
        return eraInfoDAO.queryEraPersonalInfo(loginId);
    }

    @Override
    public Map<String,String> updateEraInfo(JSONArray jsonArray){

        List<EraInfo> eraInfos = new ArrayList<>();
        Map<String,String> rsMap = new HashMap<>();

        try {

            for (int i = 0 ; i < jsonArray.size() ; i ++){
                JSONObject json = jsonArray.getJSONObject(i);
                EraInfo eraInfo = this.loginService.getEraInfoOBJ(json);
                //eraInfo.setOperation(json.getInteger("Operation"));
                if(null == eraInfo){
                    logger.error("更新时代会员信息失败:数据转换错误");
                    rsMap.put("success","0");
                    rsMap.put("messageCode","1");

                    return rsMap;
                }
                eraInfos.add(eraInfo);
            }
        } catch (Exception e) {
            logger.error("更新时代会员信息失败:数据转换异常",e);
            rsMap.put("success","0");
            rsMap.put("messageCode","1");

            return rsMap;
        }

        try {

            this.eraInfoRemoteService.updateUserInfoForTimes(eraInfos);

            rsMap.put("success","1");

        } catch (Exception e) {
            logger.error("更新时代会员信息异常",e);
            rsMap.put("success","0");
            rsMap.put("messageCode","0");
        }

        return rsMap;
    }



}
