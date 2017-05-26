package com.pltfm.app.action;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.kmzyc.commons.page.Page;
import com.opensymphony.xwork2.ModelDriven;
import com.pltfm.app.service.BnesAnswerInfoService;
import com.pltfm.app.util.ConfigureUtils;
import com.pltfm.app.vobject.BnesAnswerInfo;
import com.pltfm.app.vobject.SafeQuestion;
import com.pltfm.sys.model.SysUser;

/**
 * 安全问题答案信息Action类
 * 
 * @author cjm
 * @since 2013-8-6
 */
@Component(value = "bnesAnswerInfoAction")
@Scope(value = "prototype")
public class BnesAnswerInfoAction extends BaseAction implements ModelDriven {

  /**
   * 安全问题答案信息实体
   */
  private BnesAnswerInfo bnesAnswerInfo;

  /**
   * 安全问题答案信息业务逻辑接口
   */
  @Resource(name = "bnesAnswerInfoService")
  private BnesAnswerInfoService bnesAnswerInfoService;

  /**
   * 分页类
   */
  private Page page;

  /**
   * 安全问题集合
   */
  private List<SafeQuestion> safeList;
  /**
   * 安全问题ID
   */
  private Integer safeQuestion_Id;
  /**
   * 账户ID
   */
  private Integer account_Id;

  /**
   * 查询安全问题答案信息
   * 
   * @return
   */
  public String pageList() {
    try {
      safeList = bnesAnswerInfoService.qeuryBySafeQuestionAll();
      page = bnesAnswerInfoService.searchPageByVo(page, bnesAnswerInfo);
      return "pageSuccess";
    } catch (Exception e) {
      e.printStackTrace();
      return "pageInput";
    }

  }

  /**
   * 根据安全问题id账户查id询单些问题是否已存在
   */
  public void checkSafeQuestion() {
    try {
      List list = bnesAnswerInfoService.selectByPrimaryKey(safeQuestion_Id, account_Id);
      int row = 1;
      if (list.size() == 0) {
        row = 0;
      }
      super.writeJson(row);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * 进入添加安全问题答案信息页面
   * 
   * @return
   */
  public String preAdd() {
    try {
      safeList = bnesAnswerInfoService.qeuryBySafeQuestionAll();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return "preAddSuccess";
  }

  /**
   * 添加安全问题答案信息
   * 
   * @return
   */
  public String add() {
    SysUser sysuser = (SysUser) session.get("sysUser");
    bnesAnswerInfo.setCreated(sysuser.getUserId());
    Integer rows = 0;
    try {
      rows = bnesAnswerInfoService.addBnesAnswerInfo(bnesAnswerInfo);
    } catch (Exception e) {
      this.addActionMessage(ConfigureUtils.getMessageConfig("bnesAnswerInfo.add.fail"));
      e.printStackTrace();
    }

    if (rows > 0) {
      this.addActionMessage(ConfigureUtils.getMessageConfig("bnesAnswerInfo.add.success"));
      bnesAnswerInfo = new BnesAnswerInfo();
      return pageList();
    }
    return this.preAdd();
  }

  @Override
  public BnesAnswerInfo getModel() {
    bnesAnswerInfo = new BnesAnswerInfo();
    return bnesAnswerInfo;
  }

  public BnesAnswerInfo getBnesAnswerInfo() {
    return bnesAnswerInfo;
  }

  public void setBnesAnswerInfo(BnesAnswerInfo bnesAnswerInfo) {
    this.bnesAnswerInfo = bnesAnswerInfo;
  }

  public BnesAnswerInfoService getBnesAnswerInfoService() {
    return bnesAnswerInfoService;
  }

  public void setBnesAnswerInfoService(BnesAnswerInfoService bnesAnswerInfoService) {
    this.bnesAnswerInfoService = bnesAnswerInfoService;
  }

  @Override
public Page getPage() {
    return page;
  }

  @Override
public void setPage(Page page) {
    this.page = page;
  }

  public List<SafeQuestion> getSafeList() {
    return safeList;
  }

  public void setSafeList(List<SafeQuestion> safeList) {
    this.safeList = safeList;
  }

  public Integer getSafeQuestion_Id() {
    return safeQuestion_Id;
  }

  public void setSafeQuestion_Id(Integer safeQuestionId) {
    safeQuestion_Id = safeQuestionId;
  }

  public Integer getAccount_Id() {
    return account_Id;
  }

  public void setAccount_Id(Integer accountId) {
    account_Id = accountId;
  }
}
