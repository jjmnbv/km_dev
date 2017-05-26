package com.pltfm.app.dao;

import com.pltfm.app.vobject.PurchaseListDetailDO;

import java.math.BigDecimal;
import java.util.List;

/**
 * 数据访问对象接口
 * 
 * @since 2014-09-22
 */
public interface PurchaseListDetailDAO {
  /**
   * 获取对象列表
   * 
   * @param purchaseListDetailDO
   * @return 对象列表
   */
  public List<PurchaseListDetailDO> findListByExample(PurchaseListDetailDO purchaseListDetailDO);

  /**
   * 删除记录
   * 
   * @param purchaseDetailId
   * @return 受影响的行数
   */
  public Integer deletePurchaseListDetailDOByPrimaryKey(BigDecimal purchaseDetailId);

  public Integer deletePurchaseListDetailDOByPurchaseIdKey(BigDecimal purchaseId);

  public List<PurchaseListDetailDO> findListByPurchaseList(
      PurchaseListDetailDO purchaseListDetailDO);

  public Integer countListByPurchaseList(PurchaseListDetailDO purchaseListDetailDO);

  public BigDecimal insertPurchaseListDetailDO(PurchaseListDetailDO purchaseListDetailDO);

  /**
   * 更新记录
   * 
   * @param purchaseListDetailDO
   * @return 受影响的行数
   */
  public Integer updatePurchaseListDetailDO(PurchaseListDetailDO purchaseListDetailDO);

  public List<PurchaseListDetailDO> findListByKey(BigDecimal parameterDetailId);



  /*
    *//**
       * 插入数据
       * 
       * @param purchaseListDetailDO
       * @return 插入数据的主键
       */
  /*
  
  
  *//**
     * 统计记录数
     * 
     * @param purchaseListDetailDO
     * @return 查出的记录数
     *//*
       * public Integer countPurchaseListDetailDOByExample(PurchaseListDetailDO
       * purchaseListDetailDO);
       * 
       */
  /**
   * 根据主键获取purchaseListDetailDO
   * 
   * @param purchaseDetailId
   * @return purchaseListDetailDO
   *//*
     * public PurchaseListDetailDO findPurchaseListDetailDOByPrimaryKey(BigDecimal
     * purchaseDetailId);
     * 
     */

}
