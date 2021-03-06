package com.pltfm.app.vobject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class NwesCsReplyExample {
  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database table
   * NWES_CS_REPLY
   *
   * @ibatorgenerated Fri Aug 02 16:04:44 CST 2013
   */
  protected String orderByClause;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database table
   * NWES_CS_REPLY
   *
   * @ibatorgenerated Fri Aug 02 16:04:44 CST 2013
   */
  protected List oredCriteria;

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table NWES_CS_REPLY
   *
   * @ibatorgenerated Fri Aug 02 16:04:44 CST 2013
   */
  public NwesCsReplyExample() {
    oredCriteria = new ArrayList();
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table NWES_CS_REPLY
   *
   * @ibatorgenerated Fri Aug 02 16:04:44 CST 2013
   */
  protected NwesCsReplyExample(NwesCsReplyExample example) {
    this.orderByClause = example.orderByClause;
    this.oredCriteria = example.oredCriteria;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table NWES_CS_REPLY
   *
   * @ibatorgenerated Fri Aug 02 16:04:44 CST 2013
   */
  public void setOrderByClause(String orderByClause) {
    this.orderByClause = orderByClause;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table NWES_CS_REPLY
   *
   * @ibatorgenerated Fri Aug 02 16:04:44 CST 2013
   */
  public String getOrderByClause() {
    return orderByClause;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table NWES_CS_REPLY
   *
   * @ibatorgenerated Fri Aug 02 16:04:44 CST 2013
   */
  public List getOredCriteria() {
    return oredCriteria;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table NWES_CS_REPLY
   *
   * @ibatorgenerated Fri Aug 02 16:04:44 CST 2013
   */
  public void or(Criteria criteria) {
    oredCriteria.add(criteria);
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table NWES_CS_REPLY
   *
   * @ibatorgenerated Fri Aug 02 16:04:44 CST 2013
   */
  public Criteria createCriteria() {
    Criteria criteria = createCriteriaInternal();
    if (oredCriteria.size() == 0) {
      oredCriteria.add(criteria);
    }
    return criteria;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table NWES_CS_REPLY
   *
   * @ibatorgenerated Fri Aug 02 16:04:44 CST 2013
   */
  protected Criteria createCriteriaInternal() {
    Criteria criteria = new Criteria();
    return criteria;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table NWES_CS_REPLY
   *
   * @ibatorgenerated Fri Aug 02 16:04:44 CST 2013
   */
  public void clear() {
    oredCriteria.clear();
  }

  /**
   * This class was generated by Apache iBATIS ibator. This class corresponds to the database table
   * NWES_CS_REPLY
   *
   * @ibatorgenerated Fri Aug 02 16:04:44 CST 2013
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

    protected void addCriterionForJDBCDate(String condition, Date value1, Date value2,
        String property) {
      if (value1 == null || value2 == null) {
        throw new RuntimeException("Between values for " + property + " cannot be null");
      }
      addCriterion(condition, new java.sql.Date(value1.getTime()),
          new java.sql.Date(value2.getTime()), property);
    }

    public Criteria andReplyIdIsNull() {
      addCriterion("REPLY_ID is null");
      return this;
    }

    public Criteria andReplyIdIsNotNull() {
      addCriterion("REPLY_ID is not null");
      return this;
    }

    public Criteria andReplyIdEqualTo(BigDecimal value) {
      addCriterion("REPLY_ID =", value, "replyId");
      return this;
    }

    public Criteria andReplyIdNotEqualTo(BigDecimal value) {
      addCriterion("REPLY_ID <>", value, "replyId");
      return this;
    }

    public Criteria andReplyIdGreaterThan(BigDecimal value) {
      addCriterion("REPLY_ID >", value, "replyId");
      return this;
    }

    public Criteria andReplyIdGreaterThanOrEqualTo(BigDecimal value) {
      addCriterion("REPLY_ID >=", value, "replyId");
      return this;
    }

    public Criteria andReplyIdLessThan(BigDecimal value) {
      addCriterion("REPLY_ID <", value, "replyId");
      return this;
    }

    public Criteria andReplyIdLessThanOrEqualTo(BigDecimal value) {
      addCriterion("REPLY_ID <=", value, "replyId");
      return this;
    }

    public Criteria andReplyIdIn(List values) {
      addCriterion("REPLY_ID in", values, "replyId");
      return this;
    }

    public Criteria andReplyIdNotIn(List values) {
      addCriterion("REPLY_ID not in", values, "replyId");
      return this;
    }

    public Criteria andReplyIdBetween(BigDecimal value1, BigDecimal value2) {
      addCriterion("REPLY_ID between", value1, value2, "replyId");
      return this;
    }

    public Criteria andReplyIdNotBetween(BigDecimal value1, BigDecimal value2) {
      addCriterion("REPLY_ID not between", value1, value2, "replyId");
      return this;
    }

    public Criteria andCustomerSurveyIdIsNull() {
      addCriterion("CUSTOMER_SURVEY_ID is null");
      return this;
    }

    public Criteria andCustomerSurveyIdIsNotNull() {
      addCriterion("CUSTOMER_SURVEY_ID is not null");
      return this;
    }

    public Criteria andCustomerSurveyIdEqualTo(BigDecimal value) {
      addCriterion("CUSTOMER_SURVEY_ID =", value, "customerSurveyId");
      return this;
    }

    public Criteria andCustomerSurveyIdNotEqualTo(BigDecimal value) {
      addCriterion("CUSTOMER_SURVEY_ID <>", value, "customerSurveyId");
      return this;
    }

    public Criteria andCustomerSurveyIdGreaterThan(BigDecimal value) {
      addCriterion("CUSTOMER_SURVEY_ID >", value, "customerSurveyId");
      return this;
    }

    public Criteria andCustomerSurveyIdGreaterThanOrEqualTo(BigDecimal value) {
      addCriterion("CUSTOMER_SURVEY_ID >=", value, "customerSurveyId");
      return this;
    }

    public Criteria andCustomerSurveyIdLessThan(BigDecimal value) {
      addCriterion("CUSTOMER_SURVEY_ID <", value, "customerSurveyId");
      return this;
    }

    public Criteria andCustomerSurveyIdLessThanOrEqualTo(BigDecimal value) {
      addCriterion("CUSTOMER_SURVEY_ID <=", value, "customerSurveyId");
      return this;
    }

    public Criteria andCustomerSurveyIdIn(List values) {
      addCriterion("CUSTOMER_SURVEY_ID in", values, "customerSurveyId");
      return this;
    }

    public Criteria andCustomerSurveyIdNotIn(List values) {
      addCriterion("CUSTOMER_SURVEY_ID not in", values, "customerSurveyId");
      return this;
    }

    public Criteria andCustomerSurveyIdBetween(BigDecimal value1, BigDecimal value2) {
      addCriterion("CUSTOMER_SURVEY_ID between", value1, value2, "customerSurveyId");
      return this;
    }

    public Criteria andCustomerSurveyIdNotBetween(BigDecimal value1, BigDecimal value2) {
      addCriterion("CUSTOMER_SURVEY_ID not between", value1, value2, "customerSurveyId");
      return this;
    }

    public Criteria andReplyContentIsNull() {
      addCriterion("REPLY_CONTENT is null");
      return this;
    }

    public Criteria andReplyContentIsNotNull() {
      addCriterion("REPLY_CONTENT is not null");
      return this;
    }

    public Criteria andReplyContentEqualTo(String value) {
      addCriterion("REPLY_CONTENT =", value, "replyContent");
      return this;
    }

    public Criteria andReplyContentNotEqualTo(String value) {
      addCriterion("REPLY_CONTENT <>", value, "replyContent");
      return this;
    }

    public Criteria andReplyContentGreaterThan(String value) {
      addCriterion("REPLY_CONTENT >", value, "replyContent");
      return this;
    }

    public Criteria andReplyContentGreaterThanOrEqualTo(String value) {
      addCriterion("REPLY_CONTENT >=", value, "replyContent");
      return this;
    }

    public Criteria andReplyContentLessThan(String value) {
      addCriterion("REPLY_CONTENT <", value, "replyContent");
      return this;
    }

    public Criteria andReplyContentLessThanOrEqualTo(String value) {
      addCriterion("REPLY_CONTENT <=", value, "replyContent");
      return this;
    }

    public Criteria andReplyContentLike(String value) {
      addCriterion("REPLY_CONTENT like", value, "replyContent");
      return this;
    }

    public Criteria andReplyContentNotLike(String value) {
      addCriterion("REPLY_CONTENT not like", value, "replyContent");
      return this;
    }

    public Criteria andReplyContentIn(List values) {
      addCriterion("REPLY_CONTENT in", values, "replyContent");
      return this;
    }

    public Criteria andReplyContentNotIn(List values) {
      addCriterion("REPLY_CONTENT not in", values, "replyContent");
      return this;
    }

    public Criteria andReplyContentBetween(String value1, String value2) {
      addCriterion("REPLY_CONTENT between", value1, value2, "replyContent");
      return this;
    }

    public Criteria andReplyContentNotBetween(String value1, String value2) {
      addCriterion("REPLY_CONTENT not between", value1, value2, "replyContent");
      return this;
    }

    public Criteria andReplyRdateIsNull() {
      addCriterion("REPLY_RDATE is null");
      return this;
    }

    public Criteria andReplyRdateIsNotNull() {
      addCriterion("REPLY_RDATE is not null");
      return this;
    }

    public Criteria andReplyRdateEqualTo(Date value) {
      addCriterionForJDBCDate("REPLY_RDATE =", value, "replyRdate");
      return this;
    }

    public Criteria andReplyRdateNotEqualTo(Date value) {
      addCriterionForJDBCDate("REPLY_RDATE <>", value, "replyRdate");
      return this;
    }

    public Criteria andReplyRdateGreaterThan(Date value) {
      addCriterionForJDBCDate("REPLY_RDATE >", value, "replyRdate");
      return this;
    }

    public Criteria andReplyRdateGreaterThanOrEqualTo(Date value) {
      addCriterionForJDBCDate("REPLY_RDATE >=", value, "replyRdate");
      return this;
    }

    public Criteria andReplyRdateLessThan(Date value) {
      addCriterionForJDBCDate("REPLY_RDATE <", value, "replyRdate");
      return this;
    }

    public Criteria andReplyRdateLessThanOrEqualTo(Date value) {
      addCriterionForJDBCDate("REPLY_RDATE <=", value, "replyRdate");
      return this;
    }

    public Criteria andReplyRdateIn(List values) {
      addCriterionForJDBCDate("REPLY_RDATE in", values, "replyRdate");
      return this;
    }

    public Criteria andReplyRdateNotIn(List values) {
      addCriterionForJDBCDate("REPLY_RDATE not in", values, "replyRdate");
      return this;
    }

    public Criteria andReplyRdateBetween(Date value1, Date value2) {
      addCriterionForJDBCDate("REPLY_RDATE between", value1, value2, "replyRdate");
      return this;
    }

    public Criteria andReplyRdateNotBetween(Date value1, Date value2) {
      addCriterionForJDBCDate("REPLY_RDATE not between", value1, value2, "replyRdate");
      return this;
    }

    public Criteria andReplyParenIsNull() {
      addCriterion("REPLY_PAREN is null");
      return this;
    }

    public Criteria andReplyParenIsNotNull() {
      addCriterion("REPLY_PAREN is not null");
      return this;
    }

    public Criteria andReplyParenEqualTo(BigDecimal value) {
      addCriterion("REPLY_PAREN =", value, "replyParen");
      return this;
    }

    public Criteria andReplyParenNotEqualTo(BigDecimal value) {
      addCriterion("REPLY_PAREN <>", value, "replyParen");
      return this;
    }

    public Criteria andReplyParenGreaterThan(BigDecimal value) {
      addCriterion("REPLY_PAREN >", value, "replyParen");
      return this;
    }

    public Criteria andReplyParenGreaterThanOrEqualTo(BigDecimal value) {
      addCriterion("REPLY_PAREN >=", value, "replyParen");
      return this;
    }

    public Criteria andReplyParenLessThan(BigDecimal value) {
      addCriterion("REPLY_PAREN <", value, "replyParen");
      return this;
    }

    public Criteria andReplyParenLessThanOrEqualTo(BigDecimal value) {
      addCriterion("REPLY_PAREN <=", value, "replyParen");
      return this;
    }

    public Criteria andReplyParenIn(List values) {
      addCriterion("REPLY_PAREN in", values, "replyParen");
      return this;
    }

    public Criteria andReplyParenNotIn(List values) {
      addCriterion("REPLY_PAREN not in", values, "replyParen");
      return this;
    }

    public Criteria andReplyParenBetween(BigDecimal value1, BigDecimal value2) {
      addCriterion("REPLY_PAREN between", value1, value2, "replyParen");
      return this;
    }

    public Criteria andReplyParenNotBetween(BigDecimal value1, BigDecimal value2) {
      addCriterion("REPLY_PAREN not between", value1, value2, "replyParen");
      return this;
    }
  }
}
