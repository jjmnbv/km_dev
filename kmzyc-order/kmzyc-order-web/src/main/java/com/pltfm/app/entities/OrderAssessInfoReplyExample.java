package com.pltfm.app.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unchecked")
public class OrderAssessInfoReplyExample implements Serializable {
  private static final long serialVersionUID = 1L;
  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database table
   * KMORDER.ORDER_ASSESS_INFO_REPLY
   * 
   * @ibatorgenerated Thu Aug 15 16:02:11 CST 2013
   */
  protected String orderByClause;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database table
   * KMORDER.ORDER_ASSESS_INFO_REPLY
   * 
   * @ibatorgenerated Thu Aug 15 16:02:11 CST 2013
   */
  protected List oredCriteria;

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table KMORDER.ORDER_ASSESS_INFO_REPLY
   * 
   * @ibatorgenerated Thu Aug 15 16:02:11 CST 2013
   */
  public OrderAssessInfoReplyExample() {
    oredCriteria = new ArrayList();
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table KMORDER.ORDER_ASSESS_INFO_REPLY
   * 
   * @ibatorgenerated Thu Aug 15 16:02:11 CST 2013
   */
  protected OrderAssessInfoReplyExample(OrderAssessInfoReplyExample example) {
    this.orderByClause = example.orderByClause;
    this.oredCriteria = example.oredCriteria;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table KMORDER.ORDER_ASSESS_INFO_REPLY
   * 
   * @ibatorgenerated Thu Aug 15 16:02:11 CST 2013
   */
  public void setOrderByClause(String orderByClause) {
    this.orderByClause = orderByClause;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table KMORDER.ORDER_ASSESS_INFO_REPLY
   * 
   * @ibatorgenerated Thu Aug 15 16:02:11 CST 2013
   */
  public String getOrderByClause() {
    return orderByClause;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table KMORDER.ORDER_ASSESS_INFO_REPLY
   * 
   * @ibatorgenerated Thu Aug 15 16:02:11 CST 2013
   */
  public List getOredCriteria() {
    return oredCriteria;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table KMORDER.ORDER_ASSESS_INFO_REPLY
   * 
   * @ibatorgenerated Thu Aug 15 16:02:11 CST 2013
   */
  public void or(Criteria criteria) {
    oredCriteria.add(criteria);
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table KMORDER.ORDER_ASSESS_INFO_REPLY
   * 
   * @ibatorgenerated Thu Aug 15 16:02:11 CST 2013
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
   * table KMORDER.ORDER_ASSESS_INFO_REPLY
   * 
   * @ibatorgenerated Thu Aug 15 16:02:11 CST 2013
   */
  protected Criteria createCriteriaInternal() {
    Criteria criteria = new Criteria();
    return criteria;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table KMORDER.ORDER_ASSESS_INFO_REPLY
   * 
   * @ibatorgenerated Thu Aug 15 16:02:11 CST 2013
   */
  public void clear() {
    oredCriteria.clear();
  }

  /**
   * This class was generated by Apache iBATIS ibator. This class corresponds to the database table
   * KMORDER.ORDER_ASSESS_INFO_REPLY
   * 
   * @ibatorgenerated Thu Aug 15 16:02:11 CST 2013
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

    public Criteria andReplyIdIsNull() {
      addCriterion("REPLY_ID is null");
      return this;
    }

    public Criteria andReplyIdIsNotNull() {
      addCriterion("REPLY_ID is not null");
      return this;
    }

    public Criteria andReplyIdEqualTo(Long value) {
      addCriterion("REPLY_ID =", value, "replyId");
      return this;
    }

    public Criteria andReplyIdNotEqualTo(Long value) {
      addCriterion("REPLY_ID <>", value, "replyId");
      return this;
    }

    public Criteria andReplyIdGreaterThan(Long value) {
      addCriterion("REPLY_ID >", value, "replyId");
      return this;
    }

    public Criteria andReplyIdGreaterThanOrEqualTo(Long value) {
      addCriterion("REPLY_ID >=", value, "replyId");
      return this;
    }

    public Criteria andReplyIdLessThan(Long value) {
      addCriterion("REPLY_ID <", value, "replyId");
      return this;
    }

    public Criteria andReplyIdLessThanOrEqualTo(Long value) {
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

    public Criteria andReplyIdBetween(Long value1, Long value2) {
      addCriterion("REPLY_ID between", value1, value2, "replyId");
      return this;
    }

    public Criteria andReplyIdNotBetween(Long value1, Long value2) {
      addCriterion("REPLY_ID not between", value1, value2, "replyId");
      return this;
    }

    public Criteria andReplyGuestNumIsNull() {
      addCriterion("REPLY_GUEST_NUM is null");
      return this;
    }

    public Criteria andReplyGuestNumIsNotNull() {
      addCriterion("REPLY_GUEST_NUM is not null");
      return this;
    }

    public Criteria andReplyGuestNumEqualTo(String value) {
      addCriterion("REPLY_GUEST_NUM =", value, "replyGuestNum");
      return this;
    }

    public Criteria andReplyGuestNumNotEqualTo(String value) {
      addCriterion("REPLY_GUEST_NUM <>", value, "replyGuestNum");
      return this;
    }

    public Criteria andReplyGuestNumGreaterThan(String value) {
      addCriterion("REPLY_GUEST_NUM >", value, "replyGuestNum");
      return this;
    }

    public Criteria andReplyGuestNumGreaterThanOrEqualTo(String value) {
      addCriterion("REPLY_GUEST_NUM >=", value, "replyGuestNum");
      return this;
    }

    public Criteria andReplyGuestNumLessThan(String value) {
      addCriterion("REPLY_GUEST_NUM <", value, "replyGuestNum");
      return this;
    }

    public Criteria andReplyGuestNumLessThanOrEqualTo(String value) {
      addCriterion("REPLY_GUEST_NUM <=", value, "replyGuestNum");
      return this;
    }

    public Criteria andReplyGuestNumLike(String value) {
      addCriterion("REPLY_GUEST_NUM like", value, "replyGuestNum");
      return this;
    }

    public Criteria andReplyGuestNumNotLike(String value) {
      addCriterion("REPLY_GUEST_NUM not like", value, "replyGuestNum");
      return this;
    }

    public Criteria andReplyGuestNumIn(List values) {
      addCriterion("REPLY_GUEST_NUM in", values, "replyGuestNum");
      return this;
    }

    public Criteria andReplyGuestNumNotIn(List values) {
      addCriterion("REPLY_GUEST_NUM not in", values, "replyGuestNum");
      return this;
    }

    public Criteria andReplyGuestNumBetween(String value1, String value2) {
      addCriterion("REPLY_GUEST_NUM between", value1, value2, "replyGuestNum");
      return this;
    }

    public Criteria andReplyGuestNumNotBetween(String value1, String value2) {
      addCriterion("REPLY_GUEST_NUM not between", value1, value2, "replyGuestNum");
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

    public Criteria andReplyeDateIsNull() {
      addCriterion("REPLYE_DATE is null");
      return this;
    }

    public Criteria andReplyeDateIsNotNull() {
      addCriterion("REPLYE_DATE is not null");
      return this;
    }

    public Criteria andReplyeDateEqualTo(Date value) {
      addCriterionForJDBCDate("REPLYE_DATE =", value, "replyeDate");
      return this;
    }

    public Criteria andReplyeDateNotEqualTo(Date value) {
      addCriterionForJDBCDate("REPLYE_DATE <>", value, "replyeDate");
      return this;
    }

    public Criteria andReplyeDateGreaterThan(Date value) {
      addCriterionForJDBCDate("REPLYE_DATE >", value, "replyeDate");
      return this;
    }

    public Criteria andReplyeDateGreaterThanOrEqualTo(Date value) {
      addCriterionForJDBCDate("REPLYE_DATE >=", value, "replyeDate");
      return this;
    }

    public Criteria andReplyeDateLessThan(Date value) {
      addCriterionForJDBCDate("REPLYE_DATE <", value, "replyeDate");
      return this;
    }

    public Criteria andReplyeDateLessThanOrEqualTo(Date value) {
      addCriterionForJDBCDate("REPLYE_DATE <=", value, "replyeDate");
      return this;
    }

    public Criteria andReplyeDateIn(List values) {
      addCriterionForJDBCDate("REPLYE_DATE in", values, "replyeDate");
      return this;
    }

    public Criteria andReplyeDateNotIn(List values) {
      addCriterionForJDBCDate("REPLYE_DATE not in", values, "replyeDate");
      return this;
    }

    public Criteria andReplyeDateBetween(Date value1, Date value2) {
      addCriterionForJDBCDate("REPLYE_DATE between", value1, value2, "replyeDate");
      return this;
    }

    public Criteria andReplyeDateNotBetween(Date value1, Date value2) {
      addCriterionForJDBCDate("REPLYE_DATE not between", value1, value2, "replyeDate");
      return this;
    }

    public Criteria andAssessInfoIdIsNull() {
      addCriterion("ASSESS_INFO_ID is null");
      return this;
    }

    public Criteria andAssessInfoIdIsNotNull() {
      addCriterion("ASSESS_INFO_ID is not null");
      return this;
    }

    public Criteria andAssessInfoIdEqualTo(Long value) {
      addCriterion("ASSESS_INFO_ID =", value, "assessInfoId");
      return this;
    }

    public Criteria andAssessInfoIdNotEqualTo(Long value) {
      addCriterion("ASSESS_INFO_ID <>", value, "assessInfoId");
      return this;
    }

    public Criteria andAssessInfoIdGreaterThan(Long value) {
      addCriterion("ASSESS_INFO_ID >", value, "assessInfoId");
      return this;
    }

    public Criteria andAssessInfoIdGreaterThanOrEqualTo(Long value) {
      addCriterion("ASSESS_INFO_ID >=", value, "assessInfoId");
      return this;
    }

    public Criteria andAssessInfoIdLessThan(Long value) {
      addCriterion("ASSESS_INFO_ID <", value, "assessInfoId");
      return this;
    }

    public Criteria andAssessInfoIdLessThanOrEqualTo(Long value) {
      addCriterion("ASSESS_INFO_ID <=", value, "assessInfoId");
      return this;
    }

    public Criteria andAssessInfoIdIn(List values) {
      addCriterion("ASSESS_INFO_ID in", values, "assessInfoId");
      return this;
    }

    public Criteria andAssessInfoIdNotIn(List values) {
      addCriterion("ASSESS_INFO_ID not in", values, "assessInfoId");
      return this;
    }

    public Criteria andAssessInfoIdBetween(Long value1, Long value2) {
      addCriterion("ASSESS_INFO_ID between", value1, value2, "assessInfoId");
      return this;
    }

    public Criteria andAssessInfoIdNotBetween(Long value1, Long value2) {
      addCriterion("ASSESS_INFO_ID not between", value1, value2, "assessInfoId");
      return this;
    }
  }
}
