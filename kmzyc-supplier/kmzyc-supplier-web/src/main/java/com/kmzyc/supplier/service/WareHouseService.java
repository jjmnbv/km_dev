package com.kmzyc.supplier.service;

import java.util.List;

import com.kmzyc.commons.exception.ServiceException;
import com.pltfm.app.vobject.WarehouseInfo;

/**
 * 仓库管理
 * @author Administrator
 *
 */
public interface WareHouseService {

	/**
	 * 查询所有可启用的仓库列表
	 * @return
	 * @throws ServiceException
	 */
	List<WarehouseInfo> queryAllEnableWarehouse() throws ServiceException;
	
	
	/**
	 * 查询供应商所拥有的仓库列表
	 * @return
	 * @throws ServiceException
	 */
	List<WarehouseInfo> queryWarehouseBySuppliersId(String suppliersId) throws ServiceException;
}
