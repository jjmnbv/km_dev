package com.kmzyc.promotion.presell.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.kmzyc.promotion.app.vobject.PromotionPresell;
import com.kmzyc.promotion.app.vobject.PromotionPresellCriteria;
import com.kmzyc.promotion.presell.dao.PresellProductAuditDao;

@Repository("presellProductAuditDao")
@SuppressWarnings("unchecked")
public class PresellProductAuditDaoimpl implements PresellProductAuditDao {

  @Resource
  private SqlMapClient sqlMapClient;

  @Override
  public List<PromotionPresell> queryAuditPresellList(
      PromotionPresellCriteria promotionPresellCriteria) throws Exception {
    return sqlMapClient.queryForList("PROMOTION_PRESELL.queryAuditPresellList",
        promotionPresellCriteria);

  }

  @Override
  public List<PromotionPresell> queryProductDetailList(List<PromotionPresell> list)
      throws Exception {
    return sqlMapClient.queryForList("PROMOTION_PRESELL.queryProductDetailList", list);
  }

  @Override
  public Integer queryAuditPresellCount(PromotionPresellCriteria promotionPresellCriteria)
      throws Exception {

    return (Integer) sqlMapClient.queryForObject("PROMOTION_PRESELL.queryAuditPresellCount",
        promotionPresellCriteria);
  }

  @Override
  public int updateAuditPresell(PromotionPresellCriteria promotionPresellCriteria) throws Exception {
    return sqlMapClient.update("PROMOTION_PRESELL.updateAuditPresell", promotionPresellCriteria);
  }

}
