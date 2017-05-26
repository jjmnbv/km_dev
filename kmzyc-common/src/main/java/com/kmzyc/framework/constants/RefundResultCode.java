package com.kmzyc.framework.constants;

/**
 * 退款返回结果
 * 
 * @author xlg
 * 
 */
public class RefundResultCode {

  /**
   * 退款成功
   */
  public static final String SUCCESS = "0200";

  /**
   * 退款失败
   */
  public static final String FAILED = "0201";

  /**
   * 通用异常
   */
  public static final String EXCEPTION = "0202";

  /**
   * 系统异常
   */
  public static final String SYSTEM_EXCEPTION = "0203";

  /**
   * 配置文件错误
   */
  public static final String CONFIG_EXCEPTION = "0204";

  /**
   * 不支持退款
   */
  public static final String UNSUPORT_REFUND_EXCEPTION = "0205";

  /**
   * 不支持该银行退款
   */
  public static final String UNSUPORT_BANK_REFUND_EXCEPTION = "0206";

  /**
   * 退款金额错误
   */
  public static final String ERROR_REFUND_MONEY_EXCEPTION = "0207";

  /**
   * 暂时不支持用户提现
   */
  public static final String UNSUPORT_DRAWALS_EXCEPTION = "0208";

  /**
   * 无支付流水
   */
  public static final String NO_PAY_STATEMENT_EXCEPTION = "0209";
}
