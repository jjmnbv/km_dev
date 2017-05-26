package com.pltfm.app.vobject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class AppraiseAddtoContentExample implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 6279009461568942388L;

	/**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database table APPRAISE_ADDTO_CONTENT
     *
     * @ibatorgenerated Fri Nov 01 09:40:42 CST 2013
     */
    protected String orderByClause;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database table APPRAISE_ADDTO_CONTENT
     *
     * @ibatorgenerated Fri Nov 01 09:40:42 CST 2013
     */
    protected List oredCriteria;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table APPRAISE_ADDTO_CONTENT
     *
     * @ibatorgenerated Fri Nov 01 09:40:42 CST 2013
     */
    public AppraiseAddtoContentExample() {
        oredCriteria = new ArrayList();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table APPRAISE_ADDTO_CONTENT
     *
     * @ibatorgenerated Fri Nov 01 09:40:42 CST 2013
     */
    protected AppraiseAddtoContentExample(AppraiseAddtoContentExample example) {
        this.orderByClause = example.orderByClause;
        this.oredCriteria = example.oredCriteria;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table APPRAISE_ADDTO_CONTENT
     *
     * @ibatorgenerated Fri Nov 01 09:40:42 CST 2013
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table APPRAISE_ADDTO_CONTENT
     *
     * @ibatorgenerated Fri Nov 01 09:40:42 CST 2013
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table APPRAISE_ADDTO_CONTENT
     *
     * @ibatorgenerated Fri Nov 01 09:40:42 CST 2013
     */
    public List getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table APPRAISE_ADDTO_CONTENT
     *
     * @ibatorgenerated Fri Nov 01 09:40:42 CST 2013
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table APPRAISE_ADDTO_CONTENT
     *
     * @ibatorgenerated Fri Nov 01 09:40:42 CST 2013
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
     * This method corresponds to the database table APPRAISE_ADDTO_CONTENT
     *
     * @ibatorgenerated Fri Nov 01 09:40:42 CST 2013
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table APPRAISE_ADDTO_CONTENT
     *
     * @ibatorgenerated Fri Nov 01 09:40:42 CST 2013
     */
    public void clear() {
        oredCriteria.clear();
    }

    /**
     * This class was generated by Apache iBATIS ibator.
     * This class corresponds to the database table APPRAISE_ADDTO_CONTENT
     *
     * @ibatorgenerated Fri Nov 01 09:40:42 CST 2013
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

        public Criteria andAddContentIdIsNull() {
            addCriterion("ADD_CONTENT_ID is null");
            return this;
        }

        public Criteria andAddContentIdIsNotNull() {
            addCriterion("ADD_CONTENT_ID is not null");
            return this;
        }

        public Criteria andAddContentIdEqualTo(Long value) {
            addCriterion("ADD_CONTENT_ID =", value, "addContentId");
            return this;
        }

        public Criteria andAddContentIdNotEqualTo(Long value) {
            addCriterion("ADD_CONTENT_ID <>", value, "addContentId");
            return this;
        }

        public Criteria andAddContentIdGreaterThan(Long value) {
            addCriterion("ADD_CONTENT_ID >", value, "addContentId");
            return this;
        }

        public Criteria andAddContentIdGreaterThanOrEqualTo(Long value) {
            addCriterion("ADD_CONTENT_ID >=", value, "addContentId");
            return this;
        }

        public Criteria andAddContentIdLessThan(Long value) {
            addCriterion("ADD_CONTENT_ID <", value, "addContentId");
            return this;
        }

        public Criteria andAddContentIdLessThanOrEqualTo(Long value) {
            addCriterion("ADD_CONTENT_ID <=", value, "addContentId");
            return this;
        }

        public Criteria andAddContentIdIn(List values) {
            addCriterion("ADD_CONTENT_ID in", values, "addContentId");
            return this;
        }

        public Criteria andAddContentIdNotIn(List values) {
            addCriterion("ADD_CONTENT_ID not in", values, "addContentId");
            return this;
        }

        public Criteria andAddContentIdBetween(Long value1, Long value2) {
            addCriterion("ADD_CONTENT_ID between", value1, value2, "addContentId");
            return this;
        }

        public Criteria andAddContentIdNotBetween(Long value1, Long value2) {
            addCriterion("ADD_CONTENT_ID not between", value1, value2, "addContentId");
            return this;
        }

        public Criteria andAppraiseIdIsNull() {
            addCriterion("APPRAISE_ID is null");
            return this;
        }

        public Criteria andAppraiseIdIsNotNull() {
            addCriterion("APPRAISE_ID is not null");
            return this;
        }

        public Criteria andAppraiseIdEqualTo(Long value) {
            addCriterion("APPRAISE_ID =", value, "appraiseId");
            return this;
        }

        public Criteria andAppraiseIdNotEqualTo(Long value) {
            addCriterion("APPRAISE_ID <>", value, "appraiseId");
            return this;
        }

        public Criteria andAppraiseIdGreaterThan(Long value) {
            addCriterion("APPRAISE_ID >", value, "appraiseId");
            return this;
        }

        public Criteria andAppraiseIdGreaterThanOrEqualTo(Long value) {
            addCriterion("APPRAISE_ID >=", value, "appraiseId");
            return this;
        }

        public Criteria andAppraiseIdLessThan(Long value) {
            addCriterion("APPRAISE_ID <", value, "appraiseId");
            return this;
        }

        public Criteria andAppraiseIdLessThanOrEqualTo(Long value) {
            addCriterion("APPRAISE_ID <=", value, "appraiseId");
            return this;
        }

        public Criteria andAppraiseIdIn(List values) {
            addCriterion("APPRAISE_ID in", values, "appraiseId");
            return this;
        }

        public Criteria andAppraiseIdNotIn(List values) {
            addCriterion("APPRAISE_ID not in", values, "appraiseId");
            return this;
        }

        public Criteria andAppraiseIdBetween(Long value1, Long value2) {
            addCriterion("APPRAISE_ID between", value1, value2, "appraiseId");
            return this;
        }

        public Criteria andAppraiseIdNotBetween(Long value1, Long value2) {
            addCriterion("APPRAISE_ID not between", value1, value2, "appraiseId");
            return this;
        }

        public Criteria andAddContentIsNull() {
            addCriterion("ADD_CONTENT is null");
            return this;
        }

        public Criteria andAddContentIsNotNull() {
            addCriterion("ADD_CONTENT is not null");
            return this;
        }

        public Criteria andAddContentEqualTo(String value) {
            addCriterion("ADD_CONTENT =", value, "addContent");
            return this;
        }

        public Criteria andAddContentNotEqualTo(String value) {
            addCriterion("ADD_CONTENT <>", value, "addContent");
            return this;
        }

        public Criteria andAddContentGreaterThan(String value) {
            addCriterion("ADD_CONTENT >", value, "addContent");
            return this;
        }

        public Criteria andAddContentGreaterThanOrEqualTo(String value) {
            addCriterion("ADD_CONTENT >=", value, "addContent");
            return this;
        }

        public Criteria andAddContentLessThan(String value) {
            addCriterion("ADD_CONTENT <", value, "addContent");
            return this;
        }

        public Criteria andAddContentLessThanOrEqualTo(String value) {
            addCriterion("ADD_CONTENT <=", value, "addContent");
            return this;
        }

        public Criteria andAddContentLike(String value) {
            addCriterion("ADD_CONTENT like", value, "addContent");
            return this;
        }

        public Criteria andAddContentNotLike(String value) {
            addCriterion("ADD_CONTENT not like", value, "addContent");
            return this;
        }

        public Criteria andAddContentIn(List values) {
            addCriterion("ADD_CONTENT in", values, "addContent");
            return this;
        }

        public Criteria andAddContentNotIn(List values) {
            addCriterion("ADD_CONTENT not in", values, "addContent");
            return this;
        }

        public Criteria andAddContentBetween(String value1, String value2) {
            addCriterion("ADD_CONTENT between", value1, value2, "addContent");
            return this;
        }

        public Criteria andAddContentNotBetween(String value1, String value2) {
            addCriterion("ADD_CONTENT not between", value1, value2, "addContent");
            return this;
        }

        public Criteria andAddContentDateIsNull() {
            addCriterion("ADD_CONTENT_DATE is null");
            return this;
        }

        public Criteria andAddContentDateIsNotNull() {
            addCriterion("ADD_CONTENT_DATE is not null");
            return this;
        }

        public Criteria andAddContentDateEqualTo(Date value) {
            addCriterionForJDBCDate("ADD_CONTENT_DATE =", value, "addContentDate");
            return this;
        }

        public Criteria andAddContentDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("ADD_CONTENT_DATE <>", value, "addContentDate");
            return this;
        }

        public Criteria andAddContentDateGreaterThan(Date value) {
            addCriterionForJDBCDate("ADD_CONTENT_DATE >", value, "addContentDate");
            return this;
        }

        public Criteria andAddContentDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("ADD_CONTENT_DATE >=", value, "addContentDate");
            return this;
        }

        public Criteria andAddContentDateLessThan(Date value) {
            addCriterionForJDBCDate("ADD_CONTENT_DATE <", value, "addContentDate");
            return this;
        }

        public Criteria andAddContentDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("ADD_CONTENT_DATE <=", value, "addContentDate");
            return this;
        }

        public Criteria andAddContentDateIn(List values) {
            addCriterionForJDBCDate("ADD_CONTENT_DATE in", values, "addContentDate");
            return this;
        }

        public Criteria andAddContentDateNotIn(List values) {
            addCriterionForJDBCDate("ADD_CONTENT_DATE not in", values, "addContentDate");
            return this;
        }

        public Criteria andAddContentDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("ADD_CONTENT_DATE between", value1, value2, "addContentDate");
            return this;
        }

        public Criteria andAddContentDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("ADD_CONTENT_DATE not between", value1, value2, "addContentDate");
            return this;
        }

        public Criteria andCheckStatusIsNull() {
            addCriterion("CHECK_STATUS is null");
            return this;
        }

        public Criteria andCheckStatusIsNotNull() {
            addCriterion("CHECK_STATUS is not null");
            return this;
        }

        public Criteria andCheckStatusEqualTo(Short value) {
            addCriterion("CHECK_STATUS =", value, "checkStatus");
            return this;
        }

        public Criteria andCheckStatusNotEqualTo(Short value) {
            addCriterion("CHECK_STATUS <>", value, "checkStatus");
            return this;
        }

        public Criteria andCheckStatusGreaterThan(Short value) {
            addCriterion("CHECK_STATUS >", value, "checkStatus");
            return this;
        }

        public Criteria andCheckStatusGreaterThanOrEqualTo(Short value) {
            addCriterion("CHECK_STATUS >=", value, "checkStatus");
            return this;
        }

        public Criteria andCheckStatusLessThan(Short value) {
            addCriterion("CHECK_STATUS <", value, "checkStatus");
            return this;
        }

        public Criteria andCheckStatusLessThanOrEqualTo(Short value) {
            addCriterion("CHECK_STATUS <=", value, "checkStatus");
            return this;
        }

        public Criteria andCheckStatusIn(List values) {
            addCriterion("CHECK_STATUS in", values, "checkStatus");
            return this;
        }

        public Criteria andCheckStatusNotIn(List values) {
            addCriterion("CHECK_STATUS not in", values, "checkStatus");
            return this;
        }

        public Criteria andCheckStatusBetween(Short value1, Short value2) {
            addCriterion("CHECK_STATUS between", value1, value2, "checkStatus");
            return this;
        }

        public Criteria andCheckStatusNotBetween(Short value1, Short value2) {
            addCriterion("CHECK_STATUS not between", value1, value2, "checkStatus");
            return this;
        }

        public Criteria andCheckManIdIsNull() {
            addCriterion("CHECK_MAN_ID is null");
            return this;
        }

        public Criteria andCheckManIdIsNotNull() {
            addCriterion("CHECK_MAN_ID is not null");
            return this;
        }

        public Criteria andCheckManIdEqualTo(Long value) {
            addCriterion("CHECK_MAN_ID =", value, "checkManId");
            return this;
        }

        public Criteria andCheckManIdNotEqualTo(Long value) {
            addCriterion("CHECK_MAN_ID <>", value, "checkManId");
            return this;
        }

        public Criteria andCheckManIdGreaterThan(Long value) {
            addCriterion("CHECK_MAN_ID >", value, "checkManId");
            return this;
        }

        public Criteria andCheckManIdGreaterThanOrEqualTo(Long value) {
            addCriterion("CHECK_MAN_ID >=", value, "checkManId");
            return this;
        }

        public Criteria andCheckManIdLessThan(Long value) {
            addCriterion("CHECK_MAN_ID <", value, "checkManId");
            return this;
        }

        public Criteria andCheckManIdLessThanOrEqualTo(Long value) {
            addCriterion("CHECK_MAN_ID <=", value, "checkManId");
            return this;
        }

        public Criteria andCheckManIdIn(List values) {
            addCriterion("CHECK_MAN_ID in", values, "checkManId");
            return this;
        }

        public Criteria andCheckManIdNotIn(List values) {
            addCriterion("CHECK_MAN_ID not in", values, "checkManId");
            return this;
        }

        public Criteria andCheckManIdBetween(Long value1, Long value2) {
            addCriterion("CHECK_MAN_ID between", value1, value2, "checkManId");
            return this;
        }

        public Criteria andCheckManIdNotBetween(Long value1, Long value2) {
            addCriterion("CHECK_MAN_ID not between", value1, value2, "checkManId");
            return this;
        }
    }
}