/**
 * project : 康美中药城城 module 2016年7月5日上午11:22:55 Copyright (c) 2016, KM@km.com All Rights Reserved.
 */
package com.kmzyc.b2b.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.kmzyc.b2b.service.CloseKMTPayService;
import com.kmzyc.b2b.util.httpClient.WebUtils;
import com.kmzyc.b2b.util.pay.util.PaymentUtil;
import com.kmzyc.zkconfig.ConfigurationUtil;

import net.sf.json.JSONObject;

/**
 *
 *
 * @author KM
 * @date 2016年7月5日 上午11:22:55
 * @version
 * @see
 */
@Service("closeKMTPayService")
@SuppressWarnings("unchecked")
public class CloseKMTPayServiceImpl implements CloseKMTPayService {
    private static Logger log = LoggerFactory.getLogger(RefundOrderServiceImpl.class);

    @Override
    public boolean closeKMTPay(String tradeNo, String outTradeNo) {
        log.info("---closeKMTPay(tradeNo= " + tradeNo + ",outTradeNo=" + outTradeNo + " begin---");
        boolean resultObject = false;
        String timestamp = String.valueOf(System.currentTimeMillis());
        String charset = ConfigurationUtil.getString("ten_pay_input_charset");
        SortedMap<String, String> reParams = new TreeMap<String, String>();
        reParams.put("method", "kmpay.order.close");
        reParams.put("partner", ConfigurationUtil.getString("kmt_pay_partner"));
        reParams.put("inputCharset", charset);
        reParams.put("clientIp", ConfigurationUtil.getString("kmt_request_ip"));
        reParams.put("timestamp", timestamp);

        reParams.put("tradeNo", tradeNo);
        reParams.put("outTradeNo", outTradeNo);
        try {
            String responseStr =
                    WebUtils.doPost(ConfigurationUtil.getString("kmt_pay_close_url"),
                            PaymentUtil.buildRequestParaForKMT(reParams,
                                    ConfigurationUtil.getString("kmt_sign_type")),
                            20000, 20000, true);
            JSONObject result = null;
            if (null != responseStr && !responseStr.isEmpty()
                    && null != (result = JSONObject.fromObject(responseStr))
                    && result.containsKey("data") && (boolean) result.get("success")) {
                Map<String, String> resMap = new HashMap<String, String>();
                String value = null;
                JSONObject json = result.getJSONObject("data");
                if (json != null) {
                    for (Object key : json.keySet()) {
                        value = json.getString(key.toString());
                        if (null != value && value.length() > 0 && !"null".equals(value)) {
                            resMap.put(key.toString(), value);
                        }
                    }
                    if ("SUCCESS".equals(json.getString("result"))
                            && PaymentUtil.verifyIsKMTPay(resMap)) {
                        resultObject = true;
                    }
                }
            }
        } catch (Exception e) {
            log.error("", e);
        }
        log.info("---closeKMTPay end---resultObject=" + resultObject);
        return resultObject;
    }

}
