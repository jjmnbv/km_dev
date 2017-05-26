package com.pltfm.app.action;

import com.kmzyc.commons.page.Page;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.pltfm.app.service.PersonalityInfoService;
import com.pltfm.app.vobject.PersonalityInfo;

/**
 * 个人个性信息Action类
 * 
 * @author cjm
 * @since 2013-7-10
 */
public class PersonalityInfoAction extends ActionSupport implements ModelDriven {
  /**
   * 个人个性信息Model
   */
  private PersonalityInfo personalityInfo;
  /**
   * 个人个性信息业务逻辑接口
   */
  private PersonalityInfoService personalityInfoService;

  /**
   * 分页类
   */
  private Page page;

  /**
   * 添加个人个性信息
   * 
   * @return
   */
  public String addPersonalityInfo() {

    try {
      personalityInfo.setNickname("fsda");
      personalityInfo.setQqNumber("32");
      personalityInfoService.addPersonalityInfo(personalityInfo);
    } catch (Exception e) {
      e.printStackTrace();
    }

    return Action.SUCCESS;
  }

  /**
   * 分页列表
   * 
   * @return 返回值
   */
  public String listPersonalityInfo() {
    try {
      if (page == null) {
        page = new Page();
      }
      page.setPageSize(2); // 设置每页显示条数

      personalityInfo.setNickname("a");

      Page returnPage = personalityInfoService.searchPageByVo(page, personalityInfo);

      // 测试
      for (Object o : page.getDataList()) {
        PersonalityInfo pi = (PersonalityInfo) o;

      }

      // System.out.println(returnPage.getPageCount()+"=PageCount---RecordCount="+returnPage.getRecordCount());
    } catch (Exception e) {
      e.printStackTrace();
      return ERROR;
    }
    return SUCCESS;
  }

  @Override
  public Object getModel() {
    personalityInfo = new PersonalityInfo();
    return personalityInfo;
  }

  public PersonalityInfo getPersonalityInfo() {
    return personalityInfo;
  }

  public void setPersonalityInfo(PersonalityInfo personalityInfo) {
    this.personalityInfo = personalityInfo;
  }

  public PersonalityInfoService getPersonalityInfoService() {
    return personalityInfoService;
  }

  public void setPersonalityInfoService(PersonalityInfoService personalityInfoService) {
    this.personalityInfoService = personalityInfoService;
  }

}
