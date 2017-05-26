package com.kmzyc.b2b.dao;

import java.sql.SQLException;
import java.util.List;

import com.pltfm.app.vobject.DistributionInfo;

public interface OmsLogisticNumberDao {

	public List<DistributionInfo> getDistributionInfoList(String orderCode) throws SQLException;
	
	public int saveLogisticNumber(DistributionInfo distributionInfo) throws SQLException;
}
