package com.pltfm.crowdsourcing.service.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.km.crowdsourcing.common.GlobalVariable;
import com.km.crowdsourcing.service.GlobalVariableService;

import redis.clients.jedis.JedisCluster;


@Service("globalVariableService")
public class GlobalVariableServiceImpl implements GlobalVariableService {

  private static Logger logger = LoggerFactory.getLogger(GlobalVariableServiceImpl.class);

  @Resource
  private JedisCluster jedis;

  // 全局变量map的名称
  private static final String Map_Global_Variable = "mapglobalVariable";
  // 众包功能开关(0关，1开)
  private static final String IS_FUNCTION_VALID = "isFunctionValid";
  // 手机注册验证方式（0短信，1语音）
  private static final String IS_VERIFICATION_MODE = "isVerificationMode";
  // 申请机构账号审核（0自动审核，1人工审核）
  private static final String IS_INSTITUTION_AUDIT = "isInstitutionAudit";
  // 推广有效期
  private static final String VALID_DATE = "validDate";
  // 结算金额
  private static final String CLEARING_AMOUNT = "clearingAmount";


  /**
   * 获取全局变量
   * 
   * @param
   * @return GlobalVariable
   */
  @Override
  public GlobalVariable getGlobalVariable() {


    GlobalVariable globalVariable = new GlobalVariable();
    Map<String, String> map = jedis.hgetAll(Map_Global_Variable);
    if (null != map) {
      globalVariable.setMap(map);
      globalVariable.setValue(map);
    }

    return globalVariable;
  }

  /**
   * 设置全局变量
   * 
   * @param
   * @return GlobalVariable
   */
  @Override
  public void updateGlobalVariable(GlobalVariable globalVariable) {
    try {

        jedis.hset(Map_Global_Variable, IS_FUNCTION_VALID,
          globalVariable.getIsFunctionValid());
        jedis.hset(Map_Global_Variable, IS_VERIFICATION_MODE,
          globalVariable.getIsVerificationMode());
        jedis.hset(Map_Global_Variable, IS_INSTITUTION_AUDIT,
          globalVariable.getIsInstitutionAudit());
        jedis.hset(Map_Global_Variable, VALID_DATE, globalVariable.getValidDate());
        jedis.hset(Map_Global_Variable, CLEARING_AMOUNT, globalVariable.getClearingAmount());
    } catch (Exception e) {
      logger.error("更新全局变量失败", e);
    }
  }
}
