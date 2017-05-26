package com.kmzyc.supplier.model.example;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SuppliersAvailableCategorysExample {
    /**
     * SUPPLIERS_AVAILABLE_CATEGORYS
     */
    protected String orderByClause;

    /**
     * SUPPLIERS_AVAILABLE_CATEGORYS
     */
    protected boolean distinct;

    /**
     * SUPPLIERS_AVAILABLE_CATEGORYS
     */
    protected List oredCriteria;

    /**
     * SUPPLIERS_AVAILABLE_CATEGORYS SuppliersAvailableCategorysExample
     */
    public SuppliersAvailableCategorysExample() {
        oredCriteria = new ArrayList();
    }

    /**
     * SUPPLIERS_AVAILABLE_CATEGORYS SuppliersAvailableCategorysExample
     */
    protected SuppliersAvailableCategorysExample(SuppliersAvailableCategorysExample example) {
        this.orderByClause = example.orderByClause;
        this.oredCriteria = example.oredCriteria;
        this.distinct = example.distinct;
    }

    /**
     * SUPPLIERS_AVAILABLE_CATEGORYS setOrderByClause
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * SUPPLIERS_AVAILABLE_CATEGORYS getOrderByClause
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * SUPPLIERS_AVAILABLE_CATEGORYS setDistinct
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * SUPPLIERS_AVAILABLE_CATEGORYS isDistinct
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * SUPPLIERS_AVAILABLE_CATEGORYS getOredCriteria
     */
    public List getOredCriteria() {
        return oredCriteria;
    }

    /**
     * SUPPLIERS_AVAILABLE_CATEGORYS or
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * SUPPLIERS_AVAILABLE_CATEGORYS or
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * SUPPLIERS_AVAILABLE_CATEGORYS createCriteria
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     * SUPPLIERS_AVAILABLE_CATEGORYS createCriteriaInternal
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * SUPPLIERS_AVAILABLE_CATEGORYS clear
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * SUPPLIERS_AVAILABLE_CATEGORYS
     */
    protected abstract static class GeneratedCriteria {
        protected List criteriaWithoutValue;

        protected List criteriaWithSingleValue;

        protected List criteriaWithListValue;

        protected List criteriaWithBetweenValue;

        protected GeneratedCriteria() {
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

        public Criteria andSacIdIsNull() {
            addCriterion("SAC_ID is null");
            return (Criteria) this;
        }

        public Criteria andSacIdIsNotNull() {
            addCriterion("SAC_ID is not null");
            return (Criteria) this;
        }

        public Criteria andSacIdEqualTo(Long value) {
            addCriterion("SAC_ID =", value, "sacId");
            return (Criteria) this;
        }

        public Criteria andSacIdNotEqualTo(Long value) {
            addCriterion("SAC_ID <>", value, "sacId");
            return (Criteria) this;
        }

        public Criteria andSacIdGreaterThan(Long value) {
            addCriterion("SAC_ID >", value, "sacId");
            return (Criteria) this;
        }

        public Criteria andSacIdGreaterThanOrEqualTo(Long value) {
            addCriterion("SAC_ID >=", value, "sacId");
            return (Criteria) this;
        }

        public Criteria andSacIdLessThan(Long value) {
            addCriterion("SAC_ID <", value, "sacId");
            return (Criteria) this;
        }

        public Criteria andSacIdLessThanOrEqualTo(Long value) {
            addCriterion("SAC_ID <=", value, "sacId");
            return (Criteria) this;
        }

        public Criteria andSacIdIn(List values) {
            addCriterion("SAC_ID in", values, "sacId");
            return (Criteria) this;
        }

        public Criteria andSacIdNotIn(List values) {
            addCriterion("SAC_ID not in", values, "sacId");
            return (Criteria) this;
        }

        public Criteria andSacIdBetween(Long value1, Long value2) {
            addCriterion("SAC_ID between", value1, value2, "sacId");
            return (Criteria) this;
        }

        public Criteria andSacIdNotBetween(Long value1, Long value2) {
            addCriterion("SAC_ID not between", value1, value2, "sacId");
            return (Criteria) this;
        }

        public Criteria andSupplierIdIsNull() {
            addCriterion("SUPPLIER_ID is null");
            return (Criteria) this;
        }

        public Criteria andSupplierIdIsNotNull() {
            addCriterion("SUPPLIER_ID is not null");
            return (Criteria) this;
        }

        public Criteria andSupplierIdEqualTo(Long value) {
            addCriterion("SUPPLIER_ID =", value, "supplierId");
            return (Criteria) this;
        }

        public Criteria andSupplierIdNotEqualTo(Long value) {
            addCriterion("SUPPLIER_ID <>", value, "supplierId");
            return (Criteria) this;
        }

        public Criteria andSupplierIdGreaterThan(Long value) {
            addCriterion("SUPPLIER_ID >", value, "supplierId");
            return (Criteria) this;
        }

        public Criteria andSupplierIdGreaterThanOrEqualTo(Long value) {
            addCriterion("SUPPLIER_ID >=", value, "supplierId");
            return (Criteria) this;
        }

        public Criteria andSupplierIdLessThan(Long value) {
            addCriterion("SUPPLIER_ID <", value, "supplierId");
            return (Criteria) this;
        }

        public Criteria andSupplierIdLessThanOrEqualTo(Long value) {
            addCriterion("SUPPLIER_ID <=", value, "supplierId");
            return (Criteria) this;
        }

        public Criteria andSupplierIdIn(List values) {
            addCriterion("SUPPLIER_ID in", values, "supplierId");
            return (Criteria) this;
        }

        public Criteria andSupplierIdNotIn(List values) {
            addCriterion("SUPPLIER_ID not in", values, "supplierId");
            return (Criteria) this;
        }

        public Criteria andSupplierIdBetween(Long value1, Long value2) {
            addCriterion("SUPPLIER_ID between", value1, value2, "supplierId");
            return (Criteria) this;
        }

        public Criteria andSupplierIdNotBetween(Long value1, Long value2) {
            addCriterion("SUPPLIER_ID not between", value1, value2, "supplierId");
            return (Criteria) this;
        }

        public Criteria andCategoryIdIsNull() {
            addCriterion("CATEGORY_ID is null");
            return (Criteria) this;
        }

        public Criteria andCategoryIdIsNotNull() {
            addCriterion("CATEGORY_ID is not null");
            return (Criteria) this;
        }

        public Criteria andCategoryIdEqualTo(Long value) {
            addCriterion("CATEGORY_ID =", value, "categoryId");
            return (Criteria) this;
        }

        public Criteria andCategoryIdNotEqualTo(Long value) {
            addCriterion("CATEGORY_ID <>", value, "categoryId");
            return (Criteria) this;
        }

        public Criteria andCategoryIdGreaterThan(Long value) {
            addCriterion("CATEGORY_ID >", value, "categoryId");
            return (Criteria) this;
        }

        public Criteria andCategoryIdGreaterThanOrEqualTo(Long value) {
            addCriterion("CATEGORY_ID >=", value, "categoryId");
            return (Criteria) this;
        }

        public Criteria andCategoryIdLessThan(Long value) {
            addCriterion("CATEGORY_ID <", value, "categoryId");
            return (Criteria) this;
        }

        public Criteria andCategoryIdLessThanOrEqualTo(Long value) {
            addCriterion("CATEGORY_ID <=", value, "categoryId");
            return (Criteria) this;
        }

        public Criteria andCategoryIdIn(List values) {
            addCriterion("CATEGORY_ID in", values, "categoryId");
            return (Criteria) this;
        }

        public Criteria andCategoryIdNotIn(List values) {
            addCriterion("CATEGORY_ID not in", values, "categoryId");
            return (Criteria) this;
        }

        public Criteria andCategoryIdBetween(Long value1, Long value2) {
            addCriterion("CATEGORY_ID between", value1, value2, "categoryId");
            return (Criteria) this;
        }

        public Criteria andCategoryIdNotBetween(Long value1, Long value2) {
            addCriterion("CATEGORY_ID not between", value1, value2, "categoryId");
            return (Criteria) this;
        }

        public Criteria andCommissionRatioIsNull() {
            addCriterion("COMMISSION_RATIO is null");
            return (Criteria) this;
        }

        public Criteria andCommissionRatioIsNotNull() {
            addCriterion("COMMISSION_RATIO is not null");
            return (Criteria) this;
        }

        public Criteria andCommissionRatioEqualTo(BigDecimal value) {
            addCriterion("COMMISSION_RATIO =", value, "commissionRatio");
            return (Criteria) this;
        }

        public Criteria andCommissionRatioNotEqualTo(BigDecimal value) {
            addCriterion("COMMISSION_RATIO <>", value, "commissionRatio");
            return (Criteria) this;
        }

        public Criteria andCommissionRatioGreaterThan(BigDecimal value) {
            addCriterion("COMMISSION_RATIO >", value, "commissionRatio");
            return (Criteria) this;
        }

        public Criteria andCommissionRatioGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("COMMISSION_RATIO >=", value, "commissionRatio");
            return (Criteria) this;
        }

        public Criteria andCommissionRatioLessThan(BigDecimal value) {
            addCriterion("COMMISSION_RATIO <", value, "commissionRatio");
            return (Criteria) this;
        }

        public Criteria andCommissionRatioLessThanOrEqualTo(BigDecimal value) {
            addCriterion("COMMISSION_RATIO <=", value, "commissionRatio");
            return (Criteria) this;
        }

        public Criteria andCommissionRatioIn(List values) {
            addCriterion("COMMISSION_RATIO in", values, "commissionRatio");
            return (Criteria) this;
        }

        public Criteria andCommissionRatioNotIn(List values) {
            addCriterion("COMMISSION_RATIO not in", values, "commissionRatio");
            return (Criteria) this;
        }

        public Criteria andCommissionRatioBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("COMMISSION_RATIO between", value1, value2, "commissionRatio");
            return (Criteria) this;
        }

        public Criteria andCommissionRatioNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("COMMISSION_RATIO not between", value1, value2, "commissionRatio");
            return (Criteria) this;
        }
    }

    /**
     * SUPPLIERS_AVAILABLE_CATEGORYS
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }
}