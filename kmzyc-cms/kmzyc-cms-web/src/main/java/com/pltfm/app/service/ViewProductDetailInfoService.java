package com.pltfm.app.service;

import com.pltfm.app.vobject.ViewProductDetailInfo;
import com.pltfm.app.vobject.ViewSkuAttr;

import java.util.List;

/**
 * 产品详情业务逻辑接口
 *
 * @author cjm
 * @since 2013-9-23
 */
public interface ViewProductDetailInfoService {
    /**
     * 根据产品主键查询产品详情信息
     *
     * @param productId 产品详情条件类
     * @throws Exception 异常
     * @return 返回值
     */
    public List<ViewProductDetailInfo> selectByproductId(Integer productId) throws Exception;

    /**
     * 通过产品主键查询产品药品信息
     *
     * @param productId 产品主键
     * @return 属性值集合信息
     * @throws Exception 异常
     */
    public List<ViewSkuAttr> findProductAttr(Long productId) throws Exception;

    /**
     * 通过草稿产品主键查询产品药品信息
     *
     * @param productId 产品主键
     * @return 属性值集合信息
     * @throws Exception 异常
     */
    public List<ViewSkuAttr> findProductAttrDraft(Long productId) throws Exception;

    /**
     * 通过skuId查询产品详细信息
     *
     * @param skuId sku主键信息
     * @throws Exception 异常信息
     */
    public List<ViewProductDetailInfo> selectBySkuId(Integer skuId) throws Exception;

    /**
     * 通过产品主键查询产品个性信息
     *
     * @param productId 产品主键
     * @return 属性值集合信息
     * @throws Exception 异常
     */
    public List<ViewSkuAttr> findAttrAndValueByProductId(Long productId) throws Exception;

    /**
     * 通过草稿产品主键查询产品个性信息
     *
     * @param productId 产品主键
     * @return 属性值集合信息
     * @throws Exception 异常
     */
    public List<ViewSkuAttr> findAttrAndValueByProductIdDraft(Long productId) throws Exception;
}
