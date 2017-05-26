package com.pltfm.app.action;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ModelDriven;
import com.pltfm.app.dataobject.BnesCustomerTypeDO;
import com.pltfm.app.service.BnesCustomerTypeService;
import com.pltfm.app.vobject.BnesCustomerTypeQuery;
import com.pltfm.sys.model.SysUser;

@Component(value = "custDefinedAciton")
@Scope(value = "prototype")
public class CustDefinedAciton extends BaseAction implements ModelDriven {
  /**
   * 
   */
  private static final long serialVersionUID = 625797255648700063L;

  @Resource(name = "bnesCustomerTypeService")
  private BnesCustomerTypeService bnesCustomerTypeService;
  private BnesCustomerTypeQuery bnesCustomerTypeQuery;

  private BnesCustomerTypeDO bnesCustomerTypeDO;

  private List<BnesCustomerTypeQuery> list;

  private List<Integer> customerTypeId;
  private String RtnMessage = "ok";
  private String isParent;



  /**
   * @throws Exception 查询客户类别
   * 
   * @param @return String @exception
   */

  public String showCustType() throws Exception {
    list = bnesCustomerTypeService.findListByExample(bnesCustomerTypeQuery);

    return "list";
  }

  /**
   * 查询客户类别明细
   * 
   * @throws Exception @param @return String @exception
   */
  public String findCustType() throws Exception {

    bnesCustomerTypeQuery = bnesCustomerTypeService
        .findBnesCustomerTypeDOByPrimaryKey(bnesCustomerTypeQuery.getCustomerTypeId());

    List<BnesCustomerTypeQuery> lists =
        bnesCustomerTypeService.findParentList(bnesCustomerTypeQuery.getCustomerTypeId());
    if (lists != null && lists.size() > 0) {
      isParent = "1";
    } else {
      isParent = "0";
    }

    return "custDeList";

  }

  /**
   * 修改客户类别
   * 
   * @throws Exception @param @return String @exception
   */
  public String editCustType() throws Exception {
    // DatetimeUtil.getCalendarInstance().getTime()
   bnesCustomerTypeService.updateBnesCustomerTypeDO(bnesCustomerTypeQuery);

    setRtnMessage("updateOK");
    bnesCustomerTypeQuery = new BnesCustomerTypeQuery();
    return this.showCustType();
  }


  /**
   * @throws Exception 删除客户类别
   * 
   * @throws Exception @param @return String @exception
   */

  public String deleteCustType() throws Exception {

    bnesCustomerTypeService
        .deleteBnesCustomerTypeDOByPrimaryKey(bnesCustomerTypeQuery.getCustomerTypeId());
    bnesCustomerTypeQuery = new BnesCustomerTypeQuery();
    return this.showCustType();
  }

  /**
   * 修改客户类别
   * 
   * @throws Exception @param @return String @exception
   */
  public String adddCustType() throws Exception {

    bnesCustomerTypeQuery.setParentId(bnesCustomerTypeQuery.getCustomerTypeId());
    bnesCustomerTypeQuery.setName("");
    bnesCustomerTypeQuery.setDescription("");
    bnesCustomerTypeQuery.setSortno("");
    if (bnesCustomerTypeQuery.getParentId() == null) {
      return "addTopSuccess";

    } else {
      return "addSuccess";
    }
  }

  /**
   * 保存客户类别
   * 
   * @throws Exception @param @return String @exception
   */
  public String saveCutType() throws Exception {

    SysUser user = (SysUser) session.get("sysUser");
    bnesCustomerTypeQuery.setCreatedId(user.getUserId());
    bnesCustomerTypeQuery.setModifieId(user.getUserId());
    if (bnesCustomerTypeQuery.getParentId() == null) {
      bnesCustomerTypeQuery.setParentId(0);
    }
    bnesCustomerTypeService.insertBnesCustomerTypeDO(bnesCustomerTypeQuery);
    bnesCustomerTypeQuery = new BnesCustomerTypeQuery();
    return this.showCustType();
  }

  /**
   * 校验客户类别名称重复
   * 
   * @throws Exception
   * 
   */
  public void checkCustTypeName() throws Exception {
    // System.err.println("----------------------bnesCustomerTypeQuery.getName():------"+bnesCustomerTypeQuery.getName()+"--------------"+bnesCustomerTypeQuery.getParentId());
    super.writeJson(bnesCustomerTypeService.findListByExample(bnesCustomerTypeQuery).size());
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

  @Override
  public Object getModel() {
    // TODO Auto-generated method stub

    bnesCustomerTypeQuery = new BnesCustomerTypeQuery();
    return bnesCustomerTypeQuery;
  }

}
