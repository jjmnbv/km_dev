package com.kmzyc.b2b.dao;

import java.sql.SQLException;

import com.kmzyc.b2b.model.CommercialTenantBasicInfo;

public interface CommercialTenantBasicInfoDAO {
  public CommercialTenantBasicInfo queryByLoginId(Long loginId) throws SQLException;
}
