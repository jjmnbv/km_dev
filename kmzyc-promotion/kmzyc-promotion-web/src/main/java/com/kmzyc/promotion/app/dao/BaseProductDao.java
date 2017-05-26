package com.kmzyc.promotion.app.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.km.framework.page.Pagination;
import com.kmzyc.promotion.app.vobject.BaseProduct;
import com.kmzyc.promotion.app.vobject.PromotionInfo;

public interface BaseProductDao {
  /**
   * 获取加价购商品价格
   * 
   * @param promotionId
   * @return
   * @throws SQLException
   */
  public List<BaseProduct> getAddPriceCarProductInfoList(Long promotionId) throws SQLException;

  /**
   * 获取赠品
   * 
   * @param promotionId
   * @return
   * @throws SQLException
   */
  public List<BaseProduct> getGiftProduct(Long promotionId) throws SQLException;

  /**
   * 获取单个产品活动、价格、产品详细页
   * 
   * @param skuId
   * @return
   * @throws SQLException
   */
  public BaseProduct getProductPriceInfo(Long skuId) throws SQLException;

  /**
   * 分页查询促销产品价格
   * 
   * @param page
   * @param promotion
   * @return
   * @throws SQLException
   */
  public Pagination getPromotionProduct(Pagination page, PromotionInfo promotion)
      throws SQLException;

  /**
   * 批量获取产品价格信息
   * 
   * @param idList
   * @return
   * @throws SQLException
   */
  public List<BaseProduct> queryBatchProductPriceInfo(List<Long> idList) throws SQLException;

  /**
   * 查询套餐
   * 
   * @param params
   * @return
   * @throws SQLException
   */
  public List<Map<String, String>> queryAbleComposition(Long suitId, Integer amount)
      throws SQLException;

  /**
   * 批量查询套餐
   * 
   * @param params
   * @return
   * @throws SQLException
   */
  public List<Map<String, String>> queryBatchAbleComposition(Map<Long, Integer> suitIds)
      throws SQLException;

  /**
   * 查询活动期间用户购买某产品数量
   * 
   * @param params
   * @return
   * @throws SQLException
   */
  public Integer querySumUserBuySkuNum(Map<String, Object> params) throws SQLException;


  /**
   * 获取产品价格
   * 
   * @param skuId
   * @return
   * @throws SQLException
   */
  public BaseProduct queryProductPrice(Long skuId) throws SQLException;

  /**
   * 批量获取产品价格
   * 
   * @param idList
   * @return
   * @throws SQLException
   */
  public List<BaseProduct> queryBatchProductPrice(List<Long> idList) throws SQLException;
}
