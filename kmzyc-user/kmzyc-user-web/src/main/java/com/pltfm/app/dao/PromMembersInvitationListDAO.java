package com.pltfm.app.dao;

import com.pltfm.app.vobject.PromMembersInvitationList;

import java.math.BigDecimal;
import java.sql.SQLException;

public interface PromMembersInvitationListDAO {
  /**
   * 删除
   * 
   * @param membersInvitationListId
   * @return
   * @throws SQLException
   */
  int deleteByPrimaryKey(BigDecimal membersInvitationListId) throws SQLException;

  /**
   * 添加
   * 
   * @param record
   * @throws SQLException
   */
  BigDecimal insert(PromMembersInvitationList record) throws SQLException;

  /**
   * 查询
   * 
   * @param membersInvitationListId
   * @return
   * @throws SQLException
   */
  PromMembersInvitationList selectByPrimaryKey(PromMembersInvitationList record)
      throws SQLException;

  /**
   * 修改
   * 
   * @param record
   * @return
   * @throws SQLException
   */
  int updateByPrimaryKeySelective(PromMembersInvitationList record) throws SQLException;
}
