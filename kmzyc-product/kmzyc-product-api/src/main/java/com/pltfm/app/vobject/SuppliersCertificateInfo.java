package com.pltfm.app.vobject;

import java.io.File;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * 功能：
 *
 * @author Zhoujiwei
 * @since 2016/8/30 14:51
 */
public class SuppliersCertificateInfo implements Serializable {

    private static final long serialVersionUID = -3772999506307731924L;

    private Long infoId;

    private Long supplierId;

    /**
     * 证书编号
     含：
     营业执照统一社会信用代码
     营业执照注册号
     组织机构代码证号
     法人身份证件号码
     税务登记证号

     食品经营许可证编号
     食品流通许可证
     卫生许可证号

     食品生产者的证书编号
     */
    private String fileCode;

    /**
     * 营业范围
     */
    private String businessScope;

    /**
     * 法定代表人
     */
    private String enterpriseLegalRepresentativ;

    /**
     * 法人身份证件号码
     */
    private String certificateNumber;

    /**
     * 资质文件类型:
     1：三证合一
     2：营业执照电子版
     3：组织机构代码证电子版
     4：税务登记证电子版
     5：法人身份证电子版
     6：食品经营许可证电子版
     7：食品安全管理人员相关证件电子版
     8：食品流通许可证电子版
     9：卫生许可证
     10：食品生产许可证或GMP资质认证
     */
    private Integer certificateType;

    /**
     * 是否长期：0：不是，1：是
     */
    private Integer longTerm;

    /**
     * 有效期开始时间
     */
    private Date validBeginTime;

    /**
     * 有效期结束时间
     */
    private Date validEndTime;

    /**
     * 食品生产许可证或GMP资质认证的有效期至
     */
    private Date validTime;

    /**
     * 验证状态：0：未验证，1：验证通过，2：验证不通过
     */
    private Integer status;

    private Long createUser;

    private Timestamp createTime;

    private Long modifUser;

    private Timestamp modifTime;

    private File file;

    private String fileNameExt;

    private List<SuppliersCertificateFile> fileList;

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

    public Integer getCertificateType() {
        return certificateType;
    }

    public void setCertificateType(Integer certificateType) {
        this.certificateType = certificateType;
    }

    public Integer getLongTerm() {
        return longTerm;
    }

    public void setLongTerm(Integer longTerm) {
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Long getModifUser() {
        return modifUser;
    }

    public void setModifUser(Long modifUser) {
        this.modifUser = modifUser;
    }

    public Timestamp getModifTime() {
        return modifTime;
    }

    public void setModifTime(Timestamp modifTime) {
        this.modifTime = modifTime;
    }

    public List<SuppliersCertificateFile> getFileList() {
        return fileList;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public void setFileList(List<SuppliersCertificateFile> fileList) {
        this.fileList = fileList;
    }

    public String getFileNameExt() {
        return fileNameExt;
    }

    public void setFileNameExt(String fileNameExt) {
        this.fileNameExt = fileNameExt;
    }

    @Override
    public String toString() {
        return "SuppliersCertificateInfo{" +
                "infoId=" + infoId +
                ", supplierId=" + supplierId +
                ", fileCode='" + fileCode + '\'' +
                ", businessScope='" + businessScope + '\'' +
                ", enterpriseLegalRepresentativ='" + enterpriseLegalRepresentativ + '\'' +
                ", certificateNumber='" + certificateNumber + '\'' +
                ", certificateType=" + certificateType +
                ", longTerm=" + longTerm +
                ", validBeginTime=" + validBeginTime +
                ", validEndTime=" + validEndTime +
                ", validTime=" + validTime +
                ", status=" + status +
                ", createUser=" + createUser +
                ", createTime=" + createTime +
                ", modifUser=" + modifUser +
                ", modifTime=" + modifTime +
                ", file=" + file +
                ", fileNameExt='" + fileNameExt + '\'' +
                ", fileList=" + fileList +
                '}';
    }
}