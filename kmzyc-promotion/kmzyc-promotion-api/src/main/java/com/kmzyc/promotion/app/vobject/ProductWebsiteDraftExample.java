package com.kmzyc.promotion.app.vobject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unchecked")
public class ProductWebsiteDraftExample {
	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds
	 * to the database table PRODUCT_WEBSITE_DRAFT
	 * 
	 * @ibatorgenerated Fri Jun 06 09:46:11 CST 2014
	 */
	protected String orderByClause;

	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds
	 * to the database table PRODUCT_WEBSITE_DRAFT
	 * 
	 * @ibatorgenerated Fri Jun 06 09:46:11 CST 2014
	 */
	protected List oredCriteria;

	/**
	 * This method was generated by Apache iBATIS ibator. This method
	 * corresponds to the database table PRODUCT_WEBSITE_DRAFT
	 * 
	 * @ibatorgenerated Fri Jun 06 09:46:11 CST 2014
	 */
	public ProductWebsiteDraftExample() {
		oredCriteria = new ArrayList();
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method
	 * corresponds to the database table PRODUCT_WEBSITE_DRAFT
	 * 
	 * @ibatorgenerated Fri Jun 06 09:46:11 CST 2014
	 */
	protected ProductWebsiteDraftExample(ProductWebsiteDraftExample example) {
		this.orderByClause = example.orderByClause;
		this.oredCriteria = example.oredCriteria;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method
	 * corresponds to the database table PRODUCT_WEBSITE_DRAFT
	 * 
	 * @ibatorgenerated Fri Jun 06 09:46:11 CST 2014
	 */
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method
	 * corresponds to the database table PRODUCT_WEBSITE_DRAFT
	 * 
	 * @ibatorgenerated Fri Jun 06 09:46:11 CST 2014
	 */
	public String getOrderByClause() {
		return orderByClause;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method
	 * corresponds to the database table PRODUCT_WEBSITE_DRAFT
	 * 
	 * @ibatorgenerated Fri Jun 06 09:46:11 CST 2014
	 */
	public List getOredCriteria() {
		return oredCriteria;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method
	 * corresponds to the database table PRODUCT_WEBSITE_DRAFT
	 * 
	 * @ibatorgenerated Fri Jun 06 09:46:11 CST 2014
	 */
	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method
	 * corresponds to the database table PRODUCT_WEBSITE_DRAFT
	 * 
	 * @ibatorgenerated Fri Jun 06 09:46:11 CST 2014
	 */
	public Criteria createCriteria() {
		Criteria criteria = createCriteriaInternal();
		if (oredCriteria.size() == 0) {
			oredCriteria.add(criteria);
		}
		return criteria;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method
	 * corresponds to the database table PRODUCT_WEBSITE_DRAFT
	 * 
	 * @ibatorgenerated Fri Jun 06 09:46:11 CST 2014
	 */
	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method
	 * corresponds to the database table PRODUCT_WEBSITE_DRAFT
	 * 
	 * @ibatorgenerated Fri Jun 06 09:46:11 CST 2014
	 */
	public void clear() {
		oredCriteria.clear();
	}

	/**
	 * This class was generated by Apache iBATIS ibator. This class corresponds
	 * to the database table PRODUCT_WEBSITE_DRAFT
	 * 
	 * @ibatorgenerated Fri Jun 06 09:46:11 CST 2014
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

		public Criteria andPwidsIsNull() {
			addCriterion("PWIDS is null");
			return this;
		}

		public Criteria andPwidsIsNotNull() {
			addCriterion("PWIDS is not null");
			return this;
		}

		public Criteria andPwidsEqualTo(Long value) {
			addCriterion("PWIDS =", value, "pwids");
			return this;
		}

		public Criteria andPwidsNotEqualTo(Long value) {
			addCriterion("PWIDS <>", value, "pwids");
			return this;
		}

		public Criteria andPwidsGreaterThan(Long value) {
			addCriterion("PWIDS >", value, "pwids");
			return this;
		}

		public Criteria andPwidsGreaterThanOrEqualTo(Long value) {
			addCriterion("PWIDS >=", value, "pwids");
			return this;
		}

		public Criteria andPwidsLessThan(Long value) {
			addCriterion("PWIDS <", value, "pwids");
			return this;
		}

		public Criteria andPwidsLessThanOrEqualTo(Long value) {
			addCriterion("PWIDS <=", value, "pwids");
			return this;
		}

		public Criteria andPwidsIn(List values) {
			addCriterion("PWIDS in", values, "pwids");
			return this;
		}

		public Criteria andPwidsNotIn(List values) {
			addCriterion("PWIDS not in", values, "pwids");
			return this;
		}

		public Criteria andPwidsBetween(Long value1, Long value2) {
			addCriterion("PWIDS between", value1, value2, "pwids");
			return this;
		}

		public Criteria andPwidsNotBetween(Long value1, Long value2) {
			addCriterion("PWIDS not between", value1, value2, "pwids");
			return this;
		}

		public Criteria andProductIdIsNull() {
			addCriterion("PRODUCT_ID is null");
			return this;
		}

		public Criteria andProductIdIsNotNull() {
			addCriterion("PRODUCT_ID is not null");
			return this;
		}

		public Criteria andProductIdEqualTo(Long value) {
			addCriterion("PRODUCT_ID =", value, "productId");
			return this;
		}

		public Criteria andProductIdNotEqualTo(Long value) {
			addCriterion("PRODUCT_ID <>", value, "productId");
			return this;
		}

		public Criteria andProductIdGreaterThan(Long value) {
			addCriterion("PRODUCT_ID >", value, "productId");
			return this;
		}

		public Criteria andProductIdGreaterThanOrEqualTo(Long value) {
			addCriterion("PRODUCT_ID >=", value, "productId");
			return this;
		}

		public Criteria andProductIdLessThan(Long value) {
			addCriterion("PRODUCT_ID <", value, "productId");
			return this;
		}

		public Criteria andProductIdLessThanOrEqualTo(Long value) {
			addCriterion("PRODUCT_ID <=", value, "productId");
			return this;
		}

		public Criteria andProductIdIn(List values) {
			addCriterion("PRODUCT_ID in", values, "productId");
			return this;
		}

		public Criteria andProductIdNotIn(List values) {
			addCriterion("PRODUCT_ID not in", values, "productId");
			return this;
		}

		public Criteria andProductIdBetween(Long value1, Long value2) {
			addCriterion("PRODUCT_ID between", value1, value2, "productId");
			return this;
		}

		public Criteria andProductIdNotBetween(Long value1, Long value2) {
			addCriterion("PRODUCT_ID not between", value1, value2, "productId");
			return this;
		}

		public Criteria andWebsiteIsNull() {
			addCriterion("WEBSITE is null");
			return this;
		}

		public Criteria andWebsiteIsNotNull() {
			addCriterion("WEBSITE is not null");
			return this;
		}

		public Criteria andWebsiteEqualTo(String value) {
			addCriterion("WEBSITE =", value, "website");
			return this;
		}

		public Criteria andWebsiteNotEqualTo(String value) {
			addCriterion("WEBSITE <>", value, "website");
			return this;
		}

		public Criteria andWebsiteGreaterThan(String value) {
			addCriterion("WEBSITE >", value, "website");
			return this;
		}

		public Criteria andWebsiteGreaterThanOrEqualTo(String value) {
			addCriterion("WEBSITE >=", value, "website");
			return this;
		}

		public Criteria andWebsiteLessThan(String value) {
			addCriterion("WEBSITE <", value, "website");
			return this;
		}

		public Criteria andWebsiteLessThanOrEqualTo(String value) {
			addCriterion("WEBSITE <=", value, "website");
			return this;
		}

		public Criteria andWebsiteLike(String value) {
			addCriterion("WEBSITE like", value, "website");
			return this;
		}

		public Criteria andWebsiteNotLike(String value) {
			addCriterion("WEBSITE not like", value, "website");
			return this;
		}

		public Criteria andWebsiteIn(List values) {
			addCriterion("WEBSITE in", values, "website");
			return this;
		}

		public Criteria andWebsiteNotIn(List values) {
			addCriterion("WEBSITE not in", values, "website");
			return this;
		}

		public Criteria andWebsiteBetween(String value1, String value2) {
			addCriterion("WEBSITE between", value1, value2, "website");
			return this;
		}

		public Criteria andWebsiteNotBetween(String value1, String value2) {
			addCriterion("WEBSITE not between", value1, value2, "website");
			return this;
		}
	}
}