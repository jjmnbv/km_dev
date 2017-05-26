package com.pltfm.remote.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.km.crowdsourcing.model.InstitutionInfo;
import com.kmzyc.user.remote.service.BnesAcctEnchashmentRemoteService;
import com.pltfm.app.dao.BnesAcctEnchashmentDAO;
import com.pltfm.app.dao.BnesAcctTransListDAO;
import com.pltfm.app.dao.CommercialTenantBasicInfoDAO;
import com.pltfm.app.dao.SpreaderInfoDAO;
import com.pltfm.app.dataobject.BnesAcctTransListDO;
import com.pltfm.app.service.AccountInfoService;
import com.pltfm.app.service.BnesAcctEnchashmentService;
import com.pltfm.app.service.BnesAcctTransactionService;
import com.pltfm.app.util.Constants;
import com.pltfm.app.util.DateTimeUtils;
import com.pltfm.app.util.EnchashmentResourceEnumType;
import com.pltfm.app.vobject.AccountInfo;
import com.pltfm.app.vobject.BnesAcctEnchashment;
import com.pltfm.app.vobject.BnesAcctTransactionQuery;
import com.pltfm.app.vobject.CommercialTenantBasicInfo;
import com.pltfm.app.vobject.SpreaderInfo;
import com.pltfm.crowdsourcing.dao.InstitutionInfoDao;

@Service(value = "bnesAcctEnchashmentRemoteService")
public class BnesAcctEnchashmentRemoteServiceImpl implements BnesAcctEnchashmentRemoteService {

  // 日志类
  private Logger logger = LoggerFactory.getLogger(BnesAcctEnchashmentRemoteServiceImpl.class);

  @Resource(name = "bnesAcctTransactionService")
  private BnesAcctTransactionService bnesAcctTransactionService;

  @Resource(name = "bnesAcctEnchashmentService")
  private BnesAcctEnchashmentService bnesAcctEnchashmentService;

  @Resource(name = "bnesAcctEnchashmentDAO")
  private BnesAcctEnchashmentDAO bnesAcctEnchashmentDAO;

  @Resource(name = "accountInfoService")
  private AccountInfoService accountInfoService;

  /** 余额变化记录 */
  @Resource(name = "bnesAcctTransListDAO")
  private BnesAcctTransListDAO bnesAcctTransListDAO;

  /** 微商推广信息DAO */
  @Resource(name = "spreaderInfoDAO")
  private SpreaderInfoDAO spreaderInfoDAO;

  /** 微商推广信息DAO */
  @Resource(name = "commercialTenantBasicInfoDAO")
  private CommercialTenantBasicInfoDAO commercialTenantBasicInfoDAO;

  /** 机构信息DAO */
  @Resource(name = "institutionInfoDao")
  private InstitutionInfoDao institutionInfoDao;

  /**
   * 添加供应商余额取现申请记录 -1 重复 0异常 成功返回ID
   */
  @Transactional(rollbackFor = Exception.class)
  public BigDecimal insertBnesAcctEnchashment(BnesAcctEnchashment bnesAcctEnchashment)
      throws Exception {
    BigDecimal bnesAcctEnchashmentId = null;

    // 获取当前账户信息
    AccountInfo accountInfo =
        accountInfoService.selectByPrimaryKey(bnesAcctEnchashment.getAccountId().intValue());

    // 如果已存在未审核完的余额提现记录则不做处理直接返回-1
    if (accountInfo.getAmountAvlibal().compareTo(bnesAcctEnchashment.getEnchashmentAmount())==-1) {
      return BigDecimal.ZERO;
    } else if (bnesAcctEnchashment.getEnchashmentAmount()
        .compareTo(BigDecimal.TEN.multiply(BigDecimal.TEN)) < 0) {
      return BigDecimal.ZERO;
    } else if (bnesAcctEnchashmentService.checkIsHaveUnfinashedEnchashment(bnesAcctEnchashment)) {
      return BigDecimal.ONE.negate();
    }
    bnesAcctEnchashment.setEnchashmentAmount(
        bnesAcctEnchashment.getEnchashmentAmount().setScale(2, BigDecimal.ROUND_DOWN));
    BigDecimal beforeAmount = BigDecimal.ZERO;
    if (accountInfo.getAmountAvlibal() != null) {
      beforeAmount = accountInfo.getAmountAvlibal();
    } else {
      beforeAmount = BigDecimal.ZERO;
    }
    // 冻结申请金额
    BigDecimal afterFroen = accountInfo.getAmountFrozen()
        .add(bnesAcctEnchashment.getEnchashmentAmount());
    BigDecimal after = accountInfo.getAmountAvlibal()
        .subtract(bnesAcctEnchashment.getEnchashmentAmount());
    accountInfo.setAmountAvlibal(after);
    accountInfo.setAmountFrozen(afterFroen);
    accountInfoService.updateAccountInfo(accountInfo);
    // 添加冻结流水
    BnesAcctTransactionQuery bnesAcctTransactionQuery = new BnesAcctTransactionQuery();
    bnesAcctTransactionQuery.setAccountId(accountInfo.getN_AccountId());
    bnesAcctTransactionQuery.setType(Constants.TRANSACTION_TYPE_FREEZING);
    bnesAcctTransactionQuery.setContent(
        accountInfo.getAccountLogin() + "余额冻结:￥" + bnesAcctEnchashment.getEnchashmentAmount());
    bnesAcctTransactionQuery
        .setAmount(bnesAcctEnchashment.getEnchashmentAmount().negate());
    bnesAcctTransactionQuery.setStatus(Constants.TRANSACTIONSTATUSSUCCESS);
    String number = new SimpleDateFormat("yyyyMMddhhmmssSSS").format(new Date());
    bnesAcctTransactionQuery.setAccountNumber(number);
    // 添加创建时间
    bnesAcctTransactionQuery.setCreateDate(DateTimeUtils.getCalendarInstance().getTime());
    bnesAcctTransactionQuery.setCreatedId(accountInfo.getN_LoginId());
    Integer accountTransactionId =
        bnesAcctTransactionService.insertBnesAcctTransactionDO(bnesAcctTransactionQuery);
    // 添加余额交易变化记录
    BnesAcctTransListDO bnesAcctTransListDO = new BnesAcctTransListDO();
    bnesAcctTransListDO.setAccountId(Integer.valueOf(accountInfo.getN_AccountId()));
    bnesAcctTransListDO.setAccountTransactionId(accountTransactionId);
    bnesAcctTransListDO.setBeforeAmount(beforeAmount);
    bnesAcctTransListDO.setAfterAmount(after);
    bnesAcctTransListDO.setMoneyAmount(bnesAcctTransactionQuery.getAmount());
    bnesAcctTransListDO.setCreateDate(DateTimeUtils.getCalendarInstance().getTime());
    bnesAcctTransListDO.setCreatedId(bnesAcctTransactionQuery.getLoginId());
    bnesAcctTransListDO.setModifieId(bnesAcctTransactionQuery.getLoginId());
    bnesAcctTransListDO.setModifyDate(DateTimeUtils.getCalendarInstance().getTime());
    bnesAcctTransListDAO.insertBnesAcctTransListDO(bnesAcctTransListDO);
    // 添加申请记录
    bnesAcctEnchashment.setAccountTransactionId(bnesAcctTransactionQuery.getAccountNumber());
    bnesAcctEnchashment.setCreateDate(DateTimeUtils.getCalendarInstance().getTime());
    bnesAcctEnchashment.setEnchashmentStatus((short) Constants.ENCHASHMENT_STATUS_PENDING);

    if (bnesAcctEnchashment.getEnchashmentResource() != null) {
      if (EnchashmentResourceEnumType.ENCHASHMENT_RESOURCE_GYS.getType()
          .equals(bnesAcctEnchashment.getEnchashmentResource().toPlainString())) {
        // 供应商,根据N_LoginId 获取供应商基本信息
        CommercialTenantBasicInfo commerInfo =
            commercialTenantBasicInfoDAO.selectByPrimaryLoginInfo(accountInfo.getN_LoginId());
        if (commerInfo != null) {
          // 提现人说明(公司名称)
          bnesAcctEnchashment.setEnchashmentDepict(commerInfo.getCorporateName());
          // 银行账户名
          bnesAcctEnchashment.setBankAccountName(commerInfo.getBankAccountName());
          // 银行账号（公司对公账户）
          bnesAcctEnchashment.setBankCardNumber(commerInfo.getCompanyAccount());
          // 开户行
          bnesAcctEnchashment.setBankOfDeposit(commerInfo.getBankOfDeposit());
          // 开户银行支行名称
          bnesAcctEnchashment.setBankBranchName(commerInfo.getBankOfDepositBranchName());
        }
      } /*else if (EnchashmentResourceEnumType.ENCHASHMENT_RESOURCE_VS.getType()
          .equals(bnesAcctEnchashment.getEnchashmentResource().toPlainString())) {
        // 微商，根据N_LoginId获取微商推广基本信息
        SpreaderInfo spreaderInfo = new SpreaderInfo();
        spreaderInfo.setLoginId(new BigDecimal(accountInfo.getN_LoginId()));
        spreaderInfo = spreaderInfoDAO.selectByPrimaryKey(spreaderInfo);
        if (spreaderInfo != null) {
          // 提现人说明(微商号)
          if (spreaderInfo.getVsNumber() != null) {
            bnesAcctEnchashment.setEnchashmentDepict(spreaderInfo.getVsNumber().toPlainString());
          }
          // 银行账户名
          bnesAcctEnchashment.setBankAccountName(spreaderInfo.getBankUname());
          // 银行账号
          bnesAcctEnchashment.setBankCardNumber(spreaderInfo.getBankAccount());
          // 开户行
          bnesAcctEnchashment.setBankOfDeposit(spreaderInfo.getBankName());
          // 开户银行支行名称(分支行)
          bnesAcctEnchashment.setBankBranchName(spreaderInfo.getBranchName());
        }
      } else if (EnchashmentResourceEnumType.ENCHASHMENT_RESOURCE_JG.getType()
          .equals(bnesAcctEnchashment.getEnchashmentResource().toPlainString())) {
        // 机构，根据N_LoginId获取机构基本信息
        InstitutionInfo institutionInfo = new InstitutionInfo();
        institutionInfo.setLoginId(Long.valueOf(accountInfo.getN_LoginId()));
        institutionInfo = institutionInfoDao.selectByPrimaryId(institutionInfo);
        if (institutionInfo != null) {
          // 提现人说明(机构名称)
          bnesAcctEnchashment.setEnchashmentDepict(institutionInfo.getInstitutionName());
          // 银行账户名
          bnesAcctEnchashment.setBankAccountName(institutionInfo.getBankUname());
          // 银行账号
          bnesAcctEnchashment.setBankCardNumber(institutionInfo.getBankAccount());
          // 开户行
          bnesAcctEnchashment.setBankOfDeposit(institutionInfo.getBankName());
        }
      }*/
    }
    bnesAcctEnchashmentId = bnesAcctEnchashmentDAO.insertSelective(bnesAcctEnchashment);

    if (bnesAcctEnchashmentId == null) {
      bnesAcctEnchashmentId = BigDecimal.ZERO;
    }

    logger.info("添加供应商申请提现记录成功---------------");
    return bnesAcctEnchashmentId;
  }
}
