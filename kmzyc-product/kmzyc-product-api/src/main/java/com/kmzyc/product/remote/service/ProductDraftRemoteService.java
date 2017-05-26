package com.kmzyc.product.remote.service;

import java.util.List;

import com.pltfm.app.bean.ResultMessage;
import com.pltfm.app.fobject.SkuCheckAttr;
import com.pltfm.app.vobject.Product;
import com.pltfm.app.vobject.ProductDraft;
import com.pltfm.app.vobject.ProductImage;
import com.pltfm.app.vobject.ProductImageDraft;
import com.pltfm.app.vobject.ProductSkuDraft;
import com.kmzyc.commons.exception.ServiceException;

public interface ProductDraftRemoteService {

    /**
     * 增加产品草稿数据
     *
     * @param product
     * @param skuCheckAttrs
     * @return
     * @throws ServiceException
     */
    Long insertProduct(ProductDraft product, List<SkuCheckAttr> skuCheckAttrs) throws ServiceException;

    /**
     * 更新对象
     *
     * @throws ServiceException
     */
    int updateObject(ProductDraft product) throws ServiceException;

    /**
     * 批量更新
     *
     * @param list
     * @throws ServiceException
     */
    int batchUpdateObject(List<ProductDraft> list) throws ServiceException;

    /**
     * 根据产品Id复制正式表数据到草稿表中
     *
     * @param productId      产品Id
     * @param isExistProduct 是否已存在产品
     * @return
     * @throws ServiceException
     */
    int copyOfficialDataToDraft(Long productId, String isExistProduct) throws ServiceException;

    /**
     * 根据主键删除产品草稿信息
     *
     * @param productId
     * @throws ServiceException
     */
    int deleteProductDraftByProductId(Long productId) throws ServiceException;

    /**
     * 检查是否符合上架条件
     *
     * @param productIds
     * @return
     * @throws ServiceException
     */
    ResultMessage checkUpshelf(List<Long> productIds) throws ServiceException;

    /**
     * 状态为新增时的上架
     *
     * @param productIds
     * @return
     * @throws ServiceException
     */
    ResultMessage upshelf(List<Long> productIds, Long loginId, Long supplierId) throws ServiceException;

    /**
     * 正式表的更新，向草稿表中插入数据
     *
     * @param product
     * @param skuCheckAttrs
     * @throws ServiceException
     */
    int insertDraftFromProduct(ProductDraft product, List<SkuCheckAttr> skuCheckAttrs) throws ServiceException;

    /**
     * 产品草稿表的更新
     *
     * @return
     */
    int productDraftUpdate(Integer dataType, Long loginId, ProductDraft productDraft, List<SkuCheckAttr> skuCheckAttrs,
                           List<String> oldSkuCheckedId, String toDeleteSkuIds) throws ServiceException;

    /**
     * 查找是否有草稿表中是否有相同的记录
     *
     * @param productId 产品Id
     * @return 0：没有相同的记录。1：存在更新所有信息的记录。2：存在单独修改价格的记录
     */
    int findSameProductFromDraft(Long productId) throws ServiceException;

    /**
     * 发布产品价格
     *
     * @param productId 产品ID
     * @param loginId   登录Id
     * @throws ServiceException
     */
    ResultMessage releaseProductPrice(Long productId, Long loginId) throws ServiceException;

    /**
     * 单独的更新SKU价格，正式数据修改后，插入草稿表中
     *
     * @param productId 产品ID
     * @param list      SKU集合
     * @throws ServiceException
     */
    int updateSingleSkuPrice(Long productId, List<ProductSkuDraft> list) throws ServiceException;

    /**
     * 审核价格
     *
     * @param productIdChk 产品Id集合
     * @param auditStatus  审核状态
     * @param reasonText   审核原因
     * @throws ServiceException
     */
    int auditProductPrice(Long[] productIdChk, String auditStatus, String reasonText) throws ServiceException;

    /**
     * 更新价格及重量
     *
     * @param productId 产品Id
     * @param list      SKU集合
     * @throws ServiceException
     */
    int updateBatchByPrimaryKey(Long productId, List<ProductSkuDraft> list) throws ServiceException;

    /**
     * 商品下架
     *
     * @param productList
     * @return
     * @throws ServiceException
     */
    boolean downShelf(List<Product> productList) throws ServiceException;

    /**
     * 正式商品上架
     *
     * @param productList
     * @return
     * @throws ServiceException
     */
    ResultMessage productUpShelf(List<Product> productList) throws ServiceException;

    /**
     * 是否图片已经存在10个
     *
     * @param productSkuId
     * @return
     * @throws ServiceException
     */
    boolean isLimitImage(Long productSkuId) throws ServiceException;

    /**
     * 获取某个SKU下图片的最大排序
     *
     * @param productSkuId
     * @return
     * @throws ServiceException
     */
    int findMaxSortNoBySkuId(Long productSkuId) throws ServiceException;

    /**
     * 保存图片
     *
     * @param image
     * @return
     */
    Long saveImage(ProductImageDraft image) throws ServiceException;

    /**
     * 根据Id删除图片
     *
     * @param imageId
     * @throws ServiceException
     */
    int deleteImageById(Long imageId) throws ServiceException;

    /**
     * 修改产品图片的默认值，将产品id为productId，图片id为imageId的变为默认，其他的变为不是默认的
     *
     * @param productSkuId
     * @param imageId
     * @throws ServiceException
     */
    int modifyImageDefault(Long productSkuId, Long imageId) throws ServiceException;

    /**
     * 批量更新DRAFT
     *
     * @param list
     * @throws ServiceException
     */
    boolean updateProductImageDraftBatch(List<ProductImageDraft> list) throws ServiceException;

    /**
     * 批量更新
     *
     * @param list
     * @throws ServiceException
     */
    boolean updateProductImageBatch(List<ProductImage> list) throws ServiceException;

    int updateProductSkuDraft(ProductSkuDraft productSkuDraft) throws ServiceException;

    /**
     * 产品预览
     *
     * @param productId
     * @return
     * @throws ServiceException
     */
    String previewProductDraftInfoPage(String productId) throws ServiceException;
}
