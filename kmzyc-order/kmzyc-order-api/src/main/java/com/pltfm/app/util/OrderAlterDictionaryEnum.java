package com.pltfm.app.util;

import com.pltfm.app.util.OrderDictionaryEnum.OrderPayMethod;

public class OrderAlterDictionaryEnum {

  /**
   * 订单退款方式类型的枚举类
   * 
   * @author Administrator
   * @author zengming
   * @since 2014-9-12
   */
  public enum OrderReturnMethod {
    Balance(1, "返还余额", 1, "Balance"), Coupon(2, "优惠券返还", 0, "Coupon"), Bank(3, "返还到网银/信用卡", 2,
        "Bank"), Platform(4, "返还到第三方支付平台", 3, "Platform"),/*删除预备金  Reserve(7, "返还到预备金", 6, "Reserve"),*/ AlipayToBalance(
        8, "支付宝支付退还到余额", 7, "AlipayToBalance"), AliPay(9, "支付宝", 9, "Aliay"), TenPay(10, "财付通", 10,
        "TenPay"), YeePay(11, "易宝", 11, "YeePay"), WeiXin(12, "微信", 12, "WeiXin");

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
    private OrderReturnMethod(int key, String value, int index, String code) {
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
     * 根据索引查找key键
     * 
     * @param index
     * @return
     */
    public static int getKeyByValue(String value) {
      for (OrderReturnMethod o : OrderReturnMethod.values()) {
        if (o.getValue().equals(value)) {
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
  }

  /**
   * 退换货状态的枚举类
   * 
   * @author zengming
   * @since 2013-8-1
   */
  public enum Propose_Status {
    Veto(-2, "已驳回", 0, "Veto"), Cancel(-1, "已取消", 1, "Cancel"), Audit(1, "已提交待审核", 2, "Audit"), Pass(
        2, "已通过待退货", 3, "Pass"), Returning(3, "已退货待取件", 4, "Returning"), Pickup(4, "已取件待质检", 5,
        "Pickup"), F_Backpay(51, "已通过待退款", 6, "F_Backpay"), F_Stockout(52, "已通过待发货", 7,
        "F_Stockout"), F_BackShop(53, "已驳回待原件返回", 8, "F_BackShop"), ExchangeToReturn(54, "换货转退货",
        9, "ExchangeToReturn"), Backpay(61, "已退款待确认", 10, "Backpay"), Stockout(62, "已发货待签收", 11,
        "Stockout"), BackShop(63, "已原件返回待签收", 12, "BackShop"), Returns_Done(7, "已完成", 13,
        "Returns_Done");
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
    private Propose_Status(int key, String value, int index, String code) {
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
      for (Propose_Status o : Propose_Status.values()) {
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
      for (Propose_Status o : Propose_Status.values()) {
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
      for (Propose_Status o : Propose_Status.values()) {
        if (o.getKey() == key) {
          return o.getCode();
        }
      }
      return null;
    }
  }

  /**
   * 退换货操作类型
   * 
   * @author Administrator
   * 
   */
  public enum OrderAlterOperateType {
    Cancel(-1, "取消", 0, "cancel"), Create(1, "申请", 1, "Create"), Audit(2, "审核", 2, "Audit"), Returning(
        3, "退货", 3, "Returning"), Pickup(4, "取件", 4, "Pickup"), Quality(5, "质检", 5, "Pickup"), PayBack(
        6, "退款", 6, "PayBack"), Stockout(7, "发货", 7, "Stockout"), BackShop(8, "返回原件", 8, "BackShop"), ExchangeToReturn(
        9, "换货转退货", 9, "ExchangeToReturn"), Finish(10, "完成", 10, "Finish");

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
    private OrderAlterOperateType(int key, String value, int index, String code) {
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
      for (OrderAlterOperateType o : OrderAlterOperateType.values()) {
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
      for (OrderAlterOperateType o : OrderAlterOperateType.values()) {
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
      for (OrderAlterOperateType o : OrderAlterOperateType.values()) {
        if (o.getKey() == key) {
          return o.getCode();
        }
      }
      return null;
    }
  }

  /**
   * 退换货类型的枚举类
   * 
   * @author zengming
   * @since 2013-8-1
   */
  public enum AlterTypes {
    Return(1, "退货", 0, "Return"), EXchange(2, "换货", 1, "EXchange"), Refund(3, "不退货退款", 2, "Refund"),Indemnity(4,"超时未发货赔付",3,"Indemnity");
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
    private AlterTypes(int key, String value, int index, String code) {
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
      for (AlterTypes o : AlterTypes.values()) {
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
      for (AlterTypes o : AlterTypes.values()) {
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
      for (AlterTypes o : AlterTypes.values()) {
        if (o.getKey() == key) {
          return o.getCode();
        }
      }
      return null;
    }
  }

  /**
   * 商品返回方式的枚举类
   * 
   * @author zengming
   * @since 2013-8-1
   */
  public enum BackTypes {
    Express(1, "快递至康美", 0, "Express"), Pickup(2, "送货至自提点", 1, "Pickup"), Door(3, "上门取件", 2, "Door"), ;
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
    private BackTypes(int key, String value, int index, String code) {
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
      for (BackTypes o : BackTypes.values()) {
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
      for (BackTypes o : BackTypes.values()) {
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
      for (BackTypes o : BackTypes.values()) {
        if (o.getKey() == key) {
          return o.getCode();
        }
      }
      return null;
    }
  }

  /**
   * 退换货单是/否枚举类
   * 
   * @author zengming
   * @since 2013-8-1
   */
  public enum Whether {
    YES(1, "是", 0, "YES"), NO(-1, "否", 1, "NO");

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
    private Whether(int key, String value, int index, String code) {
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
      for (Whether o : Whether.values()) {
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
      for (Whether o : Whether.values()) {
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
      for (Whether o : Whether.values()) {
        if (o.getKey() == key) {
          return o.getCode();
        }
      }
      return null;
    }
  }

}
