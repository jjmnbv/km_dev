package com.kmzyc.supplier.service;

import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.supplier.vo.ActivitySkuVo;
import com.pltfm.app.vobject.ActivityPayment;

import java.math.BigDecimal;
import java.util.List;

/**
 * 功能：
 *
 * @author Zhoujiwei
 * @since 2016/3/28 14:08
 */
public interface ActivityPaymentService {

    /**
     * 首次缴费
     *
     * @param activityId        活动id
     * @param supplierEntryId   活动商家入驻id
     * @param supplierId        商家id
     * @param total             商家报名此次活动总共费用
     * @param loginUserId       使用者
     * @return
     * @throws ServiceException
     */
    Long saveFirstActivityPayment(String activityId, Long supplierEntryId, Long supplierId,
                                  BigDecimal total, Long loginUserId) throws ServiceException;

    /**
     * 新增退款
     *
     * @param activityId        活动id
     * @param supplierId        商家id
     * @param supplierEntryId   活动商家入驻id
     * @param loginUserId       使用者
     * @param activityPaymentId 对应的缴费id
     * @param total             商家报名此次活动总共费用
     * @param paymentCode       退款流水号
     * @return
     * @throws ServiceException
     */
    Long saveRefundActivityPayment(String activityId, Long supplierId, Long supplierEntryId,
                                   Long loginUserId, Long activityPaymentId, BigDecimal total, String paymentCode) throws ServiceException;

    /**
     * 撤销报名，修改首次缴费
     *
     * @param activityId        活动id
     * @param supplierEntryId   商家活动报名id
     * @param supplierId        商家id
     * @param loginUserId       使用者
     * @param loginUserName
     * @return
     * @throws ServiceException
     */
    void cancelActivityPayment(String activityId, Long supplierEntryId,
                               Long supplierId, Long loginUserId, String loginUserName) throws ServiceException;

    /**
     * 将本次商家报名活动id的付款设为无效
     *
     * @param loginUserId       使用者
     * @param activityPaymentId 商家报名本次付款id
     * @return
     * @throws ServiceException
     */
    int invalidLastFirstPayment(Long loginUserId, Long activityPaymentId) throws ServiceException;

    /**
     * 将退款修改为已退款
     *
     * @param activityPaymentId 商家报名本次付款id
     * @return
     * @throws ServiceException
     */
    int updateRefundPayment2Refunded(Long activityPaymentId) throws ServiceException;

    /**
     * 获取最后一次有效的首次缴费
     *
     * @param activityId        活动id
     * @param supplierEntryId   商家活动报名id
     * @param supplierId        商家id
     * @return
     * @throws ServiceException
     */
    ActivityPayment getLastValidFirstPayment(String activityId, Long supplierEntryId, Long supplierId)
            throws ServiceException;

    /**
     * 付款
     *
     * @param activityId    活动id
     * @param supplierId    商家id
     * @throws ServiceException
     */
    void pay(String activityId, Long supplierId) throws ServiceException;

    /**
     * 追加推广支付
     *
     *
     * @param totalPrice
     * @param appendSkuList 追加的sku
     * @param loginUserId   操作者
     * @param activityId    活动id
     * @param supplierId    商家id
     * @param entryId
     * @param paymentCode
     * @throws ServiceException
     */
    void saveAppend(BigDecimal totalPrice, List<ActivitySkuVo> appendSkuList, Long loginUserId,
                    String activityId, Long supplierId, Long entryId, String paymentCode)
            throws ServiceException;

    /**
     * 新增追加推广付款记录
     *
     * @param activityId        活动id
     * @param supplierEntryId   报名id
     * @param supplierId        商家id
     * @param total             追加费用
     * @param loginUserId       操作者
     * @return
     * @throws ServiceException
     */
    Long saveAppendPayment(String activityId, Long supplierEntryId, Long supplierId, BigDecimal total,
                           Long loginUserId, String paymentCode) throws ServiceException;
}
