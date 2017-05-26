package com.kmzyc.promotion.presell.service;

import java.util.List;

import com.kmzyc.promotion.app.vobject.PromotionPresell;
import com.kmzyc.promotion.app.vobject.PromotionPresellCriteria;



public interface PresellProductAuditService {

  /**
   * @查询预售审核列表信息
   * @param PromotionPresellCriteria promotionPresellCriteria
   * @throws Exception
   */
  public List<PromotionPresell> queryAuditPresellList(
      PromotionPresellCriteria promotionPresellCriteria) throws Exception;

  /**
   * @查询预售审核列表数量
   * @param PromotionPresellCriteria promotionPresellCriteria
   * @throws Exception
   */
  public Integer queryAuditPresellCount(PromotionPresellCriteria promotionPresellCriteria)
      throws Exception;

  /**
   * @查询预售产品详情信息
   * @param Map map
   * @throws Exception
   */
  public List<PromotionPresell> queryProductDetailList(List<PromotionPresell> list)
      throws Exception;

  /**
   * @更新预售产品信心
   * @param PromotionPresellCriteria promotionPresellCriteria
   * @throws Exception
   */
  public int updateAuditPresell(PromotionPresellCriteria promotionPresellCriteria) throws Exception;

}
