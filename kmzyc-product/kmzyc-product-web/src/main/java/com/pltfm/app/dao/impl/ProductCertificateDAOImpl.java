package com.pltfm.app.dao.impl;

import com.pltfm.app.dao.BaseDao;
import com.pltfm.app.dao.ProductCertificateDAO;
import com.pltfm.app.vobject.ProductCertificate;
import com.pltfm.app.vobject.ProductCertificateExample;
import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Repository;

@Repository("productCertificateDao")
public class ProductCertificateDAOImpl extends BaseDao<ProductCertificate> implements ProductCertificateDAO {

    public int countByExample(ProductCertificateExample example) throws SQLException {
        Integer count = (Integer)  sqlMapClient.queryForObject("PRODUCT_CERTIFICATE.ibatorgenerated_countByExample", example);
        return count.intValue();
    }

    public int deleteByExample(ProductCertificateExample example) throws SQLException {
        int rows = sqlMapClient.delete("PRODUCT_CERTIFICATE.ibatorgenerated_deleteByExample", example);
        return rows;
    }

    public int deleteByPrimaryKey(Long pscId) throws SQLException {
        ProductCertificate key = new ProductCertificate();
        key.setPscId(pscId);
        int rows = sqlMapClient.delete("PRODUCT_CERTIFICATE.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    public void insert(ProductCertificate record) throws SQLException {
        sqlMapClient.insert("PRODUCT_CERTIFICATE.ibatorgenerated_insert", record);
    }

    public void insertSelective(ProductCertificate record) throws SQLException {
        sqlMapClient.insert("PRODUCT_CERTIFICATE.ibatorgenerated_insertSelective", record);
    }

    public List selectByExample(ProductCertificateExample example) throws SQLException {
        List list = sqlMapClient.queryForList("PRODUCT_CERTIFICATE.ibatorgenerated_selectByExample", example);
        return list;
    }

    public ProductCertificate selectByPrimaryKey(Long pscId) throws SQLException {
        ProductCertificate key = new ProductCertificate();
        key.setPscId(pscId);
        ProductCertificate record = (ProductCertificate) sqlMapClient.queryForObject("PRODUCT_CERTIFICATE.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    public int updateByExampleSelective(ProductCertificate record, ProductCertificateExample example)
            throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = sqlMapClient.update("PRODUCT_CERTIFICATE.ibatorgenerated_updateByExampleSelective", parms);
        return rows;
    }

    public int updateByExample(ProductCertificate record, ProductCertificateExample example)
            throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = sqlMapClient.update("PRODUCT_CERTIFICATE.ibatorgenerated_updateByExample", parms);
        return rows;
    }

    public int updateByPrimaryKeySelective(ProductCertificate record) throws SQLException {
        int rows = sqlMapClient.update("PRODUCT_CERTIFICATE.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    public int updateByPrimaryKey(ProductCertificate record) throws SQLException {
        int rows = sqlMapClient.update("PRODUCT_CERTIFICATE.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }

    private static class UpdateByExampleParms extends ProductCertificateExample {
        private Object record;

        public UpdateByExampleParms(Object record, ProductCertificateExample example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }
}