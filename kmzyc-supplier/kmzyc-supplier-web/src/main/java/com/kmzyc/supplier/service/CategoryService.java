package com.kmzyc.supplier.service;

import java.util.List;
import java.util.Map;

import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.promotion.app.vobject.PromotionInfo;
import com.kmzyc.supplier.model.SuppliersAvailableCategorys;
import com.pltfm.app.bean.ResultMessage;
import com.pltfm.app.vobject.Category;

/**
 * 产品类别业务逻辑接口
 */
public interface CategoryService {

    /**
     * 查询类目及其父节点
     *
     * @param category 类目基本信息
     * @return List<Category> 类目基本信息列表
     * @throws ServiceException
     */
    List<Category> queryCategoryParentList(Category category) throws ServiceException;

    /**
     * 查询类目下的孩子节点
     *
     * @param category 类目基本信息
     * @return List<Category> 类目基本信息列表
     * @throws ServiceException
     */
    List<Category> queryCategoryChildrenList(Category category, List<SuppliersAvailableCategorys> categoryList)
            throws ServiceException;

    List<Category> queryCategoryList(Category category) throws ServiceException;

    /**
     * 选择供应商一级或者二级目录（权限范围） <note> 因二级目录佣金修改 </note>
     *
     * @param map 类目基本信息
     * @return List<Category> 类目基本信息列表
     * @throws ServiceException
     */
    List<Category> selectCategoryWithSupplyAvailable(Map<String, Object> map) throws ServiceException;

    Integer promotionIsLock(PromotionInfo promotion) throws ServiceException;

    /**
     * 检查sku的pv值是否正确
     *
     * @param skuPrice   sku价格
     * @param skuPvValue pv值
     * @param categoryId 类目id
     * @return
     * @throws ServiceException
     */
    ResultMessage checkSkuPvValue(Double skuPrice, Double skuPvValue, Long categoryId) throws ServiceException;

    /**
     * 检查所有的sku的pv值是否正确
     * @param skuPrice
     * @param skuPvValue
     * @param categoryId
     * @return
     * @throws ServiceException
     */
    ResultMessage checkAllSkuPvValue(String[] skuPrice, String[] skuPvValue, Long categoryId) throws ServiceException;
}
