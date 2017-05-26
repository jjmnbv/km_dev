package com.pltfm.app.entities;

import java.io.Serializable;
import java.util.Date;

public class ExportInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    
    /**
     * 主键ID
     */
    private Long exportId;
    /**
     * 导出时间
     */
    private Date createDate;
    /**
     * 操作人
     */
    private String operator;
    /**
     * 报表类型 -- 属于什么报表 0代表商城报表；1代表入驻商家结算报表
     */
    private String exportType;
    /**
     * 报表状态 0代表生成中，1代表已生成
     */
    private String exportStatus;
    /**
     * 报表导出条件信息
     */
    private String exportContent;
    /**
     * 报表访问地址
     */
    private String url;
    public Long getExportId() {
        return exportId;
    }
    public void setExportId(Long exportId) {
        this.exportId = exportId;
    }
    public Date getCreateDate() {
        return createDate;
    }
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    public String getOperator() {
        return operator;
    }
    public void setOperator(String operator) {
        this.operator = operator;
    }
    public String getExportType() {
        return exportType;
    }
    public void setExportType(String exportType) {
        this.exportType = exportType;
    }
    public String getExportStatus() {
        return exportStatus;
    }
    public void setExportStatus(String exportStatus) {
        this.exportStatus = exportStatus;
    }
    public String getExportContent() {
        return exportContent;
    }
    public void setExportContent(String exportContent) {
        this.exportContent = exportContent;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public static long getSerialversionuid() {
        return serialVersionUID;
    }
}
