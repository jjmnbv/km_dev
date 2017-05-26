package com.pltfm.app.action;


import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.kmzyc.commons.page.Page;
import com.opensymphony.xwork2.ModelDriven;
import com.pltfm.app.service.MobileCodeInfService;
import com.pltfm.app.vobject.MobileCodeInf;

/**
 * 手机随机码信息Action类
 * 
 * @author cjm
 * @since 2013-7-10
 */
@Component(value = "mobileCodeInfAction")
public class MobileCodeInfAction extends BaseAction implements ModelDriven {
  /**
   * 手机随机码实体
   */
  private MobileCodeInf mobileCodeInf;

  /**
   * 手机随机码业务逻辑接口
   */
  @Resource(name = "mobileCodeInfService")
  private MobileCodeInfService mobileCodeInfService;

  /**
   * 分页类
   */
  private Page page;

  /**
   * 添加手机随机码
   * 
   * @return
   */
  public String add() {
    try {
      mobileCodeInfService.addMobileCodeInf(mobileCodeInf);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return "addSuccess";
  }

  /**
   * 查询手机随机码信息
   * 
   * @return
   */
  public String pageList() {
    try {
      page = mobileCodeInfService.searchPageByVo(page, mobileCodeInf);
      return "pageSuccess";
    } catch (Exception e) {
      e.printStackTrace();
      return "pageInput";
    }
  }

  @Override
  public Object getModel() {
    mobileCodeInf = new MobileCodeInf();
    return mobileCodeInf;
  }


  public MobileCodeInf getMobileCodeInf() {
    return mobileCodeInf;
  }


  public void setMobileCodeInf(MobileCodeInf mobileCodeInf) {
    this.mobileCodeInf = mobileCodeInf;
  }


  public MobileCodeInfService getMobileCodeInfService() {
    return mobileCodeInfService;
  }


  public void setMobileCodeInfService(MobileCodeInfService mobileCodeInfService) {
    this.mobileCodeInfService = mobileCodeInfService;
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
