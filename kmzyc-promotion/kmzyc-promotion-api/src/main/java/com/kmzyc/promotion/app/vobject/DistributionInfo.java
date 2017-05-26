package com.kmzyc.promotion.app.vobject;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 配送单实体类
 * 
 * @author luoyi
 * @since 2013/08/20
 */
public class DistributionInfo implements Serializable {

	private static final long serialVersionUID = 137197322429730029L;

	/**
	 * 无参构造
	 */
	public DistributionInfo() {

	}

	private Long distributionId;// 配送单ID

	private String distributionNo;// 配送单编号

	private String logisticsCompany;// 物流单位

	private String logisticsNo;// 物流编号

	private Long warehouseId;// 仓库ID

	private String warehouseName;// 仓库名称

	private String billNo;// 业务单据ID

	private Integer totalQuantity;// 总数量

	private BigDecimal totalSum;// 总金额

	private String userName;// 收货人

	private String deliveryAddress;// 收货地址

	private String tel;// 收货人电话

	private Date logisticsDate;// 配送日期(等于创建日期)

	private String isDeliveries;// 是否送达

	private String remark;// 备注

	private Date createDate;// 修改日期

	private Integer createUser;// 创建人(修改人)

	private String CreateUserName;// 创建人姓名(修改姓名)

	private String orderNo;// 订单编号

	public Long getDistributionId() {
		return distributionId;
	}

	public void setDistributionId(Long distributionId) {
		this.distributionId = distributionId;
	}

	public String getDistributionNo() {
		return distributionNo;
	}

	public void setDistributionNo(String distributionNo) {
		this.distributionNo = distributionNo;
	}

	public String getLogisticsCompany() {
		return logisticsCompany;
	}

	public void setLogisticsCompany(String logisticsCompany) {
		this.logisticsCompany = logisticsCompany;
	}

	public String getLogisticsNo() {
		return logisticsNo;
	}

	public void setLogisticsNo(String logisticsNo) {
		this.logisticsNo = logisticsNo;
	}

	public Long getWarehouseId() {
		return warehouseId;
	}

	public void setWarehouseId(Long warehouseId) {
		this.warehouseId = warehouseId;
	}

	public String getWarehouseName() {
		return warehouseName;
	}

	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}

	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	public Integer getTotalQuantity() {
		return totalQuantity;
	}

	public void setTotalQuantity(Integer totalQuantity) {
		this.totalQuantity = totalQuantity;
	}

	public BigDecimal getTotalSum() {
		return totalSum;
	}

	public void setTotalSum(BigDecimal totalSum) {
		this.totalSum = totalSum;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getDeliveryAddress() {
		return deliveryAddress;
	}

	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public Date getLogisticsDate() {
		return logisticsDate;
	}

	public void setLogisticsDate(Date logisticsDate) {
		this.logisticsDate = logisticsDate;
	}

	public String getIsDeliveries() {
		return isDeliveries;
	}

	public void setIsDeliveries(String isDeliveries) {
		this.isDeliveries = isDeliveries;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Integer getCreateUser() {
		return createUser;
	}

	public void setCreateUser(Integer createUser) {
		this.createUser = createUser;
	}

	public String getCreateUserName() {
		return CreateUserName;
	}

	public void setCreateUserName(String createUserName) {
		CreateUserName = createUserName;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	@Override
	public String toString() {
		return "DistributionInfo [distributionId=" + distributionId + ", distributionNo=" + distributionNo
				+ ", logisticsCompany=" + logisticsCompany + ", logisticsNo=" + logisticsNo + ", warehouseId="
				+ warehouseId + ", warehouseName=" + warehouseName + ", billNo=" + billNo + ", totalQuantity="
				+ totalQuantity + ", totalSum=" + totalSum + ", userName=" + userName + ", deliveryAddress="
				+ deliveryAddress + ", tel=" + tel + ", logisticsDate=" + logisticsDate + ", isDeliveries="
				+ isDeliveries + ", remark=" + remark + ", createDate=" + createDate + ", createUser=" + createUser
				+ ", CreateUserName=" + CreateUserName + "orderNo" + orderNo + "]";
	}

}