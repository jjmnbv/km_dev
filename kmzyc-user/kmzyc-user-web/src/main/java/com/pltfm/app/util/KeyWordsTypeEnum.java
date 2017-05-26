package com.pltfm.app.util;

import java.io.Serializable;

public enum KeyWordsTypeEnum implements Serializable {

  user_searchKeys((short) 1), user_adviceKeys((short) 2), user_EvaKeys((short) 3);

  private short wordsType;



  KeyWordsTypeEnum(short wordsType) {
    this.wordsType = wordsType;
  };

  public short getWordsType() {
    return wordsType;
  }


}
