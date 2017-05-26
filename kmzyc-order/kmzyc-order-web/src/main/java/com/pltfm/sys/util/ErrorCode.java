package com.pltfm.sys.util;

public class ErrorCode {

  public static final int ERROR_UNKOWN = -1;

  public static final int NULL_INPUT = 10000;

  public static final int NO_LOGIN_USER = 10001;
  public static final int INVALIDATE_PWD = 10002;
  public static final int INVALIDATE_CODE = 10003;

  public static final int SPIDER_NETWORK_ERROR = 200001;
  public static final int SPIDER_ANALYSIS_ERROR = 200002;
  public static final int SPIDER_NODATA_ERROR = 200003;

  public static final int FILE_STREAM_NULL_ERROR = 300001;
  public static final int FILE_SIZE_ERROR = 300002;
  public static final int FILE_NOT_FOUND_ERROR = 300003;

  public static final int DB_ERROR = 99999;

  public static final int ILLEGAL_REQUEST = 50000;
  public static final int INPUT_DATE_ERROR = 50001;

  /**
   * 下单模块 01 01
   */
  public static final int INNER_SINGLE_ERROR = 101;
  public static final int INTER_SINGLE_ERROR = 201;

  // 正向物流信息
  public static final int INNER_SINGLE_LOGISTICS_ERROR = 1011;
  public static final int INTER_SINGLE_LOGISTICS_ERROR = 2011;

  // 下单余额
  public static final int INNER_SINGLE_MONEY_ERROR = 1012;
  public static final int INTER_SINGLE_MONEY_ERROR = 2012;

  // 下单优惠券
  public static final int INNER_SINGLE_COUPON_ERROR = 1013;
  public static final int INTER_SINGLE_COUPON_ERROR = 2013;
  /**
   * 支付模块 02 02
   */
  public static final int INNER_PAYMENT_ERROR = 102;
  public static final int INTER_PAYMENT_ERROR = 202;

  // 计算
  public static final int INNER_PAYMENT_CALC_ERROR = 1021;
  public static final int INTER_PAYMENT_CALC_ERROR = 2021;

  /**
   * 运费模块 03 03
   */
  public static final int INNER_FREIGHT_ERROR = 103;
  public static final int INTER_FREIGHT_ERROR = 203;

  // 查询
  public static final int INNER_FREIGHT_QUERY_ERROR = 103;
  public static final int INTER_FREIGHT_QUERY_ERROR = 203;

  // 计算
  public static final int INNER_FREIGHT_CALC_ERROR = 103;
  public static final int INTER_FREIGHT_CALC_ERROR = 203;
  /**
   * 发票模块 04 04
   */
  public static final int INNER_INVOICE_ERROR = 104;
  public static final int INTER_INVOICE_ERROR = 204;
  /**
   * 结转模块 05 05
   */
  public static final int INNER_CARRYOVER_ERROR = 105;
  public static final int INTER_CARRYOVER_ERROR = 205;
  /**
   * 操作流水模块 06 06
   */
  public static final int INNER_OPERATE_ASSEMBLY_ERROR = 106;
  public static final int INTER_OPERATE_ASSEMBLY_ERROR = 206;

  // 退换货操作流水
  public static final int INNER_OPERATE_ASSEMBLY_RETURNS_ERROR = 1061;
  public static final int INTER_OPERATE_ASSEMBLY_RETURNS_ERROR = 2061;

  // 订单操作流水
  public static final int INNER_OPERATE_ASSEMBLY_ORDER_ERROR = 1062;
  public static final int INTER_OPERATE_ASSEMBLY_ORDER_ERROR = 2062;

  // 订单评价操作流水
  public static final int INNER_OPERATE_ASSEMBLY_EVALUATE_ERROR = 1063;
  public static final int INTER_OPERATE_ASSEMBLY_EVALUATE_ERROR = 2063;

  // 产品操作流水
  public static final int INNER_OPERATE_ASSEMBLY_PRODUCT_ERROR = 1063;
  public static final int INTER_OPERATE_ASSEMBLY_PRODUCT_ERROR = 2063;

  // 优惠券操作流水
  public static final int INNER_OPERATE_ASSEMBLY_COUPON_ERROR = 1063;
  public static final int INTER_OPERATE_ASSEMBLY_COUPON_ERROR = 2063;

  /**
   * 支付流水模块 07 07
   */
  public static final int INNER_PAYMENT_ASSEMBLY_ERROR = 107;
  public static final int INTER_PAYMENT_ASSEMBLY_ERROR = 207;

  // 余额支付流水
  public static final int INNER_PAYMENT_ASSEMBLY_PAY_ERROR = 1071;
  public static final int INTER_PAYMENT_ASSEMBLY_PAY_ERROR = 2071;

  // 优惠券支付流水
  public static final int INNER_PAYMENT_ASSEMBLY_COUPON_ERROR = 1072;
  public static final int INTER_PAYMENT_ASSEMBLY_COUPON_ERROR = 2072;

  // 第三方支付流水
  public static final int INNER_PAYMENT_ASSEMBLY_THIRD_ERROR = 1073;
  public static final int INTER_PAYMENT_ASSEMBLY_THIRD_ERROR = 2073;
  /**
   * 退换货处理模块 08 08
   */
  public static final int INNER_RETURNS_ERROR = 108;
  public static final int INTER_RETURNS_ERROR = 208;

  // 查询处理
  public static final int INNER_RETURNS_QUERY_ERROR = 1081;
  public static final int INTER_RETURNS_QUERY_ERROR = 2081;

  // 更新处理
  public static final int INNER_RETURNS_UPDATE_ERROR = 1082;
  public static final int INTER_RETURNS_UPDATE_ERROR = 2082;

  // 退款处理
  public static final int INNER_RETURNS_REFUND_ERROR = 1083;
  public static final int INTER_RETURNS_REFUND_ERROR = 2083;

  // 反向物流信息
  public static final int INNER_RETURNS_LOGISTICS_ERROR = 1084;
  public static final int INTER_RETURNS_LOGISTICS_ERROR = 2014;

  // 解冻优惠券
  public static final int INNER_RETURNS_COUPON_ERROR = 1085;
  public static final int INTER_RETURNS_COUPON_ERROR = 2085;

  /**
   * 订单评价模块 09 09
   */
  public static final int INNER_ORDER_EVALUATE_ERROR = 109;
  public static final int INTER_ORDER_EVALUATE_ERROR = 209;

  // 生成评价
  public static final int INNER_ORDER_EVALUATE_NEW_ERROR = 109;
  public static final int INTER_ORDER_EVALUATE_NEW_ERROR = 209;
  /**
   * 订单查询模块 10 10
   */
  public static final int INNER_ORDER_QUERY_ERROR = 1010;
  public static final int INTER_ORDER_QUERY_ERROR = 2010;
  /**
   * 订单明细查询模块 11 11
   */
  public static final int INNER_ORDER_DETAIL_ERROR = 1011;
  public static final int INTER_ORDER_DETAIL_ERROR = 2011;

  // 订单商品查询
  public static final int INNER_ORDER_PRODUCT_DETAIL_ERROR = 1012;
  public static final int INTER_ORDER_PRODUCT_DETAIL_ERROR = 2012;

  // 订单支付查询
  public static final int INNER_ORDER_PAY_DETAIL_ERROR = 1013;
  public static final int INTER_ORDER_PAY_DETAIL_ERROR = 2013;

  // 订单操作查询
  public static final int INNER_ORDER_OPERATE_DETAIL_ERROR = 1014;
  public static final int INTER_ORDER_OPERATE_DETAIL_ERROR = 2014;

  // 订单优惠查询
  public static final int INNER_ORDER_PREFERENTIAL_DETAIL_ERROR = 1015;
  public static final int INTER_ORDER_PREFERENTIAL_DETAIL_ERROR = 2015;

  // 订单发票查询
  public static final int INNER_ORDER_INVOICE_DETAIL_ERROR = 1016;
  public static final int INTER_ORDER_INVOICE_DETAIL_ERROR = 2016;

  // 订单分析表
  public static final int INNER_ORDER_ANALYSIS_REPORT_ERROR = 1017;
  public static final int INTER_ORDER_ANALYSIS_REPORT_ERROR = 2017;
  // 订单分同步表
  public static final int INNER_ORDER_SYNC_REPORT_ERROR = 1018;

  /**
   * 结算单查询seller_Settlement
   */
  public static final int INTER_SELLER_SETTLEMENT_ERROR = 2020;
  /**
   * 妥投商品明细表查询hurl_product
   */
  public static final int INTER_HURL_PRODUCT_ERROR = 2021;
  /**
   * 妥投订单运费明细: hurl_fare
   */
  public static final int INTER_HURL_FARE_ERROR = 2022;
  /**
   * 退款明细：Settlement_refund
   */
  public static final int INTER_SETTLEMENT_REFUND_ERROR = 2023;

  /**
   * 差异调整明细Diff_adj
   */
  public static final int INTER_DIFF_ADJ_ERROR = 2024;
  /**
   * 结算单操作流水：settment_operate_statement
   */
  public static final int INTER_SETTLEMENT_OPERATE_STATEMENT_ERROR = 2025;

  public static final int INTER_SELLER_SETTLEMENT_SIZE_ERROR = 2026;
  public static final int INTER_SELLER_SETTLEMENT_DETAIL_ERROR = 2027;
  public static final int INTER_SELLER_SETTLEMENT_SIMPLE_DETAIL_ERROR = 2028;


  /**
   * 订单同步到总部系统异常号
   */
  public static final int SYNC_ORDER_2_BASE_ERROR = 3001;

  /**
   * 退换货订单同步到总部系统异常号
   */
  public static final int SYNC_ORDER_ALTER_2_BASE_ERROR = 3002;

  /**
   * 订单风控
   */
  public static final int INTER_ORDER_RISK_ERROR = 2029;
}
