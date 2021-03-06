package com.pltfm.app.dataobject;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 数据对象
 * 
 * @since 2013-07-17
 */
public class BnesAcctTransactionDO implements Serializable {

  private static final long serialVersionUID = 137404448348854275L;



  /**
   * This field was generated by Abator for iBATIS. This field corresponds to the database table
   * ACCOUNT_INFO
   *
   * @abatorgenerated Wed Jul 10 15:14:23 CST 2013
   */
  protected String orderByClause;

  /**
   * This field was generated by Abator for iBATIS. This field corresponds to the database table
   * ACCOUNT_INFO
   *
   * @abatorgenerated Wed Jul 10 15:14:23 CST 2013
   */
  protected List oredCriteria;

  /**
   * This method was generated by Abator for iBATIS. This method corresponds to the database table
   * ACCOUNT_INFO
   *
   * @abatorgenerated Wed Jul 10 15:14:23 CST 2013
   */
  public BnesAcctTransactionDO() {
    oredCriteria = new ArrayList();
  }

  /**
   * This method was generated by Abator for iBATIS. This method corresponds to the database table
   * ACCOUNT_INFO
   *
   * @abatorgenerated Wed Jul 10 15:14:23 CST 2013
   */
  protected BnesAcctTransactionDO(BnesAcctTransactionDO example) {
    this.orderByClause = example.orderByClause;
    this.oredCriteria = example.oredCriteria;
  }

  /**
   * This method was generated by Abator for iBATIS. This method corresponds to the database table
   * ACCOUNT_INFO
   *
   * @abatorgenerated Wed Jul 10 15:14:23 CST 2013
   */
  public void setOrderByClause(String orderByClause) {
    this.orderByClause = orderByClause;
  }

  /**
   * This method was generated by Abator for iBATIS. This method corresponds to the database table
   * ACCOUNT_INFO
   *
   * @abatorgenerated Wed Jul 10 15:14:23 CST 2013
   */
  public String getOrderByClause() {
    return orderByClause;
  }

  /**
   * This method was generated by Abator for iBATIS. This method corresponds to the database table
   * ACCOUNT_INFO
   *
   * @abatorgenerated Wed Jul 10 15:14:23 CST 2013
   */
  public List getOredCriteria() {
    return oredCriteria;
  }

  /**
   * This method was generated by Abator for iBATIS. This method corresponds to the database table
   * ACCOUNT_INFO
   *
   * @abatorgenerated Wed Jul 10 15:14:23 CST 2013
   */
  public void or(Criteria criteria) {
    oredCriteria.add(criteria);
  }

  /**
   * This method was generated by Abator for iBATIS. This method corresponds to the database table
   * ACCOUNT_INFO
   *
   * @abatorgenerated Wed Jul 10 15:14:23 CST 2013
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
   * ACCOUNT_INFO
   *
   * @abatorgenerated Wed Jul 10 15:14:23 CST 2013
   */
  protected Criteria createCriteriaInternal() {
    Criteria criteria = new Criteria();
    return criteria;
  }

  /**
   * This method was generated by Abator for iBATIS. This method corresponds to the database table
   * ACCOUNT_INFO
   *
   * @abatorgenerated Wed Jul 10 15:14:23 CST 2013
   */
  public void clear() {
    oredCriteria.clear();
  }

  /**
   * This class was generated by Abator for iBATIS. This class corresponds to the database table
   * ACCOUNT_INFO
   *
   * @abatorgenerated Wed Jul 10 15:14:23 CST 2013
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

    // -------------------------------------------------------------------------ACCOUNT_TRANSACTION_ID
    public Criteria andAccountTransactionIdIsNull() {
      addCriterion("ACCOUNT_TRANSACTION_ID is null");
      return this;
    }


    public Criteria andAccountTransactionIdIsNotNull() {
      addCriterion("ACCOUNT_TRANSACTION_ID is not null");
      return this;
    }

    public Criteria andAccountTransactionIdEqualTo(BigDecimal value) {
      addCriterion("ACCOUNT_TRANSACTION_ID =", value, "accountTransactionId");
      return this;
    }

    public Criteria andAccountTransactionIdNotEqualTo(BigDecimal value) {
      addCriterion("ACCOUNT_TRANSACTION_ID <>", value, "accountTransactionId");
      return this;
    }

    public Criteria andAccountTransactionIdGreaterThan(BigDecimal value) {
      addCriterion("ACCOUNT_TRANSACTION_ID >", value, "accountTransactionId");
      return this;
    }

    public Criteria andAccountTransactionIdGreaterThanOrEqualTo(BigDecimal value) {
      addCriterion("ACCOUNT_TRANSACTION_ID >=", value, "accountTransactionId");
      return this;
    }

    public Criteria andAccountTransactionIdLessThan(BigDecimal value) {
      addCriterion("ACCOUNT_TRANSACTION_ID <", value, "accountTransactionId");
      return this;
    }

    public Criteria andAccountTransactionIdLessThanOrEqualTo(BigDecimal value) {
      addCriterion("ACCOUNT_TRANSACTION_ID <=", value, "accountTransactionId");
      return this;
    }

    public Criteria andAccountTransactionIdIn(List values) {
      addCriterion("ACCOUNT_TRANSACTION_ID in", values, "accountTransactionId");
      return this;
    }

    public Criteria andAccountTransactionIdNotIn(List values) {
      addCriterion("ACCOUNT_TRANSACTION_ID not in", values, "accountTransactionId");
      return this;
    }

    public Criteria andAccountTransactionIdBetween(BigDecimal value1, BigDecimal value2) {
      addCriterion("ACCOUNT_TRANSACTION_ID between", value1, value2, "accountTransactionId");
      return this;
    }

    public Criteria andAccountTransactionIdNotBetween(BigDecimal value1, BigDecimal value2) {
      addCriterion("ACCOUNT_TRANSACTION_ID not between", value1, value2, "accountTransactionId");
      return this;
    }



    // -----------------------------------------------------------------------------------------


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

    // -----------------------
    public Criteria andAccountNumberIsNull() {
      addCriterion("ACCOUNT_NUMBER is null");
      return this;
    }

    public Criteria andAccountNumberIsNotNull() {
      addCriterion("ACCOUNT_NUMBER is not null");
      return this;
    }

    public Criteria andAccountNumberEqualTo(BigDecimal value) {
      addCriterion("ACCOUNT_NUMBER =", value, "accountNumber");
      return this;
    }

    public Criteria andAccountNumberNotEqualTo(BigDecimal value) {
      addCriterion("ACCOUNT_NUMBER <>", value, "accountNumber");
      return this;
    }

    public Criteria andAccountNumberGreaterThan(BigDecimal value) {
      addCriterion("ACCOUNT_NUMBER >", value, "accountNumber");
      return this;
    }

    public Criteria andAccountNumberGreaterThanOrEqualTo(BigDecimal value) {
      addCriterion("ACCOUNT_NUMBER >=", value, "accountNumber");
      return this;
    }

    public Criteria andAccountNumberLessThan(BigDecimal value) {
      addCriterion("ACCOUNT_NUMBER <", value, "accountNumber");
      return this;
    }

    public Criteria andAccountNumberLessThanOrEqualTo(BigDecimal value) {
      addCriterion("ACCOUNT_NUMBER <=", value, "accountNumber");
      return this;
    }

    public Criteria andAccountNumberIn(List values) {
      addCriterion("ACCOUNT_NUMBER in", values, "accountNumber");
      return this;
    }

    public Criteria andAccountNumberNotIn(List values) {
      addCriterion("ACCOUNT_NUMBER not in", values, "accountNumber");
      return this;
    }

    public Criteria andAccountNumberBetween(BigDecimal value1, BigDecimal value2) {
      addCriterion("ACCOUNT_NUMBER between", value1, value2, "accountNumber");
      return this;
    }

    public Criteria andAccountNumberNotBetween(BigDecimal value1, BigDecimal value2) {
      addCriterion("ACCOUNT_NUMBER not between", value1, value2, "accountNumber");
      return this;
    }

    // ---------------------
    public Criteria andTypeIsNull() {
      addCriterion("TYPE is null");
      return this;
    }

    public Criteria andtypeIsNotNull() {
      addCriterion("TYPE is not null");
      return this;
    }

    public Criteria andTypeEqualTo(BigDecimal value) {
      addCriterion("TYPE =", value, "type");
      return this;
    }

    public Criteria andTypeNotEqualTo(BigDecimal value) {
      addCriterion("TYPE <>", value, "type");
      return this;
    }

    public Criteria andTypeGreaterThan(BigDecimal value) {
      addCriterion("TYPE >", value, "type");
      return this;
    }

    public Criteria andTypeGreaterThanOrEqualTo(BigDecimal value) {
      addCriterion("TYPE >=", value, "type");
      return this;
    }

    public Criteria andTypeLessThan(BigDecimal value) {
      addCriterion("TYPE <", value, "type");
      return this;
    }

    public Criteria andTypeLessThanOrEqualTo(BigDecimal value) {
      addCriterion("TYPE <=", value, "type");
      return this;
    }

    public Criteria andTypeIn(List values) {
      addCriterion("TYPE in", values, "type");
      return this;
    }

    public Criteria andTypeNotIn(List values) {
      addCriterion("TYPE not in", values, "type");
      return this;
    }

    public Criteria andTypeBetween(BigDecimal value1, BigDecimal value2) {
      addCriterion("TYPE between", value1, value2, "type");
      return this;
    }

    public Criteria andTypeNotBetween(BigDecimal value1, BigDecimal value2) {
      addCriterion("TYPE not between", value1, value2, "type");
      return this;
    }

    // ----------------
    public Criteria andContentIsNull() {
      addCriterion("CONTENT is null");
      return this;
    }

    public Criteria andContentIsNotNull() {
      addCriterion("CONTENT is not null");
      return this;
    }

    public Criteria andContentEqualTo(BigDecimal value) {
      addCriterion("CONTENT =", value, "content");
      return this;
    }

    public Criteria andContentNotEqualTo(BigDecimal value) {
      addCriterion("CONTENT <>", value, "content");
      return this;
    }

    public Criteria andContentGreaterThan(BigDecimal value) {
      addCriterion("CONTENT >", value, "content");
      return this;
    }

    public Criteria andContentGreaterThanOrEqualTo(BigDecimal value) {
      addCriterion("CONTENT >=", value, "content");
      return this;
    }

    public Criteria andContentLessThan(BigDecimal value) {
      addCriterion("CONTENT <", value, "content");
      return this;
    }

    public Criteria andContentLessThanOrEqualTo(BigDecimal value) {
      addCriterion("CONTENT <=", value, "content");
      return this;
    }

    public Criteria andContentIn(List values) {
      addCriterion("CONTENT in", values, "content");
      return this;
    }

    public Criteria andContentNotIn(List values) {
      addCriterion("CONTENT not in", values, "content");
      return this;
    }

    public Criteria andContentBetween(BigDecimal value1, BigDecimal value2) {
      addCriterion("CONTENT between", value1, value2, "content");
      return this;
    }

    public Criteria andContentNotBetween(BigDecimal value1, BigDecimal value2) {
      addCriterion("CONTENT not between", value1, value2, "content");
      return this;
    }

    // ---
    public Criteria andAmountIsNull() {
      addCriterion("AMOUNT is null");
      return this;
    }

    public Criteria andAmountIsNotNull() {
      addCriterion("AMOUNT is not null");
      return this;
    }

    public Criteria andAmountEqualTo(BigDecimal value) {
      addCriterion("AMOUNT =", value, "amount");
      return this;
    }

    public Criteria andAmountNotEqualTo(BigDecimal value) {
      addCriterion("AMOUNT <>", value, "amount");
      return this;
    }

    public Criteria andAmountGreaterThan(BigDecimal value) {
      addCriterion("AMOUNT >", value, "amount");
      return this;
    }

    public Criteria andAmountGreaterThanOrEqualTo(BigDecimal value) {
      addCriterion("AMOUNT >=", value, "amount");
      return this;
    }

    public Criteria andAmountLessThan(BigDecimal value) {
      addCriterion("AMOUNT <", value, "amount");
      return this;
    }

    public Criteria andAmountLessThanOrEqualTo(BigDecimal value) {
      addCriterion("AMOUNT <=", value, "amount");
      return this;
    }

    public Criteria andAmountIn(List values) {
      addCriterion("AMOUNT in", values, "amount");
      return this;
    }

    public Criteria andAmountNotIn(List values) {
      addCriterion("AMOUNT not in", values, "amount");
      return this;
    }

    public Criteria andAmountBetween(BigDecimal value1, BigDecimal value2) {
      addCriterion("AMOUNT between", value1, value2, "amount");
      return this;
    }

    public Criteria andamountNotBetween(BigDecimal value1, BigDecimal value2) {
      addCriterion("AMOUNT not between", value1, value2, "amount");
      return this;
    }

    // -------
    public Criteria andStatusIsNull() {
      addCriterion("STATUS is null");
      return this;
    }

    public Criteria andStatusIsNotNull() {
      addCriterion("STATUS is not null");
      return this;
    }

    public Criteria andStatusEqualTo(BigDecimal value) {
      addCriterion("STATUS =", value, "status");
      return this;
    }

    public Criteria andStatusNotEqualTo(BigDecimal value) {
      addCriterion("STATUS <>", value, "status");
      return this;
    }

    public Criteria andStatusGreaterThan(BigDecimal value) {
      addCriterion("STATUS >", value, "status");
      return this;
    }

    public Criteria andStatusGreaterThanOrEqualTo(BigDecimal value) {
      addCriterion("STATUS >=", value, "status");
      return this;
    }

    public Criteria andStatusLessThan(BigDecimal value) {
      addCriterion("STATUS <", value, "status");
      return this;
    }

    public Criteria andStatusLessThanOrEqualTo(BigDecimal value) {
      addCriterion("STATUS <=", value, "status");
      return this;
    }

    public Criteria andStatusIn(List values) {
      addCriterion("STATUS in", values, "status");
      return this;
    }

    public Criteria andStatusNotIn(List values) {
      addCriterion("STATUS not in", values, "status");
      return this;
    }

    public Criteria andStatusBetween(BigDecimal value1, BigDecimal value2) {
      addCriterion("STATUS between", value1, value2, "status");
      return this;
    }

    public Criteria andStatusNotBetween(BigDecimal value1, BigDecimal value2) {
      addCriterion("STATUS not between", value1, value2, "status");
      return this;
    }

    // --------
    public Criteria andCreateDateIsNull() {
      addCriterion("CREATE_DATE is null");
      return this;
    }

    public Criteria andCreateDateIsNotNull() {
      addCriterion("CREATE_DATE is not null");
      return this;
    }

    public Criteria andCreateDateEqualTo(BigDecimal value) {
      addCriterion("CREATE_DATE =", value, "createDate");
      return this;
    }

    public Criteria andCreateDateNotEqualTo(BigDecimal value) {
      addCriterion("CREATE_DATE <>", value, "createDate");
      return this;
    }

    public Criteria andCreateDateGreaterThan(BigDecimal value) {
      addCriterion("CREATE_DATE >", value, "createDate");
      return this;
    }

    public Criteria andCreateDateGreaterThanOrEqualTo(BigDecimal value) {
      addCriterion("CREATE_DATE >=", value, "createDate");
      return this;
    }

    public Criteria andCreateDateLessThan(BigDecimal value) {
      addCriterion("CREATE_DATE <", value, "createDate");
      return this;
    }

    public Criteria andCreateDateLessThanOrEqualTo(BigDecimal value) {
      addCriterion("CREATE_DATE <=", value, "createDate");
      return this;
    }

    public Criteria andCreateDateIn(List values) {
      addCriterion("CREATE_DATE in", values, "createDate");
      return this;
    }

    public Criteria andCreateDateNotIn(List values) {
      addCriterion("CREATE_DATE not in", values, "createDate");
      return this;
    }

    public Criteria andCreateDateBetween(BigDecimal value1, BigDecimal value2) {
      addCriterion("CREATE_DATE between", value1, value2, "createDate");
      return this;
    }

    public Criteria andCreateDateNotBetween(BigDecimal value1, BigDecimal value2) {
      addCriterion("CREATE_DATE not between", value1, value2, "createDate");
      return this;
    }

    // -----------------------------------------

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

    // -----------------
    public Criteria andModifyDateIsNull() {
      addCriterion("MODIFY_DATE is null");
      return this;
    }

    public Criteria andModifyDateIsNotNull() {
      addCriterion("MODIFY_DATE is not null");
      return this;
    }

    public Criteria andModifyDateEqualTo(BigDecimal value) {
      addCriterion("MODIFY_DATE =", value, "modifyDate");
      return this;
    }

    public Criteria andModifyDateNotEqualTo(BigDecimal value) {
      addCriterion("MODIFY_DATE <>", value, "modifyDate");
      return this;
    }

    public Criteria andModifyDateGreaterThan(BigDecimal value) {
      addCriterion("MODIFY_DATE >", value, "modifyDate");
      return this;
    }

    public Criteria andModifyDateGreaterThanOrEqualTo(BigDecimal value) {
      addCriterion("MODIFY_DATE >=", value, "modifyDate");
      return this;
    }

    public Criteria andModifyDateLessThan(BigDecimal value) {
      addCriterion("MODIFY_DATE <", value, "modifyDate");
      return this;
    }

    public Criteria andModifyDateLessThanOrEqualTo(BigDecimal value) {
      addCriterion("MODIFY_DATE <=", value, "modifyDate");
      return this;
    }

    public Criteria andModifyDateIn(List values) {
      addCriterion("MODIFY_DATE in", values, "modifyDate");
      return this;
    }

    public Criteria andModifyDateNotIn(List values) {
      addCriterion("MODIFY_DATE not in", values, "modifyDate");
      return this;
    }

    public Criteria andModifyDateBetween(BigDecimal value1, BigDecimal value2) {
      addCriterion("MODIFY_DATE between", value1, value2, "modifyDate");
      return this;
    }

    public Criteria andModifyDateNotBetween(BigDecimal value1, BigDecimal value2) {
      addCriterion("MODIFY_DATE not between", value1, value2, "modifyDate");
      return this;
    }

    // --------------
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
