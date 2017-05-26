package com.pltfm.app.enums;

/**
 * 活动类型：编号、名称、作用域
 * @author hewenfeng
 *
 */
public enum PromotionTypeEnums {
	/**
	 * 特价
	 */
	SALE(10,"特价",2),
	/**
	 * 打折
	 */
	DISCOUNT(8,"打折",2),
	/**
	 * 满额减免
	 */
	REDUCTION(6,"满减",1),
	
	/**
	 * 加价购 increase
	 */
	INCREASE(5,"加价购",1),
	
	/**
	 * 满额送券
	 */
	COUPON(4,"送券",1),
	
	/**
	 * 满额赠品
	 */
	GIFT(3,"满赠",1);
	/**
	 * 其它
	 */
	//OTHER(2,"其它",0);
	
	private Integer value;
	private String title;
	/**作用域  2商品；1订单*/
	private Integer scope;

	PromotionTypeEnums(Integer value,String title,Integer scope){
		this.value = value;
		this.title = title;
		this.scope = scope;
	}
	
	
	public Integer getValue() {
		return value;
	}


	public void setValue(Integer value) {
		this.value = value;
	}


	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	/**作用域   2商品；1订单*/
	public Integer getScope() {
		return scope;
	}

	/**作用域   2商品；1订单*/
	public void setScope(Integer scope) {
		this.scope = scope;
	}


	public String toString() {
		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append("PromotionTypeEnums[value=").append(this.value)
		.append(",title=").append(this.title).append("]");
		return strBuilder.toString();
	}
	public static void main(String[] agr){
		PromotionTypeEnums[] t = PromotionTypeEnums.values();
	}
}
