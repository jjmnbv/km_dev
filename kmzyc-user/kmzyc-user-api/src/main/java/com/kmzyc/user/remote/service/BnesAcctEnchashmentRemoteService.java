package com.kmzyc.user.remote.service;

import java.math.BigDecimal;

import com.pltfm.app.vobject.BnesAcctEnchashment;

/**
 * 供应商余额取现相关接口
 * 
 * @author lijianjun
 * @since 15-04-28
 */
public interface BnesAcctEnchashmentRemoteService {
  /**
   * 添加供应商余额取现申请记录
   * 
   * @param bnesAcctEnchashment
   * @return null：添加申请记录失败； 否则返回bnesAcctEnchashmentId
   * @throws SQLExceptionaaaa
   */
  BigDecimal insertBnesAcctEnchashment(BnesAcctEnchashment bnesAcctEnchashment) throws Exception;
}
