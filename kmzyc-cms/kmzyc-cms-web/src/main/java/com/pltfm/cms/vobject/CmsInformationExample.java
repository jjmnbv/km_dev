package com.pltfm.cms.vobject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class CmsInformationExample {
    /**
     * This field was generated by Abator for iBATIS.
     * This field corresponds to the database table CMS_INFORMATION
     *
     * @abatorgenerated Wed Sep 11 09:42:35 CST 2013
     */
    protected String orderByClause;

    /**
     * This field was generated by Abator for iBATIS.
     * This field corresponds to the database table CMS_INFORMATION
     *
     * @abatorgenerated Wed Sep 11 09:42:35 CST 2013
     */
    protected List oredCriteria;

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table CMS_INFORMATION
     *
     * @abatorgenerated Wed Sep 11 09:42:35 CST 2013
     */
    public CmsInformationExample() {
        oredCriteria = new ArrayList();
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table CMS_INFORMATION
     *
     * @abatorgenerated Wed Sep 11 09:42:35 CST 2013
     */
    protected CmsInformationExample(CmsInformationExample example) {
        this.orderByClause = example.orderByClause;
        this.oredCriteria = example.oredCriteria;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table CMS_INFORMATION
     *
     * @abatorgenerated Wed Sep 11 09:42:35 CST 2013
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table CMS_INFORMATION
     *
     * @abatorgenerated Wed Sep 11 09:42:35 CST 2013
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table CMS_INFORMATION
     *
     * @abatorgenerated Wed Sep 11 09:42:35 CST 2013
     */
    public List getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table CMS_INFORMATION
     *
     * @abatorgenerated Wed Sep 11 09:42:35 CST 2013
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table CMS_INFORMATION
     *
     * @abatorgenerated Wed Sep 11 09:42:35 CST 2013
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table CMS_INFORMATION
     *
     * @abatorgenerated Wed Sep 11 09:42:35 CST 2013
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table CMS_INFORMATION
     *
     * @abatorgenerated Wed Sep 11 09:42:35 CST 2013
     */
    public void clear() {
        oredCriteria.clear();
    }

    /**
     * This class was generated by Abator for iBATIS.
     * This class corresponds to the database table CMS_INFORMATION
     *
     * @abatorgenerated Wed Sep 11 09:42:35 CST 2013
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

        public Criteria andInforIdIsNull() {
            addCriterion("INFOR_ID is null");
            return this;
        }

        public Criteria andInforIdIsNotNull() {
            addCriterion("INFOR_ID is not null");
            return this;
        }

        public Criteria andInforIdEqualTo(BigDecimal value) {
            addCriterion("INFOR_ID =", value, "inforId");
            return this;
        }

        public Criteria andInforIdNotEqualTo(BigDecimal value) {
            addCriterion("INFOR_ID <>", value, "inforId");
            return this;
        }

        public Criteria andInforIdGreaterThan(BigDecimal value) {
            addCriterion("INFOR_ID >", value, "inforId");
            return this;
        }

        public Criteria andInforIdGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("INFOR_ID >=", value, "inforId");
            return this;
        }

        public Criteria andInforIdLessThan(BigDecimal value) {
            addCriterion("INFOR_ID <", value, "inforId");
            return this;
        }

        public Criteria andInforIdLessThanOrEqualTo(BigDecimal value) {
            addCriterion("INFOR_ID <=", value, "inforId");
            return this;
        }

        public Criteria andInforIdIn(List values) {
            addCriterion("INFOR_ID in", values, "inforId");
            return this;
        }

        public Criteria andInforIdNotIn(List values) {
            addCriterion("INFOR_ID not in", values, "inforId");
            return this;
        }

        public Criteria andInforIdBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("INFOR_ID between", value1, value2, "inforId");
            return this;
        }

        public Criteria andInforIdNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("INFOR_ID not between", value1, value2, "inforId");
            return this;
        }

        public Criteria andTypeIdIsNull() {
            addCriterion("TYPE_ID is null");
            return this;
        }

        public Criteria andTypeIdIsNotNull() {
            addCriterion("TYPE_ID is not null");
            return this;
        }

        public Criteria andTypeIdEqualTo(BigDecimal value) {
            addCriterion("TYPE_ID =", value, "typeId");
            return this;
        }

        public Criteria andTypeIdNotEqualTo(BigDecimal value) {
            addCriterion("TYPE_ID <>", value, "typeId");
            return this;
        }

        public Criteria andTypeIdGreaterThan(BigDecimal value) {
            addCriterion("TYPE_ID >", value, "typeId");
            return this;
        }

        public Criteria andTypeIdGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("TYPE_ID >=", value, "typeId");
            return this;
        }

        public Criteria andTypeIdLessThan(BigDecimal value) {
            addCriterion("TYPE_ID <", value, "typeId");
            return this;
        }

        public Criteria andTypeIdLessThanOrEqualTo(BigDecimal value) {
            addCriterion("TYPE_ID <=", value, "typeId");
            return this;
        }

        public Criteria andTypeIdIn(List values) {
            addCriterion("TYPE_ID in", values, "typeId");
            return this;
        }

        public Criteria andTypeIdNotIn(List values) {
            addCriterion("TYPE_ID not in", values, "typeId");
            return this;
        }

        public Criteria andTypeIdBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("TYPE_ID between", value1, value2, "typeId");
            return this;
        }

        public Criteria andTypeIdNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("TYPE_ID not between", value1, value2, "typeId");
            return this;
        }

        public Criteria andNameIsNull() {
            addCriterion("NAME is null");
            return this;
        }

        public Criteria andNameIsNotNull() {
            addCriterion("NAME is not null");
            return this;
        }

        public Criteria andNameEqualTo(String value) {
            addCriterion("NAME =", value, "name");
            return this;
        }

        public Criteria andNameNotEqualTo(String value) {
            addCriterion("NAME <>", value, "name");
            return this;
        }

        public Criteria andNameGreaterThan(String value) {
            addCriterion("NAME >", value, "name");
            return this;
        }

        public Criteria andNameGreaterThanOrEqualTo(String value) {
            addCriterion("NAME >=", value, "name");
            return this;
        }

        public Criteria andNameLessThan(String value) {
            addCriterion("NAME <", value, "name");
            return this;
        }

        public Criteria andNameLessThanOrEqualTo(String value) {
            addCriterion("NAME <=", value, "name");
            return this;
        }

        public Criteria andNameLike(String value) {
            addCriterion("NAME like", value, "name");
            return this;
        }

        public Criteria andNameNotLike(String value) {
            addCriterion("NAME not like", value, "name");
            return this;
        }

        public Criteria andNameIn(List values) {
            addCriterion("NAME in", values, "name");
            return this;
        }

        public Criteria andNameNotIn(List values) {
            addCriterion("NAME not in", values, "name");
            return this;
        }

        public Criteria andNameBetween(String value1, String value2) {
            addCriterion("NAME between", value1, value2, "name");
            return this;
        }

        public Criteria andNameNotBetween(String value1, String value2) {
            addCriterion("NAME not between", value1, value2, "name");
            return this;
        }

        public Criteria andContentIsNull() {
            addCriterion("CONTENT is null");
            return this;
        }

        public Criteria andContentIsNotNull() {
            addCriterion("CONTENT is not null");
            return this;
        }

        public Criteria andContentEqualTo(String value) {
            addCriterion("CONTENT =", value, "content");
            return this;
        }

        public Criteria andContentNotEqualTo(String value) {
            addCriterion("CONTENT <>", value, "content");
            return this;
        }

        public Criteria andContentGreaterThan(String value) {
            addCriterion("CONTENT >", value, "content");
            return this;
        }

        public Criteria andContentGreaterThanOrEqualTo(String value) {
            addCriterion("CONTENT >=", value, "content");
            return this;
        }

        public Criteria andContentLessThan(String value) {
            addCriterion("CONTENT <", value, "content");
            return this;
        }

        public Criteria andContentLessThanOrEqualTo(String value) {
            addCriterion("CONTENT <=", value, "content");
            return this;
        }

        public Criteria andContentLike(String value) {
            addCriterion("CONTENT like", value, "content");
            return this;
        }

        public Criteria andContentNotLike(String value) {
            addCriterion("CONTENT not like", value, "content");
            return this;
        }

        public Criteria andContentIn(List values) {
            addCriterion("CONTENT in", values, "content");
            return this;
        }

        public Criteria andContentNotIn(List values) {
            addCriterion("CONTENT not in", values, "content");
            return this;
        }

        public Criteria andContentBetween(String value1, String value2) {
            addCriterion("CONTENT between", value1, value2, "content");
            return this;
        }

        public Criteria andContentNotBetween(String value1, String value2) {
            addCriterion("CONTENT not between", value1, value2, "content");
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

        public Criteria andKeyIsNull() {
            addCriterion("KEY is null");
            return this;
        }

        public Criteria andKeyIsNotNull() {
            addCriterion("KEY is not null");
            return this;
        }

        public Criteria andKeyEqualTo(String value) {
            addCriterion("KEY =", value, "key");
            return this;
        }

        public Criteria andKeyNotEqualTo(String value) {
            addCriterion("KEY <>", value, "key");
            return this;
        }

        public Criteria andKeyGreaterThan(String value) {
            addCriterion("KEY >", value, "key");
            return this;
        }

        public Criteria andKeyGreaterThanOrEqualTo(String value) {
            addCriterion("KEY >=", value, "key");
            return this;
        }

        public Criteria andKeyLessThan(String value) {
            addCriterion("KEY <", value, "key");
            return this;
        }

        public Criteria andKeyLessThanOrEqualTo(String value) {
            addCriterion("KEY <=", value, "key");
            return this;
        }

        public Criteria andKeyLike(String value) {
            addCriterion("KEY like", value, "key");
            return this;
        }

        public Criteria andKeyNotLike(String value) {
            addCriterion("KEY not like", value, "key");
            return this;
        }

        public Criteria andKeyIn(List values) {
            addCriterion("KEY in", values, "key");
            return this;
        }

        public Criteria andKeyNotIn(List values) {
            addCriterion("KEY not in", values, "key");
            return this;
        }

        public Criteria andKeyBetween(String value1, String value2) {
            addCriterion("KEY between", value1, value2, "key");
            return this;
        }

        public Criteria andKeyNotBetween(String value1, String value2) {
            addCriterion("KEY not between", value1, value2, "key");
            return this;
        }

        public Criteria andDescriptionIsNull() {
            addCriterion("DESCRIPTION is null");
            return this;
        }

        public Criteria andDescriptionIsNotNull() {
            addCriterion("DESCRIPTION is not null");
            return this;
        }

        public Criteria andDescriptionEqualTo(String value) {
            addCriterion("DESCRIPTION =", value, "description");
            return this;
        }

        public Criteria andDescriptionNotEqualTo(String value) {
            addCriterion("DESCRIPTION <>", value, "description");
            return this;
        }

        public Criteria andDescriptionGreaterThan(String value) {
            addCriterion("DESCRIPTION >", value, "description");
            return this;
        }

        public Criteria andDescriptionGreaterThanOrEqualTo(String value) {
            addCriterion("DESCRIPTION >=", value, "description");
            return this;
        }

        public Criteria andDescriptionLessThan(String value) {
            addCriterion("DESCRIPTION <", value, "description");
            return this;
        }

        public Criteria andDescriptionLessThanOrEqualTo(String value) {
            addCriterion("DESCRIPTION <=", value, "description");
            return this;
        }

        public Criteria andDescriptionLike(String value) {
            addCriterion("DESCRIPTION like", value, "description");
            return this;
        }

        public Criteria andDescriptionNotLike(String value) {
            addCriterion("DESCRIPTION not like", value, "description");
            return this;
        }

        public Criteria andDescriptionIn(List values) {
            addCriterion("DESCRIPTION in", values, "description");
            return this;
        }

        public Criteria andDescriptionNotIn(List values) {
            addCriterion("DESCRIPTION not in", values, "description");
            return this;
        }

        public Criteria andDescriptionBetween(String value1, String value2) {
            addCriterion("DESCRIPTION between", value1, value2, "description");
            return this;
        }

        public Criteria andDescriptionNotBetween(String value1, String value2) {
            addCriterion("DESCRIPTION not between", value1, value2, "description");
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

        public Criteria andOrdersIsNull() {
            addCriterion("ORDERS is null");
            return this;
        }

        public Criteria andOrdersIsNotNull() {
            addCriterion("ORDERS is not null");
            return this;
        }

        public Criteria andOrdersEqualTo(BigDecimal value) {
            addCriterion("ORDERS =", value, "orders");
            return this;
        }

        public Criteria andOrdersNotEqualTo(BigDecimal value) {
            addCriterion("ORDERS <>", value, "orders");
            return this;
        }

        public Criteria andOrdersGreaterThan(BigDecimal value) {
            addCriterion("ORDERS >", value, "orders");
            return this;
        }

        public Criteria andOrdersGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("ORDERS >=", value, "orders");
            return this;
        }

        public Criteria andOrdersLessThan(BigDecimal value) {
            addCriterion("ORDERS <", value, "orders");
            return this;
        }

        public Criteria andOrdersLessThanOrEqualTo(BigDecimal value) {
            addCriterion("ORDERS <=", value, "orders");
            return this;
        }

        public Criteria andOrdersIn(List values) {
            addCriterion("ORDERS in", values, "orders");
            return this;
        }

        public Criteria andOrdersNotIn(List values) {
            addCriterion("ORDERS not in", values, "orders");
            return this;
        }

        public Criteria andOrdersBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ORDERS between", value1, value2, "orders");
            return this;
        }

        public Criteria andOrdersNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ORDERS not between", value1, value2, "orders");
            return this;
        }
    }
}