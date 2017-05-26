package com.pltfm.app.vobject;

public class AttributeValue extends BaseBean implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5228552647201819757L;
	/**
	 * 状态(deleted,unvalid,valid)
	 */
	public String status;
	public String value;
	/**
	 * 类目id
	 */
	public Long attId;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Long getAttId() {
		return attId;
	}

	public void setAttId(Long attId) {
		this.attId = attId;
	}

}
