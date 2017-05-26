package com.pltfm.app.vobject;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 功能：
 *
 * @author Zhoujiwei
 * @since 2016/8/30 14:46
 */
public class SuppliersRecheck implements Serializable {

    private static final long serialVersionUID = -6401791472282323646L;

    private Long supplierId;

    /**
     * 重审状态 0：待申请重审 1：重审中 2：重审通过 3：重审不通过
     */
    private Integer status;

    /**
     * 重审申请时间
     */
    private Timestamp applyTime;

    /**
     * 资质重审不过原因
     */
    private String reason;

    /**
     * 是否关闭类目和下架商品  0未处理；1已处理
     */
    private Integer isClose;

    /**
     * 三证合一 1：未进行三证合一 2：已进行三证合一
     */
    private Integer threeIdType;

    /**
     * 企业类型  1：食品经营者 2：食品生产者 3：食品农产品
     */
    private Integer supplierType;

    /**
     * 食品经营者证件更新 0：空 1：已更换新证件（有食品经营许可证） 2：未更换新证件，有食品流通许可证或卫生许可证
     */
    private Integer businessType;

    private Long checkUser;

    private String checkUserName;

    private Timestamp checkTime;

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Timestamp getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Timestamp applyTime) {
        this.applyTime = applyTime;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Integer getThreeIdType() {
        return threeIdType;
    }

    public void setThreeIdType(Integer threeIdType) {
        this.threeIdType = threeIdType;
    }

    public Integer getSupplierType() {
        return supplierType;
    }

    public void setSupplierType(Integer supplierType) {
        this.supplierType = supplierType;
    }

    public Integer getBusinessType() {
        return businessType;
    }

    public void setBusinessType(Integer businessType) {
        this.businessType = businessType;
    }

    public Long getCheckUser() {
        return checkUser;
    }

    public void setCheckUser(Long checkUser) {
        this.checkUser = checkUser;
    }

    public String getCheckUserName() {
        return checkUserName;
    }

    public void setCheckUserName(String checkUserName) {
        this.checkUserName = checkUserName;
    }

    public Timestamp getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(Timestamp checkTime) {
        this.checkTime = checkTime;
    }

    public Integer getIsClose() {
        return isClose;
    }

    public void setIsClose(Integer isClose) {
        this.isClose = isClose;
    }

    @Override
    public String toString() {
        return "SuppliersRecheck{" +
                "supplierId=" + supplierId +
                ", status=" + status +
                ", applyTime=" + applyTime +
                ", reason='" + reason + '\'' +
                ", isClose=" + isClose +
                ", threeIdType=" + threeIdType +
                ", supplierType=" + supplierType +
                ", businessType=" + businessType +
                ", checkUser=" + checkUser +
                ", checkUserName='" + checkUserName + '\'' +
                ", checkTime=" + checkTime +
                '}';
    }
}
