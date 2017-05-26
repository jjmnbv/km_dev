package com.kmzyc.promotion.app.vobject;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 配送单查询条件
 * 
 * @author luoyi
 * 
 */

@SuppressWarnings("unchecked")
public class DistributionInfoExample implements Serializable {

	private static final long serialVersionUID = -6615565729684045813L;

	protected String orderByClause;

	protected boolean distinct;

	protected List oredCriteria;

	public DistributionInfoExample() {
		oredCriteria = new ArrayList();
	}

	protected DistributionInfoExample(DistributionInfoExample example) {
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

		public Criteria andDistributionNoIsNull() {
			addCriterion("DISTRIBUTION_NO is null");
			return (Criteria) this;
		}

		public Criteria andDistributionNoIsNotNull() {
			addCriterion("DISTRIBUTION_NO is not null");
			return (Criteria) this;
		}

		public Criteria andDistributionNoEqualTo(String value) {
			addCriterion("DISTRIBUTION_NO =", value, "distributionNo");
			return (Criteria) this;
		}

		public Criteria andDistributionNoNotEqualTo(String value) {
			addCriterion("DISTRIBUTION_NO <>", value, "distributionNo");
			return (Criteria) this;
		}

		public Criteria andDistributionNoGreaterThan(String value) {
			addCriterion("DISTRIBUTION_NO >", value, "distributionNo");
			return (Criteria) this;
		}

		public Criteria andDistributionNoGreaterThanOrEqualTo(String value) {
			addCriterion("DISTRIBUTION_NO >=", value, "distributionNo");
			return (Criteria) this;
		}

		public Criteria andDistributionNoLessThan(String value) {
			addCriterion("DISTRIBUTION_NO <", value, "distributionNo");
			return (Criteria) this;
		}

		public Criteria andDistributionNoLessThanOrEqualTo(String value) {
			addCriterion("DISTRIBUTION_NO <=", value, "distributionNo");
			return (Criteria) this;
		}

		public Criteria andDistributionNoLike(String value) {
			addCriterion("DISTRIBUTION_NO like", "%" + value + "%", "distributionNo");
			return (Criteria) this;
		}

		public Criteria andDistributionNoNotLike(String value) {
			addCriterion("DISTRIBUTION_NO not like", "%" + value + "%", "distributionNo");
			return (Criteria) this;
		}

		public Criteria andDistributionNoIn(List values) {
			addCriterion("DISTRIBUTION_NO in", values, "distributionNo");
			return (Criteria) this;
		}

		public Criteria andDistributionNoNotIn(List values) {
			addCriterion("DISTRIBUTION_NO not in", values, "distributionNo");
			return (Criteria) this;
		}

		public Criteria andDistributionNoBetween(String value1, String value2) {
			addCriterion("DISTRIBUTION_NO between", value1, value2, "distributionNo");
			return (Criteria) this;
		}

		public Criteria andDistributionNoNotBetween(String value1, String value2) {
			addCriterion("DISTRIBUTION_NO not between", value1, value2, "distributionNo");
			return (Criteria) this;
		}

		public Criteria andLogisticsCompanyIsNull() {
			addCriterion("LOGISTICS_COMPANY is null");
			return (Criteria) this;
		}

		public Criteria andLogisticsCompanyIsNotNull() {
			addCriterion("LOGISTICS_COMPANY is not null");
			return (Criteria) this;
		}

		public Criteria andLogisticsCompanyEqualTo(String value) {
			addCriterion("LOGISTICS_COMPANY =", value, "logisticsCompany");
			return (Criteria) this;
		}

		public Criteria andLogisticsCompanyNotEqualTo(String value) {
			addCriterion("LOGISTICS_COMPANY <>", value, "logisticsCompany");
			return (Criteria) this;
		}

		public Criteria andLogisticsCompanyGreaterThan(String value) {
			addCriterion("LOGISTICS_COMPANY >", value, "logisticsCompany");
			return (Criteria) this;
		}

		public Criteria andLogisticsCompanyGreaterThanOrEqualTo(String value) {
			addCriterion("LOGISTICS_COMPANY >=", value, "logisticsCompany");
			return (Criteria) this;
		}

		public Criteria andLogisticsCompanyLessThan(String value) {
			addCriterion("LOGISTICS_COMPANY <", value, "logisticsCompany");
			return (Criteria) this;
		}

		public Criteria andLogisticsCompanyLessThanOrEqualTo(String value) {
			addCriterion("LOGISTICS_COMPANY <=", value, "logisticsCompany");
			return (Criteria) this;
		}

		public Criteria andLogisticsCompanyLike(String value) {
			addCriterion("LOGISTICS_COMPANY like", "%" + value + "%", "logisticsCompany");
			return (Criteria) this;
		}

		public Criteria andLogisticsCompanyNotLike(String value) {
			addCriterion("LOGISTICS_COMPANY not like", "%" + value + "%", "logisticsCompany");
			return (Criteria) this;
		}

		public Criteria andLogisticsCompanyIn(List values) {
			addCriterion("LOGISTICS_COMPANY in", values, "logisticsCompany");
			return (Criteria) this;
		}

		public Criteria andLogisticsCompanyNotIn(List values) {
			addCriterion("LOGISTICS_COMPANY not in", values, "logisticsCompany");
			return (Criteria) this;
		}

		public Criteria andLogisticsCompanyBetween(String value1, String value2) {
			addCriterion("LOGISTICS_COMPANY between", value1, value2, "logisticsCompany");
			return (Criteria) this;
		}

		public Criteria andLogisticsCompanyNotBetween(String value1, String value2) {
			addCriterion("LOGISTICS_COMPANY not between", value1, value2, "logisticsCompany");
			return (Criteria) this;
		}

		public Criteria andLogisticsNoIsNull() {
			addCriterion("LOGISTICS_NO is null");
			return (Criteria) this;
		}

		public Criteria andLogisticsNoIsNotNull() {
			addCriterion("LOGISTICS_NO is not null");
			return (Criteria) this;
		}

		public Criteria andLogisticsNoEqualTo(String value) {
			addCriterion("LOGISTICS_NO =", value, "logisticsNo");
			return (Criteria) this;
		}

		public Criteria andLogisticsNoNotEqualTo(String value) {
			addCriterion("LOGISTICS_NO <>", value, "logisticsNo");
			return (Criteria) this;
		}

		public Criteria andLogisticsNoGreaterThan(String value) {
			addCriterion("LOGISTICS_NO >", value, "logisticsNo");
			return (Criteria) this;
		}

		public Criteria andLogisticsNoGreaterThanOrEqualTo(String value) {
			addCriterion("LOGISTICS_NO >=", value, "logisticsNo");
			return (Criteria) this;
		}

		public Criteria andLogisticsNoLessThan(String value) {
			addCriterion("LOGISTICS_NO <", value, "logisticsNo");
			return (Criteria) this;
		}

		public Criteria andLogisticsNoLessThanOrEqualTo(String value) {
			addCriterion("LOGISTICS_NO <=", value, "logisticsNo");
			return (Criteria) this;
		}

		public Criteria andLogisticsNoLike(String value) {
			addCriterion("LOGISTICS_NO like", "%" + value + "%", "logisticsNo");
			return (Criteria) this;
		}

		public Criteria andLogisticsNoNotLike(String value) {
			addCriterion("LOGISTICS_NO not like", "%" + value + "%", "logisticsNo");
			return (Criteria) this;
		}

		public Criteria andLogisticsNoIn(List values) {
			addCriterion("LOGISTICS_NO in", values, "logisticsNo");
			return (Criteria) this;
		}

		public Criteria andLogisticsNoNotIn(List values) {
			addCriterion("LOGISTICS_NO not in", values, "logisticsNo");
			return (Criteria) this;
		}

		public Criteria andLogisticsNoBetween(String value1, String value2) {
			addCriterion("LOGISTICS_NO between", value1, value2, "logisticsNo");
			return (Criteria) this;
		}

		public Criteria andLogisticsNoNotBetween(String value1, String value2) {
			addCriterion("LOGISTICS_NO not between", value1, value2, "logisticsNo");
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

		public Criteria andWarehouseIdGreaterThan(Integer value) {
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

		public Criteria andWarehouseNameIsNull() {
			addCriterion("WAREHOUSE_NAME is null");
			return (Criteria) this;
		}

		public Criteria andWarehouseNameIsNotNull() {
			addCriterion("WAREHOUSE_NAME is not null");
			return (Criteria) this;
		}

		public Criteria andWarehouseNameEqualTo(String value) {
			addCriterion("WAREHOUSE_NAME =", value, "warehouseName");
			return (Criteria) this;
		}

		public Criteria andWarehouseNameNotEqualTo(String value) {
			addCriterion("WAREHOUSE_NAME <>", value, "warehouseName");
			return (Criteria) this;
		}

		public Criteria andWarehouseNameGreaterThan(String value) {
			addCriterion("WAREHOUSE_NAME >", value, "warehouseName");
			return (Criteria) this;
		}

		public Criteria andWarehouseNameGreaterThanOrEqualTo(String value) {
			addCriterion("WAREHOUSE_NAME >=", value, "warehouseName");
			return (Criteria) this;
		}

		public Criteria andWarehouseNameLessThan(String value) {
			addCriterion("WAREHOUSE_NAME <", value, "warehouseName");
			return (Criteria) this;
		}

		public Criteria andWarehouseNameLessThanOrEqualTo(String value) {
			addCriterion("WAREHOUSE_NAME <=", value, "warehouseName");
			return (Criteria) this;
		}

		public Criteria andWarehouseNameLike(String value) {
			addCriterion("WAREHOUSE_NAME like", "%" + value + "%", "warehouseName");
			return (Criteria) this;
		}

		public Criteria andWarehouseNameNotLike(String value) {
			addCriterion("WAREHOUSE_NAME not like", "%" + value + "%", "warehouseName");
			return (Criteria) this;
		}

		public Criteria andWarehouseNameIn(List values) {
			addCriterion("WAREHOUSE_NAME in", values, "warehouseName");
			return (Criteria) this;
		}

		public Criteria andWarehouseNameNotIn(List values) {
			addCriterion("WAREHOUSE_NAME not in", values, "warehouseName");
			return (Criteria) this;
		}

		public Criteria andWarehouseNameBetween(String value1, String value2) {
			addCriterion("WAREHOUSE_NAME between", value1, value2, "warehouseName");
			return (Criteria) this;
		}

		public Criteria andWarehouseNameNotBetween(String value1, String value2) {
			addCriterion("WAREHOUSE_NAME not between", value1, value2, "warehouseName");
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

		public Criteria andDeliveryAddressIsNull() {
			addCriterion("DELIVERY_ADDRESS is null");
			return (Criteria) this;
		}

		public Criteria andDeliveryAddressIsNotNull() {
			addCriterion("DELIVERY_ADDRESS is not null");
			return (Criteria) this;
		}

		public Criteria andDeliveryAddressEqualTo(String value) {
			addCriterion("DELIVERY_ADDRESS =", value, "deliveryAddress");
			return (Criteria) this;
		}

		public Criteria andDeliveryAddressNotEqualTo(String value) {
			addCriterion("DELIVERY_ADDRESS <>", value, "deliveryAddress");
			return (Criteria) this;
		}

		public Criteria andDeliveryAddressGreaterThan(String value) {
			addCriterion("DELIVERY_ADDRESS >", value, "deliveryAddress");
			return (Criteria) this;
		}

		public Criteria andDeliveryAddressGreaterThanOrEqualTo(String value) {
			addCriterion("DELIVERY_ADDRESS >=", value, "deliveryAddress");
			return (Criteria) this;
		}

		public Criteria andDeliveryAddressLessThan(String value) {
			addCriterion("DELIVERY_ADDRESS <", value, "deliveryAddress");
			return (Criteria) this;
		}

		public Criteria andDeliveryAddressLessThanOrEqualTo(String value) {
			addCriterion("DELIVERY_ADDRESS <=", value, "deliveryAddress");
			return (Criteria) this;
		}

		public Criteria andDeliveryAddressLike(String value) {
			addCriterion("DELIVERY_ADDRESS like", "%" + value + "%", "deliveryAddress");
			return (Criteria) this;
		}

		public Criteria andDeliveryAddressNotLike(String value) {
			addCriterion("DELIVERY_ADDRESS not like", "%" + value + "%", "deliveryAddress");
			return (Criteria) this;
		}

		public Criteria andDeliveryAddressIn(List values) {
			addCriterion("DELIVERY_ADDRESS in", values, "deliveryAddress");
			return (Criteria) this;
		}

		public Criteria andDeliveryAddressNotIn(List values) {
			addCriterion("DELIVERY_ADDRESS not in", values, "deliveryAddress");
			return (Criteria) this;
		}

		public Criteria andDeliveryAddressBetween(String value1, String value2) {
			addCriterion("DELIVERY_ADDRESS between", value1, value2, "deliveryAddress");
			return (Criteria) this;
		}

		public Criteria andDeliveryAddressNotBetween(String value1, String value2) {
			addCriterion("DELIVERY_ADDRESS not between", value1, value2, "deliveryAddress");
			return (Criteria) this;
		}

		public Criteria andTelIsNull() {
			addCriterion("TEL is null");
			return (Criteria) this;
		}

		public Criteria andTelIsNotNull() {
			addCriterion("TEL is not null");
			return (Criteria) this;
		}

		public Criteria andTelEqualTo(String value) {
			addCriterion("TEL =", value, "tel");
			return (Criteria) this;
		}

		public Criteria andTelNotEqualTo(String value) {
			addCriterion("TEL <>", value, "tel");
			return (Criteria) this;
		}

		public Criteria andTelGreaterThan(String value) {
			addCriterion("TEL >", value, "tel");
			return (Criteria) this;
		}

		public Criteria andTelGreaterThanOrEqualTo(String value) {
			addCriterion("TEL >=", value, "tel");
			return (Criteria) this;
		}

		public Criteria andTelLessThan(String value) {
			addCriterion("TEL <", value, "tel");
			return (Criteria) this;
		}

		public Criteria andTelLessThanOrEqualTo(String value) {
			addCriterion("TEL <=", value, "tel");
			return (Criteria) this;
		}

		public Criteria andTelLike(String value) {
			addCriterion("TEL like", "%" + value + "%", "tel");
			return (Criteria) this;
		}

		public Criteria andTelNotLike(String value) {
			addCriterion("TEL not like", "%" + value + "%", "tel");
			return (Criteria) this;
		}

		public Criteria andTelIn(List values) {
			addCriterion("TEL in", values, "tel");
			return (Criteria) this;
		}

		public Criteria andTelNotIn(List values) {
			addCriterion("TEL not in", values, "tel");
			return (Criteria) this;
		}

		public Criteria andTelBetween(String value1, String value2) {
			addCriterion("TEL between", value1, value2, "tel");
			return (Criteria) this;
		}

		public Criteria andTelNotBetween(String value1, String value2) {
			addCriterion("TEL not between", value1, value2, "tel");
			return (Criteria) this;
		}

		public Criteria andLogisticsDateIsNull() {
			addCriterion("LOGISTICS_DATE is null");
			return (Criteria) this;
		}

		public Criteria andLogisticsDateIsNotNull() {
			addCriterion("LOGISTICS_DATE is not null");
			return (Criteria) this;
		}

		public Criteria andLogisticsDateEqualTo(Date value) {
			addCriterionForJDBCDate("LOGISTICS_DATE =", value, "logisticsDate");
			return (Criteria) this;
		}

		public Criteria andLogisticsDateNotEqualTo(Date value) {
			addCriterionForJDBCDate("LOGISTICS_DATE <>", value, "logisticsDate");
			return (Criteria) this;
		}

		public Criteria andLogisticsDateGreaterThan(Date value) {
			addCriterionForJDBCDate("LOGISTICS_DATE >", value, "logisticsDate");
			return (Criteria) this;
		}

		public Criteria andLogisticsDateGreaterThanOrEqualTo(Date value) {
			addCriterionForJDBCDate("LOGISTICS_DATE >=", value, "logisticsDate");
			return (Criteria) this;
		}

		public Criteria andLogisticsDateLessThan(Date value) {
			addCriterionForJDBCDate("LOGISTICS_DATE <", value, "logisticsDate");
			return (Criteria) this;
		}

		public Criteria andLogisticsDateLessThanOrEqualTo(Date value) {
			addCriterionForJDBCDate("LOGISTICS_DATE <=", value, "logisticsDate");
			return (Criteria) this;
		}

		public Criteria andLogisticsDateIn(List values) {
			addCriterionForJDBCDate("LOGISTICS_DATE in", values, "logisticsDate");
			return (Criteria) this;
		}

		public Criteria andLogisticsDateNotIn(List values) {
			addCriterionForJDBCDate("LOGISTICS_DATE not in", values, "logisticsDate");
			return (Criteria) this;
		}

		public Criteria andLogisticsDateBetween(Date value1, Date value2) {
			addCriterionForJDBCDate("LOGISTICS_DATE between", value1, value2, "logisticsDate");
			return (Criteria) this;
		}

		public Criteria andLogisticsDateNotBetween(Date value1, Date value2) {
			addCriterionForJDBCDate("LOGISTICS_DATE not between", value1, value2, "logisticsDate");
			return (Criteria) this;
		}

		public Criteria andIsDeliveriesIsNull() {
			addCriterion("IS_DELIVERIES is null");
			return (Criteria) this;
		}

		public Criteria andIsDeliveriesIsNotNull() {
			addCriterion("IS_DELIVERIES is not null");
			return (Criteria) this;
		}

		public Criteria andIsDeliveriesEqualTo(String value) {
			addCriterion("IS_DELIVERIES =", value, "isDeliveries");
			return (Criteria) this;
		}

		public Criteria andIsDeliveriesNotEqualTo(String value) {
			addCriterion("IS_DELIVERIES <>", value, "isDeliveries");
			return (Criteria) this;
		}

		public Criteria andIsDeliveriesGreaterThan(String value) {
			addCriterion("IS_DELIVERIES >", value, "isDeliveries");
			return (Criteria) this;
		}

		public Criteria andIsDeliveriesGreaterThanOrEqualTo(String value) {
			addCriterion("IS_DELIVERIES >=", value, "isDeliveries");
			return (Criteria) this;
		}

		public Criteria andIsDeliveriesLessThan(String value) {
			addCriterion("IS_DELIVERIES <", value, "isDeliveries");
			return (Criteria) this;
		}

		public Criteria andIsDeliveriesLessThanOrEqualTo(String value) {
			addCriterion("IS_DELIVERIES <=", value, "isDeliveries");
			return (Criteria) this;
		}

		public Criteria andIsDeliveriesLike(String value) {
			addCriterion("IS_DELIVERIES like", "%" + value + "%", "isDeliveries");
			return (Criteria) this;
		}

		public Criteria andIsDeliveriesNotLike(String value) {
			addCriterion("IS_DELIVERIES not like", "%" + value + "%", "isDeliveries");
			return (Criteria) this;
		}

		public Criteria andIsDeliveriesIn(List values) {
			addCriterion("IS_DELIVERIES in", values, "isDeliveries");
			return (Criteria) this;
		}

		public Criteria andIsDeliveriesNotIn(List values) {
			addCriterion("IS_DELIVERIES not in", values, "isDeliveries");
			return (Criteria) this;
		}

		public Criteria andIsDeliveriesBetween(String value1, String value2) {
			addCriterion("IS_DELIVERIES between", value1, value2, "isDeliveries");
			return (Criteria) this;
		}

		public Criteria andIsDeliveriesNotBetween(String value1, String value2) {
			addCriterion("IS_DELIVERIES not between", value1, value2, "isDeliveries");
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

		public Criteria andCreateUserIsNull() {
			addCriterion("CREATE_USER is null");
			return (Criteria) this;
		}

		public Criteria andCreateUserIsNotNull() {
			addCriterion("CREATE_USER is not null");
			return (Criteria) this;
		}

		public Criteria andCreateUserEqualTo(BigDecimal value) {
			addCriterion("CREATE_USER =", value, "createUser");
			return (Criteria) this;
		}

		public Criteria andCreateUserNotEqualTo(BigDecimal value) {
			addCriterion("CREATE_USER <>", value, "createUser");
			return (Criteria) this;
		}

		public Criteria andCreateUserGreaterThan(BigDecimal value) {
			addCriterion("CREATE_USER >", value, "createUser");
			return (Criteria) this;
		}

		public Criteria andCreateUserGreaterThanOrEqualTo(BigDecimal value) {
			addCriterion("CREATE_USER >=", value, "createUser");
			return (Criteria) this;
		}

		public Criteria andCreateUserLessThan(BigDecimal value) {
			addCriterion("CREATE_USER <", value, "createUser");
			return (Criteria) this;
		}

		public Criteria andCreateUserLessThanOrEqualTo(BigDecimal value) {
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

		public Criteria andCreateUserBetween(BigDecimal value1, BigDecimal value2) {
			addCriterion("CREATE_USER between", value1, value2, "createUser");
			return (Criteria) this;
		}

		public Criteria andCreateUserNotBetween(BigDecimal value1, BigDecimal value2) {
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

		public Criteria andOrderNoIsNull() {
			addCriterion("ORDER_NO is null");
			return (Criteria) this;
		}

		public Criteria andOrderNoIsNotNull() {
			addCriterion("ORDER_NO is not null");
			return (Criteria) this;
		}

		public Criteria andOrderNoEqualTo(String value) {
			addCriterion("ORDER_NO =", value, "orderNo");
			return (Criteria) this;
		}

		public Criteria andOrderNoNotEqualTo(String value) {
			addCriterion("ORDER_NO <>", value, "orderNo");
			return (Criteria) this;
		}

		public Criteria andOrderNoGreaterThan(String value) {
			addCriterion("ORDER_NO >", value, "orderNo");
			return (Criteria) this;
		}

		public Criteria andOrderNoGreaterThanOrEqualTo(String value) {
			addCriterion("ORDER_NO >=", value, "orderNo");
			return (Criteria) this;
		}

		public Criteria andOrderNoLessThan(String value) {
			addCriterion("ORDER_NO <", value, "orderNo");
			return (Criteria) this;
		}

		public Criteria andOrderNoLessThanOrEqualTo(String value) {
			addCriterion("ORDER_NO <=", value, "orderNo");
			return (Criteria) this;
		}

		public Criteria andOrderNoLike(String value) {
			addCriterion("ORDER_NO like", value, "orderNo");
			return (Criteria) this;
		}

		public Criteria andOrderNoNotLike(String value) {
			addCriterion("ORDER_NO not like", value, "orderNo");
			return (Criteria) this;
		}

		public Criteria andOrderNoIn(List values) {
			addCriterion("ORDER_NO in", values, "orderNo");
			return (Criteria) this;
		}

		public Criteria andOrderNoNotIn(List values) {
			addCriterion("ORDER_NO not in", values, "orderNo");
			return (Criteria) this;
		}

		public Criteria andOrderNoBetween(String value1, String value2) {
			addCriterion("ORDER_NO between", value1, value2, "orderNo");
			return (Criteria) this;
		}

		public Criteria andOrderNoNotBetween(String value1, String value2) {
			addCriterion("ORDER_NO not between", value1, value2, "orderNo");
			return (Criteria) this;
		}
	}

	public static class Criteria extends GeneratedCriteria {

		protected Criteria() {
			super();
		}
	}
}