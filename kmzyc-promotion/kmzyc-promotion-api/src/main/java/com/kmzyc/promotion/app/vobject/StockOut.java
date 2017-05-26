package com.kmzyc.promotion.app.vobject;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 出库单实体类
 * 
 * @author luoyi
 * @createDate 2013/08/15
 * 
 */
public class StockOut implements java.io.Serializable {
	private static final long serialVersionUID = -7957139740698826331L;

	/**
	 * StockOut无参构造方法
	 */
	public StockOut() {

	}

	private Long stockOutId;// 出库单ID

	private BigDecimal customerTypeId;// 客户类别

	private String stockOutNo;// 订单编号

	private Long warehouseId;// 出库仓库ID

	private Short type;// 出库单类型

	private Integer userId;// 经手人

	private String userName;// 经手人姓名

	private Integer totalQuantity;// 出库总数

	private BigDecimal taxSum;// 出库含税总额

	private BigDecimal totalTax;// 出库总税额

	private BigDecimal totalSum;// 出库不含税总额

	private Integer createUser;// 录入人

	private String createUserName;// 录入人姓名

	private Date createDate;// 录入时间

	private Integer auditUser;// 审核人

	private String checkUserName;// 审核人姓名

	private Date auditDate;// 审核时间

	private Short status;// 状态

	private Short dayEndStatus;// 日结状态

	private String remark;// 备注

	private String billNo;// 订单编号

	private Date modifiyDate;// 出库日期(修改日期)

	private Integer modifierId;// 修改人ID

	private String modifierName;// 修改人姓名

	/*
	 * 以下为属性set和get方法
	 * 
	 * @return
	 */
	public Long getStockOutId() {
		return stockOutId;
	}

	public void setStockOutId(Long stockOutId) {
		this.stockOutId = stockOutId;
	}

	public BigDecimal getCustomerTypeId() {
		return customerTypeId;
	}

	public void setCustomerTypeId(BigDecimal customerTypeId) {
		this.customerTypeId = customerTypeId;
	}

	public String getStockOutNo() {
		return stockOutNo;
	}

	public void setStockOutNo(String stockOutNo) {
		this.stockOutNo = stockOutNo;
	}

	public Long getWarehouseId() {
		return warehouseId;
	}

	public void setWarehouseId(Long warehouseId) {
		this.warehouseId = warehouseId;
	}

	public Short getType() {
		return type;
	}

	public void setType(Short type) {
		this.type = type;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getTotalQuantity() {
		return totalQuantity;
	}

	public void setTotalQuantity(Integer totalQuantity) {
		this.totalQuantity = totalQuantity;
	}

	public BigDecimal getTaxSum() {
		return taxSum;
	}

	public void setTaxSum(BigDecimal taxSum) {
		this.taxSum = taxSum;
	}

	public BigDecimal getTotalTax() {
		return totalTax;
	}

	public void setTotalTax(BigDecimal totalTax) {
		this.totalTax = totalTax;
	}

	public BigDecimal getTotalSum() {
		return totalSum;
	}

	public void setTotalSum(BigDecimal totalSum) {
		this.totalSum = totalSum;
	}

	public Integer getCreateUser() {
		return createUser;
	}

	public void setCreateUser(Integer createUser) {
		this.createUser = createUser;
	}

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Integer getAuditUser() {
		return auditUser;
	}

	public void setAuditUser(Integer auditUser) {
		this.auditUser = auditUser;
	}

	public String getCheckUserName() {
		return checkUserName;
	}

	public void setCheckUserName(String checkUserName) {
		this.checkUserName = checkUserName;
	}

	public Date getAuditDate() {
		return auditDate;
	}

	public void setAuditDate(Date auditDate) {
		this.auditDate = auditDate;
	}

	public Short getStatus() {
		return status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	public Short getDayEndStatus() {
		return dayEndStatus;
	}

	public void setDayEndStatus(Short dayEndStatus) {
		this.dayEndStatus = dayEndStatus;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	public Date getModifiyDate() {
		return modifiyDate;
	}

	public void setModifiyDate(Date modifiyDate) {
		this.modifiyDate = modifiyDate;
	}

	public Integer getModifierId() {
		return modifierId;
	}

	public void setModifierId(Integer modifierId) {
		this.modifierId = modifierId;
	}

	public String getModifierName() {
		return modifierName;
	}

	public void setModifierName(String modifierName) {
		this.modifierName = modifierName;
	}

	@Override
	public String toString() {
		return "StockOut [stockOutId=" + stockOutId + ", customerTypeId=" + customerTypeId + ", stockOutNo="
				+ stockOutNo + ", warehouseId=" + warehouseId + ", type=" + type + ", userId=" + userId + ", userName="
				+ userName + ", totalQuantity=" + totalQuantity + ", taxSum=" + taxSum + ", totalTax=" + totalTax
				+ ", totalSum=" + totalSum + ", createUser=" + createUser + ", createUserName=" + createUserName
				+ ", createDate=" + createDate + ", auditUser=" + auditUser + ", checkUserName=" + checkUserName
				+ ", auditDate=" + auditDate + ", status=" + status + ", dayEndStatus=" + dayEndStatus + ", remark="
				+ remark + ", billNo=" + billNo + ", modifiyDate=" + modifiyDate + ", modifierId=" + modifierId
				+ ", modifierName=" + modifierName + "]";
	}

}