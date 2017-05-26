package com.pltfm.app.service;

import java.util.List;
import java.util.Map;

import com.kmzyc.commons.exception.ServiceException;
import com.pltfm.app.vobject.ProductSkuAttr;
import com.pltfm.app.vobject.ProductSkuAttrDraft;
import com.pltfm.app.vobject.ProductSkuDraft;
import com.pltfm.app.vobject.ViewSkuAttr;

public interface ProductSkuAttrDraftService {

    /**
     * 添加草稿SKU属性
     *
     * @param productSkuAttrDraft
     * @throws ServiceException
     */
    void addProductSkuAttrDraft(ProductSkuAttrDraft productSkuAttrDraft) throws ServiceException;

    /**
     * 根据产品Id查找产品SKU属性
     *
     * @param productId
     * @return
     * @throws ServiceException
     */
    List<ProductSkuAttrDraft> findByProductId(Long productId) throws ServiceException;

    /**
     * 批量插入正式表
     *
     * @param list
     * @throws ServiceException
     */
    void batchInsertOfficical(List<ProductSkuAttrDraft> list) throws ServiceException;

    /**
     * 批量删除草稿表信息
     *
     * @param list
     * @throws ServiceException
     */
    void batchDeleteDraft(List<ProductSkuAttrDraft> list) throws ServiceException;

    /**
     * 根据SkuId删除SKU属性
     *
     * @param productSkuId
     */
    void deleteProductSkuAttr(Long productSkuId) throws ServiceException;

    /**
     * 根据productId删除SKU属性
     *
     * @param productId 产品id
     */
    void deleteProductSkuAttrByProductId(Long productId) throws ServiceException;

    /**
     * 批量插入草稿表
     *
     * @param list
     * @throws ServiceException
     */
    void batchInsertDraft(List<ProductSkuAttr> list) throws ServiceException;

    /**
     * 根据产品Id获取相关SKU规格（草稿）
     *
     * @param productId
     * @return
     * @throws ServiceException
     */
    List<ProductSkuAttrDraft> findSkuAttrDraftByProductId(Long productId) throws ServiceException;

    /**
     * 根据SkuId获取SKU属性（草稿）
     *
     * @param productSkuId
     * @return
     * @throws ServiceException
     */
    List<ViewSkuAttr> findSkuDraftInfoByProductSkuId(Long productSkuId) throws ServiceException;

    /**
     * 查询是否有使用的SKU属性值
     *
     * @param attrValueId
     * @return
     * @throws ServiceException
     */
    boolean queryByAttrValueId(Long attrValueId) throws ServiceException;

    /**
     * 批量根据SKU更新SKU状态
     *
     * @param productSkuDraftList
     * @throws ServiceException
     */
    void batchUpdateSkuAttrStatus(List<ProductSkuDraft> productSkuDraftList) throws ServiceException;

    /**
     * 查询新增的SKU属性并做处理
     *
     * @param productId
     * @return
     * @throws ServiceException
     */
    Map<Long, List<ProductSkuAttrDraft>> findAndChangeSkuNewAttr(Long productId) throws ServiceException;

}
