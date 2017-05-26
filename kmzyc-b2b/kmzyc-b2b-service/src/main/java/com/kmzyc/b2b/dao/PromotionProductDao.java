package com.kmzyc.b2b.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kmzyc.b2b.model.PromotionInfo;
import com.kmzyc.b2b.model.PromotionProduct;
import com.kmzyc.b2b.vo.CarProduct;
import com.km.framework.page.Pagination;
import com.km.framework.persistence.Dao;

public interface PromotionProductDao extends Dao {
  public Pagination getPromotionProduct(Pagination page, PromotionInfo promotion);

  /**
   * 获取加价购商品
   * 
   * @param prizeData活动规则奖励数据
   */
  public Map<Long, CarProduct> getIncreaseProduct(Long prizeData) throws SQLException;

  /**
   * 获取赠品
   * 
   * @param promotionId活动ID
   * @return
   */
  public Map<Long, CarProduct> getGiftProductByPromotion(Long promotionId) throws SQLException;

  public List<PromotionProduct> getPromotionProductBySku(HashMap map) throws SQLException;

}
