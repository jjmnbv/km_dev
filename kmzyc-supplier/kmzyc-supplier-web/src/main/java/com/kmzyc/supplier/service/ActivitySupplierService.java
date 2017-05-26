package com.kmzyc.supplier.service;

import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.supplier.vo.ActivitySkuVo;
import com.pltfm.app.vobject.ActivityInfo;
import com.pltfm.app.vobject.ActivitySupplierEntry;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;


/**
 * 功能：供应商参加活动服务
 *
 * @author Zhoujiwei
 * @since 2016/3/18 9:48
 */
public interface ActivitySupplierService {

    /**
     * 更新sku预售数量缓存
     *
     * @param supplierEntryId       商家报名id
     * @param newActivitySkuList    新增的
     * @param exitsActivitySkuList  原有的
     * @param needAppend            是否需要追加
     */
    void updateSkuStockQuantityCache(Long supplierEntryId, List<ActivitySkuVo> newActivitySkuList,
                                     List<ActivitySkuVo> exitsActivitySkuList, Boolean needAppend);

    /**
     * 根据参数查询供应商报名数据
     *
     * @param entry 查询参数
     * @return
     * @throws ServiceException
     */
    ActivitySupplierEntry selectByExample(ActivitySupplierEntry entry) throws ServiceException;

    /**
     * 撤销报名
     *
     * @param entryId       商家报名id
     * @param loginUserId   操作用户
     *
     * @return
     * @throws ServiceException
     */
    int cancelActivity(Long entryId, Long loginUserId) throws ServiceException;

    /**
     * 获取商家报名id
     *
     * @param supplierId    商家id
     * @param activityId    活动id
     * @param isInvited     true邀请/false非邀请
     *
     * @return
     * @throws SQLException
     */
    Long getSupplierEntryId(Long supplierId, String activityId, Boolean isInvited) throws ServiceException;

    /**
     * 判断商家是否为邀请报名类型
     *
     * @param supplierId    商家id
     * @param activityId    活动id
     *
     * @return
     * @throws SQLException
     */
    boolean isInvited(Long supplierId, String activityId) throws ServiceException;

    /**
     * 获取商家有效的报名活动总费用
     *
     * @param supplierId    商家id
     * @param activityId    活动id
     *
     * @return
     * @throws ServiceException
     */
    BigDecimal getActivityTotalPrice(Long supplierId, String activityId) throws ServiceException;

    /**
     * 报名活动  --邀请是修改入驻记录，其他情况是新增
     * @param activityId            活动id
     * @param supplierId            供应商id
     * @param loginUserId           操作者
     * @param activity              活动信息
     * @param newActivitySkuList    报名参加活动的sku
     */
    void saveSupplierEntry(String activityId, Long supplierId, Long loginUserId,
                           ActivityInfo activity, List<ActivitySkuVo> newActivitySkuList) throws ServiceException;

    /**
     * 获取活动商家报名状态的具体情况
     *
     * @param supplierId    商家id
     * @param activityId    活动id
     *
     * @return
     * @throws ServiceException
     */
    Map<String, Long> getActivitySupplierEntryStatus(Long supplierId, String activityId) throws ServiceException;

    /**
     * 修改报名活动
     * @param activityId            活动id
     * @param supplierId            供应商id
     * @param loginUserId           操作者
     * @param activity              活动信息
     * @param newActivitySkuList    报名新参加活动的sku
     * @param exitsActivitySkuList  之前报名参加活动的sku
     * @param deleteSkuId           被删除了的之前参加或的skuId
     */
    void updateSupplierEntry(String activityId, Long supplierId,
                             Long loginUserId, ActivityInfo activity,
                             List<ActivitySkuVo> newActivitySkuList,
                             List<ActivitySkuVo> exitsActivitySkuList,
                             String deleteSkuId) throws ServiceException;

    /**
     * 追加推广时，修改商家报名的总费用
     *
     * @param totalPrice    本次追加费用
     * @param supplierId    商家id
     * @param loginUserId   操作者
     * @param activityId    活动id
     * @return
     * @throws ServiceException
     */
    int appendPrice(BigDecimal totalPrice, Long supplierId, Long loginUserId, String activityId) throws ServiceException;


    /**
     * 获取当前供应商店铺的评分
     *
     * @param supplierId    供应商id
     * @return
     * @throws ServiceException
     */
    Double getShopScore(Long supplierId) throws ServiceException;
}
