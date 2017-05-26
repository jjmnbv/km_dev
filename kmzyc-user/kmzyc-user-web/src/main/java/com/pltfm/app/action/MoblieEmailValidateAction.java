package com.pltfm.app.action;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.kmzyc.commons.page.Page;
import com.opensymphony.xwork2.ModelDriven;
import com.pltfm.app.service.MoblieEmailValidateService;
import com.pltfm.app.vobject.MoblieEmailValidate;


@Scope("prototype")
@Component(value = "moblieEmailValidateAction")
public class MoblieEmailValidateAction extends BaseAction implements ModelDriven {
  private static Logger logger = LoggerFactory.getLogger(MoblieEmailValidateAction.class);
  /**
   * 分页类
   */
  private Page page;
  // 手机邮箱验证实体
  private MoblieEmailValidate moblieEmailValidate;
  @Resource(name = "moblieEmailValidateService")
  private MoblieEmailValidateService moblieEmailValidateService;

  @Override
public Object getModel() {
    moblieEmailValidate = new MoblieEmailValidate();
    return moblieEmailValidate;
  }



  /***
   * 
   * 邮箱验证内容显示
   * 
   * @return
   */
  public String pageList() {
    try {
      if (page == null) {
        page = new Page();
      }
      Integer count = moblieEmailValidateService.selectListMoblieEmailCount(moblieEmailValidate);
      page.setRecordCount(count);
      moblieEmailValidate.setSkip(page.getStartIndex());
      moblieEmailValidate.setMax(page.getStartIndex() + page.getPageSize());
      List list = moblieEmailValidateService.selectListMoblieEmail(moblieEmailValidate);
      page.setDataList(list);
    } catch (Exception e) {
      logger.error("获取邮箱验证内容异常" + e.getMessage(), e);
    }
    return "pageList";
  }

  /***
   * 
   * 手机验证内容显示
   * 
   * @return
   */

  public String mobilePageList() {
    try {
      if (page == null) {
        page = new Page();
      }
      Integer count = moblieEmailValidateService.selectListMoblieEmailCount(moblieEmailValidate);
      page.setRecordCount(count);
      moblieEmailValidate.setSkip(page.getStartIndex());
      moblieEmailValidate.setMax(page.getStartIndex() + page.getPageSize());
      List list = moblieEmailValidateService.selectListMoblieEmail(moblieEmailValidate);
      page.setDataList(list);
    } catch (Exception e) {
      logger.error("获取手机验证列表异常" + e.getMessage(), e);
    }
    return "mobilePageList";
  }

  /****
   * 提交验证邮箱
   * 
   * @return
   * @throws SQLException
   */
  public String emailValidateSubmit() throws SQLException {
    moblieEmailValidateService.updateEmailValidate(moblieEmailValidate);
    this.addActionMessage("邮箱已验证!");
    return pageList();
  }


  /****
   * 提交手机验证
   * 
   * @returnmoblieEmailValidate
   * @throws SQLException
   */
  public String mobileValidateSubmit() throws SQLException {

    moblieEmailValidateService.mobileValidate(moblieEmailValidate);
    this.addActionMessage("手机已验证!");
    return mobilePageList();
  }


  @Override
public Page getPage() {
    return page;
  }

  @Override
public void setPage(Page page) {
    this.page = page;
  }

  public MoblieEmailValidate getMoblieEmailValidate() {
    return moblieEmailValidate;
  }

  public void setMoblieEmailValidate(MoblieEmailValidate moblieEmailValidate) {
    this.moblieEmailValidate = moblieEmailValidate;
  }



  public MoblieEmailValidateService getMoblieEmailValidateService() {
    return moblieEmailValidateService;
  }



  public void setMoblieEmailValidateService(MoblieEmailValidateService moblieEmailValidateService) {
    this.moblieEmailValidateService = moblieEmailValidateService;
  }



}
