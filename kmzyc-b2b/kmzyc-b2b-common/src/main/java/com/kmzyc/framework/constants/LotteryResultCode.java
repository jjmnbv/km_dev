package com.kmzyc.framework.constants;

/**
 * Description:抽奖接口返回的结果代码 User: lishiming Date: 14-2-26 下午2:57 Since: 1.0
 */
public class LotteryResultCode {
  /**
   * 成功
   */
  public static final String SUCCESS = "200";

  /**
   * 未登录
   */
  public static final String NOT_LOGIN = "1";

  /**
   * 失败
   */
  public static final String FAILED = "0";
  /**
   * 领奖，已领过该奖品
   */
  public static final String ORDER_FAILED = "500";
  /**
   * 领奖，已过期
   */
  public static final String TIME_FAILED = "505";
  /**
   * 中奖，但是颁奖异常
   */
  public static final String GIVEN_PRIZE_FAILED = "101";

  /**
   * 某人（即开） 中奖
   */
  public static final String LOTTERY_AWARDS = "201";

  /**
   * 某人（即开） 未中奖
   */
  public static final String LOSE_AWARDS = "202";

  /**
   * 抽奖活动不在进行中
   */
  public static final String NOT_START = "300";

  /**
   * 抽奖活动不存在
   */
  public static final String NOT_EXIST = "400";

  /**
   * 抽奖活动暂停
   */
  public static final String PAUSE = "301";

  /**
   * 抽奖活动已结束
   */
  public static final String STOP = "302";

  /**
   * 会员等级不够
   */
  public static final String LEVEL_LOW = "402";
  /**
   * 会员未验证手机号码
   */
  public static final String MOBILE_NOT_VERIFY = "405";

  /**
   * 活动不满足A天允许B人参加
   */
  public static final String DAYSCANATTEND = "406";

  /**
   * 个人参与限制已达上限
   */
  public static final String PEPLCANATTEND = "407";

  /**
   * 注册时间不在活动要求的注册范围内
   */
  public static final String REGISTIME_NOT_IN = "408";

  /**
   * 订单消费总金额不满足活动要求
   */
  public static final String ORDERMONEY_NOT_FILL = "409";

  /**
   * 订单在某时间区间内累计消费总金额不满足活动要求
   */
  public static final String ORDERACCUMULATE_MONEY_NOT_FILL = "415";

  /**
   * 从XX开始计算，已完成订单商品总额（不含运费）不满足活动要求
   */
  public static final String ORDERCOMPLETE_SPECIFYMONEY_NOT_FILL = "416";

  /**
   * 会员的积分不满足活动要求
   */
  public static final String POINT_NOT_FILL = "410";

  /**
   * 中奖次数已达上限
   */
  public static final String WIN_COUNT_FULL = "411";

  /**
   * 奖项中奖次数已达上限
   */
  public static final String WINPRIZE_COUNT_FULL = "412";

  /**
   * 奖项XX天可以开出XX已达上限
   */
  public static final String WIPRIZE_WIN_FULL = "413";

  /**
   * 在XX时间到XX时间才可能中奖
   */
  public static final String WINPRIZE_WIN_TIME = "414";
  /**
   * 超过并发数量
   * */
  public static final String EXCEEDED = "415";

}
