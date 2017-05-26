package com.pltfm.app.service;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import com.pltfm.app.dataobject.CommercialTenantBasicCopyDO;
import com.pltfm.app.vobject.AreaDict;

public interface CommercialTenantBasicCopyService {

  Integer selectListCommercialTenantBasicCopyCount(
      CommercialTenantBasicCopyDO commercialTenantBasicCopyDO) throws SQLException;

  List selectListCommercialTenantBasicCopy(CommercialTenantBasicCopyDO commercialTenantBasicCopyDO)
      throws SQLException;

  CommercialTenantBasicCopyDO queryCommercialTenantBasicCopyDO(BigDecimal getnCommercialCopyId)
      throws SQLException;

  void updateBasicStatus(CommercialTenantBasicCopyDO commercialTenantBasicCopyDO)
      throws SQLException;

  void updatePass(CommercialTenantBasicCopyDO commercialTenantBasicCopyDO) throws SQLException;

  List<AreaDict> selectAreaByAreaId(Integer cityId) throws SQLException;

  /**
   * 更改采购商变更审核信息
   *
   *
   */

  Integer updateCopyStatus(CommercialTenantBasicCopyDO commercialTenantBasicCopyDO)
      throws SQLException;



}
