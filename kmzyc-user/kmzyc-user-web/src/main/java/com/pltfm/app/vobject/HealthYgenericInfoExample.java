package com.pltfm.app.vobject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class HealthYgenericInfoExample {
  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database table
   * HEALTH_YGENERIC_INFO
   *
   * @ibatorgenerated Thu Jul 11 09:01:21 CST 2013
   */
  protected String orderByClause;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database table
   * HEALTH_YGENERIC_INFO
   *
   * @ibatorgenerated Thu Jul 11 09:01:21 CST 2013
   */
  protected List oredCriteria;

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table HEALTH_YGENERIC_INFO
   *
   * @ibatorgenerated Thu Jul 11 09:01:21 CST 2013
   */
  public HealthYgenericInfoExample() {
    oredCriteria = new ArrayList();
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table HEALTH_YGENERIC_INFO
   *
   * @ibatorgenerated Thu Jul 11 09:01:21 CST 2013
   */
  protected HealthYgenericInfoExample(HealthYgenericInfoExample example) {
    this.orderByClause = example.orderByClause;
    this.oredCriteria = example.oredCriteria;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table HEALTH_YGENERIC_INFO
   *
   * @ibatorgenerated Thu Jul 11 09:01:21 CST 2013
   */
  public void setOrderByClause(String orderByClause) {
    this.orderByClause = orderByClause;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table HEALTH_YGENERIC_INFO
   *
   * @ibatorgenerated Thu Jul 11 09:01:21 CST 2013
   */
  public String getOrderByClause() {
    return orderByClause;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table HEALTH_YGENERIC_INFO
   *
   * @ibatorgenerated Thu Jul 11 09:01:21 CST 2013
   */
  public List getOredCriteria() {
    return oredCriteria;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table HEALTH_YGENERIC_INFO
   *
   * @ibatorgenerated Thu Jul 11 09:01:21 CST 2013
   */
  public void or(Criteria criteria) {
    oredCriteria.add(criteria);
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table HEALTH_YGENERIC_INFO
   *
   * @ibatorgenerated Thu Jul 11 09:01:21 CST 2013
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
   * table HEALTH_YGENERIC_INFO
   *
   * @ibatorgenerated Thu Jul 11 09:01:21 CST 2013
   */
  protected Criteria createCriteriaInternal() {
    Criteria criteria = new Criteria();
    return criteria;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table HEALTH_YGENERIC_INFO
   *
   * @ibatorgenerated Thu Jul 11 09:01:21 CST 2013
   */
  public void clear() {
    oredCriteria.clear();
  }

  /**
   * This class was generated by Apache iBATIS ibator. This class corresponds to the database table
   * HEALTH_YGENERIC_INFO
   *
   * @ibatorgenerated Thu Jul 11 09:01:21 CST 2013
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

    public Criteria andNHealthYgenericIdIsNull() {
      addCriterion("N_HEALTH_YGENERIC_ID is null");
      return this;
    }

    public Criteria andNHealthYgenericIdIsNotNull() {
      addCriterion("N_HEALTH_YGENERIC_ID is not null");
      return this;
    }

    public Criteria andNHealthYgenericIdEqualTo(BigDecimal value) {
      addCriterion("N_HEALTH_YGENERIC_ID =", value, "nHealthYgenericId");
      return this;
    }

    public Criteria andNHealthYgenericIdNotEqualTo(BigDecimal value) {
      addCriterion("N_HEALTH_YGENERIC_ID <>", value, "nHealthYgenericId");
      return this;
    }

    public Criteria andNHealthYgenericIdGreaterThan(BigDecimal value) {
      addCriterion("N_HEALTH_YGENERIC_ID >", value, "nHealthYgenericId");
      return this;
    }

    public Criteria andNHealthYgenericIdGreaterThanOrEqualTo(BigDecimal value) {
      addCriterion("N_HEALTH_YGENERIC_ID >=", value, "nHealthYgenericId");
      return this;
    }

    public Criteria andNHealthYgenericIdLessThan(BigDecimal value) {
      addCriterion("N_HEALTH_YGENERIC_ID <", value, "nHealthYgenericId");
      return this;
    }

    public Criteria andNHealthYgenericIdLessThanOrEqualTo(BigDecimal value) {
      addCriterion("N_HEALTH_YGENERIC_ID <=", value, "nHealthYgenericId");
      return this;
    }

    public Criteria andNHealthYgenericIdIn(List values) {
      addCriterion("N_HEALTH_YGENERIC_ID in", values, "nHealthYgenericId");
      return this;
    }

    public Criteria andNHealthYgenericIdNotIn(List values) {
      addCriterion("N_HEALTH_YGENERIC_ID not in", values, "nHealthYgenericId");
      return this;
    }

    public Criteria andNHealthYgenericIdBetween(BigDecimal value1, BigDecimal value2) {
      addCriterion("N_HEALTH_YGENERIC_ID between", value1, value2, "nHealthYgenericId");
      return this;
    }

    public Criteria andNHealthYgenericIdNotBetween(BigDecimal value1, BigDecimal value2) {
      addCriterion("N_HEALTH_YGENERIC_ID not between", value1, value2, "nHealthYgenericId");
      return this;
    }

    public Criteria andNLoginIdIsNull() {
      addCriterion("N_LOGIN_ID is null");
      return this;
    }

    public Criteria andNLoginIdIsNotNull() {
      addCriterion("N_LOGIN_ID is not null");
      return this;
    }

    public Criteria andNLoginIdEqualTo(BigDecimal value) {
      addCriterion("N_LOGIN_ID =", value, "nLoginId");
      return this;
    }

    public Criteria andNLoginIdNotEqualTo(BigDecimal value) {
      addCriterion("N_LOGIN_ID <>", value, "nLoginId");
      return this;
    }

    public Criteria andNLoginIdGreaterThan(BigDecimal value) {
      addCriterion("N_LOGIN_ID >", value, "nLoginId");
      return this;
    }

    public Criteria andNLoginIdGreaterThanOrEqualTo(BigDecimal value) {
      addCriterion("N_LOGIN_ID >=", value, "nLoginId");
      return this;
    }

    public Criteria andNLoginIdLessThan(BigDecimal value) {
      addCriterion("N_LOGIN_ID <", value, "nLoginId");
      return this;
    }

    public Criteria andNLoginIdLessThanOrEqualTo(BigDecimal value) {
      addCriterion("N_LOGIN_ID <=", value, "nLoginId");
      return this;
    }

    public Criteria andNLoginIdIn(List values) {
      addCriterion("N_LOGIN_ID in", values, "nLoginId");
      return this;
    }

    public Criteria andNLoginIdNotIn(List values) {
      addCriterion("N_LOGIN_ID not in", values, "nLoginId");
      return this;
    }

    public Criteria andNLoginIdBetween(BigDecimal value1, BigDecimal value2) {
      addCriterion("N_LOGIN_ID between", value1, value2, "nLoginId");
      return this;
    }

    public Criteria andNLoginIdNotBetween(BigDecimal value1, BigDecimal value2) {
      addCriterion("N_LOGIN_ID not between", value1, value2, "nLoginId");
      return this;
    }

    public Criteria andNMaritalStatusIsNull() {
      addCriterion("N_MARITAL_STATUS is null");
      return this;
    }

    public Criteria andNMaritalStatusIsNotNull() {
      addCriterion("N_MARITAL_STATUS is not null");
      return this;
    }

    public Criteria andNMaritalStatusEqualTo(Short value) {
      addCriterion("N_MARITAL_STATUS =", value, "nMaritalStatus");
      return this;
    }

    public Criteria andNMaritalStatusNotEqualTo(Short value) {
      addCriterion("N_MARITAL_STATUS <>", value, "nMaritalStatus");
      return this;
    }

    public Criteria andNMaritalStatusGreaterThan(Short value) {
      addCriterion("N_MARITAL_STATUS >", value, "nMaritalStatus");
      return this;
    }

    public Criteria andNMaritalStatusGreaterThanOrEqualTo(Short value) {
      addCriterion("N_MARITAL_STATUS >=", value, "nMaritalStatus");
      return this;
    }

    public Criteria andNMaritalStatusLessThan(Short value) {
      addCriterion("N_MARITAL_STATUS <", value, "nMaritalStatus");
      return this;
    }

    public Criteria andNMaritalStatusLessThanOrEqualTo(Short value) {
      addCriterion("N_MARITAL_STATUS <=", value, "nMaritalStatus");
      return this;
    }

    public Criteria andNMaritalStatusIn(List values) {
      addCriterion("N_MARITAL_STATUS in", values, "nMaritalStatus");
      return this;
    }

    public Criteria andNMaritalStatusNotIn(List values) {
      addCriterion("N_MARITAL_STATUS not in", values, "nMaritalStatus");
      return this;
    }

    public Criteria andNMaritalStatusBetween(Short value1, Short value2) {
      addCriterion("N_MARITAL_STATUS between", value1, value2, "nMaritalStatus");
      return this;
    }

    public Criteria andNMaritalStatusNotBetween(Short value1, Short value2) {
      addCriterion("N_MARITAL_STATUS not between", value1, value2, "nMaritalStatus");
      return this;
    }

    public Criteria andHealthyStateIsNull() {
      addCriterion("HEALTHY_STATE is null");
      return this;
    }

    public Criteria andHealthyStateIsNotNull() {
      addCriterion("HEALTHY_STATE is not null");
      return this;
    }

    public Criteria andHealthyStateEqualTo(String value) {
      addCriterion("HEALTHY_STATE =", value, "healthyState");
      return this;
    }

    public Criteria andHealthyStateNotEqualTo(String value) {
      addCriterion("HEALTHY_STATE <>", value, "healthyState");
      return this;
    }

    public Criteria andHealthyStateGreaterThan(String value) {
      addCriterion("HEALTHY_STATE >", value, "healthyState");
      return this;
    }

    public Criteria andHealthyStateGreaterThanOrEqualTo(String value) {
      addCriterion("HEALTHY_STATE >=", value, "healthyState");
      return this;
    }

    public Criteria andHealthyStateLessThan(String value) {
      addCriterion("HEALTHY_STATE <", value, "healthyState");
      return this;
    }

    public Criteria andHealthyStateLessThanOrEqualTo(String value) {
      addCriterion("HEALTHY_STATE <=", value, "healthyState");
      return this;
    }

    public Criteria andHealthyStateLike(String value) {
      addCriterion("HEALTHY_STATE like", value, "healthyState");
      return this;
    }

    public Criteria andHealthyStateNotLike(String value) {
      addCriterion("HEALTHY_STATE not like", value, "healthyState");
      return this;
    }

    public Criteria andHealthyStateIn(List values) {
      addCriterion("HEALTHY_STATE in", values, "healthyState");
      return this;
    }

    public Criteria andHealthyStateNotIn(List values) {
      addCriterion("HEALTHY_STATE not in", values, "healthyState");
      return this;
    }

    public Criteria andHealthyStateBetween(String value1, String value2) {
      addCriterion("HEALTHY_STATE between", value1, value2, "healthyState");
      return this;
    }

    public Criteria andHealthyStateNotBetween(String value1, String value2) {
      addCriterion("HEALTHY_STATE not between", value1, value2, "healthyState");
      return this;
    }

    public Criteria andEatingHabitsIsNull() {
      addCriterion("EATING_HABITS is null");
      return this;
    }

    public Criteria andEatingHabitsIsNotNull() {
      addCriterion("EATING_HABITS is not null");
      return this;
    }

    public Criteria andEatingHabitsEqualTo(String value) {
      addCriterion("EATING_HABITS =", value, "eatingHabits");
      return this;
    }

    public Criteria andEatingHabitsNotEqualTo(String value) {
      addCriterion("EATING_HABITS <>", value, "eatingHabits");
      return this;
    }

    public Criteria andEatingHabitsGreaterThan(String value) {
      addCriterion("EATING_HABITS >", value, "eatingHabits");
      return this;
    }

    public Criteria andEatingHabitsGreaterThanOrEqualTo(String value) {
      addCriterion("EATING_HABITS >=", value, "eatingHabits");
      return this;
    }

    public Criteria andEatingHabitsLessThan(String value) {
      addCriterion("EATING_HABITS <", value, "eatingHabits");
      return this;
    }

    public Criteria andEatingHabitsLessThanOrEqualTo(String value) {
      addCriterion("EATING_HABITS <=", value, "eatingHabits");
      return this;
    }

    public Criteria andEatingHabitsLike(String value) {
      addCriterion("EATING_HABITS like", value, "eatingHabits");
      return this;
    }

    public Criteria andEatingHabitsNotLike(String value) {
      addCriterion("EATING_HABITS not like", value, "eatingHabits");
      return this;
    }

    public Criteria andEatingHabitsIn(List values) {
      addCriterion("EATING_HABITS in", values, "eatingHabits");
      return this;
    }

    public Criteria andEatingHabitsNotIn(List values) {
      addCriterion("EATING_HABITS not in", values, "eatingHabits");
      return this;
    }

    public Criteria andEatingHabitsBetween(String value1, String value2) {
      addCriterion("EATING_HABITS between", value1, value2, "eatingHabits");
      return this;
    }

    public Criteria andEatingHabitsNotBetween(String value1, String value2) {
      addCriterion("EATING_HABITS not between", value1, value2, "eatingHabits");
      return this;
    }

    public Criteria andSmokingAndAlcoholHistoryIsNull() {
      addCriterion("SMOKING_AND_ALCOHOL_HISTORY is null");
      return this;
    }

    public Criteria andSmokingAndAlcoholHistoryIsNotNull() {
      addCriterion("SMOKING_AND_ALCOHOL_HISTORY is not null");
      return this;
    }

    public Criteria andSmokingAndAlcoholHistoryEqualTo(String value) {
      addCriterion("SMOKING_AND_ALCOHOL_HISTORY =", value, "smokingAndAlcoholHistory");
      return this;
    }

    public Criteria andSmokingAndAlcoholHistoryNotEqualTo(String value) {
      addCriterion("SMOKING_AND_ALCOHOL_HISTORY <>", value, "smokingAndAlcoholHistory");
      return this;
    }

    public Criteria andSmokingAndAlcoholHistoryGreaterThan(String value) {
      addCriterion("SMOKING_AND_ALCOHOL_HISTORY >", value, "smokingAndAlcoholHistory");
      return this;
    }

    public Criteria andSmokingAndAlcoholHistoryGreaterThanOrEqualTo(String value) {
      addCriterion("SMOKING_AND_ALCOHOL_HISTORY >=", value, "smokingAndAlcoholHistory");
      return this;
    }

    public Criteria andSmokingAndAlcoholHistoryLessThan(String value) {
      addCriterion("SMOKING_AND_ALCOHOL_HISTORY <", value, "smokingAndAlcoholHistory");
      return this;
    }

    public Criteria andSmokingAndAlcoholHistoryLessThanOrEqualTo(String value) {
      addCriterion("SMOKING_AND_ALCOHOL_HISTORY <=", value, "smokingAndAlcoholHistory");
      return this;
    }

    public Criteria andSmokingAndAlcoholHistoryLike(String value) {
      addCriterion("SMOKING_AND_ALCOHOL_HISTORY like", value, "smokingAndAlcoholHistory");
      return this;
    }

    public Criteria andSmokingAndAlcoholHistoryNotLike(String value) {
      addCriterion("SMOKING_AND_ALCOHOL_HISTORY not like", value, "smokingAndAlcoholHistory");
      return this;
    }

    public Criteria andSmokingAndAlcoholHistoryIn(List values) {
      addCriterion("SMOKING_AND_ALCOHOL_HISTORY in", values, "smokingAndAlcoholHistory");
      return this;
    }

    public Criteria andSmokingAndAlcoholHistoryNotIn(List values) {
      addCriterion("SMOKING_AND_ALCOHOL_HISTORY not in", values, "smokingAndAlcoholHistory");
      return this;
    }

    public Criteria andSmokingAndAlcoholHistoryBetween(String value1, String value2) {
      addCriterion("SMOKING_AND_ALCOHOL_HISTORY between", value1, value2,
          "smokingAndAlcoholHistory");
      return this;
    }

    public Criteria andSmokingAndAlcoholHistoryNotBetween(String value1, String value2) {
      addCriterion("SMOKING_AND_ALCOHOL_HISTORY not between", value1, value2,
          "smokingAndAlcoholHistory");
      return this;
    }

    public Criteria andPastMedicalHistoryIsNull() {
      addCriterion("PAST_MEDICAL_HISTORY is null");
      return this;
    }

    public Criteria andPastMedicalHistoryIsNotNull() {
      addCriterion("PAST_MEDICAL_HISTORY is not null");
      return this;
    }

    public Criteria andPastMedicalHistoryEqualTo(String value) {
      addCriterion("PAST_MEDICAL_HISTORY =", value, "pastMedicalHistory");
      return this;
    }

    public Criteria andPastMedicalHistoryNotEqualTo(String value) {
      addCriterion("PAST_MEDICAL_HISTORY <>", value, "pastMedicalHistory");
      return this;
    }

    public Criteria andPastMedicalHistoryGreaterThan(String value) {
      addCriterion("PAST_MEDICAL_HISTORY >", value, "pastMedicalHistory");
      return this;
    }

    public Criteria andPastMedicalHistoryGreaterThanOrEqualTo(String value) {
      addCriterion("PAST_MEDICAL_HISTORY >=", value, "pastMedicalHistory");
      return this;
    }

    public Criteria andPastMedicalHistoryLessThan(String value) {
      addCriterion("PAST_MEDICAL_HISTORY <", value, "pastMedicalHistory");
      return this;
    }

    public Criteria andPastMedicalHistoryLessThanOrEqualTo(String value) {
      addCriterion("PAST_MEDICAL_HISTORY <=", value, "pastMedicalHistory");
      return this;
    }

    public Criteria andPastMedicalHistoryLike(String value) {
      addCriterion("PAST_MEDICAL_HISTORY like", value, "pastMedicalHistory");
      return this;
    }

    public Criteria andPastMedicalHistoryNotLike(String value) {
      addCriterion("PAST_MEDICAL_HISTORY not like", value, "pastMedicalHistory");
      return this;
    }

    public Criteria andPastMedicalHistoryIn(List values) {
      addCriterion("PAST_MEDICAL_HISTORY in", values, "pastMedicalHistory");
      return this;
    }

    public Criteria andPastMedicalHistoryNotIn(List values) {
      addCriterion("PAST_MEDICAL_HISTORY not in", values, "pastMedicalHistory");
      return this;
    }

    public Criteria andPastMedicalHistoryBetween(String value1, String value2) {
      addCriterion("PAST_MEDICAL_HISTORY between", value1, value2, "pastMedicalHistory");
      return this;
    }

    public Criteria andPastMedicalHistoryNotBetween(String value1, String value2) {
      addCriterion("PAST_MEDICAL_HISTORY not between", value1, value2, "pastMedicalHistory");
      return this;
    }

    public Criteria andSocialSecurityNumberIsNull() {
      addCriterion("SOCIAL_SECURITY_NUMBER is null");
      return this;
    }

    public Criteria andSocialSecurityNumberIsNotNull() {
      addCriterion("SOCIAL_SECURITY_NUMBER is not null");
      return this;
    }

    public Criteria andSocialSecurityNumberEqualTo(String value) {
      addCriterion("SOCIAL_SECURITY_NUMBER =", value, "socialSecurityNumber");
      return this;
    }

    public Criteria andSocialSecurityNumberNotEqualTo(String value) {
      addCriterion("SOCIAL_SECURITY_NUMBER <>", value, "socialSecurityNumber");
      return this;
    }

    public Criteria andSocialSecurityNumberGreaterThan(String value) {
      addCriterion("SOCIAL_SECURITY_NUMBER >", value, "socialSecurityNumber");
      return this;
    }

    public Criteria andSocialSecurityNumberGreaterThanOrEqualTo(String value) {
      addCriterion("SOCIAL_SECURITY_NUMBER >=", value, "socialSecurityNumber");
      return this;
    }

    public Criteria andSocialSecurityNumberLessThan(String value) {
      addCriterion("SOCIAL_SECURITY_NUMBER <", value, "socialSecurityNumber");
      return this;
    }

    public Criteria andSocialSecurityNumberLessThanOrEqualTo(String value) {
      addCriterion("SOCIAL_SECURITY_NUMBER <=", value, "socialSecurityNumber");
      return this;
    }

    public Criteria andSocialSecurityNumberLike(String value) {
      addCriterion("SOCIAL_SECURITY_NUMBER like", value, "socialSecurityNumber");
      return this;
    }

    public Criteria andSocialSecurityNumberNotLike(String value) {
      addCriterion("SOCIAL_SECURITY_NUMBER not like", value, "socialSecurityNumber");
      return this;
    }

    public Criteria andSocialSecurityNumberIn(List values) {
      addCriterion("SOCIAL_SECURITY_NUMBER in", values, "socialSecurityNumber");
      return this;
    }

    public Criteria andSocialSecurityNumberNotIn(List values) {
      addCriterion("SOCIAL_SECURITY_NUMBER not in", values, "socialSecurityNumber");
      return this;
    }

    public Criteria andSocialSecurityNumberBetween(String value1, String value2) {
      addCriterion("SOCIAL_SECURITY_NUMBER between", value1, value2, "socialSecurityNumber");
      return this;
    }

    public Criteria andSocialSecurityNumberNotBetween(String value1, String value2) {
      addCriterion("SOCIAL_SECURITY_NUMBER not between", value1, value2, "socialSecurityNumber");
      return this;
    }

    public Criteria andNThereIsNoFertilityIsNull() {
      addCriterion("N_THERE_IS_NO_FERTILITY is null");
      return this;
    }

    public Criteria andNThereIsNoFertilityIsNotNull() {
      addCriterion("N_THERE_IS_NO_FERTILITY is not null");
      return this;
    }

    public Criteria andNThereIsNoFertilityEqualTo(Short value) {
      addCriterion("N_THERE_IS_NO_FERTILITY =", value, "nThereIsNoFertility");
      return this;
    }

    public Criteria andNThereIsNoFertilityNotEqualTo(Short value) {
      addCriterion("N_THERE_IS_NO_FERTILITY <>", value, "nThereIsNoFertility");
      return this;
    }

    public Criteria andNThereIsNoFertilityGreaterThan(Short value) {
      addCriterion("N_THERE_IS_NO_FERTILITY >", value, "nThereIsNoFertility");
      return this;
    }

    public Criteria andNThereIsNoFertilityGreaterThanOrEqualTo(Short value) {
      addCriterion("N_THERE_IS_NO_FERTILITY >=", value, "nThereIsNoFertility");
      return this;
    }

    public Criteria andNThereIsNoFertilityLessThan(Short value) {
      addCriterion("N_THERE_IS_NO_FERTILITY <", value, "nThereIsNoFertility");
      return this;
    }

    public Criteria andNThereIsNoFertilityLessThanOrEqualTo(Short value) {
      addCriterion("N_THERE_IS_NO_FERTILITY <=", value, "nThereIsNoFertility");
      return this;
    }

    public Criteria andNThereIsNoFertilityIn(List values) {
      addCriterion("N_THERE_IS_NO_FERTILITY in", values, "nThereIsNoFertility");
      return this;
    }

    public Criteria andNThereIsNoFertilityNotIn(List values) {
      addCriterion("N_THERE_IS_NO_FERTILITY not in", values, "nThereIsNoFertility");
      return this;
    }

    public Criteria andNThereIsNoFertilityBetween(Short value1, Short value2) {
      addCriterion("N_THERE_IS_NO_FERTILITY between", value1, value2, "nThereIsNoFertility");
      return this;
    }

    public Criteria andNThereIsNoFertilityNotBetween(Short value1, Short value2) {
      addCriterion("N_THERE_IS_NO_FERTILITY not between", value1, value2, "nThereIsNoFertility");
      return this;
    }

    public Criteria andBloodTypeIsNull() {
      addCriterion("BLOOD_TYPE is null");
      return this;
    }

    public Criteria andBloodTypeIsNotNull() {
      addCriterion("BLOOD_TYPE is not null");
      return this;
    }

    public Criteria andBloodTypeEqualTo(String value) {
      addCriterion("BLOOD_TYPE =", value, "bloodType");
      return this;
    }

    public Criteria andBloodTypeNotEqualTo(String value) {
      addCriterion("BLOOD_TYPE <>", value, "bloodType");
      return this;
    }

    public Criteria andBloodTypeGreaterThan(String value) {
      addCriterion("BLOOD_TYPE >", value, "bloodType");
      return this;
    }

    public Criteria andBloodTypeGreaterThanOrEqualTo(String value) {
      addCriterion("BLOOD_TYPE >=", value, "bloodType");
      return this;
    }

    public Criteria andBloodTypeLessThan(String value) {
      addCriterion("BLOOD_TYPE <", value, "bloodType");
      return this;
    }

    public Criteria andBloodTypeLessThanOrEqualTo(String value) {
      addCriterion("BLOOD_TYPE <=", value, "bloodType");
      return this;
    }

    public Criteria andBloodTypeLike(String value) {
      addCriterion("BLOOD_TYPE like", value, "bloodType");
      return this;
    }

    public Criteria andBloodTypeNotLike(String value) {
      addCriterion("BLOOD_TYPE not like", value, "bloodType");
      return this;
    }

    public Criteria andBloodTypeIn(List values) {
      addCriterion("BLOOD_TYPE in", values, "bloodType");
      return this;
    }

    public Criteria andBloodTypeNotIn(List values) {
      addCriterion("BLOOD_TYPE not in", values, "bloodType");
      return this;
    }

    public Criteria andBloodTypeBetween(String value1, String value2) {
      addCriterion("BLOOD_TYPE between", value1, value2, "bloodType");
      return this;
    }

    public Criteria andBloodTypeNotBetween(String value1, String value2) {
      addCriterion("BLOOD_TYPE not between", value1, value2, "bloodType");
      return this;
    }

    public Criteria andHealthySeekAdviceFromIsNull() {
      addCriterion("HEALTHY_SEEK_ADVICE_FROM is null");
      return this;
    }

    public Criteria andHealthySeekAdviceFromIsNotNull() {
      addCriterion("HEALTHY_SEEK_ADVICE_FROM is not null");
      return this;
    }

    public Criteria andHealthySeekAdviceFromEqualTo(String value) {
      addCriterion("HEALTHY_SEEK_ADVICE_FROM =", value, "healthySeekAdviceFrom");
      return this;
    }

    public Criteria andHealthySeekAdviceFromNotEqualTo(String value) {
      addCriterion("HEALTHY_SEEK_ADVICE_FROM <>", value, "healthySeekAdviceFrom");
      return this;
    }

    public Criteria andHealthySeekAdviceFromGreaterThan(String value) {
      addCriterion("HEALTHY_SEEK_ADVICE_FROM >", value, "healthySeekAdviceFrom");
      return this;
    }

    public Criteria andHealthySeekAdviceFromGreaterThanOrEqualTo(String value) {
      addCriterion("HEALTHY_SEEK_ADVICE_FROM >=", value, "healthySeekAdviceFrom");
      return this;
    }

    public Criteria andHealthySeekAdviceFromLessThan(String value) {
      addCriterion("HEALTHY_SEEK_ADVICE_FROM <", value, "healthySeekAdviceFrom");
      return this;
    }

    public Criteria andHealthySeekAdviceFromLessThanOrEqualTo(String value) {
      addCriterion("HEALTHY_SEEK_ADVICE_FROM <=", value, "healthySeekAdviceFrom");
      return this;
    }

    public Criteria andHealthySeekAdviceFromLike(String value) {
      addCriterion("HEALTHY_SEEK_ADVICE_FROM like", value, "healthySeekAdviceFrom");
      return this;
    }

    public Criteria andHealthySeekAdviceFromNotLike(String value) {
      addCriterion("HEALTHY_SEEK_ADVICE_FROM not like", value, "healthySeekAdviceFrom");
      return this;
    }

    public Criteria andHealthySeekAdviceFromIn(List values) {
      addCriterion("HEALTHY_SEEK_ADVICE_FROM in", values, "healthySeekAdviceFrom");
      return this;
    }

    public Criteria andHealthySeekAdviceFromNotIn(List values) {
      addCriterion("HEALTHY_SEEK_ADVICE_FROM not in", values, "healthySeekAdviceFrom");
      return this;
    }

    public Criteria andHealthySeekAdviceFromBetween(String value1, String value2) {
      addCriterion("HEALTHY_SEEK_ADVICE_FROM between", value1, value2, "healthySeekAdviceFrom");
      return this;
    }

    public Criteria andHealthySeekAdviceFromNotBetween(String value1, String value2) {
      addCriterion("HEALTHY_SEEK_ADVICE_FROM not between", value1, value2, "healthySeekAdviceFrom");
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
