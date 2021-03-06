package com.pltfm.app.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unchecked")
public class SettmentOperateStatementExample implements Serializable {
  private static final long serialVersionUID = 1L;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database table
   * SETTMENT_OPERATE_STATEMENT
   * 
   * @ibatorgenerated Wed Apr 15 10:27:20 CST 2015
   */
  protected String orderByClause;
  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database table
   * SETTMENT_OPERATE_STATEMENT
   * 
   * @ibatorgenerated Wed Apr 15 10:27:20 CST 2015
   */
  protected List oredCriteria;

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table SETTMENT_OPERATE_STATEMENT
   * 
   * @ibatorgenerated Wed Apr 15 10:27:20 CST 2015
   */
  public SettmentOperateStatementExample() {
    oredCriteria = new ArrayList();
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table SETTMENT_OPERATE_STATEMENT
   * 
   * @ibatorgenerated Wed Apr 15 10:27:20 CST 2015
   */
  protected SettmentOperateStatementExample(SettmentOperateStatementExample example) {
    this.orderByClause = example.orderByClause;
    this.oredCriteria = example.oredCriteria;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table SETTMENT_OPERATE_STATEMENT
   * 
   * @ibatorgenerated Wed Apr 15 10:27:20 CST 2015
   */
  public void setOrderByClause(String orderByClause) {
    this.orderByClause = orderByClause;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table SETTMENT_OPERATE_STATEMENT
   * 
   * @ibatorgenerated Wed Apr 15 10:27:20 CST 2015
   */
  public String getOrderByClause() {
    return orderByClause;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table SETTMENT_OPERATE_STATEMENT
   * 
   * @ibatorgenerated Wed Apr 15 10:27:20 CST 2015
   */
  public List getOredCriteria() {
    return oredCriteria;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table SETTMENT_OPERATE_STATEMENT
   * 
   * @ibatorgenerated Wed Apr 15 10:27:20 CST 2015
   */
  public void or(Criteria criteria) {
    oredCriteria.add(criteria);
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table SETTMENT_OPERATE_STATEMENT
   * 
   * @ibatorgenerated Wed Apr 15 10:27:20 CST 2015
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
   * table SETTMENT_OPERATE_STATEMENT
   * 
   * @ibatorgenerated Wed Apr 15 10:27:20 CST 2015
   */
  protected Criteria createCriteriaInternal() {
    Criteria criteria = new Criteria();
    return criteria;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table SETTMENT_OPERATE_STATEMENT
   * 
   * @ibatorgenerated Wed Apr 15 10:27:20 CST 2015
   */
  public void clear() {
    oredCriteria.clear();
  }

  /**
   * This class was generated by Apache iBATIS ibator. This class corresponds to the database table
   * SETTMENT_OPERATE_STATEMENT
   * 
   * @ibatorgenerated Wed Apr 15 10:27:20 CST 2015
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
      addCriterion(condition, new java.sql.Date(value1.getTime()), new java.sql.Date(value2
          .getTime()), property);
    }

    public Criteria andStatementIdIsNull() {
      addCriterion("STATEMENT_ID is null");
      return this;
    }

    public Criteria andStatementIdIsNotNull() {
      addCriterion("STATEMENT_ID is not null");
      return this;
    }

    public Criteria andStatementIdEqualTo(Long value) {
      addCriterion("STATEMENT_ID =", value, "statementId");
      return this;
    }

    public Criteria andStatementIdNotEqualTo(Long value) {
      addCriterion("STATEMENT_ID <>", value, "statementId");
      return this;
    }

    public Criteria andStatementIdGreaterThan(Long value) {
      addCriterion("STATEMENT_ID >", value, "statementId");
      return this;
    }

    public Criteria andStatementIdGreaterThanOrEqualTo(Long value) {
      addCriterion("STATEMENT_ID >=", value, "statementId");
      return this;
    }

    public Criteria andStatementIdLessThan(Long value) {
      addCriterion("STATEMENT_ID <", value, "statementId");
      return this;
    }

    public Criteria andStatementIdLessThanOrEqualTo(Long value) {
      addCriterion("STATEMENT_ID <=", value, "statementId");
      return this;
    }

    public Criteria andStatementIdIn(List values) {
      addCriterion("STATEMENT_ID in", values, "statementId");
      return this;
    }

    public Criteria andStatementIdNotIn(List values) {
      addCriterion("STATEMENT_ID not in", values, "statementId");
      return this;
    }

    public Criteria andStatementIdBetween(Long value1, Long value2) {
      addCriterion("STATEMENT_ID between", value1, value2, "statementId");
      return this;
    }

    public Criteria andStatementIdNotBetween(Long value1, Long value2) {
      addCriterion("STATEMENT_ID not between", value1, value2, "statementId");
      return this;
    }

    public Criteria andSettmentNoIsNull() {
      addCriterion("SETTMENT_NO is null");
      return this;
    }

    public Criteria andSettmentNoIsNotNull() {
      addCriterion("SETTMENT_NO is not null");
      return this;
    }

    public Criteria andSettmentNoEqualTo(String value) {
      addCriterion("SETTMENT_NO =", value, "settmentNo");
      return this;
    }

    public Criteria andSettmentNoNotEqualTo(String value) {
      addCriterion("SETTMENT_NO <>", value, "settmentNo");
      return this;
    }

    public Criteria andSettmentNoGreaterThan(String value) {
      addCriterion("SETTMENT_NO >", value, "settmentNo");
      return this;
    }

    public Criteria andSettmentNoGreaterThanOrEqualTo(String value) {
      addCriterion("SETTMENT_NO >=", value, "settmentNo");
      return this;
    }

    public Criteria andSettmentNoLessThan(String value) {
      addCriterion("SETTMENT_NO <", value, "settmentNo");
      return this;
    }

    public Criteria andSettmentNoLessThanOrEqualTo(String value) {
      addCriterion("SETTMENT_NO <=", value, "settmentNo");
      return this;
    }

    public Criteria andSettmentNoLike(String value) {
      addCriterion("SETTMENT_NO like", value, "settmentNo");
      return this;
    }

    public Criteria andSettmentNoNotLike(String value) {
      addCriterion("SETTMENT_NO not like", value, "settmentNo");
      return this;
    }

    public Criteria andSettmentNoIn(List values) {
      addCriterion("SETTMENT_NO in", values, "settmentNo");
      return this;
    }

    public Criteria andSettmentNoNotIn(List values) {
      addCriterion("SETTMENT_NO not in", values, "settmentNo");
      return this;
    }

    public Criteria andSettmentNoBetween(String value1, String value2) {
      addCriterion("SETTMENT_NO between", value1, value2, "settmentNo");
      return this;
    }

    public Criteria andSettmentNoNotBetween(String value1, String value2) {
      addCriterion("SETTMENT_NO not between", value1, value2, "settmentNo");
      return this;
    }

    public Criteria andOperateTimeIsNull() {
      addCriterion("OPERATE_TIME is null");
      return this;
    }

    public Criteria andOperateTimeIsNotNull() {
      addCriterion("OPERATE_TIME is not null");
      return this;
    }

    public Criteria andOperateTimeEqualTo(Date value) {
      addCriterionForJDBCDate("OPERATE_TIME =", value, "operateTime");
      return this;
    }

    public Criteria andOperateTimeNotEqualTo(Date value) {
      addCriterionForJDBCDate("OPERATE_TIME <>", value, "operateTime");
      return this;
    }

    public Criteria andOperateTimeGreaterThan(Date value) {
      addCriterionForJDBCDate("OPERATE_TIME >", value, "operateTime");
      return this;
    }

    public Criteria andOperateTimeGreaterThanOrEqualTo(Date value) {
      addCriterionForJDBCDate("OPERATE_TIME >=", value, "operateTime");
      return this;
    }

    public Criteria andOperateTimeLessThan(Date value) {
      addCriterionForJDBCDate("OPERATE_TIME <", value, "operateTime");
      return this;
    }

    public Criteria andOperateTimeLessThanOrEqualTo(Date value) {
      addCriterionForJDBCDate("OPERATE_TIME <=", value, "operateTime");
      return this;
    }

    public Criteria andOperateTimeIn(List values) {
      addCriterionForJDBCDate("OPERATE_TIME in", values, "operateTime");
      return this;
    }

    public Criteria andOperateTimeNotIn(List values) {
      addCriterionForJDBCDate("OPERATE_TIME not in", values, "operateTime");
      return this;
    }

    public Criteria andOperateTimeBetween(Date value1, Date value2) {
      addCriterionForJDBCDate("OPERATE_TIME between", value1, value2, "operateTime");
      return this;
    }

    public Criteria andOperateTimeNotBetween(Date value1, Date value2) {
      addCriterionForJDBCDate("OPERATE_TIME not between", value1, value2, "operateTime");
      return this;
    }

    public Criteria andOperatorTypeIsNull() {
      addCriterion("OPERATOR_TYPE is null");
      return this;
    }

    public Criteria andOperatorTypeIsNotNull() {
      addCriterion("OPERATOR_TYPE is not null");
      return this;
    }

    public Criteria andOperatorTypeEqualTo(Short value) {
      addCriterion("OPERATOR_TYPE =", value, "operatorType");
      return this;
    }

    public Criteria andOperatorTypeNotEqualTo(Short value) {
      addCriterion("OPERATOR_TYPE <>", value, "operatorType");
      return this;
    }

    public Criteria andOperatorTypeGreaterThan(Short value) {
      addCriterion("OPERATOR_TYPE >", value, "operatorType");
      return this;
    }

    public Criteria andOperatorTypeGreaterThanOrEqualTo(Short value) {
      addCriterion("OPERATOR_TYPE >=", value, "operatorType");
      return this;
    }

    public Criteria andOperatorTypeLessThan(Short value) {
      addCriterion("OPERATOR_TYPE <", value, "operatorType");
      return this;
    }

    public Criteria andOperatorTypeLessThanOrEqualTo(Short value) {
      addCriterion("OPERATOR_TYPE <=", value, "operatorType");
      return this;
    }

    public Criteria andOperatorTypeIn(List values) {
      addCriterion("OPERATOR_TYPE in", values, "operatorType");
      return this;
    }

    public Criteria andOperatorTypeNotIn(List values) {
      addCriterion("OPERATOR_TYPE not in", values, "operatorType");
      return this;
    }

    public Criteria andOperatorTypeBetween(Short value1, Short value2) {
      addCriterion("OPERATOR_TYPE between", value1, value2, "operatorType");
      return this;
    }

    public Criteria andOperatorTypeNotBetween(Short value1, Short value2) {
      addCriterion("OPERATOR_TYPE not between", value1, value2, "operatorType");
      return this;
    }

    public Criteria andOperatorIsNull() {
      addCriterion("OPERATOR is null");
      return this;
    }

    public Criteria andOperatorIsNotNull() {
      addCriterion("OPERATOR is not null");
      return this;
    }

    public Criteria andOperatorEqualTo(String value) {
      addCriterion("OPERATOR =", value, "operator");
      return this;
    }

    public Criteria andOperatorNotEqualTo(String value) {
      addCriterion("OPERATOR <>", value, "operator");
      return this;
    }

    public Criteria andOperatorGreaterThan(String value) {
      addCriterion("OPERATOR >", value, "operator");
      return this;
    }

    public Criteria andOperatorGreaterThanOrEqualTo(String value) {
      addCriterion("OPERATOR >=", value, "operator");
      return this;
    }

    public Criteria andOperatorLessThan(String value) {
      addCriterion("OPERATOR <", value, "operator");
      return this;
    }

    public Criteria andOperatorLessThanOrEqualTo(String value) {
      addCriterion("OPERATOR <=", value, "operator");
      return this;
    }

    public Criteria andOperatorLike(String value) {
      addCriterion("OPERATOR like", value, "operator");
      return this;
    }

    public Criteria andOperatorNotLike(String value) {
      addCriterion("OPERATOR not like", value, "operator");
      return this;
    }

    public Criteria andOperatorIn(List values) {
      addCriterion("OPERATOR in", values, "operator");
      return this;
    }

    public Criteria andOperatorNotIn(List values) {
      addCriterion("OPERATOR not in", values, "operator");
      return this;
    }

    public Criteria andOperatorBetween(String value1, String value2) {
      addCriterion("OPERATOR between", value1, value2, "operator");
      return this;
    }

    public Criteria andOperatorNotBetween(String value1, String value2) {
      addCriterion("OPERATOR not between", value1, value2, "operator");
      return this;
    }

    public Criteria andOperateTypeIsNull() {
      addCriterion("OPERATE_TYPE is null");
      return this;
    }

    public Criteria andOperateTypeIsNotNull() {
      addCriterion("OPERATE_TYPE is not null");
      return this;
    }

    public Criteria andOperateTypeEqualTo(Short value) {
      addCriterion("OPERATE_TYPE =", value, "operateType");
      return this;
    }

    public Criteria andOperateTypeNotEqualTo(Short value) {
      addCriterion("OPERATE_TYPE <>", value, "operateType");
      return this;
    }

    public Criteria andOperateTypeGreaterThan(Short value) {
      addCriterion("OPERATE_TYPE >", value, "operateType");
      return this;
    }

    public Criteria andOperateTypeGreaterThanOrEqualTo(Short value) {
      addCriterion("OPERATE_TYPE >=", value, "operateType");
      return this;
    }

    public Criteria andOperateTypeLessThan(Short value) {
      addCriterion("OPERATE_TYPE <", value, "operateType");
      return this;
    }

    public Criteria andOperateTypeLessThanOrEqualTo(Short value) {
      addCriterion("OPERATE_TYPE <=", value, "operateType");
      return this;
    }

    public Criteria andOperateTypeIn(List values) {
      addCriterion("OPERATE_TYPE in", values, "operateType");
      return this;
    }

    public Criteria andOperateTypeNotIn(List values) {
      addCriterion("OPERATE_TYPE not in", values, "operateType");
      return this;
    }

    public Criteria andOperateTypeBetween(Short value1, Short value2) {
      addCriterion("OPERATE_TYPE between", value1, value2, "operateType");
      return this;
    }

    public Criteria andOperateTypeNotBetween(Short value1, Short value2) {
      addCriterion("OPERATE_TYPE not between", value1, value2, "operateType");
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
  }
}
