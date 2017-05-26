package com.pltfm.app.dao;

import java.sql.SQLException;
import java.util.List;

import com.pltfm.app.vobject.Category;
import com.pltfm.app.vobject.CategoryExample;
import com.pltfm.app.vobject.CategoryPv;
import com.kmzyc.commons.page.Page;

public interface CategoryDAO {

    int countByExample(CategoryExample example) throws SQLException;

    int deleteByExample(CategoryExample example) throws SQLException;

    int deleteByPrimaryKey(Long categoryId) throws SQLException;

    Long insert(Category record) throws SQLException;

    void insertSelective(Category record) throws SQLException;

    List selectByExample(CategoryExample example) throws SQLException;

    Category selectByPrimaryKey(Long categoryId) throws SQLException;

    int updateByExampleSelective(Category record, CategoryExample example) throws SQLException;

    int updateByExample(Category record, CategoryExample example) throws SQLException;

    int updateByPrimaryKeySelective(Category record) throws SQLException;

    int updateByPrimaryKey(Category record) throws SQLException;

    List<Category> queryCategoryList(Category record) throws SQLException;

    List<Category> queryCategoryRebateList(Category record) throws SQLException;

    List<Category> queryCategoryParentList(Category record) throws SQLException;

    String queryMaxCategoryNoByParentId(Long parentId) throws SQLException;

    int queryRepeatName(Category record) throws SQLException;

    /**
     * 查询是否与产品有关联
     *
     * @param categoryId
     * @return
     * @throws SQLException
     */
    List<String> findIsRelationCateId(Long categoryId) throws SQLException;

    List<Category> findSomePhyCategories() throws SQLException;

    int relationDelete(Long categoryId) throws SQLException;

    /**
     * 根据三级类目查询出一级类目的id
     *
     * @param categoryId
     * @return
     * @throws SQLException
     */
    Category findCategoryId(Long categoryId) throws SQLException;

    /**
     * 删除佣金类目表数据
     *
     * @return
     * @throws SQLException
     */
    void delRebateCategory() throws SQLException;

    /**
     * 佣金类目添加表数据
     *
     * @param listCategories
     * @return
     * @throws SQLException
     */
    void addRebateCategory(List<Category> listCategories) throws SQLException;

    /**
     * 类目一级类目列表
     *
     * @param category 类目信息
     * @return List<Category> 类目信息列表
     * @throws Exception 异常
     */
    List<Category> queryCategoryOneLevelList(Category category, Page page) throws SQLException;

    /**
     * 类目一级类目列表总条数
     *
     * @param category
     * @return
     * @throws SQLException
     */
    int countItemCategories(Category category) throws SQLException;

    List<CategoryPv> queryPVCategoryList() throws SQLException;

    /**
     * 删除三级类目PV比例
     *
     * @throws SQLException
     */
    void delPvCategory() throws SQLException;

    /**
     * 新增三级类目PV比例
     *
     * @param categoryList
     * @throws SQLException
     */
    void addPvCategory(List<Category> categoryList) throws SQLException;
}