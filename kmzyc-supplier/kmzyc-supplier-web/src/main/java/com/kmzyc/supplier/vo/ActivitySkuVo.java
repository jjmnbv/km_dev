package com.kmzyc.supplier.vo;

import com.pltfm.app.vobject.ActivitySku;

import java.io.File;

/**
 * 功能：
 *
 * @author Zhoujiwei
 * @since 2016/3/25 13:59
 */
public class ActivitySkuVo extends ActivitySku {

    private static final long serialVersionUID = 6892722298555134515L;

    /**
     * 图文推广时图片名称
     */
    private String activitySkuImageFileName;

    /**
     * 图文推广时图片文件
     */
    private File activitySkuImageFile;

    private String activitySkuImageFileFileName;

    private String activitySkuImageFileContentType;

    /**
     * 库存
     */
    private Integer stock;

    private String remark;

    public String getActivitySkuImageFileFileName() {
        return activitySkuImageFileFileName;
    }

    public void setActivitySkuImageFileFileName(String activitySkuImageFileFileName) {
        this.activitySkuImageFileFileName = activitySkuImageFileFileName;
    }

    public String getActivitySkuImageFileName() {
        return activitySkuImageFileName;
    }

    public void setActivitySkuImageFileName(String activitySkuImageFileName) {
        this.activitySkuImageFileName = activitySkuImageFileName;
    }

    public File getActivitySkuImageFile() {
        return activitySkuImageFile;
    }

    public void setActivitySkuImageFile(File activitySkuImageFile) {
        this.activitySkuImageFile = activitySkuImageFile;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getActivitySkuImageFileContentType() {
        return activitySkuImageFileContentType;
    }

    public void setActivitySkuImageFileContentType(String activitySkuImageFileContentType) {
        this.activitySkuImageFileContentType = activitySkuImageFileContentType;
    }

    @Override
    public String getRemark() {
        return remark;
    }

    @Override
    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "ActivitySkuVo{" +
                "activitySkuImageFileName='" + activitySkuImageFileName + '\'' +
                ", activitySkuImageFile=" + activitySkuImageFile +
                ", activitySkuImageFileFileName='" + activitySkuImageFileFileName + '\'' +
                ", activitySkuImageFileContentType='" + activitySkuImageFileContentType + '\'' +
                ", stock=" + stock +
                ", remark='" + remark + '\'' +
                '}';
    }
}
