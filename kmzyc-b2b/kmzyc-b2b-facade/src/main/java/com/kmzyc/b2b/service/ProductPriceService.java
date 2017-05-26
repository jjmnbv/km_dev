package com.kmzyc.b2b.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.kmzyc.b2b.model.ProductSku;
import com.kmzyc.b2b.vo.BaseProduct;

/**
 * 获取价格和促销信息接口<br>
 * 优先读取缓存数据
 * 
 * @author hewenfeng
 * 
 */
public interface ProductPriceService {

  /**
   * 批量获取商品促销价格和促销标签
   * 
   * @param list
   * @return
   * @throws Exception
   */
  public <T extends BaseProduct> List<T> getPriceBatch(List<T> list) throws Exception;

  /**
   * 批量获取商品促销价格,根据传入的活动时间
   * 
   * @param list
   * @return
   * @throws Exception
   */
  public List<ProductSku> getPriceBatchByDate(String skuIds, Date promotionDate) throws Exception;

  // /**
  // * 获取指定商品促销信息和价格
  // *
  // * @param t
  // * @return
  // * @throws Exception
  // */
  // @Deprecated
  // public <T extends BaseProduct> void setPromotionInfoPrice(T t) throws Exception;

  /**
   * 批量获取商品促销价格和促销标签
   * 
   * @param <T>
   * @param list
   * @param isGetTag 是否获取右上角标签
   * @return
   * @throws Exception
   */
  public <T extends BaseProduct> List<T> getPriceBatch(List<T> list, Boolean isGetTag)
      throws Exception;

  /**
   * 批量获取活动商品活动价格 add by songmiao 2015-10-27
   * 
   * @throws Exception
   */
  public Map<String, BigDecimal> getPromotionPricetBySkuIds(Map<String, BigDecimal> map)
      throws Exception;


}
