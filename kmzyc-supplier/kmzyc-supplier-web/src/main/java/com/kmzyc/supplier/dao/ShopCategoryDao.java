package com.kmzyc.supplier.dao;

import java.sql.SQLException;
import java.util.List;

import com.kmzyc.supplier.model.ShopCategorys;

public interface ShopCategoryDao {

    /**
     * 新增 并返回其主键
     *
     * @param para
     * @throws SQLException
     */
    Long insert(ShopCategorys para) throws SQLException;

    /**
     * 查询父类目下的子类目的最大Code编码
     *
     * @param parentId
     * @return
     * @throws SQLException
     */
    String queryMaxChildCodeByParentId(long parentId) throws SQLException;

    /**
     * 根据categoryId查询类目信息
     *
     * @param categoryId
     * @return
     * @throws SQLException
     */
    ShopCategorys queryByPrimaryKey(long categoryId) throws SQLException;

    /**
     * 根据父级类目查询其所有的子孙后代
     *
     * @param condition
     * @return
     * @throws SQLException
     */
    List<ShopCategorys> queryShopCategoryList(ShopCategorys condition) throws SQLException;

    /**
     * 查询同一店铺平级类目是否有重复的名称
     *
     * @param condition
     * @return
     * @throws SQLException
     */
    Integer queryExistRepeatCategoryName(ShopCategorys condition) throws SQLException;

    /**
     * 依据父类Id以及店铺ID查询属于该店铺分类的子级类目
     *
     * @param condition
     * @return
     * @throws SQLException
     */
    List<ShopCategorys> queryCategoryByParentId(ShopCategorys condition) throws SQLException;

    /**
     * 更新类目信息
     *
     * @param para
     * @throws SQLException
     */
    void udpateShopCategory(ShopCategorys para) throws SQLException;

    /**
     * 根据父级类目的推荐情况统一其后代子级类目的推荐关系
     *
     * @param para
     * @throws SQLException
     */
    void updateIsSuggestByParent(ShopCategorys para) throws SQLException;

    /**
     * 查询是否与产品草稿有关联
     *
     * @param condition 店铺所属的供应商ID
     * @throws SQLException
     */
    Integer queryIsRelationWithDraft(ShopCategorys condition) throws SQLException;

    /**
     * 查询是否与产品草稿有关联
     *
     * @param condition 店铺所属的供应商ID
     * @throws SQLException
     */
    Integer queryIsRelationWithOfficial(ShopCategorys condition) throws SQLException;

    /**
     * 删除类目连带删除旗下的所有子类目
     *
     * @param para
     * @throws SQLException
     */
    void deleteShopCategory(ShopCategorys para) throws SQLException;

    /**
     * 根据id更新排序号
     *
     * @param categoryId
     * @param sortNo
     * @throws SQLException
     */
    void updateSortNoByCategoryId(long categoryId, int sortNo) throws SQLException;

    /**
     * 查询店铺内的统一级别的排序号是否存在
     *
     * @return
     * @throws SQLException
     */
    Integer queryIsExistSortNo(ShopCategorys para) throws SQLException;

    /**
     * 查询店铺的默认分类
     *
     * @param shopId
     * @return
     * @throws SQLException
     */
    ShopCategorys queryDefaultShopCategory(long shopId) throws SQLException;

    /**
     * 20150916 maliqun add 查询指定店铺是否有除了系统默认分配的分类是否还存在自己创建的分类
     *
     * @param shopId
     * @return
     * @throws SQLException
     */
    Integer isExistShopCateCreateBySelf(long shopId) throws SQLException;

    /**
     * 查询该店铺是否已经存在默认分类
     *
     * @param shopId
     * @return
     * @throws SQLException
     */
    Integer queryIsExistDefaultCategory(long shopId) throws SQLException;

    /**
     * 查询店铺分类名称
     * @param shopCategoryId
     * @return
     * @throws SQLException
     */
    String queryShopCategoryName(List<Long> shopCategoryId) throws SQLException;

}
