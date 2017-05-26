package com.kmzyc.supplier.service;

import java.util.List;
import java.util.Map;

import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.supplier.model.ShopCategorys;

public interface ShopCategoryService {

    /**
     * 新增类目
     *
     * @param para
     * @throws ServiceException
     */
    ShopCategorys saveShopCategory(ShopCategorys para) throws ServiceException;

    /**
     * 根据categoryId查询category实体
     *
     * @param categoryId
     * @throws ServiceException
     */
    ShopCategorys queryCategoryById(long categoryId) throws ServiceException;

    /**
     * 根据shopCategoryIdList查询店铺分类名称
     *
     * @param shopCategoryIdList
     * @throws ServiceException
     */
    String queryShopCategoryName(List<Long> shopCategoryIdList) throws ServiceException;


    /**
     * 根据父级id查询其所有的子节点
     *
     * @return
     * @throws ServiceException
     */
    List<ShopCategorys> queryShopCategoryList(ShopCategorys condition) throws ServiceException;


    /**
     * 按指定的父级Id查询某店铺该父类类目的子级,包含子孙级
     *
     * @param parentId
     * @param shopId
     * @return
     * @throws ServiceException
     */
    List<ShopCategorys> queryShopCategoryByParentId(long parentId, long shopId) throws ServiceException;

    /**
     * 查询同一店铺平级类目是否有重复名称
     *
     * @param condition
     * @return
     * @throws ServiceException
     */
    boolean queryIsExistCategoryName(ShopCategorys condition) throws ServiceException;

    /**
     * 更新店内分类类目信息
     *
     * @param para
     * @throws ServiceException
     */
    void updateShopCategoryList(ShopCategorys para) throws ServiceException;

    /**
     * 查询该店内分类是否在有被产品表做了引用
     *
     * @param condition
     * @return
     * @throws ServiceException
     */
    String isRelatedWithProduct(ShopCategorys condition) throws ServiceException;

    /**
     * 删除类目
     *
     * @param para
     * @throws ServiceException
     */
    void deleteShopCategory(ShopCategorys para) throws ServiceException;

    /**
     * 交换排序号
     *
     * @param sourceCategoryId 源类目id
     * @param sourceSortNo     源类目排序号
     * @param targetCategoryId 目标替换类目id
     * @param targetSortNo     目标替换排序号
     * @throws ServiceException
     */
    void updateShopCategorySortNo(long sourceCategoryId, int sourceSortNo, long targetCategoryId, int targetSortNo)
            throws ServiceException;

    /**
     * 用于查询指定的排序号是否存在
     *
     * @param para
     * @return
     * @throws ServiceException
     */
    boolean queryIsExistSortNo(ShopCategorys para) throws ServiceException;

    /**
     * 根据店铺Id查询默认分类
     *
     * @param shopId
     * @return
     * @throws ServiceException
     */
    ShopCategorys queryDefaultShopCategoryByShopId(long shopId) throws ServiceException;

    /**
     * 查询指定店铺是否存在除了系统默认分配的分类以外是否自己还存了分类
     *
     * @param shopId
     * @return
     * @throws ServiceException
     */
    boolean queryIsExistShopCategoryCreateBySelf(long shopId) throws ServiceException;

    /**
     * 当店铺审核通过时,给店铺自动分类默认的店内分类
     *
     * @param shopId
     * @return key:0表示添加成功 key:-1 表示添加失败 key:1 表示已经存在店内的默认分类
     * @throws ServiceException
     */
    Map<String, String> addDefaultShopCategoryForShop(Long shopId, Long loginUserId) throws ServiceException;
}