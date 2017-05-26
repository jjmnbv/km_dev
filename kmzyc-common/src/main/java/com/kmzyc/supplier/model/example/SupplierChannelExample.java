package com.kmzyc.supplier.model.example;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SupplierChannelExample {
    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database table SUPPLIER_CHANNEL
     *
     * @ibatorgenerated Wed Jan 15 10:04:26 CST 2014
     */
    protected String orderByClause;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database table SUPPLIER_CHANNEL
     *
     * @ibatorgenerated Wed Jan 15 10:04:26 CST 2014
     */
    protected List oredCriteria;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SUPPLIER_CHANNEL
     *
     * @ibatorgenerated Wed Jan 15 10:04:26 CST 2014
     */
    public SupplierChannelExample() {
        oredCriteria = new ArrayList();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SUPPLIER_CHANNEL
     *
     * @ibatorgenerated Wed Jan 15 10:04:26 CST 2014
     */
    protected SupplierChannelExample(SupplierChannelExample example) {
        this.orderByClause = example.orderByClause;
        this.oredCriteria = example.oredCriteria;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SUPPLIER_CHANNEL
     *
     * @ibatorgenerated Wed Jan 15 10:04:26 CST 2014
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SUPPLIER_CHANNEL
     *
     * @ibatorgenerated Wed Jan 15 10:04:26 CST 2014
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SUPPLIER_CHANNEL
     *
     * @ibatorgenerated Wed Jan 15 10:04:26 CST 2014
     */
    public List getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SUPPLIER_CHANNEL
     *
     * @ibatorgenerated Wed Jan 15 10:04:26 CST 2014
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SUPPLIER_CHANNEL
     *
     * @ibatorgenerated Wed Jan 15 10:04:26 CST 2014
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
     * This method corresponds to the database table SUPPLIER_CHANNEL
     *
     * @ibatorgenerated Wed Jan 15 10:04:26 CST 2014
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SUPPLIER_CHANNEL
     *
     * @ibatorgenerated Wed Jan 15 10:04:26 CST 2014
     */
    public void clear() {
        oredCriteria.clear();
    }

    /**
     * This class was generated by Apache iBATIS ibator.
     * This class corresponds to the database table SUPPLIER_CHANNEL
     *
     * @ibatorgenerated Wed Jan 15 10:04:26 CST 2014
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

        public Criteria andIdsIsNull() {
            addCriterion("IDS is null");
            return this;
        }

        public Criteria andIdsIsNotNull() {
            addCriterion("IDS is not null");
            return this;
        }

        public Criteria andIdsEqualTo(BigDecimal value) {
            addCriterion("IDS =", value, "ids");
            return this;
        }

        public Criteria andIdsNotEqualTo(BigDecimal value) {
            addCriterion("IDS <>", value, "ids");
            return this;
        }

        public Criteria andIdsGreaterThan(BigDecimal value) {
            addCriterion("IDS >", value, "ids");
            return this;
        }

        public Criteria andIdsGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("IDS >=", value, "ids");
            return this;
        }

        public Criteria andIdsLessThan(BigDecimal value) {
            addCriterion("IDS <", value, "ids");
            return this;
        }

        public Criteria andIdsLessThanOrEqualTo(BigDecimal value) {
            addCriterion("IDS <=", value, "ids");
            return this;
        }

        public Criteria andIdsIn(List values) {
            addCriterion("IDS in", values, "ids");
            return this;
        }

        public Criteria andIdsNotIn(List values) {
            addCriterion("IDS not in", values, "ids");
            return this;
        }

        public Criteria andIdsBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("IDS between", value1, value2, "ids");
            return this;
        }

        public Criteria andIdsNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("IDS not between", value1, value2, "ids");
            return this;
        }

        public Criteria andSupplierIdIsNull() {
            addCriterion("SUPPLIER_ID is null");
            return this;
        }

        public Criteria andSupplierIdIsNotNull() {
            addCriterion("SUPPLIER_ID is not null");
            return this;
        }

        public Criteria andSupplierIdEqualTo(BigDecimal value) {
            addCriterion("SUPPLIER_ID =", value, "supplierId");
            return this;
        }

        public Criteria andSupplierIdNotEqualTo(BigDecimal value) {
            addCriterion("SUPPLIER_ID <>", value, "supplierId");
            return this;
        }

        public Criteria andSupplierIdGreaterThan(BigDecimal value) {
            addCriterion("SUPPLIER_ID >", value, "supplierId");
            return this;
        }

        public Criteria andSupplierIdGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("SUPPLIER_ID >=", value, "supplierId");
            return this;
        }

        public Criteria andSupplierIdLessThan(BigDecimal value) {
            addCriterion("SUPPLIER_ID <", value, "supplierId");
            return this;
        }

        public Criteria andSupplierIdLessThanOrEqualTo(BigDecimal value) {
            addCriterion("SUPPLIER_ID <=", value, "supplierId");
            return this;
        }

        public Criteria andSupplierIdIn(List values) {
            addCriterion("SUPPLIER_ID in", values, "supplierId");
            return this;
        }

        public Criteria andSupplierIdNotIn(List values) {
            addCriterion("SUPPLIER_ID not in", values, "supplierId");
            return this;
        }

        public Criteria andSupplierIdBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("SUPPLIER_ID between", value1, value2, "supplierId");
            return this;
        }

        public Criteria andSupplierIdNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("SUPPLIER_ID not between", value1, value2, "supplierId");
            return this;
        }

        public Criteria andChannelNameIsNull() {
            addCriterion("CHANNEL_NAME is null");
            return this;
        }

        public Criteria andChannelNameIsNotNull() {
            addCriterion("CHANNEL_NAME is not null");
            return this;
        }

        public Criteria andChannelNameEqualTo(String value) {
            addCriterion("CHANNEL_NAME =", value, "channelName");
            return this;
        }

        public Criteria andChannelNameNotEqualTo(String value) {
            addCriterion("CHANNEL_NAME <>", value, "channelName");
            return this;
        }

        public Criteria andChannelNameGreaterThan(String value) {
            addCriterion("CHANNEL_NAME >", value, "channelName");
            return this;
        }

        public Criteria andChannelNameGreaterThanOrEqualTo(String value) {
            addCriterion("CHANNEL_NAME >=", value, "channelName");
            return this;
        }

        public Criteria andChannelNameLessThan(String value) {
            addCriterion("CHANNEL_NAME <", value, "channelName");
            return this;
        }

        public Criteria andChannelNameLessThanOrEqualTo(String value) {
            addCriterion("CHANNEL_NAME <=", value, "channelName");
            return this;
        }

        public Criteria andChannelNameLike(String value) {
            addCriterion("CHANNEL_NAME like", value, "channelName");
            return this;
        }

        public Criteria andChannelNameNotLike(String value) {
            addCriterion("CHANNEL_NAME not like", value, "channelName");
            return this;
        }

        public Criteria andChannelNameIn(List values) {
            addCriterion("CHANNEL_NAME in", values, "channelName");
            return this;
        }

        public Criteria andChannelNameNotIn(List values) {
            addCriterion("CHANNEL_NAME not in", values, "channelName");
            return this;
        }

        public Criteria andChannelNameBetween(String value1, String value2) {
            addCriterion("CHANNEL_NAME between", value1, value2, "channelName");
            return this;
        }

        public Criteria andChannelNameNotBetween(String value1, String value2) {
            addCriterion("CHANNEL_NAME not between", value1, value2, "channelName");
            return this;
        }
    }
}