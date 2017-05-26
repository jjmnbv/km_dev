package com.pltfm.app.util;

/**
 * 功能：产品列表查询状态
 *
 * @author Zhoujiwei
 * @since 2016/1/12 15:52
 */
public interface ProductListType {

    /**
     * 产品信息复核列表
     */
    final static String RECHECK = "recheck";

    /**
     * 查看违规下架
     */
    final static String ILLEGAL = "illegal";

    final static String PRICE = "price";

    final static String PRICE_SUCCESS = "priceSuccess";

    final static String PRICE_FAIL_FROM_OFFICIAL = "priceFailFromOfficial";

    final static String WEIGHT = "weight";

    final static String WEIGHT_SUCCESS = "weightSuccess";

    final static String ZYC_PRICE = "zycPrice";

    final static String ZYC_UPDATE_PIRCE = "zycUpdatePirce";

    final static String SECTIONS = "sections";

    final static String STOCK = "stock";

    final static String VIEW_ZYC_PIRCE = "viewZycPirce";
}
