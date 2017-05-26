package com.kmzyc.supplier.vo;

import com.pltfm.app.vobject.ActivityInfo;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

/**
 * 功能：
 *
 * @author Zhoujiwei
 * @since 2016/3/18 16:42
 */
public class ActivityInfoVo extends ActivityInfo {

    private static final long serialVersionUID = -1169884158992261341L;

    private BigDecimal fixedCharge;

    private BigDecimal singleCharge;

    private BigDecimal commissionRate;

    private Integer entryStatus;

    private Integer activityPaymentType;

    private Integer activityPaymentStatus;

    private Timestamp entryTime;

    private Integer supplierType;

    /**
     * 审核不通过原因
     */
    private String remark;

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

    public Integer getEntryStatus() {
        return entryStatus;
    }

    public void setEntryStatus(Integer entryStatus) {
        this.entryStatus = entryStatus;
    }

    public Integer getActivityPaymentType() {
        return activityPaymentType;
    }

    public void setActivityPaymentType(Integer activityPaymentType) {
        this.activityPaymentType = activityPaymentType;
    }

    public Integer getActivityPaymentStatus() {
        return activityPaymentStatus;
    }

    public void setActivityPaymentStatus(Integer activityPaymentStatus) {
        this.activityPaymentStatus = activityPaymentStatus;
    }

    public Timestamp getEntryTime() {
        Timestamp temp = entryTime;
        return temp;
    }

    public void setEntryTime(Timestamp entryTime) {
        this.entryTime = entryTime;
    }

    @Override
    public String getRemark() {
        return remark;
    }

    @Override
    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getSupplierType() {
        return supplierType;
    }

    public void setSupplierType(Integer supplierType) {
        this.supplierType = supplierType;
    }

    @Override
    public String toString() {
        return "ActivityInfoVo{" +
                "fixedCharge='" + fixedCharge + '\'' +
                ", singleCharge='" + singleCharge + '\'' +
                ", commissionRate='" + commissionRate + '\'' +
                ", entryStatus=" + entryStatus +
                ", activityPaymentType=" + activityPaymentType +
                ", activityPaymentStatus=" + activityPaymentStatus +
                ", entryTime=" + entryTime +
                ", supplierType=" + supplierType +
                ", remark='" + remark + '\'' +
                '}';
    }
}
