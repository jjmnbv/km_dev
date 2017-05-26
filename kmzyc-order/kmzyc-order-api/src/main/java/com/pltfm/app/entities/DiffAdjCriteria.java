package com.pltfm.app.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 差异调整明细 条件
 * 
 * @author lvzj
 * 
 */
@SuppressWarnings("unchecked")
public class DiffAdjCriteria implements Serializable {

  /**
	 * 
	 */
  private static final long serialVersionUID = 1L;

  private Long diffAdjId;
  /**
   * 对应结算单
   */
  private String currSettmentNo;
  /**
   * 计入结算单
   */
  private String calcSettmentNo;

  /**
   * 调整事务详细说明
   */
  private String adjDetail;

  /**
   * 调整操作人
   */
  private Long operater;

  /**
   * 开始时间
   */
  private Date startDate;

  /**
   * 结束时间
   */
  private Date endDate;

  private String userName;

  private List hurlIds;

  /**
   * 分页条件
   */
  private Integer startIndex = 0;
  private Integer endIndex = 20;

  public String getCalcSettmentNo() {
    return calcSettmentNo;
  }

  public void setCalcSettmentNo(String calcSettmentNo) {
    this.calcSettmentNo = calcSettmentNo;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public Long getDiffAdjId() {
    return diffAdjId;
  }

  public void setDiffAdjId(Long diffAdjId) {
    this.diffAdjId = diffAdjId;
  }

  public String getCurrSettmentNo() {
    return currSettmentNo;
  }

  public void setCurrSettmentNo(String currSettmentNo) {
    this.currSettmentNo = currSettmentNo;
  }

  public String getAdjDetail() {
    return adjDetail;
  }

  public void setAdjDetail(String adjDetail) {
    this.adjDetail = adjDetail;
  }

  public Long getOperater() {
    return operater;
  }

  public void setOperater(Long operater) {
    this.operater = operater;
  }

  public Date getStartDate() {
    return startDate;
  }

  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }

  public Date getEndDate() {
    return endDate;
  }

  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }

  public Integer getStartIndex() {
    return startIndex;
  }

  public void setStartIndex(Integer startIndex) {
    this.startIndex = startIndex;
  }

  public Integer getEndIndex() {
    return endIndex;
  }

  public void setEndIndex(Integer endIndex) {
    this.endIndex = endIndex;
  }


  public List getHurlIds() {
    return hurlIds;
  }

  public void setHurlIds(List hurlIds) {
    this.hurlIds = hurlIds;
  }

  public DiffAdjCriteria() {
    super();
    // TODO Auto-generated constructor stub
  }

  @Override
  public String toString() {
    return "DiffAdjCriteria [adjDetail=" + adjDetail + ", operater=" + operater + ", startDate="
        + startDate + ", endDate=" + endDate + ", startIndex=" + startIndex + ", endIndex="
        + endIndex + "]";
  }

}
