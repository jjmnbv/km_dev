package com.pltfm.app.dao.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.pltfm.app.dao.BaseDao;
import com.pltfm.app.dao.ProductRelationDAO;
import com.pltfm.app.vobject.ProductRelation;
import com.pltfm.app.vobject.ProductRelationAndDetail;
import com.pltfm.app.vobject.ProductRelationDetailExample;
import com.pltfm.app.vobject.ProductRelationExample;
import com.kmzyc.commons.page.Page;

@Component("productRelationDao")
public class ProductRelationDAOImpl extends BaseDao implements ProductRelationDAO {

    public int countByExample(ProductRelationExample example) throws SQLException {
        Integer count = (Integer) sqlMapClient.queryForObject("PRODUCT_RELATION.ibatorgenerated_countByExample", example);
        return count.intValue();
    }

    public int countByExampleTaoCan(ProductRelationExample example) throws SQLException {
        Integer count = (Integer) sqlMapClient.queryForObject("PRODUCT_RELATION.ibatorgenerated_countByExampleTaoCan", example);
        return count.intValue();
    }

    public int deleteByExample(ProductRelationExample example) throws SQLException {
        int rows = sqlMapClient.delete("PRODUCT_RELATION.ibatorgenerated_deleteByExample", example);
        return rows;
    }

    public int deleteByPrimaryKey(Long relationId) throws SQLException {
        ProductRelation key = new ProductRelation();
        key.setRelationId(relationId);
        int rows = sqlMapClient.delete("PRODUCT_RELATION.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    public void insert(ProductRelation record) throws SQLException {
        sqlMapClient.insert("PRODUCT_RELATION.ibatorgenerated_insert", record);
    }

    public void insertSelective(ProductRelation record) throws SQLException {
        sqlMapClient.insert("PRODUCT_RELATION.ibatorgenerated_insertSelective", record);
    }

    public List selectByExample(ProductRelationExample example) throws SQLException {
        List list = sqlMapClient.queryForList("PRODUCT_RELATION.ibatorgenerated_selectByExample", example);
        return list;
    }

    public List selectByExample(ProductRelationExample example, Page page) throws SQLException {
        List list = sqlMapClient.queryForList("PRODUCT_RELATION.ibatorgenerated_selectByExample", example, (page.getPageNo() - 1)
                * page.getPageSize(), page.getPageSize());
        return list;
    }

    public List selectByExampleTaoCan(ProductRelationExample example, Page page) throws SQLException {
        List list = sqlMapClient.queryForList("PRODUCT_RELATION.ibatorgenerated_selectByExampleTaoCan", example, (page.getPageNo() - 1)
                * page.getPageSize(), page.getPageSize());
        return list;
    }

    public ProductRelation selectByPrimaryKey(Long relationId) throws SQLException {
        ProductRelation key = new ProductRelation();
        key.setRelationId(relationId);
        ProductRelation record = (ProductRelation) sqlMapClient.queryForObject("PRODUCT_RELATION.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    public int updateByExampleSelective(ProductRelation record, ProductRelationExample example) throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = sqlMapClient.update("PRODUCT_RELATION.ibatorgenerated_updateByExampleSelective", parms);
        return rows;
    }

    public int updateByExample(ProductRelation record, ProductRelationExample example) throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = sqlMapClient.update("PRODUCT_RELATION.ibatorgenerated_updateByExample", parms);
        return rows;
    }

    public int updateByPrimaryKeySelective(ProductRelation record) throws SQLException {
        int rows = sqlMapClient.update("PRODUCT_RELATION.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    public int updateByPrimaryKey(ProductRelation record) throws SQLException {
        int rows = sqlMapClient.update("PRODUCT_RELATION.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }

    private static class UpdateByExampleParms extends ProductRelationExample {
        private Object record;

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
        return (Long) sqlMapClient.insert("PRODUCT_RELATION.ibatorgenerated_insertSelective", record);
    }

    public int batchDelProductRelation(List<Long> detailIdList) throws SQLException {
        return batchDeleteByDataPrimaryKeyNt(detailIdList, "PRODUCT_RELATION.deleteProductRelationByDeatilId");
    }

    @Override
    public List<ProductRelationAndDetail> selectProductRelationAndDetailBySkuId(Long skuId) throws SQLException {
        return sqlMapClient.queryForList("PRODUCT_RELATION.selectProductRelationAndDetailBySkuId", skuId);
    }

    public int batchUpateProductRelationStatus(List<Long> relations) throws SQLException {
        return batchUpdateData("PRODUCT_RELATION.batchUpdateStatus", relations);
    }

    @Override
    public int updateProductRelation(ProductRelation productRelation) throws SQLException {
        return sqlMapClient.update("PRODUCT_RELATION.ibatorgenerated_updateByPrimaryKeySelective", productRelation);
    }

    @Override
    public List<ProductRelationAndDetail> selectProductRelationAndDetailsByRelaitonSkuId(Long skuId) throws SQLException {
        return sqlMapClient.queryForList("PRODUCT_RELATION.selectProductRelationAndDetailByRelationSkuId", skuId);
    }

    @Override
    public List<Integer> selectProductIdByRelationId(List<Long> relationId) throws SQLException {
        return sqlMapClient.queryForList("PRODUCT_RELATION.selectProductIdByRelationId", relationId);
    }

    @Override
    public List selectByExampleZF(ProductRelationExample example, Page page) throws SQLException {
        return sqlMapClient.queryForList("PRODUCT_RELATION.ibatorgenerated_selectByExampleZF", example, (page.getPageNo() - 1)
                * page.getPageSize(), page.getPageSize());
    }

    public int countByExampleZF(ProductRelationExample example) throws SQLException {
        Integer count = (Integer) sqlMapClient.queryForObject("PRODUCT_RELATION.ibatorgenerated_countByExampleZF", example);
        return count.intValue();
    }

    @Override
    public List<ProductRelation> getRecommendListBySkuId(Long skuId) throws SQLException {
        return sqlMapClient.queryForList("PRODUCT_RELATION.getRecommendListBySkuId", skuId);
    }

    @Override
    public List<ProductRelation> getPackageListBySkuId(Long skuId) throws SQLException {
        return sqlMapClient.queryForList("PRODUCT_RELATION.getPackageListBySkuId", skuId);
    }

    @Override
    public int updateProductRelationStatus(Map<String, String> condition) throws SQLException {
        return sqlMapClient.update("PRODUCT_RELATION.updateProductRelationStatus", condition);
    }

    @Override
    public int batchUpdateRelationList(List<ProductRelation> list) throws SQLException {
        return batchUpdateData("PRODUCT_RELATION.ibatorgenerated_updateByPrimaryKeySelective", list);
    }

}