package com.pltfm.app.dao.impl;

import com.pltfm.app.dao.BaseDao;
import com.pltfm.app.dao.ProductCertificateDraftDAO;
import com.pltfm.app.vobject.ProductCertificateDraft;
import com.pltfm.app.vobject.ProductCertificateDraftExample;
import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Repository;

@Repository("productCertificateDraftDao")
public class ProductCertificateDraftDAOImpl extends BaseDao<ProductCertificateDraft> implements ProductCertificateDraftDAO {

    public int countByExample(ProductCertificateDraftExample example) throws SQLException {
        Integer count = (Integer)  sqlMapClient.queryForObject("PRODUCT_CERTIFICATE_DRAFT.ibatorgenerated_countByExample", example);
        return count.intValue();
    }

    public int deleteByExample(ProductCertificateDraftExample example) throws SQLException {
        int rows = sqlMapClient.delete("PRODUCT_CERTIFICATE_DRAFT.ibatorgenerated_deleteByExample", example);
        return rows;
    }

    public int deleteByPrimaryKey(Long pscId) throws SQLException {
        ProductCertificateDraft key = new ProductCertificateDraft();
        key.setPscId(pscId);
        int rows = sqlMapClient.delete("PRODUCT_CERTIFICATE_DRAFT.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    public void insert(ProductCertificateDraft record) throws SQLException {
        sqlMapClient.insert("PRODUCT_CERTIFICATE_DRAFT.ibatorgenerated_insert", record);
    }
    
    @Override
	public int batchInsertDraft(List<ProductCertificateDraft> list) throws SQLException {
		return super.batchInsertDataNt(list, "PRODUCT_CERTIFICATE_DRAFT.ibatorgenerated_insert");
	}
    
    @Override
	public int batchInsertOfficialFromDraft(List<ProductCertificateDraft> list) throws SQLException {
		return super.batchInsertDataNt(list, "PRODUCT_CERTIFICATE_DRAFT.insertIntoOfficialFromDraft");
	}
    
    @Override
	public void insertDraftFromOfficialByProductId(Long productId) throws SQLException {
		sqlMapClient.insert("PRODUCT_CERTIFICATE_DRAFT.insertIntoDraftFromOfficial", productId);
	}

    public void insertSelective(ProductCertificateDraft record) throws SQLException {
        sqlMapClient.insert("PRODUCT_CERTIFICATE_DRAFT.ibatorgenerated_insertSelective", record);
    }

    public List selectByExample(ProductCertificateDraftExample example) throws SQLException {
        List list = sqlMapClient.queryForList("PRODUCT_CERTIFICATE_DRAFT.ibatorgenerated_selectByExample", example);
        return list;
    }

    public ProductCertificateDraft selectByPrimaryKey(Long pscId) throws SQLException {
        ProductCertificateDraft key = new ProductCertificateDraft();
        key.setPscId(pscId);
        ProductCertificateDraft record = (ProductCertificateDraft) sqlMapClient.queryForObject("PRODUCT_CERTIFICATE_DRAFT.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    public int updateByExampleSelective(ProductCertificateDraft record, ProductCertificateDraftExample example)
            throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = sqlMapClient.update("PRODUCT_CERTIFICATE_DRAFT.ibatorgenerated_updateByExampleSelective", parms);
        return rows;
    }

    public int updateByExample(ProductCertificateDraft record, ProductCertificateDraftExample example)
            throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = sqlMapClient.update("PRODUCT_CERTIFICATE_DRAFT.ibatorgenerated_updateByExample", parms);
        return rows;
    }

    public int updateByPrimaryKeySelective(ProductCertificateDraft record) throws SQLException {
        int rows = sqlMapClient.update("PRODUCT_CERTIFICATE_DRAFT.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    public int updateByPrimaryKey(ProductCertificateDraft record) throws SQLException {
        int rows = sqlMapClient.update("PRODUCT_CERTIFICATE_DRAFT.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }

    private static class UpdateByExampleParms extends ProductCertificateDraftExample {
        private Object record;

        public UpdateByExampleParms(Object record, ProductCertificateDraftExample example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }

}