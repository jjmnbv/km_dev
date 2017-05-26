package com.pltfm.app.action;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kmzyc.commons.exception.ActionException;
import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.express.entities.ExpressCompany;
import com.kmzyc.express.entities.ExpressSubscription;
import com.kmzyc.express.remote.ExpressSubscriptionRemoteService;
import com.kmzyc.express.util.constant.ExpressSubConstants;
import com.pltfm.app.service.ExpressCompanyService;
import com.pltfm.app.service.ExpressSubscriptionService;

import redis.clients.jedis.JedisCluster;

@Controller("expressSubscriptionAction")
@Scope("prototype")
@SuppressWarnings("unchecked")
public class ExpressSubscriptionAction extends BaseAction {

    /**
     * UID
     */
    private static final long serialVersionUID = 7140471830782308308L;

    private Logger log = Logger.getLogger(ExpressSubscriptionAction.class);

    @Resource
    private ExpressSubscriptionService expressSubscriptionService;

    @Resource
    private ExpressCompanyService expressCompanyService;

    @Resource(name = "jedisCluster")
    private JedisCluster jedisCluster;

    private Map<String, String> map = new HashMap<String, String>();

    private ExpressSubscription expressSubscription = new ExpressSubscription();

    @Resource
    private ExpressSubscriptionRemoteService expressSubscriptionRemoteService;

    /**
     * 物流订阅列表
     */
    public String pageList() throws ActionException {
        try {
            // 查询取现记录数
            int count = expressSubscriptionService.queryExpressSubscriptionCount(map);
            // 分页查询参数
            map.put("startRow", ((getPage().getPageNo() - 1) * getPage().getPageSize() + 1) + "");
            map.put("endRow", (getPage().getPageNo() * getPage().getPageSize()) + "");
            // page赋值
            getPage().setRecordCount(count);
            getPage().setDataList(
                    expressSubscriptionService.queryExpressSubscriptionListByPage(map));
        } catch (Exception ex) {
            log.error("查询物流订阅列表异常:" + ex.getMessage());
            throw new ActionException("物流信息订阅异常：" + ex.getMessage());
        }
        return "PageList";
    }

    /**
     * 查询物流订阅及跟踪信息
     */
    public String querySubWithDetail() throws ActionException {
        try {
            String logisticsCode = map.get("logisticsCode");
            String logisticsOrderNo = map.get("logisticsNo");
            if (StringUtils.isNotEmpty(logisticsCode) && StringUtils.isNotEmpty(logisticsOrderNo)) {
                expressSubscription = expressSubscriptionRemoteService
                        .queryOrderExpressInfo(logisticsCode, logisticsOrderNo);
            }
        } catch (Exception ex) {
            log.error("查询物流跟踪信息异常：" + ex.getMessage());
            throw new ActionException("查询物流跟踪信息异常：" + ex.getMessage());
        }
        return "SubDetail";
    }

    public String initInsert() throws ActionException {
        try {
            List<ExpressCompany> companyList = expressCompanyService.queryAllEnableExpressCompany();
            JSONArray jsonarray = new JSONArray();
            JSONObject tempJSON = new JSONObject();
            if (companyList != null && companyList.size() > 0) {
                for (int i = 0; i < companyList.size(); i++) {
                    tempJSON = new JSONObject();
                    tempJSON.put("value", companyList.get(i).getLogisticsName() + "(" +
                            companyList.get(i).getLogisticsCode() + ")");
                    tempJSON.put("data", companyList.get(i).getLogisticsCode());
                    jsonarray.add(tempJSON);
                }
            }
            super.getHttpServletRequest()
                    .setAttribute("availableExpCompanys", jsonarray.toJSONString());
        } catch (ServiceException e) {
            throw new ActionException("初始化新增订阅出错：" + e.getMessage());
        }
        return "Insert";
    }

    // 新增订阅
    public String saveInsert() throws ActionException {

        try {
            String result = expressSubscriptionRemoteService
                    .sucribeOrderExpressInfo(expressSubscription);
        } catch (Exception ex) {
            log.error("提交订阅失败," + ex.getMessage());
            throw new ActionException("初始化新增订阅出错：" + ex.getMessage());
        }
        return "SaveSuccess";
    }

    // 重新订阅
    public String reSubscribeExpres() throws ActionException {

        try {
            // 根据主键重新获取订阅记录
            expressSubscription = expressSubscriptionService
                    .selectSubscriptionByPrimaryKey(expressSubscription);
            String result = expressSubscriptionRemoteService
                    .sucribeOrderExpressInfo(expressSubscription);
        } catch (Exception ex) {
            throw new ActionException(ex.getMessage());
        }
        return "SaveSuccess";
    }

    // 校验是否该次订阅已经在订阅处理中
    public void checkIsHaveSubscribed() throws ActionException {
        JSONObject jsonResult = new JSONObject();
        boolean cacheFlag = false;
        try {
            HttpServletRequest request = super.getHttpServletRequest();
            HttpServletResponse response = super.getHttpServletResponse();
            String subId = request.getParameter("subId");
            String logisticsCode = request.getParameter("logisticsCode");
            String logisticsNo = request.getParameter("logisticsNo");
            if (expressSubscription == null) {
                expressSubscription = new ExpressSubscription();
            }
            expressSubscription.setSubId(new BigDecimal(subId));

            // 查看是否缓存
            StringBuilder sBuilder = new StringBuilder(200);
            sBuilder.append("expresssubprocessingflag-").append(logisticsCode).append("-")
                    .append(logisticsNo);
            cacheFlag = jedisCluster.exists(sBuilder.toString());

            // 如果缓存中存在提示正在缓存
            if (cacheFlag) {
                jsonResult.put("scribFlag", "IS_SCRIBEDING"); // 正在订阅中
            } else {
                // 如果缓存中不存在，查询订阅状态
                expressSubscription = expressSubscriptionService
                        .selectSubscriptionByPrimaryKey(expressSubscription);
                Integer trackStatus = expressSubscription.getTrackStatus();
                if (!ExpressSubConstants.TrackStatus.NOSUB.getIntegerKey().equals(trackStatus) &&
                        !ExpressSubConstants.TrackStatus.SUB_FAIL.getIntegerKey()
                                .equals(trackStatus)) {
                    jsonResult.put("scribFlag", "HAVE_SCRIBED");// 已订阅
                } else {
                    jsonResult.put("scribFlag", "NOT_SCRIBEDED");// 未订阅，或未订阅中
                }
            }
            response.getWriter().print(jsonResult.toString());
        } catch (Exception e) {
            throw new ActionException("初始化新增订阅出错：" + e.getMessage());
        }
    }

    public void checkDuplicate() {
        try {
            boolean checkResult = false;
            HttpServletRequest request = super.getHttpServletRequest();
            HttpServletResponse response = super.getHttpServletResponse();
            String logisticsCode = request.getParameter("logisticsCode");
            String logisticsNo = request.getParameter("logisticsNo");
            map = new HashMap<String, String>();
            map.put("logisticsCode", logisticsCode);
            map.put("logisticsNo", logisticsNo);
            int count = this.expressSubscriptionService.querySubCountByLosCodeAndNo(map);
            if (count > 0) {
                checkResult = true;
            }
            JSONObject jsonResult = new JSONObject();
            jsonResult.put("checkResult", String.valueOf(checkResult));
            response.getWriter().print(jsonResult.toString());
        } catch (Exception ex) {
            log.error("校验新增的跟踪单据是否重复失败," + ex.getMessage());
        }
    }


    public ExpressSubscription getExpressSubscription() {
        return expressSubscription;
    }

    public void setExpressSubscription(ExpressSubscription expressSubscription) {
        this.expressSubscription = expressSubscription;
    }

    public Map<String, String> getMap() {
        return map;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }

}
