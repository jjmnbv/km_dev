package com.kmzyc.supplier.service;


import java.util.List;

import com.kmzyc.commons.exception.ServiceException;
import com.pltfm.app.vobject.OperationAttr;
/**
 * 运营属性业务逻辑接口
 * 
 */
public interface OperationAttrService {
	
	/**
	 * 查询运营属性
	 * @param operationAttrId 运营属性ID
	 * @return OperationAttr 运营属性对象
	 * @throws Exception
	 */
	OperationAttr queryOperationAttr(Long operationAttrId) throws ServiceException;
	
	/**
	 * 查询运营属性列表
	 * @return List<OperationAttr> 运营属性列表
	 * @throws Exception
	 */
	List<OperationAttr> queryOperationAttrList() throws ServiceException;
}
