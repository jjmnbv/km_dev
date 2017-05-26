package com.pltfm.app.vobject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class PhysicalFitnessTestInfoExample {
  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database table
   * PHYSICAL_FITNESS_TEST_INFO
   *
   * @ibatorgenerated Thu Jul 11 16:57:47 CST 2013
   */
  protected String orderByClause;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database table
   * PHYSICAL_FITNESS_TEST_INFO
   *
   * @ibatorgenerated Thu Jul 11 16:57:47 CST 2013
   */
  protected List oredCriteria;

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table PHYSICAL_FITNESS_TEST_INFO
   *
   * @ibatorgenerated Thu Jul 11 16:57:47 CST 2013
   */
  public PhysicalFitnessTestInfoExample() {
    oredCriteria = new ArrayList();
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table PHYSICAL_FITNESS_TEST_INFO
   *
   * @ibatorgenerated Thu Jul 11 16:57:47 CST 2013
   */
  protected PhysicalFitnessTestInfoExample(PhysicalFitnessTestInfoExample example) {
    this.orderByClause = example.orderByClause;
    this.oredCriteria = example.oredCriteria;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table PHYSICAL_FITNESS_TEST_INFO
   *
   * @ibatorgenerated Thu Jul 11 16:57:47 CST 2013
   */
  public void setOrderByClause(String orderByClause) {
    this.orderByClause = orderByClause;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table PHYSICAL_FITNESS_TEST_INFO
   *
   * @ibatorgenerated Thu Jul 11 16:57:47 CST 2013
   */
  public String getOrderByClause() {
    return orderByClause;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table PHYSICAL_FITNESS_TEST_INFO
   *
   * @ibatorgenerated Thu Jul 11 16:57:47 CST 2013
   */
  public List getOredCriteria() {
    return oredCriteria;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table PHYSICAL_FITNESS_TEST_INFO
   *
   * @ibatorgenerated Thu Jul 11 16:57:47 CST 2013
   */
  public void or(Criteria criteria) {
    oredCriteria.add(criteria);
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table PHYSICAL_FITNESS_TEST_INFO
   *
   * @ibatorgenerated Thu Jul 11 16:57:47 CST 2013
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
   * table PHYSICAL_FITNESS_TEST_INFO
   *
   * @ibatorgenerated Thu Jul 11 16:57:47 CST 2013
   */
  protected Criteria createCriteriaInternal() {
    Criteria criteria = new Criteria();
    return criteria;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table PHYSICAL_FITNESS_TEST_INFO
   *
   * @ibatorgenerated Thu Jul 11 16:57:47 CST 2013
   */
  public void clear() {
    oredCriteria.clear();
  }

  /**
   * This class was generated by Apache iBATIS ibator. This class corresponds to the database table
   * PHYSICAL_FITNESS_TEST_INFO
   *
   * @ibatorgenerated Thu Jul 11 16:57:47 CST 2013
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

    public Criteria andNPhysicalFitnessIdIsNull() {
      addCriterion("N_PHYSICAL_FITNESS_ID is null");
      return this;
    }

    public Criteria andNPhysicalFitnessIdIsNotNull() {
      addCriterion("N_PHYSICAL_FITNESS_ID is not null");
      return this;
    }

    public Criteria andNPhysicalFitnessIdEqualTo(BigDecimal value) {
      addCriterion("N_PHYSICAL_FITNESS_ID =", value, "nPhysicalFitnessId");
      return this;
    }

    public Criteria andNPhysicalFitnessIdNotEqualTo(BigDecimal value) {
      addCriterion("N_PHYSICAL_FITNESS_ID <>", value, "nPhysicalFitnessId");
      return this;
    }

    public Criteria andNPhysicalFitnessIdGreaterThan(BigDecimal value) {
      addCriterion("N_PHYSICAL_FITNESS_ID >", value, "nPhysicalFitnessId");
      return this;
    }

    public Criteria andNPhysicalFitnessIdGreaterThanOrEqualTo(BigDecimal value) {
      addCriterion("N_PHYSICAL_FITNESS_ID >=", value, "nPhysicalFitnessId");
      return this;
    }

    public Criteria andNPhysicalFitnessIdLessThan(BigDecimal value) {
      addCriterion("N_PHYSICAL_FITNESS_ID <", value, "nPhysicalFitnessId");
      return this;
    }

    public Criteria andNPhysicalFitnessIdLessThanOrEqualTo(BigDecimal value) {
      addCriterion("N_PHYSICAL_FITNESS_ID <=", value, "nPhysicalFitnessId");
      return this;
    }

    public Criteria andNPhysicalFitnessIdIn(List values) {
      addCriterion("N_PHYSICAL_FITNESS_ID in", values, "nPhysicalFitnessId");
      return this;
    }

    public Criteria andNPhysicalFitnessIdNotIn(List values) {
      addCriterion("N_PHYSICAL_FITNESS_ID not in", values, "nPhysicalFitnessId");
      return this;
    }

    public Criteria andNPhysicalFitnessIdBetween(BigDecimal value1, BigDecimal value2) {
      addCriterion("N_PHYSICAL_FITNESS_ID between", value1, value2, "nPhysicalFitnessId");
      return this;
    }

    public Criteria andNPhysicalFitnessIdNotBetween(BigDecimal value1, BigDecimal value2) {
      addCriterion("N_PHYSICAL_FITNESS_ID not between", value1, value2, "nPhysicalFitnessId");
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

    public Criteria andDTestingTimeIsNull() {
      addCriterion("D_TESTING_TIME is null");
      return this;
    }

    public Criteria andDTestingTimeIsNotNull() {
      addCriterion("D_TESTING_TIME is not null");
      return this;
    }

    public Criteria andDTestingTimeEqualTo(Date value) {
      addCriterionForJDBCDate("D_TESTING_TIME =", value, "dTestingTime");
      return this;
    }

    public Criteria andDTestingTimeNotEqualTo(Date value) {
      addCriterionForJDBCDate("D_TESTING_TIME <>", value, "dTestingTime");
      return this;
    }

    public Criteria andDTestingTimeGreaterThan(Date value) {
      addCriterionForJDBCDate("D_TESTING_TIME >", value, "dTestingTime");
      return this;
    }

    public Criteria andDTestingTimeGreaterThanOrEqualTo(Date value) {
      addCriterionForJDBCDate("D_TESTING_TIME >=", value, "dTestingTime");
      return this;
    }

    public Criteria andDTestingTimeLessThan(Date value) {
      addCriterionForJDBCDate("D_TESTING_TIME <", value, "dTestingTime");
      return this;
    }

    public Criteria andDTestingTimeLessThanOrEqualTo(Date value) {
      addCriterionForJDBCDate("D_TESTING_TIME <=", value, "dTestingTime");
      return this;
    }

    public Criteria andDTestingTimeIn(List values) {
      addCriterionForJDBCDate("D_TESTING_TIME in", values, "dTestingTime");
      return this;
    }

    public Criteria andDTestingTimeNotIn(List values) {
      addCriterionForJDBCDate("D_TESTING_TIME not in", values, "dTestingTime");
      return this;
    }

    public Criteria andDTestingTimeBetween(Date value1, Date value2) {
      addCriterionForJDBCDate("D_TESTING_TIME between", value1, value2, "dTestingTime");
      return this;
    }

    public Criteria andDTestingTimeNotBetween(Date value1, Date value2) {
      addCriterionForJDBCDate("D_TESTING_TIME not between", value1, value2, "dTestingTime");
      return this;
    }

    public Criteria andNWeightIsNull() {
      addCriterion("N_WEIGHT is null");
      return this;
    }

    public Criteria andNWeightIsNotNull() {
      addCriterion("N_WEIGHT is not null");
      return this;
    }

    public Criteria andNWeightEqualTo(BigDecimal value) {
      addCriterion("N_WEIGHT =", value, "nWeight");
      return this;
    }

    public Criteria andNWeightNotEqualTo(BigDecimal value) {
      addCriterion("N_WEIGHT <>", value, "nWeight");
      return this;
    }

    public Criteria andNWeightGreaterThan(BigDecimal value) {
      addCriterion("N_WEIGHT >", value, "nWeight");
      return this;
    }

    public Criteria andNWeightGreaterThanOrEqualTo(BigDecimal value) {
      addCriterion("N_WEIGHT >=", value, "nWeight");
      return this;
    }

    public Criteria andNWeightLessThan(BigDecimal value) {
      addCriterion("N_WEIGHT <", value, "nWeight");
      return this;
    }

    public Criteria andNWeightLessThanOrEqualTo(BigDecimal value) {
      addCriterion("N_WEIGHT <=", value, "nWeight");
      return this;
    }

    public Criteria andNWeightIn(List values) {
      addCriterion("N_WEIGHT in", values, "nWeight");
      return this;
    }

    public Criteria andNWeightNotIn(List values) {
      addCriterion("N_WEIGHT not in", values, "nWeight");
      return this;
    }

    public Criteria andNWeightBetween(BigDecimal value1, BigDecimal value2) {
      addCriterion("N_WEIGHT between", value1, value2, "nWeight");
      return this;
    }

    public Criteria andNWeightNotBetween(BigDecimal value1, BigDecimal value2) {
      addCriterion("N_WEIGHT not between", value1, value2, "nWeight");
      return this;
    }

    public Criteria andNConstitutionalIndexIsNull() {
      addCriterion("N_CONSTITUTIONAL_INDEX is null");
      return this;
    }

    public Criteria andNConstitutionalIndexIsNotNull() {
      addCriterion("N_CONSTITUTIONAL_INDEX is not null");
      return this;
    }

    public Criteria andNConstitutionalIndexEqualTo(BigDecimal value) {
      addCriterion("N_CONSTITUTIONAL_INDEX =", value, "nConstitutionalIndex");
      return this;
    }

    public Criteria andNConstitutionalIndexNotEqualTo(BigDecimal value) {
      addCriterion("N_CONSTITUTIONAL_INDEX <>", value, "nConstitutionalIndex");
      return this;
    }

    public Criteria andNConstitutionalIndexGreaterThan(BigDecimal value) {
      addCriterion("N_CONSTITUTIONAL_INDEX >", value, "nConstitutionalIndex");
      return this;
    }

    public Criteria andNConstitutionalIndexGreaterThanOrEqualTo(BigDecimal value) {
      addCriterion("N_CONSTITUTIONAL_INDEX >=", value, "nConstitutionalIndex");
      return this;
    }

    public Criteria andNConstitutionalIndexLessThan(BigDecimal value) {
      addCriterion("N_CONSTITUTIONAL_INDEX <", value, "nConstitutionalIndex");
      return this;
    }

    public Criteria andNConstitutionalIndexLessThanOrEqualTo(BigDecimal value) {
      addCriterion("N_CONSTITUTIONAL_INDEX <=", value, "nConstitutionalIndex");
      return this;
    }

    public Criteria andNConstitutionalIndexIn(List values) {
      addCriterion("N_CONSTITUTIONAL_INDEX in", values, "nConstitutionalIndex");
      return this;
    }

    public Criteria andNConstitutionalIndexNotIn(List values) {
      addCriterion("N_CONSTITUTIONAL_INDEX not in", values, "nConstitutionalIndex");
      return this;
    }

    public Criteria andNConstitutionalIndexBetween(BigDecimal value1, BigDecimal value2) {
      addCriterion("N_CONSTITUTIONAL_INDEX between", value1, value2, "nConstitutionalIndex");
      return this;
    }

    public Criteria andNConstitutionalIndexNotBetween(BigDecimal value1, BigDecimal value2) {
      addCriterion("N_CONSTITUTIONAL_INDEX not between", value1, value2, "nConstitutionalIndex");
      return this;
    }

    public Criteria andNFatContentIsNull() {
      addCriterion("N_FAT_CONTENT is null");
      return this;
    }

    public Criteria andNFatContentIsNotNull() {
      addCriterion("N_FAT_CONTENT is not null");
      return this;
    }

    public Criteria andNFatContentEqualTo(BigDecimal value) {
      addCriterion("N_FAT_CONTENT =", value, "nFatContent");
      return this;
    }

    public Criteria andNFatContentNotEqualTo(BigDecimal value) {
      addCriterion("N_FAT_CONTENT <>", value, "nFatContent");
      return this;
    }

    public Criteria andNFatContentGreaterThan(BigDecimal value) {
      addCriterion("N_FAT_CONTENT >", value, "nFatContent");
      return this;
    }

    public Criteria andNFatContentGreaterThanOrEqualTo(BigDecimal value) {
      addCriterion("N_FAT_CONTENT >=", value, "nFatContent");
      return this;
    }

    public Criteria andNFatContentLessThan(BigDecimal value) {
      addCriterion("N_FAT_CONTENT <", value, "nFatContent");
      return this;
    }

    public Criteria andNFatContentLessThanOrEqualTo(BigDecimal value) {
      addCriterion("N_FAT_CONTENT <=", value, "nFatContent");
      return this;
    }

    public Criteria andNFatContentIn(List values) {
      addCriterion("N_FAT_CONTENT in", values, "nFatContent");
      return this;
    }

    public Criteria andNFatContentNotIn(List values) {
      addCriterion("N_FAT_CONTENT not in", values, "nFatContent");
      return this;
    }

    public Criteria andNFatContentBetween(BigDecimal value1, BigDecimal value2) {
      addCriterion("N_FAT_CONTENT between", value1, value2, "nFatContent");
      return this;
    }

    public Criteria andNFatContentNotBetween(BigDecimal value1, BigDecimal value2) {
      addCriterion("N_FAT_CONTENT not between", value1, value2, "nFatContent");
      return this;
    }

    public Criteria andNHeightIsNull() {
      addCriterion("N_HEIGHT is null");
      return this;
    }

    public Criteria andNHeightIsNotNull() {
      addCriterion("N_HEIGHT is not null");
      return this;
    }

    public Criteria andNHeightEqualTo(BigDecimal value) {
      addCriterion("N_HEIGHT =", value, "nHeight");
      return this;
    }

    public Criteria andNHeightNotEqualTo(BigDecimal value) {
      addCriterion("N_HEIGHT <>", value, "nHeight");
      return this;
    }

    public Criteria andNHeightGreaterThan(BigDecimal value) {
      addCriterion("N_HEIGHT >", value, "nHeight");
      return this;
    }

    public Criteria andNHeightGreaterThanOrEqualTo(BigDecimal value) {
      addCriterion("N_HEIGHT >=", value, "nHeight");
      return this;
    }

    public Criteria andNHeightLessThan(BigDecimal value) {
      addCriterion("N_HEIGHT <", value, "nHeight");
      return this;
    }

    public Criteria andNHeightLessThanOrEqualTo(BigDecimal value) {
      addCriterion("N_HEIGHT <=", value, "nHeight");
      return this;
    }

    public Criteria andNHeightIn(List values) {
      addCriterion("N_HEIGHT in", values, "nHeight");
      return this;
    }

    public Criteria andNHeightNotIn(List values) {
      addCriterion("N_HEIGHT not in", values, "nHeight");
      return this;
    }

    public Criteria andNHeightBetween(BigDecimal value1, BigDecimal value2) {
      addCriterion("N_HEIGHT between", value1, value2, "nHeight");
      return this;
    }

    public Criteria andNHeightNotBetween(BigDecimal value1, BigDecimal value2) {
      addCriterion("N_HEIGHT not between", value1, value2, "nHeight");
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
