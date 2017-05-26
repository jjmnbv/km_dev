package com.kmzyc.framework.constants;

/**
 * 订单字典表中相关的枚举类
 * 
 * @author lishiming
 * @version 1.0
 */
public class OrderDictionaryEnums {

  /**
   * 订单状态枚举类
   */
  public enum OrderStatus {
    Cancel_Done(-1, "已取消", 0, "Already_Cancel"), Not_Pay(1, "未付款", 1, "Not_Pay"), Pay_Done(2,
        "已付款", 2, "Pay_Done"), Settle_Done(3, "已结转", 3, "Settle_Done"), Stock_Done(4, "已出库", 4,
        "Already_Stock"), Ship_Done(5, "已配送", 5, "Already_Ship"), Order_Done(6, "已完成", 6,
        "Already_Done"), Ship_Fail(12, "送货失败", 8, "Ship_Fail"), Settle_Not_Stock(15, "已结转未出库", 10,
        "Settle_Not_Stock"), Split_Done(16, "已拆分", 11, "Split_Done"), Merge_Done(17, "已合并", 12,
        "Merge_Done"), Splited_Not_Settle(18, "已拆分未结转", 13, "Splited_Not_Settle"), Merge_Not_Settle(
        19, "已合并未结转", 14, "Merge_Not_Settle"), Exception_Order(-3, "异常订单", 15, "Exception_Order"), Stock_Lock(
        20, "已锁库存", 17, "Stock_Lock"), Risk_Appraise(21, "待风控评估", 18, "Risk_Appraise"), Risk_Pass(
        22, "风控通过", 19, "Risk_Pass");

    private int key;
    private String value;
    private int index;
    private String code;

    /**
     * 构造函数
     * 
     * @param key
     * @param value
     * @param index
     * @param code
     */
    private OrderStatus(int key, String value, int index, String code) {
      this.key = key;
      this.value = value;
      this.index = index;
      this.code = code;
    }

    /**
     * 根据索引查找key键
     * 
     * @param index
     * @return
     */
    public static int getKeyByIndex(int index) {
      for (OrderStatus o : OrderStatus.values()) {
        if (o.getIndex() == index) {
          return o.getKey();
        }
      }
      return 0;
    }

    /**
     * 根据key键查找value值
     * 
     * @param key
     * @return
     */
    public static String getValueByKey(int key) {
      for (OrderStatus o : OrderStatus.values()) {
        if (o.getKey() == key) {
          return o.getValue();
        }
      }
      return null;
    }

    /**
     * 根据key键查找code代码
     * 
     * @param key
     * @return
     */
    public static String getCodeByKey(int key) {
      for (OrderStatus o : OrderStatus.values()) {
        if (o.getKey() == key) {
          return o.getCode();
        }
      }
      return null;
    }

    public int getKey() {
      return key;
    }

    public void setKey(int key) {
      this.key = key;
    }

    public String getValue() {
      return value;
    }

    public void setValue(String value) {
      this.value = value;
    }

    public int getIndex() {
      return index;
    }

    public void setIndex(int index) {
      this.index = index;
    }

    public String getCode() {
      return code;
    }

    public void setCode(String code) {
      this.code = code;
    }
  }

  /**
   * 订单支付类型枚举类
   * 
   */
  public enum OrderPayMethod {
    Balance(1, "账户余额支付", 1, "Balance"), Coupon(2, "优惠券支付", 0, "Coupon"), Bank(3, "网银/信用卡支付", 2,
        "Bank"), Platform(4, "第三方支付平台支付", 3, "Platform"), Online(5, "在线支付", 4, "Online");

    private int key;
    private String value;
    private int index;
    private String code;

    /**
     * 构造函数
     * 
     * @param key
     * @param value
     * @param index
     * @param code
     */
    private OrderPayMethod(int key, String value, int index, String code) {
      this.key = key;
      this.value = value;
      this.index = index;
      this.code = code;
    }

    /**
     * 根据索引查找key键
     * 
     * @param index
     * @return
     */
    public static int getKeyByIndex(int index) {
      for (OrderPayMethod o : OrderPayMethod.values()) {
        if (o.getIndex() == index) {
          return o.getKey();
        }
      }
      return 0;
    }

    /**
     * 根据key键查找value值
     * 
     * @param key
     * @return
     */
    public static String getValueByKey(int key) {
      for (OrderPayMethod o : OrderPayMethod.values()) {
        if (o.getKey() == key) {
          return o.getValue();
        }
      }
      return null;
    }

    /**
     * 根据key键查找code代码
     * 
     * @param key
     * @return
     */
    public static String getCodeByKey(int key) {
      for (OrderPayMethod o : OrderPayMethod.values()) {
        if (o.getKey() == key) {
          return o.getCode();
        }
      }
      return null;
    }

    public int getKey() {
      return key;
    }

    public void setKey(int key) {
      this.key = key;
    }

    public String getValue() {
      return value;
    }

    public void setValue(String value) {
      this.value = value;
    }

    public int getIndex() {
      return index;
    }

    public void setIndex(int index) {
      this.index = index;
    }

    public String getCode() {
      return code;
    }

    public void setCode(String code) {
      this.code = code;
    }
  }

  /**
   * 订单是否被删除的枚举类
   * 
   */
  public enum OrderDisabled {
    Display(1, "显示", 0, "Display"), Delete(2, "删除", 1, "Delete"), Drop(3, "永久删除", 2, "Drop");
    private int key;
    private String value;
    private int index;
    private String code;

    /**
     * 构造函数
     * 
     * @param key
     * @param value
     * @param index
     * @param code
     */
    private OrderDisabled(int key, String value, int index, String code) {
      this.key = key;
      this.value = value;
      this.index = index;
      this.code = code;
    }

    /**
     * 根据索引查找key键
     * 
     * @param index
     * @return
     */
    public static int getKeyByIndex(int index) {
      for (OrderDisabled o : OrderDisabled.values()) {
        if (o.getIndex() == index) {
          return o.getKey();
        }
      }
      return 0;
    }

    /**
     * 根据key键查找value值
     * 
     * @param key
     * @return
     */
    public static String getValueByKey(int key) {
      for (OrderDisabled o : OrderDisabled.values()) {
        if (o.getKey() == key) {
          return o.getValue();
        }
      }
      return null;
    }

    /**
     * 根据key键查找code代码
     * 
     * @param key
     * @return
     */
    public static String getCodeByKey(int key) {
      for (OrderDisabled o : OrderDisabled.values()) {
        if (o.getKey() == key) {
          return o.getCode();
        }
      }
      return null;
    }

    public int getKey() {
      return key;
    }

    public void setKey(int key) {
      this.key = key;
    }

    public String getValue() {
      return value;
    }

    public void setValue(String value) {
      this.value = value;
    }

    public int getIndex() {
      return index;
    }

    public void setIndex(int index) {
      this.index = index;
    }

    public String getCode() {
      return code;
    }

    public void setCode(String code) {
      this.code = code;
    }
  }

  /**
   * 物流公司枚举类
   * 
   */
  public enum Logistics {
    SF(1, "顺丰", 0, "SF1"), EMS(2, "邮政", 1, "EMS1"), ;

    private int key;
    private String value;
    private int index;
    private String code;

    /**
     * 构造函数
     * 
     * @param key
     * @param value
     * @param index
     * @param code
     */
    private Logistics(int key, String value, int index, String code) {
      this.key = key;
      this.value = value;
      this.index = index;
      this.code = code;
    }

    /**
     * 根据索引查找key键
     * 
     * @param index
     * @return
     */
    public static int getKeyByIndex(int index) {
      for (Logistics o : Logistics.values()) {
        if (o.getIndex() == index) {
          return o.getKey();
        }
      }
      return 0;
    }

    /**
     * 根据key键查找value值
     * 
     * @param key
     * @return
     */
    public static String getValueByKey(int key) {
      for (Logistics o : Logistics.values()) {
        if (o.getKey() == key) {
          return o.getValue();
        }
      }
      return null;
    }

    /**
     * 根据key键查找code代码
     * 
     * @param key
     * @return
     */
    public static String getCodeByKey(int key) {
      for (Logistics o : Logistics.values()) {
        if (o.getKey() == key) {
          return o.getCode();
        }
      }
      return null;
    }

    public int getKey() {
      return key;
    }

    public void setKey(int key) {
      this.key = key;
    }

    public String getValue() {
      return value;
    }

    public void setValue(String value) {
      this.value = value;
    }

    public int getIndex() {
      return index;
    }

    public void setIndex(int index) {
      this.index = index;
    }

    public String getCode() {
      return code;
    }

    public void setCode(String code) {
      this.code = code;
    }
  }

  /**
   * 订单评价状态枚举类
   * 
   * @author zengming
   * @since 2013-8-1
   */
  public enum Assess_Status {
    None(1, "未评价", 0, "None"), Assess(2, "已评价", 1, "Assess"), Additional(3, "已追加评价", 2,
        "Additional");
    private int key;
    private String value;
    private int index;
    private String code;

    public int getKey() {
      return key;
    }

    public void setKey(int key) {
      this.key = key;
    }

    public String getValue() {
      return value;
    }

    public void setValue(String value) {
      this.value = value;
    }

    public int getIndex() {
      return index;
    }

    public void setIndex(int index) {
      this.index = index;
    }

    public String getCode() {
      return code;
    }

    public void setCode(String code) {
      this.code = code;
    }

    /**
     * 构造函数
     * 
     * @param key
     * @param value
     * @param index
     * @param code
     */
    private Assess_Status(int key, String value, int index, String code) {
      this.key = key;
      this.value = value;
      this.index = index;
      this.code = code;
    }

    /**
     * 根据索引查找key键
     * 
     * @param index
     * @return
     */
    public static int getKeyByIndex(int index) {
      for (Assess_Status o : Assess_Status.values()) {
        if (o.getIndex() == index) {
          return o.getKey();
        }
      }
      return 0;
    }

    /**
     * 根据key键查找value值
     * 
     * @param key
     * @return
     */
    public static String getValueByKey(int key) {
      for (Assess_Status o : Assess_Status.values()) {
        if (o.getKey() == key) {
          return o.getValue();
        }
      }
      return null;
    }

    /**
     * 根据key键查找code代码
     * 
     * @param key
     * @return
     */
    public static String getCodeByKey(int key) {
      for (Assess_Status o : Assess_Status.values()) {
        if (o.getKey() == key) {
          return o.getCode();
        }
      }
      return null;
    }
  }

}
