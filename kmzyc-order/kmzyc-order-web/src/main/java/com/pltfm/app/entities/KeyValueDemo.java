package com.pltfm.app.entities;

import java.io.Serializable;

public class KeyValueDemo implements Serializable {
  private static final long serialVersionUID = 1L;

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

  public KeyValueDemo(int key, String value, int index, String code) {
    super();
    this.key = key;
    this.value = value;
    this.index = index;
    this.code = code;
  }

}
