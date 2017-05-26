package com.pltfm.app.dao.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.pltfm.app.dao.PurchaseListDAO;
import com.pltfm.app.vobject.PurchaseListDO;

/**
 * 数据访问对象实现类
 * 
 * @since 2014-09-22
 */
@Component(value = "purchaseListDAO")
public class PurchaseListDAOImpl implements PurchaseListDAO {
  @Resource(name = "sqlMapClient")
  private SqlMapClient sqlMapClient;



  /**
   * 统计记录数
   * 
   * @param purchaseListDO
   * @return 查出的记录数
   */
  public Integer countPurchaseListDOByExample(PurchaseListDO purchaseListDO) {
    Integer count = 0;
    try {
      count =
          (Integer) sqlMapClient.queryForObject("PurchaseList.countByDOExample", purchaseListDO);
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return count;
  }

  /**
   * 获取对象列表
   * 
   * @param purchaseListDO
   * @return 对象列表
   */
  @SuppressWarnings("unchecked")
  public List<PurchaseListDO> findListByExample(PurchaseListDO purchaseListDO) {
    List<PurchaseListDO> list = null;
    try {
      list = sqlMapClient.queryForList("PurchaseList.findListByDO", purchaseListDO);
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return list;
  }

  /**
   * 根据主键获取purchaseListDO
   * 
   * @param purchaseId
   * @return purchaseListDO
   */
  public PurchaseListDO findPurchaseListDOByPrimaryKey(BigDecimal purchaseId) {
    PurchaseListDO purchaseListDO = null;
    try {
      purchaseListDO =
          (PurchaseListDO) sqlMapClient.queryForObject("PurchaseList.findByPrimaryKey", purchaseId);
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return purchaseListDO;
  }

  /**
   * 更新记录
   * 
   * @param purchaseListDO
   * @return 受影响的行数
   */
  public Integer updatePurchaseListDO(PurchaseListDO purchaseListDO) {
    int result = 0;
    try {
      result = sqlMapClient.update("PurchaseList.update", purchaseListDO);
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return result;
  }

  /**
   * 删除记录
   * 
   * @param purchaseId
   * @return 受影响的行数
   */
  public Integer deletePurchaseListDOByPrimaryKey(BigDecimal purchaseId) {
    Integer rows = 0;
    try {
      rows = (Integer) sqlMapClient.delete("PurchaseList.deleteByPrimaryKey", purchaseId);
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return rows;
  }

  public SqlMapClient getSqlMapClient() {
    return sqlMapClient;
  }

  public void setSqlMapClient(SqlMapClient sqlMapClient) {
    this.sqlMapClient = sqlMapClient;
  }

  /**
   * 插入数据
   * 
   * @param purchaseListDO
   * @return 插入数据的主键
   */
  /*
   * public BigDecimal insertPurchaseListDO(PurchaseListDO purchaseListDO) { Object PURCHASE_ID =
   * getSqlMapClientTemplate().insert("PurchaseList.insert", purchaseListDO); return (BigDecimal)
   * PURCHASE_ID; }
   */



}
