package com.kmzyc.b2b.action.member;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.km.framework.action.BaseAction;
import com.km.framework.page.Pagination;
import com.kmzyc.b2b.model.CouponGrant;
import com.kmzyc.b2b.model.User;
import com.kmzyc.b2b.service.LoginService;
import com.kmzyc.b2b.service.member.MyCouponService;
import com.kmzyc.b2b.vo.ReturnResult;
import com.kmzyc.framework.ajax.AjaxUtil;
import com.kmzyc.framework.constants.Constants;
import com.kmzyc.framework.constants.CouponStatusMap;
import com.kmzyc.framework.constants.InterfaceResultCode;
import com.pltfm.app.enums.CouponStatus;

@SuppressWarnings("serial")
@Controller
@Scope("prototype")
public class MyCouponAction extends BaseAction {
    // private static Logger logger = Logger.getLogger(MyCouponAction.class);
    private static Logger logger = LoggerFactory.getLogger(MyCouponAction.class);

    @Resource(name = "myCouponServiceImpl")
    private MyCouponService myCouponServiceImpl;

    @Resource(name = "loginServiceImp")
    private LoginService loginService;

    private static final String KEYWORD_TIPS = "请输入红包验证码";

    // 优惠券的个体使用状态
    private Map<String, String> createCouponStatusMap;

    // 优惠券激活号
    private String grantId;

    private String couponStatus;

    // 返回至页面的对象
    @SuppressWarnings("rawtypes")
    private ReturnResult returnResult;

    // wap项目的第几页
    private String pageIndex;

    /**
     * 查询会员的优惠券列表
     * 
     * @return
     */
    @SuppressWarnings({"unchecked", "static-access"})
    public String queryCouponList() {
        // 初始化优惠券状态的查询条件项
        // initCouponStatusSearch();
        HttpServletRequest request = ServletActionContext.getRequest();
        Long userId = (Long) request.getSession().getAttribute(Constants.SESSION_USER_ID);
        Pagination page = this.getPagination(5, 10);

        // 页面接受查询条件
        // Map<String, String> objconditon = (Map<String, String
        // >)page.getObjCondition();
        // 后台进行查询的条件
        Map<String, Object> newCondition = new HashMap<String, Object>();
        List<Integer> list = new ArrayList<Integer>();

        if (null != couponStatus && 0 == Integer.parseInt(couponStatus)) { // 无效的优惠券
            list.add(Integer.parseInt(CouponStatus.HAVEPASSDATE_COUPONSTATUS.getType()));
            list.add(Integer.parseInt((CouponStatus.INVALID_COUPONSTATUS.getType())));
            newCondition.put("orderItem1", 1);
        } else if (null != couponStatus && 4 == Integer.parseInt(couponStatus)) { // 已使用
            list.add(Integer.parseInt(CouponStatus.FREEZE_COUPONSTATUS.getType()));
            list.add(Integer.parseInt(CouponStatus.HAVEUSE_COUPONSTATUS.getType()));
            newCondition.put("orderItem2", 2);
        } else {// 未使用
            list.add(Integer.parseInt(CouponStatus.NOTUSE_COUPONSTATUS.getType()));
            newCondition.put("orderItem3", 3);
        }
        newCondition.put("userId", userId);
        newCondition.put("couponStatus", list);
        page.setObjCondition(newCondition);
        try {
            this.pagintion = myCouponServiceImpl.findCouponListByPage(page);
        } catch (Exception e) {
            logger.error("查询我的优惠券列表出错：" + e.getMessage(), e);
            return ERROR;
        }
        return SUCCESS;
    }

    /**
     * 初始化页面优惠券状态
     * 
     */
    private void initCouponStatusSearch() {
        createCouponStatusMap = CouponStatusMap.getMap();
    }

    /**
     * 激活优惠券
     * 
     * @return
     * @throws Exception
     */
    public String activeCoupon() {
        Map map = new HashMap();
        Long userId = (Long) getSession().getAttribute(Constants.SESSION_USER_ID);
        if (userId == null) {// 未登录
            map.put("result", "noSession");
            AjaxUtil.writeJSONToResponse(map);
            return NONE;
        }
        try {
            User user = loginService.queryUserByLoginId(userId.toString());
            logger.info("用户" + user.getLoginAccount() + "开始激活优惠券，优惠券发放编码：" + grantId);
            // // 用户未验证手机号
            // if (user.getMobile() == null || StringUtils.isEmpty(user.getMobile())) {
            // map.put("result", "tel_Unpass");
            // AjaxUtil.writeJSONToResponse(map);
            // return NONE;
            // } else {
            logger.info("用户" + user.getLoginAccount() + "开始调用远程接口进行激活操作");
            int activeResult = myCouponServiceImpl.activitionCoupon(grantId, userId.intValue());

            if (Constants.ACTIVE_SUCCESS == activeResult) {
                // CouponGrant grantCondition = new CouponGrant();
                // CouponGrant grant = myCouponServiceImpl.getCouponByGrantCode(grantId);
                // boolean result =
                // myCouponServiceImpl.sendActivitionMsg(user,grant);
                logger.info("用户" + user.getLoginAccount() + "激活优惠券成功");
                map.put("result", "activition_success");
                AjaxUtil.writeJSONToResponse(map);
                return NONE;
            } else if (Constants.ACTIVE_NOUSE == activeResult) {
                map.put("result", "no_couponGrant");
                AjaxUtil.writeJSONToResponse(map);
                return NONE;
            } else if (Constants.ACTIVE_HAVEUSE == activeResult) {
                map.put("result", "have_activition");
                logger.info("用户" + user.getLoginAccount() + "此条优惠码已被激活");
                AjaxUtil.writeJSONToResponse(map);
                return NONE;
            } else if (Constants.ACTIVE_OUTTIME == activeResult) {
                map.put("result", "activition_outTime");
                logger.info("用户" + user.getLoginAccount() + "此条优惠码对应的优惠券规则已过期");
                AjaxUtil.writeJSONToResponse(map);
                return NONE;
            } else {
                map.put("result", "activition_erro");
                logger.error("用户" + user.getLoginAccount() + "b2b端优惠券激活失败，系统异常");
                AjaxUtil.writeJSONToResponse(map);
                return NONE;
            }
            // }
        } catch (Exception e) {
            map.put("result", "remote_erro");
            AjaxUtil.writeJSONToResponse(map);
            logger.error("用户" + userId + "b2b 异常报错 优惠券激活异常" + e.getMessage(), e);
        }
        return NONE;
    }

    /**
     * wab端我的优惠券的列表
     * 
     * @return
     */
    public String wapQueryCoupon() {
        // 初始化优惠券状态的查询条件项
        HttpServletRequest request = ServletActionContext.getRequest();
        Long userId = (Long) request.getSession().getAttribute(Constants.SESSION_USER_ID);
        Pagination page = this.getPagination(5, 10);
        pageIndex = request.getParameter("pageIndex");
        if (pageIndex != null) {
            if (!pageIndex.equals("1")) {
                page.setStartindex((Integer.parseInt(pageIndex.toString()) - 1) * 10 + 1);
                page.setEndindex(Integer.parseInt(pageIndex.toString()) * 10);
            }
        }

        Map<String, Object> newCondition = new HashMap<String, Object>();
        List<Integer> list = new ArrayList<Integer>();
        Map<String, Object> mapData = new HashMap<String, Object>();

        if (null != couponStatus && 0 == Integer.parseInt(couponStatus)) { // 无效的优惠券:0
            list.add(Integer.parseInt(CouponStatus.HAVEPASSDATE_COUPONSTATUS.getType()));
            list.add(Integer.parseInt((CouponStatus.INVALID_COUPONSTATUS.getType())));
            newCondition.put("orderItem1", 1);
        } else if (null != couponStatus && 4 == Integer.parseInt(couponStatus)) { // 已使用:4
            list.add(Integer.parseInt(CouponStatus.FREEZE_COUPONSTATUS.getType()));
            list.add(Integer.parseInt(CouponStatus.HAVEUSE_COUPONSTATUS.getType()));
            newCondition.put("orderItem2", 2);
        } else {// 未使用
            list.add(Integer.parseInt(CouponStatus.NOTUSE_COUPONSTATUS.getType()));
            newCondition.put("orderItem3", 3);
        }
        newCondition.put("userId", userId);
        newCondition.put("couponStatus", list);
        page.setObjCondition(newCondition);
        try {
            this.pagintion = myCouponServiceImpl.findCouponListByPage(page);
            List<CouponGrant> grantList = pagintion.getRecordList();
            mapData.put("totalPage", page.getTotalpage());
            if (grantList.size() > 0) {
                mapData.put("myCouponList", grantList);
            }
            returnResult = new ReturnResult(InterfaceResultCode.SUCCESS, "成功", mapData);
        } catch (Exception e) {
            logger.error("查询我的优惠券列表出错：" + e.getMessage(), e);
            returnResult = new ReturnResult(InterfaceResultCode.FAILED, "失败", null);
            return ERROR;
        }
        return SUCCESS;
    }

    /**
     * wap端激活优惠券
     * 
     * @return
     */
    public String wapActiveCoupon() {
        Long userId = (Long) getSession().getAttribute(Constants.SESSION_USER_ID);
        if (userId == null) {// 未登录
            returnResult = new ReturnResult(InterfaceResultCode.FAILED, "用户未登陆", null);
            return SUCCESS;
        }
        try {
            User user = loginService.queryUserByLoginId(userId.toString());
            logger.info("wab端用户" + user.getLoginAccount() + "开始激活优惠券，优惠券发放编码：" + grantId);
            // 用户未验证手机号
            // if (user.getMobile() == null || StringUtils.isEmpty(user.getMobile())) {
            // returnResult = new ReturnResult(InterfaceResultCode.FAILED, "手机号未验证", null);
            // return SUCCESS;
            // } else {
            logger.info("wab用户" + user.getLoginAccount() + "开始调用远程接口进行激活操作");
            int activeResult = myCouponServiceImpl.activitionCoupon(grantId, userId.intValue());
            if (Constants.ACTIVE_SUCCESS == activeResult) {
                // CouponGrant grantCondition = new CouponGrant();
                // CouponGrant grant = myCouponServiceImpl.getCouponByGrantCode(grantId);
                logger.info("用户" + user.getLoginAccount() + "激活优惠券成功");
                returnResult = new ReturnResult(Constants.ACTIVE_SUCCESS + "", "优惠券激活成功", null);
                return SUCCESS;
            } else if (Constants.ACTIVE_NOUSE == activeResult) {
                returnResult = new ReturnResult(Constants.ACTIVE_NOUSE + "", "优惠券激活码无效", null);
                return SUCCESS;
            } else if (Constants.ACTIVE_HAVEUSE == activeResult) {
                logger.info("用户" + user.getLoginAccount() + "此条优惠码已被激活");
                returnResult = new ReturnResult(Constants.ACTIVE_HAVEUSE + "", "此条优惠码已被激活", null);
                return SUCCESS;
            } else if (Constants.ACTIVE_OUTTIME == activeResult) {
                logger.info("用户" + user.getLoginAccount() + "此条优惠码对应的优惠券规则已过期");
                returnResult =
                        new ReturnResult(Constants.ACTIVE_OUTTIME + "", "此条优惠码对应的优惠券规则已过期", null);
                return SUCCESS;
            } else {
                logger.error("用户" + user.getLoginAccount() + "b2b端优惠券激活失败，系统异常");
                returnResult = new ReturnResult(InterfaceResultCode.FAILED, "系统异常", null);
                return SUCCESS;
            }
            // }
        } catch (Exception e) {
            returnResult = new ReturnResult(InterfaceResultCode.FAILED, "系统异常", null);
            logger.error("用户" + userId + "b2b 异常报错 优惠券激活异常" + e.getMessage(), e);
        }
        return NONE;
    }

    public Map<String, String> getCreateCouponStatusMap() {
        return createCouponStatusMap;
    }

    public void setCreateCouponStatusMap(Map<String, String> createCouponStatusMap) {
        this.createCouponStatusMap = createCouponStatusMap;
    }

    public String getGrantId() {
        return grantId;
    }

    public void setGrantId(String grantId) {
        this.grantId = grantId;
    }

    public String getCouponStatus() {
        return couponStatus;
    }

    public void setCouponStatus(String couponStatus) {
        this.couponStatus = couponStatus;
    }

    public ReturnResult getReturnResult() {
        return returnResult;
    }

    public void setReturnResult(ReturnResult returnResult) {
        this.returnResult = returnResult;
    }
}
