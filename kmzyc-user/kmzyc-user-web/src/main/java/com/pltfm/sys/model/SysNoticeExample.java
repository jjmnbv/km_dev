package com.pltfm.sys.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SysNoticeExample implements Serializable {
  /**
   * 
   */
  private static final long serialVersionUID = 5044828119258413324L;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database table
   * sys_notice
   *
   * @ibatorgenerated Wed Jan 27 15:44:06 CST 2010
   */
  protected String orderByClause;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database table
   * sys_notice
   *
   * @ibatorgenerated Wed Jan 27 15:44:06 CST 2010
   */
  protected List<Criteria> oredCriteria;

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table sys_notice
   *
   * @ibatorgenerated Wed Jan 27 15:44:06 CST 2010
   */
  public SysNoticeExample() {
    oredCriteria = new ArrayList<Criteria>();
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table sys_notice
   *
   * @ibatorgenerated Wed Jan 27 15:44:06 CST 2010
   */
  protected SysNoticeExample(SysNoticeExample example) {
    this.orderByClause = example.orderByClause;
    this.oredCriteria = example.oredCriteria;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table sys_notice
   *
   * @ibatorgenerated Wed Jan 27 15:44:06 CST 2010
   */
  public void setOrderByClause(String orderByClause) {
    this.orderByClause = orderByClause;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table sys_notice
   *
   * @ibatorgenerated Wed Jan 27 15:44:06 CST 2010
   */
  public String getOrderByClause() {
    return orderByClause;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table sys_notice
   *
   * @ibatorgenerated Wed Jan 27 15:44:06 CST 2010
   */
  public List<Criteria> getOredCriteria() {
    return oredCriteria;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table sys_notice
   *
   * @ibatorgenerated Wed Jan 27 15:44:06 CST 2010
   */
  public void or(Criteria criteria) {
    oredCriteria.add(criteria);
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table sys_notice
   *
   * @ibatorgenerated Wed Jan 27 15:44:06 CST 2010
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
   * table sys_notice
   *
   * @ibatorgenerated Wed Jan 27 15:44:06 CST 2010
   */
  protected Criteria createCriteriaInternal() {
    Criteria criteria = new Criteria();
    return criteria;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table sys_notice
   *
   * @ibatorgenerated Wed Jan 27 15:44:06 CST 2010
   */
  public void clear() {
    oredCriteria.clear();
  }

  /**
   * This class was generated by Apache iBATIS ibator. This class corresponds to the database table
   * sys_notice
   *
   * @ibatorgenerated Wed Jan 27 15:44:06 CST 2010
   */
  public static class Criteria implements Serializable {
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

    public Criteria andNoticeIdIsNull() {
      addCriterion("notice_id is null");
      return this;
    }

    public Criteria andNoticeIdIsNotNull() {
      addCriterion("notice_id is not null");
      return this;
    }

    public Criteria andNoticeIdEqualTo(Integer value) {
      addCriterion("notice_id =", value, "noticeId");
      return this;
    }

    public Criteria andNoticeIdNotEqualTo(Integer value) {
      addCriterion("notice_id <>", value, "noticeId");
      return this;
    }

    public Criteria andNoticeIdGreaterThan(Integer value) {
      addCriterion("notice_id >", value, "noticeId");
      return this;
    }

    public Criteria andNoticeIdGreaterThanOrEqualTo(Integer value) {
      addCriterion("notice_id >=", value, "noticeId");
      return this;
    }

    public Criteria andNoticeIdLessThan(Integer value) {
      addCriterion("notice_id <", value, "noticeId");
      return this;
    }

    public Criteria andNoticeIdLessThanOrEqualTo(Integer value) {
      addCriterion("notice_id <=", value, "noticeId");
      return this;
    }

    public Criteria andNoticeIdIn(List<Integer> values) {
      addCriterion("notice_id in", values, "noticeId");
      return this;
    }

    public Criteria andNoticeIdNotIn(List<Integer> values) {
      addCriterion("notice_id not in", values, "noticeId");
      return this;
    }

    public Criteria andNoticeIdBetween(Integer value1, Integer value2) {
      addCriterion("notice_id between", value1, value2, "noticeId");
      return this;
    }

    public Criteria andNoticeIdNotBetween(Integer value1, Integer value2) {
      addCriterion("notice_id not between", value1, value2, "noticeId");
      return this;
    }

    public Criteria andNoticeTitleIsNull() {
      addCriterion("notice_title is null");
      return this;
    }

    public Criteria andNoticeTitleIsNotNull() {
      addCriterion("notice_title is not null");
      return this;
    }

    public Criteria andNoticeTitleEqualTo(String value) {
      addCriterion("notice_title =", value, "noticeTitle");
      return this;
    }

    public Criteria andNoticeTitleNotEqualTo(String value) {
      addCriterion("notice_title <>", value, "noticeTitle");
      return this;
    }

    public Criteria andNoticeTitleGreaterThan(String value) {
      addCriterion("notice_title >", value, "noticeTitle");
      return this;
    }

    public Criteria andNoticeTitleGreaterThanOrEqualTo(String value) {
      addCriterion("notice_title >=", value, "noticeTitle");
      return this;
    }

    public Criteria andNoticeTitleLessThan(String value) {
      addCriterion("notice_title <", value, "noticeTitle");
      return this;
    }

    public Criteria andNoticeTitleLessThanOrEqualTo(String value) {
      addCriterion("notice_title <=", value, "noticeTitle");
      return this;
    }

    public Criteria andNoticeTitleLike(String value) {
      addCriterion("notice_title like", value, "noticeTitle");
      return this;
    }

    public Criteria andNoticeTitleNotLike(String value) {
      addCriterion("notice_title not like", value, "noticeTitle");
      return this;
    }

    public Criteria andNoticeTitleIn(List<String> values) {
      addCriterion("notice_title in", values, "noticeTitle");
      return this;
    }

    public Criteria andNoticeTitleNotIn(List<String> values) {
      addCriterion("notice_title not in", values, "noticeTitle");
      return this;
    }

    public Criteria andNoticeTitleBetween(String value1, String value2) {
      addCriterion("notice_title between", value1, value2, "noticeTitle");
      return this;
    }

    public Criteria andNoticeTitleNotBetween(String value1, String value2) {
      addCriterion("notice_title not between", value1, value2, "noticeTitle");
      return this;
    }

    public Criteria andNoticeContentIsNull() {
      addCriterion("notice_content is null");
      return this;
    }

    public Criteria andNoticeContentIsNotNull() {
      addCriterion("notice_content is not null");
      return this;
    }

    public Criteria andNoticeContentEqualTo(String value) {
      addCriterion("notice_content =", value, "noticeContent");
      return this;
    }

    public Criteria andNoticeContentNotEqualTo(String value) {
      addCriterion("notice_content <>", value, "noticeContent");
      return this;
    }

    public Criteria andNoticeContentGreaterThan(String value) {
      addCriterion("notice_content >", value, "noticeContent");
      return this;
    }

    public Criteria andNoticeContentGreaterThanOrEqualTo(String value) {
      addCriterion("notice_content >=", value, "noticeContent");
      return this;
    }

    public Criteria andNoticeContentLessThan(String value) {
      addCriterion("notice_content <", value, "noticeContent");
      return this;
    }

    public Criteria andNoticeContentLessThanOrEqualTo(String value) {
      addCriterion("notice_content <=", value, "noticeContent");
      return this;
    }

    public Criteria andNoticeContentLike(String value) {
      addCriterion("notice_content like", value, "noticeContent");
      return this;
    }

    public Criteria andNoticeContentNotLike(String value) {
      addCriterion("notice_content not like", value, "noticeContent");
      return this;
    }

    public Criteria andNoticeContentIn(List<String> values) {
      addCriterion("notice_content in", values, "noticeContent");
      return this;
    }

    public Criteria andNoticeContentNotIn(List<String> values) {
      addCriterion("notice_content not in", values, "noticeContent");
      return this;
    }

    public Criteria andNoticeContentBetween(String value1, String value2) {
      addCriterion("notice_content between", value1, value2, "noticeContent");
      return this;
    }

    public Criteria andNoticeContentNotBetween(String value1, String value2) {
      addCriterion("notice_content not between", value1, value2, "noticeContent");
      return this;
    }

    public Criteria andNoticeTypeIsNull() {
      addCriterion("notice_type is null");
      return this;
    }

    public Criteria andNoticeTypeIsNotNull() {
      addCriterion("notice_type is not null");
      return this;
    }

    public Criteria andNoticeTypeEqualTo(String value) {
      addCriterion("notice_type =", value, "noticeType");
      return this;
    }

    public Criteria andNoticeTypeNotEqualTo(String value) {
      addCriterion("notice_type <>", value, "noticeType");
      return this;
    }

    public Criteria andNoticeTypeGreaterThan(String value) {
      addCriterion("notice_type >", value, "noticeType");
      return this;
    }

    public Criteria andNoticeTypeGreaterThanOrEqualTo(String value) {
      addCriterion("notice_type >=", value, "noticeType");
      return this;
    }

    public Criteria andNoticeTypeLessThan(String value) {
      addCriterion("notice_type <", value, "noticeType");
      return this;
    }

    public Criteria andNoticeTypeLessThanOrEqualTo(String value) {
      addCriterion("notice_type <=", value, "noticeType");
      return this;
    }

    public Criteria andNoticeTypeLike(String value) {
      addCriterion("notice_type like", value, "noticeType");
      return this;
    }

    public Criteria andNoticeTypeNotLike(String value) {
      addCriterion("notice_type not like", value, "noticeType");
      return this;
    }

    public Criteria andNoticeTypeIn(List<String> values) {
      addCriterion("notice_type in", values, "noticeType");
      return this;
    }

    public Criteria andNoticeTypeNotIn(List<String> values) {
      addCriterion("notice_type not in", values, "noticeType");
      return this;
    }

    public Criteria andNoticeTypeBetween(String value1, String value2) {
      addCriterion("notice_type between", value1, value2, "noticeType");
      return this;
    }

    public Criteria andNoticeTypeNotBetween(String value1, String value2) {
      addCriterion("notice_type not between", value1, value2, "noticeType");
      return this;
    }

    public Criteria andNoticeStIsNull() {
      addCriterion("notice_st is null");
      return this;
    }

    public Criteria andNoticeStIsNotNull() {
      addCriterion("notice_st is not null");
      return this;
    }

    public Criteria andNoticeStEqualTo(String value) {
      addCriterion("notice_st =", value, "noticeSt");
      return this;
    }

    public Criteria andNoticeStNotEqualTo(String value) {
      addCriterion("notice_st <>", value, "noticeSt");
      return this;
    }

    public Criteria andNoticeStGreaterThan(String value) {
      addCriterion("notice_st >", value, "noticeSt");
      return this;
    }

    public Criteria andNoticeStGreaterThanOrEqualTo(String value) {
      addCriterion("notice_st >=", value, "noticeSt");
      return this;
    }

    public Criteria andNoticeStLessThan(String value) {
      addCriterion("notice_st <", value, "noticeSt");
      return this;
    }

    public Criteria andNoticeStLessThanOrEqualTo(String value) {
      addCriterion("notice_st <=", value, "noticeSt");
      return this;
    }

    public Criteria andNoticeStLike(String value) {
      addCriterion("notice_st like", value, "noticeSt");
      return this;
    }

    public Criteria andNoticeStNotLike(String value) {
      addCriterion("notice_st not like", value, "noticeSt");
      return this;
    }

    public Criteria andNoticeStIn(List<String> values) {
      addCriterion("notice_st in", values, "noticeSt");
      return this;
    }

    public Criteria andNoticeStNotIn(List<String> values) {
      addCriterion("notice_st not in", values, "noticeSt");
      return this;
    }

    public Criteria andNoticeStBetween(String value1, String value2) {
      addCriterion("notice_st between", value1, value2, "noticeSt");
      return this;
    }

    public Criteria andNoticeStNotBetween(String value1, String value2) {
      addCriterion("notice_st not between", value1, value2, "noticeSt");
      return this;
    }

    public Criteria andNoticeRemarkIsNull() {
      addCriterion("notice_remark is null");
      return this;
    }

    public Criteria andNoticeRemarkIsNotNull() {
      addCriterion("notice_remark is not null");
      return this;
    }

    public Criteria andNoticeRemarkEqualTo(String value) {
      addCriterion("notice_remark =", value, "noticeRemark");
      return this;
    }

    public Criteria andNoticeRemarkNotEqualTo(String value) {
      addCriterion("notice_remark <>", value, "noticeRemark");
      return this;
    }

    public Criteria andNoticeRemarkGreaterThan(String value) {
      addCriterion("notice_remark >", value, "noticeRemark");
      return this;
    }

    public Criteria andNoticeRemarkGreaterThanOrEqualTo(String value) {
      addCriterion("notice_remark >=", value, "noticeRemark");
      return this;
    }

    public Criteria andNoticeRemarkLessThan(String value) {
      addCriterion("notice_remark <", value, "noticeRemark");
      return this;
    }

    public Criteria andNoticeRemarkLessThanOrEqualTo(String value) {
      addCriterion("notice_remark <=", value, "noticeRemark");
      return this;
    }

    public Criteria andNoticeRemarkLike(String value) {
      addCriterion("notice_remark like", value, "noticeRemark");
      return this;
    }

    public Criteria andNoticeRemarkNotLike(String value) {
      addCriterion("notice_remark not like", value, "noticeRemark");
      return this;
    }

    public Criteria andNoticeRemarkIn(List<String> values) {
      addCriterion("notice_remark in", values, "noticeRemark");
      return this;
    }

    public Criteria andNoticeRemarkNotIn(List<String> values) {
      addCriterion("notice_remark not in", values, "noticeRemark");
      return this;
    }

    public Criteria andNoticeRemarkBetween(String value1, String value2) {
      addCriterion("notice_remark between", value1, value2, "noticeRemark");
      return this;
    }

    public Criteria andNoticeRemarkNotBetween(String value1, String value2) {
      addCriterion("notice_remark not between", value1, value2, "noticeRemark");
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
