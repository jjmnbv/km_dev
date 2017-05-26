package com.pltfm.app.dao.impl;


import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.pltfm.app.dao.BnesAcctTransListDAO;
import com.pltfm.app.dataobject.BnesAcctTransListDO;
import com.pltfm.app.vobject.BnesAcctTransListQuery;
import com.pltfm.sys.model.SysModelUtil;

/**
 * 数据访问对象实现类
 * 
 * @since 2013-07-17
 */
@Component(value = "bnesAcctTransListDAO")
public class BnesAcctTransListDAOImpl implements BnesAcctTransListDAO {

  @Resource(name = "sqlMapClient")
  private SqlMapClient sqlMapClient;


  /**
   * 统计记录数
   * 
   * @param bnesAcctTransListQuery
   * @return 查出的记录数
   */
  public Integer countBnesAcctTransListQueryByExample(BnesAcctTransListQuery bnesAcctTransListQuery)
      throws DataAccessException {
    Integer count = 0;
    try {
      List list = sqlMapClient.queryForList("BnesAcctTransList.countByQueryExample",
          bnesAcctTransListQuery);
      SysModelUtil countResult = (SysModelUtil) list.get(0);
      // 总条数
      count = countResult.getTheCount().intValue();

    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return count;
  }


  /**
   * 获取对象列表
   * 
   * @param bnesAcctTransListQuery
   * @return 对象列表
   */
  @SuppressWarnings("unchecked")
  public List<BnesAcctTransListQuery> findListByExample(
      BnesAcctTransListQuery bnesAcctTransListQuery) throws DataAccessException {
    List<BnesAcctTransListQuery> list = null;
    try {
      list = sqlMapClient.queryForList("BnesAcctTransList.findListByQuery", bnesAcctTransListQuery);
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return list;
  }


  /**
   * 插入数据
   * 
   * @param bnesAcctTransListDO
   * @return 插入数据的主键
   */
  public Integer insertBnesAcctTransListDO(BnesAcctTransListDO bnesAcctTransListDO)
      throws DataAccessException {
    Object TRANS_LIST_ID = null;
    try {
      TRANS_LIST_ID = sqlMapClient.insert("BnesAcctTransList.insert", bnesAcctTransListDO);
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return (Integer) TRANS_LIST_ID;
  }

  // -------------------------------------------------------------------------------------------
  /*
    
  
    *//**
       * 统计记录数
       * 
       * @param bnesAcctTransListDO
       * @return 查出的记录数
       */
  /*
   * public Integer countBnesAcctTransListDOByExample(BnesAcctTransListDO bnesAcctTransListDO)
   * throws DataAccessException { Integer count = null; try { count = (Integer)
   * sqlMapClient.queryForObject("BnesAcctTransList.countByDOExample", bnesAcctTransListDO); } catch
   * (SQLException e) { // TODO Auto-generated catch block e.printStackTrace(); } return count; }
   * 
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
   * DataAccessException { int result = 0; try { result =
   * sqlMapClient.update("BnesAcctTransList.update", bnesAcctTransListDO); } catch (SQLException e)
   * { // TODO Auto-generated catch block e.printStackTrace(); } return result; }
   * 
   *//**
     * 获取对象列表
     * 
     * @param bnesAcctTransListDO
     * @return 对象列表
     */
  /*
   * @SuppressWarnings("unchecked") public List<BnesAcctTransListDO>
   * findListByExample(BnesAcctTransListDO bnesAcctTransListDO) throws DataAccessException {
   * List<BnesAcctTransListDO> list = null; try { list =
   * sqlMapClient.queryForList("BnesAcctTransList.findListByDO", bnesAcctTransListDO); } catch
   * (SQLException e) { // TODO Auto-generated catch block e.printStackTrace(); } return list; }
   * 
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
   * DataAccessException { BnesAcctTransListDO bnesAcctTransListDO = null; try { bnesAcctTransListDO
   * = (BnesAcctTransListDO) sqlMapClient.queryForObject("BnesAcctTransList.findByPrimaryKey",
   * transListId); } catch (SQLException e) { // TODO Auto-generated catch block
   * e.printStackTrace(); } return bnesAcctTransListDO; }
   * 
   *//**
     * 删除记录
     * 
     * @return 受影响的行数
     *//*
       * public Integer deleteBnesAcctTransListDOByPrimaryKey(Integer transListId) throws
       * DataAccessException { Integer rows = null; try { rows = (Integer)
       * sqlMapClient.delete("BnesAcctTransList.deleteByPrimaryKey", transListId); } catch
       * (SQLException e) { // TODO Auto-generated catch block e.printStackTrace(); } return rows; }
       */

  public SqlMapClient getSqlMapClient() {
    return sqlMapClient;
  }

  public void setSqlMapClient(SqlMapClient sqlMapClient) {
    this.sqlMapClient = sqlMapClient;
  }


  @Override
  public List<BnesAcctTransListQuery> queryAllBnesAcctTransList(
      BnesAcctTransListQuery bnesAcctTransListQuery) throws SQLException {
    return sqlMapClient.queryForList("BnesAcctTransList.AllBnesAcctTransListQuery",
        bnesAcctTransListQuery);
  }

}
