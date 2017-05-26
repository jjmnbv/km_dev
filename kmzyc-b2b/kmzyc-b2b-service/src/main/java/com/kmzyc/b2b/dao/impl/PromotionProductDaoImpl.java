package com.kmzyc.b2b.dao.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.kmzyc.b2b.dao.PromotionProductDao;
import com.kmzyc.b2b.model.PromotionInfo;
import com.kmzyc.b2b.model.PromotionProduct;
import com.kmzyc.b2b.shopcart.util.ShopCartUtil;
import com.kmzyc.b2b.vo.CarProduct;
import com.km.framework.page.Pagination;
import com.km.framework.persistence.impl.DaoImpl;

@Component("promotionProductDao")
@SuppressWarnings("unchecked")
public class PromotionProductDaoImpl extends DaoImpl implements PromotionProductDao {
    private static Logger logger= LoggerFactory.getLogger(PromotionProductDao.class);
    @javax.annotation.Resource(name = "sqlMapClient")
    private com.ibatis.sqlmap.client.SqlMapClient sqlMapClient;

    @Override
    public Pagination getPromotionProduct(Pagination page, PromotionInfo promotion) {
        // {1:'全场',2:'指定商品',3:'商品类目',4:'商品品牌',5:'指定商家'}
        Integer selectProductType = promotion.getProductFilterType();

        Map<String, Object> conditionMap = new HashMap<String, Object>();
        String sql = "";
        String countSql = "";
        sql = "PromotionProduct.getProductInfo";
        countSql = "PromotionProduct.getProductInfoCount";

        String promotionSelectSql =
                promotion.getProductFilterSql() == null ? "" : promotion.getProductFilterSql();
        int start = 0, end = promotionSelectSql.length();
        if (promotionSelectSql.startsWith(",")) {
            start = start + 1;
        }
        if (promotionSelectSql.endsWith(",")) {
            end = end - 1;
        }
        promotionSelectSql = promotionSelectSql.substring(start, end);
        conditionMap.put("channel", ShopCartUtil.SHOP_CAR_CHANNEL);
        if (selectProductType.intValue() == 1 && promotion.getShopSort() == 2) {
            selectProductType = 5;
        }
        if (selectProductType.intValue() == 1 && promotion.getShopSort() == 3) { // 自营代销全场
            conditionMap.put("shopCode", "221");
        }
        switch (selectProductType.intValue()) {
            case 1:
                break;
            case 2:
                sql = "PromotionProduct.getProductSkuIdBySku";
                countSql = "PromotionProduct.getProductSkuIdBySkuCount";
                conditionMap.put("promotionId", promotion.getPromotionId());
                conditionMap.put("promotionStatus", 2);
                break;
            case 3:
                conditionMap.put("categoryIds", promotionSelectSql.split(","));
                break;
            case 4:
                conditionMap.put("brandIds", promotionSelectSql.split(","));
                break;
            case 5:
                conditionMap.put("shopCodes", promotion.getSellerId().toString().split(","));
                break;
            default:
                break;
        }
        try {
            page.setObjCondition(conditionMap);
            page = super.findByPage(sql, countSql, page);
        } catch (SQLException e) {
            logger.error(e.getMessage(),e);
        }
        return page;
    }

    /**
     * 获取加价购商品
     * 
     * @param prizeData活动规则奖励数据
     */
    public Map<Long, CarProduct> getIncreaseProduct(Long prizeData) throws SQLException {
        return sqlMapClient.queryForMap("CarProduct.getIncreaseProduct", prizeData, "productSkuId");
    }

    /**
     * 获取赠品
     * 
     * @param promotionId活动ID
     * @return
     */
    public Map<Long, CarProduct> getGiftProductByPromotion(Long promotionId) throws SQLException {
        return sqlMapClient.queryForMap("CarProduct.getGiftProduct", promotionId, "productSkuId");
    }

    public List<PromotionProduct> getPromotionProductBySku(HashMap map) throws SQLException {


        return (List<PromotionProduct>) sqlMapClient.queryForList(
                "PromotionProduct.getPromotionProductBySku", map);
    }
}
