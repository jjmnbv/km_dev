package com.pltfm.app.util;

import java.io.Serializable;

public enum BusinessTypeEnum implements Serializable {


  All_Business(1, "所有商家"), Only_Self(2, "仅自营代销 "), All_Assigned(3, "所有入驻商品 "), Specified_Assigned(4,
      "指定入驻商品 ");

  // 1:所有商家 2:仅自营代销 3:所有入驻商品4:指定入驻商品
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





  BusinessTypeEnum(int wordsType, String titles) {
    this.wordsType = wordsType;
    this.titles = titles;
  };



}
