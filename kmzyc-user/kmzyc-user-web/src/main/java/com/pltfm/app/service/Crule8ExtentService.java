package com.pltfm.app.service;

import java.math.BigDecimal;
import java.util.List;

import com.pltfm.app.dataobject.Crule8ExtentDO;

public interface Crule8ExtentService {
  /**
   * 插入数据
   * 
   * @param crule8ExtentDO
   * @return 插入数据的主键
   */
  public int insertCrule8ExtentDO(String supplierId, BigDecimal pkeyDetailId);

  public int insertCrule8ExProduct(String skuCodeLists, BigDecimal pkeyDetailId);

  /**
   * 获取对象列表
   * 
   * @param crule8ExtentDO
   * @return 对象列表
   */
  public List<Crule8ExtentDO> findListByExample(Crule8ExtentDO crule8ExtentDO);

  public List<Crule8ExtentDO> findSuplerListByExample(Crule8ExtentDO crule8ExtentDO);

}
