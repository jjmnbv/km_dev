package com.kmzyc.b2b.util.pay;

/**
 * 定义支付行为的接口
 * 
 * @author zengming
 * @version 1.0
 *@since 2013-10-17
 * 
 */
public interface PayPlugInInterface {
  // 调支付平台接口
  public String onPay() throws Exception;

  // 支付平台回调
  public Object onReturn() throws Exception;
}
