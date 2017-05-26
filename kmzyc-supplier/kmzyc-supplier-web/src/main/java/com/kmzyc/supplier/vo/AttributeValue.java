package com.kmzyc.supplier.vo;

public class AttributeValue implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long categoryAttrId;
	
	private Long categoryAttrValueId;

	private String attribute;

	private String value;

    private String isNewAttr;

	public String getAttribute() {
		return attribute;
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Long getCategoryAttrId() {
		return categoryAttrId;
	}

	public void setCategoryAttrId(Long categoryAttrId) {
		this.categoryAttrId = categoryAttrId;
	}

	public Long getCategoryAttrValueId() {
		return categoryAttrValueId;
	}

	public void setCategoryAttrValueId(Long categoryAttrValueId) {
		this.categoryAttrValueId = categoryAttrValueId;
	}

    public String getIsNewAttr() {
        return isNewAttr;
    }

    public void setIsNewAttr(String isNewAttr) {
        this.isNewAttr = isNewAttr;
    }
}
