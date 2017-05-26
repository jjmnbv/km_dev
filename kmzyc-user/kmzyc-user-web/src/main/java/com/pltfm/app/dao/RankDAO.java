package com.pltfm.app.dao;

import com.pltfm.app.dataobject.RankDO;
import com.pltfm.app.vobject.Rank;

import java.sql.SQLException;
import java.util.List;

/**
 * 客户头衔信息处理接口
 * 
 * @author gwl
 * @since 2013-07-08
 */
public interface RankDAO {
  /**
   * 添加客户头衔信息
   * 
   * @param Rank 客户头衔等级实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  public void insertRank(Rank rank) throws SQLException;

  /**
   * 删除客户头衔信息
   * 
   * @param Rank 客户头衔信息实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  public Integer deleteRank(Rank rank) throws SQLException;

  /**
   * 按条件查询基本信息总数量
   * 
   * @param vo
   * @return 返回值
   */
  int selectCountByVo(Rank vo) throws SQLException;

  /**
   * 根据vo条件查询分类信息page
   * 
   * @param page 分页类
   * @param vo 信息实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  public List selectPageByVo(Rank vo) throws SQLException;

  /**
   * 按类型条件查询息总数量
   * 
   * @param vo
   * @return 返回值
   */
  public Integer selectCountRank(Rank vo) throws SQLException;

  /**
   * 按类型查询最新记录
   * 
   * @param vo
   * @return 返回值
   */
  public Rank maxRankiId(Rank vo) throws SQLException;

  /**
   * 查询是否已存在头衔编号
   * 
   * @param vo
   * @return 返回值
   */
  public List selectCode(String code) throws SQLException;

  /**
   * 查询是否已存在头衔名称
   * 
   * @param vo
   * @return 返回值
   */
  public List selectRankName(String rankName) throws SQLException;

  /**
   * 修改客户头衔信息
   * 
   * @param Rank 客户头衔信息实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  public Integer udpateRank(Rank rank) throws SQLException;

  /**
   * 跟据个人id查询客户头衔信息
   * 
   * @param Rank 客户头衔信息实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  public Rank getRankInfoId(Integer rankId) throws SQLException;

  /**
   * 跟据个性数据查对应该头衔
   * 
   **/
  public Rank getRankiId(RankDO rank) throws SQLException;

  /**
   * 跟据客户类型来查询客户头衔信息
   * 
   **/
  public List getCustomerTypeIdKey(Integer customerTypeId) throws SQLException;

  /**
   * 跟据客户类型来查询客户头最大经验值
   * 
   **/
  public Integer getScoreMaxs(Rank rank) throws SQLException;
}
