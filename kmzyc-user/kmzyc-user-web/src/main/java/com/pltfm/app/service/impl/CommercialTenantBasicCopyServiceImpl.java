package com.pltfm.app.service.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.pltfm.app.dao.CommercialTenantBasicCopyDAO;
import com.pltfm.app.dataobject.CommercialTenantBasicCopyDO;
import com.pltfm.app.service.CommercialTenantBasicCopyService;
import com.pltfm.app.vobject.AreaDict;


@Component(value = "commercialTenantBasicCopyService")
public class CommercialTenantBasicCopyServiceImpl implements CommercialTenantBasicCopyService {

  @Resource(name = "commercialTenantBasicCopyDAO")
  private CommercialTenantBasicCopyDAO commercialTenantBasicCopyDAO;

  public List selectListCommercialTenantBasicCopy(
      CommercialTenantBasicCopyDO commercialTenantBasicCopyDO) throws SQLException {
    // TODO Auto-generated method stub
    return commercialTenantBasicCopyDAO
        .selectListCommercialTenantBasicCopy(commercialTenantBasicCopyDO);
  }


  public Integer selectListCommercialTenantBasicCopyCount(
      CommercialTenantBasicCopyDO commercialTenantBasicCopyDO) throws SQLException {
    // TODO Auto-generated method stub
    return commercialTenantBasicCopyDAO
        .selectListCommercialTenantBasicCopyCount(commercialTenantBasicCopyDO);
  }


  public CommercialTenantBasicCopyDAO getCommercialTenantBasicCopyDAO() {
    return commercialTenantBasicCopyDAO;
  }


  public void setCommercialTenantBasicCopyDAO(
      CommercialTenantBasicCopyDAO commercialTenantBasicCopyDAO) {
    this.commercialTenantBasicCopyDAO = commercialTenantBasicCopyDAO;
  }


  @Override
  public CommercialTenantBasicCopyDO queryCommercialTenantBasicCopyDO(
      BigDecimal getnCommercialCopyId) throws SQLException {
    // TODO Auto-generated method stub
    return commercialTenantBasicCopyDAO.queryCommercialTenantBasicCopyDO(getnCommercialCopyId);
  }


  @Override
  public void updateBasicStatus(CommercialTenantBasicCopyDO commercialTenantBasicCopyDO)
      throws SQLException {
    // TODO Auto-generated method stub
    commercialTenantBasicCopyDAO.updateBasicStatus(commercialTenantBasicCopyDO);
  }

  /**
   * 更改采购商变更审核信息
   *
   *
   */
  @Override
  public Integer updateCopyStatus(CommercialTenantBasicCopyDO commercialTenantBasicCopyDO)
      throws SQLException {
    return commercialTenantBasicCopyDAO.updateCopyStatus(commercialTenantBasicCopyDO);

  }

  @Override
  public void updatePass(CommercialTenantBasicCopyDO commercialTenantBasicCopyDO)
      throws SQLException {
    // TODO Auto-generated method stub
    commercialTenantBasicCopyDAO.updatePass(commercialTenantBasicCopyDO);
  }


  @Override
  public List<AreaDict> selectAreaByAreaId(Integer cityId) throws SQLException {
    // TODO Auto-generated method stub
    return commercialTenantBasicCopyDAO.selectAreaByAreaId(cityId);
  }



}
