package com.kmzyc.promotion.app.fobject;


import java.io.Serializable;
import java.math.BigDecimal;

public class OrderCouponInfo implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 4954525374132398575L;


    private String orderCode;

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }


    private String couponId;

    public String getCouponId() {
        return couponId;
    }

    public void setCouponId(String couponId) {
        this.couponId = couponId;
    }

    private BigDecimal grantId;

    public BigDecimal getGrantId() {
        return grantId;
    }

    public void setGrantId(BigDecimal grantId) {
        this.grantId = grantId;
    }

}
