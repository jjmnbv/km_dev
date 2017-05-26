package com.pltfm.app.service;

import com.kmzyc.commons.exception.ServiceException;
import com.pltfm.app.vobject.OperationAttr;
import com.pltfm.app.vobject.ProductAttr;
import com.pltfm.app.vobject.ProductAttrDraft;
import com.pltfm.app.vobject.ProductDraft;

import java.util.List;

public interface ProductAttrDraftService {

    /**
     * 添加记录
     *
     * @param productAttrDraft
     * @throws Exception
     */
    void addProductAttrDraft(ProductAttrDraft productAttrDraft) throws ServiceException;

    /**
     * 批量更新属性
     *
     * @param productAttrDraftList
     * @throws ServiceException
     */
    void batchUpdateProductAttrDraft(List<ProductAttrDraft> productAttrDraftList) throws ServiceException;

    /**
     * 条件查询
     *
     * @return
     * @throws ServiceException
     */
    List<ProductAttrDraft> findByCondition(ProductAttrDraft productAttrDraft) throws ServiceException;

    /**
     * 根据产品Id查找产品属性
     *
     * @param productId
     * @return
     * @throws ServiceException
     */
    List<ProductAttrDraft> findProductDraftAttrByProductId(Long productId) throws ServiceException;

    /**
     * 查找类目属性
     *
     * @param productId
     * @return
     * @throws ServiceException
     */
    List<ProductAttrDraft> findCategoryAttr(Long productId) throws ServiceException;

    /**
     * 查找SKU属性
     *
     * @param productId
     * @return
     * @throws ServiceException
     */
    List<ProductAttrDraft> findSkuAttr(Long productId) throws ServiceException;

    /**
     * 查找自定义属性
     *
     * @param productId
     * @return
     * @throws ServiceException
     */
    List<ProductAttrDraft> findDefinitionAttr(Long productId) throws ServiceException;

    /**
     * 查找运营属性
     *
     * @param productId
     * @return
     * @throws ServiceException
     */
    List<ProductAttrDraft> findOperationAttr(Long productId) throws ServiceException;

    /**
     * 批量插入正式表
     *
     * @param list
     * @throws ServiceException
     */
    void batchInsertOfficial(List<ProductAttrDraft> list) throws ServiceException;

    /**
     * 批量插入草稿表
     *
     * @param list
     * @throws ServiceException
     */
    void batchInsertDraft(List<ProductAttrDraft> list) throws ServiceException;

    /**
     * 删除产品属性草稿表信息
     *
     * @param productId
     * @throws ServiceException
     */
    void deleteDraftByProductId(Long productId) throws ServiceException;

    /**
     * 编辑类目属性，修改哪些是选择的数据
     *
     * @param productAttrList
     * @throws ServiceException
     */
    void changeCategoryAttrValue(List<ProductAttrDraft> productAttrList) throws ServiceException;

    /**
     * 编辑运营属性，修改哪些是选择的数据
     *
     * @param productId
     * @param operationAttrList
     * @throws ServiceException
     */
    void changeOperationAttrValue(Long productId, List<OperationAttr> operationAttrList) throws ServiceException;

    /**
     * 更新产品SKU属性
     *
     * @param prodAttrList
     * @throws ServiceException
     */
    void updateCategoryAttrValue(ProductDraft product, List<ProductAttrDraft> prodAttrList) throws ServiceException;

    /**
     * 更新自定义属性
     *
     * @param productId
     * @param productAttrDraftList
     */
    void updateDefinitionAttrValue(ProductDraft product, Long productId, List<ProductAttrDraft> productAttrDraftList)
            throws ServiceException;

    /**
     * 更新运营属性
     *
     * @param productId
     * @param operationAttrIds
     */
    void updateOperationAttrValue(ProductDraft product, Long productId, Long[] operationAttrIds) throws ServiceException;

    /**
     * 批量保存草稿数据（数据来源至正式表）
     *
     * @param list
     * @throws ServiceException
     */
    void batchInsertDraftFromOfficial(List<ProductAttr> list) throws ServiceException;

}