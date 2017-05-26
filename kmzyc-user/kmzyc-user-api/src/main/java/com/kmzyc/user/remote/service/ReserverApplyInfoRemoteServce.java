package com.kmzyc.user.remote.service;

import java.math.BigDecimal;
import java.sql.SQLException;

import com.pltfm.app.vobject.ReserverApplyInfo;

/**
 * 预备金申请相关信息远程接口
 * 
 * @author cjm
 * @since 2013-8-16
 */
@SuppressWarnings("unused")
public interface ReserverApplyInfoRemoteServce {
  /**
   * 添加申请记录
   * 
   * @param record
   * @throws SQLException
   */
  void insertSelective(ReserverApplyInfo record) throws Exception;

  /**
   * 根据申请记录id查询申请记录
   * 
   * @param applyNotesId
   * @return
   * @throws SQLException
   */
  ReserverApplyInfo selectByPrimaryKey(BigDecimal applyNotesId) throws Exception;
}
