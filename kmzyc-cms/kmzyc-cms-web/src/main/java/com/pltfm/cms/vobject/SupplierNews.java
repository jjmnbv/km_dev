package com.pltfm.cms.vobject;

import java.util.Arrays;
import java.util.Date;

/***
 * 供应商资讯
 * */
public class SupplierNews {

    private Long newsId;

    private Long supplierId;

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column SUPPLIER_NEWS.NEWS_CATEGORY_ID
     *
     * @ibatorgenerated Mon Apr 14 10:32:41 CST 2014
     */
    private Long newsCategoryId;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column SUPPLIER_NEWS.NEWS_TITLE
     *
     * @ibatorgenerated Mon Apr 14 10:32:41 CST 2014
     */
    private String newsTitle;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column SUPPLIER_NEWS.NEWS_SEO
     *
     * @ibatorgenerated Mon Apr 14 10:32:41 CST 2014
     */
    private String newsSeo;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column SUPPLIER_NEWS.VISIT_NUM
     *
     * @ibatorgenerated Mon Apr 14 10:32:41 CST 2014
     */
    private Long visitNum;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column SUPPLIER_NEWS.appraise_NUM
     *
     * @ibatorgenerated Mon Apr 14 10:32:41 CST 2014
     */
    private Long appraiseNum;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column SUPPLIER_NEWS.STATUS
     *
     * @ibatorgenerated Mon Apr 14 10:32:41 CST 2014
     */
    private String status;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column SUPPLIER_NEWS.AUDIT_STATUS
     *
     * @ibatorgenerated Mon Apr 14 10:32:41 CST 2014
     */
    private String auditStatus;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column SUPPLIER_NEWS.AUDIT_USER
     *
     * @ibatorgenerated Mon Apr 14 10:32:41 CST 2014
     */
    private Long auditUser;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column SUPPLIER_NEWS.AUDIT_TIME
     *
     * @ibatorgenerated Mon Apr 14 10:32:41 CST 2014
     */
    private Date auditTime;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column SUPPLIER_NEWS.CREATE_USER
     *
     * @ibatorgenerated Mon Apr 14 10:32:41 CST 2014
     */
    private Long createUser;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column SUPPLIER_NEWS.CREATE_TIME
     *
     * @ibatorgenerated Mon Apr 14 10:32:41 CST 2014
     */
    private Date createTime;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column SUPPLIER_NEWS.NEWS_CONTENT
     *
     * @ibatorgenerated Mon Apr 14 10:32:41 CST 2014
     */
    private byte[] newsContent;
    /**
     * 开始索引值
     */
    private Long startIndex;
    /**
     * 结束索引值
     */
    private Long endIndex;

    public Long getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(Long startIndex) {
        this.startIndex = startIndex;
    }

    public Long getEndIndex() {
        return endIndex;
    }

    public void setEndIndex(Long endIndex) {
        this.endIndex = endIndex;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column SUPPLIER_NEWS.NEWS_ID
     *
     * @return the value of SUPPLIER_NEWS.NEWS_ID
     * @ibatorgenerated Mon Apr 14 10:32:41 CST 2014
     */
    public Long getNewsId() {
        return newsId;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column SUPPLIER_NEWS.NEWS_ID
     *
     * @param newsId the value for SUPPLIER_NEWS.NEWS_ID
     * @ibatorgenerated Mon Apr 14 10:32:41 CST 2014
     */
    public void setNewsId(Long newsId) {
        this.newsId = newsId;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column SUPPLIER_NEWS.NEWS_CATEGORY_ID
     *
     * @return the value of SUPPLIER_NEWS.NEWS_CATEGORY_ID
     * @ibatorgenerated Mon Apr 14 10:32:41 CST 2014
     */
    public Long getNewsCategoryId() {
        return newsCategoryId;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column SUPPLIER_NEWS.NEWS_CATEGORY_ID
     *
     * @param newsCategoryId the value for SUPPLIER_NEWS.NEWS_CATEGORY_ID
     * @ibatorgenerated Mon Apr 14 10:32:41 CST 2014
     */
    public void setNewsCategoryId(Long newsCategoryId) {
        this.newsCategoryId = newsCategoryId;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column SUPPLIER_NEWS.NEWS_TITLE
     *
     * @return the value of SUPPLIER_NEWS.NEWS_TITLE
     * @ibatorgenerated Mon Apr 14 10:32:41 CST 2014
     */
    public String getNewsTitle() {
        return newsTitle;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column SUPPLIER_NEWS.NEWS_TITLE
     *
     * @param newsTitle the value for SUPPLIER_NEWS.NEWS_TITLE
     * @ibatorgenerated Mon Apr 14 10:32:41 CST 2014
     */
    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column SUPPLIER_NEWS.NEWS_SEO
     *
     * @return the value of SUPPLIER_NEWS.NEWS_SEO
     * @ibatorgenerated Mon Apr 14 10:32:41 CST 2014
     */
    public String getNewsSeo() {
        return newsSeo;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column SUPPLIER_NEWS.NEWS_SEO
     *
     * @param newsSeo the value for SUPPLIER_NEWS.NEWS_SEO
     * @ibatorgenerated Mon Apr 14 10:32:41 CST 2014
     */
    public void setNewsSeo(String newsSeo) {
        this.newsSeo = newsSeo;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column SUPPLIER_NEWS.VISIT_NUM
     *
     * @return the value of SUPPLIER_NEWS.VISIT_NUM
     * @ibatorgenerated Mon Apr 14 10:32:41 CST 2014
     */
    public Long getVisitNum() {
        return visitNum;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column SUPPLIER_NEWS.VISIT_NUM
     *
     * @param visitNum the value for SUPPLIER_NEWS.VISIT_NUM
     * @ibatorgenerated Mon Apr 14 10:32:41 CST 2014
     */
    public void setVisitNum(Long visitNum) {
        this.visitNum = visitNum;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column SUPPLIER_NEWS.appraise_NUM
     *
     * @return the value of SUPPLIER_NEWS.appraise_NUM
     * @ibatorgenerated Mon Apr 14 10:32:41 CST 2014
     */
    public Long getAppraiseNum() {
        return appraiseNum;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column SUPPLIER_NEWS.appraise_NUM
     *
     * @param appraiseNum the value for SUPPLIER_NEWS.appraise_NUM
     * @ibatorgenerated Mon Apr 14 10:32:41 CST 2014
     */
    public void setAppraiseNum(Long appraiseNum) {
        this.appraiseNum = appraiseNum;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column SUPPLIER_NEWS.STATUS
     *
     * @return the value of SUPPLIER_NEWS.STATUS
     * @ibatorgenerated Mon Apr 14 10:32:41 CST 2014
     */
    public String getStatus() {
        return status;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column SUPPLIER_NEWS.STATUS
     *
     * @param status the value for SUPPLIER_NEWS.STATUS
     * @ibatorgenerated Mon Apr 14 10:32:41 CST 2014
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column SUPPLIER_NEWS.AUDIT_STATUS
     *
     * @return the value of SUPPLIER_NEWS.AUDIT_STATUS
     * @ibatorgenerated Mon Apr 14 10:32:41 CST 2014
     */
    public String getAuditStatus() {
        return auditStatus;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column SUPPLIER_NEWS.AUDIT_STATUS
     *
     * @param auditStatus the value for SUPPLIER_NEWS.AUDIT_STATUS
     * @ibatorgenerated Mon Apr 14 10:32:41 CST 2014
     */
    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column SUPPLIER_NEWS.AUDIT_USER
     *
     * @return the value of SUPPLIER_NEWS.AUDIT_USER
     * @ibatorgenerated Mon Apr 14 10:32:41 CST 2014
     */
    public Long getAuditUser() {
        return auditUser;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column SUPPLIER_NEWS.AUDIT_USER
     *
     * @param auditUser the value for SUPPLIER_NEWS.AUDIT_USER
     * @ibatorgenerated Mon Apr 14 10:32:41 CST 2014
     */
    public void setAuditUser(Long auditUser) {
        this.auditUser = auditUser;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column SUPPLIER_NEWS.AUDIT_TIME
     *
     * @return the value of SUPPLIER_NEWS.AUDIT_TIME
     * @ibatorgenerated Mon Apr 14 10:32:41 CST 2014
     */
    public Date getAuditTime() {
        return auditTime;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column SUPPLIER_NEWS.AUDIT_TIME
     *
     * @param auditTime the value for SUPPLIER_NEWS.AUDIT_TIME
     * @ibatorgenerated Mon Apr 14 10:32:41 CST 2014
     */
    public void setAuditTime(Date auditTime) {
        this.auditTime = auditTime;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column SUPPLIER_NEWS.CREATE_USER
     *
     * @return the value of SUPPLIER_NEWS.CREATE_USER
     * @ibatorgenerated Mon Apr 14 10:32:41 CST 2014
     */
    public Long getCreateUser() {
        return createUser;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column SUPPLIER_NEWS.CREATE_USER
     *
     * @param createUser the value for SUPPLIER_NEWS.CREATE_USER
     * @ibatorgenerated Mon Apr 14 10:32:41 CST 2014
     */
    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column SUPPLIER_NEWS.CREATE_TIME
     *
     * @return the value of SUPPLIER_NEWS.CREATE_TIME
     * @ibatorgenerated Mon Apr 14 10:32:41 CST 2014
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column SUPPLIER_NEWS.CREATE_TIME
     *
     * @param createTime the value for SUPPLIER_NEWS.CREATE_TIME
     * @ibatorgenerated Mon Apr 14 10:32:41 CST 2014
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column SUPPLIER_NEWS.NEWS_CONTENT
     *
     * @return the value of SUPPLIER_NEWS.NEWS_CONTENT
     * @ibatorgenerated Mon Apr 14 10:32:41 CST 2014
     */
    public byte[] getNewsContent() {
        return newsContent;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column SUPPLIER_NEWS.NEWS_CONTENT
     *
     * @param newsContent the value for SUPPLIER_NEWS.NEWS_CONTENT
     * @ibatorgenerated Mon Apr 14 10:32:41 CST 2014
     */
    public void setNewsContent(byte[] newsContent) {
        this.newsContent = newsContent;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((appraiseNum == null) ? 0 : appraiseNum.hashCode());
        result = prime * result
                + ((auditStatus == null) ? 0 : auditStatus.hashCode());
        result = prime * result
                + ((auditTime == null) ? 0 : auditTime.hashCode());
        result = prime * result
                + ((auditUser == null) ? 0 : auditUser.hashCode());
        result = prime * result
                + ((createTime == null) ? 0 : createTime.hashCode());
        result = prime * result
                + ((createUser == null) ? 0 : createUser.hashCode());
        result = prime * result
                + ((endIndex == null) ? 0 : endIndex.hashCode());
        result = prime * result
                + ((newsCategoryId == null) ? 0 : newsCategoryId.hashCode());
        result = prime * result + Arrays.hashCode(newsContent);
        result = prime * result + ((newsId == null) ? 0 : newsId.hashCode());
        result = prime * result + ((newsSeo == null) ? 0 : newsSeo.hashCode());
        result = prime * result
                + ((newsTitle == null) ? 0 : newsTitle.hashCode());
        result = prime * result
                + ((startIndex == null) ? 0 : startIndex.hashCode());
        result = prime * result + ((status == null) ? 0 : status.hashCode());
        result = prime * result
                + ((supplierId == null) ? 0 : supplierId.hashCode());
        result = prime * result
                + ((visitNum == null) ? 0 : visitNum.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        SupplierNews other = (SupplierNews) obj;
        if (appraiseNum == null) {
            if (other.appraiseNum != null)
                return false;
        } else if (!appraiseNum.equals(other.appraiseNum))
            return false;
        if (auditStatus == null) {
            if (other.auditStatus != null)
                return false;
        } else if (!auditStatus.equals(other.auditStatus))
            return false;
        if (auditTime == null) {
            if (other.auditTime != null)
                return false;
        } else if (!auditTime.equals(other.auditTime))
            return false;
        if (auditUser == null) {
            if (other.auditUser != null)
                return false;
        } else if (!auditUser.equals(other.auditUser))
            return false;
        if (createTime == null) {
            if (other.createTime != null)
                return false;
        } else if (!createTime.equals(other.createTime))
            return false;
        if (createUser == null) {
            if (other.createUser != null)
                return false;
        } else if (!createUser.equals(other.createUser))
            return false;
        if (endIndex == null) {
            if (other.endIndex != null)
                return false;
        } else if (!endIndex.equals(other.endIndex))
            return false;
        if (newsCategoryId == null) {
            if (other.newsCategoryId != null)
                return false;
        } else if (!newsCategoryId.equals(other.newsCategoryId))
            return false;
        if (!Arrays.equals(newsContent, other.newsContent))
            return false;
        if (newsId == null) {
            if (other.newsId != null)
                return false;
        } else if (!newsId.equals(other.newsId))
            return false;
        if (newsSeo == null) {
            if (other.newsSeo != null)
                return false;
        } else if (!newsSeo.equals(other.newsSeo))
            return false;
        if (newsTitle == null) {
            if (other.newsTitle != null)
                return false;
        } else if (!newsTitle.equals(other.newsTitle))
            return false;
        if (startIndex == null) {
            if (other.startIndex != null)
                return false;
        } else if (!startIndex.equals(other.startIndex))
            return false;
        if (status == null) {
            if (other.status != null)
                return false;
        } else if (!status.equals(other.status))
            return false;
        if (supplierId == null) {
            if (other.supplierId != null)
                return false;
        } else if (!supplierId.equals(other.supplierId))
            return false;
        if (visitNum == null) {
            if (other.visitNum != null)
                return false;
        } else if (!visitNum.equals(other.visitNum))
            return false;
        return true;
    }

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column SUPPLIER_NEWS.NEWS_ID
     *
     * @ibatorgenerated Mon Apr 14 10:32:41 CST 2014
     */
}