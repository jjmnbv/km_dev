package com.pltfm.cms.vobject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class CmsStylesExample {
    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database table CMS_STYLES
     *
     * @ibatorgenerated Fri Aug 22 10:49:25 CST 2014
     */
    protected String orderByClause;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database table CMS_STYLES
     *
     * @ibatorgenerated Fri Aug 22 10:49:25 CST 2014
     */
    protected List oredCriteria;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table CMS_STYLES
     *
     * @ibatorgenerated Fri Aug 22 10:49:25 CST 2014
     */
    public CmsStylesExample() {
        oredCriteria = new ArrayList();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table CMS_STYLES
     *
     * @ibatorgenerated Fri Aug 22 10:49:25 CST 2014
     */
    protected CmsStylesExample(CmsStylesExample example) {
        this.orderByClause = example.orderByClause;
        this.oredCriteria = example.oredCriteria;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table CMS_STYLES
     *
     * @ibatorgenerated Fri Aug 22 10:49:25 CST 2014
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table CMS_STYLES
     *
     * @ibatorgenerated Fri Aug 22 10:49:25 CST 2014
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table CMS_STYLES
     *
     * @ibatorgenerated Fri Aug 22 10:49:25 CST 2014
     */
    public List getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table CMS_STYLES
     *
     * @ibatorgenerated Fri Aug 22 10:49:25 CST 2014
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table CMS_STYLES
     *
     * @ibatorgenerated Fri Aug 22 10:49:25 CST 2014
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
     * This method corresponds to the database table CMS_STYLES
     *
     * @ibatorgenerated Fri Aug 22 10:49:25 CST 2014
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table CMS_STYLES
     *
     * @ibatorgenerated Fri Aug 22 10:49:25 CST 2014
     */
    public void clear() {
        oredCriteria.clear();
    }

    /**
     * This class was generated by Apache iBATIS ibator.
     * This class corresponds to the database table CMS_STYLES
     *
     * @ibatorgenerated Fri Aug 22 10:49:25 CST 2014
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

        public Criteria andStylesIdIsNull() {
            addCriterion("STYLES_ID is null");
            return this;
        }

        public Criteria andStylesIdIsNotNull() {
            addCriterion("STYLES_ID is not null");
            return this;
        }

        public Criteria andStylesIdEqualTo(Integer value) {
            addCriterion("STYLES_ID =", value, "stylesId");
            return this;
        }

        public Criteria andStylesIdNotEqualTo(Integer value) {
            addCriterion("STYLES_ID <>", value, "stylesId");
            return this;
        }

        public Criteria andStylesIdGreaterThan(Integer value) {
            addCriterion("STYLES_ID >", value, "stylesId");
            return this;
        }

        public Criteria andStylesIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("STYLES_ID >=", value, "stylesId");
            return this;
        }

        public Criteria andStylesIdLessThan(Integer value) {
            addCriterion("STYLES_ID <", value, "stylesId");
            return this;
        }

        public Criteria andStylesIdLessThanOrEqualTo(Integer value) {
            addCriterion("STYLES_ID <=", value, "stylesId");
            return this;
        }

        public Criteria andStylesIdIn(List values) {
            addCriterion("STYLES_ID in", values, "stylesId");
            return this;
        }

        public Criteria andStylesIdNotIn(List values) {
            addCriterion("STYLES_ID not in", values, "stylesId");
            return this;
        }

        public Criteria andStylesIdBetween(Integer value1, Integer value2) {
            addCriterion("STYLES_ID between", value1, value2, "stylesId");
            return this;
        }

        public Criteria andStylesIdNotBetween(Integer value1, Integer value2) {
            addCriterion("STYLES_ID not between", value1, value2, "stylesId");
            return this;
        }

        public Criteria andTemplateIdIsNull() {
            addCriterion("TEMPLATE_ID is null");
            return this;
        }

        public Criteria andTemplateIdIsNotNull() {
            addCriterion("TEMPLATE_ID is not null");
            return this;
        }

        public Criteria andTemplateIdEqualTo(Integer value) {
            addCriterion("TEMPLATE_ID =", value, "templateId");
            return this;
        }

        public Criteria andTemplateIdNotEqualTo(Integer value) {
            addCriterion("TEMPLATE_ID <>", value, "templateId");
            return this;
        }

        public Criteria andTemplateIdGreaterThan(Integer value) {
            addCriterion("TEMPLATE_ID >", value, "templateId");
            return this;
        }

        public Criteria andTemplateIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("TEMPLATE_ID >=", value, "templateId");
            return this;
        }

        public Criteria andTemplateIdLessThan(Integer value) {
            addCriterion("TEMPLATE_ID <", value, "templateId");
            return this;
        }

        public Criteria andTemplateIdLessThanOrEqualTo(Integer value) {
            addCriterion("TEMPLATE_ID <=", value, "templateId");
            return this;
        }

        public Criteria andTemplateIdIn(List values) {
            addCriterion("TEMPLATE_ID in", values, "templateId");
            return this;
        }

        public Criteria andTemplateIdNotIn(List values) {
            addCriterion("TEMPLATE_ID not in", values, "templateId");
            return this;
        }

        public Criteria andTemplateIdBetween(Integer value1, Integer value2) {
            addCriterion("TEMPLATE_ID between", value1, value2, "templateId");
            return this;
        }

        public Criteria andTemplateIdNotBetween(Integer value1, Integer value2) {
            addCriterion("TEMPLATE_ID not between", value1, value2, "templateId");
            return this;
        }

        public Criteria andStylesNameIsNull() {
            addCriterion("STYLES_NAME is null");
            return this;
        }

        public Criteria andStylesNameIsNotNull() {
            addCriterion("STYLES_NAME is not null");
            return this;
        }

        public Criteria andStylesNameEqualTo(String value) {
            addCriterion("STYLES_NAME =", value, "stylesName");
            return this;
        }

        public Criteria andStylesNameNotEqualTo(String value) {
            addCriterion("STYLES_NAME <>", value, "stylesName");
            return this;
        }

        public Criteria andStylesNameGreaterThan(String value) {
            addCriterion("STYLES_NAME >", value, "stylesName");
            return this;
        }

        public Criteria andStylesNameGreaterThanOrEqualTo(String value) {
            addCriterion("STYLES_NAME >=", value, "stylesName");
            return this;
        }

        public Criteria andStylesNameLessThan(String value) {
            addCriterion("STYLES_NAME <", value, "stylesName");
            return this;
        }

        public Criteria andStylesNameLessThanOrEqualTo(String value) {
            addCriterion("STYLES_NAME <=", value, "stylesName");
            return this;
        }

        public Criteria andStylesNameLike(String value) {
            addCriterion("STYLES_NAME like", value, "stylesName");
            return this;
        }

        public Criteria andStylesNameNotLike(String value) {
            addCriterion("STYLES_NAME not like", value, "stylesName");
            return this;
        }

        public Criteria andStylesNameIn(List values) {
            addCriterion("STYLES_NAME in", values, "stylesName");
            return this;
        }

        public Criteria andStylesNameNotIn(List values) {
            addCriterion("STYLES_NAME not in", values, "stylesName");
            return this;
        }

        public Criteria andStylesNameBetween(String value1, String value2) {
            addCriterion("STYLES_NAME between", value1, value2, "stylesName");
            return this;
        }

        public Criteria andStylesNameNotBetween(String value1, String value2) {
            addCriterion("STYLES_NAME not between", value1, value2, "stylesName");
            return this;
        }

        public Criteria andStylesDescribeIsNull() {
            addCriterion("STYLES_DESCRIBE is null");
            return this;
        }

        public Criteria andStylesDescribeIsNotNull() {
            addCriterion("STYLES_DESCRIBE is not null");
            return this;
        }

        public Criteria andStylesDescribeEqualTo(Integer value) {
            addCriterion("STYLES_DESCRIBE =", value, "stylesDescribe");
            return this;
        }

        public Criteria andStylesDescribeNotEqualTo(Integer value) {
            addCriterion("STYLES_DESCRIBE <>", value, "stylesDescribe");
            return this;
        }

        public Criteria andStylesDescribeGreaterThan(Integer value) {
            addCriterion("STYLES_DESCRIBE >", value, "stylesDescribe");
            return this;
        }

        public Criteria andStylesDescribeGreaterThanOrEqualTo(Integer value) {
            addCriterion("STYLES_DESCRIBE >=", value, "stylesDescribe");
            return this;
        }

        public Criteria andStylesDescribeLessThan(Integer value) {
            addCriterion("STYLES_DESCRIBE <", value, "stylesDescribe");
            return this;
        }

        public Criteria andStylesDescribeLessThanOrEqualTo(Integer value) {
            addCriterion("STYLES_DESCRIBE <=", value, "stylesDescribe");
            return this;
        }

        public Criteria andStylesDescribeIn(List values) {
            addCriterion("STYLES_DESCRIBE in", values, "stylesDescribe");
            return this;
        }

        public Criteria andStylesDescribeNotIn(List values) {
            addCriterion("STYLES_DESCRIBE not in", values, "stylesDescribe");
            return this;
        }

        public Criteria andStylesDescribeBetween(Integer value1, Integer value2) {
            addCriterion("STYLES_DESCRIBE between", value1, value2, "stylesDescribe");
            return this;
        }

        public Criteria andStylesDescribeNotBetween(Integer value1, Integer value2) {
            addCriterion("STYLES_DESCRIBE not between", value1, value2, "stylesDescribe");
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

        public Criteria andSiteIdEqualTo(Integer value) {
            addCriterion("SITE_ID =", value, "siteId");
            return this;
        }

        public Criteria andSiteIdNotEqualTo(Integer value) {
            addCriterion("SITE_ID <>", value, "siteId");
            return this;
        }

        public Criteria andSiteIdGreaterThan(Integer value) {
            addCriterion("SITE_ID >", value, "siteId");
            return this;
        }

        public Criteria andSiteIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("SITE_ID >=", value, "siteId");
            return this;
        }

        public Criteria andSiteIdLessThan(Integer value) {
            addCriterion("SITE_ID <", value, "siteId");
            return this;
        }

        public Criteria andSiteIdLessThanOrEqualTo(Integer value) {
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

        public Criteria andSiteIdBetween(Integer value1, Integer value2) {
            addCriterion("SITE_ID between", value1, value2, "siteId");
            return this;
        }

        public Criteria andSiteIdNotBetween(Integer value1, Integer value2) {
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

        public Criteria andCreatedEqualTo(Integer value) {
            addCriterion("CREATED =", value, "created");
            return this;
        }

        public Criteria andCreatedNotEqualTo(Integer value) {
            addCriterion("CREATED <>", value, "created");
            return this;
        }

        public Criteria andCreatedGreaterThan(Integer value) {
            addCriterion("CREATED >", value, "created");
            return this;
        }

        public Criteria andCreatedGreaterThanOrEqualTo(Integer value) {
            addCriterion("CREATED >=", value, "created");
            return this;
        }

        public Criteria andCreatedLessThan(Integer value) {
            addCriterion("CREATED <", value, "created");
            return this;
        }

        public Criteria andCreatedLessThanOrEqualTo(Integer value) {
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

        public Criteria andCreatedBetween(Integer value1, Integer value2) {
            addCriterion("CREATED between", value1, value2, "created");
            return this;
        }

        public Criteria andCreatedNotBetween(Integer value1, Integer value2) {
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

        public Criteria andModifiedEqualTo(Integer value) {
            addCriterion("MODIFIED =", value, "modified");
            return this;
        }

        public Criteria andModifiedNotEqualTo(Integer value) {
            addCriterion("MODIFIED <>", value, "modified");
            return this;
        }

        public Criteria andModifiedGreaterThan(Integer value) {
            addCriterion("MODIFIED >", value, "modified");
            return this;
        }

        public Criteria andModifiedGreaterThanOrEqualTo(Integer value) {
            addCriterion("MODIFIED >=", value, "modified");
            return this;
        }

        public Criteria andModifiedLessThan(Integer value) {
            addCriterion("MODIFIED <", value, "modified");
            return this;
        }

        public Criteria andModifiedLessThanOrEqualTo(Integer value) {
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

        public Criteria andModifiedBetween(Integer value1, Integer value2) {
            addCriterion("MODIFIED between", value1, value2, "modified");
            return this;
        }

        public Criteria andModifiedNotBetween(Integer value1, Integer value2) {
            addCriterion("MODIFIED not between", value1, value2, "modified");
            return this;
        }
    }
}