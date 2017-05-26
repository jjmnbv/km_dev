package com.pltfm.app.service;

import java.util.List;

import com.kmzyc.commons.exception.ServiceException;
import com.pltfm.app.vobject.CategoryAttr;
import com.kmzyc.commons.page.Page;

/**
 * 产品类别属性业务逻辑接口
 *
 * @author humy
 * @since 2013-7-9
 */
public interface CategoryAttrService {

    /**
     * 查询类目属性列表
     *
     * @param page         分页对象
     * @param categoryAttr 类目属性基本信息
     * @return List<CategoryAttr> 类目属性列表
     * @throws ServiceException
     */
    Page queryCategoryAttrList(Page page, CategoryAttr categoryAttr) throws ServiceException;

    /**
     * 添加类目属性
     *
     * @param categoryAttr 类目基本信息
     * @return
     * @throws ServiceException
     */
    void addCategoryAttr(CategoryAttr categoryAttr) throws ServiceException;

    /**
     * 更新类目属性
     *
     * @param categoryAttr 类目基本信息
     * @return
     * @throws ServiceException
     */
    void updateCategoryAttr(CategoryAttr categoryAttr) throws ServiceException;

    /**
     * 进入编辑类目属性页面
     *
     * @param categoryAttr 类目属性基本信息
     * @return 返回值
     * @throws ServiceException 异常
     */
    CategoryAttr showCategoryAttr(CategoryAttr categoryAttr) throws ServiceException;

    /**
     * 根据主键删除类目属性
     *
     * @param delId 主键数组
     * @throws ServiceException
     */
    String deleteByPrimaryKey(String[] delId) throws ServiceException;

    /**
     * 查询类目属性列表
     *
     * @param categoryAttr 类目属性基本信息
     * @return List<CategoryAttr> 类目属性列表
     * @throws ServiceException
     */
    List<CategoryAttr> queryCategoryAttrList(CategoryAttr categoryAttr) throws ServiceException;

    /**
     * 查找属性名是否重复
     *
     * @param categoryAttr
     * @return
     * @throws ServiceException
     */
    boolean findAttrRepeatName(CategoryAttr categoryAttr) throws ServiceException;

    /**
     * 根据主键获取类目属性
     *
     * @param categoryAttrId
     * @return
     * @throws ServiceException
     */
    CategoryAttr findByPrimaryKey(Long categoryAttrId) throws ServiceException;
}
