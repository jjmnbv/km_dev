package com.kmzyc.b2b.action.member;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.km.framework.action.BaseAction;
import com.km.framework.page.Pagination;
import com.kmzyc.b2b.model.PersonalityInfo;
import com.kmzyc.b2b.service.EraInfoService;
import com.kmzyc.b2b.service.member.ScoreInfoService;
import com.kmzyc.b2b.service.member.UserInfoService;
import com.kmzyc.b2b.vo.EraInfo;
import com.kmzyc.b2b.vo.ScoreView;
import com.kmzyc.framework.constants.Constants;

@Controller("myPointsAction")
@Scope("prototype")
public class MyPointsAction extends BaseAction {

    /**
     * 
     */
    private static final long serialVersionUID = -6611845326996643421L;

    // private static Logger logger = Logger.getLogger(MyPointsAction.class);

    private static Logger logger = LoggerFactory.getLogger(MyPointsAction.class);

    @Resource(name = "scoreInfoServiceImpl")
    private ScoreInfoService scoreInfoService;

    @Resource(name = "eraInfoServiceImpl")
    private EraInfoService eraInfoService;
    @Resource(name = "userInfoServiceImpl")
    private UserInfoService userInfoService;

    private String rtnMsg;

    private String couponValue;

    private Integer result;

    private ScoreView scoreView = new ScoreView();

    private EraInfo eraInfo;

    private Long yearSpendMin;

    private PersonalityInfo personalityInfo;

    @SuppressWarnings("unchecked")
    public String queryPointsRecordList() {
        Long userId = (Long) super.getSession().getAttribute(Constants.SESSION_USER_ID);
        Pagination page = this.getPagination(5, 10);

        // 页面传入的查询条件
        Map<String, String> objContion = (Map<String, String>) page.getObjCondition();
        // sql查询条件对象
        Map<String, Object> conditon = new HashMap<String, Object>();

        // 解析并组装查询条件
        conditon.put("loginId", userId);
        try {
            if (objContion != null) {
                if (StringUtils.isNotEmpty(objContion.get("dateBegin"))) {
                    conditon.put("dateBegin", objContion.get("dateBegin"));
                }
                if (StringUtils.isNotEmpty(objContion.get("dateEnd"))) {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    Date date = simpleDateFormat.parse(objContion.get("dateEnd"));
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    conditon.put("dateEnd",
                            sdf.format(DateUtils.addSeconds(DateUtils.addDays(date, 1), -1)));
                }
            }
            // 设置查询条件
            page.setObjCondition(conditon);
            scoreView = scoreInfoService.findDetailMyScoreByLoginId(userId.intValue());
            this.pagintion = scoreInfoService.findPointsRecordByPage(page);
            // UserLevel nextLevel = userLevelService.findByLevelId(scoreView.getNextLevelId());
            // yearSpendMin = nextLevel.getYearMin();
            personalityInfo = userInfoService.queryPersonalityByUserId(userId);
            // String couponTypes =
            // ConfigurationUtil.getString(userLevelService.findCodeByLoginId(userId));
            // super.getRequest().setAttribute("couponTypes", couponTypes);
            eraInfo = eraInfoService.selectEranInfoById(conditon);
            if (eraInfo != null) {
                return "era";
            }
        } catch (SQLException e) {
            logger.error("积分记录查询出错：" + e.getMessage(), e);
        } catch (ParseException e) {
            logger.error("积分记录查询出错：" + e.getMessage(), e);
        } catch (Exception e) {
            logger.error("积分记录查询出错：" + e.getMessage(), e);
        }
        return SUCCESS;
    }

    public String queryPointsRuleList() {
        try {
            Long userId = (Long) super.getSession().getAttribute(Constants.SESSION_USER_ID);
            scoreView = scoreInfoService.findDetailMyScoreByLoginId(userId.intValue());
            // super.getRequest().setAttribute("userLevelList",
            // userLevelService.findAllUserLevel());
            // super.getRequest().setAttribute("scoreRuleList", scoreRuleService.findAllRule());
            super.getRequest().setAttribute("scoreRuleList", null);
        } catch (Exception e) {
            logger.error("积分规则查询出错：" + e.getMessage(), e);
        }
        return SUCCESS;
    }

    @SuppressWarnings("unchecked")
    public String queryPointsExchangeCouponList() {
        Long userId = (Long) super.getSession().getAttribute(Constants.SESSION_USER_ID);
        Pagination page = this.getPagination(5, 10);

        // 页面传入的查询条件
        Map<String, String> objContion = (Map<String, String>) page.getObjCondition();
        // sql查询条件对象
        Map<String, Object> conditon = new HashMap<String, Object>();

        // 解析并组装查询条件
        conditon.put("loginId", userId);
        conditon.put("consumeType", 1);

        try {
            if (objContion != null) {
                if (StringUtils.isNotEmpty(objContion.get("dateBegin"))) {
                    conditon.put("dateBegin", objContion.get("dateBegin"));
                }
                if (StringUtils.isNotEmpty(objContion.get("dateEnd"))) {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    Date date = simpleDateFormat.parse(objContion.get("dateEnd"));
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    conditon.put("dateEnd",
                            sdf.format(DateUtils.addSeconds(DateUtils.addDays(date, 1), -1)));
                }
            }
            // 设置查询条件
            page.setObjCondition(conditon);
            scoreView = scoreInfoService.findDetailMyScoreByLoginId(userId.intValue());
            // String couponTypes =
            // ConfigurationUtil.getString(userLevelService.findCodeByLoginId(userId));
            // super.getRequest().setAttribute("couponTypes", couponTypes);
            this.pagintion = scoreInfoService.findPointsRecordByPage(page);
        } catch (Exception e) {
            logger.error("积分兑换优惠券查询出错：" + e.getMessage(), e);
        }
        return SUCCESS;
    }

    /*
     * public String savePointsExchangeCoupon() { Long userId = (Long)
     * super.getSession().getAttribute(Constants.SESSION_USER_ID); try {
     * 
     * if (couponValue == null) { return null; } int money =
     * Integer.valueOf(ConfigurationUtil.getString(couponValue)); result =
     * scoreInfoService.exChangeCoupon(userId, Long.valueOf(couponValue), (double) (money * 100),
     * money); getResponse().getWriter().print(result); if (result == -1 || result == 0) { rtnMsg =
     * "系统发生错误，兑换失败！"; } if (result == -2) { rtnMsg = "不存在该优惠劵或者该优惠劵已经过期！"; } if (result == 1) {
     * rtnMsg = "兑换成功！"; } if (result == 2) { rtnMsg = "对不起，您的积分不足，兑换失败！"; } } catch
     * (ServiceException e) { rtnMsg = "系统发生错误，兑换失败！"; logger.error("兑换优惠券出错：" + e.getMessage(), e);
     * } catch (Exception e) { rtnMsg = "系统发生错误，兑换失败！"; logger.error("兑换优惠券出错：" + e.getMessage(),
     * e); } return null; }
     */

    public String getCouponValue() {
        return couponValue;
    }

    public void setCouponValue(String couponValue) {
        this.couponValue = couponValue;
    }

    public ScoreView getScoreView() {
        return scoreView;
    }

    public void setScoreView(ScoreView scoreView) {
        this.scoreView = scoreView;
    }

    public String getRtnMsg() {
        return rtnMsg;
    }

    public void setRtnMsg(String rtnMsg) {
        this.rtnMsg = rtnMsg;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public EraInfo getEraInfo() {
        return eraInfo;
    }

    public void setEraInfo(EraInfo eraInfo) {
        this.eraInfo = eraInfo;
    }

    public Long getYearSpendMin() {
        return yearSpendMin;
    }

    public void setYearSpendMin(Long yearSpendMin) {
        this.yearSpendMin = yearSpendMin;
    }

    public PersonalityInfo getPersonalityInfo() {
        return personalityInfo;
    }

    public void setPersonalityInfo(PersonalityInfo personalityInfo) {
        this.personalityInfo = personalityInfo;
    }

}
