package com.pltfm.app.service;

import java.util.List;

import com.pltfm.app.vobject.ProductImage;
import com.pltfm.app.vobject.ProductImageDraft;
import com.kmzyc.commons.exception.ServiceException;

public interface ProductImageDraftService {

    /**
     * 保存图片
     *
     * @param image
     * @return
     */
    Long saveImage(ProductImageDraft image) throws ServiceException;

    /**
     * 更新图片
     *
     * @param image
     * @throws ServiceException 
     */
    void updateImageById(ProductImageDraft image) throws ServiceException ;

    /**
     * 根据Id删除图片
     *
     * @param imageId
     * @throws ServiceException 
     */
    void deleteImageById(Long imageId) throws ServiceException ;

    /**
     * 修改产品图片的默认值，将产品id为productId，图片id为imageId的变为默认，其他的变为不是默认的
     *
     * @param productSkuId
     * @param imageId
     * @throws ServiceException 
     */
    void modifyImageDefault(Long productSkuId, Long imageId) throws ServiceException ;

    /**
     * 获取某个SKU商品下所有的图片
     *
     * @param productSkuId
     * @return
     * @throws ServiceException 
     */
    List<ProductImageDraft> findAllBySkuId(Long productSkuId) throws ServiceException ;

    /**
     * 获取所有的图片信息
     *
     * @return
     * @throws ServiceException 
     */
    List<ProductImageDraft> findAllImage() throws ServiceException ;

    /**
     * 获取某个SKU商品下所有的图片（放入正式表中）
     *
     * @param productSkuId
     * @return
     * @throws ServiceException 
     */
    List<ProductImage> findAllIntoOfficialBySkuId(Long productSkuId) throws ServiceException ;

    /**
     * 获取某个SKU下图片的最大排序
     *
     * @param productSkuId
     * @return
     * @throws ServiceException 
     */
    int findMaxSortNoBySkuId(Long productSkuId) throws ServiceException ;

    /**
     * 批量更新
     *
     * @param list
     * @throws ServiceException 
     */
    boolean updateProductImageBatch(List<ProductImageDraft> list) throws ServiceException;

    /**
     * 是否图片已经存在10个
     *
     * @param productSkuId
     * @return
     * @throws ServiceException 
     */
    boolean isLimitImage(Long productSkuId) throws ServiceException ;

    /**
     * 根据产品Id查找图片
     *
     * @param productId
     * @return
     * @throws ServiceException 
     */
    List<ProductImageDraft> findByProductId(Long productId) throws ServiceException ;

    /**
     * 批量插入产品图片正式表
     *
     * @param list
     * @throws ServiceException 
     */
    void batchInsertOfficial(List<ProductImageDraft> list) throws ServiceException ;

    /**
     * 批量删除产品图片草稿表数据及是否删除图片文件物理数据
     *
     * @param list           草稿数据
     * @param needRemoveFile true需要删除图片物理数据，false不需要
     * @throws ServiceException 
     */
    void batchDeleteDraft(List<ProductImageDraft> list, boolean needRemoveFile) throws ServiceException ;

    /**
     * 批量删除产品图片草稿表
     *
     * @param list
     * @throws ServiceException 
     */
    void batchDeleteDraft(List<ProductImageDraft> list) throws ServiceException ;

    /**
     * 批量删除产品图片并将图片文件一起删除
     *
     * @throws ServiceException 
     */
    void batchDeleteDraftWithImagePath(List<ProductImageDraft> list) throws ServiceException ;

    /**
     * 从正式表中将数据复制到草稿表中
     *
     * @param productId
     * @throws ServiceException 
     */
    void insertDraftFromOfficialByProductId(Long productId) throws ServiceException ;

    /**
     * 主要用于图片更新的逻辑，比较正式表中的数据。再进行删除或者新增
     *
     * @param productId
     * @param list
     * @throws ServiceException 
     */
    List<ProductImageDraft> updateImageOfficial(Long productId, List<ProductImageDraft> list) throws ServiceException ;

}