package com.pltfm.app.service;

import com.pltfm.app.vobject.Category;
import com.pltfm.app.vobject.CategoryDetailInfo;

import java.sql.SQLException;
import java.util.List;

/**
 * 类目业务逻辑层接口
 *
 * @author cjm
 * @since 2013-9-11
 */
public interface CategoryService {
    public List selectCategoryParent(String categoryIds) throws SQLException;

    /**
     * 按层级查询全部的物理类目
     */
    public List queryCategoryList(Category category) throws Exception;

    /**
     * 按层级查询全部的运营类目
     */
    public List queryCategoryPhyList(Category category) throws Exception;

    /**
     * 根据类目Id查询单个类目
     */
    Category selectByPrimaryKey(Integer categoryId) throws Exception;

    /**
     * 根据类目父Id查询类目
     */
    List selectByparentId(Integer parentId) throws Exception;

    /**
     * 根据类目查询排行榜产品
     */
    List<Integer> selectByCategoryIds(List<Integer> categoryIds);

    /**
     * 查询三级物理类目
     */
    public List querySubCategory() throws Exception;

    /**
     * 通过产品主键查询产品所属类目信息
     *
     * @param productId 产品主键信息
     * @return 产品所属类目信息
     * @throws Exception 异常信息
     */
    public CategoryDetailInfo selectByProductId(Integer productId) throws Exception;
}
