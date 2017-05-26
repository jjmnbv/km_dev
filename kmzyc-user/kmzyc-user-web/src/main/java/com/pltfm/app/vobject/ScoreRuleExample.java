package com.pltfm.app.vobject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 客户积分规则查询实体
 * 
 * @author zhl
 * @since 2013-07-10
 */
public class ScoreRuleExample {
  /**
   * 排序条件
   */
  protected String orderByClause;

  /**
   * 查询条件集合
   */
  protected List oredCriteria;

  public ScoreRuleExample() {
    oredCriteria = new ArrayList();
  }

  protected ScoreRuleExample(ScoreRuleExample example) {
    this.orderByClause = example.orderByClause;
    this.oredCriteria = example.oredCriteria;
  }

  public void setOrderByClause(String orderByClause) {
    this.orderByClause = orderByClause;
  }

  public String getOrderByClause() {
    return orderByClause;
  }

  public List getOredCriteria() {
    return oredCriteria;
  }

  public void or(Criteria criteria) {
    oredCriteria.add(criteria);
  }

  public Criteria createCriteria() {
    Criteria criteria = createCriteriaInternal();
    if (oredCriteria.size() == 0) {
      oredCriteria.add(criteria);
    }
    return criteria;
  }

  protected Criteria createCriteriaInternal() {
    Criteria criteria = new Criteria();
    return criteria;
  }

  public void clear() {
    oredCriteria.clear();
  }

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

    public Criteria andNScoreRuleIdIsNull() {
      addCriterion("N_SCORE_RULE_ID is null");
      return this;
    }

    public Criteria andNScoreRuleIdIsNotNull() {
      addCriterion("N_SCORE_RULE_ID is not null");
      return this;
    }

    public Criteria andNScoreRuleIdEqualTo(BigDecimal value) {
      addCriterion("N_SCORE_RULE_ID =", value, "n_scoreRuleId");
      return this;
    }

    public Criteria andn_scoreRuleIdNotEqualTo(BigDecimal value) {
      addCriterion("N_SCORE_RULE_ID <>", value, "n_scoreRuleId");
      return this;
    }

    public Criteria andn_scoreRuleIdGreaterThan(BigDecimal value) {
      addCriterion("N_SCORE_RULE_ID >", value, "n_scoreRuleId");
      return this;
    }

    public Criteria andn_scoreRuleIdGreaterThanOrEqualTo(BigDecimal value) {
      addCriterion("N_SCORE_RULE_ID >=", value, "n_scoreRuleId");
      return this;
    }

    public Criteria andn_scoreRuleIdLessThan(BigDecimal value) {
      addCriterion("N_SCORE_RULE_ID <", value, "n_scoreRuleId");
      return this;
    }

    public Criteria andn_scoreRuleIdLessThanOrEqualTo(BigDecimal value) {
      addCriterion("N_SCORE_RULE_ID <=", value, "n_scoreRuleId");
      return this;
    }

    @SuppressWarnings("unchecked")
    public Criteria andn_scoreRuleIdIn(List values) {
      addCriterion("N_SCORE_RULE_ID in", values, "n_scoreRuleId");
      return this;
    }

    @SuppressWarnings("unchecked")
    public Criteria andn_scoreRuleIdNotIn(List values) {
      addCriterion("N_SCORE_RULE_ID not in", values, "n_scoreRuleId");
      return this;
    }

    public Criteria andn_scoreRuleIdBetween(BigDecimal value1, BigDecimal value2) {
      addCriterion("N_SCORE_RULE_ID between", value1, value2, "n_scoreRuleId");
      return this;
    }

    public Criteria andn_scoreRuleIdNotBetween(BigDecimal value1, BigDecimal value2) {
      addCriterion("N_SCORE_RULE_ID not between", value1, value2, "n_scoreRuleId");
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

    public Criteria andCodeIsNull() {
      addCriterion("CODE is null");
      return this;
    }

    public Criteria andCodeIsNotNull() {
      addCriterion("CODE is not null");
      return this;
    }

    public Criteria andCodeEqualTo(String value) {
      addCriterion("CODE =", value, "code");
      return this;
    }

    public Criteria andCodeNotEqualTo(String value) {
      addCriterion("CODE <>", value, "code");
      return this;
    }

    public Criteria andCodeGreaterThan(String value) {
      addCriterion("CODE >", value, "code");
      return this;
    }

    public Criteria andCodeGreaterThanOrEqualTo(String value) {
      addCriterion("CODE >=", value, "code");
      return this;
    }

    public Criteria andCodeLessThan(String value) {
      addCriterion("CODE <", value, "code");
      return this;
    }

    public Criteria andCodeLessThanOrEqualTo(String value) {
      addCriterion("CODE <=", value, "code");
      return this;
    }

    public Criteria andCodeLike(String value) {
      addCriterion("CODE like", value, "code");
      return this;
    }

    public Criteria andCodeNotLike(String value) {
      addCriterion("CODE not like", value, "code");
      return this;
    }

    public Criteria andCodeIn(List values) {
      addCriterion("CODE in", values, "code");
      return this;
    }

    public Criteria andCodeNotIn(List values) {
      addCriterion("CODE not in", values, "code");
      return this;
    }

    public Criteria andCodeBetween(String value1, String value2) {
      addCriterion("CODE between", value1, value2, "code");
      return this;
    }

    public Criteria andCodeNotBetween(String value1, String value2) {
      addCriterion("CODE not between", value1, value2, "code");
      return this;
    }

    public Criteria andDiscribeIsNull() {
      addCriterion("DISCRIBE is null");
      return this;
    }

    public Criteria andDiscribeIsNotNull() {
      addCriterion("DISCRIBE is not null");
      return this;
    }

    public Criteria andDiscribeEqualTo(String value) {
      addCriterion("DISCRIBE =", value, "discribe");
      return this;
    }

    public Criteria andDiscribeNotEqualTo(String value) {
      addCriterion("DISCRIBE <>", value, "discribe");
      return this;
    }

    public Criteria andDiscribeGreaterThan(String value) {
      addCriterion("DISCRIBE >", value, "discribe");
      return this;
    }

    public Criteria andDiscribeGreaterThanOrEqualTo(String value) {
      addCriterion("DISCRIBE >=", value, "discribe");
      return this;
    }

    public Criteria andDiscribeLessThan(String value) {
      addCriterion("DISCRIBE <", value, "discribe");
      return this;
    }

    public Criteria andDiscribeLessThanOrEqualTo(String value) {
      addCriterion("DISCRIBE <=", value, "discribe");
      return this;
    }

    public Criteria andDiscribeLike(String value) {
      addCriterion("DISCRIBE like", value, "discribe");
      return this;
    }

    public Criteria andDiscribeNotLike(String value) {
      addCriterion("DISCRIBE not like", value, "discribe");
      return this;
    }

    public Criteria andDiscribeIn(List values) {
      addCriterion("DISCRIBE in", values, "discribe");
      return this;
    }

    public Criteria andDiscribeNotIn(List values) {
      addCriterion("DISCRIBE not in", values, "discribe");
      return this;
    }

    public Criteria andDiscribeBetween(String value1, String value2) {
      addCriterion("DISCRIBE between", value1, value2, "discribe");
      return this;
    }

    public Criteria andDiscribeNotBetween(String value1, String value2) {
      addCriterion("DISCRIBE not between", value1, value2, "discribe");
      return this;
    }

    public Criteria andClientTypeIsNull() {
      addCriterion("CLIENT_TYPE is null");
      return this;
    }

    public Criteria andClientTypeIsNotNull() {
      addCriterion("CLIENT_TYPE is not null");
      return this;
    }

    public Criteria andClientTypeEqualTo(String value) {
      addCriterion("CLIENT_TYPE =", value, "clientType");
      return this;
    }

    public Criteria andClientTypeNotEqualTo(String value) {
      addCriterion("CLIENT_TYPE <>", value, "clientType");
      return this;
    }

    public Criteria andClientTypeGreaterThan(String value) {
      addCriterion("CLIENT_TYPE >", value, "clientType");
      return this;
    }

    public Criteria andClientTypeGreaterThanOrEqualTo(String value) {
      addCriterion("CLIENT_TYPE >=", value, "clientType");
      return this;
    }

    public Criteria andClientTypeLessThan(String value) {
      addCriterion("CLIENT_TYPE <", value, "clientType");
      return this;
    }

    public Criteria andClientTypeLessThanOrEqualTo(String value) {
      addCriterion("CLIENT_TYPE <=", value, "clientType");
      return this;
    }

    public Criteria andClientTypeLike(String value) {
      addCriterion("CLIENT_TYPE like", value, "clientType");
      return this;
    }

    public Criteria andClientTypeNotLike(String value) {
      addCriterion("CLIENT_TYPE not like", value, "clientType");
      return this;
    }

    public Criteria andClientTypeIn(List values) {
      addCriterion("CLIENT_TYPE in", values, "clientType");
      return this;
    }

    public Criteria andClientTypeNotIn(List values) {
      addCriterion("CLIENT_TYPE not in", values, "clientType");
      return this;
    }

    public Criteria andClientTypeBetween(String value1, String value2) {
      addCriterion("CLIENT_TYPE between", value1, value2, "clientType");
      return this;
    }

    public Criteria andClientTypeNotBetween(String value1, String value2) {
      addCriterion("CLIENT_TYPE not between", value1, value2, "clientType");
      return this;
    }

    public Criteria andScoreScaleIsNull() {
      addCriterion("SCORE_SCALE is null");
      return this;
    }

    public Criteria andScoreScaleIsNotNull() {
      addCriterion("SCORE_SCALE is not null");
      return this;
    }

    public Criteria andScoreScaleEqualTo(BigDecimal value) {
      addCriterion("SCORE_SCALE =", value, "scoreScale");
      return this;
    }

    public Criteria andScoreScaleNotEqualTo(BigDecimal value) {
      addCriterion("SCORE_SCALE <>", value, "scoreScale");
      return this;
    }

    public Criteria andScoreScaleGreaterThan(BigDecimal value) {
      addCriterion("SCORE_SCALE >", value, "scoreScale");
      return this;
    }

    public Criteria andScoreScaleGreaterThanOrEqualTo(BigDecimal value) {
      addCriterion("SCORE_SCALE >=", value, "scoreScale");
      return this;
    }

    public Criteria andScoreScaleLessThan(BigDecimal value) {
      addCriterion("SCORE_SCALE <", value, "scoreScale");
      return this;
    }

    public Criteria andScoreScaleLessThanOrEqualTo(BigDecimal value) {
      addCriterion("SCORE_SCALE <=", value, "scoreScale");
      return this;
    }

    public Criteria andScoreScaleIn(List values) {
      addCriterion("SCORE_SCALE in", values, "scoreScale");
      return this;
    }

    public Criteria andScoreScaleNotIn(List values) {
      addCriterion("SCORE_SCALE not in", values, "scoreScale");
      return this;
    }

    public Criteria andScoreScaleBetween(BigDecimal value1, BigDecimal value2) {
      addCriterion("SCORE_SCALE between", value1, value2, "scoreScale");
      return this;
    }

    public Criteria andScoreScaleNotBetween(BigDecimal value1, BigDecimal value2) {
      addCriterion("SCORE_SCALE not between", value1, value2, "scoreScale");
      return this;
    }

    public Criteria andScoreNumberIsNull() {
      addCriterion("SCORE_NUMBER is null");
      return this;
    }

    public Criteria andScoreNumberIsNotNull() {
      addCriterion("SCORE_NUMBER is not null");
      return this;
    }

    public Criteria andScoreNumberEqualTo(BigDecimal value) {
      addCriterion("SCORE_NUMBER =", value, "scoreNumber");
      return this;
    }

    public Criteria andScoreNumberNotEqualTo(BigDecimal value) {
      addCriterion("SCORE_NUMBER <>", value, "scoreNumber");
      return this;
    }

    public Criteria andScoreNumberGreaterThan(BigDecimal value) {
      addCriterion("SCORE_NUMBER >", value, "scoreNumber");
      return this;
    }

    public Criteria andScoreNumberGreaterThanOrEqualTo(BigDecimal value) {
      addCriterion("SCORE_NUMBER >=", value, "scoreNumber");
      return this;
    }

    public Criteria andScoreNumberLessThan(BigDecimal value) {
      addCriterion("SCORE_NUMBER <", value, "scoreNumber");
      return this;
    }

    public Criteria andScoreNumberLessThanOrEqualTo(BigDecimal value) {
      addCriterion("SCORE_NUMBER <=", value, "scoreNumber");
      return this;
    }

    public Criteria andScoreNumberIn(List values) {
      addCriterion("SCORE_NUMBER in", values, "scoreNumber");
      return this;
    }

    public Criteria andScoreNumberNotIn(List values) {
      addCriterion("SCORE_NUMBER not in", values, "scoreNumber");
      return this;
    }

    public Criteria andScoreNumberBetween(BigDecimal value1, BigDecimal value2) {
      addCriterion("SCORE_NUMBER between", value1, value2, "scoreNumber");
      return this;
    }

    public Criteria andScoreNumberNotBetween(BigDecimal value1, BigDecimal value2) {
      addCriterion("SCORE_NUMBER not between", value1, value2, "scoreNumber");
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
