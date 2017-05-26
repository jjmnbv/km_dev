package com.pltfm.app.action;


import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.kmzyc.commons.page.Page;
import com.opensymphony.xwork2.ModelDriven;
import com.pltfm.app.service.LatestLoginService;
import com.pltfm.app.vobject.LatestLogin;

/**
 * 最近登录Action类
 * 
 * @author cjm
 * @since 2013-7-24
 */
@Component(value = "latestLoginAction")
public class LatestLoginAction extends BaseAction implements ModelDriven {
  /**
   * 最近登录业务逻辑接口
   */
  @Resource(name = "latestLoginService")
  private LatestLoginService latestLoginService;

  /**
   * 最近登录实体
   */
  private LatestLogin latestLogin;

  /**
   * 分页类
   */
  private Page page;

  /**
   * 添加最近登录
   * 
   * @return
   */
  public String add() {
    try {
      latestLoginService.addLatestLogin(latestLogin);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return "addSuccess";
  }

  /**
   * 查询最近登录信息
   * 
   * @return
   */
  public String pageList() {
    try {
      page = latestLoginService.searchPageByVo(page, latestLogin);
      return "pageSuccess";
    } catch (Exception e) {
      e.printStackTrace();
      return "pageInput";
    }
  }

  @Override
  public Object getModel() {
    latestLogin = new LatestLogin();
    return latestLogin;
  }

  public LatestLoginService getLatestLoginService() {
    return latestLoginService;
  }

  public void setLatestLoginService(LatestLoginService latestLoginService) {
    this.latestLoginService = latestLoginService;
  }

  public LatestLogin getLatestLogin() {
    return latestLogin;
  }

  public void setLatestLogin(LatestLogin latestLogin) {
    this.latestLogin = latestLogin;
  }

  @Override
public Page getPage() {
    return page;
  }

  @Override
public void setPage(Page page) {
    this.page = page;
  }

}
