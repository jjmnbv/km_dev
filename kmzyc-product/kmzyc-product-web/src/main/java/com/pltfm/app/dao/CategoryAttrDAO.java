package com.pltfm.app.dao;

import java.sql.SQLException;
import java.util.List;

import com.pltfm.app.vobject.CategoryAttr;
import com.pltfm.app.vobject.CategoryAttrExample;
import com.kmzyc.commons.page.Page;

public interface CategoryAttrDAO {

    int countByExample(CategoryAttrExample example) throws SQLException;

    int deleteByExample(CategoryAttrExample example) throws SQLException;

    int deleteByPrimaryKey(Long categoryAttrId) throws SQLException;

    void insert(CategoryAttr record) throws SQLException;

    void insertSelective(CategoryAttr record) throws SQLException;

    List selectByExample(CategoryAttrExample example) throws SQLException;

    CategoryAttr selectByPrimaryKey(Long categoryAttrId) throws SQLException;

    int updateByExampleSelective(CategoryAttr record, CategoryAttrExample example) throws SQLException;

    int updateByExample(CategoryAttr record, CategoryAttrExample example) throws SQLException;

    int updateByPrimaryKeySelective(CategoryAttr record) throws SQLException;

    int updateByPrimaryKey(CategoryAttr record) throws SQLException;

    /**
     * 获取类目属性信息列表
     *
     * @param page         分页对象
     * @param categoryAttr 类目属性信息实体
     * @return 分页类目属性信息列表
     * @throws Exception 异常
     */
    Page selectPageByVo(Page page, CategoryAttr categoryAttr) throws SQLException;

    /**
     * 查找重命名
     *
     * @param categoryAttr
     * @return
     * @throws SQLException
     */
    int findRepeatAttrName(CategoryAttr categoryAttr) throws SQLException;

    /**
     * 检测该属性Id是否被关联
     *
     * @param categoryAttrId
     * @return
     * @throws SQLException
     */
    String checkIsRelationAttr(Long categoryAttrId) throws SQLException;
}