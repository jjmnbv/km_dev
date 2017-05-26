package com.pltfm.app.dao.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.pltfm.app.dao.ScoreInfoDAO;
import com.pltfm.app.vobject.ScoreInfo;
import com.pltfm.app.vobject.ScoreInfoExample;

/**
 * 积分明细数据操作实现类
 * 
 * @author zhl
 * @since 2013-07-24
 */
@SuppressWarnings("unchecked")
@Component(value = "scoreInfoDAO")
public class ScoreInfoDAOImpl implements ScoreInfoDAO {
  @Resource(name = "sqlMapClient")
  private SqlMapClient sqlMapClient;

  /**
   * 保存积分明细信息
   */
  public Integer insert(ScoreInfo record) throws SQLException {
    Integer rows = (Integer) sqlMapClient.insert("SCORE_INFO.abatorgenerated_insert", record);
    return rows;
  }

  /**
   * 通过主键更新积分明细信息
   */
  public int updateByPrimaryKey(ScoreInfo scoreInfo) throws SQLException {
    int rows = sqlMapClient.update("SCORE_INFO.abatorgenerated_updateByPrimaryKey", scoreInfo);
    return rows;
  }

  /**
   * 多条件查询积分明细信息
   */
  public List selectByExample(ScoreInfoExample example) throws SQLException {
    List list = sqlMapClient.queryForList("SCORE_INFO.abatorgenerated_selectByExample", example);
    return list;
  }

  /**
   * 通过积分明细主键查询积分明细信息
   * 
   * @param scoreInfoId 积分明细主键
   * @return
   * @throws SQLException 异常
   */
  public ScoreInfo selectByPrimaryKey(Integer scoreInfoId) throws SQLException {
    return (ScoreInfo) sqlMapClient.queryForObject("SCORE_INFO.abatorgenerated_selectByPrimaryKey",
        scoreInfoId);
  }

  /**
   * 通过积分明细主键删除积分明细信息
   * 
   * @param scoreInfoId 积分明细主键
   * @return
   * @throws SQLException 异常
   */
  public int deleteByPrimaryKey(Integer scoreInfoId) throws SQLException {
    ScoreInfo key = new ScoreInfo();
    key.setN_scoreInfoId(scoreInfoId);
    int rows = sqlMapClient.delete("SCORE_INFO.abatorgenerated_deleteByPrimaryKey", key);
    return rows;
  }

  /**
   * 查询积分明细信息总数
   * 
   * @param scoreInfo 积分明细实体
   * @return
   * @throws SQLException 异常
   */
  public int countByExample(ScoreInfo scoreInfo) throws SQLException {
    Integer count = 0;
    count = (Integer) sqlMapClient.queryForObject("SCORE_INFO.abatorgenerated_countByScoreInfo",
        scoreInfo);
    return count.intValue();
  }

  /**
   * 查询每日积分总和
   * 
   * @param Integer n_login_id,Integer n_score_rule_id
   * @return
   * @throws SQLException 异常
   */

  public int isDayMaxSorce(ScoreInfo scoreInfo) throws SQLException {
    Integer count = 0;
    try {
      count = (Integer) sqlMapClient.queryForObject("SCORE_INFO.countIsDayMaxSorce", scoreInfo);
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return count.intValue();
  }

  /**
   * 分页查询积分明细信息
   * 
   * @param scoreInfo 积分明细实体
   * @return
   * @throws SQLException 异常
   */
  public List queryForPage(ScoreInfo scoreInfo) throws SQLException {
    return sqlMapClient.queryForList("SCORE_INFO.abatorgenerated_pageByScoreInfo", scoreInfo);
  }


  @Override
  public int countByCardNum(ScoreInfo scoreInfo) throws SQLException {
    Integer count = 0;
    count = (Integer) sqlMapClient.queryForObject("SCORE_INFO.abatorgenerated_countByByCardNum",
        scoreInfo);
    return count.intValue();
  }

  @Override
  public List queryForPageByCardNum(ScoreInfo scoreInfo) throws SQLException {
    return sqlMapClient.queryForList("SCORE_INFO.abatorgenerated_pageByCardNum", scoreInfo);
  }

  public SqlMapClient getSqlMapClient() {
    return sqlMapClient;
  }

  public void setSqlMapClient(SqlMapClient sqlMapClient) {
    this.sqlMapClient = sqlMapClient;
  }

}
