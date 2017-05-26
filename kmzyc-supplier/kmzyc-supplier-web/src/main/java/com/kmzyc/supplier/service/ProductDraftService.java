package com.kmzyc.supplier.service;

import com.km.framework.page.Pagination;
import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.supplier.vo.ProductDraftVo;
import com.pltfm.app.bean.ResultMessage;
import com.pltfm.app.fobject.SkuCheckAttr;
import com.pltfm.app.vobject.CategoryAttr;
import com.pltfm.app.vobject.OperationAttr;
import com.pltfm.app.vobject.ProductAttrDraft;
import com.pltfm.app.vobject.ProductDraft;

import java.util.List;

public interface ProductDraftService {

    /**
     * 分页 查找数据
     *
     * @param product
     * @param page
     * @param shopCategoryId
     */
    Pagination searchPage(ProductDraft product, String shopCategoryId, Pagination page) throws ServiceException;

    /**
     * 修改
     *
     * @param pd
     * @return
     * @throws Exception
     */
    int updateObject(ProductDraft pd, String shopCategoryIds) throws ServiceException;

    /**
     * 根据主键获取对象，并不获取clob字段
     *
     * @param productId
     * @return
     * @throws Exception
     */
    ProductDraft findByProductIdWithOutClob(Long productId) throws ServiceException;

    /**
     * 删除草稿产品
     * @param pd
     * @throws ServiceException
     */
    void deleteProductDraft(ProductDraft pd) throws ServiceException;

    /**
     * 根据商品id查询草稿产品信息
     * @param id
     * @return
     * @throws ServiceException
     */
    ProductDraftVo findProductDraftVoByProductId(Long id) throws ServiceException;

    /**
     * 根据商品id查询商品属性
     * @param id
     * @return
     * @throws ServiceException
     */
    List<ProductAttrDraft> getProductAttrDraftList(Long id) throws ServiceException;

    /**
     * 设置产品属性值
     * @param productAttrList 产品属性基本信息列表
     * @throws Exception
     */
    void setProductAttrValue(List<ProductAttrDraft> productAttrList) throws ServiceException;

    /**
     * 取产品SKU属性列表
     *
     * @param id 产品ID
     * @return List<CategoryAttr> 类目属性列表
     * @throws ServiceException 异常
     */
    List<ProductAttrDraft> getProductSkuAttrList(Long id) throws ServiceException;

    List<ProductAttrDraft> findByCondition(ProductAttrDraft productAttrDraft) throws ServiceException;

    ProductDraft findSingleProductAndSkusAndAttrValues(ProductDraft productDraft) throws ServiceException;

    List<ProductAttrDraft> findDefinitionAttr(Long id) throws ServiceException;

    /**
     * 根据产品id获取运营属性
     * @param id
     * @return
     * @throws Exception
     */
    List<ProductAttrDraft> findOperationAttr(Long id) throws ServiceException;

    /**
     * 取运营属性列表
     *
     * @return List<CategoryAttr> 运营属性列表
     * @throws Exception 异常
     */
    List<OperationAttr> getOperationAttrList() throws ServiceException;

    /**
     * 取类目属性列表
     *
     * @param categoryId 类目属性ID
     * @return List<CategoryAttr> 类目属性列表
     * @throws Exception 异常
     */
    List<CategoryAttr> getCategoryAttrList(Long categoryId) throws ServiceException;

    /**
     * 获取产品属性
     * @param productId
     * @return
     * @throws ServiceException
     */
    List<ProductAttrDraft> getProductAttrList(Long productId) throws ServiceException;

    /**
     * 设置类目属性的checkbox和checkId
     * @param productAttrList
     * @throws ServiceException
     */
    void setCategoryAttrValue(List<ProductAttrDraft> productAttrList) throws ServiceException;

    /**
     * 设置运营属性的值
     *
     * @param productId             商品id
     * @param operationAttrList     运营属性基本数据集合
     * @throws ServiceException
     */
    void setOperationAttrValue(Long productId, List<OperationAttr> operationAttrList) throws ServiceException;

    /**
     * 上架
     * @param productIds
     * @param longValue
     * @param supplierId
     * @return
     * @throws ServiceException
     */
    ResultMessage upShelf(Long[] productIds, long longValue, Long supplierId) throws ServiceException;

    /**
     * 更新运营属性
     * @param product
     * @param productId
     * @param operationAttrIds
     * @throws Exception
     */
    void updateOperationAttrValue(ProductDraft product, Long productId, Long[] operationAttrIds) throws ServiceException;

    /**
     * 更新基本属性
     * @param product
     * @param dataType
     * @throws Exception
     */
    void updateCategoryAttrValue(ProductDraft product, Integer dataType) throws ServiceException;

    void updateDefinitionAttrValue(ProductDraft product, Integer dataType) throws ServiceException;

    int updateSkuAttrValue(ProductDraft product, List<SkuCheckAttr> skuCheckAttrs,
                           List<String> oldSkuCheckedId, String toDeleteSkuIds, Integer dataType) throws ServiceException;

    /**
     * 插入产品
     *
     * @param product
     * @param skuCheckAttrs
     * @param shopCategoryIds
     * @return
     */
    Long insertProduct(ProductDraft product, List<SkuCheckAttr> skuCheckAttrs, String shopCategoryIds)
            throws ServiceException;

    int deleteProductDraftByProductId(Long productId) throws ServiceException;

    ResultMessage releaseProductPrice(Long productId) throws ServiceException;

    String previewProductDraftInfoPage(String productId) throws ServiceException;

    /**
     * 产品草稿表的更新
     *
     * @return
     */
    int productDraftModify(Integer dataType, Long loginId, ProductDraft productDraft, List<SkuCheckAttr> skuCheckAttrs,
                           List<String> oldSkuCheckedId, String toDeleteSkuIds, String shopCategoryIds)
            throws ServiceException;

}