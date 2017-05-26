/**
 * project : B2B 康美健康商城 module : km-cms-web package : com.pltfm.cms.vobject date:
 * 2016年9月20日下午2:44:10 Copyright (c) 2016, KM@km.com All Rights Reserved.
 */
package com.pltfm.cms.vobject;

import java.util.Date;

/**
 * 供应商资质重审信息<br/> 
 *
 * @author jerrmy
 * @date 2016年9月20日 下午2:44:10
 * @version
 * @see
 */
public class SuppliersRecheck {
    /** 供应商ID */
    private Long supplierId;
    /** 重审状态 0：待申请重审 1：重审中 2：重审通过 3：重审不通过 */
    private Long status;
    /** 重审申请时间 */
    private Date applyTime;
    /** 资质重审不过原因 */
    private String reason;
    /** 是否关闭类目和下架商品  0未处理；1已处理 */
    private Long isClose;
    /** 三证合一 1：未进行三证合一 2：已进行三证合一 */
    private Long threeIdType;
    /** 企业类型 1：食品经营者 2：食品生产者 3：食品农产品 */
    private Long supplierType;
    /** 食品经营者证件更新 0：空 1：已更换新证件（有食品经营许可证） 2：未更换新证件，有食品流通许可证或卫生许可证 */
    private Long businessType;
    /** 审核人 */
    private Long checkUser;
    /** 审核时间 */
    private Date checkTime;

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Long getIsClose() {
        return isClose;
    }

    public void setIsClose(Long isClose) {
        this.isClose = isClose;
    }

    public Long getThreeIdType() {
        return threeIdType;
    }

    public void setThreeIdType(Long threeIdType) {
        this.threeIdType = threeIdType;
    }

    public Long getSupplierType() {
        return supplierType;
    }

    public void setSupplierType(Long supplierType) {
        this.supplierType = supplierType;
    }

    public Long getBusinessType() {
        return businessType;
    }

    public void setBusinessType(Long businessType) {
        this.businessType = businessType;
    }

    public Long getCheckUser() {
        return checkUser;
    }

    public void setCheckUser(Long checkUser) {
        this.checkUser = checkUser;
    }

    public Date getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(Date checkTime) {
        this.checkTime = checkTime;
    }
}
  