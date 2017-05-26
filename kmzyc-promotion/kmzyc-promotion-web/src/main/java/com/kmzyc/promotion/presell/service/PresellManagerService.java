package com.kmzyc.promotion.presell.service;

import java.util.List;

import com.kmzyc.promotion.app.vobject.PromotionPresell;
import com.kmzyc.promotion.app.vobject.PromotionPresellCriteria;



public interface PresellManagerService {

  /**
   * 查询预售活动列表信息
   * 
   * @param Map （查询条件）
   * @throws Exception
   */
  public List<PromotionPresell> queryPresellManagerList(
      PromotionPresellCriteria promotionPresellCriteria) throws Exception;

  /**
   * 查询预售活动列表数量
   * 
   * @param PromotionPresellCriteria promotionPresellCriteria
   * @throws Exception
   */
  public Integer queryPresellManagerCount(PromotionPresellCriteria promotionPresellCriteria)
      throws Exception;


  /**
   * 查询预售产品详情信息
   * 
   * @param Map map
   * @throws Exception
   */
  public List<PromotionPresell> queryProductDetailList(List<PromotionPresell> list)
      throws Exception;


  /**
   * 预售活动提审
   * 
   * @param promotionPresellCriteria 预售活动对象
   * @throws Exception
   */
  public void submitPresellApp(PromotionPresellCriteria promotionPresellCriteria) throws Exception;



  /**
   * 预售活动删除
   * 
   * @param presellId （预售活动id）
   * @throws Exception
   */
  public void deletePresellInfo(Long presellId) throws Exception;

  /**
   * 预售活动撤销审批
   * 
   * @param presellId （预售活动id）
   * @throws Exception
   */
  public void cancelPresellApply(Long presellId) throws Exception;

  /**
   * 预售活动终止
   * 
   * @param promotionPresellCriteria 预售活动对象
   * @throws Exception
   */
  public void stopPresell(PromotionPresellCriteria promotionPresellCriteria) throws Exception;



  /**
   * 推送商品价格
   * 
   * @param listSkuids skuidList
   * @throws Exception
   */
  public void sendNormalProductsPrice(List<Long> listSkuids) throws Exception;



}
