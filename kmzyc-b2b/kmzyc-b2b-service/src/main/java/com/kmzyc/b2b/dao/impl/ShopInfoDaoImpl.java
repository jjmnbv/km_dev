package com.kmzyc.b2b.dao.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.kmzyc.b2b.dao.ShopInfoDao;
import com.kmzyc.b2b.model.ShopCategory;
import com.kmzyc.b2b.model.ShopInfo;
import com.km.framework.persistence.impl.DaoImpl;

/**
 * 
 * @author KM
 * 
 */

@Repository("shopInfoDaoImpl")
public class ShopInfoDaoImpl extends DaoImpl implements ShopInfoDao {

  @Resource(name = "sqlMapClient")
  private SqlMapClient sqlMapClient;

  @Override
  public ShopInfo queryShopInfoByIdForApp(Long shopId) throws SQLException {
    return (ShopInfo) sqlMapClient.queryForObject("SHOP_INFO.queryShopInfoForAppById", shopId);
  }

  @Override
  public Map<String, Object> querySimleShopInfoByMap(Map condition) throws SQLException {
    return (Map<String, Object>) sqlMapClient.queryForObject("SHOP_INFO.queryShopInfoByMap",
        condition);
  }

  @Override
  public List<ShopCategory> queryAllShopCategory(Long shopId) throws SQLException {
    return (List<ShopCategory>) sqlMapClient.queryForList("SHOP_INFO.queryAllFirstShopCategory",
        shopId);
  }

  @Override
  public Map<Object, Object> queryShopFareBySupplierId(Long supplierId) throws SQLException {
    return (Map<Object, Object>) sqlMapClient.queryForObject("SHOP_INFO.queryShopFareBySupplierId",
        supplierId);
  }

}
