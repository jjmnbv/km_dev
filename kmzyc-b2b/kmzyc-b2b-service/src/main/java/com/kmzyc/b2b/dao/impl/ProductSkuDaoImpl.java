package com.kmzyc.b2b.dao.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.kmzyc.b2b.dao.ProductSkuDao;
import com.kmzyc.b2b.model.OrderItem;
import com.kmzyc.b2b.model.ProductSku;
import com.kmzyc.b2b.shopcart.vo.CartProduct;
import com.kmzyc.b2b.vo.ShopCategorys;
import com.kmzyc.framework.exception.ServiceException;
import com.km.framework.page.Pagination;
import com.km.framework.persistence.impl.DaoImpl;
import com.pltfm.app.vobject.CategoryAttrValue;
import com.pltfm.app.vobject.ProductAttr;

@SuppressWarnings("unchecked")
@Component
public class ProductSkuDaoImpl extends DaoImpl implements ProductSkuDao {

    private static Logger logger = LoggerFactory.getLogger(ProductSkuDao.class);
    @javax.annotation.Resource(name = "sqlMapClient")
    private com.ibatis.sqlmap.client.SqlMapClient sqlMapClient;

    public ProductSku findProductBySkuCode(String productSkucode) throws SQLException {
        ProductSku sku = (ProductSku) this.sqlMapClient
                .queryForObject("ProductSku.findBySkuCode", productSkucode);
        return sku;
    }

    /**
     * 通过产品主键查询产品sku属性信息
     */
    public List<ProductAttr> queryProductAttrByProductId(Long productId) throws SQLException {
        List list = sqlMapClient
                .queryForList("ProductSku.app_queryProductAttrByProductId", productId);
        return list;
    }

    public List<CategoryAttrValue> findByValueId(String attrValue) throws SQLException {
        List list = sqlMapClient.queryForList("ProductSku.app_findByValueId", attrValue);
        return list;
    }

    public List<ProductSku> findPorductSkuByProductId(Long productId) throws SQLException {
        List<ProductSku> productSkuList = sqlMapClient
                .queryForList("ProductSku.app_queryProductSkuMap", productId);
        return productSkuList;
    }

    @Override
    public String queryProductWareHouse(Long productSkuId) throws SQLException {
        String wareHouse = (String) sqlMapClient
                .queryForObject("ProductStock.queryWarehouseName", productSkuId);
        return wareHouse;
    }

    /**
     * 根据店铺Id 查询店铺地下所有的商品所在订单
     */
    @Override
    public List<OrderItem> findOrderCodeByShopId(Map<String, Object> map) throws SQLException {
        List<OrderItem> orderItemList = sqlMapClient
                .queryForList("ProductSku.app_queryOrderItemByShopId", map);
        return orderItemList;
    }

    public List<ProductSku> queryProductDetailBySku(String productSkuCode) throws SQLException {
        // String productSkuCode12 =
        // "011000182001,041000164402,041000164302,051000158401";
        String[] productSkuCode1 = productSkuCode.split(",");
        List<ProductSku> skuList = sqlMapClient
                .queryForList("ProductSku.findProductDetail", productSkuCode1);
        return skuList;
    }

    /**
     * 销量排行
     */
    public List<ProductSku> queryProductRankByCondition(Map mapCondition) throws SQLException {
        List<ProductSku> skuList = sqlMapClient
                .queryForList("ProductSku.findProductListRank", mapCondition);
        return skuList;
    }

    /**
     * 收藏排行
     */
    public List<ProductSku> findProductFavRankByCondition(Map mapCondition) throws SQLException {
        List<ProductSku> skuList = sqlMapClient
                .queryForList("ProductSku.findProductListFavRank", mapCondition);
        return skuList;
    }

    /**
     * 上架时间排行
     */
    public List<ProductSku> findProductUpTimeRankByCondition(Map mapCondition) throws SQLException {
        List<ProductSku> skuList = sqlMapClient
                .queryForList("ProductSku.findUptimeProductListRank", mapCondition);
        return skuList;
    }

    /**
     * 通过供应商ID，查询店铺的类目以及推荐类目
     */
    public List<ShopCategorys> findCategorysByShopId(Map condition, String recommde) throws
            SQLException {

        List<ShopCategorys> cateGroys;
        if (null != recommde) { // 推介类目
            cateGroys = sqlMapClient.queryForList("ProductSku.findCateGroysByshopidTui", condition);
        } else { // 全部类目
            cateGroys = sqlMapClient.queryForList("ProductSku.findCateGroysByshopidAll", condition);
        }
        return cateGroys;
    }

    /**
     * 获取产品价格信息
     */
    public ProductSku queryPriceInfoBySkuId(Long skuId) throws SQLException {
        return (ProductSku) sqlMapClient
                .queryForObject("ProductSku.SQL_QUERY_PRICE_BY_SKU_ID", skuId);
    }

    @Override
    public Long querySkuByBarCode(String barCode) throws SQLException {
        return (Long) sqlMapClient.queryForObject("ProductSku.querySkuIdByBarcode", barCode);
    }

    /**
     * 查询商品库存、状态、价格 详情页面专用
     */
    public Map<String, Object> querySkuInfoForDetailPage(String skuId) throws SQLException {
        return (Map<String, Object>) sqlMapClient
                .queryForObject("ProductSku.SQL_QUERY_PRODUCT_SKU_INFO_DETAIL_PAGE",
                        Long.parseLong(skuId));
    }

    /**
     * 查询返佣率
     */
    public Map<String, BigDecimal> queryBatchComRatio(List<Long> ids) throws SQLException {
        return (Map<String, BigDecimal>) sqlMapClient
                .queryForMap("ProductSku.SQL_QUERY_BATCHCOMRATIO", ids, "PRODUCTNO", "COMRATIO");
    }

    /**
     * 查询活动产品信息
     */
    public List<CartProduct> queryPromotionProducts(List<Long> ids) throws SQLException {
        return (List<CartProduct>) sqlMapClient
                .queryForList("ProductSku.SQL_QUERY_PROMOTION_PRODUCTS", ids);
    }

    @Override
    public String getSeckillProducts(HashMap<String, String> skucondition) throws SQLException {
        return (String) sqlMapClient.queryForObject("ProductSku.getSeckillProducts", skucondition);
    }

    @Override
    public List<ProductSku> getProductBySkuIds(String skuIds) throws SQLException {
        return (List<ProductSku>) sqlMapClient
                .queryForList("ProductSku.getProductBySkuIds", skuIds);
    }

    @Override
    public Pagination getRecommendProduct(Pagination page, String name) throws SQLException {
        Map<String, Object> conditionMap = new HashMap<String, Object>();
        conditionMap.put("name", name);
        try {
            page.setObjCondition(conditionMap);
            page = super.findByPage("ProductSku.getRecommendProduct",
                    "ProductSku.getRecommendProductCount", page);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return page;
    }

    @Override
    public List<ProductSku> getWindowDatas(String jboxWindowName) throws SQLException {
        return (List<ProductSku>) sqlMapClient
                .queryForList("ProductSku.getWindowDatas", jboxWindowName);
    }

    /**
     * 根据productRelationType类型及productSkuId组成Map查询套餐或组合
     */
    @Override
    public List findProductRelation(Map typeAndSkuIdMap, String productRelationType) throws
            SQLException {
        if (productRelationType.equals("0")) {
            // 套餐
            return sqlMapClient
                    .queryForList("ProductSku.SQL_FIND_PRODUCT_RELATION_SET_MEAL", typeAndSkuIdMap);
        } else {
            // 组合
            return sqlMapClient
                    .queryForList("ProductSku.SQL_FIND_PRODUCT_RELATION_GROUP", typeAndSkuIdMap);
        }
    }

/*    @Override
    public ProductSku querySkuInfoForFanli(Map condition) throws SQLException {
        return (ProductSku) sqlMapClient.queryForObject("ProductSku.querySkuInfoForFanli",
                condition);
    }*/

    /**
     * 超级返佣金
     */
    @Override
    public Map<Long, BigDecimal> queryBatchOutComRatio(Map<String, Object> ratioMap) throws
            SQLException {
        return (Map<Long, BigDecimal>) sqlMapClient
                .queryForMap("ProductSku.SQL_QUERY_BATCH_OUT_COM_RATIO", ratioMap, "SKUID",
                        "COMRATIO");
    }

    /**
     * 查询普通返佣
     */
    @Override
    public Map<String, BigDecimal> queryBatchOutNorComRatio(Map<String, Object> ratioMap) throws
            SQLException {
        return (Map<String, BigDecimal>) sqlMapClient
                .queryForMap("ProductSku.SQL_QUERY_BATCH_OUT_NOR_COM_RATIO", ratioMap, "PRODUCTNO",
                        "COMRATIO");
    }

    /**
     * 查询商品类目
     */
    @Override
    public Map<String, Integer> queryProductCate(List<Long> skuIds) throws SQLException {
        return (Map<String, Integer>) sqlMapClient
                .queryForMap("ProductSku.SQL_QUERY_PRODUCT_PARENT_CATE", skuIds, "PRODUCTNO",
                        "CATEGORYID");
    }

    @Override
    public Pagination queryProductAndShopBySupplierId(Pagination page, String supplierId) throws
            SQLException {
        Map<String, Object> conditionMap = new HashMap<String, Object>();
        conditionMap.put("supplierId", supplierId);
        try {
            page.setObjCondition(conditionMap);
            page = super.findByPage("ProductSku.queryProductAndShopBySupplierId",
                    "ProductSku.queryProductAndShopBySupplierIdCount", page);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return page;
    }
}
