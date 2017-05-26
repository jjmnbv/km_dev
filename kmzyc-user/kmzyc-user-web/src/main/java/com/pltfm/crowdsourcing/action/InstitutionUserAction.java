package com.pltfm.crowdsourcing.action;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.km.crowdsourcing.model.InstitutionUser;
import com.km.crowdsourcing.service.ClearingService;
import com.km.crowdsourcing.service.InstitutionInfoService;
import com.km.crowdsourcing.service.InstitutionUserService;
import com.kmzyc.commons.page.Page;
import com.opensymphony.xwork2.ModelDriven;
import com.pltfm.app.action.BaseAction;

/**
 * 机构管理 2016-3-21
 * 
 * @author songmiao
 *
 */
@Controller("institutionUserAction")
@Scope(value = "prototype")
public class InstitutionUserAction extends BaseAction implements ModelDriven {
  @Resource
  private InstitutionInfoService institutionInfoService;
  @Resource
  private InstitutionUserService institutionUerService;
  private static final long serialVersionUID = 4248109542989699382L;
  private static Logger logger = LoggerFactory.getLogger(InstitutionUserAction.class);
  private InstitutionUser institutionUser; // 查询条件对象
  private String edit;// 操作类型 clearAll：结算全部 ，其他为正常查询
  private String message;// 返回页面标示： 0 为正常查询返回，其他值需页面提示
  private String ids; // 页面checkbox选中的id
  private String clearingType; // 0:结算全部 1：批量结算
  @Resource
  private ClearingService clearingService;

  /**
   * 引荐注册列表查询 add by songmiao 2016-3-28
   * 
   * @return
   */
  public String queryInstitutionUserList() {
    com.km.commons.general.model.Page nPage = new com.km.commons.general.model.Page();
    message = "0";

    try {
      if (page != null) {
        page.setRecordCount(1); // 初始总条数需大于零，否则取出的pageSize会被默认赋值为 20
        nPage.setPageIndex(page.getPageNo());
        nPage.setPageSize(page.getPageSize());
      } else {
        page = new Page();
        page.setRecordCount(1); // 初始总条数需大于零，否则取出的pageSize会被默认赋值为 20
        nPage.setPageIndex(page.getPageNo());
        nPage.setPageSize(page.getPageSize());
      }
      page.setRecordCount(0);
      if (edit != null && edit.equals("clearAll")) { // 如果edit为clearAll说明页面点击的是“结算全部”或“批量结算按钮”
        message = clearAll(institutionUser);
        edit = null;
      }
      nPage = institutionUerService.searchPageByVo(institutionUser, nPage);
      if (nPage != null) { // 将新的分页对象转化为原有的分页对象传回页面
        page.setDataList(nPage.getRecordList());
        page.setRecordCount(nPage.getTotalRecords());
      }

    } catch (Exception e) {
      logger.error("分页查询引荐注册列表报错", e);
    }
    return "success";
  }

  /**
   * 结算方法 add by songmiao 2016-3-28
   * 
   * @return String @exception
   */
  public String clearAll(InstitutionUser institutionUser) throws Exception {
    if (institutionUser == null) {
      institutionUser = new InstitutionUser();
    }
    String resultStr = "结算失败"; // 页面返回提示
    if (clearingType == null || !clearingType.equals("0")) {
      // 批量结算，结算页面选中id
      List<String> l = new ArrayList<>();
      Collections.addAll(l, ids.split(","));
      institutionUser.setIds(l);
    }
    int result = clearingService.allClearing(institutionUser);
    if (result == 1) resultStr = "结算成功";
    return resultStr;
  }

  public void disClear() {
    message = "0";
    boolean flag;
    Map jsonMap = new HashMap();
    String title = "";
    try {
      if (institutionUser.getId() != 0) {
        institutionUser.setClearingStatus(Short.valueOf("2"));// 设置状态禁止结算
        institutionUerService.updateById(institutionUser);
        title = "禁止结算成功！";
        flag = true;
      } else {// 批量结算
        institutionUerService.unClearByIds(ids);
        title = "禁止结算成功！";
        flag = true;
      }

    } catch (Exception e) {
      flag = false;
      logger.error("禁止结算操作异常", e);
    }
    jsonMap.put("success", flag);
    jsonMap.put("msg", title);

    writeJson(jsonMap);
  }


  @Override
  public Object getModel() {
    // TODO Auto-generated method stub
    return null;
  }

  public InstitutionUser getInstitutionUser() {
    return institutionUser;
  }

  public void setInstitutionUser(InstitutionUser institutionUser) {
    this.institutionUser = institutionUser;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getEdit() {
    return edit;
  }

  public void setEdit(String edit) {
    this.edit = edit;
  }

  public String getIds() {
    return ids;
  }

  public void setIds(String ids) {
    this.ids = ids;
  }

  public String getClearingType() {
    return clearingType;
  }

  public void setClearingType(String clearingType) {
    this.clearingType = clearingType;
  }



}
