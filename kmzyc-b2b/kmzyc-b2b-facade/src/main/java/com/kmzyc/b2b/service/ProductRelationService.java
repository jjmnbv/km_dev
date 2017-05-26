package com.kmzyc.b2b.service;

import java.util.List;
import java.util.Map;

import com.kmzyc.b2b.model.ProductRelation;
import com.kmzyc.framework.exception.ServiceException;

/**
 * 获取套餐信息接口
 * 
 * @author wangkai
 * 
 */
@SuppressWarnings("unchecked")
public interface ProductRelationService {

  public Map findProductRelationCount(Long id) throws Exception;

  public ProductRelation findProductRelationById(Long id) throws Exception;


  /**
   * 查询主SKUID的所有组合产品
   * 
   * @param skuId
   * @return
   * @throws ServiceException
   */
  public List<ProductRelation> queryProductRelationBySkuId(Long skuId) throws ServiceException;

  /**
   * 排序组合
   * 
   * @param skuId
   * @return
   * @throws ServiceException
   */
  public List<ProductRelation> querySortCombine(Long skuId, String openId) throws ServiceException;
}
