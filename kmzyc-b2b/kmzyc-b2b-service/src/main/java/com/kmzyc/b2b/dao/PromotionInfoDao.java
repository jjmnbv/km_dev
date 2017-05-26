package com.kmzyc.b2b.dao;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.kmzyc.b2b.model.PromotionInfo;
import com.kmzyc.b2b.model.PromotionProduct;
import com.kmzyc.framework.exception.ServiceException;
import com.km.framework.persistence.Dao;

public interface PromotionInfoDao extends Dao {

  /**
   * 新方法获取已发布的促销信息 2013-11-11
   * 
   */
  public List<PromotionInfo> getPromotionInfoList(Map<String, Object> map);

  public PromotionProduct getPromotionProduct(Long skuId, Long promotionId);

  /** 获取特价价格 返回null 说明该商品的没有参加该特价活动 */
  public BigDecimal getSalePriceProduct(Long skuId, Long promotionId);

  public PromotionInfo findById(Long id);

  /**
   * 根据SKUID查询限购活动
   * 
   * @param skuid
   * @return
   */
  public List<PromotionInfo> queryPurchasePromotionBySku(String skuCode) throws SQLException;

  /**
   * 查询商家有效活动
   * 
   * @param map
   * @return
   * @throws SQLException
   */
  public List<PromotionInfo> queryAblePromotionsIds(Map<String, Object> map) throws SQLException;

  /**
   * 根据商家类型查询有效活动
   * 
   * @param map
   * @return
   * @throws SQLException
   */
  public List<PromotionInfo> queryAblePromotionsByType(Map<String, Object> map) throws SQLException;

  /**
   * 查询已改变类型的商家
   * 
   * @param sellerIds
   * @return
   * @throws ServiceException
   */
  public List<Map<String, Object>> queryRetypeSeller(List<Long> sellerIds) throws SQLException;
}
