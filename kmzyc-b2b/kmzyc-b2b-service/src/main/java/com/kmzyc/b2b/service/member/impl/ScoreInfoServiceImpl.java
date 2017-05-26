package com.kmzyc.b2b.service.member.impl;

import java.sql.SQLException;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.km.framework.page.Pagination;
import com.kmzyc.b2b.dao.member.ScoreInfoDao;
import com.kmzyc.b2b.model.PersonalityInfo;
import com.kmzyc.b2b.model.User;
import com.kmzyc.b2b.service.ResetPwdService;
import com.kmzyc.b2b.service.member.ScoreInfoService;
import com.kmzyc.b2b.service.member.UserInfoService;
import com.kmzyc.b2b.vo.ScoreView;

@Service
public class ScoreInfoServiceImpl implements ScoreInfoService {

    private static Logger logger = LoggerFactory.getLogger(ScoreInfoServiceImpl.class);

    @Resource(name = "scoreInfoDaoImpl")
    private ScoreInfoDao scoreInfoDao;

    @Resource(name = "userInfoServiceImpl")
    private UserInfoService userInfoService;

    @Resource(name = "resetPwdServiceImpl")
    private ResetPwdService resetPwdService;

    @Override
    public Pagination findPointsRecordByPage(Pagination page) throws SQLException {
        @SuppressWarnings("unchecked")
        Map<String, Object> conditon = (Map<String, Object>) page.getObjCondition();
        logger.debug("开始查询用户的积分列表,loginId:" + conditon.get("loginId") + ",dateBegin:"
                + conditon.get("dataBegin") + ",dateEnd:" + conditon.get("dateEnd"));
        long startTime = System.currentTimeMillis();
        // 使用客户数据源

        page = scoreInfoDao.findByPage("ScoreInfo.searchPage", "ScoreInfo.searchCountPage", page);

        logger.debug("查询用户的订单列表结束，耗时" + (System.currentTimeMillis() - startTime) / 1000 + "秒");
        return page;
    }


/*
    @Override
    public int exChangeCoupon(Long loginId, Long couponId, Double scoreNumber, Integer couponValue)
            throws ServiceException {
        int result = -1;
        try {
            // CouponRemoteService couponRemoteService =
            // (CouponRemoteService) RemoteTool.getRemote(Constants.PROMOTION_SYSTEM_CODE,
            // "couponService");
            UserInfoDO udo = new UserInfoDO();
            udo.setLoginId(loginId.intValue());
            List<UserInfoDO> list = new ArrayList<UserInfoDO>();
            list.add(udo);

            com.pltfm.app.vobject.ScoreInfo scoreInfo = new com.pltfm.app.vobject.ScoreInfo();
            // 当前登录ID
            scoreInfo.setN_loginId(loginId.intValue());
            // 兑换优惠劵积分值
            scoreInfo.setScoreNumber(scoreNumber);
            // 积分消费描述
            scoreInfo.setDiscribe(scoreNumber + "积分兑换" + couponValue + "元优惠劵");

            // 积分扣减
            scoreInfo.setScoreType(0);
            // 兑换优惠劵面值
            scoreInfo.setCouponValue(couponValue);

            // 积分消费类型1---优惠券0其它
            scoreInfo.setConsumeType(1);
            // 积分兑换优惠劵状态;1---成功0--失败
            scoreInfo.setIsStauts(1);
            // 积分记录创建日期时间
            scoreInfo.setD_createDate(new Date());
            result = userGrowingService.changeSorcToCoupon(scoreInfo);
            logger.info("扣除积分结果---->>" + result);
            if (result == 1) {
                // 调用产品优惠券接口，兑换成功会在优惠劵信息中添加优惠劵
                int couponReslut =
                        couponRemoteService.changeCustomGrantToGive(udo, couponId, loginId,
                                Long.valueOf(41), loginId.toString(), null);
                logger.info("兑换优惠劵结果--->>" + couponReslut);
                *//*
                 * if(couponReslut == 1){ EmailCouponInfoVO emailCouponInfoVO = new
                 * EmailCouponInfoVO(); emailCouponInfoVO.setCoupon(coupon); }
                 *//*
            }
        } catch (Exception e) {
            result = -1;
            logger.error(e.getMessage(), e);
            throw new ServiceException(500, "系统发生异常，兑换失败！");
        }
        return result;
    }*/

    @Override
    public ScoreView findDetailMyScoreByLoginId(Integer n_LoginId) throws Exception {
        ScoreView mySorce = new ScoreView();
        if (n_LoginId != null) {
            try {
                mySorce.setLoginId(n_LoginId);
                Integer n_AvailableIntegral = 0;
                // 积分值
                PersonalityInfo personalityInfo =
                        userInfoService.queryPersonalityByUserId(Long.valueOf(n_LoginId));
                n_AvailableIntegral = personalityInfo.getAvailableIntegral().intValue();
                // 可用积分值
                mySorce.setCurScore(n_AvailableIntegral);
                // 等级名称
                User u = new User();
                u.setLoginId(Long.valueOf(n_LoginId));
                User loginInfo = resetPwdService.getUserByLoginId(Long.valueOf(n_LoginId));
//                UserLevel userLevel =
//                        userLevelService.findByLevelId(Long.valueOf(loginInfo.getLevelId()));
                mySorce.setCardNum(loginInfo.getCardNum());
                mySorce.setCurLevel("普通会员");
                mySorce.setCurLevelCode("普通会员");
                // 等级下一级
                double max = 1;

//                UserLevel userLevelDO = new UserLevel();
//                userLevelDO.setExpend(max);
//                userLevelDO.setCustomerTypeId(Long.valueOf(1));
                //UserLevel userLevel2 = userLevelService.findBySomeCondition(userLevelDO);
                mySorce.setNextLevel("普通会员");
                mySorce.setNextLevelId(1l);


                Object obj = scoreInfoDao.findById("ScoreInfo.findSumListByQueryList", n_LoginId);
                double sum = 0.00;
                if (obj != null) {
                    sum = (Double) obj;
                }
                // 消费金额
                mySorce.setTotalConsume(sum);
                // 需要再消费金额升级
                Double afterAmount = (double) (max - sum);
                mySorce.setNextConsume(afterAmount);

                Object lastConsum =
                        scoreInfoDao
                                .findById("ScoreInfo.findLastYearSumListByQueryList", n_LoginId);
                Double lastYear = 0.00;
                if (lastConsum != null) {
                    lastYear = (Double) lastConsum;
                }
                mySorce.setLastYearConsume(lastYear);
            } catch (Exception e) {
                logger.error("查询我的积分异常，登陆账号:" + n_LoginId, e);
            }
        }
        return mySorce;
    }

}
