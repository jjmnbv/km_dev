package com.kmzyc.supplier.service;


import java.util.List;

import com.kmzyc.commons.exception.ServiceException;
import com.pltfm.app.vobject.CategoryAttrValue;
/**
 * 产品类目属性值业务逻辑接口
 * @author humy
 * @since 2013-7-9
 */
public interface CategoryAttrValueService {

	/**
	 * 查询类目属性值对象
	 * @param categoryAttrValueId 类目属性值ID基本信息
	 * @return CategoryAttrValue 类目属性值
	 * @throws Exception
	 */
	CategoryAttrValue queryCategoryAttrValue(Long categoryAttrValueId) throws ServiceException;
	
	/**
	 * 查询类目属性值列表
	 * @param categoryAttrId 类目属性ID
	 * @return List<CategoryAttrValue> 类目属性值列表
	 * @throws Exception
	 */
	List<CategoryAttrValue> queryCategoryAttrValueList(Long categoryAttrId) throws ServiceException;
	
}