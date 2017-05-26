package com.kmzyc.supplier.service;

import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.supplier.vo.ActivitySkuVo;
import com.pltfm.app.vobject.ActivityPaymentDetail;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

/**
 * 功能：
 *
 * @author Zhoujiwei
 * @since 2016/3/28 14:08
 */
public interface ActivityPaymentDetailService {

    /**
     * 新增首次付款明细
     *
     * @param paymentId      缴费id
     * @param productSkuId   skuId
     * @param total          单个sku花费
     * @return
     * @throws SQLException
     */
    Long saveActivityPaymentDetail(Long paymentId, Long productSkuId, BigDecimal total) throws ServiceException;

    /**
     * 批量新增付款明细
     *
     * @param paymentId     付款id
     * @param list          付款明细数据
     * @return
     * @throws SQLException
     */
    void batchInsertPaymentDetail(Long paymentId, List<ActivitySkuVo> list) throws ServiceException;

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
     * 根据付款id获取付款详细
     *
     * @param activityPaymentId 付款id
     * @return
     * @throws ServiceException
     */
    List<ActivityPaymentDetail> getPaymentDetails(Long activityPaymentId) throws ServiceException;
}
