package com.pltfm.app.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.pltfm.app.dao.BaseDao;
import com.pltfm.app.dao.StockOutDetailDAO;
import com.pltfm.app.vobject.StockOutDetail;
import com.pltfm.app.vobject.StockOutDetailExample;
import com.kmzyc.commons.exception.ServiceException;

/**
 * 出库明细单Dao的实现类
 * 
 * @author luoyi
 * @createDate 2013/08/15
 * 
 */
@Repository("stockOutDetailDao")
public class StockOutDetailDAOImpl extends BaseDao implements StockOutDetailDAO {
	
	public int countByExample(StockOutDetailExample example) throws SQLException {
		Integer count = (Integer) sqlMapClient.queryForObject("STOCK_OUT_DETAIL.countByExample", example);
		return count.intValue();
	}

	public int deleteByExample(StockOutDetailExample example) throws SQLException {
		return sqlMapClient.delete("STOCK_OUT_DETAIL.deleteByExample", example);
	}

	public int deleteByPrimaryKey(Long detailId) throws SQLException {
		StockOutDetail _key = new StockOutDetail();
		_key.setDetailId(detailId);
		return sqlMapClient.delete("STOCK_OUT_DETAIL.deleteByPrimaryKey", _key);
	}

	public void insert(StockOutDetail record) throws SQLException {
		sqlMapClient.insert("STOCK_OUT_DETAIL.insert", record);
	}

	public void insertSelective(StockOutDetail record) throws SQLException {
		sqlMapClient.insert("STOCK_OUT_DETAIL.insertSelective", record);
	}
	
	public int batchInsertStockOut(List<StockOutDetail> parameterList) throws SQLException {
		 return super.batchInsertDataNt(parameterList, "STOCK_OUT_DETAIL.insertSelective");
	}
	
	public int batchDeleteStockOutDetail(List<StockOutDetail> parameterList) throws SQLException {
		return super.batchDeleteByDataPrimaryKeyNt(parameterList, "STOCK_OUT_DETAIL.deleteByPrimaryKey");
	}
	
	public int batchUpdateStockOutDetail(List<StockOutDetail> parameterList) throws SQLException {
		return super.batchUpdateData("STOCK_OUT_DETAIL.updateByPrimaryKeySelective", parameterList);
	}

	public List<StockOutDetail> selectByExample(StockOutDetailExample example,int skip,int max)
			throws SQLException {
		return sqlMapClient.queryForList("STOCK_OUT_DETAIL.selectByExample", example,skip,max);
	}
    
	@Override
	public List<StockOutDetail> selectByExample(StockOutDetailExample example) throws SQLException {
        return sqlMapClient.queryForList("STOCK_OUT_DETAIL.selectByExample", example);
	}

	public StockOutDetail selectByPrimaryKey(Long detailId) throws SQLException {
		StockOutDetail _key = new StockOutDetail();
		_key.setDetailId(detailId);
        return (StockOutDetail) sqlMapClient.queryForObject("STOCK_OUT_DETAIL.selectByPrimaryKey", _key);
	}

	public int updateByExampleSelective(StockOutDetail record, StockOutDetailExample example) throws SQLException {
		UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        return sqlMapClient.update("STOCK_OUT_DETAIL.updateByExampleSelective", parms);
	}

	public int updateByExample(StockOutDetail record, StockOutDetailExample example) throws SQLException {
		UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
		return sqlMapClient.update("STOCK_OUT_DETAIL.updateByExample", parms);
	}

	public int updateByPrimaryKeySelective(StockOutDetail record) throws SQLException {
		return sqlMapClient.update("STOCK_OUT_DETAIL.updateByPrimaryKeySelective", record);
	}

	public int updateByPrimaryKey(StockOutDetail record) throws SQLException {
		return sqlMapClient.update("STOCK_OUT_DETAIL.updateByPrimaryKey", record);
	}

	protected static class UpdateByExampleParms extends StockOutDetailExample {
		private static final long serialVersionUID = -2448798947693521560L;
		private Object record;

		public UpdateByExampleParms(Object record, StockOutDetailExample example) {
			super(example);
			this.record = record;
		}

		public Object getRecord() {
			return record;
		}
	}

}