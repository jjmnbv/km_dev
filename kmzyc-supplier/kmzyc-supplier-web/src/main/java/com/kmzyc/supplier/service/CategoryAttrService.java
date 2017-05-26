package com.kmzyc.supplier.service;

import java.util.List;

import com.kmzyc.commons.exception.ServiceException;
import com.pltfm.app.vobject.Category;
import com.pltfm.app.vobject.CategoryAttr;
import com.pltfm.app.vobject.Sections;

/**
 * 产品类别属性业务逻辑接口
 * @author humy
 * @since 2013-7-9queryCategoryAttrList
 */
public interface CategoryAttrService {

	/**
	 * 查询类目属性列表
	 * @param categoryAttr 类目属性基本信息
	 * @return List<CategoryAttr> 类目属性列表
	 * @throws ServiceException
	 */
	List<CategoryAttr> queryCategoryAttrList(CategoryAttr categoryAttr) throws ServiceException;
}