package com.kmzyc.supplier.dao;

import java.sql.SQLException;
import java.util.List;

import com.pltfm.app.vobject.WarehouseInfo;

/**
 * 仓库管理
 * @author Administrator
 *
 */
public interface WareHouseDao {
	
	/**
	 * 查询所有可启用的仓库列表
	 * @return
	 * @throws SQLException
	 */
	List<WarehouseInfo> queryAllEnableWarehouse() throws SQLException;
	
	
	/**
	 * 查询供应商所拥有的仓库列表
	 * @return
	 * @throws SQLException
	 */
	List<WarehouseInfo> queryWarehouseBySuppliersId(String suppliersId) throws SQLException;
}