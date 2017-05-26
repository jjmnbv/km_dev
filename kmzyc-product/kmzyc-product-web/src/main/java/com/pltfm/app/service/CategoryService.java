package com.pltfm.app.service;

import java.util.List;

import com.pltfm.app.vobject.Category;
import com.pltfm.app.vobject.CategoryPv;
import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.commons.page.Page;

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
     * @param category 类目信息
     * @return List<Category> 类目信息列表
     * @throws ServiceException 异常
     */
    List<Category> queryCategoryList(Category category) throws ServiceException;

    /**
     * 类目佣金信息列表
     *
     * @param category 类目信息
     * @return List<Category> 类目信息列表
     * @throws ServiceException 异常
     */
    List<Category> queryCategoryRebateList(Category category) throws ServiceException;

    /**
     * 类目信息
     *
     * @param categoryId 类目ID
     * @return Category 类目信息
     * @throws ServiceException 异常
     */
    Category showCategory(Long categoryId) throws ServiceException;

    /**
     * 添加类目
     *
     * @param category 类目基本信息
     * @return
     * @throws ServiceException
     */
    void addCategory(Category category) throws ServiceException;

    /**
     * 更新类目
     *
     * @param category 类目基本信息
     * @return
     * @throws ServiceException
     */
    void updateCategory(Category category) throws ServiceException;

    /**
     * 查询类目下的孩子节点
     *
     * @param category 类目基本信息
     * @return List<Category> 类目基本信息列表
     * @throws ServiceException
     */
    List<Category> queryCategoryChildrenList(Category category) throws ServiceException;

    /**
     * 查询类目及其父节点
     *
     * @param category 类目基本信息
     * @return List<Category> 类目基本信息列表
     * @throws ServiceException
     */
    List<Category> queryCategoryParentList(Category category) throws ServiceException;

    /**
     * 查询是否有重名
     *
     * @param category
     * @return
     * @throws ServiceException
     */
    boolean queryRepeatName(Category category) throws ServiceException;

    /**
     * @param categoryId
     * @return
     * @throws ServiceException
     */
    String delCategory(Long categoryId) throws ServiceException;

    /**
     * 查询与运营类目不重名的物理类目
     *
     * @return
     * @throws ServiceException
     */
    List<Category> findSomePhyCategories() throws ServiceException;

    /**
     * 复制类目
     *
     * @param copyCategoryIds
     * @return
     * @throws ServiceException
     */
    int copyCategories(Long[] copyCategoryIds) throws ServiceException;

    /**
     * 删除运营类目的图片
     *
     * @param categoryId
     * @throws ServiceException
     */
    void delCategoryFile(Long categoryId) throws ServiceException;

    /**
     * 删除佣金类目表数据
     *
     * @return
     * @throws ServiceException
     */
    void delRebateCategory() throws ServiceException;

    /**
     * 佣金类目添加表数据
     *
     * @param listCategories
     * @return
     * @throws ServiceException
     */
    void addRebateCategory(List<Category> listCategories) throws ServiceException;

    /**
     * 分页查询出一级类目的分页信息
     *
     * @param page
     * @throws ServiceException
     */
    void queryCategoryOneLevelList(Category category, Page page) throws ServiceException;

    /**
     * 查询三级类目PV设置
     *
     * @return
     * @throws ServiceException
     */
    List<CategoryPv> queryPVCategoryList() throws ServiceException;

    /**
     * 修改三级类目PV比例
     *
     * @param categoryList
     * @throws ServiceException
     */
    void modifyPvCategory(List<Category> categoryList) throws ServiceException;
}
