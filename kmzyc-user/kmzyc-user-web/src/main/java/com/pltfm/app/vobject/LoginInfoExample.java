package com.pltfm.app.vobject;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 登录信息条件类
 * 
 * @author cjm
 * @since 2013-7-10
 */
public class LoginInfoExample {

  /**
   * This field was generated by Abator for iBATIS. This field corresponds to the database table
   * LOGIN_INFO
   * 
   * @abatorgenerated Tue Jul 09 16:19:36 CST 2013
   */
  protected String orderByClause;
  /**
   * This field was generated by Abator for iBATIS. This field corresponds to the database table
   * LOGIN_INFO
   * 
   * @abatorgenerated Tue Jul 09 16:19:36 CST 2013
   */
  protected List oredCriteria;

  /**
   * This method was generated by Abator for iBATIS. This method corresponds to the database table
   * LOGIN_INFO
   * 
   * @abatorgenerated Tue Jul 09 16:19:36 CST 2013
   */
  public LoginInfoExample() {
    oredCriteria = new ArrayList();
  }

  /**
   * This method was generated by Abator for iBATIS. This method corresponds to the database table
   * LOGIN_INFO
   * 
   * @abatorgenerated Tue Jul 09 16:19:36 CST 2013
   */
  protected LoginInfoExample(LoginInfoExample example) {
    this.orderByClause = example.orderByClause;
    this.oredCriteria = example.oredCriteria;
  }

  /**
   * This method was generated by Abator for iBATIS. This method corresponds to the database table
   * LOGIN_INFO
   * 
   * @abatorgenerated Tue Jul 09 16:19:36 CST 2013
   */
  public void setOrderByClause(String orderByClause) {
    this.orderByClause = orderByClause;
  }

  /**
   * This method was generated by Abator for iBATIS. This method corresponds to the database table
   * LOGIN_INFO
   * 
   * @abatorgenerated Tue Jul 09 16:19:36 CST 2013
   */
  public String getOrderByClause() {
    return orderByClause;
  }

  /**
   * This method was generated by Abator for iBATIS. This method corresponds to the database table
   * LOGIN_INFO
   * 
   * @abatorgenerated Tue Jul 09 16:19:36 CST 2013
   */
  public List getOredCriteria() {
    return oredCriteria;
  }

  /**
   * This method was generated by Abator for iBATIS. This method corresponds to the database table
   * LOGIN_INFO
   * 
   * @abatorgenerated Tue Jul 09 16:19:36 CST 2013
   */
  public void or(Criteria criteria) {
    oredCriteria.add(criteria);
  }

  /**
   * This method was generated by Abator for iBATIS. This method corresponds to the database table
   * LOGIN_INFO
   * 
   * @abatorgenerated Tue Jul 09 16:19:36 CST 2013
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
   * LOGIN_INFO
   * 
   * @abatorgenerated Tue Jul 09 16:19:36 CST 2013
   */
  protected Criteria createCriteriaInternal() {
    Criteria criteria = new Criteria();
    return criteria;
  }

  /**
   * This method was generated by Abator for iBATIS. This method corresponds to the database table
   * LOGIN_INFO
   * 
   * @abatorgenerated Tue Jul 09 16:19:36 CST 2013
   */
  public void clear() {
    oredCriteria.clear();
  }

  /**
   * This class was generated by Abator for iBATIS. This class corresponds to the database table
   * LOGIN_INFO
   * 
   * @abatorgenerated Tue Jul 09 16:19:36 CST 2013
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

    public Criteria andn_LoginIdIsNull() {
      addCriterion("N_LOGIN_ID is null");
      return this;
    }

    public Criteria andn_LoginIdIsNotNull() {
      addCriterion("N_LOGIN_ID is not null");
      return this;
    }

    public Criteria andn_LoginIdEqualTo(Integer value) {
      addCriterion("N_LOGIN_ID =", value, "n_LoginId");
      return this;
    }

    public Criteria andn_LoginIdNotEqualTo(Integer value) {
      addCriterion("N_LOGIN_ID <>", value, "n_LoginId");
      return this;
    }

    public Criteria andn_LoginIdGreaterThan(BigDecimal value) {
      addCriterion("N_LOGIN_ID >", value, "n_LoginId");
      return this;
    }

    public Criteria andn_LoginIdGreaterThanOrEqualTo(BigDecimal value) {
      addCriterion("N_LOGIN_ID >=", value, "n_LoginId");
      return this;
    }

    public Criteria andn_LoginIdLessThan(BigDecimal value) {
      addCriterion("N_LOGIN_ID <", value, "n_LoginId");
      return this;
    }

    public Criteria andn_LoginIdLessThanOrEqualTo(BigDecimal value) {
      addCriterion("N_LOGIN_ID <=", value, "n_LoginId");
      return this;
    }

    public Criteria andn_LoginIdIn(List values) {
      addCriterion("N_LOGIN_ID in", values, "n_LoginId");
      return this;
    }

    public Criteria andn_LoginIdNotIn(List values) {
      addCriterion("N_LOGIN_ID not in", values, "n_LoginId");
      return this;
    }

    public Criteria andn_LoginIdBetween(BigDecimal value1, BigDecimal value2) {
      addCriterion("N_LOGIN_ID between", value1, value2, "n_LoginId");
      return this;
    }

    public Criteria andn_LoginIdNotBetween(BigDecimal value1, BigDecimal value2) {
      addCriterion("N_LOGIN_ID not between", value1, value2, "n_LoginId");
      return this;
    }

    public Criteria andn_LevelIdIsNull() {
      addCriterion("N_LEVEL_ID is null");
      return this;
    }

    public Criteria andn_LevelIdIsNotNull() {
      addCriterion("N_LEVEL_ID is not null");
      return this;
    }

    public Criteria andn_LevelIdEqualTo(BigDecimal value) {
      addCriterion("N_LEVEL_ID =", value, "n_LevelId");
      return this;
    }

    public Criteria andn_LevelIdNotEqualTo(BigDecimal value) {
      addCriterion("N_LEVEL_ID <>", value, "n_LevelId");
      return this;
    }

    public Criteria andn_LevelIdGreaterThan(BigDecimal value) {
      addCriterion("N_LEVEL_ID >", value, "n_LevelId");
      return this;
    }

    public Criteria andn_LevelIdGreaterThanOrEqualTo(BigDecimal value) {
      addCriterion("N_LEVEL_ID >=", value, "n_LevelId");
      return this;
    }

    public Criteria andn_LevelIdLessThan(BigDecimal value) {
      addCriterion("N_LEVEL_ID <", value, "n_LevelId");
      return this;
    }

    public Criteria andn_LevelIdLessThanOrEqualTo(BigDecimal value) {
      addCriterion("N_LEVEL_ID <=", value, "n_LevelId");
      return this;
    }

    public Criteria andn_LevelIdIn(List values) {
      addCriterion("N_LEVEL_ID in", values, "n_LevelId");
      return this;
    }

    public Criteria andn_LevelIdNotIn(List values) {
      addCriterion("N_LEVEL_ID not in", values, "n_LevelId");
      return this;
    }

    public Criteria andn_LevelIdBetween(BigDecimal value1, BigDecimal value2) {
      addCriterion("N_LEVEL_ID between", value1, value2, "n_LevelId");
      return this;
    }

    public Criteria andn_LevelIdNotBetween(Integer value1, Integer value2) {
      addCriterion("N_LEVEL_ID not between", value1, value2, "n_LevelId");
      return this;
    }

    public Criteria andn_CustomerTypeIdIsNull() {
      addCriterion("N_CUSTOMER_TYPE_ID is null");
      return this;
    }

    public Criteria andn_CustomerTypeIdIsNotNull() {
      addCriterion("N_CUSTOMER_TYPE_ID is not null");
      return this;
    }

    public Criteria andn_CustomerTypeIdEqualTo(Integer value) {
      addCriterion("N_CUSTOMER_TYPE_ID =", value, "n_CustomerTypeId");
      return this;
    }

    public Criteria andn_CustomerTypeIdNotEqualTo(Integer value) {
      addCriterion("N_CUSTOMER_TYPE_ID <>", value, "n_CustomerTypeId");
      return this;
    }

    public Criteria andn_CustomerTypeIdGreaterThan(Integer value) {
      addCriterion("N_CUSTOMER_TYPE_ID >", value, "n_CustomerTypeId");
      return this;
    }

    public Criteria andn_CustomerTypeIdGreaterThanOrEqualTo(Integer value) {
      addCriterion("N_CUSTOMER_TYPE_ID >=", value, "n_CustomerTypeId");
      return this;
    }

    public Criteria andn_CustomerTypeIdLessThan(Integer value) {
      addCriterion("N_CUSTOMER_TYPE_ID <", value, "n_CustomerTypeId");
      return this;
    }

    public Criteria andn_CustomerTypeIdLessThanOrEqualTo(Integer value) {
      addCriterion("N_CUSTOMER_TYPE_ID <=", value, "n_CustomerTypeId");
      return this;
    }

    public Criteria andn_CustomerTypeIdIn(List values) {
      addCriterion("N_CUSTOMER_TYPE_ID in", values, "n_CustomerTypeId");
      return this;
    }

    public Criteria andn_CustomerTypeIdNotIn(List values) {
      addCriterion("N_CUSTOMER_TYPE_ID not in", values, "n_CustomerTypeId");
      return this;
    }

    public Criteria andn_CustomerTypeIdBetween(Integer value1, Integer value2) {
      addCriterion("N_CUSTOMER_TYPE_ID between", value1, value2, "n_CustomerTypeId");
      return this;
    }

    public Criteria andn_CustomerTypeIdNotBetween(Integer value1, Integer value2) {
      addCriterion("N_CUSTOMER_TYPE_ID not between", value1, value2, "n_CustomerTypeId");
      return this;
    }

    public Criteria andLoginAccountIsNull() {
      addCriterion("LOGIN_ACCOUNT is null");
      return this;
    }

    public Criteria andLoginAccountIsNotNull() {
      addCriterion("LOGIN_ACCOUNT is not null");
      return this;
    }

    public Criteria andLoginAccountEqualTo(String value) {
      addCriterion("LOGIN_ACCOUNT =", value, "loginAccount");
      return this;
    }

    public Criteria andLoginAccountNotEqualTo(String value) {
      addCriterion("LOGIN_ACCOUNT <>", value, "loginAccount");
      return this;
    }

    public Criteria andLoginAccountGreaterThan(String value) {
      addCriterion("LOGIN_ACCOUNT >", value, "loginAccount");
      return this;
    }

    public Criteria andLoginAccountGreaterThanOrEqualTo(String value) {
      addCriterion("LOGIN_ACCOUNT >=", value, "loginAccount");
      return this;
    }

    public Criteria andLoginAccountLessThan(String value) {
      addCriterion("LOGIN_ACCOUNT <", value, "loginAccount");
      return this;
    }

    public Criteria andLoginAccountLessThanOrEqualTo(String value) {
      addCriterion("LOGIN_ACCOUNT <=", value, "loginAccount");
      return this;
    }

    public Criteria andLoginAccountLike(String value) {
      addCriterion("LOGIN_ACCOUNT like", value, "loginAccount");
      return this;
    }

    public Criteria andLoginAccountNotLike(String value) {
      addCriterion("LOGIN_ACCOUNT not like", value, "loginAccount");
      return this;
    }

    public Criteria andLoginAccountIn(List values) {
      addCriterion("LOGIN_ACCOUNT in", values, "loginAccount");
      return this;
    }

    public Criteria andLoginAccountNotIn(List values) {
      addCriterion("LOGIN_ACCOUNT not in", values, "loginAccount");
      return this;
    }

    public Criteria andLoginAccountBetween(String value1, String value2) {
      addCriterion("LOGIN_ACCOUNT between", value1, value2, "loginAccount");
      return this;
    }

    public Criteria andLoginAccountNotBetween(String value1, String value2) {
      addCriterion("LOGIN_ACCOUNT not between", value1, value2, "loginAccount");
      return this;
    }

    public Criteria andLoginPasswordIsNull() {
      addCriterion("LOGIN_PASSWORD is null");
      return this;
    }

    public Criteria andLoginPasswordIsNotNull() {
      addCriterion("LOGIN_PASSWORD is not null");
      return this;
    }

    public Criteria andLoginPasswordEqualTo(String value) {
      addCriterion("LOGIN_PASSWORD =", value, "loginPassword");
      return this;
    }

    public Criteria andLoginPasswordNotEqualTo(String value) {
      addCriterion("LOGIN_PASSWORD <>", value, "loginPassword");
      return this;
    }

    public Criteria andLoginPasswordGreaterThan(String value) {
      addCriterion("LOGIN_PASSWORD >", value, "loginPassword");
      return this;
    }

    public Criteria andLoginPasswordGreaterThanOrEqualTo(String value) {
      addCriterion("LOGIN_PASSWORD >=", value, "loginPassword");
      return this;
    }

    public Criteria andLoginPasswordLessThan(String value) {
      addCriterion("LOGIN_PASSWORD <", value, "loginPassword");
      return this;
    }

    public Criteria andLoginPasswordLessThanOrEqualTo(String value) {
      addCriterion("LOGIN_PASSWORD <=", value, "loginPassword");
      return this;
    }

    public Criteria andLoginPasswordLike(String value) {
      addCriterion("LOGIN_PASSWORD like", value, "loginPassword");
      return this;
    }

    public Criteria andLoginPasswordNotLike(String value) {
      addCriterion("LOGIN_PASSWORD not like", value, "loginPassword");
      return this;
    }

    public Criteria andLoginPasswordIn(List values) {
      addCriterion("LOGIN_PASSWORD in", values, "loginPassword");
      return this;
    }

    public Criteria andLoginPasswordNotIn(List values) {
      addCriterion("LOGIN_PASSWORD not in", values, "loginPassword");
      return this;
    }

    public Criteria andLoginPasswordBetween(String value1, String value2) {
      addCriterion("LOGIN_PASSWORD between", value1, value2, "loginPassword");
      return this;
    }

    public Criteria andLoginPasswordNotBetween(String value1, String value2) {
      addCriterion("LOGIN_PASSWORD not between", value1, value2, "loginPassword");
      return this;
    }

    public Criteria andMobileIsNull() {
      addCriterion("MOBILE is null");
      return this;
    }

    public Criteria andMobileIsNotNull() {
      addCriterion("MOBILE is not null");
      return this;
    }

    public Criteria andMobileEqualTo(String value) {
      addCriterion("MOBILE =", value, "mobile");
      return this;
    }

    public Criteria andMobileNotEqualTo(String value) {
      addCriterion("MOBILE <>", value, "mobile");
      return this;
    }

    public Criteria andMobileGreaterThan(String value) {
      addCriterion("MOBILE >", value, "mobile");
      return this;
    }

    public Criteria andMobileGreaterThanOrEqualTo(String value) {
      addCriterion("MOBILE >=", value, "mobile");
      return this;
    }

    public Criteria andMobileLessThan(String value) {
      addCriterion("MOBILE <", value, "mobile");
      return this;
    }

    public Criteria andMobileLessThanOrEqualTo(String value) {
      addCriterion("MOBILE <=", value, "mobile");
      return this;
    }

    public Criteria andMobileLike(String value) {
      addCriterion("MOBILE like", value, "mobile");
      return this;
    }

    public Criteria andMobileNotLike(String value) {
      addCriterion("MOBILE not like", value, "mobile");
      return this;
    }

    public Criteria andMobileIn(List values) {
      addCriterion("MOBILE in", values, "mobile");
      return this;
    }

    public Criteria andMobileNotIn(List values) {
      addCriterion("MOBILE not in", values, "mobile");
      return this;
    }

    public Criteria andMobileBetween(String value1, String value2) {
      addCriterion("MOBILE between", value1, value2, "mobile");
      return this;
    }

    public Criteria andMobileNotBetween(String value1, String value2) {
      addCriterion("MOBILE not between", value1, value2, "mobile");
      return this;
    }

    public Criteria andEmailIsNull() {
      addCriterion("EMAIL is null");
      return this;
    }

    public Criteria andEmailIsNotNull() {
      addCriterion("EMAIL is not null");
      return this;
    }

    public Criteria andEmailEqualTo(String value) {
      addCriterion("EMAIL =", value, "email");
      return this;
    }

    public Criteria andEmailNotEqualTo(String value) {
      addCriterion("EMAIL <>", value, "email");
      return this;
    }

    public Criteria andEmailGreaterThan(String value) {
      addCriterion("EMAIL >", value, "email");
      return this;
    }

    public Criteria andEmailGreaterThanOrEqualTo(String value) {
      addCriterion("EMAIL >=", value, "email");
      return this;
    }

    public Criteria andEmailLessThan(String value) {
      addCriterion("EMAIL <", value, "email");
      return this;
    }

    public Criteria andEmailLessThanOrEqualTo(String value) {
      addCriterion("EMAIL <=", value, "email");
      return this;
    }

    public Criteria andEmailLike(String value) {
      addCriterion("EMAIL like", value, "email");
      return this;
    }

    public Criteria andEmailNotLike(String value) {
      addCriterion("EMAIL not like", value, "email");
      return this;
    }

    public Criteria andEmailIn(List values) {
      addCriterion("EMAIL in", values, "email");
      return this;
    }

    public Criteria andEmailNotIn(List values) {
      addCriterion("EMAIL not in", values, "email");
      return this;
    }

    public Criteria andEmailBetween(String value1, String value2) {
      addCriterion("EMAIL between", value1, value2, "email");
      return this;
    }

    public Criteria andEmailNotBetween(String value1, String value2) {
      addCriterion("EMAIL not between", value1, value2, "email");
      return this;
    }

    public Criteria andn_StatusIsNull() {
      addCriterion("N_STATUS is null");
      return this;
    }

    public Criteria andn_StatusIsNotNull() {
      addCriterion("N_STATUS is not null");
      return this;
    }

    public Criteria andn_StatusEqualTo(Short value) {
      addCriterion("N_STATUS =", value, "n_Status");
      return this;
    }

    public Criteria andn_StatusNotEqualTo(Short value) {
      addCriterion("N_STATUS <>", value, "n_Status");
      return this;
    }

    public Criteria andn_StatusGreaterThan(Short value) {
      addCriterion("N_STATUS >", value, "n_Status");
      return this;
    }

    public Criteria andn_StatusGreaterThanOrEqualTo(Short value) {
      addCriterion("N_STATUS >=", value, "n_Status");
      return this;
    }

    public Criteria andn_StatusLessThan(Short value) {
      addCriterion("N_STATUS <", value, "n_Status");
      return this;
    }

    public Criteria andn_StatusLessThanOrEqualTo(Short value) {
      addCriterion("N_STATUS <=", value, "n_Status");
      return this;
    }

    public Criteria andn_StatusIn(List values) {
      addCriterion("N_STATUS in", values, "n_Status");
      return this;
    }

    public Criteria andn_StatusNotIn(List values) {
      addCriterion("N_STATUS not in", values, "n_Status");
      return this;
    }

    public Criteria andn_StatusBetween(Short value1, Short value2) {
      addCriterion("N_STATUS between", value1, value2, "n_Status");
      return this;
    }

    public Criteria andn_StatusNotBetween(Short value1, Short value2) {
      addCriterion("N_STATUS not between", value1, value2, "n_Status");
      return this;
    }

    public Criteria andd_CreateDateIsNull() {
      addCriterion("D_CREATE_DATE is null");
      return this;
    }

    public Criteria andd_CreateDateIsNotNull() {
      addCriterion("D_CREATE_DATE is not null");
      return this;
    }

    public Criteria andd_CreateDateEqualTo(Date value) {
      addCriterionForJDBCDate("D_CREATE_DATE =", value, "d_CreateDate");
      return this;
    }

    public Criteria andd_CreateDateNotEqualTo(Date value) {
      addCriterionForJDBCDate("D_CREATE_DATE <>", value, "d_CreateDate");
      return this;
    }

    public Criteria andd_CreateDateGreaterThan(Date value) {
      addCriterionForJDBCDate("D_CREATE_DATE >", value, "d_CreateDate");
      return this;
    }

    public Criteria andd_CreateDateGreaterThanOrEqualTo(Date value) {
      addCriterionForJDBCDate("D_CREATE_DATE >=", value, "d_CreateDate");
      return this;
    }

    public Criteria andd_CreateDateLessThan(Date value) {
      addCriterionForJDBCDate("D_CREATE_DATE <", value, "d_CreateDate");
      return this;
    }

    public Criteria andd_CreateDateLessThanOrEqualTo(Date value) {
      addCriterionForJDBCDate("D_CREATE_DATE <=", value, "d_CreateDate");
      return this;
    }

    public Criteria andd_CreateDateIn(List values) {
      addCriterionForJDBCDate("D_CREATE_DATE in", values, "d_CreateDate");
      return this;
    }

    public Criteria andd_CreateDateNotIn(List values) {
      addCriterionForJDBCDate("D_CREATE_DATE not in", values, "d_CreateDate");
      return this;
    }

    public Criteria andd_CreateDateBetween(Date value1, Date value2) {
      addCriterionForJDBCDate("D_CREATE_DATE between", value1, value2, "d_CreateDate");
      return this;
    }

    public Criteria andd_CreateDateNotBetween(Date value1, Date value2) {
      addCriterionForJDBCDate("D_CREATE_DATE not between", value1, value2, "d_CreateDate");
      return this;
    }

    public Criteria andn_CreatedIsNull() {
      addCriterion("N_CREATED is null");
      return this;
    }

    public Criteria andn_CreatedIsNotNull() {
      addCriterion("N_CREATED is not null");
      return this;
    }

    public Criteria andn_CreatedEqualTo(Integer value) {
      addCriterion("N_CREATED =", value, "n_Created");
      return this;
    }

    public Criteria andn_CreatedNotEqualTo(Integer value) {
      addCriterion("N_CREATED <>", value, "n_Created");
      return this;
    }

    public Criteria andn_CreatedGreaterThan(Integer value) {
      addCriterion("N_CREATED >", value, "n_Created");
      return this;
    }

    public Criteria andn_CreatedGreaterThanOrEqualTo(Integer value) {
      addCriterion("N_CREATED >=", value, "n_Created");
      return this;
    }

    public Criteria andn_CreatedLessThan(Integer value) {
      addCriterion("N_CREATED <", value, "n_Created");
      return this;
    }

    public Criteria andn_CreatedLessThanOrEqualTo(Integer value) {
      addCriterion("N_CREATED <=", value, "n_Created");
      return this;
    }

    public Criteria andn_CreatedIn(List values) {
      addCriterion("N_CREATED in", values, "n_Created");
      return this;
    }

    public Criteria andn_CreatedNotIn(List values) {
      addCriterion("N_CREATED not in", values, "n_Created");
      return this;
    }

    public Criteria andn_CreatedBetween(Integer value1, Integer value2) {
      addCriterion("N_CREATED between", value1, value2, "n_Created");
      return this;
    }

    public Criteria andn_CreatedNotBetween(Integer value1, Integer value2) {
      addCriterion("N_CREATED not between", value1, value2, "n_Created");
      return this;
    }

    public Criteria andd_ModifyDateIsNull() {
      addCriterion("D_MODIFY_DATE is null");
      return this;
    }

    public Criteria andd_ModifyDateIsNotNull() {
      addCriterion("D_MODIFY_DATE is not null");
      return this;
    }

    public Criteria andd_ModifyDateEqualTo(Date value) {
      addCriterionForJDBCDate("D_MODIFY_DATE =", value, "d_ModifyDate");
      return this;
    }

    public Criteria andd_ModifyDateNotEqualTo(Date value) {
      addCriterionForJDBCDate("D_MODIFY_DATE <>", value, "d_ModifyDate");
      return this;
    }

    public Criteria andd_ModifyDateGreaterThan(Date value) {
      addCriterionForJDBCDate("D_MODIFY_DATE >", value, "d_ModifyDate");
      return this;
    }

    public Criteria andd_ModifyDateGreaterThanOrEqualTo(Date value) {
      addCriterionForJDBCDate("D_MODIFY_DATE >=", value, "d_ModifyDate");
      return this;
    }

    public Criteria andd_ModifyDateLessThan(Date value) {
      addCriterionForJDBCDate("D_MODIFY_DATE <", value, "d_ModifyDate");
      return this;
    }

    public Criteria andd_ModifyDateLessThanOrEqualTo(Date value) {
      addCriterionForJDBCDate("D_MODIFY_DATE <=", value, "d_ModifyDate");
      return this;
    }

    public Criteria andd_ModifyDateIn(List values) {
      addCriterionForJDBCDate("D_MODIFY_DATE in", values, "d_ModifyDate");
      return this;
    }

    public Criteria andd_ModifyDateNotIn(List values) {
      addCriterionForJDBCDate("D_MODIFY_DATE not in", values, "d_ModifyDate");
      return this;
    }

    public Criteria andd_ModifyDateBetween(Date value1, Date value2) {
      addCriterionForJDBCDate("D_MODIFY_DATE between", value1, value2, "d_ModifyDate");
      return this;
    }

    public Criteria andd_ModifyDateNotBetween(Date value1, Date value2) {
      addCriterionForJDBCDate("D_MODIFY_DATE not between", value1, value2, "d_ModifyDate");
      return this;
    }

    public Criteria andn_ModifiedIsNull() {
      addCriterion("N_MODIFIED is null");
      return this;
    }

    public Criteria andn_ModifiedIsNotNull() {
      addCriterion("N_MODIFIED is not null");
      return this;
    }

    public Criteria andn_ModifiedEqualTo(Integer value) {
      addCriterion("N_MODIFIED =", value, "n_Modified");
      return this;
    }

    public Criteria andn_ModifiedNotEqualTo(Integer value) {
      addCriterion("N_MODIFIED <>", value, "n_Modified");
      return this;
    }

    public Criteria andn_ModifiedGreaterThan(Integer value) {
      addCriterion("N_MODIFIED >", value, "n_Modified");
      return this;
    }

    public Criteria andn_ModifiedGreaterThanOrEqualTo(Integer value) {
      addCriterion("N_MODIFIED >=", value, "n_Modified");
      return this;
    }

    public Criteria andn_ModifiedLessThan(Integer value) {
      addCriterion("N_MODIFIED <", value, "n_Modified");
      return this;
    }

    public Criteria andn_ModifiedLessThanOrEqualTo(Integer value) {
      addCriterion("N_MODIFIED <=", value, "n_Modified");
      return this;
    }

    public Criteria andn_ModifiedIn(List values) {
      addCriterion("N_MODIFIED in", values, "n_Modified");
      return this;
    }

    public Criteria andn_ModifiedNotIn(List values) {
      addCriterion("N_MODIFIED not in", values, "n_Modified");
      return this;
    }

    public Criteria andn_ModifiedBetween(Integer value1, Integer value2) {
      addCriterion("N_MODIFIED between", value1, value2, "n_Modified");
      return this;
    }

    public Criteria andn_ModifiedNotBetween(Integer value1, Integer value2) {
      addCriterion("N_MODIFIED not between", value1, value2, "n_Modified");
      return this;
    }
  }
}
