package com.kmzyc.promotion.util;

public class UserChannelContextHolder {

  private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();

  /**
   * 设置用户渠道号
   * 
   * @param channel
   */
  public static void setUserChannel(String channel) {
    contextHolder.set(channel);
  }

  /**
   * 获取用户渠道号
   * 
   * @return
   */
  public static String getUserChannel() {
    return contextHolder.get();
  }

  /**
   * 删除当前记录
   */
  public static void destroyUserChannel() {
    contextHolder.remove();
  }
}
