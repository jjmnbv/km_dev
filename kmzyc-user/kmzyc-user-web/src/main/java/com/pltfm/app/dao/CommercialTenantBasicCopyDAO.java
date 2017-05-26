package com.pltfm.app.dao;

import com.pltfm.app.dataobject.CommercialTenantBasicCopyDO;
import com.pltfm.app.vobject.AreaDict;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

/**
 * 数据访问对象接口
 * 
 * @since 2014-07-21
 */
public interface CommercialTenantBasicCopyDAO {

  /**
   * 插入数据
   * 
   * @param commercialTenantBasicCopyDO
   * @return 插入数据的主键
   */
  public BigDecimal insertCommercialTenantBasicCopyDO(
      CommercialTenantBasicCopyDO commercialTenantBasicCopyDO);

  public List selectListCommercialTenantBasicCopy(
      CommercialTenantBasicCopyDO commercialTenantBasicCopyDO) throws SQLException;

  public Integer selectListCommercialTenantBasicCopyCount(
      CommercialTenantBasicCopyDO commercialTenantBasicCopyDO) throws SQLException;

  public CommercialTenantBasicCopyDO queryCommercialTenantBasicCopyDO(
      BigDecimal getnCommercialCopyId) throws SQLException;

  public void updateBasicStatus(CommercialTenantBasicCopyDO commercialTenantBasicCopyDO)
      throws SQLException;

  /**
   * 更改采购商变更审核信息
   *
   *
   */

  public Integer updateCopyStatus(CommercialTenantBasicCopyDO commercialTenantBasicCopyDO)
      throws SQLException;

  public void updatePass(CommercialTenantBasicCopyDO commercialTenantBasicCopyDO)
      throws SQLException;

  /**
   * 删除未审核记录
   * 
   * @param nCommercialCopyId
   * @return 受影响的行数
   */
  public Integer deleteCommercialTenantBasicCopyDOByPrimaryKey(BigDecimal commercialTenantId);

  public List<AreaDict> selectAreaByAreaId(Integer cityId) throws SQLException;

  /**
   * 统计记录数
   * 
   * @param commercialTenantBasicCopyDO
   * @return 查出的记录数
   */
  /*
   * public Integer countCommercialTenantBasicCopyDOByExample(CommercialTenantBasicCopyDO
   * commercialTenantBasicCopyDO);
   */
  /**
   * 更新记录
   * 
   * @param commercialTenantBasicCopyDO
   * @return 受影响的行数
   */
  /*
   * public Integer updateCommercialTenantBasicCopyDO(CommercialTenantBasicCopyDO
   * commercialTenantBasicCopyDO);
   */
  /**
   * 获取对象列表
   * 
   * @param commercialTenantBasicCopyDO
   * @return 对象列表
   */
  /*
   * public List<CommercialTenantBasicCopyDO> findListByExample(CommercialTenantBasicCopyDO
   * commercialTenantBasicCopyDO);
   */
  /**
   * 根据主键获取commercialTenantBasicCopyDO
   * 
   * @param nCommercialCopyId
   * @return commercialTenantBasicCopyDO
   */
  /*
   * public CommercialTenantBasicCopyDO findCommercialTenantBasicCopyDOByPrimaryKey(BigDecimal
   * nCommercialCopyId);
   */
  /**
   * 删除记录
   * 
   * @param nCommercialCopyId
   * @return 受影响的行数
   */
  /*
   * public Integer deleteCommercialTenantBasicCopyDOByPrimaryKey(BigDecimal nCommercialCopyId);
   */
}
