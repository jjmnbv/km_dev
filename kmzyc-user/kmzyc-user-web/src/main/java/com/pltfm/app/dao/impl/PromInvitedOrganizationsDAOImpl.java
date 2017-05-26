package com.pltfm.app.dao.impl;

import java.math.BigDecimal;
import java.sql.SQLException;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.pltfm.app.dao.PromInvitedOrganizationsDAO;
import com.pltfm.app.vobject.PromInvitedOrganizations;

@Component(value = "promInvitedOrganizationsDAO")
public class PromInvitedOrganizationsDAOImpl implements PromInvitedOrganizationsDAO {
  @Resource(name = "sqlMapClient")
  private SqlMapClient sqlMapClient;

  public int deleteByPrimaryKey(BigDecimal invitedOrganizationsId) throws SQLException {
    PromInvitedOrganizations key = new PromInvitedOrganizations();
    key.setInvitedOrganizationsId(invitedOrganizationsId);
    int rows =
        sqlMapClient.delete("PROM_INVITED_ORGANIZATIONS.ibatorgenerated_deleteByPrimaryKey", key);
    return rows;
  }

  public void insert(PromInvitedOrganizations record) throws SQLException {
    sqlMapClient.insert("PROM_INVITED_ORGANIZATIONS.ibatorgenerated_insert", record);
  }


  public PromInvitedOrganizations selectByPrimaryKey(PromInvitedOrganizations record)
      throws SQLException {
    record = (PromInvitedOrganizations) sqlMapClient
        .queryForObject("PROM_INVITED_ORGANIZATIONS.ibatorgenerated_selectByPrimaryKey", record);
    return record;
  }


  public int updateByPrimaryKeySelective(PromInvitedOrganizations record) throws SQLException {
    int rows = sqlMapClient
        .update("PROM_INVITED_ORGANIZATIONS.ibatorgenerated_updateByPrimaryKeySelective", record);
    return rows;
  }

}
