package com.pltfm.app.dao.impl;

import com.pltfm.app.dao.BaseDao;
import com.pltfm.app.dao.ViewProductSkuDAO;
import com.pltfm.app.vobject.ViewProductSku;
import com.pltfm.app.vobject.ViewProductSkuExample;
import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 定向发布产品视图
 * @author tanyunxing
 *
 */
@Repository("viewProductSkuDao")
public class ViewProductSkuDAOImpl extends BaseDao implements ViewProductSkuDAO {

    public int countByExample(ViewProductSkuExample example) throws SQLException {
        Integer count = (Integer)  sqlMapClient.queryForObject("VIEW_PRODUCT_SKU.ibatorgenerated_countByExample", example);
        return count.intValue();                                                 
    }
    
    public int countByExampleByUser(ViewProductSkuExample example) throws SQLException {
    	Integer count = (Integer)  sqlMapClient.queryForObject("VIEW_PRODUCT_SKU.ibatorgenerated_countByExampleByUser", example);
    	return count.intValue();                                                 
    }

    public int deleteByExample(ViewProductSkuExample example) throws SQLException {
        return sqlMapClient.delete("VIEW_PRODUCT_SKU.ibatorgenerated_deleteByExample", example);
    }

    public void insert(ViewProductSku record) throws SQLException {
        sqlMapClient.insert("VIEW_PRODUCT_SKU.ibatorgenerated_insert", record);
    }

    public void insertSelective(ViewProductSku record) throws SQLException {
        sqlMapClient.insert("VIEW_PRODUCT_SKU.ibatorgenerated_insertSelective", record);
    }

    public List selectByExample(ViewProductSkuExample example) throws SQLException {
        return sqlMapClient.queryForList("VIEW_PRODUCT_SKU.ibatorgenerated_selectByExample", example);
    }
    
    public List selectSKUAttrByExample(ViewProductSkuExample example) throws SQLException{
    	return sqlMapClient.queryForList("VIEW_PRODUCT_SKU.ibatorgenerated_selectSKUAttrByExample", example);
    }
    
    public List selectByExample(ViewProductSkuExample example,int skip,int max) throws SQLException {
    	return sqlMapClient.queryForList("VIEW_PRODUCT_SKU.ibatorgenerated_selectSKUAttrByExample", example,skip,max);
    }
    
    public List selectByExampleByUser(ViewProductSkuExample example,int skip,int max) throws SQLException {
    	return sqlMapClient.queryForList("VIEW_PRODUCT_SKU.ibatorgenerated_selectSKUAttrByExampleByUser", example,skip,max);
    }

    public int updateByExampleSelective(ViewProductSku record, ViewProductSkuExample example) throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        return sqlMapClient.update("VIEW_PRODUCT_SKU.ibatorgenerated_updateByExampleSelective", parms);
    }

    public int updateByExample(ViewProductSku record, ViewProductSkuExample example) throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        return sqlMapClient.update("VIEW_PRODUCT_SKU.ibatorgenerated_updateByExample", parms);
    }

    private static class UpdateByExampleParms extends ViewProductSkuExample {
        private Object record;

        public UpdateByExampleParms(Object record, ViewProductSkuExample example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }

	public List selectByExampleForFreightByUser(ViewProductSkuExample example, int skip, int max) 
            throws SQLException {
		return sqlMapClient.queryForList("VIEW_PRODUCT_SKU.selectSKUListForFreightByExampleByUser", example,skip,max);
	}

}