package com.kmzyc.supplier.dao.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.km.framework.page.Pagination;
import com.kmzyc.supplier.model.OrderMain;
import com.kmzyc.supplier.dao.ActivityPromotionEffectDAO;
import com.pltfm.app.vobject.ActivityCharge;
import com.pltfm.app.vobject.ActivityInfoExample;
import com.pltfm.app.vobject.ActivitySku;

@Repository
public class ActivityPromotionEffectDAOImpl implements ActivityPromotionEffectDAO {

	@Resource
	private SqlMapClient sqlMapClient;

	@Override
	public ActivityCharge getActivityChargeByActivityId(Long activityId) throws SQLException {
		return (ActivityCharge) sqlMapClient.queryForObject("SUPPLIER_ACTIVITY_PROMOTION_EFFECT.selectActivityFee",
				activityId);
	}

	@Override
	public int countPromotionEffect(ActivityInfoExample example) throws SQLException {
		return (Integer) sqlMapClient.queryForObject("SUPPLIER_ACTIVITY_PROMOTION_EFFECT.countByExample", example);
	}

	// @Override
	// public List selectPromotionEffect(ActivityInfoExample example, int skip,
	// int max) throws SQLException {
	// return
	// sqlMapClient.queryForList("_ACTIVITY_PROMOTION_EFFECT.selectByExample",
	// example, skip, max);
	// }

	@Override
	public Double getActivityFirstPay(Map map) throws SQLException {
		return (Double) sqlMapClient.queryForObject("SUPPLIER_ACTIVITY_PROMOTION_EFFECT.selectActivityFirstPay", map);
	}

	@Override
	public Double getActivityAppendPay(Map map) throws SQLException {
		return (Double) sqlMapClient.queryForObject("SUPPLIER_ACTIVITY_PROMOTION_EFFECT.selectActivityAppendPay", map);
	}

	@Override
	public int countSPAndTxtProductSales(Pagination page) throws SQLException {
		return (Integer) sqlMapClient
				.queryForObject("SUPPLIER_ACTIVITY_PROMOTION_EFFECT.countSPAndTxtProductSalesByParam", page);
	}

	@Override
	public List selectSPAndTxtProductSales(Pagination page) throws SQLException {
		return sqlMapClient.queryForList("SUPPLIER_ACTIVITY_PROMOTION_EFFECT.selectSPAndTxtProductSalesByParam", page);
	}

	@Override
	public int countChannelProductSales(Pagination page) throws SQLException {
		return (Integer) sqlMapClient
				.queryForObject("SUPPLIER_ACTIVITY_PROMOTION_EFFECT.countChannelProductSalesByParam", page);
	}

	@Override
	public List selectChannelProductSales(Pagination page) throws SQLException {
		return sqlMapClient.queryForList("SUPPLIER_ACTIVITY_PROMOTION_EFFECT.selectChannelProductSalesByParam", page);
	}

	@Override
	public int countAppendProduct(Pagination page) throws SQLException {
		return (Integer) sqlMapClient.queryForObject("SUPPLIER_ACTIVITY_PROMOTION_EFFECT.countAppendProductByParam",
				page);
	}

	@Override
	public List<ActivitySku> selectAppendProductList(Pagination page) throws SQLException {
		return sqlMapClient.queryForList("SUPPLIER_ACTIVITY_PROMOTION_EFFECT.selectAppendProductListByParam", page);
	}

	@Override
	public Integer countAppendPreSaleQuantity(Map map) throws SQLException {
		return (Integer) sqlMapClient.queryForObject("SUPPLIER_ACTIVITY_PROMOTION_EFFECT.countAppendPreSaleQuantity",
				map);
	}

	@Override
	public Integer countFirstPreSaleQuantity(Map map) throws SQLException {
		return (Integer) sqlMapClient.queryForObject("SUPPLIER_ACTIVITY_PROMOTION_EFFECT.countFirstPreSaleQuantity",
				map);
	}

	@Override
	public Pagination selectPromotionEffect(Pagination pagination) throws SQLException {
		try {
			pagination.setRecordList(
					sqlMapClient.queryForList("SUPPLIER_ACTIVITY_PROMOTION_EFFECT.selectActivityResult", pagination));
		} catch (SQLException e) {
			throw new SQLException("分页查询：查询分页列表出现异常!", e);
		}
		try {
			pagination.setTotalRecords((Integer) sqlMapClient
					.queryForObject("SUPPLIER_ACTIVITY_PROMOTION_EFFECT.countActivityResult", pagination));
		} catch (SQLException e) {
			throw new SQLException("分页查询：查询总记录数出现异常!", e);
		}
		return pagination;
	}

	@Override
	public int countProductSalesDetail(Pagination page) throws SQLException {
		return (Integer) sqlMapClient.queryForObject("SUPPLIER_ACTIVITY_PROMOTION_EFFECT.countProductSalesDetailByParam", page);
	}

	@Override
	public List<OrderMain> selectProductSalesDetail(Pagination page) throws SQLException {
		return sqlMapClient.queryForList("SUPPLIER_ACTIVITY_PROMOTION_EFFECT.selectProductSalesDetailByParam", page);

	}

}
