package com.pltfm.app.service;

import java.sql.SQLException;
import java.util.List;

import com.pltfm.app.bean.ResultMessage;
import com.pltfm.app.vobject.ProductImage;
import com.pltfm.app.vobject.ProductSku;
import com.kmzyc.commons.exception.ServiceException;

/**
 * 产品图片业务接口
 *
 * @author tanyunxing
 */
public interface ProductImageService {

    /**
     * 保存图片
     *
     * @param image
     * @return
     */
    Long saveImage(ProductImage image) throws ServiceException ;

    /**
     * 更新图片
     *
     * @param image
     * @throws ServiceException 
     */
    void updateImageById(ProductImage image) throws ServiceException ;

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
     * @param productId
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
    List<ProductImage> findAllBySkuId(Long productSkuId) throws ServiceException ;

    /**
     * 获取所有的图片信息
     *
     * @return
     * @throws ServiceException 
     */
    List<ProductImage> findAllImage() throws ServiceException ;

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
    boolean updateProductImageBatch(List<ProductImage> list) throws ServiceException;

    /**
     * 是否图片已经存在10个
     *
     * @param productSkuId
     * @return
     * @throws ServiceException 
     */
    boolean isLimitImage(Long productSkuId) throws ServiceException ;

    /**
     * 根据SKUID删除所有此SKU图片
     *
     * @param skuId
     * @return
     * @throws SQLException
     */
    ResultMessage deleteImagesBySkuId(ProductSku productSku, ResultMessage message) throws ServiceException ;

}