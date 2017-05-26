package com.kmzyc.promotion.app.util;

/**
 * 编码工具类
 * 
 * @author tanyunxing
 * 
 */
public class CodeUtils {

    // 康美电商的“商户编码”
    // public static final String KANGMEI_CODE = "S001";
    // 产品消息频道标识
    public static final String DESTINATION = "product";

    /**
     * 促销消息队列-搜索引擎
     */
    public static final String DESTINATION_PROMOTION_SEARCH = "promotionSearchQueue";

    /**
     * 促销消息队列-CMS
     */
    public static final String DESTINATION_PROMOTION_CMS = "promotionCmsQueue";

    public static final String HOT_PRODUCTS_B2B_KEY = "product_hotsectionproducts";

    /**
     * 将传入的编码自增 1；如传入001，输出002；
     * 
     * @param sourceCode
     * @return
     */
    public static String getCode(String sourceCode) {
        StringBuffer sb = new StringBuffer(sourceCode);
        // System.out.println(sourceCode.indexOf(KANGMEI_CODE));
        // if(sb.indexOf(KANGMEI_CODE)>=0){
        // sb = new
        // StringBuffer(sb.substring(sb.indexOf(KANGMEI_CODE)+KANGMEI_CODE.length()));
        // flag = true;
        // }

        int length = sb.length();
        Long oldNum = Long.valueOf(sb.toString());
        ++oldNum;
        sb = new StringBuffer(String.valueOf((oldNum)));
        int max = length - sb.length();
        for (int i = 0; i < max; i++) {
            sb = sb.insert(0, "0");
        }
        // if(flag){
        // sb.insert(0, KANGMEI_CODE);
        // }
        return sb.toString();
    }

    /**
     * 新的编码规则 将传入的编码自增 1；如传入001，输出002；
     * 
     * @param sourceCode
     * @return
     */
    public static String getNewCode(String sourceCode) {
        StringBuffer sb = new StringBuffer(sourceCode);
        String cateCode = sb.substring(0, 2);
        String code = sb.substring(cateCode.length());
        Long oldNum = Long.valueOf(code);
        ++oldNum;
        sb = new StringBuffer(String.valueOf((oldNum)));
        sb.insert(0, cateCode);
        return sb.toString();
    }

}
