package com.pltfm.cms.vobject;

import java.io.Serializable;
import java.util.Date;

public class CmsAdv implements Serializable{
    /**
     * This field was generated by Abator for iBATIS.
     * This field corresponds to the database column CMS_ADV.ADV_ID
     *
     * @abatorgenerated Tue Sep 03 08:55:19 CST 2013
     */
    private Integer advId;

    /**
     * This field was generated by Abator for iBATIS.
     * This field corresponds to the database column CMS_ADV.ADCOLUMN_ID
     *
     * @abatorgenerated Tue Sep 03 08:55:19 CST 2013
     */
    private Integer adcolumnId;
    
    private Integer flag;
    

    /**
     * This field was generated by Abator for iBATIS.
     * This field corresponds to the database column CMS_ADV.NAME
     *
     * @abatorgenerated Tue Sep 03 08:55:19 CST 2013
     */
    private String name;

    /**
     * This field was generated by Abator for iBATIS.
     * This field corresponds to the database column CMS_ADV.URL
     *
     * @abatorgenerated Tue Sep 03 08:55:19 CST 2013
     */
    private String url;
    private String uploadAdvFile;
    
    private String url2;
    private String uploadAdvFile2;
    
    private String url3;
    private String uploadAdvFile3;
    
    private String url4;
    private String uploadAdvFile4;
    private String p2;
    private String p3;//照片路径
  
    private Integer types;
    /**
     * This field was generated by Abator for iBATIS.
     * This field corresponds to the database column CMS_ADV.BEGIN_TIME
     *
     * @abatorgenerated Tue Sep 03 08:55:19 CST 2013
     */
    private Date beginTime;
    
    private String beginTimeStr;

    /**
     * This field was generated by Abator for iBATIS.
     * This field corresponds to the database column CMS_ADV.END_TIME
     *
     * @abatorgenerated Tue Sep 03 08:55:19 CST 2013
     */
    private Date endTime;
    
    private String endTimeStr;

    /**
     * This field was generated by Abator for iBATIS.
     * This field corresponds to the database column CMS_ADV.UNIT_NAME
     *
     * @abatorgenerated Tue Sep 03 08:55:19 CST 2013
     */
    private String unitName;

    /**
     * This field was generated by Abator for iBATIS.
     * This field corresponds to the database column CMS_ADV.LINKMAN
     *
     * @abatorgenerated Tue Sep 03 08:55:19 CST 2013
     */
    private String linkman;

    /**
     * This field was generated by Abator for iBATIS.
     * This field corresponds to the database column CMS_ADV.CONTACT
     *
     * @abatorgenerated Tue Sep 03 08:55:19 CST 2013
     */
    private String contact;

    /**
     * This field was generated by Abator for iBATIS.
     * This field corresponds to the database column CMS_ADV.CLICK_COUNT
     *
     * @abatorgenerated Tue Sep 03 08:55:19 CST 2013
     */
    private Integer clickCount;

    /**
     * This field was generated by Abator for iBATIS.
     * This field corresponds to the database column CMS_ADV.REMARK
     *
     * @abatorgenerated Tue Sep 03 08:55:19 CST 2013
     */
    private String remark;

    /**
     * This field was generated by Abator for iBATIS.
     * This field corresponds to the database column CMS_ADV.STATUS
     *
     * @abatorgenerated Tue Sep 03 08:55:19 CST 2013
     */
    private Integer status;

    /**
     * This field was generated by Abator for iBATIS.
     * This field corresponds to the database column CMS_ADV.CREATE_DATE
     *
     * @abatorgenerated Tue Sep 03 08:55:19 CST 2013
     */
    private Date createDate;

    /**
     * This field was generated by Abator for iBATIS.
     * This field corresponds to the database column CMS_ADV.CREATED
     *
     * @abatorgenerated Tue Sep 03 08:55:19 CST 2013
     */
    private Integer created;

    /**
     * This field was generated by Abator for iBATIS.
     * This field corresponds to the database column CMS_ADV.MODIFY_DATE
     *
     * @abatorgenerated Tue Sep 03 08:55:19 CST 2013
     */
    private Date modifyDate;

    /**
     * This field was generated by Abator for iBATIS.
     * This field corresponds to the database column CMS_ADV.MODIFIED
     *
     * @abatorgenerated Tue Sep 03 08:55:19 CST 2013
     */
    private Integer modified;

    /**
     * This field was generated by Abator for iBATIS.
     * This field corresponds to the database column CMS_ADV.CONTENT
     *
     * @abatorgenerated Tue Sep 03 08:55:19 CST 2013
     */
    private String content;

    /**
     *  开始索引值 
     */
	private Integer startIndex;
	/** 
	 * 结束索引值 
	 */
	private Integer endIndex;
	/** 
	 * 广告位名
	 */
	private String adname;
	/** 
	 * 发布判断
	 */
    private Integer isPublish;
    
    /** 
	 * 发布判断
	 */
    private Integer siteId;
    
    /**
     * 店铺code
     */
    private String siteCode;
    
    /**
     * 供应商广告位置主键
     */
    private Integer  supplierId;
    
    /** 
	 * ID 集合
	 */
    private String  ids;

    private String url6;
    private String uploadAdvFile6;

    public String getUrl6() {
        return url6;
    }

    public void setUrl6(String url6) {
        this.url6 = url6;
    }

    public String getUploadAdvFile6() {
        return uploadAdvFile6;
    }

    public void setUploadAdvFile6(String uploadAdvFile6) {
        this.uploadAdvFile6 = uploadAdvFile6;
    }
    
    
    
    public String getName() {
        return name;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method sets the value of the database column CMS_ADV.NAME
     *
     * @param name the value for CMS_ADV.NAME
     *
     * @abatorgenerated Tue Sep 03 08:55:19 CST 2013
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method returns the value of the database column CMS_ADV.URL
     *
     * @return the value of CMS_ADV.URL
     *
     * @abatorgenerated Tue Sep 03 08:55:19 CST 2013
     */
    public String getUrl() {
        return url;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method sets the value of the database column CMS_ADV.URL
     *
     * @param url the value for CMS_ADV.URL
     *
     * @abatorgenerated Tue Sep 03 08:55:19 CST 2013
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method returns the value of the database column CMS_ADV.UPLOAD_ADV_FILE
     *
     * @return the value of CMS_ADV.UPLOAD_ADV_FILE
     *
     * @abatorgenerated Tue Sep 03 08:55:19 CST 2013
     */
    public String getUploadAdvFile() {
        return uploadAdvFile;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method sets the value of the database column CMS_ADV.UPLOAD_ADV_FILE
     *
     * @param uploadAdvFile the value for CMS_ADV.UPLOAD_ADV_FILE
     *
     * @abatorgenerated Tue Sep 03 08:55:19 CST 2013
     */
    public void setUploadAdvFile(String uploadAdvFile) {
        this.uploadAdvFile = uploadAdvFile;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method returns the value of the database column CMS_ADV.BEGIN_TIME
     *
     * @return the value of CMS_ADV.BEGIN_TIME
     *
     * @abatorgenerated Tue Sep 03 08:55:19 CST 2013
     */
    public Date getBeginTime() {
        return beginTime;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method sets the value of the database column CMS_ADV.BEGIN_TIME
     *
     * @param beginTime the value for CMS_ADV.BEGIN_TIME
     *
     * @abatorgenerated Tue Sep 03 08:55:19 CST 2013
     */
    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method returns the value of the database column CMS_ADV.END_TIME
     *
     * @return the value of CMS_ADV.END_TIME
     *
     * @abatorgenerated Tue Sep 03 08:55:19 CST 2013
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method sets the value of the database column CMS_ADV.END_TIME
     *
     * @param endTime the value for CMS_ADV.END_TIME
     *
     * @abatorgenerated Tue Sep 03 08:55:19 CST 2013
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method returns the value of the database column CMS_ADV.UNIT_NAME
     *
     * @return the value of CMS_ADV.UNIT_NAME
     *
     * @abatorgenerated Tue Sep 03 08:55:19 CST 2013
     */
    public String getUnitName() {
        return unitName;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method sets the value of the database column CMS_ADV.UNIT_NAME
     *
     * @param unitName the value for CMS_ADV.UNIT_NAME
     *
     * @abatorgenerated Tue Sep 03 08:55:19 CST 2013
     */
    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method returns the value of the database column CMS_ADV.LINKMAN
     *
     * @return the value of CMS_ADV.LINKMAN
     *
     * @abatorgenerated Tue Sep 03 08:55:19 CST 2013
     */
    public String getLinkman() {
        return linkman;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method sets the value of the database column CMS_ADV.LINKMAN
     *
     * @param linkman the value for CMS_ADV.LINKMAN
     *
     * @abatorgenerated Tue Sep 03 08:55:19 CST 2013
     */
    public void setLinkman(String linkman) {
        this.linkman = linkman;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method returns the value of the database column CMS_ADV.CONTACT
     *
     * @return the value of CMS_ADV.CONTACT
     *
     * @abatorgenerated Tue Sep 03 08:55:19 CST 2013
     */
    public String getContact() {
        return contact;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method sets the value of the database column CMS_ADV.CONTACT
     *
     * @param contact the value for CMS_ADV.CONTACT
     *
     * @abatorgenerated Tue Sep 03 08:55:19 CST 2013
     */
    public void setContact(String contact) {
        this.contact = contact;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method returns the value of the database column CMS_ADV.CLICK_COUNT
     *
     * @return the value of CMS_ADV.CLICK_COUNT
     *
     * @abatorgenerated Tue Sep 03 08:55:19 CST 2013
     */
   
    public String getRemark() {
        return remark;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method sets the value of the database column CMS_ADV.REMARK
     *
     * @param remark the value for CMS_ADV.REMARK
     *
     * @abatorgenerated Tue Sep 03 08:55:19 CST 2013
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method returns the value of the database column CMS_ADV.STATUS
     *
     * @return the value of CMS_ADV.STATUS
     *
     * @abatorgenerated Tue Sep 03 08:55:19 CST 2013
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method sets the value of the database column CMS_ADV.STATUS
     *
     * @param status the value for CMS_ADV.STATUS
     *
     * @abatorgenerated Tue Sep 03 08:55:19 CST 2013
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method returns the value of the database column CMS_ADV.CREATE_DATE
     *
     * @return the value of CMS_ADV.CREATE_DATE
     *
     * @abatorgenerated Tue Sep 03 08:55:19 CST 2013
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method sets the value of the database column CMS_ADV.CREATE_DATE
     *
     * @param createDate the value for CMS_ADV.CREATE_DATE
     *
     * @abatorgenerated Tue Sep 03 08:55:19 CST 2013
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method returns the value of the database column CMS_ADV.CREATED
     *
     * @return the value of CMS_ADV.CREATED
     *
     * @abatorgenerated Tue Sep 03 08:55:19 CST 2013
     */
   

    /**
     * This method was generated by Abator for iBATIS.
     * This method returns the value of the database column CMS_ADV.MODIFY_DATE
     *
     * @return the value of CMS_ADV.MODIFY_DATE
     *
     * @abatorgenerated Tue Sep 03 08:55:19 CST 2013
     */
    public Date getModifyDate() {
        return modifyDate;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method sets the value of the database column CMS_ADV.MODIFY_DATE
     *
     * @param modifyDate the value for CMS_ADV.MODIFY_DATE
     *
     * @abatorgenerated Tue Sep 03 08:55:19 CST 2013
     */
    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

   
    public String getContent() {
        return content;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method sets the value of the database column CMS_ADV.CONTENT
     *
     * @param content the value for CMS_ADV.CONTENT
     *
     * @abatorgenerated Tue Sep 03 08:55:19 CST 2013
     */
    public void setContent(String content) {
        this.content = content;
    }

	public Integer getAdvId() {
		return advId;
	}

	public void setAdvId(Integer advId) {
		this.advId = advId;
	}

	public Integer getAdcolumnId() {
		return adcolumnId;
	}

	public void setAdcolumnId(Integer adcolumnId) {
		this.adcolumnId = adcolumnId;
	}

	public Integer getClickCount() {
		return clickCount;
	}

	public void setClickCount(Integer clickCount) {
		this.clickCount = clickCount;
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

	public String getUrl2() {
		return url2;
	}

	public void setUrl2(String url2) {
		this.url2 = url2;
	}

	public String getUploadAdvFile2() {
		return uploadAdvFile2;
	}

	public void setUploadAdvFile2(String uploadAdvFile2) {
		this.uploadAdvFile2 = uploadAdvFile2;
	}

	public String getUrl3() {
		return url3;
	}

	public void setUrl3(String url3) {
		this.url3 = url3;
	}

	public String getUploadAdvFile3() {
		return uploadAdvFile3;
	}

	public void setUploadAdvFile3(String uploadAdvFile3) {
		this.uploadAdvFile3 = uploadAdvFile3;
	}

	public String getUrl4() {
		return url4;
	}

	public void setUrl4(String url4) {
		this.url4 = url4;
	}

	public String getUploadAdvFile4() {
		return uploadAdvFile4;
	}

	public void setUploadAdvFile4(String uploadAdvFile4) {
		this.uploadAdvFile4 = uploadAdvFile4;
	}

	public Integer getTypes() {
		return types;
	}

	public void setTypes(Integer types) {
		this.types = types;
	}

	public String getAdname() {
		return adname;
	}

	public void setAdname(String adname) {
		this.adname = adname;
	}

	public Integer getIsPublish() {
		return isPublish;
	}

	public void setIsPublish(Integer isPublish) {
		this.isPublish = isPublish;
	}

	public String getP2() {
		return p2;
	}

	public void setP2(String p2) {
		this.p2 = p2;
	}

	public String getP3() {
		return p3;
	}

	public void setP3(String p3) {
		this.p3 = p3;
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

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	public String getBeginTimeStr() {
		return beginTimeStr;
	}

	public void setBeginTimeStr(String beginTimeStr) {
		this.beginTimeStr = beginTimeStr;
	}

	public String getEndTimeStr() {
		return endTimeStr;
	}

	public void setEndTimeStr(String endTimeStr) {
		this.endTimeStr = endTimeStr;
	}

	public Integer getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Integer supplierId) {
		this.supplierId = supplierId;
	}

	public String getSiteCode() {
		return siteCode;
	}

	public void setSiteCode(String siteCode) {
		this.siteCode = siteCode;
	}



}
