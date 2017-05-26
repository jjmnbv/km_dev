package com.pltfm.app.dao.impl;

import java.math.BigDecimal;
import java.sql.SQLException;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.pltfm.app.dao.PromMembersInvitationListDAO;
import com.pltfm.app.vobject.PromMembersInvitationList;

@Component(value = "promMembersInvitationListDAO")
public class PromMembersInvitationListDAOImpl implements PromMembersInvitationListDAO {
  @Resource(name = "sqlMapClient")
  private SqlMapClient sqlMapClient;


  /**
   * 删除
   * 
   * @param membersInvitationListId
   * @return
   * @throws SQLException
   */
  public int deleteByPrimaryKey(BigDecimal membersInvitationListId) throws SQLException {
    PromMembersInvitationList key = new PromMembersInvitationList();
    key.setMembersInvitationListId(membersInvitationListId);
    int rows =
        sqlMapClient.delete("PROM_MEMBERS_INVITATION_LIST.ibatorgenerated_deleteByPrimaryKey", key);
    return rows;
  }

  /**
   * 添加
   * 
   * @param record
   * @throws SQLException
   */
  public BigDecimal insert(PromMembersInvitationList record) throws SQLException {
    return (BigDecimal) sqlMapClient.insert("PROM_MEMBERS_INVITATION_LIST.ibatorgenerated_insert",
        record);
  }

  /**
   * 查询
   * 
   * @param membersInvitationListId
   * @return
   * @throws SQLException
   */
  public PromMembersInvitationList selectByPrimaryKey(PromMembersInvitationList record)
      throws SQLException {
    record = (PromMembersInvitationList) sqlMapClient
        .queryForObject("PROM_MEMBERS_INVITATION_LIST.ibatorgenerated_selectByPrimaryKey", record);
    return record;
  }

  /**
   * 修改
   * 
   * @param record
   * @return
   * @throws SQLException
   */
  public int updateByPrimaryKeySelective(PromMembersInvitationList record) throws SQLException {
    int rows = sqlMapClient
        .update("PROM_MEMBERS_INVITATION_LIST.ibatorgenerated_updateByPrimaryKeySelective", record);
    return rows;
  }
}
