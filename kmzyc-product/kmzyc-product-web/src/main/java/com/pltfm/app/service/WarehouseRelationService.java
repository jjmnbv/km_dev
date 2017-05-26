package com.pltfm.app.service;

import com.kmzyc.commons.exception.ServiceException;
import com.pltfm.app.vobject.WarehouseRelation;

public interface WarehouseRelationService {

	/**
	 * 添加仓库关系
	 * @param relation
	 * @return
	 * @throws ServiceException
	 */
	Long insert(WarehouseRelation relation) throws ServiceException;
	
	/**
	 * 修改仓库关系
	 * @param relation
	 * @return
	 * @throws ServiceException
	 */
	int update(WarehouseRelation relation) throws ServiceException;
	
}