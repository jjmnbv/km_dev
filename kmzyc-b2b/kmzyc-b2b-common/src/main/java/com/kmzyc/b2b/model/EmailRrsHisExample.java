package com.kmzyc.b2b.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unchecked")
public class EmailRrsHisExample {
  /**
   * EMAIL_RRS_HIS
   */
  protected String orderByClause;

  /**
   * EMAIL_RRS_HIS
   */
  protected boolean distinct;

  /**
   * EMAIL_RRS_HIS
   */
  @SuppressWarnings("rawtypes")
  protected List oredCriteria;

  /**
   * EMAIL_RRS_HIS EmailRrsHisExample
   */
  @SuppressWarnings("rawtypes")
  public EmailRrsHisExample() {
    oredCriteria = new ArrayList();
  }

  /**
   * EMAIL_RRS_HIS EmailRrsHisExample
   */
  protected EmailRrsHisExample(EmailRrsHisExample example) {
    this.orderByClause = example.orderByClause;
    this.oredCriteria = example.oredCriteria;
    this.distinct = example.distinct;
  }

  /**
   * EMAIL_RRS_HIS setOrderByClause
   */
  public void setOrderByClause(String orderByClause) {
    this.orderByClause = orderByClause;
  }

  /**
   * EMAIL_RRS_HIS getOrderByClause
   */
  public String getOrderByClause() {
    return orderByClause;
  }

  /**
   * EMAIL_RRS_HIS setDistinct
   */
  public void setDistinct(boolean distinct) {
    this.distinct = distinct;
  }

  /**
   * EMAIL_RRS_HIS isDistinct
   */
  public boolean isDistinct() {
    return distinct;
  }

  /**
   * EMAIL_RRS_HIS getOredCriteria
   */
  @SuppressWarnings("rawtypes")
  public List getOredCriteria() {
    return oredCriteria;
  }

  /**
   * EMAIL_RRS_HIS or
   */
  public void or(Criteria criteria) {
    oredCriteria.add(criteria);
  }

  /**
   * EMAIL_RRS_HIS or
   */
  public Criteria or() {
    Criteria criteria = createCriteriaInternal();
    oredCriteria.add(criteria);
    return criteria;
  }

  /**
   * EMAIL_RRS_HIS createCriteria
   */
  public Criteria createCriteria() {
    Criteria criteria = createCriteriaInternal();
    if (oredCriteria.size() == 0) {
      oredCriteria.add(criteria);
    }
    return criteria;
  }

  /**
   * EMAIL_RRS_HIS createCriteriaInternal
   */
  protected Criteria createCriteriaInternal() {
    Criteria criteria = new Criteria();
    return criteria;
  }

  /**
   * EMAIL_RRS_HIS clear
   */
  public void clear() {
    oredCriteria.clear();
    orderByClause = null;
    distinct = false;
  }

  /**
   * EMAIL_RRS_HIS
   */
  @SuppressWarnings("rawtypes")
  protected abstract static class GeneratedCriteria {
    protected List criteriaWithoutValue;

    protected List criteriaWithSingleValue;

    protected List criteriaWithListValue;

    protected List criteriaWithBetweenValue;

    protected GeneratedCriteria() {
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
      if (value == null) {
        throw new RuntimeException("Value for " + property + " cannot be null");
      }
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

    public Criteria andEmailRrsHisIdIsNull() {
      addCriterion("EMAIL_RRS_HIS_ID is null");
      return (Criteria) this;
    }

    public Criteria andEmailRrsHisIdIsNotNull() {
      addCriterion("EMAIL_RRS_HIS_ID is not null");
      return (Criteria) this;
    }

    public Criteria andEmailRrsHisIdEqualTo(Long value) {
      addCriterion("EMAIL_RRS_HIS_ID =", value, "emailRrsHisId");
      return (Criteria) this;
    }

    public Criteria andEmailRrsHisIdNotEqualTo(Long value) {
      addCriterion("EMAIL_RRS_HIS_ID <>", value, "emailRrsHisId");
      return (Criteria) this;
    }

    public Criteria andEmailRrsHisIdGreaterThan(Long value) {
      addCriterion("EMAIL_RRS_HIS_ID >", value, "emailRrsHisId");
      return (Criteria) this;
    }

    public Criteria andEmailRrsHisIdGreaterThanOrEqualTo(Long value) {
      addCriterion("EMAIL_RRS_HIS_ID >=", value, "emailRrsHisId");
      return (Criteria) this;
    }

    public Criteria andEmailRrsHisIdLessThan(Long value) {
      addCriterion("EMAIL_RRS_HIS_ID <", value, "emailRrsHisId");
      return (Criteria) this;
    }

    public Criteria andEmailRrsHisIdLessThanOrEqualTo(Long value) {
      addCriterion("EMAIL_RRS_HIS_ID <=", value, "emailRrsHisId");
      return (Criteria) this;
    }

    public Criteria andEmailRrsHisIdIn(List values) {
      addCriterion("EMAIL_RRS_HIS_ID in", values, "emailRrsHisId");
      return (Criteria) this;
    }

    public Criteria andEmailRrsHisIdNotIn(List values) {
      addCriterion("EMAIL_RRS_HIS_ID not in", values, "emailRrsHisId");
      return (Criteria) this;
    }

    public Criteria andEmailRrsHisIdBetween(Long value1, Long value2) {
      addCriterion("EMAIL_RRS_HIS_ID between", value1, value2, "emailRrsHisId");
      return (Criteria) this;
    }

    public Criteria andEmailRrsHisIdNotBetween(Long value1, Long value2) {
      addCriterion("EMAIL_RRS_HIS_ID not between", value1, value2, "emailRrsHisId");
      return (Criteria) this;
    }

    public Criteria andEmailRrsIdIsNull() {
      addCriterion("EMAIL_RRS_ID is null");
      return (Criteria) this;
    }

    public Criteria andEmailRrsIdIsNotNull() {
      addCriterion("EMAIL_RRS_ID is not null");
      return (Criteria) this;
    }

    public Criteria andEmailRrsIdEqualTo(Long value) {
      addCriterion("EMAIL_RRS_ID =", value, "emailRrsId");
      return (Criteria) this;
    }

    public Criteria andEmailRrsIdNotEqualTo(Long value) {
      addCriterion("EMAIL_RRS_ID <>", value, "emailRrsId");
      return (Criteria) this;
    }

    public Criteria andEmailRrsIdGreaterThan(Long value) {
      addCriterion("EMAIL_RRS_ID >", value, "emailRrsId");
      return (Criteria) this;
    }

    public Criteria andEmailRrsIdGreaterThanOrEqualTo(Long value) {
      addCriterion("EMAIL_RRS_ID >=", value, "emailRrsId");
      return (Criteria) this;
    }

    public Criteria andEmailRrsIdLessThan(Long value) {
      addCriterion("EMAIL_RRS_ID <", value, "emailRrsId");
      return (Criteria) this;
    }

    public Criteria andEmailRrsIdLessThanOrEqualTo(Long value) {
      addCriterion("EMAIL_RRS_ID <=", value, "emailRrsId");
      return (Criteria) this;
    }

    public Criteria andEmailRrsIdLike(Long value) {
      addCriterion("EMAIL_RRS_ID like", value, "emailRrsId");
      return (Criteria) this;
    }

    public Criteria andEmailRrsIdNotLike(Long value) {
      addCriterion("EMAIL_RRS_ID not like", value, "emailRrsId");
      return (Criteria) this;
    }

    public Criteria andEmailRrsIdIn(List values) {
      addCriterion("EMAIL_RRS_ID in", values, "emailRrsId");
      return (Criteria) this;
    }

    public Criteria andEmailRrsIdNotIn(List values) {
      addCriterion("EMAIL_RRS_ID not in", values, "emailRrsId");
      return (Criteria) this;
    }

    public Criteria andEmailRrsIdBetween(Long value1, Long value2) {
      addCriterion("EMAIL_RRS_ID between", value1, value2, "emailRrsId");
      return (Criteria) this;
    }

    public Criteria andEmailRrsIdNotBetween(Long value1, Long value2) {
      addCriterion("EMAIL_RRS_ID not between", value1, value2, "emailRrsId");
      return (Criteria) this;
    }

    public Criteria andNLoginIdIsNull() {
      addCriterion("N_LOGIN_ID is null");
      return (Criteria) this;
    }

    public Criteria andNLoginIdIsNotNull() {
      addCriterion("N_LOGIN_ID is not null");
      return (Criteria) this;
    }

    public Criteria andNLoginIdEqualTo(Long value) {
      addCriterion("N_LOGIN_ID =", value, "nLoginId");
      return (Criteria) this;
    }

    public Criteria andNLoginIdNotEqualTo(Long value) {
      addCriterion("N_LOGIN_ID <>", value, "nLoginId");
      return (Criteria) this;
    }

    public Criteria andNLoginIdGreaterThan(Long value) {
      addCriterion("N_LOGIN_ID >", value, "nLoginId");
      return (Criteria) this;
    }

    public Criteria andNLoginIdGreaterThanOrEqualTo(Long value) {
      addCriterion("N_LOGIN_ID >=", value, "nLoginId");
      return (Criteria) this;
    }

    public Criteria andNLoginIdLessThan(Long value) {
      addCriterion("N_LOGIN_ID <", value, "nLoginId");
      return (Criteria) this;
    }

    public Criteria andNLoginIdLessThanOrEqualTo(Long value) {
      addCriterion("N_LOGIN_ID <=", value, "nLoginId");
      return (Criteria) this;
    }

    public Criteria andNLoginIdIn(List values) {
      addCriterion("N_LOGIN_ID in", values, "nLoginId");
      return (Criteria) this;
    }

    public Criteria andNLoginIdNotIn(List values) {
      addCriterion("N_LOGIN_ID not in", values, "nLoginId");
      return (Criteria) this;
    }

    public Criteria andNLoginIdBetween(Long value1, Long value2) {
      addCriterion("N_LOGIN_ID between", value1, value2, "nLoginId");
      return (Criteria) this;
    }

    public Criteria andNLoginIdNotBetween(Long value1, Long value2) {
      addCriterion("N_LOGIN_ID not between", value1, value2, "nLoginId");
      return (Criteria) this;
    }

    public Criteria andCreateDateIsNull() {
      addCriterion("CREATE_DATE is null");
      return (Criteria) this;
    }

    public Criteria andCreateDateIsNotNull() {
      addCriterion("CREATE_DATE is not null");
      return (Criteria) this;
    }

    public Criteria andCreateDateEqualTo(Date value) {
      addCriterionForJDBCDate("CREATE_DATE =", value, "createDate");
      return (Criteria) this;
    }

    public Criteria andCreateDateNotEqualTo(Date value) {
      addCriterionForJDBCDate("CREATE_DATE <>", value, "createDate");
      return (Criteria) this;
    }

    public Criteria andCreateDateGreaterThan(Date value) {
      addCriterionForJDBCDate("CREATE_DATE >", value, "createDate");
      return (Criteria) this;
    }

    public Criteria andCreateDateGreaterThanOrEqualTo(Date value) {
      addCriterionForJDBCDate("CREATE_DATE >=", value, "createDate");
      return (Criteria) this;
    }

    public Criteria andCreateDateLessThan(Date value) {
      addCriterionForJDBCDate("CREATE_DATE <", value, "createDate");
      return (Criteria) this;
    }

    public Criteria andCreateDateLessThanOrEqualTo(Date value) {
      addCriterionForJDBCDate("CREATE_DATE <=", value, "createDate");
      return (Criteria) this;
    }

    public Criteria andCreateDateIn(List values) {
      addCriterionForJDBCDate("CREATE_DATE in", values, "createDate");
      return (Criteria) this;
    }

    public Criteria andCreateDateNotIn(List values) {
      addCriterionForJDBCDate("CREATE_DATE not in", values, "createDate");
      return (Criteria) this;
    }

    public Criteria andCreateDateBetween(Date value1, Date value2) {
      addCriterionForJDBCDate("CREATE_DATE between", value1, value2, "createDate");
      return (Criteria) this;
    }

    public Criteria andCreateDateNotBetween(Date value1, Date value2) {
      addCriterionForJDBCDate("CREATE_DATE not between", value1, value2, "createDate");
      return (Criteria) this;
    }

    public Criteria andCreatedIdIsNull() {
      addCriterion("CREATED_ID is null");
      return (Criteria) this;
    }

    public Criteria andCreatedIdIsNotNull() {
      addCriterion("CREATED_ID is not null");
      return (Criteria) this;
    }

    public Criteria andCreatedIdEqualTo(Long value) {
      addCriterion("CREATED_ID =", value, "createdId");
      return (Criteria) this;
    }

    public Criteria andCreatedIdNotEqualTo(Long value) {
      addCriterion("CREATED_ID <>", value, "createdId");
      return (Criteria) this;
    }

    public Criteria andCreatedIdGreaterThan(Long value) {
      addCriterion("CREATED_ID >", value, "createdId");
      return (Criteria) this;
    }

    public Criteria andCreatedIdGreaterThanOrEqualTo(Long value) {
      addCriterion("CREATED_ID >=", value, "createdId");
      return (Criteria) this;
    }

    public Criteria andCreatedIdLessThan(Long value) {
      addCriterion("CREATED_ID <", value, "createdId");
      return (Criteria) this;
    }

    public Criteria andCreatedIdLessThanOrEqualTo(Long value) {
      addCriterion("CREATED_ID <=", value, "createdId");
      return (Criteria) this;
    }

    public Criteria andCreatedIdIn(List values) {
      addCriterion("CREATED_ID in", values, "createdId");
      return (Criteria) this;
    }

    public Criteria andCreatedIdNotIn(List values) {
      addCriterion("CREATED_ID not in", values, "createdId");
      return (Criteria) this;
    }

    public Criteria andCreatedIdBetween(Long value1, Long value2) {
      addCriterion("CREATED_ID between", value1, value2, "createdId");
      return (Criteria) this;
    }

    public Criteria andCreatedIdNotBetween(Long value1, Long value2) {
      addCriterion("CREATED_ID not between", value1, value2, "createdId");
      return (Criteria) this;
    }

    public Criteria andModifyDateIsNull() {
      addCriterion("MODIFY_DATE is null");
      return (Criteria) this;
    }

    public Criteria andModifyDateIsNotNull() {
      addCriterion("MODIFY_DATE is not null");
      return (Criteria) this;
    }

    public Criteria andModifyDateEqualTo(Date value) {
      addCriterionForJDBCDate("MODIFY_DATE =", value, "modifyDate");
      return (Criteria) this;
    }

    public Criteria andModifyDateNotEqualTo(Date value) {
      addCriterionForJDBCDate("MODIFY_DATE <>", value, "modifyDate");
      return (Criteria) this;
    }

    public Criteria andModifyDateGreaterThan(Date value) {
      addCriterionForJDBCDate("MODIFY_DATE >", value, "modifyDate");
      return (Criteria) this;
    }

    public Criteria andModifyDateGreaterThanOrEqualTo(Date value) {
      addCriterionForJDBCDate("MODIFY_DATE >=", value, "modifyDate");
      return (Criteria) this;
    }

    public Criteria andModifyDateLessThan(Date value) {
      addCriterionForJDBCDate("MODIFY_DATE <", value, "modifyDate");
      return (Criteria) this;
    }

    public Criteria andModifyDateLessThanOrEqualTo(Date value) {
      addCriterionForJDBCDate("MODIFY_DATE <=", value, "modifyDate");
      return (Criteria) this;
    }

    public Criteria andModifyDateIn(List values) {
      addCriterionForJDBCDate("MODIFY_DATE in", values, "modifyDate");
      return (Criteria) this;
    }

    public Criteria andModifyDateNotIn(List values) {
      addCriterionForJDBCDate("MODIFY_DATE not in", values, "modifyDate");
      return (Criteria) this;
    }

    public Criteria andModifyDateBetween(Date value1, Date value2) {
      addCriterionForJDBCDate("MODIFY_DATE between", value1, value2, "modifyDate");
      return (Criteria) this;
    }

    public Criteria andModifyDateNotBetween(Date value1, Date value2) {
      addCriterionForJDBCDate("MODIFY_DATE not between", value1, value2, "modifyDate");
      return (Criteria) this;
    }

    public Criteria andModifieIdIsNull() {
      addCriterion("MODIFIE_ID is null");
      return (Criteria) this;
    }

    public Criteria andModifieIdIsNotNull() {
      addCriterion("MODIFIE_ID is not null");
      return (Criteria) this;
    }

    public Criteria andModifieIdEqualTo(Long value) {
      addCriterion("MODIFIE_ID =", value, "modifieId");
      return (Criteria) this;
    }

    public Criteria andModifieIdNotEqualTo(Long value) {
      addCriterion("MODIFIE_ID <>", value, "modifieId");
      return (Criteria) this;
    }

    public Criteria andModifieIdGreaterThan(Long value) {
      addCriterion("MODIFIE_ID >", value, "modifieId");
      return (Criteria) this;
    }

    public Criteria andModifieIdGreaterThanOrEqualTo(Long value) {
      addCriterion("MODIFIE_ID >=", value, "modifieId");
      return (Criteria) this;
    }

    public Criteria andModifieIdLessThan(Long value) {
      addCriterion("MODIFIE_ID <", value, "modifieId");
      return (Criteria) this;
    }

    public Criteria andModifieIdLessThanOrEqualTo(Long value) {
      addCriterion("MODIFIE_ID <=", value, "modifieId");
      return (Criteria) this;
    }

    public Criteria andModifieIdIn(List values) {
      addCriterion("MODIFIE_ID in", values, "modifieId");
      return (Criteria) this;
    }

    public Criteria andModifieIdNotIn(List values) {
      addCriterion("MODIFIE_ID not in", values, "modifieId");
      return (Criteria) this;
    }

    public Criteria andModifieIdBetween(Long value1, Long value2) {
      addCriterion("MODIFIE_ID between", value1, value2, "modifieId");
      return (Criteria) this;
    }

    public Criteria andModifieIdNotBetween(Long value1, Long value2) {
      addCriterion("MODIFIE_ID not between", value1, value2, "modifieId");
      return (Criteria) this;
    }
  }

  /**
   * EMAIL_RRS_HIS
   */
  public static class Criteria extends GeneratedCriteria {

    protected Criteria() {
      super();
    }
  }
}
