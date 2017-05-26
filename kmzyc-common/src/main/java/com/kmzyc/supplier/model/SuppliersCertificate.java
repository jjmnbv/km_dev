package com.kmzyc.supplier.model;

import java.io.Serializable;

/**
 * 供应商资质文件实体类
 * @createDate 2013/12/25
 * @author luoyi
 */

public class SuppliersCertificate implements Serializable {
	private static final long serialVersionUID = -8109669629808992000L;
	/**
     * 资质文件ID
     */
    private Long scId;

    /**
     * 供应商ID
     */
    private Long supplierId;

    /**
     * 医药资质文件类型
     */
    private String docType;

    /**
     * 文件编号
     */
    private String docCode;

    /**
     * 文件路径
     */
    private String filePath;
    
    /**
     * 文件名称
     */
    private String fileName;
    

    /**
     * 以下为set和get方法
     */
    public Long getScId() {
        return scId;
    }

    public void setScId(Long scId) {
        this.scId = scId;
    }
    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }
    public String getDocCode() {
        return docCode;
    }

    public void setDocCode(String docCode) {
        this.docCode = docCode;
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

	@Override
	public String toString() {
		return "SuppliersCertificate [scId=" + scId + ", supplierId="
				+ supplierId + ", docType=" + docType + ", docCode=" + docCode
				+ ", filePath=" + filePath + ", fileName=" + fileName + "]";
	}

   

}