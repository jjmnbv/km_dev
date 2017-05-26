package com.kmzyc.user.remote.service;

import com.pltfm.app.vobject.BnesAcctTransactionQuery;


/**
 * 账户订单交易远程接口
 * 
 * @author libo
 * @since
 */
public interface TrationListRemoteService {
  // Integer

  /**
   * 订单账户金额修改
   * 
   * @param String accountLogin,账户号
   * @param Double Amount,交易金额
   * @param String orderNum,订单号
   * @param String content,交易内容
   * @param Integer type 1---订单扣减金额 0---订单退回金额
   * @return 返回值 Integer 0--账户金额修改失败 1--账户金额修改成功 2--账户号不存在 3--账户金额小于订单交易金额 4--订单号不能为空 5--交易内容不能为空
   * 
   * @throws Exception 异常
   */

  Integer orderTrationAccout(String accountLogin, Double Amount, String orderNum, String content,
      Integer type) throws Exception;

  /**
   * 
   * @param bnesAcctTransactionQuery
   * @return Integer---- 0 失败 ----主键值 成功
   * @throws Exception
   * 
   * 
   */
  Integer paymentAccount(BnesAcctTransactionQuery bnesAcctTransactionQuery) throws Exception;

  /**
   * 
   * @param bnesAcctTransactionQuery
   * @return Integer---- 0 失败 ----1成功
   * @throws Exception
   * 
   * 
   */
  Integer updatePaymentAccountList(BnesAcctTransactionQuery bnesAcctTransactionQuery)
      throws Exception;

  /**
   * 预备金还款
   * 
   * @param bnesAcctTransactionQuery
   * @return
   * @throws Exception
   */
  Integer paySubmit(BnesAcctTransactionQuery bnesAcctTransactionQuery, Integer type)
      throws Exception;

  /**
   * 修改流水状态
   * 
   * @param accountTransactionId
   * @param status
   * @return
   * @throws Exception
   */
  Integer updateTransaction(String accountNumber, Integer status) throws Exception;

  /**
   * 预备金还款到账
   * 
   * @param bnesAcctTransactionQuery
   * @return
   * @throws Exception
   */
  Integer paySubmitRemitted(BnesAcctTransactionQuery bnesAcctTransactionQuery, Integer type)
      throws Exception;

  /**
   * 预备金 订单支付取消退款 异常调整
   * 
   * @param accountLogin
   * @param Amount
   * @param orderNum
   * @param content
   * @param type
   * @param usertype
   * @return
   * @throws Exception
   */
  Integer updateReserver(String accountLogin, Double Amount, String orderNum, String content,
      Integer type, Integer usertype) throws Exception;
}
