package com.pltfm.app.dao.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.pltfm.app.dao.PurchaseListDetailDAO;
import com.pltfm.app.vobject.PurchaseListDetailDO;

/**
 * 数据访问对象实现类
 * 
 * @since 2014-09-22
 */
@Component(value = "purchaseListDetailDAO")
public class PurchaseListDetailDAOImpl implements PurchaseListDetailDAO {
  @Resource(name = "sqlMapClient")
  private SqlMapClient sqlMapClient;

  /**
   * 获取对象列表
   * 
   * @param purchaseListDetailDO
   * @return 对象列表
   */
  @SuppressWarnings("unchecked")
  public List<PurchaseListDetailDO> findListByExample(PurchaseListDetailDO purchaseListDetailDO) {
    List<PurchaseListDetailDO> list = null;
    try {
      list = sqlMapClient.queryForList("PurchaseListDetail.findListByDO", purchaseListDetailDO);
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return list;
  }

  /**
   * 删除记录
   * 
   * @param purchaseDetailId
   * @return 受影响的行数
   */
  public Integer deletePurchaseListDetailDOByPrimaryKey(BigDecimal purchaseDetailId) {
    Integer rows = 0;
    try {
      rows =
          (Integer) sqlMapClient.delete("PurchaseListDetail.deleteByPrimaryKey", purchaseDetailId);
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return rows;
  }

  public List<PurchaseListDetailDO> findListByPurchaseList(
      PurchaseListDetailDO purchaseListDetailDO) {

    List<PurchaseListDetailDO> list = null;
    try {
      list = sqlMapClient.queryForList("PurchaseListDetail.findListByPurchaseList",
          purchaseListDetailDO);
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return list;
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<PurchaseListDetailDO> findListByKey(BigDecimal parameterDetailId) {
    // TODO Auto-generated method stub
    List<PurchaseListDetailDO> list = null;
    try {
      list = sqlMapClient.queryForList("PurchaseListDetail.findListByKey", parameterDetailId);
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return list;
  }

  public Integer countListByPurchaseList(PurchaseListDetailDO purchaseListDetailDO) {
    Integer count = 0;
    try {
      count = (Integer) sqlMapClient.queryForObject("PurchaseListDetail.countListByPurchaseList",
          purchaseListDetailDO);
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return count;

  }

  /**
   * 插入数据
   * 
   * @param purchaseListDetailDO
   * @return 插入数据的主键
   */
  public BigDecimal insertPurchaseListDetailDO(PurchaseListDetailDO purchaseListDetailDO) {
    Object PURCHASE_DETAIL_ID = null;
    try {
      PURCHASE_DETAIL_ID = sqlMapClient.insert("PurchaseListDetail.insert", purchaseListDetailDO);
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return (BigDecimal) PURCHASE_DETAIL_ID;
  }


  public SqlMapClient getSqlMapClient() {
    return sqlMapClient;
  }

  public void setSqlMapClient(SqlMapClient sqlMapClient) {
    this.sqlMapClient = sqlMapClient;
  }
  /*
    */


  /**
   * 更新记录
   * 
   * @param purchaseListDetailDO
   * @return 受影响的行数
   */
  public Integer updatePurchaseListDetailDO(PurchaseListDetailDO purchaseListDetailDO) {
    int result = 0;
    try {
      result = sqlMapClient.update("PurchaseListDetail.update", purchaseListDetailDO);
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return result;
  }



  /**
   * 根据主键获取purchaseListDetailDO
   * 
   * @param purchaseDetailId
   * @return purchaseListDetailDO
   *//*
     * public PurchaseListDetailDO findPurchaseListDetailDOByPrimaryKey(BigDecimal purchaseDetailId)
     * { PurchaseListDetailDO purchaseListDetailDO = (PurchaseListDetailDO)
     * getSqlMapClientTemplate().queryForObject("PurchaseListDetail.findByPrimaryKey",
     * purchaseDetailId); return purchaseListDetailDO; }
     * 
     */
  @Override
  public Integer deletePurchaseListDetailDOByPurchaseIdKey(BigDecimal purchaseId) {
    // TODO Auto-generated method stub
    Integer rows = 0;
    try {
      rows = (Integer) sqlMapClient.delete("PurchaseListDetail.deleteByPurchaseIdKey", purchaseId);
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return rows;
  }

}
