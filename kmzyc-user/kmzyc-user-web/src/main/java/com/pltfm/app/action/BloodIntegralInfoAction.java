package com.pltfm.app.action;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.kmzyc.commons.page.Page;
import com.opensymphony.xwork2.ModelDriven;
import com.pltfm.app.service.BloodIntegralInfoService;
import com.pltfm.app.util.ConfigureUtils;
import com.pltfm.app.util.Token;
import com.pltfm.app.vobject.BloodIntegralInfo;
import com.pltfm.app.vobject.LoginInfo;

/***
 * 经验明细Action
 */
@Component(value = "bloodIntegralInfoAction")
@Scope(value = "prototype")
public class BloodIntegralInfoAction extends BaseAction implements ModelDriven {
  private static final long serialVersionUID = -579941341825593085L;
  /***
   * 经验明细Service
   */
  @Resource(name = "bloodIntegralInfoService")
  private BloodIntegralInfoService bloodIntegralInfoService;
  /***
   * 经验明细id集合
   */
  private List<Integer> integralInfoIds;
  /***
   * 经验明细实体
   */
  private BloodIntegralInfo bloodIntegralInfo;
  /**
   * 分页类
   */
  private Page page;
  /** 公共页面参数 **/
  private Integer showType;

  /**
   * 经验明细信息列表
   * 
   * @return
   */
  public String pageList() {
    try {
      if (page == null) {
        page = new Page();
      }
      if (showType != null) {
        LoginInfo loginInfo = bloodIntegralInfoService.getLoginName(bloodIntegralInfo.getLoginId());
        bloodIntegralInfo.setLoginAccount(loginInfo.getLoginAccount());
      }
      page = bloodIntegralInfoService.searchPageByVo(page, bloodIntegralInfo);

    } catch (Exception e) {
      // TODO Auto-generated catch block
      this.addActionError(ConfigureUtils.getMessageConfig("integralInfo.query.fail"));
      return "queryFail";
    }
    return "pageSuccess";
  }

  /***
   * 删除经验明细信
   */
  @Token
  public String detele() {
    try {
      bloodIntegralInfoService.delete(integralInfoIds);
      this.addActionMessage(ConfigureUtils.getMessageConfig("integralInfo.delete.success"));
      return this.pageList();
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      this.addActionError(ConfigureUtils.getMessageConfig("integralInfo.delete.fail"));
      return this.pageList();
    }
  }

  @Override
  public Object getModel() {
    bloodIntegralInfo = new BloodIntegralInfo();
    return bloodIntegralInfo;
  }

  public BloodIntegralInfoService getBloodIntegralInfoService() {
    return bloodIntegralInfoService;
  }

  public void setBloodIntegralInfoService(BloodIntegralInfoService bloodIntegralInfoService) {
    this.bloodIntegralInfoService = bloodIntegralInfoService;
  }

  public List<Integer> getIntegralInfoIds() {
    return integralInfoIds;
  }

  public void setIntegralInfoIds(List<Integer> integralInfoIds) {
    this.integralInfoIds = integralInfoIds;
  }

  public BloodIntegralInfo getBloodIntegralInfo() {
    return bloodIntegralInfo;
  }

  public void setBloodIntegralInfo(BloodIntegralInfo bloodIntegralInfo) {
    this.bloodIntegralInfo = bloodIntegralInfo;
  }

  @Override
public Page getPage() {
    return page;
  }

  @Override
public void setPage(Page page) {
    this.page = page;
  }

  public Integer getShowType() {
    return showType;
  }

  public void setShowType(Integer showType) {
    this.showType = showType;
  }
}
