package com.pltfm.cms.vobject;

import java.util.Date;
/*
 * 供应商广位关系实体
 */

public class CmsSupplierAdcolumn {
    Integer supplierAdcolumnId;//供应商广告位置
    Integer supplierId;//供应商广告位置主键
    String supplierName;
    Integer adcolumnId;//供应商主键
    String adcolumnName;
    String contentPath;//广告位置主键
    String siteCode;//内容路径
    String remark;//站点主键
    Integer status;
    Date createDate;
    Integer created;
    Date modifyDate;
    Integer modified;
    /**
     * 开始索引值
     */
    private Integer startIndex;
    /**
     * 结束索引值
     */
    private Integer endIndex;
    /**
     * 文件内容
     */
    private String content;

    public Integer getSupplierAdcolumnId() {
        return supplierAdcolumnId;
    }

    public void setSupplierAdcolumnId(Integer supplierAdcolumnId) {
        this.supplierAdcolumnId = supplierAdcolumnId;
    }

    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }

    public Integer getAdcolumnId() {
        return adcolumnId;
    }

    public void setAdcolumnId(Integer adcolumnId) {
        this.adcolumnId = adcolumnId;
    }

    public String getContentPath() {
        return contentPath;
    }

    public void setContentPath(String contentPath) {
        this.contentPath = contentPath;
    }

    public String getSiteCode() {
        return siteCode;
    }

    public void setSiteCode(String siteCode) {
        this.siteCode = siteCode;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getCreated() {
        return created;
    }

    public void setCreated(Integer created) {
        this.created = created;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public Integer getModified() {
        return modified;
    }

    public void setModified(Integer modified) {
        this.modified = modified;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getAdcolumnName() {
        return adcolumnName;
    }

    public void setAdcolumnName(String adcolumnName) {
        this.adcolumnName = adcolumnName;
    }


}
