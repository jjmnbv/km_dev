package com.kmzyc.b2b.model;

import java.io.Serializable;
import java.util.Date;

public class BnesMessageCenterResponse implements Serializable {

  /**
     *
     */
  private static final long serialVersionUID = -6008777822609697115L;

  /**
   * 内容
   * 
   * */
  private String content;

  /**
   * 标题
   * 
   * */
  private String title;

  /**
   * 
   * 发布日期
   * */
  private Date releaseDate;

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Date getReleaseDate() {
    return releaseDate;
  }

  public void setReleaseDate(Date releaseDate) {
    this.releaseDate = releaseDate;
  }

}
