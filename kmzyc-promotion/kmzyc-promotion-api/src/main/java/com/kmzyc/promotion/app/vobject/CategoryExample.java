package com.kmzyc.promotion.app.vobject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unchecked")
public class CategoryExample {
  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database table
   * CATEGORY
   * 
   * @ibatorgenerated Tue Sep 03 11:24:10 CST 2013
   */
  protected String orderByClause;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database table
   * CATEGORY
   * 
   * @ibatorgenerated Tue Sep 03 11:24:10 CST 2013
   */
  protected List oredCriteria;

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table CATEGORY
   * 
   * @ibatorgenerated Tue Sep 03 11:24:10 CST 2013
   */
  public CategoryExample() {
    oredCriteria = new ArrayList();
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table CATEGORY
   * 
   * @ibatorgenerated Tue Sep 03 11:24:10 CST 2013
   */
  protected CategoryExample(CategoryExample example) {
    this.orderByClause = example.orderByClause;
    this.oredCriteria = example.oredCriteria;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table CATEGORY
   * 
   * @ibatorgenerated Tue Sep 03 11:24:10 CST 2013
   */
  public void setOrderByClause(String orderByClause) {
    this.orderByClause = orderByClause;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table CATEGORY
   * 
   * @ibatorgenerated Tue Sep 03 11:24:10 CST 2013
   */
  public String getOrderByClause() {
    return orderByClause;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table CATEGORY
   * 
   * @ibatorgenerated Tue Sep 03 11:24:10 CST 2013
   */
  public List getOredCriteria() {
    return oredCriteria;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table CATEGORY
   * 
   * @ibatorgenerated Tue Sep 03 11:24:10 CST 2013
   */
  public void or(Criteria criteria) {
    oredCriteria.add(criteria);
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table CATEGORY
   * 
   * @ibatorgenerated Tue Sep 03 11:24:10 CST 2013
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
   * table CATEGORY
   * 
   * @ibatorgenerated Tue Sep 03 11:24:10 CST 2013
   */
  protected Criteria createCriteriaInternal() {
    Criteria criteria = new Criteria();
    return criteria;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table CATEGORY
   * 
   * @ibatorgenerated Tue Sep 03 11:24:10 CST 2013
   */
  public void clear() {
    oredCriteria.clear();
  }

  /**
   * This class was generated by Apache iBATIS ibator. This class corresponds to the database table
   * CATEGORY
   * 
   * @ibatorgenerated Tue Sep 03 11:24:10 CST 2013
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

    public Criteria andCategoryIdIsNull() {
      addCriterion("CATEGORY_ID is null");
      return this;
    }

    public Criteria andCategoryIdIsNotNull() {
      addCriterion("CATEGORY_ID is not null");
      return this;
    }

    public Criteria andCategoryIdEqualTo(Long value) {
      addCriterion("CATEGORY_ID =", value, "categoryId");
      return this;
    }

    public Criteria andCategoryIdNotEqualTo(Long value) {
      addCriterion("CATEGORY_ID <>", value, "categoryId");
      return this;
    }

    public Criteria andCategoryIdGreaterThan(Long value) {
      addCriterion("CATEGORY_ID >", value, "categoryId");
      return this;
    }

    public Criteria andCategoryIdGreaterThanOrEqualTo(Long value) {
      addCriterion("CATEGORY_ID >=", value, "categoryId");
      return this;
    }

    public Criteria andCategoryIdLessThan(Long value) {
      addCriterion("CATEGORY_ID <", value, "categoryId");
      return this;
    }

    public Criteria andCategoryIdLessThanOrEqualTo(Long value) {
      addCriterion("CATEGORY_ID <=", value, "categoryId");
      return this;
    }

    public Criteria andCategoryIdIn(List values) {
      addCriterion("CATEGORY_ID in", values, "categoryId");
      return this;
    }

    public Criteria andCategoryIdNotIn(List values) {
      addCriterion("CATEGORY_ID not in", values, "categoryId");
      return this;
    }

    public Criteria andCategoryIdBetween(Long value1, Long value2) {
      addCriterion("CATEGORY_ID between", value1, value2, "categoryId");
      return this;
    }

    public Criteria andCategoryIdNotBetween(Long value1, Long value2) {
      addCriterion("CATEGORY_ID not between", value1, value2, "categoryId");
      return this;
    }

    public Criteria andParentIdIsNull() {
      addCriterion("PARENT_ID is null");
      return this;
    }

    public Criteria andParentIdIsNotNull() {
      addCriterion("PARENT_ID is not null");
      return this;
    }

    public Criteria andParentIdEqualTo(Long value) {
      addCriterion("PARENT_ID =", value, "parentId");
      return this;
    }

    public Criteria andParentIdNotEqualTo(Long value) {
      addCriterion("PARENT_ID <>", value, "parentId");
      return this;
    }

    public Criteria andParentIdGreaterThan(Long value) {
      addCriterion("PARENT_ID >", value, "parentId");
      return this;
    }

    public Criteria andParentIdGreaterThanOrEqualTo(Long value) {
      addCriterion("PARENT_ID >=", value, "parentId");
      return this;
    }

    public Criteria andParentIdLessThan(Long value) {
      addCriterion("PARENT_ID <", value, "parentId");
      return this;
    }

    public Criteria andParentIdLessThanOrEqualTo(Long value) {
      addCriterion("PARENT_ID <=", value, "parentId");
      return this;
    }

    public Criteria andParentIdIn(List values) {
      addCriterion("PARENT_ID in", values, "parentId");
      return this;
    }

    public Criteria andParentIdNotIn(List values) {
      addCriterion("PARENT_ID not in", values, "parentId");
      return this;
    }

    public Criteria andParentIdBetween(Long value1, Long value2) {
      addCriterion("PARENT_ID between", value1, value2, "parentId");
      return this;
    }

    public Criteria andParentIdNotBetween(Long value1, Long value2) {
      addCriterion("PARENT_ID not between", value1, value2, "parentId");
      return this;
    }

    public Criteria andCategoryCodeIsNull() {
      addCriterion("CATEGORY_CODE is null");
      return this;
    }

    public Criteria andCategoryCodeIsNotNull() {
      addCriterion("CATEGORY_CODE is not null");
      return this;
    }

    public Criteria andCategoryCodeEqualTo(String value) {
      addCriterion("CATEGORY_CODE =", value, "categoryCode");
      return this;
    }

    public Criteria andCategoryCodeNotEqualTo(String value) {
      addCriterion("CATEGORY_CODE <>", value, "categoryCode");
      return this;
    }

    public Criteria andCategoryCodeGreaterThan(String value) {
      addCriterion("CATEGORY_CODE >", value, "categoryCode");
      return this;
    }

    public Criteria andCategoryCodeGreaterThanOrEqualTo(String value) {
      addCriterion("CATEGORY_CODE >=", value, "categoryCode");
      return this;
    }

    public Criteria andCategoryCodeLessThan(String value) {
      addCriterion("CATEGORY_CODE <", value, "categoryCode");
      return this;
    }

    public Criteria andCategoryCodeLessThanOrEqualTo(String value) {
      addCriterion("CATEGORY_CODE <=", value, "categoryCode");
      return this;
    }

    public Criteria andCategoryCodeLike(String value) {
      addCriterion("CATEGORY_CODE like", value, "categoryCode");
      return this;
    }

    public Criteria andCategoryCodeNotLike(String value) {
      addCriterion("CATEGORY_CODE not like", value, "categoryCode");
      return this;
    }

    public Criteria andCategoryCodeIn(List values) {
      addCriterion("CATEGORY_CODE in", values, "categoryCode");
      return this;
    }

    public Criteria andCategoryCodeNotIn(List values) {
      addCriterion("CATEGORY_CODE not in", values, "categoryCode");
      return this;
    }

    public Criteria andCategoryCodeBetween(String value1, String value2) {
      addCriterion("CATEGORY_CODE between", value1, value2, "categoryCode");
      return this;
    }

    public Criteria andCategoryCodeNotBetween(String value1, String value2) {
      addCriterion("CATEGORY_CODE not between", value1, value2, "categoryCode");
      return this;
    }

    public Criteria andCategoryNameIsNull() {
      addCriterion("CATEGORY_NAME is null");
      return this;
    }

    public Criteria andCategoryNameIsNotNull() {
      addCriterion("CATEGORY_NAME is not null");
      return this;
    }

    public Criteria andCategoryNameEqualTo(String value) {
      addCriterion("CATEGORY_NAME =", value, "categoryName");
      return this;
    }

    public Criteria andCategoryNameNotEqualTo(String value) {
      addCriterion("CATEGORY_NAME <>", value, "categoryName");
      return this;
    }

    public Criteria andCategoryNameGreaterThan(String value) {
      addCriterion("CATEGORY_NAME >", value, "categoryName");
      return this;
    }

    public Criteria andCategoryNameGreaterThanOrEqualTo(String value) {
      addCriterion("CATEGORY_NAME >=", value, "categoryName");
      return this;
    }

    public Criteria andCategoryNameLessThan(String value) {
      addCriterion("CATEGORY_NAME <", value, "categoryName");
      return this;
    }

    public Criteria andCategoryNameLessThanOrEqualTo(String value) {
      addCriterion("CATEGORY_NAME <=", value, "categoryName");
      return this;
    }

    public Criteria andCategoryNameLike(String value) {
      addCriterion("CATEGORY_NAME like", value, "categoryName");
      return this;
    }

    public Criteria andCategoryNameNotLike(String value) {
      addCriterion("CATEGORY_NAME not like", value, "categoryName");
      return this;
    }

    public Criteria andCategoryNameIn(List values) {
      addCriterion("CATEGORY_NAME in", values, "categoryName");
      return this;
    }

    public Criteria andCategoryNameNotIn(List values) {
      addCriterion("CATEGORY_NAME not in", values, "categoryName");
      return this;
    }

    public Criteria andCategoryNameBetween(String value1, String value2) {
      addCriterion("CATEGORY_NAME between", value1, value2, "categoryName");
      return this;
    }

    public Criteria andCategoryNameNotBetween(String value1, String value2) {
      addCriterion("CATEGORY_NAME not between", value1, value2, "categoryName");
      return this;
    }

    public Criteria andStatusIsNull() {
      addCriterion("STATUS is null");
      return this;
    }

    public Criteria andStatusIsNotNull() {
      addCriterion("STATUS is not null");
      return this;
    }

    public Criteria andStatusEqualTo(Integer value) {
      addCriterion("STATUS =", value, "status");
      return this;
    }

    public Criteria andStatusNotEqualTo(Integer value) {
      addCriterion("STATUS <>", value, "status");
      return this;
    }

    public Criteria andStatusGreaterThan(Integer value) {
      addCriterion("STATUS >", value, "status");
      return this;
    }

    public Criteria andStatusGreaterThanOrEqualTo(Integer value) {
      addCriterion("STATUS >=", value, "status");
      return this;
    }

    public Criteria andStatusLessThan(Integer value) {
      addCriterion("STATUS <", value, "status");
      return this;
    }

    public Criteria andStatusLessThanOrEqualTo(Integer value) {
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

    public Criteria andStatusBetween(Integer value1, Integer value2) {
      addCriterion("STATUS between", value1, value2, "status");
      return this;
    }

    public Criteria andStatusNotBetween(Integer value1, Integer value2) {
      addCriterion("STATUS not between", value1, value2, "status");
      return this;
    }

    public Criteria andSortnoIsNull() {
      addCriterion("SORTNO is null");
      return this;
    }

    public Criteria andSortnoIsNotNull() {
      addCriterion("SORTNO is not null");
      return this;
    }

    public Criteria andSortnoEqualTo(Integer value) {
      addCriterion("SORTNO =", value, "sortno");
      return this;
    }

    public Criteria andSortnoNotEqualTo(Integer value) {
      addCriterion("SORTNO <>", value, "sortno");
      return this;
    }

    public Criteria andSortnoGreaterThan(Integer value) {
      addCriterion("SORTNO >", value, "sortno");
      return this;
    }

    public Criteria andSortnoGreaterThanOrEqualTo(Integer value) {
      addCriterion("SORTNO >=", value, "sortno");
      return this;
    }

    public Criteria andSortnoLessThan(Integer value) {
      addCriterion("SORTNO <", value, "sortno");
      return this;
    }

    public Criteria andSortnoLessThanOrEqualTo(Integer value) {
      addCriterion("SORTNO <=", value, "sortno");
      return this;
    }

    public Criteria andSortnoIn(List values) {
      addCriterion("SORTNO in", values, "sortno");
      return this;
    }

    public Criteria andSortnoNotIn(List values) {
      addCriterion("SORTNO not in", values, "sortno");
      return this;
    }

    public Criteria andSortnoBetween(Integer value1, Integer value2) {
      addCriterion("SORTNO between", value1, value2, "sortno");
      return this;
    }

    public Criteria andSortnoNotBetween(Integer value1, Integer value2) {
      addCriterion("SORTNO not between", value1, value2, "sortno");
      return this;
    }

    public Criteria andCategoryDescIsNull() {
      addCriterion("CATEGORY_DESC is null");
      return this;
    }

    public Criteria andCategoryDescIsNotNull() {
      addCriterion("CATEGORY_DESC is not null");
      return this;
    }

    public Criteria andCategoryDescEqualTo(String value) {
      addCriterion("CATEGORY_DESC =", value, "categoryDesc");
      return this;
    }

    public Criteria andCategoryDescNotEqualTo(String value) {
      addCriterion("CATEGORY_DESC <>", value, "categoryDesc");
      return this;
    }

    public Criteria andCategoryDescGreaterThan(String value) {
      addCriterion("CATEGORY_DESC >", value, "categoryDesc");
      return this;
    }

    public Criteria andCategoryDescGreaterThanOrEqualTo(String value) {
      addCriterion("CATEGORY_DESC >=", value, "categoryDesc");
      return this;
    }

    public Criteria andCategoryDescLessThan(String value) {
      addCriterion("CATEGORY_DESC <", value, "categoryDesc");
      return this;
    }

    public Criteria andCategoryDescLessThanOrEqualTo(String value) {
      addCriterion("CATEGORY_DESC <=", value, "categoryDesc");
      return this;
    }

    public Criteria andCategoryDescLike(String value) {
      addCriterion("CATEGORY_DESC like", value, "categoryDesc");
      return this;
    }

    public Criteria andCategoryDescNotLike(String value) {
      addCriterion("CATEGORY_DESC not like", value, "categoryDesc");
      return this;
    }

    public Criteria andCategoryDescIn(List values) {
      addCriterion("CATEGORY_DESC in", values, "categoryDesc");
      return this;
    }

    public Criteria andCategoryDescNotIn(List values) {
      addCriterion("CATEGORY_DESC not in", values, "categoryDesc");
      return this;
    }

    public Criteria andCategoryDescBetween(String value1, String value2) {
      addCriterion("CATEGORY_DESC between", value1, value2, "categoryDesc");
      return this;
    }

    public Criteria andCategoryDescNotBetween(String value1, String value2) {
      addCriterion("CATEGORY_DESC not between", value1, value2, "categoryDesc");
      return this;
    }

    public Criteria andCreateTimeIsNull() {
      addCriterion("CREATE_TIME is null");
      return this;
    }

    public Criteria andCreateTimeIsNotNull() {
      addCriterion("CREATE_TIME is not null");
      return this;
    }

    public Criteria andCreateTimeEqualTo(Date value) {
      addCriterionForJDBCDate("CREATE_TIME =", value, "createTime");
      return this;
    }

    public Criteria andCreateTimeNotEqualTo(Date value) {
      addCriterionForJDBCDate("CREATE_TIME <>", value, "createTime");
      return this;
    }

    public Criteria andCreateTimeGreaterThan(Date value) {
      addCriterionForJDBCDate("CREATE_TIME >", value, "createTime");
      return this;
    }

    public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
      addCriterionForJDBCDate("CREATE_TIME >=", value, "createTime");
      return this;
    }

    public Criteria andCreateTimeLessThan(Date value) {
      addCriterionForJDBCDate("CREATE_TIME <", value, "createTime");
      return this;
    }

    public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
      addCriterionForJDBCDate("CREATE_TIME <=", value, "createTime");
      return this;
    }

    public Criteria andCreateTimeIn(List values) {
      addCriterionForJDBCDate("CREATE_TIME in", values, "createTime");
      return this;
    }

    public Criteria andCreateTimeNotIn(List values) {
      addCriterionForJDBCDate("CREATE_TIME not in", values, "createTime");
      return this;
    }

    public Criteria andCreateTimeBetween(Date value1, Date value2) {
      addCriterionForJDBCDate("CREATE_TIME between", value1, value2, "createTime");
      return this;
    }

    public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
      addCriterionForJDBCDate("CREATE_TIME not between", value1, value2, "createTime");
      return this;
    }

    public Criteria andModifTimeIsNull() {
      addCriterion("MODIF_TIME is null");
      return this;
    }

    public Criteria andModifTimeIsNotNull() {
      addCriterion("MODIF_TIME is not null");
      return this;
    }

    public Criteria andModifTimeEqualTo(Date value) {
      addCriterionForJDBCDate("MODIF_TIME =", value, "modifTime");
      return this;
    }

    public Criteria andModifTimeNotEqualTo(Date value) {
      addCriterionForJDBCDate("MODIF_TIME <>", value, "modifTime");
      return this;
    }

    public Criteria andModifTimeGreaterThan(Date value) {
      addCriterionForJDBCDate("MODIF_TIME >", value, "modifTime");
      return this;
    }

    public Criteria andModifTimeGreaterThanOrEqualTo(Date value) {
      addCriterionForJDBCDate("MODIF_TIME >=", value, "modifTime");
      return this;
    }

    public Criteria andModifTimeLessThan(Date value) {
      addCriterionForJDBCDate("MODIF_TIME <", value, "modifTime");
      return this;
    }

    public Criteria andModifTimeLessThanOrEqualTo(Date value) {
      addCriterionForJDBCDate("MODIF_TIME <=", value, "modifTime");
      return this;
    }

    public Criteria andModifTimeIn(List values) {
      addCriterionForJDBCDate("MODIF_TIME in", values, "modifTime");
      return this;
    }

    public Criteria andModifTimeNotIn(List values) {
      addCriterionForJDBCDate("MODIF_TIME not in", values, "modifTime");
      return this;
    }

    public Criteria andModifTimeBetween(Date value1, Date value2) {
      addCriterionForJDBCDate("MODIF_TIME between", value1, value2, "modifTime");
      return this;
    }

    public Criteria andModifTimeNotBetween(Date value1, Date value2) {
      addCriterionForJDBCDate("MODIF_TIME not between", value1, value2, "modifTime");
      return this;
    }

    public Criteria andModifUserIsNull() {
      addCriterion("MODIF_USER is null");
      return this;
    }

    public Criteria andModifUserIsNotNull() {
      addCriterion("MODIF_USER is not null");
      return this;
    }

    public Criteria andModifUserEqualTo(Long value) {
      addCriterion("MODIF_USER =", value, "modifUser");
      return this;
    }

    public Criteria andModifUserNotEqualTo(Long value) {
      addCriterion("MODIF_USER <>", value, "modifUser");
      return this;
    }

    public Criteria andModifUserGreaterThan(Long value) {
      addCriterion("MODIF_USER >", value, "modifUser");
      return this;
    }

    public Criteria andModifUserGreaterThanOrEqualTo(Long value) {
      addCriterion("MODIF_USER >=", value, "modifUser");
      return this;
    }

    public Criteria andModifUserLessThan(Long value) {
      addCriterion("MODIF_USER <", value, "modifUser");
      return this;
    }

    public Criteria andModifUserLessThanOrEqualTo(Long value) {
      addCriterion("MODIF_USER <=", value, "modifUser");
      return this;
    }

    public Criteria andModifUserIn(List values) {
      addCriterion("MODIF_USER in", values, "modifUser");
      return this;
    }

    public Criteria andModifUserNotIn(List values) {
      addCriterion("MODIF_USER not in", values, "modifUser");
      return this;
    }

    public Criteria andModifUserBetween(Long value1, Long value2) {
      addCriterion("MODIF_USER between", value1, value2, "modifUser");
      return this;
    }

    public Criteria andModifUserNotBetween(Long value1, Long value2) {
      addCriterion("MODIF_USER not between", value1, value2, "modifUser");
      return this;
    }

    public Criteria andExecSqlIsNull() {
      addCriterion("EXEC_SQL is null");
      return this;
    }

    public Criteria andExecSqlIsNotNull() {
      addCriterion("EXEC_SQL is not null");
      return this;
    }

    public Criteria andExecSqlEqualTo(String value) {
      addCriterion("EXEC_SQL =", value, "execSql");
      return this;
    }

    public Criteria andExecSqlNotEqualTo(String value) {
      addCriterion("EXEC_SQL <>", value, "execSql");
      return this;
    }

    public Criteria andExecSqlGreaterThan(String value) {
      addCriterion("EXEC_SQL >", value, "execSql");
      return this;
    }

    public Criteria andExecSqlGreaterThanOrEqualTo(String value) {
      addCriterion("EXEC_SQL >=", value, "execSql");
      return this;
    }

    public Criteria andExecSqlLessThan(String value) {
      addCriterion("EXEC_SQL <", value, "execSql");
      return this;
    }

    public Criteria andExecSqlLessThanOrEqualTo(String value) {
      addCriterion("EXEC_SQL <=", value, "execSql");
      return this;
    }

    public Criteria andExecSqlLike(String value) {
      addCriterion("EXEC_SQL like", value, "execSql");
      return this;
    }

    public Criteria andExecSqlNotLike(String value) {
      addCriterion("EXEC_SQL not like", value, "execSql");
      return this;
    }

    public Criteria andExecSqlIn(List values) {
      addCriterion("EXEC_SQL in", values, "execSql");
      return this;
    }

    public Criteria andExecSqlNotIn(List values) {
      addCriterion("EXEC_SQL not in", values, "execSql");
      return this;
    }

    public Criteria andExecSqlBetween(String value1, String value2) {
      addCriterion("EXEC_SQL between", value1, value2, "execSql");
      return this;
    }

    public Criteria andExecSqlNotBetween(String value1, String value2) {
      addCriterion("EXEC_SQL not between", value1, value2, "execSql");
      return this;
    }

    public Criteria andIsPhyIsNull() {
      addCriterion("IS_PHY is null");
      return this;
    }

    public Criteria andIsPhyIsNotNull() {
      addCriterion("IS_PHY is not null");
      return this;
    }

    public Criteria andIsPhyEqualTo(Integer value) {
      addCriterion("IS_PHY =", value, "isPhy");
      return this;
    }

    public Criteria andIsPhyNotEqualTo(Integer value) {
      addCriterion("IS_PHY <>", value, "isPhy");
      return this;
    }

    public Criteria andIsPhyGreaterThan(Integer value) {
      addCriterion("IS_PHY >", value, "isPhy");
      return this;
    }

    public Criteria andIsPhyGreaterThanOrEqualTo(Integer value) {
      addCriterion("IS_PHY >=", value, "isPhy");
      return this;
    }

    public Criteria andIsPhyLessThan(Integer value) {
      addCriterion("IS_PHY <", value, "isPhy");
      return this;
    }

    public Criteria andIsPhyLessThanOrEqualTo(Integer value) {
      addCriterion("IS_PHY <=", value, "isPhy");
      return this;
    }

    public Criteria andIsPhyIn(List values) {
      addCriterion("IS_PHY in", values, "isPhy");
      return this;
    }

    public Criteria andIsPhyNotIn(List values) {
      addCriterion("IS_PHY not in", values, "isPhy");
      return this;
    }

    public Criteria andIsPhyBetween(Integer value1, Integer value2) {
      addCriterion("IS_PHY between", value1, value2, "isPhy");
      return this;
    }

    public Criteria andIsPhyNotBetween(Integer value1, Integer value2) {
      addCriterion("IS_PHY not between", value1, value2, "isPhy");
      return this;
    }
  }
}
