/**
 * 
 */
/**
 * @author KM
 *
 */
package com.kmzyc.promotion.presell.service;

import java.util.List;

import com.kmzyc.promotion.app.vobject.PromotionPresell;
import com.kmzyc.promotion.app.vobject.PromotionPresellProduct;

public interface PresellInfoService {

  /**
   * 
   * 保存预售规则及预售产品信息
   *
   * @author Administrator
   * @param promotionPresell
   * @param listPresellProducts
   * @return
   * @throws Exception
   */
  Long savePresellRuleProduct(PromotionPresell promotionPresell,
      List<PromotionPresellProduct> listPresellProducts) throws Exception;

  /**
   * 
   * 通过预售id查询预售详细信息
   *
   * @author Administrator
   * @param presellId
   * @return
   * @throws Exception
   */
  public PromotionPresell findPresellInfoDetailById(Long presellId) throws Exception;

  /**
   * 
   * 修改预售规则
   *
   * @author Administrator
   * @param promotionPresell
   * @param listPresellProducts
   * @return
   * @throws Exception
   */
  public void updatePresellRule(PromotionPresell promotionPresell) throws Exception;

  /**
   * 
   * 查询正在参加的预售商品及参加了商家活动中心报名（促销推广、图文推广、渠道推广）的商品
   *
   * @author Administrator
   * @param skuIds
   * @return
   */
  public int selectPresellAndActivityProductCount(List<Long> skuIds) throws Exception;

}
