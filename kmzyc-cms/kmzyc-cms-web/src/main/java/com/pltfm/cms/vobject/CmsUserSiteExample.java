package com.pltfm.cms.vobject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class CmsUserSiteExample {
    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database table CMS_USER_SITE
     *
     * @ibatorgenerated Fri Nov 15 13:56:27 CST 2013
     */
    protected String orderByClause;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database table CMS_USER_SITE
     *
     * @ibatorgenerated Fri Nov 15 13:56:27 CST 2013
     */
    protected List oredCriteria;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table CMS_USER_SITE
     *
     * @ibatorgenerated Fri Nov 15 13:56:27 CST 2013
     */
    public CmsUserSiteExample() {
        oredCriteria = new ArrayList();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table CMS_USER_SITE
     *
     * @ibatorgenerated Fri Nov 15 13:56:27 CST 2013
     */
    protected CmsUserSiteExample(CmsUserSiteExample example) {
        this.orderByClause = example.orderByClause;
        this.oredCriteria = example.oredCriteria;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table CMS_USER_SITE
     *
     * @ibatorgenerated Fri Nov 15 13:56:27 CST 2013
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table CMS_USER_SITE
     *
     * @ibatorgenerated Fri Nov 15 13:56:27 CST 2013
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table CMS_USER_SITE
     *
     * @ibatorgenerated Fri Nov 15 13:56:27 CST 2013
     */
    public List getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table CMS_USER_SITE
     *
     * @ibatorgenerated Fri Nov 15 13:56:27 CST 2013
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table CMS_USER_SITE
     *
     * @ibatorgenerated Fri Nov 15 13:56:27 CST 2013
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
     * This method corresponds to the database table CMS_USER_SITE
     *
     * @ibatorgenerated Fri Nov 15 13:56:27 CST 2013
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table CMS_USER_SITE
     *
     * @ibatorgenerated Fri Nov 15 13:56:27 CST 2013
     */
    public void clear() {
        oredCriteria.clear();
    }

    /**
     * This class was generated by Apache iBATIS ibator.
     * This class corresponds to the database table CMS_USER_SITE
     *
     * @ibatorgenerated Fri Nov 15 13:56:27 CST 2013
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

        public Criteria andUserSiteIdIsNull() {
            addCriterion("USER_SITE_ID is null");
            return this;
        }

        public Criteria andUserSiteIdIsNotNull() {
            addCriterion("USER_SITE_ID is not null");
            return this;
        }

        public Criteria andUserSiteIdEqualTo(BigDecimal value) {
            addCriterion("USER_SITE_ID =", value, "userSiteId");
            return this;
        }

        public Criteria andUserSiteIdNotEqualTo(BigDecimal value) {
            addCriterion("USER_SITE_ID <>", value, "userSiteId");
            return this;
        }

        public Criteria andUserSiteIdGreaterThan(BigDecimal value) {
            addCriterion("USER_SITE_ID >", value, "userSiteId");
            return this;
        }

        public Criteria andUserSiteIdGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("USER_SITE_ID >=", value, "userSiteId");
            return this;
        }

        public Criteria andUserSiteIdLessThan(BigDecimal value) {
            addCriterion("USER_SITE_ID <", value, "userSiteId");
            return this;
        }

        public Criteria andUserSiteIdLessThanOrEqualTo(BigDecimal value) {
            addCriterion("USER_SITE_ID <=", value, "userSiteId");
            return this;
        }

        public Criteria andUserSiteIdIn(List values) {
            addCriterion("USER_SITE_ID in", values, "userSiteId");
            return this;
        }

        public Criteria andUserSiteIdNotIn(List values) {
            addCriterion("USER_SITE_ID not in", values, "userSiteId");
            return this;
        }

        public Criteria andUserSiteIdBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("USER_SITE_ID between", value1, value2, "userSiteId");
            return this;
        }

        public Criteria andUserSiteIdNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("USER_SITE_ID not between", value1, value2, "userSiteId");
            return this;
        }

        public Criteria andUserIdIsNull() {
            addCriterion("USER_ID is null");
            return this;
        }

        public Criteria andUserIdIsNotNull() {
            addCriterion("USER_ID is not null");
            return this;
        }

        public Criteria andUserIdEqualTo(BigDecimal value) {
            addCriterion("USER_ID =", value, "userId");
            return this;
        }

        public Criteria andUserIdNotEqualTo(BigDecimal value) {
            addCriterion("USER_ID <>", value, "userId");
            return this;
        }

        public Criteria andUserIdGreaterThan(BigDecimal value) {
            addCriterion("USER_ID >", value, "userId");
            return this;
        }

        public Criteria andUserIdGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("USER_ID >=", value, "userId");
            return this;
        }

        public Criteria andUserIdLessThan(BigDecimal value) {
            addCriterion("USER_ID <", value, "userId");
            return this;
        }

        public Criteria andUserIdLessThanOrEqualTo(BigDecimal value) {
            addCriterion("USER_ID <=", value, "userId");
            return this;
        }

        public Criteria andUserIdIn(List values) {
            addCriterion("USER_ID in", values, "userId");
            return this;
        }

        public Criteria andUserIdNotIn(List values) {
            addCriterion("USER_ID not in", values, "userId");
            return this;
        }

        public Criteria andUserIdBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("USER_ID between", value1, value2, "userId");
            return this;
        }

        public Criteria andUserIdNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("USER_ID not between", value1, value2, "userId");
            return this;
        }

        public Criteria andSiteIdIsNull() {
            addCriterion("SITE_ID is null");
            return this;
        }

        public Criteria andSiteIdIsNotNull() {
            addCriterion("SITE_ID is not null");
            return this;
        }

        public Criteria andSiteIdEqualTo(BigDecimal value) {
            addCriterion("SITE_ID =", value, "siteId");
            return this;
        }

        public Criteria andSiteIdNotEqualTo(BigDecimal value) {
            addCriterion("SITE_ID <>", value, "siteId");
            return this;
        }

        public Criteria andSiteIdGreaterThan(BigDecimal value) {
            addCriterion("SITE_ID >", value, "siteId");
            return this;
        }

        public Criteria andSiteIdGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("SITE_ID >=", value, "siteId");
            return this;
        }

        public Criteria andSiteIdLessThan(BigDecimal value) {
            addCriterion("SITE_ID <", value, "siteId");
            return this;
        }

        public Criteria andSiteIdLessThanOrEqualTo(BigDecimal value) {
            addCriterion("SITE_ID <=", value, "siteId");
            return this;
        }

        public Criteria andSiteIdIn(List values) {
            addCriterion("SITE_ID in", values, "siteId");
            return this;
        }

        public Criteria andSiteIdNotIn(List values) {
            addCriterion("SITE_ID not in", values, "siteId");
            return this;
        }

        public Criteria andSiteIdBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("SITE_ID between", value1, value2, "siteId");
            return this;
        }

        public Criteria andSiteIdNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("SITE_ID not between", value1, value2, "siteId");
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

        public Criteria andStatusIsNull() {
            addCriterion("STATUS is null");
            return this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("STATUS is not null");
            return this;
        }

        public Criteria andStatusEqualTo(Short value) {
            addCriterion("STATUS =", value, "status");
            return this;
        }

        public Criteria andStatusNotEqualTo(Short value) {
            addCriterion("STATUS <>", value, "status");
            return this;
        }

        public Criteria andStatusGreaterThan(Short value) {
            addCriterion("STATUS >", value, "status");
            return this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Short value) {
            addCriterion("STATUS >=", value, "status");
            return this;
        }

        public Criteria andStatusLessThan(Short value) {
            addCriterion("STATUS <", value, "status");
            return this;
        }

        public Criteria andStatusLessThanOrEqualTo(Short value) {
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

        public Criteria andStatusBetween(Short value1, Short value2) {
            addCriterion("STATUS between", value1, value2, "status");
            return this;
        }

        public Criteria andStatusNotBetween(Short value1, Short value2) {
            addCriterion("STATUS not between", value1, value2, "status");
            return this;
        }

        public Criteria andCreateDateIsNull() {
            addCriterion("CREATE_DATE is null");
            return this;
        }

        public Criteria andCreateDateIsNotNull() {
            addCriterion("CREATE_DATE is not null");
            return this;
        }

        public Criteria andCreateDateEqualTo(Date value) {
            addCriterionForJDBCDate("CREATE_DATE =", value, "createDate");
            return this;
        }

        public Criteria andCreateDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("CREATE_DATE <>", value, "createDate");
            return this;
        }

        public Criteria andCreateDateGreaterThan(Date value) {
            addCriterionForJDBCDate("CREATE_DATE >", value, "createDate");
            return this;
        }

        public Criteria andCreateDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("CREATE_DATE >=", value, "createDate");
            return this;
        }

        public Criteria andCreateDateLessThan(Date value) {
            addCriterionForJDBCDate("CREATE_DATE <", value, "createDate");
            return this;
        }

        public Criteria andCreateDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("CREATE_DATE <=", value, "createDate");
            return this;
        }

        public Criteria andCreateDateIn(List values) {
            addCriterionForJDBCDate("CREATE_DATE in", values, "createDate");
            return this;
        }

        public Criteria andCreateDateNotIn(List values) {
            addCriterionForJDBCDate("CREATE_DATE not in", values, "createDate");
            return this;
        }

        public Criteria andCreateDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("CREATE_DATE between", value1, value2, "createDate");
            return this;
        }

        public Criteria andCreateDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("CREATE_DATE not between", value1, value2, "createDate");
            return this;
        }

        public Criteria andCreatedIsNull() {
            addCriterion("CREATED is null");
            return this;
        }

        public Criteria andCreatedIsNotNull() {
            addCriterion("CREATED is not null");
            return this;
        }

        public Criteria andCreatedEqualTo(BigDecimal value) {
            addCriterion("CREATED =", value, "created");
            return this;
        }

        public Criteria andCreatedNotEqualTo(BigDecimal value) {
            addCriterion("CREATED <>", value, "created");
            return this;
        }

        public Criteria andCreatedGreaterThan(BigDecimal value) {
            addCriterion("CREATED >", value, "created");
            return this;
        }

        public Criteria andCreatedGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("CREATED >=", value, "created");
            return this;
        }

        public Criteria andCreatedLessThan(BigDecimal value) {
            addCriterion("CREATED <", value, "created");
            return this;
        }

        public Criteria andCreatedLessThanOrEqualTo(BigDecimal value) {
            addCriterion("CREATED <=", value, "created");
            return this;
        }

        public Criteria andCreatedIn(List values) {
            addCriterion("CREATED in", values, "created");
            return this;
        }

        public Criteria andCreatedNotIn(List values) {
            addCriterion("CREATED not in", values, "created");
            return this;
        }

        public Criteria andCreatedBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("CREATED between", value1, value2, "created");
            return this;
        }

        public Criteria andCreatedNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("CREATED not between", value1, value2, "created");
            return this;
        }

        public Criteria andModifyDateIsNull() {
            addCriterion("MODIFY_DATE is null");
            return this;
        }

        public Criteria andModifyDateIsNotNull() {
            addCriterion("MODIFY_DATE is not null");
            return this;
        }

        public Criteria andModifyDateEqualTo(Date value) {
            addCriterionForJDBCDate("MODIFY_DATE =", value, "modifyDate");
            return this;
        }

        public Criteria andModifyDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("MODIFY_DATE <>", value, "modifyDate");
            return this;
        }

        public Criteria andModifyDateGreaterThan(Date value) {
            addCriterionForJDBCDate("MODIFY_DATE >", value, "modifyDate");
            return this;
        }

        public Criteria andModifyDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("MODIFY_DATE >=", value, "modifyDate");
            return this;
        }

        public Criteria andModifyDateLessThan(Date value) {
            addCriterionForJDBCDate("MODIFY_DATE <", value, "modifyDate");
            return this;
        }

        public Criteria andModifyDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("MODIFY_DATE <=", value, "modifyDate");
            return this;
        }

        public Criteria andModifyDateIn(List values) {
            addCriterionForJDBCDate("MODIFY_DATE in", values, "modifyDate");
            return this;
        }

        public Criteria andModifyDateNotIn(List values) {
            addCriterionForJDBCDate("MODIFY_DATE not in", values, "modifyDate");
            return this;
        }

        public Criteria andModifyDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("MODIFY_DATE between", value1, value2, "modifyDate");
            return this;
        }

        public Criteria andModifyDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("MODIFY_DATE not between", value1, value2, "modifyDate");
            return this;
        }

        public Criteria andModifiedIsNull() {
            addCriterion("MODIFIED is null");
            return this;
        }

        public Criteria andModifiedIsNotNull() {
            addCriterion("MODIFIED is not null");
            return this;
        }

        public Criteria andModifiedEqualTo(BigDecimal value) {
            addCriterion("MODIFIED =", value, "modified");
            return this;
        }

        public Criteria andModifiedNotEqualTo(BigDecimal value) {
            addCriterion("MODIFIED <>", value, "modified");
            return this;
        }

        public Criteria andModifiedGreaterThan(BigDecimal value) {
            addCriterion("MODIFIED >", value, "modified");
            return this;
        }

        public Criteria andModifiedGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("MODIFIED >=", value, "modified");
            return this;
        }

        public Criteria andModifiedLessThan(BigDecimal value) {
            addCriterion("MODIFIED <", value, "modified");
            return this;
        }

        public Criteria andModifiedLessThanOrEqualTo(BigDecimal value) {
            addCriterion("MODIFIED <=", value, "modified");
            return this;
        }

        public Criteria andModifiedIn(List values) {
            addCriterion("MODIFIED in", values, "modified");
            return this;
        }

        public Criteria andModifiedNotIn(List values) {
            addCriterion("MODIFIED not in", values, "modified");
            return this;
        }

        public Criteria andModifiedBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("MODIFIED between", value1, value2, "modified");
            return this;
        }

        public Criteria andModifiedNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("MODIFIED not between", value1, value2, "modified");
            return this;
        }
    }
}