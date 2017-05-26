package com.kmzyc.promotion.app.service.impl;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.google.common.collect.Lists;
import com.kmzyc.commons.page.Page;
import com.kmzyc.promotion.app.dao.CouponGrantFlowDAO;
import com.kmzyc.promotion.app.enums.CouponGrantFlowStatus;
import com.kmzyc.promotion.app.enums.CouponStatus;
import com.kmzyc.promotion.app.service.CouponGrantFlowService;
import com.kmzyc.promotion.app.vobject.Coupon;
import com.kmzyc.promotion.app.vobject.CouponGrant;
import com.kmzyc.promotion.app.vobject.CouponGrantFlow;
import com.kmzyc.user.remote.service.CustomerRemoteService;
import com.pltfm.app.dataobject.UserInfoDO;

@Repository("CouponGrantFlowService")
public class CouponGrantFlowServiceImpl implements CouponGrantFlowService {

    // 日志记录
    private static final Logger logger = LoggerFactory.getLogger(CouponGrantFlowServiceImpl.class);

    @Resource
    private CustomerRemoteService customerRemoteService;
    /**
     * 优惠券流水DAO
     */
    @Resource
    private CouponGrantFlowDAO couponGrantFlowDAO;

    @Override
    public void insert(CouponGrantFlow couponGrantFlow) throws SQLException {
        couponGrantFlowDAO.insert(couponGrantFlow);

    }

    @Override
    public Page selectList(CouponGrantFlow couponGrantFlow, Page page) throws SQLException {
        return couponGrantFlowDAO.selectPageByVo(page, couponGrantFlow);

    }

    /**
     * 添加优惠券流水信息
     */

    @Override
    public boolean insertFlow(CouponGrant couponGrant, Coupon coupon) {
        try {
            // CustomerRemoteService costomerService =
            // (CustomerRemoteService) RemoteTool.getRemote("03", "customerRemoteService");
            UserInfoDO userCondition = new UserInfoDO();
            List<UserInfoDO> customList = Lists.newArrayList();
            if (StringUtils.isNotBlank(couponGrant.getCustomId() + " ")) { // 如果不是空
                userCondition.setLoginId(couponGrant.getCustomId());
                customList = customerRemoteService.selectByUserInfoDOByObj(userCondition);
            }

            CouponGrantFlow couponGrantFlow = new CouponGrantFlow();
            couponGrantFlow.setCouponGrantFlowType(
                    Long.valueOf(CouponGrantFlowStatus.HAVEPASSDATE_COUPONFLOWSTATUS.getType()));
            couponGrantFlow.setCouponGrantId(Long.valueOf(couponGrant.getCouponGrantId()));
            couponGrantFlow.setCreateDate(new Date());
            couponGrantFlow.setCouponName(coupon.getCouponName());
            couponGrantFlow.setCouponId(coupon.getCouponId());
            couponGrantFlow.setOperatingPersonName("系统定时任务");
            if (couponGrant.getCustomId() != null) {
                couponGrantFlow.setCustomId(Long.valueOf(couponGrant.getCustomId()));
            }
            if (customList.size() > 0) {
                couponGrantFlow.setCustomer(customList.get(0).getLoginAccount());
            }
            couponGrantFlow.setRemark("定时任务 ， 优惠券操作类型为:"
                    + CouponGrantFlowStatus.HAVEPASSDATE_COUPONFLOWSTATUS.getTitle() + "，   "
                    + "优惠券编号：" + coupon.getCouponId() + "，   优惠券发放id"
                    + couponGrant.getCouponGrantId());
            insert(couponGrantFlow);
            logger.error("优惠券发放状态已改变为:" + CouponStatus.HAVEPASSDATE_COUPONSTATUS.getType() + "，"
                    + "规则id" + coupon.getCouponId() + "发放id" + couponGrant.getCouponGrantId());
        } catch (Exception e) {
            logger.error("添加优惠券流水信息异常：", e);
            return false;
        }
        return true;
    }


}
