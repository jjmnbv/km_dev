package com.pltfm.app.service;



import java.util.List;

import com.kmzyc.commons.page.Page;
import com.pltfm.app.vobject.BnesAcctTransactionQuery;

/**
 * 数据访问对象接口
 * 
 * @since 2013-07-17
 */
public interface BnesAcctTransactionService {

  /**
   * 查询
   * 
   * @param bnesAcctTransactionDO
   * @return
   */

  List<BnesAcctTransactionQuery> findListByExample(
      BnesAcctTransactionQuery bnesAcctTransactionQuery) throws Exception;


  /**
   * 分页查询查询
   * 
   * @param bnesAcctTransactionDO
   * @return
   */

  Page findListByPageExample(Page pageParam, BnesAcctTransactionQuery bnesAcctTransactionQuery)
      throws Exception;


  /**
   * 新增充值
   * 
   * @param bnesAcctTransactionDO
   * @return 受影响的行数
   */
  Integer insertBnesAcctTransactionDO(BnesAcctTransactionQuery bnesAcctTransactionQuery)
      throws Exception;

  /**
   * 修改充值
   * 
   * @param bnesAcctTransactionDO
   * @return 受影响的行数
   */

  Integer updateBnesAcctTransactionDO(BnesAcctTransactionQuery bnesAcctTransactionQuery)
      throws Exception;

  /**
   * 查询充值信息
   * 
   * @param bnesAcctTransactionDO
   * @return bnesAcctTransactionQuery
   */

  BnesAcctTransactionQuery findById(Integer accountTransactionId) throws Exception;

  /**
   * 删除记录
   * 
   * @param accountTransactionId
   * @return 受影响的行数
   */
  Integer deleteBnesAcctTransactionDOByPrimaryKey(Integer accountTransactionId) throws Exception;

  /**
   * 多条删除信息
   * 
   * @param levelIds
   * @return
   * @throws Exception 异常
   */
  Integer deleteAll(List<String> levelIds) throws Exception;
  /*
   * ----------------------------------------------------------------------------------
   */
  /* 
   *//**
      * 统计记录数
      * 
      * @param bnesAcctTransactionDO
      * @return 查出的记录数
      */
  /*
   * public abstract Integer countBnesAcctTransactionDOByExample(BnesAcctTransactionDO
   * bnesAcctTransactionDO) throws DataAccessException;
   * 
   *//**
     * 统计记录数
     * 
     * @param bnesAcctTransactionQuery
     * @return 查出的记录数
     */
  /*
   * public abstract Integer countBnesAcctTransactionQueryByExample(BnesAcctTransactionQuery
   * bnesAcctTransactionQuery) throws DataAccessException;
   * 
   *//**
     * 更新记录
     * 
     * @param bnesAcctTransactionDO
     * @return 受影响的行数
     */
  /*
   * public abstract DataAccessException;
   * 
   *//**
     * 获取对象列表
     * 
     * @param bnesAcctTransactionDO
     * @return 对象列表
     */
  /*
   * public abstract List<BnesAcctTransactionDO> findListByExample(BnesAcctTransactionDO
   * bnesAcctTransactionDO) throws DataAccessException;
   * 
   * 
   *//**
     * 根据主键获取bnesAcctTransactionDO
     * 
     * @param accountTransactionId
     * @return bnesAcctTransactionDO
     *//*
       * public abstract BnesAcctTransactionDO findBnesAcctTransactionDOByPrimaryKey(Integer
       * accountTransactionId) throws DataAccessException;
       * 
       */

}
