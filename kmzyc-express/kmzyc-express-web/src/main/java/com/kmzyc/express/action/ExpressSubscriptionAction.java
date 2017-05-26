package com.kmzyc.express.action;

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

import com.alibaba.fastjson.JSONObject;
import com.kmzyc.commons.exception.ActionException;
import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.express.entities.ExpressSubscription;
import com.kmzyc.express.service.ExpressCompanyService;
import com.kmzyc.express.service.ExpressSubscriptionService;
import com.kmzyc.express.util.JacksonHelper;
import com.kmzyc.express.util.Page;
import com.kmzyc.express.util.constant.ExpressSubConstants;
import com.kmzyc.express.vobject.ExpressNoticeRequestVO;
import com.kmzyc.express.vobject.ExpressNoticeResponseVO;

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

    private Map<String, String> map = new HashMap<String, String>();

    private ExpressSubscription expressSubscription = new ExpressSubscription();

    @Resource(name="jedisCluster")
    private JedisCluster jedisCluster;

    // 分页对象
    private Page page;

    /**
     * 物流订阅列表
     * 
     * @return
     * @throws ActionException
     */
    public String pageList() throws ActionException {
        if (page == null) {
            page = new Page();
        }
        try {
            // 查询取现记录数
            int count = expressSubscriptionService.queryExpressSubscriptionCount(map);
            // page赋值
            page.setRecordCount(count);
            // 分页查询参数
            map.put("startRow", ((getPage().getPageNo() - 1) * getPage().getPageSize() + 1) + "");
            map.put("endRow", (getPage().getPageNo() * getPage().getPageSize()) + "");
            // 设置datalist
            page.setDataList(expressSubscriptionService.queryExpressSubscriptionListByPage(map));
        } catch (Exception ex) {
            throw new ActionException("物流信息订阅异常：" + ex.getMessage());
        }
        return "PageList";
    }

    /**
     * 物流订阅及跟踪信息
     * 
     * @return
     * @throws ActionException
     */
    public String querySubWithDetail() throws ActionException {
        try {
            String logisticsCode = map.get("logisticsCode");
            String logisticsOrderNo = map.get("logisticsNo");
            if (StringUtils.isNotEmpty(logisticsCode) && StringUtils.isNotEmpty(logisticsOrderNo)) {
                List resultList =
                        expressSubscriptionService.queryExpressSubWithTrackDetail(
                            logisticsCode, logisticsOrderNo);
                if (resultList != null && resultList.size() > 0) {
                    expressSubscription = (ExpressSubscription) resultList.get(0);
                }
            }
        } catch (Exception ex) {
            throw new ActionException("查询物流跟踪信息异常：" + ex.getMessage());
        }
        return "SubDetail";
    }

    public String initInsert() throws ActionException {
        try {
            List companyList = expressCompanyService.queryAllEnableExpressCompany();
            super.getHttpServletRequest().setAttribute("companyList", companyList);

        } catch (ServiceException e) {
            throw new ActionException("初始化新增订阅出错：" + e.getMessage());
        }
        return "Insert";
    }

    // 新增订阅
    public String saveInsert() throws ActionException {
        BigDecimal subId = BigDecimal.ZERO;
        try {
            // 内部系统向中间件订阅
            if (expressSubscription != null) {
                subId = expressSubscriptionService.executeInnerSubscription(expressSubscription);
            }
        } catch (ServiceException e) {
            throw new ActionException(e.getMessage());
        }
        return "SaveSuccess";
    }

    // 重新订阅
    public String reSubscribeExpres() throws ActionException {
        try {
            // 根据主键重新获取订阅记录
            expressSubscription =
                    expressSubscriptionService.selectSubscriptionByPrimaryKey(expressSubscription);
            // 执行订阅
            expressSubscriptionService.executeInnerSubscription(expressSubscription);
        } catch (ServiceException e) {
            throw new ActionException(e.getMessage());
        }
        return "SaveSuccess";
    }

    // 快递100回调,用来处理订阅推送的物流信息,写日志，新增物流轨迹，等操作
    public void callBackExpress() {
        // MD5.md5crypt(String),推送的消息进行md5检查,不通过只记录日志,暂不处理
        HttpServletRequest request = super.getHttpServletRequest();
        HttpServletResponse response = super.getHttpServletResponse();
        // 返回信息
        ExpressNoticeResponseVO responseVO = new ExpressNoticeResponseVO();
        responseVO.setResult(false);
        responseVO.setReturnCode("500");
        responseVO.setMessage("保存失败");

        try {
            String param = request.getParameter("param");
            // 必须要传参
            if (StringUtils.isNotEmpty(param)) {
                ExpressNoticeRequestVO requestVO = JacksonHelper.fromJSON(param, ExpressNoticeRequestVO.class);
                // 处理订阅推送的物流信息
                int result = expressSubscriptionService.processExpressSubPushInfo(requestVO);
                if (result > 0) {
                    responseVO.setResult(true);
                    responseVO.setReturnCode("200");
                    responseVO.setMessage("保存成功");
                } else {
                    responseVO.setMessage("保存失败，内部处理失败！");
                    log.error("处理快递100推送信息失败,造成原因：处理结果的影响为0！");
                }
                response.getWriter().print(JacksonHelper.toJSON(responseVO));
            } else {
                responseVO.setMessage("保存失败，造成原因：请求参数为空！");
                response.getWriter().print(JacksonHelper.toJSON(responseVO));
                log.error("处理快递100推送信息失败,造成原因：请求参数为空！");
            }
        } catch (Exception e) {
            try { // 处理信息异常，尝试发送响应
                responseVO.setMessage("保存失败：处理快递100推送信息异常");
                response.getWriter().print(JacksonHelper.toJSON(responseVO));
            } catch (Exception ex) {
                log.error("处理快递100推送信息异常," + ex.getMessage());
            }
            log.error("处理快递100推送信息异常," + e.getMessage());
        }
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
                expressSubscription =
                        expressSubscriptionService
                                .selectSubscriptionByPrimaryKey(expressSubscription);
                Integer trackStatus = expressSubscription.getTrackStatus();
                if (!ExpressSubConstants.TrackStatus.NOSUB.getIntegerKey().equals(trackStatus)
                        && !ExpressSubConstants.TrackStatus.SUB_FAIL.getIntegerKey().equals(
                                trackStatus)) {
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
            response.getWriter().print(JacksonHelper.toJSON(checkResult));
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

    @Override
    public Page getPage() {
        return page;
    }

    @Override
    public void setPage(Page page) {
        this.page = page;
    }

}
