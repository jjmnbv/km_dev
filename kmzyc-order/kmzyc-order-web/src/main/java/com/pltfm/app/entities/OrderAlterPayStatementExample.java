package com.pltfm.app.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unchecked")
public class OrderAlterPayStatementExample implements Serializable {
  private static final long serialVersionUID = 1L;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database table
   * KMORDER.ORDER_ALTER_PAY_STATEMENT
   * 
   * @ibatorgenerated Thu Sep 05 18:38:07 CST 2013
   */
  protected String orderByClause;
  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database table
   * KMORDER.ORDER_ALTER_PAY_STATEMENT
   * 
   * @ibatorgenerated Thu Sep 05 18:38:07 CST 2013
   */
  protected List oredCriteria;

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table KMORDER.ORDER_ALTER_PAY_STATEMENT
   * 
   * @ibatorgenerated Thu Sep 05 18:38:07 CST 2013
   */
  public OrderAlterPayStatementExample() {
    oredCriteria = new ArrayList();
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table KMORDER.ORDER_ALTER_PAY_STATEMENT
   * 
   * @ibatorgenerated Thu Sep 05 18:38:07 CST 2013
   */
  protected OrderAlterPayStatementExample(OrderAlterPayStatementExample example) {
    this.orderByClause = example.orderByClause;
    this.oredCriteria = example.oredCriteria;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table KMORDER.ORDER_ALTER_PAY_STATEMENT
   * 
   * @ibatorgenerated Thu Sep 05 18:38:07 CST 2013
   */
  public void setOrderByClause(String orderByClause) {
    this.orderByClause = orderByClause;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table KMORDER.ORDER_ALTER_PAY_STATEMENT
   * 
   * @ibatorgenerated Thu Sep 05 18:38:07 CST 2013
   */
  public String getOrderByClause() {
    return orderByClause;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table KMORDER.ORDER_ALTER_PAY_STATEMENT
   * 
   * @ibatorgenerated Thu Sep 05 18:38:07 CST 2013
   */
  public List getOredCriteria() {
    return oredCriteria;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table KMORDER.ORDER_ALTER_PAY_STATEMENT
   * 
   * @ibatorgenerated Thu Sep 05 18:38:07 CST 2013
   */
  public void or(Criteria criteria) {
    oredCriteria.add(criteria);
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table KMORDER.ORDER_ALTER_PAY_STATEMENT
   * 
   * @ibatorgenerated Thu Sep 05 18:38:07 CST 2013
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
   * table KMORDER.ORDER_ALTER_PAY_STATEMENT
   * 
   * @ibatorgenerated Thu Sep 05 18:38:07 CST 2013
   */
  protected Criteria createCriteriaInternal() {
    Criteria criteria = new Criteria();
    return criteria;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table KMORDER.ORDER_ALTER_PAY_STATEMENT
   * 
   * @ibatorgenerated Thu Sep 05 18:38:07 CST 2013
   */
  public void clear() {
    oredCriteria.clear();
  }

  /**
   * This class was generated by Apache iBATIS ibator. This class corresponds to the database table
   * KMORDER.ORDER_ALTER_PAY_STATEMENT
   * 
   * @ibatorgenerated Thu Sep 05 18:38:07 CST 2013
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

    public Criteria andPayStatementNoIsNull() {
      addCriterion("PAY_STATEMENT_NO is null");
      return this;
    }

    public Criteria andPayStatementNoIsNotNull() {
      addCriterion("PAY_STATEMENT_NO is not null");
      return this;
    }

    public Criteria andPayStatementNoEqualTo(Long value) {
      addCriterion("PAY_STATEMENT_NO =", value, "payStatementNo");
      return this;
    }

    public Criteria andPayStatementNoNotEqualTo(Long value) {
      addCriterion("PAY_STATEMENT_NO <>", value, "payStatementNo");
      return this;
    }

    public Criteria andPayStatementNoGreaterThan(Long value) {
      addCriterion("PAY_STATEMENT_NO >", value, "payStatementNo");
      return this;
    }

    public Criteria andPayStatementNoGreaterThanOrEqualTo(Long value) {
      addCriterion("PAY_STATEMENT_NO >=", value, "payStatementNo");
      return this;
    }

    public Criteria andPayStatementNoLessThan(Long value) {
      addCriterion("PAY_STATEMENT_NO <", value, "payStatementNo");
      return this;
    }

    public Criteria andPayStatementNoLessThanOrEqualTo(Long value) {
      addCriterion("PAY_STATEMENT_NO <=", value, "payStatementNo");
      return this;
    }

    public Criteria andPayStatementNoIn(List values) {
      addCriterion("PAY_STATEMENT_NO in", values, "payStatementNo");
      return this;
    }

    public Criteria andPayStatementNoNotIn(List values) {
      addCriterion("PAY_STATEMENT_NO not in", values, "payStatementNo");
      return this;
    }

    public Criteria andPayStatementNoBetween(Long value1, Long value2) {
      addCriterion("PAY_STATEMENT_NO between", value1, value2, "payStatementNo");
      return this;
    }

    public Criteria andPayStatementNoNotBetween(Long value1, Long value2) {
      addCriterion("PAY_STATEMENT_NO not between", value1, value2, "payStatementNo");
      return this;
    }

    public Criteria andPaymentWayIsNull() {
      addCriterion("PAYMENT_WAY is null");
      return this;
    }

    public Criteria andPaymentWayIsNotNull() {
      addCriterion("PAYMENT_WAY is not null");
      return this;
    }

    public Criteria andPaymentWayEqualTo(Long value) {
      addCriterion("PAYMENT_WAY =", value, "paymentWay");
      return this;
    }

    public Criteria andPaymentWayNotEqualTo(Long value) {
      addCriterion("PAYMENT_WAY <>", value, "paymentWay");
      return this;
    }

    public Criteria andPaymentWayGreaterThan(Long value) {
      addCriterion("PAYMENT_WAY >", value, "paymentWay");
      return this;
    }

    public Criteria andPaymentWayGreaterThanOrEqualTo(Long value) {
      addCriterion("PAYMENT_WAY >=", value, "paymentWay");
      return this;
    }

    public Criteria andPaymentWayLessThan(Long value) {
      addCriterion("PAYMENT_WAY <", value, "paymentWay");
      return this;
    }

    public Criteria andPaymentWayLessThanOrEqualTo(Long value) {
      addCriterion("PAYMENT_WAY <=", value, "paymentWay");
      return this;
    }

    public Criteria andPaymentWayIn(List values) {
      addCriterion("PAYMENT_WAY in", values, "paymentWay");
      return this;
    }

    public Criteria andPaymentWayNotIn(List values) {
      addCriterion("PAYMENT_WAY not in", values, "paymentWay");
      return this;
    }

    public Criteria andPaymentWayBetween(Long value1, Long value2) {
      addCriterion("PAYMENT_WAY between", value1, value2, "paymentWay");
      return this;
    }

    public Criteria andPaymentWayNotBetween(Long value1, Long value2) {
      addCriterion("PAYMENT_WAY not between", value1, value2, "paymentWay");
      return this;
    }

    public Criteria andStateIsNull() {
      addCriterion("STATE is null");
      return this;
    }

    public Criteria andStateIsNotNull() {
      addCriterion("STATE is not null");
      return this;
    }

    public Criteria andStateEqualTo(Long value) {
      addCriterion("STATE =", value, "state");
      return this;
    }

    public Criteria andStateNotEqualTo(Long value) {
      addCriterion("STATE <>", value, "state");
      return this;
    }

    public Criteria andStateGreaterThan(Long value) {
      addCriterion("STATE >", value, "state");
      return this;
    }

    public Criteria andStateGreaterThanOrEqualTo(Long value) {
      addCriterion("STATE >=", value, "state");
      return this;
    }

    public Criteria andStateLessThan(Long value) {
      addCriterion("STATE <", value, "state");
      return this;
    }

    public Criteria andStateLessThanOrEqualTo(Long value) {
      addCriterion("STATE <=", value, "state");
      return this;
    }

    public Criteria andStateIn(List values) {
      addCriterion("STATE in", values, "state");
      return this;
    }

    public Criteria andStateNotIn(List values) {
      addCriterion("STATE not in", values, "state");
      return this;
    }

    public Criteria andStateBetween(Long value1, Long value2) {
      addCriterion("STATE between", value1, value2, "state");
      return this;
    }

    public Criteria andStateNotBetween(Long value1, Long value2) {
      addCriterion("STATE not between", value1, value2, "state");
      return this;
    }

    public Criteria andAccountIsNull() {
      addCriterion("ACCOUNT is null");
      return this;
    }

    public Criteria andAccountIsNotNull() {
      addCriterion("ACCOUNT is not null");
      return this;
    }

    public Criteria andAccountEqualTo(String value) {
      addCriterion("ACCOUNT =", value, "account");
      return this;
    }

    public Criteria andAccountNotEqualTo(String value) {
      addCriterion("ACCOUNT <>", value, "account");
      return this;
    }

    public Criteria andAccountGreaterThan(String value) {
      addCriterion("ACCOUNT >", value, "account");
      return this;
    }

    public Criteria andAccountGreaterThanOrEqualTo(String value) {
      addCriterion("ACCOUNT >=", value, "account");
      return this;
    }

    public Criteria andAccountLessThan(String value) {
      addCriterion("ACCOUNT <", value, "account");
      return this;
    }

    public Criteria andAccountLessThanOrEqualTo(String value) {
      addCriterion("ACCOUNT <=", value, "account");
      return this;
    }

    public Criteria andAccountLike(String value) {
      addCriterion("ACCOUNT like", value, "account");
      return this;
    }

    public Criteria andAccountNotLike(String value) {
      addCriterion("ACCOUNT not like", value, "account");
      return this;
    }

    public Criteria andAccountIn(List values) {
      addCriterion("ACCOUNT in", values, "account");
      return this;
    }

    public Criteria andAccountNotIn(List values) {
      addCriterion("ACCOUNT not in", values, "account");
      return this;
    }

    public Criteria andAccountBetween(String value1, String value2) {
      addCriterion("ACCOUNT between", value1, value2, "account");
      return this;
    }

    public Criteria andAccountNotBetween(String value1, String value2) {
      addCriterion("ACCOUNT not between", value1, value2, "account");
      return this;
    }

    public Criteria andOrderAlterCodeIsNull() {
      addCriterion("ORDER_ALTER_CODE is null");
      return this;
    }

    public Criteria andOrderAlterCodeIsNotNull() {
      addCriterion("ORDER_ALTER_CODE is not null");
      return this;
    }

    public Criteria andOrderAlterCodeEqualTo(String value) {
      addCriterion("ORDER_ALTER_CODE =", value, "orderAlterCode");
      return this;
    }

    public Criteria andOrderAlterCodeNotEqualTo(String value) {
      addCriterion("ORDER_ALTER_CODE <>", value, "orderAlterCode");
      return this;
    }

    public Criteria andOrderAlterCodeGreaterThan(String value) {
      addCriterion("ORDER_ALTER_CODE >", value, "orderAlterCode");
      return this;
    }

    public Criteria andOrderAlterCodeGreaterThanOrEqualTo(String value) {
      addCriterion("ORDER_ALTER_CODE >=", value, "orderAlterCode");
      return this;
    }

    public Criteria andOrderAlterCodeLessThan(String value) {
      addCriterion("ORDER_ALTER_CODE <", value, "orderAlterCode");
      return this;
    }

    public Criteria andOrderAlterCodeLessThanOrEqualTo(String value) {
      addCriterion("ORDER_ALTER_CODE <=", value, "orderAlterCode");
      return this;
    }

    public Criteria andOrderAlterCodeLike(String value) {
      addCriterion("ORDER_ALTER_CODE like", value, "orderAlterCode");
      return this;
    }

    public Criteria andOrderAlterCodeNotLike(String value) {
      addCriterion("ORDER_ALTER_CODE not like", value, "orderAlterCode");
      return this;
    }

    public Criteria andOrderAlterCodeIn(List values) {
      addCriterion("ORDER_ALTER_CODE in", values, "orderAlterCode");
      return this;
    }

    public Criteria andOrderAlterCodeNotIn(List values) {
      addCriterion("ORDER_ALTER_CODE not in", values, "orderAlterCode");
      return this;
    }

    public Criteria andOrderAlterCodeBetween(String value1, String value2) {
      addCriterion("ORDER_ALTER_CODE between", value1, value2, "orderAlterCode");
      return this;
    }

    public Criteria andOrderAlterCodeNotBetween(String value1, String value2) {
      addCriterion("ORDER_ALTER_CODE not between", value1, value2, "orderAlterCode");
      return this;
    }

    public Criteria andOrderMoneyIsNull() {
      addCriterion("ORDER_MONEY is null");
      return this;
    }

    public Criteria andOrderMoneyIsNotNull() {
      addCriterion("ORDER_MONEY is not null");
      return this;
    }

    public Criteria andOrderMoneyEqualTo(BigDecimal value) {
      addCriterion("ORDER_MONEY =", value, "orderMoney");
      return this;
    }

    public Criteria andOrderMoneyNotEqualTo(BigDecimal value) {
      addCriterion("ORDER_MONEY <>", value, "orderMoney");
      return this;
    }

    public Criteria andOrderMoneyGreaterThan(BigDecimal value) {
      addCriterion("ORDER_MONEY >", value, "orderMoney");
      return this;
    }

    public Criteria andOrderMoneyGreaterThanOrEqualTo(BigDecimal value) {
      addCriterion("ORDER_MONEY >=", value, "orderMoney");
      return this;
    }

    public Criteria andOrderMoneyLessThan(BigDecimal value) {
      addCriterion("ORDER_MONEY <", value, "orderMoney");
      return this;
    }

    public Criteria andOrderMoneyLessThanOrEqualTo(BigDecimal value) {
      addCriterion("ORDER_MONEY <=", value, "orderMoney");
      return this;
    }

    public Criteria andOrderMoneyIn(List values) {
      addCriterion("ORDER_MONEY in", values, "orderMoney");
      return this;
    }

    public Criteria andOrderMoneyNotIn(List values) {
      addCriterion("ORDER_MONEY not in", values, "orderMoney");
      return this;
    }

    public Criteria andOrderMoneyBetween(BigDecimal value1, BigDecimal value2) {
      addCriterion("ORDER_MONEY between", value1, value2, "orderMoney");
      return this;
    }

    public Criteria andOrderMoneyNotBetween(BigDecimal value1, BigDecimal value2) {
      addCriterion("ORDER_MONEY not between", value1, value2, "orderMoney");
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

    public Criteria andEndDateIsNull() {
      addCriterion("END_DATE is null");
      return this;
    }

    public Criteria andEndDateIsNotNull() {
      addCriterion("END_DATE is not null");
      return this;
    }

    public Criteria andEndDateEqualTo(Date value) {
      addCriterionForJDBCDate("END_DATE =", value, "endDate");
      return this;
    }

    public Criteria andEndDateNotEqualTo(Date value) {
      addCriterionForJDBCDate("END_DATE <>", value, "endDate");
      return this;
    }

    public Criteria andEndDateGreaterThan(Date value) {
      addCriterionForJDBCDate("END_DATE >", value, "endDate");
      return this;
    }

    public Criteria andEndDateGreaterThanOrEqualTo(Date value) {
      addCriterionForJDBCDate("END_DATE >=", value, "endDate");
      return this;
    }

    public Criteria andEndDateLessThan(Date value) {
      addCriterionForJDBCDate("END_DATE <", value, "endDate");
      return this;
    }

    public Criteria andEndDateLessThanOrEqualTo(Date value) {
      addCriterionForJDBCDate("END_DATE <=", value, "endDate");
      return this;
    }

    public Criteria andEndDateIn(List values) {
      addCriterionForJDBCDate("END_DATE in", values, "endDate");
      return this;
    }

    public Criteria andEndDateNotIn(List values) {
      addCriterionForJDBCDate("END_DATE not in", values, "endDate");
      return this;
    }

    public Criteria andEndDateBetween(Date value1, Date value2) {
      addCriterionForJDBCDate("END_DATE between", value1, value2, "endDate");
      return this;
    }

    public Criteria andEndDateNotBetween(Date value1, Date value2) {
      addCriterionForJDBCDate("END_DATE not between", value1, value2, "endDate");
      return this;
    }

    public Criteria andOutsidePayStatementNoIsNull() {
      addCriterion("OUTSIDE_PAY_STATEMENT_NO is null");
      return this;
    }

    public Criteria andOutsidePayStatementNoIsNotNull() {
      addCriterion("OUTSIDE_PAY_STATEMENT_NO is not null");
      return this;
    }

    public Criteria andOutsidePayStatementNoEqualTo(Long value) {
      addCriterion("OUTSIDE_PAY_STATEMENT_NO =", value, "outsidePayStatementNo");
      return this;
    }

    public Criteria andOutsidePayStatementNoNotEqualTo(Long value) {
      addCriterion("OUTSIDE_PAY_STATEMENT_NO <>", value, "outsidePayStatementNo");
      return this;
    }

    public Criteria andOutsidePayStatementNoGreaterThan(Long value) {
      addCriterion("OUTSIDE_PAY_STATEMENT_NO >", value, "outsidePayStatementNo");
      return this;
    }

    public Criteria andOutsidePayStatementNoGreaterThanOrEqualTo(Long value) {
      addCriterion("OUTSIDE_PAY_STATEMENT_NO >=", value, "outsidePayStatementNo");
      return this;
    }

    public Criteria andOutsidePayStatementNoLessThan(Long value) {
      addCriterion("OUTSIDE_PAY_STATEMENT_NO <", value, "outsidePayStatementNo");
      return this;
    }

    public Criteria andOutsidePayStatementNoLessThanOrEqualTo(Long value) {
      addCriterion("OUTSIDE_PAY_STATEMENT_NO <=", value, "outsidePayStatementNo");
      return this;
    }

    public Criteria andOutsidePayStatementNoIn(List values) {
      addCriterion("OUTSIDE_PAY_STATEMENT_NO in", values, "outsidePayStatementNo");
      return this;
    }

    public Criteria andOutsidePayStatementNoNotIn(List values) {
      addCriterion("OUTSIDE_PAY_STATEMENT_NO not in", values, "outsidePayStatementNo");
      return this;
    }

    public Criteria andOutsidePayStatementNoBetween(Long value1, Long value2) {
      addCriterion("OUTSIDE_PAY_STATEMENT_NO between", value1, value2, "outsidePayStatementNo");
      return this;
    }

    public Criteria andOutsidePayStatementNoNotBetween(Long value1, Long value2) {
      addCriterion("OUTSIDE_PAY_STATEMENT_NO not between", value1, value2, "outsidePayStatementNo");
      return this;
    }

    public Criteria andFlagIsNull() {
      addCriterion("FLAG is null");
      return this;
    }

    public Criteria andFlagIsNotNull() {
      addCriterion("FLAG is not null");
      return this;
    }

    public Criteria andFlagEqualTo(Long value) {
      addCriterion("FLAG =", value, "flag");
      return this;
    }

    public Criteria andFlagNotEqualTo(Long value) {
      addCriterion("FLAG <>", value, "flag");
      return this;
    }

    public Criteria andFlagGreaterThan(Long value) {
      addCriterion("FLAG >", value, "flag");
      return this;
    }

    public Criteria andFlagGreaterThanOrEqualTo(Long value) {
      addCriterion("FLAG >=", value, "flag");
      return this;
    }

    public Criteria andFlagLessThan(Long value) {
      addCriterion("FLAG <", value, "flag");
      return this;
    }

    public Criteria andFlagLessThanOrEqualTo(Long value) {
      addCriterion("FLAG <=", value, "flag");
      return this;
    }

    public Criteria andFlagIn(List values) {
      addCriterion("FLAG in", values, "flag");
      return this;
    }

    public Criteria andFlagNotIn(List values) {
      addCriterion("FLAG not in", values, "flag");
      return this;
    }

    public Criteria andFlagBetween(Long value1, Long value2) {
      addCriterion("FLAG between", value1, value2, "flag");
      return this;
    }

    public Criteria andFlagNotBetween(Long value1, Long value2) {
      addCriterion("FLAG not between", value1, value2, "flag");
      return this;
    }

    public Criteria andPreferentialNoIsNull() {
      addCriterion("PREFERENTIAL_NO is null");
      return this;
    }

    public Criteria andPreferentialNoIsNotNull() {
      addCriterion("PREFERENTIAL_NO is not null");
      return this;
    }

    public Criteria andPreferentialNoEqualTo(BigDecimal value) {
      addCriterion("PREFERENTIAL_NO =", value, "preferentialNo");
      return this;
    }

    public Criteria andPreferentialNoNotEqualTo(BigDecimal value) {
      addCriterion("PREFERENTIAL_NO <>", value, "preferentialNo");
      return this;
    }

    public Criteria andPreferentialNoGreaterThan(BigDecimal value) {
      addCriterion("PREFERENTIAL_NO >", value, "preferentialNo");
      return this;
    }

    public Criteria andPreferentialNoGreaterThanOrEqualTo(BigDecimal value) {
      addCriterion("PREFERENTIAL_NO >=", value, "preferentialNo");
      return this;
    }

    public Criteria andPreferentialNoLessThan(BigDecimal value) {
      addCriterion("PREFERENTIAL_NO <", value, "preferentialNo");
      return this;
    }

    public Criteria andPreferentialNoLessThanOrEqualTo(BigDecimal value) {
      addCriterion("PREFERENTIAL_NO <=", value, "preferentialNo");
      return this;
    }

    public Criteria andPreferentialNoIn(List values) {
      addCriterion("PREFERENTIAL_NO in", values, "preferentialNo");
      return this;
    }

    public Criteria andPreferentialNoNotIn(List values) {
      addCriterion("PREFERENTIAL_NO not in", values, "preferentialNo");
      return this;
    }

    public Criteria andPreferentialNoBetween(BigDecimal value1, BigDecimal value2) {
      addCriterion("PREFERENTIAL_NO between", value1, value2, "preferentialNo");
      return this;
    }

    public Criteria andPreferentialNoNotBetween(BigDecimal value1, BigDecimal value2) {
      addCriterion("PREFERENTIAL_NO not between", value1, value2, "preferentialNo");
      return this;
    }

    public Criteria andPreferentialNameIsNull() {
      addCriterion("PREFERENTIAL_NAME is null");
      return this;
    }

    public Criteria andPreferentialNameIsNotNull() {
      addCriterion("PREFERENTIAL_NAME is not null");
      return this;
    }

    public Criteria andPreferentialNameEqualTo(String value) {
      addCriterion("PREFERENTIAL_NAME =", value, "preferentialName");
      return this;
    }

    public Criteria andPreferentialNameNotEqualTo(String value) {
      addCriterion("PREFERENTIAL_NAME <>", value, "preferentialName");
      return this;
    }

    public Criteria andPreferentialNameGreaterThan(String value) {
      addCriterion("PREFERENTIAL_NAME >", value, "preferentialName");
      return this;
    }

    public Criteria andPreferentialNameGreaterThanOrEqualTo(String value) {
      addCriterion("PREFERENTIAL_NAME >=", value, "preferentialName");
      return this;
    }

    public Criteria andPreferentialNameLessThan(String value) {
      addCriterion("PREFERENTIAL_NAME <", value, "preferentialName");
      return this;
    }

    public Criteria andPreferentialNameLessThanOrEqualTo(String value) {
      addCriterion("PREFERENTIAL_NAME <=", value, "preferentialName");
      return this;
    }

    public Criteria andPreferentialNameLike(String value) {
      addCriterion("PREFERENTIAL_NAME like", value, "preferentialName");
      return this;
    }

    public Criteria andPreferentialNameNotLike(String value) {
      addCriterion("PREFERENTIAL_NAME not like", value, "preferentialName");
      return this;
    }

    public Criteria andPreferentialNameIn(List values) {
      addCriterion("PREFERENTIAL_NAME in", values, "preferentialName");
      return this;
    }

    public Criteria andPreferentialNameNotIn(List values) {
      addCriterion("PREFERENTIAL_NAME not in", values, "preferentialName");
      return this;
    }

    public Criteria andPreferentialNameBetween(String value1, String value2) {
      addCriterion("PREFERENTIAL_NAME between", value1, value2, "preferentialName");
      return this;
    }

    public Criteria andPreferentialNameNotBetween(String value1, String value2) {
      addCriterion("PREFERENTIAL_NAME not between", value1, value2, "preferentialName");
      return this;
    }
    
    public Criteria andYsflagEqualTo(String value) {
      addCriterion("ysflag =", value, "ysFlag");
      return this;
    }
  }
}
