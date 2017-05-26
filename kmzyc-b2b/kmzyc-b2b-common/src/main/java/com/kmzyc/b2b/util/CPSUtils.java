package com.kmzyc.b2b.util;

import java.util.HashMap;
import java.util.Map;

import com.kmzyc.zkconfig.ConfigurationUtil;

public class CPSUtils {
    // CPS COOKIE键
    public static final String[] B2B_CPS_YQF_COOKIE_KEYS = {"cps_yqf_source", "cps_yqf_channel",
            "cps_yqf_cid", "cps_yqf_wi", "cps_yqf_target", "cps_promotion_type"};

    // 推广信息集合，格式：公司,interId,编码,佣金类型,结算周期(月),cookie有效期(秒),接口每日限查次数,授权码
    public static Map<String, String[]> B2B_CPS_INFO = new HashMap<String, String[]>();
    // 推广类型
    public static Map<Integer, String> B2B_PROMOTION_TYPE = new HashMap<Integer, String>();
    static {
        if (B2B_CPS_INFO.isEmpty()) {
            String tokenInfoStr = ConfigurationUtil.getString("b2b_cps_token_info");
            String[] tokenInfoArray = (null == tokenInfoStr ? "" : tokenInfoStr).split(";");
            if (null != tokenInfoArray && tokenInfoArray.length > 0) {
                for (String temp : tokenInfoArray) {
                    String[] tokenInfo = temp.split(",");
                    if (null != tokenInfo && 8 == tokenInfo.length) {
                        B2B_CPS_INFO.put(tokenInfo[0], tokenInfo);
                    }
                }
            }
        }
        if (B2B_PROMOTION_TYPE.isEmpty()) {
            String promotionTypeStr = ConfigurationUtil.getString("b2b_promotion_type");
            String[] promotionTypeArray =
                    (null == promotionTypeStr ? "" : promotionTypeStr).split(";");
            if (null != promotionTypeArray && promotionTypeArray.length > 0) {
                for (String temp : promotionTypeArray) {
                    String[] types = temp.split(":");
                    if (null != types && types.length == 2) {
                        B2B_PROMOTION_TYPE.put(Integer.parseInt(types[0]), types[1]);
                    }
                }
            }
        }
    }
}
