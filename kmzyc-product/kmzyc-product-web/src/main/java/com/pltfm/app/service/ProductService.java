package com.pltfm.app.service;

import com.kmzyc.commons.exception.ServiceException;
import com.pltfm.app.bean.ResultMessage;
import com.pltfm.app.vobject.CategoryAttr;
import com.pltfm.app.vobject.OperationAttr;
import com.pltfm.app.vobject.Product;
import com.pltfm.app.vobject.ProductAttr;
import com.pltfm.app.vobject.ProductSku;
import com.pltfm.app.vobject.ProductSkuForExport;
import com.kmzyc.commons.page.Page;

import java.util.List;
import java.util.Map;

public interface ProductService {

    Long insertProduct(Product product, List<String> skuCheckedId) throws ServiceException;

    /**
     * 分页 查找数据
     *
     * @param page
     * @param product
     * @param type
     */
    void searchPage(Page page, Product product, String type) throws ServiceException;

    /**
     * 根据编号查找产品
     *
     * @param id
     * @throws ServiceException
     */
    Product findProductById(Long id) throws ServiceException;

    boolean updateProductById(Product product) throws ServiceException;

    void executeUpdateCachePrice(List<Long> skuIdList) throws ServiceException;

    /**
     * 更新缓存中的价格
     *
     * @param skuIdList
     * @throws ServiceException
     */
    void updateCachePrice(List<Long> skuIdList) throws ServiceException;

    /**
     * 检查SKU库存
     *
     * @param skuList
     * @param message
     * @return
     * @throws ServiceException
     */
    ResultMessage checkStockForDelProduct(List<ProductSku> skuList, ResultMessage message) throws ServiceException;

    /**
     * 产品关联表、明细表中不包含sku返回true
     *
     * @param skuList
     * @return
     * @throws ServiceException
     */
    ResultMessage checkProductRelationForDelProduct(List<ProductSku> skuList, ResultMessage message)
            throws ServiceException;

    /**
     * 优惠券产品表COUPON_PRODUCT表中不包含sku返回true
     *
     * @param skuList
     * @return
     * @throws ServiceException
     */
    ResultMessage checkCouponForDelProduct(List<ProductSku> skuList, ResultMessage message) throws ServiceException;

    /**
     * 商品上架
     *
     * @param productList
     * @return
     * @throws ServiceException
     */
    ResultMessage upShelf(List<Product> productList) throws ServiceException;

    /**
     * 商品下架
     *
     * @param productList
     * @return
     * @throws ServiceException
     */
    boolean downShelf(List<Product> productList) throws ServiceException;

    /**
     * 取类目属性列表
     *
     * @param categoryId 类目属性ID
     * @return List<CategoryAttr> 类目属性列表
     * @throws ServiceException 异常
     */
    List<CategoryAttr> getCategoryAttrList(Long categoryId) throws ServiceException;

    /**
     * 取产品属性列表
     *
     * @param productId 产品ID
     * @return List<ProductAttr> 产品属性列表
     * @throws ServiceException 异常
     */
    List<ProductAttr> getProductAttrList(Long productId) throws ServiceException;

    /**
     * 取运营属性列表
     *
     * @return List<CategoryAttr> 运营属性列表
     * @throws ServiceException 异常
     */
    List<OperationAttr> getOperationAttrList() throws ServiceException;

    /**
     * 设置产品属性值
     *
     * @param productAttrList 产品属性基本信息列表
     * @return
     * @throws ServiceException 异常
     */
    void setProductAttrValue(List<ProductAttr> productAttrList) throws ServiceException;

    /**
     * 设置运营属性值
     *
     * @param productId         产品Id
     * @param operationAttrList 运营属性基本信息列表
     * @return
     * @throws ServiceException 异常
     */
    void setOperationAttrValue(Long productId, List<OperationAttr> operationAttrList) throws ServiceException;

    /**
     * 更新产品类目属性值
     *
     * @param productAttrList 类目属性基本信息列表
     * @return
     * @throws ServiceException 异常
     */
    void updateCategoryAttrValue(List<ProductAttr> productAttrList) throws ServiceException;

    /**
     * 更新产品SKU属性
     *
     * @param productId       产品Id
     * @param productNo       产品编号
     * @param productAttrList 类目属性基本信息列表
     * @return
     * @throws ServiceException 异常
     */
    void updateSkuAttrValue(Long productId, String productNo, List<ProductAttr> productAttrList,List<String> skuCheckedId,
                            List<String> oldSkuCheckedId, String toDeleteSkuIds) throws ServiceException;

    /**
     * 更新产品自定义属性值
     *
     * @param productId       产品Id
     * @param productAttrList 产品属性基本信息列表
     * @return
     * @throws ServiceException 异常
     */
    void updateDefinitionAttrValue(Long productId, List<ProductAttr> productAttrList) throws ServiceException;

    /**
     * 更新运营属性值
     *
     * @param productId        产品Id
     * @param operationAttrIds 运营属性数组
     * @return
     * @throws ServiceException 异常
     */
    void updateOperationAttrValue(Long productId, Long[] operationAttrIds) throws ServiceException;

    /**
     * 查询产品表中所有可以进行上架的商品
     *
     * @return
     */
    List getCanUpShelfPro(Page page, Product product) throws ServiceException;

    /**
     * 产品审核
     *
     * @param productId
     * @return
     * @throws ServiceException
     */
    boolean auditProductById(Long productId) throws ServiceException;

    /**
     * 产品批量审核
     *
     * @param productIdList
     * @return
     * @throws ServiceException
     */
    ResultMessage batchAuditProduct(List<Long> productIdList) throws ServiceException;

    /**
     * 删除产品
     *
     * @param productId
     * @return
     * @throws ServiceException
     */
    ResultMessage deleteProductById(Long productId) throws ServiceException;

    /**
     * 产品提交审核
     *
     * @param productId
     * @return
     * @throws ServiceException
     */
    boolean submitTheAudit(Long productId) throws ServiceException;

    /**
     * 取产品SKU列表
     *
     * @param productId 产品ID
     * @return List<CategoryAttr> 类目属性列表
     * @throws ServiceException 异常
     */
    List<ProductSku> getProductSkuAttrList(Long productId) throws ServiceException;

    /**
     * 设置类目属性值
     *
     * @param productAttrList 产品属性基本信息列表
     * @return
     * @throws ServiceException 异常
     */
    void setCategoryAttrValue(List<ProductAttr> productAttrList) throws ServiceException;

    /**
     * 检查产品重名
     *
     * @param productName
     * @return
     * @throws ServiceException
     */
    ResultMessage checkProductName(String productName) throws ServiceException;

    /**
     * 检查产品的介绍是否脏
     *
     * @param productIntroduct
     * @return
     * @throws ServiceException
     */
    boolean checkIntro(String productIntroduction) throws ServiceException;

    /**
     * 产品信息更改或者新增后推送MQ消息给搜索引擎
     *
     * @param ids
     * @param opType
     * @throws ServiceException
     */
    void changeProductInfoNotify(List<Long> ids, String opType) throws ServiceException;

    /**
     * 批量查询产品是否具有到货通知运营属性
     *
     * @param productIdList
     * @return
     * @throws ServiceException
     */
    Map<Long, ProductAttr> checkOperationAttr(List<Long> productIdList) throws ServiceException;

    /**
     * 根据产品编号获得产品对象
     *
     * @param productNo
     * @return
     * @throws ServiceException
     */
    Product queryProductByProductNo(String productNo) throws ServiceException;

    /**
     * 产品明细页预览
     *
     * @param productId
     * @return
     * @throws ServiceException
     */
    String previewProductInfoPage(String productId) throws ServiceException;

    /**
     * 分页 查找数据,并根据用户的渠道进行过滤
     *
     * @param page
     * @param product
     * @param type
     */
    void searchPage(Page page, Product product, String type, Integer loginUserId) throws ServiceException;

    void searchPageByUserIdForPrice(Page page, Product product, String type, Integer loginUserId)
            throws ServiceException;

    void searchPageByUserIdForPV(Page page, Product product, String type, Integer loginUserId)
            throws ServiceException;

    void searchCertificateProductPageByUserId(Page page, Product product, String type, Integer loginUserId)
            throws ServiceException;

    /**
     * 查询sku
     *
     * @param page
     * @param product
     * @param type
     * @throws ServiceException
     */
    void searchSkuPage(Page page, Product product, String type, Integer loginUserId) throws ServiceException;

    void exportExcelForProductSku(List<ProductSkuForExport> productList) throws ServiceException;

    List<ProductSkuForExport> exportProductInfo(Product product, String type) throws ServiceException;

    List<Integer> getProductIdByBrandId(Long brandId) throws ServiceException;

    /**
     * 上架发布所属供应商全部产品页面
     *
     * @param supplierIds
     * @return
     * @throws ServiceException
     */
    ResultMessage upShelfForSupplier(List<Long> supplierIds) throws ServiceException;

    /**
     * 下架指定供应商ID
     *
     * @param supplierIds
     * @return
     * @throws ServiceException
     */
    ResultMessage downShelfForSupplier(List<Long> supplierIds) throws ServiceException;

    /**
     * 规格产品上架
     *
     * @param product 违规产品
     */
    ResultMessage illegalUp(Product product) throws ServiceException;

    /**
     * 违规产品下架
     *
     * @param product 违规产品
     *                <note>
     *                productId     违规产品id
     *                reasons       违规下架原因
     *                </note>
     */
    ResultMessage illegalDown(Product product) throws ServiceException;

}