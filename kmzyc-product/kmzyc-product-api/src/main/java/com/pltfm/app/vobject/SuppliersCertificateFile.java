package com.pltfm.app.vobject;

import java.io.File;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 功能：
 *
 * @author Zhoujiwei
 * @since 2016/8/30 14:59
 */
public class SuppliersCertificateFile implements Serializable {

    private static final long serialVersionUID = 4393737858454393113L;

    private Long id;

    /**
     * 供应商资质文件信息ID
     */
    private Long infoId;

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

    private File file;

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

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    @Override
    public String toString() {
        return "SuppliersCertificateFile{" +
                "id=" + id +
                ", infoId=" + infoId +
                ", filePath='" + filePath + '\'' +
                ", fileName='" + fileName + '\'' +
                ", isValid=" + isValid +
                ", createUser=" + createUser +
                ", createTime=" + createTime +
                ", modifUser=" + modifUser +
                ", modifTime=" + modifTime +
                '}';
    }
}