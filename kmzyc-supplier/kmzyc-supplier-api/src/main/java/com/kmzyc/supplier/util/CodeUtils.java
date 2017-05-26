package com.kmzyc.supplier.util;

import java.util.Date;
import java.util.Random;

import org.apache.commons.lang.time.DateFormatUtils;

/**
 * 编码工具类
 * @author tanyunxing
 *
 */
public class CodeUtils {
	
	//康美电商的“商户编码”
//	public static final String KANGMEI_CODE = "S001";
	//产品消息频道标识
	public static final String DESTINATION = "product";
	//MQ商品促销价格标识
	public static final String DESTINATION_PROMOTION = "product_promotion";
	
    // public static final String HOT_PRODUCTS_B2C_KEY = "product_hotsectionproducts";
	
	public static final String HOT_PRODUCTS_ZYC_KEY = "product_hotsectionproducts_zyc";
	
	public static final int SUPPLIERTYPE = 5;//5代表用户为供应商类型
	
	public static final String HOT_PRODUCTS_B2B_KEY = "product_hotsectionproducts_b2b";

	/**
	 * 将传入的编码自增 1；如传入001，输出002；
	 * @param sourceCode
	 * @return
	 */
	public static String getCode(String sourceCode){
		StringBuffer sb = new StringBuffer(sourceCode);
		boolean flag = false;
//		System.out.println(sourceCode.indexOf(KANGMEI_CODE));
//		if(sb.indexOf(KANGMEI_CODE)>=0){
//			sb = new StringBuffer(sb.substring(sb.indexOf(KANGMEI_CODE)+KANGMEI_CODE.length()));
//			flag = true;
//		}
		
		int length = sb.length();
		Long oldNum = Long.valueOf(sb.toString());
		++oldNum;
		sb = new StringBuffer(String.valueOf((oldNum)));
		int max = length - sb.length();
		for(int i=0;i<max;i++){
			sb = sb.insert(0, "0");
		}
//		if(flag){
//			sb.insert(0, KANGMEI_CODE);
//		}
		return sb.toString();
	}
	
	/**
	 * 新的编码规则
	 * 将传入的编码自增 1；如传入001，输出002；
	 * @param sourceCode
	 * @return
	 */
	public static String getNewCode(String sourceCode){
		StringBuffer sb = new StringBuffer(sourceCode);
		String cateCode = sb.substring(0, 2);
		String code = sb.substring(cateCode.length());
		Long oldNum = Long.valueOf(code);
		++oldNum;
		sb = new StringBuffer(String.valueOf((oldNum)));
		sb.insert(0, cateCode);
		return sb.toString();
	}

    /**
     * 获取活动付款号码
     *
     * @param isRefund  true退款/false付款
     * @return
     */
    public static String getActivityPaymentCode(Boolean isRefund) {
        String code = DateFormatUtils.format(new Date(), "yyyyMMddHHmmssSSS")
                + "_" + new Random().nextInt(1000);
        if (isRefund) {
            code = "R" + code;
        } else {
            code = "P" + code;
        }
        return code;
    }
}
