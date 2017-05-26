package com.pltfm.app.action;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.kmzyc.commons.page.Page;
import com.opensymphony.xwork2.ModelDriven;
import com.pltfm.app.dataobject.SuppliersInfoDO;
import com.pltfm.app.service.SuppliersInfoService;

@SuppressWarnings("rawtypes")
@Component(value = "supplierInfoAction")
@Scope(value = "prototype")
public class SupplierInfoAction extends BaseAction implements ModelDriven {



  /**
   * 
   */
  private static final long serialVersionUID = 8355516423880449239L;

  /** 分页对象 **/
  private Page page;
  private List<SuppliersInfoDO> listSuppliersInfoDO;



  @Resource(name = "suppliersInfoService")
  private SuppliersInfoService suppliersInfoService;
  private SuppliersInfoDO suppliersInfoDO;
  private String callBack;



  /**
   * 分页查询信息
   */
  public String queryPageList() {
    try {
      if (page == null) {
        page = new Page();
        page.setPageSize(10);
      }
      // page.setPageSize(10);
      page = suppliersInfoService.queryForPageList(suppliersInfoDO, page);
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return "pageList";

  }


  public String getCallBack() {
    return callBack;
  }

  public void setCallBack(String callBack) {
    this.callBack = callBack;
  }

  public SuppliersInfoDO getSuppliersInfoDO() {
    return suppliersInfoDO;
  }

  public void setSuppliersInfoDO(SuppliersInfoDO suppliersInfoDO) {
    this.suppliersInfoDO = suppliersInfoDO;
  }

  @Override
public Page getPage() {
    return page;
  }

  @Override
public void setPage(Page page) {
    this.page = page;
  }

  public List<SuppliersInfoDO> getListSuppliersInfoDO() {
    return listSuppliersInfoDO;
  }

  public void setListSuppliersInfoDO(List<SuppliersInfoDO> listSuppliersInfoDO) {
    this.listSuppliersInfoDO = listSuppliersInfoDO;
  }

  public SuppliersInfoService getSuppliersInfoService() {
    return suppliersInfoService;
  }

  public void setSuppliersInfoService(SuppliersInfoService suppliersInfoService) {
    this.suppliersInfoService = suppliersInfoService;
  }

  @Override
  public Object getModel() {
    // TODO Auto-generated method stub
    suppliersInfoDO = new SuppliersInfoDO();
    return suppliersInfoDO;
  }

}
