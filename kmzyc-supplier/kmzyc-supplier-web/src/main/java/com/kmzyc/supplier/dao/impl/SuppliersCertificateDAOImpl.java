package com.kmzyc.supplier.dao.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.kmzyc.supplier.model.SuppliersCertificate;
import com.kmzyc.supplier.model.example.SuppliersCertificateExample;
import com.kmzyc.supplier.dao.SuppliersCertificateDAO;

@Repository(value = "suppliersCertificateDAO")
public class SuppliersCertificateDAOImpl extends BaseDAO<SuppliersCertificate> implements SuppliersCertificateDAO {

    @Resource
    private SqlMapClient sqlMapClient;

    public SuppliersCertificateDAOImpl() {

    }

    public int countByExample(SuppliersCertificateExample example) throws SQLException {
        Integer count = (Integer) sqlMapClient.queryForObject("SUPPLIERS_CERTIFICATE.countByExample", example);
        return count.intValue();
    }

    public int deleteByExample(SuppliersCertificateExample example) throws SQLException {
        return sqlMapClient.delete("SUPPLIERS_CERTIFICATE.deleteByExample", example);
    }

    public int deleteByPrimaryKey(Long scId) throws SQLException {
        SuppliersCertificate _key = new SuppliersCertificate();
        _key.setScId(scId);
        return sqlMapClient.delete("SUPPLIERS_CERTIFICATE.deleteByPrimaryKey", _key);
    }

    public void insert(SuppliersCertificate record) throws SQLException {
        sqlMapClient.insert("SUPPLIERS_CERTIFICATE.insert", record);
    }

    public void insertSelective(SuppliersCertificate record) throws SQLException {
        sqlMapClient.insert("SUPPLIERS_CERTIFICATE.insertSelective", record);
    }

    public List selectByExample(SuppliersCertificateExample example) throws SQLException {
        return sqlMapClient.queryForList("SUPPLIERS_CERTIFICATE.selectByExample", example);
    }

    public List selectCertificateInfoList(SuppliersCertificate suppliersCertificate) throws SQLException {
        return sqlMapClient.queryForList("SUPPLIERS_CERTIFICATE.selectCertificateInfoList", suppliersCertificate);
    }

    public SuppliersCertificate selectByPrimaryKey(Long scId) throws SQLException {
        SuppliersCertificate _key = new SuppliersCertificate();
        _key.setScId(scId);
        SuppliersCertificate record = (SuppliersCertificate) sqlMapClient.queryForObject("SUPPLIERS_CERTIFICATE.selectByPrimaryKey", _key);
        return record;
    }

    public int updateByExampleSelective(SuppliersCertificate record, SuppliersCertificateExample example) throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        return sqlMapClient.update("SUPPLIERS_CERTIFICATE.updateByExampleSelective", parms);
    }

    public int updateByExample(SuppliersCertificate record, SuppliersCertificateExample example) throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        return sqlMapClient.update("SUPPLIERS_CERTIFICATE.updateByExample", parms);
    }

    public int updateByPrimaryKeySelective(SuppliersCertificate record) throws SQLException {
        return sqlMapClient.update("SUPPLIERS_CERTIFICATE.updateByPrimaryKeySelective", record);
    }

    public int updateByPrimaryKey(SuppliersCertificate record) throws SQLException {
        return sqlMapClient.update("SUPPLIERS_CERTIFICATE.updateByPrimaryKey", record);
    }

    protected static class UpdateByExampleParms extends SuppliersCertificateExample {
        private Object record;

        public UpdateByExampleParms(Object record, SuppliersCertificateExample example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }

    @Override
    public int countBySupplierId(SuppliersCertificate record) throws SQLException {
        return (Integer) sqlMapClient.queryForObject("SUPPLIERS_CERTIFICATE.countCertificate", record);
    }
}