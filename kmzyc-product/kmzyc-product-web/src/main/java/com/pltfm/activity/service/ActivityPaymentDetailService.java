package com.pltfm.activity.service;


import java.sql.SQLException;
import java.util.List;

import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.supplier.model.User;
import com.pltfm.app.vobject.ActivityPaymentDetail;

public interface ActivityPaymentDetailService {
    /**
     * 根据付款id获取付款详细
     *
     * @param activityPaymentId 付款id
     * @return
     * @throws ServiceException
     */
    List<ActivityPaymentDetail> getPaymentDetails(Long activityPaymentId) throws ServiceException;
    
    /**
     * 批量新增退款明细
     *
     * @param paymentId     退款id
     * @param list          上次有效付款明细数据
     * @return
     * @throws SQLException
     */
    void batchInsertRefundPaymentDetail(Long paymentId, List<ActivityPaymentDetail> list) throws ServiceException;
    
    /**
     * 根据用id查询用户信息
     * 
     * @param id
     * @return
     * @throws ServiceException
     */
    User findByUserIdObj(Long id) throws ServiceException;
}
