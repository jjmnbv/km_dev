package com.kmzyc.b2b.service;

import com.pltfm.app.vobject.BnesAcctAppealInfo;

import java.sql.SQLException;


/**
 * 建议投诉Service接口
 * 
 * @author luoyi
 * @createDate 2013/10/10
 */
public interface MemberComplaintService {

  /**
   * 保存用户的建议投诉
   * 
   * @param bnesAcctAppealInfo
   * @return
   * @throws SQLException
   */
  void saveComplaintsByUser(BnesAcctAppealInfo bnesAcctAppealInfo) throws SQLException;

}
