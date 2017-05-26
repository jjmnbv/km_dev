package com.pltfm.remote.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.kmzyc.b2b.model.User;
import com.kmzyc.promotion.remote.service.CouponRemoteService;
import com.kmzyc.user.remote.service.TrationListRemoteService;
import com.kmzyc.zkconfig.ConfigurationUtil;
import com.pltfm.app.dao.AccountInfoDAO;
import com.pltfm.app.dao.ReserverInfoDAO;
import com.pltfm.app.dao.ReserverTransactionInfoDAO;
import com.pltfm.app.dataobject.BnesAcctTransListDO;
import com.pltfm.app.service.AccountInfoService;
import com.pltfm.app.service.BnesAcctTransListService;
import com.pltfm.app.service.BnesAcctTransactionService;
import com.pltfm.app.util.Constants;
import com.pltfm.app.util.DateTimeUtils;
import com.pltfm.app.vobject.AccountInfo;
import com.pltfm.app.vobject.AccountInfoExample;
import com.pltfm.app.vobject.BnesAcctTransactionQuery;
import com.pltfm.app.vobject.ReserverBill;
import com.pltfm.app.vobject.ReserverInfo;
import com.pltfm.app.vobject.ReserverTransactionInfo;

/**
 * 账户订单交易远程接口
 * 
 * @author libo
 * @since
 */
@SuppressWarnings("unchecked")
@Component(value = "trationListRemoteService")
public class TrationListRemoteServiceImpl implements TrationListRemoteService {
  @Resource(name = "accountInfoService")
  private AccountInfoService accountInfoService;
  @Resource(name = "accountInfoDAO")
  private AccountInfoDAO accountInfoDAO;
  @Resource(name = "bnesAcctTransListService")
  private BnesAcctTransListService bnesAcctTransListService;
  @Resource(name = "bnesAcctTransactionService")
  private BnesAcctTransactionService bnesAcctTransactionService;
  // 预备金额度变化dao类
  @Resource(name = "reserverTransactionInfoDAO")
  private ReserverTransactionInfoDAO reserverTransactionInfoDAO;
  // 预备金dao类
  @Resource(name = "reserverInfoDAO")
  private ReserverInfoDAO reserverInfoDAO;
  
  @Autowired
  private CouponRemoteService couponRemoteService;
  
  // 日志类
  private Logger logger = LoggerFactory.getLogger(TrationListRemoteServiceImpl.class);
  // 预备金余额变化记录实体
  private ReserverTransactionInfo reserverTransactionInfo;
  // 预备金实体
  private ReserverInfo reserverInfo;
  // 流水实体
  private BnesAcctTransactionQuery bnesAcctTransactionQuery;
  // 账单实体
  private ReserverBill reserverBill;
  // 充值额度
  private static final String RECHARGE_FIRST =
      ConfigurationUtil.getString("RECHARGE_FIRST");
  private static final String RECHARGE_TWO =
      ConfigurationUtil.getString("RECHARGE_TWO");
  // 充值赠送相关券
  private static final String COUPON_RULE_FIRST =
      ConfigurationUtil.getString("COUPON_RULE_FIRST");
  private static final String COUPON_RULE_TWO =
      ConfigurationUtil.getString("COUPON_RULE_TWO");
  private static final String COUPON_RULE_THREE =
      ConfigurationUtil.getString("COUPON_RULE_THREE");
  // 送券时间设置
  private static final String REWARD_STSRTTIME =
      ConfigurationUtil.getString("REWARD_STSRTTIME");
  private static final String REWARD_ENDTIME =
      ConfigurationUtil.getString("REWARD_ENDTIME");

  /**
   * 订单账户金额修改
   * 
   * @param String accountLogin,账户号
   * @param Double Amount,交易金额
   * @param String orderNum,订单号
   * @param String content,交易内容
   * @param Integer type 1---余额支付 0---取消订单 2----订单退款
   * @return 返回值 Integer 0--账户金额修改失败 1--账户金额修改成功 2--账户号不存在 3--账户金额小于订单交易金额 4--订单号不能为空 5--交易内容不能为空
   * 
   * @throws Exception 异常
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public Integer orderTrationAccout(String accountLogin, Double amount, String orderNum,
      String content, Integer type) throws Exception {
    Integer result = 0;
    // 订单号不能为空
    if (orderNum == null) {
      return 4;
    }
    if (accountLogin == null) {
      return 2;
    }
    AccountInfoExample example = new AccountInfoExample();
    example.createCriteria().andAccountLoginEqualTo(accountLogin);
    List<AccountInfo> listAcct = accountInfoDAO.selectByExample(example);
    if (listAcct.size() == 0) {
      logger.error("订单 "+orderNum+"余额修改失败！根据账户号"+accountLogin+"查询表account_info结果为空"); 
      result = 2;
    }
    for (AccountInfo info : listAcct) {
      BnesAcctTransactionQuery bnesAcctTransactionQuery = new BnesAcctTransactionQuery();
      switch (type) {
          case 0:
              bnesAcctTransactionQuery.setType(4);
              break;
          case 1:
              amount = -amount;
              bnesAcctTransactionQuery.setType(3);
              break;
          case 2:
              bnesAcctTransactionQuery.setType(5);
              break;
          default:
              bnesAcctTransactionQuery.setType(type);
              break;
      }

      bnesAcctTransactionQuery.setStatus(1);
      bnesAcctTransactionQuery.setCreatedId(info.getN_LoginId());
      bnesAcctTransactionQuery.setCreateDate(DateTimeUtils.getCalendarInstance().getTime());
      bnesAcctTransactionQuery.setModifieId(info.getN_LoginId());
      bnesAcctTransactionQuery.setModifyDate(DateTimeUtils.getCalendarInstance().getTime());
      bnesAcctTransactionQuery.setAccountId(info.getN_AccountId());
      String number = new SimpleDateFormat("yyyyMMddhhmmssSSS").format(new Date());
      bnesAcctTransactionQuery.setAccountNumber(number);
      int accountTransactionId = 0;
      bnesAcctTransactionQuery.setTrasObject(1);
      bnesAcctTransactionQuery.setAmount(new BigDecimal(amount));
      if (Constants.TRANSACTION_TYPE_RETURN.equals(type) || type == 2) {
        String contents = "退货单号：" + orderNum + "," + content;
        bnesAcctTransactionQuery.setOtherOrder(orderNum);
        bnesAcctTransactionQuery.setContent(contents);
      } else {
        String contents = "订单：" + orderNum + "," + content;
        bnesAcctTransactionQuery.setOtherOrder(orderNum);
        bnesAcctTransactionQuery.setContent(contents);
      }
      BigDecimal beforeAmount = BigDecimal.ZERO;
      if (info.getAmountAvlibal() != null) {
        beforeAmount = info.getAmountAvlibal();
      }
      BigDecimal afterAmount = beforeAmount.add(new BigDecimal(amount));
      Map<String, String> map = new HashMap<String, String>();
      map.put("loginId", info.getN_LoginId().toString());
      map.put("amount", amount.toString());
      map.put("beforeAmount", String.valueOf(beforeAmount));
      if (afterAmount.doubleValue() < 0) {
        return 3;
      } else if ((accountTransactionId =
          bnesAcctTransactionService.insertBnesAcctTransactionDO(bnesAcctTransactionQuery)) > 0
          && accountInfoDAO.updateAccountBalance(map) == 1) {
        // 保存交易记录
        BnesAcctTransListDO bnesAcctTransListDO = new BnesAcctTransListDO();
        bnesAcctTransListDO.setAccountId(info.getN_AccountId());
        bnesAcctTransListDO.setAccountTransactionId(accountTransactionId);
        bnesAcctTransListDO.setBeforeAmount(beforeAmount);
        bnesAcctTransListDO.setAfterAmount(afterAmount);
        bnesAcctTransListDO.setMoneyAmount(new BigDecimal(amount));
        bnesAcctTransListService.insertBnesAcctTransListDO(bnesAcctTransListDO);
        result = 1;
      } else {
        throw new Exception("订单 "+orderNum+"余额修改失败");
      }
      return result;
    }
    return result;
  }

  /**
   * 用户充值接口
   * 
   * @update lijainjun date 150324
   * @param bnesAcctTransactionQuery
   * @return Integer---- 0 失败 ----主键值 成功
   * @throws Exception
   * 
   * 
   */

  @Override
  @Transactional(rollbackFor = Exception.class)
  public Integer paymentAccount(BnesAcctTransactionQuery bnesAcctTransactionQuery)
      throws Exception {
    int result = 0;
    if (bnesAcctTransactionQuery.getAccountId() != null) {
      // 在线充值
      bnesAcctTransactionQuery.setType(Constants.TRANSACTION_TYPE_RECHARGE_ONLINE);
      bnesAcctTransactionQuery.setCreateDate(DateTimeUtils.getCalendarInstance().getTime());
      bnesAcctTransactionQuery.setModifyDate(DateTimeUtils.getCalendarInstance().getTime());
      String number = new SimpleDateFormat("yyyyMMddhhmmssSSS").format(new Date());
      bnesAcctTransactionQuery.setAccountNumber(number);
      int accountId = bnesAcctTransactionQuery.getAccountId();
      Integer accountTransactionId = 0;
      // 保存交易流水
      accountTransactionId =
          bnesAcctTransactionService.insertBnesAcctTransactionDO(bnesAcctTransactionQuery);
      // 查询账户信息金额
      AccountInfo accountInfo =
          accountInfoService.selectByPrimaryKey(bnesAcctTransactionQuery.getAccountId());
      BigDecimal beforeAmount = BigDecimal.ZERO;
      if (accountInfo.getAmountAvlibal() != null) {
        beforeAmount = accountInfo.getAmountAvlibal();
      } else {
        beforeAmount = BigDecimal.ZERO;
      }
      BigDecimal amout = bnesAcctTransactionQuery.getAmount();
      BigDecimal afterAmount = beforeAmount.add(amout);
      // 保存金额交易变化记录
      BnesAcctTransListDO bnesAcctTransListDO = new BnesAcctTransListDO();
      bnesAcctTransListDO.setAccountId(accountId);
      bnesAcctTransListDO.setAccountTransactionId(accountTransactionId);
      bnesAcctTransListDO.setBeforeAmount(beforeAmount);
      bnesAcctTransListDO.setAfterAmount(afterAmount);
      bnesAcctTransListDO.setMoneyAmount(amout);
      bnesAcctTransListService.insertBnesAcctTransListDO(bnesAcctTransListDO);
      result = accountTransactionId;
    }
    return result;
  }

  /**
   * 用户充值银行回调修改余额接口
   * 
   * @update lijianjun date 150324
   * @param bnesAcctTransactionQuery
   * @return Integer---- 0 失败 ----1成功
   * @throws Exception
   * 
   * 
   */
  @Override
public Integer updatePaymentAccountList(BnesAcctTransactionQuery bnesAcctTransactionQuery)
      throws Exception {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 小写的mm表示的是分钟
    int result = 0;
    if (bnesAcctTransactionQuery.getAccountTransactionId() != null) {
      // 在线充值
      bnesAcctTransactionQuery.setType(Constants.TRANSACTION_TYPE_RECHARGE_ONLINE);
      Integer accountTransactionId = 0;
      // 保存
      accountTransactionId = bnesAcctTransactionQuery.getAccountTransactionId();
      // 查询账户更改金额
      BnesAcctTransactionQuery bnesAcctTransactionQuery1 =
          bnesAcctTransactionService.findById(accountTransactionId);
      BigDecimal amount = bnesAcctTransactionQuery1.getAmount();
      // 查询账户信息金额
      AccountInfo accountInfo =
          accountInfoService.selectByPrimaryKey(bnesAcctTransactionQuery1.getAccountId());
      BigDecimal beforeAmount =BigDecimal.ZERO;
      if (accountInfo.getAmountAvlibal() != null) {
        beforeAmount = accountInfo.getAmountAvlibal();
      } else {
        beforeAmount = BigDecimal.ZERO;
      }
      BigDecimal afterAmount = beforeAmount.add(amount);
      accountInfo.setAmountAvlibal(afterAmount);
      // 更新账户金额
      int num = accountInfoService.updateAccountInfo(accountInfo);
      // 更新交易流水
      bnesAcctTransactionQuery.setAmount(amount);
      result = bnesAcctTransactionService.updateBnesAcctTransactionDO(bnesAcctTransactionQuery);
      Date date = new Date();
      // 充值成功进行其他业务处理
      if (num == 1
          && ((date.after(sdf.parse(REWARD_STSRTTIME)) || date.equals(sdf.parse(REWARD_STSRTTIME)))
              && date.before(sdf.parse(REWARD_ENDTIME)))) {
        User user = new User();
        user.setLoginId(accountInfo.getN_LoginId().longValue());
        if (amount.doubleValue() >= Double.valueOf(RECHARGE_FIRST) && amount.doubleValue() < Double.valueOf(RECHARGE_TWO)) {
          Boolean bool = couponRemoteService.rechargeGrantCoupon(user,
              Long.valueOf(COUPON_RULE_FIRST), null, 1);
          if (bool) {
            logger.info(user.getLoginId() + ":优惠券发放成功");
          } else {
            logger.error(user.getLoginId() + ":优惠券发放失败");
          }
        } else if (amount.doubleValue() >= Double.valueOf(RECHARGE_TWO)) {
          int b = (int) (amount.doubleValue() / Double.valueOf(RECHARGE_TWO));
          for (int i = 0; i < b; i++) {
            // 50元优惠券2张
            Boolean bo = couponRemoteService.rechargeGrantCoupon(user,
                Long.valueOf(COUPON_RULE_TWO), null, 2);
            if (bo) {
              logger.info(user.getLoginId() + ":优惠券发放成功");
            } else {
              logger.error(user.getLoginId() + ":优惠券发放失败");
            }
            // 100元优惠券1张
            Boolean boo = couponRemoteService.rechargeGrantCoupon(user,
                Long.valueOf(COUPON_RULE_THREE), null, 1);
            if (boo) {
              logger.info(user.getLoginId() + ":优惠券发放成功");
            } else {
              logger.error(user.getLoginId() + ":优惠券发放失败");
            }
          }
        }
      }
    }
    return result;
  }


  // 预备金还款 插入交易流水，插入预备金余额变化记录 type=1代表预备金
  @Override
public Integer paySubmit(BnesAcctTransactionQuery bnesAcctTransactionQuery, Integer type)
      throws Exception {
    int result = 0;
    int accountTransactionpId = 0;
    if (bnesAcctTransactionQuery == null) {
      logger.error("传入参数为空");
      throw new Exception("传入参数为空");
    }
    // 预备金相关操作
    if (Constants.USERTYPERESERVER.equals(type)) {
      // 设置流水对象
      // 预备金
      bnesAcctTransactionQuery.setTrasObject(Constants.TRANSACTIONOBJECTRESERVER);
      // 创建时间
      bnesAcctTransactionQuery.setCreateDate(DateTimeUtils.getCalendarInstance().getTime());
      // 流水号
      bnesAcctTransactionQuery
          .setAccountNumber(new SimpleDateFormat("yyyyMMddhhmmssSSS").format(new Date()));
      // 默认未付款
      bnesAcctTransactionQuery.setStatus(Constants.TRANSACTIONSTATUSNO);
      // 插入流水
      accountTransactionpId =
          bnesAcctTransactionService.insertBnesAcctTransactionDO(bnesAcctTransactionQuery);
      result = accountTransactionpId;

      // 根据账户id获取预备金对象
      reserverInfo = new ReserverInfo();
      reserverInfo.setAccountId(new BigDecimal(bnesAcctTransactionQuery.getAccountId()));
      reserverInfo = reserverInfoDAO.selectByPrimaryKey(reserverInfo);
      // 设置余额变化记录对象
      if (reserverTransactionInfo == null) {
        reserverTransactionInfo = new ReserverTransactionInfo();
      }
      // 流水id
      reserverTransactionInfo.setAccountTransactionpId(new BigDecimal(accountTransactionpId));
      // 预备金账户id
      reserverTransactionInfo.setReserveId(reserverInfo.getReserveId());
      // 交易前金额
      reserverTransactionInfo.setBeforeAmount(reserverInfo.getRemainLimit());
      // 交易后金额
      BigDecimal changeAmount = reserverInfo.getRemainLimit()
          .subtract(bnesAcctTransactionQuery.getAmount());
      reserverTransactionInfo.setAfterAmount(changeAmount);
      // 交易金额(正数支出，负数收入)
      reserverTransactionInfo.setPayAmount(bnesAcctTransactionQuery.getAmount());
      // 交易日期
      reserverTransactionInfo.setPayDate(DateTimeUtils.getCalendarInstance().getTime());
      // 交易人
      reserverTransactionInfo.setCreateId(new BigDecimal(bnesAcctTransactionQuery.getAccountId()));
      // 插入余额变化
      reserverTransactionInfoDAO.insert(reserverTransactionInfo);
    } else {
      paymentAccount(bnesAcctTransactionQuery);
    }
    return result;
  }

  // 修改交易流水状态
  @Override
public Integer updateTransaction(String accountNumber, Integer status) throws Exception {
    bnesAcctTransactionQuery = new BnesAcctTransactionQuery();
    bnesAcctTransactionQuery.setAccountNumber(accountNumber);
    bnesAcctTransactionQuery.setStatus(status);
    Integer count =
        bnesAcctTransactionService.updateBnesAcctTransactionDO(bnesAcctTransactionQuery);
    return count;
  }

  // 预备金还款到账 跟新预备金账户，账单，流水状态
  @Override
public Integer paySubmitRemitted(BnesAcctTransactionQuery bnesAcctTransactionQuery, Integer type)
      throws Exception {
    int result = 0;
    // 预备金相关操作
    if (Constants.USERTYPERESERVER.equals(type)) {
      Integer accountTransactionId = bnesAcctTransactionQuery.getAccountTransactionId();
      // 获取流水交易信息
      BnesAcctTransactionQuery bnesAcctTransactionQuery1 =
          bnesAcctTransactionService.findById(accountTransactionId);
      BigDecimal amount = bnesAcctTransactionQuery1.getAmount();
      // 获取预备金账号信息
      reserverInfo = new ReserverInfo();
      reserverInfo.setAccountId(new BigDecimal(bnesAcctTransactionQuery.getAccountId()));
      reserverInfo = reserverInfoDAO.selectByPrimaryKey(reserverInfo);
      // 设置可用额度 (正数支出 负数收入)
      BigDecimal change = reserverInfo.getRemainLimit().add(amount);
      reserverInfo.setRemainLimit(change);
      // 更新预备金账户
      reserverInfoDAO.updateByPrimaryKey(reserverInfo);
      // 更新流水为成功
      bnesAcctTransactionQuery.setStatus(Constants.TRANSACTIONSTATUSSUCCESS);
      // 预备金对象
      bnesAcctTransactionQuery.setTrasObject(Constants.TRANSACTIONOBJECTRESERVER);
      bnesAcctTransactionService.updateBnesAcctTransactionDO(bnesAcctTransactionQuery);
    } else {
      updatePaymentAccountList(bnesAcctTransactionQuery);
    }
    return result;
  }

  /**
   * 预备金账户金额修改
   * 
   * @param String accountLogin,账户号
   * @param Double Amount,交易金额
   * @param String orderNum,订单号
   * @param String content,交易内容
   * @param Integer type 11---预备金支付订单;13---预备金订单退款;14--预备金取消订单;15--预备金调整
   * @param integer userType 1 预备金 ，其他 ，之前操作
   * @return 返回值 Integer 0--账户金额修改失败 1--账户金额修改成功 2--账户号不存在 3--账户金额小于订单交易金额 4--订单号不能为空 5--交易内容不能为空
   * 
   * @throws Exception 异常
   */
  @Override
public Integer updateReserver(String accountLogin, Double amount, String orderNum, String content,
      Integer type, Integer usertype) throws Exception {
    // 预备金账户更改
    if (Constants.USERTYPERESERVER.equals(usertype)) {
      Integer result = 0;
      // 订单号不能为空
      if (orderNum == null) {
        return 4;
      }
      // 用户名不能为空
      if (accountLogin == null) {
        return 2;
      }
      // 根据用户名获取预备金账户
      List<ReserverInfo> listAcct =
          reserverInfoDAO.selectReserverInfoByAccountLogin(accountLogin);
      if (listAcct.size() == 0) {
        result = 2;
      }
      for (ReserverInfo info : listAcct) {
        // 添加流水
        BnesAcctTransactionQuery bnesAcctTransactionQuery = new BnesAcctTransactionQuery();
        bnesAcctTransactionQuery.setType(type);
        bnesAcctTransactionQuery.setStatus(Constants.TRANSACTIONSTATUSSUCCESS);
        bnesAcctTransactionQuery.setCreatedId(info.getLoginId().intValue());
        bnesAcctTransactionQuery.setCreateDate(DateTimeUtils.getCalendarInstance().getTime());
        bnesAcctTransactionQuery.setModifieId(info.getLoginId().intValue());
        bnesAcctTransactionQuery.setModifyDate(DateTimeUtils.getCalendarInstance().getTime());
        bnesAcctTransactionQuery.setAccountId(info.getAccountId().intValue());
        String number = new SimpleDateFormat("yyyyMMddhhmmssSSS").format(new Date());
        bnesAcctTransactionQuery.setAccountNumber(number);
        int accountTransactionId = 0;
        // 设置交易对象 预备金4 支付退款取消订单
        String contents = "";
        if (Constants.TRANSACTION_TYPE_RESERVER_PAYBILL.equals(type)
            || Constants.TRANSACTION_TYPE_RESERVER_CANCELBILL.equals(type)) {
          contents = "订单：" + orderNum + "," + content;
          bnesAcctTransactionQuery.setOtherOrder(orderNum);
        } else if (Constants.TRANSACTION_TYPE_RESERVER_RESERVERCHANGE.equals(type)) {// 预备金调整
          contents = content;
        } else if (Constants.TRANSACTION_TYPE_RESERVER_RETURN.equals(type)) {
          contents = "退换货单：" + orderNum + "," + content;
          bnesAcctTransactionQuery.setOtherOrder(orderNum);
        }
        bnesAcctTransactionQuery.setTrasObject(Constants.TRANSACTIONOBJECTRESERVER);
        bnesAcctTransactionQuery.setContent(contents);
        BigDecimal beforeAmount = BigDecimal.ZERO;
        if (info.getRemainLimit() != null) {
          beforeAmount = info.getRemainLimit();
        } else {
          beforeAmount = BigDecimal.ZERO;
        }
        // 处理后的预备金可用额度
        BigDecimal afterAmount = BigDecimal.ZERO;
        if (type == 11) {
          afterAmount = beforeAmount.subtract(new BigDecimal(amount));
          bnesAcctTransactionQuery.setAmount(new BigDecimal(-amount));
        } else if (Constants.TRANSACTION_TYPE_RESERVER_RETURN.equals(type)
            || Constants.TRANSACTION_TYPE_RESERVER_CANCELBILL.equals(type)
            || Constants.TRANSACTION_TYPE_RESERVER_RESERVERCHANGE.equals(type)) {
          bnesAcctTransactionQuery.setAmount(new BigDecimal(amount));
          afterAmount = beforeAmount.add(new BigDecimal(amount));
        }
        if (afterAmount.doubleValue() < 0) {// 额度不足
          return 3;
        } else {// 添加流水
          accountTransactionId =
              bnesAcctTransactionService.insertBnesAcctTransactionDO(bnesAcctTransactionQuery);
          info.setRemainLimit(afterAmount);
          // 更新预备金账户
          reserverInfoDAO.updateByPrimaryKey(info);
          // 保存交易记录
          ReserverTransactionInfo reserverTransactionInfo = new ReserverTransactionInfo();
          reserverTransactionInfo.setAccountTransactionpId(new BigDecimal(accountTransactionId));
          reserverTransactionInfo.setAfterAmount(afterAmount);
          reserverTransactionInfo.setBeforeAmount(beforeAmount);
          reserverTransactionInfo.setCreateId(info.getLoginId());
          reserverTransactionInfo.setPayAmount(new BigDecimal(amount));
          reserverTransactionInfo.setPayDate(DateTimeUtils.getCalendarInstance().getTime());
          reserverTransactionInfo.setReserveId(info.getReserveId());
          reserverTransactionInfoDAO.insert(reserverTransactionInfo);
          result = 1;
        }
        return result;
      }
      return result;
    } else {
      orderTrationAccout(accountLogin, amount, orderNum, content, type);
    }
    return null;
  }


  public ReserverBill getReserverBill() {
    return reserverBill;
  }


  public void setReserverBill(ReserverBill reserverBill) {
    this.reserverBill = reserverBill;
  }


  public BnesAcctTransactionQuery getBnesAcctTransactionQuery() {
    return bnesAcctTransactionQuery;
  }


  public void setBnesAcctTransactionQuery(BnesAcctTransactionQuery bnesAcctTransactionQuery) {
    this.bnesAcctTransactionQuery = bnesAcctTransactionQuery;
  }


  public ReserverInfo getReserverInfo() {
    return reserverInfo;
  }


  public void setReserverInfo(ReserverInfo reserverInfo) {
    this.reserverInfo = reserverInfo;
  }


  public ReserverTransactionInfo getReserverTransactionInfo() {
    return reserverTransactionInfo;
  }


  public void setReserverTransactionInfo(ReserverTransactionInfo reserverTransactionInfo) {
    this.reserverTransactionInfo = reserverTransactionInfo;
  }


  public BnesAcctTransListService getBnesAcctTransListService() {
    return bnesAcctTransListService;
  }

  public void setBnesAcctTransListService(BnesAcctTransListService bnesAcctTransListService) {
    this.bnesAcctTransListService = bnesAcctTransListService;
  }

  public AccountInfoDAO getAccountInfoDAO() {
    return accountInfoDAO;
  }

  public void setAccountInfoDAO(AccountInfoDAO accountInfoDAO) {
    this.accountInfoDAO = accountInfoDAO;
  }

  public AccountInfoService getAccountInfoService() {
    return accountInfoService;
  }


  public void setAccountInfoService(AccountInfoService accountInfoService) {
    this.accountInfoService = accountInfoService;
  }


}
