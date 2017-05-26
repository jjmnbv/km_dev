package com.kmzyc.supplier.service;

import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.supplier.vo.ActivitySkuVo;
import com.km.framework.page.Pagination;
import com.pltfm.app.vobject.ViewProductSku;

import java.math.BigDecimal;
import java.util.List;

/**
 * 功能：
 *
 * @author Zhoujiwei
 * @since 2016/3/24 14:31
 */
public interface ActivitySkuService {

    /**
     * 查询活动时选择商品
     *
     * @param supplierId        供应商id
     * @param brandIds          品牌id集合
     * @param haveSkuId         已经选择了的sku
     * @param viewProductSku    查询参数
     * @param page              分页信息
     * @throws ServiceException
     * @return
     */
    Pagination selectProductSkuList4Activity(Long supplierId, String brandIds, String haveSkuId,
                                             ViewProductSku viewProductSku,
                                             Pagination page) throws ServiceException;

    /**
     * 批量新增活动sku信息
     *
     * @param newActivitySkuList    sku信息集合
     * @param loginUserId           登录用户
     * @param supplierEntryId       商家入驻id
     * @throws ServiceException
     */
    void batchSaveActivitySku(List<ActivitySkuVo> newActivitySkuList, Long loginUserId, Long supplierEntryId)
            throws ServiceException;

    /**
     * 计算单个sku在渠道推广时的总共费用
     *
     * @param preSaleQuantity   预售数量
     * @param activityPrice     活动价格
     * @param commissionRate    推广比例
     * @throws ServiceException
     */
    BigDecimal computeChannelMoney(Integer preSaleQuantity, BigDecimal activityPrice, BigDecimal commissionRate);

    /**
     * 查询是否还有商品在还未结束的促销推广活动中
     *
     * @param productSkuIdList  sku的id集合
     * @param supplierId
     *@param activityId        活动id  @return
     * @throws ServiceException
     */
    List<String> getSkuInUnfinishedActivity(List<Long> productSkuIdList, Long supplierId, Long activityId)
            throws ServiceException;

    /**
     * 根据活动id和商家id获取当前商家报名此次活动的商品
     *
     * @param supplierId    商家id
     * @param activityId    活动id
     * @return
     * @throws ServiceException
     */
    List<ActivitySkuVo> getActivitySku(Long supplierId, String activityId) throws ServiceException;

    /**
     * 删除报名参加活动的sku
     *
     * @param deleteSkuId       skuId
     * @param supplierEntryId   商家报名id
     */
    void deleteActivitySku(String deleteSkuId, Long supplierEntryId) throws ServiceException;

    /**
     * 批量更新报名参加了活动的sku
     *
     * @param activitySkuList       已经报名的sku
     * @param loginUserId           操作者
     */
    void batchUpdateActivitySku(List<ActivitySkuVo> activitySkuList, Long loginUserId) throws ServiceException;

    /**
     * 增加追加推广的sku
     *
     * @param appendSkuList sku列表
     * @param entryId
     * @throws ServiceException
     */
    void saveAppendSku(List<ActivitySkuVo> appendSkuList, Long entryId) throws ServiceException;
}
