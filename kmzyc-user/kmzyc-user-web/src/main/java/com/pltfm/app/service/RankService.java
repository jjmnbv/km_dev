package com.pltfm.app.service;

import java.sql.SQLException;
import java.util.List;

import com.kmzyc.commons.page.Page;
import com.pltfm.app.dataobject.RankDO;
import com.pltfm.app.vobject.Rank;

/**
 * 客户头衔信息处理
 * 
 * @author gwl
 * @since 2013-07-08
 */
public interface RankService {
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
  public Integer deleteRank(List<String> nRankIds) throws SQLException;

  /**
   * 分页查询客户头衔信息
   * 
   * @param 客户头衔信息实体
   * @return
   * @throws Exception 异常
   */
  public Page searchPageByVo(Page pageParam, Rank vo) throws Exception;

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
  public Rank getRankInfoId(Integer nRankId) throws SQLException;

  /**
   * 按类型条件查询息总数量
   * 
   * @param vo
   * @return 返回值
   */
  public Integer selectCountRank(Integer customerTypeId) throws SQLException;

  /**
   * 查询是否已存在头衔编号
   * 
   * @param vo
   * @return 返回值
   */
  public List selectCode(String Code) throws SQLException;

  /**
   * 查询是否已存在头衔名称
   * 
   * @param vo
   * @return 返回值
   */
  public List selectRankName(String rankName) throws SQLException;

  /**
   * 按类型查询最新记录
   * 
   * @param vo
   * @return 返回值
   */
  public Rank maxRankiId(Integer customerTypeId) throws SQLException;

  /**
   * 跟据客户类型客户最大经验值 和来查询是否有下个等级头衔
   * 
   **/
  public Rank selectRank(Integer scoreMax, Integer customerTypeId) throws SQLException;

  /**
   * 通过个性id查询客户头衔并更新客户信息中头衔字段
   * 
   * @param userLevelDO
   * @return 受影响行数
   * @throws Exception
   */
  public Integer updateRankName(RankDO rank) throws SQLException;

  /**
   * 跟据客户类型来查询客户头衔信息
   * 
   **/
  public List getCustomerTypeIdKey(Integer customerTypeId) throws SQLException;

  /**
   * 跟据客户类型来查询客户头最大经验值
   * 
   **/
  public int getScoreMaxs(Integer customerTypeId) throws SQLException;
}
