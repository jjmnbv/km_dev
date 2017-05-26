package com.pltfm.app.dao;

import com.pltfm.app.dataobject.SuppliersInfoDO;

import java.sql.SQLException;
import java.util.List;

/**
 * 数据访问对象接口
 * 
 * @since 2014-10-22
 */
public interface SuppliersInfoDAO {
  /**
   * 根据产品Id查询供应商信息
   * 
   * @param productId
   * @return
   * @throws SQLException
   */
  SuppliersInfoDO selectDetailByProductId(String productId) throws SQLException;

  /**
   * 统计记录数
   * 
   * @param suppliersInfoDO
   * @return 查出的记录数
   */
  public Integer countSuppliersInfoDOByExample(SuppliersInfoDO suppliersInfoDO);

  /**
   * 获取对象列表
   * 
   * @param suppliersInfoDO
   * @return 对象列表
   */
  public List<SuppliersInfoDO> findListByExample(SuppliersInfoDO suppliersInfoDO);

  /**
   * 插入数据
   * 
   * @param suppliersInfoDO
   * @return 插入数据的主键
   */
  /*
   * public BigDecimal insertSuppliersInfoDO(SuppliersInfoDO suppliersInfoDO);
   * 
   * 
   * 
   *//**
     * 更新记录
     * 
     * @param suppliersInfoDO
     * @return 受影响的行数
     */
  /*
   * public Integer updateSuppliersInfoDO(SuppliersInfoDO suppliersInfoDO);
   * 
   * 
   * 
   *//**
     * 根据主键获取suppliersInfoDO
     * 
     * @param supplierId
     * @return suppliersInfoDO
     */
  /*
   * public SuppliersInfoDO findSuppliersInfoDOByPrimaryKey(BigDecimal supplierId);
   * 
   *//**
     * 删除记录
     * 
     * @param supplierId
     * @return 受影响的行数
     *//*
       * public Integer deleteSuppliersInfoDOByPrimaryKey(BigDecimal supplierId);
       */

}
