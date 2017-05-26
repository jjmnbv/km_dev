package com.pltfm.app.vobject;

import java.io.Serializable;
import java.util.Date;

public class SafeQuestion implements Serializable {

  /** 安全问题主键 **/
  private Integer n_safe_question_id;

  /** 问题名称 **/
  private String question_name;

  /** 创建日期 **/
  private Date d_create_date;

  /** 创建人 **/
  private Integer n_created;

  /** 修改日期 **/
  private Date d_modify_date;

  /** 修改人 */
  private Integer n_modified;

  public Integer getN_safe_question_id() {
    return n_safe_question_id;
  }

  public void setN_safe_question_id(Integer n_safe_question_id) {
    this.n_safe_question_id = n_safe_question_id;
  }

  public String getQuestion_name() {
    return question_name;
  }

  public void setQuestion_name(String question_name) {
    this.question_name = question_name;
  }

  public Date getD_create_date() {
    return d_create_date;
  }

  public void setD_create_date(Date d_create_date) {
    this.d_create_date = d_create_date;
  }

  public Integer getN_created() {
    return n_created;
  }

  public void setN_created(Integer n_created) {
    this.n_created = n_created;
  }

  public Date getD_modify_date() {
    return d_modify_date;
  }

  public void setD_modify_date(Date d_modify_date) {
    this.d_modify_date = d_modify_date;
  }

  public Integer getN_modified() {
    return n_modified;
  }

  public void setN_modified(Integer n_modified) {
    this.n_modified = n_modified;
  }

  public SafeQuestion() {
    super();
  }

}
