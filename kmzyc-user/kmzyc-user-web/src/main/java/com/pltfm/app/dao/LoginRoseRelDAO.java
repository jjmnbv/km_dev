package com.pltfm.app.dao;

import com.pltfm.app.dataobject.LoginRoseRelDO;

import java.math.BigDecimal;

/**
 * 数据访问对象接口
 * 
 * @since 2014-05-22
 */
public interface LoginRoseRelDAO {

  /**
   * 插入数据
   * 
   * @param loginRoseRelDO
   * @return 插入数据的主键
   */
  public BigDecimal insertLoginRoseRelDO(LoginRoseRelDO loginRoseRelDO);
  /*
    *//**
       * 统计记录数
       * 
       * @param loginRoseRelDO
       * @return 查出的记录数
       */
  /*
   * public Integer countLoginRoseRelDOByExample(LoginRoseRelDO loginRoseRelDO);
   * 
   *//**
     * 统计记录数
     * 
     * @param loginRoseRelQuery
     * @return 查出的记录数
     */

  /*
   * public Integer countLoginRoseRelQueryByExample(LoginRoseRelQuery loginRoseRelQuery);
   * 
   *//**
     * 更新记录
     * 
     * @param loginRoseRelDO
     * @return 受影响的行数
     */
  public Integer updateLoginRoseRelDO(LoginRoseRelDO loginRoseRelDO);

  /**
   * 获取对象列表
   * 
   * @param loginRoseRelDO
   * @return 对象列表
   */
  /*
   * public List<LoginRoseRelDO> findListByExample(LoginRoseRelDO loginRoseRelDO);
   * 
   *//**
     * 获取对象列表
     * 
     * @param loginRoseRelQuery
     * @return 对象列表
     */
  /*
   * public List<LoginRoseRelQuery> findListByExample(LoginRoseRelQuery loginRoseRelQuery);
   * 
   *//**
     * 根据主键获取loginRoseRelDO
     * 
     * @param lrId
     * @return loginRoseRelDO
     */
  /*
   * public LoginRoseRelDO findLoginRoseRelDOByPrimaryKey(BigDecimal lrId);
   * 
   *//**
     * 删除记录
     * 
     * @param lrId
     * @return 受影响的行数
     *//*
       * public Integer deleteLoginRoseRelDOByPrimaryKey(BigDecimal lrId);
       */
}
