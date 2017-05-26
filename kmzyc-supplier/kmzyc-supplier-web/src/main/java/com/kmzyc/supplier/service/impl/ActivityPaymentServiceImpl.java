package com.kmzyc.supplier.service.impl;

import com.kmzyc.commons.exception.ServiceException;
import com.pltfm.app.enums.ActivityPaymentStatus;
import com.pltfm.app.enums.ActivityPaymentType;
import com.pltfm.app.util.TransactionTypeEnum;
import com.pltfm.app.vobject.ActivityPayment;
import com.pltfm.app.vobject.ActivityPaymentDetail;
import com.pltfm.app.vobject.ActivityPaymentExample;
import com.kmzyc.user.remote.service.TrationListRemoteService;
import com.kmzyc.supplier.service.ActivityPaymentDetailService;
import com.kmzyc.supplier.service.ActivityPaymentService;
import com.kmzyc.supplier.util.CodeUtils;
import com.kmzyc.supplier.vo.ActivitySkuVo;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 功能：
 *
 * @author Zhoujiwei
 * @since 2016/3/28 14:09
 */
@Service("activityPaymentService")
public class ActivityPaymentServiceImpl implements ActivityPaymentService {

    private Logger logger = LoggerFactory.getLogger(ActivityPaymentServiceImpl.class);

    @Resource
    private com.kmzyc.supplier.dao.ActivityPaymentDAO ActivityPaymentDAO;

    @Resource
    private ActivityPaymentDetailService activityPaymentDetailService;

    @Resource
    private TrationListRemoteService trationListRemoteService;

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public Long saveFirstActivityPayment(String activityId, Long supplierEntryId,
                                         Long supplierId, BigDecimal total,
                                         Long loginUserId) throws ServiceException {
        ActivityPayment activityPayment = new ActivityPayment();
        activityPayment.setActivityPaymentCode(CodeUtils.getActivityPaymentCode(Boolean.FALSE));
        activityPayment.setActivityId(Long.parseLong(activityId));
        activityPayment.setSupplierEntryId(supplierEntryId);
        activityPayment.setSupplierId(supplierId);
        activityPayment.setActivityPaymentType(ActivityPaymentType.FIRST_PAY.getType());
        if (total.doubleValue() == 0) {
            activityPayment.setActivityPaymentStatus(ActivityPaymentStatus.PAYED.getStatus());
        } else {
            activityPayment.setActivityPaymentStatus(ActivityPaymentStatus.NOT_PAY.getStatus());
        }
        activityPayment.setTotalFunds(total);
        Timestamp now = new Timestamp(System.currentTimeMillis());
        activityPayment.setCreateTime(now);
        activityPayment.setModifyTime(now);
        activityPayment.setCreateUser(loginUserId);
        activityPayment.setModifUser(loginUserId);
        activityPayment.setOnePayStatus(1);
        try {
            return ActivityPaymentDAO.insertSelective(activityPayment);
        } catch (SQLException e) {
            logger.error("首次缴款失败，失败信息：", e);
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public void cancelActivityPayment(String activityId, Long supplierEntryId,
                                      Long supplierId, Long loginUserId, String loginUserName) throws ServiceException {
        try {
            //1.获取最后一次有效的首次缴费
            ActivityPayment payment = getLastValidFirstPayment(activityId, supplierEntryId, supplierId);
            if (payment == null) {
                logger.error("获取最后一次有效的首次缴费失败，没有找到对应的付款信息,商家id：{}，活动id：{}，商家报名id:{}.",
                        new Object[]{supplierId, activityId, supplierEntryId});
                throw new ServiceException("获取最后一次有效的首次缴费失败，没有找到对应的付款信息.");
            }
            Long activityPaymentId = payment.getActivityPaymentId();
            BigDecimal totalFunds = payment.getTotalFunds();

            //2.将之前有效的首次缴费变为无效
            invalidLastFirstPayment(loginUserId, activityPaymentId);

            //上一次缴费是否为已缴费
            if (payment.getActivityPaymentStatus().intValue() != ActivityPaymentStatus.PAYED.getStatus().intValue()) {
                return;
            }

            //3.新增一条退款记录
            String paymentCode = CodeUtils.getActivityPaymentCode(Boolean.TRUE);
            Long refundPaymentId = saveRefundActivityPayment(activityId, supplierId,
                    supplierEntryId, loginUserId, activityPaymentId, totalFunds,paymentCode);

            //4.获取上次有效首次缴费的详细信息
            List<ActivityPaymentDetail> list = activityPaymentDetailService.getPaymentDetails(activityPaymentId);

            //5.批量保存退款信息
            activityPaymentDetailService.batchInsertRefundPaymentDetail(refundPaymentId, list);

            //如果上次缴费不为0，需要退款
            if (totalFunds.doubleValue() != 0) {
                if (trationListRemoteService == null) {
                    logger.error("付款失败，商家用户名{},参加活动{},远程接口获取不到。",
                            new Object[]{loginUserName, activityId});
                    throw new ServiceException("付款失败,远程接口获取不到。");
                }
                Integer resultCode = trationListRemoteService.orderTrationAccout(loginUserName,
                        totalFunds.doubleValue(),
                        paymentCode,
                        "活动退款,活动ID："+ activityId,
                        TransactionTypeEnum.TRANSACTION_TYPE_PROMOTION_REFUND.getType());
                logger.info("撤销报名退款，商家用户名{}，活动{},费用{}，返回状态{}.",
                        new Object[]{loginUserName, activityId, totalFunds, resultCode});
                if (resultCode != 1) {
                    logger.error("撤销报名退款失败，商家用户名{}，活动{},费用{}，返回状态{}.",
                            new Object[]{loginUserName, activityId, totalFunds, resultCode});
                    throw new ServiceException("撤销报名退款失败，" +
                            "商家用户名" + loginUserName +
                            "，活动" + activityId +
                            ",费用" + totalFunds +
                            "，返回状态" + resultCode + ".");
                }

                //将退款改为已退款
                //updateRefundPayment2Refunded(refundPaymentId);
            }
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public Long saveRefundActivityPayment(String activityId, Long supplierId,
                                          Long supplierEntryId, Long loginUserId,
                                          Long activityPaymentId, BigDecimal total, String paymentCode) throws ServiceException {
        ActivityPayment activityPayment = new ActivityPayment();
        activityPayment.setActivityPaymentCode(paymentCode);
        activityPayment.setActivityId(Long.parseLong(activityId));
        activityPayment.setSupplierEntryId(supplierEntryId);
        activityPayment.setSupplierId(supplierId);
        activityPayment.setActivityPaymentType(ActivityPaymentType.ENTRY_CANCLE_REFUND.getType());
        activityPayment.setParentId(activityPaymentId);
        activityPayment.setActivityPaymentStatus(ActivityPaymentStatus.REFUNDED.getStatus());
        activityPayment.setTotalFunds(total);
        Timestamp now = new Timestamp(System.currentTimeMillis());
        activityPayment.setCreateTime(now);
        activityPayment.setModifyTime(now);
        activityPayment.setCreateUser(loginUserId);
        activityPayment.setModifUser(loginUserId);
        activityPayment.setOnePayStatus(0);
        try {
            return ActivityPaymentDAO.insertSelective(activityPayment);
        } catch (SQLException e) {
            logger.error("新增退款失败，失败信息：", e);
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public int invalidLastFirstPayment(Long loginUserId, Long activityPaymentId) throws ServiceException {
        if (activityPaymentId == null) {
            logger.error("将最后一次有效的首次缴费设为无效失败，没有找到商家付款id。");
            throw new ServiceException("将最后一次有效的首次缴费设为无效失败，没有找到商家付款id。");
        }

        try {
            ActivityPayment activityPayment = new ActivityPayment();
            activityPayment.setActivityPaymentId(activityPaymentId);
            Timestamp now = new Timestamp(System.currentTimeMillis());
            activityPayment.setModifyTime(now);
            activityPayment.setModifUser(loginUserId);
            activityPayment.setOnePayStatus(0);
            return ActivityPaymentDAO.updateById(activityPayment);
        } catch (SQLException e) {
            logger.error("将id为{}的付款信息设为无效失败，失败信息："
                    , new Object[]{activityPaymentId, e});
            throw new ServiceException(e);
        }
    }

    @Override
    public int updateRefundPayment2Refunded(Long activityPaymentId) throws ServiceException {
        if (activityPaymentId == null) {
            logger.error("将退款信息设为已退款失败，没有找到商家退款id。");
            throw new ServiceException("将退款信息设为已退款失败，没有找到商家退款id。");
        }

        try {
            ActivityPayment activityPayment = new ActivityPayment();
            activityPayment.setActivityPaymentId(activityPaymentId);
            activityPayment.setActivityPaymentStatus(ActivityPaymentStatus.REFUNDED.getStatus());
            return ActivityPaymentDAO.updateById(activityPayment);
        } catch (SQLException e) {
            logger.error("将id为{}的退款信息设为已退款失败，失败信息："
                    , new Object[]{activityPaymentId, e});
            throw new ServiceException(e);
        }
    }

    @Override
    public ActivityPayment getLastValidFirstPayment(String activityId, Long supplierEntryId,
                                                    Long supplierId) throws ServiceException {
        if (StringUtils.isBlank(activityId) || supplierEntryId == null) {
            logger.error("获取最后一次有效的首次缴费失败，没有找到活动id或者商家报名id。");
            throw new ServiceException("撤销报名活动失败，没有找到活动id或者商家报名id。");
        }

        ActivityPaymentExample example = new ActivityPaymentExample();
        ActivityPaymentExample.Criteria criteria = example.createCriteria();
        criteria.andActivityIdEqualTo(Long.parseLong(activityId));
        criteria.andSupplierIdEqualTo(supplierId);
        criteria.andActivityPaymentTypeEqualTo(ActivityPaymentType.FIRST_PAY.getType());
        criteria.andSupplierEntryIdEqualTo(supplierEntryId);
        criteria.andOnePayStatusEqualTo((short) 1);
        try {
            return ActivityPaymentDAO.selectByExample(example);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public void pay(String activityId, Long supplierId) throws ServiceException {
        try {
            Map<String, Object> condition = new HashMap<String, Object>();
            condition.put("supplierId", supplierId);
            condition.put("activityId", activityId);
            ActivityPaymentDAO.updateExample(condition);
        } catch (SQLException e) {
            logger.error("商家{}参加活动id{}的有效的首次缴费付款失败，失败信息：{}"
                    , new Object[]{supplierId, activityId, e});
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public void saveAppend(BigDecimal totalPrice, List<ActivitySkuVo> appendSkuList, Long loginUserId,
                           String activityId, Long supplierId, Long entryId, String paymentCode)
            throws ServiceException {
        Long paymentId = saveAppendPayment(activityId, entryId, supplierId, totalPrice, loginUserId, paymentCode);

        activityPaymentDetailService.batchInsertPaymentDetail(paymentId, appendSkuList);
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public Long saveAppendPayment(String activityId, Long supplierEntryId, Long supplierId,
                                  BigDecimal total, Long loginUserId,String paymentCode) throws ServiceException {
        ActivityPayment activityPayment = new ActivityPayment();
        activityPayment.setActivityPaymentCode(paymentCode);
        activityPayment.setActivityId(Long.parseLong(activityId));
        activityPayment.setSupplierEntryId(supplierEntryId);
        activityPayment.setSupplierId(supplierId);
        activityPayment.setActivityPaymentType(ActivityPaymentType.ADD_PAY.getType());
        activityPayment.setActivityPaymentStatus(ActivityPaymentStatus.PAYED.getStatus());
        activityPayment.setTotalFunds(total);
        Timestamp now = new Timestamp(System.currentTimeMillis());
        activityPayment.setCreateTime(now);
        activityPayment.setModifyTime(now);
        activityPayment.setCreateUser(loginUserId);
        activityPayment.setModifUser(loginUserId);
        try {
            return ActivityPaymentDAO.insertSelective(activityPayment);
        } catch (SQLException e) {
            logger.error("追加推广失败，失败信息：", e);
            throw new ServiceException(e);
        }
    }
}
