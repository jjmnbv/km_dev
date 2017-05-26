package com.pltfm.app.dao;

import java.sql.SQLException;
import java.util.List;

import com.kmzyc.supplier.model.ShopCategorys;

/**
 * 店内分类数据操作
 *
 * @author Administrator
 */
public interface ShopCategoryDao {
    public List<ShopCategorys> queryShopCategoryList(ShopCategorys shopCategorys) throws SQLException;

    //所有已绑定的店铺类目
    public List queryBandShopCategoryList(String categoryIds) throws SQLException;

    //查询父类下的子类
    public List queryChildrenByParentId(long categoryId) throws SQLException;

    /**
     * 根据categoryId查询类目信息
     */
    public ShopCategorys queryByPrimaryKey(long categoryId) throws SQLException;

    /**
     * 查询指定店铺所有可用的类目 (包含所有的子孙后代)
     */
    public List<ShopCategorys> queryShopCategoryForTree(ShopCategorys condition) throws SQLException;

    /**
     * 依据父类Id以及店铺ID查询属于该店铺分类的子级类目 只包含后代,不包含后代的子后代
     */
    public List<ShopCategorys> queryCategoryByParentId(ShopCategorys condition) throws SQLException;


    /**
     * 查询店铺内所有的类目,列表结构
     */
    public List<ShopCategorys> queryShopCategoryForList(ShopCategorys condition) throws SQLException;

    /**
     * 依据id 更新默认展开
     */
    public void updateIsExpandBatchById(String[] idArrays) throws SQLException;

    /**
     * 批量更新展开选项
     */
    public void updateExpandByShopId(ShopCategorys condition) throws SQLException;

    /**
     * 批量更新默认展开
     */
    public void updateIsSuggestBatchById(String[] idArrays) throws SQLException;

    /**
     * 设置店铺分类的推荐选项
     */
    public void updateSuggestByShopId(ShopCategorys condition) throws SQLException;

}
