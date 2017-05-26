package com.kmzyc.product.remote.service;

import java.util.List;

import com.pltfm.app.vobject.Category;

/**
 * 运营类目接口
 * @author tanyunxing
 */
public interface CategoryRemoteService {

	/**
	 * 根据主键获取运营类目
	 * @param categoryId
	 * @return
	 * @throws Exception
	 */
	Category findByCategoryId(Long categoryId) throws Exception;
	
	/**
     * 获取某个频道的运营类目 缺省时为ALL
     * 
     * @param channel B2B，All
     * @return
     * @throws Exception
     */
	List<Category> findAllBusiCategorys(String channel) throws Exception;
	
	
	/**
	 * 查询类目下的孩子节点
	 * @param category 类目基本信息
	 * @return List<Category> 类目基本信息列表
	 * @throws Exception
	 */
	List<Category> findCategoryChildrenList(Category category) throws Exception;
	
}