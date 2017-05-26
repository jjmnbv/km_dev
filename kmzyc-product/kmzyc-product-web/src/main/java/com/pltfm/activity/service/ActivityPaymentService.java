package com.pltfm.activity.service;

import java.math.BigDecimal;

import com.kmzyc.commons.exception.ServiceException;
import com.pltfm.app.bean.ResultMessage;
import com.pltfm.app.vobject.ActivityPayment;



public interface ActivityPaymentService {
  
	/**
	 * 保存活动退费记录
	 * @throws ServiceException
	 */
	void saveActivityPaymentForRefundment(ActivityPayment activityPayment) throws ServiceException;
	
	/**
	 * 验证退款的合法性
	 * @param supplierEntryId
	 * @throws ServiceException
	 */
	ResultMessage refundmentValidity(Long supplierEntryId,Integer activityPaymentType,Long createUser)
            throws ServiceException;
	
	/**
	 * 调用远程退款接口
	 * @param activityPayment
	 * @param createUser
	 * @throws ServiceException
	 */
	ResultMessage activityEntryReturnByRemote(ActivityPayment activityPayment,Long createUser)
            throws ServiceException;
	
	/**
	 * 获取最后一次有效的首次缴费
	 * @param supplierEntryId
	 * @return
	 * @throws ServiceException
	 */
	ActivityPayment getLastValidFirstPayment(String activityId, Long supplierEntryId, Long supplierId)
            throws ServiceException;
	
	/**
     * 新增退款
     *
     * @param activityId        活动id
     * @param supplierId        商家id
     * @param supplierEntryId   活动商家入驻id
     * @param loginUserId       使用者
     * @param activityPaymentId 对应的缴费id
     * @param total             商家报名此次活动总共费用
     * @return
     * @throws ServiceException
     */
    Long saveRefundActivityPayment(Long activityId, Long supplierId, Long supplierEntryId,
                                   Long loginUserId, Long activityPaymentId, BigDecimal total,
                                   String paymentCode) throws ServiceException;
    
    /**
     * 将本次商家报名活动id的付款设为无效
     * @param loginUserId 审核登录id  activityPaymentId 商家报名本次付款id
     * @return
     * @throws ServiceException
     */
    int invalidLastFirstPayment(Long loginUserId,Long activityPaymentId) throws ServiceException;
    
    /**
     * 将退款修改为已退款
     *
     * @param activityPaymentId 商家报名本次付款id
     * @return
     * @throws ServiceException
     */
    int updateRefundPayment2Refunded(Long activityPaymentId) throws ServiceException;
	
}
