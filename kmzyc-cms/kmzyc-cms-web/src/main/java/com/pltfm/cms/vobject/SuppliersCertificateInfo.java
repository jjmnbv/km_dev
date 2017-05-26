/**
 * project : B2B 康美健康商城 module : km-cms-web package : com.pltfm.cms.vobject date:
 * 2016年9月14日上午9:55:48 Copyright (c) 2016, KM@km.com All Rights Reserved.
 */
package com.pltfm.cms.vobject;

import java.util.Date;
import java.util.List;

/**
 * 供应商资质信息<br/> 
 *
 * @author jerrmy
 * @date 2016年9月14日 上午9:55:48
 * @version
 * @see
 */
public class SuppliersCertificateInfo {

    private Long infoId;
    /** 供应商ID */
    private Long supplierId;
    /** 证书编号 */
    private String fileCode;
    /** 营业范围 */
    private String businessScope;
    /** 法定代表人 */
    private String enterpriseLegalRepresentativ;
    /** 法人身份证件号码 */
    private String certificateNumber;
    /** 资质文件类型:
     1：三证合一
     2：营业执照电子版
     3：组织机构代码证电子版
     4：税务登记证电子版
     5：法人身份证电子版
     6：食品经营许可证电子版
     7：食品安全管理人员相关证件电子版
     8：食品流通许可证电子版
     9：卫生许可证
     10：食品生产许可证或GMP资质认证 */
    private Long certificateType;
    /** 是否长期：0：不是，1：是 */
    private Long longTerm;
    /** 有效期开始时间 */
    private Date validBeginTime;
    /** 有效期结束时间 */
    private Date validEndTime;
    /** 食品生产许可证或GMP资质认证的有效期至 */
    private Date validTime;
    /** 验证状态：0：未验证，1：验证通过，2：验证不通过 */
    private Long status;
    private Long createUser;
    private Date createTime;
    private Long modifUser;
    private Date modifTime;

    /** 供应商资质文件 */
    private List<SuppliersCertificateFile> suppliersCertificateFiles;

    public Long getInfoId() {
        return infoId;
    }

    public void setInfoId(Long infoId) {
        this.infoId = infoId;
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public String getFileCode() {
        return fileCode;
    }

    public void setFileCode(String fileCode) {
        this.fileCode = fileCode;
    }

    public String getBusinessScope() {
        return businessScope;
    }

    public void setBusinessScope(String businessScope) {
        this.businessScope = businessScope;
    }

    public String getEnterpriseLegalRepresentativ() {
        return enterpriseLegalRepresentativ;
    }

    public void setEnterpriseLegalRepresentativ(String enterpriseLegalRepresentativ) {
        this.enterpriseLegalRepresentativ = enterpriseLegalRepresentativ;
    }

    public String getCertificateNumber() {
        return certificateNumber;
    }

    public void setCertificateNumber(String certificateNumber) {
        this.certificateNumber = certificateNumber;
    }

    public Long getCertificateType() {
        return certificateType;
    }

    public void setCertificateType(Long certificateType) {
        this.certificateType = certificateType;
    }

    public Long getLongTerm() {
        return longTerm;
    }

    public void setLongTerm(Long longTerm) {
        this.longTerm = longTerm;
    }

    public Date getValidBeginTime() {
        return validBeginTime;
    }

    public void setValidBeginTime(Date validBeginTime) {
        this.validBeginTime = validBeginTime;
    }

    public Date getValidEndTime() {
        return validEndTime;
    }

    public void setValidEndTime(Date validEndTime) {
        this.validEndTime = validEndTime;
    }

    public Date getValidTime() {
        return validTime;
    }

    public void setValidTime(Date validTime) {
        this.validTime = validTime;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public Long getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getModifUser() {
        return modifUser;
    }

    public void setModifUser(Long modifUser) {
        this.modifUser = modifUser;
    }

    public Date getModifTime() {
        return modifTime;
    }

    public void setModifTime(Date modifTime) {
        this.modifTime = modifTime;
    }

    public List<SuppliersCertificateFile> getSuppliersCertificateFiles() {
        return suppliersCertificateFiles;
    }

    public void setSuppliersCertificateFiles(List<SuppliersCertificateFile> suppliersCertificateFiles) {
        this.suppliersCertificateFiles = suppliersCertificateFiles;
    }
}
  