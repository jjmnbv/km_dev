package com.kmzyc.promotion.app.dao.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.kmzyc.commons.page.Page;
import com.kmzyc.promotion.app.dao.BaseDao;
import com.kmzyc.promotion.app.dao.ProductRelationDAO;
import com.kmzyc.promotion.app.vobject.ProductRelation;
import com.kmzyc.promotion.app.vobject.ProductRelationAndDetail;
import com.kmzyc.promotion.app.vobject.ProductRelationDetailExample;
import com.kmzyc.promotion.app.vobject.ProductRelationExample;

@Component("productRelationDAO")
@SuppressWarnings({"unchecked", "unused"})
public class ProductRelationDAOImpl extends BaseDao implements ProductRelationDAO {
    /**
     * This field was generated by Apache iBATIS ibator. This field corresponds to the database
     * table PRODUCT_RELATION
     * 
     * @ibatorgenerated Sat Oct 12 17:19:56 CST 2013
     */
    @Resource
    private SqlMapClient sqlMapClient;

    public ProductRelationDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database
     * table PRODUCT_RELATION
     * 
     * @ibatorgenerated Sat Oct 12 17:19:56 CST 2013
     */
    @Override
    public int countByExample(ProductRelationExample example) throws SQLException {
        Integer count = (Integer) sqlMapClient
                .queryForObject("PRODUCT_RELATION.ibatorgenerated_countByExample", example);
        return count.intValue();
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database
     * table PRODUCT_RELATION
     * 
     * @ibatorgenerated Sat Oct 12 17:19:56 CST 2013
     */
    @Override
    public int deleteByExample(ProductRelationExample example) throws SQLException {
        int rows = sqlMapClient.delete("PRODUCT_RELATION.ibatorgenerated_deleteByExample", example);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database
     * table PRODUCT_RELATION
     * 
     * @ibatorgenerated Sat Oct 12 17:19:56 CST 2013
     */
    @Override
    public int deleteByPrimaryKey(Long relationId) throws SQLException {
        ProductRelation key = new ProductRelation();
        key.setRelationId(relationId);
        int rows = sqlMapClient.delete("PRODUCT_RELATION.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database
     * table PRODUCT_RELATION
     * 
     * @ibatorgenerated Sat Oct 12 17:19:56 CST 2013
     */
    @Override
    public void insert(ProductRelation record) throws SQLException {
        sqlMapClient.insert("PRODUCT_RELATION.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database
     * table PRODUCT_RELATION
     * 
     * @ibatorgenerated Sat Oct 12 17:19:56 CST 2013
     */
    @Override
    public void insertSelective(ProductRelation record) throws SQLException {
        sqlMapClient.insert("PRODUCT_RELATION.ibatorgenerated_insertSelective", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database
     * table PRODUCT_RELATION
     * 
     * @ibatorgenerated Sat Oct 12 17:19:56 CST 2013
     */
    @Override
    public List selectByExample(ProductRelationExample example) throws SQLException {
        List list = sqlMapClient.queryForList("PRODUCT_RELATION.ibatorgenerated_selectByExample",
                example);
        return list;
    }

    @Override
    public List selectByExample(ProductRelationExample example, Page page) throws SQLException {
        List list = sqlMapClient.queryForList("PRODUCT_RELATION.ibatorgenerated_selectByExample",
                example, (page.getPageNo() - 1) * page.getPageSize(), page.getPageSize());
        return list;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database
     * table PRODUCT_RELATION
     * 
     * @ibatorgenerated Sat Oct 12 17:19:56 CST 2013
     */
    @Override
    public ProductRelation selectByPrimaryKey(Long relationId) throws SQLException {
        ProductRelation key = new ProductRelation();
        key.setRelationId(relationId);
        ProductRelation record = (ProductRelation) sqlMapClient
                .queryForObject("PRODUCT_RELATION.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database
     * table PRODUCT_RELATION
     * 
     * @ibatorgenerated Sat Oct 12 17:19:56 CST 2013
     */
    @Override
    public int updateByExampleSelective(ProductRelation record, ProductRelationExample example)
            throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = sqlMapClient.update("PRODUCT_RELATION.ibatorgenerated_updateByExampleSelective",
                parms);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database
     * table PRODUCT_RELATION
     * 
     * @ibatorgenerated Sat Oct 12 17:19:56 CST 2013
     */
    @Override
    public int updateByExample(ProductRelation record, ProductRelationExample example)
            throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = sqlMapClient.update("PRODUCT_RELATION.ibatorgenerated_updateByExample", parms);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database
     * table PRODUCT_RELATION
     * 
     * @ibatorgenerated Sat Oct 12 17:19:56 CST 2013
     */
    @Override
    public int updateByPrimaryKeySelective(ProductRelation record) throws SQLException {
        int rows = sqlMapClient
                .update("PRODUCT_RELATION.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database
     * table PRODUCT_RELATION
     * 
     * @ibatorgenerated Sat Oct 12 17:19:56 CST 2013
     */
    @Override
    public int updateByPrimaryKey(ProductRelation record) throws SQLException {
        int rows =
                sqlMapClient.update("PRODUCT_RELATION.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }

    @Override
    public int updateEditableByRelationId(Long relationId) throws SQLException {
        return sqlMapClient.update("PRODUCT_RELATION.updateEditableByRelationId", relationId);

    }

    /**
     * This class was generated by Apache iBATIS ibator. This class corresponds to the database
     * table PRODUCT_RELATION
     * 
     * @ibatorgenerated Sat Oct 12 17:19:56 CST 2013
     */
    private static class UpdateByExampleParms extends ProductRelationExample {
        private final Object record;

        public UpdateByExampleParms(Object record, ProductRelationExample example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }

    @Override
    public Long insertProductRelation(ProductRelation record) throws SQLException {
        Long id = (Long) sqlMapClient.insert("PRODUCT_RELATION.ibatorgenerated_insertSelective",
                record);
        return id;

    }

    @Override
    public int batchDelProductRelation(List<Long> detailIdlist) throws SQLException {
        return batchDeleteByDataPrimaryKeyNt(detailIdlist,
                "PRODUCT_RELATION.deleteProductRelationByDeatilId");

    }

    @Override
    public List<ProductRelationAndDetail> selectProductRelationAndDetailPackageList(Long skuId)
            throws SQLException {
        List<ProductRelationAndDetail> details =
                sqlMapClient.queryForList("PRODUCT_RELATION.selectProductAndDetailPackage", skuId);

        return details;

    }

    @Override
    public List<ProductRelationAndDetail> selectProductRelationAndDetailRecommendList(Long skuId)
            throws SQLException {
        List<ProductRelationAndDetail> details = sqlMapClient
                .queryForList("PRODUCT_RELATION.selectProductAndDetailRecommend", skuId);
        return details;

    }

    @Override
    public Map<Long, ProductRelation> selectProductRelationPackageMapBySkuId(Long skuId)
            throws SQLException {
        Map<Long, ProductRelation> map = sqlMapClient.queryForMap(
                "PRODUCT_RELATION.selectProductDetailPackageBySkuId", skuId, "relationId");

        return map;
    }

    @Override
    public Map<Long, ProductRelation> selectProductRelationRecommendMapBySkuId(Long skuId)
            throws SQLException {
        Map<Long, ProductRelation> map = sqlMapClient.queryForMap(
                "PRODUCT_RELATION.selectProductDetailRecommendBySkuId", skuId, "relationId");

        return map;

    }

    @Override
    public Map<Long, ProductRelation> selectProductAndDetailGlanceList(Long skuId)
            throws Exception {
        Map<Long, ProductRelation> map = sqlMapClient.queryForMap(
                "PRODUCT_RELATION.selectProductDetailGlanceBySkuId", skuId, "relationId");

        return map;
    }

    @Override
    public Map<Long, ProductRelation> selectProductAndDetailPurchaseList(Long skuId)
            throws Exception {
        Map<Long, ProductRelation> map = sqlMapClient.queryForMap(
                "PRODUCT_RELATION.selectProductDetailPurchaseBySkuId", skuId, "relationId");

        return map;
    }

    @Override
    public List<ProductRelationAndDetail> selectProductRelationAndDetailBySkuId(Long skuId)
            throws SQLException {
        return sqlMapClient.queryForList("PRODUCT_RELATION.selectProductRelationAndDetailBySkuId",
                skuId);
    }

    @Override
    public List<Long> selectProductRelationAndDetailByProductId(List<Long> productIdList)
            throws SQLException {
        return sqlMapClient.queryForList(
                "PRODUCT_RELATION.selectProductRelationAndDetailByProductId", productIdList);
    }

    /**
     * 根据relationId 更改套餐的状态
     * 
     * @param relations
     * @return
     * @throws SQLException
     */
    @Override
    public int batchUpateProductRelationStatus(List<Long> relations) throws SQLException {
        int i = batchUpdateData("PRODUCT_RELATION.batchUpdateStatus", relations);

        return i;
    }

    @Override
    public int updateProductRelation(ProductRelation productRelation) throws SQLException {
        int i = sqlMapClient.update("PRODUCT_RELATION.ibatorgenerated_updateByPrimaryKeySelective",
                productRelation);
        return i;
    }

    @Override
    public List<ProductRelationAndDetail> selectProductRelationAndDetailsByRelaitonSkuId(Long skuId)
            throws SQLException {
        return sqlMapClient.queryForList(
                "PRODUCT_RELATION.selectProductRelationAndDetailByRelationSkuId", skuId);
    }

    @Override
    public BigDecimal getTotalRelationPrice(ProductRelationDetailExample productRelation)
            throws SQLException {
        // System.out.println((BigDecimal)
        // sqlMapClient.queryForObject("PRODUCT_RELATION_DETAIL.getTotalRelationPrice",
        // productRelation));
        // 此SQL不存在
        return (BigDecimal) sqlMapClient
                .queryForObject("PRODUCT_RELATION_DETAIL.getTotalRelationPrice", productRelation);
    }

    @Override
    public int updateUnEditableByRelationId(Long id) throws SQLException {
        return sqlMapClient.update("PRODUCT_RELATION.updateUnEditableByRelationId", id);

    }

    @Override
    public int updateStatus(Long relationId, String status) throws SQLException {
        int i = 0;
        if (status.trim().equals("1")) {
            i = sqlMapClient.update("PRODUCT_RELATION.updateProductRelationStatus", relationId);
        } else {
            i = sqlMapClient.update("PRODUCT_RELATION.updateProductRelationUnStatus", relationId);
        }
        return i;
    }

    @Override
    public int batchUpdateData(List<ProductRelation> list) throws SQLException {
        return batchUpdateData("PRODUCT_RELATION.updateProductRelationEdible", list);
    }
}