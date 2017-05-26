package com.kmzyc.supplier.model;

/**
 * 供应商账户信息实体(无此实体表)
 * 
 * @author luoyi
 * @createDate 2013/01/09
 */

public class SupplierAccountInfo implements java.io.Serializable {
	private static final long serialVersionUID = 7430008270693878055L;

	// 以下取自表ACCOUNT_INFO
	private Integer nAccountId;// 登录账号userId
	private Integer customerTypeId;
	private Long nLoginId;// 登录账号loginId
	private String accountLogin;// 登录账号名称
	private String name;// 用户真实姓名
	private String mobile;// 手机号
	private String email;// 邮箱
	private String address;// 地址
	private Double nAccountAmount;// 账户总余额
	private Double amountAvlibal;// 账户可用余额
	private Double amountFrozen;// 账户冻结金额
	private Integer status;// 账户状态
	// 以下取自表login_info
	private Long nLevelId;// 账户级别ID
	private String loginAccount;// 账户状态
	private String loginPassword;// 地址
	// 以下取自表user_level
	private String code;// 会员级别code
	private String levelName;// 会员级别名称
	private String remark;// 备注
	// 以下取自表PERSONALITY_INFO
	private Long rankId;// 会员头衔ID
	// 以下取自表RANK
	private String rankName;// 会员头衔名称

	/**
	 * 以下为set和get方法
	 * 
	 * @return
	 */
	public Integer getnAccountId() {
		return nAccountId;
	}

	public void setnAccountId(Integer nAccountId) {
		this.nAccountId = nAccountId;
	}

	public Integer getCustomerTypeId() {
		return customerTypeId;
	}

	public void setCustomerTypeId(Integer customerTypeId) {
		this.customerTypeId = customerTypeId;
	}

	public Long getnLoginId() {
		return nLoginId;
	}

	public void setnLoginId(Long nLoginId) {
		this.nLoginId = nLoginId;
	}

	public String getAccountLogin() {
		return accountLogin;
	}

	public void setAccountLogin(String accountLogin) {
		this.accountLogin = accountLogin;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public Double getnAccountAmount() {
		return nAccountAmount;
	}

	public void setnAccountAmount(Double nAccountAmount) {
		this.nAccountAmount = nAccountAmount;
	}

	public Double getAmountAvlibal() {
		return amountAvlibal;
	}

	public void setAmountAvlibal(Double amountAvlibal) {
		this.amountAvlibal = amountAvlibal;
	}

	public Double getAmountFrozen() {
		return amountFrozen;
	}

	public void setAmountFrozen(Double amountFrozen) {
		this.amountFrozen = amountFrozen;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Long getnLevelId() {
		return nLevelId;
	}

	public void setnLevelId(Long nLevelId) {
		this.nLevelId = nLevelId;
	}

	public String getLoginAccount() {
		return loginAccount;
	}

	public void setLoginAccount(String loginAccount) {
		this.loginAccount = loginAccount;
	}

	public String getLoginPassword() {
		return loginPassword;
	}

	public void setLoginPassword(String loginPassword) {
		this.loginPassword = loginPassword;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getLevelName() {
		return levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRankName() {
		return rankName;
	}

	public void setRankName(String rankName) {
		this.rankName = rankName;
	}

	@Override
	public String toString() {
		return "SupplierAccountInfo [nAccountId=" + nAccountId
				+ ", customerTypeId=" + customerTypeId + ", nLoginId="
				+ nLoginId + ", accountLogin=" + accountLogin + ", name="
				+ name + ", mobile=" + mobile + ", email=" + email
				+ ", address=" + address + ", nAccountAmount=" + nAccountAmount
				+ ", amountAvlibal=" + amountAvlibal + ", amountFrozen="
				+ amountFrozen + ", status=" + status + ", nLevelId="
				+ nLevelId + ", loginAccount=" + loginAccount
				+ ", loginPassword=" + loginPassword + ", code=" + code
				+ ", levelName=" + levelName + ", remark=" + remark
				+ ",rankName=" + rankName + "]";
	}

}