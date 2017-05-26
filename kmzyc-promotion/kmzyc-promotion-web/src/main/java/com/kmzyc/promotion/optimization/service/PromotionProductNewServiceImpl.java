package com.kmzyc.promotion.optimization.service;

import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.km.framework.page.Pagination;
import com.kmzyc.promotion.app.dao.ProductSkuDAO;
import com.kmzyc.promotion.app.dao.PromotionProductDAO;
import com.kmzyc.promotion.app.vobject.ProductAndSku;
import com.kmzyc.promotion.app.vobject.PromotionInfo;
import com.kmzyc.promotion.optimization.model.RestrictionProduct;

@Service("promotionProductNewService")
public class PromotionProductNewServiceImpl implements PromotionProductNewService {
    @Resource
    PromotionProductDAO promotionProductDAO;
    @Resource
    ProductSkuDAO productSkuDao;

    private final static Logger logger =
            LoggerFactory.getLogger(PromotionProductNewServiceImpl.class);



    @Override
    public Map<String, Double> queryPromotionProductSkuIdMapFromDb(PromotionInfo promotion,
            Pagination page) {
        // {1:'全场',2:'指定商品',3:'商品类目',4:'商品品牌',5:'指定商家'}
        Integer selectProductType = promotion.getProductFilterType();
        Map<String, String> conditionMap = new HashMap<String, String>();
        // String sql = "PROMOTION_PRODUCT.getProductInfo";
        String sql = "promotion_optimization.getProductSkuSet";
        String promotionSelectSql =
                promotion.getProductFilterSql() == null ? "" : promotion.getProductFilterSql();
        int start = 0, end = promotionSelectSql.length();
        if (promotionSelectSql.startsWith(",")) {
            start = start + 1;
        }
        if (promotionSelectSql.endsWith(",")) {
            end = end - 1;
        }
        conditionMap.put("promotionId", promotion.getPromotionId().toString());
        int shopSort = promotion.getShopSort().intValue();
        if (shopSort == 2) {
            conditionMap.put("shopCodes", promotion.getSupplierId().toString());
        } else if (shopSort == 3) {
            // 康美自营代销
            conditionMap.put("shopCodes", "1");
            // sql = "PROMOTION_PRODUCT.getProductInfoA";
            sql = "promotion_optimization.getProductSkuSetKM";
        } else {
            conditionMap.put("shopCodes", "");
        }
        // {1:'全场',2:'指定商品',3:'商品类目',4:'商品品牌'}
        switch (selectProductType.intValue()) {
            case 1:
                break;
            case 2:
                // sql = "PROMOTION_PRODUCT.getProductSkuIdBySku";
                sql = "promotion_optimization.getPromotionProductSkuSetBySku";
                break;
            case 3:
                conditionMap.put("categoryIds", promotionSelectSql.substring(start, end));
                break;
            case 4:
                conditionMap.put("brandIds", promotionSelectSql.substring(start, end));
                break;
            default:
                break;
        }
        page.setObjCondition(conditionMap);
        Map<String, Double> map =
                promotionProductDAO.queryProductSkuIdMapByPromotionInfo(sql, page);
        return map;
    }

    @Override
    public List<ProductAndSku> queryProductAndSkuForBatch(Collection<?> skuIds, Long promotionId) {
        return promotionProductDAO.queryProductAndSkuForBatch(skuIds, promotionId);
    }


    @Override
    public RestrictionProduct queryRestrictionProduct(Long skuId, Long promotionId) {
        try {
            return promotionProductDAO.queryRestrictionProduct(skuId, promotionId);
        } catch (SQLException e) {
            logger.error("查询商品特价价格或限购信息异常", e);
            return null;
        }
    }



}
