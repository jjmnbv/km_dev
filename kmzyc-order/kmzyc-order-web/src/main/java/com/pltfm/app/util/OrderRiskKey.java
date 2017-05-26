package com.pltfm.app.util;

import java.util.ArrayList;
import java.util.List;

public class OrderRiskKey {
  // 风控条件key
  public static final byte[] ORDER_RISK_SETTING_KEY = "ORDER_RISK_SETTING_KEY".getBytes();
  // 风控很白名单状态
  public static final int ORDER_RISK_VALID_DELED = 0;// 删除
  public static final int ORDER_RISK_VALID_NORMAL = 1;// 有效

  // 黑白名单设置
  public static final String ORDER_RISK_BLACK_LIST = "order_risk_black_list";
  public static final String ORDER_RISK_WHITE_LIST = "order_risk_white_list";

  // 风控设置
  public static final String ORDER_RISK_THRESHOLD = "order_risk_threshold";
  public static final String ORDER_RISK_AUTO_ADD_BLACK_LIST = "order_risk_auto_add_black_list";
  public static final String ORDER_RISK_ORDER_PAY_RATE = "order_risk_order_pay_rate";
  public static final String ORDER_RISK_ORDER_MONEY_MAX = "order_risk_order_money_max";
  public static final String ORDER_RISK_ORDER_PV_RATE = "order_risk_order_pv_rate";
  public static final String ORDER_RISK_SAME_CONSIGNEE_MOBILE = "order_risk_same_consignee_mobile";
  public static final String ORDER_RISK_SAME_CUSTOMER_ACCOUNT = "order_risk_same_customer_account";
  public static final String ORDER_RISK_SCM_RECENT_TIME = "order_risk_scm_recent_time";
  public static final String ORDER_RISK_COMMERCE_YEST_ORDER_COUNT =
      "order_risk_commerce_yest_order_count";
  public static final String ORDER_RISK_COMMERCE_YEST_ORDER_MONEY =
      "order_risk_commerce_yest_order_money";

  public static final String[] ORDER_RISK_INIT_SETTINGS = {ORDER_RISK_THRESHOLD + ",1,0,0",
      ORDER_RISK_AUTO_ADD_BLACK_LIST + ",1,1,0", ORDER_RISK_ORDER_PAY_RATE + ",10,1,0",
      ORDER_RISK_ORDER_MONEY_MAX + ",1000,1,0", ORDER_RISK_ORDER_PV_RATE + ",100,2,0",
      ORDER_RISK_SAME_CONSIGNEE_MOBILE + ",5,1,0", ORDER_RISK_SAME_CUSTOMER_ACCOUNT + ",5,1,0",
      ORDER_RISK_SCM_RECENT_TIME + ",5,1,0", ORDER_RISK_COMMERCE_YEST_ORDER_COUNT + ",200,1,20",
      ORDER_RISK_COMMERCE_YEST_ORDER_MONEY + ",200,1,5000"};

  // 风控设置

  public static final List<Integer> RISK_STATUS = new ArrayList<Integer>();// 风控列表允许出现的订单状态
  static {
    RISK_STATUS.add(21);
    RISK_STATUS.add(22);
    RISK_STATUS.add(-3);
  }

  /**
   * 风控名单类型
   * 
   * @author xlg
   * 
   */
  public enum OrderRiskRosterTypeEnum {
    ROSTER_TYPE_CUSTOMER_ACCOUNT(1, "下单账号"), ROSTER_TYPE_CONSIGNEE_MOBILE(2,
        "收货手机"), ROSTER_TYPE_COMMERCE(3, "入驻商家");
    private int code;
    private String key;

    public int getCode() {
      return code;
    }

    public void setCode(int code) {
      this.code = code;
    }

    public String getKey() {
      return key;
    }

    public void setKey(String key) {
      this.key = key;
    }

    private OrderRiskRosterTypeEnum(int code, String key) {
      this.code = code;
      this.key = key;
    }
  }
  /**
   * 风控纬度
   * 
   * @author xlg
   * 
   */
  public enum OrderRiskLatitudeEnum {
    ORDERRISKLATITUDE_ROSTER(0, "黑白名单"), ORDERRISKLATITUDE_ORDER(1,
        "订单维度"), ORDERRISKLATITUDE_CONSUMER(2, "消费者维度"), ORDERRISKLATITUDE_SUPPLIER(3, "商家维度");
    private int code;
    private String key;

    public int getCode() {
      return code;
    }

    public void setCode(int code) {
      this.code = code;
    }

    public String getKey() {
      return key;
    }

    public void setKey(String key) {
      this.key = key;
    }

    private OrderRiskLatitudeEnum(int code, String key) {
      this.code = code;
      this.key = key;
    }
  }
}
