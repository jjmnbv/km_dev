package com.pltfm.app.dao.impl;

import com.kmzyc.supplier.model.ShopMain;
import com.kmzyc.supplier.model.example.ShopMainExample;
import com.pltfm.app.dao.BaseDao;
import com.pltfm.app.dao.ShopMainDAO;
import com.pltfm.app.vobject.ShopForExport;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Repository("shopMainDAO")
public class ShopMainDAOImpl extends BaseDao implements ShopMainDAO {

    public int countByExample(ShopMainExample example) throws SQLException {
        Integer count = (Integer)  sqlMapClient.queryForObject("SHOP_MAIN.ibatorgenerated_countByExample", example);
        return count.intValue();
    }

    public int deleteByExample(ShopMainExample example) throws SQLException {
        return sqlMapClient.delete("SHOP_MAIN.ibatorgenerated_deleteByExample", example);
    }

    public int deleteByPrimaryKey(Long shopId) throws SQLException {
        ShopMain key = new ShopMain();
        key.setShopId(shopId);
        return sqlMapClient.delete("SHOP_MAIN.ibatorgenerated_deleteByPrimaryKey", key);
    }

    public void insert(ShopMain record) throws SQLException {
        sqlMapClient.insert("SHOP_MAIN.ibatorgenerated_insert", record);
    }

    public void insertSelective(ShopMain record) throws SQLException {
        sqlMapClient.insert("SHOP_MAIN.ibatorgenerated_insertSelective", record);
    }

    public ShopMain selectByPrimaryKey(Long shopId) throws SQLException {
        ShopMain key = new ShopMain();
        key.setShopId(shopId);
        return (ShopMain) sqlMapClient.queryForObject("SHOP_MAIN.ibatorgenerated_selectByPrimaryKey", key);
    }

    public int updateByExampleSelective(ShopMain record, ShopMainExample example) throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        return sqlMapClient.update("SHOP_MAIN.ibatorgenerated_updateByExampleSelective", parms);
    }

    public int updateByPrimaryKeySelective(ShopMain record) throws SQLException {
        return sqlMapClient.update("SHOP_MAIN.ibatorgenerated_updateByPrimaryKeySelective", record);
    }

    private static class UpdateByExampleParms extends ShopMainExample {
        private Object record;

        public UpdateByExampleParms(Object record, ShopMainExample example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }

	@Override
	public List selectByExampleShopList(ShopMainExample example, int skip, int max) throws SQLException {
        return sqlMapClient.queryForList("SHOP_MAIN.ibatorgenerated_selectByExample", example,skip,max);
	}
	
	public List<ShopMain> selectBySupplierId(Long supplierId) throws SQLException {
		ShopMain ma=new ShopMain();
		ma.setSupplierId(supplierId);
        return sqlMapClient.queryForList("SHOP_MAIN.ibatorgenerated_selectBySupperliId", ma);
    }

	@Override
	public List<ShopMain> querySupplierShop() throws SQLException {
        return sqlMapClient.queryForList("SHOP_MAIN.querySupplierShop");
	}

	@Override
	public List<ShopForExport> selectSupplierShopInfoForExcel(ShopMainExample example) throws SQLException {
		return sqlMapClient.queryForList("SHOP_MAIN.selectSupplierShopInfoForExcel", example);
	}
}