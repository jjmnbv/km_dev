package com.pltfm.cms.vobject;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
/**
 * 窗口数据实体类
 * @author cjm
 * @since 2013-9-3
 */
public class CmsWindowData implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * 窗口数据主键
     */
	private Integer windowDataId;

    /**
     * 窗口主键
     */
    private Integer windowId;

    /**
     * 数据类型
     */
    private Integer dataType;

    /**
     * 数据主键
     */
    private Integer dataId;
    private List pageIds;
  

	/**
     * 数据名称
     */
    private String dataName;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 备注
     */
    private String remark;

    /**
     * 状态
     */
    private Integer status;
    
    /**
     * 产品SkuId
     */
    private Integer productSkuId;
    
    

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
     *  开始索引值 
     */
	private Integer startIndex;
	/** 
	 * 结束索引值 
	 */
	private Integer endIndex;
	/**
	 * 自定义数据名称
	 */
	private String user_defined_name;
	/**
	 * 自定义数据URL
	 */
	private String user_defined_url;
	
	/**
	 * 自定义数据类型
	 */
	private Integer user_defined_type;
	/**
	 * 自定义图片路径
	 */
	private String user_defined_picpath;
	/**
	 * 图片选择标志
	 */
	private Integer picType;
	/**
	 * 完整的图片输出路径
	 */
	private String picFullPath;
	
	/**
	 * 站点ID
	 */
	private Integer siteId;
	
	public Integer getWindowDataId() {
		return windowDataId;
	}

	public void setWindowDataId(Integer windowDataId) {
		this.windowDataId = windowDataId;
	}

	public Integer getWindowId() {
		return windowId;
	}

	public void setWindowId(Integer windowId) {
		this.windowId = windowId;
	}

	public Integer getDataType() {
		return dataType;
	}

	public void setDataType(Integer dataType) {
		this.dataType = dataType;
	}

	public Integer getDataId() {
		return dataId;
	}

	public void setDataId(Integer dataId) {
		this.dataId = dataId;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
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
	  public List getPageIds() {
			return pageIds;
		}

		public void setPageIds(List pageIds) {
			this.pageIds = pageIds;
		}
	public String getDataName() {
		return dataName;
	}

	public void setDataName(String dataName) {
		this.dataName = dataName;
	}

	public String getUser_defined_name() {
		return user_defined_name;
	}

	public void setUser_defined_name(String userDefinedName) {
		user_defined_name = userDefinedName;
	}

	public String getUser_defined_url() {
		return user_defined_url;
	}

	public void setUser_defined_url(String userDefinedUrl) {
		user_defined_url = userDefinedUrl;
	}

	public Integer getUser_defined_type() {
		return user_defined_type;
	}

	public void setUser_defined_type(Integer userDefinedType) {
		user_defined_type = userDefinedType;
	}

	public String getUser_defined_picpath() {
		return user_defined_picpath;
	}

	public void setUser_defined_picpath(String userDefinedPicpath) {
		user_defined_picpath = userDefinedPicpath;
	}

	public Integer getPicType() {
		return picType;
	}

	public void setPicType(Integer picType) {
		this.picType = picType;
	}

	public String getPicFullPath() {
		return picFullPath;
	}

	public void setPicFullPath(String picFullPath) {
		this.picFullPath = picFullPath;
	}

	public Integer getSiteId() {
		return siteId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}

	public Integer getProductSkuId() {
		return productSkuId;
	}

	public void setProductSkuId(Integer productSkuId) {
		this.productSkuId = productSkuId;
	}


}