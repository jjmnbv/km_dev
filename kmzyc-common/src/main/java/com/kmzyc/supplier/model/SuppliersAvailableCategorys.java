package com.kmzyc.supplier.model;

/**
 * 供应商可用类目实体
 * 
 * @createDate 2013/12/25
 * @author luoyi
 */

import java.io.Serializable;
import java.math.BigDecimal;

public class SuppliersAvailableCategorys implements Serializable {

    private static final long serialVersionUID = -4549822433741158117L;
    /**
     * 供应商可用类目ID
     */
    private Long sacId;

    /**
     * 供应商ID
     */
    private Long supplierId;

    /**
     * 类目ID
     */
    private Long categoryId;
    /**
     * 主类目id
     */
    private Long categoryParentId;
    /**
     * 类目名称
     */
    private String categoryName;// 非数据库字段

    /**
     * 佣金比例
     */
    private BigDecimal commissionRatio;

    /**
     * 供应商类目审核状态：0：不通过，1：通过
     */
    private Short status;
    /**
     * 资质认证关闭：0：否，1：是
     */
    private Short isClose;

    /**
     * 以下为set和get方法
     */

    public Long getCategoryParentId() {
        return categoryParentId;
    }

    public void setCategoryParentId(Long categoryParentId) {
        this.categoryParentId = categoryParentId;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public Short getIsClose() {
        return isClose;
    }

    public void setIsClose(Short isClose) {
        this.isClose = isClose;
    }

    public Long getSacId() {
        return sacId;
    }

    public void setSacId(Long sacId) {
        this.sacId = sacId;
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public BigDecimal getCommissionRatio() {
        return commissionRatio;
    }

    public void setCommissionRatio(BigDecimal commissionRatio) {
        this.commissionRatio = commissionRatio;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public String toString() {
        return "SuppliersAvailableCategorys [sacId=" + sacId + ", supplierId=" + supplierId
                + ", categoryId=" + categoryId + ", categoryName=" + categoryName
                + ", commissionRatio=" + commissionRatio + "]";
    }


}
