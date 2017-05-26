package com.kmzyc.b2b.service.impl;

import java.sql.SQLException;
import java.util.Date;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.kmzyc.b2b.dao.CommercialTenantBasicInfoDAO;
import com.kmzyc.b2b.model.CommercialTenantBasicInfo;
import com.kmzyc.b2b.service.CommercialTenantBasicInfoService;
import com.kmzyc.framework.exception.ServiceException;
import com.whalin.MemCached.MemCachedClient;

@Service("commercialTenantBasicInfoService")
public class CommercialTenantBasicInfoServiceImpl implements CommercialTenantBasicInfoService {
    @Resource(name = "commercialTenantBasicInfoDAOImpl")
    private CommercialTenantBasicInfoDAO commercialTenantBasicInfodao;
    @Resource
    private MemCachedClient memCachedClient;

    @Override
    public CommercialTenantBasicInfo queryByLoginId(Long loginID) throws ServiceException {
        try {

            return commercialTenantBasicInfodao.queryByLoginId(loginID);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }


    @Override
    public CommercialTenantBasicInfo queryByLoginIdCache(Long loginID) {
        String key = "commercialTenantBasicInfo_" + loginID;
        CommercialTenantBasicInfo info = (CommercialTenantBasicInfo) memCachedClient.get(key);
        if (info == null) {
            info = this.queryByLoginId(loginID);
            if (info == null) {
                info = new CommercialTenantBasicInfo();
            }
            memCachedClient.set(key, info, new Date(1000 * 60 * 30));// 缓存30分钟
        }
        if (info.getnLoginId() == null) {
            return null;
        }
        return info;
    }
}
