package com.pltfm.app.entities;

import java.io.Serializable;
import java.util.Date;

/**
 * 定时任务实体
 * 
 * @author Administrator
 * 
 */
public class QuartzInfo implements Serializable {
  /**
	 * 
	 */
  private static final long serialVersionUID = 1L;
  /**
   * 任务ID
   */
  private Long taskId;
  /**
   * 系统编号
   */
  private String sysCode;
  /**
   * 触发名称
   */
  private String triggerName;
  /**
   * 触发组
   */
  private String triggerGroup;
  /**
   * 触发表达式
   */
  private String cronExpression;
  /**
   * 任务名称
   */
  private String jobName;
  /**
   * 任务组
   */
  private String jobGroup;
  /**
   * 任务类
   */
  private String jobClass;
  /**
   * 任务状态0:启用，1停用
   */
  private String isVolatile;
  /**
   * 任务描述
   */
  private String jobDescription;
  /**
   * 创建时间
   */
  private Date createDate;
  /**
   * 更新时间
   */
  private Date updateDate;

  public Long getTaskId() {
    return taskId;
  }

  public void setTaskId(Long taskId) {
    this.taskId = taskId;
  }

  public String getSysCode() {
    return sysCode;
  }

  public void setSysCode(String sysCode) {
    this.sysCode = sysCode;
  }

  public String getTriggerName() {
    return triggerName;
  }

  public void setTriggerName(String triggerName) {
    this.triggerName = triggerName;
  }

  public String getTriggerGroup() {
    return triggerGroup;
  }

  public void setTriggerGroup(String triggerGroup) {
    this.triggerGroup = triggerGroup;
  }

  public String getCronExpression() {
    return cronExpression;
  }

  public void setCronExpression(String cronExpression) {
    this.cronExpression = cronExpression;
  }

  public String getJobName() {
    return jobName;
  }

  public void setJobName(String jobName) {
    this.jobName = jobName;
  }

  public String getJobGroup() {
    return jobGroup;
  }

  public void setJobGroup(String jobGroup) {
    this.jobGroup = jobGroup;
  }

  public String getJobClass() {
    return jobClass;
  }

  public void setJobClass(String jobClass) {
    this.jobClass = jobClass;
  }

  public String getIsVolatile() {
    return isVolatile;
  }

  public void setIsVolatile(String isVolatile) {
    this.isVolatile = isVolatile;
  }

  public String getJobDescription() {
    return jobDescription;
  }

  public void setJobDescription(String jobDescription) {
    this.jobDescription = jobDescription;
  }

  public Date getCreateDate() {
    return createDate;
  }

  public void setCreateDate(Date createDate) {
    this.createDate = createDate;
  }

  public Date getUpdateDate() {
    return updateDate;
  }

  public void setUpdateDate(Date updateDate) {
    this.updateDate = updateDate;
  }
}
