package com.kmzyc.user.remote.service;

/**
 * 专家经验远程接口
 * 
 * @author gwl
 * @since 2013-08-06
 */
@SuppressWarnings("unused")
public interface UserExperienceService{
  /**
   * 更新专家经验远程接口
   * 
   * @param ruleCode 规则编号 rule003在线回复 rule002 完善资料 rule001 个人注册 满意度(rule0011 rule0012 rule0013
   *        rule0014 rule0015 )
   * @param loginAccount 登录账号
   * @param customerTypeId 客户类别主键
   * @return 0 代表出现异常失败 1代表执行成功
   * @throws Exception 异常信息
   */
  public Integer updateUserIntegralRule(String ruleCode, String loginAccount) throws Exception;
}
