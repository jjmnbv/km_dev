package com.pltfm.app.vobject;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

public class ActivitySupplierEntry implements Serializable {
    // 公司名称(SUPPLIERS_INFO.COMPANY_SHOW_NAME)
    private String companyShowName;
    // 店铺名称（SHOP_MAIN.SHOP_NAME）
    private String shopName;
    // 登录帐号
    private String loginAccount;
    // 联系电话
    private String mobile;
    // 缴款状态
    private Integer activityPaymentStatus;
    // 活动名称(ACTIVITY_INFO.ACTIVITY_NAME)
    private String activityName;
    // 活动类型(ACTIVITY_INFO.ACTIVITY_TYPE)
    private Integer activityType;
    // 活动收费类型(ACTIVITY_INFO.CHARGE_TYPE)
    private Integer chargeType;
    // 活动收费固定收费（ACTIVITY_CHARGE.FIXED_CHARGE）
    private BigDecimal fixedCharge;
    // 活动收费单个产品收费（ACTIVITY_CHARGE.SINGLE_CHARGE）
    private BigDecimal singleCharge;
    // 活动收费返佣比例（ACTIVITY_CHARGE.COMMISSION_RATE）
    private BigDecimal commissionRate;
    // 查询使用报名初始时间
    private Timestamp entryStartTime;
    // 查询使用报名末时间
    private Timestamp entryEndTime;
    // 已缴纳活动费用
    private BigDecimal totalPayAmount;
    // 剩余活动费用
    private BigDecimal residualActivityPrice;
    private ActivityInfo activityInfo;
    private ActivityPayment activityPayment;
    // 查询使用创建初始时间
    private Timestamp createStartTime;
    // 查询使用创建末时间
    private Timestamp createEndTime;
    // 商家总销量
    private Integer totalSalesQuantity;
    // 已退款活动费用
    private BigDecimal refundActivityAmount;

    public BigDecimal getRefundActivityAmount() {
        return refundActivityAmount;
    }

    public void setRefundActivityAmount(BigDecimal refundActivityAmount) {
        this.refundActivityAmount = refundActivityAmount;
    }

    public ActivityInfo getActivityInfo() {
        return activityInfo;
    }

    public void setActivityInfo(ActivityInfo activityInfo) {
        this.activityInfo = activityInfo;
    }

    public ActivityPayment getActivityPayment() {
        return activityPayment;
    }

    public void setActivityPayment(ActivityPayment activityPayment) {
        this.activityPayment = activityPayment;
    }

    /**
	 * 
	 */
    private static final long serialVersionUID = 8806523710633171530L;

    /**
     * This field was generated by Apache iBATIS ibator. This field corresponds to the database
     * column ACTIVITY_SUPPLIER_ENTRY.SUPPLIER_ENTRY_ID
     *
     * @ibatorgenerated Thu Mar 17 15:35:56 CST 2016
     */
    private Long supplierEntryId;

    /**
     * This field was generated by Apache iBATIS ibator. This field corresponds to the database
     * column ACTIVITY_SUPPLIER_ENTRY.ACTIVITY_ID
     *
     * @ibatorgenerated Thu Mar 17 15:35:56 CST 2016
     */
    private Long activityId;

    /**
     * This field was generated by Apache iBATIS ibator. This field corresponds to the database
     * column ACTIVITY_SUPPLIER_ENTRY.SUPPLIER_ID
     *
     * @ibatorgenerated Thu Mar 17 15:35:56 CST 2016
     */
    private Long supplierId;

    /**
     * This field was generated by Apache iBATIS ibator. This field corresponds to the database
     * column ACTIVITY_SUPPLIER_ENTRY.ENTRY_STATUS
     *
     * @ibatorgenerated Thu Mar 17 15:35:56 CST 2016
     */
    private Integer entryStatus;

    /**
     * This field was generated by Apache iBATIS ibator. This field corresponds to the database
     * column ACTIVITY_SUPPLIER_ENTRY.ACTIVITY_SUPPLIER_TYPE
     *
     * @ibatorgenerated Thu Mar 17 15:35:56 CST 2016
     */
    private Integer activitySupplierType;

    /**
     * This field was generated by Apache iBATIS ibator. This field corresponds to the database
     * column ACTIVITY_SUPPLIER_ENTRY.AUDIT_STATUS
     *
     * @ibatorgenerated Thu Mar 17 15:35:56 CST 2016
     */
    private Integer auditStatus;

    /**
     * This field was generated by Apache iBATIS ibator. This field corresponds to the database
     * column ACTIVITY_SUPPLIER_ENTRY.ACTIVITY_TOTAL_PRICE
     *
     * @ibatorgenerated Thu Mar 17 15:35:56 CST 2016
     */
    private BigDecimal activityTotalPrice;

    /**
     * This field was generated by Apache iBATIS ibator. This field corresponds to the database
     * column ACTIVITY_SUPPLIER_ENTRY.CREATE_TIME
     *
     * @ibatorgenerated Thu Mar 17 15:35:56 CST 2016
     */
    private Timestamp createTime;

    /**
     * This field was generated by Apache iBATIS ibator. This field corresponds to the database
     * column ACTIVITY_SUPPLIER_ENTRY.MODIFY_TIME
     *
     * @ibatorgenerated Thu Mar 17 15:35:56 CST 2016
     */
    private Timestamp modifyTime;

    /**
     * This field was generated by Apache iBATIS ibator. This field corresponds to the database
     * column ACTIVITY_SUPPLIER_ENTRY.AUDIT_TIME
     *
     * @ibatorgenerated Thu Mar 17 15:35:56 CST 2016
     */
    private Timestamp auditTime;

    /**
     * This field was generated by Apache iBATIS ibator. This field corresponds to the database
     * column ACTIVITY_SUPPLIER_ENTRY.ENTRY_TIME
     *
     * @ibatorgenerated Thu Mar 17 15:35:56 CST 2016
     */
    private Timestamp entryTime;

    /**
     * This field was generated by Apache iBATIS ibator. This field corresponds to the database
     * column ACTIVITY_SUPPLIER_ENTRY.CREATE_USER
     *
     * @ibatorgenerated Thu Mar 17 15:35:56 CST 2016
     */
    private Long createUser;

    /**
     * This field was generated by Apache iBATIS ibator. This field corresponds to the database
     * column ACTIVITY_SUPPLIER_ENTRY.MODIF_USER
     *
     * @ibatorgenerated Thu Mar 17 15:35:56 CST 2016
     */
    private Long modifUser;

    /**
     * This field was generated by Apache iBATIS ibator. This field corresponds to the database
     * column ACTIVITY_SUPPLIER_ENTRY.ENTRY_USER
     *
     * @ibatorgenerated Thu Mar 17 15:35:56 CST 2016
     */
    private Long entryUser;

    /**
     * This field was generated by Apache iBATIS ibator. This field corresponds to the database
     * column ACTIVITY_SUPPLIER_ENTRY.AUDIT_USER
     *
     * @ibatorgenerated Thu Mar 17 15:35:56 CST 2016
     */
    private Long auditUser;

    /**
     * This field was generated by Apache iBATIS ibator. This field corresponds to the database
     * column ACTIVITY_SUPPLIER_ENTRY.REMARK
     *
     * @ibatorgenerated Thu Mar 17 15:35:56 CST 2016
     */
    private String remark;

    private List<ActivitySku> activitySkuList;

    private List<ActivityPayment> activityPaymentList;

    public Integer getActivityType() {
        return activityType;
    }

    public void setActivityType(Integer activityType) {
        this.activityType = activityType;
    }

    public Integer getChargeType() {
        return chargeType;
    }

    public void setChargeType(Integer chargeType) {
        this.chargeType = chargeType;
    }

    public BigDecimal getFixedCharge() {
        return fixedCharge;
    }

    public void setFixedCharge(BigDecimal fixedCharge) {
        this.fixedCharge = fixedCharge;
    }

    public BigDecimal getSingleCharge() {
        return singleCharge;
    }

    public void setSingleCharge(BigDecimal singleCharge) {
        this.singleCharge = singleCharge;
    }

    public BigDecimal getCommissionRate() {
        return commissionRate;
    }

    public void setCommissionRate(BigDecimal commissionRate) {
        this.commissionRate = commissionRate;
    }

    public String getLoginAccount() {
        return loginAccount;
    }

    public void setLoginAccount(String loginAccount) {
        this.loginAccount = loginAccount;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getActivityPaymentStatus() {
        return activityPaymentStatus;
    }

    public void setActivityPaymentStatus(Integer activityPaymentStatus) {
        this.activityPaymentStatus = activityPaymentStatus;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public Timestamp getEntryStartTime() {
        return entryStartTime;
    }

    public void setEntryStartTime(Timestamp entryStartTime) {
        this.entryStartTime = entryStartTime;
    }

    public Timestamp getEntryEndTime() {
        return entryEndTime;
    }

    public void setEntryEndTime(Timestamp entryEndTime) {
        this.entryEndTime = entryEndTime;
    }

    public String getCompanyShowName() {
        return companyShowName;
    }

    public void setCompanyShowName(String companyShowName) {
        this.companyShowName = companyShowName;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method returns the value of the
     * database column ACTIVITY_SUPPLIER_ENTRY.SUPPLIER_ENTRY_ID
     *
     * @return the value of ACTIVITY_SUPPLIER_ENTRY.SUPPLIER_ENTRY_ID
     *
     * @ibatorgenerated Thu Mar 17 15:35:56 CST 2016
     */
    public Long getSupplierEntryId() {
        return supplierEntryId;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method sets the value of the database
     * column ACTIVITY_SUPPLIER_ENTRY.SUPPLIER_ENTRY_ID
     *
     * @param supplierEntryId the value for ACTIVITY_SUPPLIER_ENTRY.SUPPLIER_ENTRY_ID
     *
     * @ibatorgenerated Thu Mar 17 15:35:56 CST 2016
     */
    public void setSupplierEntryId(Long supplierEntryId) {
        this.supplierEntryId = supplierEntryId;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method returns the value of the
     * database column ACTIVITY_SUPPLIER_ENTRY.ACTIVITY_ID
     *
     * @return the value of ACTIVITY_SUPPLIER_ENTRY.ACTIVITY_ID
     *
     * @ibatorgenerated Thu Mar 17 15:35:56 CST 2016
     */
    public Long getActivityId() {
        return activityId;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method sets the value of the database
     * column ACTIVITY_SUPPLIER_ENTRY.ACTIVITY_ID
     *
     * @param activityId the value for ACTIVITY_SUPPLIER_ENTRY.ACTIVITY_ID
     *
     * @ibatorgenerated Thu Mar 17 15:35:56 CST 2016
     */
    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method returns the value of the
     * database column ACTIVITY_SUPPLIER_ENTRY.SUPPLIER_ID
     *
     * @return the value of ACTIVITY_SUPPLIER_ENTRY.SUPPLIER_ID
     *
     * @ibatorgenerated Thu Mar 17 15:35:56 CST 2016
     */
    public Long getSupplierId() {
        return supplierId;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method sets the value of the database
     * column ACTIVITY_SUPPLIER_ENTRY.SUPPLIER_ID
     *
     * @param supplierId the value for ACTIVITY_SUPPLIER_ENTRY.SUPPLIER_ID
     *
     * @ibatorgenerated Thu Mar 17 15:35:56 CST 2016
     */
    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method returns the value of the
     * database column ACTIVITY_SUPPLIER_ENTRY.ENTRY_STATUS
     *
     * @return the value of ACTIVITY_SUPPLIER_ENTRY.ENTRY_STATUS
     *
     * @ibatorgenerated Thu Mar 17 15:35:56 CST 2016
     */
    public Integer getEntryStatus() {
        return entryStatus;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method sets the value of the database
     * column ACTIVITY_SUPPLIER_ENTRY.ENTRY_STATUS
     *
     * @param entryStatus the value for ACTIVITY_SUPPLIER_ENTRY.ENTRY_STATUS
     *
     * @ibatorgenerated Thu Mar 17 15:35:56 CST 2016
     */
    public void setEntryStatus(Integer entryStatus) {
        this.entryStatus = entryStatus;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method returns the value of the
     * database column ACTIVITY_SUPPLIER_ENTRY.ACTIVITY_SUPPLIER_TYPE
     *
     * @return the value of ACTIVITY_SUPPLIER_ENTRY.ACTIVITY_SUPPLIER_TYPE
     *
     * @ibatorgenerated Thu Mar 17 15:35:56 CST 2016
     */
    public Integer getActivitySupplierType() {
        return activitySupplierType;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method sets the value of the database
     * column ACTIVITY_SUPPLIER_ENTRY.ACTIVITY_SUPPLIER_TYPE
     *
     * @param activitySupplierType the value for ACTIVITY_SUPPLIER_ENTRY.ACTIVITY_SUPPLIER_TYPE
     *
     * @ibatorgenerated Thu Mar 17 15:35:56 CST 2016
     */
    public void setActivitySupplierType(Integer activitySupplierType) {
        this.activitySupplierType = activitySupplierType;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method returns the value of the
     * database column ACTIVITY_SUPPLIER_ENTRY.AUDIT_STATUS
     *
     * @return the value of ACTIVITY_SUPPLIER_ENTRY.AUDIT_STATUS
     *
     * @ibatorgenerated Thu Mar 17 15:35:56 CST 2016
     */
    public Integer getAuditStatus() {
        return auditStatus;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method sets the value of the database
     * column ACTIVITY_SUPPLIER_ENTRY.AUDIT_STATUS
     *
     * @param auditStatus the value for ACTIVITY_SUPPLIER_ENTRY.AUDIT_STATUS
     *
     * @ibatorgenerated Thu Mar 17 15:35:56 CST 2016
     */
    public void setAuditStatus(Integer auditStatus) {
        this.auditStatus = auditStatus;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method returns the value of the
     * database column ACTIVITY_SUPPLIER_ENTRY.ACTIVITY_TOTAL_PRICE
     *
     * @return the value of ACTIVITY_SUPPLIER_ENTRY.ACTIVITY_TOTAL_PRICE
     *
     * @ibatorgenerated Thu Mar 17 15:35:56 CST 2016
     */
    public BigDecimal getActivityTotalPrice() {
        return activityTotalPrice;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method sets the value of the database
     * column ACTIVITY_SUPPLIER_ENTRY.ACTIVITY_TOTAL_PRICE
     *
     * @param activityTotalPrice the value for ACTIVITY_SUPPLIER_ENTRY.ACTIVITY_TOTAL_PRICE
     *
     * @ibatorgenerated Thu Mar 17 15:35:56 CST 2016
     */
    public void setActivityTotalPrice(BigDecimal activityTotalPrice) {
        this.activityTotalPrice = activityTotalPrice;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method returns the value of the
     * database column ACTIVITY_SUPPLIER_ENTRY.CREATE_TIME
     *
     * @return the value of ACTIVITY_SUPPLIER_ENTRY.CREATE_TIME
     *
     * @ibatorgenerated Thu Mar 17 15:35:56 CST 2016
     */
    public Timestamp getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method sets the value of the database
     * column ACTIVITY_SUPPLIER_ENTRY.CREATE_TIME
     *
     * @param createTime the value for ACTIVITY_SUPPLIER_ENTRY.CREATE_TIME
     *
     * @ibatorgenerated Thu Mar 17 15:35:56 CST 2016
     */
    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method returns the value of the
     * database column ACTIVITY_SUPPLIER_ENTRY.MODIFY_TIME
     *
     * @return the value of ACTIVITY_SUPPLIER_ENTRY.MODIFY_TIME
     *
     * @ibatorgenerated Thu Mar 17 15:35:56 CST 2016
     */
    public Timestamp getModifyTime() {
        return modifyTime;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method sets the value of the database
     * column ACTIVITY_SUPPLIER_ENTRY.MODIFY_TIME
     *
     * @param modifyTime the value for ACTIVITY_SUPPLIER_ENTRY.MODIFY_TIME
     *
     * @ibatorgenerated Thu Mar 17 15:35:56 CST 2016
     */
    public void setModifyTime(Timestamp modifyTime) {
        this.modifyTime = modifyTime;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method returns the value of the
     * database column ACTIVITY_SUPPLIER_ENTRY.AUDIT_TIME
     *
     * @return the value of ACTIVITY_SUPPLIER_ENTRY.AUDIT_TIME
     *
     * @ibatorgenerated Thu Mar 17 15:35:56 CST 2016
     */
    public Timestamp getAuditTime() {
        return auditTime;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method sets the value of the database
     * column ACTIVITY_SUPPLIER_ENTRY.AUDIT_TIME
     *
     * @param auditTime the value for ACTIVITY_SUPPLIER_ENTRY.AUDIT_TIME
     *
     * @ibatorgenerated Thu Mar 17 15:35:56 CST 2016
     */
    public void setAuditTime(Timestamp auditTime) {
        this.auditTime = auditTime;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method returns the value of the
     * database column ACTIVITY_SUPPLIER_ENTRY.ENTRY_TIME
     *
     * @return the value of ACTIVITY_SUPPLIER_ENTRY.ENTRY_TIME
     *
     * @ibatorgenerated Thu Mar 17 15:35:56 CST 2016
     */
    public Timestamp getEntryTime() {
        return entryTime;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method sets the value of the database
     * column ACTIVITY_SUPPLIER_ENTRY.ENTRY_TIME
     *
     * @param entryTime the value for ACTIVITY_SUPPLIER_ENTRY.ENTRY_TIME
     *
     * @ibatorgenerated Thu Mar 17 15:35:56 CST 2016
     */
    public void setEntryTime(Timestamp entryTime) {
        this.entryTime = entryTime;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method returns the value of the
     * database column ACTIVITY_SUPPLIER_ENTRY.CREATE_USER
     *
     * @return the value of ACTIVITY_SUPPLIER_ENTRY.CREATE_USER
     *
     * @ibatorgenerated Thu Mar 17 15:35:56 CST 2016
     */
    public Long getCreateUser() {
        return createUser;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method sets the value of the database
     * column ACTIVITY_SUPPLIER_ENTRY.CREATE_USER
     *
     * @param createUser the value for ACTIVITY_SUPPLIER_ENTRY.CREATE_USER
     *
     * @ibatorgenerated Thu Mar 17 15:35:56 CST 2016
     */
    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method returns the value of the
     * database column ACTIVITY_SUPPLIER_ENTRY.MODIF_USER
     *
     * @return the value of ACTIVITY_SUPPLIER_ENTRY.MODIF_USER
     *
     * @ibatorgenerated Thu Mar 17 15:35:56 CST 2016
     */
    public Long getModifUser() {
        return modifUser;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method sets the value of the database
     * column ACTIVITY_SUPPLIER_ENTRY.MODIF_USER
     *
     * @param modifUser the value for ACTIVITY_SUPPLIER_ENTRY.MODIF_USER
     *
     * @ibatorgenerated Thu Mar 17 15:35:56 CST 2016
     */
    public void setModifUser(Long modifUser) {
        this.modifUser = modifUser;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method returns the value of the
     * database column ACTIVITY_SUPPLIER_ENTRY.ENTRY_USER
     *
     * @return the value of ACTIVITY_SUPPLIER_ENTRY.ENTRY_USER
     *
     * @ibatorgenerated Thu Mar 17 15:35:56 CST 2016
     */
    public Long getEntryUser() {
        return entryUser;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method sets the value of the database
     * column ACTIVITY_SUPPLIER_ENTRY.ENTRY_USER
     *
     * @param entryUser the value for ACTIVITY_SUPPLIER_ENTRY.ENTRY_USER
     *
     * @ibatorgenerated Thu Mar 17 15:35:56 CST 2016
     */
    public void setEntryUser(Long entryUser) {
        this.entryUser = entryUser;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method returns the value of the
     * database column ACTIVITY_SUPPLIER_ENTRY.AUDIT_USER
     *
     * @return the value of ACTIVITY_SUPPLIER_ENTRY.AUDIT_USER
     *
     * @ibatorgenerated Thu Mar 17 15:35:56 CST 2016
     */
    public Long getAuditUser() {
        return auditUser;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method sets the value of the database
     * column ACTIVITY_SUPPLIER_ENTRY.AUDIT_USER
     *
     * @param auditUser the value for ACTIVITY_SUPPLIER_ENTRY.AUDIT_USER
     *
     * @ibatorgenerated Thu Mar 17 15:35:56 CST 2016
     */
    public void setAuditUser(Long auditUser) {
        this.auditUser = auditUser;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method returns the value of the
     * database column ACTIVITY_SUPPLIER_ENTRY.REMARK
     *
     * @return the value of ACTIVITY_SUPPLIER_ENTRY.REMARK
     *
     * @ibatorgenerated Thu Mar 17 15:35:56 CST 2016
     */
    public String getRemark() {
        return remark;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method sets the value of the database
     * column ACTIVITY_SUPPLIER_ENTRY.REMARK
     *
     * @param remark the value for ACTIVITY_SUPPLIER_ENTRY.REMARK
     *
     * @ibatorgenerated Thu Mar 17 15:35:56 CST 2016
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<ActivitySku> getActivitySkuList() {
        return activitySkuList;
    }

    public void setActivitySkuList(List<ActivitySku> activitySkuList) {
        this.activitySkuList = activitySkuList;
    }

    public BigDecimal getTotalPayAmount() {
        return totalPayAmount;
    }

    public void setTotalPayAmount(BigDecimal totalPayAmount) {
        this.totalPayAmount = totalPayAmount;
    }

    public Timestamp getCreateStartTime() {
        return createStartTime;
    }

    public void setCreateStartTime(Timestamp createStartTime) {
        this.createStartTime = createStartTime;
    }

    public Timestamp getCreateEndTime() {
        return createEndTime;
    }

    public void setCreateEndTime(Timestamp createEndTime) {
        this.createEndTime = createEndTime;
    }

    public List<ActivityPayment> getActivityPaymentList() {
        return activityPaymentList;
    }

    public void setActivityPaymentList(List<ActivityPayment> activityPaymentList) {
        this.activityPaymentList = activityPaymentList;
    }

    public BigDecimal getResidualActivityPrice() {
        return residualActivityPrice;
    }

    public void setResidualActivityPrice(BigDecimal residualActivityPrice) {
        this.residualActivityPrice = residualActivityPrice;
    }

    public Integer getTotalSalesQuantity() {
        return totalSalesQuantity;
    }

    public void setTotalSalesQuantity(Integer totalSalesQuantity) {
        this.totalSalesQuantity = totalSalesQuantity;
    }

}
