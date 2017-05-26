package com.pltfm.activity.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.kmzyc.supplier.model.User;
import com.pltfm.activity.dao.ActivityPaymentDetailDAO;
import com.pltfm.app.dao.BaseDao;
import com.pltfm.app.vobject.ActivityPaymentDetail;
import com.pltfm.app.vobject.ActivityPaymentDetailExample;

@Repository("activityPaymentDetailDao")
public class ActivityPaymentDetailDAOImpl extends BaseDao<ActivityPaymentDetail> implements ActivityPaymentDetailDAO {

	@Override
	public int batchSaveActivityPaymentForReturn(List<ActivityPaymentDetail> activityPaymentDetailList)
			throws SQLException {
		return super.batchinsertNt(activityPaymentDetailList,"ACTIVITY_PAYMENT_DETAIL.ibatorgenerated_insert");
	}

	@Override
    public List<ActivityPaymentDetail> getPaymentDetails(ActivityPaymentDetailExample example) throws SQLException {
        return sqlMapClient.queryForList("ACTIVITY_PAYMENT_DETAIL.ibatorgenerated_selectByExample", example);
    }

	@Override
    public void batchInsertSelective(List<ActivityPaymentDetail> list) throws SQLException {
        batchInsertDataNt(list,"ACTIVITY_PAYMENT_DETAIL.ibatorgenerated_insertSelective");
    }

    @Override
    public User selectUserByUserId(User user) throws SQLException {
        return (User) sqlMapClient.queryForObject("User.queryUserByLoginId", user);
    }

	@Override
	public User selectUserBySupplierId(Long supplierId) throws SQLException {
		return (User) sqlMapClient.queryForObject("User.queryUserBySupplierId", supplierId);
	}
	
}