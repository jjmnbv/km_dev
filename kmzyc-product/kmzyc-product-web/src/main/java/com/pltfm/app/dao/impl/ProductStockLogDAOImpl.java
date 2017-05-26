package com.pltfm.app.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Component;

import com.pltfm.app.dao.BaseDao;
import com.pltfm.app.dao.ProductStockLogDAO;
import com.pltfm.app.vobject.ProductStockLog;
import com.pltfm.app.vobject.ProductStockLogExample;
import com.kmzyc.commons.page.Page;
@Component("productStockLogDao")
public class ProductStockLogDAOImpl extends BaseDao implements ProductStockLogDAO {
    
    public int countByExample(ProductStockLogExample example) throws SQLException {
        Integer count = (Integer)  sqlMapClient.queryForObject("PRODUCT_STOCK_LOG.ibatorgenerated_countByExample", example);
        return count.intValue();
    }

    public int deleteByExample(ProductStockLogExample example) throws SQLException {
        return sqlMapClient.delete("PRODUCT_STOCK_LOG.ibatorgenerated_deleteByExample", example);
    }

    public int deleteByPrimaryKey(Long logId) throws SQLException {
        ProductStockLog key = new ProductStockLog();
        key.setLogId(logId);
        return sqlMapClient.delete("PRODUCT_STOCK_LOG.ibatorgenerated_deleteByPrimaryKey", key);
    }

    public void insert(ProductStockLog record) throws SQLException {
        sqlMapClient.insert("PRODUCT_STOCK_LOG.ibatorgenerated_insert", record);
    }

    public void insertSelective(ProductStockLog record) throws SQLException {
        sqlMapClient.insert("PRODUCT_STOCK_LOG.ibatorgenerated_insertSelective", record);
    }

    public List selectByExample(ProductStockLogExample example,Page page) throws SQLException {
        return sqlMapClient.queryForList("PRODUCT_STOCK_LOG.ibatorgenerated_selectByExample", example,
        		(page.getPageNo() - 1) * page.getPageSize(), page.getPageSize());
    }

    public ProductStockLog selectByPrimaryKey(Long logId) throws SQLException {
        ProductStockLog key = new ProductStockLog();
        key.setLogId(logId);
        return (ProductStockLog) sqlMapClient.queryForObject("PRODUCT_STOCK_LOG.ibatorgenerated_selectByPrimaryKey", key);
    }

    public int updateByExampleSelective(ProductStockLog record, ProductStockLogExample example) throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        return sqlMapClient.update("PRODUCT_STOCK_LOG.ibatorgenerated_updateByExampleSelective", parms);
    }

    public int updateByExample(ProductStockLog record, ProductStockLogExample example) throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        return sqlMapClient.update("PRODUCT_STOCK_LOG.ibatorgenerated_updateByExample", parms);
    }

    public int updateByPrimaryKeySelective(ProductStockLog record) throws SQLException {
        return sqlMapClient.update("PRODUCT_STOCK_LOG.ibatorgenerated_updateByPrimaryKeySelective", record);
    }

    public int updateByPrimaryKey(ProductStockLog record) throws SQLException {
        return sqlMapClient.update("PRODUCT_STOCK_LOG.ibatorgenerated_updateByPrimaryKey", record);
    }

    private static class UpdateByExampleParms extends ProductStockLogExample {
        private Object record;

        public UpdateByExampleParms(Object record, ProductStockLogExample example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }

	@Override
	public void batchAddStockLog(List<ProductStockLog> productStockLogList) throws SQLException {
		super.batchInsertData(productStockLogList,"PRODUCT_STOCK_LOG.ibatorgenerated_insert");
	}
    
}