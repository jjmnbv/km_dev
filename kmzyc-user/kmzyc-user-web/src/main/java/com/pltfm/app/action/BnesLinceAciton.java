package com.pltfm.app.action;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ModelDriven;
import com.pltfm.app.dataobject.BnesCustomerTypeDO;
import com.pltfm.app.service.BnesCustomerTypeService;
import com.pltfm.app.service.BnesFunctionsService;
import com.pltfm.app.service.BnesResPermissionService;
import com.pltfm.app.util.DateTimeUtils;
import com.pltfm.app.util.ListUtils;
import com.pltfm.app.vobject.BnesCustomerTypeQuery;
import com.pltfm.app.vobject.BnesFunctionsQuery;
import com.pltfm.app.vobject.BnesResPermissionQuery;
import com.pltfm.sys.model.SysUser;

@Component(value = "bnesLinceAciton")
@Scope(value = "prototype")
public class BnesLinceAciton extends BaseAction implements ModelDriven {
  /**
   * 
   */
  private static final long serialVersionUID = 625797255648700063L;

  @Resource(name = "bnesCustomerTypeService")
  private BnesCustomerTypeService bnesCustomerTypeService;
  @Resource(name = "bnesFunctionsService")
  private BnesFunctionsService bnesFunctionsService;
  @Resource(name = "bnesResPermissionService")
  private BnesResPermissionService bnesResPermissionService;

  private BnesFunctionsQuery bnesFunctionsQuery;


  private BnesCustomerTypeQuery bnesCustomerTypeQuery;

  private BnesCustomerTypeDO bnesCustomerTypeDO;

  private List<BnesCustomerTypeQuery> list;
  private List<BnesFunctionsQuery> listBnesFunctionsQuery;


  private List<Integer> customerTypeId;
  private String RtnMessage = "ok";
  private String isParent;
  private List<String> levelId;
  private BnesResPermissionQuery bnesResPermissionQuery;
  private List<BnesResPermissionQuery> listbp;



  /**
   * 保存客户类别授权 @throws Exception
   * 
   * 
   * @param @return String @exception
   */

  public String saveBnesLinces() throws Exception {


    String CustomerTypeIds = httpServletRequest.getParameter("customerTypeId");
    int count = 0;

    if (StringUtils.isNotBlank(CustomerTypeIds)) {
      SysUser sysuser = (SysUser) session.get("sysUser");
      Date dates = DateTimeUtils.getCalendarInstance().getTime();
      List<BnesCustomerTypeQuery> ls =
          bnesCustomerTypeService.findListBnesCustTypeById(Integer.valueOf(CustomerTypeIds));



      bnesResPermissionService
          .deleteBnesResPermissionDOByPrimaryKey(Integer.valueOf(CustomerTypeIds));
      if (ListUtils.isNotEmpty(levelId)) {
        for (BnesCustomerTypeQuery bq : ls) {
          for (String id : levelId) {
            BnesResPermissionQuery bnesResPermissionQuery = new BnesResPermissionQuery();
            bnesResPermissionQuery.setCustomerTypeId(bq.getCustomerTypeId());
            bnesResPermissionQuery.setBinesFunctionId(Integer.valueOf(id));
            bnesResPermissionQuery.setCreateDate(dates);
            bnesResPermissionQuery.setModifyDate(dates);
            bnesResPermissionQuery.setCreatedId(sysuser.getUserId());
            bnesResPermissionQuery.setModifieId(sysuser.getUserId());
            count += bnesResPermissionService.insertBnesResPermissionDO(bnesResPermissionQuery);

          }
        }
      }
    }



    return this.showBnesLince();
  }

  /**
   * 显示客户类别成员具有的功能清单 @throws Exception
   * 
   * 
   * @param @return String @exception
   */
  public String showBnesLince() throws Exception {
    bnesCustomerTypeQuery = new BnesCustomerTypeQuery();

    list = bnesCustomerTypeService.findListByExample(bnesCustomerTypeQuery);
    listBnesFunctionsQuery = bnesFunctionsService.findListByExample(bnesFunctionsQuery);
    String CustomerTypeIds = httpServletRequest.getParameter("customerTypeId");
    if (StringUtils.isNotBlank(CustomerTypeIds)) {
      listbp = bnesResPermissionService.findListByExample(Integer.valueOf(CustomerTypeIds));
    }
    return "list";
  }


  public String bnesLince() throws Exception {

    listBnesFunctionsQuery = bnesFunctionsService.findListByExample(bnesFunctionsQuery);

    return "funList";
  }

  public BnesResPermissionQuery getBnesResPermissionQuery() {
    return bnesResPermissionQuery;
  }

  public void setBnesResPermissionQuery(BnesResPermissionQuery bnesResPermissionQuery) {
    this.bnesResPermissionQuery = bnesResPermissionQuery;
  }

  public List<BnesResPermissionQuery> getListbp() {
    return listbp;
  }

  public void setListbp(List<BnesResPermissionQuery> listbp) {
    this.listbp = listbp;
  }

  public BnesResPermissionService getBnesResPermissionService() {
    return bnesResPermissionService;
  }

  public void setBnesResPermissionService(BnesResPermissionService bnesResPermissionService) {
    this.bnesResPermissionService = bnesResPermissionService;
  }

  public List<String> getLevelId() {
    return levelId;
  }

  public void setLevelId(List<String> levelId) {
    this.levelId = levelId;
  }

  public String getIsParent() {
    return isParent;
  }

  public void setIsParent(String isParent) {
    this.isParent = isParent;
  }

  public String getRtnMessage() {
    return RtnMessage;
  }

  public void setRtnMessage(String rtnMessage) {
    RtnMessage = rtnMessage;
  }

  public List<Integer> getCustomerTypeId() {
    return customerTypeId;
  }

  public void setCustomerTypeId(List<Integer> customerTypeId) {
    this.customerTypeId = customerTypeId;
  }

  public List<BnesCustomerTypeQuery> getList() {
    return list;
  }

  public void setList(List<BnesCustomerTypeQuery> list) {
    this.list = list;
  }

  public BnesCustomerTypeService getBnesCustomerTypeService() {
    return bnesCustomerTypeService;
  }

  public void setBnesCustomerTypeService(BnesCustomerTypeService bnesCustomerTypeService) {
    this.bnesCustomerTypeService = bnesCustomerTypeService;
  }

  public BnesCustomerTypeDO getBnesCustomerTypeDO() {
    return bnesCustomerTypeDO;
  }

  public void setBnesCustomerTypeDO(BnesCustomerTypeDO bnesCustomerTypeDO) {
    this.bnesCustomerTypeDO = bnesCustomerTypeDO;
  }

  public BnesCustomerTypeQuery getBnesCustomerTypeQuery() {
    return bnesCustomerTypeQuery;
  }

  public void setBnesCustomerTypeQuery(BnesCustomerTypeQuery bnesCustomerTypeQuery) {
    this.bnesCustomerTypeQuery = bnesCustomerTypeQuery;
  }

  public BnesFunctionsQuery getBnesFunctionsQuery() {
    return bnesFunctionsQuery;
  }

  public void setBnesFunctionsQuery(BnesFunctionsQuery bnesFunctionsQuery) {
    this.bnesFunctionsQuery = bnesFunctionsQuery;
  }

  public BnesFunctionsService getBnesFunctionsService() {
    return bnesFunctionsService;
  }



  public void setBnesFunctionsService(BnesFunctionsService bnesFunctionsService) {
    this.bnesFunctionsService = bnesFunctionsService;
  }



  public List<BnesFunctionsQuery> getListBnesFunctionsQuery() {
    return listBnesFunctionsQuery;
  }



  public void setListBnesFunctionsQuery(List<BnesFunctionsQuery> listBnesFunctionsQuery) {
    this.listBnesFunctionsQuery = listBnesFunctionsQuery;
  }

  @Override
  public Object getModel() {
    // TODO Auto-generated method stub

    bnesCustomerTypeQuery = new BnesCustomerTypeQuery();
    return bnesCustomerTypeQuery;
  }

}
