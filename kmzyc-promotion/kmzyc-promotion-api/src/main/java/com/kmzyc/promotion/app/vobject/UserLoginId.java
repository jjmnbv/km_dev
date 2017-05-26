package com.kmzyc.promotion.app.vobject;

import java.io.Serializable;
public class UserLoginId implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long customId;
	private Long leveId;
	private String loginAccount;
	private String name;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getCustomId() {
		return customId;
	}
	public void setCustomId(Long customId) {
		this.customId = customId;
	}
	public Long getLeveId() {
		return leveId;
	}
	public void setLeveId(Long leveId) {
		this.leveId = leveId;
	}
	public String getLoginAccount() {
		return loginAccount;
	}
	public void setLoginAccount(String loginAccount) {
		this.loginAccount = loginAccount;
	}
}
