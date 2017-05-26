package com.pltfm.activity.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import com.km.framework.page.Pagination;
import com.pltfm.app.vobject.ActivityCharge;
import com.pltfm.app.vobject.ActivityInfo;
import com.pltfm.app.vobject.ActivityInfoExample;

public interface ActivityPromotionEffectDAO {

	Pagination getActivityPromotionEffectList(Pagination page) throws SQLException;

	ActivityCharge getActivityChargeByActivityId(Long activityId) throws SQLException;

//	int countPromotionEffect(Map<String, Object> map) throws SQLException;
	
	int countPromotionEffect(ActivityInfoExample example) throws SQLException;

//	List selectPromotionEffect(Map<String, Object> map, int skip, int max) throws SQLException;
	
	List selectPromotionEffect(ActivityInfoExample example, int skip, int max) throws SQLException;

	Double getActivityFirstPay(Long activityId, Long supplierId) throws SQLException;

	Double getActivityAppendPay(Long activityId, Long supplierId) throws SQLException;

	int countSPAndTxtSupplierProductSales(Map map) throws SQLException;

	List selectSPAndTxtSupplierProductSales(Map map, int skip, int max) throws SQLException;

	int countChannelSupplierProductSales(Map map) throws SQLException;

	List selectChannelSupplierProductSales(Map map, int skip, int max) throws SQLException;

	int countSupplierAppendProduct(Map map) throws SQLException;

	List selectSupplierAppendProductList(Map map, int skip, int max) throws SQLException;

	Integer countSupplierAppendPreSaleQuantity(Map map) throws SQLException;

	Integer countSupplierFristPreSaleQuantity(Map map) throws SQLException;

	List<ActivityInfo> selectActivityProgressList(Map map) throws SQLException;

	int countProductSalesDetail(Map map) throws SQLException;

	List selectProductSalesDetail(Map map, int skip, int max) throws SQLException;
	
	/**
     * 活动报名商家记录条数
     * 
     * @param map
     * @return
     * @throws SQLException
     */
    int countByEntryExample(Map map) throws SQLException;

    /**
     * 活动报名商家列表
     * 
     * @param map
     * @param skip
     * @param max
     * @return
     * @throws SQLException
     */
    List selectByEntryExamples(Map map, int skip, int max) throws SQLException;
}
