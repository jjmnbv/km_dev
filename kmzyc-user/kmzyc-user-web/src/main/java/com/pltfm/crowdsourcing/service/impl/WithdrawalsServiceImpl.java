package com.pltfm.crowdsourcing.service.impl;

import java.math.BigDecimal;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


import com.km.crowdsourcing.model.InstitutionInfo;
import com.km.crowdsourcing.model.WithdrawalsInfo;
import com.km.crowdsourcing.service.WithdrawalsService;
import com.kmzyc.user.remote.service.BnesAcctEnchashmentRemoteService;
import com.pltfm.app.dao.AccountInfoDAO;
import com.pltfm.app.util.StringUtils;
import com.pltfm.app.vobject.AccountInfo;
import com.pltfm.app.vobject.BnesAcctEnchashment;
import com.pltfm.crowdsourcing.dao.InstitutionInfoDao;
import com.pltfm.crowdsourcing.dao.WithdrawalsDao;

import redis.clients.jedis.JedisCluster;


@Service("withdrawalsService")
public class WithdrawalsServiceImpl implements WithdrawalsService {

  private static final Logger logger = LoggerFactory.getLogger(WithdrawalsServiceImpl.class);

  @Resource
  private WithdrawalsDao withdrawalsDao;


  @Resource
  private BnesAcctEnchashmentRemoteService bnesAcctEnchashmentRemoteService;

  @Resource
  private AccountInfoDAO accountInfoDAO;

  @Resource
  private InstitutionInfoDao institutionInfoDao;


  @Resource
  private JedisCluster jedis;

  /**
   * @Title: getWithdrawalsInfo @Description: 跳转提现页面 @return WithdrawalsInfo @throws
   */
  @Override
  public WithdrawalsInfo getWithdrawalsInfo(String institutionCode) {
    WithdrawalsInfo withdrawalsInfo = new WithdrawalsInfo();

    try {
      withdrawalsInfo = withdrawalsDao.selectByPrimaryKey(institutionCode);
    } catch (Exception e) {
      logger.error("获取机构信息失败，机构编码:{}" + institutionCode, e);
    }
    return withdrawalsInfo;
  }

  /**
   * @Title: insertWithdrawalsInfo @Description: 申请提现 @return int @throws
   */
  @Override
  public int insertWithdrawalsInfo(WithdrawalsInfo info) {

    AccountInfo accountInfo = new AccountInfo();
    BigDecimal id = BigDecimal.ZERO;
    String key = info.getLoginId().toString();
    try {
        Long i = jedis.setnx(key, "1");
        /*if (redisTemplate.tryLock(info.getLoginId().toString())) {*/
        if(i>0){
            jedis.expire(key, 3*60*60);
        if (!StringUtils.isEmpty(info.getLoginId())) {
          accountInfo = accountInfoDAO
              .selectByPrimaryLoginInfo(Integer.valueOf((info.getLoginId().toString())));
        }

        if (accountInfo.getAmountAvlibal().compareTo(info.getAmount())==-1) {
          return -3;
        }
        // 账户被冻结
        if (accountInfo.getN_Status().intValue() == 0) {
          return -4;
        }

        String bankAccount = info.getBankAccount();
        String bankName = info.getBankName();
        String bankUname = info.getBankUname();

        if (!StringUtils.isEmpty(bankAccount)
            && !StringUtils.isEmpty(bankName)
            && !StringUtils.isEmpty(bankUname)) {
          InstitutionInfo institutionInfo = new InstitutionInfo();
          institutionInfo.setBankAccount(bankAccount);
          institutionInfo.setBankName(java.net.URLDecoder.decode(bankName, "UTF-8"));
          institutionInfo.setBankUname(java.net.URLDecoder.decode(bankUname, "UTF-8"));
          institutionInfo.setLoginId(info.getLoginId());
          // 更新银行信息
          if (institutionInfoDao.updateInsInfoSelective(institutionInfo) < 1) {
            logger.info("更新机构提现银行信息失败loginId:{}" + info.getLoginId());
          }
        }
        BnesAcctEnchashment bae = new BnesAcctEnchashment();
        bae.setnLoginId(new BigDecimal(accountInfo.getN_LoginId()));
        bae.setAccountId(new BigDecimal(accountInfo.getN_AccountId()));
        bae.setLoginAccount(accountInfo.getLoginAccount());
        bae.setEnchashmentAmount(info.getAmount());
        bae.setEnchashmentResource(new BigDecimal(2));// 提现来源 0：供应商；1微商;2机构
        bae.setEnchashmentType(BigDecimal.ZERO);// 提现方式 0：到银行卡.1支付宝
        id = bnesAcctEnchashmentRemoteService.insertBnesAcctEnchashment(bae);
      } else {
        logger.info("key:{},加锁失败，机构提现缓存正在创建,请稍候在试。", info.getLoginId().toString());
        return 0;
      }
    } catch (Exception e) {
      logger.error("申请提现失败loginId:{}", info.getLoginId(), e);
    }finally {
        jedis.del(key);
      }
    return id.intValue();
  }
}
