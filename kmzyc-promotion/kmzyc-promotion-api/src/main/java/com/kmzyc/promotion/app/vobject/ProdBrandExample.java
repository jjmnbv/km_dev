package com.kmzyc.promotion.app.vobject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unchecked")
public class ProdBrandExample {

	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds
	 * to the database table PROD_BRAND
	 * 
	 * @ibatorgenerated Mon Jul 15 09:14:02 CST 2013
	 */
	protected String orderByClause;
	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds
	 * to the database table PROD_BRAND
	 * 
	 * @ibatorgenerated Mon Jul 15 09:14:02 CST 2013
	 */
	protected List oredCriteria;

	/**
	 * This method was generated by Apache iBATIS ibator. This method
	 * corresponds to the database table PROD_BRAND
	 * 
	 * @ibatorgenerated Mon Jul 15 09:14:02 CST 2013
	 */
	public ProdBrandExample() {
		oredCriteria = new ArrayList();
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method
	 * corresponds to the database table PROD_BRAND
	 * 
	 * @ibatorgenerated Mon Jul 15 09:14:02 CST 2013
	 */
	protected ProdBrandExample(ProdBrandExample example) {
		this.orderByClause = example.orderByClause;
		this.oredCriteria = example.oredCriteria;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method
	 * corresponds to the database table PROD_BRAND
	 * 
	 * @ibatorgenerated Mon Jul 15 09:14:02 CST 2013
	 */
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method
	 * corresponds to the database table PROD_BRAND
	 * 
	 * @ibatorgenerated Mon Jul 15 09:14:02 CST 2013
	 */
	public String getOrderByClause() {
		return orderByClause;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method
	 * corresponds to the database table PROD_BRAND
	 * 
	 * @ibatorgenerated Mon Jul 15 09:14:02 CST 2013
	 */
	public List getOredCriteria() {
		return oredCriteria;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method
	 * corresponds to the database table PROD_BRAND
	 * 
	 * @ibatorgenerated Mon Jul 15 09:14:02 CST 2013
	 */
	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method
	 * corresponds to the database table PROD_BRAND
	 * 
	 * @ibatorgenerated Mon Jul 15 09:14:02 CST 2013
	 */
	public Criteria createCriteria() {
		Criteria criteria = createCriteriaInternal();
		if (oredCriteria.size() == 0) {
			oredCriteria.add(criteria);
		}
		return criteria;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method
	 * corresponds to the database table PROD_BRAND
	 * 
	 * @ibatorgenerated Mon Jul 15 09:14:02 CST 2013
	 */
	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method
	 * corresponds to the database table PROD_BRAND
	 * 
	 * @ibatorgenerated Mon Jul 15 09:14:02 CST 2013
	 */
	public void clear() {
		oredCriteria.clear();
	}

	/**
	 * This class was generated by Apache iBATIS ibator. This class corresponds
	 * to the database table PROD_BRAND
	 * 
	 * @ibatorgenerated Mon Jul 15 09:14:02 CST 2013
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

		public Criteria andBrandIdNotBetween(Long value1, Long value2) {
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

		public Criteria andHomepageIsNull() {
			addCriterion("HOMEPAGE is null");
			return this;
		}

		public Criteria andHomepageIsNotNull() {
			addCriterion("HOMEPAGE is not null");
			return this;
		}

		public Criteria andHomepageEqualTo(String value) {
			addCriterion("HOMEPAGE =", value, "homepage");
			return this;
		}

		public Criteria andHomepageNotEqualTo(String value) {
			addCriterion("HOMEPAGE <>", value, "homepage");
			return this;
		}

		public Criteria andHomepageGreaterThan(String value) {
			addCriterion("HOMEPAGE >", value, "homepage");
			return this;
		}

		public Criteria andHomepageGreaterThanOrEqualTo(String value) {
			addCriterion("HOMEPAGE >=", value, "homepage");
			return this;
		}

		public Criteria andHomepageLessThan(String value) {
			addCriterion("HOMEPAGE <", value, "homepage");
			return this;
		}

		public Criteria andHomepageLessThanOrEqualTo(String value) {
			addCriterion("HOMEPAGE <=", value, "homepage");
			return this;
		}

		public Criteria andHomepageLike(String value) {
			addCriterion("HOMEPAGE like", value, "homepage");
			return this;
		}

		public Criteria andHomepageNotLike(String value) {
			addCriterion("HOMEPAGE not like", value, "homepage");
			return this;
		}

		public Criteria andHomepageIn(List values) {
			addCriterion("HOMEPAGE in", values, "homepage");
			return this;
		}

		public Criteria andHomepageNotIn(List values) {
			addCriterion("HOMEPAGE not in", values, "homepage");
			return this;
		}

		public Criteria andHomepageBetween(String value1, String value2) {
			addCriterion("HOMEPAGE between", value1, value2, "homepage");
			return this;
		}

		public Criteria andHomepageNotBetween(String value1, String value2) {
			addCriterion("HOMEPAGE not between", value1, value2, "homepage");
			return this;
		}

		public Criteria andDesIsNull() {
			addCriterion("DES is null");
			return this;
		}

		public Criteria andDesIsNotNull() {
			addCriterion("DES is not null");
			return this;
		}

		public Criteria andDesEqualTo(String value) {
			addCriterion("DES =", value, "des");
			return this;
		}

		public Criteria andDesNotEqualTo(String value) {
			addCriterion("DES <>", value, "des");
			return this;
		}

		public Criteria andDesGreaterThan(String value) {
			addCriterion("DES >", value, "des");
			return this;
		}

		public Criteria andDesGreaterThanOrEqualTo(String value) {
			addCriterion("DES >=", value, "des");
			return this;
		}

		public Criteria andDesLessThan(String value) {
			addCriterion("DES <", value, "des");
			return this;
		}

		public Criteria andDesLessThanOrEqualTo(String value) {
			addCriterion("DES <=", value, "des");
			return this;
		}

		public Criteria andDesLike(String value) {
			addCriterion("DES like", value, "des");
			return this;
		}

		public Criteria andDesNotLike(String value) {
			addCriterion("DES not like", value, "des");
			return this;
		}

		public Criteria andDesIn(List values) {
			addCriterion("DES in", values, "des");
			return this;
		}

		public Criteria andDesNotIn(List values) {
			addCriterion("DES not in", values, "des");
			return this;
		}

		public Criteria andDesBetween(String value1, String value2) {
			addCriterion("DES between", value1, value2, "des");
			return this;
		}

		public Criteria andDesNotBetween(String value1, String value2) {
			addCriterion("DES not between", value1, value2, "des");
			return this;
		}

		public Criteria andRemarkIsNull() {
			addCriterion("REMARK is null");
			return this;
		}

		public Criteria andRemarkIsNotNull() {
			addCriterion("REMARK is not null");
			return this;
		}

		public Criteria andRemarkEqualTo(String value) {
			addCriterion("REMARK =", value, "remark");
			return this;
		}

		public Criteria andRemarkNotEqualTo(String value) {
			addCriterion("REMARK <>", value, "remark");
			return this;
		}

		public Criteria andRemarkGreaterThan(String value) {
			addCriterion("REMARK >", value, "remark");
			return this;
		}

		public Criteria andRemarkGreaterThanOrEqualTo(String value) {
			addCriterion("REMARK >=", value, "remark");
			return this;
		}

		public Criteria andRemarkLessThan(String value) {
			addCriterion("REMARK <", value, "remark");
			return this;
		}

		public Criteria andRemarkLessThanOrEqualTo(String value) {
			addCriterion("REMARK <=", value, "remark");
			return this;
		}

		public Criteria andRemarkLike(String value) {
			addCriterion("REMARK like", value, "remark");
			return this;
		}

		public Criteria andRemarkNotLike(String value) {
			addCriterion("REMARK not like", value, "remark");
			return this;
		}

		public Criteria andRemarkIn(List values) {
			addCriterion("REMARK in", values, "remark");
			return this;
		}

		public Criteria andRemarkNotIn(List values) {
			addCriterion("REMARK not in", values, "remark");
			return this;
		}

		public Criteria andRemarkBetween(String value1, String value2) {
			addCriterion("REMARK between", value1, value2, "remark");
			return this;
		}

		public Criteria andRemarkNotBetween(String value1, String value2) {
			addCriterion("REMARK not between", value1, value2, "remark");
			return this;
		}

		public Criteria andSpa1IsNull() {
			addCriterion("SPA_1 is null");
			return this;
		}

		public Criteria andSpa1IsNotNull() {
			addCriterion("SPA_1 is not null");
			return this;
		}

		public Criteria andSpa1EqualTo(String value) {
			addCriterion("SPA_1 =", value, "spa1");
			return this;
		}

		public Criteria andSpa1NotEqualTo(String value) {
			addCriterion("SPA_1 <>", value, "spa1");
			return this;
		}

		public Criteria andSpa1GreaterThan(String value) {
			addCriterion("SPA_1 >", value, "spa1");
			return this;
		}

		public Criteria andSpa1GreaterThanOrEqualTo(String value) {
			addCriterion("SPA_1 >=", value, "spa1");
			return this;
		}

		public Criteria andSpa1LessThan(String value) {
			addCriterion("SPA_1 <", value, "spa1");
			return this;
		}

		public Criteria andSpa1LessThanOrEqualTo(String value) {
			addCriterion("SPA_1 <=", value, "spa1");
			return this;
		}

		public Criteria andSpa1Like(String value) {
			addCriterion("SPA_1 like", value, "spa1");
			return this;
		}

		public Criteria andSpa1NotLike(String value) {
			addCriterion("SPA_1 not like", value, "spa1");
			return this;
		}

		public Criteria andSpa1In(List values) {
			addCriterion("SPA_1 in", values, "spa1");
			return this;
		}

		public Criteria andSpa1NotIn(List values) {
			addCriterion("SPA_1 not in", values, "spa1");
			return this;
		}

		public Criteria andSpa1Between(String value1, String value2) {
			addCriterion("SPA_1 between", value1, value2, "spa1");
			return this;
		}

		public Criteria andSpa1NotBetween(String value1, String value2) {
			addCriterion("SPA_1 not between", value1, value2, "spa1");
			return this;
		}

		public Criteria andSpa2IsNull() {
			addCriterion("SPA_2 is null");
			return this;
		}

		public Criteria andSpa2IsNotNull() {
			addCriterion("SPA_2 is not null");
			return this;
		}

		public Criteria andSpa2EqualTo(String value) {
			addCriterion("SPA_2 =", value, "spa2");
			return this;
		}

		public Criteria andSpa2NotEqualTo(String value) {
			addCriterion("SPA_2 <>", value, "spa2");
			return this;
		}

		public Criteria andSpa2GreaterThan(String value) {
			addCriterion("SPA_2 >", value, "spa2");
			return this;
		}

		public Criteria andSpa2GreaterThanOrEqualTo(String value) {
			addCriterion("SPA_2 >=", value, "spa2");
			return this;
		}

		public Criteria andSpa2LessThan(String value) {
			addCriterion("SPA_2 <", value, "spa2");
			return this;
		}

		public Criteria andSpa2LessThanOrEqualTo(String value) {
			addCriterion("SPA_2 <=", value, "spa2");
			return this;
		}

		public Criteria andSpa2Like(String value) {
			addCriterion("SPA_2 like", value, "spa2");
			return this;
		}

		public Criteria andSpa2NotLike(String value) {
			addCriterion("SPA_2 not like", value, "spa2");
			return this;
		}

		public Criteria andSpa2In(List values) {
			addCriterion("SPA_2 in", values, "spa2");
			return this;
		}

		public Criteria andSpa2NotIn(List values) {
			addCriterion("SPA_2 not in", values, "spa2");
			return this;
		}

		public Criteria andSpa2Between(String value1, String value2) {
			addCriterion("SPA_2 between", value1, value2, "spa2");
			return this;
		}

		public Criteria andSpa2NotBetween(String value1, String value2) {
			addCriterion("SPA_2 not between", value1, value2, "spa2");
			return this;
		}

		public Criteria andIsValidIsNull() {
			addCriterion("IS_VALID is null");
			return this;
		}

		public Criteria andIsValidIsNotNull() {
			addCriterion("IS_VALID is not null");
			return this;
		}

		public Criteria andIsValidEqualTo(String value) {
			addCriterion("IS_VALID =", value, "isValid");
			return this;
		}

		public Criteria andIsValidNotEqualTo(String value) {
			addCriterion("IS_VALID <>", value, "isValid");
			return this;
		}

		public Criteria andIsValidGreaterThan(String value) {
			addCriterion("IS_VALID >", value, "isValid");
			return this;
		}

		public Criteria andIsValidGreaterThanOrEqualTo(String value) {
			addCriterion("IS_VALID >=", value, "isValid");
			return this;
		}

		public Criteria andIsValidLessThan(String value) {
			addCriterion("IS_VALID <", value, "isValid");
			return this;
		}

		public Criteria andIsValidLessThanOrEqualTo(String value) {
			addCriterion("IS_VALID <=", value, "isValid");
			return this;
		}

		public Criteria andIsValidLike(String value) {
			addCriterion("IS_VALID like", value, "isValid");
			return this;
		}

		public Criteria andIsValidNotLike(String value) {
			addCriterion("IS_VALID not like", value, "isValid");
			return this;
		}

		public Criteria andIsValidIn(List values) {
			addCriterion("IS_VALID in", values, "isValid");
			return this;
		}

		public Criteria andIsValidNotIn(List values) {
			addCriterion("IS_VALID not in", values, "isValid");
			return this;
		}

		public Criteria andIsValidBetween(String value1, String value2) {
			addCriterion("IS_VALID between", value1, value2, "isValid");
			return this;
		}

		public Criteria andIsValidNotBetween(String value1, String value2) {
			addCriterion("IS_VALID not between", value1, value2, "isValid");
			return this;
		}

		public Criteria andContactInfoIsNull() {
			addCriterion("CONTACT_INFO is null");
			return this;
		}

		public Criteria andContactInfoIsNotNull() {
			addCriterion("CONTACT_INFO is not null");
			return this;
		}

		public Criteria andContactInfoEqualTo(String value) {
			addCriterion("CONTACT_INFO =", value, "contactInfo");
			return this;
		}

		public Criteria andContactInfoNotEqualTo(String value) {
			addCriterion("CONTACT_INFO <>", value, "contactInfo");
			return this;
		}

		public Criteria andContactInfoGreaterThan(String value) {
			addCriterion("CONTACT_INFO >", value, "contactInfo");
			return this;
		}

		public Criteria andContactInfoGreaterThanOrEqualTo(String value) {
			addCriterion("CONTACT_INFO >=", value, "contactInfo");
			return this;
		}

		public Criteria andContactInfoLessThan(String value) {
			addCriterion("CONTACT_INFO <", value, "contactInfo");
			return this;
		}

		public Criteria andContactInfoLessThanOrEqualTo(String value) {
			addCriterion("CONTACT_INFO <=", value, "contactInfo");
			return this;
		}

		public Criteria andContactInfoLike(String value) {
			addCriterion("CONTACT_INFO like", value, "contactInfo");
			return this;
		}

		public Criteria andContactInfoNotLike(String value) {
			addCriterion("CONTACT_INFO not like", value, "contactInfo");
			return this;
		}

		public Criteria andContactInfoIn(List values) {
			addCriterion("CONTACT_INFO in", values, "contactInfo");
			return this;
		}

		public Criteria andContactInfoNotIn(List values) {
			addCriterion("CONTACT_INFO not in", values, "contactInfo");
			return this;
		}

		public Criteria andContactInfoBetween(String value1, String value2) {
			addCriterion("CONTACT_INFO between", value1, value2, "contactInfo");
			return this;
		}

		public Criteria andContactInfoNotBetween(String value1, String value2) {
			addCriterion("CONTACT_INFO not between", value1, value2, "contactInfo");
			return this;
		}

		public Criteria andPavilionPicPathIsNull() {
			addCriterion("PAVILION_PIC_PATH is null");
			return this;
		}

		public Criteria andPavilionPicPathIsNotNull() {
			addCriterion("PAVILION_PIC_PATH is not null");
			return this;
		}

		public Criteria andPavilionPicPathEqualTo(String value) {
			addCriterion("PAVILION_PIC_PATH =", value, "pavilionPicPath");
			return this;
		}

		public Criteria andPavilionPicPathNotEqualTo(String value) {
			addCriterion("PAVILION_PIC_PATH <>", value, "pavilionPicPath");
			return this;
		}

		public Criteria andPavilionPicPathGreaterThan(String value) {
			addCriterion("PAVILION_PIC_PATH >", value, "pavilionPicPath");
			return this;
		}

		public Criteria andPavilionPicPathGreaterThanOrEqualTo(String value) {
			addCriterion("PAVILION_PIC_PATH >=", value, "pavilionPicPath");
			return this;
		}

		public Criteria andPavilionPicPathLessThan(String value) {
			addCriterion("PAVILION_PIC_PATH <", value, "pavilionPicPath");
			return this;
		}

		public Criteria andPavilionPicPathLessThanOrEqualTo(String value) {
			addCriterion("PAVILION_PIC_PATH <=", value, "pavilionPicPath");
			return this;
		}

		public Criteria andPavilionPicPathLike(String value) {
			addCriterion("PAVILION_PIC_PATH like", value, "pavilionPicPath");
			return this;
		}

		public Criteria andPavilionPicPathNotLike(String value) {
			addCriterion("PAVILION_PIC_PATH not like", value, "pavilionPicPath");
			return this;
		}

		public Criteria andPavilionPicPathIn(List values) {
			addCriterion("PAVILION_PIC_PATH in", values, "pavilionPicPath");
			return this;
		}

		public Criteria andPavilionPicPathNotIn(List values) {
			addCriterion("PAVILION_PIC_PATH not in", values, "pavilionPicPath");
			return this;
		}

		public Criteria andPavilionPicPathBetween(String value1, String value2) {
			addCriterion("PAVILION_PIC_PATH between", value1, value2, "pavilionPicPath");
			return this;
		}

		public Criteria andPavilionPicPathNotBetween(String value1, String value2) {
			addCriterion("PAVILION_PIC_PATH not between", value1, value2, "pavilionPicPath");
			return this;
		}

		public Criteria andIntroduceFilePathIsNull() {
			addCriterion("INTRODUCE_FILE_PATH is null");
			return this;
		}

		public Criteria andIntroduceFilePathIsNotNull() {
			addCriterion("INTRODUCE_FILE_PATH is not null");
			return this;
		}

		public Criteria andIntroduceFilePathEqualTo(String value) {
			addCriterion("INTRODUCE_FILE_PATH =", value, "introduceFilePath");
			return this;
		}

		public Criteria andIntroduceFilePathNotEqualTo(String value) {
			addCriterion("INTRODUCE_FILE_PATH <>", value, "introduceFilePath");
			return this;
		}

		public Criteria andIntroduceFilePathGreaterThan(String value) {
			addCriterion("INTRODUCE_FILE_PATH >", value, "introduceFilePath");
			return this;
		}

		public Criteria andIntroduceFilePathGreaterThanOrEqualTo(String value) {
			addCriterion("INTRODUCE_FILE_PATH >=", value, "introduceFilePath");
			return this;
		}

		public Criteria andIntroduceFilePathLessThan(String value) {
			addCriterion("INTRODUCE_FILE_PATH <", value, "introduceFilePath");
			return this;
		}

		public Criteria andIntroduceFilePathLessThanOrEqualTo(String value) {
			addCriterion("INTRODUCE_FILE_PATH <=", value, "introduceFilePath");
			return this;
		}

		public Criteria andIntroduceFilePathLike(String value) {
			addCriterion("INTRODUCE_FILE_PATH like", value, "introduceFilePath");
			return this;
		}

		public Criteria andIntroduceFilePathNotLike(String value) {
			addCriterion("INTRODUCE_FILE_PATH not like", value, "introduceFilePath");
			return this;
		}

		public Criteria andIntroduceFilePathIn(List values) {
			addCriterion("INTRODUCE_FILE_PATH in", values, "introduceFilePath");
			return this;
		}

		public Criteria andIntroduceFilePathNotIn(List values) {
			addCriterion("INTRODUCE_FILE_PATH not in", values, "introduceFilePath");
			return this;
		}

		public Criteria andIntroduceFilePathBetween(String value1, String value2) {
			addCriterion("INTRODUCE_FILE_PATH between", value1, value2, "introduceFilePath");
			return this;
		}

		public Criteria andIntroduceFilePathNotBetween(String value1, String value2) {
			addCriterion("INTRODUCE_FILE_PATH not between", value1, value2, "introduceFilePath");
			return this;
		}

		public Criteria andCertificateHonorIsNull() {
			addCriterion("CERTIFICATE_HONOR is null");
			return this;
		}

		public Criteria andCertificateHonorIsNotNull() {
			addCriterion("CERTIFICATE_HONOR is not null");
			return this;
		}

		public Criteria andCertificateHonorEqualTo(String value) {
			addCriterion("CERTIFICATE_HONOR =", value, "certificateHonor");
			return this;
		}

		public Criteria andCertificateHonorNotEqualTo(String value) {
			addCriterion("CERTIFICATE_HONOR <>", value, "certificateHonor");
			return this;
		}

		public Criteria andCertificateHonorGreaterThan(String value) {
			addCriterion("CERTIFICATE_HONOR >", value, "certificateHonor");
			return this;
		}

		public Criteria andCertificateHonorGreaterThanOrEqualTo(String value) {
			addCriterion("CERTIFICATE_HONOR >=", value, "certificateHonor");
			return this;
		}

		public Criteria andCertificateHonorLessThan(String value) {
			addCriterion("CERTIFICATE_HONOR <", value, "certificateHonor");
			return this;
		}

		public Criteria andCertificateHonorLessThanOrEqualTo(String value) {
			addCriterion("CERTIFICATE_HONOR <=", value, "certificateHonor");
			return this;
		}

		public Criteria andCertificateHonorLike(String value) {
			addCriterion("CERTIFICATE_HONOR like", value, "certificateHonor");
			return this;
		}

		public Criteria andCertificateHonorNotLike(String value) {
			addCriterion("CERTIFICATE_HONOR not like", value, "certificateHonor");
			return this;
		}

		public Criteria andCertificateHonorIn(List values) {
			addCriterion("CERTIFICATE_HONOR in", values, "certificateHonor");
			return this;
		}

		public Criteria andCertificateHonorNotIn(List values) {
			addCriterion("CERTIFICATE_HONOR not in", values, "certificateHonor");
			return this;
		}

		public Criteria andCertificateHonorBetween(String value1, String value2) {
			addCriterion("CERTIFICATE_HONOR between", value1, value2, "certificateHonor");
			return this;
		}

		public Criteria andCertificateHonorNotBetween(String value1, String value2) {
			addCriterion("CERTIFICATE_HONOR not between", value1, value2, "certificateHonor");
			return this;
		}
	}
}