package com.kmzyc.promotion.app.service.impl;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.kmzyc.commons.page.Page;
import com.kmzyc.promotion.app.dao.CouponDAO;
import com.kmzyc.promotion.app.dao.CouponGrantDAO;
import com.kmzyc.promotion.app.dao.CouponGrantFlowDAO;
import com.kmzyc.promotion.app.dao.CouponProductDAO;
import com.kmzyc.promotion.app.enums.CouponStatus;
import com.kmzyc.promotion.app.maps.CouponGrantDetailTypeMap;
import com.kmzyc.promotion.app.maps.CouponGrantFlowTypeMap;
import com.kmzyc.promotion.app.service.CouponGrantService;
import com.kmzyc.promotion.app.vobject.Coupon;
import com.kmzyc.promotion.app.vobject.CouponGrant;
import com.kmzyc.promotion.app.vobject.CouponGrantExample;
import com.kmzyc.promotion.app.vobject.CouponGrantFlow;
import com.kmzyc.promotion.exception.ServiceException;
import com.pltfm.app.dataobject.UserInfoDO;

@SuppressWarnings("unchecked")
@Repository("CouponGrantService")
public class CouponGrantServiceImpl implements CouponGrantService {


    // 日志记录
    private static final Logger logger = LoggerFactory.getLogger(CouponGrantServiceImpl.class);
    /**
     * 优惠券DAO
     */
    @Resource(name = "coupondao")
    private CouponDAO coupondao;

    /**
     * 优惠券产品dao
     */
    @Resource(name = "couponProductdao")
    private CouponProductDAO couponProductdao;

    @Resource(name = "couponGrantdao")
    private CouponGrantDAO couponGrantdao;

    /**
     * 优惠券流水DAO
     */
    @Resource
    private CouponGrantFlowDAO couponGrantFlowDAO;

    @Override
    public Integer updateGrantStatus(CouponGrant couponGrant) throws SQLException {
        int rows = couponGrantdao.updateGrantStauts(couponGrant);
        return rows;
    }

    /**
     * 根据条件查询优惠券发放表
     */
    @Override
    public List<CouponGrant> findGrantByCondition(CouponGrantExample grantExzample)
            throws SQLException {
        List<CouponGrant> grantList = new ArrayList<CouponGrant>();
        grantList = couponGrantdao.selectByExample(grantExzample);
        return grantList;
    }


    /**
     * 查找未过期且有效日期小于等于多少天的优惠券
     * 
     * @throws SQLException
     */
    @Override
    public List<CouponGrant> selectUseLeaseDay(int remainDay) throws SQLException {
        List<CouponGrant> couponGrantList = couponGrantdao.selectCanUseLeaseDays(remainDay);
        return couponGrantList;
    }


    @Override
    public void queryAlreadyGrantCouponList(Page pageCondition, CouponGrant queryPara) {
        int pageIndex = pageCondition.getPageNo();

        if (pageIndex == 0) {
            pageIndex = 1;
        }
        int pageSize = pageCondition.getPageSize();
        int skip = (pageIndex - 1) * pageSize + 1;
        int max = pageSize * pageIndex;

        queryPara.setSkip(skip);
        queryPara.setMax(max);


        try {
            int count = couponGrantdao.queryAlreadyGrantCouponCount(queryPara);
            List<CouponGrant> list = couponGrantdao.queryAlreadyGrantCouponList(queryPara);

            pageCondition.setRecordCount(count);
            pageCondition.setDataList(list);

        } catch (SQLException e) {
            logger.error("查询已发放优惠券列表出现异常:", e);
        }


    }

    /**
     * 更新已到期的优惠券
     */
    @Override
    public int updateExpiredCoupon() throws SQLException {
        return couponGrantdao.updateExpriedCoupon();
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED,
            rollbackFor = ServiceException.class)
    public void insertCouponInfo(UserInfoDO user, Coupon couponNew, String couponGrantDetailType,
            String couponGrantFlowStatus) throws ServiceException {
        // 插入优惠券发放表
        Long couponGrantId = this.insertCouponGrant(user, couponNew, couponGrantDetailType);
        String operatingPersonName = "远程调用接口使用";
        // 插入优惠券操作流水表
        this.insertCouponGrantFlow(user, couponNew, couponGrantId, couponGrantDetailType,
                couponGrantFlowStatus, operatingPersonName);
        logger.info("给用户{}发放优惠券成功,并且成功插入优惠券流水表:grantID:{},couponID:{}", user.getLoginAccount(),
                couponGrantId, couponNew.getCouponId());
    }

    /**
     * 插入优惠券发放信息
     * 
     * @param user
     * @param coupon
     * @return
     * @throws SQLException
     */
    public Long insertCouponGrant(UserInfoDO user, Coupon couponNew, String couponGrantDetailType)
            throws ServiceException {
        Long grantId = null;
        try {
            Date date = new Date();
            // 发放表
            CouponGrant grantSave = new CouponGrant();
            grantSave.setCustomId(user.getLoginId());
            grantSave.setCouponStatus(Long.parseLong(CouponStatus.NOTUSE_COUPONSTATUS.getType()));
            grantSave.setGrantCreateman(user.getLoginId().longValue());
            grantSave.setGrantCreattime(date);
            grantSave.setGrantType(Long.parseLong(couponGrantDetailType));
            grantSave.setActTime(date);
            SimpleDateFormat formate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            // 根据优惠券ID 设置优惠券有效时间
            if (couponNew.getCouponValidDay() != null) {
                Calendar ca = Calendar.getInstance();
                grantSave.setStartTime(formate.parse(formate.format(date)));

                ca.add(Calendar.DATE, couponNew.getCouponValidDay().intValue());
                grantSave.setEndTime(ca.getTime());
            } else {
                if (couponNew.getEndtime().before(date)) {
                    grantSave.setCouponStatus(
                            Long.parseLong(CouponStatus.HAVEPASSDATE_COUPONSTATUS.getType()));
                }
                grantSave.setStartTime(couponNew.getStarttime());
                grantSave.setEndTime(couponNew.getEndtime());
            }
            grantSave.setCouponId(couponNew.getCouponId());
            grantId = couponGrantdao.insertGrant(grantSave);
        } catch (Exception e) {
            logger.error("insertCouponGrant插入优惠券发放信息异常：", e);
            throw new ServiceException(e);
        }
        return grantId;

    }

    /**
     * 插入优惠券操作流水信息
     * 
     * @param user
     * @param coupon
     * @param couponGrantId
     * @throws SQLException
     */
    public void insertCouponGrantFlow(UserInfoDO user, Coupon couponNew, Long couponGrantId,
            String couponGrantDetailType, String couponGrantFlowStatus, String operatingPersonName)
            throws ServiceException {
        try {
            // 流水表
            CouponGrantFlow grantFlow = new CouponGrantFlow();
            grantFlow.setCouponGrantFlowId(Long.valueOf(couponGrantFlowStatus));
            grantFlow.setCouponGrantFlowType(Long.valueOf(couponGrantFlowStatus));
            grantFlow.setCreateDate(new Date());
            grantFlow.setOperatingPersonName(operatingPersonName);
            grantFlow.setCustomer(user.getLoginAccount());
            grantFlow.setCustomId(user.getLoginId().longValue());
            grantFlow.setCouponName(couponNew.getCouponName());
            grantFlow.setCouponId(couponNew.getCouponId());
            grantFlow.setCouponGrantId(couponGrantId);
            grantFlow.setRemark(CouponGrantDetailTypeMap.getValue(couponGrantDetailType)
                    + ", 优惠券操作类型为:" + CouponGrantFlowTypeMap.getValue(couponGrantFlowStatus) + "，"
                    + "   优惠券编号：" + couponNew.getCouponId() + "，   发放id" + couponGrantId
                    + "，      操作人：" + user.getLoginId());
            couponGrantFlowDAO.insert(grantFlow);
        } catch (Exception e) {
            logger.error("insertCouponGrantFlow插入优惠券操作流水信息异常：", e);
            throw new ServiceException(e);
        }
    }

    public CouponDAO getCoupondao() {
        return coupondao;
    }

    public void setCoupondao(CouponDAO coupondao) {
        this.coupondao = coupondao;
    }

    public CouponProductDAO getCouponProductdao() {
        return couponProductdao;
    }

    public void setCouponProductdao(CouponProductDAO couponProductdao) {
        this.couponProductdao = couponProductdao;
    }

    public CouponGrantDAO getCouponGrantdao() {
        return couponGrantdao;
    }

    public void setCouponGrantdao(CouponGrantDAO couponGrantdao) {
        this.couponGrantdao = couponGrantdao;
    }

    @Override
    public List<CouponGrant> queryCouponGrantByTime(CouponGrant cg) throws SQLException {
        return couponGrantdao.queryCouponGrantTimeOut(cg);
    }

    @Override
    public CouponGrant selectCouponGrantById(CouponGrant cg) throws SQLException {

        return couponGrantdao.selectCouponGrantById(cg);
    }

}
