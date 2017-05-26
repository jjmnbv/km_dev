package com.pltfm.app.vobject;

import java.io.Serializable;
import java.util.Date;

/**
 * 账户信息类
 * 
 * @author cjm
 * @since 2013-7-10
 */
public class KeyWords implements Serializable {
  /**
   * 过滤关键字ID
   */
  private Integer keyWordsId;
  /**
   * 关键字
   */
  private String keyWords;
  /**
   * 关键字替换
   */
  private String repWords;

  /**
   * 状态 1---有效 0 ---失效
   */
  private Integer status;

  /**
   * 创建日期
   */
  private Date createDate;

  /**
   * 创建人 CREATED_ID
   */
  private Integer createdId;
  /**
   * 修改人 MODIFIE_ID
   */
  private Integer modifieId;
  /**
   * 修改日期 MODIFY_DATE
   */
  private Date modifyDate;

  /**
   * 关键字类型 1---搜索 2---评价 3--评价 WORDS_TYPE
   */
  private Integer wordsType;

  public Integer getKeyWordsId() {
    return keyWordsId;
  }

  public void setKeyWordsId(Integer keyWordsId) {
    this.keyWordsId = keyWordsId;
  }

  public String getKeyWords() {
    return keyWords;
  }

  public void setKeyWords(String keyWords) {
    this.keyWords = keyWords;
  }

  public String getRepWords() {
    return repWords;
  }

  public void setRepWords(String repWords) {
    this.repWords = repWords;
  }

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public Date getCreateDate() {
    return createDate;
  }

  public void setCreateDate(Date createDate) {
    this.createDate = createDate;
  }

  public Integer getCreatedId() {
    return createdId;
  }

  public void setCreatedId(Integer createdId) {
    this.createdId = createdId;
  }

  public Integer getModifieId() {
    return modifieId;
  }

  public void setModifieId(Integer modifieId) {
    this.modifieId = modifieId;
  }

  public Date getModifyDate() {
    return modifyDate;
  }

  public void setModifyDate(Date modifyDate) {
    this.modifyDate = modifyDate;
  }

  public Integer getWordsType() {
    return wordsType;
  }

  public void setWordsType(Integer wordsType) {
    this.wordsType = wordsType;
  }



}
