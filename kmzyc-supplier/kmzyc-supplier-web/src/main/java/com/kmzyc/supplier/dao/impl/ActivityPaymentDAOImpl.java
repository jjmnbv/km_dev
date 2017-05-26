package com.kmzyc.supplier.dao.impl;

import com.kmzyc.supplier.dao.ActivityPaymentDAO;
import com.pltfm.app.vobject.ActivityPayment;
import com.pltfm.app.vobject.ActivityPaymentExample;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.Map;

/**
 * 功能：
 *
 * @author Zhoujiwei
 * @since 2016/3/28 14:07
 */
@Repository("activityPaymentDAO")
public class ActivityPaymentDAOImpl extends BaseDAO implements ActivityPaymentDAO {

    @Override
    public Long insertSelective(ActivityPayment activityPayment) throws SQLException {
        return (Long) sqlMapClient.insert("ACTIVITY_PAYMENT.insertSelective", activityPayment);
    }

    @Override
    public int updateById(ActivityPayment activityPayment) throws SQLException {
        return sqlMapClient.update("ACTIVITY_PAYMENT.updateByPrimaryKeySelective", activityPayment);
    }

    @Override
    public ActivityPayment selectByExample(ActivityPaymentExample activityPaymentExample) throws SQLException {
        return (ActivityPayment) sqlMapClient.queryForObject("ACTIVITY_PAYMENT.selectByExample", activityPaymentExample);
    }

    @Override
    public int updateExample(Map<String, Object> param) throws SQLException {
        return sqlMapClient.update("ACTIVITY_PAYMENT.payed", param);
    }

}