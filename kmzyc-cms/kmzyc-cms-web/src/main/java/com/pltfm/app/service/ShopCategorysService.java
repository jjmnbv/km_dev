package com.pltfm.app.service;

import java.sql.SQLException;
import java.util.List;

import com.kmzyc.supplier.model.ShopCategorys;

/**
 * 店铺类目服务
 *
 * @author xkj
 */
public interface ShopCategorysService {
    /*
     * 店铺所有类目
     */
    public List<ShopCategorys> queryShopCategoryList(ShopCategorys shopCategorys) throws Exception;

    public ShopCategorys queryByPrimaryKey(long categoryId) throws SQLException;

    /**
     * 获取到店铺内所有的类目
     * 树结构
     */
    public List<ShopCategorys> getAllShopCategorysForTree(Long shopId) throws Exception;

    /**
     * 获取到店铺内所有的类目
     * 列表结构  不分推荐和不推荐
     */
    public List<ShopCategorys> getAllShopCategorysForList(Long shopId) throws Exception;


    /**
     * 或的店铺中指定父级类目的子类目
     * 一级类目的parent_id 为0
     */
    public List<ShopCategorys> queryShopCategoryListByParentId(long parentId) throws Exception;


    /**
     * 根据类目id获得类目实体
     */
    public ShopCategorys queryShopCategoryById(long shopCategoryId) throws Exception;


    /**
     * 查询指定店铺 的所有推荐类目,包含一级和二级
     * 树结构类型的数据
     */
    public List<ShopCategorys> querySuggestShopCategoryForTree(long shopId) throws Exception;


    /**
     * 批量更新 默认展开选项
     */
    public void updateIsExpandById(String[] idArrays, long shopId) throws Exception;

    /**
     * 更新默认推荐以及是否展开
     */
    public void updateSuggestBatchId(String[] idArrays, long shopId, String isExpand) throws Exception;
}
