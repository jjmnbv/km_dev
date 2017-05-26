package com.kmzyc.supplier.model.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SuppliersWarehouseExample {
    /**
     * SUPPLIERS_WAREHOUSE
     */
    protected String orderByClause;

    /**
     * SUPPLIERS_WAREHOUSE
     */
    protected boolean distinct;

    /**
     * SUPPLIERS_WAREHOUSE
     */
    protected List oredCriteria;

    /**
     * SUPPLIERS_WAREHOUSE SuppliersWarehouseExample
     */
    public SuppliersWarehouseExample() {
        oredCriteria = new ArrayList();
    }

    /**
     * SUPPLIERS_WAREHOUSE SuppliersWarehouseExample
     */
    protected SuppliersWarehouseExample(SuppliersWarehouseExample example) {
        this.orderByClause = example.orderByClause;
        this.oredCriteria = example.oredCriteria;
        this.distinct = example.distinct;
    }

    /**
     * SUPPLIERS_WAREHOUSE setOrderByClause
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * SUPPLIERS_WAREHOUSE getOrderByClause
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * SUPPLIERS_WAREHOUSE setDistinct
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * SUPPLIERS_WAREHOUSE isDistinct
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * SUPPLIERS_WAREHOUSE getOredCriteria
     */
    public List getOredCriteria() {
        return oredCriteria;
    }

    /**
     * SUPPLIERS_WAREHOUSE or
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * SUPPLIERS_WAREHOUSE or
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * SUPPLIERS_WAREHOUSE createCriteria
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     * SUPPLIERS_WAREHOUSE createCriteriaInternal
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * SUPPLIERS_WAREHOUSE clear
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * SUPPLIERS_WAREHOUSE
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

        public Criteria andSupWarehouseIdIsNull() {
            addCriterion("SUP_WAREHOUSE_ID is null");
            return (Criteria) this;
        }

        public Criteria andSupWarehouseIdIsNotNull() {
            addCriterion("SUP_WAREHOUSE_ID is not null");
            return (Criteria) this;
        }

        public Criteria andSupWarehouseIdEqualTo(Long value) {
            addCriterion("SUP_WAREHOUSE_ID =", value, "supWarehouseId");
            return (Criteria) this;
        }

        public Criteria andSupWarehouseIdNotEqualTo(Long value) {
            addCriterion("SUP_WAREHOUSE_ID <>", value, "supWarehouseId");
            return (Criteria) this;
        }

        public Criteria andSupWarehouseIdGreaterThan(Long value) {
            addCriterion("SUP_WAREHOUSE_ID >", value, "supWarehouseId");
            return (Criteria) this;
        }

        public Criteria andSupWarehouseIdGreaterThanOrEqualTo(Long value) {
            addCriterion("SUP_WAREHOUSE_ID >=", value, "supWarehouseId");
            return (Criteria) this;
        }

        public Criteria andSupWarehouseIdLessThan(Long value) {
            addCriterion("SUP_WAREHOUSE_ID <", value, "supWarehouseId");
            return (Criteria) this;
        }

        public Criteria andSupWarehouseIdLessThanOrEqualTo(Long value) {
            addCriterion("SUP_WAREHOUSE_ID <=", value, "supWarehouseId");
            return (Criteria) this;
        }

        public Criteria andSupWarehouseIdIn(List values) {
            addCriterion("SUP_WAREHOUSE_ID in", values, "supWarehouseId");
            return (Criteria) this;
        }

        public Criteria andSupWarehouseIdNotIn(List values) {
            addCriterion("SUP_WAREHOUSE_ID not in", values, "supWarehouseId");
            return (Criteria) this;
        }

        public Criteria andSupWarehouseIdBetween(Long value1, Long value2) {
            addCriterion("SUP_WAREHOUSE_ID between", value1, value2, "supWarehouseId");
            return (Criteria) this;
        }

        public Criteria andSupWarehouseIdNotBetween(Long value1, Long value2) {
            addCriterion("SUP_WAREHOUSE_ID not between", value1, value2, "supWarehouseId");
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

        public Criteria andWarehouseIdIsNull() {
            addCriterion("WAREHOUSE_ID is null");
            return (Criteria) this;
        }

        public Criteria andWarehouseIdIsNotNull() {
            addCriterion("WAREHOUSE_ID is not null");
            return (Criteria) this;
        }

        public Criteria andWarehouseIdEqualTo(Long value) {
            addCriterion("WAREHOUSE_ID =", value, "warehouseId");
            return (Criteria) this;
        }

        public Criteria andWarehouseIdNotEqualTo(Long value) {
            addCriterion("WAREHOUSE_ID <>", value, "warehouseId");
            return (Criteria) this;
        }

        public Criteria andWarehouseIdGreaterThan(Long value) {
            addCriterion("WAREHOUSE_ID >", value, "warehouseId");
            return (Criteria) this;
        }

        public Criteria andWarehouseIdGreaterThanOrEqualTo(Long value) {
            addCriterion("WAREHOUSE_ID >=", value, "warehouseId");
            return (Criteria) this;
        }

        public Criteria andWarehouseIdLessThan(Long value) {
            addCriterion("WAREHOUSE_ID <", value, "warehouseId");
            return (Criteria) this;
        }

        public Criteria andWarehouseIdLessThanOrEqualTo(Long value) {
            addCriterion("WAREHOUSE_ID <=", value, "warehouseId");
            return (Criteria) this;
        }

        public Criteria andWarehouseIdIn(List values) {
            addCriterion("WAREHOUSE_ID in", values, "warehouseId");
            return (Criteria) this;
        }

        public Criteria andWarehouseIdNotIn(List values) {
            addCriterion("WAREHOUSE_ID not in", values, "warehouseId");
            return (Criteria) this;
        }

        public Criteria andWarehouseIdBetween(Long value1, Long value2) {
            addCriterion("WAREHOUSE_ID between", value1, value2, "warehouseId");
            return (Criteria) this;
        }

        public Criteria andWarehouseIdNotBetween(Long value1, Long value2) {
            addCriterion("WAREHOUSE_ID not between", value1, value2, "warehouseId");
            return (Criteria) this;
        }
    }

    /**
     * SUPPLIERS_WAREHOUSE
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }
}