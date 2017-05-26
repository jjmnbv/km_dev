package com.pltfm.cms.vobject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CmsPromotionAttrExample {
    /**
     * This field was generated by Abator for iBATIS.
     * This field corresponds to the database table CMS_PROMOTION_ATTR
     *
     * @abatorgenerated Mon Oct 14 09:04:48 CST 2013
     */
    protected String orderByClause;

    /**
     * This field was generated by Abator for iBATIS.
     * This field corresponds to the database table CMS_PROMOTION_ATTR
     *
     * @abatorgenerated Mon Oct 14 09:04:48 CST 2013
     */
    protected List oredCriteria;

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table CMS_PROMOTION_ATTR
     *
     * @abatorgenerated Mon Oct 14 09:04:48 CST 2013
     */
    public CmsPromotionAttrExample() {
        oredCriteria = new ArrayList();
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table CMS_PROMOTION_ATTR
     *
     * @abatorgenerated Mon Oct 14 09:04:48 CST 2013
     */
    protected CmsPromotionAttrExample(CmsPromotionAttrExample example) {
        this.orderByClause = example.orderByClause;
        this.oredCriteria = example.oredCriteria;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table CMS_PROMOTION_ATTR
     *
     * @abatorgenerated Mon Oct 14 09:04:48 CST 2013
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table CMS_PROMOTION_ATTR
     *
     * @abatorgenerated Mon Oct 14 09:04:48 CST 2013
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table CMS_PROMOTION_ATTR
     *
     * @abatorgenerated Mon Oct 14 09:04:48 CST 2013
     */
    public List getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table CMS_PROMOTION_ATTR
     *
     * @abatorgenerated Mon Oct 14 09:04:48 CST 2013
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table CMS_PROMOTION_ATTR
     *
     * @abatorgenerated Mon Oct 14 09:04:48 CST 2013
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table CMS_PROMOTION_ATTR
     *
     * @abatorgenerated Mon Oct 14 09:04:48 CST 2013
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table CMS_PROMOTION_ATTR
     *
     * @abatorgenerated Mon Oct 14 09:04:48 CST 2013
     */
    public void clear() {
        oredCriteria.clear();
    }

    /**
     * This class was generated by Abator for iBATIS.
     * This class corresponds to the database table CMS_PROMOTION_ATTR
     *
     * @abatorgenerated Mon Oct 14 09:04:48 CST 2013
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
            return criteriaWithoutValue.size() > 0
                    || criteriaWithSingleValue.size() > 0
                    || criteriaWithListValue.size() > 0
                    || criteriaWithBetweenValue.size() > 0;
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

        public Criteria andPromotionAttrIdIsNull() {
            addCriterion("PROMOTION_ATTR_ID is null");
            return this;
        }

        public Criteria andPromotionAttrIdIsNotNull() {
            addCriterion("PROMOTION_ATTR_ID is not null");
            return this;
        }

        public Criteria andPromotionAttrIdEqualTo(BigDecimal value) {
            addCriterion("PROMOTION_ATTR_ID =", value, "promotionAttrId");
            return this;
        }

        public Criteria andPromotionAttrIdNotEqualTo(BigDecimal value) {
            addCriterion("PROMOTION_ATTR_ID <>", value, "promotionAttrId");
            return this;
        }

        public Criteria andPromotionAttrIdGreaterThan(BigDecimal value) {
            addCriterion("PROMOTION_ATTR_ID >", value, "promotionAttrId");
            return this;
        }

        public Criteria andPromotionAttrIdGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("PROMOTION_ATTR_ID >=", value, "promotionAttrId");
            return this;
        }

        public Criteria andPromotionAttrIdLessThan(BigDecimal value) {
            addCriterion("PROMOTION_ATTR_ID <", value, "promotionAttrId");
            return this;
        }

        public Criteria andPromotionAttrIdLessThanOrEqualTo(BigDecimal value) {
            addCriterion("PROMOTION_ATTR_ID <=", value, "promotionAttrId");
            return this;
        }

        public Criteria andPromotionAttrIdIn(List values) {
            addCriterion("PROMOTION_ATTR_ID in", values, "promotionAttrId");
            return this;
        }

        public Criteria andPromotionAttrIdNotIn(List values) {
            addCriterion("PROMOTION_ATTR_ID not in", values, "promotionAttrId");
            return this;
        }

        public Criteria andPromotionAttrIdBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("PROMOTION_ATTR_ID between", value1, value2, "promotionAttrId");
            return this;
        }

        public Criteria andPromotionAttrIdNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("PROMOTION_ATTR_ID not between", value1, value2, "promotionAttrId");
            return this;
        }

        public Criteria andPromotionIdIsNull() {
            addCriterion("PROMOTION_ID is null");
            return this;
        }

        public Criteria andPromotionIdIsNotNull() {
            addCriterion("PROMOTION_ID is not null");
            return this;
        }

        public Criteria andPromotionIdEqualTo(BigDecimal value) {
            addCriterion("PROMOTION_ID =", value, "promotionId");
            return this;
        }

        public Criteria andPromotionIdNotEqualTo(BigDecimal value) {
            addCriterion("PROMOTION_ID <>", value, "promotionId");
            return this;
        }

        public Criteria andPromotionIdGreaterThan(BigDecimal value) {
            addCriterion("PROMOTION_ID >", value, "promotionId");
            return this;
        }

        public Criteria andPromotionIdGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("PROMOTION_ID >=", value, "promotionId");
            return this;
        }

        public Criteria andPromotionIdLessThan(BigDecimal value) {
            addCriterion("PROMOTION_ID <", value, "promotionId");
            return this;
        }

        public Criteria andPromotionIdLessThanOrEqualTo(BigDecimal value) {
            addCriterion("PROMOTION_ID <=", value, "promotionId");
            return this;
        }

        public Criteria andPromotionIdIn(List values) {
            addCriterion("PROMOTION_ID in", values, "promotionId");
            return this;
        }

        public Criteria andPromotionIdNotIn(List values) {
            addCriterion("PROMOTION_ID not in", values, "promotionId");
            return this;
        }

        public Criteria andPromotionIdBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("PROMOTION_ID between", value1, value2, "promotionId");
            return this;
        }

        public Criteria andPromotionIdNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("PROMOTION_ID not between", value1, value2, "promotionId");
            return this;
        }

        public Criteria andPromotionImgFirstIsNull() {
            addCriterion("PROMOTION_IMG_FIRST is null");
            return this;
        }

        public Criteria andPromotionImgFirstIsNotNull() {
            addCriterion("PROMOTION_IMG_FIRST is not null");
            return this;
        }

        public Criteria andPromotionImgFirstEqualTo(String value) {
            addCriterion("PROMOTION_IMG_FIRST =", value, "promotionImgFirst");
            return this;
        }

        public Criteria andPromotionImgFirstNotEqualTo(String value) {
            addCriterion("PROMOTION_IMG_FIRST <>", value, "promotionImgFirst");
            return this;
        }

        public Criteria andPromotionImgFirstGreaterThan(String value) {
            addCriterion("PROMOTION_IMG_FIRST >", value, "promotionImgFirst");
            return this;
        }

        public Criteria andPromotionImgFirstGreaterThanOrEqualTo(String value) {
            addCriterion("PROMOTION_IMG_FIRST >=", value, "promotionImgFirst");
            return this;
        }

        public Criteria andPromotionImgFirstLessThan(String value) {
            addCriterion("PROMOTION_IMG_FIRST <", value, "promotionImgFirst");
            return this;
        }

        public Criteria andPromotionImgFirstLessThanOrEqualTo(String value) {
            addCriterion("PROMOTION_IMG_FIRST <=", value, "promotionImgFirst");
            return this;
        }

        public Criteria andPromotionImgFirstLike(String value) {
            addCriterion("PROMOTION_IMG_FIRST like", value, "promotionImgFirst");
            return this;
        }

        public Criteria andPromotionImgFirstNotLike(String value) {
            addCriterion("PROMOTION_IMG_FIRST not like", value, "promotionImgFirst");
            return this;
        }

        public Criteria andPromotionImgFirstIn(List values) {
            addCriterion("PROMOTION_IMG_FIRST in", values, "promotionImgFirst");
            return this;
        }

        public Criteria andPromotionImgFirstNotIn(List values) {
            addCriterion("PROMOTION_IMG_FIRST not in", values, "promotionImgFirst");
            return this;
        }

        public Criteria andPromotionImgFirstBetween(String value1, String value2) {
            addCriterion("PROMOTION_IMG_FIRST between", value1, value2, "promotionImgFirst");
            return this;
        }

        public Criteria andPromotionImgFirstNotBetween(String value1, String value2) {
            addCriterion("PROMOTION_IMG_FIRST not between", value1, value2, "promotionImgFirst");
            return this;
        }

        public Criteria andPromotionImgSecondIsNull() {
            addCriterion("PROMOTION_IMG_SECOND is null");
            return this;
        }

        public Criteria andPromotionImgSecondIsNotNull() {
            addCriterion("PROMOTION_IMG_SECOND is not null");
            return this;
        }

        public Criteria andPromotionImgSecondEqualTo(String value) {
            addCriterion("PROMOTION_IMG_SECOND =", value, "promotionImgSecond");
            return this;
        }

        public Criteria andPromotionImgSecondNotEqualTo(String value) {
            addCriterion("PROMOTION_IMG_SECOND <>", value, "promotionImgSecond");
            return this;
        }

        public Criteria andPromotionImgSecondGreaterThan(String value) {
            addCriterion("PROMOTION_IMG_SECOND >", value, "promotionImgSecond");
            return this;
        }

        public Criteria andPromotionImgSecondGreaterThanOrEqualTo(String value) {
            addCriterion("PROMOTION_IMG_SECOND >=", value, "promotionImgSecond");
            return this;
        }

        public Criteria andPromotionImgSecondLessThan(String value) {
            addCriterion("PROMOTION_IMG_SECOND <", value, "promotionImgSecond");
            return this;
        }

        public Criteria andPromotionImgSecondLessThanOrEqualTo(String value) {
            addCriterion("PROMOTION_IMG_SECOND <=", value, "promotionImgSecond");
            return this;
        }

        public Criteria andPromotionImgSecondLike(String value) {
            addCriterion("PROMOTION_IMG_SECOND like", value, "promotionImgSecond");
            return this;
        }

        public Criteria andPromotionImgSecondNotLike(String value) {
            addCriterion("PROMOTION_IMG_SECOND not like", value, "promotionImgSecond");
            return this;
        }

        public Criteria andPromotionImgSecondIn(List values) {
            addCriterion("PROMOTION_IMG_SECOND in", values, "promotionImgSecond");
            return this;
        }

        public Criteria andPromotionImgSecondNotIn(List values) {
            addCriterion("PROMOTION_IMG_SECOND not in", values, "promotionImgSecond");
            return this;
        }

        public Criteria andPromotionImgSecondBetween(String value1, String value2) {
            addCriterion("PROMOTION_IMG_SECOND between", value1, value2, "promotionImgSecond");
            return this;
        }

        public Criteria andPromotionImgSecondNotBetween(String value1, String value2) {
            addCriterion("PROMOTION_IMG_SECOND not between", value1, value2, "promotionImgSecond");
            return this;
        }

        public Criteria andPromotionUrlIsNull() {
            addCriterion("PROMOTION_URL is null");
            return this;
        }

        public Criteria andPromotionUrlIsNotNull() {
            addCriterion("PROMOTION_URL is not null");
            return this;
        }

        public Criteria andPromotionUrlEqualTo(String value) {
            addCriterion("PROMOTION_URL =", value, "promotionUrl");
            return this;
        }

        public Criteria andPromotionUrlNotEqualTo(String value) {
            addCriterion("PROMOTION_URL <>", value, "promotionUrl");
            return this;
        }

        public Criteria andPromotionUrlGreaterThan(String value) {
            addCriterion("PROMOTION_URL >", value, "promotionUrl");
            return this;
        }

        public Criteria andPromotionUrlGreaterThanOrEqualTo(String value) {
            addCriterion("PROMOTION_URL >=", value, "promotionUrl");
            return this;
        }

        public Criteria andPromotionUrlLessThan(String value) {
            addCriterion("PROMOTION_URL <", value, "promotionUrl");
            return this;
        }

        public Criteria andPromotionUrlLessThanOrEqualTo(String value) {
            addCriterion("PROMOTION_URL <=", value, "promotionUrl");
            return this;
        }

        public Criteria andPromotionUrlLike(String value) {
            addCriterion("PROMOTION_URL like", value, "promotionUrl");
            return this;
        }

        public Criteria andPromotionUrlNotLike(String value) {
            addCriterion("PROMOTION_URL not like", value, "promotionUrl");
            return this;
        }

        public Criteria andPromotionUrlIn(List values) {
            addCriterion("PROMOTION_URL in", values, "promotionUrl");
            return this;
        }

        public Criteria andPromotionUrlNotIn(List values) {
            addCriterion("PROMOTION_URL not in", values, "promotionUrl");
            return this;
        }

        public Criteria andPromotionUrlBetween(String value1, String value2) {
            addCriterion("PROMOTION_URL between", value1, value2, "promotionUrl");
            return this;
        }

        public Criteria andPromotionUrlNotBetween(String value1, String value2) {
            addCriterion("PROMOTION_URL not between", value1, value2, "promotionUrl");
            return this;
        }

        public Criteria andPromotionTinyIconIsNull() {
            addCriterion("PROMOTION_TINY_ICON is null");
            return this;
        }

        public Criteria andPromotionTinyIconIsNotNull() {
            addCriterion("PROMOTION_TINY_ICON is not null");
            return this;
        }

        public Criteria andPromotionTinyIconEqualTo(String value) {
            addCriterion("PROMOTION_TINY_ICON =", value, "promotionTinyIcon");
            return this;
        }

        public Criteria andPromotionTinyIconNotEqualTo(String value) {
            addCriterion("PROMOTION_TINY_ICON <>", value, "promotionTinyIcon");
            return this;
        }

        public Criteria andPromotionTinyIconGreaterThan(String value) {
            addCriterion("PROMOTION_TINY_ICON >", value, "promotionTinyIcon");
            return this;
        }

        public Criteria andPromotionTinyIconGreaterThanOrEqualTo(String value) {
            addCriterion("PROMOTION_TINY_ICON >=", value, "promotionTinyIcon");
            return this;
        }

        public Criteria andPromotionTinyIconLessThan(String value) {
            addCriterion("PROMOTION_TINY_ICON <", value, "promotionTinyIcon");
            return this;
        }

        public Criteria andPromotionTinyIconLessThanOrEqualTo(String value) {
            addCriterion("PROMOTION_TINY_ICON <=", value, "promotionTinyIcon");
            return this;
        }

        public Criteria andPromotionTinyIconLike(String value) {
            addCriterion("PROMOTION_TINY_ICON like", value, "promotionTinyIcon");
            return this;
        }

        public Criteria andPromotionTinyIconNotLike(String value) {
            addCriterion("PROMOTION_TINY_ICON not like", value, "promotionTinyIcon");
            return this;
        }

        public Criteria andPromotionTinyIconIn(List values) {
            addCriterion("PROMOTION_TINY_ICON in", values, "promotionTinyIcon");
            return this;
        }

        public Criteria andPromotionTinyIconNotIn(List values) {
            addCriterion("PROMOTION_TINY_ICON not in", values, "promotionTinyIcon");
            return this;
        }

        public Criteria andPromotionTinyIconBetween(String value1, String value2) {
            addCriterion("PROMOTION_TINY_ICON between", value1, value2, "promotionTinyIcon");
            return this;
        }

        public Criteria andPromotionTinyIconNotBetween(String value1, String value2) {
            addCriterion("PROMOTION_TINY_ICON not between", value1, value2, "promotionTinyIcon");
            return this;
        }

        public Criteria andPromotionBigIconIsNull() {
            addCriterion("PROMOTION_BIG_ICON is null");
            return this;
        }

        public Criteria andPromotionBigIconIsNotNull() {
            addCriterion("PROMOTION_BIG_ICON is not null");
            return this;
        }

        public Criteria andPromotionBigIconEqualTo(String value) {
            addCriterion("PROMOTION_BIG_ICON =", value, "promotionBigIcon");
            return this;
        }

        public Criteria andPromotionBigIconNotEqualTo(String value) {
            addCriterion("PROMOTION_BIG_ICON <>", value, "promotionBigIcon");
            return this;
        }

        public Criteria andPromotionBigIconGreaterThan(String value) {
            addCriterion("PROMOTION_BIG_ICON >", value, "promotionBigIcon");
            return this;
        }

        public Criteria andPromotionBigIconGreaterThanOrEqualTo(String value) {
            addCriterion("PROMOTION_BIG_ICON >=", value, "promotionBigIcon");
            return this;
        }

        public Criteria andPromotionBigIconLessThan(String value) {
            addCriterion("PROMOTION_BIG_ICON <", value, "promotionBigIcon");
            return this;
        }

        public Criteria andPromotionBigIconLessThanOrEqualTo(String value) {
            addCriterion("PROMOTION_BIG_ICON <=", value, "promotionBigIcon");
            return this;
        }

        public Criteria andPromotionBigIconLike(String value) {
            addCriterion("PROMOTION_BIG_ICON like", value, "promotionBigIcon");
            return this;
        }

        public Criteria andPromotionBigIconNotLike(String value) {
            addCriterion("PROMOTION_BIG_ICON not like", value, "promotionBigIcon");
            return this;
        }

        public Criteria andPromotionBigIconIn(List values) {
            addCriterion("PROMOTION_BIG_ICON in", values, "promotionBigIcon");
            return this;
        }

        public Criteria andPromotionBigIconNotIn(List values) {
            addCriterion("PROMOTION_BIG_ICON not in", values, "promotionBigIcon");
            return this;
        }

        public Criteria andPromotionBigIconBetween(String value1, String value2) {
            addCriterion("PROMOTION_BIG_ICON between", value1, value2, "promotionBigIcon");
            return this;
        }

        public Criteria andPromotionBigIconNotBetween(String value1, String value2) {
            addCriterion("PROMOTION_BIG_ICON not between", value1, value2, "promotionBigIcon");
            return this;
        }

        public Criteria andPromotionTagIsNull() {
            addCriterion("PROMOTION_TAG is null");
            return this;
        }

        public Criteria andPromotionTagIsNotNull() {
            addCriterion("PROMOTION_TAG is not null");
            return this;
        }

        public Criteria andPromotionTagEqualTo(String value) {
            addCriterion("PROMOTION_TAG =", value, "promotionTag");
            return this;
        }

        public Criteria andPromotionTagNotEqualTo(String value) {
            addCriterion("PROMOTION_TAG <>", value, "promotionTag");
            return this;
        }

        public Criteria andPromotionTagGreaterThan(String value) {
            addCriterion("PROMOTION_TAG >", value, "promotionTag");
            return this;
        }

        public Criteria andPromotionTagGreaterThanOrEqualTo(String value) {
            addCriterion("PROMOTION_TAG >=", value, "promotionTag");
            return this;
        }

        public Criteria andPromotionTagLessThan(String value) {
            addCriterion("PROMOTION_TAG <", value, "promotionTag");
            return this;
        }

        public Criteria andPromotionTagLessThanOrEqualTo(String value) {
            addCriterion("PROMOTION_TAG <=", value, "promotionTag");
            return this;
        }

        public Criteria andPromotionTagLike(String value) {
            addCriterion("PROMOTION_TAG like", value, "promotionTag");
            return this;
        }

        public Criteria andPromotionTagNotLike(String value) {
            addCriterion("PROMOTION_TAG not like", value, "promotionTag");
            return this;
        }

        public Criteria andPromotionTagIn(List values) {
            addCriterion("PROMOTION_TAG in", values, "promotionTag");
            return this;
        }

        public Criteria andPromotionTagNotIn(List values) {
            addCriterion("PROMOTION_TAG not in", values, "promotionTag");
            return this;
        }

        public Criteria andPromotionTagBetween(String value1, String value2) {
            addCriterion("PROMOTION_TAG between", value1, value2, "promotionTag");
            return this;
        }

        public Criteria andPromotionTagNotBetween(String value1, String value2) {
            addCriterion("PROMOTION_TAG not between", value1, value2, "promotionTag");
            return this;
        }

        public Criteria andPromotionRemark1IsNull() {
            addCriterion("PROMOTION_REMARK1 is null");
            return this;
        }

        public Criteria andPromotionRemark1IsNotNull() {
            addCriterion("PROMOTION_REMARK1 is not null");
            return this;
        }

        public Criteria andPromotionRemark1EqualTo(String value) {
            addCriterion("PROMOTION_REMARK1 =", value, "promotionRemark1");
            return this;
        }

        public Criteria andPromotionRemark1NotEqualTo(String value) {
            addCriterion("PROMOTION_REMARK1 <>", value, "promotionRemark1");
            return this;
        }

        public Criteria andPromotionRemark1GreaterThan(String value) {
            addCriterion("PROMOTION_REMARK1 >", value, "promotionRemark1");
            return this;
        }

        public Criteria andPromotionRemark1GreaterThanOrEqualTo(String value) {
            addCriterion("PROMOTION_REMARK1 >=", value, "promotionRemark1");
            return this;
        }

        public Criteria andPromotionRemark1LessThan(String value) {
            addCriterion("PROMOTION_REMARK1 <", value, "promotionRemark1");
            return this;
        }

        public Criteria andPromotionRemark1LessThanOrEqualTo(String value) {
            addCriterion("PROMOTION_REMARK1 <=", value, "promotionRemark1");
            return this;
        }

        public Criteria andPromotionRemark1Like(String value) {
            addCriterion("PROMOTION_REMARK1 like", value, "promotionRemark1");
            return this;
        }

        public Criteria andPromotionRemark1NotLike(String value) {
            addCriterion("PROMOTION_REMARK1 not like", value, "promotionRemark1");
            return this;
        }

        public Criteria andPromotionRemark1In(List values) {
            addCriterion("PROMOTION_REMARK1 in", values, "promotionRemark1");
            return this;
        }

        public Criteria andPromotionRemark1NotIn(List values) {
            addCriterion("PROMOTION_REMARK1 not in", values, "promotionRemark1");
            return this;
        }

        public Criteria andPromotionRemark1Between(String value1, String value2) {
            addCriterion("PROMOTION_REMARK1 between", value1, value2, "promotionRemark1");
            return this;
        }

        public Criteria andPromotionRemark1NotBetween(String value1, String value2) {
            addCriterion("PROMOTION_REMARK1 not between", value1, value2, "promotionRemark1");
            return this;
        }

        public Criteria andPromotionRemark2IsNull() {
            addCriterion("PROMOTION_REMARK2 is null");
            return this;
        }

        public Criteria andPromotionRemark2IsNotNull() {
            addCriterion("PROMOTION_REMARK2 is not null");
            return this;
        }

        public Criteria andPromotionRemark2EqualTo(String value) {
            addCriterion("PROMOTION_REMARK2 =", value, "promotionRemark2");
            return this;
        }

        public Criteria andPromotionRemark2NotEqualTo(String value) {
            addCriterion("PROMOTION_REMARK2 <>", value, "promotionRemark2");
            return this;
        }

        public Criteria andPromotionRemark2GreaterThan(String value) {
            addCriterion("PROMOTION_REMARK2 >", value, "promotionRemark2");
            return this;
        }

        public Criteria andPromotionRemark2GreaterThanOrEqualTo(String value) {
            addCriterion("PROMOTION_REMARK2 >=", value, "promotionRemark2");
            return this;
        }

        public Criteria andPromotionRemark2LessThan(String value) {
            addCriterion("PROMOTION_REMARK2 <", value, "promotionRemark2");
            return this;
        }

        public Criteria andPromotionRemark2LessThanOrEqualTo(String value) {
            addCriterion("PROMOTION_REMARK2 <=", value, "promotionRemark2");
            return this;
        }

        public Criteria andPromotionRemark2Like(String value) {
            addCriterion("PROMOTION_REMARK2 like", value, "promotionRemark2");
            return this;
        }

        public Criteria andPromotionRemark2NotLike(String value) {
            addCriterion("PROMOTION_REMARK2 not like", value, "promotionRemark2");
            return this;
        }

        public Criteria andPromotionRemark2In(List values) {
            addCriterion("PROMOTION_REMARK2 in", values, "promotionRemark2");
            return this;
        }

        public Criteria andPromotionRemark2NotIn(List values) {
            addCriterion("PROMOTION_REMARK2 not in", values, "promotionRemark2");
            return this;
        }

        public Criteria andPromotionRemark2Between(String value1, String value2) {
            addCriterion("PROMOTION_REMARK2 between", value1, value2, "promotionRemark2");
            return this;
        }

        public Criteria andPromotionRemark2NotBetween(String value1, String value2) {
            addCriterion("PROMOTION_REMARK2 not between", value1, value2, "promotionRemark2");
            return this;
        }
    }
}