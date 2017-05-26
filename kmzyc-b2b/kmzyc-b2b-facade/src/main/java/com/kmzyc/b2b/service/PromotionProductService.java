package com.kmzyc.b2b.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kmzyc.b2b.model.PromotionInfo;
import com.kmzyc.b2b.model.PromotionProduct;
import com.kmzyc.b2b.vo.CarProduct;
import com.kmzyc.framework.exception.ServiceException;
import com.km.framework.page.Pagination;

public interface PromotionProductService {
  public Pagination getPromotionProductList(Pagination page, PromotionInfo promotion);

  /**
   * 获取赠品
   * 
   * @param promotionId活动ID
   * @return
   */
  public Map<Long, CarProduct> getGiftProductByPromotion(Long promotionId) throws ServiceException;

  /**
   * 获取加价购商品
   * 
   * @param prizeData活动规则奖励数据
   */
  public Map<Long, CarProduct> getIncreaseProduct(Long prizeData) throws ServiceException;

  /**
   * 根据sku和活动id获取活动关联商品
   * 
   * @param productSkuId
   * @param promotionId
   * @return
   * @throws ServiceException
   * @throws SQLException
   */
  public List<PromotionProduct> getPromotionProductBySku(HashMap map) throws SQLException;
}
