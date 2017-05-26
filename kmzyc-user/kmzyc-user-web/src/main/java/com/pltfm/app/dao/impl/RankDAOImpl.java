package com.pltfm.app.dao.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.pltfm.app.dao.RankDAO;
import com.pltfm.app.dataobject.RankDO;
import com.pltfm.app.vobject.Rank;
import com.pltfm.app.vobject.RankExample;
import com.pltfm.sys.model.SysModelUtil;

/**
 * 客户头衔信息处理
 * 
 * @author gwl
 * @since 2013-07-08
 */
@Component(value = "rankDAO")
public class RankDAOImpl implements RankDAO {
  @Resource(name = "sqlMapClient")
  private SqlMapClient sqlMapClient;

  /**
   * 删除客户头衔信息
   * 
   * @param Rank 客户头衔信息实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  public Integer deleteRank(Rank rank) throws SQLException {
    int rows = sqlMapClient.delete("RANK.ibatorgenerated_deleteByPrimaryKey", rank);
    return rows;
  }

  /**
   * 添加客户头衔信息
   * 
   * @param Rank 客户头衔等级实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  public void insertRank(Rank rank) throws SQLException {
    sqlMapClient.insert("RANK.ibatorgenerated_insert", rank);
  }


  /**
   * 按类型条件查询息总数量
   * 
   * @param vo
   * @return 返回值
   */
  public Integer selectCountRank(Rank vo) throws SQLException {
    Object object = sqlMapClient.queryForObject("RANK.getCountRank", vo);
    return (Integer) object;
  }

  /**
   * 按条件查询息总数量
   * 
   * @param vo
   * @return 返回值
   */
  public int selectCountByVo(Rank vo) throws SQLException {
    List list = sqlMapClient.queryForList("RANK.selectCountByVo", vo);

    SysModelUtil countResult = (SysModelUtil) list.get(0);
    // 总条数
    int recs = countResult.getTheCount().intValue();
    return recs;
  }

  /**
   * 查询是否已存在头衔编号
   * 
   * @param vo
   * @return 返回值
   */
  public List selectCode(String code) throws SQLException {
    List list = sqlMapClient.queryForList("RANK.ibatorgenerated_selectCode", code);
    return list;
  }

  /**
   * 查询是否已存在头衔名称
   * 
   * @param vo
   * @return 返回值
   */
  public List selectRankName(String rankName) throws SQLException {
    List list = sqlMapClient.queryForList("RANK.selectRankName", rankName);
    return list;
  }

  /**
   * 按类型查询最新记录
   * 
   * @param vo
   * @return 返回值
   */
  public Rank maxRankiId(Rank vo) throws SQLException {
    return (Rank) sqlMapClient.queryForObject("RANK.maxRankiId", vo);
  }

  /**
   * 根据vo条件查询分类信息page
   * 
   * @param page 分页类
   * @param vo 信息实体
   * @return 返回值
   * @throws SQLException sql异常
   */

  public List selectPageByVo(Rank vo) throws SQLException {
    List pageList = sqlMapClient.queryForList("RANK.searchPageByVo", vo);
    return pageList;
  }

  /**
   * 修改客户头衔信息
   * 
   * @param Rank 客户头衔信息实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  public Integer udpateRank(Rank rank) throws SQLException {
    int rows = sqlMapClient.update("RANK.ibatorgenerated_updateByPrimaryKey", rank);
    return rows;
  }

  /**
   * 跟据个人id查询客户头衔信息
   * 
   * @param Rank 客户头衔信息实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  public Rank getRankInfoId(Integer value) throws SQLException {
    // TODO Auto-generated method stub
    /*
     * Rank key=new Rank(); key.setRankId(value); Rank rank=(Rank)
     * sqlMapClient.queryForList("RANK.ibatorgenerated_getRankInfoId", key);
     */
    return (Rank) sqlMapClient.queryForObject("RANK.ibatorgenerated_getRankInfoId", value);
  }

  /**
   * 跟据个性数据查对应该头衔
   * 
   **/
  public Rank getRankiId(RankDO rank) throws SQLException {
    return (Rank) sqlMapClient.queryForObject("RANK.getRankiId", rank);
  }

  /**
   * 跟据客户类型来查询客户头衔信息
   * 
   **/
  public List getCustomerTypeIdKey(Integer customerTypeId) throws SQLException {
    Rank rank = new Rank();
    rank.setCustomerTypeId(customerTypeId);
    return sqlMapClient.queryForList("RANK.getCustomerTypeIdKey", rank);
  }

  /**
   * 跟据客户类型来查询客户头最大经验值
   * 
   **/
  public Integer getScoreMaxs(Rank rank) throws SQLException {
    Integer count = (Integer) sqlMapClient.queryForObject("RANK.abatorgenerated_getScoreMax", rank);
    return count;
  }

  private static class UpdateByExampleParms extends RankExample {
    private Object record;

    public UpdateByExampleParms(Object record, RankExample example) {
      super(example);
      this.record = record;
    }

    public Object getRecord() {
      return record;
    }
  }

  public SqlMapClient getSqlMapClient() {
    return sqlMapClient;
  }

  public void setSqlMapClient(SqlMapClient sqlMapClient) {
    this.sqlMapClient = sqlMapClient;
  }
}
