package com.pltfm.sys.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SysDeptExample {
  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database table
   * sys_dept
   * 
   * @ibatorgenerated Wed Dec 30 18:19:34 CST 2009
   */
  protected String orderByClause;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database table
   * sys_dept
   * 
   * @ibatorgenerated Wed Dec 30 18:19:34 CST 2009
   */
  protected List<Criteria> oredCriteria;

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table sys_dept
   * 
   * @ibatorgenerated Wed Dec 30 18:19:34 CST 2009
   */
  public SysDeptExample() {
    oredCriteria = new ArrayList<Criteria>();
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table sys_dept
   * 
   * @ibatorgenerated Wed Dec 30 18:19:34 CST 2009
   */
  protected SysDeptExample(SysDeptExample example) {
    this.orderByClause = example.orderByClause;
    this.oredCriteria = example.oredCriteria;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table sys_dept
   * 
   * @ibatorgenerated Wed Dec 30 18:19:34 CST 2009
   */
  public void setOrderByClause(String orderByClause) {
    this.orderByClause = orderByClause;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table sys_dept
   * 
   * @ibatorgenerated Wed Dec 30 18:19:34 CST 2009
   */
  public String getOrderByClause() {
    return orderByClause;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table sys_dept
   * 
   * @ibatorgenerated Wed Dec 30 18:19:34 CST 2009
   */
  public List<Criteria> getOredCriteria() {
    return oredCriteria;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table sys_dept
   * 
   * @ibatorgenerated Wed Dec 30 18:19:34 CST 2009
   */
  public void or(Criteria criteria) {
    oredCriteria.add(criteria);
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table sys_dept
   * 
   * @ibatorgenerated Wed Dec 30 18:19:34 CST 2009
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
   * table sys_dept
   * 
   * @ibatorgenerated Wed Dec 30 18:19:34 CST 2009
   */
  protected Criteria createCriteriaInternal() {
    Criteria criteria = new Criteria();
    return criteria;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table sys_dept
   * 
   * @ibatorgenerated Wed Dec 30 18:19:34 CST 2009
   */
  public void clear() {
    oredCriteria.clear();
  }

  /**
   * This class was generated by Apache iBATIS ibator. This class corresponds to the database table
   * sys_dept
   * 
   * @ibatorgenerated Wed Dec 30 18:19:34 CST 2009
   */
  public static class Criteria {
    protected List<String> criteriaWithoutValue;

    protected List<Map<String, Object>> criteriaWithSingleValue;

    protected List<Map<String, Object>> criteriaWithListValue;

    protected List<Map<String, Object>> criteriaWithBetweenValue;

    protected Criteria() {
      super();
      criteriaWithoutValue = new ArrayList<String>();
      criteriaWithSingleValue = new ArrayList<Map<String, Object>>();
      criteriaWithListValue = new ArrayList<Map<String, Object>>();
      criteriaWithBetweenValue = new ArrayList<Map<String, Object>>();
    }

    public boolean isValid() {
      return criteriaWithoutValue.size() > 0 || criteriaWithSingleValue.size() > 0
          || criteriaWithListValue.size() > 0 || criteriaWithBetweenValue.size() > 0;
    }

    public List<String> getCriteriaWithoutValue() {
      return criteriaWithoutValue;
    }

    public List<Map<String, Object>> getCriteriaWithSingleValue() {
      return criteriaWithSingleValue;
    }

    public List<Map<String, Object>> getCriteriaWithListValue() {
      return criteriaWithListValue;
    }

    public List<Map<String, Object>> getCriteriaWithBetweenValue() {
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
      Map<String, Object> map = new HashMap<String, Object>();
      map.put("condition", condition);
      map.put("value", value);
      criteriaWithSingleValue.add(map);
    }

    protected void addCriterion(String condition, List<? extends Object> values, String property) {
      if (values == null || values.size() == 0) {
        throw new RuntimeException("Value list for " + property + " cannot be null or empty");
      }
      Map<String, Object> map = new HashMap<String, Object>();
      map.put("condition", condition);
      map.put("values", values);
      criteriaWithListValue.add(map);
    }

    protected void addCriterion(String condition, Object value1, Object value2, String property) {
      if (value1 == null || value2 == null) {
        throw new RuntimeException("Between values for " + property + " cannot be null");
      }
      List<Object> list = new ArrayList<Object>();
      list.add(value1);
      list.add(value2);
      Map<String, Object> map = new HashMap<String, Object>();
      map.put("condition", condition);
      map.put("values", list);
      criteriaWithBetweenValue.add(map);
    }

    public Criteria andDeptIdIsNull() {
      addCriterion("dept_id is null");
      return this;
    }

    public Criteria andDeptIdIsNotNull() {
      addCriterion("dept_id is not null");
      return this;
    }

    public Criteria andDeptIdEqualTo(Integer value) {
      addCriterion("dept_id =", value, "deptId");
      return this;
    }

    public Criteria andDeptIdNotEqualTo(Integer value) {
      addCriterion("dept_id <>", value, "deptId");
      return this;
    }

    public Criteria andDeptIdGreaterThan(Integer value) {
      addCriterion("dept_id >", value, "deptId");
      return this;
    }

    public Criteria andDeptIdGreaterThanOrEqualTo(Integer value) {
      addCriterion("dept_id >=", value, "deptId");
      return this;
    }

    public Criteria andDeptIdLessThan(Integer value) {
      addCriterion("dept_id <", value, "deptId");
      return this;
    }

    public Criteria andDeptIdLessThanOrEqualTo(Integer value) {
      addCriterion("dept_id <=", value, "deptId");
      return this;
    }

    public Criteria andDeptIdIn(List<Integer> values) {
      addCriterion("dept_id in", values, "deptId");
      return this;
    }

    public Criteria andDeptIdNotIn(List<Integer> values) {
      addCriterion("dept_id not in", values, "deptId");
      return this;
    }

    public Criteria andDeptIdBetween(Integer value1, Integer value2) {
      addCriterion("dept_id between", value1, value2, "deptId");
      return this;
    }

    public Criteria andDeptIdNotBetween(Integer value1, Integer value2) {
      addCriterion("dept_id not between", value1, value2, "deptId");
      return this;
    }

    public Criteria andDeptCdIsNull() {
      addCriterion("dept_cd is null");
      return this;
    }

    public Criteria andDeptCdIsNotNull() {
      addCriterion("dept_cd is not null");
      return this;
    }

    public Criteria andDeptCdEqualTo(String value) {
      addCriterion("dept_cd =", value, "deptCd");
      return this;
    }

    public Criteria andDeptCdNotEqualTo(String value) {
      addCriterion("dept_cd <>", value, "deptCd");
      return this;
    }

    public Criteria andDeptCdGreaterThan(String value) {
      addCriterion("dept_cd >", value, "deptCd");
      return this;
    }

    public Criteria andDeptCdGreaterThanOrEqualTo(String value) {
      addCriterion("dept_cd >=", value, "deptCd");
      return this;
    }

    public Criteria andDeptCdLessThan(String value) {
      addCriterion("dept_cd <", value, "deptCd");
      return this;
    }

    public Criteria andDeptCdLessThanOrEqualTo(String value) {
      addCriterion("dept_cd <=", value, "deptCd");
      return this;
    }

    public Criteria andDeptCdLike(String value) {
      addCriterion("dept_cd like", value, "deptCd");
      return this;
    }

    public Criteria andDeptCdNotLike(String value) {
      addCriterion("dept_cd not like", value, "deptCd");
      return this;
    }

    public Criteria andDeptCdIn(List<String> values) {
      addCriterion("dept_cd in", values, "deptCd");
      return this;
    }

    public Criteria andDeptCdNotIn(List<String> values) {
      addCriterion("dept_cd not in", values, "deptCd");
      return this;
    }

    public Criteria andDeptCdBetween(String value1, String value2) {
      addCriterion("dept_cd between", value1, value2, "deptCd");
      return this;
    }

    public Criteria andDeptCdNotBetween(String value1, String value2) {
      addCriterion("dept_cd not between", value1, value2, "deptCd");
      return this;
    }

    public Criteria andDeptNameIsNull() {
      addCriterion("dept_name is null");
      return this;
    }

    public Criteria andDeptNameIsNotNull() {
      addCriterion("dept_name is not null");
      return this;
    }

    public Criteria andDeptNameEqualTo(String value) {
      addCriterion("dept_name =", value, "deptName");
      return this;
    }

    public Criteria andDeptNameNotEqualTo(String value) {
      addCriterion("dept_name <>", value, "deptName");
      return this;
    }

    public Criteria andDeptNameGreaterThan(String value) {
      addCriterion("dept_name >", value, "deptName");
      return this;
    }

    public Criteria andDeptNameGreaterThanOrEqualTo(String value) {
      addCriterion("dept_name >=", value, "deptName");
      return this;
    }

    public Criteria andDeptNameLessThan(String value) {
      addCriterion("dept_name <", value, "deptName");
      return this;
    }

    public Criteria andDeptNameLessThanOrEqualTo(String value) {
      addCriterion("dept_name <=", value, "deptName");
      return this;
    }

    public Criteria andDeptNameLike(String value) {
      addCriterion("dept_name like", value, "deptName");
      return this;
    }

    public Criteria andDeptNameNotLike(String value) {
      addCriterion("dept_name not like", value, "deptName");
      return this;
    }

    public Criteria andDeptNameIn(List<String> values) {
      addCriterion("dept_name in", values, "deptName");
      return this;
    }

    public Criteria andDeptNameNotIn(List<String> values) {
      addCriterion("dept_name not in", values, "deptName");
      return this;
    }

    public Criteria andDeptNameBetween(String value1, String value2) {
      addCriterion("dept_name between", value1, value2, "deptName");
      return this;
    }

    public Criteria andDeptNameNotBetween(String value1, String value2) {
      addCriterion("dept_name not between", value1, value2, "deptName");
      return this;
    }

    public Criteria andUpDeptidIsNull() {
      addCriterion("up_deptid is null");
      return this;
    }

    public Criteria andUpDeptidIsNotNull() {
      addCriterion("up_deptid is not null");
      return this;
    }

    public Criteria andUpDeptidEqualTo(Integer value) {
      addCriterion("up_deptid =", value, "upDeptid");
      return this;
    }

    public Criteria andUpDeptidNotEqualTo(Integer value) {
      addCriterion("up_deptid <>", value, "upDeptid");
      return this;
    }

    public Criteria andUpDeptidGreaterThan(Integer value) {
      addCriterion("up_deptid >", value, "upDeptid");
      return this;
    }

    public Criteria andUpDeptidGreaterThanOrEqualTo(Integer value) {
      addCriterion("up_deptid >=", value, "upDeptid");
      return this;
    }

    public Criteria andUpDeptidLessThan(Integer value) {
      addCriterion("up_deptid <", value, "upDeptid");
      return this;
    }

    public Criteria andUpDeptidLessThanOrEqualTo(Integer value) {
      addCriterion("up_deptid <=", value, "upDeptid");
      return this;
    }

    public Criteria andUpDeptidIn(List<Integer> values) {
      addCriterion("up_deptid in", values, "upDeptid");
      return this;
    }

    public Criteria andUpDeptidNotIn(List<Integer> values) {
      addCriterion("up_deptid not in", values, "upDeptid");
      return this;
    }

    public Criteria andUpDeptidBetween(Integer value1, Integer value2) {
      addCriterion("up_deptid between", value1, value2, "upDeptid");
      return this;
    }

    public Criteria andUpDeptidNotBetween(Integer value1, Integer value2) {
      addCriterion("up_deptid not between", value1, value2, "upDeptid");
      return this;
    }

    public Criteria andDeptLvIsNull() {
      addCriterion("dept_lv is null");
      return this;
    }

    public Criteria andDeptLvIsNotNull() {
      addCriterion("dept_lv is not null");
      return this;
    }

    public Criteria andDeptLvEqualTo(String value) {
      addCriterion("dept_lv =", value, "deptLv");
      return this;
    }

    public Criteria andDeptLvNotEqualTo(String value) {
      addCriterion("dept_lv <>", value, "deptLv");
      return this;
    }

    public Criteria andDeptLvGreaterThan(String value) {
      addCriterion("dept_lv >", value, "deptLv");
      return this;
    }

    public Criteria andDeptLvGreaterThanOrEqualTo(String value) {
      addCriterion("dept_lv >=", value, "deptLv");
      return this;
    }

    public Criteria andDeptLvLessThan(String value) {
      addCriterion("dept_lv <", value, "deptLv");
      return this;
    }

    public Criteria andDeptLvLessThanOrEqualTo(String value) {
      addCriterion("dept_lv <=", value, "deptLv");
      return this;
    }

    public Criteria andDeptLvLike(String value) {
      addCriterion("dept_lv like", value, "deptLv");
      return this;
    }

    public Criteria andDeptLvNotLike(String value) {
      addCriterion("dept_lv not like", value, "deptLv");
      return this;
    }

    public Criteria andDeptLvIn(List<String> values) {
      addCriterion("dept_lv in", values, "deptLv");
      return this;
    }

    public Criteria andDeptLvNotIn(List<String> values) {
      addCriterion("dept_lv not in", values, "deptLv");
      return this;
    }

    public Criteria andDeptLvBetween(String value1, String value2) {
      addCriterion("dept_lv between", value1, value2, "deptLv");
      return this;
    }

    public Criteria andDeptLvNotBetween(String value1, String value2) {
      addCriterion("dept_lv not between", value1, value2, "deptLv");
      return this;
    }

    public Criteria andDeptRemarkIsNull() {
      addCriterion("dept_remark is null");
      return this;
    }

    public Criteria andDeptRemarkIsNotNull() {
      addCriterion("dept_remark is not null");
      return this;
    }

    public Criteria andDeptRemarkEqualTo(String value) {
      addCriterion("dept_remark =", value, "deptRemark");
      return this;
    }

    public Criteria andDeptRemarkNotEqualTo(String value) {
      addCriterion("dept_remark <>", value, "deptRemark");
      return this;
    }

    public Criteria andDeptRemarkGreaterThan(String value) {
      addCriterion("dept_remark >", value, "deptRemark");
      return this;
    }

    public Criteria andDeptRemarkGreaterThanOrEqualTo(String value) {
      addCriterion("dept_remark >=", value, "deptRemark");
      return this;
    }

    public Criteria andDeptRemarkLessThan(String value) {
      addCriterion("dept_remark <", value, "deptRemark");
      return this;
    }

    public Criteria andDeptRemarkLessThanOrEqualTo(String value) {
      addCriterion("dept_remark <=", value, "deptRemark");
      return this;
    }

    public Criteria andDeptRemarkLike(String value) {
      addCriterion("dept_remark like", value, "deptRemark");
      return this;
    }

    public Criteria andDeptRemarkNotLike(String value) {
      addCriterion("dept_remark not like", value, "deptRemark");
      return this;
    }

    public Criteria andDeptRemarkIn(List<String> values) {
      addCriterion("dept_remark in", values, "deptRemark");
      return this;
    }

    public Criteria andDeptRemarkNotIn(List<String> values) {
      addCriterion("dept_remark not in", values, "deptRemark");
      return this;
    }

    public Criteria andDeptRemarkBetween(String value1, String value2) {
      addCriterion("dept_remark between", value1, value2, "deptRemark");
      return this;
    }

    public Criteria andDeptRemarkNotBetween(String value1, String value2) {
      addCriterion("dept_remark not between", value1, value2, "deptRemark");
      return this;
    }

    public Criteria andIsEnableIsNull() {
      addCriterion("is_enable is null");
      return this;
    }

    public Criteria andIsEnableIsNotNull() {
      addCriterion("is_enable is not null");
      return this;
    }

    public Criteria andIsEnableEqualTo(String value) {
      addCriterion("is_enable =", value, "isEnable");
      return this;
    }

    public Criteria andIsEnableNotEqualTo(String value) {
      addCriterion("is_enable <>", value, "isEnable");
      return this;
    }

    public Criteria andIsEnableGreaterThan(String value) {
      addCriterion("is_enable >", value, "isEnable");
      return this;
    }

    public Criteria andIsEnableGreaterThanOrEqualTo(String value) {
      addCriterion("is_enable >=", value, "isEnable");
      return this;
    }

    public Criteria andIsEnableLessThan(String value) {
      addCriterion("is_enable <", value, "isEnable");
      return this;
    }

    public Criteria andIsEnableLessThanOrEqualTo(String value) {
      addCriterion("is_enable <=", value, "isEnable");
      return this;
    }

    public Criteria andIsEnableLike(String value) {
      addCriterion("is_enable like", value, "isEnable");
      return this;
    }

    public Criteria andIsEnableNotLike(String value) {
      addCriterion("is_enable not like", value, "isEnable");
      return this;
    }

    public Criteria andIsEnableIn(List<String> values) {
      addCriterion("is_enable in", values, "isEnable");
      return this;
    }

    public Criteria andIsEnableNotIn(List<String> values) {
      addCriterion("is_enable not in", values, "isEnable");
      return this;
    }

    public Criteria andIsEnableBetween(String value1, String value2) {
      addCriterion("is_enable between", value1, value2, "isEnable");
      return this;
    }

    public Criteria andIsEnableNotBetween(String value1, String value2) {
      addCriterion("is_enable not between", value1, value2, "isEnable");
      return this;
    }

    public Criteria andCreateDateIsNull() {
      addCriterion("create_date is null");
      return this;
    }

    public Criteria andCreateDateIsNotNull() {
      addCriterion("create_date is not null");
      return this;
    }

    public Criteria andCreateDateEqualTo(Date value) {
      addCriterion("create_date =", value, "createDate");
      return this;
    }

    public Criteria andCreateDateNotEqualTo(Date value) {
      addCriterion("create_date <>", value, "createDate");
      return this;
    }

    public Criteria andCreateDateGreaterThan(Date value) {
      addCriterion("create_date >", value, "createDate");
      return this;
    }

    public Criteria andCreateDateGreaterThanOrEqualTo(Date value) {
      addCriterion("create_date >=", value, "createDate");
      return this;
    }

    public Criteria andCreateDateLessThan(Date value) {
      addCriterion("create_date <", value, "createDate");
      return this;
    }

    public Criteria andCreateDateLessThanOrEqualTo(Date value) {
      addCriterion("create_date <=", value, "createDate");
      return this;
    }

    public Criteria andCreateDateIn(List<Date> values) {
      addCriterion("create_date in", values, "createDate");
      return this;
    }

    public Criteria andCreateDateNotIn(List<Date> values) {
      addCriterion("create_date not in", values, "createDate");
      return this;
    }

    public Criteria andCreateDateBetween(Date value1, Date value2) {
      addCriterion("create_date between", value1, value2, "createDate");
      return this;
    }

    public Criteria andCreateDateNotBetween(Date value1, Date value2) {
      addCriterion("create_date not between", value1, value2, "createDate");
      return this;
    }

    public Criteria andCreateUserIsNull() {
      addCriterion("create_user is null");
      return this;
    }

    public Criteria andCreateUserIsNotNull() {
      addCriterion("create_user is not null");
      return this;
    }

    public Criteria andCreateUserEqualTo(Integer value) {
      addCriterion("create_user =", value, "createUser");
      return this;
    }

    public Criteria andCreateUserNotEqualTo(Integer value) {
      addCriterion("create_user <>", value, "createUser");
      return this;
    }

    public Criteria andCreateUserGreaterThan(Integer value) {
      addCriterion("create_user >", value, "createUser");
      return this;
    }

    public Criteria andCreateUserGreaterThanOrEqualTo(Integer value) {
      addCriterion("create_user >=", value, "createUser");
      return this;
    }

    public Criteria andCreateUserLessThan(Integer value) {
      addCriterion("create_user <", value, "createUser");
      return this;
    }

    public Criteria andCreateUserLessThanOrEqualTo(Integer value) {
      addCriterion("create_user <=", value, "createUser");
      return this;
    }

    public Criteria andCreateUserIn(List<Integer> values) {
      addCriterion("create_user in", values, "createUser");
      return this;
    }

    public Criteria andCreateUserNotIn(List<Integer> values) {
      addCriterion("create_user not in", values, "createUser");
      return this;
    }

    public Criteria andCreateUserBetween(Integer value1, Integer value2) {
      addCriterion("create_user between", value1, value2, "createUser");
      return this;
    }

    public Criteria andCreateUserNotBetween(Integer value1, Integer value2) {
      addCriterion("create_user not between", value1, value2, "createUser");
      return this;
    }

    public Criteria andUpdateDateIsNull() {
      addCriterion("update_date is null");
      return this;
    }

    public Criteria andUpdateDateIsNotNull() {
      addCriterion("update_date is not null");
      return this;
    }

    public Criteria andUpdateDateEqualTo(Date value) {
      addCriterion("update_date =", value, "updateDate");
      return this;
    }

    public Criteria andUpdateDateNotEqualTo(Date value) {
      addCriterion("update_date <>", value, "updateDate");
      return this;
    }

    public Criteria andUpdateDateGreaterThan(Date value) {
      addCriterion("update_date >", value, "updateDate");
      return this;
    }

    public Criteria andUpdateDateGreaterThanOrEqualTo(Date value) {
      addCriterion("update_date >=", value, "updateDate");
      return this;
    }

    public Criteria andUpdateDateLessThan(Date value) {
      addCriterion("update_date <", value, "updateDate");
      return this;
    }

    public Criteria andUpdateDateLessThanOrEqualTo(Date value) {
      addCriterion("update_date <=", value, "updateDate");
      return this;
    }

    public Criteria andUpdateDateIn(List<Date> values) {
      addCriterion("update_date in", values, "updateDate");
      return this;
    }

    public Criteria andUpdateDateNotIn(List<Date> values) {
      addCriterion("update_date not in", values, "updateDate");
      return this;
    }

    public Criteria andUpdateDateBetween(Date value1, Date value2) {
      addCriterion("update_date between", value1, value2, "updateDate");
      return this;
    }

    public Criteria andUpdateDateNotBetween(Date value1, Date value2) {
      addCriterion("update_date not between", value1, value2, "updateDate");
      return this;
    }

    public Criteria andUpdateUserIsNull() {
      addCriterion("update_user is null");
      return this;
    }

    public Criteria andUpdateUserIsNotNull() {
      addCriterion("update_user is not null");
      return this;
    }

    public Criteria andUpdateUserEqualTo(Integer value) {
      addCriterion("update_user =", value, "updateUser");
      return this;
    }

    public Criteria andUpdateUserNotEqualTo(Integer value) {
      addCriterion("update_user <>", value, "updateUser");
      return this;
    }

    public Criteria andUpdateUserGreaterThan(Integer value) {
      addCriterion("update_user >", value, "updateUser");
      return this;
    }

    public Criteria andUpdateUserGreaterThanOrEqualTo(Integer value) {
      addCriterion("update_user >=", value, "updateUser");
      return this;
    }

    public Criteria andUpdateUserLessThan(Integer value) {
      addCriterion("update_user <", value, "updateUser");
      return this;
    }

    public Criteria andUpdateUserLessThanOrEqualTo(Integer value) {
      addCriterion("update_user <=", value, "updateUser");
      return this;
    }

    public Criteria andUpdateUserIn(List<Integer> values) {
      addCriterion("update_user in", values, "updateUser");
      return this;
    }

    public Criteria andUpdateUserNotIn(List<Integer> values) {
      addCriterion("update_user not in", values, "updateUser");
      return this;
    }

    public Criteria andUpdateUserBetween(Integer value1, Integer value2) {
      addCriterion("update_user between", value1, value2, "updateUser");
      return this;
    }

    public Criteria andUpdateUserNotBetween(Integer value1, Integer value2) {
      addCriterion("update_user not between", value1, value2, "updateUser");
      return this;
    }
  }
}
