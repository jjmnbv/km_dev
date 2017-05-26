package com.pltfm.app.service;

import java.util.List;

import com.kmzyc.commons.exception.ServiceException;
import com.pltfm.app.vobject.ProductCertificateDraft;

public interface ProductCertificateDraftService {

    /**
     * 批量向草稿表插入数据
     *
     * @param list
     * @throws ServiceException 
     */
    void batchInsertDraft(List<ProductCertificateDraft> list) throws ServiceException;

    /**
     * 根据产品Id获取资质文件
     *
     * @param productId
     * @return
     * @throws ServiceException 
     */
    List<ProductCertificateDraft> findByProductId(Long productId) throws ServiceException ;

    /**
     * 根据主键的集合获取资质文件
     *
     * @param pscIds
     * @return
     * @throws ServiceException 
     */
    List<ProductCertificateDraft> findByPscIds(List<Long> pscIds) throws ServiceException ;

    /**
     * 删除资质文件（删除物理文件）
     *
     * @param list
     * @throws ServiceException 
     */
    void deleteByCertificateFiles(List<ProductCertificateDraft> list) throws ServiceException ;

    /**
     * 删除资质文件（只删除数据库记录）
     *
     * @param pscIds
     * @throws ServiceException 
     */
    void deleteByPscIdsWithNotFile(List<Long> pscIds) throws ServiceException ;

    /**
     * 删除资质文件（只删除数据库记录）
     *
     * @param productId
     * @throws ServiceException 
     */
    void deleteByProductIdWithNotFile(Long productId) throws ServiceException ;

    /**
     * 批量向正式表插入数据（将草稿中的数据copy至正式表中）
     *
     * @param list
     * @throws ServiceException 
     */
    void batchInsertOfficialFromDraft(List<ProductCertificateDraft> list) throws ServiceException ;

    /**
     * 更新正式表中的数据
     *
     * @param productId
     * @param list
     * @throws ServiceException 
     */
    void updateOfficialData(Long productId, List<ProductCertificateDraft> list) throws ServiceException ;

    /**
     * 将正式表中的数据插入草稿表中
     *
     * @param productId
     * @throws ServiceException 
     */
    void insertDraftFromOfficialByProductId(Long productId) throws ServiceException ;

    /**
     * 根据产品Id获取资质文件路径
     *
     * @param productId
     * @return
     * @throws ServiceException 
     */
    String findPathByProductId(Long productId) throws ServiceException ;

}
