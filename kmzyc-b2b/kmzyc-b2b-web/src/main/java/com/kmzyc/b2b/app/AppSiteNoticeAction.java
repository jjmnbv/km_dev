package com.kmzyc.b2b.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSONObject;
import com.km.framework.action.BaseAction;
import com.kmzyc.b2b.model.BnesMessageCenter;
import com.kmzyc.b2b.model.BnesMessageCenterResponse;
import com.kmzyc.b2b.model.SiteNoticeResponse;
import com.kmzyc.b2b.service.MemberSiteNoticeService;
import com.kmzyc.b2b.vo.ReturnResult;
import com.kmzyc.framework.constants.InterfaceResultCode;
import com.kmzyc.util.StringUtil;
import com.kmzyc.zkconfig.ConfigurationUtil;
import com.whalin.MemCached.MemCachedClient;

@Scope("prototype")
@Controller("appSiteNoticeAction")
public class AppSiteNoticeAction extends BaseAction {

    /**
     * 序列化
     */
    private static final long serialVersionUID = -3958773689659863185L;

    // private static final Logger logger = Logger.getLogger(AppSiteNoticeAction.class);
    private static Logger logger = LoggerFactory.getLogger(AppSiteNoticeAction.class);

    @Resource(name = "memCachedClient")
    private MemCachedClient memCachedClient;
    @Resource(name = "memberSiteNoticeService")
    private MemberSiteNoticeService memberSiteNoticeService;

    private ReturnResult<Map<String, Object>> returnResult;
    private Long uid;
    private String token;
    private String message = "请求参数错误";
    private String code = InterfaceResultCode.FAILED;

    // 登录用户IDkey前缀
    // private static final String UID_PREX_KEY = ConfigurationUtil
    // .getString("b2b_login_uid_memcached_key_prex");

    private Integer SITENOTICE_NOT_READ_STATUS = 0;

    /**
     * 获取站内通知
     * 
     * @return
     */

    @SuppressWarnings("unchecked")
    public String getSiteNoticetList() {
        // pagintion = this.getPagination(5, 10);
        JSONObject jsonParams = AppJsonUtils.getJsonObject(getRequest());
        if (null != jsonParams && !jsonParams.isEmpty()) {
            uid = jsonParams.getLong("uid");
            token = jsonParams.getString("token");
            int pn = jsonParams.getIntValue("pn");
            int ps = jsonParams.getIntValue("ps");
            if (pn <= 0 || ps <= 0) {
                pn = 1;
                ps = 10;
            }
            this.page = pn;
            pagintion = this.getPagination(pn, ps);
            if (validToken(uid, token)) {
                try {
                    List<SiteNoticeResponse> siteNoticeResponseList =
                            new ArrayList<SiteNoticeResponse>();
                    List<BnesMessageCenter> siteNoticeList = null;
                    Map<String, Object> newConditon = new HashMap<String, Object>();
                    newConditon.put("userId", uid);
                    pagintion.setObjCondition(newConditon);
                    this.pagintion = memberSiteNoticeService.findSiteNoticeByUserId(pagintion);
                    Integer countNum = memberSiteNoticeService.countNoticeNum(uid);
                    siteNoticeList = pagintion.getRecordList();
                    for (Iterator<BnesMessageCenter> iterator = siteNoticeList.iterator(); iterator
                            .hasNext();) {
                        BnesMessageCenter siteNoticeDetail = iterator.next();
                        SiteNoticeResponse siteNoticeResponse = new SiteNoticeResponse();
                        siteNoticeResponse.setMessageId(siteNoticeDetail.getMessageId());
                        siteNoticeResponse.setTitle(siteNoticeDetail.getTitle());
                        siteNoticeResponse.setContent(siteNoticeDetail.getContent());
                        siteNoticeResponse.setReleaseDate(siteNoticeDetail.getReleaseDate());
                        siteNoticeResponse.setStatus(siteNoticeDetail.getStatus());
                        siteNoticeResponseList.add(siteNoticeResponse);
                    }
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("siteNoticeList", siteNoticeResponseList);
                    map.put("countNum", countNum);
                    returnResult = new ReturnResult<Map<String, Object>>(
                            InterfaceResultCode.SUCCESS, "成功", map);
                } catch (Exception e) {
                    logger.error("查询常见问题出错：" + e.getMessage(), e);
                    returnResult = new ReturnResult<Map<String, Object>>(InterfaceResultCode.FAILED,
                            "失败", null);
                }
            } else {
                message = "未登录！";
                code = InterfaceResultCode.NOT_LOGIN;
                returnResult = new ReturnResult<Map<String, Object>>(code, message, null);
            }
        } else {
            returnResult = new ReturnResult<Map<String, Object>>(code, message, null);
        }
        return SUCCESS;
    }

    /**
     * 获取站内通知
     * 
     * @return
     */

    @SuppressWarnings("unchecked")
    public String getLatestNotReadSiteNotice() {
        // pagintion = this.getPagination(5, 10);
        JSONObject jsonParams = AppJsonUtils.getJsonObject(getRequest());
        if (null != jsonParams && !jsonParams.isEmpty()) {
            uid = jsonParams.getLong("uid");
            token = jsonParams.getString("token");
            pagintion = this.getPagination(1, 10);
            if (validToken(uid, token)) {
                try {
                    Map<String, Object> newConditon = new HashMap<String, Object>();
                    newConditon.put("userId", uid);
                    newConditon.put("status", this.SITENOTICE_NOT_READ_STATUS);
                    pagintion.setObjCondition(newConditon);
                    this.pagintion =
                            memberSiteNoticeService.findLatestSiteNoticeByUserId(pagintion);
                    List<BnesMessageCenter> siteNoticeList = pagintion.getRecordList();
                    Map<String, Object> map = new HashMap<String, Object>();
                    if (siteNoticeList != null && siteNoticeList.size() > 0) {
                        BnesMessageCenter bnesMessageCenter = siteNoticeList.get(0);
                        BnesMessageCenterResponse bnesMessageCenterResponse =
                                new BnesMessageCenterResponse();
                        bnesMessageCenterResponse.setContent(bnesMessageCenter.getContent());
                        bnesMessageCenterResponse.setTitle(bnesMessageCenter.getTitle());
                        bnesMessageCenterResponse
                                .setReleaseDate(bnesMessageCenter.getReleaseDate());
                        map.put("bnesMessageCenter", bnesMessageCenterResponse);
                    } else {
                        map.put("bnesMessageCenter", null);
                    }
                    returnResult = new ReturnResult<Map<String, Object>>(
                            InterfaceResultCode.SUCCESS, "成功", map);
                } catch (Exception e) {
                    logger.error("查询常见问题出错：" + e.getMessage(), e);
                    returnResult = new ReturnResult<Map<String, Object>>(InterfaceResultCode.FAILED,
                            "失败", null);
                }
            } else {
                message = "未登录！";
                code = InterfaceResultCode.NOT_LOGIN;
                returnResult = new ReturnResult<Map<String, Object>>(code, message, null);
            }
        } else {
            returnResult = new ReturnResult<Map<String, Object>>(code, message, null);
        }
        return SUCCESS;
    }

    /**
     * 获取站内通知
     * 
     * @return
     */

    public String updateNoticeStatus() {
        JSONObject jsonParams = AppJsonUtils.getJsonObject(getRequest());
        if (null != jsonParams && !jsonParams.isEmpty()) {
            uid = jsonParams.getLong("uid");
            token = jsonParams.getString("token");
            Integer messageId = jsonParams.getInteger("messageId");
            if (validToken(uid, token)) {
                try {
                    this.memberSiteNoticeService.updateNoticeStatus(messageId);
                    returnResult = new ReturnResult<Map<String, Object>>(
                            InterfaceResultCode.SUCCESS, "成功", null);
                } catch (Exception e) {
                    logger.error("查询常见问题出错：" + e.getMessage(), e);
                    returnResult = new ReturnResult<Map<String, Object>>(InterfaceResultCode.FAILED,
                            "失败", null);
                }
            } else {
                message = "未登录！";
                code = InterfaceResultCode.NOT_LOGIN;
                returnResult = new ReturnResult<Map<String, Object>>(code, message, null);
            }
        } else {
            returnResult = new ReturnResult<Map<String, Object>>(code, message, null);
        }
        return SUCCESS;
    }

    /**
     * 校验令牌 成功:true，失败：false
     * 
     * @param loginId
     * @param token
     * @return
     */
    private boolean validToken(long loginId, String pToken) {
        boolean result = false;
        // b2b_login_uid_memcached_key_prex:登录用户IDkey前缀
        if (!StringUtil.isEmpty(pToken) && 0L != loginId) {
            String mToken = (String) memCachedClient
                    .get(ConfigurationUtil.getString("b2b_login_uid_memcached_key_prex") + loginId);
            result = pToken.equalsIgnoreCase(mToken);
        }
        return result;
    }

    public MemCachedClient getMemCachedClient() {
        return memCachedClient;
    }

    public void setMemCachedClient(MemCachedClient memCachedClient) {
        this.memCachedClient = memCachedClient;
    }

    public ReturnResult<Map<String, Object>> getReturnResult() {
        return returnResult;
    }

    public void setReturnResult(ReturnResult<Map<String, Object>> returnResult) {
        this.returnResult = returnResult;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
