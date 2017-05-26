package com.kmzyc.supplier.maps;

import org.springframework.stereotype.Service;

import com.kmzyc.supplier.constrant.RechargeTypeEnum;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 账号余额明细充值类型map
 * @author luoyi
 * @createDate 2013/11/11
 *
 */
public class RechargeDetailTypeMap {
	private static Map<Integer, String> map = null;

	public static Map<Integer, String> getMap() {
		if (map == null) {
            Map<Integer, String> maps = new LinkedHashMap();
			maps.put(Integer.valueOf(RechargeTypeEnum.ONLINERECHARGE.getType()),
					RechargeTypeEnum.ONLINERECHARGE.getTitle());
			maps.put(Integer.valueOf(RechargeTypeEnum.BACKGROUNDRECHARGE.getType()),
                    RechargeTypeEnum.BACKGROUNDRECHARGE.getTitle());
			maps.put(Integer.valueOf(RechargeTypeEnum.BALANCERECHARGE.getType()),
                    RechargeTypeEnum.BALANCERECHARGE.getTitle());
			maps.put(Integer.valueOf(RechargeTypeEnum.CANCELORDER.getType()),
                    RechargeTypeEnum.CANCELORDER.getTitle());
			maps.put(Integer.valueOf(RechargeTypeEnum.ORDERRETURN.getType()),
                    RechargeTypeEnum.ORDERRETURN.getTitle());
			maps.put(Integer.valueOf(RechargeTypeEnum.INVITATIONAWARD.getType()),
                    RechargeTypeEnum.INVITATIONAWARD.getTitle());
			maps.put(Integer.valueOf(RechargeTypeEnum.MERCHANTCLEARUBG.getType()),
                    RechargeTypeEnum.MERCHANTCLEARUBG.getTitle());
			maps.put(Integer.valueOf(RechargeTypeEnum.BUSINESSESTAKENOW.getType()),
                    RechargeTypeEnum.BUSINESSESTAKENOW.getTitle());
			maps.put(Integer.valueOf(RechargeTypeEnum.BALANCETHAWING.getType()),
                    RechargeTypeEnum.BALANCETHAWING.getTitle());
			maps.put(Integer.valueOf(RechargeTypeEnum.BAKANCEFREEZE.getType()),
                    RechargeTypeEnum.BAKANCEFREEZE.getTitle());
			maps.put(Integer.valueOf(RechargeTypeEnum.BALANCETHAWING.getType()),
                    RechargeTypeEnum.BALANCETHAWING.getTitle());
			maps.put(Integer.valueOf(RechargeTypeEnum.CONSUMERREBATES.getType()),
                    RechargeTypeEnum.CONSUMERREBATES.getTitle());
			maps.put(Integer.valueOf(RechargeTypeEnum.SALESRETURN.getType()),
                    RechargeTypeEnum.SALESRETURN.getTitle());
			maps.put(Integer.valueOf(RechargeTypeEnum.DISTRIBUTIONRETURNCOMMISSION.getType()),
                    RechargeTypeEnum.DISTRIBUTIONRETURNCOMMISSION.getTitle());
            maps.put(Integer.valueOf(RechargeTypeEnum.TRANSACTION_TYPE_PROMOTION_PAY.getType()),
                    RechargeTypeEnum.TRANSACTION_TYPE_PROMOTION_PAY.getTitle());
            maps.put(Integer.valueOf(RechargeTypeEnum.TRANSACTION_TYPE_PROMOTION_REFUND.getType()),
                    RechargeTypeEnum.TRANSACTION_TYPE_PROMOTION_REFUND.getTitle());
            map = maps;
		}
		return map;
	}

	public static String getValue(Integer key) {
		if (map == null) {
			getMap();
		}
		return map.get(key);
	}
}
