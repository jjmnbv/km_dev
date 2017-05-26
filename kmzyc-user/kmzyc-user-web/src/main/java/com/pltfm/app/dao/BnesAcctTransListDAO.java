package com.pltfm.app.dao;


import com.pltfm.app.dataobject.BnesAcctTransListDO;
import com.pltfm.app.vobject.BnesAcctTransListQuery;

import org.springframework.dao.DataAccessException;

import java.sql.SQLException;
import java.util.List;

/**
 * 数据访问对象接口
 * 
 * @since 2013-07-17
 */
public interface BnesAcctTransListDAO {

  /**
   * 分页查询查询
   * 
   * @param bnesAcctTransactionDO
   * @return
   */

  List<BnesAcctTransListQuery> findListByExample(BnesAcctTransListQuery bnesAcctTransListQuery)
      throws DataAccessException;

  /**
   * 统计记录数
   * 
   * @param bnesAcctTransListQuery
   * @return 查出的记录数
   */
  Integer countBnesAcctTransListQueryByExample(BnesAcctTransListQuery bnesAcctTransListQuery)
      throws DataAccessException;

  /**
   * 插入数据
   * 
   * @param bnesAcctTransListDO
   * @return 插入数据的主键
   */
  Integer insertBnesAcctTransListDO(BnesAcctTransListDO bnesAcctTransListDO)
      throws DataAccessException;

  List<BnesAcctTransListQuery> queryAllBnesAcctTransList(
      BnesAcctTransListQuery bnesAcctTransListQuery) throws SQLException;


  //
  // -------------------------------------------------------------------------
  /*
    *//*
      
      *//**
         * 统计记录数
         * 
         * @param bnesAcctTransListDO
         * @return 查出的记录数
         */
  /*
   * public Integer countBnesAcctTransListDOByExample(BnesAcctTransListDO bnesAcctTransListDO)
   * throws DataAccessException;
   * 
   * 
   *//**
     * 更新记录
     * 
     * @param bnesAcctTransListDO
     * @return 受影响的行数
     */
  /*
   * public Integer updateBnesAcctTransListDO(BnesAcctTransListDO bnesAcctTransListDO) throws
   * DataAccessException;
   * 
   *//**
     * 获取对象列表
     * 
     * @param bnesAcctTransListDO
     * @return 对象列表
     */
  /*
   * public List<BnesAcctTransListDO> findListByExample(BnesAcctTransListDO bnesAcctTransListDO)
   * throws DataAccessException;
   * 
   * 
   *//**
     * 根据主键获取bnesAcctTransListDO
     * 
     * @param transListId
     * @return bnesAcctTransListDO
     */
  /*
   * public BnesAcctTransListDO findBnesAcctTransListDOByPrimaryKey(Integer transListId) throws
   * DataAccessException;
   * 
   *//**
     * 删除记录
     * 
     * @param transListId
     * @return 受影响的行数
     *//*
       * public Integer deleteBnesAcctTransListDOByPrimaryKey(Integer transListId) throws
       * DataAccessException;
       */
}
