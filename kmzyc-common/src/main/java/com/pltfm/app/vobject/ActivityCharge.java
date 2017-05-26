package com.pltfm.app.vobject;

import java.io.Serializable;
import java.math.BigDecimal;


public class ActivityCharge implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -1655767017793690602L;

	/**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column ACTIVITY_CHARGE.ACTIVITY_CHARGE_ID
     *
     * @ibatorgenerated Thu Mar 17 15:11:32 CST 2016
     */
    private Long activityChargeId;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column ACTIVITY_CHARGE.ACTIVITY_ID
     *
     * @ibatorgenerated Thu Mar 17 15:11:32 CST 2016
     */
    private Long activityId;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column ACTIVITY_CHARGE.FIXED_CHARGE
     *
     * @ibatorgenerated Thu Mar 17 15:11:32 CST 2016
     */
    private BigDecimal fixedCharge;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column ACTIVITY_CHARGE.SINGLE_CHARGE
     *
     * @ibatorgenerated Thu Mar 17 15:11:32 CST 2016
     */
    private BigDecimal singleCharge;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column ACTIVITY_CHARGE.COMMISSION_RATE
     *
     * @ibatorgenerated Thu Mar 17 15:11:32 CST 2016
     */
    private BigDecimal commissionRate;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column ACTIVITY_CHARGE.ACTIVITY_CHARGE_ID
     *
     * @return the value of ACTIVITY_CHARGE.ACTIVITY_CHARGE_ID
     *
     * @ibatorgenerated Thu Mar 17 15:11:32 CST 2016
     */
    public Long getActivityChargeId() {
        return activityChargeId;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column ACTIVITY_CHARGE.ACTIVITY_CHARGE_ID
     *
     * @param activityChargeId the value for ACTIVITY_CHARGE.ACTIVITY_CHARGE_ID
     *
     * @ibatorgenerated Thu Mar 17 15:11:32 CST 2016
     */
    public void setActivityChargeId(Long activityChargeId) {
        this.activityChargeId = activityChargeId;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column ACTIVITY_CHARGE.ACTIVITY_ID
     *
     * @return the value of ACTIVITY_CHARGE.ACTIVITY_ID
     *
     * @ibatorgenerated Thu Mar 17 15:11:32 CST 2016
     */
    public Long getActivityId() {
        return activityId;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column ACTIVITY_CHARGE.ACTIVITY_ID
     *
     * @param activityId the value for ACTIVITY_CHARGE.ACTIVITY_ID
     *
     * @ibatorgenerated Thu Mar 17 15:11:32 CST 2016
     */
    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column ACTIVITY_CHARGE.FIXED_CHARGE
     *
     * @return the value of ACTIVITY_CHARGE.FIXED_CHARGE
     *
     * @ibatorgenerated Thu Mar 17 15:11:32 CST 2016
     */
    public BigDecimal getFixedCharge() {
        return fixedCharge;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column ACTIVITY_CHARGE.FIXED_CHARGE
     *
     * @param fixedCharge the value for ACTIVITY_CHARGE.FIXED_CHARGE
     *
     * @ibatorgenerated Thu Mar 17 15:11:32 CST 2016
     */
    public void setFixedCharge(BigDecimal fixedCharge) {
        this.fixedCharge = fixedCharge;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column ACTIVITY_CHARGE.SINGLE_CHARGE
     *
     * @return the value of ACTIVITY_CHARGE.SINGLE_CHARGE
     *
     * @ibatorgenerated Thu Mar 17 15:11:32 CST 2016
     */
    public BigDecimal getSingleCharge() {
        return singleCharge;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column ACTIVITY_CHARGE.SINGLE_CHARGE
     *
     * @param singleCharge the value for ACTIVITY_CHARGE.SINGLE_CHARGE
     *
     * @ibatorgenerated Thu Mar 17 15:11:32 CST 2016
     */
    public void setSingleCharge(BigDecimal singleCharge) {
        this.singleCharge = singleCharge;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column ACTIVITY_CHARGE.COMMISSION_RATE
     *
     * @return the value of ACTIVITY_CHARGE.COMMISSION_RATE
     *
     * @ibatorgenerated Thu Mar 17 15:11:32 CST 2016
     */
    public BigDecimal getCommissionRate() {
        return commissionRate;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column ACTIVITY_CHARGE.COMMISSION_RATE
     *
     * @param commissionRate the value for ACTIVITY_CHARGE.COMMISSION_RATE
     *
     * @ibatorgenerated Thu Mar 17 15:11:32 CST 2016
     */
    public void setCommissionRate(BigDecimal commissionRate) {
        this.commissionRate = commissionRate;
    }
}