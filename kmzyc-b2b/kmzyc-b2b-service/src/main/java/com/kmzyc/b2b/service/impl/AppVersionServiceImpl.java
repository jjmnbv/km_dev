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

import com.kmzyc.b2b.dao.AppVersionDao;
import com.kmzyc.b2b.model.CmsAppManager;
import com.kmzyc.b2b.service.AppVersionService;
import com.kmzyc.framework.exception.ServiceException;

import redis.clients.jedis.JedisCluster;

@SuppressWarnings("unchecked")
@Service
public class AppVersionServiceImpl implements AppVersionService {

    // private static Logger logger = Logger.getLogger(AppVersionServiceImpl.class);
    private static Logger logger = LoggerFactory.getLogger(AppVersionServiceImpl.class);
    @Resource(name = "appVersionDaoImpl")
    private AppVersionDao appVersionDao;

    @Resource(name = "jedisCluster")
    private JedisCluster jedisCluster;

    /**
     * 查找移动客户端与当前版本不同版本的系统
     */
    @Override
    public List<CmsAppManager> queryCmsAppManagerList(String appType, String version) {
        // 使用产品数据源

        List<CmsAppManager> cmsAppManagerList = new ArrayList<>();
        Map<String, Object> condition = new HashMap<>();
        logger.info("查询参数为appType：【" + appType + "】，version为【" + version + "】");
        condition.put("appType", appType);
        condition.put("version", version);
        try {
            cmsAppManagerList = appVersionDao
                    .findByProperty("CmsAppManager.cmsAppManagerList", condition);
        } catch (SQLException e) {
            logger.error("", e);
        }
        return cmsAppManagerList;
    }

    /**
     * 查找移动客户端与当前版本不同版本的系统,适用于安卓系统情况
     */
    @Override
    public List<CmsAppManager> querCmsAppManagerListAndroid(Integer versionCode) {
        // 使用产品数据源

        List<CmsAppManager> cmsAppManagerList = new ArrayList<>();
        Map<String, Object> condition = new HashMap<>();
        condition.put("versionCode", versionCode);
        try {
            cmsAppManagerList = appVersionDao
                    .findByProperty("CmsAppManager.cmsAppManagerListAndroid", condition);
        } catch (SQLException e) {
            logger.error("",e);
        }
        return cmsAppManagerList;
    }

    @Override
    public List<CmsAppManager> getLatestAndroidAppVersion() {
        // 使用产品数据源

        List<CmsAppManager> cmsAppManagerList = new ArrayList<>();
        try {
            cmsAppManagerList = (List<CmsAppManager>) appVersionDao
                    .findByProperty("CmsAppManager.getAndroidLatestVersion", null);
        } catch (SQLException e) {
            logger.error("",e);
        }
        return cmsAppManagerList;
    }


    /**
     * 查询最新版本
     */
    public CmsAppManager queryLastestVersionApp(String appType) throws ServiceException {

        try {
            List<CmsAppManager> camList = (List<CmsAppManager>) appVersionDao
                    .findByProperty("CmsAppManager.SQL_QUERY_LASTEST_VERSION_APP", appType);
            if (null != camList && !camList.isEmpty()) {
                return camList.get(0);
            } else {
                return null;
            }
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    /**
     * 根据版本号和type 查找版本
     */
    @Override
    public CmsAppManager getByVersionAndType(String appType, String version) {

        CmsAppManager app = new CmsAppManager();
        Map<String, Object> condition = new HashMap<>();
        logger.info("查询参数为appType：【" + appType + "】，version为【" + version + "】");
        condition.put("appType", appType);
        condition.put("version", version);
        try {
            List<CmsAppManager> cmsAppManagerList = appVersionDao
                    .findByProperty("CmsAppManager.getListByVersionAndType", condition);
            if (cmsAppManagerList != null && cmsAppManagerList.size() > 0) {
                app = cmsAppManagerList.get(0);
            } else {
                app = null;
            }
        } catch (SQLException e) {
            logger.error("",e);
        }
        return app;
    }

    /**
     * 更新redis中app下载次数
     */
    @Override
    public void updateDownCount(String appType) {
        try {
            String key = "app_down_load_" + appType;
            String count = jedisCluster.get(key);
            if (null == count) {
                jedisCluster.set(key, "1");
            } else {
                jedisCluster.set(key, String.valueOf(Integer.parseInt(count) + 1));
            }
        } catch (Exception e) {
            logger.error("更新下次次数出错：" + e.getMessage(), e);
        }
    }

    @Override
    public List<CmsAppManager> getLasterAppList(String appType, Integer versionCode) {

        Map<String, Object> condition = new HashMap<>();
        List<CmsAppManager> cmsAppManagerList = new ArrayList<>();
        logger.info("查询参数为appType：【" + appType + "】，version为【" + versionCode + "】");
        condition.put("appType", appType);
        condition.put("versionCode", versionCode);
        try {
            cmsAppManagerList = appVersionDao
                    .findByProperty("CmsAppManager.getlasterVersion", condition);
        } catch (SQLException e) {
            logger.error("",e);
        }
        return cmsAppManagerList;
    }
}
