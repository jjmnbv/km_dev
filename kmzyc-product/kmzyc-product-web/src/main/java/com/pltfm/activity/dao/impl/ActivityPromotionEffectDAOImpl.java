package com.pltfm.activity.dao.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Repository;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.km.framework.page.Pagination;
import com.pltfm.activity.dao.ActivityPromotionEffectDAO;
import com.pltfm.app.vobject.ActivityCharge;
import com.pltfm.app.vobject.ActivityInfo;
import com.pltfm.app.vobject.ActivityInfoExample;

@Repository("activityPromotionEffectDao")
public class ActivityPromotionEffectDAOImpl implements ActivityPromotionEffectDAO {

	@Resource
	protected SqlMapClient sqlMapClient;

	@Override
	public ActivityCharge getActivityChargeByActivityId(Long activityId) throws SQLException {
		return (ActivityCharge) sqlMapClient.queryForObject("ACTIVITY_PROMOTION_EFFECT.selectActivityFee", activityId);
	}

	@Override
	public List selectPromotionEffect(ActivityInfoExample example, int skip, int max) throws SQLException {
		return sqlMapClient.queryForList("ACTIVITY_PROMOTION_EFFECT.selectByExample", example, skip, max);
	}

	@Override
	public int countPromotionEffect(ActivityInfoExample example) throws SQLException {
		return (Integer) sqlMapClient.queryForObject("ACTIVITY_PROMOTION_EFFECT.countByExample", example);
	}

	@Override
	public Pagination getActivityPromotionEffectList(Pagination pagination) throws SQLException {
		pagination.setRecordList(
				sqlMapClient.queryForList("ACTIVITY_PROMOTION_EFFECT.selectPromotionEffcet", pagination));
		pagination.setTotalRecords(
				(Integer) sqlMapClient.queryForObject("ACTIVITY_PROMOTION_EFFECT.countPromotionEffect", pagination));
		return pagination;
	}

	@Override
	public Double getActivityFirstPay(Long activityId, Long supplierId) throws SQLException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("activityId", activityId);
		map.put("supplierId", supplierId);
		return (Double) sqlMapClient.queryForObject("ACTIVITY_PROMOTION_EFFECT.selectActivityFirstPay", map);
	}

	@Override
	public Double getActivityAppendPay(Long activityId, Long supplierId) throws SQLException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("activityId", activityId);
		map.put("supplierId", supplierId);
		return (Double) sqlMapClient.queryForObject("ACTIVITY_PROMOTION_EFFECT.selectActivityAppendPay", map);
	}

	@Override
	public int countSPAndTxtSupplierProductSales(Map map) throws SQLException {
		return (Integer) sqlMapClient
				.queryForObject("ACTIVITY_PROMOTION_EFFECT.countSPAndTxtSupplierProductSalesByParam", map);
	}

	@Override
	public List selectSPAndTxtSupplierProductSales(Map map, int skip, int max) throws SQLException {
		return sqlMapClient.queryForList("ACTIVITY_PROMOTION_EFFECT.selectSPAndTxtSupplierProductSalesByParam", map,
				skip, max);
	}

	@Override
	public int countChannelSupplierProductSales(Map map) throws SQLException {
		return (Integer) sqlMapClient
				.queryForObject("ACTIVITY_PROMOTION_EFFECT.countChannelSupplierProductSalesByParam", map);
	}

	@Override
	public List selectChannelSupplierProductSales(Map map, int skip, int max) throws SQLException {
		return sqlMapClient.queryForList("ACTIVITY_PROMOTION_EFFECT.selectChannelSupplierProductSalesByParam", map,
				skip, max);
	}

	@Override
	public int countSupplierAppendProduct(Map map) throws SQLException {
		return (Integer) sqlMapClient.queryForObject("ACTIVITY_PROMOTION_EFFECT.countSupplierAppendProductByParam",
				map);
	}

	@Override
	public List selectSupplierAppendProductList(Map map, int skip, int max) throws SQLException {
		return sqlMapClient.queryForList("ACTIVITY_PROMOTION_EFFECT.selectSupplierAppendProductListByParam", map, skip,
				max);
	}

	@Override
	public Integer countSupplierFristPreSaleQuantity(Map map) throws SQLException {
		return (Integer) sqlMapClient.queryForObject("ACTIVITY_PROMOTION_EFFECT.countSupplierFristPreSaleQuantity",
				map);
	}

	@Override
	public Integer countSupplierAppendPreSaleQuantity(Map map) throws SQLException {
		return (Integer) sqlMapClient.queryForObject("ACTIVITY_PROMOTION_EFFECT.countSupplierAppendPreSaleQuantity",
				map);
	}

	@Override
	public List<ActivityInfo> selectActivityProgressList(Map map) throws SQLException {
		return (List<ActivityInfo>) sqlMapClient
				.queryForList("ACTIVITY_PROMOTION_EFFECT.selectActivityProgressBySkuCode", map);
	}

	@Override
	public int countProductSalesDetail(Map map) throws SQLException {
		return (Integer) sqlMapClient.queryForObject("ACTIVITY_PROMOTION_EFFECT.countProductSalesDetailByParam", map);
	}

	@Override
	public List selectProductSalesDetail(Map map, int skip, int max) throws SQLException {
		return sqlMapClient.queryForList("ACTIVITY_PROMOTION_EFFECT.selectProductSalesDetailByParam", map, skip, max);
	}

	@Override
	public int countByEntryExample(Map map) throws SQLException {
		return (Integer) sqlMapClient.queryForObject("ACTIVITY_PROMOTION_EFFECT.countByEntryExample", map);
	}

	@Override
	public List selectByEntryExamples(Map map, int skip, int max) throws SQLException {
		return sqlMapClient.queryForList("ACTIVITY_PROMOTION_EFFECT.selectByEntryExamples", map, skip, max);
	}

}
