package com.kmzyc.b2b.service;

import java.util.List;
import java.util.Map;

import com.kmzyc.b2b.model.ShopCategory;
import com.kmzyc.b2b.model.ShopInfo;
import com.kmzyc.commons.exception.ServiceException;

/**
 * 店铺信息service 层 20150917 mlq add
 * 
 * @author KM
 * 
 */
public interface ShopInfoService {

  /**
   * 查询店铺整套信息 app 接口
   * 
   * @param shopId
   * @return
   * @throws ServiceException
   */
  public ShopInfo queryShopInfoForApp(Integer shopId) throws ServiceException;

  /**
   * 根据条件查询店铺信息
   * 
   * @param map
   * @return
   * @throws ServiceException
   */
  public Map<String, Object> querySimpleShopInfoByMap(Map map) throws ServiceException;

  /**
   * 查询该店铺所有的店内分类,包含子级,列表形式
   * 
   * @param shopId
   * @return
   * @throws ServiceException
   */
  public List<ShopCategory> queryAllShopCategory(Long shopId) throws ServiceException;

  /**
   * 查询店铺运费
   * 
   * @return
   * @throws ServiceException
   */
  public Map<Object, Object> queryShopFareBySupplierId(Long supplierId) throws ServiceException;

}
