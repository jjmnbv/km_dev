package com.pltfm.app.local;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface ICustomer {
  /**
   * 订单账户金额修改
   * 
   * @param String accountLogin,账户号
   * @param Double Amount,交易金额
   * @param String orderNum,订单号
   * @param String content,交易内容
   * @param Integer type 1---订单扣减金额 2---订单退回金额
   * @return 返回值 Integer 0--账户金额修改失败 1--账户金额修改成功 2--账户号不存在 3--账户金额小于订单交易金额 4--订单号不能为空 5--交易内容不能为空
   */
  public Integer orderTrationAccout(String account, Double orderMoney, String orderNum,
      String content, Integer type);

  /**
   * 优惠券支付
   * 
   * @return
   */
  public Integer payByPreferential(String account, List<Map<Long, Double>> list, Integer type);

  /**
   * 通过客户账户获取客户基本数据信息
   * 
   * @param accountInfo
   * @param type
   * @return
   * @throws Exception
   */
  // public AccountInfo selectByAccountInfo(AccountInfo accountInfo,Integer
  // type) throws Exception;

  /**
   * 更新客户积分远程接口
   * 
   * @param ruleCode 规则编号 rule002 个人购买商品 rule001 个人注册会员
   * @param scoreType 积分类型 2积分消费 1 积分积累（所有退款、撤销等按照积分消费来处理）
   * @param loginAccount 登录账号
   * @param paramsMap 积分规则表达式参数对应值集合
   * @return 0 代表出现异常失败 1代表执行成功
   * @throws Exception 异常信息
   */
  public Integer updateUserScoreInfo(String ruleCode, Integer scoreType, String loginAccount,
      Map<String, String> paramsMap) throws Exception;

  /**
   * 通过客户交易金额更新客户消费总额以及客户等级信息
   * 
   * @param feeNum 交易金额
   * @param loginAccount 客户主键
   * @param transactionType 交易类型 0 代表退款金额 1代表消费金额
   * @return 0 代表执行失败 1代表执行成功
   * @throws Exception
   */
  public Integer byFeeUpdateUserLevel(Double feeNum, String loginAccount, Integer transactionType)
      throws Exception;

  /**
   * 获取订单总优惠额
   * 
   * @return
   */
  public BigDecimal getDiscountAmount();
}
