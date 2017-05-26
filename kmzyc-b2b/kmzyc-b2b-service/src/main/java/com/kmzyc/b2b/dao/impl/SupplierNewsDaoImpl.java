package com.kmzyc.b2b.dao.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.kmzyc.b2b.dao.SupplierNewsDao;
import com.kmzyc.b2b.model.SupplierNews;
import com.kmzyc.b2b.vo.CommercialTenantBasicInfo;
import com.km.framework.persistence.impl.DaoImpl;

@Service
public class SupplierNewsDaoImpl extends DaoImpl implements SupplierNewsDao {
  // 数据库处理
  @Resource(name = "sqlMapClient")
  private SqlMapClient sqlMapClient;

  @Override
  public SupplierNews queryByNewsId(String newsId) throws Exception {
    return (SupplierNews) sqlMapClient.queryForObject("SUPPLIER_NEWS.selectByNewsId", newsId);
  }

  @Override
  public CommercialTenantBasicInfo queryCommercialBySupplierId(Long supplierId) throws Exception {
    CommercialTenantBasicInfo com =
        (CommercialTenantBasicInfo) sqlMapClient.queryForObject(
            "SUPPLIER_NEWS.app_queryCommecalBySupplierId", supplierId);
    return com;
  }
}
