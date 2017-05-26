package com.pltfm.app.dao.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.pltfm.app.dao.PublishMessageDAO;
import com.pltfm.app.vobject.BnesInfoPrompt;

@Component(value = "publishMessageDao")
public class PublishMessageDAOImpl implements PublishMessageDAO {
  @Resource(name = "sqlMapClient")
  private SqlMapClient sqlMapClient;

  // 增加
  @Override
  public void addMessage(BnesInfoPrompt infoPrompt) throws SQLException {

    sqlMapClient.insert("BnesInfoPrompt.insertPrompt", infoPrompt);
    // infoPrompt.getLoginId()
  }

  // 查询
  public BnesInfoPrompt queryPrompt(BnesInfoPrompt infoPrompt) throws SQLException {
    return (BnesInfoPrompt) sqlMapClient.queryForObject("BnesInfoPrompt.detailPrompt", infoPrompt);
  }

  // 修改
  public void updateMessage(BnesInfoPrompt infoPrompt) throws SQLException {
    sqlMapClient.update("BnesInfoPrompt.updatePrompt", infoPrompt);
  }

  // 查询列表
  public List selectList(BnesInfoPrompt infoPrompt) throws SQLException {
    List pageList = sqlMapClient.queryForList("BnesInfoPrompt.searchPageByVo", infoPrompt);
    return pageList;


  }
  // 记录数

  public Integer selectListCount(BnesInfoPrompt infoPrompt) throws SQLException {
    Integer count =
        (Integer) sqlMapClient.queryForObject("BnesInfoPrompt.countBnesInfoPrompt", infoPrompt);
    return count;


  }

  // 删除
  public void delete(BnesInfoPrompt infoPrompt) throws SQLException {


    sqlMapClient.delete("BnesInfoPrompt.deletePrompt", infoPrompt);

  }

  public List selectBnesInfoPromp() throws SQLException {
    List list = sqlMapClient.queryForList("BnesInfoPrompt.ibatorgenerated_select");
    return list;
  }


  // Page selectPageByVo(Page page, PersonalityInfo vo) throws SQLException;
  public SqlMapClient getSqlMapClient() {
    return sqlMapClient;
  }

  public void setSqlMapClient(SqlMapClient sqlMapClient) {
    this.sqlMapClient = sqlMapClient;
  }


}
