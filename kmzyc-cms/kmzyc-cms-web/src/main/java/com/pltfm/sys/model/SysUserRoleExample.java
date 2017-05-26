package com.pltfm.sys.model;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SysUserRoleExample {
    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database table SYS_USER_ROLE
     *
     * @ibatorgenerated Thu Nov 24 14:28:04 CST 2011
     */
    protected String orderByClause;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database table SYS_USER_ROLE
     *
     * @ibatorgenerated Thu Nov 24 14:28:04 CST 2011
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_USER_ROLE
     *
     * @ibatorgenerated Thu Nov 24 14:28:04 CST 2011
     */
    public SysUserRoleExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_USER_ROLE
     *
     * @ibatorgenerated Thu Nov 24 14:28:04 CST 2011
     */
    protected SysUserRoleExample(SysUserRoleExample example) {
        this.orderByClause = example.orderByClause;
        this.oredCriteria = example.oredCriteria;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_USER_ROLE
     *
     * @ibatorgenerated Thu Nov 24 14:28:04 CST 2011
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_USER_ROLE
     *
     * @ibatorgenerated Thu Nov 24 14:28:04 CST 2011
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_USER_ROLE
     *
     * @ibatorgenerated Thu Nov 24 14:28:04 CST 2011
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_USER_ROLE
     *
     * @ibatorgenerated Thu Nov 24 14:28:04 CST 2011
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_USER_ROLE
     *
     * @ibatorgenerated Thu Nov 24 14:28:04 CST 2011
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_USER_ROLE
     *
     * @ibatorgenerated Thu Nov 24 14:28:04 CST 2011
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_USER_ROLE
     *
     * @ibatorgenerated Thu Nov 24 14:28:04 CST 2011
     */
    public void clear() {
        oredCriteria.clear();
    }

    /**
     * This class was generated by Apache iBATIS ibator.
     * This class corresponds to the database table SYS_USER_ROLE
     *
     * @ibatorgenerated Thu Nov 24 14:28:04 CST 2011
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
            return criteriaWithoutValue.size() > 0
                    || criteriaWithSingleValue.size() > 0
                    || criteriaWithListValue.size() > 0
                    || criteriaWithBetweenValue.size() > 0;
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

        public Criteria andUserroleIdIsNull() {
            addCriterion("USERROLE_ID is null");
            return this;
        }

        public Criteria andUserroleIdIsNotNull() {
            addCriterion("USERROLE_ID is not null");
            return this;
        }

        public Criteria andUserroleIdEqualTo(Integer value) {
            addCriterion("USERROLE_ID =", value, "userroleId");
            return this;
        }

        public Criteria andUserroleIdNotEqualTo(Integer value) {
            addCriterion("USERROLE_ID <>", value, "userroleId");
            return this;
        }

        public Criteria andUserroleIdGreaterThan(Integer value) {
            addCriterion("USERROLE_ID >", value, "userroleId");
            return this;
        }

        public Criteria andUserroleIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("USERROLE_ID >=", value, "userroleId");
            return this;
        }

        public Criteria andUserroleIdLessThan(Integer value) {
            addCriterion("USERROLE_ID <", value, "userroleId");
            return this;
        }

        public Criteria andUserroleIdLessThanOrEqualTo(Integer value) {
            addCriterion("USERROLE_ID <=", value, "userroleId");
            return this;
        }

        public Criteria andUserroleIdIn(List<Integer> values) {
            addCriterion("USERROLE_ID in", values, "userroleId");
            return this;
        }

        public Criteria andUserroleIdNotIn(List<Integer> values) {
            addCriterion("USERROLE_ID not in", values, "userroleId");
            return this;
        }

        public Criteria andUserroleIdBetween(Integer value1, Integer value2) {
            addCriterion("USERROLE_ID between", value1, value2, "userroleId");
            return this;
        }

        public Criteria andUserroleIdNotBetween(Integer value1, Integer value2) {
            addCriterion("USERROLE_ID not between", value1, value2, "userroleId");
            return this;
        }

        public Criteria andUserIdIsNull() {
            addCriterion("USER_ID is null");
            return this;
        }

        public Criteria andUserIdIsNotNull() {
            addCriterion("USER_ID is not null");
            return this;
        }

        public Criteria andUserIdEqualTo(Integer value) {
            addCriterion("USER_ID =", value, "userId");
            return this;
        }

        public Criteria andUserIdNotEqualTo(Integer value) {
            addCriterion("USER_ID <>", value, "userId");
            return this;
        }

        public Criteria andUserIdGreaterThan(Integer value) {
            addCriterion("USER_ID >", value, "userId");
            return this;
        }

        public Criteria andUserIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("USER_ID >=", value, "userId");
            return this;
        }

        public Criteria andUserIdLessThan(Integer value) {
            addCriterion("USER_ID <", value, "userId");
            return this;
        }

        public Criteria andUserIdLessThanOrEqualTo(Integer value) {
            addCriterion("USER_ID <=", value, "userId");
            return this;
        }

        public Criteria andUserIdIn(List<Integer> values) {
            addCriterion("USER_ID in", values, "userId");
            return this;
        }

        public Criteria andUserIdNotIn(List<Integer> values) {
            addCriterion("USER_ID not in", values, "userId");
            return this;
        }

        public Criteria andUserIdBetween(Integer value1, Integer value2) {
            addCriterion("USER_ID between", value1, value2, "userId");
            return this;
        }

        public Criteria andUserIdNotBetween(Integer value1, Integer value2) {
            addCriterion("USER_ID not between", value1, value2, "userId");
            return this;
        }

        public Criteria andRoleIdIsNull() {
            addCriterion("ROLE_ID is null");
            return this;
        }

        public Criteria andRoleIdIsNotNull() {
            addCriterion("ROLE_ID is not null");
            return this;
        }

        public Criteria andRoleIdEqualTo(Integer value) {
            addCriterion("ROLE_ID =", value, "roleId");
            return this;
        }

        public Criteria andRoleIdNotEqualTo(Integer value) {
            addCriterion("ROLE_ID <>", value, "roleId");
            return this;
        }

        public Criteria andRoleIdGreaterThan(Integer value) {
            addCriterion("ROLE_ID >", value, "roleId");
            return this;
        }

        public Criteria andRoleIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("ROLE_ID >=", value, "roleId");
            return this;
        }

        public Criteria andRoleIdLessThan(Integer value) {
            addCriterion("ROLE_ID <", value, "roleId");
            return this;
        }

        public Criteria andRoleIdLessThanOrEqualTo(Integer value) {
            addCriterion("ROLE_ID <=", value, "roleId");
            return this;
        }

        public Criteria andRoleIdIn(List<Integer> values) {
            addCriterion("ROLE_ID in", values, "roleId");
            return this;
        }

        public Criteria andRoleIdNotIn(List<Integer> values) {
            addCriterion("ROLE_ID not in", values, "roleId");
            return this;
        }

        public Criteria andRoleIdBetween(Integer value1, Integer value2) {
            addCriterion("ROLE_ID between", value1, value2, "roleId");
            return this;
        }

        public Criteria andRoleIdNotBetween(Integer value1, Integer value2) {
            addCriterion("ROLE_ID not between", value1, value2, "roleId");
            return this;
        }
    }
}