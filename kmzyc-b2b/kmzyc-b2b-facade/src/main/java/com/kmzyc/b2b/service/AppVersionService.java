package com.kmzyc.b2b.service;

import java.util.List;

import com.kmzyc.b2b.model.CmsAppManager;
import com.kmzyc.framework.exception.ServiceException;

public interface AppVersionService {

  /**
   * 查找移动客户端与当前版本不同版本的系统
   * 
   * @param appType
   * @param version
   * @return
   */
  public List<CmsAppManager> queryCmsAppManagerList(String appType, String version);

  /**
   * 查找移动客户端与当前版本不同版本的系统,适用于安卓系统情况
   * 
   * @param appType
   * @param version
   * @return
   */
  public List<CmsAppManager> querCmsAppManagerListAndroid(Integer versionCode);

  /**
   * 
   * @return
   */
  public List<CmsAppManager> getLatestAndroidAppVersion();

  /**
   * 查询最新版本
   * 
   * @param appType
   * @return
   * @throws ServiceException
   */
  public CmsAppManager queryLastestVersionApp(String appType) throws ServiceException;

  /**
   * 根据版本号和type 查找版本
   * 
   * @param appType
   * @param version
   * @return
   */
  CmsAppManager getByVersionAndType(String appType, String version);

  /**
   * 更新redis中app下载次数
   * 
   * @param appType
   */
  void updateDownCount(String appType);

  /**
   * 查找比versionCode更新的版本
   * 
   * @param appType
   * @param versionCode
   * @return
   */
  public List<CmsAppManager> getLasterAppList(String appType, Integer versionCode);
}
