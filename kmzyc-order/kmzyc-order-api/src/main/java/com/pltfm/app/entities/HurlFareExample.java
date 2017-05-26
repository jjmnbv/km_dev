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
public class HurlFareExample implements Serializable {
  private static final long serialVersionUID = 1L;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database table
   * HURL_FARE
   * 
   * @ibatorgenerated Wed Apr 15 10:27:20 CST 2015
   */
  protected String orderByClause;
  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database table
   * HURL_FARE
   * 
   * @ibatorgenerated Wed Apr 15 10:27:20 CST 2015
   */
  protected List oredCriteria;

  /**
   * 分页条件
   */
  private Integer startIndex = 0;
  private Integer endIndex = 20;

  public Integer getStartIndex() {
    return startIndex;
  }

  public Integer getEndIndex() {
    return endIndex;
  }

  public void setEndIndex(Integer endIndex) {
    this.endIndex = endIndex;
  }

  public void setStartIndex(Integer startIndex) {
    this.startIndex = startIndex;
  }

  public void setOredCriteria(List oredCriteria) {
    this.oredCriteria = oredCriteria;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table HURL_FARE
   * 
   * @ibatorgenerated Wed Apr 15 10:27:20 CST 2015
   */
  public HurlFareExample() {
    oredCriteria = new ArrayList();
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table HURL_FARE
   * 
   * @ibatorgenerated Wed Apr 15 10:27:20 CST 2015
   */
  protected HurlFareExample(HurlFareExample example) {
    this.orderByClause = example.orderByClause;
    this.oredCriteria = example.oredCriteria;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table HURL_FARE
   * 
   * @ibatorgenerated Wed Apr 15 10:27:20 CST 2015
   */
  public void setOrderByClause(String orderByClause) {
    this.orderByClause = orderByClause;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table HURL_FARE
   * 
   * @ibatorgenerated Wed Apr 15 10:27:20 CST 2015
   */
  public String getOrderByClause() {
    return orderByClause;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table HURL_FARE
   * 
   * @ibatorgenerated Wed Apr 15 10:27:20 CST 2015
   */
  public List getOredCriteria() {
    return oredCriteria;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table HURL_FARE
   * 
   * @ibatorgenerated Wed Apr 15 10:27:20 CST 2015
   */
  public void or(Criteria criteria) {
    oredCriteria.add(criteria);
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table HURL_FARE
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
   * table HURL_FARE
   * 
   * @ibatorgenerated Wed Apr 15 10:27:20 CST 2015
   */
  protected Criteria createCriteriaInternal() {
    Criteria criteria = new Criteria();
    return criteria;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table HURL_FARE
   * 
   * @ibatorgenerated Wed Apr 15 10:27:20 CST 2015
   */
  public void clear() {
    oredCriteria.clear();
  }

  /**
   * This class was generated by Apache iBATIS ibator. This class corresponds to the database table
   * HURL_FARE
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
      addCriterion(condition, new java.util.Date(value.getTime()), property);
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

    public Criteria andSettlementFareIdIsNull() {
      addCriterion("SETTLEMENT_FARE_ID is null");
      return this;
    }

    public Criteria andSettlementFareIdIsNotNull() {
      addCriterion("SETTLEMENT_FARE_ID is not null");
      return this;
    }

    public Criteria andSettlementFareIdEqualTo(Long value) {
      addCriterion("SETTLEMENT_FARE_ID =", value, "settlementFareId");
      return this;
    }

    public Criteria andSettlementFareIdNotEqualTo(Long value) {
      addCriterion("SETTLEMENT_FARE_ID <>", value, "settlementFareId");
      return this;
    }

    public Criteria andSettlementFareIdGreaterThan(Long value) {
      addCriterion("SETTLEMENT_FARE_ID >", value, "settlementFareId");
      return this;
    }

    public Criteria andSettlementFareIdGreaterThanOrEqualTo(Long value) {
      addCriterion("SETTLEMENT_FARE_ID >=", value, "settlementFareId");
      return this;
    }

    public Criteria andSettlementFareIdLessThan(Long value) {
      addCriterion("SETTLEMENT_FARE_ID <", value, "settlementFareId");
      return this;
    }

    public Criteria andSettlementFareIdLessThanOrEqualTo(Long value) {
      addCriterion("SETTLEMENT_FARE_ID <=", value, "settlementFareId");
      return this;
    }

    public Criteria andSettlementFareIdIn(List values) {
      addCriterion("SETTLEMENT_FARE_ID in", values, "settlementFareId");
      return this;
    }

    public Criteria andSettlementFareIdNotIn(List values) {
      addCriterion("SETTLEMENT_FARE_ID not in", values, "settlementFareId");
      return this;
    }

    public Criteria andSettlementFareIdBetween(Long value1, Long value2) {
      addCriterion("SETTLEMENT_FARE_ID between", value1, value2, "settlementFareId");
      return this;
    }

    public Criteria andSettlementFareIdNotBetween(Long value1, Long value2) {
      addCriterion("SETTLEMENT_FARE_ID not between", value1, value2, "settlementFareId");
      return this;
    }

    public Criteria andSettlementNoIsNull() {
      addCriterion("SETTLEMENT_NO is null");
      return this;
    }

    public Criteria andSettlementNoIsNotNull() {
      addCriterion("SETTLEMENT_NO is not null");
      return this;
    }

    public Criteria andSettlementNoEqualTo(String value) {
      addCriterion("SETTLEMENT_NO =", value, "settlementNo");
      return this;
    }

    public Criteria andSettlementNoNotEqualTo(String value) {
      addCriterion("SETTLEMENT_NO <>", value, "settlementNo");
      return this;
    }

    public Criteria andSettlementNoGreaterThan(String value) {
      addCriterion("SETTLEMENT_NO >", value, "settlementNo");
      return this;
    }

    public Criteria andSettlementNoGreaterThanOrEqualTo(String value) {
      addCriterion("SETTLEMENT_NO >=", value, "settlementNo");
      return this;
    }

    public Criteria andSettlementNoLessThan(String value) {
      addCriterion("SETTLEMENT_NO <", value, "settlementNo");
      return this;
    }

    public Criteria andSettlementNoLessThanOrEqualTo(String value) {
      addCriterion("SETTLEMENT_NO <=", value, "settlementNo");
      return this;
    }

    public Criteria andSettlementNoLike(String value) {
      addCriterion("SETTLEMENT_NO like", value, "settlementNo");
      return this;
    }

    public Criteria andSettlementNoNotLike(String value) {
      addCriterion("SETTLEMENT_NO not like", value, "settlementNo");
      return this;
    }

    public Criteria andSettlementNoIn(List values) {
      addCriterion("SETTLEMENT_NO in", values, "settlementNo");
      return this;
    }

    public Criteria andSettlementNoNotIn(List values) {
      addCriterion("SETTLEMENT_NO not in", values, "settlementNo");
      return this;
    }

    public Criteria andSettlementNoBetween(String value1, String value2) {
      addCriterion("SETTLEMENT_NO between", value1, value2, "settlementNo");
      return this;
    }

    public Criteria andSettlementNoNotBetween(String value1, String value2) {
      addCriterion("SETTLEMENT_NO not between", value1, value2, "settlementNo");
      return this;
    }

    public Criteria andOrderCodeIsNull() {
      addCriterion("ORDER_CODE is null");
      return this;
    }

    public Criteria andOrderCodeIsNotNull() {
      addCriterion("ORDER_CODE is not null");
      return this;
    }

    public Criteria andOrderCodeEqualTo(String value) {
      addCriterion("ORDER_CODE =", value, "orderCode");
      return this;
    }

    public Criteria andOrderCodeNotEqualTo(String value) {
      addCriterion("ORDER_CODE <>", value, "orderCode");
      return this;
    }

    public Criteria andOrderCodeGreaterThan(String value) {
      addCriterion("ORDER_CODE >", value, "orderCode");
      return this;
    }

    public Criteria andOrderCodeGreaterThanOrEqualTo(String value) {
      addCriterion("ORDER_CODE >=", value, "orderCode");
      return this;
    }

    public Criteria andOrderCodeLessThan(String value) {
      addCriterion("ORDER_CODE <", value, "orderCode");
      return this;
    }

    public Criteria andOrderCodeLessThanOrEqualTo(String value) {
      addCriterion("ORDER_CODE <=", value, "orderCode");
      return this;
    }

    public Criteria andOrderCodeLike(String value) {
      addCriterion("ORDER_CODE like", value, "orderCode");
      return this;
    }

    public Criteria andOrderCodeNotLike(String value) {
      addCriterion("ORDER_CODE not like", value, "orderCode");
      return this;
    }

    public Criteria andOrderCodeIn(List values) {
      addCriterion("ORDER_CODE in", values, "orderCode");
      return this;
    }

    public Criteria andOrderCodeNotIn(List values) {
      addCriterion("ORDER_CODE not in", values, "orderCode");
      return this;
    }

    public Criteria andOrderCodeBetween(String value1, String value2) {
      addCriterion("ORDER_CODE between", value1, value2, "orderCode");
      return this;
    }

    public Criteria andOrderCodeNotBetween(String value1, String value2) {
      addCriterion("ORDER_CODE not between", value1, value2, "orderCode");
      return this;
    }

    public Criteria andConsigneeNameIsNull() {
      addCriterion("CONSIGNEE_NAME is null");
      return this;
    }

    public Criteria andConsigneeNameIsNotNull() {
      addCriterion("CONSIGNEE_NAME is not null");
      return this;
    }

    public Criteria andConsigneeNameEqualTo(String value) {
      addCriterion("CONSIGNEE_NAME =", value, "consigneeName");
      return this;
    }

    public Criteria andConsigneeNameNotEqualTo(String value) {
      addCriterion("CONSIGNEE_NAME <>", value, "consigneeName");
      return this;
    }

    public Criteria andConsigneeNameGreaterThan(String value) {
      addCriterion("CONSIGNEE_NAME >", value, "consigneeName");
      return this;
    }

    public Criteria andConsigneeNameGreaterThanOrEqualTo(String value) {
      addCriterion("CONSIGNEE_NAME >=", value, "consigneeName");
      return this;
    }

    public Criteria andConsigneeNameLessThan(String value) {
      addCriterion("CONSIGNEE_NAME <", value, "consigneeName");
      return this;
    }

    public Criteria andConsigneeNameLessThanOrEqualTo(String value) {
      addCriterion("CONSIGNEE_NAME <=", value, "consigneeName");
      return this;
    }

    public Criteria andConsigneeNameLike(String value) {
      addCriterion("CONSIGNEE_NAME like", value, "consigneeName");
      return this;
    }

    public Criteria andConsigneeNameNotLike(String value) {
      addCriterion("CONSIGNEE_NAME not like", value, "consigneeName");
      return this;
    }

    public Criteria andConsigneeNameIn(List values) {
      addCriterion("CONSIGNEE_NAME in", values, "consigneeName");
      return this;
    }

    public Criteria andConsigneeNameNotIn(List values) {
      addCriterion("CONSIGNEE_NAME not in", values, "consigneeName");
      return this;
    }

    public Criteria andConsigneeNameBetween(String value1, String value2) {
      addCriterion("CONSIGNEE_NAME between", value1, value2, "consigneeName");
      return this;
    }

    public Criteria andConsigneeNameNotBetween(String value1, String value2) {
      addCriterion("CONSIGNEE_NAME not between", value1, value2, "consigneeName");
      return this;
    }

    public Criteria andConsigneeMobileIsNull() {
      addCriterion("CONSIGNEE_MOBILE is null");
      return this;
    }

    public Criteria andConsigneeMobileIsNotNull() {
      addCriterion("CONSIGNEE_MOBILE is not null");
      return this;
    }

    public Criteria andConsigneeMobileEqualTo(String value) {
      addCriterion("CONSIGNEE_MOBILE =", value, "consigneeMobile");
      return this;
    }

    public Criteria andConsigneeMobileNotEqualTo(String value) {
      addCriterion("CONSIGNEE_MOBILE <>", value, "consigneeMobile");
      return this;
    }

    public Criteria andConsigneeMobileGreaterThan(String value) {
      addCriterion("CONSIGNEE_MOBILE >", value, "consigneeMobile");
      return this;
    }

    public Criteria andConsigneeMobileGreaterThanOrEqualTo(String value) {
      addCriterion("CONSIGNEE_MOBILE >=", value, "consigneeMobile");
      return this;
    }

    public Criteria andConsigneeMobileLessThan(String value) {
      addCriterion("CONSIGNEE_MOBILE <", value, "consigneeMobile");
      return this;
    }

    public Criteria andConsigneeMobileLessThanOrEqualTo(String value) {
      addCriterion("CONSIGNEE_MOBILE <=", value, "consigneeMobile");
      return this;
    }

    public Criteria andConsigneeMobileLike(String value) {
      addCriterion("CONSIGNEE_MOBILE like", value, "consigneeMobile");
      return this;
    }

    public Criteria andConsigneeMobileNotLike(String value) {
      addCriterion("CONSIGNEE_MOBILE not like", value, "consigneeMobile");
      return this;
    }

    public Criteria andConsigneeMobileIn(List values) {
      addCriterion("CONSIGNEE_MOBILE in", values, "consigneeMobile");
      return this;
    }

    public Criteria andConsigneeMobileNotIn(List values) {
      addCriterion("CONSIGNEE_MOBILE not in", values, "consigneeMobile");
      return this;
    }

    public Criteria andConsigneeMobileBetween(String value1, String value2) {
      addCriterion("CONSIGNEE_MOBILE between", value1, value2, "consigneeMobile");
      return this;
    }

    public Criteria andConsigneeMobileNotBetween(String value1, String value2) {
      addCriterion("CONSIGNEE_MOBILE not between", value1, value2, "consigneeMobile");
      return this;
    }

    public Criteria andConsigneeAddrIsNull() {
      addCriterion("CONSIGNEE_ADDR is null");
      return this;
    }

    public Criteria andConsigneeAddrIsNotNull() {
      addCriterion("CONSIGNEE_ADDR is not null");
      return this;
    }

    public Criteria andConsigneeAddrEqualTo(String value) {
      addCriterion("CONSIGNEE_ADDR =", value, "consigneeAddr");
      return this;
    }

    public Criteria andConsigneeAddrNotEqualTo(String value) {
      addCriterion("CONSIGNEE_ADDR <>", value, "consigneeAddr");
      return this;
    }

    public Criteria andConsigneeAddrGreaterThan(String value) {
      addCriterion("CONSIGNEE_ADDR >", value, "consigneeAddr");
      return this;
    }

    public Criteria andConsigneeAddrGreaterThanOrEqualTo(String value) {
      addCriterion("CONSIGNEE_ADDR >=", value, "consigneeAddr");
      return this;
    }

    public Criteria andConsigneeAddrLessThan(String value) {
      addCriterion("CONSIGNEE_ADDR <", value, "consigneeAddr");
      return this;
    }

    public Criteria andConsigneeAddrLessThanOrEqualTo(String value) {
      addCriterion("CONSIGNEE_ADDR <=", value, "consigneeAddr");
      return this;
    }

    public Criteria andConsigneeAddrLike(String value) {
      addCriterion("CONSIGNEE_ADDR like", value, "consigneeAddr");
      return this;
    }

    public Criteria andConsigneeAddrNotLike(String value) {
      addCriterion("CONSIGNEE_ADDR not like", value, "consigneeAddr");
      return this;
    }

    public Criteria andConsigneeAddrIn(List values) {
      addCriterion("CONSIGNEE_ADDR in", values, "consigneeAddr");
      return this;
    }

    public Criteria andConsigneeAddrNotIn(List values) {
      addCriterion("CONSIGNEE_ADDR not in", values, "consigneeAddr");
      return this;
    }

    public Criteria andConsigneeAddrBetween(String value1, String value2) {
      addCriterion("CONSIGNEE_ADDR between", value1, value2, "consigneeAddr");
      return this;
    }

    public Criteria andConsigneeAddrNotBetween(String value1, String value2) {
      addCriterion("CONSIGNEE_ADDR not between", value1, value2, "consigneeAddr");
      return this;
    }

    public Criteria andFareIsNull() {
      addCriterion("FARE is null");
      return this;
    }

    public Criteria andFareIsNotNull() {
      addCriterion("FARE is not null");
      return this;
    }

    public Criteria andFareEqualTo(BigDecimal value) {
      addCriterion("FARE =", value, "fare");
      return this;
    }

    public Criteria andFareNotEqualTo(BigDecimal value) {
      addCriterion("FARE <>", value, "fare");
      return this;
    }

    public Criteria andFareGreaterThan(BigDecimal value) {
      addCriterion("FARE >", value, "fare");
      return this;
    }

    public Criteria andFareGreaterThanOrEqualTo(BigDecimal value) {
      addCriterion("FARE >=", value, "fare");
      return this;
    }

    public Criteria andFareLessThan(BigDecimal value) {
      addCriterion("FARE <", value, "fare");
      return this;
    }

    public Criteria andFareLessThanOrEqualTo(BigDecimal value) {
      addCriterion("FARE <=", value, "fare");
      return this;
    }

    public Criteria andFareIn(List values) {
      addCriterion("FARE in", values, "fare");
      return this;
    }

    public Criteria andFareNotIn(List values) {
      addCriterion("FARE not in", values, "fare");
      return this;
    }

    public Criteria andFareBetween(BigDecimal value1, BigDecimal value2) {
      addCriterion("FARE between", value1, value2, "fare");
      return this;
    }

    public Criteria andFareNotBetween(BigDecimal value1, BigDecimal value2) {
      addCriterion("FARE not between", value1, value2, "fare");
      return this;
    }

    public Criteria andSettlementTimeIsNull() {
      addCriterion("SETTLEMENT_TIME is null");
      return this;
    }

    public Criteria andSettlementTimeIsNotNull() {
      addCriterion("SETTLEMENT_TIME is not null");
      return this;
    }

    public Criteria andSettlementTimeEqualTo(Date value) {
      addCriterionForJDBCDate("SETTLEMENT_TIME =", value, "settlementTime");
      return this;
    }

    public Criteria andSettlementTimeNotEqualTo(Date value) {
      addCriterionForJDBCDate("SETTLEMENT_TIME <>", value, "settlementTime");
      return this;
    }

    public Criteria andSettlementTimeGreaterThan(Date value) {
      addCriterionForJDBCDate("SETTLEMENT_TIME >", value, "settlementTime");
      return this;
    }

    public Criteria andSettlementTimeGreaterThanOrEqualTo(Date value) {
      addCriterionForJDBCDate("SETTLEMENT_TIME >=", value, "settlementTime");
      return this;
    }

    public Criteria andSettlementTimeLessThan(Date value) {
      addCriterionForJDBCDate("SETTLEMENT_TIME <", value, "settlementTime");
      return this;
    }

    public Criteria andSettlementTimeLessThanOrEqualTo(Date value) {
      addCriterionForJDBCDate("SETTLEMENT_TIME <=", value, "settlementTime");
      return this;
    }

    public Criteria andSettlementTimeIn(List values) {
      addCriterionForJDBCDate("SETTLEMENT_TIME in", values, "settlementTime");
      return this;
    }

    public Criteria andSettlementTimeNotIn(List values) {
      addCriterionForJDBCDate("SETTLEMENT_TIME not in", values, "settlementTime");
      return this;
    }

    public Criteria andSettlementTimeBetween(Date value1, Date value2) {
      addCriterionForJDBCDate("SETTLEMENT_TIME between", value1, value2, "settlementTime");
      return this;
    }

    public Criteria andSettlementTimeNotBetween(Date value1, Date value2) {
      addCriterionForJDBCDate("SETTLEMENT_TIME not between", value1, value2, "settlementTime");
      return this;
    }
  }
}
