package com.pltfm.app.dao;


import com.pltfm.app.dataobject.Crule8ExtentDO;

import java.math.BigDecimal;
import java.util.List;

/**
 * 数据访问对象接口
 * 
 * @since 2014-10-24
 */
public interface Crule8ExtentDAO {

  /**
   * 插入数据
   * 
   * @param crule8ExtentDO
   * @return 插入数据的主键
   */
  public BigDecimal insertCrule8ExtentDO(Crule8ExtentDO crule8ExtentDO);

  public List<Crule8ExtentDO> findSuplerListByExample(Crule8ExtentDO crule8ExtentDO);

  public int deleteCrule8ExtentDO(BigDecimal crule8Id);

  /*  *//**
         * 统计记录数
         * 
         * @param crule8ExtentDO
         * @return 查出的记录数
         */
  /*
   * public Integer countCrule8ExtentDOByExample(Crule8ExtentDO crule8ExtentDO);
   * 
   *//**
     * 获取对象列表
     * 
     * @param crule8ExtentDO
     * @return 对象列表
     *//*
       * public List<Crule8ExtentDO> findListByExample(Crule8ExtentDO crule8ExtentDO);
       */

}
