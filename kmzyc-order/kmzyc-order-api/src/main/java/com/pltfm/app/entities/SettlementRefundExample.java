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
public class SettlementRefundExample implements Serializable {
  private static final long serialVersionUID = 1L;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database table
   * SETTLEMENT_REFUND
   * 
   * @ibatorgenerated Wed Apr 15 10:27:20 CST 2015
   */
  protected String orderByClause;
  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database table
   * SETTLEMENT_REFUND
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

  public void setStartIndex(Integer startIndex) {
    this.startIndex = startIndex;
  }

  public Integer getEndIndex() {
    return endIndex;
  }

  public void setEndIndex(Integer endIndex) {
    this.endIndex = endIndex;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table SETTLEMENT_REFUND
   * 
   * @ibatorgenerated Wed Apr 15 10:27:20 CST 2015
   */
  public SettlementRefundExample() {
    oredCriteria = new ArrayList();
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table SETTLEMENT_REFUND
   * 
   * @ibatorgenerated Wed Apr 15 10:27:20 CST 2015
   */
  protected SettlementRefundExample(SettlementRefundExample example) {
    this.orderByClause = example.orderByClause;
    this.oredCriteria = example.oredCriteria;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table SETTLEMENT_REFUND
   * 
   * @ibatorgenerated Wed Apr 15 10:27:20 CST 2015
   */
  public void setOrderByClause(String orderByClause) {
    this.orderByClause = orderByClause;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table SETTLEMENT_REFUND
   * 
   * @ibatorgenerated Wed Apr 15 10:27:20 CST 2015
   */
  public String getOrderByClause() {
    return orderByClause;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table SETTLEMENT_REFUND
   * 
   * @ibatorgenerated Wed Apr 15 10:27:20 CST 2015
   */
  public List getOredCriteria() {
    return oredCriteria;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table SETTLEMENT_REFUND
   * 
   * @ibatorgenerated Wed Apr 15 10:27:20 CST 2015
   */
  public void or(Criteria criteria) {
    oredCriteria.add(criteria);
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table SETTLEMENT_REFUND
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
   * table SETTLEMENT_REFUND
   * 
   * @ibatorgenerated Wed Apr 15 10:27:20 CST 2015
   */
  protected Criteria createCriteriaInternal() {
    Criteria criteria = new Criteria();
    return criteria;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table SETTLEMENT_REFUND
   * 
   * @ibatorgenerated Wed Apr 15 10:27:20 CST 2015
   */
  public void clear() {
    oredCriteria.clear();
  }

  /**
   * This class was generated by Apache iBATIS ibator. This class corresponds to the database table
   * SETTLEMENT_REFUND
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

    public Criteria andSettlementRefundIdIsNull() {
      addCriterion("SETTLEMENT_REFUND_ID is null");
      return this;
    }

    public Criteria andSettlementRefundIdIsNotNull() {
      addCriterion("SETTLEMENT_REFUND_ID is not null");
      return this;
    }

    public Criteria andSettlementRefundIdEqualTo(Long value) {
      addCriterion("SETTLEMENT_REFUND_ID =", value, "settlementRefundId");
      return this;
    }

    public Criteria andSettlementRefundIdNotEqualTo(Long value) {
      addCriterion("SETTLEMENT_REFUND_ID <>", value, "settlementRefundId");
      return this;
    }

    public Criteria andSettlementRefundIdGreaterThan(Long value) {
      addCriterion("SETTLEMENT_REFUND_ID >", value, "settlementRefundId");
      return this;
    }

    public Criteria andSettlementRefundIdGreaterThanOrEqualTo(Long value) {
      addCriterion("SETTLEMENT_REFUND_ID >=", value, "settlementRefundId");
      return this;
    }

    public Criteria andSettlementRefundIdLessThan(Long value) {
      addCriterion("SETTLEMENT_REFUND_ID <", value, "settlementRefundId");
      return this;
    }

    public Criteria andSettlementRefundIdLessThanOrEqualTo(Long value) {
      addCriterion("SETTLEMENT_REFUND_ID <=", value, "settlementRefundId");
      return this;
    }

    public Criteria andSettlementRefundIdIn(List values) {
      addCriterion("SETTLEMENT_REFUND_ID in", values, "settlementRefundId");
      return this;
    }

    public Criteria andSettlementRefundIdNotIn(List values) {
      addCriterion("SETTLEMENT_REFUND_ID not in", values, "settlementRefundId");
      return this;
    }

    public Criteria andSettlementRefundIdBetween(Long value1, Long value2) {
      addCriterion("SETTLEMENT_REFUND_ID between", value1, value2, "settlementRefundId");
      return this;
    }

    public Criteria andSettlementRefundIdNotBetween(Long value1, Long value2) {
      addCriterion("SETTLEMENT_REFUND_ID not between", value1, value2, "settlementRefundId");
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

    public Criteria andServiceTypeIsNull() {
      addCriterion("SERVICE_TYPE is null");
      return this;
    }

    public Criteria andServiceTypeIsNotNull() {
      addCriterion("SERVICE_TYPE is not null");
      return this;
    }

    public Criteria andServiceTypeEqualTo(Short value) {
      addCriterion("SERVICE_TYPE =", value, "serviceType");
      return this;
    }

    public Criteria andServiceTypeNotEqualTo(Short value) {
      addCriterion("SERVICE_TYPE <>", value, "serviceType");
      return this;
    }

    public Criteria andServiceTypeGreaterThan(Short value) {
      addCriterion("SERVICE_TYPE >", value, "serviceType");
      return this;
    }

    public Criteria andServiceTypeGreaterThanOrEqualTo(Short value) {
      addCriterion("SERVICE_TYPE >=", value, "serviceType");
      return this;
    }

    public Criteria andServiceTypeLessThan(Short value) {
      addCriterion("SERVICE_TYPE <", value, "serviceType");
      return this;
    }

    public Criteria andServiceTypeLessThanOrEqualTo(Short value) {
      addCriterion("SERVICE_TYPE <=", value, "serviceType");
      return this;
    }

    public Criteria andServiceTypeIn(List values) {
      addCriterion("SERVICE_TYPE in", values, "serviceType");
      return this;
    }

    public Criteria andServiceTypeNotIn(List values) {
      addCriterion("SERVICE_TYPE not in", values, "serviceType");
      return this;
    }

    public Criteria andServiceTypeBetween(Short value1, Short value2) {
      addCriterion("SERVICE_TYPE between", value1, value2, "serviceType");
      return this;
    }

    public Criteria andServiceTypeNotBetween(Short value1, Short value2) {
      addCriterion("SERVICE_TYPE not between", value1, value2, "serviceType");
      return this;
    }

    public Criteria andSkuNoIsNull() {
      addCriterion("SKU_NO is null");
      return this;
    }

    public Criteria andSkuNoIsNotNull() {
      addCriterion("SKU_NO is not null");
      return this;
    }

    public Criteria andSkuNoEqualTo(String value) {
      addCriterion("SKU_NO =", value, "skuNo");
      return this;
    }

    public Criteria andSkuNoNotEqualTo(String value) {
      addCriterion("SKU_NO <>", value, "skuNo");
      return this;
    }

    public Criteria andSkuNoGreaterThan(String value) {
      addCriterion("SKU_NO >", value, "skuNo");
      return this;
    }

    public Criteria andSkuNoGreaterThanOrEqualTo(String value) {
      addCriterion("SKU_NO >=", value, "skuNo");
      return this;
    }

    public Criteria andSkuNoLessThan(String value) {
      addCriterion("SKU_NO <", value, "skuNo");
      return this;
    }

    public Criteria andSkuNoLessThanOrEqualTo(String value) {
      addCriterion("SKU_NO <=", value, "skuNo");
      return this;
    }

    public Criteria andSkuNoLike(String value) {
      addCriterion("SKU_NO like", value, "skuNo");
      return this;
    }

    public Criteria andSkuNoNotLike(String value) {
      addCriterion("SKU_NO not like", value, "skuNo");
      return this;
    }

    public Criteria andSkuNoIn(List values) {
      addCriterion("SKU_NO in", values, "skuNo");
      return this;
    }

    public Criteria andSkuNoNotIn(List values) {
      addCriterion("SKU_NO not in", values, "skuNo");
      return this;
    }

    public Criteria andSkuNoBetween(String value1, String value2) {
      addCriterion("SKU_NO between", value1, value2, "skuNo");
      return this;
    }

    public Criteria andSkuNoNotBetween(String value1, String value2) {
      addCriterion("SKU_NO not between", value1, value2, "skuNo");
      return this;
    }

    public Criteria andProductTitleIsNull() {
      addCriterion("PRODUCT_TITLE is null");
      return this;
    }

    public Criteria andProductTitleIsNotNull() {
      addCriterion("PRODUCT_TITLE is not null");
      return this;
    }

    public Criteria andProductTitleEqualTo(String value) {
      addCriterion("PRODUCT_TITLE =", value, "productTitle");
      return this;
    }

    public Criteria andProductTitleNotEqualTo(String value) {
      addCriterion("PRODUCT_TITLE <>", value, "productTitle");
      return this;
    }

    public Criteria andProductTitleGreaterThan(String value) {
      addCriterion("PRODUCT_TITLE >", value, "productTitle");
      return this;
    }

    public Criteria andProductTitleGreaterThanOrEqualTo(String value) {
      addCriterion("PRODUCT_TITLE >=", value, "productTitle");
      return this;
    }

    public Criteria andProductTitleLessThan(String value) {
      addCriterion("PRODUCT_TITLE <", value, "productTitle");
      return this;
    }

    public Criteria andProductTitleLessThanOrEqualTo(String value) {
      addCriterion("PRODUCT_TITLE <=", value, "productTitle");
      return this;
    }

    public Criteria andProductTitleLike(String value) {
      addCriterion("PRODUCT_TITLE like", value, "productTitle");
      return this;
    }

    public Criteria andProductTitleNotLike(String value) {
      addCriterion("PRODUCT_TITLE not like", value, "productTitle");
      return this;
    }

    public Criteria andProductTitleIn(List values) {
      addCriterion("PRODUCT_TITLE in", values, "productTitle");
      return this;
    }

    public Criteria andProductTitleNotIn(List values) {
      addCriterion("PRODUCT_TITLE not in", values, "productTitle");
      return this;
    }

    public Criteria andProductTitleBetween(String value1, String value2) {
      addCriterion("PRODUCT_TITLE between", value1, value2, "productTitle");
      return this;
    }

    public Criteria andProductTitleNotBetween(String value1, String value2) {
      addCriterion("PRODUCT_TITLE not between", value1, value2, "productTitle");
      return this;
    }

    public Criteria andDealNumberIsNull() {
      addCriterion("DEAL_NUMBER is null");
      return this;
    }

    public Criteria andDealNumberIsNotNull() {
      addCriterion("DEAL_NUMBER is not null");
      return this;
    }

    public Criteria andDealNumberEqualTo(Short value) {
      addCriterion("DEAL_NUMBER =", value, "dealNumber");
      return this;
    }

    public Criteria andDealNumberNotEqualTo(Short value) {
      addCriterion("DEAL_NUMBER <>", value, "dealNumber");
      return this;
    }

    public Criteria andDealNumberGreaterThan(Short value) {
      addCriterion("DEAL_NUMBER >", value, "dealNumber");
      return this;
    }

    public Criteria andDealNumberGreaterThanOrEqualTo(Short value) {
      addCriterion("DEAL_NUMBER >=", value, "dealNumber");
      return this;
    }

    public Criteria andDealNumberLessThan(Short value) {
      addCriterion("DEAL_NUMBER <", value, "dealNumber");
      return this;
    }

    public Criteria andDealNumberLessThanOrEqualTo(Short value) {
      addCriterion("DEAL_NUMBER <=", value, "dealNumber");
      return this;
    }

    public Criteria andDealNumberIn(List values) {
      addCriterion("DEAL_NUMBER in", values, "dealNumber");
      return this;
    }

    public Criteria andDealNumberNotIn(List values) {
      addCriterion("DEAL_NUMBER not in", values, "dealNumber");
      return this;
    }

    public Criteria andDealNumberBetween(Short value1, Short value2) {
      addCriterion("DEAL_NUMBER between", value1, value2, "dealNumber");
      return this;
    }

    public Criteria andDealNumberNotBetween(Short value1, Short value2) {
      addCriterion("DEAL_NUMBER not between", value1, value2, "dealNumber");
      return this;
    }

    public Criteria andRefundMoneyIsNull() {
      addCriterion("REFUND_MONEY is null");
      return this;
    }

    public Criteria andRefundMoneyIsNotNull() {
      addCriterion("REFUND_MONEY is not null");
      return this;
    }

    public Criteria andRefundMoneyEqualTo(Integer value) {
      addCriterion("REFUND_MONEY =", value, "refundMoney");
      return this;
    }

    public Criteria andRefundMoneyNotEqualTo(Integer value) {
      addCriterion("REFUND_MONEY <>", value, "refundMoney");
      return this;
    }

    public Criteria andRefundMoneyGreaterThan(Integer value) {
      addCriterion("REFUND_MONEY >", value, "refundMoney");
      return this;
    }

    public Criteria andRefundMoneyGreaterThanOrEqualTo(Integer value) {
      addCriterion("REFUND_MONEY >=", value, "refundMoney");
      return this;
    }

    public Criteria andRefundMoneyLessThan(Integer value) {
      addCriterion("REFUND_MONEY <", value, "refundMoney");
      return this;
    }

    public Criteria andRefundMoneyLessThanOrEqualTo(Integer value) {
      addCriterion("REFUND_MONEY <=", value, "refundMoney");
      return this;
    }

    public Criteria andRefundMoneyIn(List values) {
      addCriterion("REFUND_MONEY in", values, "refundMoney");
      return this;
    }

    public Criteria andRefundMoneyNotIn(List values) {
      addCriterion("REFUND_MONEY not in", values, "refundMoney");
      return this;
    }

    public Criteria andRefundMoneyBetween(Integer value1, Integer value2) {
      addCriterion("REFUND_MONEY between", value1, value2, "refundMoney");
      return this;
    }

    public Criteria andRefundMoneyNotBetween(Integer value1, Integer value2) {
      addCriterion("REFUND_MONEY not between", value1, value2, "refundMoney");
      return this;
    }

    public Criteria andCommissionRateIsNull() {
      addCriterion("COMMISSION_RATE is null");
      return this;
    }

    public Criteria andCommissionRateIsNotNull() {
      addCriterion("COMMISSION_RATE is not null");
      return this;
    }

    public Criteria andCommissionRateEqualTo(BigDecimal value) {
      addCriterion("COMMISSION_RATE =", value, "commissionRate");
      return this;
    }

    public Criteria andCommissionRateNotEqualTo(BigDecimal value) {
      addCriterion("COMMISSION_RATE <>", value, "commissionRate");
      return this;
    }

    public Criteria andCommissionRateGreaterThan(BigDecimal value) {
      addCriterion("COMMISSION_RATE >", value, "commissionRate");
      return this;
    }

    public Criteria andCommissionRateGreaterThanOrEqualTo(BigDecimal value) {
      addCriterion("COMMISSION_RATE >=", value, "commissionRate");
      return this;
    }

    public Criteria andCommissionRateLessThan(BigDecimal value) {
      addCriterion("COMMISSION_RATE <", value, "commissionRate");
      return this;
    }

    public Criteria andCommissionRateLessThanOrEqualTo(BigDecimal value) {
      addCriterion("COMMISSION_RATE <=", value, "commissionRate");
      return this;
    }

    public Criteria andCommissionRateIn(List values) {
      addCriterion("COMMISSION_RATE in", values, "commissionRate");
      return this;
    }

    public Criteria andCommissionRateNotIn(List values) {
      addCriterion("COMMISSION_RATE not in", values, "commissionRate");
      return this;
    }

    public Criteria andCommissionRateBetween(BigDecimal value1, BigDecimal value2) {
      addCriterion("COMMISSION_RATE between", value1, value2, "commissionRate");
      return this;
    }

    public Criteria andCommissionRateNotBetween(BigDecimal value1, BigDecimal value2) {
      addCriterion("COMMISSION_RATE not between", value1, value2, "commissionRate");
      return this;
    }

    public Criteria andCommissionIsNull() {
      addCriterion("COMMISSION is null");
      return this;
    }

    public Criteria andCommissionIsNotNull() {
      addCriterion("COMMISSION is not null");
      return this;
    }

    public Criteria andCommissionEqualTo(BigDecimal value) {
      addCriterion("COMMISSION =", value, "commission");
      return this;
    }

    public Criteria andCommissionNotEqualTo(BigDecimal value) {
      addCriterion("COMMISSION <>", value, "commission");
      return this;
    }

    public Criteria andCommissionGreaterThan(BigDecimal value) {
      addCriterion("COMMISSION >", value, "commission");
      return this;
    }

    public Criteria andCommissionGreaterThanOrEqualTo(BigDecimal value) {
      addCriterion("COMMISSION >=", value, "commission");
      return this;
    }

    public Criteria andCommissionLessThan(BigDecimal value) {
      addCriterion("COMMISSION <", value, "commission");
      return this;
    }

    public Criteria andCommissionLessThanOrEqualTo(BigDecimal value) {
      addCriterion("COMMISSION <=", value, "commission");
      return this;
    }

    public Criteria andCommissionIn(List values) {
      addCriterion("COMMISSION in", values, "commission");
      return this;
    }

    public Criteria andCommissionNotIn(List values) {
      addCriterion("COMMISSION not in", values, "commission");
      return this;
    }

    public Criteria andCommissionBetween(BigDecimal value1, BigDecimal value2) {
      addCriterion("COMMISSION between", value1, value2, "commission");
      return this;
    }

    public Criteria andCommissionNotBetween(BigDecimal value1, BigDecimal value2) {
      addCriterion("COMMISSION not between", value1, value2, "commission");
      return this;
    }

    public Criteria andSettleAccountsIsNull() {
      addCriterion("SETTLE_ACCOUNTS is null");
      return this;
    }

    public Criteria andSettleAccountsIsNotNull() {
      addCriterion("SETTLE_ACCOUNTS is not null");
      return this;
    }

    public Criteria andSettleAccountsEqualTo(BigDecimal value) {
      addCriterion("SETTLE_ACCOUNTS =", value, "settleAccounts");
      return this;
    }

    public Criteria andSettleAccountsNotEqualTo(BigDecimal value) {
      addCriterion("SETTLE_ACCOUNTS <>", value, "settleAccounts");
      return this;
    }

    public Criteria andSettleAccountsGreaterThan(BigDecimal value) {
      addCriterion("SETTLE_ACCOUNTS >", value, "settleAccounts");
      return this;
    }

    public Criteria andSettleAccountsGreaterThanOrEqualTo(BigDecimal value) {
      addCriterion("SETTLE_ACCOUNTS >=", value, "settleAccounts");
      return this;
    }

    public Criteria andSettleAccountsLessThan(BigDecimal value) {
      addCriterion("SETTLE_ACCOUNTS <", value, "settleAccounts");
      return this;
    }

    public Criteria andSettleAccountsLessThanOrEqualTo(BigDecimal value) {
      addCriterion("SETTLE_ACCOUNTS <=", value, "settleAccounts");
      return this;
    }

    public Criteria andSettleAccountsIn(List values) {
      addCriterion("SETTLE_ACCOUNTS in", values, "settleAccounts");
      return this;
    }

    public Criteria andSettleAccountsNotIn(List values) {
      addCriterion("SETTLE_ACCOUNTS not in", values, "settleAccounts");
      return this;
    }

    public Criteria andSettleAccountsBetween(BigDecimal value1, BigDecimal value2) {
      addCriterion("SETTLE_ACCOUNTS between", value1, value2, "settleAccounts");
      return this;
    }

    public Criteria andSettleAccountsNotBetween(BigDecimal value1, BigDecimal value2) {
      addCriterion("SETTLE_ACCOUNTS not between", value1, value2, "settleAccounts");
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
