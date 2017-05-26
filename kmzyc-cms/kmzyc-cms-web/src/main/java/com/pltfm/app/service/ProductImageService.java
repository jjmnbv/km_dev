package com.pltfm.app.service;

import com.pltfm.app.vobject.ProductImage;

import java.util.List;

/**
 * 产品图片业务逻辑接口
 *
 * @author cjm
 * @since 2013-9-22
 */
public interface ProductImageService {
    /**
     * 根据产品图片条件查询产品数据
     *
     * @param example 产品图片条件类
     * @throws Exception sql异常
     * @return 返回值
     */
    public List<ProductImage> selectBySkuId(Integer skuId) throws Exception;

    /**
     * 根据产品图片主键查询产品数据
     *
     * @param imageId 产品图片主键
     * @throws Exception sql异常
     * @return 返回值
     */
    ProductImage selectByPrimaryKey(Integer imageId) throws Exception;

}
