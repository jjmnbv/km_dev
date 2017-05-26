package com.pltfm.cms.vobject;

import java.io.Serializable;
import java.util.Date;

/**
 * 站点数据实体类
 *
 * @author cjm
 * @since 2013-11-18
 */
public class CmsSiteData implements Serializable {
    /**
     * 用户站点数据主键
     */
    private Integer userSiteDataId;

    /**
     * 用户主键
     */
    private Integer userId;


    /**
     * 站点主键
     */
    private Integer siteId;

    /**
     * 数据主键
     */
    private Integer dataId;

    /**
     * 数据类型
     */
    private Integer dataType;

    /**
     * 数据姓名
     */
    private String dataName;


    /**
     * 备注
     */
    private String remark;
    /**
     * 状态:0.有效1.无效
     */
    private Integer status;

    /**
     * 创建日期
     */
    private Date createDate;

    /**
     * 创建人
     */
    private Integer created;

    /**
     * 修改日期
     */
    private Date modifyDate;

    /**
     * 修改人
     */
    private Integer modified;

    /**
     * 开始索引值
     */
    private Integer startIndex;
    /**
     * 结束索引值
     */
    private Integer endIndex;


    public Integer getUserSiteDataId() {
        return userSiteDataId;
    }


    public void setUserSiteDataId(Integer userSiteDataId) {
        this.userSiteDataId = userSiteDataId;
    }


    public Integer getUserId() {
        return userId;
    }


    public void setUserId(Integer userId) {
        this.userId = userId;
    }


    public Integer getSiteId() {
        return siteId;
    }


    public void setSiteId(Integer siteId) {
        this.siteId = siteId;
    }


    public Integer getDataId() {
        return dataId;
    }


    public void setDataId(Integer dataId) {
        this.dataId = dataId;
    }


    public Integer getDataType() {
        return dataType;
    }


    public void setDataType(Integer dataType) {
        this.dataType = dataType;
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


    public String getDataName() {
        return dataName;
    }


    public void setDataName(String dataName) {
        this.dataName = dataName;
    }


}