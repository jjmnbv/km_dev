package com.kmzyc.b2b.action.member;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.km.framework.action.BaseAction;
import com.km.framework.page.Pagination;
import com.kmzyc.b2b.model.AccountInfo;
import com.kmzyc.b2b.model.AppraiseReply;
import com.kmzyc.b2b.model.Favorite;
import com.kmzyc.b2b.model.PersonalityInfo;
import com.kmzyc.b2b.model.User;
import com.kmzyc.b2b.service.EraInfoService;
import com.kmzyc.b2b.service.ProductPriceService;
import com.kmzyc.b2b.service.SecurityCentreService;
import com.kmzyc.b2b.service.member.AccountService;
import com.kmzyc.b2b.service.member.MyCouponService;
import com.kmzyc.b2b.service.member.MyFavoriteService;
import com.kmzyc.b2b.service.member.MyOrderService;
import com.kmzyc.b2b.service.member.ProdAppraiseService;
import com.kmzyc.b2b.service.member.ScoreInfoService;
import com.kmzyc.b2b.service.member.UserInfoService;
import com.kmzyc.b2b.vo.EraInfo;
import com.kmzyc.b2b.vo.ReturnResult;
import com.kmzyc.b2b.vo.ScoreView;
import com.kmzyc.b2b.vo.SecurityCentreInfo;
import com.kmzyc.framework.constants.Constants;
import com.kmzyc.framework.constants.InterfaceResultCode;
import com.kmzyc.framework.exception.ServiceException;
import com.kmzyc.zkconfig.ConfigurationUtil;

import redis.clients.jedis.JedisCluster;

/**
 * Description:会员中心首页访问入口 User: lishiming Date: 13-10-15 下午3:45 Since: 1.0
 */
@SuppressWarnings({"serial", "unchecked"})
@Controller
@Scope("prototype")
public class HomeAction extends BaseAction {

    // private static Logger logger = Logger.getLogger(HomeAction.class);
    private static Logger logger = LoggerFactory.getLogger(HomeAction.class);
    @Resource(name = "securityCentreServiceImpl")
    private SecurityCentreService securityCentreService;

    @Resource(name = "accountServiceImpl")
    private AccountService accountService;

    @Resource(name = "userInfoServiceImpl")
    private UserInfoService userInfoService;

    @Resource(name = "myCouponServiceImpl")
    private MyCouponService myCouponService;

    @Resource(name = "myOrderServiceImpl")
    private MyOrderService myOrderService;

    @Resource(name = "prodAppraiseServiceImpl")
    private ProdAppraiseService prodAppraiseService;

    @Resource(name = "myFavoriteServiceImpl")
    private MyFavoriteService myFavoriteService;

    @Resource(name = "scoreInfoServiceImpl")
    private ScoreInfoService scoreInfoService;

    @Resource(name = "productPriceService")
    private ProductPriceService productPriceService;

    @Resource(name = "jedisCluster")
 private JedisCluster jedisCluster;

    @Resource(name = "eraInfoServiceImpl")
    private EraInfoService eraInfoService;
    private ReturnResult returnResult;

    private User user;

    private Long favoriteId;

    // wap项目的第几页
    private String pageIndex;

    // SHOW 显示什么
    private String show;

    /**
     * 加载会员中心主页相关信息
     * 
     * @return
     */

    public String loadHomeInfo() {
        HttpSession session = getSession();
        Long userId = (Long) session.getAttribute(Constants.SESSION_USER_ID);
        logger.info("加载会员中心主页，参数：userID：" + userId);
        long startTime = System.currentTimeMillis();
        user = securityCentreService.getUserByLoginId(userId);
        Map<String, Object> mapData = new HashMap<String, Object>();
        try {
            Map<String, Object> conditon = new HashMap<String, Object>();
            conditon.put("loginId", userId);
            User user = accountService.findHomeLoadById(conditon);
            // 检测是否为时代会员
            EraInfo eraInfo = user.getEarInfo();
            if (eraInfo != null) { // 时代会员
                eraInfo = eraInfoService.queryEraInfoByLoginId(user.getLoginId());
                mapData.put("isSD", "yes");
                if (StringUtils.isEmpty(eraInfo.getLoginAccount())) {
                    mapData.put("isBind", "no");
                }
                mapData.put("nickName", user.getNickName());
                mapData.put("eraInfo", eraInfo);
                // 获取可用积分
                mapData.put("availableIntegral", myOrderService.sumPvByCustomerId(userId));
            } else {
                // 获取可用积分
                mapData.put("availableIntegral", user.getAvailableIntegral());
            }
            // 个人积分
            ScoreView scoreView = new ScoreView();
            scoreView.setCurLevel(user.getLevelName()); // 会员等级
            scoreView.setTotalConsume(user.getAvailableIntegral() != null ? user
                    .getAvailableIntegral().doubleValue() : Double.valueOf("0.0"));// 当前会员总积分
            scoreView.setLastYearConsume(user.getLastYearAmount() != null ? user
                    .getLastYearAmount().doubleValue() : Double.valueOf("0.0"));// 上年度消费总积分
            mapData.put("scoreView", scoreView);

            mapData.put("cardNum", user.getCardNum());
            // 安全中心
            SecurityCentreInfo securityCentreInfo = new SecurityCentreInfo();
            securityCentreInfo.setEmailValidate(!"".equals(user.getEmail())
                    && null != user.getEmail() ? true : false);
            securityCentreInfo.setPayPasswordInvocation(user.getPaymentPwd() != null
                    && !user.getPaymentPwd().equals("") ? true : false);
            securityCentreInfo.setMobileValidate(user.getMobile() != null
                    && !user.getMobile().equals("") ? true : false);
            mapData.put("securityCentreInfo", securityCentreInfo);
            // 获取账户余额信息
            mapData.put("amountAvailable", user.getAmountAvlibal());

            // 获取头像信息
            if (!StringUtils.isBlank(user.getHeadSculpture())) {
                mapData.put("userImgUrl", user.getHeadSculpture());
            }
            // 获取收藏夹中现货商品数
            mapData.put("instockSkuAmount", user.getInstockSkuAmount());
            List<Favorite> recourseList = myFavoriteService.findAllFavoriteGoods(user.getLoginId());
            mapData.put("myFavoriteList", recourseList);
            // 获取优惠券总数
            long couponAmount =
                    myCouponService.countCouponByUserId(user.getLoginId(),
                            Constants.NOTUSE_COUPONSTATUS);
            mapData.put("couponAmount", couponAmount);
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("orderChannel", ConfigurationUtil.getString("CHANNEL"));
            params.put("loginId", user.getLoginId());
            Map<String, BigDecimal> result = myOrderService.queryMyOrderStatusCount(params);
            mapData.put("notPayOrderAmount", (null == result || null == result
                    .get("NOTPAYORDERAMOUNT")) ? 0 : result.get("NOTPAYORDERAMOUNT"));// 获取待支付订单总数
            mapData.put("notAssessOrderAmount", (null == result || null == result
                    .get("NOTASSESSORDERAMOUNT")) ? 0 : result.get("NOTASSESSORDERAMOUNT")); // 获取待评价订单总数
            mapData.put(
                    "doneOrderAmount",
                    (null == result || null == result.get("DISTRIBUTION")) ? 0 : result
                            .get("DISTRIBUTION"));// 配送中


            try {
                // 获取已发表的商品评论数（审核通过的） 从缓存中取得
                if (jedisCluster.hget("myHomeInfo_" + userId, "appraiseAmout_" + userId) != null) { // 取得发表的商品评论数
                                                                                             // appraiseAmout
                    List<String> appraiseAmout_ =
                            jedisCluster.hmget("myHomeInfo_" + userId, "appraiseAmout_" + userId);
                    mapData.put("prodAppraiseAmount", appraiseAmout_.get(0));

                    List<String> replyAmount_ =
                            jedisCluster.hmget("myHomeInfo_" + userId, "replyAmount_" + userId);
                    mapData.put("repliedProdAppraiseAmount", replyAmount_.get(0));

                    List<String> consultAmount_ =
                            jedisCluster.hmget("myHomeInfo_" + userId, "consultAmount_" + userId);
                    mapData.put("consultAmount", consultAmount_.get(0));
                } else { // 查表
                    List<Integer> statusList = new ArrayList<Integer>();
                    statusList.add(Constants.CHECK_STATUS_PASS);
                    Map<String, String> map = new HashMap<String, String>();
                    // 评论和咨询
                    AppraiseReply prodAppriseConsult =
                            prodAppraiseService.countProdAppraiseByUserId(user.getLoginId(),
                                    statusList);
                    // 通过审核的评论
                    map.put("appraiseAmout_" + userId, prodAppriseConsult.getProdAppraiseCount());
                    mapData.put("prodAppraiseAmount", prodAppriseConsult.getProdAppraiseCount());
                    // 以回复的评论
                    map.put("replyAmount_" + userId, prodAppriseConsult.getProdReplyCount());
                    mapData.put("repliedProdAppraiseAmount", prodAppriseConsult.getProdReplyCount());
                    // 通过审核的咨询
                    map.put("consultAmount_" + userId, prodAppriseConsult.getProdReplyCount());
                    mapData.put("consultAmount", prodAppriseConsult.getProdReplyCount());
                    jedisCluster.hmset("myHomeInfo_" + userId, map);
                    jedisCluster.expire("myHomeInfo_" + userId, 3600);//一个小时后失效
                }
            } catch (Exception e) {
                logger.info("从缓存中取商品评论信息、咨询信息出错" + e.getMessage(), e);
            }
            logger.info("用户:" + userId + "加载我的时代完成,耗时：" + (System.currentTimeMillis() - startTime)
                    / 1000 + "秒");
            returnResult = new ReturnResult(InterfaceResultCode.SUCCESS, "加载个人主页信息成功", mapData);
        } catch (ServiceException se) {
            logger.error(se.getMessage(), se);
            returnResult = new ReturnResult(InterfaceResultCode.FAILED, "加载个人主页信息失败", null);
        } catch (SQLException sqe) {
            logger.error(sqe.getMessage(), sqe);
            returnResult = new ReturnResult(InterfaceResultCode.FAILED, "加载个人主页信息失败", null);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            returnResult = new ReturnResult(InterfaceResultCode.FAILED, "加载个人主页信息失败", null);
        }
        return SUCCESS;
    }


    /**
     * wap加载会员中心主页相关信息
     * 
     * @return
     */
    public String waploadHomeInfo() {
        HttpSession session = getSession();
        Long userId = (Long) session.getAttribute(Constants.SESSION_USER_ID);
        logger.info("加载会员中心主页，参数：userID：" + userId);
        user = securityCentreService.getUserByLoginId(userId);
        Map<String, Object> mapData = new HashMap<String, Object>();
        Map<String, Object> condition = new HashMap<String, Object>();
        try {
            Map<String, Object> conditon = new HashMap<String, Object>();
            conditon.put("loginId", userId);
            // 个人积分
            ScoreView scoreView = scoreInfoService.findDetailMyScoreByLoginId(userId.intValue());
            mapData.put("scoreView", scoreView);
            // 获取账户余额信息
            AccountInfo accountInfo = accountService.findAccountByUserId(user.getLoginId());
            BigDecimal amountAvailable = new BigDecimal(accountInfo.getAmountAvlibal());
            mapData.put("amountAvailable", amountAvailable);
            // 获取可用积分与会员头像
            PersonalityInfo personality =
                    userInfoService.queryPersonalityByUserId(user.getLoginId());
            Long availableIntegral = personality.getAvailableIntegral();
            mapData.put("availableIntegral", availableIntegral);
            if (!StringUtils.isBlank(personality.getHeadSculpture())) {
                mapData.put("userImgUrl", personality.getHeadSculpture());
            }
            // 获取优惠券总数
            long couponAmount =
                    myCouponService.countCouponByUserId(user.getLoginId(),
                            Constants.NOTUSE_COUPONSTATUS);
            mapData.put("couponAmount", couponAmount);
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("orderChannel", ConfigurationUtil.getString("CHANNEL"));
            params.put("loginId", user.getLoginId());
            Map<String, BigDecimal> result = myOrderService.queryMyOrderStatusCount(params);
            mapData.put("notPayOrderAmount", (null == result || null == result
                    .get("NOTPAYORDERAMOUNT")) ? 0 : result.get("NOTPAYORDERAMOUNT"));// 获取待支付订单总数
            mapData.put(
                    "payOrderAmount",
                    (null == result || null == result.get("PAYORDERAMOUNT")) ? 0 : result
                            .get("PAYORDERAMOUNT"));// 获取已支付订单总数
            // 获取收藏列表
            Pagination page = this.getPagination(5, 10);
            condition.put("userId", userId);
            condition.put("channel", ConfigurationUtil.getString("CHANNEL"));
            page.setObjCondition(condition);
            this.pagintion = myFavoriteService.findFavoriteProductByPage(page);
            productPriceService.getPriceBatch(this.pagintion.getRecordList());
            List<Favorite> favoriteList = this.pagintion.getRecordList();
            if (favoriteList != null && favoriteList.size() > 0) { // 获取类目属性
                for (int i = 0; i < favoriteList.size(); i++) {
                    String categoryAttrValueStr = "";
                    List<Map<String, String>> categoryAttrValueList =
                            favoriteList.get(i).getCategoryAttrValueList();
                    if (categoryAttrValueList != null && categoryAttrValueList.size() > 0) {
                        for (int j = 0; j < categoryAttrValueList.size(); j++) {
                            categoryAttrValueStr +=
                                    categoryAttrValueList.get(j).get("categoryAttrValue") + "  ";
                        }
//                        categoryAttrValueStr.subSequence(0,
//                                categoryAttrValueStr.length() - 1);
                    }
                    favoriteList.get(i).setCategoryAttrValueStr(categoryAttrValueStr);
                }
            }
            List<Favorite> recourseList = pagintion.getRecordList();
            mapData.put("myFavoriteList", recourseList);
            mapData.put("favorTotal", pagintion.getTotalpage());
            returnResult = new ReturnResult(InterfaceResultCode.SUCCESS, "加载个人主页信息成功", mapData);
        } catch (ServiceException se) {
            logger.error(se.getMessage(), se);
            returnResult = new ReturnResult(InterfaceResultCode.FAILED, "加载个人主页信息失败", null);
        } catch (SQLException sqe) {
            logger.error(sqe.getMessage(), sqe);
            returnResult = new ReturnResult(InterfaceResultCode.FAILED, "加载个人主页信息失败", null);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            returnResult = new ReturnResult(InterfaceResultCode.FAILED, "加载个人主页信息失败", null);
        }
        return SUCCESS;
    }

    /**
     * 
     * 
     */
    public String goWapMyFavorite() {

        return SUCCESS;
    }

    /**
     * wap加载我的收藏
     */
    public String wapQueryMyFavorite() {
        HttpSession session = getSession();
        HttpServletRequest request = ServletActionContext.getRequest();
        Long userId = (Long) session.getAttribute(Constants.SESSION_USER_ID);
        Map<String, Object> mapData = new HashMap<String, Object>();
        Map<String, Object> condition = new HashMap<String, Object>();
        // 获取收藏列表
        Pagination page = this.getPagination(5, 10);
        condition.put("userId", userId);
        condition.put("channel", ConfigurationUtil.getString("CHANNEL"));
        pageIndex = request.getParameter("pageIndex");
        if (pageIndex != null) {
            if (!pageIndex.equals("1")) {
                page.setStartindex((Integer.parseInt(pageIndex.toString()) - 1) * 10 + 1);
                page.setEndindex(Integer.parseInt(pageIndex.toString()) * 10);
            }
        }
        page.setObjCondition(condition);
        try {
            this.pagintion = myFavoriteService.findFavoriteProductByPage(page);
            productPriceService.getPriceBatch(this.pagintion.getRecordList());
            // 将商品的类目属性值List转换为String,中间通过空格相连
            List<Favorite> favoriteList = this.pagintion.getRecordList();
            if (favoriteList != null && favoriteList.size() > 0) {
                for (int i = 0; i < favoriteList.size(); i++) {
                    String categoryAttrValueStr = "";
                    List<Map<String, String>> categoryAttrValueList =
                            favoriteList.get(i).getCategoryAttrValueList();
                    if (categoryAttrValueList != null && categoryAttrValueList.size() > 0) {
                        for (int j = 0; j < categoryAttrValueList.size(); j++) {
                            categoryAttrValueStr +=
                                    categoryAttrValueList.get(j).get("categoryAttrValue") + "  ";
                        }
//                        categoryAttrValueStr.subSequence(0, categoryAttrValueStr.length() - 1);
                    }
                    favoriteList.get(i).setCategoryAttrValueStr(categoryAttrValueStr);
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            returnResult = new ReturnResult(InterfaceResultCode.FAILED, "加载我的收藏失败", null);
        }
        List<Favorite> recourseList = pagintion.getRecordList();
        mapData.put("myFavoriteList", recourseList);
        returnResult = new ReturnResult(InterfaceResultCode.SUCCESS, "加载我的收藏成功", mapData);
        return SUCCESS;
    }

    /**
     * wap删除收藏记录
     * 
     * @return
     */
    public String wapdeleteFavorite() {
        HttpServletRequest request = ServletActionContext.getRequest();
        Long userId = (Long) request.getSession().getAttribute(Constants.SESSION_USER_ID);
        logger.info("删除收藏记录，参数：favoriteId：" + this.favoriteId + "，userID：" + userId);
        try {
            logger.info("删除收藏记录，收藏Id为【" + this.favoriteId + "】");
            myFavoriteService.deleteFavorite(this.favoriteId);
            returnResult = new ReturnResult(InterfaceResultCode.SUCCESS, "删除我的收藏成功", "1");
        } catch (Exception e) {
            logger.error("删除收藏记录出错：" + e.getMessage(), e);
            returnResult = new ReturnResult(InterfaceResultCode.FAILED, "删除我的收藏失败", "2");
            return ERROR;
        }
        return SUCCESS;
    }

    public ReturnResult getReturnResult() {
        return returnResult;
    }

    public void setReturnResult(ReturnResult returnResult) {
        this.returnResult = returnResult;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getFavoriteId() {
        return favoriteId;
    }

    public void setFavoriteId(Long favoriteId) {
        this.favoriteId = favoriteId;
    }

    public String getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(String pageIndex) {
        this.pageIndex = pageIndex;
    }

    public String getShow() {
        return show;
    }

    public void setShow(String show) {
        this.show = show;
    }

}
