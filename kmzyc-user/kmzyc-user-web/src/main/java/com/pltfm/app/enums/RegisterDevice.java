package com.pltfm.app.enums;

/**
 * 功能：终端来源
 *
 * @author Zhoujiwei
 * @since 2016/4/23 11:53
 */
public enum RegisterDevice {

  PC(1, "PC"),

  WAP(2, "WAP"),

  APP(3, "APP");

  private int type;

  private String title;

  RegisterDevice(int type, String title) {
    this.type = type;
    this.title = title;
  }

  public int getType() {
    return type;
  }


  public String getTitle() {
    return title;
  }


  @Override
  public String toString() {
    return "registerDevice{" + "type=" + type + ", title='" + title + '\'' + '}';
  }
}
