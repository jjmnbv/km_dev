package com.kmzyc.promotion.app.vobject;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 配送明细单查询条件组合
 * 
 * @author luoyi
 * @since 2013/08/20
 */
@SuppressWarnings("unchecked")
public class DistributionDetailInfoExample implements Serializable {
	private static final long serialVersionUID = 450628247380317830L;

	protected String orderByClause;

	protected boolean distinct;

	protected List oredCriteria;

	public DistributionDetailInfoExample() {
		oredCriteria = new ArrayList();
	}

	protected DistributionDetailInfoExample(DistributionDetailInfoExample example) {
		this.orderByClause = example.orderByClause;
		this.oredCriteria = example.oredCriteria;
		this.distinct = example.distinct;
	}

	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	public String getOrderByClause() {
		return orderByClause;
	}

	public void setDistinct(boolean distinct) {
		this.distinct = distinct;
	}

	public boolean isDistinct() {
		return distinct;
	}

	public List getOredCriteria() {
		return oredCriteria;
	}

	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	public Criteria or() {
		Criteria criteria = createCriteriaInternal();
		oredCriteria.add(criteria);
		return criteria;
	}

	public Criteria createCriteria() {
		Criteria criteria = createCriteriaInternal();
		if (oredCriteria.size() == 0) {
			oredCriteria.add(criteria);
		}
		return criteria;
	}

	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	public void clear() {
		oredCriteria.clear();
		orderByClause = null;
		distinct = false;
	}

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

		public Criteria andDistributionIdIsNull() {
			addCriterion("DISTRIBUTION_ID is null");
			return (Criteria) this;
		}

		public Criteria andDistributionIdIsNotNull() {
			addCriterion("DISTRIBUTION_ID is not null");
			return (Criteria) this;
		}

		public Criteria andDistributionIdEqualTo(Long value) {
			addCriterion("DISTRIBUTION_ID =", value, "distributionId");
			return (Criteria) this;
		}

		public Criteria andDistributionIdNotEqualTo(Long value) {
			addCriterion("DISTRIBUTION_ID <>", value, "distributionId");
			return (Criteria) this;
		}

		public Criteria andDistributionIdGreaterThan(Long value) {
			addCriterion("DISTRIBUTION_ID >", value, "distributionId");
			return (Criteria) this;
		}

		public Criteria andDistributionIdGreaterThanOrEqualTo(Long value) {
			addCriterion("DISTRIBUTION_ID >=", value, "distributionId");
			return (Criteria) this;
		}

		public Criteria andDistributionIdLessThan(Long value) {
			addCriterion("DISTRIBUTION_ID <", value, "distributionId");
			return (Criteria) this;
		}

		public Criteria andDistributionIdLessThanOrEqualTo(Long value) {
			addCriterion("DISTRIBUTION_ID <=", value, "distributionId");
			return (Criteria) this;
		}

		public Criteria andDistributionIdIn(List values) {
			addCriterion("DISTRIBUTION_ID in", values, "distributionId");
			return (Criteria) this;
		}

		public Criteria andDistributionIdNotIn(List values) {
			addCriterion("DISTRIBUTION_ID not in", values, "distributionId");
			return (Criteria) this;
		}

		public Criteria andDistributionIdBetween(Long value1, Long value2) {
			addCriterion("DISTRIBUTION_ID between", value1, value2, "distributionId");
			return (Criteria) this;
		}

		public Criteria andDistributionIdNotBetween(Long value1, Long value2) {
			addCriterion("DISTRIBUTION_ID not between", value1, value2, "distributionId");
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
			addCriterion("PRODUCT_NAME like", "%" + value + "%", "productName");
			return (Criteria) this;
		}

		public Criteria andProductNameNotLike(String value) {
			addCriterion("PRODUCT_NAME not like", "%" + value + "%", "productName");
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
			addCriterion("PRODUCT_NO like", "%" + value + "%", "productNo");
			return (Criteria) this;
		}

		public Criteria andProductNoNotLike(String value) {
			addCriterion("PRODUCT_NO not like", "%" + value + "%", "productNo");
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
			addCriterion("PRODUCT_SKU_VALUE like", "%" + value + "%", "productSkuValue");
			return (Criteria) this;
		}

		public Criteria andProductSkuValueNotLike(String value) {
			addCriterion("PRODUCT_SKU_VALUE not like", "%" + value + "%", "productSkuValue");
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

		public Criteria andPriceGreaterThanOrEqualTo(Integer value) {
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
	}

	public static class Criteria extends GeneratedCriteria {

		protected Criteria() {
			super();
		}
	}
}