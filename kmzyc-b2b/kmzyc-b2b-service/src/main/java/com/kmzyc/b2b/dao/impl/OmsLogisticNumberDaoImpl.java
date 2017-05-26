package com.kmzyc.b2b.dao.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.kmzyc.b2b.dao.OmsLogisticNumberDao;
import com.pltfm.app.vobject.DistributionInfo;

@SuppressWarnings("unchecked")
@Component
public class OmsLogisticNumberDaoImpl implements OmsLogisticNumberDao {

	@Resource(name = "sqlMapClient")
	private SqlMapClient sqlMapClient;
	
	@Override
	public List<DistributionInfo> getDistributionInfoList(String orderCode) throws SQLException {
		return sqlMapClient.queryForList("DISTRIBUTION_INFO.getDistributionInfoListByOrderCode", orderCode);
	}

	@Override
	public int saveLogisticNumber(DistributionInfo distributionInfo)
			throws SQLException {
		return sqlMapClient.update("DISTRIBUTION_INFO.updateLogisticNumber", distributionInfo);
	}

}
