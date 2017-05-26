package com.kmzyc.b2b.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.alipay.util.AlipayCore;
import com.km.framework.action.BaseAction;
import com.km.framework.common.util.MD5;
import com.kmzyc.b2b.service.OmsProductService;
import com.kmzyc.b2b.service.ProductSkuService;
import com.kmzyc.b2b.service.impl.AccountInfoServiceImp;
import com.kmzyc.b2b.service.member.MyOrderService;
import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.zkconfig.ConfigurationUtil;

@SuppressWarnings({"serial", "unchecked"})
@Controller("outApiAction")
@Scope(value = "prototype")
public class OutApiAction extends BaseAction {
    // private static final Logger logger = Logger.getLogger(AppMyOrderAction.class);
    private static Logger logger = LoggerFactory.getLogger(AccountInfoServiceImp.class);
    // private String OMS_APP_KEY = ConfigurationUtil.getString("OMS_APP_KEY");
    // private String OMS_APP_SECRET = ConfigurationUtil.getString("OMS_APP_SECRET");
    @Resource(name = "myOrderServiceImpl")
    private MyOrderService myOrderService;
    @Resource
    private OmsProductService omsProductService;

    @Resource
    private ProductSkuService productSkuService;

    /**
     * OMS查询
     */
    public void omsApi() {
        String result = "";
        String code = "1", msg = "失败";
        try {
            HttpServletRequest request = getRequest();
            Map<String, String> params = getSignData(request);
            if (null == params) {
                logger.info("OMS查询失败");
                msg = "sign error";
            } else {
                String method = params.get("method");
                if ("trades.sold.increment.get".equals(method) ||
                        "trade.fullinfo.get".equals(method)) {
                    // 批量、单个订单接口
                    result = myOrderService.queryExeOrder(params);
                } else if ("logistics.offline.send".equals(method)) {
                    // 物流信息发货接口
                    result = omsProductService.toOrderLogisticNumber(params);
                } else if ("item.quantity.update".equals(method)) {
                    // 更新库存接口
                    result = omsProductService.updateStockForErp(params);
                } else if ("item.skus.get".equals(method)) {
                    // 单个商品查询接口
                    result = omsProductService.querySingleProduct(params);
                } else if ("item.list.get".equals(method)) {
                    // 批量查询
                    result = omsProductService.queryProductListForOms(params);
                }
                code = "0";
                msg = "成功";
            }
        } catch (ServiceException e) {
            logger.error("", e);
            msg = e.getMessage();
        } catch (Exception e) {
            logger.error("", e);
            msg = "system error";
        }
        if (null != result) {
            outPrintString(getResponse(), code, msg, result);
        }
    }

    /**
     * 获取请求参数
     */
    private Map<String, String> getSignData(HttpServletRequest request) throws Exception {
        String sign = request.getParameter("sign");
        if (null != sign) {
            Map<String, String> params = new HashMap<>();
            Map<String, String[]> requestParams = request.getParameterMap();
            for (Iterator<Map.Entry<String, String[]>> iterator = requestParams.entrySet().iterator();
                 iterator.hasNext(); ) {
                Map.Entry<String, String[]> entry=iterator.next();
                String name = entry.getKey();
                String[] values = entry.getValue();
                String valueStr = StringUtils.join(values,",");
                params.put(name, valueStr);
            }
            params.put("app_id", ConfigurationUtil.getString("OMS_APP_KEY"));
            if (sign.equals(
                    MD5.getMD5Str(getSecretStr(AlipayCore.paraFilter(params))).toUpperCase())) {
                return params;
            }
        }
        return null;
    }

    /**
     * 获取Secret
     */
    private String getSecretStr(Map<String, String> params) throws Exception {
        List<String> keys = new ArrayList(params.keySet());
        Collections.sort(keys);
        String OMS_APP_SECRET = ConfigurationUtil.getString("OMS_APP_SECRET");
        StringBuilder sb = new StringBuilder(OMS_APP_SECRET);
        for (String key : keys) {
            sb.append(key).append(params.get(key));
        }
        sb.append(OMS_APP_SECRET);
        return sb.toString();
    }

    /**
     * 输出xml数据
     */
    private void outPrintString(HttpServletResponse response, String code, String msg, String str) {
        PrintWriter out = null;
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/xml;charset=UTF-8");
            response.setHeader("Cache-Control", "no-cache");
            response.setHeader("Expires", "0");
            response.setHeader("Pragma", "No-cache");
            out = response.getWriter();
            out.print("<?xml version=\"1.0\" encoding=\"UTF-8\" ?><response><code>" + code +
                    "</code><msg>" + msg + "</msg>" + str + "</response>");
        } catch (Exception e) {
            logger.error("", e);
        } finally {
            if (out != null) {
                out.flush();
                out.close();
            }
        }
    }


    /**
     * 20151222 add 返利网查询超级返产品
     */
  /*  public void queryProductForFanli() {

        HttpServletRequest request = getRequest();
        String productSkuId = request.getParameter("id");
        // String timestamp = request.getParameter("timestamp");
        // String sign = request.getParameter("sign");

        if (StringUtils.isBlank(productSkuId)) {
            logger.info("返利网调用接口获取超级返产品productSkuId为空,此为必填选项,productSkuId=" + productSkuId);
            outPrintStringForFanli(getResponse(), "返利网调用接口获取超级返产品productSkuId为空,此为必填选项!");
            return;
        }

        // String MD5Str = MD5.getMD5Str(productSkuId + timestamp + FANLI_KEY);

        // md5加密后需要取大写 20151223 去掉时间戳以及sign的验证
        // if (!(MD5Str.toUpperCase()).equals(sign)) {
        // logger.info("返利网调用接口获取超级返产品签名认证不通过,productSkuId=" + productSkuId + ",timstamp=" +
        // timestamp
        // + ",sign=" + sign + ",md5Str=" + MD5Str.toUpperCase());
        // outPrintStringForFanli(getResponse(), "返利网调用接口获取超级返产品签名认证不通过!");
        // return;
        // }
        String xmlInfo = productSkuService.querySkuInfoForFanLi(Long.valueOf(productSkuId));
        if (StringUtils.isBlank(xmlInfo)) {
            logger.info("返利网调用接口获取超级返产品xml数据获取为空!xmlInfo=" + xmlInfo);
            return;
        }
        outPrintStringForFanli(getResponse(), xmlInfo);

    }*/

    /**
     * 输出xml数据
     *
     * @param response
     * @param obj
     * @throws Exception
     */
  /*  private void outPrintStringForFanli(HttpServletResponse response, String content) {
        PrintWriter out = null;
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/xml;charset=UTF-8");
            response.setHeader("Cache-Control", "no-cache");
            response.setHeader("Expires", "0");
            response.setHeader("Pragma", "No-cache");
            out = response.getWriter();
            out.print("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>" + content + "");
        } catch (Exception e) {
            logger.error("", e);
        } finally {
            if (out != null) {
                out.flush();
                out.close();
            }
        }
    }*/

}
