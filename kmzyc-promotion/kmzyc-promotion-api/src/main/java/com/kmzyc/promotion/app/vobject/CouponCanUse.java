package com.kmzyc.promotion.app.vobject;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 供远程接口使用,在优惠券使用之后，返回值
 * 
 * @author Administrator
 * 
 */
public class CouponCanUse implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * 实际抵扣金额(实际优惠券的抵扣金额)
	 */
	private BigDecimal bondPrice;

	public BigDecimal getBondPrice() {
		return bondPrice;
	}

	public void setBondPrice(BigDecimal bondPrice) {
		this.bondPrice = bondPrice;
	}

}
