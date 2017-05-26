package com.kmzyc.b2b.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.km.framework.persistence.Dao;
import com.kmzyc.b2b.model.ShopCategory;
import com.kmzyc.b2b.model.ShopInfo;
import com.kmzyc.commons.exception.ServiceException;

/**
 * 20150917 mlq add店铺信息数据库操作
 * 
 * @author KM
 * 
 */
public interface ShopInfoDao extends Dao {

  /**
   * app 接口提供 查询店铺信息
   * 
   * @param shopId
   * @return
   * @throws SQLException
   */
  public ShopInfo queryShopInfoByIdForApp(Long shopId) throws SQLException;

  /**
   * 根据map条件查询所需要的指定的shopinfo当中的信息
   * 
   * @param condition
   * @return
   * @throws SQLException
   */
  public Map<String, Object> querySimleShopInfoByMap(Map condition) throws SQLException;

  /**
   * 查询该店铺所有的店内分类,包含子级,列表形式
   * 
   * @param shopId
   * @return
   * @throws ServiceException
   */
  public List<ShopCategory> queryAllShopCategory(Long shopId) throws SQLException;

  /**
   * 查询店铺运费根据供应商id
   * 
   * @param supplierId
   * @return
   * @throws SQLException
   */
  public Map<Object, Object> queryShopFareBySupplierId(Long supplierId) throws SQLException;

}
