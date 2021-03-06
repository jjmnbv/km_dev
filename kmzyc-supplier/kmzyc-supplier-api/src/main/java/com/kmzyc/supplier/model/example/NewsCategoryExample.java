package com.kmzyc.supplier.model.example;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class NewsCategoryExample {
    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database table NEWS_CATEGORY
     *
     * @ibatorgenerated Thu Apr 10 11:28:54 CST 2014
     */
    protected String orderByClause;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database table NEWS_CATEGORY
     *
     * @ibatorgenerated Thu Apr 10 11:28:54 CST 2014
     */
    protected List oredCriteria;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table NEWS_CATEGORY
     *
     * @ibatorgenerated Thu Apr 10 11:28:54 CST 2014
     */
    public NewsCategoryExample() {
        oredCriteria = new ArrayList();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table NEWS_CATEGORY
     *
     * @ibatorgenerated Thu Apr 10 11:28:54 CST 2014
     */
    protected NewsCategoryExample(NewsCategoryExample example) {
        this.orderByClause = example.orderByClause;
        this.oredCriteria = example.oredCriteria;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table NEWS_CATEGORY
     *
     * @ibatorgenerated Thu Apr 10 11:28:54 CST 2014
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table NEWS_CATEGORY
     *
     * @ibatorgenerated Thu Apr 10 11:28:54 CST 2014
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table NEWS_CATEGORY
     *
     * @ibatorgenerated Thu Apr 10 11:28:54 CST 2014
     */
    public List getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table NEWS_CATEGORY
     *
     * @ibatorgenerated Thu Apr 10 11:28:54 CST 2014
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table NEWS_CATEGORY
     *
     * @ibatorgenerated Thu Apr 10 11:28:54 CST 2014
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table NEWS_CATEGORY
     *
     * @ibatorgenerated Thu Apr 10 11:28:54 CST 2014
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table NEWS_CATEGORY
     *
     * @ibatorgenerated Thu Apr 10 11:28:54 CST 2014
     */
    public void clear() {
        oredCriteria.clear();
    }

    /**
     * This class was generated by Apache iBATIS ibator.
     * This class corresponds to the database table NEWS_CATEGORY
     *
     * @ibatorgenerated Thu Apr 10 11:28:54 CST 2014
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
                dateList.add(new java.sql.Date(((Date)iter.next()).getTime()));
            }
            addCriterion(condition, dateList, property);
        }

        protected void addCriterionForJDBCDate(String condition, Date value1, Date value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value1.getTime()), new java.sql.Date(value2.getTime()), property);
        }

        public Criteria andNewsCategoryIdIsNull() {
            addCriterion("NEWS_CATEGORY_ID is null");
            return this;
        }

        public Criteria andNewsCategoryIdIsNotNull() {
            addCriterion("NEWS_CATEGORY_ID is not null");
            return this;
        }

        public Criteria andNewsCategoryIdEqualTo(Long value) {
            addCriterion("NEWS_CATEGORY_ID =", value, "newsCategoryId");
            return this;
        }

        public Criteria andNewsCategoryIdNotEqualTo(Long value) {
            addCriterion("NEWS_CATEGORY_ID <>", value, "newsCategoryId");
            return this;
        }

        public Criteria andNewsCategoryIdGreaterThan(Long value) {
            addCriterion("NEWS_CATEGORY_ID >", value, "newsCategoryId");
            return this;
        }

        public Criteria andNewsCategoryIdGreaterThanOrEqualTo(Long value) {
            addCriterion("NEWS_CATEGORY_ID >=", value, "newsCategoryId");
            return this;
        }

        public Criteria andNewsCategoryIdLessThan(Long value) {
            addCriterion("NEWS_CATEGORY_ID <", value, "newsCategoryId");
            return this;
        }

        public Criteria andNewsCategoryIdLessThanOrEqualTo(Long value) {
            addCriterion("NEWS_CATEGORY_ID <=", value, "newsCategoryId");
            return this;
        }

        public Criteria andNewsCategoryIdIn(List values) {
            addCriterion("NEWS_CATEGORY_ID in", values, "newsCategoryId");
            return this;
        }

        public Criteria andNewsCategoryIdNotIn(List values) {
            addCriterion("NEWS_CATEGORY_ID not in", values, "newsCategoryId");
            return this;
        }

        public Criteria andNewsCategoryIdBetween(Long value1, Long value2) {
            addCriterion("NEWS_CATEGORY_ID between", value1, value2, "newsCategoryId");
            return this;
        }

        public Criteria andNewsCategoryIdNotBetween(Long value1, Long value2) {
            addCriterion("NEWS_CATEGORY_ID not between", value1, value2, "newsCategoryId");
            return this;
        }

        public Criteria andSupplierIdIsNull() {
            addCriterion("SUPPLIER_ID is null");
            return this;
        }

        public Criteria andSupplierIdIsNotNull() {
            addCriterion("SUPPLIER_ID is not null");
            return this;
        }

        public Criteria andSupplierIdEqualTo(Long value) {
            addCriterion("SUPPLIER_ID =", value, "supplierId");
            return this;
        }

        public Criteria andSupplierIdNotEqualTo(Long value) {
            addCriterion("SUPPLIER_ID <>", value, "supplierId");
            return this;
        }

        public Criteria andSupplierIdGreaterThan(Long value) {
            addCriterion("SUPPLIER_ID >", value, "supplierId");
            return this;
        }

        public Criteria andSupplierIdGreaterThanOrEqualTo(Long value) {
            addCriterion("SUPPLIER_ID >=", value, "supplierId");
            return this;
        }

        public Criteria andSupplierIdLessThan(Long value) {
            addCriterion("SUPPLIER_ID <", value, "supplierId");
            return this;
        }

        public Criteria andSupplierIdLessThanOrEqualTo(Long value) {
            addCriterion("SUPPLIER_ID <=", value, "supplierId");
            return this;
        }

        public Criteria andSupplierIdIn(List values) {
            addCriterion("SUPPLIER_ID in", values, "supplierId");
            return this;
        }

        public Criteria andSupplierIdNotIn(List values) {
            addCriterion("SUPPLIER_ID not in", values, "supplierId");
            return this;
        }

        public Criteria andSupplierIdBetween(Long value1, Long value2) {
            addCriterion("SUPPLIER_ID between", value1, value2, "supplierId");
            return this;
        }

        public Criteria andSupplierIdNotBetween(Long value1, Long value2) {
            addCriterion("SUPPLIER_ID not between", value1, value2, "supplierId");
            return this;
        }

        public Criteria andNewsCategoryNameIsNull() {
            addCriterion("NEWS_CATEGORY_NAME is null");
            return this;
        }

        public Criteria andNewsCategoryNameIsNotNull() {
            addCriterion("NEWS_CATEGORY_NAME is not null");
            return this;
        }

        public Criteria andNewsCategoryNameEqualTo(String value) {
            addCriterion("NEWS_CATEGORY_NAME =", value, "newsCategoryName");
            return this;
        }

        public Criteria andNewsCategoryNameNotEqualTo(String value) {
            addCriterion("NEWS_CATEGORY_NAME <>", value, "newsCategoryName");
            return this;
        }

        public Criteria andNewsCategoryNameGreaterThan(String value) {
            addCriterion("NEWS_CATEGORY_NAME >", value, "newsCategoryName");
            return this;
        }

        public Criteria andNewsCategoryNameGreaterThanOrEqualTo(String value) {
            addCriterion("NEWS_CATEGORY_NAME >=", value, "newsCategoryName");
            return this;
        }

        public Criteria andNewsCategoryNameLessThan(String value) {
            addCriterion("NEWS_CATEGORY_NAME <", value, "newsCategoryName");
            return this;
        }

        public Criteria andNewsCategoryNameLessThanOrEqualTo(String value) {
            addCriterion("NEWS_CATEGORY_NAME <=", value, "newsCategoryName");
            return this;
        }

        public Criteria andNewsCategoryNameLike(String value) {
            addCriterion("NEWS_CATEGORY_NAME like", value, "newsCategoryName");
            return this;
        }

        public Criteria andNewsCategoryNameNotLike(String value) {
            addCriterion("NEWS_CATEGORY_NAME not like", value, "newsCategoryName");
            return this;
        }

        public Criteria andNewsCategoryNameIn(List values) {
            addCriterion("NEWS_CATEGORY_NAME in", values, "newsCategoryName");
            return this;
        }

        public Criteria andNewsCategoryNameNotIn(List values) {
            addCriterion("NEWS_CATEGORY_NAME not in", values, "newsCategoryName");
            return this;
        }

        public Criteria andNewsCategoryNameBetween(String value1, String value2) {
            addCriterion("NEWS_CATEGORY_NAME between", value1, value2, "newsCategoryName");
            return this;
        }

        public Criteria andNewsCategoryNameNotBetween(String value1, String value2) {
            addCriterion("NEWS_CATEGORY_NAME not between", value1, value2, "newsCategoryName");
            return this;
        }

        public Criteria andParentIdIsNull() {
            addCriterion("PARENT_ID is null");
            return this;
        }

        public Criteria andParentIdIsNotNull() {
            addCriterion("PARENT_ID is not null");
            return this;
        }

        public Criteria andParentIdEqualTo(Long value) {
            addCriterion("PARENT_ID =", value, "parentId");
            return this;
        }

        public Criteria andParentIdNotEqualTo(Long value) {
            addCriterion("PARENT_ID <>", value, "parentId");
            return this;
        }

        public Criteria andParentIdGreaterThan(Long value) {
            addCriterion("PARENT_ID >", value, "parentId");
            return this;
        }

        public Criteria andParentIdGreaterThanOrEqualTo(Long value) {
            addCriterion("PARENT_ID >=", value, "parentId");
            return this;
        }

        public Criteria andParentIdLessThan(Long value) {
            addCriterion("PARENT_ID <", value, "parentId");
            return this;
        }

        public Criteria andParentIdLessThanOrEqualTo(Long value) {
            addCriterion("PARENT_ID <=", value, "parentId");
            return this;
        }

        public Criteria andParentIdIn(List values) {
            addCriterion("PARENT_ID in", values, "parentId");
            return this;
        }

        public Criteria andParentIdNotIn(List values) {
            addCriterion("PARENT_ID not in", values, "parentId");
            return this;
        }

        public Criteria andParentIdBetween(Long value1, Long value2) {
            addCriterion("PARENT_ID between", value1, value2, "parentId");
            return this;
        }

        public Criteria andParentIdNotBetween(Long value1, Long value2) {
            addCriterion("PARENT_ID not between", value1, value2, "parentId");
            return this;
        }

        public Criteria andSortNoIsNull() {
            addCriterion("SORT_NO is null");
            return this;
        }

        public Criteria andSortNoIsNotNull() {
            addCriterion("SORT_NO is not null");
            return this;
        }

        public Criteria andSortNoEqualTo(String value) {
            addCriterion("SORT_NO =", value, "sortNo");
            return this;
        }

        public Criteria andSortNoNotEqualTo(String value) {
            addCriterion("SORT_NO <>", value, "sortNo");
            return this;
        }

        public Criteria andSortNoGreaterThan(String value) {
            addCriterion("SORT_NO >", value, "sortNo");
            return this;
        }

        public Criteria andSortNoGreaterThanOrEqualTo(String value) {
            addCriterion("SORT_NO >=", value, "sortNo");
            return this;
        }

        public Criteria andSortNoLessThan(String value) {
            addCriterion("SORT_NO <", value, "sortNo");
            return this;
        }

        public Criteria andSortNoLessThanOrEqualTo(String value) {
            addCriterion("SORT_NO <=", value, "sortNo");
            return this;
        }

        public Criteria andSortNoLike(String value) {
            addCriterion("SORT_NO like", value, "sortNo");
            return this;
        }

        public Criteria andSortNoNotLike(String value) {
            addCriterion("SORT_NO not like", value, "sortNo");
            return this;
        }

        public Criteria andSortNoIn(List values) {
            addCriterion("SORT_NO in", values, "sortNo");
            return this;
        }

        public Criteria andSortNoNotIn(List values) {
            addCriterion("SORT_NO not in", values, "sortNo");
            return this;
        }

        public Criteria andSortNoBetween(String value1, String value2) {
            addCriterion("SORT_NO between", value1, value2, "sortNo");
            return this;
        }

        public Criteria andSortNoNotBetween(String value1, String value2) {
            addCriterion("SORT_NO not between", value1, value2, "sortNo");
            return this;
        }

        public Criteria andRemarkIsNull() {
            addCriterion("REMARK is null");
            return this;
        }

        public Criteria andRemarkIsNotNull() {
            addCriterion("REMARK is not null");
            return this;
        }

        public Criteria andRemarkEqualTo(String value) {
            addCriterion("REMARK =", value, "remark");
            return this;
        }

        public Criteria andRemarkNotEqualTo(String value) {
            addCriterion("REMARK <>", value, "remark");
            return this;
        }

        public Criteria andRemarkGreaterThan(String value) {
            addCriterion("REMARK >", value, "remark");
            return this;
        }

        public Criteria andRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("REMARK >=", value, "remark");
            return this;
        }

        public Criteria andRemarkLessThan(String value) {
            addCriterion("REMARK <", value, "remark");
            return this;
        }

        public Criteria andRemarkLessThanOrEqualTo(String value) {
            addCriterion("REMARK <=", value, "remark");
            return this;
        }

        public Criteria andRemarkLike(String value) {
            addCriterion("REMARK like", value, "remark");
            return this;
        }

        public Criteria andRemarkNotLike(String value) {
            addCriterion("REMARK not like", value, "remark");
            return this;
        }

        public Criteria andRemarkIn(List values) {
            addCriterion("REMARK in", values, "remark");
            return this;
        }

        public Criteria andRemarkNotIn(List values) {
            addCriterion("REMARK not in", values, "remark");
            return this;
        }

        public Criteria andRemarkBetween(String value1, String value2) {
            addCriterion("REMARK between", value1, value2, "remark");
            return this;
        }

        public Criteria andRemarkNotBetween(String value1, String value2) {
            addCriterion("REMARK not between", value1, value2, "remark");
            return this;
        }

        public Criteria andCreateUserIsNull() {
            addCriterion("CREATE_USER is null");
            return this;
        }

        public Criteria andCreateUserIsNotNull() {
            addCriterion("CREATE_USER is not null");
            return this;
        }

        public Criteria andCreateUserEqualTo(Long value) {
            addCriterion("CREATE_USER =", value, "createUser");
            return this;
        }

        public Criteria andCreateUserNotEqualTo(Long value) {
            addCriterion("CREATE_USER <>", value, "createUser");
            return this;
        }

        public Criteria andCreateUserGreaterThan(Long value) {
            addCriterion("CREATE_USER >", value, "createUser");
            return this;
        }

        public Criteria andCreateUserGreaterThanOrEqualTo(Long value) {
            addCriterion("CREATE_USER >=", value, "createUser");
            return this;
        }

        public Criteria andCreateUserLessThan(Long value) {
            addCriterion("CREATE_USER <", value, "createUser");
            return this;
        }

        public Criteria andCreateUserLessThanOrEqualTo(Long value) {
            addCriterion("CREATE_USER <=", value, "createUser");
            return this;
        }

        public Criteria andCreateUserIn(List values) {
            addCriterion("CREATE_USER in", values, "createUser");
            return this;
        }

        public Criteria andCreateUserNotIn(List values) {
            addCriterion("CREATE_USER not in", values, "createUser");
            return this;
        }

        public Criteria andCreateUserBetween(Long value1, Long value2) {
            addCriterion("CREATE_USER between", value1, value2, "createUser");
            return this;
        }

        public Criteria andCreateUserNotBetween(Long value1, Long value2) {
            addCriterion("CREATE_USER not between", value1, value2, "createUser");
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
    }
}