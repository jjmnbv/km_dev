package com.kmzyc.express.util.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * @author hekai
 * @since JDK1.6
 * @history 2015-12-11 快递信息订阅记录常量类
 */
public class ExpressSubConstants {

  // 请求的信息编码
  public static final String POST_CHARSET = "UTF-8";

  // 快递100，返回订阅重复的编号
  public static final String DUPLICATE_RETURN_CODE = "501";


  // 是否废单标志， 1：表示有用单据， 2表示废单，不向快递100推送
  public static final Integer FLAG_USEABLE = 1;
  public static final Integer FLAG_USELESS = 2;

  // 允许收到abort推送的最大次数
  public static final Integer MAX_ABORT_NUM = 4;

  // 自动重新订阅延迟时间(收到abort推送后多久才开始重新订阅毫秒数)
  public static final Integer RE_SUB_DELAY_MS = 30 * 60 * 1000;

  /**
   * 跟踪状态
   * 
   * @author hekai
   * 
   */
  public static enum TrackStatus {

    NOSUB("1", "nosub", "未订阅"), POLLING("2", "polling", "监控中"), ABORT("3", "abort", "中止"), UPDATEALL(
        "4", "updateall", "重新推送"), SHUTDOWN("5", "shutdown", "结束"), SUB_FAIL("6", "subfail", "订阅失败");

    /** 键 */
    private String key;
    /** 编码 */
    private String code;

    /** 整型键值 */
    private Integer integerKey;

    /** 值 */
    private String value;

    private TrackStatus(String key, String code, String value) {
      this.key = key;
      this.code = code;
      this.integerKey = Integer.valueOf(key);
      this.value = value;
    }

    /**
     * 用于快速查询的map映射
     */
    private static final Map<String, TrackStatus> trackStatusMap =
        new HashMap<String, TrackStatus>(10);

    static {
      for (TrackStatus s : TrackStatus.values()) {
        trackStatusMap.put(s.getKey(), s);
      }
    }

    public static boolean isContains(String key) {
      return trackStatusMap.containsKey(String.valueOf(key));
    }

    public static TrackStatus getByKey(String key) {
      if (isContains(key)) {
        return trackStatusMap.get(key);
      } else {
        return null;
      }
    }

    public static TrackStatus getByKey(Integer key) {
      return getByKey(String.valueOf(key));
    }

    public static TrackStatus getByCode(String code) {
      TrackStatus SubTrackStatus = null;
      for (TrackStatus statu : trackStatusMap.values()) {
        if (statu.getCode().equals(code)) {
          SubTrackStatus = statu;
          break;
        }
      }
      return SubTrackStatus;
    }

    public Integer getIntegerKey() {
      return this.integerKey;
    }

    public String getKey() {
      return this.key;
    }

    public String getCode() {
      return code;
    }

    public String getName() {
      return this.name();
    }

    public String getValue() {
      return this.value;
    }
  }

  /**
   * 快递状态枚举
   * 
   * @author hekai
   * 
   */
  public static enum ExpressStatus {
    ON_WAY("0", "在途"), COLLECTED("1", "揽件"), PUZZLED("2", "疑难"), SIGN_IN("3", "签收"), SIGN_OUT("4",
        "退签"), SENDING("5", "派件"), RETURND("6", "退回"), EXCHANGE("7", "转单");

    /** 键 */
    private String key;

    /** 整型键值 */
    private Integer integerKey;

    /** 值 */
    private String value;

    private ExpressStatus(String key, String value) {
      this.key = key;
      this.integerKey = Integer.valueOf(key);
      this.value = value;
    }

    /**
     * 用于快速查询的map映射
     */
    private static final Map<String, ExpressStatus> expressStatusMap =
        new HashMap<String, ExpressStatus>(10);

    static {
      for (ExpressStatus s : ExpressStatus.values()) {
        expressStatusMap.put(s.getKey(), s);
      }
    }

    public static boolean isContains(String key) {
      return expressStatusMap.containsKey(String.valueOf(key));
    }

    public static String getValueByKey(String key) {
      String value = "";
      if (isContains(key)) {
        return expressStatusMap.get(key).getValue();
      }
      return value;
    }

    public static String getValueByKey(Integer key) {
      return getValueByKey(String.valueOf(key));
    }

    public Integer getIntegerKey() {
      return this.integerKey;
    }

    public String getKey() {
      return this.key;
    }

    public String getName() {
      return this.name();
    }

    public String getValue() {
      return this.value;
    }
  }

  /**
   * 推送状态
   * 
   * @author hekai
   * 
   */
  public static enum PushStatus {
    PUSH_SUCESS("1", "成功"), PUSH_FAIL("2", "失败");

    /** 键 */
    private String key;

    /** 整型键值 */
    private Integer integerKey;

    /** 值 */
    private String value;

    private PushStatus(String key, String value) {
      this.key = key;
      this.integerKey = Integer.valueOf(key);
      this.value = value;
    }

    /**
     * 用于快速查询的map映射
     */
    private static final Map<String, PushStatus> pushStatusMap =
        new HashMap<String, PushStatus>(10);

    static {
      for (PushStatus s : PushStatus.values()) {
        pushStatusMap.put(s.getKey(), s);
      }
    }

    public static boolean isContains(String key) {
      return pushStatusMap.containsKey(String.valueOf(key));
    }

    public static String getValueByKey(String key) {
      String value = "";
      if (isContains(key)) {
        return pushStatusMap.get(key).getValue();
      }
      return value;
    }

    public static String getValueByKey(Integer key) {
      return getValueByKey(String.valueOf(key));
    }

    public Integer getIntegerKey() {
      return this.integerKey;
    }

    public String getKey() {
      return this.key;
    }

    public String getName() {
      return this.name();
    }

    public String getValue() {
      return this.value;
    }
  }
}
