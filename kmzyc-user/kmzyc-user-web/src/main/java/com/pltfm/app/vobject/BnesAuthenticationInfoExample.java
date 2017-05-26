package com.pltfm.app.vobject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class BnesAuthenticationInfoExample {
  /**
   * This field was generated by Abator for iBATIS. This field corresponds to the database table
   * BNES_AUTHENTICATION_INFO
   *
   * @abatorgenerated Thu Aug 01 14:05:44 CST 2013
   */
  protected String orderByClause;

  /**
   * This field was generated by Abator for iBATIS. This field corresponds to the database table
   * BNES_AUTHENTICATION_INFO
   *
   * @abatorgenerated Thu Aug 01 14:05:44 CST 2013
   */
  protected List oredCriteria;

  /**
   * This method was generated by Abator for iBATIS. This method corresponds to the database table
   * BNES_AUTHENTICATION_INFO
   *
   * @abatorgenerated Thu Aug 01 14:05:44 CST 2013
   */
  public BnesAuthenticationInfoExample() {
    oredCriteria = new ArrayList();
  }

  /**
   * This method was generated by Abator for iBATIS. This method corresponds to the database table
   * BNES_AUTHENTICATION_INFO
   *
   * @abatorgenerated Thu Aug 01 14:05:44 CST 2013
   */
  protected BnesAuthenticationInfoExample(BnesAuthenticationInfoExample example) {
    this.orderByClause = example.orderByClause;
    this.oredCriteria = example.oredCriteria;
  }

  /**
   * This method was generated by Abator for iBATIS. This method corresponds to the database table
   * BNES_AUTHENTICATION_INFO
   *
   * @abatorgenerated Thu Aug 01 14:05:44 CST 2013
   */
  public void setOrderByClause(String orderByClause) {
    this.orderByClause = orderByClause;
  }

  /**
   * This method was generated by Abator for iBATIS. This method corresponds to the database table
   * BNES_AUTHENTICATION_INFO
   *
   * @abatorgenerated Thu Aug 01 14:05:44 CST 2013
   */
  public String getOrderByClause() {
    return orderByClause;
  }

  /**
   * This method was generated by Abator for iBATIS. This method corresponds to the database table
   * BNES_AUTHENTICATION_INFO
   *
   * @abatorgenerated Thu Aug 01 14:05:44 CST 2013
   */
  public List getOredCriteria() {
    return oredCriteria;
  }

  /**
   * This method was generated by Abator for iBATIS. This method corresponds to the database table
   * BNES_AUTHENTICATION_INFO
   *
   * @abatorgenerated Thu Aug 01 14:05:44 CST 2013
   */
  public void or(Criteria criteria) {
    oredCriteria.add(criteria);
  }

  /**
   * This method was generated by Abator for iBATIS. This method corresponds to the database table
   * BNES_AUTHENTICATION_INFO
   *
   * @abatorgenerated Thu Aug 01 14:05:44 CST 2013
   */
  public Criteria createCriteria() {
    Criteria criteria = createCriteriaInternal();
    if (oredCriteria.size() == 0) {
      oredCriteria.add(criteria);
    }
    return criteria;
  }

  /**
   * This method was generated by Abator for iBATIS. This method corresponds to the database table
   * BNES_AUTHENTICATION_INFO
   *
   * @abatorgenerated Thu Aug 01 14:05:44 CST 2013
   */
  protected Criteria createCriteriaInternal() {
    Criteria criteria = new Criteria();
    return criteria;
  }

  /**
   * This method was generated by Abator for iBATIS. This method corresponds to the database table
   * BNES_AUTHENTICATION_INFO
   *
   * @abatorgenerated Thu Aug 01 14:05:44 CST 2013
   */
  public void clear() {
    oredCriteria.clear();
  }

  /**
   * This class was generated by Abator for iBATIS. This class corresponds to the database table
   * BNES_AUTHENTICATION_INFO
   *
   * @abatorgenerated Thu Aug 01 14:05:44 CST 2013
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

    public Criteria andAuthenticationIdIsNull() {
      addCriterion("AUTHENTICATION_ID is null");
      return this;
    }

    public Criteria andAuthenticationIdIsNotNull() {
      addCriterion("AUTHENTICATION_ID is not null");
      return this;
    }

    public Criteria andAuthenticationIdEqualTo(BigDecimal value) {
      addCriterion("AUTHENTICATION_ID =", value, "authenticationId");
      return this;
    }

    public Criteria andAuthenticationIdNotEqualTo(BigDecimal value) {
      addCriterion("AUTHENTICATION_ID <>", value, "authenticationId");
      return this;
    }

    public Criteria andAuthenticationIdGreaterThan(BigDecimal value) {
      addCriterion("AUTHENTICATION_ID >", value, "authenticationId");
      return this;
    }

    public Criteria andAuthenticationIdGreaterThanOrEqualTo(BigDecimal value) {
      addCriterion("AUTHENTICATION_ID >=", value, "authenticationId");
      return this;
    }

    public Criteria andAuthenticationIdLessThan(BigDecimal value) {
      addCriterion("AUTHENTICATION_ID <", value, "authenticationId");
      return this;
    }

    public Criteria andAuthenticationIdLessThanOrEqualTo(BigDecimal value) {
      addCriterion("AUTHENTICATION_ID <=", value, "authenticationId");
      return this;
    }

    public Criteria andAuthenticationIdIn(List values) {
      addCriterion("AUTHENTICATION_ID in", values, "authenticationId");
      return this;
    }

    public Criteria andAuthenticationIdNotIn(List values) {
      addCriterion("AUTHENTICATION_ID not in", values, "authenticationId");
      return this;
    }

    public Criteria andAuthenticationIdBetween(BigDecimal value1, BigDecimal value2) {
      addCriterion("AUTHENTICATION_ID between", value1, value2, "authenticationId");
      return this;
    }

    public Criteria andAuthenticationIdNotBetween(BigDecimal value1, BigDecimal value2) {
      addCriterion("AUTHENTICATION_ID not between", value1, value2, "authenticationId");
      return this;
    }

    public Criteria andAccountIdIsNull() {
      addCriterion("ACCOUNT_ID is null");
      return this;
    }

    public Criteria andAccountIdIsNotNull() {
      addCriterion("ACCOUNT_ID is not null");
      return this;
    }

    public Criteria andAccountIdEqualTo(BigDecimal value) {
      addCriterion("ACCOUNT_ID =", value, "accountId");
      return this;
    }

    public Criteria andAccountIdNotEqualTo(BigDecimal value) {
      addCriterion("ACCOUNT_ID <>", value, "accountId");
      return this;
    }

    public Criteria andAccountIdGreaterThan(BigDecimal value) {
      addCriterion("ACCOUNT_ID >", value, "accountId");
      return this;
    }

    public Criteria andAccountIdGreaterThanOrEqualTo(BigDecimal value) {
      addCriterion("ACCOUNT_ID >=", value, "accountId");
      return this;
    }

    public Criteria andAccountIdLessThan(BigDecimal value) {
      addCriterion("ACCOUNT_ID <", value, "accountId");
      return this;
    }

    public Criteria andAccountIdLessThanOrEqualTo(BigDecimal value) {
      addCriterion("ACCOUNT_ID <=", value, "accountId");
      return this;
    }

    public Criteria andAccountIdIn(List values) {
      addCriterion("ACCOUNT_ID in", values, "accountId");
      return this;
    }

    public Criteria andAccountIdNotIn(List values) {
      addCriterion("ACCOUNT_ID not in", values, "accountId");
      return this;
    }

    public Criteria andAccountIdBetween(BigDecimal value1, BigDecimal value2) {
      addCriterion("ACCOUNT_ID between", value1, value2, "accountId");
      return this;
    }

    public Criteria andAccountIdNotBetween(BigDecimal value1, BigDecimal value2) {
      addCriterion("ACCOUNT_ID not between", value1, value2, "accountId");
      return this;
    }

    public Criteria andAuthenticationModeIsNull() {
      addCriterion("AUTHENTICATION_MODE is null");
      return this;
    }

    public Criteria andAuthenticationModeIsNotNull() {
      addCriterion("AUTHENTICATION_MODE is not null");
      return this;
    }

    public Criteria andAuthenticationModeEqualTo(Short value) {
      addCriterion("AUTHENTICATION_MODE =", value, "authenticationMode");
      return this;
    }

    public Criteria andAuthenticationModeNotEqualTo(Short value) {
      addCriterion("AUTHENTICATION_MODE <>", value, "authenticationMode");
      return this;
    }

    public Criteria andAuthenticationModeGreaterThan(Short value) {
      addCriterion("AUTHENTICATION_MODE >", value, "authenticationMode");
      return this;
    }

    public Criteria andAuthenticationModeGreaterThanOrEqualTo(Short value) {
      addCriterion("AUTHENTICATION_MODE >=", value, "authenticationMode");
      return this;
    }

    public Criteria andAuthenticationModeLessThan(Short value) {
      addCriterion("AUTHENTICATION_MODE <", value, "authenticationMode");
      return this;
    }

    public Criteria andAuthenticationModeLessThanOrEqualTo(Short value) {
      addCriterion("AUTHENTICATION_MODE <=", value, "authenticationMode");
      return this;
    }

    public Criteria andAuthenticationModeIn(List values) {
      addCriterion("AUTHENTICATION_MODE in", values, "authenticationMode");
      return this;
    }

    public Criteria andAuthenticationModeNotIn(List values) {
      addCriterion("AUTHENTICATION_MODE not in", values, "authenticationMode");
      return this;
    }

    public Criteria andAuthenticationModeBetween(Short value1, Short value2) {
      addCriterion("AUTHENTICATION_MODE between", value1, value2, "authenticationMode");
      return this;
    }

    public Criteria andAuthenticationModeNotBetween(Short value1, Short value2) {
      addCriterion("AUTHENTICATION_MODE not between", value1, value2, "authenticationMode");
      return this;
    }

    public Criteria andCertificateDiscriptionIsNull() {
      addCriterion("CERTIFICATE_DISCRIPTION is null");
      return this;
    }

    public Criteria andCertificateDiscriptionIsNotNull() {
      addCriterion("CERTIFICATE_DISCRIPTION is not null");
      return this;
    }

    public Criteria andCertificateDiscriptionEqualTo(String value) {
      addCriterion("CERTIFICATE_DISCRIPTION =", value, "certificateDiscription");
      return this;
    }

    public Criteria andCertificateDiscriptionNotEqualTo(String value) {
      addCriterion("CERTIFICATE_DISCRIPTION <>", value, "certificateDiscription");
      return this;
    }

    public Criteria andCertificateDiscriptionGreaterThan(String value) {
      addCriterion("CERTIFICATE_DISCRIPTION >", value, "certificateDiscription");
      return this;
    }

    public Criteria andCertificateDiscriptionGreaterThanOrEqualTo(String value) {
      addCriterion("CERTIFICATE_DISCRIPTION >=", value, "certificateDiscription");
      return this;
    }

    public Criteria andCertificateDiscriptionLessThan(String value) {
      addCriterion("CERTIFICATE_DISCRIPTION <", value, "certificateDiscription");
      return this;
    }

    public Criteria andCertificateDiscriptionLessThanOrEqualTo(String value) {
      addCriterion("CERTIFICATE_DISCRIPTION <=", value, "certificateDiscription");
      return this;
    }

    public Criteria andCertificateDiscriptionLike(String value) {
      addCriterion("CERTIFICATE_DISCRIPTION like", value, "certificateDiscription");
      return this;
    }

    public Criteria andCertificateDiscriptionNotLike(String value) {
      addCriterion("CERTIFICATE_DISCRIPTION not like", value, "certificateDiscription");
      return this;
    }

    public Criteria andCertificateDiscriptionIn(List values) {
      addCriterion("CERTIFICATE_DISCRIPTION in", values, "certificateDiscription");
      return this;
    }

    public Criteria andCertificateDiscriptionNotIn(List values) {
      addCriterion("CERTIFICATE_DISCRIPTION not in", values, "certificateDiscription");
      return this;
    }

    public Criteria andCertificateDiscriptionBetween(String value1, String value2) {
      addCriterion("CERTIFICATE_DISCRIPTION between", value1, value2, "certificateDiscription");
      return this;
    }

    public Criteria andCertificateDiscriptionNotBetween(String value1, String value2) {
      addCriterion("CERTIFICATE_DISCRIPTION not between", value1, value2, "certificateDiscription");
      return this;
    }

    public Criteria andExaminationValueIsNull() {
      addCriterion("EXAMINATION_VALUE is null");
      return this;
    }

    public Criteria andExaminationValueIsNotNull() {
      addCriterion("EXAMINATION_VALUE is not null");
      return this;
    }

    public Criteria andExaminationValueEqualTo(Short value) {
      addCriterion("EXAMINATION_VALUE =", value, "examinationValue");
      return this;
    }

    public Criteria andExaminationValueNotEqualTo(Short value) {
      addCriterion("EXAMINATION_VALUE <>", value, "examinationValue");
      return this;
    }

    public Criteria andExaminationValueGreaterThan(Short value) {
      addCriterion("EXAMINATION_VALUE >", value, "examinationValue");
      return this;
    }

    public Criteria andExaminationValueGreaterThanOrEqualTo(Short value) {
      addCriterion("EXAMINATION_VALUE >=", value, "examinationValue");
      return this;
    }

    public Criteria andExaminationValueLessThan(Short value) {
      addCriterion("EXAMINATION_VALUE <", value, "examinationValue");
      return this;
    }

    public Criteria andExaminationValueLessThanOrEqualTo(Short value) {
      addCriterion("EXAMINATION_VALUE <=", value, "examinationValue");
      return this;
    }

    public Criteria andExaminationValueIn(List values) {
      addCriterion("EXAMINATION_VALUE in", values, "examinationValue");
      return this;
    }

    public Criteria andExaminationValueNotIn(List values) {
      addCriterion("EXAMINATION_VALUE not in", values, "examinationValue");
      return this;
    }

    public Criteria andExaminationValueBetween(Short value1, Short value2) {
      addCriterion("EXAMINATION_VALUE between", value1, value2, "examinationValue");
      return this;
    }

    public Criteria andExaminationValueNotBetween(Short value1, Short value2) {
      addCriterion("EXAMINATION_VALUE not between", value1, value2, "examinationValue");
      return this;
    }

    public Criteria andExaminationPersonIsNull() {
      addCriterion("EXAMINATION_PERSON is null");
      return this;
    }

    public Criteria andExaminationPersonIsNotNull() {
      addCriterion("EXAMINATION_PERSON is not null");
      return this;
    }

    public Criteria andExaminationPersonEqualTo(BigDecimal value) {
      addCriterion("EXAMINATION_PERSON =", value, "examinationPerson");
      return this;
    }

    public Criteria andExaminationPersonNotEqualTo(BigDecimal value) {
      addCriterion("EXAMINATION_PERSON <>", value, "examinationPerson");
      return this;
    }

    public Criteria andExaminationPersonGreaterThan(BigDecimal value) {
      addCriterion("EXAMINATION_PERSON >", value, "examinationPerson");
      return this;
    }

    public Criteria andExaminationPersonGreaterThanOrEqualTo(BigDecimal value) {
      addCriterion("EXAMINATION_PERSON >=", value, "examinationPerson");
      return this;
    }

    public Criteria andExaminationPersonLessThan(BigDecimal value) {
      addCriterion("EXAMINATION_PERSON <", value, "examinationPerson");
      return this;
    }

    public Criteria andExaminationPersonLessThanOrEqualTo(BigDecimal value) {
      addCriterion("EXAMINATION_PERSON <=", value, "examinationPerson");
      return this;
    }

    public Criteria andExaminationPersonIn(List values) {
      addCriterion("EXAMINATION_PERSON in", values, "examinationPerson");
      return this;
    }

    public Criteria andExaminationPersonNotIn(List values) {
      addCriterion("EXAMINATION_PERSON not in", values, "examinationPerson");
      return this;
    }

    public Criteria andExaminationPersonBetween(BigDecimal value1, BigDecimal value2) {
      addCriterion("EXAMINATION_PERSON between", value1, value2, "examinationPerson");
      return this;
    }

    public Criteria andExaminationPersonNotBetween(BigDecimal value1, BigDecimal value2) {
      addCriterion("EXAMINATION_PERSON not between", value1, value2, "examinationPerson");
      return this;
    }

    public Criteria andExaminationDateIsNull() {
      addCriterion("EXAMINATION_DATE is null");
      return this;
    }

    public Criteria andExaminationDateIsNotNull() {
      addCriterion("EXAMINATION_DATE is not null");
      return this;
    }

    public Criteria andExaminationDateEqualTo(Date value) {
      addCriterionForJDBCDate("EXAMINATION_DATE =", value, "examinationDate");
      return this;
    }

    public Criteria andExaminationDateNotEqualTo(Date value) {
      addCriterionForJDBCDate("EXAMINATION_DATE <>", value, "examinationDate");
      return this;
    }

    public Criteria andExaminationDateGreaterThan(Date value) {
      addCriterionForJDBCDate("EXAMINATION_DATE >", value, "examinationDate");
      return this;
    }

    public Criteria andExaminationDateGreaterThanOrEqualTo(Date value) {
      addCriterionForJDBCDate("EXAMINATION_DATE >=", value, "examinationDate");
      return this;
    }

    public Criteria andExaminationDateLessThan(Date value) {
      addCriterionForJDBCDate("EXAMINATION_DATE <", value, "examinationDate");
      return this;
    }

    public Criteria andExaminationDateLessThanOrEqualTo(Date value) {
      addCriterionForJDBCDate("EXAMINATION_DATE <=", value, "examinationDate");
      return this;
    }

    public Criteria andExaminationDateIn(List values) {
      addCriterionForJDBCDate("EXAMINATION_DATE in", values, "examinationDate");
      return this;
    }

    public Criteria andExaminationDateNotIn(List values) {
      addCriterionForJDBCDate("EXAMINATION_DATE not in", values, "examinationDate");
      return this;
    }

    public Criteria andExaminationDateBetween(Date value1, Date value2) {
      addCriterionForJDBCDate("EXAMINATION_DATE between", value1, value2, "examinationDate");
      return this;
    }

    public Criteria andExaminationDateNotBetween(Date value1, Date value2) {
      addCriterionForJDBCDate("EXAMINATION_DATE not between", value1, value2, "examinationDate");
      return this;
    }

    public Criteria andCreateDateIsNull() {
      addCriterion("CREATE_DATE is null");
      return this;
    }

    public Criteria andCreateDateIsNotNull() {
      addCriterion("CREATE_DATE is not null");
      return this;
    }

    public Criteria andCreateDateEqualTo(Date value) {
      addCriterionForJDBCDate("CREATE_DATE =", value, "createDate");
      return this;
    }

    public Criteria andCreateDateNotEqualTo(Date value) {
      addCriterionForJDBCDate("CREATE_DATE <>", value, "createDate");
      return this;
    }

    public Criteria andCreateDateGreaterThan(Date value) {
      addCriterionForJDBCDate("CREATE_DATE >", value, "createDate");
      return this;
    }

    public Criteria andCreateDateGreaterThanOrEqualTo(Date value) {
      addCriterionForJDBCDate("CREATE_DATE >=", value, "createDate");
      return this;
    }

    public Criteria andCreateDateLessThan(Date value) {
      addCriterionForJDBCDate("CREATE_DATE <", value, "createDate");
      return this;
    }

    public Criteria andCreateDateLessThanOrEqualTo(Date value) {
      addCriterionForJDBCDate("CREATE_DATE <=", value, "createDate");
      return this;
    }

    public Criteria andCreateDateIn(List values) {
      addCriterionForJDBCDate("CREATE_DATE in", values, "createDate");
      return this;
    }

    public Criteria andCreateDateNotIn(List values) {
      addCriterionForJDBCDate("CREATE_DATE not in", values, "createDate");
      return this;
    }

    public Criteria andCreateDateBetween(Date value1, Date value2) {
      addCriterionForJDBCDate("CREATE_DATE between", value1, value2, "createDate");
      return this;
    }

    public Criteria andCreateDateNotBetween(Date value1, Date value2) {
      addCriterionForJDBCDate("CREATE_DATE not between", value1, value2, "createDate");
      return this;
    }

    public Criteria andCreatedIdIsNull() {
      addCriterion("CREATED_ID is null");
      return this;
    }

    public Criteria andCreatedIdIsNotNull() {
      addCriterion("CREATED_ID is not null");
      return this;
    }

    public Criteria andCreatedIdEqualTo(BigDecimal value) {
      addCriterion("CREATED_ID =", value, "createdId");
      return this;
    }

    public Criteria andCreatedIdNotEqualTo(BigDecimal value) {
      addCriterion("CREATED_ID <>", value, "createdId");
      return this;
    }

    public Criteria andCreatedIdGreaterThan(BigDecimal value) {
      addCriterion("CREATED_ID >", value, "createdId");
      return this;
    }

    public Criteria andCreatedIdGreaterThanOrEqualTo(BigDecimal value) {
      addCriterion("CREATED_ID >=", value, "createdId");
      return this;
    }

    public Criteria andCreatedIdLessThan(BigDecimal value) {
      addCriterion("CREATED_ID <", value, "createdId");
      return this;
    }

    public Criteria andCreatedIdLessThanOrEqualTo(BigDecimal value) {
      addCriterion("CREATED_ID <=", value, "createdId");
      return this;
    }

    public Criteria andCreatedIdIn(List values) {
      addCriterion("CREATED_ID in", values, "createdId");
      return this;
    }

    public Criteria andCreatedIdNotIn(List values) {
      addCriterion("CREATED_ID not in", values, "createdId");
      return this;
    }

    public Criteria andCreatedIdBetween(BigDecimal value1, BigDecimal value2) {
      addCriterion("CREATED_ID between", value1, value2, "createdId");
      return this;
    }

    public Criteria andCreatedIdNotBetween(BigDecimal value1, BigDecimal value2) {
      addCriterion("CREATED_ID not between", value1, value2, "createdId");
      return this;
    }

    public Criteria andModifyDateIsNull() {
      addCriterion("MODIFY_DATE is null");
      return this;
    }

    public Criteria andModifyDateIsNotNull() {
      addCriterion("MODIFY_DATE is not null");
      return this;
    }

    public Criteria andModifyDateEqualTo(Date value) {
      addCriterionForJDBCDate("MODIFY_DATE =", value, "modifyDate");
      return this;
    }

    public Criteria andModifyDateNotEqualTo(Date value) {
      addCriterionForJDBCDate("MODIFY_DATE <>", value, "modifyDate");
      return this;
    }

    public Criteria andModifyDateGreaterThan(Date value) {
      addCriterionForJDBCDate("MODIFY_DATE >", value, "modifyDate");
      return this;
    }

    public Criteria andModifyDateGreaterThanOrEqualTo(Date value) {
      addCriterionForJDBCDate("MODIFY_DATE >=", value, "modifyDate");
      return this;
    }

    public Criteria andModifyDateLessThan(Date value) {
      addCriterionForJDBCDate("MODIFY_DATE <", value, "modifyDate");
      return this;
    }

    public Criteria andModifyDateLessThanOrEqualTo(Date value) {
      addCriterionForJDBCDate("MODIFY_DATE <=", value, "modifyDate");
      return this;
    }

    public Criteria andModifyDateIn(List values) {
      addCriterionForJDBCDate("MODIFY_DATE in", values, "modifyDate");
      return this;
    }

    public Criteria andModifyDateNotIn(List values) {
      addCriterionForJDBCDate("MODIFY_DATE not in", values, "modifyDate");
      return this;
    }

    public Criteria andModifyDateBetween(Date value1, Date value2) {
      addCriterionForJDBCDate("MODIFY_DATE between", value1, value2, "modifyDate");
      return this;
    }

    public Criteria andModifyDateNotBetween(Date value1, Date value2) {
      addCriterionForJDBCDate("MODIFY_DATE not between", value1, value2, "modifyDate");
      return this;
    }

    public Criteria andModifieIdIsNull() {
      addCriterion("MODIFIE_ID is null");
      return this;
    }

    public Criteria andModifieIdIsNotNull() {
      addCriterion("MODIFIE_ID is not null");
      return this;
    }

    public Criteria andModifieIdEqualTo(BigDecimal value) {
      addCriterion("MODIFIE_ID =", value, "modifieId");
      return this;
    }

    public Criteria andModifieIdNotEqualTo(BigDecimal value) {
      addCriterion("MODIFIE_ID <>", value, "modifieId");
      return this;
    }

    public Criteria andModifieIdGreaterThan(BigDecimal value) {
      addCriterion("MODIFIE_ID >", value, "modifieId");
      return this;
    }

    public Criteria andModifieIdGreaterThanOrEqualTo(BigDecimal value) {
      addCriterion("MODIFIE_ID >=", value, "modifieId");
      return this;
    }

    public Criteria andModifieIdLessThan(BigDecimal value) {
      addCriterion("MODIFIE_ID <", value, "modifieId");
      return this;
    }

    public Criteria andModifieIdLessThanOrEqualTo(BigDecimal value) {
      addCriterion("MODIFIE_ID <=", value, "modifieId");
      return this;
    }

    public Criteria andModifieIdIn(List values) {
      addCriterion("MODIFIE_ID in", values, "modifieId");
      return this;
    }

    public Criteria andModifieIdNotIn(List values) {
      addCriterion("MODIFIE_ID not in", values, "modifieId");
      return this;
    }

    public Criteria andModifieIdBetween(BigDecimal value1, BigDecimal value2) {
      addCriterion("MODIFIE_ID between", value1, value2, "modifieId");
      return this;
    }

    public Criteria andModifieIdNotBetween(BigDecimal value1, BigDecimal value2) {
      addCriterion("MODIFIE_ID not between", value1, value2, "modifieId");
      return this;
    }
  }
}
