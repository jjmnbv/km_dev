package com.kmzyc.b2b.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.kmzyc.b2b.dao.ProductRelationDao;
import com.kmzyc.b2b.model.ProductRelation;
import com.kmzyc.b2b.model.ProductsOrderBySale;
import com.kmzyc.b2b.model.ViewProductRelationPurchase;
import com.kmzyc.b2b.vo.CarProduct;
import com.kmzyc.framework.exception.DaoException;
import com.km.framework.persistence.impl.DaoImpl;

@SuppressWarnings("unchecked")
@Component("productRelationDao")
public class ProductRelationDaoImpl extends DaoImpl implements ProductRelationDao {
    @javax.annotation.Resource(name = "sqlMapClient")
    private com.ibatis.sqlmap.client.SqlMapClient sqlMapClient;

    @Override
    public ProductRelation findById(Long Id) throws SQLException, DaoException {
        return (ProductRelation) sqlMapClient.queryForObject("ProductRelation.findById", Id);
    }

    @Override
    public List<CarProduct> findProductRelationDetailAllByRelationId(Long Id) throws SQLException {

        List<CarProduct> list =
                sqlMapClient
                        .queryForList("ProductRelationDetailAll.findCarProductByRelationId", Id);
        return list;
    }

    @Override
    public List<ViewProductRelationPurchase> findViewProductRelationPurchaseByRelationid(
            Integer Id, Integer size) throws SQLException {
        Map map = new HashMap();
        map.put("id", Id);
        map.put("size", size);
        List list = sqlMapClient.queryForList("ViewProductRelationPurchase.findByRelationId", map);
        return list;
    }

    @Override
    public List<ViewProductRelationPurchase> findBuyViewProductRelationPurchaseArray(Long[] skuids)
            throws SQLException {
        List list =
                sqlMapClient.queryForList("ViewProductRelationPurchase.findBuyByRelationIdArray",
                        skuids);
        return list;
    }

    @Override
    public List<ViewProductRelationPurchase> findBrowseViewProductRelationPurchaseArray(
            Long[] skuids) throws SQLException {
        List list =
                sqlMapClient.queryForList(
                        "ViewProductRelationPurchase.findBrowseByRelationIdArray", skuids);

        return list;
    }

    /**
     * 查询关联商品，不足数据条数时当前商品同品类补足
     * 
     * @param params
     * @return
     */
    public List<ViewProductRelationPurchase> findRelationProduct(Map<String, Object> params)
            throws SQLException {
        List list =
                sqlMapClient.queryForList("ViewProductRelationPurchase.queryRelationPage", params);
        return (List<ViewProductRelationPurchase>) list;
    }

    /**
     * 查询前*商品
     * 
     * @param params
     * @return
     */
    public List<ViewProductRelationPurchase> findTopProduct(Map<String, Object> params)
            throws SQLException {
        List list =
                sqlMapClient.queryForList("ViewProductRelationPurchase.queryTopProduct", params);
        return (List<ViewProductRelationPurchase>) list;
    }

    /**
     * 根据ID查套餐详情
     * 
     * @param ID
     * @return ProductRelation
     */
    @Override
    public ProductRelation findByProductRelationId(Long id) throws SQLException {

        return (ProductRelation) sqlMapClient.queryForObject(
                "ProductRelation.findByProductRelationId", id);
    }

    /**
     * 查询主SKUID的所有组合产品
     * 
     * @param skuId
     * @return
     * @throws SQLException
     */
    @Override
    public List<ProductRelation> queryProductRelationBySkuId(Long skuId) throws SQLException {
        return (List<ProductRelation>) sqlMapClient.queryForList(
                "ProductRelation.SQL_QUERY_PRODUCT_RELATION_BY_SKU_ID", skuId);
    }

    @Override
    public String queryCategoryParentId(String skuid) throws SQLException {
        String parentId = "";
        parentId =
                (String) sqlMapClient.queryForObject(
                        "ViewProductRelationPurchase.queryCategoryParentId", skuid);
        return parentId;
    }

    @Override
    public List<ProductsOrderBySale> findProductOrderBySalequantity(Map<String, Object> params)
            throws SQLException {
        List<ProductsOrderBySale> productList =
                sqlMapClient.queryForList(
                        "ViewProductRelationPurchase.findProductOrderBySalequantity", params);
        return productList;
    }
}
