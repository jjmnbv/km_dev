package com.pltfm.app.vobject;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 出库明细单条件查询
 * @author luoyi
 * @createDate 2013/08/15
 *
 */
@SuppressWarnings("rawtypes")
public class StockOutDetailExample  implements Serializable{
	private static final long serialVersionUID = 491474918795309149L;

	/**
     * STOCK_OUT_DETAIL
     */
    protected String orderByClause;

    /**
     * STOCK_OUT_DETAIL
     */
    protected boolean distinct;

    /**
     * STOCK_OUT_DETAIL
     */
    protected List oredCriteria;

    /**
     * STOCK_OUT_DETAIL StockOutDetailExample
     */
	public StockOutDetailExample() {
        oredCriteria = new ArrayList();
    }

    /**
     * STOCK_OUT_DETAIL StockOutDetailExample
     */
    protected StockOutDetailExample(StockOutDetailExample example) {
        this.orderByClause = example.orderByClause;
        this.oredCriteria = example.oredCriteria;
        this.distinct = example.distinct;
    }

    /**
     * STOCK_OUT_DETAIL setOrderByClause
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * STOCK_OUT_DETAIL getOrderByClause
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * STOCK_OUT_DETAIL setDistinct
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * STOCK_OUT_DETAIL isDistinct
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * STOCK_OUT_DETAIL getOredCriteria
     */
    public List getOredCriteria() {
        return oredCriteria;
    }

    /**
     * STOCK_OUT_DETAIL or
     */
    @SuppressWarnings("unchecked")
	public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * STOCK_OUT_DETAIL or
     */
    @SuppressWarnings("unchecked")
	public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * STOCK_OUT_DETAIL createCriteria
     */
    @SuppressWarnings("unchecked")
	public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     * STOCK_OUT_DETAIL createCriteriaInternal
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * STOCK_OUT_DETAIL clear
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * STOCK_OUT_DETAIL
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

        @SuppressWarnings("unchecked")
		protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteriaWithoutValue.add(condition);
        }

        @SuppressWarnings("unchecked")
		protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            Map map = new HashMap();
            map.put("condition", condition);
            map.put("value", value);
            criteriaWithSingleValue.add(map);
        }

        @SuppressWarnings("unchecked")
		protected void addCriterion(String condition, List values, String property) {
            if (values == null || values.size() == 0) {
                throw new RuntimeException("Value list for " + property + " cannot be null or empty");
            }
            Map map = new HashMap();
            map.put("condition", condition);
            map.put("values", values);
            criteriaWithListValue.add(map);
        }

        @SuppressWarnings("unchecked")
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

        public Criteria andDetailIdIsNull() {
            addCriterion("DETAIL_ID is null");
            return (Criteria) this;
        }

        public Criteria andDetailIdIsNotNull() {
            addCriterion("DETAIL_ID is not null");
            return (Criteria) this;
        }

        public Criteria andDetailIdEqualTo(Long value) {
            addCriterion("DETAIL_ID =", value, "detailId");
            return (Criteria) this;
        }

        public Criteria andDetailIdNotEqualTo(Long value) {
            addCriterion("DETAIL_ID <>", value, "detailId");
            return (Criteria) this;
        }

        public Criteria andDetailIdGreaterThan(Long value) {
            addCriterion("DETAIL_ID >", value, "detailId");
            return (Criteria) this;
        }

        public Criteria andDetailIdGreaterThanOrEqualTo(Long value) {
            addCriterion("DETAIL_ID >=", value, "detailId");
            return (Criteria) this;
        }

        public Criteria andDetailIdLessThan(Long value) {
            addCriterion("DETAIL_ID <", value, "detailId");
            return (Criteria) this;
        }

        public Criteria andDetailIdLessThanOrEqualTo(Long value) {
            addCriterion("DETAIL_ID <=", value, "detailId");
            return (Criteria) this;
        }

        public Criteria andDetailIdIn(List values) {
            addCriterion("DETAIL_ID in", values, "detailId");
            return (Criteria) this;
        }

        public Criteria andDetailIdNotIn(List values) {
            addCriterion("DETAIL_ID not in", values, "detailId");
            return (Criteria) this;
        }

        public Criteria andDetailIdBetween(Long value1, Long value2) {
            addCriterion("DETAIL_ID between", value1, value2, "detailId");
            return (Criteria) this;
        }

        public Criteria andDetailIdNotBetween(Long value1, Long value2) {
            addCriterion("DETAIL_ID not between", value1, value2, "detailId");
            return (Criteria) this;
        }

        public Criteria andStockOutIdIsNull() {
            addCriterion("STOCK_OUT_ID is null");
            return (Criteria) this;
        }

        public Criteria andStockOutIdIsNotNull() {
            addCriterion("STOCK_OUT_ID is not null");
            return (Criteria) this;
        }

        public Criteria andStockOutIdEqualTo(Long value) {
            addCriterion("STOCK_OUT_ID =", value, "stockOutId");
            return (Criteria) this;
        }

        public Criteria andStockOutIdNotEqualTo(Long value) {
            addCriterion("STOCK_OUT_ID <>", value, "stockOutId");
            return (Criteria) this;
        }

        public Criteria andStockOutIdGreaterThan(Long value) {
            addCriterion("STOCK_OUT_ID >", value, "stockOutId");
            return (Criteria) this;
        }

        public Criteria andStockOutIdGreaterThanOrEqualTo(Long value) {
            addCriterion("STOCK_OUT_ID >=", value, "stockOutId");
            return (Criteria) this;
        }

        public Criteria andStockOutIdLessThan(Long value) {
            addCriterion("STOCK_OUT_ID <", value, "stockOutId");
            return (Criteria) this;
        }

        public Criteria andStockOutIdLessThanOrEqualTo(Long value) {
            addCriterion("STOCK_OUT_ID <=", value, "stockOutId");
            return (Criteria) this;
        }

        public Criteria andStockOutIdIn(List values) {
            addCriterion("STOCK_OUT_ID in", values, "stockOutId");
            return (Criteria) this;
        }

        public Criteria andStockOutIdNotIn(List values) {
            addCriterion("STOCK_OUT_ID not in", values, "stockOutId");
            return (Criteria) this;
        }

        public Criteria andStockOutIdBetween(Long value1, Long value2) {
            addCriterion("STOCK_OUT_ID between", value1, value2, "stockOutId");
            return (Criteria) this;
        }

        public Criteria andStockOutIdNotBetween(Long value1, Long value2) {
            addCriterion("STOCK_OUT_ID not between", value1, value2, "stockOutId");
            return (Criteria) this;
        }

        public Criteria andStockIdIsNull() {
            addCriterion("STOCK_ID is null");
            return (Criteria) this;
        }

        public Criteria andStockIdIsNotNull() {
            addCriterion("STOCK_ID is not null");
            return (Criteria) this;
        }

        public Criteria andStockIdEqualTo(Long value) {
            addCriterion("STOCK_ID =", value, "stockId");
            return (Criteria) this;
        }

        public Criteria andStockIdNotEqualTo(Long value) {
            addCriterion("STOCK_ID <>", value, "stockId");
            return (Criteria) this;
        }

        public Criteria andStockIdGreaterThan(Long value) {
            addCriterion("STOCK_ID >", value, "stockId");
            return (Criteria) this;
        }

        public Criteria andStockIdGreaterThanOrEqualTo(Long value) {
            addCriterion("STOCK_ID >=", value, "stockId");
            return (Criteria) this;
        }

        public Criteria andStockIdLessThan(Long value) {
            addCriterion("STOCK_ID <", value, "stockId");
            return (Criteria) this;
        }

        public Criteria andStockIdLessThanOrEqualTo(Long value) {
            addCriterion("STOCK_ID <=", value, "stockId");
            return (Criteria) this;
        }

        public Criteria andStockIdIn(List values) {
            addCriterion("STOCK_ID in", values, "stockId");
            return (Criteria) this;
        }

        public Criteria andStockIdNotIn(List values) {
            addCriterion("STOCK_ID not in", values, "stockId");
            return (Criteria) this;
        }

        public Criteria andStockIdBetween(Long value1, Long value2) {
            addCriterion("STOCK_ID between", value1, value2, "stockId");
            return (Criteria) this;
        }

        public Criteria andStockIdNotBetween(Long value1, Long value2) {
            addCriterion("STOCK_ID not between", value1, value2, "stockId");
            return (Criteria) this;
        }

        public Criteria andProductIdIsNull() {
            addCriterion("PRODUCT_ID is null");
            return (Criteria) this;
        }

        public Criteria andProductIdIsNotNull() {
            addCriterion("PRODUCT_ID is not null");
            return (Criteria) this;
        }

        public Criteria andProductIdEqualTo(Integer value) {
            addCriterion("PRODUCT_ID =", value, "productId");
            return (Criteria) this;
        }

        public Criteria andProductIdNotEqualTo(Integer value) {
            addCriterion("PRODUCT_ID <>", value, "productId");
            return (Criteria) this;
        }

        public Criteria andProductIdGreaterThan(Integer value) {
            addCriterion("PRODUCT_ID >", value, "productId");
            return (Criteria) this;
        }

        public Criteria andProductIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("PRODUCT_ID >=", value, "productId");
            return (Criteria) this;
        }

        public Criteria andProductIdLessThan(Integer value) {
            addCriterion("PRODUCT_ID <", value, "productId");
            return (Criteria) this;
        }

        public Criteria andProductIdLessThanOrEqualTo(Integer value) {
            addCriterion("PRODUCT_ID <=", value, "productId");
            return (Criteria) this;
        }

        public Criteria andProductIdIn(List values) {
            addCriterion("PRODUCT_ID in", values, "productId");
            return (Criteria) this;
        }

        public Criteria andProductIdNotIn(List values) {
            addCriterion("PRODUCT_ID not in", values, "productId");
            return (Criteria) this;
        }

        public Criteria andProductIdBetween(Integer value1, Integer value2) {
            addCriterion("PRODUCT_ID between", value1, value2, "productId");
            return (Criteria) this;
        }

        public Criteria andProductIdNotBetween(Integer value1, Integer value2) {
            addCriterion("PRODUCT_ID not between", value1, value2, "productId");
            return (Criteria) this;
        }

        public Criteria andProductNameIsNull() {
            addCriterion("PRODUCT_NAME is null");
            return (Criteria) this;
        }

        public Criteria andProductNameIsNotNull() {
            addCriterion("PRODUCT_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andProductNameEqualTo(String value) {
            addCriterion("PRODUCT_NAME =", value, "productName");
            return (Criteria) this;
        }

        public Criteria andProductNameNotEqualTo(String value) {
            addCriterion("PRODUCT_NAME <>", value, "productName");
            return (Criteria) this;
        }

        public Criteria andProductNameGreaterThan(String value) {
            addCriterion("PRODUCT_NAME >", value, "productName");
            return (Criteria) this;
        }

        public Criteria andProductNameGreaterThanOrEqualTo(String value) {
            addCriterion("PRODUCT_NAME >=", value, "productName");
            return (Criteria) this;
        }

        public Criteria andProductNameLessThan(String value) {
            addCriterion("PRODUCT_NAME <", value, "productName");
            return (Criteria) this;
        }

        public Criteria andProductNameLessThanOrEqualTo(String value) {
            addCriterion("PRODUCT_NAME <=", value, "productName");
            return (Criteria) this;
        }

        public Criteria andProductNameLike(String value) {
            addCriterion("PRODUCT_NAME like", "%"+value+"%", "productName");
            return (Criteria) this;
        }

        public Criteria andProductNameNotLike(String value) {
            addCriterion("PRODUCT_NAME not like", "%"+value+"%", "productName");
            return (Criteria) this;
        }

        public Criteria andProductNameIn(List values) {
            addCriterion("PRODUCT_NAME in", values, "productName");
            return (Criteria) this;
        }

        public Criteria andProductNameNotIn(List values) {
            addCriterion("PRODUCT_NAME not in", values, "productName");
            return (Criteria) this;
        }

        public Criteria andProductNameBetween(String value1, String value2) {
            addCriterion("PRODUCT_NAME between", value1, value2, "productName");
            return (Criteria) this;
        }

        public Criteria andProductNameNotBetween(String value1, String value2) {
            addCriterion("PRODUCT_NAME not between", value1, value2, "productName");
            return (Criteria) this;
        }

        public Criteria andProductNoIsNull() {
            addCriterion("PRODUCT_NO is null");
            return (Criteria) this;
        }

        public Criteria andProductNoIsNotNull() {
            addCriterion("PRODUCT_NO is not null");
            return (Criteria) this;
        }

        public Criteria andProductNoEqualTo(String value) {
            addCriterion("PRODUCT_NO =", value, "productNo");
            return (Criteria) this;
        }

        public Criteria andProductNoNotEqualTo(String value) {
            addCriterion("PRODUCT_NO <>", value, "productNo");
            return (Criteria) this;
        }

        public Criteria andProductNoGreaterThan(String value) {
            addCriterion("PRODUCT_NO >", value, "productNo");
            return (Criteria) this;
        }

        public Criteria andProductNoGreaterThanOrEqualTo(String value) {
            addCriterion("PRODUCT_NO >=", value, "productNo");
            return (Criteria) this;
        }

        public Criteria andProductNoLessThan(String value) {
            addCriterion("PRODUCT_NO <", value, "productNo");
            return (Criteria) this;
        }

        public Criteria andProductNoLessThanOrEqualTo(String value) {
            addCriterion("PRODUCT_NO <=", value, "productNo");
            return (Criteria) this;
        }

        public Criteria andProductNoLike(String value) {
            addCriterion("PRODUCT_NO like", "%"+value+"%", "productNo");
            return (Criteria) this;
        }

        public Criteria andProductNoNotLike(String value) {
            addCriterion("PRODUCT_NO not like", "%"+value+"%", "productNo");
            return (Criteria) this;
        }

        public Criteria andProductNoIn(List values) {
            addCriterion("PRODUCT_NO in", values, "productNo");
            return (Criteria) this;
        }

        public Criteria andProductNoNotIn(List values) {
            addCriterion("PRODUCT_NO not in", values, "productNo");
            return (Criteria) this;
        }

        public Criteria andProductNoBetween(String value1, String value2) {
            addCriterion("PRODUCT_NO between", value1, value2, "productNo");
            return (Criteria) this;
        }

        public Criteria andProductNoNotBetween(String value1, String value2) {
            addCriterion("PRODUCT_NO not between", value1, value2, "productNo");
            return (Criteria) this;
        }

        public Criteria andProductSkuIdIsNull() {
            addCriterion("PRODUCT_SKU_ID is null");
            return (Criteria) this;
        }

        public Criteria andProductSkuIdIsNotNull() {
            addCriterion("PRODUCT_SKU_ID is not null");
            return (Criteria) this;
        }

        public Criteria andProductSkuIdEqualTo(Integer value) {
            addCriterion("PRODUCT_SKU_ID =", value, "productSkuId");
            return (Criteria) this;
        }

        public Criteria andProductSkuIdNotEqualTo(Integer value) {
            addCriterion("PRODUCT_SKU_ID <>", value, "productSkuId");
            return (Criteria) this;
        }

        public Criteria andProductSkuIdGreaterThan(Integer value) {
            addCriterion("PRODUCT_SKU_ID >", value, "productSkuId");
            return (Criteria) this;
        }

        public Criteria andProductSkuIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("PRODUCT_SKU_ID >=", value, "productSkuId");
            return (Criteria) this;
        }

        public Criteria andProductSkuIdLessThan(Integer value) {
            addCriterion("PRODUCT_SKU_ID <", value, "productSkuId");
            return (Criteria) this;
        }

        public Criteria andProductSkuIdLessThanOrEqualTo(Integer value) {
            addCriterion("PRODUCT_SKU_ID <=", value, "productSkuId");
            return (Criteria) this;
        }

        public Criteria andProductSkuIdIn(List values) {
            addCriterion("PRODUCT_SKU_ID in", values, "productSkuId");
            return (Criteria) this;
        }

        public Criteria andProductSkuIdNotIn(List values) {
            addCriterion("PRODUCT_SKU_ID not in", values, "productSkuId");
            return (Criteria) this;
        }

        public Criteria andProductSkuIdBetween(Integer value1, Integer value2) {
            addCriterion("PRODUCT_SKU_ID between", value1, value2, "productSkuId");
            return (Criteria) this;
        }

        public Criteria andProductSkuIdNotBetween(Integer value1, Integer value2) {
            addCriterion("PRODUCT_SKU_ID not between", value1, value2, "productSkuId");
            return (Criteria) this;
        }

        public Criteria andProductSkuValueIsNull() {
            addCriterion("PRODUCT_SKU_VALUE is null");
            return (Criteria) this;
        }

        public Criteria andProductSkuValueIsNotNull() {
            addCriterion("PRODUCT_SKU_VALUE is not null");
            return (Criteria) this;
        }

        public Criteria andProductSkuValueEqualTo(String value) {
            addCriterion("PRODUCT_SKU_VALUE =", value, "productSkuValue");
            return (Criteria) this;
        }

        public Criteria andProductSkuValueNotEqualTo(String value) {
            addCriterion("PRODUCT_SKU_VALUE <>", value, "productSkuValue");
            return (Criteria) this;
        }

        public Criteria andProductSkuValueGreaterThan(String value) {
            addCriterion("PRODUCT_SKU_VALUE >", value, "productSkuValue");
            return (Criteria) this;
        }

        public Criteria andProductSkuValueGreaterThanOrEqualTo(String value) {
            addCriterion("PRODUCT_SKU_VALUE >=", value, "productSkuValue");
            return (Criteria) this;
        }

        public Criteria andProductSkuValueLessThan(String value) {
            addCriterion("PRODUCT_SKU_VALUE <", value, "productSkuValue");
            return (Criteria) this;
        }

        public Criteria andProductSkuValueLessThanOrEqualTo(String value) {
            addCriterion("PRODUCT_SKU_VALUE <=", value, "productSkuValue");
            return (Criteria) this;
        }

        public Criteria andProductSkuValueLike(String value) {
            addCriterion("PRODUCT_SKU_VALUE like", "%"+value+"%", "productSkuValue");
            return (Criteria) this;
        }

        public Criteria andProductSkuValueNotLike(String value) {
            addCriterion("PRODUCT_SKU_VALUE not like", "%"+value+"%", "productSkuValue");
            return (Criteria) this;
        }

        public Criteria andProductSkuValueIn(List values) {
            addCriterion("PRODUCT_SKU_VALUE in", values, "productSkuValue");
            return (Criteria) this;
        }

        public Criteria andProductSkuValueNotIn(List values) {
            addCriterion("PRODUCT_SKU_VALUE not in", values, "productSkuValue");
            return (Criteria) this;
        }

        public Criteria andProductSkuValueBetween(String value1, String value2) {
            addCriterion("PRODUCT_SKU_VALUE between", value1, value2, "productSkuValue");
            return (Criteria) this;
        }

        public Criteria andProductSkuValueNotBetween(String value1, String value2) {
            addCriterion("PRODUCT_SKU_VALUE not between", value1, value2, "productSkuValue");
            return (Criteria) this;
        }

        public Criteria andBarCodeIsNull() {
            addCriterion("BAR_CODE is null");
            return (Criteria) this;
        }

        public Criteria andBarCodeIsNotNull() {
            addCriterion("BAR_CODE is not null");
            return (Criteria) this;
        }

        public Criteria andBarCodeEqualTo(String value) {
            addCriterion("BAR_CODE =", value, "barCode");
            return (Criteria) this;
        }

        public Criteria andBarCodeNotEqualTo(String value) {
            addCriterion("BAR_CODE <>", value, "barCode");
            return (Criteria) this;
        }

        public Criteria andBarCodeGreaterThan(String value) {
            addCriterion("BAR_CODE >", value, "barCode");
            return (Criteria) this;
        }

        public Criteria andBarCodeGreaterThanOrEqualTo(String value) {
            addCriterion("BAR_CODE >=", value, "barCode");
            return (Criteria) this;
        }

        public Criteria andBarCodeLessThan(String value) {
            addCriterion("BAR_CODE <", value, "barCode");
            return (Criteria) this;
        }

        public Criteria andBarCodeLessThanOrEqualTo(String value) {
            addCriterion("BAR_CODE <=", value, "barCode");
            return (Criteria) this;
        }

        public Criteria andBarCodeLike(String value) {
            addCriterion("BAR_CODE like", "%"+value+"%", "barCode");
            return (Criteria) this;
        }

        public Criteria andBarCodeNotLike(String value) {
            addCriterion("BAR_CODE not like", "%"+value+"%", "barCode");
            return (Criteria) this;
        }

        public Criteria andBarCodeIn(List values) {
            addCriterion("BAR_CODE in", values, "barCode");
            return (Criteria) this;
        }

        public Criteria andBarCodeNotIn(List values) {
            addCriterion("BAR_CODE not in", values, "barCode");
            return (Criteria) this;
        }

        public Criteria andBarCodeBetween(String value1, String value2) {
            addCriterion("BAR_CODE between", value1, value2, "barCode");
            return (Criteria) this;
        }

        public Criteria andBarCodeNotBetween(String value1, String value2) {
            addCriterion("BAR_CODE not between", value1, value2, "barCode");
            return (Criteria) this;
        }

        public Criteria andBillDetailIdIsNull() {
            addCriterion("BILL_DETAIL_ID is null");
            return (Criteria) this;
        }

        public Criteria andBillDetailIdIsNotNull() {
            addCriterion("BILL_DETAIL_ID is not null");
            return (Criteria) this;
        }

        public Criteria andBillDetailIdEqualTo(Integer value) {
            addCriterion("BILL_DETAIL_ID =", value, "billDetailId");
            return (Criteria) this;
        }

        public Criteria andBillDetailIdNotEqualTo(Integer value) {
            addCriterion("BILL_DETAIL_ID <>", value, "billDetailId");
            return (Criteria) this;
        }

        public Criteria andBillDetailIdGreaterThan(Integer value) {
            addCriterion("BILL_DETAIL_ID >", value, "billDetailId");
            return (Criteria) this;
        }

        public Criteria andBillDetailIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("BILL_DETAIL_ID >=", value, "billDetailId");
            return (Criteria) this;
        }

        public Criteria andBillDetailIdLessThan(Integer value) {
            addCriterion("BILL_DETAIL_ID <", value, "billDetailId");
            return (Criteria) this;
        }

        public Criteria andBillDetailIdLessThanOrEqualTo(Integer value) {
            addCriterion("BILL_DETAIL_ID <=", value, "billDetailId");
            return (Criteria) this;
        }

        public Criteria andBillDetailIdIn(List values) {
            addCriterion("BILL_DETAIL_ID in", values, "billDetailId");
            return (Criteria) this;
        }

        public Criteria andBillDetailIdNotIn(List values) {
            addCriterion("BILL_DETAIL_ID not in", values, "billDetailId");
            return (Criteria) this;
        }

        public Criteria andBillDetailIdBetween(Integer value1, Integer value2) {
            addCriterion("BILL_DETAIL_ID between", value1, value2, "billDetailId");
            return (Criteria) this;
        }

        public Criteria andBillDetailIdNotBetween(Integer value1, Integer value2) {
            addCriterion("BILL_DETAIL_ID not between", value1, value2, "billDetailId");
            return (Criteria) this;
        }

        public Criteria andBillTypeIsNull() {
            addCriterion("BILL_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andBillTypeIsNotNull() {
            addCriterion("BILL_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andBillTypeEqualTo(Short value) {
            addCriterion("BILL_TYPE =", value, "billType");
            return (Criteria) this;
        }

        public Criteria andBillTypeNotEqualTo(Short value) {
            addCriterion("BILL_TYPE <>", value, "billType");
            return (Criteria) this;
        }

        public Criteria andBillTypeGreaterThan(Short value) {
            addCriterion("BILL_TYPE >", value, "billType");
            return (Criteria) this;
        }

        public Criteria andBillTypeGreaterThanOrEqualTo(Short value) {
            addCriterion("BILL_TYPE >=", value, "billType");
            return (Criteria) this;
        }

        public Criteria andBillTypeLessThan(Short value) {
            addCriterion("BILL_TYPE <", value, "billType");
            return (Criteria) this;
        }

        public Criteria andBillTypeLessThanOrEqualTo(Short value) {
            addCriterion("BILL_TYPE <=", value, "billType");
            return (Criteria) this;
        }

        public Criteria andBillTypeIn(List values) {
            addCriterion("BILL_TYPE in", values, "billType");
            return (Criteria) this;
        }

        public Criteria andBillTypeNotIn(List values) {
            addCriterion("BILL_TYPE not in", values, "billType");
            return (Criteria) this;
        }

        public Criteria andBillTypeBetween(Short value1, Short value2) {
            addCriterion("BILL_TYPE between", value1, value2, "billType");
            return (Criteria) this;
        }

        public Criteria andBillTypeNotBetween(Short value1, Short value2) {
            addCriterion("BILL_TYPE not between", value1, value2, "billType");
            return (Criteria) this;
        }

        public Criteria andQuantityIsNull() {
            addCriterion("QUANTITY is null");
            return (Criteria) this;
        }

        public Criteria andQuantityIsNotNull() {
            addCriterion("QUANTITY is not null");
            return (Criteria) this;
        }

        public Criteria andQuantityEqualTo(Integer value) {
            addCriterion("QUANTITY =", value, "quantity");
            return (Criteria) this;
        }

        public Criteria andQuantityNotEqualTo(Integer value) {
            addCriterion("QUANTITY <>", value, "quantity");
            return (Criteria) this;
        }

        public Criteria andQuantityGreaterThan(Integer value) {
            addCriterion("QUANTITY >", value, "quantity");
            return (Criteria) this;
        }

        public Criteria andQuantityGreaterThanOrEqualTo(Integer value) {
            addCriterion("QUANTITY >=", value, "quantity");
            return (Criteria) this;
        }

        public Criteria andQuantityLessThan(Integer value) {
            addCriterion("QUANTITY <", value, "quantity");
            return (Criteria) this;
        }

        public Criteria andQuantityLessThanOrEqualTo(Integer value) {
            addCriterion("QUANTITY <=", value, "quantity");
            return (Criteria) this;
        }

        public Criteria andQuantityIn(List values) {
            addCriterion("QUANTITY in", values, "quantity");
            return (Criteria) this;
        }

        public Criteria andQuantityNotIn(List values) {
            addCriterion("QUANTITY not in", values, "quantity");
            return (Criteria) this;
        }

        public Criteria andQuantityBetween(Integer value1, Integer value2) {
            addCriterion("QUANTITY between", value1, value2, "quantity");
            return (Criteria) this;
        }

        public Criteria andQuantityNotBetween(Integer value1, Integer value2) {
            addCriterion("QUANTITY not between", value1, value2, "quantity");
            return (Criteria) this;
        }

        public Criteria andTaxPriceIsNull() {
            addCriterion("TAX_PRICE is null");
            return (Criteria) this;
        }

        public Criteria andTaxPriceIsNotNull() {
            addCriterion("TAX_PRICE is not null");
            return (Criteria) this;
        }

        public Criteria andTaxPriceEqualTo(BigDecimal value) {
            addCriterion("TAX_PRICE =", value, "taxPrice");
            return (Criteria) this;
        }

        public Criteria andTaxPriceNotEqualTo(BigDecimal value) {
            addCriterion("TAX_PRICE <>", value, "taxPrice");
            return (Criteria) this;
        }

        public Criteria andTaxPriceGreaterThan(BigDecimal value) {
            addCriterion("TAX_PRICE >", value, "taxPrice");
            return (Criteria) this;
        }

        public Criteria andTaxPriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("TAX_PRICE >=", value, "taxPrice");
            return (Criteria) this;
        }

        public Criteria andTaxPriceLessThan(BigDecimal value) {
            addCriterion("TAX_PRICE <", value, "taxPrice");
            return (Criteria) this;
        }

        public Criteria andTaxPriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("TAX_PRICE <=", value, "taxPrice");
            return (Criteria) this;
        }

        public Criteria andTaxPriceIn(List values) {
            addCriterion("TAX_PRICE in", values, "taxPrice");
            return (Criteria) this;
        }

        public Criteria andTaxPriceNotIn(List values) {
            addCriterion("TAX_PRICE not in", values, "taxPrice");
            return (Criteria) this;
        }

        public Criteria andTaxPriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("TAX_PRICE between", value1, value2, "taxPrice");
            return (Criteria) this;
        }

        public Criteria andTaxPriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("TAX_PRICE not between", value1, value2, "taxPrice");
            return (Criteria) this;
        }

        public Criteria andTaxSumIsNull() {
            addCriterion("TAX_SUM is null");
            return (Criteria) this;
        }

        public Criteria andTaxSumIsNotNull() {
            addCriterion("TAX_SUM is not null");
            return (Criteria) this;
        }

        public Criteria andTaxSumEqualTo(BigDecimal value) {
            addCriterion("TAX_SUM =", value, "taxSum");
            return (Criteria) this;
        }

        public Criteria andTaxSumNotEqualTo(BigDecimal value) {
            addCriterion("TAX_SUM <>", value, "taxSum");
            return (Criteria) this;
        }

        public Criteria andTaxSumGreaterThan(BigDecimal value) {
            addCriterion("TAX_SUM >", value, "taxSum");
            return (Criteria) this;
        }

        public Criteria andTaxSumGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("TAX_SUM >=", value, "taxSum");
            return (Criteria) this;
        }

        public Criteria andTaxSumLessThan(BigDecimal value) {
            addCriterion("TAX_SUM <", value, "taxSum");
            return (Criteria) this;
        }

        public Criteria andTaxSumLessThanOrEqualTo(BigDecimal value) {
            addCriterion("TAX_SUM <=", value, "taxSum");
            return (Criteria) this;
        }

        public Criteria andTaxSumIn(List values) {
            addCriterion("TAX_SUM in", values, "taxSum");
            return (Criteria) this;
        }

        public Criteria andTaxSumNotIn(List values) {
            addCriterion("TAX_SUM not in", values, "taxSum");
            return (Criteria) this;
        }

        public Criteria andTaxSumBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("TAX_SUM between", value1, value2, "taxSum");
            return (Criteria) this;
        }

        public Criteria andTaxSumNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("TAX_SUM not between", value1, value2, "taxSum");
            return (Criteria) this;
        }

        public Criteria andTaxRateIsNull() {
            addCriterion("TAX_RATE is null");
            return (Criteria) this;
        }

        public Criteria andTaxRateIsNotNull() {
            addCriterion("TAX_RATE is not null");
            return (Criteria) this;
        }

        public Criteria andTaxRateEqualTo(BigDecimal value) {
            addCriterion("TAX_RATE =", value, "taxRate");
            return (Criteria) this;
        }

        public Criteria andTaxRateNotEqualTo(BigDecimal value) {
            addCriterion("TAX_RATE <>", value, "taxRate");
            return (Criteria) this;
        }

        public Criteria andTaxRateGreaterThan(BigDecimal value) {
            addCriterion("TAX_RATE >", value, "taxRate");
            return (Criteria) this;
        }

        public Criteria andTaxRateGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("TAX_RATE >=", value, "taxRate");
            return (Criteria) this;
        }

        public Criteria andTaxRateLessThan(BigDecimal value) {
            addCriterion("TAX_RATE <", value, "taxRate");
            return (Criteria) this;
        }

        public Criteria andTaxRateLessThanOrEqualTo(BigDecimal value) {
            addCriterion("TAX_RATE <=", value, "taxRate");
            return (Criteria) this;
        }

        public Criteria andTaxRateIn(List values) {
            addCriterion("TAX_RATE in", values, "taxRate");
            return (Criteria) this;
        }

        public Criteria andTaxRateNotIn(List values) {
            addCriterion("TAX_RATE not in", values, "taxRate");
            return (Criteria) this;
        }

        public Criteria andTaxRateBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("TAX_RATE between", value1, value2, "taxRate");
            return (Criteria) this;
        }

        public Criteria andTaxRateNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("TAX_RATE not between", value1, value2, "taxRate");
            return (Criteria) this;
        }

        public Criteria andTaxIsNull() {
            addCriterion("TAX is null");
            return (Criteria) this;
        }

        public Criteria andTaxIsNotNull() {
            addCriterion("TAX is not null");
            return (Criteria) this;
        }

        public Criteria andTaxEqualTo(BigDecimal value) {
            addCriterion("TAX =", value, "tax");
            return (Criteria) this;
        }

        public Criteria andTaxNotEqualTo(BigDecimal value) {
            addCriterion("TAX <>", value, "tax");
            return (Criteria) this;
        }

        public Criteria andTaxGreaterThan(BigDecimal value) {
            addCriterion("TAX >", value, "tax");
            return (Criteria) this;
        }

        public Criteria andTaxGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("TAX >=", value, "tax");
            return (Criteria) this;
        }

        public Criteria andTaxLessThan(BigDecimal value) {
            addCriterion("TAX <", value, "tax");
            return (Criteria) this;
        }

        public Criteria andTaxLessThanOrEqualTo(BigDecimal value) {
            addCriterion("TAX <=", value, "tax");
            return (Criteria) this;
        }

        public Criteria andTaxIn(List values) {
            addCriterion("TAX in", values, "tax");
            return (Criteria) this;
        }

        public Criteria andTaxNotIn(List values) {
            addCriterion("TAX not in", values, "tax");
            return (Criteria) this;
        }

        public Criteria andTaxBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("TAX between", value1, value2, "tax");
            return (Criteria) this;
        }

        public Criteria andTaxNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("TAX not between", value1, value2, "tax");
            return (Criteria) this;
        }

        public Criteria andPriceIsNull() {
            addCriterion("PRICE is null");
            return (Criteria) this;
        }

        public Criteria andPriceIsNotNull() {
            addCriterion("PRICE is not null");
            return (Criteria) this;
        }

        public Criteria andPriceEqualTo(BigDecimal value) {
            addCriterion("PRICE =", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceNotEqualTo(BigDecimal value) {
            addCriterion("PRICE <>", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceGreaterThan(BigDecimal value) {
            addCriterion("PRICE >", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("PRICE >=", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceLessThan(BigDecimal value) {
            addCriterion("PRICE <", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("PRICE <=", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceIn(List values) {
            addCriterion("PRICE in", values, "price");
            return (Criteria) this;
        }

        public Criteria andPriceNotIn(List values) {
            addCriterion("PRICE not in", values, "price");
            return (Criteria) this;
        }

        public Criteria andPriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("PRICE between", value1, value2, "price");
            return (Criteria) this;
        }

        public Criteria andPriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("PRICE not between", value1, value2, "price");
            return (Criteria) this;
        }

        public Criteria andSumIsNull() {
            addCriterion("SUM is null");
            return (Criteria) this;
        }

        public Criteria andSumIsNotNull() {
            addCriterion("SUM is not null");
            return (Criteria) this;
        }

        public Criteria andSumEqualTo(BigDecimal value) {
            addCriterion("SUM =", value, "sum");
            return (Criteria) this;
        }

        public Criteria andSumNotEqualTo(BigDecimal value) {
            addCriterion("SUM <>", value, "sum");
            return (Criteria) this;
        }

        public Criteria andSumGreaterThan(BigDecimal value) {
            addCriterion("SUM >", value, "sum");
            return (Criteria) this;
        }

        public Criteria andSumGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("SUM >=", value, "sum");
            return (Criteria) this;
        }

        public Criteria andSumLessThan(BigDecimal value) {
            addCriterion("SUM <", value, "sum");
            return (Criteria) this;
        }

        public Criteria andSumLessThanOrEqualTo(BigDecimal value) {
            addCriterion("SUM <=", value, "sum");
            return (Criteria) this;
        }

        public Criteria andSumIn(List values) {
            addCriterion("SUM in", values, "sum");
            return (Criteria) this;
        }

        public Criteria andSumNotIn(List values) {
            addCriterion("SUM not in", values, "sum");
            return (Criteria) this;
        }

        public Criteria andSumBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("SUM between", value1, value2, "sum");
            return (Criteria) this;
        }

        public Criteria andSumNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("SUM not between", value1, value2, "sum");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNull() {
            addCriterion("REMARK is null");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNotNull() {
            addCriterion("REMARK is not null");
            return (Criteria) this;
        }

        public Criteria andRemarkEqualTo(String value) {
            addCriterion("REMARK =", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotEqualTo(String value) {
            addCriterion("REMARK <>", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThan(String value) {
            addCriterion("REMARK >", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("REMARK >=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThan(String value) {
            addCriterion("REMARK <", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThanOrEqualTo(String value) {
            addCriterion("REMARK <=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLike(String value) {
            addCriterion("REMARK like", "%"+value+"%", "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotLike(String value) {
            addCriterion("REMARK not like", "%"+value+"%", "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkIn(List values) {
            addCriterion("REMARK in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotIn(List values) {
            addCriterion("REMARK not in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkBetween(String value1, String value2) {
            addCriterion("REMARK between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotBetween(String value1, String value2) {
            addCriterion("REMARK not between", value1, value2, "remark");
            return (Criteria) this;
        }
    }

    /**
     * STOCK_OUT_DETAIL
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }
}