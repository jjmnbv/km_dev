package com.kmzyc.supplier.service.impl;

import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.supplier.dao.ActivityPaymentDetailDAO;
import com.kmzyc.supplier.service.ActivityPaymentDetailService;
import com.kmzyc.supplier.vo.ActivitySkuVo;
import com.pltfm.app.vobject.ActivityPaymentDetail;
import com.pltfm.app.vobject.ActivityPaymentDetailExample;
import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 功能：
 *
 * @author Zhoujiwei
 * @since 2016/3/28 14:39
 */
@Service("activityPaymentDetailService")
public class ActivityPaymentDetailServiceImpl implements ActivityPaymentDetailService {

    private Logger logger = LoggerFactory.getLogger(ActivityPaymentDetailServiceImpl.class);

    @Resource
    private ActivityPaymentDetailDAO activityPaymentDetailDAO;

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public Long saveActivityPaymentDetail(Long paymentId, Long productSkuId, BigDecimal total) throws ServiceException {
        ActivityPaymentDetail detail = new ActivityPaymentDetail();
        detail.setActivityPaymentId(paymentId);
        detail.setProductSkuId(productSkuId);
        detail.setSkuTotalPrice(total);
        try {
            return activityPaymentDetailDAO.insertSelective(detail);
        } catch (SQLException e) {
            logger.error("新增首次付款明细失败，失败信息：", e);
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public void batchInsertPaymentDetail(Long paymentId, List<ActivitySkuVo> list) throws ServiceException {
        List<ActivityPaymentDetail> detailList = new ArrayList<ActivityPaymentDetail>();
        ActivityPaymentDetail detail = null;
        for(ActivitySkuVo skuVo : list) {
            detail = new ActivityPaymentDetail();
            detail.setProductSkuId(skuVo.getProductSkuId());
            detail.setActivityPaymentId(paymentId);
            detail.setSkuTotalPrice(skuVo.getSkuTotalPrice());
            detailList.add(detail);
        }

        try {
            activityPaymentDetailDAO.batchInsertSelective(detailList);
        } catch (SQLException e) {
            logger.error("批量新增付款明细失败，失败信息：", e);
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public void batchInsertRefundPaymentDetail(Long paymentId, List<ActivityPaymentDetail> list) throws ServiceException {
        for(ActivityPaymentDetail detail : list) {
            detail.setActivityPaymentId(paymentId);
            detail.setActivityPaymentDetailId(null);
        }

        try {
            activityPaymentDetailDAO.batchInsertSelective(list);
        } catch (SQLException e) {
            logger.error("批量新增退款明细失败，失败信息：", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public List<ActivityPaymentDetail> getPaymentDetails(Long activityPaymentId) throws ServiceException {
        ActivityPaymentDetailExample example = new ActivityPaymentDetailExample();
        ActivityPaymentDetailExample.Criteria criteria = example.createCriteria();
        criteria.andActivityPaymentIdEqualTo(activityPaymentId);
        try {
            return activityPaymentDetailDAO.getPaymentDetails(example);
        } catch (SQLException e) {
            logger.error("根据付款id获取付款详细失败，失败信息：", e);
            throw new ServiceException(e);
        }
    }

}
