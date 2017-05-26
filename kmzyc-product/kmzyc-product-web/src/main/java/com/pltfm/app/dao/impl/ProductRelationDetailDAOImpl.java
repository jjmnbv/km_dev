package com.pltfm.app.dao.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.pltfm.app.dao.BaseDao;
import com.pltfm.app.dao.ProductRelationDetailDAO;
import com.pltfm.app.vobject.ProductRelationDetail;
import com.pltfm.app.vobject.ProductRelationDetailExample;
import com.kmzyc.commons.page.Page;

@Component("productRelationDetailDao")
public class ProductRelationDetailDAOImpl extends BaseDao implements ProductRelationDetailDAO {

    public int countByExample(ProductRelationDetailExample example) throws SQLException {
        Integer count = (Integer) sqlMapClient.queryForObject(
                "PRODUCT_RELATION_DETAIL.ibatorgenerated_countByExample",
                example);
        return count.intValue();
    }

    public int deleteByExample(ProductRelationDetailExample example) throws SQLException {
        int rows = sqlMapClient.delete(
                "PRODUCT_RELATION_DETAIL.ibatorgenerated_deleteByExample",
                example);
        return rows;
    }

    public int deleteByPrimaryKey(Long relationDetailId) throws SQLException {
        ProductRelationDetail key = new ProductRelationDetail();
        key.setRelationDetailId(relationDetailId);
        int rows = sqlMapClient.delete(
                "PRODUCT_RELATION_DETAIL.ibatorgenerated_deleteByPrimaryKey",
                key);
        return rows;
    }

    public void insert(ProductRelationDetail record) throws SQLException {
        sqlMapClient.insert("PRODUCT_RELATION_DETAIL.ibatorgenerated_insert",
                record);
    }

    public void insertSelective(ProductRelationDetail record) throws SQLException {
        sqlMapClient.insert(
                "PRODUCT_RELATION_DETAIL.ibatorgenerated_insertSelective",
                record);
    }

    public List selectByExample(ProductRelationDetailExample example) throws SQLException {
        List list = sqlMapClient.queryForList(
                "PRODUCT_RELATION_DETAIL.ibatorgenerated_selectByExample",
                example);
        return list;
    }

    @Override
    public List selectByExample(ProductRelationDetailExample example, Page page) throws SQLException {

        List list = sqlMapClient.queryForList(
                "PRODUCT_RELATION_DETAIL.ibatorgenerated_selectByExample",
                example);
        return list;

    }

    public ProductRelationDetail selectByPrimaryKey(Long relationDetailId) throws SQLException {
        ProductRelationDetail key = new ProductRelationDetail();
        key.setRelationDetailId(relationDetailId);
        ProductRelationDetail record = (ProductRelationDetail) sqlMapClient
                .queryForObject(
                        "PRODUCT_RELATION_DETAIL.ibatorgenerated_selectByPrimaryKey",
                        key);
        return record;
    }

    public int updateByExampleSelective(ProductRelationDetail record, ProductRelationDetailExample example)
            throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = sqlMapClient
                .update("PRODUCT_RELATION_DETAIL.ibatorgenerated_updateByExampleSelective",
                        parms);
        return rows;
    }

    public int updateByExample(ProductRelationDetail record, ProductRelationDetailExample example)
            throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = sqlMapClient.update(
                "PRODUCT_RELATION_DETAIL.ibatorgenerated_updateByExample",
                parms);
        return rows;
    }

    public void updateByPrimaryKeySelective(List<ProductRelationDetail> record) throws SQLException {
        batchUpdate(record, "PRODUCT_RELATION_DETAIL.ibatorgenerated_updateByPrimaryKeySelective");
    }

    public int updateByPrimaryKeySelective(ProductRelationDetail record) throws SQLException {
        int rows = sqlMapClient
                .update("PRODUCT_RELATION_DETAIL.ibatorgenerated_updateByPrimaryKeySelective",
                        record);
        return rows;
    }

    public int updateByPrimaryKey(ProductRelationDetail record) throws SQLException {
        int rows = sqlMapClient.update(
                "PRODUCT_RELATION_DETAIL.ibatorgenerated_updateByPrimaryKey",
                record);
        return rows;
    }

    private static class UpdateByExampleParms extends ProductRelationDetailExample {
        private Object record;

        public UpdateByExampleParms(Object record,
                                    ProductRelationDetailExample example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }

    @Override
    public void batchSaveProductRelationDetail(List<ProductRelationDetail> details) throws SQLException {
        super.batchInsertData(details,
                "PRODUCT_RELATION_DETAIL.ibatorgenerated_insertSelective");
    }

    public int batchDelProductRelationDetailByRelationId(List<Long> relationId) throws SQLException {
        return batchDeleteByDataPrimaryKey(relationId, "PRODUCT_RELATION_DETAIL.deleteProductDeatilIdByRelationId");
    }

    @Override
    public int batchDelProductRelationDetailByRelationDetailId(List<Long> relationDetailId) throws SQLException {
        return batchDeleteByDataPrimaryKey(relationDetailId, "PRODUCT_RELATION_DETAIL.deleteProductDetailByRelationId");
    }

}