package com.kmzyc.supplier.model.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SuppliersCertificateExample {
    /**
     * SUPPLIERS_CERTIFICATE
     */
    protected String orderByClause;

    /**
     * SUPPLIERS_CERTIFICATE
     */
    protected boolean distinct;

    /**
     * SUPPLIERS_CERTIFICATE
     */
    protected List oredCriteria;

    /**
     * SUPPLIERS_CERTIFICATE SuppliersCertificateExample
     */
    public SuppliersCertificateExample() {
        oredCriteria = new ArrayList();
    }

    /**
     * SUPPLIERS_CERTIFICATE SuppliersCertificateExample
     */
    protected SuppliersCertificateExample(SuppliersCertificateExample example) {
        this.orderByClause = example.orderByClause;
        this.oredCriteria = example.oredCriteria;
        this.distinct = example.distinct;
    }

    /**
     * SUPPLIERS_CERTIFICATE setOrderByClause
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * SUPPLIERS_CERTIFICATE getOrderByClause
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * SUPPLIERS_CERTIFICATE setDistinct
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * SUPPLIERS_CERTIFICATE isDistinct
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * SUPPLIERS_CERTIFICATE getOredCriteria
     */
    public List getOredCriteria() {
        return oredCriteria;
    }

    /**
     * SUPPLIERS_CERTIFICATE or
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * SUPPLIERS_CERTIFICATE or
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * SUPPLIERS_CERTIFICATE createCriteria
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     * SUPPLIERS_CERTIFICATE createCriteriaInternal
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * SUPPLIERS_CERTIFICATE clear
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * SUPPLIERS_CERTIFICATE
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

        public Criteria andScIdIsNull() {
            addCriterion("SC_ID is null");
            return (Criteria) this;
        }

        public Criteria andScIdIsNotNull() {
            addCriterion("SC_ID is not null");
            return (Criteria) this;
        }

        public Criteria andScIdEqualTo(Long value) {
            addCriterion("SC_ID =", value, "scId");
            return (Criteria) this;
        }

        public Criteria andScIdNotEqualTo(Long value) {
            addCriterion("SC_ID <>", value, "scId");
            return (Criteria) this;
        }

        public Criteria andScIdGreaterThan(Long value) {
            addCriterion("SC_ID >", value, "scId");
            return (Criteria) this;
        }

        public Criteria andScIdGreaterThanOrEqualTo(Long value) {
            addCriterion("SC_ID >=", value, "scId");
            return (Criteria) this;
        }

        public Criteria andScIdLessThan(Long value) {
            addCriterion("SC_ID <", value, "scId");
            return (Criteria) this;
        }

        public Criteria andScIdLessThanOrEqualTo(Long value) {
            addCriterion("SC_ID <=", value, "scId");
            return (Criteria) this;
        }

        public Criteria andScIdIn(List values) {
            addCriterion("SC_ID in", values, "scId");
            return (Criteria) this;
        }

        public Criteria andScIdNotIn(List values) {
            addCriterion("SC_ID not in", values, "scId");
            return (Criteria) this;
        }

        public Criteria andScIdBetween(Long value1, Long value2) {
            addCriterion("SC_ID between", value1, value2, "scId");
            return (Criteria) this;
        }

        public Criteria andScIdNotBetween(Long value1, Long value2) {
            addCriterion("SC_ID not between", value1, value2, "scId");
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

        public Criteria andDocTypeIsNull() {
            addCriterion("DOC_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andDocTypeIsNotNull() {
            addCriterion("DOC_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andDocTypeEqualTo(String value) {
            addCriterion("DOC_TYPE =", value, "docType");
            return (Criteria) this;
        }

        public Criteria andDocTypeNotEqualTo(String value) {
            addCriterion("DOC_TYPE <>", value, "docType");
            return (Criteria) this;
        }

        public Criteria andDocTypeGreaterThan(String value) {
            addCriterion("DOC_TYPE >", value, "docType");
            return (Criteria) this;
        }

        public Criteria andDocTypeGreaterThanOrEqualTo(String value) {
            addCriterion("DOC_TYPE >=", value, "docType");
            return (Criteria) this;
        }

        public Criteria andDocTypeLessThan(String value) {
            addCriterion("DOC_TYPE <", value, "docType");
            return (Criteria) this;
        }

        public Criteria andDocTypeLessThanOrEqualTo(String value) {
            addCriterion("DOC_TYPE <=", value, "docType");
            return (Criteria) this;
        }

        public Criteria andDocTypeLike(String value) {
            addCriterion("DOC_TYPE like", value, "docType");
            return (Criteria) this;
        }

        public Criteria andDocTypeNotLike(String value) {
            addCriterion("DOC_TYPE not like", value, "docType");
            return (Criteria) this;
        }

        public Criteria andDocTypeIn(List values) {
            addCriterion("DOC_TYPE in", values, "docType");
            return (Criteria) this;
        }

        public Criteria andDocTypeNotIn(List values) {
            addCriterion("DOC_TYPE not in", values, "docType");
            return (Criteria) this;
        }

        public Criteria andDocTypeBetween(String value1, String value2) {
            addCriterion("DOC_TYPE between", value1, value2, "docType");
            return (Criteria) this;
        }

        public Criteria andDocTypeNotBetween(String value1, String value2) {
            addCriterion("DOC_TYPE not between", value1, value2, "docType");
            return (Criteria) this;
        }

        public Criteria andDocCodeIsNull() {
            addCriterion("DOC_CODE is null");
            return (Criteria) this;
        }

        public Criteria andDocCodeIsNotNull() {
            addCriterion("DOC_CODE is not null");
            return (Criteria) this;
        }

        public Criteria andDocCodeEqualTo(String value) {
            addCriterion("DOC_CODE =", value, "docCode");
            return (Criteria) this;
        }

        public Criteria andDocCodeNotEqualTo(String value) {
            addCriterion("DOC_CODE <>", value, "docCode");
            return (Criteria) this;
        }

        public Criteria andDocCodeGreaterThan(String value) {
            addCriterion("DOC_CODE >", value, "docCode");
            return (Criteria) this;
        }

        public Criteria andDocCodeGreaterThanOrEqualTo(String value) {
            addCriterion("DOC_CODE >=", value, "docCode");
            return (Criteria) this;
        }

        public Criteria andDocCodeLessThan(String value) {
            addCriterion("DOC_CODE <", value, "docCode");
            return (Criteria) this;
        }

        public Criteria andDocCodeLessThanOrEqualTo(String value) {
            addCriterion("DOC_CODE <=", value, "docCode");
            return (Criteria) this;
        }

        public Criteria andDocCodeLike(String value) {
            addCriterion("DOC_CODE like", value, "docCode");
            return (Criteria) this;
        }

        public Criteria andDocCodeNotLike(String value) {
            addCriterion("DOC_CODE not like", value, "docCode");
            return (Criteria) this;
        }

        public Criteria andDocCodeIn(List values) {
            addCriterion("DOC_CODE in", values, "docCode");
            return (Criteria) this;
        }

        public Criteria andDocCodeNotIn(List values) {
            addCriterion("DOC_CODE not in", values, "docCode");
            return (Criteria) this;
        }

        public Criteria andDocCodeBetween(String value1, String value2) {
            addCriterion("DOC_CODE between", value1, value2, "docCode");
            return (Criteria) this;
        }

        public Criteria andDocCodeNotBetween(String value1, String value2) {
            addCriterion("DOC_CODE not between", value1, value2, "docCode");
            return (Criteria) this;
        }

        public Criteria andFilePathIsNull() {
            addCriterion("FILE_PATH is null");
            return (Criteria) this;
        }

        public Criteria andFilePathIsNotNull() {
            addCriterion("FILE_PATH is not null");
            return (Criteria) this;
        }

        public Criteria andFilePathEqualTo(String value) {
            addCriterion("FILE_PATH =", value, "filePath");
            return (Criteria) this;
        }

        public Criteria andFilePathNotEqualTo(String value) {
            addCriterion("FILE_PATH <>", value, "filePath");
            return (Criteria) this;
        }

        public Criteria andFilePathGreaterThan(String value) {
            addCriterion("FILE_PATH >", value, "filePath");
            return (Criteria) this;
        }

        public Criteria andFilePathGreaterThanOrEqualTo(String value) {
            addCriterion("FILE_PATH >=", value, "filePath");
            return (Criteria) this;
        }

        public Criteria andFilePathLessThan(String value) {
            addCriterion("FILE_PATH <", value, "filePath");
            return (Criteria) this;
        }

        public Criteria andFilePathLessThanOrEqualTo(String value) {
            addCriterion("FILE_PATH <=", value, "filePath");
            return (Criteria) this;
        }

        public Criteria andFilePathLike(String value) {
            addCriterion("FILE_PATH like", value, "filePath");
            return (Criteria) this;
        }

        public Criteria andFilePathNotLike(String value) {
            addCriterion("FILE_PATH not like", value, "filePath");
            return (Criteria) this;
        }

        public Criteria andFilePathIn(List values) {
            addCriterion("FILE_PATH in", values, "filePath");
            return (Criteria) this;
        }

        public Criteria andFilePathNotIn(List values) {
            addCriterion("FILE_PATH not in", values, "filePath");
            return (Criteria) this;
        }

        public Criteria andFilePathBetween(String value1, String value2) {
            addCriterion("FILE_PATH between", value1, value2, "filePath");
            return (Criteria) this;
        }

        public Criteria andFilePathNotBetween(String value1, String value2) {
            addCriterion("FILE_PATH not between", value1, value2, "filePath");
            return (Criteria) this;
        }

        public Criteria andFileNameIsNull() {
            addCriterion("FILE_NAME is null");
            return (Criteria) this;
        }

        public Criteria andFileNameIsNotNull() {
            addCriterion("FILE_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andFileNameEqualTo(String value) {
            addCriterion("FILE_NAME =", value, "fileName");
            return (Criteria) this;
        }

        public Criteria andFileNameNotEqualTo(String value) {
            addCriterion("FILE_NAME <>", value, "fileName");
            return (Criteria) this;
        }

        public Criteria andFileNameGreaterThan(String value) {
            addCriterion("FILE_NAME >", value, "fileName");
            return (Criteria) this;
        }

        public Criteria andFileNameGreaterThanOrEqualTo(String value) {
            addCriterion("FILE_NAME >=", value, "fileName");
            return (Criteria) this;
        }

        public Criteria andFileNameLessThan(String value) {
            addCriterion("FILE_NAME <", value, "fileName");
            return (Criteria) this;
        }

        public Criteria andFileNameLessThanOrEqualTo(String value) {
            addCriterion("FILE_NAME <=", value, "fileName");
            return (Criteria) this;
        }

        public Criteria andFileNameLike(String value) {
            addCriterion("FILE_NAME like", value, "fileName");
            return (Criteria) this;
        }

        public Criteria andFileNameNotLike(String value) {
            addCriterion("FILE_NAME not like", value, "fileName");
            return (Criteria) this;
        }

        public Criteria andFileNameIn(List values) {
            addCriterion("FILE_NAME in", values, "fileName");
            return (Criteria) this;
        }

        public Criteria andFileNameNotIn(List values) {
            addCriterion("FILE_NAME not in", values, "fileName");
            return (Criteria) this;
        }

        public Criteria andFileNameBetween(String value1, String value2) {
            addCriterion("FILE_NAME between", value1, value2, "fileName");
            return (Criteria) this;
        }

        public Criteria andFileNameNotBetween(String value1, String value2) {
            addCriterion("FILE_NAME not between", value1, value2, "fileName");
            return (Criteria) this;
        }
    }

    /**
     * SUPPLIERS_CERTIFICATE
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }
}