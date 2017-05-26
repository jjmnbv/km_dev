package com.pltfm.app.dao;

import com.pltfm.app.vobject.ReserverApplyInfo;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public interface ReserverApplyInfoDAO {
  /**
   * 获取条件查询下预备金审核列表总数
   * 
   * @param record
   * @return
   * @throws SQLException
   */
  Integer pageQueryApplyInfoCount(ReserverApplyInfo record) throws SQLException;

  /**
   * 分页条件查询预备金审核列表
   * 
   * @param record
   * @return
   * @throws SQLException
   */
  List<ReserverApplyInfo> pageQueryApplyInfo(ReserverApplyInfo record) throws SQLException;

  /**
   * 根据申请记录id删除
   * 
   * @param applyNotesId
   * @return
   * @throws SQLException
   */
  int deleteByPrimaryKey(BigDecimal applyNotesId) throws SQLException;

  /**
   * 添加申请记录
   * 
   * @param record
   * @throws SQLException
   */
  void insertSelective(ReserverApplyInfo record) throws SQLException;

  /**
   * 条件查询申请记录
   * 
   * @param applyNotesId
   * @return
   * @throws SQLException
   */
  List<ReserverApplyInfo> selectByPrimaryKey(ReserverApplyInfo record) throws SQLException;

  /**
   * 根据申请记录id修改申请记录
   * 
   * @param record
   * @return
   * @throws SQLException
   */
  int updateByPrimaryKey(ReserverApplyInfo record) throws SQLException;
}
