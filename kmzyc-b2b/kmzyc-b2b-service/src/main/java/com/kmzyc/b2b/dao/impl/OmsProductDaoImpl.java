package com.kmzyc.b2b.dao.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.kmzyc.b2b.dao.OmsProductDao;
import com.kmzyc.b2b.vo.OmsProductData;
import com.kmzyc.b2b.vo.OmsProductSkuData;

@Repository("omsProductDao")
public class OmsProductDaoImpl implements OmsProductDao {

  @Resource(name = "sqlMapClient")
  private SqlMapClient sqlMapClient;

  @Override
  public List<OmsProductData> queryProductByMap(Map<String, Object> paraMap) throws SQLException {
    return sqlMapClient.queryForList("PRODUCT.queryProductInfoForOms", paraMap);
  }

  @Override
  public List<OmsProductSkuData> queryProductSkuByMap(Map<String, Object> paraMap)
      throws SQLException {
    return sqlMapClient.queryForList("PRODUCT.querySkuInfoForOms_original", paraMap);
  }

  @Override
  public List<OmsProductData> queryProductErpCode(String erpProCode) throws SQLException {
    return sqlMapClient.queryForList("PRODUCT.queryProductInfoByErpCode", erpProCode);
  }

  @Override
  public List<OmsProductSkuData> queryProductSkuByProductId(Map<String, Object> paraMap)
      throws SQLException {
    return sqlMapClient.queryForList("PRODUCT.querySkuInfoForOms", paraMap);
  }

  @Override
  public List<OmsProductData> queryProductListForOms(Map<String, String> paraMap)
      throws SQLException {
    return sqlMapClient.queryForList("PRODUCT.queryProductListForOms", paraMap);
  }

  @Override
  public Integer queryCountListForOms(Map<String, String> paraMap) throws SQLException {
    return (Integer) sqlMapClient.queryForObject("PRODUCT.queryProductCountForOms", paraMap);
  }

}
