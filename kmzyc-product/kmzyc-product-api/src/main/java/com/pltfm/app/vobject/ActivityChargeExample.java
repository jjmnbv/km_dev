package com.pltfm.app.vobject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivityChargeExample {
    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database table ACTIVITY_CHARGE
     *
     * @ibatorgenerated Thu Mar 17 15:11:32 CST 2016
     */
    protected String orderByClause;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database table ACTIVITY_CHARGE
     *
     * @ibatorgenerated Thu Mar 17 15:11:32 CST 2016
     */
    protected List oredCriteria;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table ACTIVITY_CHARGE
     *
     * @ibatorgenerated Thu Mar 17 15:11:32 CST 2016
     */
    public ActivityChargeExample() {
        oredCriteria = new ArrayList();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table ACTIVITY_CHARGE
     *
     * @ibatorgenerated Thu Mar 17 15:11:32 CST 2016
     */
    protected ActivityChargeExample(ActivityChargeExample example) {
        this.orderByClause = example.orderByClause;
        this.oredCriteria = example.oredCriteria;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table ACTIVITY_CHARGE
     *
     * @ibatorgenerated Thu Mar 17 15:11:32 CST 2016
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table ACTIVITY_CHARGE
     *
     * @ibatorgenerated Thu Mar 17 15:11:32 CST 2016
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table ACTIVITY_CHARGE
     *
     * @ibatorgenerated Thu Mar 17 15:11:32 CST 2016
     */
    public List getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table ACTIVITY_CHARGE
     *
     * @ibatorgenerated Thu Mar 17 15:11:32 CST 2016
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table ACTIVITY_CHARGE
     *
     * @ibatorgenerated Thu Mar 17 15:11:32 CST 2016
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table ACTIVITY_CHARGE
     *
     * @ibatorgenerated Thu Mar 17 15:11:32 CST 2016
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table ACTIVITY_CHARGE
     *
     * @ibatorgenerated Thu Mar 17 15:11:32 CST 2016
     */
    public void clear() {
        oredCriteria.clear();
    }

    /**
     * This class was generated by Apache iBATIS ibator.
     * This class corresponds to the database table ACTIVITY_CHARGE
     *
     * @ibatorgenerated Thu Mar 17 15:11:32 CST 2016
     */
    public static class Criteria {
        protected List criteriaWithoutValue;

        protected List criteriaWithSingleValue;

        protected List criteriaWithListValue;

        protected List criteriaWithBetweenValue;

        protected Criteria() {
            super();
            criteriaWithoutValue = new ArrayList();
            criteriaWithSingleValue = new ArrayList();
            criteriaWithListValue = new ArrayList();
            criteriaWithBetweenValue = new ArrayList();
        }

        public boolean isValid() {
            return criteriaWithoutValue.size() > 0
                || criteriaWithSingleValue.size() > 0
                || criteriaWithListValue.size() > 0
                || criteriaWithBetweenValue.size() > 0;
        }

        public List getCriteriaWithoutValue() {
            return criteriaWithoutValue;
        }

        public List getCriteriaWithSingleValue() {
            return criteriaWithSingleValue;
        }

        public List getCriteriaWithListValue() {
            return criteriaWithListValue;
        }

        public List getCriteriaWithBetweenValue() {
            return criteriaWithBetweenValue;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteriaWithoutValue.add(condition);
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            Map map = new HashMap();
            map.put("condition", condition);
            map.put("value", value);
            criteriaWithSingleValue.add(map);
        }

        protected void addCriterion(String condition, List values, String property) {
            if (values == null || values.size() == 0) {
                throw new RuntimeException("Value list for " + property + " cannot be null or empty");
            }
            Map map = new HashMap();
            map.put("condition", condition);
            map.put("values", values);
            criteriaWithListValue.add(map);
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            List list = new ArrayList();
            list.add(value1);
            list.add(value2);
            Map map = new HashMap();
            map.put("condition", condition);
            map.put("values", list);
            criteriaWithBetweenValue.add(map);
        }

        public Criteria andActivityChargeIdIsNull() {
            addCriterion("ACTIVITY_CHARGE_ID is null");
            return this;
        }

        public Criteria andActivityChargeIdIsNotNull() {
            addCriterion("ACTIVITY_CHARGE_ID is not null");
            return this;
        }

        public Criteria andActivityChargeIdEqualTo(Long value) {
            addCriterion("ACTIVITY_CHARGE_ID =", value, "activityChargeId");
            return this;
        }

        public Criteria andActivityChargeIdNotEqualTo(Long value) {
            addCriterion("ACTIVITY_CHARGE_ID <>", value, "activityChargeId");
            return this;
        }

        public Criteria andActivityChargeIdGreaterThan(Long value) {
            addCriterion("ACTIVITY_CHARGE_ID >", value, "activityChargeId");
            return this;
        }

        public Criteria andActivityChargeIdGreaterThanOrEqualTo(Long value) {
            addCriterion("ACTIVITY_CHARGE_ID >=", value, "activityChargeId");
            return this;
        }

        public Criteria andActivityChargeIdLessThan(Long value) {
            addCriterion("ACTIVITY_CHARGE_ID <", value, "activityChargeId");
            return this;
        }

        public Criteria andActivityChargeIdLessThanOrEqualTo(Long value) {
            addCriterion("ACTIVITY_CHARGE_ID <=", value, "activityChargeId");
            return this;
        }

        public Criteria andActivityChargeIdIn(List values) {
            addCriterion("ACTIVITY_CHARGE_ID in", values, "activityChargeId");
            return this;
        }

        public Criteria andActivityChargeIdNotIn(List values) {
            addCriterion("ACTIVITY_CHARGE_ID not in", values, "activityChargeId");
            return this;
        }

        public Criteria andActivityChargeIdBetween(Long value1, Long value2) {
            addCriterion("ACTIVITY_CHARGE_ID between", value1, value2, "activityChargeId");
            return this;
        }

        public Criteria andActivityChargeIdNotBetween(Long value1, Long value2) {
            addCriterion("ACTIVITY_CHARGE_ID not between", value1, value2, "activityChargeId");
            return this;
        }

        public Criteria andActivityIdIsNull() {
            addCriterion("ACTIVITY_ID is null");
            return this;
        }

        public Criteria andActivityIdIsNotNull() {
            addCriterion("ACTIVITY_ID is not null");
            return this;
        }

        public Criteria andActivityIdEqualTo(Long value) {
            addCriterion("ACTIVITY_ID =", value, "activityId");
            return this;
        }

        public Criteria andActivityIdNotEqualTo(Long value) {
            addCriterion("ACTIVITY_ID <>", value, "activityId");
            return this;
        }

        public Criteria andActivityIdGreaterThan(Long value) {
            addCriterion("ACTIVITY_ID >", value, "activityId");
            return this;
        }

        public Criteria andActivityIdGreaterThanOrEqualTo(Long value) {
            addCriterion("ACTIVITY_ID >=", value, "activityId");
            return this;
        }

        public Criteria andActivityIdLessThan(Long value) {
            addCriterion("ACTIVITY_ID <", value, "activityId");
            return this;
        }

        public Criteria andActivityIdLessThanOrEqualTo(Long value) {
            addCriterion("ACTIVITY_ID <=", value, "activityId");
            return this;
        }

        public Criteria andActivityIdIn(List values) {
            addCriterion("ACTIVITY_ID in", values, "activityId");
            return this;
        }

        public Criteria andActivityIdNotIn(List values) {
            addCriterion("ACTIVITY_ID not in", values, "activityId");
            return this;
        }

        public Criteria andActivityIdBetween(Long value1, Long value2) {
            addCriterion("ACTIVITY_ID between", value1, value2, "activityId");
            return this;
        }

        public Criteria andActivityIdNotBetween(Long value1, Long value2) {
            addCriterion("ACTIVITY_ID not between", value1, value2, "activityId");
            return this;
        }

        public Criteria andFixedChargeIsNull() {
            addCriterion("FIXED_CHARGE is null");
            return this;
        }

        public Criteria andFixedChargeIsNotNull() {
            addCriterion("FIXED_CHARGE is not null");
            return this;
        }

        public Criteria andFixedChargeEqualTo(BigDecimal value) {
            addCriterion("FIXED_CHARGE =", value, "fixedCharge");
            return this;
        }

        public Criteria andFixedChargeNotEqualTo(BigDecimal value) {
            addCriterion("FIXED_CHARGE <>", value, "fixedCharge");
            return this;
        }

        public Criteria andFixedChargeGreaterThan(BigDecimal value) {
            addCriterion("FIXED_CHARGE >", value, "fixedCharge");
            return this;
        }

        public Criteria andFixedChargeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("FIXED_CHARGE >=", value, "fixedCharge");
            return this;
        }

        public Criteria andFixedChargeLessThan(BigDecimal value) {
            addCriterion("FIXED_CHARGE <", value, "fixedCharge");
            return this;
        }

        public Criteria andFixedChargeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("FIXED_CHARGE <=", value, "fixedCharge");
            return this;
        }

        public Criteria andFixedChargeIn(List values) {
            addCriterion("FIXED_CHARGE in", values, "fixedCharge");
            return this;
        }

        public Criteria andFixedChargeNotIn(List values) {
            addCriterion("FIXED_CHARGE not in", values, "fixedCharge");
            return this;
        }

        public Criteria andFixedChargeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("FIXED_CHARGE between", value1, value2, "fixedCharge");
            return this;
        }

        public Criteria andFixedChargeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("FIXED_CHARGE not between", value1, value2, "fixedCharge");
            return this;
        }

        public Criteria andSingleChargeIsNull() {
            addCriterion("SINGLE_CHARGE is null");
            return this;
        }

        public Criteria andSingleChargeIsNotNull() {
            addCriterion("SINGLE_CHARGE is not null");
            return this;
        }

        public Criteria andSingleChargeEqualTo(BigDecimal value) {
            addCriterion("SINGLE_CHARGE =", value, "singleCharge");
            return this;
        }

        public Criteria andSingleChargeNotEqualTo(BigDecimal value) {
            addCriterion("SINGLE_CHARGE <>", value, "singleCharge");
            return this;
        }

        public Criteria andSingleChargeGreaterThan(BigDecimal value) {
            addCriterion("SINGLE_CHARGE >", value, "singleCharge");
            return this;
        }

        public Criteria andSingleChargeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("SINGLE_CHARGE >=", value, "singleCharge");
            return this;
        }

        public Criteria andSingleChargeLessThan(BigDecimal value) {
            addCriterion("SINGLE_CHARGE <", value, "singleCharge");
            return this;
        }

        public Criteria andSingleChargeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("SINGLE_CHARGE <=", value, "singleCharge");
            return this;
        }

        public Criteria andSingleChargeIn(List values) {
            addCriterion("SINGLE_CHARGE in", values, "singleCharge");
            return this;
        }

        public Criteria andSingleChargeNotIn(List values) {
            addCriterion("SINGLE_CHARGE not in", values, "singleCharge");
            return this;
        }

        public Criteria andSingleChargeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("SINGLE_CHARGE between", value1, value2, "singleCharge");
            return this;
        }

        public Criteria andSingleChargeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("SINGLE_CHARGE not between", value1, value2, "singleCharge");
            return this;
        }

        public Criteria andCommissionRateIsNull() {
            addCriterion("COMMISSION_RATE is null");
            return this;
        }

        public Criteria andCommissionRateIsNotNull() {
            addCriterion("COMMISSION_RATE is not null");
            return this;
        }

        public Criteria andCommissionRateEqualTo(BigDecimal value) {
            addCriterion("COMMISSION_RATE =", value, "commissionRate");
            return this;
        }

        public Criteria andCommissionRateNotEqualTo(BigDecimal value) {
            addCriterion("COMMISSION_RATE <>", value, "commissionRate");
            return this;
        }

        public Criteria andCommissionRateGreaterThan(BigDecimal value) {
            addCriterion("COMMISSION_RATE >", value, "commissionRate");
            return this;
        }

        public Criteria andCommissionRateGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("COMMISSION_RATE >=", value, "commissionRate");
            return this;
        }

        public Criteria andCommissionRateLessThan(BigDecimal value) {
            addCriterion("COMMISSION_RATE <", value, "commissionRate");
            return this;
        }

        public Criteria andCommissionRateLessThanOrEqualTo(BigDecimal value) {
            addCriterion("COMMISSION_RATE <=", value, "commissionRate");
            return this;
        }

        public Criteria andCommissionRateIn(List values) {
            addCriterion("COMMISSION_RATE in", values, "commissionRate");
            return this;
        }

        public Criteria andCommissionRateNotIn(List values) {
            addCriterion("COMMISSION_RATE not in", values, "commissionRate");
            return this;
        }

        public Criteria andCommissionRateBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("COMMISSION_RATE between", value1, value2, "commissionRate");
            return this;
        }

        public Criteria andCommissionRateNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("COMMISSION_RATE not between", value1, value2, "commissionRate");
            return this;
        }
    }
}