package com.kmzyc.supplier.model;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * AccountInfo entity. @author MyEclipse Persistence Tools
 */

public class AccountInfo implements Serializable {
	private static final long serialVersionUID = -3944440660527410877L;
	private static Logger logger = LoggerFactory.getLogger(AccountInfo.class);
	// private BigDecimal NAccountId;
	// private BigDecimal NCustomerTypeId;
	// private BigDecimal NLoginId;
	private Integer naccountId;
	private Integer customerTypeId;
	private Long loginId;
	private String accountLogin;
	private String paymentpwd;
	private String name;
	private String acconutId;
	private String mobile;
	private String email;
	private String address;
	// private Date DCreateDate;
	// private Double NAccountAmount;
	private Date createDate;
	private Double accountAmount;
	private Double amountFrozen;
	private Double amountAvlibal;
	// private Byte NStatus;
	// private Date DModifyDate;
	// private BigDecimal NModified;
	private Integer status;
	private Date modifyDate;
	private Integer modified;

	private String payRange;

	public com.pltfm.app.vobject.AccountInfo transFormToRemoteAddress() {
		com.pltfm.app.vobject.AccountInfo accountInfo = new com.pltfm.app.vobject.AccountInfo();

		// 手动转换名称不相同的属性
		accountInfo.setN_AccountId(naccountId);
		accountInfo.setN_CustomerTypeId(customerTypeId);
		accountInfo.setN_LoginId(Integer.parseInt("" + loginId));
		accountInfo.setD_CreateDate(createDate);
		accountInfo.setN_AccountAmount(new BigDecimal(accountAmount));
		accountInfo.setN_Status(status);
		accountInfo.setN_Modified(modified);
		try {
			// 转换名称相同的属性
			BeanUtils.copyProperties(accountInfo, this);
		} catch (Exception e) {
			logger.error("将本地AccountInfo对象转换为远程对象出错：" + e.getMessage(), e);
			throw new RuntimeException("将本地AccountInfo对象转换为远程对象出错！", e);
		}

		return accountInfo;
	}

	public Integer getCustomerTypeId() {
		return customerTypeId;
	}

	public void setCustomerTypeId(Integer customerTypeId) {
		this.customerTypeId = customerTypeId;
	}

	public String getAccountLogin() {
		return accountLogin;
	}

	public void setAccountLogin(String accountLogin) {
		this.accountLogin = accountLogin;
	}

	public String getPaymentpwd() {
		return paymentpwd;
	}

	public void setPaymentpwd(String paymentpwd) {
		this.paymentpwd = paymentpwd;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAcconutId() {
		return acconutId;
	}

	public void setAcconutId(String acconutId) {
		this.acconutId = acconutId;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Double getAccountAmount() {
		return accountAmount;
	}

	public void setAccountAmount(Double accountAmount) {
		this.accountAmount = accountAmount;
	}

	public Double getAmountFrozen() {
		return amountFrozen;
	}

	public void setAmountFrozen(Double amountFrozen) {
		this.amountFrozen = amountFrozen;
	}

	public Double getAmountAvlibal() {
		return amountAvlibal;
	}

	public void setAmountAvlibal(Double amountAvlibal) {
		this.amountAvlibal = amountAvlibal;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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

	public Integer getNaccountId() {
		return naccountId;
	}

	public void setNaccountId(Integer naccountId) {
		this.naccountId = naccountId;
	}

	public Long getLoginId() {
		return loginId;
	}

	public void setLoginId(Long loginId) {
		this.loginId = loginId;
	}

	public String getPayRange() {
		return payRange;
	}

	public void setPayRange(String payRange) {
		this.payRange = payRange;
	}

}