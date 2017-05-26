package com.pltfm.crowdsourcing.process;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kmzyc.promotion.remote.service.CouponRemoteService;
import com.kmzyc.user.remote.service.CustomerRemoteService;
import com.kmzyc.user.remote.service.UserGrowingService;

@Component
public class RegistProcess {
  public final static Executor exeutor = Executors.newCachedThreadPool();
  private static final Logger logger = LoggerFactory.getLogger(RegistProcess.class);

  @Resource(name = "userGrowingService")
  UserGrowingService userGrowingService;


  @Resource(name = "customerRemoteService")
  CustomerRemoteService customerRemoteService;

  @Autowired
  private CouponRemoteService couponRemoteService;
  
  /**
   * 机构注册用户发优惠卷和积分
   * 
   * @return
   * @throws Exception
   */
 /*删除机构业务  public void registerIntegral(final User user, final String couponID) {
    exeutor.execute(new Runnable() {
      @Override
      public void run() {
        Long loginId = user.getLoginId();
        try {
          // 注册积分
          Integer re = userGrowingService.updateUserScoreInfo("RU0001", 1, user.getLoginAccount(),
              new HashMap<String, String>());
          logger.info("机构用户:loginId:{}注册送积分返回状态为:{}", loginId, re);

        } catch (Exception e) {
          logger.error("注册送积分失败用户id：{}", user.getLoginId(), e);
        }

        try {

          // 注册优惠券
          UserInfoDO ufd = new UserInfoDO();
          ufd.setLoginId(loginId.intValue());
          ufd.setLoginAccount(user.getLoginAccount());
          // 此处user类定义问题
          boolean resultFlag = couponRemoteService.grantCouponForCommonRegist(ufd);
          if (resultFlag) {
            logger.info("调用注册发放优惠券接口为:ID:{},用户名:{}发放优惠券成功!", loginId, user.getLoginAccount());
          } else {
            logger.info("调用注册发放优惠券接口为:ID:{},用户名:{}发放优惠券失败!", loginId, user.getLoginAccount());
          }
        } catch (Exception e) {
          logger.error("注册发放优惠失败用户id：{}", user.getLoginId(), e);
        }

        try {
          logger.info("机构用户注册发送随机优惠卷：优惠卷ID{}", couponID);
          // 发送随机优惠券
          if (StringUtils.isEmpty(couponID)) {
            logger.info("机构用户注册不发送随机优惠卷，loginid：{},优惠卷id为空", loginId.intValue());
          } else {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("customId", loginId);
            map.put("couponIds", couponID);
            map.put("couponGrantDetailType",
                CouponGrantDetailType.MANUAL_COUPONGRANTDETAILTYPE.getType());
            map.put("couponGrantFlowStatus", CouponGrantFlowStatus.GIVE_COUPONFLOWSTATUS.getType());
            couponRemoteService.generalGivenCoupon(map);
            logger.info("机构用户注册发送随机优惠卷：优惠券成功，loginid：{},优惠卷id", loginId.intValue(), couponID);
          }

        } catch (Exception e) {
          logger.error("机构用户注册发送随机优惠卷：优惠券异常，loginid：{},优惠卷id", loginId.intValue(), couponID, e);
        }

        mobileIntegral(loginId, user.getMobile());
      }
    });
  }*/

  /**
   * 手机首次验证送积分
   * 
   * @return
   * @throws Exception
   */
 /*删除  private void mobileIntegral(final Long loginId, final String mobile) {

    try {
      // 机构注册手机首次验证送积分
      LoginInfo loginInfo = new LoginInfo();
      loginInfo.setN_LoginId(Integer.valueOf(loginId.toString()));
      loginInfo.setMobile(mobile);
      int re = customerRemoteService.userMobileVerification(loginInfo);
      logger.info("机构用户:loginId:{}首次验证送积分返回状态为:{}", loginId, re);
    } catch (Exception e) {
      logger.error("手机首次验证送积分失败用户id：{}", loginId, e);
    }
  }
*/

}
