package com.pltfm.app.dao.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.pltfm.app.dao.SuppliersInfoDAO;
import com.pltfm.app.dataobject.SuppliersInfoDO;

/**
 * 数据访问对象实现类
 * 
 * @since 2014-10-22
 */
@Component(value = "suppliersInfoDAO")
public class SuppliersInfoDAOImpl implements SuppliersInfoDAO {
  @Resource(name = "sqlMapClient")
  private SqlMapClient sqlMapClient;

  /**
   * 根据产品Id查询供应商信息
   * 
   * @param productId
   * @return
   * @throws SQLException
   */
  public SuppliersInfoDO selectDetailByProductId(String skuCode) throws SQLException {
    Object object = sqlMapClient.queryForObject("SuppliersInfo.findShopDetailByProductId", skuCode);
    if (object != null) {
      return (SuppliersInfoDO) object;
    } else {
      return null;
    }
  }

  /**
   * 统计记录数
   * 
   * @param suppliersInfoDO
   * @return 查出的记录数
   */
  public Integer countSuppliersInfoDOByExample(SuppliersInfoDO suppliersInfoDO) {
    Integer count = 0;
    try {
      count =
          (Integer) sqlMapClient.queryForObject("SuppliersInfo.countByDOExample", suppliersInfoDO);
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return count;
  }

  /**
   * 获取对象列表
   * 
   * @param suppliersInfoDO
   * @return 对象列表
   */
  @SuppressWarnings("unchecked")
  public List<SuppliersInfoDO> findListByExample(SuppliersInfoDO suppliersInfoDO) {
    List<SuppliersInfoDO> list = null;
    try {
      list = sqlMapClient.queryForList("SuppliersInfo.findListByDO", suppliersInfoDO);
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return list;
  }

  public SqlMapClient getSqlMapClient() {
    return sqlMapClient;
  }

  public void setSqlMapClient(SqlMapClient sqlMapClient) {
    this.sqlMapClient = sqlMapClient;
  }

  /*
    *//**
       * 插入数据
       * 
       * @param suppliersInfoDO
       * @return 插入数据的主键
       */
  /*
   * public BigDecimal insertSuppliersInfoDO(SuppliersInfoDO suppliersInfoDO) { Object SUPPLIER_ID =
   * getSqlMapClientTemplate().insert("SuppliersInfo.insert", suppliersInfoDO); return (BigDecimal)
   * SUPPLIER_ID; }
   * 
   * 
   *//**
     * 更新记录
     * 
     * @param suppliersInfoDO
     * @return 受影响的行数
     */
  /*
   * public Integer updateSuppliersInfoDO(SuppliersInfoDO suppliersInfoDO) { int result =
   * getSqlMapClientTemplate().update("SuppliersInfo.update", suppliersInfoDO); return result; }
   * 
   * 
   *//**
     * 根据主键获取suppliersInfoDO
     * 
     * @param supplierId
     * @return suppliersInfoDO
     */
  /*
   * public SuppliersInfoDO findSuppliersInfoDOByPrimaryKey(BigDecimal supplierId) { SuppliersInfoDO
   * suppliersInfoDO = (SuppliersInfoDO)
   * getSqlMapClientTemplate().queryForObject("SuppliersInfo.findByPrimaryKey", supplierId); return
   * suppliersInfoDO; }
   * 
   *//**
     * 删除记录
     * 
     * @param supplierId
     * @return 受影响的行数
     *//*
       * public Integer deleteSuppliersInfoDOByPrimaryKey(BigDecimal supplierId) { Integer rows =
       * (Integer) getSqlMapClientTemplate().delete("SuppliersInfo.deleteByPrimaryKey", supplierId);
       * return rows; }
       */
}
