package com.kmzyc.b2b.model;

import java.util.Date;

/**
 * 移动用户数据
 * 
 * @author hl
 * 
 */
public class RefData {

  /**
   * id
   */
  private Long id;

  /**
   * 时间
   */
  private Date show_time;

  /**
   * 所剩人数
   */
  private Long left_count;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Date getShow_time() {
    return show_time;
  }

  public void setShow_time(Date show_time) {
    this.show_time = show_time;
  }

  public Long getLeft_count() {
    return left_count;
  }

  public void setLeft_count(Long left_count) {
    this.left_count = left_count;
  }

}
