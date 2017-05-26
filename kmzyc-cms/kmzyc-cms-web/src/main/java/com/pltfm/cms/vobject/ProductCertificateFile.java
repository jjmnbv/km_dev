/**
 * project : B2B 康美健康商城 module : km-cms-web package : com.pltfm.cms.vobject date:
 * 2016年9月14日下午1:06:39 Copyright (c) 2016, KM@km.com All Rights Reserved.
 */
package com.pltfm.cms.vobject;

import java.io.Serializable;
import java.util.Date;

/**
 * 产品正式资质文件<br/> 
 *
 * @author jerrmy
 * @date 2016年9月14日 下午1:06:39
 * @version
 * @see
 */
public class ProductCertificateFile implements Serializable {
    private static final long serialVersionUID = 7723331880616239650L;

    private Long id;
    /** 产品ID */
    private Long productId;
    /** 证书编号 */
    private String fileCode;
    /** 有效时间至 */
    private Date validTime;
    /** 文件路径 */
    private String filePath;
    /** 文件名称 */
    private String fileName;
    /** 是否有效，0：无效，1：有效，2：删除 */
    private String isValid;
    /** 广告审查批准文号 */
    private String advertApprovalNo;

    private String createUser;
    private String createTime;
    private String modifUser;
    private String modifTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Date getValidTime() {
        return validTime;
    }

    public void setValidTime(Date validTime) {
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

    public String getIsValid() {
        return isValid;
    }

    public void setIsValid(String isValid) {
        this.isValid = isValid;
    }

    public String getAdvertApprovalNo() {
        return advertApprovalNo;
    }

    public void setAdvertApprovalNo(String advertApprovalNo) {
        this.advertApprovalNo = advertApprovalNo;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getModifUser() {
        return modifUser;
    }

    public void setModifUser(String modifUser) {
        this.modifUser = modifUser;
    }

    public String getModifTime() {
        return modifTime;
    }

    public void setModifTime(String modifTime) {
        this.modifTime = modifTime;
    }

}
  