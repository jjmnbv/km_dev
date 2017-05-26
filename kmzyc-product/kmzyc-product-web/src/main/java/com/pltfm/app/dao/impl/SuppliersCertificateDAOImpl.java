package com.pltfm.app.dao.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.kmzyc.supplier.model.SuppliersCertificate;
import com.kmzyc.supplier.model.example.SuppliersCertificateExample;
import com.pltfm.app.dao.BaseDao;
import com.pltfm.app.dao.SuppliersCertificateDAO;

@Component("suppliersCertificateDAO")
public class SuppliersCertificateDAOImpl extends BaseDao implements SuppliersCertificateDAO {

    public int countByExample(SuppliersCertificateExample example) throws SQLException {
        Integer count = (Integer)  sqlMapClient.queryForObject("SUPPLIERS_CERTIFICATE.ibatorgenerated_countByExample", example);
        return count.intValue();
    }

    public int deleteByExample(SuppliersCertificateExample example) throws SQLException {
        return sqlMapClient.delete("SUPPLIERS_CERTIFICATE.ibatorgenerated_deleteByExample", example);
    }

    public int deleteByPrimaryKey(Long scId) throws SQLException {
        SuppliersCertificate key = new SuppliersCertificate();
        key.setScId(scId);
        int rows = sqlMapClient.delete("SUPPLIERS_CERTIFICATE.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    public void insert(SuppliersCertificate record) throws SQLException {
        sqlMapClient.insert("SUPPLIERS_CERTIFICATE.ibatorgenerated_insert", record);
    }

    public void insertSelective(SuppliersCertificate record) throws SQLException {
        sqlMapClient.insert("SUPPLIERS_CERTIFICATE.ibatorgenerated_insertSelective", record);
    }

    public List selectByExample(SuppliersCertificateExample example) throws SQLException {
        List list = sqlMapClient.queryForList("SUPPLIERS_CERTIFICATE.ibatorgenerated_selectByExample", example);
        return list;
    }

    public SuppliersCertificate selectByPrimaryKey(Long scId) throws SQLException {
        SuppliersCertificate key = new SuppliersCertificate();
        key.setScId(scId);
        SuppliersCertificate record = (SuppliersCertificate) sqlMapClient.queryForObject("SUPPLIERS_CERTIFICATE.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    public int updateByExampleSelective(SuppliersCertificate record, SuppliersCertificateExample example) throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = sqlMapClient.update("SUPPLIERS_CERTIFICATE.ibatorgenerated_updateByExampleSelective", parms);
        return rows;
    }

    public int updateByExample(SuppliersCertificate record, SuppliersCertificateExample example) throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = sqlMapClient.update("SUPPLIERS_CERTIFICATE.ibatorgenerated_updateByExample", parms);
        return rows;
    }

    public int updateByPrimaryKeySelective(SuppliersCertificate record) throws SQLException {
        int rows = sqlMapClient.update("SUPPLIERS_CERTIFICATE.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    public int updateByPrimaryKey(SuppliersCertificate record) throws SQLException {
        int rows = sqlMapClient.update("SUPPLIERS_CERTIFICATE.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }

    private static class UpdateByExampleParms extends SuppliersCertificateExample {
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
	public List<SuppliersCertificate> selectSuppIdList(Long suppId)
			throws SQLException {
		SuppliersCertificate suppliersCertificate=new SuppliersCertificate();
		suppliersCertificate.setSupplierId(suppId);
		return sqlMapClient.queryForList("SUPPLIERS_CERTIFICATE.ibatorgenerated_selectBySuppId",suppliersCertificate);
	}

	public SuppliersCertificate selectSuppIdPath(String path) throws SQLException {
		SuppliersCertificate suppliersCertificate=new SuppliersCertificate();
		suppliersCertificate.setFilePath(path);
		return (SuppliersCertificate)sqlMapClient.queryForObject("SUPPLIERS_CERTIFICATE.ibatorgenerated_selectBySuppPath",
                suppliersCertificate);
    }
}