package com.pltfm.app.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.pltfm.app.dao.BaseDao;
import com.pltfm.app.dao.ProductDraftDAO;
import com.pltfm.app.vobject.Product;
import com.pltfm.app.vobject.ProductDraft;
import com.pltfm.app.vobject.ProductDraftExample;

/**
 * 
 * @author tanyunxing
 *
 */
@Repository("productDraftDao")
public class ProductDraftDAOImpl extends BaseDao<ProductDraft> implements ProductDraftDAO {

    public int countByExample(ProductDraftExample example) throws SQLException {
        Integer count = (Integer) sqlMapClient.queryForObject("PRODUCT_DRAFT.ibatorgenerated_countByExample", example);
        return count.intValue();
    }

    public int countByExampleByUser(ProductDraftExample example) throws SQLException {
        Integer count = (Integer) sqlMapClient.queryForObject("PRODUCT_DRAFT.ibatorgenerated_countByExampleByUser", example);
        return count.intValue();
    }

    public int deleteByExample(ProductDraftExample example) throws SQLException {
        return sqlMapClient.delete("PRODUCT_DRAFT.ibatorgenerated_deleteByExample", example);
    }

    public int deleteByPrimaryKey(Long productId) throws SQLException {
        ProductDraft key = new ProductDraft();
        key.setProductId(productId);
        return sqlMapClient.delete("PRODUCT_DRAFT.ibatorgenerated_deleteByPrimaryKey", key);
    }

    public Long insert(ProductDraft record) throws SQLException {
        return (Long) sqlMapClient.insert("PRODUCT_DRAFT.ibatorgenerated_insert", record);
    }

    public void insertWithOutSeq(ProductDraft record) throws SQLException {
        sqlMapClient.insert("PRODUCT_DRAFT.ibatorgenerated_insert_withOutSeq", record);
    }

    public void insertSelective(ProductDraft record) throws SQLException {
        sqlMapClient.insert("PRODUCT_DRAFT.ibatorgenerated_insertSelective", record);
    }

    public List selectByExampleWithoutBLOBs(ProductDraftExample example) throws SQLException {
        return sqlMapClient.queryForList("PRODUCT_DRAFT.ibatorgenerated_selectByExample", example);
    }

    public List selectByExample(ProductDraftExample example, int skip, int max) throws SQLException {
        return sqlMapClient.queryForList("PRODUCT_DRAFT.ibatorgenerated_selectByExample", example, skip, max);
    }

    @Override
    public List selectByExampleByUser(ProductDraftExample example, int skip, int max) throws SQLException {
        return sqlMapClient.queryForList("PRODUCT_DRAFT.ibatorgenerated_selectByExampleByUser", example, skip, max);
    }

    @Override
    public List selectByExampleByUserForAudit(ProductDraftExample example, int skip, int max) throws SQLException {
        return sqlMapClient.queryForList("PRODUCT_DRAFT.ibatorgenerated_selectByExampleByUserForAudit", example, skip, max);
    }

    public ProductDraft selectByPrimaryKey(Long productId) throws SQLException {
        ProductDraft key = new ProductDraft();
        key.setProductId(productId);
        return (ProductDraft) sqlMapClient.queryForObject("PRODUCT_DRAFT.ibatorgenerated_selectByPrimaryKey", key);
    }

    @Override
    public ProductDraft findSingleProduct(ProductDraft productDraft) throws SQLException {
        return (ProductDraft) sqlMapClient.queryForObject("PRODUCT_DRAFT.findSingleProductAndSkusAndAttrValues", productDraft);
    }

    public int updateByExampleSelective(ProductDraft record, ProductDraftExample example) throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        return sqlMapClient .update("PRODUCT_DRAFT.ibatorgenerated_updateByExampleSelective", parms);
    }

    public int updateByPrimaryKeySelective(ProductDraft record) throws SQLException {
        return sqlMapClient.update("PRODUCT_DRAFT.ibatorgenerated_updateByPrimaryKeySelective", record);
    }

    private static class UpdateByExampleParms extends ProductDraftExample {
        private Object record;

        public UpdateByExampleParms(Object record, ProductDraftExample example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }

    @Override
    public String insertProductOfficial(ProductDraft productDraft) throws SQLException {
        Object productNo = sqlMapClient.insert("PRODUCT_DRAFT.insertProductOfficial", productDraft);
        if (productNo != null) {
            return productNo.toString();
        }
        return null;
    }

    @Override
    public void updateProductOfficialByDraft(ProductDraft productDraft) throws SQLException {
        sqlMapClient.update("PRODUCT_DRAFT.updateOfficialByPrimaryKey", productDraft);
    }

    @Override
    public void insertDraftFromOfficial(Product obj) throws SQLException {
        sqlMapClient.insert("PRODUCT_DRAFT.insertDraftFromOfficial", obj);
    }
    
    @Override
    public void batchUpdateProductDraft(List<ProductDraft> list) throws Exception {
        batchUpdate(list, "PRODUCT_DRAFT.ibatorgenerated_updateByPrimaryKeySelective");
    }

    @Override
    public List<ProductDraft> selectSkuByProductDraftId(Long productDraftId) throws SQLException {
        return queryForList("PRODUCT_DRAFT.productDraft_selectById", productDraftId);
    }

    @Override
    public int batchSubmitDraftTheAudit(List<ProductDraft> list) throws SQLException {
        return super.batchUpdateNt(list, "PRODUCT_DRAFT.batchSubmitDraftTheAudit");
    }

}