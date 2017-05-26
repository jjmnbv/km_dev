package com.pltfm.app.service;

import java.util.List;

import com.kmzyc.commons.exception.ServiceException;
import com.pltfm.app.vobject.CategoryAttrValue;

public interface CategoryAttrValueService {
    /**
     * 查询类目属性值对象
     *
     * @param categoryAttrValueId 类目属性值ID基本信息
     * @return CategoryAttrValue 类目属性值
     * @throws ServiceException
     */
    CategoryAttrValue queryCategoryAttrValue(Long categoryAttrValueId) throws ServiceException;

    /**
     * 查询类目属性值列表
     *
     * @param categoryAttrId 类目属性ID
     * @return List<CategoryAttrValue> 类目属性值列表
     * @throws ServiceException
     */
    List<CategoryAttrValue> queryCategoryAttrValueList(Long categoryAttrId) throws ServiceException;

    /**
     * 根据目录属性值的id数组获取目录属性值
     *
     * @param categoryAttrValueIds 目录属性值的id数组
     * @return
     * @throws ServiceException
     */
    String getCategoryAttrValueByValueIds(String[] categoryAttrValueIds) throws ServiceException;
}
