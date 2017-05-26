package com.pltfm.cms.vobject;

import java.util.Date;

public class CmsInformation {
    private Integer inforId;

    private Integer typeId;

    private String name;

    private Integer siteId;

    private String content;

    private String title;

    private String key;

    private String description;

    private Integer status;

    private Date createDate;

    private Integer created;

    private Date modifyDate;

    private Integer modified;

    private Integer templateType;

    private Integer orders;

    private String inforname;
    private String InformContent;

    private String ImgUploading;
    /**
     * 开始索引值
     */
    private Integer startIndex;
    /**
     * 结束索引值
     */
    private Integer endIndex;

    private String Content_content;
    private Date publishDate;

    /**
     * 样式内容
     */
    private String contentCss;

    /**
     * 脚本内容
     */
    private String contentJs;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public Integer getInforId() {
        return inforId;
    }

    public void setInforId(Integer inforId) {
        this.inforId = inforId;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public Integer getCreated() {
        return created;
    }

    public void setCreated(Integer created) {
        this.created = created;
    }

    public Integer getModified() {
        return modified;
    }

    public void setModified(Integer modified) {
        this.modified = modified;
    }

    public Integer getOrders() {
        return orders;
    }

    public void setOrders(Integer orders) {
        this.orders = orders;
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

    public String getContent_content() {
        return Content_content;
    }

    public void setContent_content(String contentContent) {
        Content_content = contentContent;
    }

    public String getInforname() {
        return inforname;
    }

    public void setInforname(String inforname) {
        this.inforname = inforname;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getSiteId() {
        return siteId;
    }

    public void setSiteId(Integer siteId) {
        this.siteId = siteId;
    }

    public Integer getTemplateType() {
        return templateType;
    }

    public void setTemplateType(Integer templateType) {
        this.templateType = templateType;
    }

    public String getInformContent() {
        return InformContent;
    }

    public void setInformContent(String informContent) {
        InformContent = informContent;
    }

    public String getContentCss() {
        return contentCss;
    }

    public void setContentCss(String contentCss) {
        this.contentCss = contentCss;
    }

    public String getContentJs() {
        return contentJs;
    }

    public void setContentJs(String contentJs) {
        this.contentJs = contentJs;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public String getImgUploading() {
        return ImgUploading;
    }

    public void setImgUploading(String imgUploading) {
        ImgUploading = imgUploading;
    }


}