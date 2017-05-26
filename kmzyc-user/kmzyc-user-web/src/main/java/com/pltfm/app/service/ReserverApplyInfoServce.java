package com.pltfm.app.service;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import com.kmzyc.commons.page.Page;
import com.pltfm.app.vobject.ReserverApplyInfo;

public interface ReserverApplyInfoServce {
  /**
   * 分页条件查询预备级你审核列表
   * 
   * @return
   * @throws Exception
   */
  Page queryReserverApplyInfoList(Page page, ReserverApplyInfo record) throws Exception;

  /**
   * 根据申请记录id删除
   * 
   * @param applyNotesId
   * @return
   * @throws SQLException
   */
  int deleteByPrimaryKey(BigDecimal applyNotesId) throws SQLException;

  /**
   * 根据条件查询申请记录
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
