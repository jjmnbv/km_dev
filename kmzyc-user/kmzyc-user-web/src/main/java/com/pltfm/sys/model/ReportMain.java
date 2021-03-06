package com.pltfm.sys.model;

import java.io.Serializable;
import java.util.Date;

public class ReportMain implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = -2555587649021623532L;
  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column
   * report_main.report_id
   * 
   * @ibatorgenerated Thu Jan 28 16:03:32 CST 2010
   */
  private Integer reportId;
  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column
   * report_main.report_name
   * 
   * @ibatorgenerated Thu Jan 28 16:03:32 CST 2010
   */
  private String reportName;
  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column
   * report_main.report_no
   * 
   * @ibatorgenerated Thu Jan 28 16:03:32 CST 2010
   */
  private String reportNo;
  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column
   * report_main.report_sql_from
   * 
   * @ibatorgenerated Thu Jan 28 16:03:32 CST 2010
   */
  private String reportSqlFrom;
  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column
   * report_main.source_type
   * 
   * @ibatorgenerated Thu Jan 28 16:03:32 CST 2010
   */
  private String sourceType;
  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column
   * report_main.report_group
   * 
   * @ibatorgenerated Thu Jan 28 16:03:32 CST 2010
   */
  private String reportGroup;
  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column
   * report_main.report_st
   * 
   * @ibatorgenerated Thu Jan 28 16:03:32 CST 2010
   */
  private String reportSt;
  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column
   * report_main.disp_pn
   * 
   * @ibatorgenerated Thu Jan 28 16:03:32 CST 2010
   */
  private String dispPn;
  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column
   * report_main.report_sql2
   * 
   * @ibatorgenerated Thu Jan 28 16:03:32 CST 2010
   */
  private String reportSql2;
  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column
   * report_main.report_remark
   * 
   * @ibatorgenerated Thu Jan 28 16:03:32 CST 2010
   */
  private String reportRemark;
  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column
   * report_main.is_enable
   * 
   * @ibatorgenerated Thu Jan 28 16:03:32 CST 2010
   */
  private String isEnable;
  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column
   * report_main.create_user
   * 
   * @ibatorgenerated Thu Jan 28 16:03:32 CST 2010
   */
  private Integer createUser;
  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column
   * report_main.create_date
   * 
   * @ibatorgenerated Thu Jan 28 16:03:32 CST 2010
   */
  private Date createDate;
  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column
   * report_main.update_user
   * 
   * @ibatorgenerated Thu Jan 28 16:03:32 CST 2010
   */
  private Integer updateUser;
  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column
   * report_main.update_date
   * 
   * @ibatorgenerated Thu Jan 28 16:03:32 CST 2010
   */
  private Date updateDate;
  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column
   * report_main.report_sql_where
   * 
   * @ibatorgenerated Thu Jan 28 16:03:32 CST 2010
   */
  private String reportSqlWhere;

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the
   * database column report_main.report_id
   * 
   * @return the value of report_main.report_id
   * @ibatorgenerated Thu Jan 28 16:03:32 CST 2010
   */
  public Integer getReportId() {
    return reportId;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database
   * column report_main.report_id
   * 
   * @param reportId the value for report_main.report_id
   * @ibatorgenerated Thu Jan 28 16:03:32 CST 2010
   */
  public void setReportId(Integer reportId) {
    this.reportId = reportId;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the
   * database column report_main.report_name
   * 
   * @return the value of report_main.report_name
   * @ibatorgenerated Thu Jan 28 16:03:32 CST 2010
   */
  public String getReportName() {
    return reportName;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database
   * column report_main.report_name
   * 
   * @param reportName the value for report_main.report_name
   * @ibatorgenerated Thu Jan 28 16:03:32 CST 2010
   */
  public void setReportName(String reportName) {
    this.reportName = reportName == null ? null : reportName.trim();
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the
   * database column report_main.report_no
   * 
   * @return the value of report_main.report_no
   * @ibatorgenerated Thu Jan 28 16:03:32 CST 2010
   */
  public String getReportNo() {
    return reportNo;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database
   * column report_main.report_no
   * 
   * @param reportNo the value for report_main.report_no
   * @ibatorgenerated Thu Jan 28 16:03:32 CST 2010
   */
  public void setReportNo(String reportNo) {
    this.reportNo = reportNo == null ? null : reportNo.trim();
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the
   * database column report_main.report_sql_from
   * 
   * @return the value of report_main.report_sql_from
   * @ibatorgenerated Thu Jan 28 16:03:32 CST 2010
   */
  public String getReportSqlFrom() {
    return reportSqlFrom;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database
   * column report_main.report_sql_from
   * 
   * @param reportSqlFrom the value for report_main.report_sql_from
   * @ibatorgenerated Thu Jan 28 16:03:32 CST 2010
   */
  public void setReportSqlFrom(String reportSqlFrom) {
    this.reportSqlFrom = reportSqlFrom == null ? null : reportSqlFrom.trim();
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the
   * database column report_main.source_type
   * 
   * @return the value of report_main.source_type
   * @ibatorgenerated Thu Jan 28 16:03:32 CST 2010
   */
  public String getSourceType() {
    return sourceType;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database
   * column report_main.source_type
   * 
   * @param sourceType the value for report_main.source_type
   * @ibatorgenerated Thu Jan 28 16:03:32 CST 2010
   */
  public void setSourceType(String sourceType) {
    this.sourceType = sourceType == null ? null : sourceType.trim();
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the
   * database column report_main.report_group
   * 
   * @return the value of report_main.report_group
   * @ibatorgenerated Thu Jan 28 16:03:32 CST 2010
   */
  public String getReportGroup() {
    return reportGroup;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database
   * column report_main.report_group
   * 
   * @param reportGroup the value for report_main.report_group
   * @ibatorgenerated Thu Jan 28 16:03:32 CST 2010
   */
  public void setReportGroup(String reportGroup) {
    this.reportGroup = reportGroup == null ? null : reportGroup.trim();
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the
   * database column report_main.report_st
   * 
   * @return the value of report_main.report_st
   * @ibatorgenerated Thu Jan 28 16:03:32 CST 2010
   */
  public String getReportSt() {
    return reportSt;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database
   * column report_main.report_st
   * 
   * @param reportSt the value for report_main.report_st
   * @ibatorgenerated Thu Jan 28 16:03:32 CST 2010
   */
  public void setReportSt(String reportSt) {
    this.reportSt = reportSt == null ? null : reportSt.trim();
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the
   * database column report_main.disp_pn
   * 
   * @return the value of report_main.disp_pn
   * @ibatorgenerated Thu Jan 28 16:03:32 CST 2010
   */
  public String getDispPn() {
    return dispPn;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database
   * column report_main.disp_pn
   * 
   * @param dispPn the value for report_main.disp_pn
   * @ibatorgenerated Thu Jan 28 16:03:32 CST 2010
   */
  public void setDispPn(String dispPn) {
    this.dispPn = dispPn == null ? null : dispPn.trim();
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the
   * database column report_main.report_sql2
   * 
   * @return the value of report_main.report_sql2
   * @ibatorgenerated Thu Jan 28 16:03:32 CST 2010
   */
  public String getReportSql2() {
    return reportSql2;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database
   * column report_main.report_sql2
   * 
   * @param reportSql2 the value for report_main.report_sql2
   * @ibatorgenerated Thu Jan 28 16:03:32 CST 2010
   */
  public void setReportSql2(String reportSql2) {
    this.reportSql2 = reportSql2 == null ? null : reportSql2.trim();
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the
   * database column report_main.report_remark
   * 
   * @return the value of report_main.report_remark
   * @ibatorgenerated Thu Jan 28 16:03:32 CST 2010
   */
  public String getReportRemark() {
    return reportRemark;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database
   * column report_main.report_remark
   * 
   * @param reportRemark the value for report_main.report_remark
   * @ibatorgenerated Thu Jan 28 16:03:32 CST 2010
   */
  public void setReportRemark(String reportRemark) {
    this.reportRemark = reportRemark == null ? null : reportRemark.trim();
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the
   * database column report_main.is_enable
   * 
   * @return the value of report_main.is_enable
   * @ibatorgenerated Thu Jan 28 16:03:32 CST 2010
   */
  public String getIsEnable() {
    return isEnable;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database
   * column report_main.is_enable
   * 
   * @param isEnable the value for report_main.is_enable
   * @ibatorgenerated Thu Jan 28 16:03:32 CST 2010
   */
  public void setIsEnable(String isEnable) {
    this.isEnable = isEnable == null ? null : isEnable.trim();
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the
   * database column report_main.create_user
   * 
   * @return the value of report_main.create_user
   * @ibatorgenerated Thu Jan 28 16:03:32 CST 2010
   */
  public Integer getCreateUser() {
    return createUser;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database
   * column report_main.create_user
   * 
   * @param createUser the value for report_main.create_user
   * @ibatorgenerated Thu Jan 28 16:03:32 CST 2010
   */
  public void setCreateUser(Integer createUser) {
    this.createUser = createUser;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the
   * database column report_main.create_date
   * 
   * @return the value of report_main.create_date
   * @ibatorgenerated Thu Jan 28 16:03:32 CST 2010
   */
  public Date getCreateDate() {
    return createDate;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database
   * column report_main.create_date
   * 
   * @param createDate the value for report_main.create_date
   * @ibatorgenerated Thu Jan 28 16:03:32 CST 2010
   */
  public void setCreateDate(Date createDate) {
    this.createDate = createDate;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the
   * database column report_main.update_user
   * 
   * @return the value of report_main.update_user
   * @ibatorgenerated Thu Jan 28 16:03:32 CST 2010
   */
  public Integer getUpdateUser() {
    return updateUser;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database
   * column report_main.update_user
   * 
   * @param updateUser the value for report_main.update_user
   * @ibatorgenerated Thu Jan 28 16:03:32 CST 2010
   */
  public void setUpdateUser(Integer updateUser) {
    this.updateUser = updateUser;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the
   * database column report_main.update_date
   * 
   * @return the value of report_main.update_date
   * @ibatorgenerated Thu Jan 28 16:03:32 CST 2010
   */
  public Date getUpdateDate() {
    return updateDate;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database
   * column report_main.update_date
   * 
   * @param updateDate the value for report_main.update_date
   * @ibatorgenerated Thu Jan 28 16:03:32 CST 2010
   */
  public void setUpdateDate(Date updateDate) {
    this.updateDate = updateDate;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the
   * database column report_main.report_sql_where
   * 
   * @return the value of report_main.report_sql_where
   * @ibatorgenerated Thu Jan 28 16:03:32 CST 2010
   */
  public String getReportSqlWhere() {
    return reportSqlWhere;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database
   * column report_main.report_sql_where
   * 
   * @param reportSqlWhere the value for report_main.report_sql_where
   * @ibatorgenerated Thu Jan 28 16:03:32 CST 2010
   */
  public void setReportSqlWhere(String reportSqlWhere) {
    this.reportSqlWhere = reportSqlWhere == null ? null : reportSqlWhere.trim();
  }
}
