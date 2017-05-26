package com.pltfm.app.vobject;

import java.io.Serializable;

public class AlterStatusVO implements Serializable {

  private static final long serialVersionUID = -572706004422037263L;

  private String operator;
  private String orderAlterCode;
  private Long status;
  private String comment;
  private String code;
  private Long no;

  public String getOperator() {
    return operator;
  }

  public void setOperator(String operator) {
    this.operator = operator;
  }

  public String getOrderAlterCode() {
    return orderAlterCode;
  }

  public void setOrderAlterCode(String orderAlterCode) {
    this.orderAlterCode = orderAlterCode;
  }

  public Long getStatus() {
    return status;
  }

  public void setStatus(Long status) {
    this.status = status;
  }

  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public Long getNo() {
    return no;
  }

  public void setNo(Long no) {
    this.no = no;
  }

}
