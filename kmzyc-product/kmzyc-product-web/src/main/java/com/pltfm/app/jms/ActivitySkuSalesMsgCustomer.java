package com.pltfm.app.jms;

public interface ActivitySkuSalesMsgCustomer {

	/**
	 * 更新活动推广SKU销量
	 * 
	 * @param orderInfo
	 *            订单信息(包含SkuId、订单号、商品数量、支付时间、销售渠道)
	 */
	void updateActivitySkuSales(String orderInfo);
}
