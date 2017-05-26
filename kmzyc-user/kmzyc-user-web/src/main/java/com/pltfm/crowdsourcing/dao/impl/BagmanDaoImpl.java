package com.pltfm.crowdsourcing.dao.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.km.crowdsourcing.model.Bagman;
import com.pltfm.crowdsourcing.dao.BagmanDao;

@Component(value = "bagmanDao")
public class BagmanDaoImpl implements BagmanDao {

  private Logger logger = LoggerFactory.getLogger(BagmanDaoImpl.class);
  @Resource(name = "sqlMapClient")
  private SqlMapClient sqlMapClient;

  @Override
  public void insert(Bagman record) throws SQLException {
    sqlMapClient.insert("CrowdSourcingBagman.ibatorgenerated_insert", record);
  }

  @Override
  public Bagman selectByPrimaryKey(Long id) throws SQLException {
    Bagman bagman = new Bagman();
    bagman.setId(id);
    return (Bagman) sqlMapClient
        .queryForObject("CrowdSourcingBagman.ibatorgenerated_selectByPrimaryKey", bagman);
  }

  @Override
  public int updateByPrimaryKeySelective(Bagman record) throws SQLException {
    return sqlMapClient.update("CrowdSourcingBagman.ibatorgenerated_updateByPrimaryKeySelective",
        record);
  }

  @Override
  public int updateByBagManId(Long bagmanid) throws SQLException {
    return sqlMapClient.update("CrowdSourcingBagman.ibatorgenerated_updateByBagManId", bagmanid);
  }



  @SuppressWarnings("unchecked")
  @Override
  public List<Map<String, Object>> existsBmans(Bagman record) throws SQLException {
    return sqlMapClient.queryForList("CrowdSourcingBagman.ibatorgenerated_existsBmans", record);
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<Bagman> listBagMans(Bagman bagman) throws SQLException {
    return sqlMapClient.queryForList("CrowdSourcingBagman.listBagMans", bagman);
  }

  @Override
  public int countBagMans(Bagman bagman) throws SQLException {
    return (Integer) sqlMapClient.queryForObject("CrowdSourcingBagman.countBagMans", bagman);
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<Map<String, Object>> ajaxBagManInfos() throws SQLException {
    return (List<Map<String, Object>>) sqlMapClient
        .queryForList("CrowdSourcingBagman.ajaxBagManInfos");
  }

  @Override
  public Bagman selectByBagman(Bagman bagman) {
    try {
      return (Bagman) sqlMapClient.queryForObject("CrowdSourcingBagman.selectByBagman", bagman);
    } catch (SQLException e) {
      logger.error("e", e);
      return null;
    }
  }

}
