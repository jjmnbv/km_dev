package com.pltfm.crowdsourcing.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.km.crowdsourcing.model.ClearingUser;
import com.km.crowdsourcing.model.InstitutionUser;
import com.km.crowdsourcing.service.ClearingService;
import com.pltfm.app.dataobject.BnesAcctTransListDO;
import com.pltfm.app.util.DateTimeUtils;
import com.pltfm.app.vobject.AccountInfo;
import com.pltfm.app.vobject.BnesAcctTransactionQuery;
import com.pltfm.crowdsourcing.dao.InstitutionUserDao;
import com.pltfm.crowdsourcing.dao.UserClearingDao;

import redis.clients.jedis.JedisCluster;


@Service("clearingService")
public class ClearingServiceImpl implements ClearingService {

  private static Logger logger = LoggerFactory.getLogger(ClearingServiceImpl.class);

  @Resource(name = "institutionUserDao")
  private InstitutionUserDao institutionUserDao;

  @Resource(name = "userClearingDao")
  private UserClearingDao userClearingDao;
  
  @Resource
  private JedisCluster jedis;

  /**
   * 结算所有用户
   * 
   * @param
   * @return 1:成功，0失败
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public int allClearing() {
    List<BnesAcctTransactionQuery> bnesAcctTransactionQueryList = null;// 交易流水集合
    List<BnesAcctTransListDO> bnesAcctTransListDOList = null;// 余额交易变化记录集合
    List<InstitutionUser> institutionUserList = null;// 众包用户集合
    Map<String, AccountInfo> accountInfoMap = null;// 账号信息集合
    while (true) {
      try {
        // 当本次跑的数据小于999或预估执行时间超过
        Calendar cal1 = Calendar.getInstance();
        List<ClearingUser> seList =
            institutionUserDao.selectByClearingUser(new ArrayList<String>());

        bnesAcctTransactionQueryList = new ArrayList<BnesAcctTransactionQuery>();
        bnesAcctTransListDOList = new ArrayList<BnesAcctTransListDO>();
        institutionUserList = new ArrayList<InstitutionUser>();
        accountInfoMap = new HashMap<String, AccountInfo>();

        if (null == seList) {
          break;
        }
        setListValue(seList, bnesAcctTransactionQueryList, bnesAcctTransListDOList,
            institutionUserList, accountInfoMap);
        if (accountInfoMap.size() > 0) {
          userClearingDao.batchInsertListDO(accountInfoMap, institutionUserList,
              bnesAcctTransactionQueryList, bnesAcctTransListDOList);
        }

        if (null == seList || seList.size() < 99) {
          break;
        }
      } catch (Exception e) {
        logger.error("结算所有用户失败", e);
        return 0;
      }
    }
    return 1;
  }

  /**
   * 
   * 当list为空时 为全部结算，否则为批量结算
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public int allClearing(InstitutionUser institutionUser) {
    /*if (!redisTemplate.tryLock("allClearing")) {
      return 0;
    }*/
    Long i = jedis.setnx("allClearing", "1");
    if (i<=0) {
      return 0;
    }
    int result = 1;
    int index = 0;
    try {
      while (true) {
        if (index >= 100) {
          logger.info("超过10000条或存在结算失败数据需再次结算");
          break;
        }
        // 当本次跑的数据小于999或预估执行时间超过
        Calendar cal1 = Calendar.getInstance();
        List<String> seList = new ArrayList();
        if (institutionUser.getIds() == null) {
          seList = institutionUserDao.selectIdsByUser(institutionUser);
        } else {
          result = singleClearing(institutionUser.getIds());
        }
        if (null == seList || seList.size() <= 0) {
          break;
        }
        result = singleClearing(seList);
        if (result == 0) {
          break;
        }
        index++;
      }
    } catch (Exception e) {
      logger.error("结算所有用户失败", e);
      return 0;
    }finally{
        jedis.del("allClearing");
    } 
    return result;
  }

  /**
   * 批量结算
   * 
   * @param List
   * @return 1:成功，0失败
   */
  @Transactional(rollbackFor = Exception.class)
  private int singleClearing(List<String> ids) {
    List<BnesAcctTransactionQuery> bnesAcctTransactionQueryList =
        new ArrayList<BnesAcctTransactionQuery>();// 交易流水集合
    List<BnesAcctTransListDO> bnesAcctTransListDOList = new ArrayList<BnesAcctTransListDO>();// 余额交易变化记录集合
    List<InstitutionUser> institutionUserList = new ArrayList<InstitutionUser>();// 众包用户集合
    Map<String, AccountInfo> accountInfoMap = new HashMap<String, AccountInfo>();// 账号信息集合
    List<ClearingUser> seList;

    try {
      seList = institutionUserDao.selectByClearingUser(ids);
      setListValue(seList, bnesAcctTransactionQueryList, bnesAcctTransListDOList,
          institutionUserList, accountInfoMap);
      if (accountInfoMap.size() > 0) {
        userClearingDao.batchInsertListDO(accountInfoMap, institutionUserList,
            bnesAcctTransactionQueryList, bnesAcctTransListDOList);
      }
    } catch (Exception e) {
      logger.error("结算失败", e);
      return 0;
    }

    return 1;
  }

  /**
   * 设置变量值
   * 
   * @param List
   * @return
   */

  private void setListValue(List<ClearingUser> seList,
      List<BnesAcctTransactionQuery> bnesAcctTransactionQueryList,
      List<BnesAcctTransListDO> bnesAcctTransListDOList, List<InstitutionUser> institutionUserList,
      Map<String, AccountInfo> accountInfoMap) {
    BnesAcctTransactionQuery bnesAcctTransactionQuery = null;
    BnesAcctTransListDO bnesAcctTransListDO = null;
    InstitutionUser institutionUser = null;
    BigDecimal afterAmount;
    BigDecimal afterAmountAccount;
    for (ClearingUser cu : seList) {
      BigDecimal clearingAmount = cu.getClearingAmount();
      if (clearingAmount == null) {
        clearingAmount = BigDecimal.ZERO;
      }
      if (clearingAmount.compareTo(BigDecimal.ZERO) > 0) {
        bnesAcctTransactionQuery = new BnesAcctTransactionQuery();
        bnesAcctTransListDO = new BnesAcctTransListDO();
        institutionUser = new InstitutionUser();
        Date date = DateTimeUtils.getCalendarInstance().getTime();
        // 更新众包用户结算状态
        institutionUser.setId(cu.getId());
        institutionUser.setClearingStatus((short) 1);
        institutionUser.setLastModifyDate(date);
        institutionUser.setClearingDate(date);
        institutionUserList.add(institutionUser);
        // 修改账户余额
        BigDecimal beforeAmount = BigDecimal.ZERO;
        BigDecimal beforeAmountAccount = BigDecimal.ZERO;
        if (!accountInfoMap.containsKey(cu.getInstitutionCode())) {
          AccountInfo accountInfo = new AccountInfo();
          if (cu.getAmountAvlibal() == null) {
            accountInfo.setAmountAvlibal(BigDecimal.ZERO);
          }
          beforeAmount = cu.getAmountAvlibal();
          beforeAmountAccount = cu.getAccountAmount();
          afterAmount = beforeAmount.add(clearingAmount);
          afterAmountAccount = beforeAmountAccount.add(clearingAmount);
          accountInfo.setAmountAvlibal(afterAmount);
          accountInfo.setN_AccountAmount(afterAmountAccount);
          accountInfo.setD_ModifyDate(date);
          accountInfo.setN_AccountId(cu.getAccountId());
          accountInfoMap.put(cu.getInstitutionCode(), accountInfo);
        } else {
          AccountInfo account = accountInfoMap.get(cu.getInstitutionCode());
          beforeAmount =account.getAmountAvlibal();
          beforeAmountAccount = account.getN_AccountAmount();
          afterAmount = beforeAmount.add(clearingAmount);
          afterAmountAccount = beforeAmountAccount.add(clearingAmount);
          account.setAmountAvlibal(afterAmount);
          account.setN_AccountAmount(afterAmountAccount);
          account.setD_ModifyDate(date);
          accountInfoMap.put(cu.getInstitutionCode(), account);
        }
        // 生成交易流水
        bnesAcctTransactionQuery.setAccountId(cu.getAccountId());
        bnesAcctTransactionQuery.setStatus(1);
        String number = new SimpleDateFormat("yyyyMMddhhmmssSSS").format(new Date());
        bnesAcctTransactionQuery.setAccountNumber(number);
        bnesAcctTransactionQuery.setContent("众包用户:" + cu.getClearLoginId() + "结算");
        bnesAcctTransactionQuery.setCreateDate(date);
        bnesAcctTransactionQuery.setType(24);
        bnesAcctTransactionQuery.setCreatedId(cu.getLoginId());// 结算机构loginId
        bnesAcctTransactionQuery.setModifieId(cu.getClearLoginId());// 结算的用户loginId
        bnesAcctTransactionQuery.setModifyDate(date);
        bnesAcctTransactionQuery.setAmount(clearingAmount);
        bnesAcctTransactionQueryList.add(bnesAcctTransactionQuery);

        // 添加余额交易变化记录
        bnesAcctTransListDO.setAccountId(Integer.valueOf(cu.getAccountId()));
        // bnesAcctTransListDO.setAccountTransactionId(111);
        bnesAcctTransListDO.setBeforeAmount(beforeAmount);
        bnesAcctTransListDO.setAfterAmount(afterAmount);
        bnesAcctTransListDO.setMoneyAmount(bnesAcctTransactionQuery.getAmount());
        bnesAcctTransListDO.setCreateDate(date);
        bnesAcctTransListDO.setCreatedId(bnesAcctTransactionQuery.getCreatedId());// 结算机构loginId
        bnesAcctTransListDO.setModifieId(bnesAcctTransactionQuery.getModifieId());// 结算的用户loginId
        bnesAcctTransListDO.setModifyDate(date);
        bnesAcctTransListDOList.add(bnesAcctTransListDO);
      }
    }
  }
}
