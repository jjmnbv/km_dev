package com.kmzyc.promotion.app.service;

import java.util.List;

import com.kmzyc.promotion.app.vobject.Category;
import com.kmzyc.promotion.exception.ServiceException;

/**
 * 产品类别业务逻辑接口
 * 
 * @author humy
 * @since 2013-7-9
 */
public interface CategoryService {

	/**
	 * 类目信息列表
	 * 
	 * @param category
	 *            类目信息
	 * @return List<Category> 类目信息列表
	 * @throws Exception
	 *             异常
	 */
	List<Category> queryCategoryList(Category category) throws Exception;

	/**
	 * 类目信息
	 * 
	 * @param categoryId
	 *            类目ID
	 * @return Category 类目信息
	 * @throws Exception
	 *             异常
	 */
	Category showCategory(Long categoryId) throws Exception;

	/**
	 * 添加类目
	 * 
	 * @param category
	 *            类目基本信息
	 * @return
	 * @throws Exception
	 */
	void addCategory(Category category) throws Exception;

	/**
	 * 更新类目
	 * 
	 * @param category
	 *            类目基本信息
	 * @return
	 * @throws Exception
	 */
	void updateCategory(Category category) throws Exception;

	/**
	 * 删除类目
	 * 
	 * @param category
	 *            类目基本信息
	 * @return
	 * @throws Exception
	 */
	void deleteCategory(Category category) throws Exception;

	/**
	 * 查询类目下的孩子节点
	 * 
	 * @param Category
	 *            类目基本信息
	 * @return List<Category> 类目基本信息列表
	 * @throws Exception
	 */
	List<Category> queryCategoryChildrenList(Category category) throws Exception;

	/**
	 * 查询类目及其父节点
	 * 
	 * @param Category
	 *            类目基本信息
	 * @return List<Category> 类目基本信息列表
	 * @throws Exception
	 */
	List<Category> queryCategoryParentList(Category category) throws Exception;

	/**
	 * 查询能上架的类别
	 * 
	 * @param category
	 * @return
	 * @throws Exception
	 */
	public List<Category> queryCanUpShelfCategoryList(Category category) throws Exception;

	/**
	 * 查询是否有重名
	 * 
	 * @param category
	 * @return
	 * @throws Exception
	 */
	public boolean queryRepeatName(Category category) throws Exception;

	/**
	 * 
	 * @param categoryId
	 * @return
	 * @throws Exception
	 */
	public String delCategory(Long categoryId) throws Exception;

	/**
	 * 查询与运营类目不重名的物理类目
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<Category> findSomePhyCategorys() throws Exception;

	/**
	 * 复制类目
	 * 
	 * @param copyCategoryIds
	 * @return
	 * @throws Exception
	 */
	public int copyCategorys(Long[] copyCategoryIds) throws ServiceException;

	/**
	 * 运营类目修改
	 * 
	 * @param category
	 * @throws ServiceException
	 */
	public void updateCategoryByBusiCatogory(Category category) throws ServiceException;
}
