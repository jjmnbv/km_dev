package com.pltfm.app.vobject;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Params implements Serializable {
  private String key;
  private String regx;// 表达式
  private String value;

  public Params(String key, String regx, String value) {
    this.key = key;
    this.regx = regx;
    this.value = value;
  }

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public String getRegx() {
    return regx;
  }

  public void setRegx(String regx) {
    this.regx = regx;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  @Override
  public String toString() {
    return new StringBuffer().append("{key:").append(key).append(",regx:").append(regx)
        .append(",value:").append(value).append('}').toString();
  }
}
