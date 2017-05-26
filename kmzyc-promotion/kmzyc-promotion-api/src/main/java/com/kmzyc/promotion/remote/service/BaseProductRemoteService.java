package com.kmzyc.promotion.remote.service;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.km.framework.page.Pagination;
import com.kmzyc.promotion.app.vobject.BaseProduct;
import com.kmzyc.promotion.app.vobject.PriceInfo;
import com.kmzyc.promotion.app.vobject.PromotionInfo;
import com.kmzyc.promotion.app.vobject.PromotionProduct;

public interface BaseProductRemoteService {

  /**
   * 获取加价购商品价格
   * 
   * @param promotionId活动ID
   * @return
   * @throws Exception
   */
  public List<BaseProduct> getAddPriceCarProductInfoList(Long promotionId) throws Exception;

  /**
   * 获取赠品
   * 
   * @param promotionId活动ID
   * @return
   * @throws Exception
   */
  public List<BaseProduct> getGiftProduct(Long promotionId) throws Exception;

  /**
   * 参加某活动的商品
   * 
   * @param page
   * @param promotion
   * @return
   * @throws Exception
   */
  public Pagination getAppActivityProductList(Pagination page, PromotionInfo promotion)
      throws Exception;

  /**
   * 获取特价产品价格和限购信息
   * 
   * @param skuId产品skuId
   * @param promotionId活动ID
   * @return
   * @throws Exception
   */
  public PromotionProduct getPromotionProductPrice(Long skuId, Long promotionId) throws Exception;

  /**
   * 获取活动产品列表
   * 
   * @param page
   * @param promotion
   * @return
   * @throws Exception
   */
  public Pagination getAppPromotionProductList(Pagination page, PromotionInfo promotion)
      throws Exception;

  /**
   * 计算价格
   * 
   * @param uid 用户ID
   * @param jo 产品集合包括:cpArray普通产品集合，格式:'cpArray':[{'skuId':****,'amount':*},{' s k u I d ' :
   *        ****,'amount':*}] comArray套餐集合，格式:'comArray':[{'comId':****,'amount':
   *        *},{'comId':****,'amount':*}] pmArray活动产品集合，格式:'pmArray':[{'pid',
   *        ***,'rid':***,'skuId':***},{'pid',***,'rid':***,'skuId':***}]
   * @throws Exception
   */
  public JSONObject getcalcPrice(Long uid, JSONObject jo) throws Exception;

  /**
   * 根据SKUID获取缓存价格
   * 
   * @param skuId
   * @return
   * @throws Exception
   */
  public PriceInfo getCachePriceBySkuId(Long skuId) throws Exception;

  /**
   * 根据 渠道号SKUID获取缓存价格(搜索调用)
   * 
   * @param skuId
   * @return
   * @throws Exception
   */
  public PriceInfo getCachePriceBySkuId_New(Long skuId) throws Exception;
}
