package com.kmzyc.express.util.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * @author hekai
 * @since JDK1.6
 * @history 2015-12-8 快递信息同步日志常量类
 */
public class ExpressLogConstants {

  /**
   * 处理状态 1：成功 2：失败
   * 
   * @author hekai
   * 
   */
  public static enum LogStatus {
    SUCESS("1", "成功"), FAIL("2", "失败");

    /** 键 */
    private String key;

    /** 整型键值 */
    private Integer integerKey;

    /** 值 */
    private String value;

    private LogStatus(String key, String value) {
      this.key = key;
      this.integerKey = Integer.valueOf(key);
      this.value = value;
    }

    /**
     * 用于快速查询的map映射
     */
    private static final Map<String, LogStatus> statusMap = new HashMap<String, LogStatus>(10);

    static {
      for (LogStatus s : LogStatus.values()) {
        statusMap.put(s.getKey(), s);
      }
    }

    public static boolean isContains(String key) {
      return statusMap.containsKey(String.valueOf(key));
    }

    public static String getValueByKey(String key) {
      String value = "";
      if (isContains(key)) {
        return statusMap.get(key).getValue();
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
   * 日志节点枚举
   * 
   * @author hekai
   * 
   */
  public static enum SyncNode {
    SynReceive("1", "接收物流跟踪信息"), SynSubscription("2", "提交订阅"), SyncOrder("3", "接收物流单请求");

    /** 键 */
    private String key;

    /** 整型键值 */
    private Integer integerKey;

    /** 值 */
    private String value;

    private SyncNode(String key, String value) {
      this.key = key;
      this.integerKey = Integer.valueOf(key);
      this.value = value;
    }

    /**
     * 用于快速查询的map映射
     */
    private static final Map<String, SyncNode> nodesMap = new HashMap<String, SyncNode>(10);

    static {
      for (SyncNode s : SyncNode.values()) {
        nodesMap.put(s.getKey(), s);
      }
    }

    public static boolean isContains(String key) {
      return nodesMap.containsKey(String.valueOf(key));
    }

    public static String getValueByKey(String key) {
      String value = "";
      if (isContains(key)) {
        return nodesMap.get(key).getValue();
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
