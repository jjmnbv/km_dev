package com.pltfm.app.dao.impl;

import com.kmzyc.supplier.model.SuppliersInfo;
import com.kmzyc.supplier.model.example.SuppliersInfoExample;
import com.pltfm.app.dao.BaseDao;
import com.pltfm.app.dao.SuppliersInfoDAO;
import com.pltfm.app.vobject.SupplierCategoryName;
import com.pltfm.app.vobject.SupplierWarehouseName;
import com.kmzyc.commons.page.Page;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;

@Component("suppliersInfoDao")
public class SuppliersInfoDAOImpl extends BaseDao implements SuppliersInfoDAO {

    @Override
    public List<SupplierCategoryName> getSupplierCategoryName(Integer levelType) throws SQLException {
        return sqlMapClient.queryForList("SUPPLIERS_INFO.getSupplierCategoryName", levelType);
    }

    @Override
    public void saveSupplierCategoryName(List<SupplierCategoryName> firstCategoryNameList) throws SQLException {
        batchinsert(firstCategoryNameList,"SUPPLIERS_INFO.saveSupplierCategoryName");
    }

    @Override
    public List<SupplierWarehouseName> getSuppliersWarehouseName() throws SQLException {
        return sqlMapClient.queryForList("SUPPLIERS_INFO.getSuppliersWarehouseName");
    }

    @Override
    public void saveSuppliersWarehouseName(List<SupplierWarehouseName> supplierWarehouseNameList) throws SQLException {
        batchinsert(supplierWarehouseNameList,"SUPPLIERS_INFO.saveSuppliersWarehouseName");
    }

    public int countByExample(SuppliersInfoExample example) throws SQLException {
        Integer count = (Integer)  sqlMapClient.queryForObject("SUPPLIERS_INFO.ibatorgenerated_countByExample", example);
        return count.intValue();
    }

    public int deleteByExample(SuppliersInfoExample example) throws SQLException {
        int rows = sqlMapClient.delete("SUPPLIERS_INFO.ibatorgenerated_deleteByExample", example);
        return rows;
    }

    public int deleteByPrimaryKey(Long supplierId) throws SQLException {
        SuppliersInfo key = new SuppliersInfo();
        key.setSupplierId(supplierId);
        int rows = sqlMapClient.delete("SUPPLIERS_INFO.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    public void insert(SuppliersInfo record) throws SQLException {
        sqlMapClient.insert("SUPPLIERS_INFO.ibatorgenerated_insert", record);
    }

    public void insertSelective(SuppliersInfo record) throws SQLException {
        sqlMapClient.insert("SUPPLIERS_INFO.ibatorgenerated_insertSelective", record);
    }

    public List selectByExample(SuppliersInfoExample example,Page page) throws SQLException {
        List list = sqlMapClient.queryForList("SUPPLIERS_INFO.ibatorgenerated_selectByExample", example,
        		(page.getPageNo()-1)*page.getPageSize(),page.getPageSize());
        return list;
    }

    public SuppliersInfo selectByPrimaryKey(Long supplierId) throws SQLException {
        SuppliersInfo key = new SuppliersInfo();
        key.setSupplierId(supplierId);
        SuppliersInfo record = (SuppliersInfo) sqlMapClient.queryForObject("SUPPLIERS_INFO.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    public int updateByExampleSelective(SuppliersInfo record, SuppliersInfoExample example) throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = sqlMapClient.update("SUPPLIERS_INFO.ibatorgenerated_updateByExampleSelective", parms);
        return rows;
    }

    public int updateByExample(SuppliersInfo record, SuppliersInfoExample example) throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = sqlMapClient.update("SUPPLIERS_INFO.ibatorgenerated_updateByExample", parms);
        return rows;
    }

    public int updateByPrimaryKeySelective(SuppliersInfo record) throws SQLException {
        int rows = sqlMapClient.update("SUPPLIERS_INFO.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    public int updateByPrimaryKey(SuppliersInfo record) throws SQLException {
        int rows = sqlMapClient.update("SUPPLIERS_INFO.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }

    private static class UpdateByExampleParms extends SuppliersInfoExample {
        private Object record;

        public UpdateByExampleParms(Object record, SuppliersInfoExample example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }

	@Override
	public SuppliersInfo selectByMerchantId(Long merchantId) throws SQLException {
		SuppliersInfo supplier = new SuppliersInfo();
		supplier.setMerchantId(merchantId);
		return (SuppliersInfo)sqlMapClient.queryForObject("SUPPLIERS_INFO.ibatorgenerated_selectByMerId",supplier);
	}

	@Override
	public SuppliersInfo selectByMerchantId(SuppliersInfo supplier) throws SQLException {
		return (SuppliersInfo)sqlMapClient.queryForObject("SUPPLIERS_INFO.selectByMerchantId",supplier);
	}

    @Override
    public Integer getSupplierTypeByProductDraft(Long productId) throws SQLException {
        return (Integer)sqlMapClient.queryForObject("SUPPLIERS_INFO.getSupplierTypeByProductDraft",productId);
    }

    @Override
    public Integer getSupplierTypeByProduct(Long productId) throws SQLException {
        return (Integer)sqlMapClient.queryForObject("SUPPLIERS_INFO.getSupplierTypeByProduct",productId);
    }

}