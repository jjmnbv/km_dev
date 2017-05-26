package com.pltfm.app.vobject;

import java.util.*;

public class ProdBrandDraftExample {

    protected String orderByClause;

    protected List oredCriteria;

    public ProdBrandDraftExample() {
        oredCriteria = new ArrayList();
    }

    protected ProdBrandDraftExample(ProdBrandDraftExample example) {
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
        protected List criteriaWithSingleDateValue;
        protected List criteriaWithListValue;
        protected List criteriaWithBetweenDateValue;
        protected List criteriaWithBetweenValue;
        protected String[] shopCodeSuppliersInfoValue;

        protected Criteria() {
            super();
            criteriaWithoutValue = new ArrayList();
            criteriaWithSingleValue = new ArrayList();
            criteriaWithSingleDateValue = new ArrayList();
            criteriaWithListValue = new ArrayList();
            criteriaWithBetweenValue = new ArrayList();
            criteriaWithBetweenDateValue = new ArrayList();
        }

        public boolean isValid() {
            return criteriaWithoutValue.size() > 0
                    || criteriaWithSingleValue.size() > 0
                    || criteriaWithSingleDateValue.size() > 0
                    || criteriaWithListValue.size() > 0
                    || criteriaWithBetweenValue.size() > 0
                    || criteriaWithBetweenDateValue.size() > 0
                    || shopCodeSuppliersInfoValue != null;
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

        public List getCriteriaWithBetweenDateValue() {
            return criteriaWithBetweenDateValue;
        }

        public List getCriteriaWithSingleDateValue() {
            return criteriaWithSingleDateValue;
        }

        public String[] getShopCodeSuppliersInfoValue() {
            return shopCodeSuppliersInfoValue;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteriaWithoutValue.add(condition);
        }

        protected void addCriterion(String condition, Object value,
                                    String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property
                        + " cannot be null");
            }
            Map map = new HashMap();
            map.put("condition", condition);
            map.put("value", value);
            criteriaWithSingleValue.add(map);
        }

        protected void addCriterion(String condition, List values,
                                    String property) {
            if (values == null || values.size() == 0) {
                throw new RuntimeException("Value list for " + property
                        + " cannot be null or empty");
            }
            Map map = new HashMap();
            map.put("condition", condition);
            map.put("values", values);
            criteriaWithListValue.add(map);
        }

        protected void addCriterion(String condition, Object value1,
                                    Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property
                        + " cannot be null");
            }
            List list = new ArrayList();
            list.add(value1);
            list.add(value2);
            Map map = new HashMap();
            map.put("condition", condition);
            map.put("values", list);
            criteriaWithBetweenValue.add(map);
        }

        private void addDateCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property
                        + " cannot be null");
            }
            Map map = new HashMap();
            map.put("condition", condition);
            map.put("value", value);
            criteriaWithSingleDateValue.add(map);
        }

        private void addDateCriterion(String condition, Date value1, Date value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property
                        + " cannot be null");
            }
            List list = new ArrayList();
            list.add(value1);
            list.add(value2);
            Map map = new HashMap();
            map.put("condition", condition);
            map.put("values", list);
            criteriaWithBetweenDateValue.add(map);
        }

        protected void addCriterionForJDBCDate(String condition, Date value,
                                               String property) {
            addDateCriterion(condition, value, property);
        }

        protected void addCriterionForJDBCDate(String condition, Date value1,
                                               Date value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property
                        + " cannot be null");
            }
            addDateCriterion(condition, value1, value2, property);
        }

        protected void addShopCriterion(String condition1, String condition2, String value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            if (shopCodeSuppliersInfoValue == null) {
                shopCodeSuppliersInfoValue = new String[3];
            }
            shopCodeSuppliersInfoValue[0] = condition1;
            shopCodeSuppliersInfoValue[1] = condition2;
            shopCodeSuppliersInfoValue[2] = value;
        }

        public Criteria andBrandIdIsNull() {
            addCriterion("BRAND_ID is null");
            return this;
        }

        public Criteria andBrandIdIsNotNull() {
            addCriterion("BRAND_ID is not null");
            return this;
        }

        public Criteria andBrandIdEqualTo(Long value) {
            addCriterion("BRAND_ID =", value, "brandId");
            return this;
        }

        public Criteria andBrandIdNotEqualTo(Long value) {
            addCriterion("BRAND_ID <>", value, "brandId");
            return this;
        }

        public Criteria andBrandIdGreaterThan(Long value) {
            addCriterion("BRAND_ID >", value, "brandId");
            return this;
        }

        public Criteria andBrandIdGreaterThanOrEqualTo(Long value) {
            addCriterion("BRAND_ID >=", value, "brandId");
            return this;
        }

        public Criteria andBrandIdLessThan(Long value) {
            addCriterion("BRAND_ID <", value, "brandId");
            return this;
        }

        public Criteria andBrandIdLessThanOrEqualTo(Long value) {
            addCriterion("BRAND_ID <=", value, "brandId");
            return this;
        }

        public Criteria andBrandIdIn(List values) {
            addCriterion("BRAND_ID in", values, "brandId");
            return this;
        }

        public Criteria andBrandIdNotIn(List values) {
            addCriterion("BRAND_ID not in", values, "brandId");
            return this;
        }

        public Criteria andBrandIdBetween(Long value1, Long value2) {
            addCriterion("BRAND_ID between", value1, value2, "brandId");
            return this;
        }

        public Criteria andBrandIdNotBetween(Long value1,
                                             Long value2) {
            addCriterion("BRAND_ID not between", value1, value2, "brandId");
            return this;
        }

        public Criteria andBrandNameIsNull() {
            addCriterion("BRAND_NAME is null");
            return this;
        }

        public Criteria andBrandNameIsNotNull() {
            addCriterion("BRAND_NAME is not null");
            return this;
        }

        public Criteria andBrandNameEqualTo(String value) {
            addCriterion("BRAND_NAME =", value, "brandName");
            return this;
        }

        public Criteria andBrandNameNotEqualTo(String value) {
            addCriterion("BRAND_NAME <>", value, "brandName");
            return this;
        }

        public Criteria andBrandNameGreaterThan(String value) {
            addCriterion("BRAND_NAME >", value, "brandName");
            return this;
        }

        public Criteria andBrandNameGreaterThanOrEqualTo(String value) {
            addCriterion("BRAND_NAME >=", value, "brandName");
            return this;
        }

        public Criteria andBrandNameLessThan(String value) {
            addCriterion("BRAND_NAME <", value, "brandName");
            return this;
        }

        public Criteria andBrandNameLessThanOrEqualTo(String value) {
            addCriterion("BRAND_NAME <=", value, "brandName");
            return this;
        }

        public Criteria andBrandNameLike(String value) {
            addCriterion("BRAND_NAME like", value, "brandName");
            return this;
        }

        public Criteria andBrandNameNotLike(String value) {
            addCriterion("BRAND_NAME not like", value, "brandName");
            return this;
        }

        public Criteria andBrandNameIn(List values) {
            addCriterion("BRAND_NAME in", values, "brandName");
            return this;
        }

        public Criteria andBrandNameNotIn(List values) {
            addCriterion("BRAND_NAME not in", values, "brandName");
            return this;
        }

        public Criteria andBrandNameBetween(String value1, String value2) {
            addCriterion("BRAND_NAME between", value1, value2, "brandName");
            return this;
        }

        public Criteria andBrandNameNotBetween(String value1, String value2) {
            addCriterion("BRAND_NAME not between", value1, value2, "brandName");
            return this;
        }
        public Criteria andNationIsNull() {
            addCriterion("NATION is null");
            return this;
        }

        public Criteria andNationIsNotNull() {
            addCriterion("NATION is not null");
            return this;
        }

        public Criteria andNationEqualTo(String value) {
            addCriterion("NATION =", value, "nation");
            return this;
        }

        public Criteria andNationNotEqualTo(String value) {
            addCriterion("NATION <>", value, "nation");
            return this;
        }

        public Criteria andNationGreaterThan(String value) {
            addCriterion("NATION >", value, "nation");
            return this;
        }

        public Criteria andNationGreaterThanOrEqualTo(String value) {
            addCriterion("NATION >=", value, "nation");
            return this;
        }

        public Criteria andNationLessThan(String value) {
            addCriterion("NATION <", value, "nation");
            return this;
        }

        public Criteria andNationLessThanOrEqualTo(String value) {
            addCriterion("NATION <=", value, "nation");
            return this;
        }

        public Criteria andNationLike(String value) {
            addCriterion("NATION like", value, "nation");
            return this;
        }

        public Criteria andNationNotLike(String value) {
            addCriterion("NATION not like", value, "nation");
            return this;
        }

        public Criteria andNationIn(List values) {
            addCriterion("NATION in", values, "nation");
            return this;
        }

        public Criteria andNationNotIn(List values) {
            addCriterion("NATION not in", values, "nation");
            return this;
        }

        public Criteria andNationBetween(String value1, String value2) {
            addCriterion("NATION between", value1, value2, "nation");
            return this;
        }

        public Criteria andNationNotBetween(String value1, String value2) {
            addCriterion("NATION not between", value1, value2, "nation");
            return this;
        }

        public Criteria andLogoPathIsNull() {
            addCriterion("LOGO_PATH is null");
            return this;
        }

        public Criteria andLogoPathIsNotNull() {
            addCriterion("LOGO_PATH is not null");
            return this;
        }

        public Criteria andLogoPathEqualTo(String value) {
            addCriterion("LOGO_PATH =", value, "logoPath");
            return this;
        }

        public Criteria andLogoPathNotEqualTo(String value) {
            addCriterion("LOGO_PATH <>", value, "logoPath");
            return this;
        }

        public Criteria andLogoPathGreaterThan(String value) {
            addCriterion("LOGO_PATH >", value, "logoPath");
            return this;
        }

        public Criteria andLogoPathGreaterThanOrEqualTo(String value) {
            addCriterion("LOGO_PATH >=", value, "logoPath");
            return this;
        }

        public Criteria andLogoPathLessThan(String value) {
            addCriterion("LOGO_PATH <", value, "logoPath");
            return this;
        }

        public Criteria andLogoPathLessThanOrEqualTo(String value) {
            addCriterion("LOGO_PATH <=", value, "logoPath");
            return this;
        }

        public Criteria andLogoPathLike(String value) {
            addCriterion("LOGO_PATH like", value, "logoPath");
            return this;
        }

        public Criteria andLogoPathNotLike(String value) {
            addCriterion("LOGO_PATH not like", value, "logoPath");
            return this;
        }

        public Criteria andLogoPathIn(List values) {
            addCriterion("LOGO_PATH in", values, "logoPath");
            return this;
        }

        public Criteria andLogoPathNotIn(List values) {
            addCriterion("LOGO_PATH not in", values, "logoPath");
            return this;
        }

        public Criteria andLogoPathBetween(String value1, String value2) {
            addCriterion("LOGO_PATH between", value1, value2, "logoPath");
            return this;
        }

        public Criteria andLogoPathNotBetween(String value1, String value2) {
            addCriterion("LOGO_PATH not between", value1, value2, "logoPath");
            return this;
        }

        public Criteria andEngNameIsNull() {
            addCriterion("ENG_NAME is null");
            return this;
        }

        public Criteria andEngNameIsNotNull() {
            addCriterion("ENG_NAME is not null");
            return this;
        }

        public Criteria andEngNameEqualTo(String value) {
            addCriterion("ENG_NAME =", value, "engName");
            return this;
        }

        public Criteria andEngNameNotEqualTo(String value) {
            addCriterion("ENG_NAME <>", value, "engName");
            return this;
        }

        public Criteria andEngNameGreaterThan(String value) {
            addCriterion("ENG_NAME >", value, "engName");
            return this;
        }

        public Criteria andEngNameGreaterThanOrEqualTo(String value) {
            addCriterion("ENG_NAME >=", value, "engName");
            return this;
        }

        public Criteria andEngNameLessThan(String value) {
            addCriterion("ENG_NAME <", value, "engName");
            return this;
        }

        public Criteria andEngNameLessThanOrEqualTo(String value) {
            addCriterion("ENG_NAME <=", value, "engName");
            return this;
        }

        public Criteria andEngNameLike(String value) {
            addCriterion("ENG_NAME like", value, "engName");
            return this;
        }

        public Criteria andEngNameNotLike(String value) {
            addCriterion("ENG_NAME not like", value, "engName");
            return this;
        }

        public Criteria andEngNameIn(List values) {
            addCriterion("ENG_NAME in", values, "engName");
            return this;
        }

        public Criteria andEngNameNotIn(List values) {
            addCriterion("ENG_NAME not in", values, "engName");
            return this;
        }

        public Criteria andEngNameBetween(String value1, String value2) {
            addCriterion("ENG_NAME between", value1, value2, "engName");
            return this;
        }

        public Criteria andEngNameNotBetween(String value1, String value2) {
            addCriterion("ENG_NAME not between", value1, value2, "engName");
            return this;
        }

        public Criteria andChnSpellIsNull() {
            addCriterion("CHN_SPELL is null");
            return this;
        }

        public Criteria andChnSpellIsNotNull() {
            addCriterion("CHN_SPELL is not null");
            return this;
        }

        public Criteria andChnSpellEqualTo(String value) {
            addCriterion("CHN_SPELL =", value, "chnSpell");
            return this;
        }

        public Criteria andChnSpellNotEqualTo(String value) {
            addCriterion("CHN_SPELL <>", value, "chnSpell");
            return this;
        }

        public Criteria andChnSpellGreaterThan(String value) {
            addCriterion("CHN_SPELL >", value, "chnSpell");
            return this;
        }

        public Criteria andChnSpellGreaterThanOrEqualTo(String value) {
            addCriterion("CHN_SPELL >=", value, "chnSpell");
            return this;
        }

        public Criteria andChnSpellLessThan(String value) {
            addCriterion("CHN_SPELL <", value, "chnSpell");
            return this;
        }

        public Criteria andChnSpellLessThanOrEqualTo(String value) {
            addCriterion("CHN_SPELL <=", value, "chnSpell");
            return this;
        }

        public Criteria andChnSpellLike(String value) {
            addCriterion("CHN_SPELL like", value, "chnSpell");
            return this;
        }

        public Criteria andChnSpellNotLike(String value) {
            addCriterion("CHN_SPELL not like", value, "chnSpell");
            return this;
        }

        public Criteria andChnSpellIn(List values) {
            addCriterion("CHN_SPELL in", values, "chnSpell");
            return this;
        }

        public Criteria andChnSpellNotIn(List values) {
            addCriterion("CHN_SPELL not in", values, "chnSpell");
            return this;
        }

        public Criteria andChnSpellBetween(String value1, String value2) {
            addCriterion("CHN_SPELL between", value1, value2, "chnSpell");
            return this;
        }

        public Criteria andChnSpellNotBetween(String value1, String value2) {
            addCriterion("CHN_SPELL not between", value1, value2, "chnSpell");
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

        public Criteria andStatusEqualTo(String value) {
            addCriterion("STATUS =", value, "status");
            return this;
        }

        public Criteria andStatusNotEqualTo(String value) {
            addCriterion("STATUS <>", value, "status");
            return this;
        }

        public Criteria andStatusGreaterThan(String value) {
            addCriterion("STATUS >", value, "status");
            return this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(String value) {
            addCriterion("STATUS >=", value, "status");
            return this;
        }

        public Criteria andStatusLessThan(String value) {
            addCriterion("STATUS <", value, "status");
            return this;
        }

        public Criteria andStatusLessThanOrEqualTo(String value) {
            addCriterion("STATUS <=", value, "status");
            return this;
        }

        public Criteria andStatusLike(String value) {
            addCriterion("STATUS like", value, "status");
            return this;
        }

        public Criteria andStatusNotLike(String value) {
            addCriterion("STATUS not like", value, "status");
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

        public Criteria andStatusBetween(String value1, String value2) {
            addCriterion("STATUS between", value1, value2, "status");
            return this;
        }

        public Criteria andStatusNotBetween(String value1, String value2) {
            addCriterion("STATUS not between", value1, value2, "status");
            return this;
        }

        public Criteria andShopCodeIsNull() {
            addCriterion("SHOP_CODE is null");
            return this;
        }

        public Criteria andShopCodeIsNotNull() {
            addCriterion("SHOP_CODE is not null");
            return this;
        }

        public Criteria andShopCodeEqualTo(String value) {
            addCriterion("SHOP_CODE =", value, "shopCode");
            return this;
        }

        public Criteria andShopCodeNotEqualTo(String value) {
            addCriterion("SHOP_CODE <>", value, "shopCode");
            return this;
        }

        public Criteria andShopCodeGreaterThan(String value) {
            addCriterion("SHOP_CODE >", value, "shopCode");
            return this;
        }

        public Criteria andShopCodeGreaterThanOrEqualTo(String value) {
            addCriterion("SHOP_CODE >=", value, "shopCode");
            return this;
        }

        public Criteria andShopCodeLessThan(String value) {
            addCriterion("SHOP_CODE <", value, "shopCode");
            return this;
        }

        public Criteria andShopCodeLessThanOrEqualTo(String value) {
            addCriterion("SHOP_CODE <=", value, "shopCode");
            return this;
        }

        public Criteria andShopCodeLike(String value) {
            addCriterion("SHOP_CODE like", value, "shopCode");
            return this;
        }

        public Criteria andShopCodeNotLike(String value) {
            addCriterion("SHOP_CODE not like", value, "shopCode");
            return this;
        }

        public Criteria andShopCodeIn(List values) {
            addCriterion("SHOP_CODE in", values, "shopCode");
            return this;
        }

        public Criteria andShopCodeNotIn(List values) {
            addCriterion("SHOP_CODE not in", values, "shopCode");
            return this;
        }

        public Criteria andShopCodeBetween(String value1, String value2) {
            addCriterion("SHOP_CODE between", value1, value2, "shopCode");
            return this;
        }

        public Criteria andShopCodeNotBetween(String value1, String value2) {
            addCriterion("SHOP_CODE not between", value1, value2, "shopCode");
            return this;
        }

        public Criteria andShopCodeInSuppliersInfoLike(String value) {
            addShopCriterion("SHOP_CODE in (select to_char(a.supplier_id) from suppliers_info a,commercial_tenant_basic_info b" +
                    " where a.merchant_id=b.n_commercial_tenant_id and b.corporate_name like ", " )", value, "shopCode");
            return this;
        }

        public Criteria andCreateUserIsNull() {
            addCriterion("CREATE_USER is null");
            return this;
        }

        public Criteria andCreateUserIsNotNull() {
            addCriterion("CREATE_USER is not null");
            return this;
        }

        public Criteria andCreateUserEqualTo(Long value) {
            addCriterion("CREATE_USER =", value, "createUser");
            return this;
        }

        public Criteria andCreateUserNotEqualTo(Long value) {
            addCriterion("CREATE_USER <>", value, "createUser");
            return this;
        }

        public Criteria andCreateUserGreaterThan(Long value) {
            addCriterion("CREATE_USER >", value, "createUser");
            return this;
        }

        public Criteria andCreateUserGreaterThanOrEqualTo(Long value) {
            addCriterion("CREATE_USER >=", value, "createUser");
            return this;
        }

        public Criteria andCreateUserLessThan(Long value) {
            addCriterion("CREATE_USER <", value, "createUser");
            return this;
        }

        public Criteria andCreateUserLessThanOrEqualTo(Long value) {
            addCriterion("CREATE_USER <=", value, "createUser");
            return this;
        }

        public Criteria andCreateUserIn(List values) {
            addCriterion("CREATE_USER in", values, "createUser");
            return this;
        }

        public Criteria andCreateUserNotIn(List values) {
            addCriterion("CREATE_USER not in", values, "createUser");
            return this;
        }

        public Criteria andCreateUserBetween(Long value1,
                                             Long value2) {
            addCriterion("CREATE_USER between", value1, value2, "createUser");
            return this;
        }

        public Criteria andCreateUserNotBetween(Long value1,
                                                Long value2) {
            addCriterion("CREATE_USER not between", value1, value2,
                    "createUser");
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

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("CREATE_TIME between", value1, value2,
                    "createTime");
            return this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("CREATE_TIME not between", value1, value2,
                    "createTime");
            return this;
        }

        public Criteria andCheckUserIsNull() {
            addCriterion("CHECK_USER is null");
            return this;
        }

        public Criteria andCheckUserIsNotNull() {
            addCriterion("CHECK_USER is not null");
            return this;
        }

        public Criteria andCheckUserEqualTo(Long value) {
            addCriterion("CHECK_USER =", value, "checkUser");
            return this;
        }

        public Criteria andCheckUserNotEqualTo(Long value) {
            addCriterion("CHECK_USER <>", value, "checkUser");
            return this;
        }

        public Criteria andCheckUserGreaterThan(Long value) {
            addCriterion("CHECK_USER >", value, "checkUser");
            return this;
        }

        public Criteria andCheckUserGreaterThanOrEqualTo(Long value) {
            addCriterion("CHECK_USER >=", value, "checkUser");
            return this;
        }

        public Criteria andCheckUserLessThan(Long value) {
            addCriterion("CHECK_USER <", value, "checkUser");
            return this;
        }

        public Criteria andCheckUserLessThanOrEqualTo(Long value) {
            addCriterion("CHECK_USER <=", value, "checkUser");
            return this;
        }

        public Criteria andCheckUserIn(List values) {
            addCriterion("CHECK_USER in", values, "checkUser");
            return this;
        }

        public Criteria andCheckUserNotIn(List values) {
            addCriterion("CHECK_USER not in", values, "checkUser");
            return this;
        }

        public Criteria andCheckUserBetween(Long value1, Long value2) {
            addCriterion("CHECK_USER between", value1, value2, "checkUser");
            return this;
        }

        public Criteria andCheckUserNotBetween(Long value1,
                                               Long value2) {
            addCriterion("CHECK_USER not between", value1, value2, "checkUser");
            return this;
        }

        public Criteria andCheckTimeIsNull() {
            addCriterion("CHECK_TIME is null");
            return this;
        }

        public Criteria andCheckTimeIsNotNull() {
            addCriterion("CHECK_TIME is not null");
            return this;
        }

        public Criteria andCheckTimeEqualTo(Date value) {
            addCriterionForJDBCDate("CHECK_TIME =", value, "checkTime");
            return this;
        }

        public Criteria andCheckTimeNotEqualTo(Date value) {
            addCriterionForJDBCDate("CHECK_TIME <>", value, "checkTime");
            return this;
        }

        public Criteria andCheckTimeGreaterThan(Date value) {
            addCriterionForJDBCDate("CHECK_TIME >", value, "checkTime");
            return this;
        }

        public Criteria andCheckTimeGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("CHECK_TIME >=", value, "checkTime");
            return this;
        }

        public Criteria andCheckTimeLessThan(Date value) {
            addCriterionForJDBCDate("CHECK_TIME <", value, "checkTime");
            return this;
        }

        public Criteria andCheckTimeLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("CHECK_TIME <=", value, "checkTime");
            return this;
        }

        public Criteria andCheckTimeBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("CHECK_TIME between", value1, value2,
                    "checkTime");
            return this;
        }

        public Criteria andCheckTimeNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("CHECK_TIME not between", value1, value2,
                    "checkTime");
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

        public Criteria andModifUserNotBetween(Long value1,
                                               Long value2) {
            addCriterion("MODIF_USER not between", value1, value2, "modifUser");
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

        public Criteria andModifTimeBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("MODIF_TIME between", value1, value2,
                    "modifTime");
            return this;
        }

        public Criteria andModifTimeNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("MODIF_TIME not between", value1, value2,
                    "modifTime");
            return this;
        }

        public Criteria andApplyTimeIsNull() {
            addCriterion("APPLY_TIME is null");
            return this;
        }

        public Criteria andApplyTimeIsNotNull() {
            addCriterion("APPLY_TIME is not null");
            return this;
        }

        public Criteria andApplyTimeEqualTo(Date value) {
            addCriterionForJDBCDate("APPLY_TIME =", value, "applyTime");
            return this;
        }

        public Criteria andApplyTimeNotEqualTo(Date value) {
            addCriterionForJDBCDate("APPLY_TIME <>", value, "applyTime");
            return this;
        }

        public Criteria andApplyTimeGreaterThan(Date value) {
            addCriterionForJDBCDate("APPLY_TIME >", value, "applyTime");
            return this;
        }

        public Criteria andApplyTimeGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("APPLY_TIME >=", value, "applyTime");
            return this;
        }

        public Criteria andApplyTimeLessThan(Date value) {
            addCriterionForJDBCDate("APPLY_TIME <", value, "applyTime");
            return this;
        }

        public Criteria andApplyTimeLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("APPLY_TIME <=", value, "applyTime");
            return this;
        }

        public Criteria andApplyTimeBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("APPLY_TIME between", value1, value2,
                    "applyTime");
            return this;
        }

        public Criteria andApplyTimeNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("APPLY_TIME not between", value1, value2,
                    "applyTime");
            return this;
        }

    }
}