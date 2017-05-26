package com.kmzyc.supplier.model;

import java.io.Serializable;
import java.util.Date;

public class BnesAcctEnchashmentCriteria implements Serializable {

	private static final long serialVersionUID = -4340666461677697072L;
	
	private Long nLoginId;
	
	private String loginAccount;
	
	private String enchashmentDepict;
	
	private String accountTransactionId;
	
	private Date startDate;
	
	private Date endDate;
	
	private Short enchashmentStatus;
	
	private Integer startIndex = Integer.valueOf(0);
	
	private Integer endIndex = Integer.valueOf(20);

	public String getLoginAccount() {
		return loginAccount;
	}

	public void setLoginAccount(String loginAccount) {
		this.loginAccount = loginAccount;
	}

	public String getEnchashmentDepict() {
		return enchashmentDepict;
	}

	public void setEnchashmentDepict(String enchashmentDepict) {
		this.enchashmentDepict = enchashmentDepict;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Short getEnchashmentStatus() {
		return enchashmentStatus;
	}

	public void setEnchashmentStatus(Short enchashmentStatus) {
		this.enchashmentStatus = enchashmentStatus;
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

	public Long getnLoginId() {
		return nLoginId;
	}

	public void setnLoginId(Long nLoginId) {
		this.nLoginId = nLoginId;
	}

	public String getAccountTransactionId() {
		return accountTransactionId;
	}

	public void setAccountTransactionId(String accountTransactionId) {
		this.accountTransactionId = accountTransactionId;
	}

	

	
	
}
