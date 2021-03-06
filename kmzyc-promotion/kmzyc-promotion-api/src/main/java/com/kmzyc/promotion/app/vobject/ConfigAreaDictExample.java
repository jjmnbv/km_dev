package com.kmzyc.promotion.app.vobject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unchecked")
public class ConfigAreaDictExample {
	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds
	 * to the database table CONFIG_AREA_DICT
	 * 
	 * @ibatorgenerated Wed Jul 24 17:18:37 CST 2013
	 */
	protected String orderByClause;

	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds
	 * to the database table CONFIG_AREA_DICT
	 * 
	 * @ibatorgenerated Wed Jul 24 17:18:37 CST 2013
	 */
	protected List oredCriteria;

	/**
	 * This method was generated by Apache iBATIS ibator. This method
	 * corresponds to the database table CONFIG_AREA_DICT
	 * 
	 * @ibatorgenerated Wed Jul 24 17:18:37 CST 2013
	 */
	public ConfigAreaDictExample() {
		oredCriteria = new ArrayList();
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method
	 * corresponds to the database table CONFIG_AREA_DICT
	 * 
	 * @ibatorgenerated Wed Jul 24 17:18:37 CST 2013
	 */
	protected ConfigAreaDictExample(ConfigAreaDictExample example) {
		this.orderByClause = example.orderByClause;
		this.oredCriteria = example.oredCriteria;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method
	 * corresponds to the database table CONFIG_AREA_DICT
	 * 
	 * @ibatorgenerated Wed Jul 24 17:18:37 CST 2013
	 */
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method
	 * corresponds to the database table CONFIG_AREA_DICT
	 * 
	 * @ibatorgenerated Wed Jul 24 17:18:37 CST 2013
	 */
	public String getOrderByClause() {
		return orderByClause;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method
	 * corresponds to the database table CONFIG_AREA_DICT
	 * 
	 * @ibatorgenerated Wed Jul 24 17:18:37 CST 2013
	 */
	public List getOredCriteria() {
		return oredCriteria;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method
	 * corresponds to the database table CONFIG_AREA_DICT
	 * 
	 * @ibatorgenerated Wed Jul 24 17:18:37 CST 2013
	 */
	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method
	 * corresponds to the database table CONFIG_AREA_DICT
	 * 
	 * @ibatorgenerated Wed Jul 24 17:18:37 CST 2013
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
	 * corresponds to the database table CONFIG_AREA_DICT
	 * 
	 * @ibatorgenerated Wed Jul 24 17:18:37 CST 2013
	 */
	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method
	 * corresponds to the database table CONFIG_AREA_DICT
	 * 
	 * @ibatorgenerated Wed Jul 24 17:18:37 CST 2013
	 */
	public void clear() {
		oredCriteria.clear();
	}

	/**
	 * This class was generated by Apache iBATIS ibator. This class corresponds
	 * to the database table CONFIG_AREA_DICT
	 * 
	 * @ibatorgenerated Wed Jul 24 17:18:37 CST 2013
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

		public Criteria andAreaIdIsNull() {
			addCriterion("N_AREA_ID is null");
			return this;
		}

		public Criteria andAreaIdIsNotNull() {
			addCriterion("N_AREA_ID is not null");
			return this;
		}

		public Criteria andAreaIdEqualTo(Integer value) {
			addCriterion("N_AREA_ID =", value, "areaId");
			return this;
		}

		public Criteria andAreaIdNotEqualTo(Integer value) {
			addCriterion("N_AREA_ID <>", value, "areaId");
			return this;
		}

		public Criteria andAreaIdGreaterThan(Integer value) {
			addCriterion("N_AREA_ID >", value, "areaId");
			return this;
		}

		public Criteria andAreaIdGreaterThanOrEqualTo(Integer value) {
			addCriterion("N_AREA_ID >=", value, "areaId");
			return this;
		}

		public Criteria andAreaIdLessThan(Integer value) {
			addCriterion("N_AREA_ID <", value, "areaId");
			return this;
		}

		public Criteria andAreaIdLessThanOrEqualTo(Integer value) {
			addCriterion("N_AREA_ID <=", value, "areaId");
			return this;
		}

		public Criteria andAreaIdIn(List values) {
			addCriterion("N_AREA_ID in", values, "areaId");
			return this;
		}

		public Criteria andAreaIdNotIn(List values) {
			addCriterion("N_AREA_ID not in", values, "nAreaId");
			return this;
		}

		public Criteria andAreaIdBetween(Integer value1, Integer value2) {
			addCriterion("N_AREA_ID between", value1, value2, "areaId");
			return this;
		}

		public Criteria andNAreaIdNotBetween(Integer value1, Integer value2) {
			addCriterion("N_AREA_ID not between", value1, value2, "nAreaId");
			return this;
		}

		public Criteria andAreaCodeIsNull() {
			addCriterion("AREA_CODE is null");
			return this;
		}

		public Criteria andAreaCodeIsNotNull() {
			addCriterion("AREA_CODE is not null");
			return this;
		}

		public Criteria andAreaCodeEqualTo(String value) {
			addCriterion("AREA_CODE =", value, "areaCode");
			return this;
		}

		public Criteria andAreaCodeNotEqualTo(String value) {
			addCriterion("AREA_CODE <>", value, "areaCode");
			return this;
		}

		public Criteria andAreaCodeGreaterThan(String value) {
			addCriterion("AREA_CODE >", value, "areaCode");
			return this;
		}

		public Criteria andAreaCodeGreaterThanOrEqualTo(String value) {
			addCriterion("AREA_CODE >=", value, "areaCode");
			return this;
		}

		public Criteria andAreaCodeLessThan(String value) {
			addCriterion("AREA_CODE <", value, "areaCode");
			return this;
		}

		public Criteria andAreaCodeLessThanOrEqualTo(String value) {
			addCriterion("AREA_CODE <=", value, "areaCode");
			return this;
		}

		public Criteria andAreaCodeLike(String value) {
			addCriterion("AREA_CODE like", value, "areaCode");
			return this;
		}

		public Criteria andAreaCodeNotLike(String value) {
			addCriterion("AREA_CODE not like", value, "areaCode");
			return this;
		}

		public Criteria andAreaCodeIn(List values) {
			addCriterion("AREA_CODE in", values, "areaCode");
			return this;
		}

		public Criteria andAreaCodeNotIn(List values) {
			addCriterion("AREA_CODE not in", values, "areaCode");
			return this;
		}

		public Criteria andAreaCodeBetween(String value1, String value2) {
			addCriterion("AREA_CODE between", value1, value2, "areaCode");
			return this;
		}

		public Criteria andAreaCodeNotBetween(String value1, String value2) {
			addCriterion("AREA_CODE not between", value1, value2, "areaCode");
			return this;
		}

		public Criteria andAreaNameIsNull() {
			addCriterion("AREA_NAME is null");
			return this;
		}

		public Criteria andAreaNameIsNotNull() {
			addCriterion("AREA_NAME is not null");
			return this;
		}

		public Criteria andAreaNameEqualTo(String value) {
			addCriterion("AREA_NAME =", value, "areaName");
			return this;
		}

		public Criteria andAreaNameNotEqualTo(String value) {
			addCriterion("AREA_NAME <>", value, "areaName");
			return this;
		}

		public Criteria andAreaNameGreaterThan(String value) {
			addCriterion("AREA_NAME >", value, "areaName");
			return this;
		}

		public Criteria andAreaNameGreaterThanOrEqualTo(String value) {
			addCriterion("AREA_NAME >=", value, "areaName");
			return this;
		}

		public Criteria andAreaNameLessThan(String value) {
			addCriterion("AREA_NAME <", value, "areaName");
			return this;
		}

		public Criteria andAreaNameLessThanOrEqualTo(String value) {
			addCriterion("AREA_NAME <=", value, "areaName");
			return this;
		}

		public Criteria andAreaNameLike(String value) {
			addCriterion("AREA_NAME like", value, "areaName");
			return this;
		}

		public Criteria andAreaNameNotLike(String value) {
			addCriterion("AREA_NAME not like", value, "areaName");
			return this;
		}

		public Criteria andAreaNameIn(List values) {
			addCriterion("AREA_NAME in", values, "areaName");
			return this;
		}

		public Criteria andAreaNameNotIn(List values) {
			addCriterion("AREA_NAME not in", values, "areaName");
			return this;
		}

		public Criteria andAreaNameBetween(String value1, String value2) {
			addCriterion("AREA_NAME between", value1, value2, "areaName");
			return this;
		}

		public Criteria andAreaNameNotBetween(String value1, String value2) {
			addCriterion("AREA_NAME not between", value1, value2, "areaName");
			return this;
		}

		public Criteria andSupperareaIdIsNull() {
			addCriterion("N_SUPPERAREA_ID is null");
			return this;
		}

		public Criteria andSupperareaIdIsNotNull() {
			addCriterion("N_SUPPERAREA_ID is not null");
			return this;
		}

		public Criteria andSupperareaIdEqualTo(Integer value) {
			addCriterion("N_SUPPERAREA_ID =", value, "supperareaId");
			return this;
		}

		public Criteria andSupperareaIdNotEqualTo(Integer value) {
			addCriterion("N_SUPPERAREA_ID <>", value, "supperareaId");
			return this;
		}

		public Criteria andSupperareaIdGreaterThan(Integer value) {
			addCriterion("N_SUPPERAREA_ID >", value, "supperareaId");
			return this;
		}

		public Criteria andSupperareaIdGreaterThanOrEqualTo(Integer value) {
			addCriterion("N_SUPPERAREA_ID >=", value, "supperareaId");
			return this;
		}

		public Criteria andSupperareaIdLessThan(Integer value) {
			addCriterion("N_SUPPERAREA_ID <", value, "supperareaId");
			return this;
		}

		public Criteria andSupperareaIdLessThanOrEqualTo(Integer value) {
			addCriterion("N_SUPPERAREA_ID <=", value, "supperareaId");
			return this;
		}

		public Criteria andSupperareaIdIn(List values) {
			addCriterion("N_SUPPERAREA_ID in", values, "supperareaId");
			return this;
		}

		public Criteria andSupperareaIdNotIn(List values) {
			addCriterion("N_SUPPERAREA_ID not in", values, "supperareaId");
			return this;
		}

		public Criteria andSupperareaIdBetween(Integer value1, Integer value2) {
			addCriterion("N_SUPPERAREA_ID between", value1, value2, "supperareaId");
			return this;
		}

		public Criteria andSupperareaIdNotBetween(Integer value1, Integer value2) {
			addCriterion("N_SUPPERAREA_ID not between", value1, value2, "supperareaId");
			return this;
		}

		public Criteria andAreatypeIsNull() {
			addCriterion("AREATYPE is null");
			return this;
		}

		public Criteria andAreatypeIsNotNull() {
			addCriterion("AREATYPE is not null");
			return this;
		}

		public Criteria andAreatypeEqualTo(Short value) {
			addCriterion("AREATYPE =", value, "areatype");
			return this;
		}

		public Criteria andAreatypeNotEqualTo(Short value) {
			addCriterion("AREATYPE <>", value, "areatype");
			return this;
		}

		public Criteria andAreatypeGreaterThan(Short value) {
			addCriterion("AREATYPE >", value, "areatype");
			return this;
		}

		public Criteria andAreatypeGreaterThanOrEqualTo(Short value) {
			addCriterion("AREATYPE >=", value, "areatype");
			return this;
		}

		public Criteria andAreatypeLessThan(Short value) {
			addCriterion("AREATYPE <", value, "areatype");
			return this;
		}

		public Criteria andAreatypeLessThanOrEqualTo(Short value) {
			addCriterion("AREATYPE <=", value, "areatype");
			return this;
		}

		public Criteria andAreatypeIn(List values) {
			addCriterion("AREATYPE in", values, "areatype");
			return this;
		}

		public Criteria andAreatypeNotIn(List values) {
			addCriterion("AREATYPE not in", values, "areatype");
			return this;
		}

		public Criteria andAreatypeBetween(Short value1, Short value2) {
			addCriterion("AREATYPE between", value1, value2, "areatype");
			return this;
		}

		public Criteria andAreatypeNotBetween(Short value1, Short value2) {
			addCriterion("AREATYPE not between", value1, value2, "areatype");
			return this;
		}
	}
}