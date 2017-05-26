package com.kmzyc.framework.constants;

import com.kmzyc.zkconfig.ConfigurationUtil;

/**
 * 缓存KEYS
 * 
 * @author river
 * 
 */
public interface MemCacheKeys {
    // 导航属性排序缓存KEY
    public static final String FACET_SORT = ConfigurationUtil.getString("b2b.search.facet.sort");
    // 运营类目
    public static final String OPERATION_CATEGORY_SUFFIX =
            ConfigurationUtil.getString("operation.category.suffix");
    // 热闹搜索商品(搜索结果页左则，数据产品系统提供)
    public static final String HOT_PRODUCTS = ConfigurationUtil.getString("hot.section.products");
    // 缓存过期时间
    public static final int LOSE_EFFICACY =
            Integer.parseInt(ConfigurationUtil.getString("lose.efficacy"));
    // 运营类目编码与名称映射关系
    public static final String SEARCH_OPRATION_CATEGORY =
            ConfigurationUtil.getString("search.opration.category");
    // CMS出版的运营类目信息
    public static final String CMS_CATEGLIST = "_cms_categList";
}
