package com.pltfm.app.vobject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class EmailInfoExample {
  /**
   * This field was generated by Abator for iBATIS. This field corresponds to the database table
   * EMAIL_INFO
   *
   * @abatorgenerated Tue Jul 23 16:15:39 CST 2013
   */
  protected String orderByClause;

  /**
   * This field was generated by Abator for iBATIS. This field corresponds to the database table
   * EMAIL_INFO
   *
   * @abatorgenerated Tue Jul 23 16:15:39 CST 2013
   */
  protected List oredCriteria;

  /**
   * This method was generated by Abator for iBATIS. This method corresponds to the database table
   * EMAIL_INFO
   *
   * @abatorgenerated Tue Jul 23 16:15:39 CST 2013
   */
  public EmailInfoExample() {
    oredCriteria = new ArrayList();
  }

  /**
   * This method was generated by Abator for iBATIS. This method corresponds to the database table
   * EMAIL_INFO
   *
   * @abatorgenerated Tue Jul 23 16:15:39 CST 2013
   */
  protected EmailInfoExample(EmailInfoExample example) {
    this.orderByClause = example.orderByClause;
    this.oredCriteria = example.oredCriteria;
  }

  /**
   * This method was generated by Abator for iBATIS. This method corresponds to the database table
   * EMAIL_INFO
   *
   * @abatorgenerated Tue Jul 23 16:15:39 CST 2013
   */
  public void setOrderByClause(String orderByClause) {
    this.orderByClause = orderByClause;
  }

  /**
   * This method was generated by Abator for iBATIS. This method corresponds to the database table
   * EMAIL_INFO
   *
   * @abatorgenerated Tue Jul 23 16:15:39 CST 2013
   */
  public String getOrderByClause() {
    return orderByClause;
  }

  /**
   * This method was generated by Abator for iBATIS. This method corresponds to the database table
   * EMAIL_INFO
   *
   * @abatorgenerated Tue Jul 23 16:15:39 CST 2013
   */
  public List getOredCriteria() {
    return oredCriteria;
  }

  /**
   * This method was generated by Abator for iBATIS. This method corresponds to the database table
   * EMAIL_INFO
   *
   * @abatorgenerated Tue Jul 23 16:15:39 CST 2013
   */
  public void or(Criteria criteria) {
    oredCriteria.add(criteria);
  }

  /**
   * This method was generated by Abator for iBATIS. This method corresponds to the database table
   * EMAIL_INFO
   *
   * @abatorgenerated Tue Jul 23 16:15:39 CST 2013
   */
  public Criteria createCriteria() {
    Criteria criteria = createCriteriaInternal();
    if (oredCriteria.size() == 0) {
      oredCriteria.add(criteria);
    }
    return criteria;
  }

  /**
   * This method was generated by Abator for iBATIS. This method corresponds to the database table
   * EMAIL_INFO
   *
   * @abatorgenerated Tue Jul 23 16:15:39 CST 2013
   */
  protected Criteria createCriteriaInternal() {
    Criteria criteria = new Criteria();
    return criteria;
  }

  /**
   * This method was generated by Abator for iBATIS. This method corresponds to the database table
   * EMAIL_INFO
   *
   * @abatorgenerated Tue Jul 23 16:15:39 CST 2013
   */
  public void clear() {
    oredCriteria.clear();
  }

  /**
   * This class was generated by Abator for iBATIS. This class corresponds to the database table
   * EMAIL_INFO
   *
   * @abatorgenerated Tue Jul 23 16:15:39 CST 2013
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

    public Criteria andNEmailIdIsNull() {
      addCriterion("N_EMAIL_ID is null");
      return this;
    }

    public Criteria andNEmailIdIsNotNull() {
      addCriterion("N_EMAIL_ID is not null");
      return this;
    }

    public Criteria andNEmailIdEqualTo(BigDecimal value) {
      addCriterion("N_EMAIL_ID =", value, "nEmailId");
      return this;
    }

    public Criteria andNEmailIdNotEqualTo(BigDecimal value) {
      addCriterion("N_EMAIL_ID <>", value, "nEmailId");
      return this;
    }

    public Criteria andNEmailIdGreaterThan(BigDecimal value) {
      addCriterion("N_EMAIL_ID >", value, "nEmailId");
      return this;
    }

    public Criteria andNEmailIdGreaterThanOrEqualTo(BigDecimal value) {
      addCriterion("N_EMAIL_ID >=", value, "nEmailId");
      return this;
    }

    public Criteria andNEmailIdLessThan(BigDecimal value) {
      addCriterion("N_EMAIL_ID <", value, "nEmailId");
      return this;
    }

    public Criteria andNEmailIdLessThanOrEqualTo(BigDecimal value) {
      addCriterion("N_EMAIL_ID <=", value, "nEmailId");
      return this;
    }

    public Criteria andNEmailIdIn(List values) {
      addCriterion("N_EMAIL_ID in", values, "nEmailId");
      return this;
    }

    public Criteria andNEmailIdNotIn(List values) {
      addCriterion("N_EMAIL_ID not in", values, "nEmailId");
      return this;
    }

    public Criteria andNEmailIdBetween(BigDecimal value1, BigDecimal value2) {
      addCriterion("N_EMAIL_ID between", value1, value2, "nEmailId");
      return this;
    }

    public Criteria andNEmailIdNotBetween(BigDecimal value1, BigDecimal value2) {
      addCriterion("N_EMAIL_ID not between", value1, value2, "nEmailId");
      return this;
    }

    public Criteria andNAccountIdIsNull() {
      addCriterion("N_ACCOUNT_ID is null");
      return this;
    }

    public Criteria andNAccountIdIsNotNull() {
      addCriterion("N_ACCOUNT_ID is not null");
      return this;
    }

    public Criteria andNAccountIdEqualTo(BigDecimal value) {
      addCriterion("N_ACCOUNT_ID =", value, "nAccountId");
      return this;
    }

    public Criteria andNAccountIdNotEqualTo(BigDecimal value) {
      addCriterion("N_ACCOUNT_ID <>", value, "nAccountId");
      return this;
    }

    public Criteria andNAccountIdGreaterThan(BigDecimal value) {
      addCriterion("N_ACCOUNT_ID >", value, "nAccountId");
      return this;
    }

    public Criteria andNAccountIdGreaterThanOrEqualTo(BigDecimal value) {
      addCriterion("N_ACCOUNT_ID >=", value, "nAccountId");
      return this;
    }

    public Criteria andNAccountIdLessThan(BigDecimal value) {
      addCriterion("N_ACCOUNT_ID <", value, "nAccountId");
      return this;
    }

    public Criteria andNAccountIdLessThanOrEqualTo(BigDecimal value) {
      addCriterion("N_ACCOUNT_ID <=", value, "nAccountId");
      return this;
    }

    public Criteria andNAccountIdIn(List values) {
      addCriterion("N_ACCOUNT_ID in", values, "nAccountId");
      return this;
    }

    public Criteria andNAccountIdNotIn(List values) {
      addCriterion("N_ACCOUNT_ID not in", values, "nAccountId");
      return this;
    }

    public Criteria andNAccountIdBetween(BigDecimal value1, BigDecimal value2) {
      addCriterion("N_ACCOUNT_ID between", value1, value2, "nAccountId");
      return this;
    }

    public Criteria andNAccountIdNotBetween(BigDecimal value1, BigDecimal value2) {
      addCriterion("N_ACCOUNT_ID not between", value1, value2, "nAccountId");
      return this;
    }

    public Criteria andTattedCodeIsNull() {
      addCriterion("TATTED_CODE is null");
      return this;
    }

    public Criteria andTattedCodeIsNotNull() {
      addCriterion("TATTED_CODE is not null");
      return this;
    }

    public Criteria andTattedCodeEqualTo(String value) {
      addCriterion("TATTED_CODE =", value, "tattedCode");
      return this;
    }

    public Criteria andTattedCodeNotEqualTo(String value) {
      addCriterion("TATTED_CODE <>", value, "tattedCode");
      return this;
    }

    public Criteria andTattedCodeGreaterThan(String value) {
      addCriterion("TATTED_CODE >", value, "tattedCode");
      return this;
    }

    public Criteria andTattedCodeGreaterThanOrEqualTo(String value) {
      addCriterion("TATTED_CODE >=", value, "tattedCode");
      return this;
    }

    public Criteria andTattedCodeLessThan(String value) {
      addCriterion("TATTED_CODE <", value, "tattedCode");
      return this;
    }

    public Criteria andTattedCodeLessThanOrEqualTo(String value) {
      addCriterion("TATTED_CODE <=", value, "tattedCode");
      return this;
    }

    public Criteria andTattedCodeLike(String value) {
      addCriterion("TATTED_CODE like", value, "tattedCode");
      return this;
    }

    public Criteria andTattedCodeNotLike(String value) {
      addCriterion("TATTED_CODE not like", value, "tattedCode");
      return this;
    }

    public Criteria andTattedCodeIn(List values) {
      addCriterion("TATTED_CODE in", values, "tattedCode");
      return this;
    }

    public Criteria andTattedCodeNotIn(List values) {
      addCriterion("TATTED_CODE not in", values, "tattedCode");
      return this;
    }

    public Criteria andTattedCodeBetween(String value1, String value2) {
      addCriterion("TATTED_CODE between", value1, value2, "tattedCode");
      return this;
    }

    public Criteria andTattedCodeNotBetween(String value1, String value2) {
      addCriterion("TATTED_CODE not between", value1, value2, "tattedCode");
      return this;
    }

    public Criteria andLastSendTattedcodeTimeIsNull() {
      addCriterion("LAST_SEND_TATTEDCODE_TIME is null");
      return this;
    }

    public Criteria andLastSendTattedcodeTimeIsNotNull() {
      addCriterion("LAST_SEND_TATTEDCODE_TIME is not null");
      return this;
    }

    public Criteria andLastSendTattedcodeTimeEqualTo(String value) {
      addCriterion("LAST_SEND_TATTEDCODE_TIME =", value, "lastSendTattedcodeTime");
      return this;
    }

    public Criteria andLastSendTattedcodeTimeNotEqualTo(String value) {
      addCriterion("LAST_SEND_TATTEDCODE_TIME <>", value, "lastSendTattedcodeTime");
      return this;
    }

    public Criteria andLastSendTattedcodeTimeGreaterThan(String value) {
      addCriterion("LAST_SEND_TATTEDCODE_TIME >", value, "lastSendTattedcodeTime");
      return this;
    }

    public Criteria andLastSendTattedcodeTimeGreaterThanOrEqualTo(String value) {
      addCriterion("LAST_SEND_TATTEDCODE_TIME >=", value, "lastSendTattedcodeTime");
      return this;
    }

    public Criteria andLastSendTattedcodeTimeLessThan(String value) {
      addCriterion("LAST_SEND_TATTEDCODE_TIME <", value, "lastSendTattedcodeTime");
      return this;
    }

    public Criteria andLastSendTattedcodeTimeLessThanOrEqualTo(String value) {
      addCriterion("LAST_SEND_TATTEDCODE_TIME <=", value, "lastSendTattedcodeTime");
      return this;
    }

    public Criteria andLastSendTattedcodeTimeLike(String value) {
      addCriterion("LAST_SEND_TATTEDCODE_TIME like", value, "lastSendTattedcodeTime");
      return this;
    }

    public Criteria andLastSendTattedcodeTimeNotLike(String value) {
      addCriterion("LAST_SEND_TATTEDCODE_TIME not like", value, "lastSendTattedcodeTime");
      return this;
    }

    public Criteria andLastSendTattedcodeTimeIn(List values) {
      addCriterion("LAST_SEND_TATTEDCODE_TIME in", values, "lastSendTattedcodeTime");
      return this;
    }

    public Criteria andLastSendTattedcodeTimeNotIn(List values) {
      addCriterion("LAST_SEND_TATTEDCODE_TIME not in", values, "lastSendTattedcodeTime");
      return this;
    }

    public Criteria andLastSendTattedcodeTimeBetween(String value1, String value2) {
      addCriterion("LAST_SEND_TATTEDCODE_TIME between", value1, value2, "lastSendTattedcodeTime");
      return this;
    }

    public Criteria andLastSendTattedcodeTimeNotBetween(String value1, String value2) {
      addCriterion("LAST_SEND_TATTEDCODE_TIME not between", value1, value2,
          "lastSendTattedcodeTime");
      return this;
    }

    public Criteria andNFailureTimeValueIsNull() {
      addCriterion("N_FAILURE_TIME_VALUE is null");
      return this;
    }

    public Criteria andNFailureTimeValueIsNotNull() {
      addCriterion("N_FAILURE_TIME_VALUE is not null");
      return this;
    }

    public Criteria andNFailureTimeValueEqualTo(Long value) {
      addCriterion("N_FAILURE_TIME_VALUE =", value, "nFailureTimeValue");
      return this;
    }

    public Criteria andNFailureTimeValueNotEqualTo(Long value) {
      addCriterion("N_FAILURE_TIME_VALUE <>", value, "nFailureTimeValue");
      return this;
    }

    public Criteria andNFailureTimeValueGreaterThan(Long value) {
      addCriterion("N_FAILURE_TIME_VALUE >", value, "nFailureTimeValue");
      return this;
    }

    public Criteria andNFailureTimeValueGreaterThanOrEqualTo(Long value) {
      addCriterion("N_FAILURE_TIME_VALUE >=", value, "nFailureTimeValue");
      return this;
    }

    public Criteria andNFailureTimeValueLessThan(Long value) {
      addCriterion("N_FAILURE_TIME_VALUE <", value, "nFailureTimeValue");
      return this;
    }

    public Criteria andNFailureTimeValueLessThanOrEqualTo(Long value) {
      addCriterion("N_FAILURE_TIME_VALUE <=", value, "nFailureTimeValue");
      return this;
    }

    public Criteria andNFailureTimeValueIn(List values) {
      addCriterion("N_FAILURE_TIME_VALUE in", values, "nFailureTimeValue");
      return this;
    }

    public Criteria andNFailureTimeValueNotIn(List values) {
      addCriterion("N_FAILURE_TIME_VALUE not in", values, "nFailureTimeValue");
      return this;
    }

    public Criteria andNFailureTimeValueBetween(Long value1, Long value2) {
      addCriterion("N_FAILURE_TIME_VALUE between", value1, value2, "nFailureTimeValue");
      return this;
    }

    public Criteria andNFailureTimeValueNotBetween(Long value1, Long value2) {
      addCriterion("N_FAILURE_TIME_VALUE not between", value1, value2, "nFailureTimeValue");
      return this;
    }

    public Criteria andNEaillinkTimeValueIsNull() {
      addCriterion("N_EAILLINK_TIME_VALUE is null");
      return this;
    }

    public Criteria andNEaillinkTimeValueIsNotNull() {
      addCriterion("N_EAILLINK_TIME_VALUE is not null");
      return this;
    }

    public Criteria andNEaillinkTimeValueEqualTo(Long value) {
      addCriterion("N_EAILLINK_TIME_VALUE =", value, "nEaillinkTimeValue");
      return this;
    }

    public Criteria andNEaillinkTimeValueNotEqualTo(Long value) {
      addCriterion("N_EAILLINK_TIME_VALUE <>", value, "nEaillinkTimeValue");
      return this;
    }

    public Criteria andNEaillinkTimeValueGreaterThan(Long value) {
      addCriterion("N_EAILLINK_TIME_VALUE >", value, "nEaillinkTimeValue");
      return this;
    }

    public Criteria andNEaillinkTimeValueGreaterThanOrEqualTo(Long value) {
      addCriterion("N_EAILLINK_TIME_VALUE >=", value, "nEaillinkTimeValue");
      return this;
    }

    public Criteria andNEaillinkTimeValueLessThan(Long value) {
      addCriterion("N_EAILLINK_TIME_VALUE <", value, "nEaillinkTimeValue");
      return this;
    }

    public Criteria andNEaillinkTimeValueLessThanOrEqualTo(Long value) {
      addCriterion("N_EAILLINK_TIME_VALUE <=", value, "nEaillinkTimeValue");
      return this;
    }

    public Criteria andNEaillinkTimeValueIn(List values) {
      addCriterion("N_EAILLINK_TIME_VALUE in", values, "nEaillinkTimeValue");
      return this;
    }

    public Criteria andNEaillinkTimeValueNotIn(List values) {
      addCriterion("N_EAILLINK_TIME_VALUE not in", values, "nEaillinkTimeValue");
      return this;
    }

    public Criteria andNEaillinkTimeValueBetween(Long value1, Long value2) {
      addCriterion("N_EAILLINK_TIME_VALUE between", value1, value2, "nEaillinkTimeValue");
      return this;
    }

    public Criteria andNEaillinkTimeValueNotBetween(Long value1, Long value2) {
      addCriterion("N_EAILLINK_TIME_VALUE not between", value1, value2, "nEaillinkTimeValue");
      return this;
    }

    public Criteria andDCreateDateIsNull() {
      addCriterion("D_CREATE_DATE is null");
      return this;
    }

    public Criteria andDCreateDateIsNotNull() {
      addCriterion("D_CREATE_DATE is not null");
      return this;
    }

    public Criteria andDCreateDateEqualTo(Date value) {
      addCriterionForJDBCDate("D_CREATE_DATE =", value, "dCreateDate");
      return this;
    }

    public Criteria andDCreateDateNotEqualTo(Date value) {
      addCriterionForJDBCDate("D_CREATE_DATE <>", value, "dCreateDate");
      return this;
    }

    public Criteria andDCreateDateGreaterThan(Date value) {
      addCriterionForJDBCDate("D_CREATE_DATE >", value, "dCreateDate");
      return this;
    }

    public Criteria andDCreateDateGreaterThanOrEqualTo(Date value) {
      addCriterionForJDBCDate("D_CREATE_DATE >=", value, "dCreateDate");
      return this;
    }

    public Criteria andDCreateDateLessThan(Date value) {
      addCriterionForJDBCDate("D_CREATE_DATE <", value, "dCreateDate");
      return this;
    }

    public Criteria andDCreateDateLessThanOrEqualTo(Date value) {
      addCriterionForJDBCDate("D_CREATE_DATE <=", value, "dCreateDate");
      return this;
    }

    public Criteria andDCreateDateIn(List values) {
      addCriterionForJDBCDate("D_CREATE_DATE in", values, "dCreateDate");
      return this;
    }

    public Criteria andDCreateDateNotIn(List values) {
      addCriterionForJDBCDate("D_CREATE_DATE not in", values, "dCreateDate");
      return this;
    }

    public Criteria andDCreateDateBetween(Date value1, Date value2) {
      addCriterionForJDBCDate("D_CREATE_DATE between", value1, value2, "dCreateDate");
      return this;
    }

    public Criteria andDCreateDateNotBetween(Date value1, Date value2) {
      addCriterionForJDBCDate("D_CREATE_DATE not between", value1, value2, "dCreateDate");
      return this;
    }

    public Criteria andNCreatedIsNull() {
      addCriterion("N_CREATED is null");
      return this;
    }

    public Criteria andNCreatedIsNotNull() {
      addCriterion("N_CREATED is not null");
      return this;
    }

    public Criteria andNCreatedEqualTo(BigDecimal value) {
      addCriterion("N_CREATED =", value, "nCreated");
      return this;
    }

    public Criteria andNCreatedNotEqualTo(BigDecimal value) {
      addCriterion("N_CREATED <>", value, "nCreated");
      return this;
    }

    public Criteria andNCreatedGreaterThan(BigDecimal value) {
      addCriterion("N_CREATED >", value, "nCreated");
      return this;
    }

    public Criteria andNCreatedGreaterThanOrEqualTo(BigDecimal value) {
      addCriterion("N_CREATED >=", value, "nCreated");
      return this;
    }

    public Criteria andNCreatedLessThan(BigDecimal value) {
      addCriterion("N_CREATED <", value, "nCreated");
      return this;
    }

    public Criteria andNCreatedLessThanOrEqualTo(BigDecimal value) {
      addCriterion("N_CREATED <=", value, "nCreated");
      return this;
    }

    public Criteria andNCreatedIn(List values) {
      addCriterion("N_CREATED in", values, "nCreated");
      return this;
    }

    public Criteria andNCreatedNotIn(List values) {
      addCriterion("N_CREATED not in", values, "nCreated");
      return this;
    }

    public Criteria andNCreatedBetween(BigDecimal value1, BigDecimal value2) {
      addCriterion("N_CREATED between", value1, value2, "nCreated");
      return this;
    }

    public Criteria andNCreatedNotBetween(BigDecimal value1, BigDecimal value2) {
      addCriterion("N_CREATED not between", value1, value2, "nCreated");
      return this;
    }

    public Criteria andDModifyDateIsNull() {
      addCriterion("D_MODIFY_DATE is null");
      return this;
    }

    public Criteria andDModifyDateIsNotNull() {
      addCriterion("D_MODIFY_DATE is not null");
      return this;
    }

    public Criteria andDModifyDateEqualTo(Date value) {
      addCriterionForJDBCDate("D_MODIFY_DATE =", value, "dModifyDate");
      return this;
    }

    public Criteria andDModifyDateNotEqualTo(Date value) {
      addCriterionForJDBCDate("D_MODIFY_DATE <>", value, "dModifyDate");
      return this;
    }

    public Criteria andDModifyDateGreaterThan(Date value) {
      addCriterionForJDBCDate("D_MODIFY_DATE >", value, "dModifyDate");
      return this;
    }

    public Criteria andDModifyDateGreaterThanOrEqualTo(Date value) {
      addCriterionForJDBCDate("D_MODIFY_DATE >=", value, "dModifyDate");
      return this;
    }

    public Criteria andDModifyDateLessThan(Date value) {
      addCriterionForJDBCDate("D_MODIFY_DATE <", value, "dModifyDate");
      return this;
    }

    public Criteria andDModifyDateLessThanOrEqualTo(Date value) {
      addCriterionForJDBCDate("D_MODIFY_DATE <=", value, "dModifyDate");
      return this;
    }

    public Criteria andDModifyDateIn(List values) {
      addCriterionForJDBCDate("D_MODIFY_DATE in", values, "dModifyDate");
      return this;
    }

    public Criteria andDModifyDateNotIn(List values) {
      addCriterionForJDBCDate("D_MODIFY_DATE not in", values, "dModifyDate");
      return this;
    }

    public Criteria andDModifyDateBetween(Date value1, Date value2) {
      addCriterionForJDBCDate("D_MODIFY_DATE between", value1, value2, "dModifyDate");
      return this;
    }

    public Criteria andDModifyDateNotBetween(Date value1, Date value2) {
      addCriterionForJDBCDate("D_MODIFY_DATE not between", value1, value2, "dModifyDate");
      return this;
    }

    public Criteria andNModifiedIsNull() {
      addCriterion("N_MODIFIED is null");
      return this;
    }

    public Criteria andNModifiedIsNotNull() {
      addCriterion("N_MODIFIED is not null");
      return this;
    }

    public Criteria andNModifiedEqualTo(BigDecimal value) {
      addCriterion("N_MODIFIED =", value, "nModified");
      return this;
    }

    public Criteria andNModifiedNotEqualTo(BigDecimal value) {
      addCriterion("N_MODIFIED <>", value, "nModified");
      return this;
    }

    public Criteria andNModifiedGreaterThan(BigDecimal value) {
      addCriterion("N_MODIFIED >", value, "nModified");
      return this;
    }

    public Criteria andNModifiedGreaterThanOrEqualTo(BigDecimal value) {
      addCriterion("N_MODIFIED >=", value, "nModified");
      return this;
    }

    public Criteria andNModifiedLessThan(BigDecimal value) {
      addCriterion("N_MODIFIED <", value, "nModified");
      return this;
    }

    public Criteria andNModifiedLessThanOrEqualTo(BigDecimal value) {
      addCriterion("N_MODIFIED <=", value, "nModified");
      return this;
    }

    public Criteria andNModifiedIn(List values) {
      addCriterion("N_MODIFIED in", values, "nModified");
      return this;
    }

    public Criteria andNModifiedNotIn(List values) {
      addCriterion("N_MODIFIED not in", values, "nModified");
      return this;
    }

    public Criteria andNModifiedBetween(BigDecimal value1, BigDecimal value2) {
      addCriterion("N_MODIFIED between", value1, value2, "nModified");
      return this;
    }

    public Criteria andNModifiedNotBetween(BigDecimal value1, BigDecimal value2) {
      addCriterion("N_MODIFIED not between", value1, value2, "nModified");
      return this;
    }

    public Criteria andUrlAddressIsNull() {
      addCriterion("URL_ADDRESS is null");
      return this;
    }

    public Criteria andUrlAddressIsNotNull() {
      addCriterion("URL_ADDRESS is not null");
      return this;
    }

    public Criteria andUrlAddressEqualTo(String value) {
      addCriterion("URL_ADDRESS =", value, "urlAddress");
      return this;
    }

    public Criteria andUrlAddressNotEqualTo(String value) {
      addCriterion("URL_ADDRESS <>", value, "urlAddress");
      return this;
    }

    public Criteria andUrlAddressGreaterThan(String value) {
      addCriterion("URL_ADDRESS >", value, "urlAddress");
      return this;
    }

    public Criteria andUrlAddressGreaterThanOrEqualTo(String value) {
      addCriterion("URL_ADDRESS >=", value, "urlAddress");
      return this;
    }

    public Criteria andUrlAddressLessThan(String value) {
      addCriterion("URL_ADDRESS <", value, "urlAddress");
      return this;
    }

    public Criteria andUrlAddressLessThanOrEqualTo(String value) {
      addCriterion("URL_ADDRESS <=", value, "urlAddress");
      return this;
    }

    public Criteria andUrlAddressLike(String value) {
      addCriterion("URL_ADDRESS like", value, "urlAddress");
      return this;
    }

    public Criteria andUrlAddressNotLike(String value) {
      addCriterion("URL_ADDRESS not like", value, "urlAddress");
      return this;
    }

    public Criteria andUrlAddressIn(List values) {
      addCriterion("URL_ADDRESS in", values, "urlAddress");
      return this;
    }

    public Criteria andUrlAddressNotIn(List values) {
      addCriterion("URL_ADDRESS not in", values, "urlAddress");
      return this;
    }

    public Criteria andUrlAddressBetween(String value1, String value2) {
      addCriterion("URL_ADDRESS between", value1, value2, "urlAddress");
      return this;
    }

    public Criteria andUrlAddressNotBetween(String value1, String value2) {
      addCriterion("URL_ADDRESS not between", value1, value2, "urlAddress");
      return this;
    }
  }
}
