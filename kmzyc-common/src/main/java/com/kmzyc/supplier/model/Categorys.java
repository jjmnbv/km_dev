package com.kmzyc.supplier.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class Categorys implements Serializable {

	private static final long serialVersionUID = 2432584267778524299L;
	
	private Long categoryId;
	
	private Long categoryParentId;
	
	public Long getCategoryParentId() {
    return categoryParentId;
  }

  public void setCategoryParentId(Long categoryParentId) {
    this.categoryParentId = categoryParentId;
  }

  private String categoryCode;
	
	private String categoryName;
	
	private Short status;
	
	private Integer sortno;
	
	private String channel;
	
	private String execSql;

	private Integer isPhy;
	    
	private String sqlString;
    /**
     * 默认返点
     */
    private BigDecimal defaultRebate;
	
	public BigDecimal getDefaultRebate() {
      return defaultRebate;
    }

    public void setDefaultRebate(BigDecimal defaultRebate) {
      this.defaultRebate = defaultRebate;
    }

  private Categorys parentCategory;
	
	private List<Categorys> childrenCategorys;

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryCode() {
		return categoryCode;
	}

	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public Short getStatus() {
		return status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	public Integer getSortno() {
		return sortno;
	}

	public void setSortno(Integer sortno) {
		this.sortno = sortno;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getExecSql() {
		return execSql;
	}

	public void setExecSql(String execSql) {
		this.execSql = execSql;
	}

	public Integer getIsPhy() {
		return isPhy;
	}

	public void setIsPhy(Integer isPhy) {
		this.isPhy = isPhy;
	}

	public String getSqlString() {
		return sqlString;
	}

	public void setSqlString(String sqlString) {
		this.sqlString = sqlString;
	}

	public Categorys getParentCategory() {
		return parentCategory;
	}

	public void setParentCategory(Categorys parentCategory) {
		this.parentCategory = parentCategory;
	}

	public List<Categorys> getChildrenCategorys() {
		return childrenCategorys;
	}

	public void setChildrenCategorys(List<Categorys> childrenCategorys) {
		this.childrenCategorys = childrenCategorys;
	}
	
	

}
