package com.pltfm.app.vobject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ViewProductSkuExample {
	
	/**
	 * 用户id用于做查询条件
	 */
	public String userId;
	
	private Long mCategoryId;
	
	private Long bCategoryId;
	
	private Integer supplierTYpe;
	
	private List shopCode;
	
	//用于模糊查询的品牌名
	private String searchBrandName;
    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database table VIEW_PRODUCT_SKU
     *
     * @ibatorgenerated Thu Aug 15 14:32:43 CST 2013
     */
    protected String orderByClause;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database table VIEW_PRODUCT_SKU
     *
     * @ibatorgenerated Thu Aug 15 14:32:43 CST 2013
     */
    protected List oredCriteria;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table VIEW_PRODUCT_SKU
     *
     * @ibatorgenerated Thu Aug 15 14:32:43 CST 2013
     */
    public ViewProductSkuExample() {
        oredCriteria = new ArrayList();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table VIEW_PRODUCT_SKU
     *
     * @ibatorgenerated Thu Aug 15 14:32:43 CST 2013
     */
    protected ViewProductSkuExample(ViewProductSkuExample example) {
        this.orderByClause = example.orderByClause;
        this.oredCriteria = example.oredCriteria;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table VIEW_PRODUCT_SKU
     *
     * @ibatorgenerated Thu Aug 15 14:32:43 CST 2013
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table VIEW_PRODUCT_SKU
     *
     * @ibatorgenerated Thu Aug 15 14:32:43 CST 2013
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table VIEW_PRODUCT_SKU
     *
     * @ibatorgenerated Thu Aug 15 14:32:43 CST 2013
     */
    public List getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table VIEW_PRODUCT_SKU
     *
     * @ibatorgenerated Thu Aug 15 14:32:43 CST 2013
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table VIEW_PRODUCT_SKU
     *
     * @ibatorgenerated Thu Aug 15 14:32:43 CST 2013
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
     * This method corresponds to the database table VIEW_PRODUCT_SKU
     *
     * @ibatorgenerated Thu Aug 15 14:32:43 CST 2013
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table VIEW_PRODUCT_SKU
     *
     * @ibatorgenerated Thu Aug 15 14:32:43 CST 2013
     */
    public void clear() {
        oredCriteria.clear();
    }

    public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
     * This class was generated by Apache iBATIS ibator.
     * This class corresponds to the database table VIEW_PRODUCT_SKU
     *
     * @ibatorgenerated Thu Aug 15 14:32:43 CST 2013
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

        public Criteria andProductIdIsNull() {
            addCriterion("PRODUCT_ID is null");
            return this;
        }

        public Criteria andProductIdIsNotNull() {
            addCriterion("PRODUCT_ID is not null");
            return this;
        }

        public Criteria andProductIdEqualTo(Long value) {
            addCriterion("PRODUCT_ID =", value, "productId");
            return this;
        }

        public Criteria andProductIdNotEqualTo(BigDecimal value) {
            addCriterion("PRODUCT_ID <>", value, "productId");
            return this;
        }

        public Criteria andProductIdGreaterThan(BigDecimal value) {
            addCriterion("PRODUCT_ID >", value, "productId");
            return this;
        }

        public Criteria andProductIdGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("PRODUCT_ID >=", value, "productId");
            return this;
        }

        public Criteria andProductIdLessThan(BigDecimal value) {
            addCriterion("PRODUCT_ID <", value, "productId");
            return this;
        }

        public Criteria andProductIdLessThanOrEqualTo(BigDecimal value) {
            addCriterion("PRODUCT_ID <=", value, "productId");
            return this;
        }

        public Criteria andProductIdIn(List values) {
            addCriterion("PRODUCT_ID in", values, "productId");
            return this;
        }

        public Criteria andProductIdNotIn(List values) {
            addCriterion("PRODUCT_ID not in", values, "productId");
            return this;
        }

        public Criteria andProductIdBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("PRODUCT_ID between", value1, value2, "productId");
            return this;
        }

        public Criteria andProductIdNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("PRODUCT_ID not between", value1, value2, "productId");
            return this;
        }

        public Criteria andProcuctNameIsNull() {
            addCriterion("PROCUCT_NAME is null");
            return this;
        }

        public Criteria andProcuctNameIsNotNull() {
            addCriterion("PROCUCT_NAME is not null");
            return this;
        }

        public Criteria andProcuctNameEqualTo(String value) {
            addCriterion("PROCUCT_NAME =", value, "procuctName");
            return this;
        }

        public Criteria andProcuctNameNotEqualTo(String value) {
            addCriterion("PROCUCT_NAME <>", value, "procuctName");
            return this;
        }

        public Criteria andProcuctNameGreaterThan(String value) {
            addCriterion("PROCUCT_NAME >", value, "procuctName");
            return this;
        }

        public Criteria andProcuctNameGreaterThanOrEqualTo(String value) {
            addCriterion("PROCUCT_NAME >=", value, "procuctName");
            return this;
        }

        public Criteria andProcuctNameLessThan(String value) {
            addCriterion("PROCUCT_NAME <", value, "procuctName");
            return this;
        }

        public Criteria andProcuctNameLessThanOrEqualTo(String value) {
            addCriterion("PROCUCT_NAME <=", value, "procuctName");
            return this;
        }

        public Criteria andProcuctNameLike(String value) {
            addCriterion("PROCUCT_NAME like", value, "procuctName");
            return this;
        }
        
        /**
         * 查询草稿表时按产品名字匹配 maliqun  20140723 add
         * @param value
         * @return
         */
        public Criteria andProductNameLike(String value) {
            addCriterion("PRODUCT_NAME like", value, "procuctName");
            return this;
        }

        public Criteria andProcuctNameNotLike(String value) {
            addCriterion("PROCUCT_NAME not like", value, "procuctName");
            return this;
        }

        public Criteria andProcuctNameIn(List values) {
            addCriterion("PROCUCT_NAME in", values, "procuctName");
            return this;
        }

        public Criteria andProcuctNameNotIn(List values) {
            addCriterion("PROCUCT_NAME not in", values, "procuctName");
            return this;
        }

        public Criteria andProcuctNameBetween(String value1, String value2) {
            addCriterion("PROCUCT_NAME between", value1, value2, "procuctName");
            return this;
        }

        public Criteria andProcuctNameNotBetween(String value1, String value2) {
            addCriterion("PROCUCT_NAME not between", value1, value2, "procuctName");
            return this;
        }

        public Criteria andProductNoIsNull() {
            addCriterion("PRODUCT_NO is null");
            return this;
        }

        public Criteria andProductNoIsNotNull() {
            addCriterion("PRODUCT_NO is not null");
            return this;
        }

        public Criteria andProductNoEqualTo(String value) {
            addCriterion("PRODUCT_NO =", value, "productNo");
            return this;
        }

        public Criteria andProductNoNotEqualTo(String value) {
            addCriterion("PRODUCT_NO <>", value, "productNo");
            return this;
        }

        public Criteria andProductNoGreaterThan(String value) {
            addCriterion("PRODUCT_NO >", value, "productNo");
            return this;
        }

        public Criteria andProductNoGreaterThanOrEqualTo(String value) {
            addCriterion("PRODUCT_NO >=", value, "productNo");
            return this;
        }

        public Criteria andProductNoLessThan(String value) {
            addCriterion("PRODUCT_NO <", value, "productNo");
            return this;
        }

        public Criteria andProductNoLessThanOrEqualTo(String value) {
            addCriterion("PRODUCT_NO <=", value, "productNo");
            return this;
        }

        public Criteria andProductNoLike(String value) {
            addCriterion("PRODUCT_NO like", value, "productNo");
            return this;
        }

        public Criteria andProductNoNotLike(String value) {
            addCriterion("PRODUCT_NO not like", value, "productNo");
            return this;
        }

        public Criteria andProductNoIn(List values) {
            addCriterion("PRODUCT_NO in", values, "productNo");
            return this;
        }

        public Criteria andProductNoNotIn(List values) {
            addCriterion("PRODUCT_NO not in", values, "productNo");
            return this;
        }

        public Criteria andProductNoBetween(String value1, String value2) {
            addCriterion("PRODUCT_NO between", value1, value2, "productNo");
            return this;
        }

        public Criteria andProductNoNotBetween(String value1, String value2) {
            addCriterion("PRODUCT_NO not between", value1, value2, "productNo");
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
        //新增的优惠券用
        public Criteria andNewStatusEqualTo(String value) {
            addCriterion("p.STATUS =", value, "status");
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

        public Criteria andChannelIsNull() {
            addCriterion("CHANNEL is null");
            return this;
        }

        public Criteria andChannelIsNotNull() {
            addCriterion("CHANNEL is not null");
            return this;
        }

        public Criteria andChannelEqualTo(String value) {
            addCriterion("CHANNEL =", value, "channel");
            return this;
        }

        public Criteria andChannelNotEqualTo(String value) {
            addCriterion("CHANNEL <>", value, "channel");
            return this;
        }

        public Criteria andChannelGreaterThan(String value) {
            addCriterion("CHANNEL >", value, "channel");
            return this;
        }

        public Criteria andChannelGreaterThanOrEqualTo(String value) {
            addCriterion("CHANNEL >=", value, "channel");
            return this;
        }

        public Criteria andChannelLessThan(String value) {
            addCriterion("CHANNEL <", value, "channel");
            return this;
        }

        public Criteria andChannelLessThanOrEqualTo(String value) {
            addCriterion("CHANNEL <=", value, "channel");
            return this;
        }

        public Criteria andChannelLike(String value) {
            addCriterion("CHANNEL like", "%"+value+"%", "channel");
            return this;
        }

        public Criteria andChannelNotLike(String value) {
            addCriterion("CHANNEL not like", value, "channel");
            return this;
        }

        public Criteria andChannelIn(List values) {
            addCriterion("CHANNEL in", values, "channel");
            return this;
        }

        public Criteria andChannelNotIn(List values) {
            addCriterion("CHANNEL not in", values, "channel");
            return this;
        }

        public Criteria andChannelBetween(String value1, String value2) {
            addCriterion("CHANNEL between", value1, value2, "channel");
            return this;
        }

        public Criteria andChannelNotBetween(String value1, String value2) {
            addCriterion("CHANNEL not between", value1, value2, "channel");
            return this;
        }

        public Criteria andSupplierTypeEqualTo(Integer value) {
            addCriterion("SUPPLIER_TYPE !=", value, "supplierType");
            //过滤时代
            addCriterion("SUPPLIER_TYPE ！=", 4, "supplierType");
            return this;
        }
        
        public Criteria andShopCodeIn(List values) {
            addCriterion("SHOP_CODE in", values, "shopCode");
            return this;
        }

        public Criteria andMarketPriceIsNull() {
            addCriterion("MARKET_PRICE is null");
            return this;
        }

        public Criteria andMarketPriceIsNotNull() {
            addCriterion("MARKET_PRICE is not null");
            return this;
        }

        public Criteria andMarketPriceEqualTo(BigDecimal value) {
            addCriterion("MARKET_PRICE =", value, "marketPrice");
            return this;
        }

        public Criteria andMarketPriceNotEqualTo(BigDecimal value) {
            addCriterion("MARKET_PRICE <>", value, "marketPrice");
            return this;
        }

        public Criteria andMarketPriceGreaterThan(BigDecimal value) {
            addCriterion("MARKET_PRICE >", value, "marketPrice");
            return this;
        }

        public Criteria andMarketPriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("MARKET_PRICE >=", value, "marketPrice");
            return this;
        }

        public Criteria andMarketPriceLessThan(BigDecimal value) {
            addCriterion("MARKET_PRICE <", value, "marketPrice");
            return this;
        }

        public Criteria andMarketPriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("MARKET_PRICE <=", value, "marketPrice");
            return this;
        }

        public Criteria andMarketPriceIn(List values) {
            addCriterion("MARKET_PRICE in", values, "marketPrice");
            return this;
        }

        public Criteria andMarketPriceNotIn(List values) {
            addCriterion("MARKET_PRICE not in", values, "marketPrice");
            return this;
        }

        public Criteria andMarketPriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("MARKET_PRICE between", value1, value2, "marketPrice");
            return this;
        }

        public Criteria andMarketPriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("MARKET_PRICE not between", value1, value2, "marketPrice");
            return this;
        }

        public Criteria andCostPriceIsNull() {
            addCriterion("COST_PRICE is null");
            return this;
        }

        public Criteria andCostPriceIsNotNull() {
            addCriterion("COST_PRICE is not null");
            return this;
        }

        public Criteria andCostPriceEqualTo(BigDecimal value) {
            addCriterion("COST_PRICE =", value, "costPrice");
            return this;
        }

        public Criteria andCostPriceNotEqualTo(BigDecimal value) {
            addCriterion("COST_PRICE <>", value, "costPrice");
            return this;
        }

        public Criteria andCostPriceGreaterThan(BigDecimal value) {
            addCriterion("COST_PRICE >", value, "costPrice");
            return this;
        }

        public Criteria andCostPriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("COST_PRICE >=", value, "costPrice");
            return this;
        }

        public Criteria andCostPriceLessThan(BigDecimal value) {
            addCriterion("COST_PRICE <", value, "costPrice");
            return this;
        }

        public Criteria andCostPriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("COST_PRICE <=", value, "costPrice");
            return this;
        }

        public Criteria andCostPriceIn(List values) {
            addCriterion("COST_PRICE in", values, "costPrice");
            return this;
        }

        public Criteria andCostPriceNotIn(List values) {
            addCriterion("COST_PRICE not in", values, "costPrice");
            return this;
        }

        public Criteria andCostPriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("COST_PRICE between", value1, value2, "costPrice");
            return this;
        }

        public Criteria andCostPriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("COST_PRICE not between", value1, value2, "costPrice");
            return this;
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

        public Criteria andCategoryIdNotEqualTo(BigDecimal value) {
            addCriterion("CATEGORY_ID <>", value, "categoryId");
            return this;
        }

        public Criteria andCategoryIdGreaterThan(BigDecimal value) {
            addCriterion("CATEGORY_ID >", value, "categoryId");
            return this;
        }

        public Criteria andCategoryIdGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("CATEGORY_ID >=", value, "categoryId");
            return this;
        }

        public Criteria andCategoryIdLessThan(BigDecimal value) {
            addCriterion("CATEGORY_ID <", value, "categoryId");
            return this;
        }

        public Criteria andCategoryIdLessThanOrEqualTo(BigDecimal value) {
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

        public Criteria andCategoryIdBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("CATEGORY_ID between", value1, value2, "categoryId");
            return this;
        }

        public Criteria andCategoryIdNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("CATEGORY_ID not between", value1, value2, "categoryId");
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

        public Criteria andProductSkuIdIsNull() {
            addCriterion("PRODUCT_SKU_ID is null");
            return this;
        }

        public Criteria andProductSkuIdIsNotNull() {
            addCriterion("PRODUCT_SKU_ID is not null");
            return this;
        }

        public Criteria andProductSkuIdEqualTo(Long value) {
            addCriterion("PRODUCT_SKU_ID =", value, "productSkuId");
            return this;
        }

        public Criteria andProductSkuIdNotEqualTo(BigDecimal value) {
            addCriterion("PRODUCT_SKU_ID <>", value, "productSkuId");
            return this;
        }

        public Criteria andProductSkuIdGreaterThan(BigDecimal value) {
            addCriterion("PRODUCT_SKU_ID >", value, "productSkuId");
            return this;
        }

        public Criteria andProductSkuIdGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("PRODUCT_SKU_ID >=", value, "productSkuId");
            return this;
        }

        public Criteria andProductSkuIdLessThan(BigDecimal value) {
            addCriterion("PRODUCT_SKU_ID <", value, "productSkuId");
            return this;
        }

        public Criteria andProductSkuIdLessThanOrEqualTo(BigDecimal value) {
            addCriterion("PRODUCT_SKU_ID <=", value, "productSkuId");
            return this;
        }

        public Criteria andProductSkuIdIn(List values) {
            addCriterion("PRODUCT_SKU_ID in", values, "productSkuId");
            return this;
        }

        public Criteria andProductSkuIdNotIn(List values) {
            addCriterion("PRODUCT_SKU_ID not in", values, "productSkuId");
            return this;
        }

        public Criteria andProductSkuIdBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("PRODUCT_SKU_ID between", value1, value2, "productSkuId");
            return this;
        }

        public Criteria andProductSkuIdNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("PRODUCT_SKU_ID not between", value1, value2, "productSkuId");
            return this;
        }

        public Criteria andProductSkuCodeIsNull() {
            addCriterion("PRODUCT_SKU_CODE is null");
            return this;
        }

        public Criteria andProductSkuCodeIsNotNull() {
            addCriterion("PRODUCT_SKU_CODE is not null");
            return this;
        }

        public Criteria andProductSkuCodeEqualTo(String value) {
            addCriterion("PRODUCT_SKU_CODE =", value, "productSkuCode");
            return this;
        }

        public Criteria andProductSkuCodeNotEqualTo(String value) {
            addCriterion("PRODUCT_SKU_CODE <>", value, "productSkuCode");
            return this;
        }

        public Criteria andProductSkuCodeGreaterThan(String value) {
            addCriterion("PRODUCT_SKU_CODE >", value, "productSkuCode");
            return this;
        }

        public Criteria andProductSkuCodeGreaterThanOrEqualTo(String value) {
            addCriterion("PRODUCT_SKU_CODE >=", value, "productSkuCode");
            return this;
        }

        public Criteria andProductSkuCodeLessThan(String value) {
            addCriterion("PRODUCT_SKU_CODE <", value, "productSkuCode");
            return this;
        }

        public Criteria andProductSkuCodeLessThanOrEqualTo(String value) {
            addCriterion("PRODUCT_SKU_CODE <=", value, "productSkuCode");
            return this;
        }

        public Criteria andProductSkuCodeLike(String value) {
            addCriterion("PRODUCT_SKU_CODE like", value, "productSkuCode");
            return this;
        }

        public Criteria andProductSkuCodeNotLike(String value) {
            addCriterion("PRODUCT_SKU_CODE not like", value, "productSkuCode");
            return this;
        }

        public Criteria andProductSkuCodeIn(List values) {
            addCriterion("PRODUCT_SKU_CODE in", values, "productSkuCode");
            return this;
        }

        public Criteria andProductSkuCodeNotIn(List values) {
            addCriterion("PRODUCT_SKU_CODE not in", values, "productSkuCode");
            return this;
        }

        public Criteria andProductSkuCodeBetween(String value1, String value2) {
            addCriterion("PRODUCT_SKU_CODE between", value1, value2, "productSkuCode");
            return this;
        }

        public Criteria andProductSkuCodeNotBetween(String value1, String value2) {
            addCriterion("PRODUCT_SKU_CODE not between", value1, value2, "productSkuCode");
            return this;
        }

        public Criteria andPriceIsNull() {
            addCriterion("PRICE is null");
            return this;
        }

        public Criteria andPriceIsNotNull() {
            addCriterion("PRICE is not null");
            return this;
        }

        public Criteria andPriceEqualTo(BigDecimal value) {
            addCriterion("PRICE =", value, "price");
            return this;
        }

        public Criteria andPriceNotEqualTo(BigDecimal value) {
            addCriterion("PRICE <>", value, "price");
            return this;
        }

        public Criteria andPriceGreaterThan(BigDecimal value) {
            addCriterion("PRICE >", value, "price");
            return this;
        }

        public Criteria andPriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("PRICE >=", value, "price");
            return this;
        }

        public Criteria andPriceLessThan(BigDecimal value) {
            addCriterion("PRICE <", value, "price");
            return this;
        }

        public Criteria andPriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("PRICE <=", value, "price");
            return this;
        }

        public Criteria andPriceIn(List values) {
            addCriterion("PRICE in", values, "price");
            return this;
        }

        public Criteria andPriceNotIn(List values) {
            addCriterion("PRICE not in", values, "price");
            return this;
        }

        public Criteria andPriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("PRICE between", value1, value2, "price");
            return this;
        }

        public Criteria andPriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("PRICE not between", value1, value2, "price");
            return this;
        }
        
        public Criteria andSkuStatusIsNull() {
			addCriterion("SKU_STATUS is null");
			return this;
		}

		public Criteria andSkuStatusIsNotNull() {
			addCriterion("SKU_STATUS is not null");
			return this;
		}

		public Criteria andSkuStatusEqualTo(String value) {
			addCriterion("SKU_STATUS =", value, "skuStatus");
			return this;
		}

		public Criteria andSkuStatusNotEqualTo(String value) {
			addCriterion("SKU_STATUS <>", value, "skuStatus");
			return this;
		}

		public Criteria andSkuStatusGreaterThan(String value) {
			addCriterion("SKU_STATUS >", value, "skuStatus");
			return this;
		}

		public Criteria andSkuStatusGreaterThanOrEqualTo(String value) {
			addCriterion("SKU_STATUS >=", value, "skuStatus");
			return this;
		}

		public Criteria andSkuStatusLessThan(String value) {
			addCriterion("SKU_STATUS <", value, "skuStatus");
			return this;
		}

		public Criteria andSkuStatusLessThanOrEqualTo(String value) {
			addCriterion("SKU_STATUS <=", value, "skuStatus");
			return this;
		}

		public Criteria andSkuStatusLike(String value) {
			addCriterion("SKU_STATUS like", value, "skuStatus");
			return this;
		}

		public Criteria andSkuStatusNotLike(String value) {
			addCriterion("SKU_STATUS not like", value, "skuStatus");
			return this;
		}

		public Criteria andSkuStatusIn(List values) {
			addCriterion("SKU_STATUS in", values, "skuStatus");
			return this;
		}

		public Criteria andSkuStatusNotIn(List values) {
			addCriterion("SKU_STATUS not in", values, "skuStatus");
			return this;
		}

		public Criteria andSkuStatusBetween(String value1, String value2) {
			addCriterion("SKU_STATUS between", value1, value2, "skuStatus");
			return this;
		}

		public Criteria andSkuStatusNotBetween(String value1, String value2) {
			addCriterion("SKU_STATUS not between", value1, value2, "skuStatus");
			return this;
		}
		
		public Criteria andProductTitleIsNull() {
            addCriterion("PRODUCT_TITLE is null");
            return this;
        }

        public Criteria andProductTitleIsNotNull() {
            addCriterion("PRODUCT_TITLE is not null");
            return this;
        }

        public Criteria andProductTitleEqualTo(String value) {
            addCriterion("PRODUCT_TITLE =", value, "productTitle");
            return this;
        }

        public Criteria andProductTitleNotEqualTo(String value) {
            addCriterion("PRODUCT_TITLE <>", value, "productTitle");
            return this;
        }

        public Criteria andProductTitleGreaterThan(String value) {
            addCriterion("PRODUCT_TITLE >", value, "productTitle");
            return this;
        }

        public Criteria andProductTitleGreaterThanOrEqualTo(String value) {
            addCriterion("PRODUCT_TITLE >=", value, "productTitle");
            return this;
        }

        public Criteria andProductTitleLessThan(String value) {
            addCriterion("PRODUCT_TITLE <", value, "productTitle");
            return this;
        }

        public Criteria andProductTitleLessThanOrEqualTo(String value) {
            addCriterion("PRODUCT_TITLE <=", value, "productTitle");
            return this;
        }

        public Criteria andProductTitleLike(String value) {
            addCriterion("PRODUCT_TITLE like", value, "productTitle");
            return this;
        }

        public Criteria andProductTitleNotLike(String value) {
            addCriterion("PRODUCT_TITLE not like", value, "productTitle");
            return this;
        }

        public Criteria andProductTitleIn(List values) {
            addCriterion("PRODUCT_TITLE in", values, "productTitle");
            return this;
        }

        public Criteria andProductTitleNotIn(List values) {
            addCriterion("PRODUCT_TITLE not in", values, "productTitle");
            return this;
        }

        public Criteria andProductTitleBetween(String value1, String value2) {
            addCriterion("PRODUCT_TITLE between", value1, value2, "productTitle");
            return this;
        }

        public Criteria andProductTitleNotBetween(String value1, String value2) {
            addCriterion("PRODUCT_TITLE not between", value1, value2, "productTitle");
            return this;
        }
        
        
		
    }

	public Long getmCategoryId() {
		return mCategoryId;
	}

	public void setmCategoryId(Long mCategoryId) {
		this.mCategoryId = mCategoryId;
	}

	
	public Integer getSupplierTYpe() {
		return supplierTYpe;
	}

	public void setSupplierTYpe(Integer supplierTYpe) {
		this.supplierTYpe = supplierTYpe;
	}

	public Long getbCategoryId() {
		return bCategoryId;
	}

	public void setbCategoryId(Long bCategoryId) {
		this.bCategoryId = bCategoryId;
	}

	public String getSearchBrandName() {
		return searchBrandName;
	}

	public void setSearchBrandName(String searchBrandName) {
		this.searchBrandName = searchBrandName;
	}

	public List getShopCode() {
		return shopCode;
	}

	public void setShopCode(List shopCode) {
		this.shopCode = shopCode;
	}

	

	
	
	
}