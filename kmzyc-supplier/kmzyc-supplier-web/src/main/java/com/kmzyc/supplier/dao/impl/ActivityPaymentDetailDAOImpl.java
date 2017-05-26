package com.kmzyc.supplier.dao.impl;

import com.kmzyc.supplier.dao.ActivityPaymentDetailDAO;
import com.pltfm.app.vobject.ActivityPaymentDetail;
import com.pltfm.app.vobject.ActivityPaymentDetailExample;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

/**
 * 功能：付款明细DAO
 *
 * @author Zhoujiwei
 * @since 2016/3/28 14:07
 */
@Repository("activityPaymentDetailDAO")
public class ActivityPaymentDetailDAOImpl extends BaseDAO implements ActivityPaymentDetailDAO {


    @Override
    public Long insertSelective(ActivityPaymentDetail activityPaymentDetail) throws SQLException {
        return (Long) sqlMapClient.insert("ACTIVITY_PAYMENT_DETAIL.insertSelective", activityPaymentDetail);
    }

    @Override
    public void batchInsertSelective(List<ActivityPaymentDetail> list) throws SQLException {
        batchInsertData("ACTIVITY_PAYMENT_DETAIL.insertSelective", list);
    }

    @Override
    public List<ActivityPaymentDetail> getPaymentDetails(ActivityPaymentDetailExample example) throws SQLException {
        return sqlMapClient.queryForList("ACTIVITY_PAYMENT_DETAIL.selectByExample", example);
    }

}