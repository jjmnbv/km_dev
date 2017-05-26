package com.kmzyc.b2b.app;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.kmzyc.b2b.model.CmsAppManager;
import com.kmzyc.b2b.service.AppVersionService;
import com.km.framework.action.BaseAction;

@Scope("prototype")
@Controller("appDownLoadAction")
public class AppDownLoadAction extends BaseAction {

  // private static Logger logger = Logger.getLogger(AppDownLoadAction.class);
  private static Logger logger = LoggerFactory.getLogger(AppDownLoadAction.class);
  /**
   * 序列化
   */
  private static final long serialVersionUID = 3748493139163071352L;

  @Resource(name = "appVersionServiceImpl")
  private AppVersionService appVersionService;

  private String verionUrl;

  /**
   * 查询会员的版本信息
   * 
   * @return
   */
  public String getAppDownLoadFile() {
    logger.info("开始获取最新的安卓版本信息");
    List<CmsAppManager> cmsAppManagerList = appVersionService.getLatestAndroidAppVersion();
    verionUrl = cmsAppManagerList.get(0).getDownLoad();
    return SUCCESS;
  }

  public String getVerionUrl() {
    return verionUrl;
  }

  public void setVerionUrl(String verionUrl) {
    this.verionUrl = verionUrl;
  }

}
