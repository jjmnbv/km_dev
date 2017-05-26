package com.kmzyc.promotion.presell.dao.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.kmzyc.promotion.app.dao.BaseDao;
import com.kmzyc.promotion.app.vobject.PromotionPresell;
import com.kmzyc.promotion.app.vobject.PromotionPresellProduct;
import com.kmzyc.promotion.presell.dao.PresellInfoDao;

@Repository("presellInfoDao")
@SuppressWarnings("unchecked")
public class PresellInfoDaoImpl extends BaseDao<PromotionPresellProduct> implements PresellInfoDao {
  @Resource
  private SqlMapClient sqlMapClient;

  @Override
  public Long insertPresellRule(PromotionPresell promotionPresell) throws SQLException {
    return (Long) sqlMapClient.insert("PROMOTION_PRESELL.insertPresellRule", promotionPresell);

  }

  @Override
  public void batchInsertPresellProduct(List<PromotionPresellProduct> listPresellProducts)
      throws SQLException {
    super.batchInsertData(listPresellProducts, "PROMOTION_PRESELL_PRODUCT.insertPresellProduct");
  }

  @Override
  public PromotionPresell findPresellInfoDetailById(Long presellId) throws SQLException {
    return (PromotionPresell) sqlMapClient.queryForObject(
        "PROMOTION_PRESELL.findPresellInfoDetailById", presellId);
  }

  @Override
  public Integer updatePresellRule(PromotionPresell promotionPresell) throws SQLException {
    return (Integer) sqlMapClient.update("PROMOTION_PRESELL.updatePresellRule", promotionPresell);
  }

  @Override
  public int selectPresellAndActivityProductCount(List<Long> skuIds) throws SQLException {
    return (Integer) sqlMapClient.queryForObject(
        "PROMOTION_PRESELL.selectPresellAndActivityProductCount", skuIds);
  }

}
