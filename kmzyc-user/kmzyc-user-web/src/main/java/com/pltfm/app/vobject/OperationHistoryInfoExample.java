package com.pltfm.app.vobject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class OperationHistoryInfoExample {
  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database table
   * OPERATION_HISTORY_INFO
   *
   * @ibatorgenerated Fri Jul 12 09:09:31 CST 2013
   */
  protected String orderByClause;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database table
   * OPERATION_HISTORY_INFO
   *
   * @ibatorgenerated Fri Jul 12 09:09:31 CST 2013
   */
  protected List oredCriteria;

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table OPERATION_HISTORY_INFO
   *
   * @ibatorgenerated Fri Jul 12 09:09:31 CST 2013
   */
  public OperationHistoryInfoExample() {
    oredCriteria = new ArrayList();
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table OPERATION_HISTORY_INFO
   *
   * @ibatorgenerated Fri Jul 12 09:09:31 CST 2013
   */
  protected OperationHistoryInfoExample(OperationHistoryInfoExample example) {
    this.orderByClause = example.orderByClause;
    this.oredCriteria = example.oredCriteria;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table OPERATION_HISTORY_INFO
   *
   * @ibatorgenerated Fri Jul 12 09:09:31 CST 2013
   */
  public void setOrderByClause(String orderByClause) {
    this.orderByClause = orderByClause;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table OPERATION_HISTORY_INFO
   *
   * @ibatorgenerated Fri Jul 12 09:09:31 CST 2013
   */
  public String getOrderByClause() {
    return orderByClause;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table OPERATION_HISTORY_INFO
   *
   * @ibatorgenerated Fri Jul 12 09:09:31 CST 2013
   */
  public List getOredCriteria() {
    return oredCriteria;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table OPERATION_HISTORY_INFO
   *
   * @ibatorgenerated Fri Jul 12 09:09:31 CST 2013
   */
  public void or(Criteria criteria) {
    oredCriteria.add(criteria);
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table OPERATION_HISTORY_INFO
   *
   * @ibatorgenerated Fri Jul 12 09:09:31 CST 2013
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
   * table OPERATION_HISTORY_INFO
   *
   * @ibatorgenerated Fri Jul 12 09:09:31 CST 2013
   */
  protected Criteria createCriteriaInternal() {
    Criteria criteria = new Criteria();
    return criteria;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table OPERATION_HISTORY_INFO
   *
   * @ibatorgenerated Fri Jul 12 09:09:31 CST 2013
   */
  public void clear() {
    oredCriteria.clear();
  }

  /**
   * This class was generated by Apache iBATIS ibator. This class corresponds to the database table
   * OPERATION_HISTORY_INFO
   *
   * @ibatorgenerated Fri Jul 12 09:09:31 CST 2013
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

    public Criteria andNOperationHistoryIdIsNull() {
      addCriterion("N_OPERATION_HISTORY_ID is null");
      return this;
    }

    public Criteria andNOperationHistoryIdIsNotNull() {
      addCriterion("N_OPERATION_HISTORY_ID is not null");
      return this;
    }

    public Criteria andNOperationHistoryIdEqualTo(BigDecimal value) {
      addCriterion("N_OPERATION_HISTORY_ID =", value, "nOperationHistoryId");
      return this;
    }

    public Criteria andNOperationHistoryIdNotEqualTo(BigDecimal value) {
      addCriterion("N_OPERATION_HISTORY_ID <>", value, "nOperationHistoryId");
      return this;
    }

    public Criteria andNOperationHistoryIdGreaterThan(BigDecimal value) {
      addCriterion("N_OPERATION_HISTORY_ID >", value, "nOperationHistoryId");
      return this;
    }

    public Criteria andNOperationHistoryIdGreaterThanOrEqualTo(BigDecimal value) {
      addCriterion("N_OPERATION_HISTORY_ID >=", value, "nOperationHistoryId");
      return this;
    }

    public Criteria andNOperationHistoryIdLessThan(BigDecimal value) {
      addCriterion("N_OPERATION_HISTORY_ID <", value, "nOperationHistoryId");
      return this;
    }

    public Criteria andNOperationHistoryIdLessThanOrEqualTo(BigDecimal value) {
      addCriterion("N_OPERATION_HISTORY_ID <=", value, "nOperationHistoryId");
      return this;
    }

    public Criteria andNOperationHistoryIdIn(List values) {
      addCriterion("N_OPERATION_HISTORY_ID in", values, "nOperationHistoryId");
      return this;
    }

    public Criteria andNOperationHistoryIdNotIn(List values) {
      addCriterion("N_OPERATION_HISTORY_ID not in", values, "nOperationHistoryId");
      return this;
    }

    public Criteria andNOperationHistoryIdBetween(BigDecimal value1, BigDecimal value2) {
      addCriterion("N_OPERATION_HISTORY_ID between", value1, value2, "nOperationHistoryId");
      return this;
    }

    public Criteria andNOperationHistoryIdNotBetween(BigDecimal value1, BigDecimal value2) {
      addCriterion("N_OPERATION_HISTORY_ID not between", value1, value2, "nOperationHistoryId");
      return this;
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

    public Criteria andOperationNameIsNull() {
      addCriterion("OPERATION_NAME is null");
      return this;
    }

    public Criteria andOperationNameIsNotNull() {
      addCriterion("OPERATION_NAME is not null");
      return this;
    }

    public Criteria andOperationNameEqualTo(String value) {
      addCriterion("OPERATION_NAME =", value, "operationName");
      return this;
    }

    public Criteria andOperationNameNotEqualTo(String value) {
      addCriterion("OPERATION_NAME <>", value, "operationName");
      return this;
    }

    public Criteria andOperationNameGreaterThan(String value) {
      addCriterion("OPERATION_NAME >", value, "operationName");
      return this;
    }

    public Criteria andOperationNameGreaterThanOrEqualTo(String value) {
      addCriterion("OPERATION_NAME >=", value, "operationName");
      return this;
    }

    public Criteria andOperationNameLessThan(String value) {
      addCriterion("OPERATION_NAME <", value, "operationName");
      return this;
    }

    public Criteria andOperationNameLessThanOrEqualTo(String value) {
      addCriterion("OPERATION_NAME <=", value, "operationName");
      return this;
    }

    public Criteria andOperationNameLike(String value) {
      addCriterion("OPERATION_NAME like", value, "operationName");
      return this;
    }

    public Criteria andOperationNameNotLike(String value) {
      addCriterion("OPERATION_NAME not like", value, "operationName");
      return this;
    }

    public Criteria andOperationNameIn(List values) {
      addCriterion("OPERATION_NAME in", values, "operationName");
      return this;
    }

    public Criteria andOperationNameNotIn(List values) {
      addCriterion("OPERATION_NAME not in", values, "operationName");
      return this;
    }

    public Criteria andOperationNameBetween(String value1, String value2) {
      addCriterion("OPERATION_NAME between", value1, value2, "operationName");
      return this;
    }

    public Criteria andOperationNameNotBetween(String value1, String value2) {
      addCriterion("OPERATION_NAME not between", value1, value2, "operationName");
      return this;
    }

    public Criteria andHospitalIsNull() {
      addCriterion("HOSPITAL is null");
      return this;
    }

    public Criteria andHospitalIsNotNull() {
      addCriterion("HOSPITAL is not null");
      return this;
    }

    public Criteria andHospitalEqualTo(String value) {
      addCriterion("HOSPITAL =", value, "hospital");
      return this;
    }

    public Criteria andHospitalNotEqualTo(String value) {
      addCriterion("HOSPITAL <>", value, "hospital");
      return this;
    }

    public Criteria andHospitalGreaterThan(String value) {
      addCriterion("HOSPITAL >", value, "hospital");
      return this;
    }

    public Criteria andHospitalGreaterThanOrEqualTo(String value) {
      addCriterion("HOSPITAL >=", value, "hospital");
      return this;
    }

    public Criteria andHospitalLessThan(String value) {
      addCriterion("HOSPITAL <", value, "hospital");
      return this;
    }

    public Criteria andHospitalLessThanOrEqualTo(String value) {
      addCriterion("HOSPITAL <=", value, "hospital");
      return this;
    }

    public Criteria andHospitalLike(String value) {
      addCriterion("HOSPITAL like", value, "hospital");
      return this;
    }

    public Criteria andHospitalNotLike(String value) {
      addCriterion("HOSPITAL not like", value, "hospital");
      return this;
    }

    public Criteria andHospitalIn(List values) {
      addCriterion("HOSPITAL in", values, "hospital");
      return this;
    }

    public Criteria andHospitalNotIn(List values) {
      addCriterion("HOSPITAL not in", values, "hospital");
      return this;
    }

    public Criteria andHospitalBetween(String value1, String value2) {
      addCriterion("HOSPITAL between", value1, value2, "hospital");
      return this;
    }

    public Criteria andHospitalNotBetween(String value1, String value2) {
      addCriterion("HOSPITAL not between", value1, value2, "hospital");
      return this;
    }

    public Criteria andDoctorIsNull() {
      addCriterion("DOCTOR is null");
      return this;
    }

    public Criteria andDoctorIsNotNull() {
      addCriterion("DOCTOR is not null");
      return this;
    }

    public Criteria andDoctorEqualTo(String value) {
      addCriterion("DOCTOR =", value, "doctor");
      return this;
    }

    public Criteria andDoctorNotEqualTo(String value) {
      addCriterion("DOCTOR <>", value, "doctor");
      return this;
    }

    public Criteria andDoctorGreaterThan(String value) {
      addCriterion("DOCTOR >", value, "doctor");
      return this;
    }

    public Criteria andDoctorGreaterThanOrEqualTo(String value) {
      addCriterion("DOCTOR >=", value, "doctor");
      return this;
    }

    public Criteria andDoctorLessThan(String value) {
      addCriterion("DOCTOR <", value, "doctor");
      return this;
    }

    public Criteria andDoctorLessThanOrEqualTo(String value) {
      addCriterion("DOCTOR <=", value, "doctor");
      return this;
    }

    public Criteria andDoctorLike(String value) {
      addCriterion("DOCTOR like", value, "doctor");
      return this;
    }

    public Criteria andDoctorNotLike(String value) {
      addCriterion("DOCTOR not like", value, "doctor");
      return this;
    }

    public Criteria andDoctorIn(List values) {
      addCriterion("DOCTOR in", values, "doctor");
      return this;
    }

    public Criteria andDoctorNotIn(List values) {
      addCriterion("DOCTOR not in", values, "doctor");
      return this;
    }

    public Criteria andDoctorBetween(String value1, String value2) {
      addCriterion("DOCTOR between", value1, value2, "doctor");
      return this;
    }

    public Criteria andDoctorNotBetween(String value1, String value2) {
      addCriterion("DOCTOR not between", value1, value2, "doctor");
      return this;
    }

    public Criteria andNWhetherCureIsNull() {
      addCriterion("N_WHETHER_CURE is null");
      return this;
    }

    public Criteria andNWhetherCureIsNotNull() {
      addCriterion("N_WHETHER_CURE is not null");
      return this;
    }

    public Criteria andNWhetherCureEqualTo(Short value) {
      addCriterion("N_WHETHER_CURE =", value, "nWhetherCure");
      return this;
    }

    public Criteria andNWhetherCureNotEqualTo(Short value) {
      addCriterion("N_WHETHER_CURE <>", value, "nWhetherCure");
      return this;
    }

    public Criteria andNWhetherCureGreaterThan(Short value) {
      addCriterion("N_WHETHER_CURE >", value, "nWhetherCure");
      return this;
    }

    public Criteria andNWhetherCureGreaterThanOrEqualTo(Short value) {
      addCriterion("N_WHETHER_CURE >=", value, "nWhetherCure");
      return this;
    }

    public Criteria andNWhetherCureLessThan(Short value) {
      addCriterion("N_WHETHER_CURE <", value, "nWhetherCure");
      return this;
    }

    public Criteria andNWhetherCureLessThanOrEqualTo(Short value) {
      addCriterion("N_WHETHER_CURE <=", value, "nWhetherCure");
      return this;
    }

    public Criteria andNWhetherCureIn(List values) {
      addCriterion("N_WHETHER_CURE in", values, "nWhetherCure");
      return this;
    }

    public Criteria andNWhetherCureNotIn(List values) {
      addCriterion("N_WHETHER_CURE not in", values, "nWhetherCure");
      return this;
    }

    public Criteria andNWhetherCureBetween(Short value1, Short value2) {
      addCriterion("N_WHETHER_CURE between", value1, value2, "nWhetherCure");
      return this;
    }

    public Criteria andNWhetherCureNotBetween(Short value1, Short value2) {
      addCriterion("N_WHETHER_CURE not between", value1, value2, "nWhetherCure");
      return this;
    }

    public Criteria andDStartTimeIsNull() {
      addCriterion("D_START_TIME is null");
      return this;
    }

    public Criteria andDStartTimeIsNotNull() {
      addCriterion("D_START_TIME is not null");
      return this;
    }

    public Criteria andDStartTimeEqualTo(Date value) {
      addCriterionForJDBCDate("D_START_TIME =", value, "dStartTime");
      return this;
    }

    public Criteria andDStartTimeNotEqualTo(Date value) {
      addCriterionForJDBCDate("D_START_TIME <>", value, "dStartTime");
      return this;
    }

    public Criteria andDStartTimeGreaterThan(Date value) {
      addCriterionForJDBCDate("D_START_TIME >", value, "dStartTime");
      return this;
    }

    public Criteria andDStartTimeGreaterThanOrEqualTo(Date value) {
      addCriterionForJDBCDate("D_START_TIME >=", value, "dStartTime");
      return this;
    }

    public Criteria andDStartTimeLessThan(Date value) {
      addCriterionForJDBCDate("D_START_TIME <", value, "dStartTime");
      return this;
    }

    public Criteria andDStartTimeLessThanOrEqualTo(Date value) {
      addCriterionForJDBCDate("D_START_TIME <=", value, "dStartTime");
      return this;
    }

    public Criteria andDStartTimeIn(List values) {
      addCriterionForJDBCDate("D_START_TIME in", values, "dStartTime");
      return this;
    }

    public Criteria andDStartTimeNotIn(List values) {
      addCriterionForJDBCDate("D_START_TIME not in", values, "dStartTime");
      return this;
    }

    public Criteria andDStartTimeBetween(Date value1, Date value2) {
      addCriterionForJDBCDate("D_START_TIME between", value1, value2, "dStartTime");
      return this;
    }

    public Criteria andDStartTimeNotBetween(Date value1, Date value2) {
      addCriterionForJDBCDate("D_START_TIME not between", value1, value2, "dStartTime");
      return this;
    }

    public Criteria andDTerminalTimeIsNull() {
      addCriterion("D_TERMINAL_TIME is null");
      return this;
    }

    public Criteria andDTerminalTimeIsNotNull() {
      addCriterion("D_TERMINAL_TIME is not null");
      return this;
    }

    public Criteria andDTerminalTimeEqualTo(Date value) {
      addCriterionForJDBCDate("D_TERMINAL_TIME =", value, "dTerminalTime");
      return this;
    }

    public Criteria andDTerminalTimeNotEqualTo(Date value) {
      addCriterionForJDBCDate("D_TERMINAL_TIME <>", value, "dTerminalTime");
      return this;
    }

    public Criteria andDTerminalTimeGreaterThan(Date value) {
      addCriterionForJDBCDate("D_TERMINAL_TIME >", value, "dTerminalTime");
      return this;
    }

    public Criteria andDTerminalTimeGreaterThanOrEqualTo(Date value) {
      addCriterionForJDBCDate("D_TERMINAL_TIME >=", value, "dTerminalTime");
      return this;
    }

    public Criteria andDTerminalTimeLessThan(Date value) {
      addCriterionForJDBCDate("D_TERMINAL_TIME <", value, "dTerminalTime");
      return this;
    }

    public Criteria andDTerminalTimeLessThanOrEqualTo(Date value) {
      addCriterionForJDBCDate("D_TERMINAL_TIME <=", value, "dTerminalTime");
      return this;
    }

    public Criteria andDTerminalTimeIn(List values) {
      addCriterionForJDBCDate("D_TERMINAL_TIME in", values, "dTerminalTime");
      return this;
    }

    public Criteria andDTerminalTimeNotIn(List values) {
      addCriterionForJDBCDate("D_TERMINAL_TIME not in", values, "dTerminalTime");
      return this;
    }

    public Criteria andDTerminalTimeBetween(Date value1, Date value2) {
      addCriterionForJDBCDate("D_TERMINAL_TIME between", value1, value2, "dTerminalTime");
      return this;
    }

    public Criteria andDTerminalTimeNotBetween(Date value1, Date value2) {
      addCriterionForJDBCDate("D_TERMINAL_TIME not between", value1, value2, "dTerminalTime");
      return this;
    }

    public Criteria andConditionIsNull() {
      addCriterion("CONDITION is null");
      return this;
    }

    public Criteria andConditionIsNotNull() {
      addCriterion("CONDITION is not null");
      return this;
    }

    public Criteria andConditionEqualTo(String value) {
      addCriterion("CONDITION =", value, "condition");
      return this;
    }

    public Criteria andConditionNotEqualTo(String value) {
      addCriterion("CONDITION <>", value, "condition");
      return this;
    }

    public Criteria andConditionGreaterThan(String value) {
      addCriterion("CONDITION >", value, "condition");
      return this;
    }

    public Criteria andConditionGreaterThanOrEqualTo(String value) {
      addCriterion("CONDITION >=", value, "condition");
      return this;
    }

    public Criteria andConditionLessThan(String value) {
      addCriterion("CONDITION <", value, "condition");
      return this;
    }

    public Criteria andConditionLessThanOrEqualTo(String value) {
      addCriterion("CONDITION <=", value, "condition");
      return this;
    }

    public Criteria andConditionLike(String value) {
      addCriterion("CONDITION like", value, "condition");
      return this;
    }

    public Criteria andConditionNotLike(String value) {
      addCriterion("CONDITION not like", value, "condition");
      return this;
    }

    public Criteria andConditionIn(List values) {
      addCriterion("CONDITION in", values, "condition");
      return this;
    }

    public Criteria andConditionNotIn(List values) {
      addCriterion("CONDITION not in", values, "condition");
      return this;
    }

    public Criteria andConditionBetween(String value1, String value2) {
      addCriterion("CONDITION between", value1, value2, "condition");
      return this;
    }

    public Criteria andConditionNotBetween(String value1, String value2) {
      addCriterion("CONDITION not between", value1, value2, "condition");
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
