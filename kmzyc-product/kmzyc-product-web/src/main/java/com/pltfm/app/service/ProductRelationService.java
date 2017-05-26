package com.pltfm.app.service;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.pltfm.app.vobject.Product;
import com.pltfm.app.vobject.ProductRelation;
import com.pltfm.app.vobject.ProductRelationAndDetail;
import com.pltfm.app.vobject.ProductRelationDetail;
import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.commons.page.Page;


public interface ProductRelationService {

    /**
     * 保存关联主单信息
     *
     * @return
     * @throws ServiceException
     */
    Long saveProductRelation(ProductRelation productRelation) throws ServiceException;

    /**
     * 根据主产品的id查询出关联主表出来
     *
     * @param productRelation
     * @return
     * @throws ServiceException
     */
    void getProductRelationByMainSkuId(ProductRelation productRelation, Page page) throws ServiceException;

    /**
     * 根据relationId 查询出想对应的productRelation 对象出来
     *
     * @param relationId
     * @return
     * @throws ServiceException
     */
    ProductRelation queryProductRelation(Long relationId) throws ServiceException;

    /**
     * 根据SKUID 查询产品关联表、明细表
     *
     * @param skuId
     * @return
     * @throws ServiceException
     */
    List<ProductRelationAndDetail> selectProductRelationAndDetailBySkuId(Long skuId) throws ServiceException;

    /**
     * 根据relationId 更改套餐的状态为-1（删除）
     *
     * @param relations
     * @return
     * @throws ServiceException
     */
    int batchDelProductRelationStatus(List<Long> relations) throws ServiceException;

    /**
     * 更新关联组合的状态
     *
     * @return
     * @throws ServiceException
     */
    int updateProductRelationStatus(Long id, String status) throws ServiceException;

    /**
     * 根据产品的relationId 更新相应的信息
     *
     * @param productRelation
     * @return
     * @throws ServiceException
     */
    int updateProductRelation(ProductRelation productRelation) throws ServiceException;

    /**
     * 根据被关联的产品skuId，查询出关联主子表信息
     *
     * @param skuId
     * @return
     * @throws ServiceException
     */
    List<ProductRelationAndDetail> selectProductRelationAndDetailsByRelaitonSkuId(Long skuId) throws ServiceException;

    /**
     * 套餐管理-查询列表
     *
     * @param productRelation
     * @param page
     * @throws ServiceException
     */
    void getProductPackageByMainSkuId(ProductRelation productRelation, Page page) throws ServiceException;

    String upOrDownShelf(List<Long> operateList, String operateType) throws ServiceException;

    /**
     * 根据套餐id删除套餐信息
     *
     * @param relationIds
     * @return
     * @throws ServiceException
     */
    int delProductRelation(List<Long> relationIds) throws ServiceException;

    /**
     * 根据套餐id查询套餐信息
     *
     * @param productRelationId
     * @return
     */
    ProductRelation selectProductRelationById(Long productRelationId) throws ServiceException;

    /**
     * 根据套餐id修改套餐信息
     *
     * @param productRelation
     * @return
     * @throws ServiceException
     */
    int updateProductRelationSer(ProductRelation productRelation) throws ServiceException;

    /**
     * 新增状态下的修改套餐
     *
     * @param newRelationDetail
     * @return
     * @throws ServiceException
     */
    int addStatusUpdateProductRelation(ProductRelationDetail newRelationDetail) throws ServiceException;

    /**
     * 新增状态下的添加套餐产品
     *
     * @param skuIds
     * @param prices
     * @param proCount
     * @param productRelation
     * @return
     * @throws ServiceException
     */
    int addStatusInsertProductRelation(List<Long> skuIds, List<BigDecimal> prices, List<Short> proCount,
                                       ProductRelation productRelation) throws ServiceException;

    List<Integer> selectProductIdByRelationId(List<Long> relationId) throws ServiceException;

    /**
     * 获取所有组方产品
     *
     * @param productRelation
     * @param page
     * @return
     * @throws ServiceException
     */
    void getAllZFProductRelation(ProductRelation productRelation, Page page) throws ServiceException;

    /**
     * 保存关联主单以及详情子单信息
     *
     * @return
     * @throws ServiceException
     */
    void saveProductRelationAndDetail(ProductRelation productRelation, List<ProductRelationDetail> list)
            throws ServiceException;

    /**
     * 保存详情子单信息以及修改联主单
     *
     * @return
     * @throws ServiceException
     */
    void savePRDetailAndUpdatePR(ProductRelation productRelation, List<ProductRelationDetail> list)
            throws ServiceException;

    /**
     * 新增套餐-保存详情子单信息以及主单
     *
     * @return
     * @throws ServiceException
     */
    void saveTCPRDetailAndUpdatePR(ProductRelation productRelation, List<ProductRelationDetail> list,
                                   List<BigDecimal> prices, List<Long> skuIds, List<Short> proCount)
            throws ServiceException;

    /**
     * 定制管理-套餐管理-修改保存套餐信息
     *
     * @return
     * @throws ServiceException
     */
    void updateProductRelationSerAndDetail(ProductRelation productRelation, List<Long> relationDetailIdList,
                                           List<Short> proCount, List<Long> skuIds, List<BigDecimal> prices,
                                           List<Long> skuIdsAdd, List<BigDecimal> pricesAdd, List<Short> proCountAdd)
            throws ServiceException;

    /**
     * 定制管理-组方列表-修改保存组方信息
     *
     * @return
     * @throws ServiceException
     */
    void updateZFProductRelationAndDetail(String oldRIdCorrelationSIds, ProductRelation productRelation,
                                          List<ProductRelationDetail> list) throws ServiceException;

    /**
     * 根据skuId获取人气组合
     *
     * @param skuId
     * @return
     * @throws ServiceException
     */
    List<ProductRelation> getRecommendListBySkuId(Long skuId) throws ServiceException;

    /**
     * 根据skuId获取优惠套餐组合
     *
     * @param skuId
     * @return
     * @throws ServiceException
     */
    List<ProductRelation> getPackageListBySkuId(Long skuId) throws ServiceException;
}
