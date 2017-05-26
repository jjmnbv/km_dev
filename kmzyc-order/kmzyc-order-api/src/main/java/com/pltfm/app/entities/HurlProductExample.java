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
public class HurlProductExample implements Serializable {
  private static final long serialVersionUID = 1L;

  protected String orderByClause;
  protected List oredCriteria;

  /**
   * 分页条件
   */
  private Integer startIndex = 0;
  private Integer endIndex = 20;

  public HurlProductExample() {
    oredCriteria = new ArrayList();
  }

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

  protected HurlProductExample(HurlProductExample example) {
    this.orderByClause = example.orderByClause;
    this.oredCriteria = example.oredCriteria;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table HURL_PRODUCT
   * 
   * @ibatorgenerated Wed Apr 15 10:27:20 CST 2015
   */
  public void setOrderByClause(String orderByClause) {
    this.orderByClause = orderByClause;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table HURL_PRODUCT
   * 
   * @ibatorgenerated Wed Apr 15 10:27:20 CST 2015
   */
  public String getOrderByClause() {
    return orderByClause;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table HURL_PRODUCT
   * 
   * @ibatorgenerated Wed Apr 15 10:27:20 CST 2015
   */
  public List getOredCriteria() {
    return oredCriteria;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table HURL_PRODUCT
   * 
   * @ibatorgenerated Wed Apr 15 10:27:20 CST 2015
   */
  public void or(Criteria criteria) {
    oredCriteria.add(criteria);
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table HURL_PRODUCT
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
   * table HURL_PRODUCT
   * 
   * @ibatorgenerated Wed Apr 15 10:27:20 CST 2015
   */
  protected Criteria createCriteriaInternal() {
    Criteria criteria = new Criteria();
    return criteria;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table HURL_PRODUCT
   * 
   * @ibatorgenerated Wed Apr 15 10:27:20 CST 2015
   */
  public void clear() {
    oredCriteria.clear();
  }

  /**
   * This class was generated by Apache iBATIS ibator. This class corresponds to the database table
   * HURL_PRODUCT
   * 
   * @ibatorgenerated Wed Apr 15 10:27:20 CST 2015
   */
  public static class Criteria {
    protected List criteriaWithoutValue;
    protected List criteriaWithSingleValue;
    protected List criteriaWithListValue;
    protected List criteriaWithBetweenValue;

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

    public Criteria andSettlementHurlIdIsNull() {
      addCriterion("SETTLEMENT_HURL_ID is null");
      return this;
    }

    public Criteria andSettlementHurlIdIsNotNull() {
      addCriterion("SETTLEMENT_HURL_ID is not null");
      return this;
    }

    public Criteria andSettlementHurlIdEqualTo(Long value) {
      addCriterion("SETTLEMENT_HURL_ID =", value, "settlementHurlId");
      return this;
    }

    public Criteria andSettlementHurlIdNotEqualTo(Long value) {
      addCriterion("SETTLEMENT_HURL_ID <>", value, "settlementHurlId");
      return this;
    }

    public Criteria andSettlementHurlIdGreaterThan(Long value) {
      addCriterion("SETTLEMENT_HURL_ID >", value, "settlementHurlId");
      return this;
    }

    public Criteria andSettlementHurlIdGreaterThanOrEqualTo(Long value) {
      addCriterion("SETTLEMENT_HURL_ID >=", value, "settlementHurlId");
      return this;
    }

    public Criteria andSettlementHurlIdLessThan(Long value) {
      addCriterion("SETTLEMENT_HURL_ID <", value, "settlementHurlId");
      return this;
    }

    public Criteria andSettlementHurlIdLessThanOrEqualTo(Long value) {
      addCriterion("SETTLEMENT_HURL_ID <=", value, "settlementHurlId");
      return this;
    }

    public Criteria andSettlementHurlIdIn(List values) {
      addCriterion("SETTLEMENT_HURL_ID in", values, "settlementHurlId");
      return this;
    }

    public Criteria andSettlementHurlIdNotIn(List values) {
      addCriterion("SETTLEMENT_HURL_ID not in", values, "settlementHurlId");
      return this;
    }

    public Criteria andSettlementHurlIdBetween(Long value1, Long value2) {
      addCriterion("SETTLEMENT_HURL_ID between", value1, value2, "settlementHurlId");
      return this;
    }

    public Criteria andSettlementHurlIdNotBetween(Long value1, Long value2) {
      addCriterion("SETTLEMENT_HURL_ID not between", value1, value2, "settlementHurlId");
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

    public Criteria andProductTileIsNull() {
      addCriterion("PRODUCT_TILE is null");
      return this;
    }

    public Criteria andProductTileIsNotNull() {
      addCriterion("PRODUCT_TILE is not null");
      return this;
    }

    public Criteria andProductTileEqualTo(String value) {
      addCriterion("PRODUCT_TILE =", value, "productTile");
      return this;
    }

    public Criteria andProductTileNotEqualTo(String value) {
      addCriterion("PRODUCT_TILE <>", value, "productTile");
      return this;
    }

    public Criteria andProductTileGreaterThan(String value) {
      addCriterion("PRODUCT_TILE >", value, "productTile");
      return this;
    }

    public Criteria andProductTileGreaterThanOrEqualTo(String value) {
      addCriterion("PRODUCT_TILE >=", value, "productTile");
      return this;
    }

    public Criteria andProductTileLessThan(String value) {
      addCriterion("PRODUCT_TILE <", value, "productTile");
      return this;
    }

    public Criteria andProductTileLessThanOrEqualTo(String value) {
      addCriterion("PRODUCT_TILE <=", value, "productTile");
      return this;
    }

    public Criteria andProductTileLike(String value) {
      addCriterion("PRODUCT_TILE like", value, "productTile");
      return this;
    }

    public Criteria andProductTileNotLike(String value) {
      addCriterion("PRODUCT_TILE not like", value, "productTile");
      return this;
    }

    public Criteria andProductTileIn(List values) {
      addCriterion("PRODUCT_TILE in", values, "productTile");
      return this;
    }

    public Criteria andProductTileNotIn(List values) {
      addCriterion("PRODUCT_TILE not in", values, "productTile");
      return this;
    }

    public Criteria andProductTileBetween(String value1, String value2) {
      addCriterion("PRODUCT_TILE between", value1, value2, "productTile");
      return this;
    }

    public Criteria andProductTileNotBetween(String value1, String value2) {
      addCriterion("PRODUCT_TILE not between", value1, value2, "productTile");
      return this;
    }

    public Criteria andInSuitIsNull() {
      addCriterion("IN_SUIT is null");
      return this;
    }

    public Criteria andInSuitIsNotNull() {
      addCriterion("IN_SUIT is not null");
      return this;
    }

    public Criteria andInSuitEqualTo(Short value) {
      addCriterion("IN_SUIT =", value, "inSuit");
      return this;
    }

    public Criteria andInSuitNotEqualTo(Short value) {
      addCriterion("IN_SUIT <>", value, "inSuit");
      return this;
    }

    public Criteria andInSuitGreaterThan(Short value) {
      addCriterion("IN_SUIT >", value, "inSuit");
      return this;
    }

    public Criteria andInSuitGreaterThanOrEqualTo(Short value) {
      addCriterion("IN_SUIT >=", value, "inSuit");
      return this;
    }

    public Criteria andInSuitLessThan(Short value) {
      addCriterion("IN_SUIT <", value, "inSuit");
      return this;
    }

    public Criteria andInSuitLessThanOrEqualTo(Short value) {
      addCriterion("IN_SUIT <=", value, "inSuit");
      return this;
    }

    public Criteria andInSuitIn(List values) {
      addCriterion("IN_SUIT in", values, "inSuit");
      return this;
    }

    public Criteria andInSuitNotIn(List values) {
      addCriterion("IN_SUIT not in", values, "inSuit");
      return this;
    }

    public Criteria andInSuitBetween(Short value1, Short value2) {
      addCriterion("IN_SUIT between", value1, value2, "inSuit");
      return this;
    }

    public Criteria andInSuitNotBetween(Short value1, Short value2) {
      addCriterion("IN_SUIT not between", value1, value2, "inSuit");
      return this;
    }

    public Criteria andCommodityNumberIsNull() {
      addCriterion("COMMODITY_NUMBER is null");
      return this;
    }

    public Criteria andCommodityNumberIsNotNull() {
      addCriterion("COMMODITY_NUMBER is not null");
      return this;
    }

    public Criteria andCommodityNumberEqualTo(Integer value) {
      addCriterion("COMMODITY_NUMBER =", value, "commodityNumber");
      return this;
    }

    public Criteria andCommodityNumberNotEqualTo(Integer value) {
      addCriterion("COMMODITY_NUMBER <>", value, "commodityNumber");
      return this;
    }

    public Criteria andCommodityNumberGreaterThan(Integer value) {
      addCriterion("COMMODITY_NUMBER >", value, "commodityNumber");
      return this;
    }

    public Criteria andCommodityNumberGreaterThanOrEqualTo(Integer value) {
      addCriterion("COMMODITY_NUMBER >=", value, "commodityNumber");
      return this;
    }

    public Criteria andCommodityNumberLessThan(Integer value) {
      addCriterion("COMMODITY_NUMBER <", value, "commodityNumber");
      return this;
    }

    public Criteria andCommodityNumberLessThanOrEqualTo(Integer value) {
      addCriterion("COMMODITY_NUMBER <=", value, "commodityNumber");
      return this;
    }

    public Criteria andCommodityNumberIn(List values) {
      addCriterion("COMMODITY_NUMBER in", values, "commodityNumber");
      return this;
    }

    public Criteria andCommodityNumberNotIn(List values) {
      addCriterion("COMMODITY_NUMBER not in", values, "commodityNumber");
      return this;
    }

    public Criteria andCommodityNumberBetween(Integer value1, Integer value2) {
      addCriterion("COMMODITY_NUMBER between", value1, value2, "commodityNumber");
      return this;
    }

    public Criteria andCommodityNumberNotBetween(Integer value1, Integer value2) {
      addCriterion("COMMODITY_NUMBER not between", value1, value2, "commodityNumber");
      return this;
    }

    public Criteria andReceiveSubIsNull() {
      addCriterion("RECEIVE_SUB is null");
      return this;
    }

    public Criteria andReceiveSubIsNotNull() {
      addCriterion("RECEIVE_SUB is not null");
      return this;
    }

    public Criteria andReceiveSubEqualTo(BigDecimal value) {
      addCriterion("RECEIVE_SUB =", value, "receiveSub");
      return this;
    }

    public Criteria andReceiveSubNotEqualTo(BigDecimal value) {
      addCriterion("RECEIVE_SUB <>", value, "receiveSub");
      return this;
    }

    public Criteria andReceiveSubGreaterThan(BigDecimal value) {
      addCriterion("RECEIVE_SUB >", value, "receiveSub");
      return this;
    }

    public Criteria andReceiveSubGreaterThanOrEqualTo(BigDecimal value) {
      addCriterion("RECEIVE_SUB >=", value, "receiveSub");
      return this;
    }

    public Criteria andReceiveSubLessThan(BigDecimal value) {
      addCriterion("RECEIVE_SUB <", value, "receiveSub");
      return this;
    }

    public Criteria andReceiveSubLessThanOrEqualTo(BigDecimal value) {
      addCriterion("RECEIVE_SUB <=", value, "receiveSub");
      return this;
    }

    public Criteria andReceiveSubIn(List values) {
      addCriterion("RECEIVE_SUB in", values, "receiveSub");
      return this;
    }

    public Criteria andReceiveSubNotIn(List values) {
      addCriterion("RECEIVE_SUB not in", values, "receiveSub");
      return this;
    }

    public Criteria andReceiveSubBetween(BigDecimal value1, BigDecimal value2) {
      addCriterion("RECEIVE_SUB between", value1, value2, "receiveSub");
      return this;
    }

    public Criteria andReceiveSubNotBetween(BigDecimal value1, BigDecimal value2) {
      addCriterion("RECEIVE_SUB not between", value1, value2, "receiveSub");
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
