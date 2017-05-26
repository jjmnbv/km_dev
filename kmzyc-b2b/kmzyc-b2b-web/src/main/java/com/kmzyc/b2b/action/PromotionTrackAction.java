package com.kmzyc.b2b.action;

import java.io.PrintWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.km.framework.action.BaseAction;
import com.km.framework.page.Pagination;
import com.kmzyc.b2b.model.OrderItem;
import com.kmzyc.b2b.model.OrderMain;
import com.kmzyc.b2b.service.OrderItemService;
import com.kmzyc.b2b.service.OrderTrailEmailService;
import com.kmzyc.b2b.service.PromotionStatisticsService;
import com.kmzyc.b2b.util.CPSUtils;
import com.kmzyc.b2b.vo.CpsTrackInfo;
import com.kmzyc.b2b.vo.RequestInfo;
import com.kmzyc.framework.exception.ServiceException;
import com.kmzyc.util.StringUtil;
import com.kmzyc.zkconfig.ConfigurationUtil;

/**
 * 推广中转
 * 
 * @author xiongliguang
 * 
 */
@Controller("promotionTrackAction")
@Scope("prototype")
@SuppressWarnings("unchecked")
public class PromotionTrackAction extends BaseAction {

    private static final DateTimeFormatter sdf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    // private static final String cookieDomain = ConfigurationUtil.getString("cookie_domain");

    @Resource(name = "promotionStatisticsService")
    private PromotionStatisticsService promotionStatisticsService;

    // private static Logger log = Logger.getLogger(PromotionTrackAction.class);
    private static Logger log = LoggerFactory.getLogger(PromotionTrackAction.class);

    private static final long serialVersionUID = -6938338253830903046L;

    private Map resultMap;
    private String target;
    private JSONObject resultStr;
    @Resource(name = "orderTrailEmailService")
    private OrderTrailEmailService orderTrailEmailService;

    @Resource(name = "orderItemServiceImpl")
    private OrderItemService orderItemService;

    @Override
    public String execute() throws Exception {
        boolean b = false;
        HttpServletRequest request = getRequest();
        CpsTrackInfo cpsTrackInfo = new CpsTrackInfo();
        // 通用参数
        String source = request.getParameter("source");
        String type = request.getParameter("type");
        if (StringUtil.isEmpty(type)) {
            type = "1";
        }
        String channel = request.getParameter("channel");
        if (StringUtil.isEmpty(channel)) {
            channel = ConfigurationUtil.getString("b2b_cps_yqf_default_channel");
        }
        String cid = request.getParameter("cid");
        if (source != null && source.trim().endsWith("chengguowang")) {// 成果网,具体值待定
            type = "3";
            // cid = request.getParameter("id");
        }
        String wi = request.getParameter("wi");
        if (wi == null) {
            wi = "";
        }
        String url = request.getParameter("target");

        // 领客参数
        String a_id = request.getParameter("a_id"); // 我们网站下联盟会员ID。
        String m_id = request.getParameter("m_id"); // 广告主在LINKTECH网站的ID
        String c_id = request.getParameter("c_id"); // 广告点击数。
        String l_id = request.getParameter("l_id"); // 广告序号
        String l_type1 = request.getParameter("l_type1"); // 广告类型，
        String rd = request.getParameter("rd"); // COOKIE生存周期
        // 多麦网参数
        String union_id = request.getParameter("union_id");// 商家分配给联盟的身份标识，用以判断cps订单的来源
        if (union_id != null && union_id.equals("duomai")) {
            wi = request.getParameter("euid");// 多麦cps联盟旗下投放广告活动链接的网站主标识
            cid = request.getParameter("mid");// 多麦cps联盟旗下投放广告活动链接的网站主的网站ID
            url = URLDecoder.decode(request.getParameter("to"), "UTF-8");// 着陆页面，用户点击进入cps接入接口后最终访问到的页面
            source = union_id;
        }

        // String linkString = ""; // 领客数据拼接 ：
        if (m_id != null && (m_id.equals("kmb2b") || m_id.equals("kmwap"))) { // 来自领客
            url = request.getParameter("url"); // 广告主banner目标地址
            if (StringUtil.isEmpty(url)) {
                url = ConfigurationUtil.getString("b2b_cps_default_url");
            }
            HttpServletResponse response = this.getResponse();
            if (a_id == null || c_id == null || l_id == null || l_type1 == null || url == null
                    || !isNumber(rd)) {
                log.debug("cps领客参数有误");
            } else {
                // 加入cookie
                response.setHeader("P3P", "CP=\"NOI DEVa TAIa OUR BUS UNI\"");
                String[] values = {"LTINFO_" + m_id, channel, a_id, c_id, url, l_type1};
                String[] keys = CPSUtils.B2B_CPS_YQF_COOKIE_KEYS;
                for (int i = 0, len = keys.length; i < len; i++) {
                    try {
                        Cookie c = new Cookie(keys[i], URLEncoder.encode(values[i], "UTF-8"));
                        c.setDomain(ConfigurationUtil.getString("cookie_domain"));
                        c.setPath("/");
                        c.setMaxAge(60 * 60 * 24 * Integer.parseInt(rd));
                        response.addCookie(c);
                    } catch (Exception e) {
                        log.error("CPS跳转写入cookie发生异常", e);
                    }
                }
                // 设置存入数据库数据
                if (StringUtil.isEmpty(url)) {
                    url = ConfigurationUtil.getString("b2b_cps_default_url");
                }
                if (StringUtil.isEmpty(channel)) {
                    channel = ConfigurationUtil.getString("b2b_cps_yqf_default_channel");
                }
                cpsTrackInfo.setChannel(channel);
                cpsTrackInfo.setCampaignId(a_id);
                cpsTrackInfo.setFeedback(wi);
                cpsTrackInfo.setSource("LTINFO_" + m_id);
                cpsTrackInfo.setTarget(url);
                cpsTrackInfo.setTrackDate(new Date());
                b = true;
            }
        } else// 目前为除领客外的其他渠道
        {
            if (StringUtil.isEmpty(url)) {
                url = ConfigurationUtil.getString("b2b_cps_default_url");
            }
            String[] tokenInfo = CPSUtils.B2B_CPS_INFO
                    .get(CPSUtils.B2B_PROMOTION_TYPE.get(Integer.parseInt(type)));

            HttpServletResponse response = this.getResponse();
            response.setHeader("P3P",
                    "CP='IDC DSP COR ADM DEVi TAIi PSA PSD IVAi IVDi CONi HIS OUR IND CNT'");
            String[] values = {source, channel, cid, wi, url, type};
            String[] keys = CPSUtils.B2B_CPS_YQF_COOKIE_KEYS;
            for (int i = 0, len = keys.length; i < len; i++) {
                try {
                    Cookie c = new Cookie(keys[i], URLEncoder.encode(values[i], "UTF-8"));
                    c.setDomain(ConfigurationUtil.getString("cookie_domain"));
                    c.setPath("/");
                    c.setMaxAge(Integer.parseInt(tokenInfo[5]));
                    response.addCookie(c);
                } catch (Exception e) {
                    log.error("CPS跳转写入cookie发生异常", e);
                }
            }
            cpsTrackInfo.setChannel(channel);
            cpsTrackInfo.setCampaignId(cid);
            cpsTrackInfo.setFeedback(wi);
            cpsTrackInfo.setSource(source);
            cpsTrackInfo.setTarget(url);
            cpsTrackInfo.setTrackDate(new Date());
            b = true;
        }
        try {
            if (b) {
                promotionStatisticsService.insertCpsTrackInfo(cpsTrackInfo);
            }
        } catch (Exception e) {
            log.error("新增CPS跳转信息发生异常", e);
        }
        target = url;
        // response.getWriter().write("<script>window.location.href='" + target
        // + "';</script>");
        return SUCCESS;
    }

    /**
     * 校验参数是否位数字 add by songmiao
     * 
     * @param str
     * @return
     */
    public boolean isNumber(String str) {
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("[0-9]*");
        java.util.regex.Matcher match = pattern.matcher(str);
        if (match.matches() == false) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 按下单时间查询
     * 
     * @return
     * @throws Exception
     */
    public String queryOrderInfoByCreateTime() throws Exception {

        resultMap = new HashMap();
        List orders = new ArrayList();
        HttpServletRequest request = getRequest();
        Map<String, Object> params = genCPSYQFQueryOrderParams(request, 0);
        if (params.containsKey("errorStr")) {
            String errorStr = String.valueOf(params.get("errorStr"));
            orders.add(errorStr);
        } else {
            List<Map> list = promotionStatisticsService.queryOrderInfoByCreateDate(params);
            if (null != list && !list.isEmpty()) {
                String orderCode = null;
                Map<String, Object> data = new HashMap<String, Object>();
                List<Map<String, Object>> products = new ArrayList<Map<String, Object>>();
                String[] cpsInfo = (String[]) params.get("cpsInfo");
                for (Map val : list) {
                    String orderNo = String.valueOf(val.get("orderNo"));
                    if (!orderNo.equals(orderCode)) {
                        if (null != orderCode) {
                            data.put("products", products);
                            orders.add(data);
                            data = new HashMap<String, Object>();
                            products = new ArrayList<Map<String, Object>>();
                        }
                        data.put("campaignId", val.get("campaignId"));
                        data.put("fare", val.get("fare"));
                        data.put("favorable", val.get("favorable"));
                        data.put("favorableCode", val.get("favorableCode"));
                        data.put("feedback", val.get("feedback"));
                        data.put("orderNo", orderNo);
                        data.put("orderTime", val.get("orderTime"));
                        data.put("orderstatus", val.get("orderstatus"));
                        data.put("paymentType", val.get("paymentType"));
                        data.put("encoding", cpsInfo[2]);
                        orderCode = orderNo;
                    }
                    Map<String, Object> product = new HashMap<String, Object>();
                    product.put("amount", val.get("amount"));
                    product.put("category", val.get("category"));
                    product.put("commissionType", val.get("commissionType"));
                    product.put("name", val.get("name"));
                    product.put("price", val.get("price"));
                    product.put("productNo", val.get("productNo"));
                    products.add(product);
                }
                if (null != products && !products.isEmpty()) {
                    data.put("products", products);
                    orders.add(data);
                }
            } else {
                orders.add("no data!");
            }
        }
        resultMap.put("orders", orders);
        return SUCCESS;
    }

    /**
     * 按更新时间查询
     * 
     * @return
     * @throws Exception
     */
    public String queryOrderInfoByUpdateTime() throws Exception {
        resultMap = new HashMap();
        List orderStatus = new ArrayList();
        HttpServletRequest request = getRequest();
        Map<String, Object> params = genCPSYQFQueryOrderParams(request, 1);
        if (params.containsKey("errorStr")) {
            String errorStr = String.valueOf(params.get("errorStr"));
            orderStatus.add(errorStr);
        } else {
            orderStatus = promotionStatisticsService.queryOrderInfoByUpdateDate(params);
            if (null == orderStatus || orderStatus.isEmpty()) {
                orderStatus = new ArrayList();
                orderStatus.add("no data!");
            }
        }
        resultMap.put("orderStatus", orderStatus);
        return SUCCESS;
    }

    /**
     * 查询订单，成果网
     * 
     * @return
     * @throws Exception
     */
    public String queryOrderInfoCgw() throws Exception {
        resultMap = new HashMap();
        HttpServletRequest request = getRequest();
        String user = request.getParameter("user");
        String start = request.getParameter("start");
        String end = request.getParameter("end");
        String pageNumber = request.getParameter("page");
        String orderid = request.getParameter("orderid");
        String unixtime = request.getParameter("unixtime");
        String sig = request.getParameter("sig");

        RequestInfo ri = new RequestInfo();
        String queryStr = request.getQueryString();
        ri.setRequestIP(getIP(request));
        ri.setRequestParams(queryStr);
        ri.setSystemCode("b2b");
        ri.setRequestSource("out");
        ri.setServiceName("queryOrderInfoCgw");
        int queryCount;
        try {
            queryCount = promotionStatisticsService.queryRequestCount(ri);
            if (queryCount >= 20) {
                return "errorStr";
            }
        } catch (ServiceException e) {
            log.error("成果网CPS查询出错，查询超过当天查询次数上限！", e);
        }

        Long time = System.currentTimeMillis() / 1000;
        if (user == null || start == null || end == null || pageNumber == null || unixtime == null
                || sig == null || user.isEmpty() || start.isEmpty() || end.isEmpty()
                || pageNumber.isEmpty() || unixtime.isEmpty() || sig.isEmpty()) {
            return "error:参数有误";
        }
        if (time - Long.parseLong(unixtime) > 600) {
            return "error:请求超时";
        }
        // start = "20140506121314";
        // end = "20150506121314";
        SimpleDateFormat sidf = new SimpleDateFormat("yyyyMMddHHmmss");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date startDate = sidf.parse(start);
        Date endDate = sidf.parse(end);
        start = format.format(startDate);
        end = format.format(endDate);
        Pagination page = this.getPagination(5, 10);
        // sql查询条件对象
        Map<String, Object> newConditon = new HashMap<String, Object>();
        if (orderid != null && !orderid.isEmpty()) {
            newConditon.put("orderCode", orderid);
        }
        newConditon.put("createDateBegin", start);
        newConditon.put("createDateEnd", end);
        // 设置查询条件
        page.setObjCondition(newConditon);
        page.setNumperpage(Integer.parseInt(pageNumber));
        this.pagintion = orderTrailEmailService.findOrderMainByPageCgw(page);
        promotionStatisticsService.insertRequestInfo(ri);
        List<OrderMain> orderMainList = pagintion.getRecordList();
        // 一级节点
        JSONObject json = new JSONObject();
        json.put("start_time", "");
        json.put("end_time", "");
        double totalPage = (((double) pagintion.getTotalRecords()
                / (double) 10) > (pagintion.getTotalRecords() / 10)
                        ? pagintion.getTotalRecords() / 10 + 1 : pagintion.getTotalRecords() / 10);
        json.put("total_page", (int) totalPage);
        json.put("current_page", pageNumber);
        // 二级节点
        JSONObject resultsJson = new JSONObject();
        // 三级节点
        JSONArray jsonArray = new JSONArray();
        // 四级节点
        JSONObject itemJson = new JSONObject();
        JSONArray jsonArray2 = new JSONArray();
        for (OrderMain o : orderMainList) {
            resultsJson.put("order_time", format.format(o.getCreateDate()));
            resultsJson.put("order_id", o.getOrderCode());
            resultsJson.put("union_id", o.getArea());
            resultsJson.put("status", 0);
            resultsJson.put("payment", 2);
            resultsJson.put("total_price", o.getAmountPayable());
            resultsJson.put("shipping", "");
            resultsJson.put("coupon", "");
            resultsJson.put("paid", 1);
            List<OrderItem> l = orderItemService.findByOrderCode(o.getOrderCode());
            for (OrderItem ot : l) {
                itemJson.put("item_id", ot.getCommoditySku());
                itemJson.put("item_name", ot.getCommodityName());
                itemJson.put("category", "kangmei");
                itemJson.put("price", ot.getCommodityUnitPrice());
                itemJson.put("amount", ot.getCommodityNumber());
                jsonArray2.add(itemJson);
                itemJson = new JSONObject();
            }
            resultsJson.put("items", jsonArray2);
            jsonArray.add(resultsJson);
            jsonArray2 = new JSONArray();
            resultsJson = new JSONObject();
        }
        json.put("results", jsonArray);
        resultStr = json;
        return SUCCESS;
    }

    /**
     * 查询订单，领客
     * 
     * @return
     * @throws Exception
     */
    public String queryOrderInfoLK() throws Exception {
        resultMap = new HashMap();
        HttpServletRequest request = getRequest();
        RequestInfo ri = new RequestInfo();
        String queryStr = request.getQueryString();
        ri.setRequestIP(getIP(request));
        ri.setRequestParams(queryStr);
        ri.setSystemCode("b2b");
        ri.setRequestSource("out");
        ri.setServiceName("queryOrderInfoLK");
        int queryCount;
        try {
            queryCount = promotionStatisticsService.queryRequestCount(ri);
            if (queryCount >= 20) {
                return "errorStr";
            }
        } catch (ServiceException e) {
            log.error("领克特CPS查询出错，查询超过当天查询次数上限！", e);
        }

        String dateString = request.getParameter("yyyymmdd");
        String m_id = request.getParameter("m_id");
        // dateString ="20140930";
        if (dateString == null || dateString.isEmpty()) {
            return "error";
        }
        if (!isValidDate(dateString)) {// 校验时间是否合法
            return "error";
        }
        String start = dateString + "000000";
        SimpleDateFormat sidf = new SimpleDateFormat("yyyyMMddHHmmss");
        Date startDate = sidf.parse(start);
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(startDate);
        calendar.add(Calendar.DATE, 1);
        Date endDate = calendar.getTime(); // 把日期往后增加一天

        Pagination page = this.getPagination(5, 10);
        Map<String, Object> newConditon = new HashMap<String, Object>();
        newConditon.put("createDateBegin", startDate);
        newConditon.put("createDateEnd", endDate);
        if (m_id != null && !m_id.isEmpty()) {
            newConditon.put("m_id", "LTINFO_" + m_id);
        }
        // 设置查询条件
        page.setObjCondition(newConditon);
        page.setNumperpage(Integer.valueOf(1));
        this.pagintion = orderTrailEmailService.findOrderMainByPageLK(page);
        promotionStatisticsService.insertRequestInfo(ri);
        List<OrderMain> orderMainList = pagintion.getRecordList();
        StringBuilder sb = new StringBuilder();
        Date creatDate;
        for (OrderMain o : orderMainList) {
            List<OrderItem> l = orderItemService.findByOrderCode(o.getOrderCode());
            for (OrderItem ot : l) {
                sb.append("2" + "\t");
                creatDate = o.getCreateDate();
                sb.append(sidf.format(creatDate).substring(8, sidf.format(creatDate).length())
                        + "\t");
                sb.append(o.getArea() + "\t");
                sb.append(o.getOrderCode() + "\t");
                sb.append(ot.getCommoditySku() + "_" + ot.getOrderItemId() + "\t");
                sb.append("user" + "\t");
                sb.append(ot.getCommodityNumber() + "\t");
                sb.append(ot.getCommodityUnitIncoming() + "\t");
                sb.append(ot.getCommodityType() + "\t");
                sb.append("\r\n");
            }

        }
        // 指定输出内容类型和编码
        String contentType = "text/html;charset=utf-8";
        ServletActionContext.getResponse().setContentType(contentType);
        // 获取输出流，然后使用
        PrintWriter out = ServletActionContext.getResponse().getWriter();
        try {
            // 直接进行文本操作
            out.print(sb.toString());
            out.flush();
            out.close();
        } catch (Exception ex) {
            out.println(ex.toString());
        }

        return SUCCESS;
    }

    /**
     * 多麦网订单查询接口 add by songmiao 2016-3-15
     * 
     * @return
     * @throws Exception
     */
    public String queryOrderInfoDuoMai() throws Exception {
        double rate = 0.00; // 佣金比率
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // 时间格式
        HttpServletRequest request = getRequest();
        String start = request.getParameter("stime");
        String end = request.getParameter("etime");
        String orderCode = request.getParameter("sn");
        String key = request.getParameter("key") == null ? "" : request.getParameter("key");

        if (start == null && end == null && orderCode == null) {
            log.info("多麦网订单查询接口输入参数有误");
            return "error";
        }
        if (!key.equals("duomai")) {
            log.info("多麦网订单查询接口key值有误");
            return "error";
        }

        // RequestInfo ri = new RequestInfo();
        // String queryStr = request.getQueryString();
        // ri.setRequestIP(getIP(request));
        // ri.setRequestParams(queryStr);
        // ri.setSystemCode("b2b");
        // ri.setRequestSource("out");
        // ri.setServiceName("queryOrderInfoDuoMai");


        // sql查询条件对象
        Map<String, Object> newConditon = new HashMap<String, Object>();

        if (orderCode != null && !orderCode.isEmpty()) {
            newConditon.put("orderCode", orderCode);
        }
        if (start != null) {
            String sStart = format.format(new Date(Long.parseLong(start) * 1000));
            newConditon.put("createDateBeginDM", sStart);
        }
        if (end != null) {
            String sEtart = format.format(new Date(Long.parseLong(end) * 1000));
            newConditon.put("createDateEndDM", sEtart);
        }
        Pagination page = this.getPagination(5, 10);
        // 设置查询条件
        page.setObjCondition(newConditon);
        page.setNumperpage(Integer.valueOf(1));
        this.pagintion = orderTrailEmailService.findOrderMainByPageDuoMai(page);
        // this.pagintion = orderTrailEmailService.findOrderMainByPageLK(page);
        List<OrderMain> orderMainList = pagintion.getRecordList();

        // 一级节点
        JSONObject json = new JSONObject();
        json.put("success", "1");
        json.put("errors", "");
        // 二级节点
        JSONObject resultsJson = new JSONObject();
        // 二级节点子集
        JSONArray jsonArray = new JSONArray();
        // 四级节点
        JSONObject itemJson = new JSONObject();
        JSONArray jsonArray2 = new JSONArray();
        for (OrderMain om : orderMainList) {
            Double sumIncoming = 0.00;// 订单总金额
            resultsJson.put("test", "0");// 是否为测试数据，正式上线数据改为0或去除
            resultsJson.put("euid", om.getArea()); // 接入接口传入的euid 参数值
            resultsJson.put("order_sn", om.getOrderCode()); // 订单编号
            resultsJson.put("order_time", format.format(om.getCreateDate())); // 下单时间
            resultsJson.put("click_time", format.format(om.getPayDate()));// 点击时间,关联查询到跳转时间赋值给支付时间
            resultsJson.put("discount_amount", om.getDiscountAmount()); // 订单级别的优惠金额,
            resultsJson.put("order_status", om.getOrderStatus());// 订单状态
            resultsJson.put("referer", ""); // 流量来源，我方系统未记录
            List<OrderItem> l = orderItemService.findByOrderCode(om.getOrderCode());
            for (OrderItem oi : l) {
                itemJson.put("goods_id", oi.getCommoditySku() + "_" + oi.getOrderItemId());// 商品编号
                itemJson.put("goods_ta", oi.getCommodityNumber());// 商品数量
                itemJson.put("goods_price", oi.getCommodityUnitIncoming());// 商品单价
                itemJson.put("goods_name", oi.getCommodityName());// 商品名称
                itemJson.put("goods_cate", "1"); // 商品分类编号
                itemJson.put("rate", rate); // 佣金比率
                itemJson.put("commission", Double.valueOf(oi.getCommoditySum().toString()) * rate);// 此商品产生的佣金
                itemJson.put("totalPrice", Double.valueOf(oi.getCommodityUnitIncoming().toString())
                        * oi.getCommodityNumber());// 此商品有效总金额
                sumIncoming = sumIncoming + Double.valueOf(oi.getCommodityUnitIncoming().toString())
                        * oi.getCommodityNumber();
                jsonArray2.add(itemJson);
                itemJson = new JSONObject();
            }
            resultsJson.put("orders_price", sumIncoming);// 订单有效总金额
            resultsJson.put("details", jsonArray2);
            jsonArray.add(resultsJson);
            jsonArray2 = new JSONArray();
            resultsJson = new JSONObject();
        }
        json.put("orders", jsonArray);

        // 指定输出内容类型和编码
        String contentType = "text/html;charset=utf-8";
        ServletActionContext.getResponse().setContentType(contentType);
        // 获取输出流，然后使用
        PrintWriter out = ServletActionContext.getResponse().getWriter();
        try {
            // 直接进行文本操作
            out.print(json.toString());
            out.flush();
            out.close();
        } catch (Exception ex) {
            out.println(ex.toString());
        }

        return SUCCESS;
    }

    /**
     * 校验时间是否合法 add by songmiao 2016-3-4
     * 
     * @param date yyyyMMdd
     * @return
     */
    public boolean isValidDate(String date) {
        try {
            int[] DAYS = {0, 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
            int year = Integer.parseInt(date.substring(0, 4));
            if (year <= 0)
                return false;
            int month = Integer.parseInt(date.substring(4, 6));
            if (month <= 0 || month > 12)
                return false;
            int day = Integer.parseInt(date.substring(6, 8));
            if (day <= 0 || day > DAYS[month])
                return false;
            if (month == 2 && day == 29 && !isGregorianLeapYear(year)) {
                return false;
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return false;
        }
        return true;
    }

    public boolean isGregorianLeapYear(int year) {
        return year % 4 == 0 && (year % 100 != 0 || year % 400 == 0);
    }

    /**
     * 生成亿起发数据查询接口参数
     * 
     * @param request
     * @param flag 0:按下单时间查询 1:按更新时间查询
     * @return
     */
    private Map<String, Object> genCPSYQFQueryOrderParams(HttpServletRequest request, int flag) {
        Map<String, Object> params = new HashMap<String, Object>();
        String queryStr = request.getQueryString();
        String cid = request.getParameter("cid");
        String orderStartTime = request.getParameter("orderStartTime");
        String orderEndTime = request.getParameter("orderEndTime");
        String updateStartTime = request.getParameter("updateStartTime");
        String updateEndTime = request.getParameter("updateEndTime");
        String mid = request.getParameter("mid");
        String type = request.getParameter("type");
        if (StringUtil.isEmpty(type)) {
            type = "1";
        }
        String[] cpsInfo =
                CPSUtils.B2B_CPS_INFO.get(CPSUtils.B2B_PROMOTION_TYPE.get(Integer.parseInt(type)));
        if (null != cpsInfo && cpsInfo.length > 0 && cpsInfo[7].equals(mid)) {
            try {
                params.put("cid", cid);
                params.put("mid", mid);
                params.put("type", type);
                params.put("cpsInfo", cpsInfo);
                String startDate = "", endDate = "";
                if (0 == flag && !StringUtil.isEmpty(orderStartTime)
                        && !StringUtil.isEmpty(orderEndTime)) {
                    startDate = orderStartTime;
                    endDate = orderEndTime;
                } else if (1 == flag && !StringUtil.isEmpty(updateStartTime)
                        && !StringUtil.isEmpty(updateEndTime)) {
                    startDate = updateStartTime;
                    endDate = updateEndTime;
                } else {
                    params.put("errorStr", "paramter is not the numeric! ");
                    log.error("亿起发CPS查询出错，请求参数错误：" + queryStr);
                    return params;
                }
                if (!StringUtil.isEmpty(startDate)) {
                    Date startDateObj = new Date(Long.parseLong(startDate) * 1000l);
                    startDate = sdf.format(startDateObj.toInstant());
                    endDate = sdf.format(new Date(Long.parseLong(endDate) * 1000l).toInstant());
                    params.put("startDate", startDate);
                    params.put("endDate", endDate);
                    Calendar cal = Calendar.getInstance();
                    cal.set(Calendar.MONTH,
                            cal.get(Calendar.MONTH) - Integer.parseInt(cpsInfo[4]) - 1);// 允许查询一个周期+1个月的数据
                    Date minDate = cal.getTime();
                    if (minDate.compareTo(startDateObj) > 0) {
                        params.put("errorStr", "no data!");
                        return params;
                    }
                }
            } catch (Exception e) {
                params.put("errorStr", "paramter is not the numeric! ");
                log.error("亿起发CPS查询出错，CID参数错误！", e);
                return params;
            }

            RequestInfo ri = new RequestInfo();
            ri.setRequestIP(getIP(request));
            ri.setRequestParams(queryStr);
            ri.setSystemCode("b2b");
            ri.setRequestSource("out");
            ri.setUid(Long.parseLong(type));
            if (0 == flag) {
                ri.setServiceName("queryOrderInfoByCreateDate");
            } else if (1 == flag) {
                ri.setServiceName("queryOrderInfoByUpdateDate");
            }
            int queryCount;
            try {
                queryCount = promotionStatisticsService.queryRequestCount(ri);
                if (queryCount >= Integer.parseInt(cpsInfo[6])) {
                    params.put("errorStr", "no data!");
                    return params;
                }
            } catch (ServiceException e) {
                params.put("errorStr", "no data!");
                log.error("亿起发CPS查询出错，查询超过当天查询次数上限！", e);
            }

            try {
                if (!StringUtil.withinRangeByte(queryStr, 0, 500)) {
                    ri.setRequestParams("");
                    log.error("查询参数字符超过上限！" + ri.toString());
                }
                promotionStatisticsService.insertRequestInfo(ri);
            } catch (ServiceException e) {
                log.error("新增数据接口查询信息发生异常", e);
            }
        } else {
            params.put("errorStr", "sign is error! ");
            log.error("亿起发CPS查询出错，签名错误！");
        }
        return params;
    }

    /**
     * 获取客户端IP
     * 
     * @param request
     * @return
     */
    private String getIP(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (StringUtil.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (StringUtil.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StringUtil.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    public Map getResultMap() {
        return resultMap;
    }

    public void setResultMap(Map resultMap) {
        this.resultMap = resultMap;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public JSONObject getResultStr() {
        return resultStr;
    }

    public void setResultStr(JSONObject resultStr) {
        this.resultStr = resultStr;
    }

}
