package com.kmzyc.framework.constants;

/**
 * 短信发送内容类型
 * 
 * @author luoyi
 * @createDate 2013/10/17
 * 
 */
public enum LastYearSpendMinEnum {
  LEVELONE(1, "一级会员", "0.00"), LEVELTWO(2, "二级会员", "0.00"), LEVELTHREE(3, "三级会员", "0.00"), LEVELFOUR(
      4, "四级会员", "0.00"), LEVELFIVE(5, "五级会员", "500.00"), LEVELSIX(6, "六级会员", "1000.00"), LEVELSEVEN(
      7, "七级会员", "2000.00"), LEVELEIGHT(8, "八级会员", "5000.00"), LEVELNINE(9, "九级会员", "10000.00"), LEVELTEN(
      10, "十级会员", "50000.00");
  private int index;
  private String level;
  private String spend = null;

  LastYearSpendMinEnum(int index, String level, String spend) {
    this.index = index;
    this.level = level;
    this.spend = spend;
  }

  public String getLevel() {
    return level;
  }

  public void setLevel(String level) {
    this.level = level;
  }

  public String getSpend() {
    return spend;
  }

  public void setSpend(String spend) {
    this.spend = spend;
  }

  public int getIndex() {
    return index;
  }

  public void setIndex(int index) {
    this.index = index;
  }

  /**
   * 通过下标获取枚举
   * 
   * @param index
   * @return
   */
  public static String getSpendByLevel(String level) {
    String spendMin = "0.00";
    for (LastYearSpendMinEnum mt : LastYearSpendMinEnum.values()) {
      if (mt.level.equals(level)) {
        spendMin = mt.getSpend();
        break;
      }
    }
    return spendMin;
  }
}
