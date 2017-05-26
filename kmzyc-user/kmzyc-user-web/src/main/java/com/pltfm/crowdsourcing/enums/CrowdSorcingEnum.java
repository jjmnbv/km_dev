package com.pltfm.crowdsourcing.enums;

/**
 * 
 * @ClassName: CrowdSorcingEnum
 * @Description: 众包枚举类
 * @author weijl
 * @date 2016年3月15日 下午3:02:25
 * @version 1.0
 */
public enum CrowdSorcingEnum {
  bag_man_type_part_time(1, "兼职"),

  bag_man_type_all_time(0, "全职"),

  bag_man_status_valid(1, "有效"),

  bag_man_status_invalid(0, "无效");

  private int key;

  private String description;

  public int getKey() {
    return key;
  }



  public String getDescription() {
    return description;
  }



  CrowdSorcingEnum(int key, String description) {
    this.key = key;
    this.description = description;
  }

}
