package com.pltfm.app.vobject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SectionsDetailExample {

	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds to the database table SECTIONS_DETAIL
	 * @ibatorgenerated  Tue Jul 30 10:57:37 CST 2013
	 */
	protected String orderByClause;
	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds to the database table SECTIONS_DETAIL
	 * @ibatorgenerated  Tue Jul 30 10:57:37 CST 2013
	 */
	protected List oredCriteria;

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table SECTIONS_DETAIL
	 * @ibatorgenerated  Tue Jul 30 10:57:37 CST 2013
	 */
	public SectionsDetailExample() {
		oredCriteria = new ArrayList();
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table SECTIONS_DETAIL
	 * @ibatorgenerated  Tue Jul 30 10:57:37 CST 2013
	 */
	protected SectionsDetailExample(SectionsDetailExample example) {
		this.orderByClause = example.orderByClause;
		this.oredCriteria = example.oredCriteria;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table SECTIONS_DETAIL
	 * @ibatorgenerated  Tue Jul 30 10:57:37 CST 2013
	 */
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table SECTIONS_DETAIL
	 * @ibatorgenerated  Tue Jul 30 10:57:37 CST 2013
	 */
	public String getOrderByClause() {
		return orderByClause;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table SECTIONS_DETAIL
	 * @ibatorgenerated  Tue Jul 30 10:57:37 CST 2013
	 */
	public List getOredCriteria() {
		return oredCriteria;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table SECTIONS_DETAIL
	 * @ibatorgenerated  Tue Jul 30 10:57:37 CST 2013
	 */
	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table SECTIONS_DETAIL
	 * @ibatorgenerated  Tue Jul 30 10:57:37 CST 2013
	 */
	public Criteria createCriteria() {
		Criteria criteria = createCriteriaInternal();
		if (oredCriteria.size() == 0) {
			oredCriteria.add(criteria);
		}
		return criteria;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table SECTIONS_DETAIL
	 * @ibatorgenerated  Tue Jul 30 10:57:37 CST 2013
	 */
	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table SECTIONS_DETAIL
	 * @ibatorgenerated  Tue Jul 30 10:57:37 CST 2013
	 */
	public void clear() {
		oredCriteria.clear();
	}

	/**
	 * This class was generated by Apache iBATIS ibator. This class corresponds to the database table SECTIONS_DETAIL
	 * @ibatorgenerated  Tue Jul 30 10:57:37 CST 2013
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

		protected void addCriterion(String condition, Object value,
				String property) {
			if (value == null) {
				throw new RuntimeException("Value for " + property
						+ " cannot be null");
			}
			Map map = new HashMap();
			map.put("condition", condition);
			map.put("value", value);
			criteriaWithSingleValue.add(map);
		}

		protected void addCriterion(String condition, List values,
				String property) {
			if (values == null || values.size() == 0) {
				throw new RuntimeException("Value list for " + property
						+ " cannot be null or empty");
			}
			Map map = new HashMap();
			map.put("condition", condition);
			map.put("values", values);
			criteriaWithListValue.add(map);
		}

		protected void addCriterion(String condition, Object value1,
				Object value2, String property) {
			if (value1 == null || value2 == null) {
				throw new RuntimeException("Between values for " + property
						+ " cannot be null");
			}
			List list = new ArrayList();
			list.add(value1);
			list.add(value2);
			Map map = new HashMap();
			map.put("condition", condition);
			map.put("values", list);
			criteriaWithBetweenValue.add(map);
		}

		public Criteria andSectionsDetailIdIsNull() {
			addCriterion("SECTIONS_DETAIL_ID is null");
			return this;
		}

		public Criteria andSectionsDetailIdIsNotNull() {
			addCriterion("SECTIONS_DETAIL_ID is not null");
			return this;
		}

		public Criteria andSectionsDetailIdEqualTo(Long value) {
			addCriterion("SECTIONS_DETAIL_ID =", value, "sectionsDetailId");
			return this;
		}

		public Criteria andSectionsDetailIdNotEqualTo(Long value) {
			addCriterion("SECTIONS_DETAIL_ID <>", value, "sectionsDetailId");
			return this;
		}

		public Criteria andSectionsDetailIdGreaterThan(Long value) {
			addCriterion("SECTIONS_DETAIL_ID >", value, "sectionsDetailId");
			return this;
		}

		public Criteria andSectionsDetailIdGreaterThanOrEqualTo(Long value) {
			addCriterion("SECTIONS_DETAIL_ID >=", value, "sectionsDetailId");
			return this;
		}

		public Criteria andSectionsDetailIdLessThan(Long value) {
			addCriterion("SECTIONS_DETAIL_ID <", value, "sectionsDetailId");
			return this;
		}

		public Criteria andSectionsDetailIdLessThanOrEqualTo(Long value) {
			addCriterion("SECTIONS_DETAIL_ID <=", value, "sectionsDetailId");
			return this;
		}

		public Criteria andSectionsDetailIdIn(List values) {
			addCriterion("SECTIONS_DETAIL_ID in", values, "sectionsDetailId");
			return this;
		}

		public Criteria andSectionsDetailIdNotIn(List values) {
			addCriterion("SECTIONS_DETAIL_ID not in", values,
					"sectionsDetailId");
			return this;
		}

		public Criteria andSectionsDetailIdBetween(Long value1,
				Long value2) {
			addCriterion("SECTIONS_DETAIL_ID between", value1, value2,
					"sectionsDetailId");
			return this;
		}

		public Criteria andSectionsDetailIdNotBetween(Long value1,
				Long value2) {
			addCriterion("SECTIONS_DETAIL_ID not between", value1, value2,
					"sectionsDetailId");
			return this;
		}

		public Criteria andSectionsIdIsNull() {
			addCriterion("SECTIONS_ID is null");
			return this;
		}

		public Criteria andSectionsIdIsNotNull() {
			addCriterion("SECTIONS_ID is not null");
			return this;
		}

		public Criteria andSectionsIdEqualTo(Long value) {
			addCriterion("SECTIONS_ID =", value, "sectionsId");
			return this;
		}

		public Criteria andSectionsIdNotEqualTo(Long value) {
			addCriterion("SECTIONS_ID <>", value, "sectionsId");
			return this;
		}

		public Criteria andSectionsIdGreaterThan(Long value) {
			addCriterion("SECTIONS_ID >", value, "sectionsId");
			return this;
		}

		public Criteria andSectionsIdGreaterThanOrEqualTo(Long value) {
			addCriterion("SECTIONS_ID >=", value, "sectionsId");
			return this;
		}

		public Criteria andSectionsIdLessThan(Long value) {
			addCriterion("SECTIONS_ID <", value, "sectionsId");
			return this;
		}

		public Criteria andSectionsIdLessThanOrEqualTo(Long value) {
			addCriterion("SECTIONS_ID <=", value, "sectionsId");
			return this;
		}

		public Criteria andSectionsIdIn(List values) {
			addCriterion("SECTIONS_ID in", values, "sectionsId");
			return this;
		}

		public Criteria andSectionsIdNotIn(List values) {
			addCriterion("SECTIONS_ID not in", values, "sectionsId");
			return this;
		}

		public Criteria andSectionsIdBetween(Long value1,
				Long value2) {
			addCriterion("SECTIONS_ID between", value1, value2, "sectionsId");
			return this;
		}

		public Criteria andSectionsIdNotBetween(Long value1,
				Long value2) {
			addCriterion("SECTIONS_ID not between", value1, value2,
					"sectionsId");
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

		public Criteria andProductIdNotBetween(Long value1,
				Long value2) {
			addCriterion("PRODUCT_ID not between", value1, value2, "productId");
			return this;
		}

		public Criteria andTitleIsNull() {
			addCriterion("TITLE is null");
			return this;
		}

		public Criteria andTitleIsNotNull() {
			addCriterion("TITLE is not null");
			return this;
		}

		public Criteria andTitleEqualTo(String value) {
			addCriterion("TITLE =", value, "title");
			return this;
		}

		public Criteria andTitleNotEqualTo(String value) {
			addCriterion("TITLE <>", value, "title");
			return this;
		}

		public Criteria andTitleGreaterThan(String value) {
			addCriterion("TITLE >", value, "title");
			return this;
		}

		public Criteria andTitleGreaterThanOrEqualTo(String value) {
			addCriterion("TITLE >=", value, "title");
			return this;
		}

		public Criteria andTitleLessThan(String value) {
			addCriterion("TITLE <", value, "title");
			return this;
		}

		public Criteria andTitleLessThanOrEqualTo(String value) {
			addCriterion("TITLE <=", value, "title");
			return this;
		}

		public Criteria andTitleLike(String value) {
			addCriterion("TITLE like", value, "title");
			return this;
		}

		public Criteria andTitleNotLike(String value) {
			addCriterion("TITLE not like", value, "title");
			return this;
		}

		public Criteria andTitleIn(List values) {
			addCriterion("TITLE in", values, "title");
			return this;
		}

		public Criteria andTitleNotIn(List values) {
			addCriterion("TITLE not in", values, "title");
			return this;
		}

		public Criteria andTitleBetween(String value1, String value2) {
			addCriterion("TITLE between", value1, value2, "title");
			return this;
		}

		public Criteria andTitleNotBetween(String value1, String value2) {
			addCriterion("TITLE not between", value1, value2, "title");
			return this;
		}

		public Criteria andUrlLinkIsNull() {
			addCriterion("URL_LINK is null");
			return this;
		}

		public Criteria andUrlLinkIsNotNull() {
			addCriterion("URL_LINK is not null");
			return this;
		}

		public Criteria andUrlLinkEqualTo(String value) {
			addCriterion("URL_LINK =", value, "urlLink");
			return this;
		}

		public Criteria andUrlLinkNotEqualTo(String value) {
			addCriterion("URL_LINK <>", value, "urlLink");
			return this;
		}

		public Criteria andUrlLinkGreaterThan(String value) {
			addCriterion("URL_LINK >", value, "urlLink");
			return this;
		}

		public Criteria andUrlLinkGreaterThanOrEqualTo(String value) {
			addCriterion("URL_LINK >=", value, "urlLink");
			return this;
		}

		public Criteria andUrlLinkLessThan(String value) {
			addCriterion("URL_LINK <", value, "urlLink");
			return this;
		}

		public Criteria andUrlLinkLessThanOrEqualTo(String value) {
			addCriterion("URL_LINK <=", value, "urlLink");
			return this;
		}

		public Criteria andUrlLinkLike(String value) {
			addCriterion("URL_LINK like", value, "urlLink");
			return this;
		}

		public Criteria andUrlLinkNotLike(String value) {
			addCriterion("URL_LINK not like", value, "urlLink");
			return this;
		}

		public Criteria andUrlLinkIn(List values) {
			addCriterion("URL_LINK in", values, "urlLink");
			return this;
		}

		public Criteria andUrlLinkNotIn(List values) {
			addCriterion("URL_LINK not in", values, "urlLink");
			return this;
		}

		public Criteria andUrlLinkBetween(String value1, String value2) {
			addCriterion("URL_LINK between", value1, value2, "urlLink");
			return this;
		}

		public Criteria andUrlLinkNotBetween(String value1, String value2) {
			addCriterion("URL_LINK not between", value1, value2, "urlLink");
			return this;
		}

		public Criteria andReferencePriceIsNull() {
			addCriterion("REFERENCE_PRICE is null");
			return this;
		}

		public Criteria andReferencePriceIsNotNull() {
			addCriterion("REFERENCE_PRICE is not null");
			return this;
		}

		public Criteria andReferencePriceEqualTo(Long value) {
			addCriterion("REFERENCE_PRICE =", value, "referencePrice");
			return this;
		}

		public Criteria andReferencePriceNotEqualTo(Long value) {
			addCriterion("REFERENCE_PRICE <>", value, "referencePrice");
			return this;
		}

		public Criteria andReferencePriceGreaterThan(Long value) {
			addCriterion("REFERENCE_PRICE >", value, "referencePrice");
			return this;
		}

		public Criteria andReferencePriceGreaterThanOrEqualTo(Long value) {
			addCriterion("REFERENCE_PRICE >=", value, "referencePrice");
			return this;
		}

		public Criteria andReferencePriceLessThan(Long value) {
			addCriterion("REFERENCE_PRICE <", value, "referencePrice");
			return this;
		}

		public Criteria andReferencePriceLessThanOrEqualTo(Long value) {
			addCriterion("REFERENCE_PRICE <=", value, "referencePrice");
			return this;
		}

		public Criteria andReferencePriceIn(List values) {
			addCriterion("REFERENCE_PRICE in", values, "referencePrice");
			return this;
		}

		public Criteria andReferencePriceNotIn(List values) {
			addCriterion("REFERENCE_PRICE not in", values, "referencePrice");
			return this;
		}

		public Criteria andReferencePriceBetween(Long value1,
				Long value2) {
			addCriterion("REFERENCE_PRICE between", value1, value2,
					"referencePrice");
			return this;
		}

		public Criteria andReferencePriceNotBetween(Long value1,
				Long value2) {
			addCriterion("REFERENCE_PRICE not between", value1, value2,
					"referencePrice");
			return this;
		}

		public Criteria andPriceIsNull() {
			addCriterion("PRICE is null");
			return this;
		}

		public Criteria andPriceIsNotNull() {
			addCriterion("PRICE is not null");
			return this;
		}

		public Criteria andPriceEqualTo(BigDecimal value) {
			addCriterion("PRICE =", value, "price");
			return this;
		}

		public Criteria andPriceNotEqualTo(BigDecimal value) {
			addCriterion("PRICE <>", value, "price");
			return this;
		}

		public Criteria andPriceGreaterThan(BigDecimal value) {
			addCriterion("PRICE >", value, "price");
			return this;
		}

		public Criteria andPriceGreaterThanOrEqualTo(BigDecimal value) {
			addCriterion("PRICE >=", value, "price");
			return this;
		}

		public Criteria andPriceLessThan(BigDecimal value) {
			addCriterion("PRICE <", value, "price");
			return this;
		}

		public Criteria andPriceLessThanOrEqualTo(BigDecimal value) {
			addCriterion("PRICE <=", value, "price");
			return this;
		}

		public Criteria andPriceIn(List values) {
			addCriterion("PRICE in", values, "price");
			return this;
		}

		public Criteria andPriceNotIn(List values) {
			addCriterion("PRICE not in", values, "price");
			return this;
		}

		public Criteria andPriceBetween(BigDecimal value1, BigDecimal value2) {
			addCriterion("PRICE between", value1, value2, "price");
			return this;
		}

		public Criteria andPriceNotBetween(BigDecimal value1, BigDecimal value2) {
			addCriterion("PRICE not between", value1, value2, "price");
			return this;
		}

		public Criteria andImageIsNull() {
			addCriterion("IMAGE is null");
			return this;
		}

		public Criteria andImageIsNotNull() {
			addCriterion("IMAGE is not null");
			return this;
		}

		public Criteria andImageEqualTo(String value) {
			addCriterion("IMAGE =", value, "image");
			return this;
		}

		public Criteria andImageNotEqualTo(String value) {
			addCriterion("IMAGE <>", value, "image");
			return this;
		}

		public Criteria andImageGreaterThan(String value) {
			addCriterion("IMAGE >", value, "image");
			return this;
		}

		public Criteria andImageGreaterThanOrEqualTo(String value) {
			addCriterion("IMAGE >=", value, "image");
			return this;
		}

		public Criteria andImageLessThan(String value) {
			addCriterion("IMAGE <", value, "image");
			return this;
		}

		public Criteria andImageLessThanOrEqualTo(String value) {
			addCriterion("IMAGE <=", value, "image");
			return this;
		}

		public Criteria andImageLike(String value) {
			addCriterion("IMAGE like", value, "image");
			return this;
		}

		public Criteria andImageNotLike(String value) {
			addCriterion("IMAGE not like", value, "image");
			return this;
		}

		public Criteria andImageIn(List values) {
			addCriterion("IMAGE in", values, "image");
			return this;
		}

		public Criteria andImageNotIn(List values) {
			addCriterion("IMAGE not in", values, "image");
			return this;
		}

		public Criteria andImageBetween(String value1, String value2) {
			addCriterion("IMAGE between", value1, value2, "image");
			return this;
		}

		public Criteria andImageNotBetween(String value1, String value2) {
			addCriterion("IMAGE not between", value1, value2, "image");
			return this;
		}

		public Criteria andSortnoIsNull() {
			addCriterion("SORTNO is null");
			return this;
		}

		public Criteria andSortnoIsNotNull() {
			addCriterion("SORTNO is not null");
			return this;
		}

		public Criteria andSortnoEqualTo(Short value) {
			addCriterion("SORTNO =", value, "sortno");
			return this;
		}

		public Criteria andSortnoNotEqualTo(Short value) {
			addCriterion("SORTNO <>", value, "sortno");
			return this;
		}

		public Criteria andSortnoGreaterThan(Short value) {
			addCriterion("SORTNO >", value, "sortno");
			return this;
		}

		public Criteria andSortnoGreaterThanOrEqualTo(Short value) {
			addCriterion("SORTNO >=", value, "sortno");
			return this;
		}

		public Criteria andSortnoLessThan(Short value) {
			addCriterion("SORTNO <", value, "sortno");
			return this;
		}

		public Criteria andSortnoLessThanOrEqualTo(Short value) {
			addCriterion("SORTNO <=", value, "sortno");
			return this;
		}

		public Criteria andSortnoIn(List values) {
			addCriterion("SORTNO in", values, "sortno");
			return this;
		}

		public Criteria andSortnoNotIn(List values) {
			addCriterion("SORTNO not in", values, "sortno");
			return this;
		}

		public Criteria andSortnoBetween(Short value1, Short value2) {
			addCriterion("SORTNO between", value1, value2, "sortno");
			return this;
		}

		public Criteria andSortnoNotBetween(Short value1, Short value2) {
			addCriterion("SORTNO not between", value1, value2, "sortno");
			return this;
		}
	}
}