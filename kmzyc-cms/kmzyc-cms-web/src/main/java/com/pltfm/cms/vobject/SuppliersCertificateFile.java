/**
 * project : B2B 康美健康商城 module : km-cms-web package : com.pltfm.cms.vobject date:
 * 2016年9月14日上午10:52:09 Copyright (c) 2016, KM@km.com All Rights Reserved.
 */
package com.pltfm.cms.vobject;

import java.io.Serializable;
import java.util.Date;

/**
 * 供应商资质文件(新)<br/> 
 *
 * @author jerrmy
 * @date 2016年9月14日 上午10:52:09
 * @version
 * @see
 */
public class SuppliersCertificateFile implements Serializable {
    private static final long serialVersionUID = -8354161429183761135L;

    private Long id;
    /** 供应商资质文件信息ID */
    private Long infoId;
    /** 文件路径 */
    private String filePath;
    /** 文件名称 */
    private String fileName;
    /** 是否有效，0：无效，1：有效，2：删除 */
    private Long isValid;

    private Long createUser;
    private Date createTime;
    private Long modifUser;
    private Date modifTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getInfoId() {
        return infoId;
    }

    public void setInfoId(Long infoId) {
        this.infoId = infoId;
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

    public Long getIsValid() {
        return isValid;
    }

    public void setIsValid(Long isValid) {
        this.isValid = isValid;
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

    public static long getSerialversionuid() {
        return serialVersionUID;
    }
}
  