package com.pltfm.app.vobject;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 功能：产品草稿资质文件
 *
 * @author Zhoujiwei
 * @since 2016/8/30 15:29
 */
public class ProductCertificateFile implements Serializable {

    private static final long serialVersionUID = 3271269505278934330L;

    /**
     * 主键
     */
    private Long certificateFileId;
    // 广告审查批准文号
    private String advertApprovalNo;

    /**
     * 产品ID
     */
    private Long productId;

    /**
     * 证书编号
     */
    private String fileCode;

    /**
     * 有效时间至
     */
    private Timestamp validTime;

    /**
     * 文件路径
     */
    private String filePath;

    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 是否有效，0：无效，1：有效，2：删除
     */
    private Integer isValid;

    private Long createUser;

    private Timestamp createTime;

    private Long modifUser;

    private Timestamp modifTime;

    @Override
    public String toString() {
        return "ProductCertificateFile{" + "certificateFileId=" + certificateFileId
                + ", productId=" + productId + ", fileCode='" + fileCode + '\'' + ", validTime="
                + validTime + ", filePath='" + filePath + '\'' + ", fileName='" + fileName + '\''
                + ", isValid=" + isValid + ", createUser=" + createUser + ", createTime="
                + createTime + ", modifUser=" + modifUser + ", modifTime=" + modifTime + '}';
    }


    public Long getCertificateFileId() {
        return certificateFileId;
    }

    public void setCertificateFileId(Long certificateFileId) {
        this.certificateFileId = certificateFileId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getFileCode() {
        return fileCode;
    }

    public void setFileCode(String fileCode) {
        this.fileCode = fileCode;
    }

    public Timestamp getValidTime() {
        return validTime;
    }

    public void setValidTime(Timestamp validTime) {
        this.validTime = validTime;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
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

    public String getAdvertApprovalNo() {
        return advertApprovalNo;
    }

    public void setAdvertApprovalNo(String advertApprovalNo) {
        this.advertApprovalNo = advertApprovalNo;
    }

}
