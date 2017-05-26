package com.pltfm.crowdsourcing.dao.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.km.commons.general.model.Page;
import com.km.crowdsourcing.model.ClearingUser;
import com.km.crowdsourcing.model.InstitutionUser;
import com.pltfm.crowdsourcing.dao.InstitutionUserDao;

@Component(value = "institutionUserDao")
public class InstitutionUserDaoImpl implements InstitutionUserDao {

  @Resource(name = "sqlMapClient")
  /**
   * 数据库操作对象
   */
  private SqlMapClient sqlMapClient;

  @Override
  public int deleteByPrimaryKey(Long id) {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public int insert(InstitutionUser record) {
    return 0;
  }

  @Override
  public int insertSelective(InstitutionUser record) throws Exception {
    return (Integer) sqlMapClient.insert("CrowdSourcingUser.ibatorgenerated_insertSelective",
        record);
  }

  @Override
  public int checkUserExists(String mobie) throws Exception {
    return (Integer) sqlMapClient.queryForObject("CrowdSourcingUser.checkUserExists", mobie);
  }

  @Override
  public int updateByPrimaryKeySelective(InstitutionUser record) {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public int updateByPrimaryKey(InstitutionUser record) throws SQLException {
    return (Integer) sqlMapClient
        .update("CrowdSourcingUser.ibatorgenerated_updateByPrimaryKeySelective", record);
  }

  @Override
  public List<ClearingUser> selectByClearingUser(List<String> ids) throws SQLException {
    return sqlMapClient.queryForList("CrowdSourcingUser.ibatorgenerated_selectClearingUser", ids);
  }

  @Override
  public int selectCountByVo(Page record) throws SQLException {
    return (Integer) sqlMapClient.queryForObject("CrowdSourcingUser.selectCountByVo", record);
  }

  @Override
  public List selectPageByVo(Page record) throws SQLException {
    return sqlMapClient.queryForList("CrowdSourcingUser.selectListByVo", record);

  }

  @Override
  public String getinstitutionUserIds(Page nPage) throws Exception {
    return (String) sqlMapClient.queryForObject("CrowdSourcingUser.getinstitutionUserIds", nPage);
  }


  @Override
  public List<String> selectIdsByClearingUser(List<String> ids) throws Exception {
    return sqlMapClient.queryForList("CrowdSourcingUser.selectIdsByClearingUser", ids);
  }

  @Override
  public List<String> selectIdsByUser(InstitutionUser record) throws Exception {
    Page nPage = new Page();
    nPage.setQueryCondition(record);
    return sqlMapClient.queryForList("CrowdSourcingUser.selectIdsByUser", nPage);
  }

  @Override
  public Integer unClearByIds(String ids) throws Exception {
    return (Integer) sqlMapClient.update("CrowdSourcingUser.unClearByIds", ids);

  }

  @Override
  public List<ClearingUser> selectByClearingUserIds(List list) throws Exception {

    return sqlMapClient.queryForList("CrowdSourcingUser.selectIdsByIds", list);
  }

}
