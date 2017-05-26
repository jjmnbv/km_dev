package com.pltfm.app.vobject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class BnesMessageTempExample {
  /**
   * This field was generated by Abator for iBATIS. This field corresponds to the database table
   * BNES_MESSAGE_TEMP
   *
   * @abatorgenerated Tue Aug 13 16:55:57 CST 2013
   */
  protected String orderByClause;

  /**
   * This field was generated by Abator for iBATIS. This field corresponds to the database table
   * BNES_MESSAGE_TEMP
   *
   * @abatorgenerated Tue Aug 13 16:55:57 CST 2013
   */
  protected List oredCriteria;

  /**
   * This method was generated by Abator for iBATIS. This method corresponds to the database table
   * BNES_MESSAGE_TEMP
   *
   * @abatorgenerated Tue Aug 13 16:55:57 CST 2013
   */
  public BnesMessageTempExample() {
    oredCriteria = new ArrayList();
  }

  /**
   * This method was generated by Abator for iBATIS. This method corresponds to the database table
   * BNES_MESSAGE_TEMP
   *
   * @abatorgenerated Tue Aug 13 16:55:57 CST 2013
   */
  protected BnesMessageTempExample(BnesMessageTempExample example) {
    this.orderByClause = example.orderByClause;
    this.oredCriteria = example.oredCriteria;
  }

  /**
   * This method was generated by Abator for iBATIS. This method corresponds to the database table
   * BNES_MESSAGE_TEMP
   *
   * @abatorgenerated Tue Aug 13 16:55:57 CST 2013
   */
  public void setOrderByClause(String orderByClause) {
    this.orderByClause = orderByClause;
  }

  /**
   * This method was generated by Abator for iBATIS. This method corresponds to the database table
   * BNES_MESSAGE_TEMP
   *
   * @abatorgenerated Tue Aug 13 16:55:57 CST 2013
   */
  public String getOrderByClause() {
    return orderByClause;
  }

  /**
   * This method was generated by Abator for iBATIS. This method corresponds to the database table
   * BNES_MESSAGE_TEMP
   *
   * @abatorgenerated Tue Aug 13 16:55:57 CST 2013
   */
  public List getOredCriteria() {
    return oredCriteria;
  }

  /**
   * This method was generated by Abator for iBATIS. This method corresponds to the database table
   * BNES_MESSAGE_TEMP
   *
   * @abatorgenerated Tue Aug 13 16:55:57 CST 2013
   */
  public void or(Criteria criteria) {
    oredCriteria.add(criteria);
  }

  /**
   * This method was generated by Abator for iBATIS. This method corresponds to the database table
   * BNES_MESSAGE_TEMP
   *
   * @abatorgenerated Tue Aug 13 16:55:57 CST 2013
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
   * BNES_MESSAGE_TEMP
   *
   * @abatorgenerated Tue Aug 13 16:55:57 CST 2013
   */
  protected Criteria createCriteriaInternal() {
    Criteria criteria = new Criteria();
    return criteria;
  }

  /**
   * This method was generated by Abator for iBATIS. This method corresponds to the database table
   * BNES_MESSAGE_TEMP
   *
   * @abatorgenerated Tue Aug 13 16:55:57 CST 2013
   */
  public void clear() {
    oredCriteria.clear();
  }

  /**
   * This class was generated by Abator for iBATIS. This class corresponds to the database table
   * BNES_MESSAGE_TEMP
   *
   * @abatorgenerated Tue Aug 13 16:55:57 CST 2013
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

    public Criteria andMessageTempIdIsNull() {
      addCriterion("MESSAGE_TEMP_ID is null");
      return this;
    }

    public Criteria andMessageTempIdIsNotNull() {
      addCriterion("MESSAGE_TEMP_ID is not null");
      return this;
    }

    public Criteria andMessageTempIdEqualTo(BigDecimal value) {
      addCriterion("MESSAGE_TEMP_ID =", value, "messageTempId");
      return this;
    }

    public Criteria andMessageTempIdNotEqualTo(Integer value) {
      addCriterion("MESSAGE_TEMP_ID <>", value, "messageTempId");
      return this;
    }

    public Criteria andMessageTempIdGreaterThan(Integer value) {
      addCriterion("MESSAGE_TEMP_ID >", value, "messageTempId");
      return this;
    }

    public Criteria andMessageTempIdGreaterThanOrEqualTo(Integer value) {
      addCriterion("MESSAGE_TEMP_ID >=", value, "messageTempId");
      return this;
    }

    public Criteria andMessageTempIdLessThan(Integer value) {
      addCriterion("MESSAGE_TEMP_ID <", value, "messageTempId");
      return this;
    }

    public Criteria andMessageTempIdLessThanOrEqualTo(Integer value) {
      addCriterion("MESSAGE_TEMP_ID <=", value, "messageTempId");
      return this;
    }

    public Criteria andMessageTempIdIn(List values) {
      addCriterion("MESSAGE_TEMP_ID in", values, "messageTempId");
      return this;
    }

    public Criteria andMessageTempIdNotIn(List values) {
      addCriterion("MESSAGE_TEMP_ID not in", values, "messageTempId");
      return this;
    }

    public Criteria andMessageTempIdBetween(Integer value1, Integer value2) {
      addCriterion("MESSAGE_TEMP_ID between", value1, value2, "messageTempId");
      return this;
    }

    public Criteria andMessageTempIdNotBetween(Integer value1, Integer value2) {
      addCriterion("MESSAGE_TEMP_ID not between", value1, value2, "messageTempId");
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

    public Criteria andAccountIdEqualTo(Integer value) {
      addCriterion("ACCOUNT_ID =", value, "accountId");
      return this;
    }

    public Criteria andAccountIdNotEqualTo(Integer value) {
      addCriterion("ACCOUNT_ID <>", value, "accountId");
      return this;
    }

    public Criteria andAccountIdGreaterThan(Integer value) {
      addCriterion("ACCOUNT_ID >", value, "accountId");
      return this;
    }

    public Criteria andAccountIdGreaterThanOrEqualTo(Integer value) {
      addCriterion("ACCOUNT_ID >=", value, "accountId");
      return this;
    }

    public Criteria andAccountIdLessThan(Integer value) {
      addCriterion("ACCOUNT_ID <", value, "accountId");
      return this;
    }

    public Criteria andAccountIdLessThanOrEqualTo(Integer value) {
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

    public Criteria andAccountIdBetween(Integer value1, Integer value2) {
      addCriterion("ACCOUNT_ID between", value1, value2, "accountId");
      return this;
    }

    public Criteria andAccountIdNotBetween(Integer value1, Integer value2) {
      addCriterion("ACCOUNT_ID not between", value1, value2, "accountId");
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

    public Criteria andCreatedIdEqualTo(Integer value) {
      addCriterion("CREATED_ID =", value, "createdId");
      return this;
    }

    public Criteria andCreatedIdNotEqualTo(Integer value) {
      addCriterion("CREATED_ID <>", value, "createdId");
      return this;
    }

    public Criteria andCreatedIdGreaterThan(Integer value) {
      addCriterion("CREATED_ID >", value, "createdId");
      return this;
    }

    public Criteria andCreatedIdGreaterThanOrEqualTo(Integer value) {
      addCriterion("CREATED_ID >=", value, "createdId");
      return this;
    }

    public Criteria andCreatedIdLessThan(Integer value) {
      addCriterion("CREATED_ID <", value, "createdId");
      return this;
    }

    public Criteria andCreatedIdLessThanOrEqualTo(Integer value) {
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

    public Criteria andCreatedIdBetween(Integer value1, Integer value2) {
      addCriterion("CREATED_ID between", value1, value2, "createdId");
      return this;
    }

    public Criteria andCreatedIdNotBetween(Integer value1, Integer value2) {
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

    public Criteria andModifieIdEqualTo(Integer value) {
      addCriterion("MODIFIE_ID =", value, "modifieId");
      return this;
    }

    public Criteria andModifieIdNotEqualTo(Integer value) {
      addCriterion("MODIFIE_ID <>", value, "modifieId");
      return this;
    }

    public Criteria andModifieIdGreaterThan(Integer value) {
      addCriterion("MODIFIE_ID >", value, "modifieId");
      return this;
    }

    public Criteria andModifieIdGreaterThanOrEqualTo(Integer value) {
      addCriterion("MODIFIE_ID >=", value, "modifieId");
      return this;
    }

    public Criteria andModifieIdLessThan(Integer value) {
      addCriterion("MODIFIE_ID <", value, "modifieId");
      return this;
    }

    public Criteria andModifieIdLessThanOrEqualTo(Integer value) {
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

    public Criteria andModifieIdBetween(Integer value1, Integer value2) {
      addCriterion("MODIFIE_ID between", value1, value2, "modifieId");
      return this;
    }

    public Criteria andModifieIdNotBetween(Integer value1, Integer value2) {
      addCriterion("MODIFIE_ID not between", value1, value2, "modifieId");
      return this;
    }

    public Criteria andInfoPromptIdIsNull() {
      addCriterion("INFO_PROMPT_ID is null");
      return this;
    }

    public Criteria andInfoPromptIdIsNotNull() {
      addCriterion("INFO_PROMPT_ID is not null");
      return this;
    }

    public Criteria andInfoPromptIdEqualTo(Integer value) {
      addCriterion("INFO_PROMPT_ID =", value, "infoPromptId");
      return this;
    }

    public Criteria andInfoPromptIdNotEqualTo(Integer value) {
      addCriterion("INFO_PROMPT_ID <>", value, "infoPromptId");
      return this;
    }

    public Criteria andInfoPromptIdGreaterThan(Integer value) {
      addCriterion("INFO_PROMPT_ID >", value, "infoPromptId");
      return this;
    }

    public Criteria andInfoPromptIdGreaterThanOrEqualTo(Integer value) {
      addCriterion("INFO_PROMPT_ID >=", value, "infoPromptId");
      return this;
    }

    public Criteria andInfoPromptIdLessThan(Integer value) {
      addCriterion("INFO_PROMPT_ID <", value, "infoPromptId");
      return this;
    }

    public Criteria andInfoPromptIdLessThanOrEqualTo(Integer value) {
      addCriterion("INFO_PROMPT_ID <=", value, "infoPromptId");
      return this;
    }

    public Criteria andInfoPromptIdIn(List values) {
      addCriterion("INFO_PROMPT_ID in", values, "infoPromptId");
      return this;
    }

    public Criteria andInfoPromptIdNotIn(List values) {
      addCriterion("INFO_PROMPT_ID not in", values, "infoPromptId");
      return this;
    }

    public Criteria andInfoPromptIdBetween(Integer value1, Integer value2) {
      addCriterion("INFO_PROMPT_ID between", value1, value2, "infoPromptId");
      return this;
    }

    public Criteria andInfoPromptIdNotBetween(Integer value1, Integer value2) {
      addCriterion("INFO_PROMPT_ID not between", value1, value2, "infoPromptId");
      return this;
    }
  }
}