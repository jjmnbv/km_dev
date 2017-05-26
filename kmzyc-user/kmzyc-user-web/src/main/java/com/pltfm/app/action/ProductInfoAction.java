package com.pltfm.app.action;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.kmzyc.commons.page.Page;
import com.opensymphony.xwork2.ModelDriven;
import com.pltfm.app.service.CategoryService;
import com.pltfm.app.service.PurchaseListDetailService;
import com.pltfm.app.vobject.Category;
import com.pltfm.app.vobject.PurchaseListDetailDO;

@SuppressWarnings("rawtypes")
@Component(value = "productInfoAction")
@Scope(value = "prototype")
public class ProductInfoAction extends BaseAction implements ModelDriven {

  /**
   * 
   */
  private static final long serialVersionUID = -7549863419534421396L;
  private static Logger logger = LoggerFactory.getLogger(ProductInfoAction.class);


  private PurchaseListDetailDO purchaseListDetailDO;
  private Page page;
  private List<PurchaseListDetailDO> listPurchaseListDetailDO;
  private List<String> skuCodeLists;
  public String pSkuCodeLists;
  public String supplierIds;
  public String supplierTypes;



  public String getSupplierTypes() {
    return supplierTypes;
  }



  public void setSupplierTypes(String supplierTypes) {
    this.supplierTypes = supplierTypes;
  }



  public String getSupplierIds() {
    return supplierIds;
  }



  public void setSupplierIds(String supplierIds) {
    this.supplierIds = supplierIds;
  }


  public BigDecimal purchaseIds;



  public BigDecimal getPurchaseIds() {
    return purchaseIds;
  }



  public void setPurchaseIds(BigDecimal purchaseIds) {
    this.purchaseIds = purchaseIds;
  }


  // 一级类目id
  private String bcategoryId;
  // 二级类目id
  private String mcategoryId;
  // 三级类目id
  private String tcategoryId;



  // 类目集合
  private List<Category> categoryList;
  // 二级类目集合
  private List<Category> categoryList2 = new ArrayList<Category>();
  // 三级类目
  private List<Category> categoryList3 = new ArrayList<Category>();



  @Resource(name = "categoryService")
  private CategoryService categoryService;
  @Resource(name = "purchaseListDetailService")
  private PurchaseListDetailService purchaseListDetailService;



  public String insertDetails() {
purchaseListDetailService.insertPurchaseList(skuCodeLists,
        purchaseListDetailDO.getPurchaseId());
    return null;
  }



  // 跳转到 添加除了套餐中的其他类型的关联产品
  public String addProducts() {

    try {
      categoryList = categoryService.selectCategoryById(0L);
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    try {
      if (page == null) {
        page = new Page();
      }

      purchaseIds = this.getPurchaseIds();
      Integer count = purchaseListDetailService.countListByPurchaseList(purchaseListDetailDO);
      page.setRecordCount(count);
      purchaseListDetailDO.setSkip(page.getStartIndex());
      purchaseListDetailDO.setMax(page.getStartIndex() + page.getPageSize());
      listPurchaseListDetailDO =
          purchaseListDetailService.findListByPurchaseList(purchaseListDetailDO);

      page.setDataList(listPurchaseListDetailDO);


    } catch (Exception e) {
      logger.error("查询按方抓药信息异常" + e.getMessage(), e);
    }
    return "addProducts";

  }

  // 抽奖添加产品

  // 跳转到 添加除了套餐中的其他类型的关联产品
  public String addLuckProducts() {
    pSkuCodeLists = "t" + this.getpSkuCodeLists().replaceAll("\\|", " ");
    try {
      categoryList = categoryService.selectCategoryById(0L);
      if (StringUtils.isNotEmpty(bcategoryId)) {
        categoryList2 = categoryService.selectCategoryById(Long.parseLong(bcategoryId));
        if (StringUtils.isNotEmpty(mcategoryId)) {
          categoryList3 = categoryService.selectCategoryById(Long.parseLong(mcategoryId));
        }
      }


      // System.out.println("----------bcategoryId---"+bcategoryId+"-----------mcategoryId--"+mcategoryId+"----------tcategoryId----"+tcategoryId);
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    try {
      if (page == null) {
        page = new Page();
      }

      purchaseIds = this.getPurchaseIds();
      if (!supplierIds.equals("")) {
        purchaseListDetailDO.setShopCode(supplierIds);
      }
      // System.out.println("----supplierTypes---"+supplierTypes);

      if (supplierTypes.endsWith("2")) {
        purchaseListDetailDO.setSupplierType("1,3");
      } else if (supplierTypes.endsWith("3") || supplierTypes.endsWith("4")) {
        purchaseListDetailDO.setSupplierType("2");
      }
      Integer count = purchaseListDetailService.countListByPurchaseList(purchaseListDetailDO);
      page.setRecordCount(count);
      purchaseListDetailDO.setSkip(page.getStartIndex());
      purchaseListDetailDO.setMax(page.getStartIndex() + page.getPageSize());
      listPurchaseListDetailDO =
          purchaseListDetailService.findListByPurchaseList(purchaseListDetailDO);

      page.setDataList(listPurchaseListDetailDO);


    } catch (Exception e) {
      logger.error("查询按方抓药信息异常" + e.getMessage(), e);
    }
    return "addLuckProducts";

  }


  // ajax加载类目下拉框
  public void initCategory() {
    try {
      // 查询category下的子类目 categoryId
      categoryList2 = categoryService.selectCategoryById(purchaseListDetailDO.getCategoryId());
      super.writeJson(categoryList2);
      logger.info("获取类目成功" + categoryList2.size());
    } catch (Exception e) {
      logger.error("获取类目异常" + e.getMessage(), e);
    }
  }

  @Override
  public Object getModel() {
    // TODO Auto-generated method stub
    purchaseListDetailDO = new PurchaseListDetailDO();
    return purchaseListDetailDO;
  }


  public List<PurchaseListDetailDO> getListPurchaseListDetailDO() {
    return listPurchaseListDetailDO;
  }


  public void setListPurchaseListDetailDO(List<PurchaseListDetailDO> listPurchaseListDetailDO) {
    this.listPurchaseListDetailDO = listPurchaseListDetailDO;
  }


  @Override
public Page getPage() {
    return page;
  }


  @Override
public void setPage(Page page) {
    this.page = page;
  }


  public PurchaseListDetailDO getPurchaseListDetailDO() {
    return purchaseListDetailDO;
  }


  public void setPurchaseListDetailDO(PurchaseListDetailDO purchaseListDetailDO) {
    this.purchaseListDetailDO = purchaseListDetailDO;
  }

  public String getpSkuCodeLists() {
    return pSkuCodeLists;
  }



  public void setpSkuCodeLists(String pSkuCodeLists) {
    this.pSkuCodeLists = pSkuCodeLists;
  }

  public String getTcategoryId() {
    return tcategoryId;
  }


  public void setTcategoryId(String tcategoryId) {
    this.tcategoryId = tcategoryId;
  }

  public String getBcategoryId() {
    return bcategoryId;
  }


  public void setBcategoryId(String bcategoryId) {
    this.bcategoryId = bcategoryId;
  }


  public String getMcategoryId() {
    return mcategoryId;
  }


  public void setMcategoryId(String mcategoryId) {
    this.mcategoryId = mcategoryId;
  }

  public List<Category> getCategoryList2() {
    return categoryList2;
  }


  public void setCategoryList2(List<Category> categoryList2) {
    this.categoryList2 = categoryList2;
  }


  public List<Category> getCategoryList3() {
    return categoryList3;
  }


  public void setCategoryList3(List<Category> categoryList3) {
    this.categoryList3 = categoryList3;
  }

  public CategoryService getCategoryService() {
    return categoryService;
  }


  public void setCategoryService(CategoryService categoryService) {
    this.categoryService = categoryService;
  }


  public List<Category> getCategoryList() {
    return categoryList;
  }


  public void setCategoryList(List<Category> categoryList) {
    this.categoryList = categoryList;
  }


  public PurchaseListDetailService getPurchaseListDetailService() {
    return purchaseListDetailService;
  }


  public void setPurchaseListDetailService(PurchaseListDetailService purchaseListDetailService) {
    this.purchaseListDetailService = purchaseListDetailService;
  }

  public List<String> getSkuCodeLists() {
    return skuCodeLists;
  }


  public void setSkuCodeLists(List<String> skuCodeLists) {
    this.skuCodeLists = skuCodeLists;
  }



}
