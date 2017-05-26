package com.pltfm.app.vobject;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
 * 产品属性
 * @author Administrator
 *
 */
public class Attribute extends BaseBean implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5368976450841871259L;
	/**
	 * 编码
	 */
	public String code; 
	/**
	 * 输入类型（单选、多选、文本(64)
	 */
	public String inputType;
	/**
	 * 状态(deleted,unvalid,valid)
	 */
	public String status;
	/**
	 * 排序号
	 */
	public Integer index = 0;
	/**
	 * 是否SKU属性
	 */
	public String isSku;
	/**
	 * 修改时间
	 */
	public Date modifTime;
	/**
	 * 维护人
	 */
	public Integer modifUser;
	/**
	 * 是否导航
	 */
	public String isNav;
	/**
	 * 是否必选
	 */
	public String isReq;
	/**
	 * 类目id
	 */
	public Long catId;
	/**
	 * 类目名称
	 */
	public String catName;
	/**
	 * 属性值
	 */
	public List<AttributeValue> defaultValue = new ArrayList<AttributeValue>();
	/**
	 * 属性选中值
	 */
	public List<AttributeValue> checkValue =  new ArrayList<AttributeValue>();
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getInputType() {
		return inputType;
	}
	public void setInputType(String inputType) {
		this.inputType = inputType;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Integer getIndex() {
		return index;
	}
	public void setIndex(Integer index) {
		this.index = index;
	}
	public String getIsSku() {
		return isSku;
	}
	public void setIsSku(String isSku) {
		this.isSku = isSku;
	}
	public Date getModifTime() {
		return modifTime;
	}
	public void setModifTime(Date modifTime) {
		this.modifTime = modifTime;
	}
	public Integer getModifUser() {
		return modifUser;
	}
	public void setModifUser(Integer modifUser) {
		this.modifUser = modifUser;
	}
	public String getIsNav() {
		return isNav;
	}
	public void setIsNav(String isNav) {
		this.isNav = isNav;
	}
	public String getIsReq() {
		return isReq;
	}
	public void setIsReq(String isReq) {
		this.isReq = isReq;
	}
	public List<AttributeValue> getDefaultValue() {
		return defaultValue;
	}
	public void setDefaultValue(List<AttributeValue> defaultValue) {
		this.defaultValue = defaultValue;
	}
	public List<AttributeValue> getCheckValue() {
		return checkValue;
	}
	public void setCheckValue(List<AttributeValue> checkValue) {
		this.checkValue = checkValue;
	}
	public Long getCatId() {
		return catId;
	}
	public void setCatId(Long catId) {
		this.catId = catId;
	}
	public String getCatName() {
		return catName;
	}
	public void setCatName(String catName) {
		this.catName = catName;
	}

}
