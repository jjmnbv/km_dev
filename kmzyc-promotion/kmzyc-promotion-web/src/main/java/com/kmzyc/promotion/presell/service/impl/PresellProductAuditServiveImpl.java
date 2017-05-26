package com.kmzyc.promotion.presell.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.kmzyc.promotion.app.vobject.PromotionPresell;
import com.kmzyc.promotion.app.vobject.PromotionPresellCriteria;
import com.kmzyc.promotion.presell.dao.PresellProductAuditDao;
import com.kmzyc.promotion.presell.service.PresellProductAuditService;


@Repository("presellProductAuditServive")
public class PresellProductAuditServiveImpl implements PresellProductAuditService {
  private Logger logger = LoggerFactory.getLogger(PresellProductAuditServiveImpl.class);

  @Resource
  private PresellProductAuditDao presellProductAuditDao;

  @Override
  public List<PromotionPresell> queryAuditPresellList(
      PromotionPresellCriteria promotionPresellCriteria) throws Exception {
    return presellProductAuditDao.queryAuditPresellList(promotionPresellCriteria);
  }

  @Override
  public List<PromotionPresell> queryProductDetailList(List<PromotionPresell> list)
      throws Exception {
    return presellProductAuditDao.queryProductDetailList(list);

  }

  @Override
  public Integer queryAuditPresellCount(PromotionPresellCriteria promotionPresellCriteria)
      throws Exception {
    return presellProductAuditDao.queryAuditPresellCount(promotionPresellCriteria);
  }

  @Override
  public int updateAuditPresell(PromotionPresellCriteria promotionPresellCriteria) throws Exception {
    return presellProductAuditDao.updateAuditPresell(promotionPresellCriteria);
  }

}
