package com.pltfm.app.vobject;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AppraisePropExample implements Serializable {
    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database table APPRAISE_PROP
     *
     * @ibatorgenerated Wed Aug 21 10:48:14 CST 2013
     */
    protected String orderByClause;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database table APPRAISE_PROP
     *
     * @ibatorgenerated Wed Aug 21 10:48:14 CST 2013
     */
    protected List oredCriteria;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table APPRAISE_PROP
     *
     * @ibatorgenerated Wed Aug 21 10:48:14 CST 2013
     */
    public AppraisePropExample() {
        oredCriteria = new ArrayList();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table APPRAISE_PROP
     *
     * @ibatorgenerated Wed Aug 21 10:48:14 CST 2013
     */
    protected AppraisePropExample(AppraisePropExample example) {
        this.orderByClause = example.orderByClause;
        this.oredCriteria = example.oredCriteria;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table APPRAISE_PROP
     *
     * @ibatorgenerated Wed Aug 21 10:48:14 CST 2013
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table APPRAISE_PROP
     *
     * @ibatorgenerated Wed Aug 21 10:48:14 CST 2013
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table APPRAISE_PROP
     *
     * @ibatorgenerated Wed Aug 21 10:48:14 CST 2013
     */
    public List getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table APPRAISE_PROP
     *
     * @ibatorgenerated Wed Aug 21 10:48:14 CST 2013
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table APPRAISE_PROP
     *
     * @ibatorgenerated Wed Aug 21 10:48:14 CST 2013
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
     * This method corresponds to the database table APPRAISE_PROP
     *
     * @ibatorgenerated Wed Aug 21 10:48:14 CST 2013
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table APPRAISE_PROP
     *
     * @ibatorgenerated Wed Aug 21 10:48:14 CST 2013
     */
    public void clear() {
        oredCriteria.clear();
    }

    /**
     * This class was generated by Apache iBATIS ibator.
     * This class corresponds to the database table APPRAISE_PROP
     *
     * @ibatorgenerated Wed Aug 21 10:48:14 CST 2013
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

        public Criteria andAppraisePropIdIsNull() {
            addCriterion("APPRAISE_PROP_ID is null");
            return this;
        }

        public Criteria andAppraisePropIdIsNotNull() {
            addCriterion("APPRAISE_PROP_ID is not null");
            return this;
        }

        public Criteria andAppraisePropIdEqualTo(BigDecimal value) {
            addCriterion("APPRAISE_PROP_ID =", value, "appraisePropId");
            return this;
        }

        public Criteria andAppraisePropIdNotEqualTo(BigDecimal value) {
            addCriterion("APPRAISE_PROP_ID <>", value, "appraisePropId");
            return this;
        }

        public Criteria andAppraisePropIdGreaterThan(BigDecimal value) {
            addCriterion("APPRAISE_PROP_ID >", value, "appraisePropId");
            return this;
        }

        public Criteria andAppraisePropIdGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("APPRAISE_PROP_ID >=", value, "appraisePropId");
            return this;
        }

        public Criteria andAppraisePropIdLessThan(BigDecimal value) {
            addCriterion("APPRAISE_PROP_ID <", value, "appraisePropId");
            return this;
        }

        public Criteria andAppraisePropIdLessThanOrEqualTo(BigDecimal value) {
            addCriterion("APPRAISE_PROP_ID <=", value, "appraisePropId");
            return this;
        }

        public Criteria andAppraisePropIdIn(List values) {
            addCriterion("APPRAISE_PROP_ID in", values, "appraisePropId");
            return this;
        }

        public Criteria andAppraisePropIdNotIn(List values) {
            addCriterion("APPRAISE_PROP_ID not in", values, "appraisePropId");
            return this;
        }

        public Criteria andAppraisePropIdBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("APPRAISE_PROP_ID between", value1, value2, "appraisePropId");
            return this;
        }

        public Criteria andAppraisePropIdNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("APPRAISE_PROP_ID not between", value1, value2, "appraisePropId");
            return this;
        }

        public Criteria andPropNameIsNull() {
            addCriterion("PROP_NAME is null");
            return this;
        }

        public Criteria andPropNameIsNotNull() {
            addCriterion("PROP_NAME is not null");
            return this;
        }

        public Criteria andPropNameEqualTo(String value) {
            addCriterion("PROP_NAME =", value, "propName");
            return this;
        }

        public Criteria andPropNameNotEqualTo(String value) {
            addCriterion("PROP_NAME <>", value, "propName");
            return this;
        }

        public Criteria andPropNameGreaterThan(String value) {
            addCriterion("PROP_NAME >", value, "propName");
            return this;
        }

        public Criteria andPropNameGreaterThanOrEqualTo(String value) {
            addCriterion("PROP_NAME >=", value, "propName");
            return this;
        }

        public Criteria andPropNameLessThan(String value) {
            addCriterion("PROP_NAME <", value, "propName");
            return this;
        }

        public Criteria andPropNameLessThanOrEqualTo(String value) {
            addCriterion("PROP_NAME <=", value, "propName");
            return this;
        }

        public Criteria andPropNameLike(String value) {
            addCriterion("PROP_NAME like", value, "propName");
            return this;
        }

        public Criteria andPropNameNotLike(String value) {
            addCriterion("PROP_NAME not like", value, "propName");
            return this;
        }

        public Criteria andPropNameIn(List values) {
            addCriterion("PROP_NAME in", values, "propName");
            return this;
        }

        public Criteria andPropNameNotIn(List values) {
            addCriterion("PROP_NAME not in", values, "propName");
            return this;
        }

        public Criteria andPropNameBetween(String value1, String value2) {
            addCriterion("PROP_NAME between", value1, value2, "propName");
            return this;
        }

        public Criteria andPropNameNotBetween(String value1, String value2) {
            addCriterion("PROP_NAME not between", value1, value2, "propName");
            return this;
        }
    }
}