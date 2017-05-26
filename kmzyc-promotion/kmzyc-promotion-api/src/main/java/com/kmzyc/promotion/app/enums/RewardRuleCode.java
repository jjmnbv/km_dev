package com.kmzyc.promotion.app.enums;

/**
 * 注册规则码
 * 
 * @author weijunlong
 * 
 */
public enum RewardRuleCode {

	/*
	 * 注册奖励
	 */
	REGISTER("1", "注册奖励"),

	/**
	 * 手机验证奖励
	 */
	MOBILE_VERIFY("2", "手机验证奖励"),

	/**
	 * 首次购物奖励
	 */
	FIRST_TRADE("3", "首次购物奖励");

	private String code;

	private String description;

	public String getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}

	private RewardRuleCode(String code, String description) {
		this.code = code;
		this.description = description;
	}

	public String toString() {
		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append("RewardRuleCode[code=").append(this.code).append("]");
		return strBuilder.toString();
	}

}
