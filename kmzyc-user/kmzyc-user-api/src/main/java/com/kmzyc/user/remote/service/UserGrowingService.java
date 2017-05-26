package com.kmzyc.user.remote.service;

import com.pltfm.app.vobject.MySorce;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Map;

/**
 * 客户成长远程接口
 * 
 * @author zhl
 * @since 2013-08-06
 */

@SuppressWarnings("unchecked")
public interface UserGrowingService extends Serializable {
  /**
   * 更新客户积分远程接口
   * 
   * @param ruleCode 规则编号 rule002 个人购买商品 rule001 个人注册会员
   * @param scoreType 积分类型 2积分减少 1 积分增加（所有退款、撤销等按照积分消费来处理）
   * @param loginAccount 登录账号
   * @param paramsMap 积分规则表达式参数对应值集合
   * @return 0 代表出现异常失败 1代表执行成功
   * @throws Exception 异常信息
   */
  /*废除这个方法public Integer updateUserScoreInfo(String ruleCode, Integer scoreType, String loginAccount,
      Map<String, String> paramsMap) throws Exception;*/

  /**
   * 根据规则查询积分值
   * 
   * @param ruleCode 规则编号 rule002 个人购买商品 rule001 个人注册会员
   * @param paramsMap 积分规则表达式参数对应值集合
   * @return 0 代表出现异常失败 1代表执行成功
   * @throws Exception 异常信息
   */
  public Integer getScoreNumber(String ruleCode, Map<String, String> paramsMap) throws Exception;

  /**
   * 通过客户交易金额更新客户消费总额以及客户等级信息
   * 
   * @param feeNum 交易金额
   * @param loginAccount 客户主键
   * @param transactionType 交易类型 0 代表退款金额 1代表消费金额
   * @return 0 代表执行失败 1代表执行成功
   * @throws Exception
   */
 /*删除等级 @Deprecated
  public Integer byFeeUpdateUserLevel(Double feeNum, String loginAccount, Integer transactionType)
      throws Exception;
*/
  /**
   * 通过查询条件获取客户等级总数
   * 
   * @param userLevel 客户等级实体
   * @return 返回总条数
   * @throws Exception 异常
   */
 /*删除等级 public Integer countByLevel(UserLevel userLevel) throws Exception;*/

  /**
   * 通过查询条件获取客户等级信息
   * 
   * @param userLevel 客户等级实体
   * @return 返回客户等级集合
   * @throws Exception 异常
   */
/*删除等级  public List queryForUserLevel(UserLevel userLevel) throws Exception;*/

  /**
   * 通过主键获取客户等级信息
   * 
   * @param userLevelId 客户等级主键
   * @return
   * @throws SQLException 异常
   */
 /*删除等级 public UserLevel selectByPrimaryKey(Integer userLevelId) throws SQLException;*/

  /**
   * 通过登录主键获取我的积分信息
   * 
   * @return
   * @throws SQLException 异常
   */
  public MySorce getMysorc(Integer n_LoginId) throws Exception;

  /**
   * 积分兑换优惠劵信息
   * 
   * @param 客户等级主键
   * @return
   * @throws SQLException 异常
   */

 /*删除等级 public int changeSorcToCoupon(ScoreInfo scoreInfo) throws Exception;*/

  /**
   * 更新用户积分
   * 
   * @param ruleCode
   * @param loginId
   * @param amount
   * @return
   * @throws Exception
   */
  public boolean updateScoreInfo(String ruleCode, Long loginId, BigDecimal amount) throws Exception;

  /**
   * 更新用户积分,新增参与活动,更新参与活动次数的缓存
   * 
   * @param ruleCode
   * @param loginId
   * @param amount
   * @param ep
   * @return
   * @throws Exception
   */
  /*删除参与活动积分public boolean updateScoreInfoAndCreateEP(String ruleCode, Long loginId, BigDecimal amount,
      ExtractPrize ep) throws Exception;*/
  
  
  /**
   * 更新时代会员pv积分 ,增加积分明细
     * scoreType 积分明细类型 0为减少积分  1为增加积分，
     * loginAccount 时代账号
     * pv  pv积分
   * @return
   * @throws Exception
   */
  public Integer updateScoreInfoForTimeMember(Integer scoreType, String loginAccount,BigDecimal pv)throws Exception;
}
