package com.pltfm.app.util;

import java.io.Serializable;

public enum OrderTypeEnum implements Serializable {


  Have_pay(2, "已支付"), Have_completed(6, "已完成");

  // user_searchKeys((short)1),
  // user_adviceKeys((short)2),
  // user_EvaKeys((short)3);

  private int wordsType;
  private String titles;



  public int getWordsType() {
    return wordsType;
  }





  public String getTitles() {
    return titles;
  }






  OrderTypeEnum(int wordsType, String titles) {
    this.wordsType = wordsType;
    this.titles = titles;
  };



}
