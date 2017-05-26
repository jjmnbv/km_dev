package com.kmzyc.b2b.dao.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.kmzyc.b2b.dao.WxScanProductDao;
import com.kmzyc.b2b.model.WxScanProduct;
import com.km.framework.persistence.impl.DaoImpl;

@SuppressWarnings("unchecked")
@Repository("wxScanProductDao")
public class WxScanProductDaoImpl extends DaoImpl implements WxScanProductDao {

  @Resource(name = "sqlMapClient")
  private SqlMapClient sqlMapClient;


  public Object insert(WxScanProduct record) throws SQLException {
    return sqlMapClient.insert("WX_SCAN_PRODUCT.insertSelective", record);
  }

  /**
   * 根据openID获取扫码记录
   * 
   * @param mapCondition
   * @return
   * @throws SQLException
   */
  @Override
  public List<Long> queryWXScanProductSku(String openId) throws SQLException {
    return (List<Long>) sqlMapClient.queryForList("WX_SCAN_PRODUCT.SQL_QUERY_WX_SCAN_PRODUCT_SKU",
        openId);
  }
}
