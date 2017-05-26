package com.pltfm.app.dao.impl;

import com.pltfm.app.dao.BaseDao;
import com.pltfm.app.dao.ProductPriceRecordDAO;
import com.pltfm.app.vobject.ProductPriceRecord;
import com.pltfm.app.vobject.ProductPriceRecordExample;
import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Repository;

/**
 *  商品价格变更
 */
@Repository("productPriceRecordDao")
public class ProductPriceRecordDAOImpl extends BaseDao<ProductPriceRecord> implements ProductPriceRecordDAO {

    public void insert(ProductPriceRecord record) throws SQLException {
        sqlMapClient.insert("PRODUCT_PRICE_RECORD.insert", record);
    }

	@Override
	public int batchInsertRecord(List<ProductPriceRecord> list) throws SQLException {
		return super.batchInsertDataNt(list, "PRODUCT_PRICE_RECORD.insert");
	}

    @Override
    public List<ProductPriceRecord> getRecordList(Long productId) throws SQLException {
        return sqlMapClient.queryForList("PRODUCT_PRICE_RECORD.getRecordList", productId);
    }
}