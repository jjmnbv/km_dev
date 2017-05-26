package com.pltfm.app.service;

import java.util.Map;

public interface CustomerScoreService {

  /**
   * 添加客户积分
   * 
   * @param ruleCode 规则编号 rule002 个人购买商品 rule001 个人注册会员
   * @param scoreType 积分类型 2积分消费 1 积分积累（所有退款、撤销等按照积分消费来处理）
   * @param loginAccount 登录账号
   * @param paramsMap 积分规则表达式参数对应值集合
   * @return 0 代表出现异常失败 1代表执行成功
   * @throws Exception 异常信息
   */
  /*public Integer addUserScore(String ruleCode, Integer scoreType, String loginAccount,
      Map<String, String> paramsMap);*/

}
