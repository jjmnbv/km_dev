package com.pltfm.app.util;

import com.pltfm.app.util.OrderDictionaryEnum.PlatformCode;

/**
 * 订单所调用的外部接口定义的操作类型的枚举类
 * 
 * @author zengming
 * @version 1.0
 * @since 2014-8-29
 */
public class OrderInterFaceOperateTypeEnum {

  /**
   * 库存接口操作类型
   * 
   * @author zengming
   * 
   */
  public enum StockHandleType {
    batchLock(1, "批量锁库存", 0, "batchLock"), batchUnLock(2, "批量解锁库存", 1, "batchUnLock");

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
    private StockHandleType(int key, String value, int index, String code) {
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
      for (PlatformCode o : PlatformCode.values()) {
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
      for (PlatformCode o : PlatformCode.values()) {
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
      for (PlatformCode o : PlatformCode.values()) {
        if (o.getKey() == key) {
          return o.getCode();
        }
      }
      return null;
    }
  }

  /**
   * 余额接口操作类型
   * 
   * @author zengming
   * 
   */
  public enum BalanceHandleType {
    userBalance(1, "使用余额", 0, "userBalance"), returnBalance(2, "返还余额", 1, "returnBalance");

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
    private BalanceHandleType(int key, String value, int index, String code) {
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
      for (PlatformCode o : PlatformCode.values()) {
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
      for (PlatformCode o : PlatformCode.values()) {
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
      for (PlatformCode o : PlatformCode.values()) {
        if (o.getKey() == key) {
          return o.getCode();
        }
      }
      return null;
    }
  }

  /**
   * 优惠券接口操作类型
   * 
   * @author zengming
   */
  public enum CouponHandleType {
    freezeCoupon(1, "冻结优惠券", 0, "freezeCoupon"), useCoupon(2, "使用优惠券", 1, "useCoupon"), returnCoupon(
        3, "返还优惠券", 2, "returnCoupon"), cancelCoupon(4, "作废优惠券", 2, "cancelCoupon");

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
    private CouponHandleType(int key, String value, int index, String code) {
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
      for (PlatformCode o : PlatformCode.values()) {
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
      for (PlatformCode o : PlatformCode.values()) {
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
      for (PlatformCode o : PlatformCode.values()) {
        if (o.getKey() == key) {
          return o.getCode();
        }
      }
      return null;
    }
  }

  /**
   * 准备金接口操作类型
   * 
   * @author zengming
   * 
   */
  public enum ReserveHandleType {
    userReserve(1, "使用准备金", 0, "userReserve"), returnReserve(2, "返还准备金", 1, "returnReserve");

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
    private ReserveHandleType(int key, String value, int index, String code) {
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
      for (PlatformCode o : PlatformCode.values()) {
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
      for (PlatformCode o : PlatformCode.values()) {
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
      for (PlatformCode o : PlatformCode.values()) {
        if (o.getKey() == key) {
          return o.getCode();
        }
      }
      return null;
    }
  }
}
