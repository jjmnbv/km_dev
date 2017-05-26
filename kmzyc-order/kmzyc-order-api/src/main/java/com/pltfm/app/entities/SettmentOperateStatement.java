package com.pltfm.app.entities;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 结算单操作流水
 * 
 * @author weijl
 * 
 */
public class SettmentOperateStatement implements Serializable {

  /**
	 * 
	 */
  private static final long serialVersionUID = 6036435782253836898L;
  /**
   * 结算单流水ID
   */
  private Long statementId;
  /**
   * 结算单ID
   */
  private String settmentNo;
  /**
   * 操作时间
   */
  private Date operateTime;
  /**
   * 操作人类型
   */
  private short operatorType;
  /**
   * 操作人
   */
  private String operator;
  /**
   * T操作类型 结算状态
   */
  private short operateType;
  /**
   * 操作描述
   */
  private String remark;

  @Override
  public String toString() {

    StringBuilder sb = new StringBuilder();

    sb.append("[");
    sb.append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(operateTime));
    sb.append("] ");

    // 后台用户|前台用户|系统自动
    if (1 == operatorType) {
      sb.append("后台用户  ");
      sb.append(operator);
    } else if (2 == operatorType) {
      sb.append("前台用户  ");
      sb.append(operator);
    } else {
      sb.append("系统自动  ");
      // sb.append(operator);
    }

    sb.append(" ");
    // /结算状态，1:未确认,2:商家已确认,3:运营已确认,4:财务审核通过,5:财务审核拒绝,5:已结出

    switch (operateType) {
      case 1:
        sb.append("[生成] : 生成结算单；");
        break;
      case 2:
        sb.append("[商家确认] : 商家已核对并反馈意见；  ");
        break;
      case 3:
        sb.append("[运营确认] : 运营人员已核对并完成差异调整（将计入下期结算）；    ");
        break;
      case 4:
        sb.append("[财务审核] : 财务审核通过；   ");
        break;
      case 5:
        sb.append("[财务审核] : 财务审核拒绝；   ");
        break;
      case 6:
        sb.append("[财务结出 ] : 本期结算单处理完毕；  ");
        break;

      default:

        break;
    }

    return sb.toString();
    /**
     * 
     [2014-05-20 01:00:00] 系统自动 [财务结出] ：本期应结金额已经结出到商家账户余额，本期结算单处理完毕；
     * 
     * [2014-05-20 01:00:00] 后台用户 Accountant [财务审核] ：财务审核通过；
     * 
     * [2014-05-20 01:00:00] 后台用户 Admin [运营确认] ：运营人员已核对并完成差异调整（将计入下期结算）；
     * 
     * [2014-05-20 01:00:00] 后台用户 Accountant [财务审核] ：财务审核拒绝；
     * 
     * [2014-05-20 01:00:00] 后台用户 Admin [运营确认] ：运营人员已核对并完成差异调整（将计入下期结算）；
     * 
     * [2014-05-20 08:10:00] 前台用户 TestUser [商家确认] ：商家已核对并反馈意见；
     * 
     * [2014-05-20 01:00:00] 系统自动 [生成] ：生成结算单；
     */
  }

  public SettmentOperateStatement() {
    super();
    // TODO Auto-generated constructor stub
  }

  public Long getStatementId() {
    return statementId;
  }

  public void setStatementId(Long statementId) {
    this.statementId = statementId;
  }

  public String getSettmentNo() {
    return settmentNo;
  }

  public void setSettmentNo(String settmentNo) {
    this.settmentNo = settmentNo;
  }

  public Date getOperateTime() {
    return operateTime;
  }

  public void setOperateTime(Date operateTime) {
    this.operateTime = operateTime;
  }

  public short getOperatorType() {
    return operatorType;
  }

  public void setOperatorType(short operatorType) {
    this.operatorType = operatorType;
  }

  public String getOperator() {
    return operator;
  }

  public void setOperator(String operator) {
    this.operator = operator;
  }

  public short getOperateType() {
    return operateType;
  }

  public void setOperateType(short operateType) {
    this.operateType = operateType;
  }

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

}
