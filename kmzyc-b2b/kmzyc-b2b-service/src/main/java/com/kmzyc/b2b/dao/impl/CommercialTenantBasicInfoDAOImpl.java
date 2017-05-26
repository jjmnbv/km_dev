package com.kmzyc.b2b.dao.impl;

import java.sql.SQLException;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.kmzyc.b2b.dao.CommercialTenantBasicInfoDAO;
import com.kmzyc.b2b.model.CommercialTenantBasicInfo;
import com.km.framework.persistence.impl.DaoImpl;

@Repository
public class CommercialTenantBasicInfoDAOImpl extends DaoImpl
    implements
      CommercialTenantBasicInfoDAO {

  @Resource(name = "sqlMapClient")
  private SqlMapClient sqlMapClient;

  @Override
  public CommercialTenantBasicInfo queryByLoginId(Long loginId) throws SQLException {
    return (CommercialTenantBasicInfo) this.sqlMapClient.queryForObject(
        "KMUSER_COMMERCIAL_TENANT_BASIC_INFO.queryByLoginId", loginId);
  }

}
