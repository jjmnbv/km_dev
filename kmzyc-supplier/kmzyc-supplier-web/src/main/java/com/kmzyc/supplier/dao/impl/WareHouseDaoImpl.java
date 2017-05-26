package com.kmzyc.supplier.dao.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.kmzyc.supplier.dao.WareHouseDao;
import com.pltfm.app.vobject.WarehouseInfo;

@Repository("wareHouseDao")
public class WareHouseDaoImpl extends BaseDAO implements WareHouseDao {
	
	@Resource
	private SqlMapClient sqlMapClient;

	@Override
	public List<WarehouseInfo> queryAllEnableWarehouse() throws SQLException {
		return sqlMapClient.queryForList("WAREHOUSE_INFO.queryAllEnableWarehouse");
	}

	@Override
	public List<WarehouseInfo> queryWarehouseBySuppliersId(String supplierId) throws SQLException {
		return sqlMapClient.queryForList("WAREHOUSE_INFO.queryWarehouseBySuppliersId",supplierId);
	}

}
