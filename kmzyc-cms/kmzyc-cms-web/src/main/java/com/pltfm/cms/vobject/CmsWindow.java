package com.pltfm.cms.vobject;

import java.io.Serializable;
import java.util.Date;

/**
 * 窗口实体类
 *
 * @author cjm
 * @since 2013-9-10
 */
public class CmsWindow implements Serializable {
    private static final long serialVersionUID = 209820429595675997L;

    /**
     * 窗口主键
     */
    private Integer windowId;

    /**
     * id集合
     */
    private String ids;

    /**
     * 模板主键
     */
    private Integer templateId;

    /***
     * 店铺模板
     * */
    private Integer shopType;

    /**
     * 窗口名称
     */
    private String name;

    /**
     * 窗口主题
     */
    private String theme;

    /**
     * 窗口路径
     */
    private String path;
    /**
     * 窗口绑定参数类型 0代表动态绑定参数  1代表通过数据绑定功能绑定数据
     */
    private Integer paramsType;
    /**
     * 备注
     */
    private String remark;

    /**
     * 状态
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
    /**
     * 窗口内容
     */
    private String content;
    /**
     * 页面主键
     */
    private Integer pageId;
    /**
     * 开始时间
     */
    private Date startTime;
    /**
     * 结束时间
     */
    private Date endTime;
    /**
     * 标志
     */
    private Integer flag;
    //站点
    private Integer siteId;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getWindowId() {
        return windowId;
    }

    public void setWindowId(Integer windowId) {
        this.windowId = windowId;
    }

    public Integer getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Integer templateId) {
        this.templateId = templateId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public Integer getShopType() {
        return shopType;
    }

    public void setShopType(Integer shopType) {
        this.shopType = shopType;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
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

    public Integer getParamsType() {
        return paramsType;
    }

    public void setParamsType(Integer paramsType) {
        this.paramsType = paramsType;
    }

    public Integer getPageId() {
        return pageId;
    }

    public void setPageId(Integer pageId) {
        this.pageId = pageId;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public Integer getSiteId() {
        return siteId;
    }

    public void setSiteId(Integer siteId) {
        this.siteId = siteId;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }


}