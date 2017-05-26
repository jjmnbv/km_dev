package com.kmzyc.b2b.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSONObject;
import com.km.framework.page.Pagination;
import com.kmzyc.b2b.model.CouponGrant;
import com.kmzyc.b2b.model.User;
import com.kmzyc.b2b.service.LoginService;
import com.kmzyc.b2b.service.member.MyCouponService;
import com.kmzyc.b2b.vo.ReturnResult;
import com.kmzyc.framework.constants.Constants;
import com.kmzyc.framework.constants.CouponStatus;
import com.kmzyc.framework.constants.InterfaceResultCode;
import com.kmzyc.promotion.remote.service.CouponRemoteService;
import com.pltfm.app.dataobject.UserInfoDO;

@SuppressWarnings("unchecked")
@Scope("prototype")
@Controller("appCouponAction")
public class AppCouponAction extends AppBaseAction {

  private static final long serialVersionUID = 3443290927330717006L;

  // private static final Logger LOGGER = Logger.getLogger(AppCouponAction.class);
  private static Logger logger = LoggerFactory.getLogger(AppCouponAction.class);

  private ReturnResult<Map<String, Object>> returnResult;

  @Resource(name = "myCouponServiceImpl")
  private MyCouponService myCouponServiceImpl;

  @Resource(name = "loginServiceImp")
  private LoginService loginService;

  @Resource
  private CouponRemoteService couponRemoteService;

  private String uid;

  private String couponId;

  private String couponGrantId;

  private String couponStatus;

  // 每页多少条
  private int ps;
  // 第几页
  private int pn;

  private void setStartParam() {
    uid = getUserid();
    JSONObject jsonParams = AppJsonUtils.getJsonObject(getRequest());
    // uid = jsonParams.getString("uid");
    couponId = jsonParams.getString("couponId");
    couponStatus = jsonParams.getString("couponStatus");
    couponGrantId = jsonParams.getString("couponGrantId");
    ps = jsonParams.getIntValue("pageNum");
    pn = jsonParams.getIntValue("pageNo");
  }

  /**
   * 给用户发放优惠券
   */
  public String grantTouser() throws Exception {
    JSONObject jsonParams = AppJsonUtils.getJsonObject(getRequest());
    if (null != jsonParams && !jsonParams.isEmpty()) {
      this.setStartParam();
      try {
        // 发放
        // CouponRemoteService couponService =
        // (CouponRemoteService) RemoteTool.getRemote(Constants.PROMOTION_SYSTEM_CODE,
        // "couponService");
        UserInfoDO user = new UserInfoDO();
        user.setLoginId(Integer.parseInt(uid));
        couponRemoteService.changeCustomGrantToGive(user, Long.parseLong(couponId),
            Long.parseLong(uid), 51L, uid, null);
        setReturnResult(new ReturnResult<Map<String, Object>>(InterfaceResultCode.SUCCESS,
            "优惠券发放成功", null));
        return SUCCESS;
      } catch (Exception e) {
        logger.error("发放优惠券错误,couponId:" + couponId, e);
        setReturnResult(new ReturnResult<Map<String, Object>>(InterfaceResultCode.FAILED,
            "系统异常，优惠券发放失败！", null));
      }
    }
    setReturnResult(new ReturnResult<Map<String, Object>>(InterfaceResultCode.FAILED,
        "传参错误，未能进行优惠券发放", null));
    return SUCCESS;
  }

  /**
   * 用户获取自己的优惠券列表
   */
  public void getMyCoupon() {
    JSONObject jsonParams = AppJsonUtils.getJsonObject(getRequest());
    if (null != jsonParams && !jsonParams.isEmpty()) {
      this.setStartParam();
      if (null != uid) {
        // 用户查询条件
        Map<String, Object> newCondition = new HashMap<String, Object>();
        List<Integer> list = new ArrayList<Integer>();
        if (null != couponStatus) {
          if (0 == Integer.parseInt(couponStatus)) { // 无效的优惠券
            list.add(Integer.parseInt(CouponStatus.HAVEPASSDATE_COUPONSTATUS.getType()));
            list.add(Integer.parseInt((CouponStatus.INVALID_COUPONSTATUS.getType())));
            newCondition.put("orderItem1", 1);
          } else if (2 == Integer.parseInt(couponStatus)) { // 已使用
            list.add(Integer.parseInt(CouponStatus.FREEZE_COUPONSTATUS.getType()));
            list.add(Integer.parseInt(CouponStatus.HAVEUSE_COUPONSTATUS.getType()));
            newCondition.put("orderItem2", 2);
          } else {// 未使用
            list.add(Integer.parseInt(CouponStatus.NOTUSE_COUPONSTATUS.getType()));
            newCondition.put("orderItem3", 3);
          }
          newCondition.put("couponStatus", list);
        }
        newCondition.put("userId", uid);
        Pagination page = this.getPagination(5, ps);
        page.setStartindex((pn - 1) * ps + 1);
        page.setEndindex((ps * pn));
        page.setObjCondition(newCondition);
        try {
          page = myCouponServiceImpl.findCouponListByPage(page);
          List<CouponGrant> couponGrantList = page.getRecordList();
          Map<String, Object> map = new HashMap<String, Object>();
          // 分页
          map.put("couponGrantList", couponGrantList);
          List myCouponList = new ArrayList();
          for (CouponGrant grantVo : couponGrantList) {
            Map<String, Object> mapVo = new HashMap<String, Object>();
            mapVo.put("couponGrantId", grantVo.getCouponGrantId());
            mapVo.put("couponStatus", grantVo.getCouponStatus());
            mapVo.put("starttime", grantVo.getStarttime_coupon());
            mapVo.put("endtime", grantVo.getEndtime_coupon());
            mapVo.put("couponDescribe", grantVo.getCouponList().get(0).getCouponDescribe());
            mapVo.put("couponMoney", grantVo.getCouponList().get(0).getCouponMoney());
            mapVo.put("couponName", grantVo.getCouponList().get(0).getCouponName());
            mapVo.put("payLeastMoney", grantVo.getCouponList().get(0).getPayLeastMoney());
            mapVo.put("useLimitsType", grantVo.getCouponList().get(0).getUseLimitsType());
            myCouponList.add(mapVo);
          }
          map.put("pageNo", pn);// 当前页码
          map.put("pageNum", ps);// 每页条数
          map.put("totalRecords", page.getTotalRecords());
          map.put("couponGrantList", myCouponList);
          setReturnResult(new ReturnResult<Map<String, Object>>(InterfaceResultCode.SUCCESS, "成功",
              map));
        } catch (Exception e) {
          logger.error("获取我的优惠券数据出错", e);
          setReturnResult(new ReturnResult<Map<String, Object>>(InterfaceResultCode.FAILED,
              "获取我的优惠券数据出错", null));
        }
      } else {
        returnResult =
            new ReturnResult<Map<String, Object>>(InterfaceResultCode.FAILED, "未登录！", null);
      }
    } else {
      logger.error("获取我的优惠券传参数据出错");
      returnResult =
          new ReturnResult<Map<String, Object>>(InterfaceResultCode.FAILED, "请求参数错误", null);
    }
    printJsonString(returnResult);
  }

  /**
   * 
   * 激活优惠券
   */
  public void activationCoupon() {
    JSONObject jsonParams = AppJsonUtils.getJsonObject(getRequest());
    if (null != jsonParams && !jsonParams.isEmpty()) {
      this.setStartParam();
      if (null != uid && null != couponGrantId) {
        try {
          CouponGrant grantCondition = new CouponGrant();
          grantCondition.setActiveCode(couponGrantId);
          CouponGrant grant = myCouponServiceImpl.getGrantByGrantVo(grantCondition);
          if (grant == null) {
            setReturnResult(new ReturnResult<Map<String, Object>>(InterfaceResultCode.FAILED, "没有"
                + couponGrantId + "此条记录", null));
          } else {
            User user = loginService.queryUserByLoginId(uid);
            logger.info("用户" + user.getLoginAccount() + "开始调用远程接口进行激活操作");
            int activeResult =
                myCouponServiceImpl.activitionCoupon(couponGrantId, user.getLoginId().intValue());
            if (Constants.ACTIVE_SUCCESS == activeResult) {
              setReturnResult(new ReturnResult<Map<String, Object>>(InterfaceResultCode.SUCCESS,
                  "用户" + uid + "激活优惠券成功", null));
            } else if (Constants.ACTIVE_NOUSE == activeResult) {
              setReturnResult(new ReturnResult<Map<String, Object>>(InterfaceResultCode.FAILED,
                  "激活优惠券失败,此条优惠券激活码无效。", null));
            } else if (Constants.ACTIVE_HAVEUSE == activeResult) {
              setReturnResult(new ReturnResult<Map<String, Object>>(InterfaceResultCode.FAILED,
                  "激活优惠券失败,此条优惠券已被激活。", null));
            } else if (Constants.ACTIVE_OUTTIME == activeResult) {
              setReturnResult(new ReturnResult<Map<String, Object>>(InterfaceResultCode.FAILED,
                  "激活优惠券失败,此条优惠券规则已过期。", null));
            }
          }
        } catch (Exception e) {
          logger.error("激活用户" + uid + "的礼品券失败", e);
          setReturnResult(new ReturnResult<Map<String, Object>>(InterfaceResultCode.SYS_ERROR,
              "激活用户" + uid + "的礼品券失败", null));
        }
      }
    }
    printJsonString(returnResult);
  }

  public ReturnResult<Map<String, Object>> getReturnResult() {
    return returnResult;
  }

  public void setReturnResult(ReturnResult<Map<String, Object>> returnResult) {
    this.returnResult = returnResult;
  }

}
