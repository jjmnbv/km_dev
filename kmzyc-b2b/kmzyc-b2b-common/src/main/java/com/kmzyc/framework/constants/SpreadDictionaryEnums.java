package com.kmzyc.framework.constants;


/**
 * 推广返利枚举类
 * 
 * @author weijunlong
 * 
 */
public class SpreadDictionaryEnums {

  /**
   * 推广效果订单状态:key值对应数据库表存值
   * 
   * @author weijunlong
   * 
   */
  public enum SpreadEffectStatus {
    ORDER_CANCELED(0, "订单取消", 1, "order_canceled"), NOT_PAYED(1, "未支付", 2, "not_payed"), ALREADY_PAYED(
        2, "已支付", 3, "already_payed"), FINISHED(3, "已完成", 4, "finished"), SETTED(4, "已结算", 5,
        "setted"), SETT_FORBIDEN(5, "禁止结算", 6, "sett_forbiden"), ALREADY_DISTRI(6, "已配送", 7,
        "already_distri");
    private int key;
    private String value;
    private int index;
    private String code;

    private SpreadEffectStatus(int key, String value, int index, String code) {
      this.key = key;
      this.value = value;
      this.index = index;
      this.code = code;
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
}
