package com.kmzyc.promotion.app.vobject;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unchecked")
public class StockOutExample implements Serializable {
	private static final long serialVersionUID = 5775789881621027628L;

	/**
	 * STOCK_OUT
	 */
	protected String orderByClause;

	/**
	 * STOCK_OUT
	 */
	protected boolean distinct;

	/**
	 * STOCK_OUT
	 */
	protected List oredCriteria;

	/**
	 * STOCK_OUT StockOutExample
	 */
	public StockOutExample() {
		oredCriteria = new ArrayList();
	}

	/**
	 * STOCK_OUT StockOutExample
	 */
	protected StockOutExample(StockOutExample example) {
		this.orderByClause = example.orderByClause;
		this.oredCriteria = example.oredCriteria;
		this.distinct = example.distinct;
	}

	/**
	 * STOCK_OUT setOrderByClause
	 */
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	/**
	 * STOCK_OUT getOrderByClause
	 */
	public String getOrderByClause() {
		return orderByClause;
	}

	/**
	 * STOCK_OUT setDistinct
	 */
	public void setDistinct(boolean distinct) {
		this.distinct = distinct;
	}

	/**
	 * STOCK_OUT isDistinct
	 */
	public boolean isDistinct() {
		return distinct;
	}

	/**
	 * STOCK_OUT getOredCriteria
	 */
	public List getOredCriteria() {
		return oredCriteria;
	}

	/**
	 * STOCK_OUT or
	 */

	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	/**
	 * STOCK_OUT or
	 */

	public Criteria or() {
		Criteria criteria = createCriteriaInternal();
		oredCriteria.add(criteria);
		return criteria;
	}

	/**
	 * STOCK_OUT createCriteria
	 */

	public Criteria createCriteria() {
		Criteria criteria = createCriteriaInternal();
		if (oredCriteria.size() == 0) {
			oredCriteria.add(criteria);
		}
		return criteria;
	}

	/**
	 * STOCK_OUT createCriteriaInternal
	 */
	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	/**
	 * STOCK_OUT clear
	 */
	public void clear() {
		oredCriteria.clear();
		orderByClause = null;
		distinct = false;
	}

	/**
	 * STOCK_OUT
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
			return criteriaWithoutValue.size() > 0 || criteriaWithSingleValue.size() > 0
					|| criteriaWithListValue.size() > 0 || criteriaWithBetweenValue.size() > 0;
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

		protected void addCriterionForJDBCDate(String condition, Date value, String property) {
			if (value == null) {
				throw new RuntimeException("Value for " + property + " cannot be null");
			}
			addCriterion(condition, new java.sql.Date(value.getTime()), property);
		}

		protected void addCriterionForJDBCDate(String condition, List values, String property) {
			if (values == null || values.size() == 0) {
				throw new RuntimeException("Value list for " + property + " cannot be null or empty");
			}
			List dateList = new ArrayList();
			Iterator iter = values.iterator();
			while (iter.hasNext()) {
				dateList.add(new java.sql.Date(((Date) iter.next()).getTime()));
			}
			addCriterion(condition, dateList, property);
		}

		protected void addCriterionForJDBCDate(String condition, Date value1, Date value2, String property) {
			if (value1 == null || value2 == null) {
				throw new RuntimeException("Between values for " + property + " cannot be null");
			}
			addCriterion(condition, new java.sql.Date(value1.getTime()), new java.sql.Date(value2.getTime()), property);
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

		public Criteria andStockOutIdNotBetween(Long value1, Integer value2) {
			addCriterion("STOCK_OUT_ID not between", value1, value2, "stockOutId");
			return (Criteria) this;
		}

		public Criteria andCustomerTypeIdIsNull() {
			addCriterion("CUSTOMER_TYPE_ID is null");
			return (Criteria) this;
		}

		public Criteria andCustomerTypeIdIsNotNull() {
			addCriterion("CUSTOMER_TYPE_ID is not null");
			return (Criteria) this;
		}

		public Criteria andCustomerTypeIdEqualTo(BigDecimal value) {
			addCriterion("CUSTOMER_TYPE_ID =", value, "customerTypeId");
			return (Criteria) this;
		}

		public Criteria andCustomerTypeIdNotEqualTo(BigDecimal value) {
			addCriterion("CUSTOMER_TYPE_ID <>", value, "customerTypeId");
			return (Criteria) this;
		}

		public Criteria andCustomerTypeIdGreaterThan(BigDecimal value) {
			addCriterion("CUSTOMER_TYPE_ID >", value, "customerTypeId");
			return (Criteria) this;
		}

		public Criteria andCustomerTypeIdGreaterThanOrEqualTo(BigDecimal value) {
			addCriterion("CUSTOMER_TYPE_ID >=", value, "customerTypeId");
			return (Criteria) this;
		}

		public Criteria andCustomerTypeIdLessThan(BigDecimal value) {
			addCriterion("CUSTOMER_TYPE_ID <", value, "customerTypeId");
			return (Criteria) this;
		}

		public Criteria andCustomerTypeIdLessThanOrEqualTo(BigDecimal value) {
			addCriterion("CUSTOMER_TYPE_ID <=", value, "customerTypeId");
			return (Criteria) this;
		}

		public Criteria andCustomerTypeIdIn(List values) {
			addCriterion("CUSTOMER_TYPE_ID in", values, "customerTypeId");
			return (Criteria) this;
		}

		public Criteria andCustomerTypeIdNotIn(List values) {
			addCriterion("CUSTOMER_TYPE_ID not in", values, "customerTypeId");
			return (Criteria) this;
		}

		public Criteria andCustomerTypeIdBetween(BigDecimal value1, BigDecimal value2) {
			addCriterion("CUSTOMER_TYPE_ID between", value1, value2, "customerTypeId");
			return (Criteria) this;
		}

		public Criteria andCustomerTypeIdNotBetween(BigDecimal value1, BigDecimal value2) {
			addCriterion("CUSTOMER_TYPE_ID not between", value1, value2, "customerTypeId");
			return (Criteria) this;
		}

		public Criteria andStockOutNoIsNull() {
			addCriterion("STOCK_OUT_NO is null");
			return (Criteria) this;
		}

		public Criteria andStockOutNoIsNotNull() {
			addCriterion("STOCK_OUT_NO is not null");
			return (Criteria) this;
		}

		public Criteria andStockOutNoEqualTo(String value) {
			addCriterion("STOCK_OUT_NO =", value, "stockOutNo");
			return (Criteria) this;
		}

		public Criteria andStockOutNoNotEqualTo(String value) {
			addCriterion("STOCK_OUT_NO <>", value, "stockOutNo");
			return (Criteria) this;
		}

		public Criteria andStockOutNoGreaterThan(String value) {
			addCriterion("STOCK_OUT_NO >", value, "stockOutNo");
			return (Criteria) this;
		}

		public Criteria andStockOutNoGreaterThanOrEqualTo(String value) {
			addCriterion("STOCK_OUT_NO >=", value, "stockOutNo");
			return (Criteria) this;
		}

		public Criteria andStockOutNoLessThan(String value) {
			addCriterion("STOCK_OUT_NO <", value, "stockOutNo");
			return (Criteria) this;
		}

		public Criteria andStockOutNoLessThanOrEqualTo(String value) {
			addCriterion("STOCK_OUT_NO <=", value, "stockOutNo");
			return (Criteria) this;
		}

		public Criteria andStockOutNoLike(String value) {
			addCriterion("STOCK_OUT_NO like", "%" + value + "%", "stockOutNo");
			return (Criteria) this;
		}

		public Criteria andStockOutNoNotLike(String value) {
			addCriterion("STOCK_OUT_NO not like", "%" + value + "%", "stockOutNo");
			return (Criteria) this;
		}

		public Criteria andStockOutNoIn(List values) {
			addCriterion("STOCK_OUT_NO in", values, "stockOutNo");
			return (Criteria) this;
		}

		public Criteria andStockOutNoNotIn(List values) {
			addCriterion("STOCK_OUT_NO not in", values, "stockOutNo");
			return (Criteria) this;
		}

		public Criteria andStockOutNoBetween(String value1, String value2) {
			addCriterion("STOCK_OUT_NO between", value1, value2, "stockOutNo");
			return (Criteria) this;
		}

		public Criteria andStockOutNoNotBetween(String value1, String value2) {
			addCriterion("STOCK_OUT_NO not between", value1, value2, "stockOutNo");
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

		public Criteria andWarehouseIdNotEqualTo(Integer value) {
			addCriterion("WAREHOUSE_ID <>", value, "warehouseId");
			return (Criteria) this;
		}

		public Criteria andWarehouseIdGreLongn(Integer value) {
			addCriterion("WAREHOUSE_ID >", value, "warehouseId");
			return (Criteria) this;
		}

		public Criteria andWarehouseIdGreaterThanOrEqualTo(Integer value) {
			addCriterion("WAREHOUSE_ID >=", value, "warehouseId");
			return (Criteria) this;
		}

		public Criteria andWarehouseIdLessThan(Integer value) {
			addCriterion("WAREHOUSE_ID <", value, "warehouseId");
			return (Criteria) this;
		}

		public Criteria andWarehouseIdLessThanOrEqualTo(Integer value) {
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

		public Criteria andWarehouseIdBetween(Integer value1, Integer value2) {
			addCriterion("WAREHOUSE_ID between", value1, value2, "warehouseId");
			return (Criteria) this;
		}

		public Criteria andWarehouseIdNotBetween(Integer value1, Integer value2) {
			addCriterion("WAREHOUSE_ID not between", value1, value2, "warehouseId");
			return (Criteria) this;
		}

		public Criteria andTypeIsNull() {
			addCriterion("TYPE is null");
			return (Criteria) this;
		}

		public Criteria andTypeIsNotNull() {
			addCriterion("TYPE is not null");
			return (Criteria) this;
		}

		public Criteria andTypeEqualTo(Short value) {
			addCriterion("TYPE =", value, "type");
			return (Criteria) this;
		}

		public Criteria andTypeNotEqualTo(Short value) {
			addCriterion("TYPE <>", value, "type");
			return (Criteria) this;
		}

		public Criteria andTypeGreaterThan(Short value) {
			addCriterion("TYPE >", value, "type");
			return (Criteria) this;
		}

		public Criteria andTypeGreaterThanOrEqualTo(Short value) {
			addCriterion("TYPE >=", value, "type");
			return (Criteria) this;
		}

		public Criteria andTypeLessThan(Short value) {
			addCriterion("TYPE <", value, "type");
			return (Criteria) this;
		}

		public Criteria andTypeLessThanOrEqualTo(Short value) {
			addCriterion("TYPE <=", value, "type");
			return (Criteria) this;
		}

		public Criteria andTypeIn(List values) {
			addCriterion("TYPE in", values, "type");
			return (Criteria) this;
		}

		public Criteria andTypeNotIn(List values) {
			addCriterion("TYPE not in", values, "type");
			return (Criteria) this;
		}

		public Criteria andTypeBetween(Short value1, Short value2) {
			addCriterion("TYPE between", value1, value2, "type");
			return (Criteria) this;
		}

		public Criteria andTypeNotBetween(Short value1, Short value2) {
			addCriterion("TYPE not between", value1, value2, "type");
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

		public Criteria andUserIdEqualTo(Integer value) {
			addCriterion("USER_ID =", value, "userId");
			return (Criteria) this;
		}

		public Criteria andUserIdNotEqualTo(Integer value) {
			addCriterion("USER_ID <>", value, "userId");
			return (Criteria) this;
		}

		public Criteria andUserIdGreaterThan(Integer value) {
			addCriterion("USER_ID >", value, "userId");
			return (Criteria) this;
		}

		public Criteria andUserIdGreaterThanOrEqualTo(Integer value) {
			addCriterion("USER_ID >=", value, "userId");
			return (Criteria) this;
		}

		public Criteria andUserIdLessThan(Integer value) {
			addCriterion("USER_ID <", value, "userId");
			return (Criteria) this;
		}

		public Criteria andUserIdLessThanOrEqualTo(Integer value) {
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

		public Criteria andUserIdBetween(Integer value1, Integer value2) {
			addCriterion("USER_ID between", value1, value2, "userId");
			return (Criteria) this;
		}

		public Criteria andUserIdNotBetween(Integer value1, Integer value2) {
			addCriterion("USER_ID not between", value1, value2, "userId");
			return (Criteria) this;
		}

		public Criteria andUserNameIsNull() {
			addCriterion("USER_NAME is null");
			return (Criteria) this;
		}

		public Criteria andUserNameIsNotNull() {
			addCriterion("USER_NAME is not null");
			return (Criteria) this;
		}

		public Criteria andUserNameEqualTo(String value) {
			addCriterion("USER_NAME =", value, "userName");
			return (Criteria) this;
		}

		public Criteria andUserNameNotEqualTo(String value) {
			addCriterion("USER_NAME <>", value, "userName");
			return (Criteria) this;
		}

		public Criteria andUserNameGreaterThan(String value) {
			addCriterion("USER_NAME >", value, "userName");
			return (Criteria) this;
		}

		public Criteria andUserNameGreaterThanOrEqualTo(String value) {
			addCriterion("USER_NAME >=", value, "userName");
			return (Criteria) this;
		}

		public Criteria andUserNameLessThan(String value) {
			addCriterion("USER_NAME <", value, "userName");
			return (Criteria) this;
		}

		public Criteria andUserNameLessThanOrEqualTo(String value) {
			addCriterion("USER_NAME <=", value, "userName");
			return (Criteria) this;
		}

		public Criteria andUserNameLike(String value) {
			addCriterion("USER_NAME like", "%" + value + "%", "userName");
			return (Criteria) this;
		}

		public Criteria andUserNameNotLike(String value) {
			addCriterion("USER_NAME not like", "%" + value + "%", "userName");
			return (Criteria) this;
		}

		public Criteria andUserNameIn(List values) {
			addCriterion("USER_NAME in", values, "userName");
			return (Criteria) this;
		}

		public Criteria andUserNameNotIn(List values) {
			addCriterion("USER_NAME not in", values, "userName");
			return (Criteria) this;
		}

		public Criteria andUserNameBetween(String value1, String value2) {
			addCriterion("USER_NAME between", value1, value2, "userName");
			return (Criteria) this;
		}

		public Criteria andUserNameNotBetween(String value1, String value2) {
			addCriterion("USER_NAME not between", value1, value2, "userName");
			return (Criteria) this;
		}

		public Criteria andTotalQuantityIsNull() {
			addCriterion("TOTAL_QUANTITY is null");
			return (Criteria) this;
		}

		public Criteria andTotalQuantityIsNotNull() {
			addCriterion("TOTAL_QUANTITY is not null");
			return (Criteria) this;
		}

		public Criteria andTotalQuantityEqualTo(Integer value) {
			addCriterion("TOTAL_QUANTITY =", value, "totalQuantity");
			return (Criteria) this;
		}

		public Criteria andTotalQuantityNotEqualTo(Integer value) {
			addCriterion("TOTAL_QUANTITY <>", value, "totalQuantity");
			return (Criteria) this;
		}

		public Criteria andTotalQuantityGreaterThan(Integer value) {
			addCriterion("TOTAL_QUANTITY >", value, "totalQuantity");
			return (Criteria) this;
		}

		public Criteria andTotalQuantityGreaterThanOrEqualTo(Integer value) {
			addCriterion("TOTAL_QUANTITY >=", value, "totalQuantity");
			return (Criteria) this;
		}

		public Criteria andTotalQuantityLessThan(Integer value) {
			addCriterion("TOTAL_QUANTITY <", value, "totalQuantity");
			return (Criteria) this;
		}

		public Criteria andTotalQuantityLessThanOrEqualTo(Integer value) {
			addCriterion("TOTAL_QUANTITY <=", value, "totalQuantity");
			return (Criteria) this;
		}

		public Criteria andTotalQuantityIn(List values) {
			addCriterion("TOTAL_QUANTITY in", values, "totalQuantity");
			return (Criteria) this;
		}

		public Criteria andTotalQuantityNotIn(List values) {
			addCriterion("TOTAL_QUANTITY not in", values, "totalQuantity");
			return (Criteria) this;
		}

		public Criteria andTotalQuantityBetween(Integer value1, Integer value2) {
			addCriterion("TOTAL_QUANTITY between", value1, value2, "totalQuantity");
			return (Criteria) this;
		}

		public Criteria andTotalQuantityNotBetween(Integer value1, Integer value2) {
			addCriterion("TOTAL_QUANTITY not between", value1, value2, "totalQuantity");
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

		public Criteria andTotalTaxIsNull() {
			addCriterion("TOTAL_TAX is null");
			return (Criteria) this;
		}

		public Criteria andTotalTaxIsNotNull() {
			addCriterion("TOTAL_TAX is not null");
			return (Criteria) this;
		}

		public Criteria andTotalTaxEqualTo(BigDecimal value) {
			addCriterion("TOTAL_TAX =", value, "totalTax");
			return (Criteria) this;
		}

		public Criteria andTotalTaxNotEqualTo(BigDecimal value) {
			addCriterion("TOTAL_TAX <>", value, "totalTax");
			return (Criteria) this;
		}

		public Criteria andTotalTaxGreaterThan(BigDecimal value) {
			addCriterion("TOTAL_TAX >", value, "totalTax");
			return (Criteria) this;
		}

		public Criteria andTotalTaxGreaterThanOrEqualTo(BigDecimal value) {
			addCriterion("TOTAL_TAX >=", value, "totalTax");
			return (Criteria) this;
		}

		public Criteria andTotalTaxLessThan(BigDecimal value) {
			addCriterion("TOTAL_TAX <", value, "totalTax");
			return (Criteria) this;
		}

		public Criteria andTotalTaxLessThanOrEqualTo(BigDecimal value) {
			addCriterion("TOTAL_TAX <=", value, "totalTax");
			return (Criteria) this;
		}

		public Criteria andTotalTaxIn(List values) {
			addCriterion("TOTAL_TAX in", values, "totalTax");
			return (Criteria) this;
		}

		public Criteria andTotalTaxNotIn(List values) {
			addCriterion("TOTAL_TAX not in", values, "totalTax");
			return (Criteria) this;
		}

		public Criteria andTotalTaxBetween(BigDecimal value1, BigDecimal value2) {
			addCriterion("TOTAL_TAX between", value1, value2, "totalTax");
			return (Criteria) this;
		}

		public Criteria andTotalTaxNotBetween(BigDecimal value1, BigDecimal value2) {
			addCriterion("TOTAL_TAX not between", value1, value2, "totalTax");
			return (Criteria) this;
		}

		public Criteria andTotalSumIsNull() {
			addCriterion("TOTAL_SUM is null");
			return (Criteria) this;
		}

		public Criteria andTotalSumIsNotNull() {
			addCriterion("TOTAL_SUM is not null");
			return (Criteria) this;
		}

		public Criteria andTotalSumEqualTo(BigDecimal value) {
			addCriterion("TOTAL_SUM =", value, "totalSum");
			return (Criteria) this;
		}

		public Criteria andTotalSumNotEqualTo(BigDecimal value) {
			addCriterion("TOTAL_SUM <>", value, "totalSum");
			return (Criteria) this;
		}

		public Criteria andTotalSumGreaterThan(BigDecimal value) {
			addCriterion("TOTAL_SUM >", value, "totalSum");
			return (Criteria) this;
		}

		public Criteria andTotalSumGreaterThanOrEqualTo(BigDecimal value) {
			addCriterion("TOTAL_SUM >=", value, "totalSum");
			return (Criteria) this;
		}

		public Criteria andTotalSumLessThan(BigDecimal value) {
			addCriterion("TOTAL_SUM <", value, "totalSum");
			return (Criteria) this;
		}

		public Criteria andTotalSumLessThanOrEqualTo(BigDecimal value) {
			addCriterion("TOTAL_SUM <=", value, "totalSum");
			return (Criteria) this;
		}

		public Criteria andTotalSumIn(List values) {
			addCriterion("TOTAL_SUM in", values, "totalSum");
			return (Criteria) this;
		}

		public Criteria andTotalSumNotIn(List values) {
			addCriterion("TOTAL_SUM not in", values, "totalSum");
			return (Criteria) this;
		}

		public Criteria andTotalSumBetween(BigDecimal value1, BigDecimal value2) {
			addCriterion("TOTAL_SUM between", value1, value2, "totalSum");
			return (Criteria) this;
		}

		public Criteria andTotalSumNotBetween(BigDecimal value1, BigDecimal value2) {
			addCriterion("TOTAL_SUM not between", value1, value2, "totalSum");
			return (Criteria) this;
		}

		public Criteria andCreateUserIsNull() {
			addCriterion("CREATE_USER is null");
			return (Criteria) this;
		}

		public Criteria andCreateUserIsNotNull() {
			addCriterion("CREATE_USER is not null");
			return (Criteria) this;
		}

		public Criteria andCreateUserEqualTo(Integer value) {
			addCriterion("CREATE_USER =", value, "createUser");
			return (Criteria) this;
		}

		public Criteria andCreateUserNotEqualTo(Integer value) {
			addCriterion("CREATE_USER <>", value, "createUser");
			return (Criteria) this;
		}

		public Criteria andCreateUserGreaterThan(Integer value) {
			addCriterion("CREATE_USER >", value, "createUser");
			return (Criteria) this;
		}

		public Criteria andCreateUserGreaterThanOrEqualTo(Integer value) {
			addCriterion("CREATE_USER >=", value, "createUser");
			return (Criteria) this;
		}

		public Criteria andCreateUserLessThan(Integer value) {
			addCriterion("CREATE_USER <", value, "createUser");
			return (Criteria) this;
		}

		public Criteria andCreateUserLessThanOrEqualTo(Integer value) {
			addCriterion("CREATE_USER <=", value, "createUser");
			return (Criteria) this;
		}

		public Criteria andCreateUserIn(List values) {
			addCriterion("CREATE_USER in", values, "createUser");
			return (Criteria) this;
		}

		public Criteria andCreateUserNotIn(List values) {
			addCriterion("CREATE_USER not in", values, "createUser");
			return (Criteria) this;
		}

		public Criteria andCreateUserBetween(Integer value1, Integer value2) {
			addCriterion("CREATE_USER between", value1, value2, "createUser");
			return (Criteria) this;
		}

		public Criteria andCreateUserNotBetween(Integer value1, Integer value2) {
			addCriterion("CREATE_USER not between", value1, value2, "createUser");
			return (Criteria) this;
		}

		public Criteria andCreateUserNameIsNull() {
			addCriterion("CREATE_USER_NAME is null");
			return (Criteria) this;
		}

		public Criteria andCreateUserNameIsNotNull() {
			addCriterion("CREATE_USER_NAME is not null");
			return (Criteria) this;
		}

		public Criteria andCreateUserNameEqualTo(String value) {
			addCriterion("CREATE_USER_NAME =", value, "createUserName");
			return (Criteria) this;
		}

		public Criteria andCreateUserNameNotEqualTo(String value) {
			addCriterion("CREATE_USER_NAME <>", value, "createUserName");
			return (Criteria) this;
		}

		public Criteria andCreateUserNameGreaterThan(String value) {
			addCriterion("CREATE_USER_NAME >", value, "createUserName");
			return (Criteria) this;
		}

		public Criteria andCreateUserNameGreaterThanOrEqualTo(String value) {
			addCriterion("CREATE_USER_NAME >=", value, "createUserName");
			return (Criteria) this;
		}

		public Criteria andCreateUserNameLessThan(String value) {
			addCriterion("CREATE_USER_NAME <", value, "createUserName");
			return (Criteria) this;
		}

		public Criteria andCreateUserNameLessThanOrEqualTo(String value) {
			addCriterion("CREATE_USER_NAME <=", value, "createUserName");
			return (Criteria) this;
		}

		public Criteria andCreateUserNameLike(String value) {
			addCriterion("CREATE_USER_NAME like", "%" + value + "%", "createUserName");
			return (Criteria) this;
		}

		public Criteria andCreateUserNameNotLike(String value) {
			addCriterion("CREATE_USER_NAME not like", "%" + value + "%", "createUserName");
			return (Criteria) this;
		}

		public Criteria andCreateUserNameIn(List values) {
			addCriterion("CREATE_USER_NAME in", values, "createUserName");
			return (Criteria) this;
		}

		public Criteria andCreateUserNameNotIn(List values) {
			addCriterion("CREATE_USER_NAME not in", values, "createUserName");
			return (Criteria) this;
		}

		public Criteria andCreateUserNameBetween(String value1, String value2) {
			addCriterion("CREATE_USER_NAME between", value1, value2, "createUserName");
			return (Criteria) this;
		}

		public Criteria andCreateUserNameNotBetween(String value1, String value2) {
			addCriterion("CREATE_USER_NAME not between", value1, value2, "createUserName");
			return (Criteria) this;
		}

		public Criteria andCreateDateIsNull() {
			addCriterion("CREATE_DATE is null");
			return (Criteria) this;
		}

		public Criteria andCreateDateIsNotNull() {
			addCriterion("CREATE_DATE is not null");
			return (Criteria) this;
		}

		public Criteria andCreateDateEqualTo(Date value) {
			addCriterionForJDBCDate("CREATE_DATE =", value, "createDate");
			return (Criteria) this;
		}

		public Criteria andCreateDateNotEqualTo(Date value) {
			addCriterionForJDBCDate("CREATE_DATE <>", value, "createDate");
			return (Criteria) this;
		}

		public Criteria andCreateDateGreaterThan(Date value) {
			addCriterionForJDBCDate("CREATE_DATE >", value, "createDate");
			return (Criteria) this;
		}

		public Criteria andCreateDateGreaterThanOrEqualTo(Date value) {
			addCriterionForJDBCDate("CREATE_DATE >=", value, "createDate");
			return (Criteria) this;
		}

		public Criteria andCreateDateLessThan(Date value) {
			addCriterionForJDBCDate("CREATE_DATE <", value, "createDate");
			return (Criteria) this;
		}

		public Criteria andCreateDateLessThanOrEqualTo(Date value) {
			addCriterionForJDBCDate("CREATE_DATE <=", value, "createDate");
			return (Criteria) this;
		}

		public Criteria andCreateDateIn(List values) {
			addCriterionForJDBCDate("CREATE_DATE in", values, "createDate");
			return (Criteria) this;
		}

		public Criteria andCreateDateNotIn(List values) {
			addCriterionForJDBCDate("CREATE_DATE not in", values, "createDate");
			return (Criteria) this;
		}

		public Criteria andCreateDateBetween(Date value1, Date value2) {
			addCriterionForJDBCDate("CREATE_DATE between", value1, value2, "createDate");
			return (Criteria) this;
		}

		public Criteria andCreateDateNotBetween(Date value1, Date value2) {
			addCriterionForJDBCDate("CREATE_DATE not between", value1, value2, "createDate");
			return (Criteria) this;
		}

		public Criteria andAuditUserIsNull() {
			addCriterion("AUDIT_USER is null");
			return (Criteria) this;
		}

		public Criteria andAuditUserIsNotNull() {
			addCriterion("AUDIT_USER is not null");
			return (Criteria) this;
		}

		public Criteria andAuditUserEqualTo(Integer value) {
			addCriterion("AUDIT_USER =", value, "auditUser");
			return (Criteria) this;
		}

		public Criteria andAuditUserNotEqualTo(Integer value) {
			addCriterion("AUDIT_USER <>", value, "auditUser");
			return (Criteria) this;
		}

		public Criteria andAuditUserGreaterThan(Integer value) {
			addCriterion("AUDIT_USER >", value, "auditUser");
			return (Criteria) this;
		}

		public Criteria andAuditUserGreaterThanOrEqualTo(Integer value) {
			addCriterion("AUDIT_USER >=", value, "auditUser");
			return (Criteria) this;
		}

		public Criteria andAuditUserLessThan(Integer value) {
			addCriterion("AUDIT_USER <", value, "auditUser");
			return (Criteria) this;
		}

		public Criteria andAuditUserLessThanOrEqualTo(Integer value) {
			addCriterion("AUDIT_USER <=", value, "auditUser");
			return (Criteria) this;
		}

		public Criteria andAuditUserIn(List values) {
			addCriterion("AUDIT_USER in", values, "auditUser");
			return (Criteria) this;
		}

		public Criteria andAuditUserNotIn(List values) {
			addCriterion("AUDIT_USER not in", values, "auditUser");
			return (Criteria) this;
		}

		public Criteria andAuditUserBetween(Integer value1, Integer value2) {
			addCriterion("AUDIT_USER between", value1, value2, "auditUser");
			return (Criteria) this;
		}

		public Criteria andAuditUserNotBetween(Integer value1, Integer value2) {
			addCriterion("AUDIT_USER not between", value1, value2, "auditUser");
			return (Criteria) this;
		}

		public Criteria andCheckUserNameIsNull() {
			addCriterion("CHECK_USER_NAME is null");
			return (Criteria) this;
		}

		public Criteria andCheckUserNameIsNotNull() {
			addCriterion("CHECK_USER_NAME is not null");
			return (Criteria) this;
		}

		public Criteria andCheckUserNameEqualTo(String value) {
			addCriterion("CHECK_USER_NAME =", value, "checkUserName");
			return (Criteria) this;
		}

		public Criteria andCheckUserNameNotEqualTo(String value) {
			addCriterion("CHECK_USER_NAME <>", value, "checkUserName");
			return (Criteria) this;
		}

		public Criteria andCheckUserNameGreaterThan(String value) {
			addCriterion("CHECK_USER_NAME >", value, "checkUserName");
			return (Criteria) this;
		}

		public Criteria andCheckUserNameGreaterThanOrEqualTo(String value) {
			addCriterion("CHECK_USER_NAME >=", value, "checkUserName");
			return (Criteria) this;
		}

		public Criteria andCheckUserNameLessThan(String value) {
			addCriterion("CHECK_USER_NAME <", value, "checkUserName");
			return (Criteria) this;
		}

		public Criteria andCheckUserNameLessThanOrEqualTo(String value) {
			addCriterion("CHECK_USER_NAME <=", value, "checkUserName");
			return (Criteria) this;
		}

		public Criteria andCheckUserNameLike(String value) {
			addCriterion("CHECK_USER_NAME like", "%" + value + "%", "checkUserName");
			return (Criteria) this;
		}

		public Criteria andCheckUserNameNotLike(String value) {
			addCriterion("CHECK_USER_NAME not like", "%" + value + "%", "checkUserName");
			return (Criteria) this;
		}

		public Criteria andCheckUserNameIn(List values) {
			addCriterion("CHECK_USER_NAME in", values, "checkUserName");
			return (Criteria) this;
		}

		public Criteria andCheckUserNameNotIn(List values) {
			addCriterion("CHECK_USER_NAME not in", values, "checkUserName");
			return (Criteria) this;
		}

		public Criteria andCheckUserNameBetween(String value1, String value2) {
			addCriterion("CHECK_USER_NAME between", value1, value2, "checkUserName");
			return (Criteria) this;
		}

		public Criteria andCheckUserNameNotBetween(String value1, String value2) {
			addCriterion("CHECK_USER_NAME not between", value1, value2, "checkUserName");
			return (Criteria) this;
		}

		public Criteria andAuditDateIsNull() {
			addCriterion("AUDIT_DATE is null");
			return (Criteria) this;
		}

		public Criteria andAuditDateIsNotNull() {
			addCriterion("AUDIT_DATE is not null");
			return (Criteria) this;
		}

		public Criteria andAuditDateEqualTo(Date value) {
			addCriterionForJDBCDate("AUDIT_DATE =", value, "auditDate");
			return (Criteria) this;
		}

		public Criteria andAuditDateNotEqualTo(Date value) {
			addCriterionForJDBCDate("AUDIT_DATE <>", value, "auditDate");
			return (Criteria) this;
		}

		public Criteria andAuditDateGreaterThan(Date value) {
			addCriterionForJDBCDate("AUDIT_DATE >", value, "auditDate");
			return (Criteria) this;
		}

		public Criteria andAuditDateGreaterThanOrEqualTo(Date value) {
			addCriterionForJDBCDate("AUDIT_DATE >=", value, "auditDate");
			return (Criteria) this;
		}

		public Criteria andAuditDateLessThan(Date value) {
			addCriterionForJDBCDate("AUDIT_DATE <", value, "auditDate");
			return (Criteria) this;
		}

		public Criteria andAuditDateLessThanOrEqualTo(Date value) {
			addCriterionForJDBCDate("AUDIT_DATE <=", value, "auditDate");
			return (Criteria) this;
		}

		public Criteria andAuditDateIn(List values) {
			addCriterionForJDBCDate("AUDIT_DATE in", values, "auditDate");
			return (Criteria) this;
		}

		public Criteria andAuditDateNotIn(List values) {
			addCriterionForJDBCDate("AUDIT_DATE not in", values, "auditDate");
			return (Criteria) this;
		}

		public Criteria andAuditDateBetween(Date value1, Date value2) {
			addCriterionForJDBCDate("AUDIT_DATE between", value1, value2, "auditDate");
			return (Criteria) this;
		}

		public Criteria andAuditDateNotBetween(Date value1, Date value2) {
			addCriterionForJDBCDate("AUDIT_DATE not between", value1, value2, "auditDate");
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

		public Criteria andDayEndStatusIsNull() {
			addCriterion("DAY_END_STATUS is null");
			return (Criteria) this;
		}

		public Criteria andDayEndStatusIsNotNull() {
			addCriterion("DAY_END_STATUS is not null");
			return (Criteria) this;
		}

		public Criteria andDayEndStatusEqualTo(Short value) {
			addCriterion("DAY_END_STATUS =", value, "dayEndStatus");
			return (Criteria) this;
		}

		public Criteria andDayEndStatusNotEqualTo(Short value) {
			addCriterion("DAY_END_STATUS <>", value, "dayEndStatus");
			return (Criteria) this;
		}

		public Criteria andDayEndStatusGreaterThan(Short value) {
			addCriterion("DAY_END_STATUS >", value, "dayEndStatus");
			return (Criteria) this;
		}

		public Criteria andDayEndStatusGreaterThanOrEqualTo(Short value) {
			addCriterion("DAY_END_STATUS >=", value, "dayEndStatus");
			return (Criteria) this;
		}

		public Criteria andDayEndStatusLessThan(Short value) {
			addCriterion("DAY_END_STATUS <", value, "dayEndStatus");
			return (Criteria) this;
		}

		public Criteria andDayEndStatusLessThanOrEqualTo(Short value) {
			addCriterion("DAY_END_STATUS <=", value, "dayEndStatus");
			return (Criteria) this;
		}

		public Criteria andDayEndStatusIn(List values) {
			addCriterion("DAY_END_STATUS in", values, "dayEndStatus");
			return (Criteria) this;
		}

		public Criteria andDayEndStatusNotIn(List values) {
			addCriterion("DAY_END_STATUS not in", values, "dayEndStatus");
			return (Criteria) this;
		}

		public Criteria andDayEndStatusBetween(Short value1, Short value2) {
			addCriterion("DAY_END_STATUS between", value1, value2, "dayEndStatus");
			return (Criteria) this;
		}

		public Criteria andDayEndStatusNotBetween(Short value1, Short value2) {
			addCriterion("DAY_END_STATUS not between", value1, value2, "dayEndStatus");
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
			addCriterion("REMARK like", "%" + value + "%", "remark");
			return (Criteria) this;
		}

		public Criteria andRemarkNotLike(String value) {
			addCriterion("REMARK not like", "%" + value + "%", "remark");
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

		public Criteria andBillNoIsNull() {
			addCriterion("BILL_NO is null");
			return (Criteria) this;
		}

		public Criteria andBillNoIsNotNull() {
			addCriterion("BILL_NO is not null");
			return (Criteria) this;
		}

		public Criteria andBillNoEqualTo(String value) {
			addCriterion("BILL_NO =", value, "billNo");
			return (Criteria) this;
		}

		public Criteria andBillNoNotEqualTo(String value) {
			addCriterion("BILL_NO <>", value, "billNo");
			return (Criteria) this;
		}

		public Criteria andBillNoGreaterThan(String value) {
			addCriterion("BILL_NO >", value, "billNo");
			return (Criteria) this;
		}

		public Criteria andBillNoGreaterThanOrEqualTo(String value) {
			addCriterion("BILL_NO >=", value, "billNo");
			return (Criteria) this;
		}

		public Criteria andBillNoLessThan(String value) {
			addCriterion("BILL_NO <", value, "billNo");
			return (Criteria) this;
		}

		public Criteria andBillNoLessThanOrEqualTo(String value) {
			addCriterion("BILL_NO <=", value, "billNo");
			return (Criteria) this;
		}

		public Criteria andBillNoLike(String value) {
			addCriterion("BILL_NO like", "%" + value + "%", "billNo");
			return (Criteria) this;
		}

		public Criteria andBillNoNotLike(String value) {
			addCriterion("BILL_NO not like", "%" + value + "%", "billNo");
			return (Criteria) this;
		}

		public Criteria andBillNoIn(List values) {
			addCriterion("BILL_NO in", values, "billNo");
			return (Criteria) this;
		}

		public Criteria andBillNoNotIn(List values) {
			addCriterion("BILL_NO not in", values, "billNo");
			return (Criteria) this;
		}

		public Criteria andBillNoBetween(String value1, String value2) {
			addCriterion("BILL_NO between", value1, value2, "billNo");
			return (Criteria) this;
		}

		public Criteria andBillNoNotBetween(String value1, String value2) {
			addCriterion("BILL_NO not between", value1, value2, "billNo");
			return (Criteria) this;
		}

		public Criteria andModifiyDateIsNull() {
			addCriterion("MODIFIY_DATE is null");
			return (Criteria) this;
		}

		public Criteria andModifiyDateIsNotNull() {
			addCriterion("MODIFIY_DATE is not null");
			return (Criteria) this;
		}

		public Criteria andModifiyDateEqualTo(Date value) {
			addCriterionForJDBCDate("MODIFIY_DATE =", value, "modifiyDate");
			return (Criteria) this;
		}

		public Criteria andModifiyDateNotEqualTo(Date value) {
			addCriterionForJDBCDate("MODIFIY_DATE <>", value, "modifiyDate");
			return (Criteria) this;
		}

		public Criteria andModifiyDateGreaterThan(Date value) {
			addCriterionForJDBCDate("MODIFIY_DATE >", value, "modifiyDate");
			return (Criteria) this;
		}

		public Criteria andModifiyDateGreaterThanOrEqualTo(Date value) {
			addCriterionForJDBCDate("MODIFIY_DATE >=", value, "modifiyDate");
			return (Criteria) this;
		}

		public Criteria andModifiyDateLessThan(Date value) {
			addCriterionForJDBCDate("MODIFIY_DATE <", value, "modifiyDate");
			return (Criteria) this;
		}

		public Criteria andModifiyDateLessThanOrEqualTo(Date value) {
			addCriterionForJDBCDate("MODIFIY_DATE <=", value, "modifiyDate");
			return (Criteria) this;
		}

		public Criteria andModifiyDateIn(List values) {
			addCriterionForJDBCDate("MODIFIY_DATE in", values, "modifiyDate");
			return (Criteria) this;
		}

		public Criteria andModifiyDateNotIn(List values) {
			addCriterionForJDBCDate("MODIFIY_DATE not in", values, "modifiyDate");
			return (Criteria) this;
		}

		public Criteria andModifiyDateBetween(Date value1, Date value2) {
			addCriterionForJDBCDate("MODIFIY_DATE between", value1, value2, "modifiyDate");
			return (Criteria) this;
		}

		public Criteria andModifiyDateNotBetween(Date value1, Date value2) {
			addCriterionForJDBCDate("MODIFIY_DATE not between", value1, value2, "modifiyDate");
			return (Criteria) this;
		}

		public Criteria andModifierIdIsNull() {
			addCriterion("MODIFIER_ID is null");
			return (Criteria) this;
		}

		public Criteria andModifierIdIsNotNull() {
			addCriterion("MODIFIER_ID is not null");
			return (Criteria) this;
		}

		public Criteria andModifierIdEqualTo(BigDecimal value) {
			addCriterion("MODIFIER_ID =", value, "modifierId");
			return (Criteria) this;
		}

		public Criteria andModifierIdNotEqualTo(BigDecimal value) {
			addCriterion("MODIFIER_ID <>", value, "modifierId");
			return (Criteria) this;
		}

		public Criteria andModifierIdGreaterThan(BigDecimal value) {
			addCriterion("MODIFIER_ID >", value, "modifierId");
			return (Criteria) this;
		}

		public Criteria andModifierIdGreaterThanOrEqualTo(BigDecimal value) {
			addCriterion("MODIFIER_ID >=", value, "modifierId");
			return (Criteria) this;
		}

		public Criteria andModifierIdLessThan(BigDecimal value) {
			addCriterion("MODIFIER_ID <", value, "modifierId");
			return (Criteria) this;
		}

		public Criteria andModifierIdLessThanOrEqualTo(BigDecimal value) {
			addCriterion("MODIFIER_ID <=", value, "modifierId");
			return (Criteria) this;
		}

		public Criteria andModifierIdIn(List values) {
			addCriterion("MODIFIER_ID in", values, "modifierId");
			return (Criteria) this;
		}

		public Criteria andModifierIdNotIn(List values) {
			addCriterion("MODIFIER_ID not in", values, "modifierId");
			return (Criteria) this;
		}

		public Criteria andModifierIdBetween(BigDecimal value1, BigDecimal value2) {
			addCriterion("MODIFIER_ID between", value1, value2, "modifierId");
			return (Criteria) this;
		}

		public Criteria andModifierIdNotBetween(BigDecimal value1, BigDecimal value2) {
			addCriterion("MODIFIER_ID not between", value1, value2, "modifierId");
			return (Criteria) this;
		}

		public Criteria andModifierNameIsNull() {
			addCriterion("MODIFIER_NAME is null");
			return (Criteria) this;
		}

		public Criteria andModifierNameIsNotNull() {
			addCriterion("MODIFIER_NAME is not null");
			return (Criteria) this;
		}

		public Criteria andModifierNameEqualTo(String value) {
			addCriterion("MODIFIER_NAME =", value, "modifierName");
			return (Criteria) this;
		}

		public Criteria andModifierNameNotEqualTo(String value) {
			addCriterion("MODIFIER_NAME <>", value, "modifierName");
			return (Criteria) this;
		}

		public Criteria andModifierNameGreaterThan(String value) {
			addCriterion("MODIFIER_NAME >", value, "modifierName");
			return (Criteria) this;
		}

		public Criteria andModifierNameGreaterThanOrEqualTo(String value) {
			addCriterion("MODIFIER_NAME >=", value, "modifierName");
			return (Criteria) this;
		}

		public Criteria andModifierNameLessThan(String value) {
			addCriterion("MODIFIER_NAME <", value, "modifierName");
			return (Criteria) this;
		}

		public Criteria andModifierNameLessThanOrEqualTo(String value) {
			addCriterion("MODIFIER_NAME <=", value, "modifierName");
			return (Criteria) this;
		}

		public Criteria andModifierNameLike(String value) {
			addCriterion("MODIFIER_NAME like", "%" + value + "%", "modifierName");
			return (Criteria) this;
		}

		public Criteria andModifierNameNotLike(String value) {
			addCriterion("MODIFIER_NAME not like", "%" + value + "%", "modifierName");
			return (Criteria) this;
		}

		public Criteria andModifierNameIn(List values) {
			addCriterion("MODIFIER_NAME in", values, "modifierName");
			return (Criteria) this;
		}

		public Criteria andModifierNameNotIn(List values) {
			addCriterion("MODIFIER_NAME not in", values, "modifierName");
			return (Criteria) this;
		}

		public Criteria andModifierNameBetween(String value1, String value2) {
			addCriterion("MODIFIER_NAME between", value1, value2, "modifierName");
			return (Criteria) this;
		}

		public Criteria andModifierNameNotBetween(String value1, String value2) {
			addCriterion("MODIFIER_NAME not between", value1, value2, "modifierName");
			return (Criteria) this;
		}
	}

	/**
	 * STOCK_OUT
	 */
	public static class Criteria extends GeneratedCriteria {

		protected Criteria() {
			super();
		}
	}
}