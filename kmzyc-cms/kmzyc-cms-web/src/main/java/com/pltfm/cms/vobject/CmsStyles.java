package com.pltfm.cms.vobject;

import java.util.Date;

/**
 * 风格实体
 *
 * @author cjm
 * @since 2014-8-22
 */
public class CmsStyles {
    /**
     * 开始索引值
     */
    private Integer startIndex;
    /**
     * 结束索引值
     */
    private Integer endIndex;

    private String stylesJs;

    private String stylesCss;
    /**
     * 内容
     */
    private String content;
    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column CMS_STYLES.STYLES_ID
     *
     * @ibatorgenerated Fri Aug 22 10:49:25 CST 2014
     */
    private Integer stylesId;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column CMS_STYLES.TEMPLATE_ID
     *
     * @ibatorgenerated Fri Aug 22 10:49:25 CST 2014
     */
    private Integer templateId;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column CMS_STYLES.STYLES_NAME
     *
     * @ibatorgenerated Fri Aug 22 10:49:25 CST 2014
     */
    private String stylesName;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column CMS_STYLES.STYLES_DESCRIBE
     *
     * @ibatorgenerated Fri Aug 22 10:49:25 CST 2014
     */
    private String stylesDescribe;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column CMS_STYLES.SITE_ID
     *
     * @ibatorgenerated Fri Aug 22 10:49:25 CST 2014
     */
    private Integer siteId;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column CMS_STYLES.REMARK
     *
     * @ibatorgenerated Fri Aug 22 10:49:25 CST 2014
     */
    private String remark;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column CMS_STYLES.STATUS
     *
     * @ibatorgenerated Fri Aug 22 10:49:25 CST 2014
     */
    private Short status;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column CMS_STYLES.CREATE_DATE
     *
     * @ibatorgenerated Fri Aug 22 10:49:25 CST 2014
     */
    private Date createDate;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column CMS_STYLES.CREATED
     *
     * @ibatorgenerated Fri Aug 22 10:49:25 CST 2014
     */
    private Integer created;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column CMS_STYLES.MODIFY_DATE
     *
     * @ibatorgenerated Fri Aug 22 10:49:25 CST 2014
     */
    private Date modifyDate;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column CMS_STYLES.MODIFIED
     *
     * @ibatorgenerated Fri Aug 22 10:49:25 CST 2014
     */
    private Integer modified;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column CMS_STYLES.STYLES_ID
     *
     * @return the value of CMS_STYLES.STYLES_ID
     * @ibatorgenerated Fri Aug 22 10:49:25 CST 2014
     */
    public Integer getStylesId() {
        return stylesId;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column CMS_STYLES.STYLES_ID
     *
     * @param stylesId the value for CMS_STYLES.STYLES_ID
     * @ibatorgenerated Fri Aug 22 10:49:25 CST 2014
     */
    public void setStylesId(Integer stylesId) {
        this.stylesId = stylesId;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column CMS_STYLES.TEMPLATE_ID
     *
     * @return the value of CMS_STYLES.TEMPLATE_ID
     * @ibatorgenerated Fri Aug 22 10:49:25 CST 2014
     */
    public Integer getTemplateId() {
        return templateId;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column CMS_STYLES.TEMPLATE_ID
     *
     * @param templateId the value for CMS_STYLES.TEMPLATE_ID
     * @ibatorgenerated Fri Aug 22 10:49:25 CST 2014
     */
    public void setTemplateId(Integer templateId) {
        this.templateId = templateId;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column CMS_STYLES.STYLES_NAME
     *
     * @return the value of CMS_STYLES.STYLES_NAME
     * @ibatorgenerated Fri Aug 22 10:49:25 CST 2014
     */
    public String getStylesName() {
        return stylesName;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column CMS_STYLES.STYLES_NAME
     *
     * @param stylesName the value for CMS_STYLES.STYLES_NAME
     * @ibatorgenerated Fri Aug 22 10:49:25 CST 2014
     */
    public void setStylesName(String stylesName) {
        this.stylesName = stylesName;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column CMS_STYLES.STYLES_DESCRIBE
     *
     * @return the value of CMS_STYLES.STYLES_DESCRIBE
     * @ibatorgenerated Fri Aug 22 10:49:25 CST 2014
     */
    public String getStylesDescribe() {
        return stylesDescribe;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column CMS_STYLES.STYLES_DESCRIBE
     *
     * @param stylesDescribe the value for CMS_STYLES.STYLES_DESCRIBE
     * @ibatorgenerated Fri Aug 22 10:49:25 CST 2014
     */
    public void setStylesDescribe(String stylesDescribe) {
        this.stylesDescribe = stylesDescribe;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column CMS_STYLES.SITE_ID
     *
     * @return the value of CMS_STYLES.SITE_ID
     * @ibatorgenerated Fri Aug 22 10:49:25 CST 2014
     */
    public Integer getSiteId() {
        return siteId;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column CMS_STYLES.SITE_ID
     *
     * @param siteId the value for CMS_STYLES.SITE_ID
     * @ibatorgenerated Fri Aug 22 10:49:25 CST 2014
     */
    public void setSiteId(Integer siteId) {
        this.siteId = siteId;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column CMS_STYLES.REMARK
     *
     * @return the value of CMS_STYLES.REMARK
     * @ibatorgenerated Fri Aug 22 10:49:25 CST 2014
     */
    public String getRemark() {
        return remark;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column CMS_STYLES.REMARK
     *
     * @param remark the value for CMS_STYLES.REMARK
     * @ibatorgenerated Fri Aug 22 10:49:25 CST 2014
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column CMS_STYLES.STATUS
     *
     * @return the value of CMS_STYLES.STATUS
     * @ibatorgenerated Fri Aug 22 10:49:25 CST 2014
     */
    public Short getStatus() {
        return status;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column CMS_STYLES.STATUS
     *
     * @param status the value for CMS_STYLES.STATUS
     * @ibatorgenerated Fri Aug 22 10:49:25 CST 2014
     */
    public void setStatus(Short status) {
        this.status = status;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column CMS_STYLES.CREATE_DATE
     *
     * @return the value of CMS_STYLES.CREATE_DATE
     * @ibatorgenerated Fri Aug 22 10:49:25 CST 2014
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column CMS_STYLES.CREATE_DATE
     *
     * @param createDate the value for CMS_STYLES.CREATE_DATE
     * @ibatorgenerated Fri Aug 22 10:49:25 CST 2014
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column CMS_STYLES.CREATED
     *
     * @return the value of CMS_STYLES.CREATED
     * @ibatorgenerated Fri Aug 22 10:49:25 CST 2014
     */
    public Integer getCreated() {
        return created;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column CMS_STYLES.CREATED
     *
     * @param created the value for CMS_STYLES.CREATED
     * @ibatorgenerated Fri Aug 22 10:49:25 CST 2014
     */
    public void setCreated(Integer created) {
        this.created = created;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column CMS_STYLES.MODIFY_DATE
     *
     * @return the value of CMS_STYLES.MODIFY_DATE
     * @ibatorgenerated Fri Aug 22 10:49:25 CST 2014
     */
    public Date getModifyDate() {
        return modifyDate;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column CMS_STYLES.MODIFY_DATE
     *
     * @param modifyDate the value for CMS_STYLES.MODIFY_DATE
     * @ibatorgenerated Fri Aug 22 10:49:25 CST 2014
     */
    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column CMS_STYLES.MODIFIED
     *
     * @return the value of CMS_STYLES.MODIFIED
     * @ibatorgenerated Fri Aug 22 10:49:25 CST 2014
     */
    public Integer getModified() {
        return modified;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column CMS_STYLES.MODIFIED
     *
     * @param modified the value for CMS_STYLES.MODIFIED
     * @ibatorgenerated Fri Aug 22 10:49:25 CST 2014
     */
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

    public String getStylesJs() {
        return stylesJs;
    }

    public void setStylesJs(String stylesJs) {
        this.stylesJs = stylesJs;
    }

    public String getStylesCss() {
        return stylesCss;
    }

    public void setStylesCss(String stylesCss) {
        this.stylesCss = stylesCss;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}