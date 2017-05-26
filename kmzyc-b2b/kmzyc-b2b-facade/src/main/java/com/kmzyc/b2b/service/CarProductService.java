package com.kmzyc.b2b.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.kmzyc.b2b.vo.CarProduct;
import com.kmzyc.b2b.vo.CompositionCarProduct;
import com.kmzyc.framework.exception.ServiceException;
import com.pltfm.app.entities.OrderItem;

public interface CarProductService {

  // /**
  // * 创建新购物车商品对象
  // *
  // * @param productSkuId
  // * @param amount
  // * @return
  // * @throws ServiceException
  // */
  // public CarProduct createCarProductFromDB(Long productSkuId, Integer amount)
  // throws ServiceException;

  // /**
  // * 创建新购物车商品对象 并设置限购数据
  // *
  // * @param productSkuId
  // * @param amount
  // * @return
  // * @throws ServiceException
  // */
  // public CarProduct createCarProductFromDB(Long productSkuId, Integer amount, Long userId)
  // throws ServiceException;

  // /**
  // * 创建套装商品对象 并设置限购数据
  // *
  // * @param suitId
  // * @param count
  // * @return
  // * @throws ServiceException
  // */
  // public CompositionCarProduct createSuitCarProductFormdb(Long suitId, Integer count, Long
  // userId)
  // throws ServiceException;

  /**
   * 检查购物车商品上下架和库存
   * 
   * @param carProduct
   * @throws ServiceException
   */
//  public void checkCarProduct(CarProduct carProduct) throws ServiceException;

  /**
   * 只更新商品上下架库存信息
   * 
   * @param carProduct
   * @return
   */
  public CarProduct updateCarProduct(CarProduct carProduct) throws ServiceException;

  /**
   * 校验限购信息
   * 
   * @param carProduct
   * @throws ServiceException
   */
//  public void checkCarProductForPurchase(CarProduct carProduct, Long userId)
//      throws ServiceException;

  /**
   * 设置产品的sku名称
   * 
   * @param carProduct
   * @return
   */
  public CarProduct setCarProductSkuAttrValue(CarProduct carProduct);


//  public CarProduct createAbleCarProduct(Long skuId, Integer amount, String loginType)
//      throws ServiceException;


//  public CompositionCarProduct createAbleComposition(Long suitId, Integer amount, String uid)
//      throws ServiceException;

//  public List<Long> batchCheckProducts(List<Long> skuids, List<Integer> amounts)
//      throws ServiceException;

  public CarProduct getCarProduct(Long productSkuId) throws ServiceException;

  /**
   * 是否是常规产品
   * 
   * @return
   * @throws ServiceException
   */
//  public void isNormalProduct(int type, Long id) throws ServiceException;

  /**
   * 根据skuid批量检查是否是常规产品
   * 
   * @return
   * @throws ServiceException
   */
//  public void isNormalProduct(List<Long> skuIds) throws ServiceException;

  /**
   * 根据供应商ID查询商家名称
   * 
   * @param sellerId
   * @return
   * @throws SQLException
   */
  public String queryCorporateNameBySupplierId(Long sellerId) throws ServiceException;

  /**
   * 批量查询产品最大库存
   * 
   * @param skuIds
   * @return
   * @throws ServiceException
   */
  public Map<Long, Integer> queryBatchMaxStock(List<Long> skuIds) throws ServiceException;

  /**
   * 验证是否在库存库存范围内
   * 
   * @param skuIds
   * @return
   * @throws ServiceException
   */
//  public boolean validInStock(List<OrderItem> oiList) throws ServiceException;
//
//  public CarProduct queryTimeProduct(String productId) throws SQLException;
}
