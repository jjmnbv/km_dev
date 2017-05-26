package com.pltfm.app.service;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import com.kmzyc.commons.page.Page;
import com.pltfm.app.vobject.ReserverInfo;

public interface ReserverInfoService {
  /**
   * 获取所有预备金账户
   * 
   * @return
   * @throws Exception
   */
  List<ReserverInfo> selectAllReserverInfo() throws Exception;

  /**
   * 分页条件查询预备金列表
   * 
   * @param page
   * @param vo
   * @return
   * @throws SQLException
   */
  Page selectReserverInfo(Page page, ReserverInfo vo) throws SQLException;

  /**
   * 分页条件查询开通了预备金列表
   * 
   * @param page
   * @param vo
   * @return
   * @throws SQLException
   */
  Page selectReserverInfoAll(Page page, ReserverInfo vo) throws SQLException;

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
   * 根据预备金账户查询
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
