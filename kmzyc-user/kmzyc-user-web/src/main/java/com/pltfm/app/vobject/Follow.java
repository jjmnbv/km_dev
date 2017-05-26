package com.pltfm.app.vobject;

import java.io.Serializable;
import java.util.Date;

/**
 * 关注信息
 * 
 * @author cjm
 * @since 2013-7-31
 */
public class Follow implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  /**
   * 关注ID
   */
  private Integer followId;

  /**
   * 登录ID
   */
  private Integer n_LoginId;

  /**
   * 关注人姓名
   */
  private String name;

  /**
   * 关注内容
   */
  private String followContent;

  /**
   * 关注URL
   */
  private String followURL;

  /**
   * 关注日期
   */
  private Date followDate;

  public Integer getFollowId() {
    return followId;
  }

  public void setFollowId(Integer followId) {
    this.followId = followId;
  }

  public String getFollowContent() {
    return followContent;
  }

  public void setFollowContent(String followContent) {
    this.followContent = followContent;
  }

  public String getFollowURL() {
    return followURL;
  }

  public void setFollowURL(String followURL) {
    this.followURL = followURL;
  }

  public Date getFollowDate() {
    return followDate;
  }

  public void setFollowDate(Date followDate) {
    this.followDate = followDate;
  }

  public Integer getN_LoginId() {
    return n_LoginId;
  }

  public void setN_LoginId(Integer nLoginId) {
    n_LoginId = nLoginId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

}
