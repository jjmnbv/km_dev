package com.pltfm.app.dao;

import com.pltfm.app.vobject.ScoreInfo;
import com.pltfm.app.vobject.ScoreInfoExample;

import java.sql.SQLException;
import java.util.List;

/**
 * 积分明细数据操作接口
 * 
 * @author zhl
 * @since 2013-07-24
 */
public interface ScoreInfoDAO {
  /**
   * 添加积分明细数据
   * 
   * @param scoreInfo 积分明细实体
   * @throws SQLException 异常
   */
  public Integer insert(ScoreInfo record) throws SQLException;

  /**
   * 通过主键更新积分明细
   * 
   * @param scoreInfo 积分明细实体
   * @return
   * @throws SQLException 异常
   */
  public int updateByPrimaryKey(ScoreInfo scoreInfo) throws SQLException;

  /**
   * 多条件查询积分明细信息
   * 
   * @param example 积分明细查询实体
   * @return
   * @throws SQLException 异常
   */
  public List selectByExample(ScoreInfoExample example) throws SQLException;

  /**
   * 通过积分明细主键查询积分明细信息
   * 
   * @param scoreInfoId
   * @return
   * @throws SQLException
   */
  public ScoreInfo selectByPrimaryKey(Integer scoreInfoId) throws SQLException;

  /**
   * 通过积分明细主键删除积分明细信息
   * 
   * @param scoreInfoId 积分明细主键
   * @return
   * @throws SQLException 异常
   */
  public int deleteByPrimaryKey(Integer scoreInfoId) throws SQLException;

  /**
   * 查询统计积分明细总数
   * 
   * @param scoreInfo 积分明细实体
   * @return
   * @throws SQLException 异常
   */
  public int countByExample(ScoreInfo scoreInfo) throws SQLException;

  /**
   * 查询统计积分明细总数(卡号)
   * 
   * @param scoreInfo 积分明细实体
   * @return
   * @throws SQLException 异常
   */
  public int countByCardNum(ScoreInfo scoreInfo) throws SQLException;

  /**
   * 分页查询积分明细(卡号)
   * 
   * @param scoreInfo 积分明细实体
   * @return
   * @throws SQLException 异常
   */
  public List queryForPageByCardNum(ScoreInfo scoreInfo) throws SQLException;

  /**
   * 分页查询积分明细
   * 
   * @param scoreInfo 积分明细实体
   * @return
   * @throws SQLException 异常
   */
  public List queryForPage(ScoreInfo scoreInfo) throws SQLException;

  /**
   * 查询每日积分总和
   * 
   * @param Integer n_login_id,Integer n_score_rule_id
   * @return
   * @throws SQLException 异常
   */

  public int isDayMaxSorce(ScoreInfo scoreInfo) throws SQLException;
}
