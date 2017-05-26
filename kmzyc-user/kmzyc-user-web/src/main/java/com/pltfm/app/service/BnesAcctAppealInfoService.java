package com.pltfm.app.service;

import java.sql.SQLException;
import java.util.List;

import com.kmzyc.commons.page.Page;
import com.pltfm.app.vobject.BnesAcctAppealInfo;
import com.pltfm.app.vobject.BnesAcctAppealInfoQry;

@SuppressWarnings("unused")
public interface BnesAcctAppealInfoService {
  // 添加申诉
  void insert(BnesAcctAppealInfo record) throws SQLException;

  // 综合查询
  List selectByExample(BnesAcctAppealInfoQry qry) throws SQLException;

  // 记录数
  int countByExample(BnesAcctAppealInfoQry qry) throws SQLException;

  // 根据主键返回一个对象
  BnesAcctAppealInfo selectByPrimaryKey(Integer integer) throws SQLException;

  /**
   * 分页查询申诉信息
   * 
   * @param BnesAcctAppealInfoQry 申诉实体
   * @return
   * @throws Exception 异常
   */
  public Page queryForPage(BnesAcctAppealInfoQry qry, Page page) throws Exception;

  // 添加申诉
  int insertAcctAppealInfo(BnesAcctAppealInfo record) throws SQLException;

}
