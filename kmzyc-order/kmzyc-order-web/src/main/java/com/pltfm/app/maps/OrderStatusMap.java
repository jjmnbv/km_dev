package com.pltfm.app.maps;

import java.util.HashMap;
import java.util.Map;

public class OrderStatusMap extends BaseMap {

  public static final String name = "Order_Status";

  public static final String ALREADY_CANCEL = "Already_Cancel";
  public static final String NOT_PAY = "Not_Pay";
  public static final String PAY_DONE = "Pay_Done";
  public static final String SETTLE_DONE = "Settle_Done";
  public static final String ALREADY_STOCK = "Already_Stock";
  public static final String ALREADY_SHIP = "Already_Ship";
  public static final String ALREADY_DONE = "Already_Done";
  public static final String ALREADY_ASSESS = "Already_Assess";
  public static final String SHIP_FAIL = "Ship_Fail";
  public static final String SPLIT_DONE = "Split_Done";
  public static final String MERGE_DONE = "Merge_Done";
  public static final String SPLITED_NOT_SETTLE = "Splited_Not_Settle";
  public static final String MERGE_NOT_SETTLE = "Merge_Not_Settle";

  @SuppressWarnings("unused")
  private static Map<String, Long> map = new HashMap<String, Long>();

  public enum Order_Status {
    Cancel_Done, Not_Pay, Pay_Done, Settle_Done, Stock_Done, Ship_Done, Order_Done, Assess_Done, Ship_Fail, Settle_Not_Stock, Split_Done, Merge_Done, Splited_Not_Settle, Merge_Not_Settle, ;

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
    private Order_Status() {}

    private Order_Status(int key, String value, int index, String code) {
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
      for (Order_Status o : Order_Status.values()) {
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
      for (Order_Status o : Order_Status.values()) {
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
      for (Order_Status o : Order_Status.values()) {
        if (o.getKey() == key) {
          return o.getCode();
        }
      }
      return null;
    }

  }
}
