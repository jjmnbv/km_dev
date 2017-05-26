package com.kmzyc.promotion.app.vobject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unchecked")
public class CategoryAttrExample {
	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds
	 * to the database table CATEGORY_ATTR
	 * 
	 * @ibatorgenerated Tue Aug 13 10:15:23 CST 2013
	 */
	protected String orderByClause;

	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds
	 * to the database table CATEGORY_ATTR
	 * 
	 * @ibatorgenerated Tue Aug 13 10:15:23 CST 2013
	 */
	protected List oredCriteria;

	/**
	 * This method was generated by Apache iBATIS ibator. This method
	 * corresponds to the database table CATEGORY_ATTR
	 * 
	 * @ibatorgenerated Tue Aug 13 10:15:23 CST 2013
	 */
	public CategoryAttrExample() {
		oredCriteria = new ArrayList();
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method
	 * corresponds to the database table CATEGORY_ATTR
	 * 
	 * @ibatorgenerated Tue Aug 13 10:15:23 CST 2013
	 */
	protected CategoryAttrExample(CategoryAttrExample example) {
		this.orderByClause = example.orderByClause;
		this.oredCriteria = example.oredCriteria;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method
	 * corresponds to the database table CATEGORY_ATTR
	 * 
	 * @ibatorgenerated Tue Aug 13 10:15:23 CST 2013
	 */
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method
	 * corresponds to the database table CATEGORY_ATTR
	 * 
	 * @ibatorgenerated Tue Aug 13 10:15:23 CST 2013
	 */
	public String getOrderByClause() {
		return orderByClause;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method
	 * corresponds to the database table CATEGORY_ATTR
	 * 
	 * @ibatorgenerated Tue Aug 13 10:15:23 CST 2013
	 */
	public List getOredCriteria() {
		return oredCriteria;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method
	 * corresponds to the database table CATEGORY_ATTR
	 * 
	 * @ibatorgenerated Tue Aug 13 10:15:23 CST 2013
	 */
	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method
	 * corresponds to the database table CATEGORY_ATTR
	 * 
	 * @ibatorgenerated Tue Aug 13 10:15:23 CST 2013
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
	 * corresponds to the database table CATEGORY_ATTR
	 * 
	 * @ibatorgenerated Tue Aug 13 10:15:23 CST 2013
	 */
	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method
	 * corresponds to the database table CATEGORY_ATTR
	 * 
	 * @ibatorgenerated Tue Aug 13 10:15:23 CST 2013
	 */
	public void clear() {
		oredCriteria.clear();
	}

	/**
	 * This class was generated by Apache iBATIS ibator. This class corresponds
	 * to the database table CATEGORY_ATTR
	 * 
	 * @ibatorgenerated Tue Aug 13 10:15:23 CST 2013
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

		protected void addCriterionForJDBCDate(String condition, Date value, String property) {
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

		public Criteria andCategoryAttrIdIsNull() {
			addCriterion("CATEGORY_ATTR_ID is null");
			return this;
		}

		public Criteria andCategoryAttrIdIsNotNull() {
			addCriterion("CATEGORY_ATTR_ID is not null");
			return this;
		}

		public Criteria andCategoryAttrIdEqualTo(Integer value) {
			addCriterion("CATEGORY_ATTR_ID =", value, "categoryAttrId");
			return this;
		}

		public Criteria andCategoryAttrIdNotEqualTo(Integer value) {
			addCriterion("CATEGORY_ATTR_ID <>", value, "categoryAttrId");
			return this;
		}

		public Criteria andCategoryAttrIdGreaterThan(Integer value) {
			addCriterion("CATEGORY_ATTR_ID >", value, "categoryAttrId");
			return this;
		}

		public Criteria andCategoryAttrIdGreaterThanOrEqualTo(Integer value) {
			addCriterion("CATEGORY_ATTR_ID >=", value, "categoryAttrId");
			return this;
		}

		public Criteria andCategoryAttrIdLessThan(Integer value) {
			addCriterion("CATEGORY_ATTR_ID <", value, "categoryAttrId");
			return this;
		}

		public Criteria andCategoryAttrIdLessThanOrEqualTo(Integer value) {
			addCriterion("CATEGORY_ATTR_ID <=", value, "categoryAttrId");
			return this;
		}

		public Criteria andCategoryAttrIdIn(List values) {
			addCriterion("CATEGORY_ATTR_ID in", values, "categoryAttrId");
			return this;
		}

		public Criteria andCategoryAttrIdNotIn(List values) {
			addCriterion("CATEGORY_ATTR_ID not in", values, "categoryAttrId");
			return this;
		}

		public Criteria andCategoryAttrIdBetween(Integer value1, Integer value2) {
			addCriterion("CATEGORY_ATTR_ID between", value1, value2, "categoryAttrId");
			return this;
		}

		public Criteria andCategoryAttrIdNotBetween(Integer value1, Integer value2) {
			addCriterion("CATEGORY_ATTR_ID not between", value1, value2, "categoryAttrId");
			return this;
		}

		public Criteria andCategoryIdIsNull() {
			addCriterion("CATEGORY_ID is null");
			return this;
		}

		public Criteria andCategoryIdIsNotNull() {
			addCriterion("CATEGORY_ID is not null");
			return this;
		}

		public Criteria andCategoryIdEqualTo(Integer value) {
			addCriterion("CATEGORY_ID =", value, "categoryId");
			return this;
		}

		public Criteria andCategoryIdNotEqualTo(Integer value) {
			addCriterion("CATEGORY_ID <>", value, "categoryId");
			return this;
		}

		public Criteria andCategoryIdGreaterThan(Integer value) {
			addCriterion("CATEGORY_ID >", value, "categoryId");
			return this;
		}

		public Criteria andCategoryIdGreaterThanOrEqualTo(Integer value) {
			addCriterion("CATEGORY_ID >=", value, "categoryId");
			return this;
		}

		public Criteria andCategoryIdLessThan(Integer value) {
			addCriterion("CATEGORY_ID <", value, "categoryId");
			return this;
		}

		public Criteria andCategoryIdLessThanOrEqualTo(Integer value) {
			addCriterion("CATEGORY_ID <=", value, "categoryId");
			return this;
		}

		public Criteria andCategoryIdIn(List values) {
			addCriterion("CATEGORY_ID in", values, "categoryId");
			return this;
		}

		public Criteria andCategoryIdNotIn(List values) {
			addCriterion("CATEGORY_ID not in", values, "categoryId");
			return this;
		}

		public Criteria andCategoryIdBetween(Integer value1, Integer value2) {
			addCriterion("CATEGORY_ID between", value1, value2, "categoryId");
			return this;
		}

		public Criteria andCategoryIdNotBetween(Integer value1, Integer value2) {
			addCriterion("CATEGORY_ID not between", value1, value2, "categoryId");
			return this;
		}

		public Criteria andCategoryAttrNameIsNull() {
			addCriterion("CATEGORY_ATTR_NAME is null");
			return this;
		}

		public Criteria andCategoryAttrNameIsNotNull() {
			addCriterion("CATEGORY_ATTR_NAME is not null");
			return this;
		}

		public Criteria andCategoryAttrNameEqualTo(String value) {
			addCriterion("CATEGORY_ATTR_NAME =", value, "categoryAttrName");
			return this;
		}

		public Criteria andCategoryAttrNameNotEqualTo(String value) {
			addCriterion("CATEGORY_ATTR_NAME <>", value, "categoryAttrName");
			return this;
		}

		public Criteria andCategoryAttrNameGreaterThan(String value) {
			addCriterion("CATEGORY_ATTR_NAME >", value, "categoryAttrName");
			return this;
		}

		public Criteria andCategoryAttrNameGreaterThanOrEqualTo(String value) {
			addCriterion("CATEGORY_ATTR_NAME >=", value, "categoryAttrName");
			return this;
		}

		public Criteria andCategoryAttrNameLessThan(String value) {
			addCriterion("CATEGORY_ATTR_NAME <", value, "categoryAttrName");
			return this;
		}

		public Criteria andCategoryAttrNameLessThanOrEqualTo(String value) {
			addCriterion("CATEGORY_ATTR_NAME <=", value, "categoryAttrName");
			return this;
		}

		public Criteria andCategoryAttrNameLike(String value) {
			addCriterion("CATEGORY_ATTR_NAME like", value, "categoryAttrName");
			return this;
		}

		public Criteria andCategoryAttrNameNotLike(String value) {
			addCriterion("CATEGORY_ATTR_NAME not like", value, "categoryAttrName");
			return this;
		}

		public Criteria andCategoryAttrNameIn(List values) {
			addCriterion("CATEGORY_ATTR_NAME in", values, "categoryAttrName");
			return this;
		}

		public Criteria andCategoryAttrNameNotIn(List values) {
			addCriterion("CATEGORY_ATTR_NAME not in", values, "categoryAttrName");
			return this;
		}

		public Criteria andCategoryAttrNameBetween(String value1, String value2) {
			addCriterion("CATEGORY_ATTR_NAME between", value1, value2, "categoryAttrName");
			return this;
		}

		public Criteria andCategoryAttrNameNotBetween(String value1, String value2) {
			addCriterion("CATEGORY_ATTR_NAME not between", value1, value2, "categoryAttrName");
			return this;
		}

		public Criteria andInputTypeIsNull() {
			addCriterion("INPUT_TYPE is null");
			return this;
		}

		public Criteria andInputTypeIsNotNull() {
			addCriterion("INPUT_TYPE is not null");
			return this;
		}

		public Criteria andInputTypeEqualTo(Integer value) {
			addCriterion("INPUT_TYPE =", value, "inputType");
			return this;
		}

		public Criteria andInputTypeNotEqualTo(Integer value) {
			addCriterion("INPUT_TYPE <>", value, "inputType");
			return this;
		}

		public Criteria andInputTypeGreaterThan(Integer value) {
			addCriterion("INPUT_TYPE >", value, "inputType");
			return this;
		}

		public Criteria andInputTypeGreaterThanOrEqualTo(Integer value) {
			addCriterion("INPUT_TYPE >=", value, "inputType");
			return this;
		}

		public Criteria andInputTypeLessThan(Integer value) {
			addCriterion("INPUT_TYPE <", value, "inputType");
			return this;
		}

		public Criteria andInputTypeLessThanOrEqualTo(Integer value) {
			addCriterion("INPUT_TYPE <=", value, "inputType");
			return this;
		}

		public Criteria andInputTypeIn(List values) {
			addCriterion("INPUT_TYPE in", values, "inputType");
			return this;
		}

		public Criteria andInputTypeNotIn(List values) {
			addCriterion("INPUT_TYPE not in", values, "inputType");
			return this;
		}

		public Criteria andInputTypeBetween(Integer value1, Integer value2) {
			addCriterion("INPUT_TYPE between", value1, value2, "inputType");
			return this;
		}

		public Criteria andInputTypeNotBetween(Integer value1, Integer value2) {
			addCriterion("INPUT_TYPE not between", value1, value2, "inputType");
			return this;
		}

		public Criteria andIsReqIsNull() {
			addCriterion("IS_REQ is null");
			return this;
		}

		public Criteria andIsReqIsNotNull() {
			addCriterion("IS_REQ is not null");
			return this;
		}

		public Criteria andIsReqEqualTo(Integer value) {
			addCriterion("IS_REQ =", value, "isReq");
			return this;
		}

		public Criteria andIsReqNotEqualTo(Integer value) {
			addCriterion("IS_REQ <>", value, "isReq");
			return this;
		}

		public Criteria andIsReqGreaterThan(Integer value) {
			addCriterion("IS_REQ >", value, "isReq");
			return this;
		}

		public Criteria andIsReqGreaterThanOrEqualTo(Integer value) {
			addCriterion("IS_REQ >=", value, "isReq");
			return this;
		}

		public Criteria andIsReqLessThan(Integer value) {
			addCriterion("IS_REQ <", value, "isReq");
			return this;
		}

		public Criteria andIsReqLessThanOrEqualTo(Integer value) {
			addCriterion("IS_REQ <=", value, "isReq");
			return this;
		}

		public Criteria andIsReqIn(List values) {
			addCriterion("IS_REQ in", values, "isReq");
			return this;
		}

		public Criteria andIsReqNotIn(List values) {
			addCriterion("IS_REQ not in", values, "isReq");
			return this;
		}

		public Criteria andIsReqBetween(Integer value1, Integer value2) {
			addCriterion("IS_REQ between", value1, value2, "isReq");
			return this;
		}

		public Criteria andIsReqNotBetween(Integer value1, Integer value2) {
			addCriterion("IS_REQ not between", value1, value2, "isReq");
			return this;
		}

		public Criteria andIsNavIsNull() {
			addCriterion("IS_NAV is null");
			return this;
		}

		public Criteria andIsNavIsNotNull() {
			addCriterion("IS_NAV is not null");
			return this;
		}

		public Criteria andIsNavEqualTo(Integer value) {
			addCriterion("IS_NAV =", value, "isNav");
			return this;
		}

		public Criteria andIsNavNotEqualTo(Integer value) {
			addCriterion("IS_NAV <>", value, "isNav");
			return this;
		}

		public Criteria andIsNavGreaterThan(Integer value) {
			addCriterion("IS_NAV >", value, "isNav");
			return this;
		}

		public Criteria andIsNavGreaterThanOrEqualTo(Integer value) {
			addCriterion("IS_NAV >=", value, "isNav");
			return this;
		}

		public Criteria andIsNavLessThan(Integer value) {
			addCriterion("IS_NAV <", value, "isNav");
			return this;
		}

		public Criteria andIsNavLessThanOrEqualTo(Integer value) {
			addCriterion("IS_NAV <=", value, "isNav");
			return this;
		}

		public Criteria andIsNavIn(List values) {
			addCriterion("IS_NAV in", values, "isNav");
			return this;
		}

		public Criteria andIsNavNotIn(List values) {
			addCriterion("IS_NAV not in", values, "isNav");
			return this;
		}

		public Criteria andIsNavBetween(Integer value1, Integer value2) {
			addCriterion("IS_NAV between", value1, value2, "isNav");
			return this;
		}

		public Criteria andIsNavNotBetween(Integer value1, Integer value2) {
			addCriterion("IS_NAV not between", value1, value2, "isNav");
			return this;
		}

		public Criteria andStatusIsNull() {
			addCriterion("STATUS is null");
			return this;
		}

		public Criteria andStatusIsNotNull() {
			addCriterion("STATUS is not null");
			return this;
		}

		public Criteria andStatusEqualTo(Integer value) {
			addCriterion("STATUS =", value, "status");
			return this;
		}

		public Criteria andStatusNotEqualTo(Integer value) {
			addCriterion("STATUS <>", value, "status");
			return this;
		}

		public Criteria andStatusGreaterThan(Integer value) {
			addCriterion("STATUS >", value, "status");
			return this;
		}

		public Criteria andStatusGreaterThanOrEqualTo(Integer value) {
			addCriterion("STATUS >=", value, "status");
			return this;
		}

		public Criteria andStatusLessThan(Integer value) {
			addCriterion("STATUS <", value, "status");
			return this;
		}

		public Criteria andStatusLessThanOrEqualTo(Integer value) {
			addCriterion("STATUS <=", value, "status");
			return this;
		}

		public Criteria andStatusIn(List values) {
			addCriterion("STATUS in", values, "status");
			return this;
		}

		public Criteria andStatusNotIn(List values) {
			addCriterion("STATUS not in", values, "status");
			return this;
		}

		public Criteria andStatusBetween(Integer value1, Integer value2) {
			addCriterion("STATUS between", value1, value2, "status");
			return this;
		}

		public Criteria andStatusNotBetween(Integer value1, Integer value2) {
			addCriterion("STATUS not between", value1, value2, "status");
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

		public Criteria andSortnoEqualTo(Integer value) {
			addCriterion("SORTNO =", value, "sortno");
			return this;
		}

		public Criteria andSortnoNotEqualTo(Integer value) {
			addCriterion("SORTNO <>", value, "sortno");
			return this;
		}

		public Criteria andSortnoGreaterThan(Integer value) {
			addCriterion("SORTNO >", value, "sortno");
			return this;
		}

		public Criteria andSortnoGreaterThanOrEqualTo(Integer value) {
			addCriterion("SORTNO >=", value, "sortno");
			return this;
		}

		public Criteria andSortnoLessThan(Integer value) {
			addCriterion("SORTNO <", value, "sortno");
			return this;
		}

		public Criteria andSortnoLessThanOrEqualTo(Integer value) {
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

		public Criteria andSortnoBetween(Integer value1, Integer value2) {
			addCriterion("SORTNO between", value1, value2, "sortno");
			return this;
		}

		public Criteria andSortnoNotBetween(Integer value1, Integer value2) {
			addCriterion("SORTNO not between", value1, value2, "sortno");
			return this;
		}

		public Criteria andCreateTimeIsNull() {
			addCriterion("CREATE_TIME is null");
			return this;
		}

		public Criteria andCreateTimeIsNotNull() {
			addCriterion("CREATE_TIME is not null");
			return this;
		}

		public Criteria andCreateTimeEqualTo(Date value) {
			addCriterionForJDBCDate("CREATE_TIME =", value, "createTime");
			return this;
		}

		public Criteria andCreateTimeNotEqualTo(Date value) {
			addCriterionForJDBCDate("CREATE_TIME <>", value, "createTime");
			return this;
		}

		public Criteria andCreateTimeGreaterThan(Date value) {
			addCriterionForJDBCDate("CREATE_TIME >", value, "createTime");
			return this;
		}

		public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
			addCriterionForJDBCDate("CREATE_TIME >=", value, "createTime");
			return this;
		}

		public Criteria andCreateTimeLessThan(Date value) {
			addCriterionForJDBCDate("CREATE_TIME <", value, "createTime");
			return this;
		}

		public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
			addCriterionForJDBCDate("CREATE_TIME <=", value, "createTime");
			return this;
		}

		public Criteria andCreateTimeIn(List values) {
			addCriterionForJDBCDate("CREATE_TIME in", values, "createTime");
			return this;
		}

		public Criteria andCreateTimeNotIn(List values) {
			addCriterionForJDBCDate("CREATE_TIME not in", values, "createTime");
			return this;
		}

		public Criteria andCreateTimeBetween(Date value1, Date value2) {
			addCriterionForJDBCDate("CREATE_TIME between", value1, value2, "createTime");
			return this;
		}

		public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
			addCriterionForJDBCDate("CREATE_TIME not between", value1, value2, "createTime");
			return this;
		}

		public Criteria andIsSkuIsNull() {
			addCriterion("IS_SKU is null");
			return this;
		}

		public Criteria andIsSkuIsNotNull() {
			addCriterion("IS_SKU is not null");
			return this;
		}

		public Criteria andIsSkuEqualTo(Integer value) {
			addCriterion("IS_SKU =", value, "isSku");
			return this;
		}

		public Criteria andIsSkuNotEqualTo(Integer value) {
			addCriterion("IS_SKU <>", value, "isSku");
			return this;
		}

		public Criteria andIsSkuGreaterThan(Integer value) {
			addCriterion("IS_SKU >", value, "isSku");
			return this;
		}

		public Criteria andIsSkuGreaterThanOrEqualTo(Integer value) {
			addCriterion("IS_SKU >=", value, "isSku");
			return this;
		}

		public Criteria andIsSkuLessThan(Integer value) {
			addCriterion("IS_SKU <", value, "isSku");
			return this;
		}

		public Criteria andIsSkuLessThanOrEqualTo(Integer value) {
			addCriterion("IS_SKU <=", value, "isSku");
			return this;
		}

		public Criteria andIsSkuIn(List values) {
			addCriterion("IS_SKU in", values, "isSku");
			return this;
		}

		public Criteria andIsSkuNotIn(List values) {
			addCriterion("IS_SKU not in", values, "isSku");
			return this;
		}

		public Criteria andIsSkuBetween(Integer value1, Integer value2) {
			addCriterion("IS_SKU between", value1, value2, "isSku");
			return this;
		}

		public Criteria andIsSkuNotBetween(Integer value1, Integer value2) {
			addCriterion("IS_SKU not between", value1, value2, "isSku");
			return this;
		}

		public Criteria andModifTimeIsNull() {
			addCriterion("MODIF_TIME is null");
			return this;
		}

		public Criteria andModifTimeIsNotNull() {
			addCriterion("MODIF_TIME is not null");
			return this;
		}

		public Criteria andModifTimeEqualTo(Date value) {
			addCriterionForJDBCDate("MODIF_TIME =", value, "modifTime");
			return this;
		}

		public Criteria andModifTimeNotEqualTo(Date value) {
			addCriterionForJDBCDate("MODIF_TIME <>", value, "modifTime");
			return this;
		}

		public Criteria andModifTimeGreaterThan(Date value) {
			addCriterionForJDBCDate("MODIF_TIME >", value, "modifTime");
			return this;
		}

		public Criteria andModifTimeGreaterThanOrEqualTo(Date value) {
			addCriterionForJDBCDate("MODIF_TIME >=", value, "modifTime");
			return this;
		}

		public Criteria andModifTimeLessThan(Date value) {
			addCriterionForJDBCDate("MODIF_TIME <", value, "modifTime");
			return this;
		}

		public Criteria andModifTimeLessThanOrEqualTo(Date value) {
			addCriterionForJDBCDate("MODIF_TIME <=", value, "modifTime");
			return this;
		}

		public Criteria andModifTimeIn(List values) {
			addCriterionForJDBCDate("MODIF_TIME in", values, "modifTime");
			return this;
		}

		public Criteria andModifTimeNotIn(List values) {
			addCriterionForJDBCDate("MODIF_TIME not in", values, "modifTime");
			return this;
		}

		public Criteria andModifTimeBetween(Date value1, Date value2) {
			addCriterionForJDBCDate("MODIF_TIME between", value1, value2, "modifTime");
			return this;
		}

		public Criteria andModifTimeNotBetween(Date value1, Date value2) {
			addCriterionForJDBCDate("MODIF_TIME not between", value1, value2, "modifTime");
			return this;
		}

		public Criteria andModifUserIsNull() {
			addCriterion("MODIF_USER is null");
			return this;
		}

		public Criteria andModifUserIsNotNull() {
			addCriterion("MODIF_USER is not null");
			return this;
		}

		public Criteria andModifUserEqualTo(Integer value) {
			addCriterion("MODIF_USER =", value, "modifUser");
			return this;
		}

		public Criteria andModifUserNotEqualTo(Integer value) {
			addCriterion("MODIF_USER <>", value, "modifUser");
			return this;
		}

		public Criteria andModifUserGreaterThan(Integer value) {
			addCriterion("MODIF_USER >", value, "modifUser");
			return this;
		}

		public Criteria andModifUserGreaterThanOrEqualTo(Integer value) {
			addCriterion("MODIF_USER >=", value, "modifUser");
			return this;
		}

		public Criteria andModifUserLessThan(Integer value) {
			addCriterion("MODIF_USER <", value, "modifUser");
			return this;
		}

		public Criteria andModifUserLessThanOrEqualTo(Integer value) {
			addCriterion("MODIF_USER <=", value, "modifUser");
			return this;
		}

		public Criteria andModifUserIn(List values) {
			addCriterion("MODIF_USER in", values, "modifUser");
			return this;
		}

		public Criteria andModifUserNotIn(List values) {
			addCriterion("MODIF_USER not in", values, "modifUser");
			return this;
		}

		public Criteria andModifUserBetween(Integer value1, Integer value2) {
			addCriterion("MODIF_USER between", value1, value2, "modifUser");
			return this;
		}

		public Criteria andModifUserNotBetween(Integer value1, Integer value2) {
			addCriterion("MODIF_USER not between", value1, value2, "modifUser");
			return this;
		}
	}
}