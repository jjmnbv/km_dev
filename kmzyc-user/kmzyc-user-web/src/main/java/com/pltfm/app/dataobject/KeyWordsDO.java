package com.pltfm.app.dataobject;

import java.io.Serializable;
import java.util.Date;

/**
 * 数据对象
 * 
 * @since 2013-10-24
 */
public class KeyWordsDO implements Serializable {

  private static final long serialVersionUID = 138259692642601529L;

  /**
   * column KEY_WORDS.KEY_WORDS_ID
   */
  private Integer keyWordsId;

  /**
   * column KEY_WORDS.KEY_WORDS
   */
  private String keyWords;

  /**
   * column KEY_WORDS.REP_WORDS
   */
  private String repWords;

  /**
   * column KEY_WORDS.STATUS
   */
  private Short status;

  /**
   * column KEY_WORDS.CREATE_DATE
   */
  private Date createDate;

  /**
   * column KEY_WORDS.CREATED_ID
   */
  private Integer createdId;

  /**
   * column KEY_WORDS.MODIFY_DATE
   */
  private Date modifyDate;

  /**
   * column KEY_WORDS.MODIFIE_ID
   */
  private Integer modifieId;

  /**
   * column KEY_WORDS.WORDS_TYPE
   */
  private Short wordsType;

  public KeyWordsDO() {
    super();
  }

  public KeyWordsDO(Integer keyWordsId, String keyWords, String repWords, Short status,
      Date createDate, Integer createdId, Date modifyDate, Integer modifieId, Short wordsType) {
    this.keyWordsId = keyWordsId;
    this.keyWords = keyWords;
    this.repWords = repWords;
    this.status = status;
    this.createDate = createDate;
    this.createdId = createdId;
    this.modifyDate = modifyDate;
    this.modifieId = modifieId;
    this.wordsType = wordsType;
  }

  /**
   * getter for Column KEY_WORDS.KEY_WORDS_ID
   */
  public Integer getKeyWordsId() {
    return keyWordsId;
  }

  /**
   * setter for Column KEY_WORDS.KEY_WORDS_ID
   * 
   * @param keyWordsId
   */
  public void setKeyWordsId(Integer keyWordsId) {
    this.keyWordsId = keyWordsId;
  }

  /**
   * getter for Column KEY_WORDS.KEY_WORDS
   */
  public String getKeyWords() {
    return keyWords;
  }

  /**
   * setter for Column KEY_WORDS.KEY_WORDS
   * 
   * @param keyWords
   */
  public void setKeyWords(String keyWords) {
    this.keyWords = keyWords;
  }

  /**
   * getter for Column KEY_WORDS.REP_WORDS
   */
  public String getRepWords() {
    return repWords;
  }

  /**
   * setter for Column KEY_WORDS.REP_WORDS
   * 
   * @param repWords
   */
  public void setRepWords(String repWords) {
    this.repWords = repWords;
  }

  /**
   * getter for Column KEY_WORDS.STATUS
   */
  public Short getStatus() {
    return status;
  }

  /**
   * setter for Column KEY_WORDS.STATUS
   * 
   * @param status
   */
  public void setStatus(Short status) {
    this.status = status;
  }

  /**
   * getter for Column KEY_WORDS.CREATE_DATE
   */
  public Date getCreateDate() {
    return createDate;
  }

  /**
   * setter for Column KEY_WORDS.CREATE_DATE
   * 
   * @param createDate
   */
  public void setCreateDate(Date createDate) {
    this.createDate = createDate;
  }

  /**
   * getter for Column KEY_WORDS.CREATED_ID
   */
  public Integer getCreatedId() {
    return createdId;
  }

  /**
   * setter for Column KEY_WORDS.CREATED_ID
   * 
   * @param createdId
   */
  public void setCreatedId(Integer createdId) {
    this.createdId = createdId;
  }

  /**
   * getter for Column KEY_WORDS.MODIFY_DATE
   */
  public Date getModifyDate() {
    return modifyDate;
  }

  /**
   * setter for Column KEY_WORDS.MODIFY_DATE
   * 
   * @param modifyDate
   */
  public void setModifyDate(Date modifyDate) {
    this.modifyDate = modifyDate;
  }

  /**
   * getter for Column KEY_WORDS.MODIFIE_ID
   */
  public Integer getModifieId() {
    return modifieId;
  }

  /**
   * setter for Column KEY_WORDS.MODIFIE_ID
   * 
   * @param modifieId
   */
  public void setModifieId(Integer modifieId) {
    this.modifieId = modifieId;
  }

  /**
   * getter for Column KEY_WORDS.WORDS_TYPE
   */
  public Short getWordsType() {
    return wordsType;
  }

  /**
   * setter for Column KEY_WORDS.WORDS_TYPE
   * 
   * @param wordsType
   */
  public void setWordsType(Short wordsType) {
    this.wordsType = wordsType;
  }

}
