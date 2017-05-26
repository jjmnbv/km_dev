package com.pltfm.app.dao;

import com.pltfm.app.vobject.PromInvitedOrganizations;

import java.math.BigDecimal;
import java.sql.SQLException;

public interface PromInvitedOrganizationsDAO {

  /**
   * 删除机构信息
   * 
   * @param invitedOrganizationsId
   * @return
   * @throws SQLException
   */
  int deleteByPrimaryKey(BigDecimal invitedOrganizationsId) throws SQLException;

  /**
   * 添加机构信息
   * 
   * @param record
   * @throws SQLException
   */
  void insert(PromInvitedOrganizations record) throws SQLException;

  /**
   * 查询机构信息
   * 
   * @param invitedOrganizationsId
   * @return
   * @throws SQLException
   */
  PromInvitedOrganizations selectByPrimaryKey(PromInvitedOrganizations record) throws SQLException;

  /**
   * 修改机构信息
   * 
   * @param record
   * @return
   * @throws SQLException
   */
  int updateByPrimaryKeySelective(PromInvitedOrganizations record) throws SQLException;

}
