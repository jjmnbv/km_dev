package com.kmzyc.b2b.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unchecked")
public class EmailRrsInfoExample {
  /**
   * EMAIL_RRS_INFO
   */
  protected String orderByClause;

  /**
   * EMAIL_RRS_INFO
   */
  protected boolean distinct;

  /**
   * EMAIL_RRS_INFO
   */
  @SuppressWarnings("rawtypes")
  protected List oredCriteria;

  /**
   * EMAIL_RRS_INFO EmailRrsInfoExample
   */
  @SuppressWarnings("rawtypes")
  public EmailRrsInfoExample() {
    oredCriteria = new ArrayList();
  }

  /**
   * EMAIL_RRS_INFO EmailRrsInfoExample
   */
  protected EmailRrsInfoExample(EmailRrsInfoExample example) {
    this.orderByClause = example.orderByClause;
    this.oredCriteria = example.oredCriteria;
    this.distinct = example.distinct;
  }

  /**
   * EMAIL_RRS_INFO setOrderByClause
   */
  public void setOrderByClause(String orderByClause) {
    this.orderByClause = orderByClause;
  }

  /**
   * EMAIL_RRS_INFO getOrderByClause
   */
  public String getOrderByClause() {
    return orderByClause;
  }

  /**
   * EMAIL_RRS_INFO setDistinct
   */
  public void setDistinct(boolean distinct) {
    this.distinct = distinct;
  }

  /**
   * EMAIL_RRS_INFO isDistinct
   */
  public boolean isDistinct() {
    return distinct;
  }

  /**
   * EMAIL_RRS_INFO getOredCriteria
   */
  @SuppressWarnings("rawtypes")
  public List getOredCriteria() {
    return oredCriteria;
  }

  /**
   * EMAIL_RRS_INFO or
   */
  public void or(Criteria criteria) {
    oredCriteria.add(criteria);
  }

  /**
   * EMAIL_RRS_INFO or
   */
  public Criteria or() {
    Criteria criteria = createCriteriaInternal();
    oredCriteria.add(criteria);
    return criteria;
  }

  /**
   * EMAIL_RRS_INFO createCriteria
   */
  public Criteria createCriteria() {
    Criteria criteria = createCriteriaInternal();
    if (oredCriteria.size() == 0) {
      oredCriteria.add(criteria);
    }
    return criteria;
  }

  /**
   * EMAIL_RRS_INFO createCriteriaInternal
   */
  protected Criteria createCriteriaInternal() {
    Criteria criteria = new Criteria();
    return criteria;
  }

  /**
   * EMAIL_RRS_INFO clear
   */
  public void clear() {
    oredCriteria.clear();
    orderByClause = null;
    distinct = false;
  }

  /**
   * EMAIL_RRS_INFO
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

    public Criteria andEmailRrsIdIsNull() {
      addCriterion("EMAIL_RRS_ID is null");
      return (Criteria) this;
    }

    public Criteria andEmailRrsIdIsNotNull() {
      addCriterion("EMAIL_RRS_ID is not null");
      return (Criteria) this;
    }

    public Criteria andEmailRrsIdEqualTo(Integer value) {
      addCriterion("EMAIL_RRS_ID =", value, "emailRrsId");
      return (Criteria) this;
    }

    public Criteria andEmailRrsIdNotEqualTo(Integer value) {
      addCriterion("EMAIL_RRS_ID <>", value, "emailRrsId");
      return (Criteria) this;
    }

    public Criteria andEmailRrsIdGreaterThan(Integer value) {
      addCriterion("EMAIL_RRS_ID >", value, "emailRrsId");
      return (Criteria) this;
    }

    public Criteria andEmailRrsIdGreaterThanOrEqualTo(Integer value) {
      addCriterion("EMAIL_RRS_ID >=", value, "emailRrsId");
      return (Criteria) this;
    }

    public Criteria andEmailRrsIdLessThan(Integer value) {
      addCriterion("EMAIL_RRS_ID <", value, "emailRrsId");
      return (Criteria) this;
    }

    public Criteria andEmailRrsIdLessThanOrEqualTo(Integer value) {
      addCriterion("EMAIL_RRS_ID <=", value, "emailRrsId");
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

    public Criteria andEmailRrsIdBetween(Integer value1, Integer value2) {
      addCriterion("EMAIL_RRS_ID between", value1, value2, "emailRrsId");
      return (Criteria) this;
    }

    public Criteria andEmailRrsIdNotBetween(Integer value1, Integer value2) {
      addCriterion("EMAIL_RRS_ID not between", value1, value2, "emailRrsId");
      return (Criteria) this;
    }

    public Criteria andEmailRrsNameIsNull() {
      addCriterion("EMAIL_RRS_NAME is null");
      return (Criteria) this;
    }

    public Criteria andEmailRrsNameIsNotNull() {
      addCriterion("EMAIL_RRS_NAME is not null");
      return (Criteria) this;
    }

    public Criteria andEmailRrsNameEqualTo(String value) {
      addCriterion("EMAIL_RRS_NAME =", value, "emailRrsName");
      return (Criteria) this;
    }

    public Criteria andEmailRrsNameNotEqualTo(String value) {
      addCriterion("EMAIL_RRS_NAME <>", value, "emailRrsName");
      return (Criteria) this;
    }

    public Criteria andEmailRrsNameGreaterThan(String value) {
      addCriterion("EMAIL_RRS_NAME >", value, "emailRrsName");
      return (Criteria) this;
    }

    public Criteria andEmailRrsNameGreaterThanOrEqualTo(String value) {
      addCriterion("EMAIL_RRS_NAME >=", value, "emailRrsName");
      return (Criteria) this;
    }

    public Criteria andEmailRrsNameLessThan(String value) {
      addCriterion("EMAIL_RRS_NAME <", value, "emailRrsName");
      return (Criteria) this;
    }

    public Criteria andEmailRrsNameLessThanOrEqualTo(String value) {
      addCriterion("EMAIL_RRS_NAME <=", value, "emailRrsName");
      return (Criteria) this;
    }

    public Criteria andEmailRrsNameLike(String value) {
      addCriterion("EMAIL_RRS_NAME like", value, "emailRrsName");
      return (Criteria) this;
    }

    public Criteria andEmailRrsNameNotLike(String value) {
      addCriterion("EMAIL_RRS_NAME not like", value, "emailRrsName");
      return (Criteria) this;
    }

    public Criteria andEmailRrsNameIn(List values) {
      addCriterion("EMAIL_RRS_NAME in", values, "emailRrsName");
      return (Criteria) this;
    }

    public Criteria andEmailRrsNameNotIn(List values) {
      addCriterion("EMAIL_RRS_NAME not in", values, "emailRrsName");
      return (Criteria) this;
    }

    public Criteria andEmailRrsNameBetween(String value1, String value2) {
      addCriterion("EMAIL_RRS_NAME between", value1, value2, "emailRrsName");
      return (Criteria) this;
    }

    public Criteria andEmailRrsNameNotBetween(String value1, String value2) {
      addCriterion("EMAIL_RRS_NAME not between", value1, value2, "emailRrsName");
      return (Criteria) this;
    }

    public Criteria andEmailRrsParentIdIsNull() {
      addCriterion("EMAIL_RRS_PARENT_ID is null");
      return (Criteria) this;
    }

    public Criteria andEmailRrsParentIdIsNotNull() {
      addCriterion("EMAIL_RRS_PARENT_ID is not null");
      return (Criteria) this;
    }

    public Criteria andEmailRrsParentIdEqualTo(Integer value) {
      addCriterion("EMAIL_RRS_PARENT_ID =", value, "emailRrsParentId");
      return (Criteria) this;
    }

    public Criteria andEmailRrsParentIdNotEqualTo(Integer value) {
      addCriterion("EMAIL_RRS_PARENT_ID <>", value, "emailRrsParentId");
      return (Criteria) this;
    }

    public Criteria andEmailRrsParentIdGreaterThan(Integer value) {
      addCriterion("EMAIL_RRS_PARENT_ID >", value, "emailRrsParentId");
      return (Criteria) this;
    }

    public Criteria andEmailRrsParentIdGreaterThanOrEqualTo(Integer value) {
      addCriterion("EMAIL_RRS_PARENT_ID >=", value, "emailRrsParentId");
      return (Criteria) this;
    }

    public Criteria andEmailRrsParentIdLessThan(Integer value) {
      addCriterion("EMAIL_RRS_PARENT_ID <", value, "emailRrsParentId");
      return (Criteria) this;
    }

    public Criteria andEmailRrsParentIdLessThanOrEqualTo(Integer value) {
      addCriterion("EMAIL_RRS_PARENT_ID <=", value, "emailRrsParentId");
      return (Criteria) this;
    }

    public Criteria andEmailRrsParentIdIn(List values) {
      addCriterion("EMAIL_RRS_PARENT_ID in", values, "emailRrsParentId");
      return (Criteria) this;
    }

    public Criteria andEmailRrsParentIdNotIn(List values) {
      addCriterion("EMAIL_RRS_PARENT_ID not in", values, "emailRrsParentId");
      return (Criteria) this;
    }

    public Criteria andEmailRrsParentIdBetween(Integer value1, Integer value2) {
      addCriterion("EMAIL_RRS_PARENT_ID between", value1, value2, "emailRrsParentId");
      return (Criteria) this;
    }

    public Criteria andEmailRrsParentIdNotBetween(Integer value1, Integer value2) {
      addCriterion("EMAIL_RRS_PARENT_ID not between", value1, value2, "emailRrsParentId");
      return (Criteria) this;
    }

    public Criteria andIsParentIsNull() {
      addCriterion("IS_PARENT is null");
      return (Criteria) this;
    }

    public Criteria andIsParentIsNotNull() {
      addCriterion("IS_PARENT is not null");
      return (Criteria) this;
    }

    public Criteria andIsParentEqualTo(Short value) {
      addCriterion("IS_PARENT =", value, "isParent");
      return (Criteria) this;
    }

    public Criteria andIsParentNotEqualTo(Short value) {
      addCriterion("IS_PARENT <>", value, "isParent");
      return (Criteria) this;
    }

    public Criteria andIsParentGreaterThan(Short value) {
      addCriterion("IS_PARENT >", value, "isParent");
      return (Criteria) this;
    }

    public Criteria andIsParentGreaterThanOrEqualTo(Short value) {
      addCriterion("IS_PARENT >=", value, "isParent");
      return (Criteria) this;
    }

    public Criteria andIsParentLessThan(Short value) {
      addCriterion("IS_PARENT <", value, "isParent");
      return (Criteria) this;
    }

    public Criteria andIsParentLessThanOrEqualTo(Short value) {
      addCriterion("IS_PARENT <=", value, "isParent");
      return (Criteria) this;
    }

    public Criteria andIsParentIn(List values) {
      addCriterion("IS_PARENT in", values, "isParent");
      return (Criteria) this;
    }

    public Criteria andIsParentNotIn(List values) {
      addCriterion("IS_PARENT not in", values, "isParent");
      return (Criteria) this;
    }

    public Criteria andIsParentBetween(Short value1, Short value2) {
      addCriterion("IS_PARENT between", value1, value2, "isParent");
      return (Criteria) this;
    }

    public Criteria andIsParentNotBetween(Short value1, Short value2) {
      addCriterion("IS_PARENT not between", value1, value2, "isParent");
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

    public Criteria andCreatedIdEqualTo(Integer value) {
      addCriterion("CREATED_ID =", value, "createdId");
      return (Criteria) this;
    }

    public Criteria andCreatedIdNotEqualTo(Integer value) {
      addCriterion("CREATED_ID <>", value, "createdId");
      return (Criteria) this;
    }

    public Criteria andCreatedIdGreaterThan(Integer value) {
      addCriterion("CREATED_ID >", value, "createdId");
      return (Criteria) this;
    }

    public Criteria andCreatedIdGreaterThanOrEqualTo(Integer value) {
      addCriterion("CREATED_ID >=", value, "createdId");
      return (Criteria) this;
    }

    public Criteria andCreatedIdLessThan(Integer value) {
      addCriterion("CREATED_ID <", value, "createdId");
      return (Criteria) this;
    }

    public Criteria andCreatedIdLessThanOrEqualTo(Integer value) {
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

    public Criteria andCreatedIdBetween(Integer value1, Integer value2) {
      addCriterion("CREATED_ID between", value1, value2, "createdId");
      return (Criteria) this;
    }

    public Criteria andCreatedIdNotBetween(Integer value1, Integer value2) {
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

    public Criteria andModifieIdEqualTo(Integer value) {
      addCriterion("MODIFIE_ID =", value, "modifieId");
      return (Criteria) this;
    }

    public Criteria andModifieIdNotEqualTo(Integer value) {
      addCriterion("MODIFIE_ID <>", value, "modifieId");
      return (Criteria) this;
    }

    public Criteria andModifieIdGreaterThan(Integer value) {
      addCriterion("MODIFIE_ID >", value, "modifieId");
      return (Criteria) this;
    }

    public Criteria andModifieIdGreaterThanOrEqualTo(Integer value) {
      addCriterion("MODIFIE_ID >=", value, "modifieId");
      return (Criteria) this;
    }

    public Criteria andModifieIdLessThan(Integer value) {
      addCriterion("MODIFIE_ID <", value, "modifieId");
      return (Criteria) this;
    }

    public Criteria andModifieIdLessThanOrEqualTo(Integer value) {
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

    public Criteria andModifieIdBetween(Integer value1, Integer value2) {
      addCriterion("MODIFIE_ID between", value1, value2, "modifieId");
      return (Criteria) this;
    }

    public Criteria andModifieIdNotBetween(Integer value1, Integer value2) {
      addCriterion("MODIFIE_ID not between", value1, value2, "modifieId");
      return (Criteria) this;
    }

    public Criteria andDescriptionIsNull() {
      addCriterion("DESCRIPTION is null");
      return (Criteria) this;
    }

    public Criteria andDescriptionIsNotNull() {
      addCriterion("DESCRIPTION is not null");
      return (Criteria) this;
    }

    public Criteria andDescriptionEqualTo(String value) {
      addCriterion("DESCRIPTION =", value, "description");
      return (Criteria) this;
    }

    public Criteria andDescriptionNotEqualTo(String value) {
      addCriterion("DESCRIPTION <>", value, "description");
      return (Criteria) this;
    }

    public Criteria andDescriptionGreaterThan(String value) {
      addCriterion("DESCRIPTION >", value, "description");
      return (Criteria) this;
    }

    public Criteria andDescriptionGreaterThanOrEqualTo(String value) {
      addCriterion("DESCRIPTION >=", value, "description");
      return (Criteria) this;
    }

    public Criteria andDescriptionLessThan(String value) {
      addCriterion("DESCRIPTION <", value, "description");
      return (Criteria) this;
    }

    public Criteria andDescriptionLessThanOrEqualTo(String value) {
      addCriterion("DESCRIPTION <=", value, "description");
      return (Criteria) this;
    }

    public Criteria andDescriptionLike(String value) {
      addCriterion("DESCRIPTION like", value, "description");
      return (Criteria) this;
    }

    public Criteria andDescriptionNotLike(String value) {
      addCriterion("DESCRIPTION not like", value, "description");
      return (Criteria) this;
    }

    public Criteria andDescriptionIn(List values) {
      addCriterion("DESCRIPTION in", values, "description");
      return (Criteria) this;
    }

    public Criteria andDescriptionNotIn(List values) {
      addCriterion("DESCRIPTION not in", values, "description");
      return (Criteria) this;
    }

    public Criteria andDescriptionBetween(String value1, String value2) {
      addCriterion("DESCRIPTION between", value1, value2, "description");
      return (Criteria) this;
    }

    public Criteria andDescriptionNotBetween(String value1, String value2) {
      addCriterion("DESCRIPTION not between", value1, value2, "description");
      return (Criteria) this;
    }

    public Criteria andWeeklyIsNull() {
      addCriterion("WEEKLY is null");
      return (Criteria) this;
    }

    public Criteria andWeeklyIsNotNull() {
      addCriterion("WEEKLY is not null");
      return (Criteria) this;
    }

    public Criteria andWeeklyEqualTo(String value) {
      addCriterion("WEEKLY =", value, "weekly");
      return (Criteria) this;
    }

    public Criteria andWeeklyNotEqualTo(String value) {
      addCriterion("WEEKLY <>", value, "weekly");
      return (Criteria) this;
    }

    public Criteria andWeeklyGreaterThan(String value) {
      addCriterion("WEEKLY >", value, "weekly");
      return (Criteria) this;
    }

    public Criteria andWeeklyGreaterThanOrEqualTo(String value) {
      addCriterion("WEEKLY >=", value, "weekly");
      return (Criteria) this;
    }

    public Criteria andWeeklyLessThan(String value) {
      addCriterion("WEEKLY <", value, "weekly");
      return (Criteria) this;
    }

    public Criteria andWeeklyLessThanOrEqualTo(String value) {
      addCriterion("WEEKLY <=", value, "weekly");
      return (Criteria) this;
    }

    public Criteria andWeeklyLike(String value) {
      addCriterion("WEEKLY like", value, "weekly");
      return (Criteria) this;
    }

    public Criteria andWeeklyNotLike(String value) {
      addCriterion("WEEKLY not like", value, "weekly");
      return (Criteria) this;
    }

    public Criteria andWeeklyIn(List values) {
      addCriterion("WEEKLY in", values, "weekly");
      return (Criteria) this;
    }

    public Criteria andWeeklyNotIn(List values) {
      addCriterion("WEEKLY not in", values, "weekly");
      return (Criteria) this;
    }

    public Criteria andWeeklyBetween(String value1, String value2) {
      addCriterion("WEEKLY between", value1, value2, "weekly");
      return (Criteria) this;
    }

    public Criteria andWeeklyNotBetween(String value1, String value2) {
      addCriterion("WEEKLY not between", value1, value2, "weekly");
      return (Criteria) this;
    }
  }

  /**
   * EMAIL_RRS_INFO
   */
  public static class Criteria extends GeneratedCriteria {

    protected Criteria() {
      super();
    }
  }
}
