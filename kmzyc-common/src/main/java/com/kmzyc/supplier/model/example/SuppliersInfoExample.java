package com.kmzyc.supplier.model.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SuppliersInfoExample {
    /**
     * SUPPLIERS_INFO
     */
    protected String orderByClause;

    /**
     * SUPPLIERS_INFO
     */
    protected boolean distinct;

    /**
     * SUPPLIERS_INFO
     */
    protected List oredCriteria;

    /**
     * SUPPLIERS_INFO SuppliersInfoExample
     */
    public SuppliersInfoExample() {
        oredCriteria = new ArrayList();
    }

    /**
     * SUPPLIERS_INFO SuppliersInfoExample
     */
    protected SuppliersInfoExample(SuppliersInfoExample example) {
        this.orderByClause = example.orderByClause;
        this.oredCriteria = example.oredCriteria;
        this.distinct = example.distinct;
    }

    /**
     * SUPPLIERS_INFO setOrderByClause
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * SUPPLIERS_INFO getOrderByClause
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * SUPPLIERS_INFO setDistinct
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * SUPPLIERS_INFO isDistinct
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * SUPPLIERS_INFO getOredCriteria
     */
    public List getOredCriteria() {
        return oredCriteria;
    }

    /**
     * SUPPLIERS_INFO or
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * SUPPLIERS_INFO or
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * SUPPLIERS_INFO createCriteria
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     * SUPPLIERS_INFO createCriteriaInternal
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * SUPPLIERS_INFO clear
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * SUPPLIERS_INFO
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

        public Criteria andUserIdIsNull() {
            addCriterion("USER_ID is null");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNotNull() {
            addCriterion("USER_ID is not null");
            return (Criteria) this;
        }

        public Criteria andUserIdEqualTo(Long value) {
            addCriterion("USER_ID =", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotEqualTo(Long value) {
            addCriterion("USER_ID <>", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThan(Long value) {
            addCriterion("USER_ID >", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThanOrEqualTo(Long value) {
            addCriterion("USER_ID >=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThan(Long value) {
            addCriterion("USER_ID <", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThanOrEqualTo(Long value) {
            addCriterion("USER_ID <=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdIn(List values) {
            addCriterion("USER_ID in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotIn(List values) {
            addCriterion("USER_ID not in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdBetween(Long value1, Long value2) {
            addCriterion("USER_ID between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotBetween(Long value1, Long value2) {
            addCriterion("USER_ID not between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andSaleProductDescribeIsNull() {
            addCriterion("SALE_PRODUCT_DESCRIBE is null");
            return (Criteria) this;
        }

        public Criteria andSaleProductDescribeIsNotNull() {
            addCriterion("SALE_PRODUCT_DESCRIBE is not null");
            return (Criteria) this;
        }

        public Criteria andSaleProductDescribeEqualTo(String value) {
            addCriterion("SALE_PRODUCT_DESCRIBE =", value, "saleProductDescribe");
            return (Criteria) this;
        }

        public Criteria andSaleProductDescribeNotEqualTo(String value) {
            addCriterion("SALE_PRODUCT_DESCRIBE <>", value, "saleProductDescribe");
            return (Criteria) this;
        }

        public Criteria andSaleProductDescribeGreaterThan(String value) {
            addCriterion("SALE_PRODUCT_DESCRIBE >", value, "saleProductDescribe");
            return (Criteria) this;
        }

        public Criteria andSaleProductDescribeGreaterThanOrEqualTo(String value) {
            addCriterion("SALE_PRODUCT_DESCRIBE >=", value, "saleProductDescribe");
            return (Criteria) this;
        }

        public Criteria andSaleProductDescribeLessThan(String value) {
            addCriterion("SALE_PRODUCT_DESCRIBE <", value, "saleProductDescribe");
            return (Criteria) this;
        }

        public Criteria andSaleProductDescribeLessThanOrEqualTo(String value) {
            addCriterion("SALE_PRODUCT_DESCRIBE <=", value, "saleProductDescribe");
            return (Criteria) this;
        }

        public Criteria andSaleProductDescribeLike(String value) {
            addCriterion("SALE_PRODUCT_DESCRIBE like", value, "saleProductDescribe");
            return (Criteria) this;
        }

        public Criteria andSaleProductDescribeNotLike(String value) {
            addCriterion("SALE_PRODUCT_DESCRIBE not like", value, "saleProductDescribe");
            return (Criteria) this;
        }

        public Criteria andSaleProductDescribeIn(List values) {
            addCriterion("SALE_PRODUCT_DESCRIBE in", values, "saleProductDescribe");
            return (Criteria) this;
        }

        public Criteria andSaleProductDescribeNotIn(List values) {
            addCriterion("SALE_PRODUCT_DESCRIBE not in", values, "saleProductDescribe");
            return (Criteria) this;
        }

        public Criteria andSaleProductDescribeBetween(String value1, String value2) {
            addCriterion("SALE_PRODUCT_DESCRIBE between", value1, value2, "saleProductDescribe");
            return (Criteria) this;
        }

        public Criteria andSaleProductDescribeNotBetween(String value1, String value2) {
            addCriterion("SALE_PRODUCT_DESCRIBE not between", value1, value2, "saleProductDescribe");
            return (Criteria) this;
        }

        public Criteria andSettlementCycleIsNull() {
            addCriterion("SETTLEMENT_CYCLE is null");
            return (Criteria) this;
        }

        public Criteria andSettlementCycleIsNotNull() {
            addCriterion("SETTLEMENT_CYCLE is not null");
            return (Criteria) this;
        }

        public Criteria andSettlementCycleEqualTo(Short value) {
            addCriterion("SETTLEMENT_CYCLE =", value, "settlementCycle");
            return (Criteria) this;
        }

        public Criteria andSettlementCycleNotEqualTo(Short value) {
            addCriterion("SETTLEMENT_CYCLE <>", value, "settlementCycle");
            return (Criteria) this;
        }

        public Criteria andSettlementCycleGreaterThan(Short value) {
            addCriterion("SETTLEMENT_CYCLE >", value, "settlementCycle");
            return (Criteria) this;
        }

        public Criteria andSettlementCycleGreaterThanOrEqualTo(Short value) {
            addCriterion("SETTLEMENT_CYCLE >=", value, "settlementCycle");
            return (Criteria) this;
        }

        public Criteria andSettlementCycleLessThan(Short value) {
            addCriterion("SETTLEMENT_CYCLE <", value, "settlementCycle");
            return (Criteria) this;
        }

        public Criteria andSettlementCycleLessThanOrEqualTo(Short value) {
            addCriterion("SETTLEMENT_CYCLE <=", value, "settlementCycle");
            return (Criteria) this;
        }

        public Criteria andSettlementCycleIn(List values) {
            addCriterion("SETTLEMENT_CYCLE in", values, "settlementCycle");
            return (Criteria) this;
        }

        public Criteria andSettlementCycleNotIn(List values) {
            addCriterion("SETTLEMENT_CYCLE not in", values, "settlementCycle");
            return (Criteria) this;
        }

        public Criteria andSettlementCycleBetween(Short value1, Short value2) {
            addCriterion("SETTLEMENT_CYCLE between", value1, value2, "settlementCycle");
            return (Criteria) this;
        }

        public Criteria andSettlementCycleNotBetween(Short value1, Short value2) {
            addCriterion("SETTLEMENT_CYCLE not between", value1, value2, "settlementCycle");
            return (Criteria) this;
        }

        public Criteria andSettlementWayIsNull() {
            addCriterion("SETTLEMENT_WAY is null");
            return (Criteria) this;
        }

        public Criteria andSettlementWayIsNotNull() {
            addCriterion("SETTLEMENT_WAY is not null");
            return (Criteria) this;
        }

        public Criteria andSettlementWayEqualTo(Short value) {
            addCriterion("SETTLEMENT_WAY =", value, "settlementWay");
            return (Criteria) this;
        }

        public Criteria andSettlementWayNotEqualTo(Short value) {
            addCriterion("SETTLEMENT_WAY <>", value, "settlementWay");
            return (Criteria) this;
        }

        public Criteria andSettlementWayGreaterThan(Short value) {
            addCriterion("SETTLEMENT_WAY >", value, "settlementWay");
            return (Criteria) this;
        }

        public Criteria andSettlementWayGreaterThanOrEqualTo(Short value) {
            addCriterion("SETTLEMENT_WAY >=", value, "settlementWay");
            return (Criteria) this;
        }

        public Criteria andSettlementWayLessThan(Short value) {
            addCriterion("SETTLEMENT_WAY <", value, "settlementWay");
            return (Criteria) this;
        }

        public Criteria andSettlementWayLessThanOrEqualTo(Short value) {
            addCriterion("SETTLEMENT_WAY <=", value, "settlementWay");
            return (Criteria) this;
        }

        public Criteria andSettlementWayIn(List values) {
            addCriterion("SETTLEMENT_WAY in", values, "settlementWay");
            return (Criteria) this;
        }

        public Criteria andSettlementWayNotIn(List values) {
            addCriterion("SETTLEMENT_WAY not in", values, "settlementWay");
            return (Criteria) this;
        }

        public Criteria andSettlementWayBetween(Short value1, Short value2) {
            addCriterion("SETTLEMENT_WAY between", value1, value2, "settlementWay");
            return (Criteria) this;
        }

        public Criteria andSettlementWayNotBetween(Short value1, Short value2) {
            addCriterion("SETTLEMENT_WAY not between", value1, value2, "settlementWay");
            return (Criteria) this;
        }

        public Criteria andSupplierTypeIsNull() {
            addCriterion("SUPPLIER_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andSupplierTypeIsNotNull() {
            addCriterion("SUPPLIER_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andSupplierTypeEqualTo(Short value) {
            addCriterion("SUPPLIER_TYPE =", value, "supplierType");
            return (Criteria) this;
        }

        public Criteria andSupplierTypeNotEqualTo(Short value) {
            addCriterion("SUPPLIER_TYPE <>", value, "supplierType");
            return (Criteria) this;
        }

        public Criteria andSupplierTypeGreaterThan(Short value) {
            addCriterion("SUPPLIER_TYPE >", value, "supplierType");
            return (Criteria) this;
        }

        public Criteria andSupplierTypeGreaterThanOrEqualTo(Short value) {
            addCriterion("SUPPLIER_TYPE >=", value, "supplierType");
            return (Criteria) this;
        }

        public Criteria andSupplierTypeLessThan(Short value) {
            addCriterion("SUPPLIER_TYPE <", value, "supplierType");
            return (Criteria) this;
        }

        public Criteria andSupplierTypeLessThanOrEqualTo(Short value) {
            addCriterion("SUPPLIER_TYPE <=", value, "supplierType");
            return (Criteria) this;
        }

        public Criteria andSupplierTypeIn(List values) {
            addCriterion("SUPPLIER_TYPE in", values, "supplierType");
            return (Criteria) this;
        }

        public Criteria andSupplierTypeNotIn(List values) {
            addCriterion("SUPPLIER_TYPE not in", values, "supplierType");
            return (Criteria) this;
        }

        public Criteria andSupplierTypeBetween(Short value1, Short value2) {
            addCriterion("SUPPLIER_TYPE between", value1, value2, "supplierType");
            return (Criteria) this;
        }

        public Criteria andSupplierTypeNotBetween(Short value1, Short value2) {
            addCriterion("SUPPLIER_TYPE not between", value1, value2, "supplierType");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("STATUS is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("STATUS is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(Short value) {
            addCriterion("STATUS =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(Short value) {
            addCriterion("STATUS <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(Short value) {
            addCriterion("STATUS >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Short value) {
            addCriterion("STATUS >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(Short value) {
            addCriterion("STATUS <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(Short value) {
            addCriterion("STATUS <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List values) {
            addCriterion("STATUS in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List values) {
            addCriterion("STATUS not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(Short value1, Short value2) {
            addCriterion("STATUS between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(Short value1, Short value2) {
            addCriterion("STATUS not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andDescribeIsNull() {
            addCriterion("DESCRIBE is null");
            return (Criteria) this;
        }

        public Criteria andDescribeIsNotNull() {
            addCriterion("DESCRIBE is not null");
            return (Criteria) this;
        }

        public Criteria andDescribeEqualTo(String value) {
            addCriterion("DESCRIBE =", value, "describe");
            return (Criteria) this;
        }

        public Criteria andDescribeNotEqualTo(String value) {
            addCriterion("DESCRIBE <>", value, "describe");
            return (Criteria) this;
        }

        public Criteria andDescribeGreaterThan(String value) {
            addCriterion("DESCRIBE >", value, "describe");
            return (Criteria) this;
        }

        public Criteria andDescribeGreaterThanOrEqualTo(String value) {
            addCriterion("DESCRIBE >=", value, "describe");
            return (Criteria) this;
        }

        public Criteria andDescribeLessThan(String value) {
            addCriterion("DESCRIBE <", value, "describe");
            return (Criteria) this;
        }

        public Criteria andDescribeLessThanOrEqualTo(String value) {
            addCriterion("DESCRIBE <=", value, "describe");
            return (Criteria) this;
        }

        public Criteria andDescribeLike(String value) {
            addCriterion("DESCRIBE like", value, "describe");
            return (Criteria) this;
        }

        public Criteria andDescribeNotLike(String value) {
            addCriterion("DESCRIBE not like", value, "describe");
            return (Criteria) this;
        }

        public Criteria andDescribeIn(List values) {
            addCriterion("DESCRIBE in", values, "describe");
            return (Criteria) this;
        }

        public Criteria andDescribeNotIn(List values) {
            addCriterion("DESCRIBE not in", values, "describe");
            return (Criteria) this;
        }

        public Criteria andDescribeBetween(String value1, String value2) {
            addCriterion("DESCRIBE between", value1, value2, "describe");
            return (Criteria) this;
        }

        public Criteria andDescribeNotBetween(String value1, String value2) {
            addCriterion("DESCRIBE not between", value1, value2, "describe");
            return (Criteria) this;
        }
    }

    /**
     * SUPPLIERS_INFO
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }
}