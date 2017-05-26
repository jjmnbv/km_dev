package com.pltfm.app.vobject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class RankExample {
  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database table
   * RANK
   *
   * @ibatorgenerated Fri Jul 12 14:08:27 CST 2013
   */
  protected String orderByClause;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database table
   * RANK
   *
   * @ibatorgenerated Fri Jul 12 14:08:27 CST 2013
   */
  protected List oredCriteria;

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table RANK
   *
   * @ibatorgenerated Fri Jul 12 14:08:27 CST 2013
   */
  public RankExample() {
    oredCriteria = new ArrayList();
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table RANK
   *
   * @ibatorgenerated Fri Jul 12 14:08:27 CST 2013
   */
  protected RankExample(RankExample example) {
    this.orderByClause = example.orderByClause;
    this.oredCriteria = example.oredCriteria;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table RANK
   *
   * @ibatorgenerated Fri Jul 12 14:08:27 CST 2013
   */
  public void setOrderByClause(String orderByClause) {
    this.orderByClause = orderByClause;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table RANK
   *
   * @ibatorgenerated Fri Jul 12 14:08:27 CST 2013
   */
  public String getOrderByClause() {
    return orderByClause;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table RANK
   *
   * @ibatorgenerated Fri Jul 12 14:08:27 CST 2013
   */
  public List getOredCriteria() {
    return oredCriteria;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table RANK
   *
   * @ibatorgenerated Fri Jul 12 14:08:27 CST 2013
   */
  public void or(Criteria criteria) {
    oredCriteria.add(criteria);
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table RANK
   *
   * @ibatorgenerated Fri Jul 12 14:08:27 CST 2013
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
   * table RANK
   *
   * @ibatorgenerated Fri Jul 12 14:08:27 CST 2013
   */
  protected Criteria createCriteriaInternal() {
    Criteria criteria = new Criteria();
    return criteria;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table RANK
   *
   * @ibatorgenerated Fri Jul 12 14:08:27 CST 2013
   */
  public void clear() {
    oredCriteria.clear();
  }

  /**
   * This class was generated by Apache iBATIS ibator. This class corresponds to the database table
   * RANK
   *
   * @ibatorgenerated Fri Jul 12 14:08:27 CST 2013
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

    public Criteria andNRankIdIsNull() {
      addCriterion("N_RANK_ID is null");
      return this;
    }

    public Criteria andNRankIdIsNotNull() {
      addCriterion("N_RANK_ID is not null");
      return this;
    }

    public Criteria andNRankIdEqualTo(BigDecimal value) {
      addCriterion("N_RANK_ID =", value, "nRankId");
      return this;
    }

    public Criteria andNRankIdNotEqualTo(BigDecimal value) {
      addCriterion("N_RANK_ID <>", value, "nRankId");
      return this;
    }

    public Criteria andNRankIdGreaterThan(BigDecimal value) {
      addCriterion("N_RANK_ID >", value, "nRankId");
      return this;
    }

    public Criteria andNRankIdGreaterThanOrEqualTo(BigDecimal value) {
      addCriterion("N_RANK_ID >=", value, "nRankId");
      return this;
    }

    public Criteria andNRankIdLessThan(BigDecimal value) {
      addCriterion("N_RANK_ID <", value, "nRankId");
      return this;
    }

    public Criteria andNRankIdLessThanOrEqualTo(BigDecimal value) {
      addCriterion("N_RANK_ID <=", value, "nRankId");
      return this;
    }

    public Criteria andNRankIdIn(List values) {
      addCriterion("N_RANK_ID in", values, "nRankId");
      return this;
    }

    public Criteria andNRankIdNotIn(List values) {
      addCriterion("N_RANK_ID not in", values, "nRankId");
      return this;
    }

    public Criteria andNRankIdBetween(BigDecimal value1, BigDecimal value2) {
      addCriterion("N_RANK_ID between", value1, value2, "nRankId");
      return this;
    }

    public Criteria andNRankIdNotBetween(BigDecimal value1, BigDecimal value2) {
      addCriterion("N_RANK_ID not between", value1, value2, "nRankId");
      return this;
    }

    public Criteria andNCustomerTypeIdIsNull() {
      addCriterion("N_CUSTOMER_TYPE_ID is null");
      return this;
    }

    public Criteria andNCustomerTypeIdIsNotNull() {
      addCriterion("N_CUSTOMER_TYPE_ID is not null");
      return this;
    }

    public Criteria andNCustomerTypeIdEqualTo(BigDecimal value) {
      addCriterion("N_CUSTOMER_TYPE_ID =", value, "nCustomerTypeId");
      return this;
    }

    public Criteria andNCustomerTypeIdNotEqualTo(BigDecimal value) {
      addCriterion("N_CUSTOMER_TYPE_ID <>", value, "nCustomerTypeId");
      return this;
    }

    public Criteria andNCustomerTypeIdGreaterThan(BigDecimal value) {
      addCriterion("N_CUSTOMER_TYPE_ID >", value, "nCustomerTypeId");
      return this;
    }

    public Criteria andNCustomerTypeIdGreaterThanOrEqualTo(BigDecimal value) {
      addCriterion("N_CUSTOMER_TYPE_ID >=", value, "nCustomerTypeId");
      return this;
    }

    public Criteria andNCustomerTypeIdLessThan(BigDecimal value) {
      addCriterion("N_CUSTOMER_TYPE_ID <", value, "nCustomerTypeId");
      return this;
    }

    public Criteria andNCustomerTypeIdLessThanOrEqualTo(BigDecimal value) {
      addCriterion("N_CUSTOMER_TYPE_ID <=", value, "nCustomerTypeId");
      return this;
    }

    public Criteria andNCustomerTypeIdIn(List values) {
      addCriterion("N_CUSTOMER_TYPE_ID in", values, "nCustomerTypeId");
      return this;
    }

    public Criteria andNCustomerTypeIdNotIn(List values) {
      addCriterion("N_CUSTOMER_TYPE_ID not in", values, "nCustomerTypeId");
      return this;
    }

    public Criteria andNCustomerTypeIdBetween(BigDecimal value1, BigDecimal value2) {
      addCriterion("N_CUSTOMER_TYPE_ID between", value1, value2, "nCustomerTypeId");
      return this;
    }

    public Criteria andNCustomerTypeIdNotBetween(BigDecimal value1, BigDecimal value2) {
      addCriterion("N_CUSTOMER_TYPE_ID not between", value1, value2, "nCustomerTypeId");
      return this;
    }

    public Criteria andCodeIsNull() {
      addCriterion("CODE is null");
      return this;
    }

    public Criteria andCodeIsNotNull() {
      addCriterion("CODE is not null");
      return this;
    }

    public Criteria andCodeEqualTo(String value) {
      addCriterion("CODE =", value, "code");
      return this;
    }

    public Criteria andCodeNotEqualTo(String value) {
      addCriterion("CODE <>", value, "code");
      return this;
    }

    public Criteria andCodeGreaterThan(String value) {
      addCriterion("CODE >", value, "code");
      return this;
    }

    public Criteria andCodeGreaterThanOrEqualTo(String value) {
      addCriterion("CODE >=", value, "code");
      return this;
    }

    public Criteria andCodeLessThan(String value) {
      addCriterion("CODE <", value, "code");
      return this;
    }

    public Criteria andCodeLessThanOrEqualTo(String value) {
      addCriterion("CODE <=", value, "code");
      return this;
    }

    public Criteria andCodeLike(String value) {
      addCriterion("CODE like", value, "code");
      return this;
    }

    public Criteria andCodeNotLike(String value) {
      addCriterion("CODE not like", value, "code");
      return this;
    }

    public Criteria andCodeIn(List values) {
      addCriterion("CODE in", values, "code");
      return this;
    }

    public Criteria andCodeNotIn(List values) {
      addCriterion("CODE not in", values, "code");
      return this;
    }

    public Criteria andCodeBetween(String value1, String value2) {
      addCriterion("CODE between", value1, value2, "code");
      return this;
    }

    public Criteria andCodeNotBetween(String value1, String value2) {
      addCriterion("CODE not between", value1, value2, "code");
      return this;
    }

    public Criteria andRankNameIsNull() {
      addCriterion("RANK_NAME is null");
      return this;
    }

    public Criteria andRankNameIsNotNull() {
      addCriterion("RANK_NAME is not null");
      return this;
    }

    public Criteria andRankNameEqualTo(String value) {
      addCriterion("RANK_NAME =", value, "rankName");
      return this;
    }

    public Criteria andRankNameNotEqualTo(String value) {
      addCriterion("RANK_NAME <>", value, "rankName");
      return this;
    }

    public Criteria andRankNameGreaterThan(String value) {
      addCriterion("RANK_NAME >", value, "rankName");
      return this;
    }

    public Criteria andRankNameGreaterThanOrEqualTo(String value) {
      addCriterion("RANK_NAME >=", value, "rankName");
      return this;
    }

    public Criteria andRankNameLessThan(String value) {
      addCriterion("RANK_NAME <", value, "rankName");
      return this;
    }

    public Criteria andRankNameLessThanOrEqualTo(String value) {
      addCriterion("RANK_NAME <=", value, "rankName");
      return this;
    }

    public Criteria andRankNameLike(String value) {
      addCriterion("RANK_NAME like", value, "rankName");
      return this;
    }

    public Criteria andRankNameNotLike(String value) {
      addCriterion("RANK_NAME not like", value, "rankName");
      return this;
    }

    public Criteria andRankNameIn(List values) {
      addCriterion("RANK_NAME in", values, "rankName");
      return this;
    }

    public Criteria andRankNameNotIn(List values) {
      addCriterion("RANK_NAME not in", values, "rankName");
      return this;
    }

    public Criteria andRankNameBetween(String value1, String value2) {
      addCriterion("RANK_NAME between", value1, value2, "rankName");
      return this;
    }

    public Criteria andRankNameNotBetween(String value1, String value2) {
      addCriterion("RANK_NAME not between", value1, value2, "rankName");
      return this;
    }

    public Criteria andScoreMinIsNull() {
      addCriterion("SCORE_MIN is null");
      return this;
    }

    public Criteria andScoreMinIsNotNull() {
      addCriterion("SCORE_MIN is not null");
      return this;
    }

    public Criteria andScoreMinEqualTo(BigDecimal value) {
      addCriterion("SCORE_MIN =", value, "scoreMin");
      return this;
    }

    public Criteria andScoreMinNotEqualTo(BigDecimal value) {
      addCriterion("SCORE_MIN <>", value, "scoreMin");
      return this;
    }

    public Criteria andScoreMinGreaterThan(BigDecimal value) {
      addCriterion("SCORE_MIN >", value, "scoreMin");
      return this;
    }

    public Criteria andScoreMinGreaterThanOrEqualTo(BigDecimal value) {
      addCriterion("SCORE_MIN >=", value, "scoreMin");
      return this;
    }

    public Criteria andScoreMinLessThan(BigDecimal value) {
      addCriterion("SCORE_MIN <", value, "scoreMin");
      return this;
    }

    public Criteria andScoreMinLessThanOrEqualTo(BigDecimal value) {
      addCriterion("SCORE_MIN <=", value, "scoreMin");
      return this;
    }

    public Criteria andScoreMinIn(List values) {
      addCriterion("SCORE_MIN in", values, "scoreMin");
      return this;
    }

    public Criteria andScoreMinNotIn(List values) {
      addCriterion("SCORE_MIN not in", values, "scoreMin");
      return this;
    }

    public Criteria andScoreMinBetween(BigDecimal value1, BigDecimal value2) {
      addCriterion("SCORE_MIN between", value1, value2, "scoreMin");
      return this;
    }

    public Criteria andScoreMinNotBetween(BigDecimal value1, BigDecimal value2) {
      addCriterion("SCORE_MIN not between", value1, value2, "scoreMin");
      return this;
    }

    public Criteria andScoreMaxIsNull() {
      addCriterion("SCORE_MAX is null");
      return this;
    }

    public Criteria andScoreMaxIsNotNull() {
      addCriterion("SCORE_MAX is not null");
      return this;
    }

    public Criteria andScoreMaxEqualTo(BigDecimal value) {
      addCriterion("SCORE_MAX =", value, "scoreMax");
      return this;
    }

    public Criteria andScoreMaxNotEqualTo(BigDecimal value) {
      addCriterion("SCORE_MAX <>", value, "scoreMax");
      return this;
    }

    public Criteria andScoreMaxGreaterThan(BigDecimal value) {
      addCriterion("SCORE_MAX >", value, "scoreMax");
      return this;
    }

    public Criteria andScoreMaxGreaterThanOrEqualTo(BigDecimal value) {
      addCriterion("SCORE_MAX >=", value, "scoreMax");
      return this;
    }

    public Criteria andScoreMaxLessThan(BigDecimal value) {
      addCriterion("SCORE_MAX <", value, "scoreMax");
      return this;
    }

    public Criteria andScoreMaxLessThanOrEqualTo(BigDecimal value) {
      addCriterion("SCORE_MAX <=", value, "scoreMax");
      return this;
    }

    public Criteria andScoreMaxIn(List values) {
      addCriterion("SCORE_MAX in", values, "scoreMax");
      return this;
    }

    public Criteria andScoreMaxNotIn(List values) {
      addCriterion("SCORE_MAX not in", values, "scoreMax");
      return this;
    }

    public Criteria andScoreMaxBetween(BigDecimal value1, BigDecimal value2) {
      addCriterion("SCORE_MAX between", value1, value2, "scoreMax");
      return this;
    }

    public Criteria andScoreMaxNotBetween(BigDecimal value1, BigDecimal value2) {
      addCriterion("SCORE_MAX not between", value1, value2, "scoreMax");
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
  }
}
