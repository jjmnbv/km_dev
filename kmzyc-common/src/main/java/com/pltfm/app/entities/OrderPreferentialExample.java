package com.pltfm.app.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unchecked")
public class OrderPreferentialExample implements Serializable {
  private static final long serialVersionUID = 1L;
  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database table
   * KMORDER.ORDER_PREFERENTIAL
   * 
   * @ibatorgenerated Mon Jul 29 11:23:58 CST 2013
   */
  protected String orderByClause;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database table
   * KMORDER.ORDER_PREFERENTIAL
   * 
   * @ibatorgenerated Mon Jul 29 11:23:58 CST 2013
   */
  protected List oredCriteria;

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table KMORDER.ORDER_PREFERENTIAL
   * 
   * @ibatorgenerated Mon Jul 29 11:23:58 CST 2013
   */
  public OrderPreferentialExample() {
    oredCriteria = new ArrayList();
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table KMORDER.ORDER_PREFERENTIAL
   * 
   * @ibatorgenerated Mon Jul 29 11:23:58 CST 2013
   */
  protected OrderPreferentialExample(OrderPreferentialExample example) {
    this.orderByClause = example.orderByClause;
    this.oredCriteria = example.oredCriteria;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table KMORDER.ORDER_PREFERENTIAL
   * 
   * @ibatorgenerated Mon Jul 29 11:23:58 CST 2013
   */
  public void setOrderByClause(String orderByClause) {
    this.orderByClause = orderByClause;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table KMORDER.ORDER_PREFERENTIAL
   * 
   * @ibatorgenerated Mon Jul 29 11:23:58 CST 2013
   */
  public String getOrderByClause() {
    return orderByClause;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table KMORDER.ORDER_PREFERENTIAL
   * 
   * @ibatorgenerated Mon Jul 29 11:23:58 CST 2013
   */
  public List getOredCriteria() {
    return oredCriteria;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table KMORDER.ORDER_PREFERENTIAL
   * 
   * @ibatorgenerated Mon Jul 29 11:23:58 CST 2013
   */
  public void or(Criteria criteria) {
    oredCriteria.add(criteria);
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table KMORDER.ORDER_PREFERENTIAL
   * 
   * @ibatorgenerated Mon Jul 29 11:23:58 CST 2013
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
   * table KMORDER.ORDER_PREFERENTIAL
   * 
   * @ibatorgenerated Mon Jul 29 11:23:58 CST 2013
   */
  protected Criteria createCriteriaInternal() {
    Criteria criteria = new Criteria();
    return criteria;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table KMORDER.ORDER_PREFERENTIAL
   * 
   * @ibatorgenerated Mon Jul 29 11:23:58 CST 2013
   */
  public void clear() {
    oredCriteria.clear();
  }

  /**
   * This class was generated by Apache iBATIS ibator. This class corresponds to the database table
   * KMORDER.ORDER_PREFERENTIAL
   * 
   * @ibatorgenerated Mon Jul 29 11:23:58 CST 2013
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

    public Criteria andOrder_preferential_idIsNull() {
      addCriterion("ORDER_PREFERENTIAL_ID is null");
      return this;
    }

    public Criteria andOrder_preferential_idIsNotNull() {
      addCriterion("ORDER_PREFERENTIAL_ID is not null");
      return this;
    }

    public Criteria andOrder_preferential_idEqualTo(long value) {
      addCriterion("ORDER_PREFERENTIAL_ID =", new Long(value), "order_preferential_id");
      return this;
    }

    public Criteria andOrder_preferential_idNotEqualTo(long value) {
      addCriterion("ORDER_PREFERENTIAL_ID <>", new Long(value), "order_preferential_id");
      return this;
    }

    public Criteria andOrder_preferential_idGreaterThan(long value) {
      addCriterion("ORDER_PREFERENTIAL_ID >", new Long(value), "order_preferential_id");
      return this;
    }

    public Criteria andOrder_preferential_idGreaterThanOrEqualTo(long value) {
      addCriterion("ORDER_PREFERENTIAL_ID >=", new Long(value), "order_preferential_id");
      return this;
    }

    public Criteria andOrder_preferential_idLessThan(long value) {
      addCriterion("ORDER_PREFERENTIAL_ID <", new Long(value), "order_preferential_id");
      return this;
    }

    public Criteria andOrder_preferential_idLessThanOrEqualTo(long value) {
      addCriterion("ORDER_PREFERENTIAL_ID <=", new Long(value), "order_preferential_id");
      return this;
    }

    public Criteria andOrder_preferential_idIn(List values) {
      addCriterion("ORDER_PREFERENTIAL_ID in", values, "order_preferential_id");
      return this;
    }

    public Criteria andOrder_preferential_idNotIn(List values) {
      addCriterion("ORDER_PREFERENTIAL_ID not in", values, "order_preferential_id");
      return this;
    }

    public Criteria andOrder_preferential_idBetween(long value1, long value2) {
      addCriterion("ORDER_PREFERENTIAL_ID between", new Long(value1), new Long(value2),
          "order_preferential_id");
      return this;
    }

    public Criteria andOrder_preferential_idNotBetween(long value1, long value2) {
      addCriterion("ORDER_PREFERENTIAL_ID not between", new Long(value1), new Long(value2),
          "order_preferential_id");
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

    public Criteria andOrderItemIdIsNull() {
      addCriterion("ORDER_ITEM_ID is null");
      return this;
    }

    public Criteria andOrderItemIdIsNotNull() {
      addCriterion("ORDER_ITEM_ID is not null");
      return this;
    }

    public Criteria andOrderItemIdEqualTo(Long value) {
      addCriterion("ORDER_ITEM_ID =", value, "orderItemId");
      return this;
    }

    public Criteria andOrderItemIdNotEqualTo(Long value) {
      addCriterion("ORDER_ITEM_ID <>", value, "orderItemId");
      return this;
    }

    public Criteria andOrderItemIdGreaterThan(Long value) {
      addCriterion("ORDER_ITEM_ID >", value, "orderItemId");
      return this;
    }

    public Criteria andOrderItemIdGreaterThanOrEqualTo(Long value) {
      addCriterion("ORDER_ITEM_ID >=", value, "orderItemId");
      return this;
    }

    public Criteria andOrderItemIdLessThan(Long value) {
      addCriterion("ORDER_ITEM_ID <", value, "orderItemId");
      return this;
    }

    public Criteria andOrderItemIdLessThanOrEqualTo(Long value) {
      addCriterion("ORDER_ITEM_ID <=", value, "orderItemId");
      return this;
    }

    public Criteria andOrderItemIdIn(List values) {
      addCriterion("ORDER_ITEM_ID in", values, "orderItemId");
      return this;
    }

    public Criteria andOrderItemIdNotIn(List values) {
      addCriterion("ORDER_ITEM_ID not in", values, "orderItemId");
      return this;
    }

    public Criteria andOrderItemIdBetween(Long value1, Long value2) {
      addCriterion("ORDER_ITEM_ID between", value1, value2, "orderItemId");
      return this;
    }

    public Criteria andOrderItemIdNotBetween(Long value1, Long value2) {
      addCriterion("ORDER_ITEM_ID not between", value1, value2, "orderItemId");
      return this;
    }

    public Criteria andOrderPreferentialTypeIsNull() {
      addCriterion("ORDER_PREFERENTIAL_TYPE is null");
      return this;
    }

    public Criteria andOrderPreferentialTypeIsNotNull() {
      addCriterion("ORDER_PREFERENTIAL_TYPE is not null");
      return this;
    }

    public Criteria andOrderPreferentialTypeEqualTo(Long value) {
      addCriterion("ORDER_PREFERENTIAL_TYPE =", value, "orderPreferentialType");
      return this;
    }

    public Criteria andOrderPreferentialTypeNotEqualTo(Long value) {
      addCriterion("ORDER_PREFERENTIAL_TYPE <>", value, "orderPreferentialType");
      return this;
    }

    public Criteria andOrderPreferentialTypeGreaterThan(Long value) {
      addCriterion("ORDER_PREFERENTIAL_TYPE >", value, "orderPreferentialType");
      return this;
    }

    public Criteria andOrderPreferentialTypeGreaterThanOrEqualTo(Long value) {
      addCriterion("ORDER_PREFERENTIAL_TYPE >=", value, "orderPreferentialType");
      return this;
    }

    public Criteria andOrderPreferentialTypeLessThan(Long value) {
      addCriterion("ORDER_PREFERENTIAL_TYPE <", value, "orderPreferentialType");
      return this;
    }

    public Criteria andOrderPreferentialTypeLessThanOrEqualTo(Long value) {
      addCriterion("ORDER_PREFERENTIAL_TYPE <=", value, "orderPreferentialType");
      return this;
    }

    public Criteria andOrderPreferentialTypeIn(List values) {
      addCriterion("ORDER_PREFERENTIAL_TYPE in", values, "orderPreferentialType");
      return this;
    }

    public Criteria andOrderPreferentialTypeNotIn(List values) {
      addCriterion("ORDER_PREFERENTIAL_TYPE not in", values, "orderPreferentialType");
      return this;
    }

    public Criteria andOrderPreferentialTypeBetween(Long value1, Long value2) {
      addCriterion("ORDER_PREFERENTIAL_TYPE between", value1, value2, "orderPreferentialType");
      return this;
    }

    public Criteria andOrderPreferentialTypeNotBetween(Long value1, Long value2) {
      addCriterion("ORDER_PREFERENTIAL_TYPE not between", value1, value2, "orderPreferentialType");
      return this;
    }

    public Criteria andOrderPreferentialSourceIsNull() {
      addCriterion("ORDER_PREFERENTIAL_SOURCE is null");
      return this;
    }

    public Criteria andOrderPreferentialSourceIsNotNull() {
      addCriterion("ORDER_PREFERENTIAL_SOURCE is not null");
      return this;
    }

    public Criteria andOrderPreferentialSourceEqualTo(Long value) {
      addCriterion("ORDER_PREFERENTIAL_SOURCE =", value, "orderPreferentialSource");
      return this;
    }

    public Criteria andOrderPreferentialSourceNotEqualTo(Long value) {
      addCriterion("ORDER_PREFERENTIAL_SOURCE <>", value, "orderPreferentialSource");
      return this;
    }

    public Criteria andOrderPreferentialSourceGreaterThan(Long value) {
      addCriterion("ORDER_PREFERENTIAL_SOURCE >", value, "orderPreferentialSource");
      return this;
    }

    public Criteria andOrderPreferentialSourceGreaterThanOrEqualTo(Long value) {
      addCriterion("ORDER_PREFERENTIAL_SOURCE >=", value, "orderPreferentialSource");
      return this;
    }

    public Criteria andOrderPreferentialSourceLessThan(Long value) {
      addCriterion("ORDER_PREFERENTIAL_SOURCE <", value, "orderPreferentialSource");
      return this;
    }

    public Criteria andOrderPreferentialSourceLessThanOrEqualTo(Long value) {
      addCriterion("ORDER_PREFERENTIAL_SOURCE <=", value, "orderPreferentialSource");
      return this;
    }

    public Criteria andOrderPreferentialSourceIn(List values) {
      addCriterion("ORDER_PREFERENTIAL_SOURCE in", values, "orderPreferentialSource");
      return this;
    }

    public Criteria andOrderPreferentialSourceNotIn(List values) {
      addCriterion("ORDER_PREFERENTIAL_SOURCE not in", values, "orderPreferentialSource");
      return this;
    }

    public Criteria andOrderPreferentialSourceBetween(Long value1, Long value2) {
      addCriterion("ORDER_PREFERENTIAL_SOURCE between", value1, value2, "orderPreferentialSource");
      return this;
    }

    public Criteria andOrderPreferentialSourceNotBetween(Long value1, Long value2) {
      addCriterion("ORDER_PREFERENTIAL_SOURCE not between", value1, value2,
          "orderPreferentialSource");
      return this;
    }

    public Criteria andOrderPreferentialCodeIsNull() {
      addCriterion("ORDER_PREFERENTIAL_CODE is null");
      return this;
    }

    public Criteria andOrderPreferentialCodeIsNotNull() {
      addCriterion("ORDER_PREFERENTIAL_CODE is not null");
      return this;
    }

    public Criteria andOrderPreferentialCodeEqualTo(String value) {
      addCriterion("ORDER_PREFERENTIAL_CODE =", value, "orderPreferentialCode");
      return this;
    }

    public Criteria andOrderPreferentialCodeNotEqualTo(String value) {
      addCriterion("ORDER_PREFERENTIAL_CODE <>", value, "orderPreferentialCode");
      return this;
    }

    public Criteria andOrderPreferentialCodeGreaterThan(String value) {
      addCriterion("ORDER_PREFERENTIAL_CODE >", value, "orderPreferentialCode");
      return this;
    }

    public Criteria andOrderPreferentialCodeGreaterThanOrEqualTo(String value) {
      addCriterion("ORDER_PREFERENTIAL_CODE >=", value, "orderPreferentialCode");
      return this;
    }

    public Criteria andOrderPreferentialCodeLessThan(String value) {
      addCriterion("ORDER_PREFERENTIAL_CODE <", value, "orderPreferentialCode");
      return this;
    }

    public Criteria andOrderPreferentialCodeLessThanOrEqualTo(String value) {
      addCriterion("ORDER_PREFERENTIAL_CODE <=", value, "orderPreferentialCode");
      return this;
    }

    public Criteria andOrderPreferentialCodeLike(String value) {
      addCriterion("ORDER_PREFERENTIAL_CODE like", value, "orderPreferentialCode");
      return this;
    }

    public Criteria andOrderPreferentialCodeNotLike(String value) {
      addCriterion("ORDER_PREFERENTIAL_CODE not like", value, "orderPreferentialCode");
      return this;
    }

    public Criteria andOrderPreferentialCodeIn(List values) {
      addCriterion("ORDER_PREFERENTIAL_CODE in", values, "orderPreferentialCode");
      return this;
    }

    public Criteria andOrderPreferentialCodeNotIn(List values) {
      addCriterion("ORDER_PREFERENTIAL_CODE not in", values, "orderPreferentialCode");
      return this;
    }

    public Criteria andOrderPreferentialCodeBetween(String value1, String value2) {
      addCriterion("ORDER_PREFERENTIAL_CODE between", value1, value2, "orderPreferentialCode");
      return this;
    }

    public Criteria andOrderPreferentialCodeNotBetween(String value1, String value2) {
      addCriterion("ORDER_PREFERENTIAL_CODE not between", value1, value2, "orderPreferentialCode");
      return this;
    }

    public Criteria andOrderPreferentialSumIsNull() {
      addCriterion("ORDER_PREFERENTIAL_SUM is null");
      return this;
    }

    public Criteria andOrderPreferentialSumIsNotNull() {
      addCriterion("ORDER_PREFERENTIAL_SUM is not null");
      return this;
    }

    public Criteria andOrderPreferentialSumEqualTo(BigDecimal value) {
      addCriterion("ORDER_PREFERENTIAL_SUM =", value, "orderPreferentialSum");
      return this;
    }

    public Criteria andOrderPreferentialSumNotEqualTo(BigDecimal value) {
      addCriterion("ORDER_PREFERENTIAL_SUM <>", value, "orderPreferentialSum");
      return this;
    }

    public Criteria andOrderPreferentialSumGreaterThan(BigDecimal value) {
      addCriterion("ORDER_PREFERENTIAL_SUM >", value, "orderPreferentialSum");
      return this;
    }

    public Criteria andOrderPreferentialSumGreaterThanOrEqualTo(BigDecimal value) {
      addCriterion("ORDER_PREFERENTIAL_SUM >=", value, "orderPreferentialSum");
      return this;
    }

    public Criteria andOrderPreferentialSumLessThan(BigDecimal value) {
      addCriterion("ORDER_PREFERENTIAL_SUM <", value, "orderPreferentialSum");
      return this;
    }

    public Criteria andOrderPreferentialSumLessThanOrEqualTo(BigDecimal value) {
      addCriterion("ORDER_PREFERENTIAL_SUM <=", value, "orderPreferentialSum");
      return this;
    }

    public Criteria andOrderPreferentialSumIn(List values) {
      addCriterion("ORDER_PREFERENTIAL_SUM in", values, "orderPreferentialSum");
      return this;
    }

    public Criteria andOrderPreferentialSumNotIn(List values) {
      addCriterion("ORDER_PREFERENTIAL_SUM not in", values, "orderPreferentialSum");
      return this;
    }

    public Criteria andOrderPreferentialSumBetween(BigDecimal value1, BigDecimal value2) {
      addCriterion("ORDER_PREFERENTIAL_SUM between", value1, value2, "orderPreferentialSum");
      return this;
    }

    public Criteria andOrderPreferentialSumNotBetween(BigDecimal value1, BigDecimal value2) {
      addCriterion("ORDER_PREFERENTIAL_SUM not between", value1, value2, "orderPreferentialSum");
      return this;
    }

    public Criteria andCouponIdIsNull() {
      addCriterion("COUPON_ID is null");
      return this;
    }

    public Criteria andCouponIdIsNotNull() {
      addCriterion("COUPON_ID is not null");
      return this;
    }

    public Criteria andCouponIdEqualTo(BigDecimal value) {
      addCriterion("COUPON_ID =", value, "couponId");
      return this;
    }

    public Criteria andCouponIdNotEqualTo(BigDecimal value) {
      addCriterion("COUPON_ID <>", value, "couponId");
      return this;
    }

    public Criteria andCouponIdGreaterThan(BigDecimal value) {
      addCriterion("COUPON_ID >", value, "couponId");
      return this;
    }

    public Criteria andCouponIdGreaterThanOrEqualTo(BigDecimal value) {
      addCriterion("COUPON_ID >=", value, "couponId");
      return this;
    }

    public Criteria andCouponIdLessThan(BigDecimal value) {
      addCriterion("COUPON_ID <", value, "couponId");
      return this;
    }

    public Criteria andCouponIdLessThanOrEqualTo(BigDecimal value) {
      addCriterion("COUPON_ID <=", value, "couponId");
      return this;
    }

    public Criteria andCouponIdIn(List values) {
      addCriterion("COUPON_ID in", values, "couponId");
      return this;
    }

    public Criteria andCouponIdNotIn(List values) {
      addCriterion("COUPON_ID not in", values, "couponId");
      return this;
    }

    public Criteria andCouponIdBetween(BigDecimal value1, BigDecimal value2) {
      addCriterion("COUPON_ID between", value1, value2, "couponId");
      return this;
    }

    public Criteria andCouponIdNotBetween(BigDecimal value1, BigDecimal value2) {
      addCriterion("COUPON_ID not between", value1, value2, "couponId");
      return this;
    }

    public Criteria andGrantIdIsNull() {
      addCriterion("GRANT_ID is null");
      return this;
    }

    public Criteria andGrantIdIsNotNull() {
      addCriterion("GRANT_ID is not null");
      return this;
    }

    public Criteria andGrantIdEqualTo(Long value) {
      addCriterion("GRANT_ID =", value, "grantId");
      return this;
    }

    public Criteria andGrantIdNotEqualTo(Long value) {
      addCriterion("GRANT_ID <>", value, "grantId");
      return this;
    }

    public Criteria andGrantIdGreaterThan(Long value) {
      addCriterion("GRANT_ID >", value, "grantId");
      return this;
    }

    public Criteria andGrantIdGreaterThanOrEqualTo(Long value) {
      addCriterion("GRANT_ID >=", value, "grantId");
      return this;
    }

    public Criteria andGrantIdLessThan(Long value) {
      addCriterion("GRANT_ID <", value, "grantId");
      return this;
    }

    public Criteria andGrantIdLessThanOrEqualTo(Long value) {
      addCriterion("GRANT_ID <=", value, "grantId");
      return this;
    }

    public Criteria andGrantIdIn(List values) {
      addCriterion("GRANT_ID in", values, "grantId");
      return this;
    }

    public Criteria andGrantIdNotIn(List values) {
      addCriterion("GRANT_ID not in", values, "grantId");
      return this;
    }

    public Criteria andGrantIdBetween(Long value1, Long value2) {
      addCriterion("GRANT_ID between", value1, value2, "grantId");
      return this;
    }

    public Criteria andGrantIdNotBetween(Long value1, Long value2) {
      addCriterion("GRANT_ID not between", value1, value2, "grantId");
      return this;
    }
  }
}
