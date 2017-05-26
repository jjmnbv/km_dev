package com.kmzyc.b2b.dao.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.kmzyc.b2b.dao.PromotionInfoDao;
import com.kmzyc.b2b.model.PromotionInfo;
import com.kmzyc.b2b.model.PromotionProduct;
import com.kmzyc.framework.exception.ServiceException;
import com.km.framework.persistence.impl.DaoImpl;

@Component("promotionInfoDao")
@SuppressWarnings("unchecked")
public class PromotionInfoDaoImpl extends DaoImpl implements PromotionInfoDao {
  private static Logger logger= LoggerFactory.getLogger(PromotionInfoDao.class);
  @Resource
  private SqlMapClient sqlMapClient;

  @Override
  public List<PromotionInfo> getPromotionInfoList(Map<String, Object> map) {
    // 需同步到product中
    try {
      return sqlMapClient.queryForList("PromotionInfo.findPromotionNew", map);
    } catch (SQLException e) {
      // Auto-generated catch block
      logger.error(e.getMessage(),e);
    }
    return null;
  }

  @Override
  public PromotionProduct getPromotionProduct(Long skuId, Long promotionId) {
    try {
      Map<String, Object> map = new HashMap<String, Object>();
      map.put("productSkuId", skuId);
      map.put("promotionId", promotionId);
      return (PromotionProduct) sqlMapClient.queryForObject("PromotionInfo.findPromotionProduct",
          map);
    } catch (SQLException e) {
      logger.error(e.getMessage(),e);
    }
    return null;
  }

  public BigDecimal getSalePriceProduct(Long skuId, Long promotionId) {
    PromotionProduct pp = getPromotionProduct(skuId, promotionId);
    if (pp == null) return null;
    if (pp.getPrice() == null || 0 == pp.getPrice().intValue()) return null;
    return pp.getPrice();
  }

  public PromotionInfo findById(Long id) {
    try {
      return (PromotionInfo) sqlMapClient.queryForObject("PromotionInfo.findPromotionById", id);
    } catch (SQLException e) {
      logger.error(e.getMessage(),e);
      return null;
    }
  }

  /**
   * 根据SKUID查询限购活动
   * 
   * @param skuCode
   * @return
   */
  public List<PromotionInfo> queryPurchasePromotionBySku(String skuCode) throws SQLException {
    try {
      return sqlMapClient.queryForList("PromotionInfo.queryPurchasePromotionBySku", skuCode);
    } catch (Exception e) {
      throw new SQLException("根据SKUID查询限购活动出错", e);
    }
  }

  /**
   * 查询商家有效活动
   * 
   * @param map
   * @return
   * @throws SQLException
   */
  public List<PromotionInfo> queryAblePromotionsIds(Map<String, Object> map) throws SQLException {
    try {
      return sqlMapClient.queryForList("PromotionInfo.SQL_QUERY_ABLE_PROMOTION_BY_SELLER", map);
    } catch (Exception e) {
      throw new SQLException("查询商家有效活动出错", e);
    }
  }

  /**
   * 根据商家类型查询有效活动
   * 
   * @param map
   * @return
   * @throws SQLException
   */
  public List<PromotionInfo> queryAblePromotionsByType(Map<String, Object> map) throws SQLException {
    try {
      return sqlMapClient.queryForList("PromotionInfo.SQL_QUERY_ABLE_PROMOTION_BY_TYPE", map);
    } catch (Exception e) {
      throw new SQLException("根据商家类型查询有效活动出错", e);
    }
  }

  /**
   * 查询已改变类型的商家
   * 
   * @param sellerIds
   * @return
   * @throws ServiceException
   */
  public List<Map<String, Object>> queryRetypeSeller(List<Long> sellerIds) throws SQLException {
    try {
      return (List<Map<String, Object>>) sqlMapClient.queryForList(
          "PromotionInfo.SQL_QUERY_RETYP_ESELLER", sellerIds);
    } catch (Exception e) {
      throw new SQLException("查询已改变类型的商家", e);
    }
  }
}
