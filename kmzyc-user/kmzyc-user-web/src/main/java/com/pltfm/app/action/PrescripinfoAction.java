package com.pltfm.app.action;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.kmzyc.commons.page.Page;
import com.kmzyc.zkconfig.ConfigurationUtil;
import com.opensymphony.xwork2.ModelDriven;
import com.pltfm.app.service.PurchaseListDetailService;
import com.pltfm.app.service.PurchaseListService;
import com.pltfm.app.vobject.PurchaseListDO;
import com.pltfm.app.vobject.PurchaseListDetailDO;

@SuppressWarnings("rawtypes")
@Component(value = "prescripinfoAction")
@Scope(value = "prototype")
public class PrescripinfoAction extends BaseAction implements ModelDriven {

  /**
   * 
   */

  private static final long serialVersionUID = 3042614399737615313L;

  private static Logger logger = LoggerFactory.getLogger(PrescripinfoAction.class);
  private PurchaseListDO purchaseListDO;

  private List<PurchaseListDetailDO> listPurchaseListDetailDO;
  private static final String prescripinfoImg =
      ConfigurationUtil.getString("prescripinfoImg");



  @Resource(name = "purchaseListService")
  private PurchaseListService purchaseListService;

  @Resource(name = "purchaseListDetailService")
  private PurchaseListDetailService purchaseListDetailService;

  private BigDecimal purchaseDetailId;

  // private BigDecimal dProductPrice;

  // private BigDecimal dProductCount;



  /**
   * 
   * 查询按方抓药信息
   * 
   */


  public String selectPageList() {

    try {
      if (page == null) {
        page = new Page();
      }
      purchaseListDO.setType((short) 2);
      Integer count = purchaseListService.selectListPurchaseListCount(purchaseListDO);
      page.setRecordCount(count);
      purchaseListDO.setSkip(page.getStartIndex());
      purchaseListDO.setMax(page.getStartIndex() + page.getPageSize());
      List list = purchaseListService.selectListPurchaseListDO(purchaseListDO);
      page.setDataList(list);


    } catch (Exception e) {
      logger.error("查询按方抓药信息异常" + e.getMessage(), e);
    }

    return "veriPageList";
  }


  /**
   * 
   * 修改按方抓药详细信息数量
   * 
   */

  public String updatePurchaseDes() {
    PurchaseListDetailDO purchaseListDetailDO = new PurchaseListDetailDO();
    purchaseListDetailDO.setPurchaseDetailId(purchaseDetailId);
    BigDecimal productPrices = purchaseListDO.getdProductPrice();
    BigDecimal productCounts = purchaseListDO.getdProductCount();
    BigDecimal counts = productPrices.multiply(productCounts);

    purchaseListDetailDO.setProductPrice(productPrices);
    purchaseListDetailDO.setProductCount(productCounts);
    purchaseListDetailDO.setAmount(counts);
    purchaseListDetailService.updatePurchaseListDetailDO(purchaseListDetailDO);

    return toUpdatePurchaseInfo();
  }


  /**
   * 
   * 查询按方抓药详细信息
   * 
   */

  public String toUpdatePurchaseInfo() {
    try {
      BigDecimal purchaseId = purchaseListDO.getPurchaseId();
      purchaseListDO = purchaseListService.findPurchaseListDOByPrimaryKey(purchaseId);
      String presUlr = prescripinfoImg + purchaseListDO.getPresUlr();
      purchaseListDO.setPresUlr(presUlr);
      PurchaseListDetailDO purchaseListDetailDOs = new PurchaseListDetailDO();
      purchaseListDetailDOs.setPurchaseId(purchaseId);
      listPurchaseListDetailDO = purchaseListDetailService.findListByExample(purchaseListDetailDOs);
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      logger.error("查询按方抓药信息异常" + purchaseListDO.getPurchaseId() + e.getMessage(), e);
    }
    return "updatePurchaseInfo";
  }

  public String deleteBypurchaseDetailId() {
purchaseListDetailService.deletePurchaseListDetailDOByPrimaryKey(purchaseDetailId);
    return toUpdatePurchaseInfo();

  }

  public String toNoPassPres() {

    purchaseListDO.setPurchaseId(purchaseListDO.getPurchaseId());
    return "noPassPurchaseInfo";
  }

  public String noPassPres() {
    try {
 purchaseListService.updatePurchaseListDO(purchaseListDO);
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      logger.error("按方抓药审核异常" + purchaseListDO.getPurchaseId() + e.getMessage(), e);
    }
    purchaseListDO = new PurchaseListDO();
    return selectPageList();

  }

  public String purchaseInfoPass() {

    try {
      purchaseListDO.setPresStatus((short) 2);
purchaseListService.updatePurchaseListDO(purchaseListDO);
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      logger.error("按方抓药审核异常" + purchaseListDO.getPurchaseId() + e.getMessage(), e);
    }
    purchaseListDO = new PurchaseListDO();
    return selectPageList();
  }

  /**
   * 
   * 删除按方抓药信息
   * 
   */
  public String deletePurchaseInfo() {
    try {

          purchaseListService.deletePurchaseListDOByPrimaryKey(purchaseListDO.getPurchaseId());
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      logger.error("按方抓药删除异常" + purchaseListDO.getPurchaseId() + e.getMessage(), e);
    }

    return selectPageList();
  }

  /**
   * 
   * 修改按方抓药信息
   * 
   */

  public String updatePurchaseInfo() {
    // System.out.println("----"+purchaseListDO.getPurchaseId()+"-----");
    try {
 purchaseListService.updatePurchaseListDO(purchaseListDO);
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      logger.error("按方抓药修改异常" + purchaseListDO.getPurchaseId() + e.getMessage(), e);
    }

    return toUpdatePurchaseInfo();

  }

  @Override
public Page getPage() {
    return page;
  }

  @Override
public void setPage(Page page) {
    this.page = page;
  }

  public PurchaseListDO getPurchaseListDO() {
    return purchaseListDO;
  }

  public void setPurchaseListDO(PurchaseListDO purchaseListDO) {
    this.purchaseListDO = purchaseListDO;
  }

  public PurchaseListService getPurchaseListService() {
    return purchaseListService;
  }

  public void setPurchaseListService(PurchaseListService purchaseListService) {
    this.purchaseListService = purchaseListService;
  }

  public List<PurchaseListDetailDO> getListPurchaseListDetailDO() {
    return listPurchaseListDetailDO;
  }

  public void setListPurchaseListDetailDO(List<PurchaseListDetailDO> listPurchaseListDetailDO) {
    this.listPurchaseListDetailDO = listPurchaseListDetailDO;
  }

  public PurchaseListDetailService getPurchaseListDetailService() {
    return purchaseListDetailService;
  }

  public void setPurchaseListDetailService(PurchaseListDetailService purchaseListDetailService) {
    this.purchaseListDetailService = purchaseListDetailService;
  }

  public BigDecimal getPurchaseDetailId() {
    return purchaseDetailId;
  }

  public void setPurchaseDetailId(BigDecimal purchaseDetailId) {
    this.purchaseDetailId = purchaseDetailId;
  }

  private Page page;

  @Override
  public Object getModel() {
    // TODO Auto-generated method stub
    purchaseListDO = new PurchaseListDO();
    return purchaseListDO;
  }

}
