package com.pltfm.app.dao;

import com.pltfm.app.vobject.PurchaseListDO;

import java.math.BigDecimal;
import java.util.List;

/**
 * 数据访问对象接口
 * 
 * @since 2014-09-22
 */
public interface PurchaseListDAO {


  /**
   * 统计记录数
   * 
   * @param purchaseListDO
   * @return 查出的记录数
   */
  public Integer countPurchaseListDOByExample(PurchaseListDO purchaseListDO);

  /**
   * 获取对象列表
   * 
   * @param purchaseListDO
   * @return 对象列表
   */
  public List<PurchaseListDO> findListByExample(PurchaseListDO purchaseListDO);

  /**
   * 根据主键获取purchaseListDO
   * 
   * @param purchaseId
   * @return purchaseListDO
   */
  public PurchaseListDO findPurchaseListDOByPrimaryKey(BigDecimal purchaseId);


  /**
   * 更新记录
   * 
   * @param purchaseListDO
   * @return 受影响的行数
   */
  public Integer updatePurchaseListDO(PurchaseListDO purchaseListDO);

  /**
   * 删除记录
   * 
   * @param purchaseId
   * @return 受影响的行数
   */
  public Integer deletePurchaseListDOByPrimaryKey(BigDecimal purchaseId);


  /**
   * 插入数据
   * 
   * @param purchaseListDO
   * @return 插入数据的主键
   */
  /* public BigDecimal insertPurchaseListDO(PurchaseListDO purchaseListDO); */



}
