package com.kmzyc.b2b.service;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.kmzyc.b2b.model.ProductSku;
import com.kmzyc.b2b.model.Productmain;
import com.kmzyc.b2b.model.PromotionInfo;
import com.kmzyc.b2b.model.PromotionProduct;
import com.kmzyc.b2b.vo.BaseProduct;
import com.kmzyc.framework.exception.ServiceException;

public interface PromotionInfoService {
  static final String PROMOTION_PRICE = "finalPrice";
  static final String PROMOTION_INFO = "promotionInfo";
  static final String PURCHASE_INFO = "purchaseInfo";
  static final String REDUCE_PRICE = "reducePrice";
  static final String PRESELL_PRODUCT_INFO = "presellProductInfo";

  /**
   * 
   * @param sku
   * @return
   * @throws Exception
   */
  public JSONObject getPromotionInfoByProductToJson(ProductSku sku) throws ServiceException,
      Exception;

  /**
   * 重构获取活动接口
   * 
   * @param date
   * @return
   * @data 2013-11-11
   */

  public <T extends BaseProduct> void setProductPricePromotionInfoByDB(T t, Date date)
      throws ServiceException;

  /**
   * 获取活动cms静态地址
   * 
   * @param promotionId
   * @return
   */
  public String getPromotionCmsUrlPath(Long promotionId);

  /** 从所有活动列表中设置商品活动 */
  public <T extends BaseProduct> void setProductPricePromotionInfoByDB(T t,
      List<PromotionInfo> getAllPromotionListNow) throws ServiceException;

  public PromotionInfo getPromotionInfoById(Long promotionId);

  public List<com.kmzyc.b2b.model.CmsPromotionTask> getCmsPromotionTaskList();

  /** 设置规则数据 */
  public void initRuleDatas(PromotionInfo info) throws SQLException;

  public JSONObject getPromotionInfoByProductToJsonForApp(ProductSku sku) throws ServiceException,
      Exception;
}
