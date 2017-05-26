package com.kmzyc.supplier.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.km.framework.page.Pagination;
import com.kmzyc.supplier.model.OrderMain;
import com.pltfm.app.vobject.ActivityCharge;
import com.pltfm.app.vobject.ActivityInfoExample;
import com.pltfm.app.vobject.ActivitySku;

public interface ActivityPromotionEffectDAO {

	ActivityCharge getActivityChargeByActivityId(Long activityId) throws SQLException;

	/**
	 * 统计参与的活动推广效果列表
	 * @param example
	 * @return
	 * @throws SQLException
	 */
	int countPromotionEffect(ActivityInfoExample example) throws SQLException;
	/**
	 * 查询参与的活动推广效果列表
	 * @param page
	 * @return
	 * @throws SQLException
	 */
	Pagination selectPromotionEffect(Pagination page) throws SQLException;
	
	/**
	 * 查询活动首次缴费
	 * @param map
	 * @return
	 * @throws SQLException
	 */
	Double getActivityFirstPay(Map map) throws SQLException;

	/**
	 * 查询活动追加缴费
	 * @param map
	 * @return
	 * @throws SQLException
	 */
	Double getActivityAppendPay(Map map) throws SQLException;

	/**
	 * 统计参与的促销/图文活动商品销量
	 * @param page
	 * @return
	 * @throws SQLException
	 */
	int countSPAndTxtProductSales(Pagination page) throws SQLException;

	/**
	 * 查询参与的促销/图文活动商品销量列表
	 * @param page
	 * @return
	 * @throws SQLException
	 */
	List selectSPAndTxtProductSales(Pagination page) throws SQLException;

	/**
	 * 统计参与的渠道活动商品销量
	 * @param page
	 * @return
	 * @throws SQLException
	 */
	int countChannelProductSales(Pagination page) throws SQLException;

	/**
	 * 查询参与的渠道活动商品销量列表
	 * @param page
	 * @return
	 * @throws SQLException
	 */
	List selectChannelProductSales(Pagination page) throws SQLException;

	/**
	 * 统计参与的活动追加商品
	 * @param page
	 * @return
	 * @throws SQLException
	 */
	int countAppendProduct(Pagination page) throws SQLException;

	/**
	 * 查询参与的活动追加商品列表
	 * @param page
	 * @return
	 * @throws SQLException
	 */
	List<ActivitySku> selectAppendProductList(Pagination page) throws SQLException;

	/**
	 * 统计参与的活动追加数量
	 * @param map
	 * @return
	 * @throws SQLException
	 */
	Integer countAppendPreSaleQuantity(Map map) throws SQLException;
	
	/**
	 * 统计参与的活动首次预售数量
	 * @param map
	 * @return
	 * @throws SQLException
	 */
	Integer countFirstPreSaleQuantity(Map map) throws SQLException;
	
	int countProductSalesDetail(Pagination page) throws SQLException;

	List<OrderMain> selectProductSalesDetail(Pagination page) throws SQLException;
	
}
