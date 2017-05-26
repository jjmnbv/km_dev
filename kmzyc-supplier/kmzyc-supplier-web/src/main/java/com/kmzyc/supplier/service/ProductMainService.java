package com.kmzyc.supplier.service;

import java.util.List;

import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.supplier.vo.ProductMainVo;
import com.km.framework.page.Pagination;
import com.pltfm.app.bean.ResultMessage;
import com.pltfm.app.fobject.SkuCheckAttr;
import com.pltfm.app.vobject.OperationAttr;
import com.pltfm.app.vobject.Product;
import com.pltfm.app.vobject.ProductAttr;
import com.pltfm.app.vobject.ProductSku;

public interface ProductMainService {

    /**
     * 分页查找数据
     *
     * @param product
     * @param page
     * @param shopCategoryId
     */
    Pagination searchPage(Product product, String shopCategoryId, Pagination page) throws ServiceException;

    /**
     * 分页查询与店内分类关联的商品
     *
     * @param product
     * @param page
     * @return
     * @throws Exception
     */
    Pagination queryRelationWithShopCategoryPage(Product product, Pagination page) throws ServiceException;

    /**
     * 分页查询尚未与店内分类相关的商品
     *
     * @param product
     * @param page
     * @return
     * @throws Exception
     */
    Pagination queryUnRelationWithShopCategoryPage(Product product, Pagination page) throws ServiceException;

    /**
     * 根据ID查询产品
     *
     * @param productId
     * @return
     * @throws Exception
     */
    ProductMainVo findProductMainVoByProductId(Long productId) throws ServiceException;

    /**
     * 取产品属性列表
     *
     * @param productId 产品ID
     * @return List<ProductAttr> 产品属性列表
     * @throws Exception 异常
     */
    List<ProductAttr> getProductAttrList(Long productId) throws ServiceException;

    /**
     * 设置产品属性值
     *
     * @param productAttrList 产品属性基本信息列表
     * @return
     * @throws Exception 异常
     */
    void setProductAttrValue(List<ProductAttr> productAttrList) throws ServiceException;

    /**
     * 取产品SKU列表
     *
     * @param productId 产品ID
     * @return List<CategoryAttr> 类目属性列表
     * @throws Exception 异常
     */
    List<ProductSku> getProductSkuAttrList(Long productId) throws ServiceException;

    /**
     * 商品发布列表下架
     *
     * @param productList
     * @return
     * @throws Exception
     */
    boolean productDownShelf(List<Product> productList) throws ServiceException;

    /**
     * 商品发布列表上架
     *
     * @param productList
     * @return
     * @throws Exception
     */
    ResultMessage productUpShelf(List<Product> productList) throws ServiceException;

    /**
     * 根据编号查找产品
     *
     * @param id
     * @throws Exception
     */
    Product findProductById(Long id) throws ServiceException;

    /**
     * 设置类目属性值
     *
     * @param productAttrList 产品属性基本信息列表
     * @return
     * @throws Exception 异常
     */
    void setCategoryAttrValue(List<ProductAttr> productAttrList) throws ServiceException;

    /**
     * 取运营属性列表
     *
     * @return List<CategoryAttr> 运营属性列表
     * @throws Exception 异常
     */
    List<OperationAttr> getOperationAttrList() throws ServiceException;

    /**
     * 设置运营属性值
     *
     * @param productId         产品Id
     * @param operationAttrList 运营属性基本信息列表
     * @return
     * @throws Exception 异常
     */
    void setOperationAttrValue(Long productId, List<OperationAttr> operationAttrList) throws ServiceException;

    /**
     * 产品明细页预览
     *
     * @param productId
     * @return
     * @throws Exception
     */
    String previewProductInfoPage(String productId) throws ServiceException;

    /**
     * 更新操作
     *
     * @param supplierId
     * @param loginId
     * @param product
     * @param skuCheckAttrs
     * @param oldSkuCheckedId
     * @param toDeleteSkuIds
     * @return
     * @throws Exception
     */
    int productUpdate(Long supplierId, Long loginId, Product product,
                      List<SkuCheckAttr> skuCheckAttrs, List<String> oldSkuCheckedId, String toDeleteSkuIds,
                      String shopCategoryIds) throws ServiceException;

    /**
     * 查询产品SKU属性以及图片
     *
     * @param product
     * @return
     * @throws Exception
     */
    List<ProductSku> findSingleProductAndSkusAndAttrValuesAndImages(Product product) throws ServiceException;

    /**
     * 批量更新产品
     *
     * @param list
     * @return
     * @throws Exception
     */
    int updateProductBatch(List<Product> list) throws ServiceException;
}