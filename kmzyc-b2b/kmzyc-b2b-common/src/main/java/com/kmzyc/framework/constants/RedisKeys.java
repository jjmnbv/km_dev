package com.kmzyc.framework.constants;

import com.kmzyc.zkconfig.ConfigurationUtil;

public interface RedisKeys {

    /********************** SEARCH ************************/
    // 搜索词条统计KEY
    public static final String SEARCH_TERM_LIST = ConfigurationUtil.getString("search.term.list");
    // 热搜词排行保存KEY
    public static final String SEARCH_HOT_LIST = ConfigurationUtil.getString("search.hot.list");

    /********************** B2B ************************/
    public static final String B2B_PRODUCT_TAGS = ConfigurationUtil.getString("product.tags");

    // REDIS中保存商品促销KEY
    public static final String PROMOTION_KEY_PREFIX = "r_product_promotion";
}
