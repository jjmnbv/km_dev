package com.pltfm.app.dao.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pltfm.app.enums.ProductStatus;
import com.pltfm.app.vobject.ViewProductSku;
import com.pltfm.app.vobject.ViewProductSkuAndStock;
import org.springframework.stereotype.Repository;

import com.pltfm.app.dao.BaseDao;
import com.pltfm.app.dao.StockOutDAO;
import com.pltfm.app.vobject.StockOut;
import com.pltfm.app.vobject.StockOutAftersaleReturn;
import com.pltfm.app.vobject.StockOutAndDetail;
import com.pltfm.app.vobject.StockOutDetail;
import com.pltfm.app.vobject.StockOutExample;
import com.kmzyc.commons.exception.ServiceException;

/**
 * 出库单Dao层的实现类
 * 
 * @author luoyi
 * @createDate 2013/08/15
 */

@Repository(value = "stockOutDao")
public class StockOutDAOImpl extends BaseDao implements StockOutDAO {

	public int countByExample(StockOutExample example) throws SQLException {
		Integer count = (Integer) sqlMapClient.queryForObject(
				"STOCK_OUT.countByExample", example);
		return count.intValue();
	}

	public int deleteByExample(StockOutExample example) throws SQLException {
        return sqlMapClient.delete("STOCK_OUT.deleteByExample", example);
	}

	public int deleteByPrimaryKey(Long stockOutId) throws SQLException {
		StockOut _key = new StockOut();
		_key.setStockOutId(stockOutId);
        return sqlMapClient.delete("STOCK_OUT.deleteByPrimaryKey", _key);
	}

	public void insert(StockOut record) throws SQLException {
		sqlMapClient.insert("STOCK_OUT.insert", record);
	}

	public void insertSelective(StockOut record) throws SQLException {
		sqlMapClient.insert("STOCK_OUT.insertSelective", record);
	}

	public List<StockOut> selectByExample(StockOutExample example, int skip,
			int max) throws SQLException {
        return sqlMapClient.queryForList(
				"STOCK_OUT.selectByExample", example, skip, max);
	}

	@Override
	public List<StockOut> selectByExample(StockOutExample example)
			throws SQLException {
        return sqlMapClient.queryForList(
				"STOCK_OUT.selectByExample", example);
	}

	public StockOut selectByPrimaryKey(Long stockOutId) throws SQLException {
		StockOut _key = new StockOut();
		_key.setStockOutId(stockOutId);
        return (StockOut) sqlMapClient.queryForObject(
				"STOCK_OUT.selectByPrimaryKey", _key);
	}

	public int updateByExampleSelective(StockOut record, StockOutExample example)
			throws SQLException {
		UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        return sqlMapClient.update("STOCK_OUT.updateByExampleSelective",
				parms);
	}

	public int updateByExample(StockOut record, StockOutExample example)
			throws SQLException {
		UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        return sqlMapClient.update("STOCK_OUT.updateByExample", parms);
	}

	public int updateByPrimaryKeySelective(StockOut record) throws SQLException {
        return sqlMapClient.update("STOCK_OUT.updateByPrimaryKeySelective",
				record);
	}

	public int updateByPrimaryKey(StockOut record) throws SQLException {
        return sqlMapClient.update("STOCK_OUT.updateByPrimaryKey", record);
	}

	public int batchUpdateForStockOut(String statementName,
			List<StockOut> parameterList) throws SQLException {
		return super.batchUpdateData(statementName, parameterList);
	}

	public int batchDeleteForStockOut(String statementName,
			List<StockOut> parameterList) throws SQLException {
		return super.batchDeleteByDataPrimaryKeyNt(parameterList, statementName);
	}

	protected static class UpdateByExampleParms extends StockOutExample {
		private static final long serialVersionUID = -1563633967566700465L;
		private Object record;

		public UpdateByExampleParms(Object record, StockOutExample example) {
			super(example);
			this.record = record;
		}

		public Object getRecord() {
			return record;
		}
	}

	@Override
	public List<StockOutAndDetail> selectStockOutDetailByStockOutId(
			List<StockOut> stockOutList) throws SQLException {
		return sqlMapClient.queryForList("STOCK_OUT.findStockOutdetailByStockOutId", stockOutList);
	}

	@Override
	public Long checkStockOutByWarehouse(List<Long> warehouseIdList) throws SQLException {
		return (Long) sqlMapClient.queryForObject("STOCK_OUT.checkStockOutByWarehouse", warehouseIdList);
	}

	@Override
	public List<StockOut> selectStockOutListByStockOutIds(List<Long> stockOutIdList)
			throws SQLException {
		return sqlMapClient.queryForList("STOCK_OUT.selectStockOutListByStockOutIds", stockOutIdList);
	}

	@Override
	public void batchInsertStockOut(String statementName,
                                    List<StockOutDetail> parameterList) throws ServiceException {
		 super.batchInsertData(parameterList, statementName);
	}
	
	@Override
	public Long insertStockOutSelective(StockOut record) throws SQLException {
        return (Long) sqlMapClient.insert("STOCK_OUT.insertSelective", record);
	}
	
	public List<StockOutAftersaleReturn> selectStockOutAfterReturnListByReturnNo(List<StockOut> stockOutReturnNos) throws SQLException{
		return sqlMapClient.queryForList("STOCK_OUT.selectStockOutAfterReturnListByReturnNos", stockOutReturnNos);
	}

	@Override
	public Map<String,String> getStockOutByOrderNo(List<String> orderNoList)
			throws SQLException {
		return sqlMapClient.queryForMap("STOCK_OUT.getStockOutByOrderNo",orderNoList,"billNo","billNo");
	}

    @Override
    public int countViewProductSkuByExample(ViewProductSku viewProductSku, Long warehouseId) throws SQLException {
        Map pMap = new HashMap<String, Object>();
        pMap.put("warehouseId", warehouseId);
        pMap.put("productTitle", viewProductSku.getProductTitle());
        pMap.put("productSkuCode", viewProductSku.getProductSkuCode());
        pMap.put("categoryId", viewProductSku.getCategoryId());
        pMap.put("mCategoryId", viewProductSku.getMCategoryId());
        pMap.put("bCategoryId", viewProductSku.getBCategoryId());
        pMap.put("searchBrandName", viewProductSku.getSearchBrandName());
        // 产品状态(2-5)=AUDIT("2","已审核,待上架"),SYSDOWN("5","系统下架");
        pMap.put("statusBegin", ProductStatus.AUDIT.getStatus());
        pMap.put("statusEnd", ProductStatus.SYSDOWN.getStatus());
        Integer count = (Integer) sqlMapClient.queryForObject("VIEW_PRODUCT_SKU.countProductSkuByObjectList", pMap);
        return count.intValue();
    }

    @Override
    public List viewProductSkuByObjectList(ViewProductSku viewProductSku, int skip, int max, Long warehouseId)
            throws SQLException {
        Map<String, Object> pMap = new HashMap();
        pMap.put("warehouseId", warehouseId);
        pMap.put("productTitle", viewProductSku.getProductTitle());
        pMap.put("productSkuCode", viewProductSku.getProductSkuCode());
        pMap.put("categoryId", viewProductSku.getCategoryId());
        pMap.put("mCategoryId", viewProductSku.getMCategoryId());
        pMap.put("bCategoryId", viewProductSku.getBCategoryId());
        pMap.put("searchBrandName", viewProductSku.getSearchBrandName());
        // 产品状态(2-5)=AUDIT("2","已审核,待上架"),SYSDOWN("5","系统下架");
        pMap.put("statusBegin", ProductStatus.AUDIT.getStatus());
        pMap.put("statusEnd", ProductStatus.SYSDOWN.getStatus());
        return sqlMapClient.queryForList("VIEW_PRODUCT_SKU.viewProductSkuByObjectList",
                pMap, skip, max);
    }
}
