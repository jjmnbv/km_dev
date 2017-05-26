package com.pltfm.app.vobject;

import java.io.Serializable;
import java.util.Date;

/**
 * 邮箱验证信息类
 * 
 * @author cjm
 * @since 2013-7-23
 */
public class FailureTimeDefi implements Serializable {


  /**
   * 失效时间主键ID
   */
  private Integer failure_Time_Id;

  /**
   * 失效时间定义值
   */
  private Integer failure_Time_Value;

  /**
   * 失效类型1---手机随机码验证2---邮件验证
   */
  private Integer failure_Time_Type;

  /**
   * 创建日期
   */
  private Date create_Date;

  /**
   * 创建ID
   */
  private Integer createId;

  /**
   * 修改ID
   */
  private Integer modifieId;

  /**
   * 修改日期
   */
  private Date modify_Date;

  public Integer getFailure_Time_Id() {
    return failure_Time_Id;
  }

  public void setFailure_Time_Id(Integer failure_Time_Id) {
    this.failure_Time_Id = failure_Time_Id;
  }

  public Integer getFailure_Time_Value() {
    return failure_Time_Value;
  }

  public void setFailure_Time_Value(Integer failure_Time_Value) {
    this.failure_Time_Value = failure_Time_Value;
  }

  public Integer getFailure_Time_Type() {
    return failure_Time_Type;
  }

  public void setFailure_Time_Type(Integer failure_Time_Type) {
    this.failure_Time_Type = failure_Time_Type;
  }

  public Date getCreate_Date() {
    return create_Date;
  }

  public void setCreate_Date(Date create_Date) {
    this.create_Date = create_Date;
  }

  public Integer getCreateId() {
    return createId;
  }

  public void setCreateId(Integer createId) {
    this.createId = createId;
  }

  public Integer getModifieId() {
    return modifieId;
  }

  public void setModifieId(Integer modifieId) {
    this.modifieId = modifieId;
  }

  public Date getModify_Date() {
    return modify_Date;
  }

  public void setModify_Date(Date modify_Date) {
    this.modify_Date = modify_Date;
  }


}
