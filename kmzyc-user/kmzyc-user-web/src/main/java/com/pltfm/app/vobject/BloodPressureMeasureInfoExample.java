package com.pltfm.app.vobject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class BloodPressureMeasureInfoExample {
  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database table
   * BLOOD_PRESSURE_MEASURE_INFO
   *
   * @ibatorgenerated Thu Jul 11 17:29:59 CST 2013
   */
  protected String orderByClause;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database table
   * BLOOD_PRESSURE_MEASURE_INFO
   *
   * @ibatorgenerated Thu Jul 11 17:29:59 CST 2013
   */
  protected List oredCriteria;

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table BLOOD_PRESSURE_MEASURE_INFO
   *
   * @ibatorgenerated Thu Jul 11 17:29:59 CST 2013
   */
  public BloodPressureMeasureInfoExample() {
    oredCriteria = new ArrayList();
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table BLOOD_PRESSURE_MEASURE_INFO
   *
   * @ibatorgenerated Thu Jul 11 17:29:59 CST 2013
   */
  protected BloodPressureMeasureInfoExample(BloodPressureMeasureInfoExample example) {
    this.orderByClause = example.orderByClause;
    this.oredCriteria = example.oredCriteria;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table BLOOD_PRESSURE_MEASURE_INFO
   *
   * @ibatorgenerated Thu Jul 11 17:29:59 CST 2013
   */
  public void setOrderByClause(String orderByClause) {
    this.orderByClause = orderByClause;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table BLOOD_PRESSURE_MEASURE_INFO
   *
   * @ibatorgenerated Thu Jul 11 17:29:59 CST 2013
   */
  public String getOrderByClause() {
    return orderByClause;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table BLOOD_PRESSURE_MEASURE_INFO
   *
   * @ibatorgenerated Thu Jul 11 17:29:59 CST 2013
   */
  public List getOredCriteria() {
    return oredCriteria;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table BLOOD_PRESSURE_MEASURE_INFO
   *
   * @ibatorgenerated Thu Jul 11 17:29:59 CST 2013
   */
  public void or(Criteria criteria) {
    oredCriteria.add(criteria);
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table BLOOD_PRESSURE_MEASURE_INFO
   *
   * @ibatorgenerated Thu Jul 11 17:29:59 CST 2013
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
   * table BLOOD_PRESSURE_MEASURE_INFO
   *
   * @ibatorgenerated Thu Jul 11 17:29:59 CST 2013
   */
  protected Criteria createCriteriaInternal() {
    Criteria criteria = new Criteria();
    return criteria;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table BLOOD_PRESSURE_MEASURE_INFO
   *
   * @ibatorgenerated Thu Jul 11 17:29:59 CST 2013
   */
  public void clear() {
    oredCriteria.clear();
  }

  /**
   * This class was generated by Apache iBATIS ibator. This class corresponds to the database table
   * BLOOD_PRESSURE_MEASURE_INFO
   *
   * @ibatorgenerated Thu Jul 11 17:29:59 CST 2013
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

    public Criteria andNBloodPressureMeasureIdIsNull() {
      addCriterion("N_BLOOD_PRESSURE_MEASURE_ID is null");
      return this;
    }

    public Criteria andNBloodPressureMeasureIdIsNotNull() {
      addCriterion("N_BLOOD_PRESSURE_MEASURE_ID is not null");
      return this;
    }

    public Criteria andNBloodPressureMeasureIdEqualTo(BigDecimal value) {
      addCriterion("N_BLOOD_PRESSURE_MEASURE_ID =", value, "nBloodPressureMeasureId");
      return this;
    }

    public Criteria andNBloodPressureMeasureIdNotEqualTo(BigDecimal value) {
      addCriterion("N_BLOOD_PRESSURE_MEASURE_ID <>", value, "nBloodPressureMeasureId");
      return this;
    }

    public Criteria andNBloodPressureMeasureIdGreaterThan(BigDecimal value) {
      addCriterion("N_BLOOD_PRESSURE_MEASURE_ID >", value, "nBloodPressureMeasureId");
      return this;
    }

    public Criteria andNBloodPressureMeasureIdGreaterThanOrEqualTo(BigDecimal value) {
      addCriterion("N_BLOOD_PRESSURE_MEASURE_ID >=", value, "nBloodPressureMeasureId");
      return this;
    }

    public Criteria andNBloodPressureMeasureIdLessThan(BigDecimal value) {
      addCriterion("N_BLOOD_PRESSURE_MEASURE_ID <", value, "nBloodPressureMeasureId");
      return this;
    }

    public Criteria andNBloodPressureMeasureIdLessThanOrEqualTo(BigDecimal value) {
      addCriterion("N_BLOOD_PRESSURE_MEASURE_ID <=", value, "nBloodPressureMeasureId");
      return this;
    }

    public Criteria andNBloodPressureMeasureIdIn(List values) {
      addCriterion("N_BLOOD_PRESSURE_MEASURE_ID in", values, "nBloodPressureMeasureId");
      return this;
    }

    public Criteria andNBloodPressureMeasureIdNotIn(List values) {
      addCriterion("N_BLOOD_PRESSURE_MEASURE_ID not in", values, "nBloodPressureMeasureId");
      return this;
    }

    public Criteria andNBloodPressureMeasureIdBetween(BigDecimal value1, BigDecimal value2) {
      addCriterion("N_BLOOD_PRESSURE_MEASURE_ID between", value1, value2,
          "nBloodPressureMeasureId");
      return this;
    }

    public Criteria andNBloodPressureMeasureIdNotBetween(BigDecimal value1, BigDecimal value2) {
      addCriterion("N_BLOOD_PRESSURE_MEASURE_ID not between", value1, value2,
          "nBloodPressureMeasureId");
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

    public Criteria andDMeasureTimeIsNull() {
      addCriterion("D_MEASURE_TIME is null");
      return this;
    }

    public Criteria andDMeasureTimeIsNotNull() {
      addCriterion("D_MEASURE_TIME is not null");
      return this;
    }

    public Criteria andDMeasureTimeEqualTo(Date value) {
      addCriterionForJDBCDate("D_MEASURE_TIME =", value, "dMeasureTime");
      return this;
    }

    public Criteria andDMeasureTimeNotEqualTo(Date value) {
      addCriterionForJDBCDate("D_MEASURE_TIME <>", value, "dMeasureTime");
      return this;
    }

    public Criteria andDMeasureTimeGreaterThan(Date value) {
      addCriterionForJDBCDate("D_MEASURE_TIME >", value, "dMeasureTime");
      return this;
    }

    public Criteria andDMeasureTimeGreaterThanOrEqualTo(Date value) {
      addCriterionForJDBCDate("D_MEASURE_TIME >=", value, "dMeasureTime");
      return this;
    }

    public Criteria andDMeasureTimeLessThan(Date value) {
      addCriterionForJDBCDate("D_MEASURE_TIME <", value, "dMeasureTime");
      return this;
    }

    public Criteria andDMeasureTimeLessThanOrEqualTo(Date value) {
      addCriterionForJDBCDate("D_MEASURE_TIME <=", value, "dMeasureTime");
      return this;
    }

    public Criteria andDMeasureTimeIn(List values) {
      addCriterionForJDBCDate("D_MEASURE_TIME in", values, "dMeasureTime");
      return this;
    }

    public Criteria andDMeasureTimeNotIn(List values) {
      addCriterionForJDBCDate("D_MEASURE_TIME not in", values, "dMeasureTime");
      return this;
    }

    public Criteria andDMeasureTimeBetween(Date value1, Date value2) {
      addCriterionForJDBCDate("D_MEASURE_TIME between", value1, value2, "dMeasureTime");
      return this;
    }

    public Criteria andDMeasureTimeNotBetween(Date value1, Date value2) {
      addCriterionForJDBCDate("D_MEASURE_TIME not between", value1, value2, "dMeasureTime");
      return this;
    }

    public Criteria andNSystolicPressureIsNull() {
      addCriterion("N_SYSTOLIC_PRESSURE is null");
      return this;
    }

    public Criteria andNSystolicPressureIsNotNull() {
      addCriterion("N_SYSTOLIC_PRESSURE is not null");
      return this;
    }

    public Criteria andNSystolicPressureEqualTo(BigDecimal value) {
      addCriterion("N_SYSTOLIC_PRESSURE =", value, "nSystolicPressure");
      return this;
    }

    public Criteria andNSystolicPressureNotEqualTo(BigDecimal value) {
      addCriterion("N_SYSTOLIC_PRESSURE <>", value, "nSystolicPressure");
      return this;
    }

    public Criteria andNSystolicPressureGreaterThan(BigDecimal value) {
      addCriterion("N_SYSTOLIC_PRESSURE >", value, "nSystolicPressure");
      return this;
    }

    public Criteria andNSystolicPressureGreaterThanOrEqualTo(BigDecimal value) {
      addCriterion("N_SYSTOLIC_PRESSURE >=", value, "nSystolicPressure");
      return this;
    }

    public Criteria andNSystolicPressureLessThan(BigDecimal value) {
      addCriterion("N_SYSTOLIC_PRESSURE <", value, "nSystolicPressure");
      return this;
    }

    public Criteria andNSystolicPressureLessThanOrEqualTo(BigDecimal value) {
      addCriterion("N_SYSTOLIC_PRESSURE <=", value, "nSystolicPressure");
      return this;
    }

    public Criteria andNSystolicPressureIn(List values) {
      addCriterion("N_SYSTOLIC_PRESSURE in", values, "nSystolicPressure");
      return this;
    }

    public Criteria andNSystolicPressureNotIn(List values) {
      addCriterion("N_SYSTOLIC_PRESSURE not in", values, "nSystolicPressure");
      return this;
    }

    public Criteria andNSystolicPressureBetween(BigDecimal value1, BigDecimal value2) {
      addCriterion("N_SYSTOLIC_PRESSURE between", value1, value2, "nSystolicPressure");
      return this;
    }

    public Criteria andNSystolicPressureNotBetween(BigDecimal value1, BigDecimal value2) {
      addCriterion("N_SYSTOLIC_PRESSURE not between", value1, value2, "nSystolicPressure");
      return this;
    }

    public Criteria andNDiastolicPressureIsNull() {
      addCriterion("N_DIASTOLIC_PRESSURE is null");
      return this;
    }

    public Criteria andNDiastolicPressureIsNotNull() {
      addCriterion("N_DIASTOLIC_PRESSURE is not null");
      return this;
    }

    public Criteria andNDiastolicPressureEqualTo(BigDecimal value) {
      addCriterion("N_DIASTOLIC_PRESSURE =", value, "nDiastolicPressure");
      return this;
    }

    public Criteria andNDiastolicPressureNotEqualTo(BigDecimal value) {
      addCriterion("N_DIASTOLIC_PRESSURE <>", value, "nDiastolicPressure");
      return this;
    }

    public Criteria andNDiastolicPressureGreaterThan(BigDecimal value) {
      addCriterion("N_DIASTOLIC_PRESSURE >", value, "nDiastolicPressure");
      return this;
    }

    public Criteria andNDiastolicPressureGreaterThanOrEqualTo(BigDecimal value) {
      addCriterion("N_DIASTOLIC_PRESSURE >=", value, "nDiastolicPressure");
      return this;
    }

    public Criteria andNDiastolicPressureLessThan(BigDecimal value) {
      addCriterion("N_DIASTOLIC_PRESSURE <", value, "nDiastolicPressure");
      return this;
    }

    public Criteria andNDiastolicPressureLessThanOrEqualTo(BigDecimal value) {
      addCriterion("N_DIASTOLIC_PRESSURE <=", value, "nDiastolicPressure");
      return this;
    }

    public Criteria andNDiastolicPressureIn(List values) {
      addCriterion("N_DIASTOLIC_PRESSURE in", values, "nDiastolicPressure");
      return this;
    }

    public Criteria andNDiastolicPressureNotIn(List values) {
      addCriterion("N_DIASTOLIC_PRESSURE not in", values, "nDiastolicPressure");
      return this;
    }

    public Criteria andNDiastolicPressureBetween(BigDecimal value1, BigDecimal value2) {
      addCriterion("N_DIASTOLIC_PRESSURE between", value1, value2, "nDiastolicPressure");
      return this;
    }

    public Criteria andNDiastolicPressureNotBetween(BigDecimal value1, BigDecimal value2) {
      addCriterion("N_DIASTOLIC_PRESSURE not between", value1, value2, "nDiastolicPressure");
      return this;
    }

    public Criteria andNMeanPressureIsNull() {
      addCriterion("N_MEAN_PRESSURE is null");
      return this;
    }

    public Criteria andNMeanPressureIsNotNull() {
      addCriterion("N_MEAN_PRESSURE is not null");
      return this;
    }

    public Criteria andNMeanPressureEqualTo(BigDecimal value) {
      addCriterion("N_MEAN_PRESSURE =", value, "nMeanPressure");
      return this;
    }

    public Criteria andNMeanPressureNotEqualTo(BigDecimal value) {
      addCriterion("N_MEAN_PRESSURE <>", value, "nMeanPressure");
      return this;
    }

    public Criteria andNMeanPressureGreaterThan(BigDecimal value) {
      addCriterion("N_MEAN_PRESSURE >", value, "nMeanPressure");
      return this;
    }

    public Criteria andNMeanPressureGreaterThanOrEqualTo(BigDecimal value) {
      addCriterion("N_MEAN_PRESSURE >=", value, "nMeanPressure");
      return this;
    }

    public Criteria andNMeanPressureLessThan(BigDecimal value) {
      addCriterion("N_MEAN_PRESSURE <", value, "nMeanPressure");
      return this;
    }

    public Criteria andNMeanPressureLessThanOrEqualTo(BigDecimal value) {
      addCriterion("N_MEAN_PRESSURE <=", value, "nMeanPressure");
      return this;
    }

    public Criteria andNMeanPressureIn(List values) {
      addCriterion("N_MEAN_PRESSURE in", values, "nMeanPressure");
      return this;
    }

    public Criteria andNMeanPressureNotIn(List values) {
      addCriterion("N_MEAN_PRESSURE not in", values, "nMeanPressure");
      return this;
    }

    public Criteria andNMeanPressureBetween(BigDecimal value1, BigDecimal value2) {
      addCriterion("N_MEAN_PRESSURE between", value1, value2, "nMeanPressure");
      return this;
    }

    public Criteria andNMeanPressureNotBetween(BigDecimal value1, BigDecimal value2) {
      addCriterion("N_MEAN_PRESSURE not between", value1, value2, "nMeanPressure");
      return this;
    }

    public Criteria andNPulseRateIsNull() {
      addCriterion("N_PULSE_RATE is null");
      return this;
    }

    public Criteria andNPulseRateIsNotNull() {
      addCriterion("N_PULSE_RATE is not null");
      return this;
    }

    public Criteria andNPulseRateEqualTo(BigDecimal value) {
      addCriterion("N_PULSE_RATE =", value, "nPulseRate");
      return this;
    }

    public Criteria andNPulseRateNotEqualTo(BigDecimal value) {
      addCriterion("N_PULSE_RATE <>", value, "nPulseRate");
      return this;
    }

    public Criteria andNPulseRateGreaterThan(BigDecimal value) {
      addCriterion("N_PULSE_RATE >", value, "nPulseRate");
      return this;
    }

    public Criteria andNPulseRateGreaterThanOrEqualTo(BigDecimal value) {
      addCriterion("N_PULSE_RATE >=", value, "nPulseRate");
      return this;
    }

    public Criteria andNPulseRateLessThan(BigDecimal value) {
      addCriterion("N_PULSE_RATE <", value, "nPulseRate");
      return this;
    }

    public Criteria andNPulseRateLessThanOrEqualTo(BigDecimal value) {
      addCriterion("N_PULSE_RATE <=", value, "nPulseRate");
      return this;
    }

    public Criteria andNPulseRateIn(List values) {
      addCriterion("N_PULSE_RATE in", values, "nPulseRate");
      return this;
    }

    public Criteria andNPulseRateNotIn(List values) {
      addCriterion("N_PULSE_RATE not in", values, "nPulseRate");
      return this;
    }

    public Criteria andNPulseRateBetween(BigDecimal value1, BigDecimal value2) {
      addCriterion("N_PULSE_RATE between", value1, value2, "nPulseRate");
      return this;
    }

    public Criteria andNPulseRateNotBetween(BigDecimal value1, BigDecimal value2) {
      addCriterion("N_PULSE_RATE not between", value1, value2, "nPulseRate");
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
