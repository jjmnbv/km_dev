package com.pltfm.app.dao;

import com.pltfm.app.vobject.ReserverInfo;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public interface ReserverInfoDAO {
  /**
   * 根据用户名获取预备金账户
   * 
   * @param accountLogin
   * @return
   * @throws SQLException
   */
  List<ReserverInfo> selectReserverInfoByAccountLogin(String accountLogin) throws SQLException;

  /**
   * 获取所有的预备金账户
   * 
   * @return
   * @throws SQLException
   */
  List<ReserverInfo> selectAllReserverInfo() throws SQLException;

  /**
   * 分页查询预备金列表
   * 
   * @param vo
   * @return
   * @throws SQLException
   */
  List<ReserverInfo> selectReserverInfo(ReserverInfo vo) throws SQLException;

  /**
   * 条件查询预备金列表总条数
   * 
   * @param vo
   * @return
   * @throws SQLException
   */
  int selectCountByInfo(ReserverInfo vo) throws SQLException;

  /**
   * 分页查询开通了预备金列表
   * 
   * @param vo
   * @return
   * @throws SQLException
   */
  List<ReserverInfo> selectReserverInfoAll(ReserverInfo vo) throws SQLException;

  /**
   * 条件查询开通了预备金列表总条数
   * 
   * @param vo
   * @return
   * @throws SQLException
   */
  int selectCountByInfoAll(ReserverInfo vo) throws SQLException;

  /**
   * 根据预备金账户id删除
   * 
   * @param reserveId
   * @return
   * @throws SQLException
   */
  int deleteByPrimaryKey(BigDecimal reserveId) throws SQLException;

  /**
   * 添加预备金账户
   * 
   * @param record
   * @throws SQLException
   */
  void insertSelective(ReserverInfo record) throws SQLException;

  /**
   * 预备金账查询
   * 
   * @param reserveId
   * @return
   * @throws SQLException
   */
  ReserverInfo selectByPrimaryKey(ReserverInfo record) throws SQLException;

  /**
   * 修改预备金账户信息
   * 
   * @param record
   * @return
   * @throws SQLException
   */
  int updateByPrimaryKey(ReserverInfo record) throws SQLException;
}
