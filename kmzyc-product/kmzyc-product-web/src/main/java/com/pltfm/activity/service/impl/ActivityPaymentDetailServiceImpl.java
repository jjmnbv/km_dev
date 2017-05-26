package com.pltfm.activity.service.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.supplier.model.User;
import com.pltfm.activity.dao.ActivityPaymentDetailDAO;
import com.pltfm.activity.service.ActivityPaymentDetailService;
import com.pltfm.app.vobject.ActivityPaymentDetail;
import com.pltfm.app.vobject.ActivityPaymentDetailExample;

@Service("activityPaymentDetailService")
public class ActivityPaymentDetailServiceImpl implements ActivityPaymentDetailService {

    @Resource
    private ActivityPaymentDetailDAO activityPaymentDetailDao;

    @Override
    public List<ActivityPaymentDetail> getPaymentDetails(Long activityPaymentId) throws ServiceException {
        ActivityPaymentDetailExample example = new ActivityPaymentDetailExample();
        ActivityPaymentDetailExample.Criteria criteria = example.createCriteria();
        criteria.andActivityPaymentIdEqualTo(activityPaymentId);

        try {
            return activityPaymentDetailDao.getPaymentDetails(example);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }
    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public void batchInsertRefundPaymentDetail(Long paymentId, List<ActivityPaymentDetail> list)
            throws ServiceException {
        for(ActivityPaymentDetail detail : list) {
            detail.setActivityPaymentId(paymentId);
            detail.setActivityPaymentDetailId(null);
        }
        try {
            activityPaymentDetailDao.batchInsertSelective(list);
        } catch (SQLException e) {
            throw new ServiceException("批量新增退款明细失败，失败信息：", e);
        }
        
    }
    @Override
    public User findByUserIdObj(Long id) throws ServiceException {
        User user = new User();
        user.setLoginId(id);
        try {
            return activityPaymentDetailDao.selectUserByUserId(user);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }
	
	

}
