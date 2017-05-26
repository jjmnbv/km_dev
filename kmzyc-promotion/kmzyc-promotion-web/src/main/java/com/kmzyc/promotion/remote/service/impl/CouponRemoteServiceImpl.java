package com.kmzyc.promotion.remote.service.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.StringTokenizer;

import javax.annotation.Resource;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.kmzyc.b2b.model.User;
import com.kmzyc.promotion.app.dao.CouponDAO;
import com.kmzyc.promotion.app.dao.CouponGrantDAO;
import com.kmzyc.promotion.app.dao.CouponGrantFlowDAO;
import com.kmzyc.promotion.app.dao.CouponGrantSetDAO;
import com.kmzyc.promotion.app.dao.CouponProductDAO;
import com.kmzyc.promotion.app.dao.ProductDao;
import com.kmzyc.promotion.app.dao.SuppliersInfoDAO;
import com.kmzyc.promotion.app.dao.UserInfoDao;
import com.kmzyc.promotion.app.dao.ViewProductSkuDAO;
import com.kmzyc.promotion.app.enums.CouponGrantDetailType;
import com.kmzyc.promotion.app.enums.CouponGrantFlowStatus;
import com.kmzyc.promotion.app.enums.CouponProductType;
import com.kmzyc.promotion.app.enums.CouponStatus;
import com.kmzyc.promotion.app.maps.CouponGrantDetailTypeMap;
import com.kmzyc.promotion.app.service.CouponGrantService;
import com.kmzyc.promotion.app.service.CouponGrantSetService;
import com.kmzyc.promotion.app.service.CouponService;
import com.kmzyc.promotion.app.util.Constants;
import com.kmzyc.promotion.app.vobject.Coupon;
import com.kmzyc.promotion.app.vobject.CouponCanUse;
import com.kmzyc.promotion.app.vobject.CouponGrant;
import com.kmzyc.promotion.app.vobject.CouponGrantExample;
import com.kmzyc.promotion.app.vobject.CouponGrantFlow;
import com.kmzyc.promotion.app.vobject.CouponGrantSeting;
import com.kmzyc.promotion.app.vobject.OrderVo;
import com.kmzyc.promotion.app.vobject.Product;
import com.kmzyc.promotion.app.vobject.ViewProductSku;
import com.kmzyc.promotion.app.vobject.ViewProductSkuExample;
import com.kmzyc.promotion.exception.ServiceException;
import com.kmzyc.promotion.remote.service.CouponRemoteService;
import com.kmzyc.supplier.model.SuppliersInfo;
import com.pltfm.app.dataobject.UserInfoDO;
import com.pltfm.app.entities.OrderPreferential;
import com.whalin.MemCached.MemCachedClient;

@SuppressWarnings("unchecked")
@Service("couponRemoteService")
public class CouponRemoteServiceImpl implements CouponRemoteService {

    private static final Logger logger = LoggerFactory.getLogger(CouponRemoteServiceImpl.class);

    private static int SUCCESS = 1;

    private static int FAIL = 2;
    /**
     * 产品DAO
     */
    @Resource(name = "productDao")
    private ProductDao productDao;

    /**
     * 优惠券DAO
     */
    @Resource(name = "coupondao")
    private CouponDAO coupondao;
    /**
     * 供应商
     */
    @Resource(name = "suppliersInfoDAO")
    private SuppliersInfoDAO suppliersInfoDAO;

    /**
     * 优惠券产品DAO
     */
    @Resource(name = "couponProductdao")
    private CouponProductDAO couponProductdao;

    /**
     * 优惠券发放DAO
     */
    @Resource(name = "couponGrantdao")
    private CouponGrantDAO couponGrantdao;

    /**
     * 查看产品sku
     */
    @Resource
    private ViewProductSkuDAO viewProductSkuDao;

    /**
     * 优惠券接口类
     */
    @Resource(name = "CouponService")
    private CouponService CouponService;

    /**
     * 优惠券发放接口
     */
    @Resource(name = "couponGrantdao")
    private CouponGrantDAO couponGrantDAO;
    @Resource(name = "couponGrantSetdao")
    private CouponGrantSetDAO couponGrantSetDAO;

    /**
     * 优惠券流水DAO
     */
    @Resource
    private CouponGrantFlowDAO couponGrantFlowDAO;

    /**
     * 优惠券发放设置接口
     */
    @Resource(name = "couponGrantSetdao")
    private CouponGrantSetDAO couponGrantSetDao;
    @Resource(name = "CouponGrantService")
    private CouponGrantService couponGrantService;
    @Resource(name = "couponGrantSetService")
    private CouponGrantSetService couponGrantSetService;

    // 用户信息查询dao 20150618 add 用于将原先调用用户远程接口改为自己查询实现
    @Resource(name = "userInfoDao")
    private UserInfoDao userInfoDao;
    @Resource(name = "memCachedClient")
    private MemCachedClient memCachedClient;

    // 优惠券规则产品类目信息缓存15天
    private static final int COUPON_PRODUCT_RATE_TIME_OUT = 15 * 24 * 60 * 60 * 1000;

    // 修改优惠券规则状态 已发放
    @Override
    public int updateCouponStatus(Coupon coupon) throws Exception {
        coupon.setStatus(Long.parseLong(CouponStatus.HAVEGIVE_COUPONSTATUS.getType()));
        return coupondao.updateCoupon(coupon);
    }

    // 获取有效优惠券规则(未发放，已发放)
    @Override
    public List<Coupon> selectEffectiveCoupon(Coupon coupon) throws Exception {
        coupon.setNotStatus(Long.parseLong(CouponStatus.HAVEPASSDATE_COUPONSTATUS.getType()));
        return coupondao.selectEffectiveCoupon(coupon);
    }

    // 优惠券激活码验证处理 返回参数1：用户Id为空 ,2:优惠券激活码为空,3:优惠券激活码无效,4:优惠券激活码已激活
    // 6:该激活码已过期，7：激活优惠券异常，8：激活优惠券成功
    @Override
    @SuppressWarnings("static-access")
    public int checkCouponGrant(String userId, String activeCode) throws Exception {
        if (StringUtils.isBlank(userId)) {
            return 1;
        } else if (StringUtils.isBlank(activeCode)) {
            return 2;
        } else {
            CouponGrant couponGrant = couponGrantDAO.selectCouponGrantCheck(activeCode);
            UserInfoDO userInfo = userInfoDao.queryUserInfo(Long.valueOf(userId));
            if (couponGrant == null) {
                return 3;
            } else if (couponGrant.getActStatus() == 1) {
                return 4;
            } else {
                // 优惠券激活码激活 获取该规则设置的有效时间
                Coupon coupon = new Coupon();
                coupon.setCouponId(couponGrant.getCouponId());
                coupon = coupondao.selectCoupon(coupon);
                if (coupon == null) {
                    return 3;
                } else {
                    // 获取优惠券规则的优惠券时间类型
                    if (coupon.getTimeType() == 1) {
                        Date d = new Date();
                        // 固定时间
                        Date startTime = coupon.getStarttime();
                        Date endTime = coupon.getEndtime();
                        // 判断激活时间是否已过期
                        if (d.after(endTime)) {
                            // 插入优惠券流水
                            CouponGrantFlow record = new CouponGrantFlow();
                            record.setCouponGrantFlowType(Long.valueOf(
                                    CouponGrantFlowStatus.HAVEPASSDATE_COUPONFLOWSTATUS.getType()));
                            record.setCreateDate(new Date());
                            record.setCouponGrantId(couponGrant.getCouponGrantId());
                            record.setCouponId(coupon.getCouponId());
                            record.setCouponName(coupon.getCouponName());
                            record.setOperatingPerson(Long.parseLong(userId));
                            record.setRemark("不记名优惠券激活 ，优惠券操作类型为:"
                                    + CouponGrantFlowStatus.ACTIVATION_COUPONFLOWSTATUS.getTitle()
                                    + "，优惠券发放编号：" + couponGrant.getCouponGrantId() + "，优惠券编号："
                                    + couponGrant.getCouponId() + "， 操作人：远程接口调用， 优惠券激活状态：激活过期");
                            record.setOperatingPersonName("远程接口调用 ");
                            if (userInfo != null) {
                                record.setCustomer(userInfo.getLoginAccount());
                            }
                            record.setCustomId(Long.parseLong(userId));
                            couponGrantFlowDAO.insert(record);
                            return 6;
                        }
                        // 修改不记名优惠券的有效时间
                        couponGrant.setStartTime(startTime);
                        couponGrant.setEndTime(endTime);
                    } else if (coupon.getTimeType() == 2) {
                        // 固定天数
                        int days = coupon.getCouponValidDay().intValue();
                        // 修改不记名优惠券的有效时间
                        // 起始时间延迟5分钟
                        Calendar c = Calendar.getInstance();
                        c.setTime(new Date());
                        c.add(c.MINUTE, -5);
                        couponGrant.setStartTime(c.getTime());
                        c.setTime(new Date());
                        c.add(c.DAY_OF_MONTH, days);
                        couponGrant.setEndTime(c.getTime());
                    }
                    // 修改优惠券表发放信息
                    couponGrant.setCustomId(Integer.parseInt(userId));
                    couponGrant.setCouponStatus((long) 3);
                    couponGrant.setGrantUpdatetime(new Date());
                    couponGrant.setGrantUpdateman(Long.parseLong(userId));
                    couponGrant.setActStatus((long) 1);
                    couponGrant.setActTime(new Date());
                    int rows = couponGrantDAO.updateCouponGrant(couponGrant);
                    if (rows == 0) {
                        return 7;
                    } else {
                        // 插入优惠券流水
                        CouponGrantFlow record = new CouponGrantFlow();
                        record.setCouponGrantFlowType(Long.valueOf(
                                CouponGrantFlowStatus.ACTIVATION_COUPONFLOWSTATUS.getType()));
                        record.setCreateDate(new Date());
                        record.setCouponGrantId(couponGrant.getCouponGrantId());
                        record.setCouponId(coupon.getCouponId());
                        record.setCouponName(coupon.getCouponName());
                        record.setOperatingPerson(Long.parseLong(userId));
                        record.setRemark("不记名优惠券激活 ，优惠券操作类型为:"
                                + CouponGrantFlowStatus.ACTIVATION_COUPONFLOWSTATUS.getTitle()
                                + "，优惠券发放编号：" + couponGrant.getCouponGrantId() + "，优惠券编号："
                                + couponGrant.getCouponId() + "，操作人：远程接口调用， 优惠券发放人：" + userId);
                        record.setOperatingPersonName("远程接口调用 ");

                        if (userInfo != null) {
                            record.setCustomer(userInfo.getLoginAccount());
                        }
                        record.setCustomId(Long.parseLong(userId));
                        couponGrantFlowDAO.insert(record);

                        return 8;
                    }

                }
            }
        }
    }

    /**
     * 根据客户Id，查询优惠券发放信息
     * 
     * @return
     */
    @Override
    public List<CouponGrant> SelectCouponGrantList(List<Integer> customId) throws Exception {
        List<CouponGrant> couponGrantList = couponGrantdao.selectCouponGrantByCoustomId(customId);
        return couponGrantList;
    }

    /**
     * userList:给谁发放 仅仅用于注册类型和订单类型 将conpongrant的状态改为以发放 grant_man:发放人 update:10.30 ：新增发放时间
     * 如果传过来的优惠券规则已过期，则返回-1 , 新增传入的发放ID 如果是发放、更新操作，返回值为插入、更新条数。
     */
    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED,
            rollbackFor = ServiceException.class)
    public int changeCustomGrantToGive(UserInfoDO userman, Long couponId, Long grant_man,
            Long grant_type, String relatedCode, Long grantId) throws ServiceException {
        // 判断grant_type，如果非法，直接返回
        if (!grant_type.toString().equals(CouponGrantDetailType.ORDER_RETURNORDER.getType())// 订单退货
                && !grant_type.toString().equals(CouponGrantDetailType.ORDER_FREEZORDER.getType())// 订单冻结
                && !grant_type.toString()
                        .equals(CouponGrantDetailType.POINTEXCUT_COUPONGRANTDETAILTYPE.getType())// 积分兑换发放
                && !grant_type.toString().equals(CouponGrantDetailType.ORDER_UNFREEZORDER.getType())// 订单解冻
                && !grant_type.toString().equals(CouponGrantDetailType.PHONE_SHAKEGRANT.getType())// 手机端发放
                && !grant_type.toString().equals(CouponGrantDetailType.ORDER_PAYUSEING.getType())// 订单支付使用
        ) {
            return -1;
        }

        int sucess_value = 0;
        try {
            // 查询规则
            Coupon c = coupondao.selectByPrimaryKey(couponId);
            if (c == null) {
                return -2;
            }
            CouponGrant coupongrant = new CouponGrant();
            // 如果是退换货类型则改变状态
            if (grant_type.toString().equals(CouponGrantDetailType.ORDER_RETURNORDER.getType())
                    || grant_type.toString()
                            .equals(CouponGrantDetailType.ORDER_FREEZORDER.getType())
                    || grant_type.toString()
                            .equals(CouponGrantDetailType.ORDER_UNFREEZORDER.getType())
                    || grant_type.toString()
                            .equals(CouponGrantDetailType.ORDER_PAYUSEING.getType())) {

                coupongrant = couponGrantdao.selectCouponById(grantId);

                // coupongrant.setCouponGrantId(grantId);
                coupongrant.setCouponId(couponId);
                coupongrant.setCustomId(new Integer(userman.getLoginId()));
                coupongrant.setGrantType(grant_type);
                coupongrant.setGrantRelatedCode(relatedCode);

                // 插入数据到优惠券流水表
                UserInfoDO userInfo = userInfoDao.queryUserInfo(Long.valueOf(userman.getLoginId()));
                CouponGrantFlow couponGrantFlow = new CouponGrantFlow();
                if (grantId != null) {
                    couponGrantFlow.setCouponGrantId(Long.valueOf(grantId));
                }
                couponGrantFlow.setCreateDate(new Date());

                if (grant_man != null) {
                    couponGrantFlow.setOperatingPerson(Long.valueOf(grant_man));
                }
                couponGrantFlow.setCouponName(c.getCouponName());
                couponGrantFlow.setCouponId(c.getCouponId());
                couponGrantFlow.setOperatingPersonName("远程接口调用 ");
                couponGrantFlow.setCustomId(Long.valueOf(userman.getLoginId()));
                couponGrantFlow.setOrderCode(relatedCode);

                if (userInfo != null) {
                    couponGrantFlow.setCustomer(userInfo.getLoginAccount());
                }

                coupongrant.setGrantUpdateman(grant_man);
                coupongrant.setGrantUpdatetime(new Date());
                Date nowDate = new Date();
                // 使用优惠劵时过期处理
                if (nowDate.after(coupongrant.getEndTime())) {
                    coupongrant.setCouponStatus(
                            Long.parseLong(CouponStatus.HAVEPASSDATE_COUPONSTATUS.getType()));
                    couponGrantFlow.setCouponGrantFlowType(Long.valueOf(
                            CouponGrantFlowStatus.HAVEPASSDATE_COUPONFLOWSTATUS.getType()));
                    couponGrantFlow.setRemark("优惠券过期， 优惠券操作类型为:"
                            + CouponGrantFlowStatus.HAVEPASSDATE_COUPONFLOWSTATUS.getTitle() + "，"
                            + "，    优惠券编号：" + couponId + "，    优惠券发放id：" + grantId + "，   订单号："
                            + relatedCode + "，   操作人：" + grant_man);
                    sucess_value = couponGrantdao.updateGrantStauts(coupongrant);
                    // 插入优惠券流水信息
                    couponGrantFlowDAO.insert(couponGrantFlow);
                    return sucess_value;
                }

                // 退货
                if (grant_type.toString()
                        .equals(CouponGrantDetailType.ORDER_RETURNORDER.getType())) {
                    coupongrant.setCouponStatus(
                            Long.parseLong(CouponStatus.INVALID_COUPONSTATUS.getType()));
                    coupongrant.setGrantRemark("退货作废，相关订单号：" + relatedCode);
                    // 优惠券流水信息
                    if (relatedCode != null) {
                        couponGrantFlow.setOrderCode(relatedCode);
                    }
                    couponGrantFlow.setCouponGrantFlowType(
                            Long.valueOf(CouponGrantFlowStatus.INVALID_COUPONFLOWSTATUS.getType()));
                    couponGrantFlow.setRemark("退货作废, 优惠券操作类型为:"
                            + CouponGrantFlowStatus.INVALID_COUPONFLOWSTATUS.getTitle() + "，"
                            + "优惠券编号：" + couponId + "，   优惠券发放id" + grantId + "，   订单号："
                            + relatedCode + "，   操作人：" + grant_man);
                } else if (grant_type.toString()
                        .equals(CouponGrantDetailType.ORDER_FREEZORDER.getType())) {// 冻结
                    // 优惠券流水信息
                    if (relatedCode != null) {
                        couponGrantFlow.setOrderCode(relatedCode);
                    }
                    // GrantStauts从未使用状态 变为冻结状态
                    coupongrant.setCouponStatus(
                            Long.parseLong(CouponStatus.FREEZE_COUPONSTATUS.getType()));
                    coupongrant.setFromCouponStatus(
                            Long.parseLong(CouponStatus.NOTUSE_COUPONSTATUS.getType()));
                    coupongrant.setGrantRemark("冻结操作，相关连号：" + relatedCode);
                    // 优惠券流水信息
                    couponGrantFlow.setCouponGrantFlowType(
                            Long.valueOf(CouponGrantFlowStatus.FREEZE_COUPONFLOWSTATUS.getType()));
                    couponGrantFlow.setRemark("优惠券冻结， 优惠券操作类型为:"
                            + CouponGrantFlowStatus.FREEZE_COUPONFLOWSTATUS.getTitle() + "，"
                            + "，    优惠券编号：" + couponId + "，    优惠券发放id：" + grantId + "，   订单号："
                            + relatedCode + "，   操作人：" + grant_man);
                } else if (grant_type.toString()
                        .equals(CouponGrantDetailType.ORDER_UNFREEZORDER.getType())) {// 解冻
                    // 优惠券流水信息
                    if (relatedCode != null) {
                        couponGrantFlow.setOrderCode(relatedCode);
                    }
                    coupongrant.setCouponStatus(
                            Long.parseLong(CouponStatus.NOTUSE_COUPONSTATUS.getType()));
                    coupongrant.setGrantRemark("解冻操作，相关连号：" + relatedCode);
                    // 优惠券流水信息
                    couponGrantFlow.setCouponGrantFlowType(Long
                            .valueOf(CouponGrantFlowStatus.UNFREEZE_COUPONFLOWSTATUS.getType()));
                    couponGrantFlow.setRemark(
                            "优惠券操作类型为:" + CouponGrantFlowStatus.UNFREEZE_COUPONFLOWSTATUS.getTitle()
                                    + "，" + " 优惠券编号：" + couponId + "，   优惠券 发放id：" + grantId
                                    + "，   订单号：" + relatedCode + "，    操作人：" + grant_man);
                } else if (grant_type.toString()
                        .equals(CouponGrantDetailType.ORDER_PAYUSEING.getType())) {
                    coupongrant.setCouponStatus(
                            Long.parseLong(CouponStatus.HAVEUSE_COUPONSTATUS.getType()));
                    coupongrant.setGrantRemark("订单支付使用，相关订单号：" + relatedCode);
                    coupongrant.setUseTime(new Date());
                    // 优惠券流水信息
                    if (relatedCode != null) {
                        couponGrantFlow.setOrderCode(relatedCode);
                    }
                    couponGrantFlow.setCouponGrantFlowType(
                            Long.valueOf(CouponGrantFlowStatus.HAVEUSE_COUPONFLOWSTATUS.getType()));
                    couponGrantFlow.setRemark("订单支付使用，优惠券操作类型为:"
                            + CouponGrantFlowStatus.HAVEUSE_COUPONFLOWSTATUS.getTitle() + "，"
                            + "优惠券编号：" + couponId + "，    优惠券发放id：" + grantId + "，   订单号："
                            + relatedCode + "，      操作人：" + grant_man);
                }

                sucess_value = couponGrantdao.updateGrantStauts(coupongrant);
                // 插入优惠券流水信息
                couponGrantFlowDAO.insert(couponGrantFlow);
            } else {
                // 插入数据到优惠券流水表
                UserInfoDO userInfo = userInfoDao.queryUserInfo(Long.valueOf(userman.getLoginId()));
                CouponGrantFlow couponGrantFlow = new CouponGrantFlow();
                if (grantId != null) {
                    couponGrantFlow.setCouponGrantId(Long.valueOf(grantId));
                }
                couponGrantFlow.setCreateDate(new Date());

                if (grant_man != null) {
                    couponGrantFlow.setOperatingPerson(Long.valueOf(grant_man));
                }
                couponGrantFlow.setCouponName(c.getCouponName());
                couponGrantFlow.setCouponId(c.getCouponId());

                if (userInfo != null) {
                    couponGrantFlow.setCustomer(userInfo.getLoginAccount());
                }

                couponGrantFlow.setOperatingPersonName("远程接口调用 ");
                couponGrantFlow.setCustomId(Long.valueOf(userman.getLoginId()));

                coupongrant.setCouponId(couponId);
                coupongrant.setCustomId(userman.getLoginId());

                // 改变为未使用
                coupongrant.setCouponStatus(new Long(CouponStatus.NOTUSE_COUPONSTATUS.getType()));
                coupongrant.setGrantCreateman(grant_man);
                coupongrant.setGrantCreattime(new Date());
                coupongrant.setGrantType(grant_type);

                if (grant_type.toString()
                        .equals(CouponGrantDetailType.POINTEXCUT_COUPONGRANTDETAILTYPE.getType())) {
                    Calendar ca = Calendar.getInstance();
                    ca.add(Calendar.DAY_OF_WEEK, c.getCouponValidDay().intValue());
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
                    Date endDate = format.parse(format.format(ca.getTime()));

                    // 添加激活时间
                    Date datenow = new Date();
                    coupongrant.setActTime(datenow);
                    coupongrant.setStartTime(datenow);
                    coupongrant.setEndTime(endDate);

                    // 优惠券流水信息
                    couponGrantFlow.setCouponGrantFlowType(
                            Long.valueOf(CouponGrantFlowStatus.GIVE_COUPONFLOWSTATUS.getType()));
                    couponGrantFlow.setRemark("积分兑换发放，  优惠券操作类型为:"
                            + CouponGrantFlowStatus.GIVE_COUPONFLOWSTATUS.getTitle() + "，"
                            + "  优惠券编号：" + couponId + "，      操作人：" + grant_man + "，   优惠券发放id：");
                } else {
                    coupongrant.setStartTime(c.getStarttime());
                    coupongrant.setEndTime(c.getEndtime());
                }


                Long newGrantId = couponGrantdao.insertGrant(coupongrant);
                if (newGrantId != null) {
                    couponGrantFlow.setRemark(couponGrantFlow.getRemark() + newGrantId.toString());
                }
                // 插入优惠券流水信息
                couponGrantFlow.setCouponGrantId(newGrantId);
                couponGrantFlowDAO.insert(couponGrantFlow);
                sucess_value++;
            }
        } catch (Exception e) {
            logger.error("changeCustomGrantToGive接口出错！", e);
            throw new ServiceException(e);
        }

        return sucess_value;
    }

    /**
     * 
     * 批量发放，将conpongrant的状态改为以发放
     */
    @Override
    public int changeCustomGrantToGive(List<UserInfoDO> userList, Long couponId, Long grant_man,
            Long grant_type, String relatedCode) throws Exception {
        logger.info("changeCustomGrantToGive:批量发放优惠券  couponId={}", couponId);
        int sucess_value = 0;
        Coupon c = coupondao.selectByPrimaryKey(couponId);
        CouponGrant coupongrant = new CouponGrant();
        // 如果是退换货类型则改变状态
        if (grant_type.toString().equals(CouponGrantDetailType.ORDER_RETURNORDER.getType())
                || grant_type.toString().equals(CouponGrantDetailType.ORDER_FREEZORDER.getType())
                || grant_type.toString().equals(CouponGrantDetailType.ORDER_PAYUSEING.getType())) {
            for (UserInfoDO user : userList) {

                coupongrant.setCouponId(couponId);
                coupongrant.setCustomId(new Integer(user.getLoginId()));
                // 插入数据到优惠券流水表
                CouponGrantFlow couponGrantFlow = new CouponGrantFlow();
                couponGrantFlow.setCreateDate(new Date());
                couponGrantFlow.setOrderCode(relatedCode);
                couponGrantFlow.setOperatingPerson(Long.valueOf(grant_man));
                couponGrantFlow.setCouponName(c.getCouponName());
                couponGrantFlow.setCouponId(c.getCouponId());
                couponGrantFlow.setCustomer(user.getLoginAccount());
                couponGrantFlow.setOperatingPersonName("远程接口调用  id: " + grant_man);
                couponGrantFlow.setCustomId(Long.valueOf(user.getLoginId()));
                // 退货
                if (grant_type.toString()
                        .equals(CouponGrantDetailType.ORDER_RETURNORDER.getType())) {
                    coupongrant.setCouponStatus(
                            Long.parseLong(CouponStatus.INVALID_COUPONSTATUS.getType()));
                    coupongrant.setGrantRemark("退货作废，相关订单号：" + relatedCode);
                    // 优惠券流水信息
                    couponGrantFlow.setCouponGrantFlowType(
                            Long.valueOf(CouponGrantFlowStatus.INVALID_COUPONFLOWSTATUS.getType()));
                    couponGrantFlow.setRemark("批量操作退货作废 优惠券操作类型为:"
                            + CouponGrantFlowStatus.INVALID_COUPONFLOWSTATUS.getTitle() + "，"
                            + "优惠券编号：：" + couponId + "发放id：xxx" + "订单号：" + relatedCode + "操作人："
                            + grant_man);
                } else if (grant_type.toString()
                        .equals(CouponGrantDetailType.ORDER_PAYUSEING.getType())) {
                    coupongrant.setCouponStatus(
                            Long.parseLong(CouponStatus.HAVEUSE_COUPONSTATUS.getType()));
                    coupongrant.setGrantRemark("订单支付使用，相关订单号：" + relatedCode);
                    coupongrant.setUseTime(new Date());// 优惠券使用时间
                    // 优惠券流水信息
                    couponGrantFlow.setCouponGrantFlowType(
                            Long.valueOf(CouponGrantFlowStatus.HAVEUSE_COUPONFLOWSTATUS.getType()));
                    couponGrantFlow.setRemark(
                            "优惠券操作类型为:" + CouponGrantFlowStatus.HAVEUSE_COUPONFLOWSTATUS.getTitle()
                                    + "，" + "优惠券编号：：" + couponId + "发放id：xxx" + "订单号：" + relatedCode
                                    + "操作人：" + grant_man);
                } else {
                    coupongrant.setCouponStatus(
                            Long.parseLong(CouponStatus.FREEZE_COUPONSTATUS.getType()));
                    coupongrant.setGrantRemark("冻结操作，相关连号：" + relatedCode);
                    // 优惠券流水信息
                    couponGrantFlow.setCouponGrantFlowType(
                            Long.valueOf(CouponGrantFlowStatus.FREEZE_COUPONFLOWSTATUS.getType()));
                    couponGrantFlow.setRemark(
                            "优惠券操作类型为:" + CouponGrantFlowStatus.FREEZE_COUPONFLOWSTATUS.getTitle()
                                    + "，" + "优惠券编号：" + couponId + "发放id：xxx" + "订单号：" + relatedCode
                                    + "操作人：" + grant_man);
                }
                coupongrant.setGrantUpdateman(grant_man);
                coupongrant.setGrantUpdatetime(new Date());
                couponGrantdao.updateGrantStauts(coupongrant);

                // 插入优惠券流水信息
                couponGrantFlowDAO.insert(couponGrantFlow);
                sucess_value++;
                return sucess_value;
            }
        } else {

            String value = "";
            Set<String> keySet = CouponGrantDetailTypeMap.getMap().keySet();
            for (String key : keySet) {
                if (key.equals(grant_type.toString())) {
                    value = CouponGrantDetailTypeMap.getMap().get(key);
                }
            }
            for (UserInfoDO user : userList) {
                // 插入数据到优惠券流水表
                CouponGrantFlow couponGrantFlow = new CouponGrantFlow();
                couponGrantFlow.setCreateDate(new Date());
                couponGrantFlow.setOrderCode(relatedCode);
                couponGrantFlow.setOperatingPerson(Long.valueOf(grant_man));
                couponGrantFlow.setCouponName(c.getCouponName());
                couponGrantFlow.setCouponId(c.getCouponId());
                couponGrantFlow.setCustomer(user.getLoginAccount());
                couponGrantFlow.setOperatingPersonName("远程接口调用 id:" + grant_man);
                couponGrantFlow.setCustomId(Long.valueOf(user.getLoginId()));
                //
                coupongrant.setCouponId(couponId);
                coupongrant.setCustomId(user.getLoginId());
                // 改变为未使用
                coupongrant.setCouponStatus(new Long(CouponStatus.NOTUSE_COUPONSTATUS.getType()));
                coupongrant.setGrantCreateman(grant_man);
                coupongrant.setGrantCreattime(new Date());
                coupongrant.setGrantType(grant_type);
                if (grant_type.toString()
                        .equals(CouponGrantDetailType.REGIST_COUPONGRANTDETAILTYPE.getType())) {
                    coupongrant.setGrantRelatedCode(user.getLoginId().toString());
                    coupongrant.setGrantRemark(value + ":关联编号" + user.getLoginId());

                    // 优惠券流水信息
                    couponGrantFlow.setCouponGrantFlowType(
                            Long.valueOf(CouponGrantFlowStatus.GIVE_COUPONFLOWSTATUS.getType()));
                    couponGrantFlow.setRemark("注册发放  优惠券操作类型为:"
                            + CouponGrantFlowStatus.HAVEUSE_COUPONFLOWSTATUS.getTitle() + "，"
                            + "优惠券编号：" + couponId + "发放id" + coupongrant.getCouponGrantId() + "订单号："
                            + relatedCode + "操作人：" + grant_man);
                } else {
                    coupongrant.setGrantRelatedCode(relatedCode);
                    coupongrant.setGrantRemark(value + ":关联编号" + relatedCode);
                }
                if (grant_type.toString()
                        .equals(CouponGrantDetailType.POINTEXCUT_COUPONGRANTDETAILTYPE.getType())) {
                    Calendar ca = Calendar.getInstance();
                    ca.add(Calendar.DAY_OF_WEEK, c.getCouponValidDay().intValue());
                    coupongrant.setGrantEndtime(ca.getTime());
                    // 2014.12.18(添加激活时间)
                    Date datenow = new Date();
                    coupongrant.setActTime(datenow);
                    // 优惠券流水信息
                    couponGrantFlow.setCouponGrantFlowType(
                            Long.valueOf(CouponGrantFlowStatus.GIVE_COUPONFLOWSTATUS.getType()));
                    couponGrantFlow.setRemark("积分兑换发放  优惠券操作类型为:"
                            + CouponGrantFlowStatus.HAVEUSE_COUPONFLOWSTATUS.getTitle() + "，"
                            + "优惠券编号：" + couponId + "发放id" + coupongrant.getCouponGrantId() + "订单号："
                            + relatedCode + "操作人：" + grant_man);
                } else {
                    coupongrant.setGrantEndtime(c.getEndtime());
                }
                if (grant_type.toString()
                        .equals(CouponGrantDetailType.MANUAL_COUPONGRANTDETAILTYPE.getType())) {
                    // 优惠券流水信息
                    couponGrantFlow.setCouponGrantFlowType(
                            Long.valueOf(CouponGrantFlowStatus.GIVE_COUPONFLOWSTATUS.getType()));
                    couponGrantFlow.setRemark("手工发放发放  优惠券操作类型为:"
                            + CouponGrantFlowStatus.HAVEUSE_COUPONFLOWSTATUS.getTitle() + "，"
                            + "优惠券编号：" + couponId + "发放id" + coupongrant.getCouponGrantId() + "订单号："
                            + relatedCode + "操作人：" + grant_man);
                } else if (grant_type.toString()
                        .equals(CouponGrantDetailType.LOTTERY_COUPONGRANTDETAILTYPE.getType())) {
                    // 优惠券流水信息
                    couponGrantFlow.setCouponGrantFlowType(
                            Long.valueOf(CouponGrantFlowStatus.GIVE_COUPONFLOWSTATUS.getType()));
                    couponGrantFlow.setRemark("注册类型发放  优惠券操作类型为:"
                            + CouponGrantFlowStatus.HAVEUSE_COUPONFLOWSTATUS.getTitle() + "，"
                            + "优惠券编号：" + couponId + "发放id" + coupongrant.getCouponGrantId() + "订单号："
                            + relatedCode + "操作人：" + grant_man);
                } else if (grant_type.toString()
                        .equals(CouponGrantDetailType.ORDER_RELATEDACTIVITYGRANT.getType())) {
                    // 优惠券流水信息
                    couponGrantFlow.setCouponGrantFlowType(
                            Long.valueOf(CouponGrantFlowStatus.GIVE_COUPONFLOWSTATUS.getType()));
                    couponGrantFlow.setRemark("订单满足活动类型发放  优惠券操作类型为:"
                            + CouponGrantFlowStatus.HAVEUSE_COUPONFLOWSTATUS.getTitle() + "，"
                            + "优惠券编号：" + couponId + "发放id" + coupongrant.getCouponGrantId() + "订单号："
                            + relatedCode + "操作人：" + grant_man);
                }
                couponGrantdao.insertSelective(coupongrant);

                // 插入优惠券流水信息
                couponGrantFlowDAO.insert(couponGrantFlow);

                sucess_value++;
            }
        }
        // 将优惠券规则改为已发放
        Coupon coupon = new Coupon();
        coupon.setStatus(new Long(CouponStatus.HAVEGIVE_COUPONSTATUS.getType()));
        coupon.setCouponId(couponId);
        coupondao.updateCouponStatus(coupon);
        return sucess_value;
    }

    /**
     * 在注册类型下,符合规则的有效的，所有的couponList
     * 
     * @return
     */
    @Override
    public List<Coupon> selectCouponByType(Long couponGivetypeId) throws Exception {
        // 取得有效的注册类型优惠券
        logger.info("selectCouponByType:在注册类型下,符合规则的有效的，所有的couponList");
        List<Coupon> regCouponList = coupondao.selectRegistCoupon(couponGivetypeId);
        return regCouponList;
    }

    /**
     * 已知订单的产品列表 判断在自己的优惠券的列表中在筛选一次
     */
    @Override
    public List<CouponGrant> getCanUseCoupon(String loginId, List<OrderVo> orderList,
            BigDecimal orderMoney) throws Exception {
        logger.info("getCanUseCoupon:已知订单的产品列表判断在自己的优惠券的列表中在筛选一次");
        // List<CouponGrant> couponList = coupondao.selectCanUseCoupon(loginId);
        List<CouponGrant> canUseCoupon = new ArrayList<CouponGrant>();
        return canUseCoupon;
    }

    /**
     * 获取可用优惠券 优惠券判断是否能使用 传参：优惠券列表，订单列表，订单总额，供应商Id 返回参数 ：map key:couponId value:
     * 
     * @throws Exception
     */
    @Override
    public Map<Long, CouponCanUse> canuseCoupon(List<CouponGrant> couponGrantList,
            List<OrderVo> orderList, BigDecimal orderTotlePrice, Long supplierId) throws Exception {
        logger.info("----------------------------canuseCoupon：优惠券查询方法--------------------");
        Map<Long, CouponCanUse> canUseCoupon = new HashMap<Long, CouponCanUse>();
        Date now_date = new Date();
        try {
            if (couponGrantList.size() > 0) {
                // 20151012 mlq add 筛选出不重复的优惠券进行判断 begin
                List<Long> noRepeatCouponIdForCanUse = new ArrayList<Long>();
                List<Long> noRepeatCouponIdForUnUse = new ArrayList<Long>();

                for (CouponGrant couponGrantVo : couponGrantList) {

                    // 如果已经可用,但是该优惠券再次出现
                    if (noRepeatCouponIdForCanUse.indexOf(couponGrantVo.getCouponId()) >= 0) {
                        CouponCanUse returnCan = new CouponCanUse();
                        CouponGrant myGrant = new CouponGrant();
                        myGrant.setCouponGrantId(couponGrantVo.getCouponGrantId());
                        myGrant = couponGrantdao.selectGrantRecord(myGrant);
                        // 获取对应的优惠券规则
                        Coupon coupon = coupondao.selectCouponByPrimaryKey(myGrant.getCouponId());
                        returnCan.setBondPrice(coupon.getCouponMoney());
                        canUseCoupon.put(couponGrantVo.getCouponGrantId(), returnCan);
                    }


                    // 20151012 mlq add 控制可用和不可用重复couponId判断
                    if (noRepeatCouponIdForCanUse.indexOf(couponGrantVo.getCouponId()) >= 0
                            || noRepeatCouponIdForUnUse.indexOf(couponGrantVo.getCouponId()) >= 0) {
                        continue;
                    }
                    CouponGrant myGrant = new CouponGrant();
                    myGrant.setCouponGrantId(couponGrantVo.getCouponGrantId());
                    myGrant = couponGrantdao.selectGrantRecord(myGrant);
                    // 获取对应的优惠券规则
                    Coupon coupon = coupondao.selectCouponByPrimaryKey(myGrant.getCouponId());
                    // 规则限定范围为白名单
                    if (coupon.getRangType() == 1) {
                        // 获取白名单下选中的产品
                        List<String> productRelationIdList =
                                couponProductdao.selectRelationByCouponId(myGrant.getCouponId());
                        // 获取白名单下选中的类目
                        List<String> cateRelationIdList = couponProductdao
                                .selectCateRelationByCouponId(myGrant.getCouponId());

                        logger.info("canuseCoupon白名单下选中的产品productRelationIdList.size="
                                + productRelationIdList.size()
                                + ",白名单下选中的类目cateRelationIdList.size=" + cateRelationIdList.size()
                                + ",canuseCoupon的coupon对应的shopCode=" + coupon.getShopCode());
                        // 判断自营代销和入驻
                        if (coupon.getSupplierType() == 2) {// 自营代销
                            Boolean bool = false;
                            for (OrderVo order : orderList) {
                                // 查询商品
                                Product pro = couponGrantSetDAO
                                        .selectProductBySKU(order.getProductSkuCode());
                                if (!"221".equals(pro.getShopCode())) {// 代销、入驻
                                    // 获取供应商信息
                                    SuppliersInfo sinfo = suppliersInfoDAO
                                            .selectByPrimaryKey(new Long(pro.getShopCode()));
                                    if (sinfo.getSupplierType() != 1
                                            && sinfo.getSupplierType() != 3) {// 入驻
                                        bool = true;
                                        break;
                                    }
                                }
                            }
                            if (bool) {
                                // 20151012 mlq add 说明该优惠券不可用
                                noRepeatCouponIdForUnUse.add(couponGrantVo.getCouponId());
                                continue;
                            }
                        }
                        if (coupon.getSupplierType() == 3) {// 入驻商家
                            Boolean bool = false;
                            for (OrderVo order : orderList) {
                                // 查询商品
                                Product pro = couponGrantSetDAO
                                        .selectProductBySKU(order.getProductSkuCode());
                                if (pro.getShopCode().equals("221")) {// 自营商品
                                    bool = true;
                                    break;

                                } else {
                                    // 查询供应商信息
                                    SuppliersInfo sinfo = suppliersInfoDAO
                                            .selectByPrimaryKey(new Long(pro.getShopCode()));
                                    if (sinfo.getSupplierType() == 3) {// 代销
                                        bool = true;
                                        break;
                                    }
                                }
                            }
                            if (bool) {
                                // 20151012 mlq add 说明该优惠券不可用
                                noRepeatCouponIdForUnUse.add(couponGrantVo.getCouponId());
                                continue;
                            }
                        }

                        // maliqun add begin 20150608 将优惠券指定的shopCode放入map中
                        Map<String, String> shopCodeForCouponSingle = new HashMap<String, String>();
                        // 分隔规则所对应的指定入驻商家
                        if (coupon.getShopCode() != null && !coupon.getShopCode().isEmpty()) {
                            StringTokenizer st = new StringTokenizer(coupon.getShopCode(), ",");
                            while (st.hasMoreTokens()) {
                                String temp = st.nextToken();
                                shopCodeForCouponSingle.put(temp, temp);
                            }
                        }

                        // maliqun add begin 20150608 首先判断是不是规则所指定的入驻商家是不是当前商品所属的商家
                        if (!shopCodeForCouponSingle.isEmpty() && !shopCodeForCouponSingle
                                .containsKey(String.valueOf(supplierId))) {
                            // 20151012 mlq add 说明该优惠券不可用
                            noRepeatCouponIdForUnUse.add(couponGrantVo.getCouponId());
                            continue;
                        }
                        // maliqun add end 20150608 首先判断是不是规则所指定的入驻商家是不是当前商品所属的商家


                        // 全场通用类型优惠券 20150608 update
                        // 将coupon.getShopCode().contains(supplierId.toString())
                        // 变为shopCodeForCouponSingle的map key判断,避免425在4258(这中类似组合
                        // 比如425为传进来的商家Code,4258为优惠券指定的上架code字符串)的情况中出现
                        if ((productRelationIdList.size() == 0 && cateRelationIdList.size() == 0
                                && (coupon.getShopCode() == null
                                        || coupon.getShopCode().equals("")))
                                || ((productRelationIdList.size() == 0
                                        && cateRelationIdList.size() == 0
                                        && coupon.getShopCode() != null
                                        && !coupon.getShopCode().equals(""))
                                        && shopCodeForCouponSingle
                                                .containsKey(String.valueOf(supplierId)))) {
                            // 订当总金额大于等于规则最低消费金额
                            if (orderTotlePrice.compareTo(coupon.getPayLeastMoney()) == 1
                                    || orderTotlePrice.compareTo(coupon.getPayLeastMoney()) == 0) {
                                // 判断优惠券是否在使用期限内
                                if (myGrant.getStartTime().before(now_date)
                                        && myGrant.getEndTime().after(now_date)) {
                                    CouponCanUse returnCan = new CouponCanUse();
                                    returnCan.setBondPrice(coupon.getCouponMoney());
                                    canUseCoupon.put(couponGrantVo.getCouponGrantId(), returnCan);
                                    // 20151012 mlq add 筛选重复的couponId
                                    noRepeatCouponIdForCanUse.add(couponGrantVo.getCouponId());
                                } else {
                                    // 20151012 mlq add 说明该优惠券不可用
                                    noRepeatCouponIdForUnUse.add(couponGrantVo.getCouponId());
                                }
                            }
                        } // 有限定范围
                        else {
                            // 判断传过来的listSKU 是否能够匹配单品 (orderlist中在白名单范围内的单品map集合)
                            Map<String, BigDecimal> productMap =
                                    this.canUseCouponProduct(orderList, productRelationIdList);
                            // 判断传来的是不是在类目里面 单品
                            Map<String, BigDecimal> cateMap = this.canUseCouponCate(orderList,
                                    cateRelationIdList, coupon.getShopCode());
                            // 过滤出套餐信息（白名单） 返回支持白名单的商品 (20150619 add key是套餐id,value是套餐总金额 )
                            Map<BigDecimal, BigDecimal> comboMap =
                                    this.comboInfo(orderList, productRelationIdList);
                            // 判断传来的是不是在类型里面(白名单套餐) 放回支持白名单的类目的套餐 (20150619 add key是套餐id,value是套餐总金额
                            // )
                            Map<BigDecimal, BigDecimal> comboCateMap = this.comboCouponCate(
                                    orderList, cateRelationIdList, coupon.getShopCode());

                            logger.info("canuseCoupon 白名单 listSku 匹配的单品productMap.size="
                                    + productMap.size() + ",listSKu 匹配的类目cateMap.size="
                                    + cateMap.size() + ",canuseCoupon的comboMap.size="
                                    + comboMap.size() + ",comboCateMap=" + comboCateMap.size());

                            // 两个都不为空，去重，筛选出了符合优惠券规则的产品
                            if (productRelationIdList.size() > 0 && cateRelationIdList.size() > 0) {

                                // 20150618 add
                                // (券指定A商品不可用满88可用,同时购买AB商品,但是B商品价格未达到88,应该判断所有可用商品的总额是否大于券的最低可用金额)
                                // begin
                                // 可用单品的总金额
                                double totalPriceForSingleCanUseProduct = 0;

                                // 存储符合的单品,不重复(保证类目和单品种的不重复金额叠加)
                                Map<String, BigDecimal> mapForSingleCanUseProduct =
                                        new HashMap<String, BigDecimal>();
                                // 需要去重复的, productMap当中以及 cateMap当中都有的商品不要累加计算金额
                                if (!productMap.isEmpty() && productMap.size() > 0) {
                                    mapForSingleCanUseProduct.putAll(productMap);
                                }

                                if (!cateMap.isEmpty() && cateMap.size() > 0) {
                                    mapForSingleCanUseProduct.putAll(cateMap);
                                }

                                // 累加可用单品的总金额
                                if (!mapForSingleCanUseProduct.isEmpty()
                                        && mapForSingleCanUseProduct.size() > 0) {
                                    for (Entry<String, BigDecimal> entry : mapForSingleCanUseProduct
                                            .entrySet()) {
                                        BigDecimal o = entry.getValue();
                                        totalPriceForSingleCanUseProduct += o.doubleValue();
                                    }
                                }

                                // 可用套餐中的总金额
                                double totalPriceForCombineCanUseProduct = 0;
                                // 存储符合的套餐,不重复(保持类目中的和单独套餐不要重复)
                                Map<BigDecimal, BigDecimal> mapForCombineCanUseProduct =
                                        new HashMap<BigDecimal, BigDecimal>();
                                // 需要去重复的, productMap当中以及 cateMap当中都有的商品不要累加计算金额


                                if (!comboMap.isEmpty() && comboMap.size() > 0) {
                                    mapForCombineCanUseProduct.putAll(comboMap);
                                }

                                if (!comboCateMap.isEmpty() && comboCateMap.size() > 0) {
                                    mapForCombineCanUseProduct.putAll(comboCateMap);
                                }

                                // 累加可用套餐的总金额
                                if (!mapForCombineCanUseProduct.isEmpty()
                                        && mapForCombineCanUseProduct.size() > 0) {
                                    for (Entry<BigDecimal, BigDecimal> entry : mapForCombineCanUseProduct
                                            .entrySet()) {
                                        BigDecimal o = entry.getValue();
                                        totalPriceForCombineCanUseProduct += o.doubleValue();
                                    }
                                }

                                double totalPriceForProduct = totalPriceForSingleCanUseProduct
                                        + totalPriceForCombineCanUseProduct;

                                // 如果可用商品以及套餐里面的各项商品总金额>=优惠券的最低启用金额则该优惠券可用
                                if (totalPriceForProduct >= coupon.getPayLeastMoney()
                                        .doubleValue()) {
                                    // 判断优惠券有效期
                                    if (myGrant.getStartTime().before(now_date)
                                            && myGrant.getEndTime().after(now_date)) {
                                        CouponCanUse returnCan = new CouponCanUse();
                                        returnCan.setBondPrice(coupon.getCouponMoney());
                                        canUseCoupon.put(couponGrantVo.getCouponGrantId(),
                                                returnCan);
                                        // 20151012 mlq add 筛选重复的couponId
                                        noRepeatCouponIdForCanUse.add(couponGrantVo.getCouponId());
                                    } else {
                                        // 20151012 mlq add 说明该优惠券不可用
                                        noRepeatCouponIdForUnUse.add(couponGrantVo.getCouponId());
                                    }
                                } else {
                                    // 20151012 mlq add 说明该优惠券不可用
                                    noRepeatCouponIdForUnUse.add(couponGrantVo.getCouponId());
                                }
                                // 20150618 add end


                                // 20150619 annotation begin
                                // if (!productMap.isEmpty() && !cateMap.isEmpty())
                                // {
                                // //覆盖键值对去重复
                                // productMap.putAll(cateMap);
                                // productMap.putAll(productMap);
                                // Double bondMoney = 0D;
                                // //遍历productMap获取符合商品的总价格
                                // for (Entry<String,BigDecimal> entry: productMap.entrySet())
                                // {
                                // BigDecimal o = entry.getValue();
                                // Double l = o.doubleValue();
                                // bondMoney += l ;
                                // }
                                // //判断符合条件商品总价格》=规则最低消费
                                // if (bondMoney >= coupon.getPayLeastMoney().doubleValue()){
                                // //判断优惠券是否在使用期限内
                                // if(myGrant.getStartTime().before(now_date)&&myGrant.getEndTime().after(now_date)){
                                // CouponCanUse returnCan = new CouponCanUse();
                                // returnCan.setBondPrice(coupon.getCouponMoney());
                                // canUseCoupon.put(couponGrantVo.getCouponGrantId(), returnCan);
                                // }
                                // }
                                // }
                                // //两个都不为空，去重，筛选出了符合优惠券规则的套餐所有产品
                                // if(!comboMap.isEmpty() && !comboCateMap.isEmpty()){//套餐判断
                                // comboCateMap.putAll(comboMap);
                                // Double bondMoney = 0D;
                                // //计算总额
                                // for (Entry<BigDecimal,BigDecimal> entry: comboMap.entrySet()){
                                // BigDecimal o = entry.getValue();
                                // bondMoney += o.doubleValue();
                                //
                                // }
                                // //判断符合条件商品总价格》=规则最低消费
                                // if(bondMoney >= coupon.getPayLeastMoney().doubleValue()){
                                // //判断优惠券有效期
                                // if(myGrant.getStartTime().before(now_date)&&myGrant.getEndTime().after(now_date)){
                                // CouponCanUse returnCan = new CouponCanUse();
                                // returnCan.setBondPrice(coupon.getCouponMoney());
                                // canUseCoupon.put(couponGrantVo.getCouponGrantId(), returnCan);
                                // }
                                // }
                                // }
                                // 20150619 annotation end
                            } else if (productRelationIdList.size() > 0
                                    && cateRelationIdList.size() == 0) {// 只存在匹配的商品

                                // 20150618 add
                                // begin(券指定A商品不可用满88可用,同时购买AB商品,但是B商品价格未达到88,应该判断所有可用商品的总额是否大于券的最低可用金额)
                                double canUseProductTotalPrice = 0;
                                // 可用单品总金额累加
                                if (!productMap.isEmpty() && productMap.size() > 0) {
                                    for (Entry<String, BigDecimal> entry : productMap.entrySet()) {
                                        BigDecimal o = entry.getValue();
                                        canUseProductTotalPrice += o.doubleValue();
                                    }
                                }

                                // 可用套餐总金额累加
                                if (!comboMap.isEmpty() && comboMap.size() > 0) {
                                    for (Entry<BigDecimal, BigDecimal> entry : comboMap
                                            .entrySet()) {
                                        BigDecimal o = entry.getValue();
                                        canUseProductTotalPrice += o.doubleValue();
                                    }
                                }

                                // 如果可用商品以及套餐里面的各项商品总金额>=优惠券的最低启用金额则该优惠券可用
                                if (canUseProductTotalPrice >= coupon.getPayLeastMoney()
                                        .doubleValue()) {
                                    // 判断优惠券有效期
                                    if (myGrant.getStartTime().before(now_date)
                                            && myGrant.getEndTime().after(now_date)) {
                                        CouponCanUse returnCan = new CouponCanUse();
                                        returnCan.setBondPrice(coupon.getCouponMoney());
                                        canUseCoupon.put(couponGrantVo.getCouponGrantId(),
                                                returnCan);
                                        // 20151012 mlq add 筛选重复的couponId
                                        noRepeatCouponIdForCanUse.add(couponGrantVo.getCouponId());
                                    } else {
                                        // 20151012 mlq add 说明该优惠券不可用
                                        noRepeatCouponIdForUnUse.add(couponGrantVo.getCouponId());
                                    }
                                } else {
                                    // 20151012 mlq add 说明该优惠券不可用
                                    noRepeatCouponIdForUnUse.add(couponGrantVo.getCouponId());
                                }
                                // 20150618 add end
                                // (券指定A商品不可用满88可用,同时购买AB商品,但是B商品价格未达到88,应该判断所有可用商品的总额是否大于券的最低可用金额)

                                // 20150619 annotation begin
                                // 单品 匹配规则白名单商品不为空
                                // if(!productMap.isEmpty()){
                                // Double bondMoney = 0D;
                                // //获取总额
                                // for (Entry<String,BigDecimal> entry: productMap.entrySet())
                                // {
                                // BigDecimal o = entry.getValue();
                                // Double l = o.doubleValue();
                                // bondMoney += l ;
                                // }
                                // //判断符合条件商品总价格》=规则最低消费
                                // if (bondMoney >= coupon.getPayLeastMoney().doubleValue()){
                                // //判断优惠券是否在使用期限内
                                // if(myGrant.getStartTime().before(now_date)&&myGrant.getEndTime().after(now_date)){
                                // CouponCanUse returnCan = new CouponCanUse();
                                // returnCan.setBondPrice(coupon.getCouponMoney());
                                // canUseCoupon.put(couponGrantVo.getCouponGrantId(), returnCan);
                                // }
                                // }
                                // }
                                // //套餐 匹配规则白名单商品 套餐不为空（套餐所有商品在名单内）
                                // if(!comboMap.isEmpty()){
                                // Double bondMoney = 0D;
                                // //获取套餐总额
                                // for (Entry<BigDecimal,BigDecimal> entry: comboMap.entrySet()){
                                // BigDecimal o = entry.getValue();
                                // bondMoney += o.doubleValue();
                                //
                                // }
                                // ////判断符合条件商品总价格》=规则最低消费
                                // if(bondMoney >= coupon.getPayLeastMoney().doubleValue()){
                                // //判断优惠券是否在使用期限内
                                // if(myGrant.getStartTime().before(now_date)&&myGrant.getEndTime().after(now_date)){
                                // CouponCanUse returnCan = new CouponCanUse();
                                // returnCan.setBondPrice(coupon.getCouponMoney());
                                // canUseCoupon.put(couponGrantVo.getCouponGrantId(), returnCan);
                                // }
                                // }
                                // }
                                // 20150619 annotation end
                            } else if (productRelationIdList.size() == 0
                                    && cateRelationIdList.size() > 0) {

                                double canUseProductTotalPrice = 0;
                                // 20150618 add begin
                                // (券指定A商品不可用满88可用,同时购买AB商品,但是B商品价格未达到88,应该判断所有可用商品的总额是否大于券的最低可用金额)
                                // 可用单品的总金额
                                if (!cateMap.isEmpty() && cateMap.size() > 0) {
                                    for (Entry<String, BigDecimal> entry : cateMap.entrySet()) {
                                        BigDecimal o = entry.getValue();
                                        canUseProductTotalPrice += o.doubleValue();
                                    }
                                }

                                // 可用套餐的总金额
                                if (!comboCateMap.isEmpty() && comboCateMap.size() > 0) {
                                    for (Entry<BigDecimal, BigDecimal> entry : comboCateMap
                                            .entrySet()) {
                                        BigDecimal o = entry.getValue();
                                        canUseProductTotalPrice += o.doubleValue();
                                    }
                                }
                                // 如果可用商品里面的各项商品总金额>=优惠券的最低启用金额则该优惠券可用
                                if (canUseProductTotalPrice >= coupon.getPayLeastMoney()
                                        .doubleValue()) {
                                    // 判断优惠券有效期
                                    if (myGrant.getStartTime().before(now_date)
                                            && myGrant.getEndTime().after(now_date)) {
                                        CouponCanUse returnCan = new CouponCanUse();
                                        returnCan.setBondPrice(coupon.getCouponMoney());
                                        canUseCoupon.put(couponGrantVo.getCouponGrantId(),
                                                returnCan);
                                        // 20151012 mlq add 筛选重复的couponId
                                        noRepeatCouponIdForCanUse.add(couponGrantVo.getCouponId());
                                    } else {
                                        // 20151012 mlq add 说明该优惠券不可用
                                        noRepeatCouponIdForUnUse.add(couponGrantVo.getCouponId());
                                    }
                                } else {
                                    // 20151012 mlq add 说明该优惠券不可用
                                    noRepeatCouponIdForUnUse.add(couponGrantVo.getCouponId());
                                }
                                // 20150618 add end
                                // (券指定A商品不可用满88可用,同时购买AB商品,但是B商品价格未达到88,应该判断所有可用商品的总额是否大于券的最低可用金额)


                                // 20150619 annotation begin
                                // 单品 匹配规则白名单类目不为空
                                // if(!cateMap.isEmpty()){
                                // productMap.putAll(cateMap);
                                // Double bondMoney = 0D;
                                // //获取总额
                                // for (Entry<String,BigDecimal> entry: productMap.entrySet())
                                // {
                                // BigDecimal o = entry.getValue();
                                // Double l = o.doubleValue();
                                // bondMoney += l ;
                                // }
                                // //判断总额》=规则设定最小额度
                                // if (bondMoney>= coupon.getPayLeastMoney().doubleValue()){
                                // //判断优惠券是否在使用期限内
                                // if(myGrant.getStartTime().before(now_date)&&myGrant.getEndTime().after(now_date)){
                                // CouponCanUse returnCan = new CouponCanUse();
                                // returnCan.setBondPrice(coupon.getCouponMoney());
                                // canUseCoupon.put(couponGrantVo.getCouponGrantId(), returnCan);
                                // }
                                // }
                                // }
                                // 套餐 匹配规则白名单类目不为空
                                // if(!comboCateMap.isEmpty()){
                                // Double bondMoney = 0D;
                                // //获取套餐总额
                                // for (Entry<BigDecimal,BigDecimal> entry:
                                // comboCateMap.entrySet()){
                                // BigDecimal o = entry.getValue();
                                // bondMoney += o.doubleValue();
                                //
                                // }
                                // //判断套餐所有商品总额》=规则最低额度
                                // if(bondMoney >= coupon.getPayLeastMoney().doubleValue()){
                                // //判断优惠券是否在有效期内
                                // if(myGrant.getStartTime().before(now_date)&&myGrant.getEndTime().after(now_date)){
                                // CouponCanUse returnCan = new CouponCanUse();
                                // returnCan.setBondPrice(coupon.getCouponMoney());
                                // canUseCoupon.put(couponGrantVo.getCouponGrantId(), returnCan);
                                // }
                                // }
                                // }
                                // 20150619 annotation end
                            }

                        }
                    } else if (coupon.getRangType() == 2) {// 黑名单
                        if (coupon.getSupplierType() == 2) {// 自营代销
                            Boolean bool = false;
                            for (OrderVo order : orderList) {
                                // 获取商品
                                Product pro = couponGrantSetDAO
                                        .selectProductBySKU(order.getProductSkuCode());
                                if (!pro.getShopCode().equals("221")) {// 代销,入驻
                                    // 查询供应商
                                    SuppliersInfo sinfo = suppliersInfoDAO
                                            .selectByPrimaryKey(new Long(pro.getShopCode()));
                                    if (sinfo.getSupplierType() != 3) {// 入驻(供应商类型 1.自营 2.入驻 3.代销
                                                                       // 4.康美时代)
                                        bool = true;
                                        break;
                                    }
                                }
                            }
                            if (bool) {
                                // 20151012 mlq add 说明该优惠券不可用
                                noRepeatCouponIdForUnUse.add(couponGrantVo.getCouponId());
                                continue;
                            }
                        } else if (coupon.getSupplierType() == 3) {// 入驻商家
                            Boolean bool = false;
                            for (OrderVo order : orderList) {
                                Product pro = couponGrantSetDAO
                                        .selectProductBySKU(order.getProductSkuCode());
                                if (pro.getShopCode().equals("221")) {// 自营商品
                                    bool = true;
                                    break;

                                } else {
                                    SuppliersInfo sinfo = suppliersInfoDAO
                                            .selectByPrimaryKey(new Long(pro.getShopCode()));
                                    if (sinfo.getSupplierType() == 3) {// 代销
                                        bool = true;
                                        break;
                                    }


                                }
                            }
                            if (bool) {
                                // 20151012 mlq add 说明该优惠券不可用
                                noRepeatCouponIdForUnUse.add(couponGrantVo.getCouponId());
                                continue;
                            }
                        }
                        // 规则限定范围为黑名单
                        // maliqun add begin 20150608 将优惠券指定的shopCode放入map中
                        Map<String, String> shopCodeForCouponSingle = new HashMap<String, String>();
                        // 分隔规则所对应的指定入驻商家
                        if (coupon.getShopCode() != null && !coupon.getShopCode().isEmpty()) {
                            StringTokenizer st = new StringTokenizer(coupon.getShopCode(), ",");
                            while (st.hasMoreTokens()) {
                                String temp = st.nextToken();
                                shopCodeForCouponSingle.put(temp, temp);
                            }
                        }

                        // maliqun add begin 20150608 首先判断是不是规则所指定的入驻商家是不是当前商品所属的商家
                        if (!shopCodeForCouponSingle.isEmpty() && !shopCodeForCouponSingle
                                .containsKey(String.valueOf(supplierId))) {

                            // 20151012 mlq add 说明该优惠券不可用
                            noRepeatCouponIdForUnUse.add(couponGrantVo.getCouponId());
                            continue;
                        }
                        // maliqun add end 20150608 首先判断是不是规则所指定的入驻商家是不是当前商品所属的商家

                        // 匹配获取黑名单下的产品(20150618 优惠券指定不可用的商品)
                        List<String> productRelationIdList =
                                couponProductdao.selectRelationByCouponId(myGrant.getCouponId());
                        // 匹配获取黑名单下的类目(20150618 优惠券黑名单类目)
                        List<String> cateRelationIdList = couponProductdao
                                .selectCateRelationByCouponId(myGrant.getCouponId());

                        logger.info("canuseCoupon黑名单下选中的产品productRelationIdList.size="
                                + productRelationIdList.size()
                                + ",黑名单下选中的类目cateRelationIdList.size=" + cateRelationIdList
                                + ",canuseCoupon的coupon对应的shopCode=" + coupon.getShopCode());

                        // 全场通用类型优惠券 20150619 update
                        // 将coupon.getShopCode().contains(supplierId.toString())
                        // 变为shopCodeForCouponSingle的map key判断,避免425在4258(这中类似组合
                        // 比如425为传进来的商家Code,4258为优惠券指定的上架code字符串)的情况中出现
                        if ((productRelationIdList.size() == 0 && cateRelationIdList.size() == 0
                                && (coupon.getShopCode() == null
                                        || coupon.getShopCode().equals("")))
                                || ((productRelationIdList.size() == 0
                                        && cateRelationIdList.size() == 0
                                        && coupon.getShopCode() != null
                                        && !coupon.getShopCode().equals(""))
                                        && shopCodeForCouponSingle
                                                .containsKey(String.valueOf(supplierId)))) {
                            if (orderTotlePrice.compareTo(coupon.getPayLeastMoney()) == 1
                                    || orderTotlePrice.compareTo(coupon.getPayLeastMoney()) == 0) {
                                // 判断优惠券是否在使用期限内
                                if (myGrant.getStartTime().before(now_date)
                                        && myGrant.getEndTime().after(now_date)) {
                                    CouponCanUse returnCan = new CouponCanUse();
                                    returnCan.setBondPrice(coupon.getCouponMoney());
                                    canUseCoupon.put(couponGrantVo.getCouponGrantId(), returnCan);
                                    // 20151012 mlq add 筛选重复的couponId
                                    noRepeatCouponIdForCanUse.add(couponGrantVo.getCouponId());
                                } else {
                                    // 20151012 mlq add 说明该优惠券不可用
                                    noRepeatCouponIdForUnUse.add(couponGrantVo.getCouponId());
                                }
                            }
                        } // 有限定范围
                        else {
                            // 判断传过来的listSKU 获取不匹配黑名单 单品 (20150618 获取orderList中可以用券的商品)
                            Map<String, BigDecimal> productMap =
                                    this.canUseBlackCouponProduct(orderList, productRelationIdList);
                            // 判断传来的是不是不在类目里面 单品 (orderList当中可用的用券的商品)
                            Map<String, BigDecimal> cateMap = this.canUseBlackCouponCate(orderList,
                                    cateRelationIdList, coupon.getShopCode());
                            // 过滤出套餐信息（黑名单） 返回不支持黑名单的商品(map的key值是套餐的id,value是套餐的价格 20150619 update)
                            Map<BigDecimal, BigDecimal> comboMap =
                                    this.comboBlackInfo(orderList, productRelationIdList);
                            // 判断传来的不在类型里面(黑名单套餐) 放回不在黑名单的类目的套餐(map的key是套餐id,value是套餐的价格 20150619
                            // update
                            Map<BigDecimal, BigDecimal> comboCateMap = this.comboBlackCouponCate(
                                    orderList, cateRelationIdList, coupon.getShopCode());
                            logger.info("canuseCoupon 黑名单 listSku 匹配的单品productMap.size="
                                    + productMap.size() + ",listSKu 匹配的类目cateMap.size="
                                    + cateMap.size() + ",canuseCoupon的comboMap.size="
                                    + comboMap.size() + ",comboCateMap=" + comboCateMap.size());

                            // 规则黑名单选择商品和类目不为空
                            if (productRelationIdList.size() > 0 && cateRelationIdList.size() > 0) {
                                // 20150618 add
                                // (券指定A商品不可用满88可用,同时购买AB商品,但是B商品价格未达到88,应该判断所有可用商品的总额是否大于券的最低可用金额)
                                // begin
                                // 可用单品的总金额
                                double totalPriceForSingleCanUseProduct = 0;

                                // 存储符合的单品,不重复(保证类目和单品种的不重复金额叠加)
                                Map<String, BigDecimal> mapForSingleCanUseProduct =
                                        new HashMap<String, BigDecimal>();
                                // 需要去重复的, productMap当中以及 cateMap当中都有的商品不要累加计算金额
                                if (!productMap.isEmpty() && productMap.size() > 0) {
                                    mapForSingleCanUseProduct.putAll(productMap);
                                }

                                if (!cateMap.isEmpty() && cateMap.size() > 0) {
                                    mapForSingleCanUseProduct.putAll(cateMap);
                                }

                                // 累加可用单品的总金额
                                if (!mapForSingleCanUseProduct.isEmpty()
                                        && mapForSingleCanUseProduct.size() > 0) {
                                    for (Entry<String, BigDecimal> entry : mapForSingleCanUseProduct
                                            .entrySet()) {
                                        BigDecimal o = entry.getValue();
                                        totalPriceForSingleCanUseProduct += o.doubleValue();
                                    }
                                }

                                // 可用套餐中的总金额
                                double totalPriceForCombineCanUseProduct = 0;
                                // 存储符合的套餐,不重复(保持类目中的和单独套餐不要重复)
                                Map<BigDecimal, BigDecimal> mapForCombineCanUseProduct =
                                        new HashMap<BigDecimal, BigDecimal>();
                                // 需要去重复的, productMap当中以及 cateMap当中都有的商品不要累加计算金额


                                if (!comboMap.isEmpty() && comboMap.size() > 0) {
                                    mapForCombineCanUseProduct.putAll(comboMap);
                                }

                                if (!comboCateMap.isEmpty() && comboCateMap.size() > 0) {
                                    mapForCombineCanUseProduct.putAll(comboCateMap);
                                }

                                // 累加可用套餐的总金额
                                if (!mapForCombineCanUseProduct.isEmpty()
                                        && mapForCombineCanUseProduct.size() > 0) {
                                    for (Entry<BigDecimal, BigDecimal> entry : mapForCombineCanUseProduct
                                            .entrySet()) {
                                        BigDecimal o = entry.getValue();
                                        totalPriceForCombineCanUseProduct += o.doubleValue();
                                    }
                                }

                                double totalPriceForProduct = totalPriceForSingleCanUseProduct
                                        + totalPriceForCombineCanUseProduct;

                                // 如果可用商品以及套餐里面的各项商品总金额>=优惠券的最低启用金额则该优惠券可用
                                if (totalPriceForProduct >= coupon.getPayLeastMoney()
                                        .doubleValue()) {
                                    // 判断优惠券有效期
                                    if (myGrant.getStartTime().before(now_date)
                                            && myGrant.getEndTime().after(now_date)) {
                                        CouponCanUse returnCan = new CouponCanUse();
                                        returnCan.setBondPrice(coupon.getCouponMoney());
                                        canUseCoupon.put(couponGrantVo.getCouponGrantId(),
                                                returnCan);
                                        // 20151012 mlq add 筛选重复的couponId
                                        noRepeatCouponIdForCanUse.add(couponGrantVo.getCouponId());
                                    } else {
                                        // 20151012 mlq add 筛选重复的couponId
                                        noRepeatCouponIdForUnUse.add(couponGrantVo.getCouponId());
                                    }
                                } else {
                                    // 20151012 mlq add 说明该优惠券不可用
                                    noRepeatCouponIdForUnUse.add(couponGrantVo.getCouponId());
                                }
                                // 20150618 add end

                                // 20150618 annotation begin
                                // 商品黑类目黑名单下可用的单品(不是用总金额去做判断)
                                // if(!productMap.isEmpty()||!cateMap.isEmpty()){
                                // //总额》=规则最低消费金额
                                // if(orderTotlePrice.compareTo(coupon.getPayLeastMoney()) == 1||
                                // orderTotlePrice.compareTo(coupon.getPayLeastMoney())==0){
                                // //判断优惠券有效期
                                // if(myGrant.getStartTime().before(now_date)&&myGrant.getEndTime().after(now_date)){
                                // CouponCanUse returnCan = new CouponCanUse();
                                // returnCan.setBondPrice(coupon.getCouponMoney());
                                // canUseCoupon.put(couponGrantVo.getCouponGrantId(), returnCan);
                                // }
                                // }
                                // }


                                // //商品黑名单下可用的套餐
                                // if(!comboMap.isEmpty()&&!comboCateMap.isEmpty()){
                                // //合并重复
                                // comboCateMap.putAll(comboMap);
                                // Double bondMoney = 0D;
                                // //获取套餐总价
                                // for (Entry<BigDecimal,BigDecimal> entry: comboMap.entrySet()){
                                // BigDecimal o = entry.getValue();
                                // bondMoney += o.doubleValue();
                                //
                                // }
                                // //各商品总额》=规则最低额度
                                // if(bondMoney >= coupon.getPayLeastMoney().doubleValue()){
                                // //优惠券有效期
                                // if(myGrant.getStartTime().before(now_date)&&myGrant.getEndTime().after(now_date)){
                                // CouponCanUse returnCan = new CouponCanUse();
                                // returnCan.setBondPrice(coupon.getCouponMoney());
                                // canUseCoupon.put(couponGrantVo.getCouponGrantId(), returnCan);
                                // }
                                // }
                                // }
                                // 20150618 annotation end
                            } else if (productRelationIdList.size() > 0
                                    && cateRelationIdList.size() == 0) {// 规则黑名单下商品不为空
                                // 20150618 add
                                // begin(券指定A商品不可用满88可用,同时购买AB商品,但是B商品价格未达到88,应该判断所有可用商品的总额是否大于券的最低可用金额)
                                double canUseProductTotalPrice = 0;
                                // 可用单品总金额累加
                                if (!productMap.isEmpty() && productMap.size() > 0) {
                                    for (Entry<String, BigDecimal> entry : productMap.entrySet()) {
                                        BigDecimal o = entry.getValue();
                                        canUseProductTotalPrice += o.doubleValue();
                                    }
                                }

                                // 可用套餐总金额累加
                                if (!comboMap.isEmpty() && comboMap.size() > 0) {
                                    for (Entry<BigDecimal, BigDecimal> entry : comboMap
                                            .entrySet()) {
                                        BigDecimal o = entry.getValue();
                                        canUseProductTotalPrice += o.doubleValue();
                                    }
                                }

                                // 如果可用商品以及套餐里面的各项商品总金额>=优惠券的最低启用金额则该优惠券可用
                                if (canUseProductTotalPrice >= coupon.getPayLeastMoney()
                                        .doubleValue()) {
                                    // 判断优惠券有效期
                                    if (myGrant.getStartTime().before(now_date)
                                            && myGrant.getEndTime().after(now_date)) {
                                        CouponCanUse returnCan = new CouponCanUse();
                                        returnCan.setBondPrice(coupon.getCouponMoney());
                                        canUseCoupon.put(couponGrantVo.getCouponGrantId(),
                                                returnCan);
                                        // 20151012 mlq add 筛选重复的couponId
                                        noRepeatCouponIdForCanUse.add(couponGrantVo.getCouponId());
                                    } else {
                                        // 20151012 mlq add 说明该优惠券不可用
                                        noRepeatCouponIdForUnUse.add(couponGrantVo.getCouponId());
                                    }
                                } else {
                                    // 20151012 mlq add 说明该优惠券不可用
                                    noRepeatCouponIdForUnUse.add(couponGrantVo.getCouponId());
                                }
                                // 20150618 add end
                                // (券指定A商品不可用满88可用,同时购买AB商品,但是B商品价格未达到88,应该判断所有可用商品的总额是否大于券的最低可用金额)


                                // 20150618 annotation begin
                                // 黑名单下可使用优惠券的单品不为空
                                // if(!productMap.isEmpty()){
                                // if(orderTotlePrice.compareTo(coupon.getPayLeastMoney()) == 1||
                                // orderTotlePrice.compareTo(coupon.getPayLeastMoney())==0){
                                // if(myGrant.getStartTime().before(now_date)&&myGrant.getEndTime().after(now_date)){
                                // CouponCanUse returnCan = new CouponCanUse();
                                // returnCan.setBondPrice(coupon.getCouponMoney());
                                // canUseCoupon.put(couponGrantVo.getCouponGrantId(), returnCan);
                                // }
                                // }
                                // }


                                // 黑名单下 可使用优惠券的套餐不为空
                                // if(!comboMap.isEmpty()){
                                // Double bondMoney = 0D;
                                // for (Entry<BigDecimal,BigDecimal> entry: comboMap.entrySet()){
                                // BigDecimal o = entry.getValue();
                                // bondMoney += o.doubleValue();
                                //
                                // }
                                // if(bondMoney >= coupon.getPayLeastMoney().doubleValue()){
                                // if(myGrant.getStartTime().before(now_date)&&myGrant.getEndTime().after(now_date)){
                                // CouponCanUse returnCan = new CouponCanUse();
                                // returnCan.setBondPrice(coupon.getCouponMoney());
                                // canUseCoupon.put(couponGrantVo.getCouponGrantId(), returnCan);
                                // }
                                // }
                                // }
                                // 20150618 annotation end
                            } else if (productRelationIdList.size() == 0
                                    && cateRelationIdList.size() > 0) {// 规则黑名单下类目不为空
                                double canUseProductTotalPrice = 0;
                                // 20150618 add begin
                                // (券指定A商品不可用满88可用,同时购买AB商品,但是B商品价格未达到88,应该判断所有可用商品的总额是否大于券的最低可用金额)
                                // 可用单品的总金额
                                if (!cateMap.isEmpty() && cateMap.size() > 0) {
                                    for (Entry<String, BigDecimal> entry : cateMap.entrySet()) {
                                        BigDecimal o = entry.getValue();
                                        canUseProductTotalPrice += o.doubleValue();
                                    }
                                }

                                // 可用套餐的总金额
                                if (!comboCateMap.isEmpty() && comboCateMap.size() > 0) {
                                    for (Entry<BigDecimal, BigDecimal> entry : comboCateMap
                                            .entrySet()) {
                                        BigDecimal o = entry.getValue();
                                        canUseProductTotalPrice += o.doubleValue();
                                    }
                                }
                                // 如果可用商品里面的各项商品总金额>=优惠券的最低启用金额则该优惠券可用
                                if (canUseProductTotalPrice >= coupon.getPayLeastMoney()
                                        .doubleValue()) {
                                    // 判断优惠券有效期
                                    if (myGrant.getStartTime().before(now_date)
                                            && myGrant.getEndTime().after(now_date)) {
                                        CouponCanUse returnCan = new CouponCanUse();
                                        returnCan.setBondPrice(coupon.getCouponMoney());
                                        canUseCoupon.put(couponGrantVo.getCouponGrantId(),
                                                returnCan);
                                        // 20151012 mlq add 说明该优惠券不可用
                                        noRepeatCouponIdForCanUse.add(couponGrantVo.getCouponId());
                                    } else {
                                        // 20151012 mlq add 说明该优惠券不可用
                                        noRepeatCouponIdForUnUse.add(couponGrantVo.getCouponId());
                                    }
                                } else {
                                    // 20151012 mlq add 说明该优惠券不可用
                                    noRepeatCouponIdForUnUse.add(couponGrantVo.getCouponId());
                                }
                                // 20150618 add end
                                // (券指定A商品不可用满88可用,同时购买AB商品,但是B商品价格未达到88,应该判断所有可用商品的总额是否大于券的最低可用金额)


                                // 20150618 annotation begin
                                // 黑名单下可使用优惠券的类目不为空
                                // if(!cateMap.isEmpty()){
                                // if(orderTotlePrice.compareTo(coupon.getPayLeastMoney()) == 1||
                                // orderTotlePrice.compareTo(coupon.getPayLeastMoney())==0){
                                // if(myGrant.getStartTime().before(now_date)&&myGrant.getEndTime().after(now_date)){
                                // CouponCanUse returnCan = new CouponCanUse();
                                // returnCan.setBondPrice(coupon.getCouponMoney());
                                // canUseCoupon.put(couponGrantVo.getCouponGrantId(), returnCan);
                                // }
                                // }
                                // }


                                // 黑名单下 可使用优惠券的套餐不为空
                                // if(!comboCateMap.isEmpty()){
                                // Double bondMoney = 0D;
                                // for (Entry<BigDecimal,BigDecimal> entry:
                                // comboCateMap.entrySet()){
                                // BigDecimal o = entry.getValue();
                                // bondMoney += o.doubleValue();
                                //
                                // }
                                // if(bondMoney >= coupon.getPayLeastMoney().doubleValue()){
                                // if(myGrant.getStartTime().before(now_date)&&myGrant.getEndTime().after(now_date)){
                                // CouponCanUse returnCan = new CouponCanUse();
                                // returnCan.setBondPrice(coupon.getCouponMoney());
                                // canUseCoupon.put(couponGrantVo.getCouponGrantId(), returnCan);
                                // }
                                // }
                                // }
                                // 20150618 annotation end
                            }


                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("查询可以使用的优惠券报错" + e.getMessage(), e);
        }
        return canUseCoupon;
    }

    @Override
    public HashMap<String, List<HashMap<String, String>>> getCanUseAndUnUseCoupon(
            List<OrderVo> orderList, String customerId, BigDecimal orderTotlePrice, Long supplierId)
            throws Exception {
        logger.info("----------------------------canuseCoupon：优惠券查询方法--------------------");
        // 可用优惠券
        List<HashMap<String, String>> canUseCouponList = new ArrayList<HashMap<String, String>>();
        // 不可用优惠券
        List<HashMap<String, String>> unUseCouponList = new ArrayList<HashMap<String, String>>();
        // 当前用户可用、不可用优惠券返回对象,key:canUseCouponList/unUseCouponList分别表示可用、不可用优惠券
        HashMap<String, List<HashMap<String, String>>> couponResultMap =
                new HashMap<String, List<HashMap<String, String>>>();
        Date now_date = new Date();
        // 表示优惠券类型，key:couponId, value: 1：不在使用范围内,"":表示优惠券未生效或金额未达到优惠券最低消费金额
        HashMap<Long, String> couponTypeMap = new HashMap<Long, String>();
        String couponType = "";
        try {
            // 获取当前用户的优惠券
            List<CouponGrant> couponGrantList =
                    couponGrantdao.selectCouponGrantAndCoupon(Integer.parseInt(customerId));
            if (couponGrantList.size() > 0) {
                // 筛选出不重复的优惠券进行判断
                Set<Long> noRepeatCouponIdForCanUse = Sets.newHashSet();
                Set<Long> noRepeatCouponIdForUnUse = Sets.newHashSet();
                // 循环取出productSkuCode，用于批量查询商品类目信息、商家供应商信息
                List<String> productSkuCodes = new ArrayList<String>();
                for (OrderVo order : orderList) {
                    productSkuCodes.add(order.getProductSkuCode());
                }
                // 商品类目信息、商家供应商信息
                List<Product> listProduct = null;
                HashMap<String, Product> mapProduct = null;
                // 记录订单商品中的单品、套餐
                Map<String, Map<BigDecimal, List<OrderVo>>> comboAndSingleMap = null;
                List<OrderVo> orderSingleList = null;
                Map<BigDecimal, List<OrderVo>> combos = null;

                for (CouponGrant couponGrantVo : couponGrantList) {
                    // 优惠券规则
                    Coupon coupon = couponGrantVo.getCoupon().get(0);
                    // 判断优惠券是否在使用期限内
                    if (!(couponGrantVo.getStartTime().before(now_date)
                            && couponGrantVo.getEndTime().after(now_date))) {
                        // 20151012 mlq add 筛选重复的couponId
                        couponType = "";
                        unUseCouponList.add(this.putUnUseCouponInfoToMap(coupon, couponGrantVo,
                                orderTotlePrice, couponType));
                        continue;
                    }
                    // 如果已经可用,但是该优惠券再次出现
                    if (noRepeatCouponIdForCanUse.contains(coupon.getCouponId())) {
                        canUseCouponList.add(this.putCanUseCouponInfoToMap(coupon, couponGrantVo));
                        continue;
                    }
                    // 不可用优惠券再次出现，直接返回到不可用优惠券对象中
                    if (noRepeatCouponIdForUnUse.contains(coupon.getCouponId())) {
                        couponType = couponTypeMap.get(coupon.getCouponId());
                        unUseCouponList.add(this.putUnUseCouponInfoToMap(coupon, couponGrantVo,
                                orderTotlePrice, couponType));
                        continue;
                    }
                    // 优惠券规则商家类型，1：所以商家，2：康美自营，3:指定入驻商家
                    // 判断产品的商家类别是否是规则指定的商家类别
                    if (coupon.getSupplierType() == 2) {// 自营代销
                        if (!"221".equals(supplierId.toString())) {
                            // 说明该优惠券不在使用范围内，不可用,
                            couponType = "1";
                            couponTypeMap.put(coupon.getCouponId(), couponType);
                            unUseCouponList.add(this.putUnUseCouponInfoToMap(coupon, couponGrantVo,
                                    orderTotlePrice, couponType));
                            noRepeatCouponIdForUnUse.add(coupon.getCouponId());
                            continue;
                        }
                    } else if (coupon.getSupplierType() == 3) {// 入驻商家
                        String shopCode = "";
                        if (coupon.getShopCode() != null && !coupon.getShopCode().isEmpty()) {
                            // supplierId前后加上','，避免425在4258(这中类似组合
                            // 比如425为传进来的商家Code,4258为优惠券指定的shopcode字符串)的情况中出现
                            shopCode = "," + coupon.getShopCode() + ",";
                            if (shopCode.indexOf("," + supplierId.toString() + ",") == -1) {
                                // 说明该优惠券不在使用范围内，不可用
                                couponType = "1";
                                couponTypeMap.put(coupon.getCouponId(), couponType);
                                unUseCouponList.add(this.putUnUseCouponInfoToMap(coupon,
                                        couponGrantVo, orderTotlePrice, couponType));
                                noRepeatCouponIdForUnUse.add(coupon.getCouponId());
                                continue;
                            }
                        }
                    } else if (coupon.getSupplierType() != 1) {
                        // 商家类别不是所以商家， 说明该优惠券不在使用范围内，不可用
                        couponType = "1";
                        couponTypeMap.put(coupon.getCouponId(), couponType);
                        unUseCouponList.add(this.putUnUseCouponInfoToMap(coupon, couponGrantVo,
                                orderTotlePrice, couponType));
                        noRepeatCouponIdForUnUse.add(coupon.getCouponId());
                        continue;
                    }
                    // 获取优惠券规则下的产品、类目信息
                    HashMap<String, List<String>> mapCouponProCate =
                            this.getCouponProductAndCate(coupon.getCouponId());
                    // 获取优惠券规则下选中的产品
                    List<String> productRelationIdList =
                            mapCouponProCate.get("productRelationIdList");
                    // 获取优惠券规则下选中的类目
                    List<String> cateRelationIdList = mapCouponProCate.get("cateRelationIdList");
                    logger.info(
                            "canuseCoupon白名单下选中的产品productRelationIdList.size={},白名单下选中的类目cateRelationIdList.size={},canuseCoupon的coupon对应的shopCode={}",
                            productRelationIdList.size(), cateRelationIdList.size(),
                            coupon.getShopCode());

                    // 全场通用类型优惠券 20150608 update
                    if (productRelationIdList.size() == 0 && cateRelationIdList.size() == 0) {
                        // 订当总金额大于等于规则最低消费金额
                        if (orderTotlePrice.compareTo(coupon.getPayLeastMoney()) >= 0) {
                            canUseCouponList
                                    .add(this.putCanUseCouponInfoToMap(coupon, couponGrantVo));
                            // 20151012 mlq add 筛选重复的couponId
                            noRepeatCouponIdForCanUse.add(coupon.getCouponId());
                        } else {
                            // 20151012 mlq add 商品总额未达到优惠券消费金额,该优惠券不可用
                            couponType = "";
                            couponTypeMap.put(coupon.getCouponId(), couponType);
                            unUseCouponList.add(this.putUnUseCouponInfoToMap(coupon, couponGrantVo,
                                    orderTotlePrice, couponType));
                            noRepeatCouponIdForUnUse.add(coupon.getCouponId());
                        }
                    } // 有限定范围
                    else {
                        Map<String, BigDecimal> productMap = null;
                        Map<String, BigDecimal> cateMap = null;
                        Map<BigDecimal, BigDecimal> comboMap = null;
                        Map<BigDecimal, BigDecimal> comboCateMap = null;
                        // 批量查询商品类目信息、商家供应商信息信息
                        if (listProduct == null) {
                            listProduct = couponGrantSetDAO
                                    .selectProductAndSuppliersBySKU(productSkuCodes);
                            // 将product封装到map中
                            mapProduct = new HashMap<String, Product>();
                            for (Product product : listProduct) {
                                mapProduct.put(product.getProductSkus().get(0).getProductSkuCode(),
                                        product);
                            }
                        }
                        if (comboAndSingleMap == null) {
                            comboAndSingleMap = this.getComboInfoAndSingleList(orderList);
                            // 获取商品中的单品
                            orderSingleList = comboAndSingleMap.get("single").get(BigDecimal.ZERO);
                            // 过滤出商品中为套餐的商品
                            combos = comboAndSingleMap.get("combo");
                        }
                        // 规则使用范围为白名单
                        if (coupon.getRangType() == 1) {
                            // 判断传过来的listSKU 是否能够匹配单品
                            // (orderSingleList中在白名单范围内的单品map集合)
                            productMap = this.canUseCouponProduct(orderSingleList,
                                    productRelationIdList);
                            // 判断传来的是不是在类目里面 单品
                            cateMap = this.canUseCouponCates(orderSingleList, cateRelationIdList,
                                    mapProduct);
                            // 过滤出套餐信息（白名单） 返回支持白名单的商品 (20150619 add key是套餐id,value是套餐总金额 )
                            comboMap = this.canUseCouponComboInfo(combos, productRelationIdList);
                            // 判断传来的是不是在类型里面(白名单套餐) 放回支持白名单的类目的套餐 (20150619 add key是套餐id,value是套餐总金额
                            // )
                            comboCateMap =
                                    this.comboCouponCates(combos, cateRelationIdList, mapProduct);

                            logger.info(
                                    "canuseCoupon 白名单 listSku 匹配的单品productMap.size={},listSKu 匹配的类目cateMap.size={},canuseCoupon的comboMap.size={},comboCateMap={}",
                                    productMap.size(), cateMap.size(), comboMap.size(),
                                    comboCateMap.size());
                        } else if (coupon.getRangType() == 2) {
                            // 规则使用范围为黑名单
                            if (productRelationIdList.size() > 0 && cateRelationIdList.size() > 0) {
                                // 既存在类目范围，有存在商品范围，黑名单规则下，取黑名单两者交集之外的商品
                                // orderSingleList中在黑名单范围内不可用的单品map集合
                                productMap = this.canUseCouponProduct(orderSingleList,
                                        productRelationIdList);
                                // 返回黑名单下在类目范围内的不可用商品，单品
                                cateMap = this.canUseCouponCates(orderSingleList,
                                        cateRelationIdList, mapProduct);
                                // 过滤出套餐信息， 返回黑名单内不可用的套餐商品 (20150619 add key是套餐id,value是套餐总金额 )
                                comboMap =
                                        this.canUnUseCouponComboInfo(combos, productRelationIdList);
                                // 返回黑名单类目范围内不可用的套餐 (20150619 add key是套餐id,value是套餐总金额 )
                                comboCateMap = this.comboUnUseCouponCates(combos,
                                        cateRelationIdList, mapProduct);
                                logger.info(
                                        "canuseCoupon 黑名单 listSku 不可以用的单品productMap.size={},listSKu 不可用的的类目cateMap.size={},canuseCoupon的comboMap.size={},comboCateMap={}",
                                        productMap.size(), cateMap.size(), comboMap.size(),
                                        comboCateMap.size());
                            } else {
                                // 判断传过来的listSKU 获取不匹配黑名单 单品 (20150618
                                // 获取orderSingleList中可以用券的商品)
                                productMap = this.canUseBlackCouponProduct(orderSingleList,
                                        productRelationIdList);
                                // 判断传来的是不是不在类目里面 单品 (返回orderSingleList当中可用的用券的商品)
                                cateMap = this.canUseBlackCouponCate(orderSingleList,
                                        cateRelationIdList, mapProduct);
                                // 过滤出套餐信息（黑名单）
                                // 返回不在黑名单范围内的商品(map的key值是套餐的id,value是套餐的价格 20150619
                                // update)
                                comboMap = this.canUseCouponComboBlackInfo(combos,
                                        productRelationIdList);
                                // 判断传来的不在类型里面(黑名单套餐)
                                // 返回不在黑名单的类目的套餐(map的key是套餐id,value是套餐的价格 20150619
                                // update
                                comboCateMap = this.comboBlackCouponCates(combos,
                                        cateRelationIdList, mapProduct);
                                logger.info(
                                        "canuseCoupon 黑名单 listSku 可用的单品productMap.size={},listSKu 可用的类目cateMap.size={},canuseCoupon的comboMap.size={},comboCateMap={}",
                                        productMap.size(), cateMap.size(), comboMap.size(),
                                        comboCateMap.size());
                            }
                        }
                        // 商品总金额
                        double totalPriceForProduct = 0;
                        // 如果存在类目范围和产品范围，则取符合优惠券规则的产品的交集
                        if (productRelationIdList.size() > 0 && cateRelationIdList.size() > 0) {
                            // 可用单品的总金额
                            double totalPriceForSingleCanUseProduct = 0;
                            // 商品为单品,取类目和产品的交集
                            Map<String, BigDecimal> canUseSingleOrderMap =
                                    new HashMap<String, BigDecimal>();

                            if (MapUtils.isNotEmpty(productMap)) {
                                Iterator<String> singleProduct = productMap.keySet().iterator();
                                for (; singleProduct.hasNext();) {
                                    String productSkuCode = singleProduct.next();
                                    if (cateMap.get(productSkuCode) != null) {
                                        canUseSingleOrderMap.put(productSkuCode,
                                                productMap.get(productSkuCode));
                                        totalPriceForSingleCanUseProduct +=
                                                productMap.get(productSkuCode).doubleValue();
                                    }
                                }
                            }

                            // 商品为套餐，取类目和产品的交集
                            // 可用套餐中的总金额
                            double totalPriceForCombineCanUseProduct = 0;
                            Map<BigDecimal, BigDecimal> canUseComboOrderMap =
                                    new HashMap<BigDecimal, BigDecimal>();
                            Iterator<BigDecimal> comboProduct = comboMap.keySet().iterator();
                            for (; comboProduct.hasNext();) {
                                BigDecimal productSkuCode = comboProduct.next();
                                if (comboCateMap.get(productSkuCode) != null) {
                                    canUseComboOrderMap.put(productSkuCode,
                                            comboMap.get(productSkuCode));
                                    totalPriceForCombineCanUseProduct +=
                                            comboMap.get(productSkuCode).doubleValue();
                                }
                            }
                            // 黑名单范围
                            if (coupon.getRangType() == 2) {
                                // 获取可用的商品的总金额，从传入的orderList商品中去掉黑名单中交集的商品
                                totalPriceForSingleCanUseProduct = 0;
                                totalPriceForCombineCanUseProduct = 0;
                                // 单品
                                for (OrderVo ordervo : orderSingleList) {
                                    if (!canUseSingleOrderMap
                                            .containsKey(ordervo.getProductSkuCode())) {
                                        totalPriceForSingleCanUseProduct +=
                                                ordervo.getProductTotlePrice().doubleValue();
                                    }
                                }
                                // 套餐商品
                                Iterator<BigDecimal> it = combos.keySet().iterator();
                                for (; it.hasNext();) {
                                    BigDecimal bigid = it.next();
                                    if (!canUseComboOrderMap.containsKey(bigid)) {
                                        totalPriceForCombineCanUseProduct += combos.get(bigid)
                                                .get(0).getRelationPrice().doubleValue();
                                    }
                                }
                            }
                            // 商品总金额
                            totalPriceForProduct = totalPriceForSingleCanUseProduct
                                    + totalPriceForCombineCanUseProduct;
                        } else {
                            // 只存在类目范围或者商品范围
                            // (券指定A商品不可用满88可用,同时购买AB商品,但是B商品价格未达到88,应该判断所有可用商品的总额是否大于券的最低可用金额)
                            // 商品总金额
                            totalPriceForProduct = 0;
                            if (!productMap.isEmpty() && productMap.size() > 0) {
                                for (Entry<String, BigDecimal> entry : productMap.entrySet()) {
                                    BigDecimal o = entry.getValue();
                                    totalPriceForProduct += o.doubleValue();
                                }
                            }
                            if (MapUtils.isNotEmpty(cateMap)) {
                                for (Entry<String, BigDecimal> entry : cateMap.entrySet()) {
                                    BigDecimal o = entry.getValue();
                                    totalPriceForProduct += o.doubleValue();
                                }
                            }
                            if (MapUtils.isNotEmpty(comboMap)) {
                                for (Entry<BigDecimal, BigDecimal> entry : comboMap.entrySet()) {
                                    BigDecimal o = entry.getValue();
                                    totalPriceForProduct += o.doubleValue();
                                }
                            }
                            if (!comboCateMap.isEmpty() && comboCateMap.size() > 0) {
                                for (Entry<BigDecimal, BigDecimal> entry : comboCateMap
                                        .entrySet()) {
                                    BigDecimal o = entry.getValue();
                                    totalPriceForProduct += o.doubleValue();
                                }
                            }
                        }
                        // 如果可用商品以及套餐里面的各项商品总金额>=优惠券的最低使用金额则该优惠券可用
                        if (totalPriceForProduct == 0) {
                            // 说明该优惠券未在使用范围内，不可用
                            couponType = "1";
                            couponTypeMap.put(coupon.getCouponId(), couponType);
                            unUseCouponList.add(this.putUnUseCouponInfoToMap(coupon, couponGrantVo,
                                    orderTotlePrice, couponType));
                            noRepeatCouponIdForUnUse.add(coupon.getCouponId());
                        } else if (totalPriceForProduct >= coupon.getPayLeastMoney()
                                .doubleValue()) {
                            canUseCouponList
                                    .add(this.putCanUseCouponInfoToMap(coupon, couponGrantVo));
                            // 20151012 mlq add 筛选重复的couponId
                            noRepeatCouponIdForCanUse.add(coupon.getCouponId());
                        } else {
                            // 商品总额未达到优惠券消费金额,该优惠券不可用
                            couponType = "";
                            couponTypeMap.put(coupon.getCouponId(), couponType);
                            unUseCouponList.add(this.putUnUseCouponInfoToMap(coupon, couponGrantVo,
                                    orderTotlePrice, couponType));
                            noRepeatCouponIdForUnUse.add(coupon.getCouponId());
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("查询可以使用的优惠券报错" + e.getMessage(), e);
        }
        couponResultMap.put("canUseCouponList", canUseCouponList);
        couponResultMap.put("unUseCouponList", unUseCouponList);
        return couponResultMap;
    }

    /**
     * 优惠券规则下的产品、类目信息
     * 
     * @param couponId 优惠券规则id
     * @return
     * @throws SQLException
     */
    public HashMap<String, List<String>> getCouponProductAndCate(Long couponId)
            throws SQLException {
        HashMap<String, List<String>> mapProCate = (HashMap<String, List<String>>) memCachedClient
                .get(Constants.COUPON_PRODUCT_CATE_CACHED.concat(couponId.toString()));
        if (mapProCate == null) {
            // 获取优惠券规则下选择的产品
            List<String> productRelationIdList =
                    couponProductdao.selectRelationByCouponId(couponId);
            // 获取优惠券规则下选中的类目
            List<String> cateRelationIdList =
                    couponProductdao.selectCateRelationByCouponId(couponId);
            mapProCate = new HashMap<String, List<String>>();
            mapProCate.put("productRelationIdList", productRelationIdList);
            mapProCate.put("cateRelationIdList", cateRelationIdList);
            // Date expDate =
            // new Date(new Date().getTime()
            // + Long.valueOf(String.valueOf(COUPON_PRODUCT_RATE_TIME_OUT)));
            Date expDate = new Date(COUPON_PRODUCT_RATE_TIME_OUT);
            memCachedClient.add(Constants.COUPON_PRODUCT_CATE_CACHED.concat(couponId.toString()),
                    mapProCate, expDate);
        }
        return mapProCate;
    }

    /**
     * 获取不可用优惠券
     */
    @Override
    public List<HashMap<String, String>> getUnUseCoupon(List<OrderVo> orderList, String customerId,
            BigDecimal orderTotlePrice, Long supplierId) {
        List<HashMap<String, String>> reList = Lists.newArrayList();
        try {
            // 状态为 3 未使用的 优惠券需判断范围，取出无法使用的优惠券
            List<CouponGrant> availableCouponList =
                    couponGrantdao.selectCouponGrant(Integer.parseInt(customerId));

            // 20151012 mlq add begin 筛选出重复的couponId
            List<Long> noRepeatCouponIdForCanUse = new ArrayList<Long>(); // 可用的
            List<Long> noRepeatCouponIdForUnUse = new ArrayList<Long>(); // 不可用的

            for (CouponGrant couponGrantVo : availableCouponList) {

                // 照样让它显示
                if (noRepeatCouponIdForUnUse.indexOf(couponGrantVo.getCouponId()) >= 0) {
                    CouponGrant myGrant = new CouponGrant();
                    myGrant.setCouponGrantId(couponGrantVo.getCouponGrantId());
                    myGrant = couponGrantdao.selectGrantRecord(myGrant);
                    // 获取对应的优惠券规则
                    Coupon coupon = coupondao.selectCouponByPrimaryKey(myGrant.getCouponId());
                    reList.add(this.putCouponInfoToMap(coupon, myGrant, orderTotlePrice));
                }


                // 20151012 mlq add 控制可用和不可用重复couponId判断
                if (noRepeatCouponIdForCanUse.indexOf(couponGrantVo.getCouponId()) >= 0
                        || noRepeatCouponIdForUnUse.indexOf(couponGrantVo.getCouponId()) >= 0) {
                    continue;
                }
                CouponGrant myGrant = new CouponGrant();
                myGrant.setCouponGrantId(couponGrantVo.getCouponGrantId());
                myGrant = couponGrantdao.selectGrantRecord(myGrant);
                // 获取对应的优惠券规则
                Coupon coupon = coupondao.selectCouponByPrimaryKey(myGrant.getCouponId());
                short rangType = coupon.getRangType();
                Date now_date = new Date();
                if (rangType == 1) {
                    // 判断自营和入驻
                    if (coupon.getSupplierType() == 2) {// 自营代销
                        Boolean bool = false;
                        for (OrderVo order : orderList) {
                            Product pro =
                                    couponGrantSetDAO.selectProductBySKU(order.getProductSkuCode());
                            if (!pro.getShopCode().equals("221")) {// 自营商品
                                SuppliersInfo sinfo = suppliersInfoDAO
                                        .selectByPrimaryKey(new Long(pro.getShopCode()));
                                if (sinfo.getSupplierType() != 1 && sinfo.getSupplierType() != 3) {// 代销
                                    bool = true;
                                    // 20150619 update 将添加到不可用优惠券的信息封装成一个方法调用
                                    reList.add(this.putCouponInfoToMap(coupon, myGrant,
                                            orderTotlePrice));
                                    break;
                                }
                            }

                        }
                        if (bool) {
                            // 20151012 mlq add 筛选重复的couponId
                            noRepeatCouponIdForUnUse.add(couponGrantVo.getCouponId());
                            continue;
                        } else {
                            // 20151012 mlq add 筛选重复的couponId
                            noRepeatCouponIdForCanUse.add(couponGrantVo.getCouponId());
                        }
                    } else if (coupon.getSupplierType() == 3) {// 入驻商家
                        Boolean bool = false;
                        for (OrderVo order : orderList) {
                            Product pro =
                                    couponGrantSetDAO.selectProductBySKU(order.getProductSkuCode());
                            if (pro.getShopCode().equals("221")) {// 自营商品
                                bool = true;
                                // 20150619 update 将添加到不可用优惠券的信息封装成一个方法调用
                                reList.add(
                                        this.putCouponInfoToMap(coupon, myGrant, orderTotlePrice));
                                break;
                            } else {
                                SuppliersInfo sinfo = suppliersInfoDAO
                                        .selectByPrimaryKey(new Long(pro.getShopCode()));
                                if (sinfo.getSupplierType() == 3) {// 代销
                                    bool = true;
                                    // 20150619 update 将添加到不可用优惠券的信息封装成一个方法调用
                                    reList.add(this.putCouponInfoToMap(coupon, myGrant,
                                            orderTotlePrice));
                                    break;
                                }
                            }

                        }
                        if (bool) {
                            // 20151012 mlq add 筛选重复的couponId
                            noRepeatCouponIdForUnUse.add(couponGrantVo.getCouponId());
                            continue;
                        }
                    }
                    List<String> productRelationIdList =
                            couponProductdao.selectRelationByCouponId(coupon.getCouponId());
                    List<String> cateRelationIdList =
                            couponProductdao.selectCateRelationByCouponId(coupon.getCouponId());

                    String suppliers =
                            coupondao.selectByPrimaryKey(coupon.getCouponId()).getShopCode();
                    // 20150608 maliqun add begin 当规则的商家不是等于当前传入过来的商家时
                    Map<String, String> shopCodeForCouponSingle = new HashMap<String, String>();
                    // 分隔规则所对应的指定入驻商家
                    if (suppliers != null && !suppliers.isEmpty()) {
                        StringTokenizer st = new StringTokenizer(suppliers, ",");
                        while (st.hasMoreTokens()) {
                            String temp = st.nextToken();
                            shopCodeForCouponSingle.put(temp, temp);
                        }
                    }

                    // maliqun add begin 20150608 首先判断是不是规则所指定的入驻商家是不是当前商品所属的商家
                    if ((!shopCodeForCouponSingle.isEmpty()
                            && !shopCodeForCouponSingle.containsKey(String.valueOf(supplierId)))
                            || (now_date.after(myGrant.getEndTime())
                                    || now_date.before(myGrant.getStartTime()))) {
                        reList.add(this.putCouponInfoToMap(coupon, myGrant, orderTotlePrice));
                        // 20151012 mlq add 筛选重复的couponId
                        noRepeatCouponIdForUnUse.add(couponGrantVo.getCouponId());
                        continue;
                    }
                    // maliqun add end 20150608 首先判断是不是规则所指定的入驻商家是不是当前商品所属的商家



                    // 全场通用类型优惠券 update
                    // 将suppliers.contains(supplierId.toString()))该条件改为从map当中获取,因为如果商家shopCode是4258,而传入进来的supplierId恰好是425,该组合中的一种情况,则判断必定会有误
                    if ((productRelationIdList.size() == 0 && cateRelationIdList.size() == 0
                            && (suppliers == null || suppliers.equals("")))
                            || ((productRelationIdList.size() == 0 && cateRelationIdList.size() == 0
                                    && suppliers != null && !suppliers.equals(""))
                                    && shopCodeForCouponSingle
                                            .containsKey(supplierId.toString()))) {
                        if (orderTotlePrice.compareTo(coupon.getPayLeastMoney()) == -1
                                || (now_date.after(myGrant.getEndTime())
                                        || now_date.before(myGrant.getStartTime()))) {
                            // 20150619 update 将添加到不可用优惠券的信息封装成一个方法调用
                            reList.add(this.putCouponInfoToMap(coupon, myGrant, orderTotlePrice));
                            // 20151012 mlq add 筛选重复的couponId
                            noRepeatCouponIdForUnUse.add(couponGrantVo.getCouponId());
                        } else {
                            // 20151012 mlq add 筛选重复的couponId
                            noRepeatCouponIdForCanUse.add(couponGrantVo.getCouponId());
                        }
                    } else {// 有限定范围
                        Map<String, BigDecimal> productMap =
                                this.canUseCouponProduct(orderList, productRelationIdList);
                        Map<String, BigDecimal> cateMap = this.canUseCouponCate(orderList,
                                cateRelationIdList, coupon.getShopCode());
                        Map<BigDecimal, BigDecimal> comboMap =
                                this.comboInfo(orderList, productRelationIdList);
                        Map<BigDecimal, BigDecimal> comboCateMap = this.comboCouponCate(orderList,
                                cateRelationIdList, coupon.getShopCode());


                        // 自己改动逻辑 begin 20150619
                        // 优惠券指定的类目和单品都不为空
                        if (productRelationIdList.size() > 0 && cateRelationIdList.size() > 0) {

                            if ((productMap.isEmpty() && comboMap.isEmpty() && cateMap.isEmpty()
                                    && comboCateMap.isEmpty())
                                    || (now_date.after(myGrant.getEndTime())
                                            || now_date.before(myGrant.getStartTime()))) {
                                reList.add(
                                        this.putCouponInfoToMap(coupon, myGrant, orderTotlePrice));
                                // 20151012 mlq add 筛选重复的couponId
                                noRepeatCouponIdForUnUse.add(couponGrantVo.getCouponId());
                            } else {
                                // 判断金额

                                // 20150618 add
                                // (券指定A商品不可用满88可用,同时购买AB商品,但是B商品价格未达到88,应该判断所有可用商品的总额是否大于券的最低可用金额)
                                // begin
                                // 可用单品的总金额
                                double totalPriceForSingleCanUseProduct = 0;

                                // 存储符合的单品,不重复(保证类目和单品种的不重复金额叠加)
                                Map<String, BigDecimal> mapForSingleCanUseProduct =
                                        new HashMap<String, BigDecimal>();
                                // 需要去重复的, productMap当中以及 cateMap当中都有的商品不要累加计算金额
                                if (!productMap.isEmpty() && productMap.size() > 0) {
                                    mapForSingleCanUseProduct.putAll(productMap);
                                }

                                if (!cateMap.isEmpty() && cateMap.size() > 0) {
                                    mapForSingleCanUseProduct.putAll(cateMap);
                                }

                                // 累加可用单品的总金额
                                if (!mapForSingleCanUseProduct.isEmpty()
                                        && mapForSingleCanUseProduct.size() > 0) {
                                    for (Entry<String, BigDecimal> entry : mapForSingleCanUseProduct
                                            .entrySet()) {
                                        BigDecimal o = entry.getValue();
                                        totalPriceForSingleCanUseProduct += o.doubleValue();
                                    }
                                }

                                // 可用套餐中的总金额
                                double totalPriceForCombineCanUseProduct = 0;
                                // 存储符合的套餐,不重复(保持类目中的和单独套餐不要重复)
                                Map<BigDecimal, BigDecimal> mapForCombineCanUseProduct =
                                        new HashMap<BigDecimal, BigDecimal>();
                                // 需要去重复的, productMap当中以及 cateMap当中都有的商品不要累加计算金额


                                if (!comboMap.isEmpty() && comboMap.size() > 0) {
                                    mapForCombineCanUseProduct.putAll(comboMap);
                                }

                                if (!comboCateMap.isEmpty() && comboCateMap.size() > 0) {
                                    mapForCombineCanUseProduct.putAll(comboCateMap);
                                }

                                // 累加可用套餐的总金额
                                if (!mapForCombineCanUseProduct.isEmpty()
                                        && mapForCombineCanUseProduct.size() > 0) {
                                    for (Entry<BigDecimal, BigDecimal> entry : mapForCombineCanUseProduct
                                            .entrySet()) {
                                        BigDecimal o = entry.getValue();
                                        totalPriceForCombineCanUseProduct += o.doubleValue();
                                    }
                                }

                                double totalPriceForProduct = totalPriceForSingleCanUseProduct
                                        + totalPriceForCombineCanUseProduct;

                                // 如果可用商品以及套餐里面的各项商品总金额>=优惠券的最低启用金额则该优惠券可用
                                if (totalPriceForProduct < coupon.getPayLeastMoney().doubleValue()
                                        || (now_date.after(myGrant.getEndTime())
                                                || now_date.before(myGrant.getStartTime()))) {
                                    // 20150619 update 将添加到不可用优惠券的信息封装成一个方法调用
                                    reList.add(this.putCouponInfoToMap(coupon, myGrant,
                                            orderTotlePrice));
                                    // 20151012 mlq add 筛选重复的couponId
                                    noRepeatCouponIdForUnUse.add(couponGrantVo.getCouponId());
                                } else {
                                    // 20151012 mlq add 筛选重复的couponId
                                    noRepeatCouponIdForCanUse.add(couponGrantVo.getCouponId());
                                }
                                // 20150618 add end

                            }
                        } else if (productRelationIdList.size() > 0
                                && cateRelationIdList.size() == 0) {

                            if ((productMap.isEmpty() && comboMap.isEmpty()
                                    || (now_date.after(myGrant.getEndTime())
                                            || now_date.before(myGrant.getStartTime())))) {
                                reList.add(
                                        this.putCouponInfoToMap(coupon, myGrant, orderTotlePrice));
                                // 20151012 mlq add 筛选重复的couponId
                                noRepeatCouponIdForUnUse.add(couponGrantVo.getCouponId());
                            } else {
                                // 20150618 add
                                // begin(券指定A商品不可用满88可用,同时购买AB商品,但是B商品价格未达到88,应该判断所有可用商品的总额是否大于券的最低可用金额)
                                double canUseProductTotalPrice = 0;
                                // 可用单品总金额累加
                                if (!productMap.isEmpty() && productMap.size() > 0) {
                                    for (Entry<String, BigDecimal> entry : productMap.entrySet()) {
                                        BigDecimal o = entry.getValue();
                                        canUseProductTotalPrice += o.doubleValue();
                                    }
                                }

                                // 可用套餐总金额累加
                                if (!comboMap.isEmpty() && comboMap.size() > 0) {
                                    for (Entry<BigDecimal, BigDecimal> entry : comboMap
                                            .entrySet()) {
                                        BigDecimal o = entry.getValue();
                                        canUseProductTotalPrice += o.doubleValue();
                                    }
                                }

                                // 如果可用商品以及套餐里面的各项商品总金额>=优惠券的最低启用金额则该优惠券可用
                                if (canUseProductTotalPrice < coupon.getPayLeastMoney()
                                        .doubleValue()
                                        || (now_date.after(myGrant.getEndTime())
                                                || now_date.before(myGrant.getStartTime()))) {
                                    // 20150619 update 将添加到不可用优惠券的信息封装成一个方法调用
                                    reList.add(this.putCouponInfoToMap(coupon, myGrant,
                                            orderTotlePrice));
                                    // 20151012 mlq add 筛选重复的couponId
                                    noRepeatCouponIdForUnUse.add(couponGrantVo.getCouponId());
                                } else {
                                    // 20151012 mlq add 筛选重复的couponId
                                    noRepeatCouponIdForCanUse.add(couponGrantVo.getCouponId());
                                }
                                // 20150618 add end
                                // (券指定A商品不可用满88可用,同时购买AB商品,但是B商品价格未达到88,应该判断所有可用商品的总额是否大于券的最低可用金额)
                            }


                        } else if (productRelationIdList.size() == 0
                                && cateRelationIdList.size() > 0) {

                            if ((cateMap.isEmpty() && comboCateMap.isEmpty()
                                    || (now_date.after(myGrant.getEndTime())
                                            || now_date.before(myGrant.getStartTime())))) {
                                reList.add(
                                        this.putCouponInfoToMap(coupon, myGrant, orderTotlePrice));
                                // 20151012 mlq add 筛选重复的couponId
                                noRepeatCouponIdForUnUse.add(couponGrantVo.getCouponId());
                            } else {
                                // 判断金额
                                double canUseProductTotalPrice = 0;
                                // 20150618 add begin
                                // (券指定A商品不可用满88可用,同时购买AB商品,但是B商品价格未达到88,应该判断所有可用商品的总额是否大于券的最低可用金额)
                                // 可用单品的总金额
                                if (!cateMap.isEmpty() && cateMap.size() > 0) {
                                    for (Entry<String, BigDecimal> entry : cateMap.entrySet()) {
                                        BigDecimal o = entry.getValue();
                                        canUseProductTotalPrice += o.doubleValue();
                                    }
                                }

                                // 可用套餐的总金额
                                if (!comboCateMap.isEmpty() && comboCateMap.size() > 0) {
                                    for (Entry<BigDecimal, BigDecimal> entry : comboCateMap
                                            .entrySet()) {
                                        BigDecimal o = entry.getValue();
                                        canUseProductTotalPrice += o.doubleValue();
                                    }
                                }
                                // 如果可用商品里面的各项商品总金额>=优惠券的最低启用金额则该优惠券可用

                                // 如果可用商品以及套餐里面的各项商品总金额>=优惠券的最低启用金额则该优惠券可用
                                if (canUseProductTotalPrice < coupon.getPayLeastMoney()
                                        .doubleValue()
                                        || (now_date.after(myGrant.getEndTime())
                                                || now_date.before(myGrant.getStartTime()))) {
                                    // 20150619 update 将添加到不可用优惠券的信息封装成一个方法调用
                                    reList.add(this.putCouponInfoToMap(coupon, myGrant,
                                            orderTotlePrice));
                                    // 20151012 mlq add 筛选重复的couponId
                                    noRepeatCouponIdForUnUse.add(couponGrantVo.getCouponId());
                                } else {
                                    // 20151012 mlq add 筛选重复的couponId
                                    noRepeatCouponIdForCanUse.add(couponGrantVo.getCouponId());
                                }
                                // 20150618 add end
                                // (券指定A商品不可用满88可用,同时购买AB商品,但是B商品价格未达到88,应该判断所有可用商品的总额是否大于券的最低可用金额)
                            }
                        }


                        // 自己改动逻辑 20150619 end



                        /************************* 注释开始 begin ****************/
                        // if(suppliers!=null&&!suppliers.equals("")){
                        // if(cateMap.isEmpty()&&comboCateMap.isEmpty()){
                        // //20150619 update 将添加到不可用优惠券的信息封装成一个方法调用
                        // reList.add(this.putCouponInfoToMap(coupon, myGrant, orderTotlePrice));
                        // }else{
                        // if (orderTotlePrice.compareTo(coupon.getPayLeastMoney()) ==
                        // -1||(now_date.after(myGrant.getEndTime())||now_date.before(myGrant.getStartTime())))
                        // {
                        // //20150619 update 将添加到不可用优惠券的信息封装成一个方法调用
                        // reList.add(this.putCouponInfoToMap(coupon, myGrant, orderTotlePrice));
                        // }
                        // }
                        //
                        // }
                        // else if(productRelationIdList.size()>0&&cateRelationIdList.size()>0){
                        //
                        // if((productMap.isEmpty()&&comboMap.isEmpty())||(cateMap.isEmpty()&&comboCateMap.isEmpty())){//并集为空，加入到不可使用优惠券list中
                        // //20150619 update 将添加到不可用优惠券的信息封装成一个方法调用
                        // reList.add(this.putCouponInfoToMap(coupon, myGrant, orderTotlePrice));
                        // }else{
                        // Double bondMoney = 0D;
                        // Boolean flag = true;
                        // boolean bool1 = true;
                        // boolean bool2 = true;
                        // boolean bool3 = true;
                        // boolean bool4 = true;
                        // if(!productMap.isEmpty()){
                        // Double bondM = 0D;
                        // for (Entry<String, BigDecimal> entry : productMap
                        // .entrySet()) {
                        // BigDecimal o = entry.getValue();
                        // Double l = o.doubleValue();
                        // bondM += l;
                        // }
                        // if(bondM >= coupon.getPayLeastMoney().doubleValue()){
                        // bool1 = false;
                        //
                        // }else bondMoney = bondM;
                        // }
                        // if(!comboMap.isEmpty()){
                        // Double bondM = 0D;
                        // for (Entry<BigDecimal, BigDecimal> entry : comboMap
                        // .entrySet()) {
                        // BigDecimal o = entry.getValue();
                        // Double l = o.doubleValue();
                        // bondM +=l;
                        //
                        // }
                        // if(bondM >= coupon.getPayLeastMoney().doubleValue()){
                        // bool2 = false;
                        // }else bondMoney = bondM;
                        // }
                        // if(!cateMap.isEmpty()){
                        // Double bondM = 0D;
                        // for (Entry<String, BigDecimal> entry : cateMap
                        // .entrySet()) {
                        // BigDecimal o = entry.getValue();
                        // Double l = o.doubleValue();
                        // bondM += l;
                        // }
                        // if(bondM >= coupon.getPayLeastMoney().doubleValue() ){
                        // bool3 = false;
                        //
                        // }else bondMoney = bondM;
                        // }
                        // if(!comboCateMap.isEmpty()){
                        // Double bondM = 0D;
                        // for (Entry<BigDecimal, BigDecimal> entry : comboCateMap
                        // .entrySet()) {
                        // BigDecimal o = entry.getValue();
                        // Double l = o.doubleValue();
                        // bondM += l;
                        //
                        // }
                        // if(bondM >= coupon.getPayLeastMoney().doubleValue()){
                        // bool4 = false;
                        // }else bondMoney = bondM;
                        // }
                        // if(!bool1||!bool2||!bool3||!bool4){
                        // flag = false;
                        // }
                        // if
                        // (flag||(now_date.after(myGrant.getEndTime())||now_date.before(myGrant.getStartTime())))
                        // {
                        //
                        // //20150619 update 将添加到不可用优惠券的信息封装成一个方法调用
                        // reList.add(this.putCouponInfoToMap(coupon, myGrant, orderTotlePrice));
                        // }
                        // }
                        // }else if(productRelationIdList.size()>0&&cateRelationIdList.size()==0){
                        //
                        // if(productMap.isEmpty()&&comboMap.isEmpty()){
                        // //20150619 update 将添加到不可用优惠券的信息封装成一个方法调用
                        // reList.add(this.putCouponInfoToMap(coupon, myGrant, orderTotlePrice));
                        // }else {
                        // boolean flag = true;
                        // Double bondMoney = 0D;
                        // if(productMap.isEmpty()&&!comboMap.isEmpty()){
                        // Double bondM = 0D;
                        // for (Entry<BigDecimal, BigDecimal> entry : comboMap
                        // .entrySet()) {
                        // BigDecimal o = entry.getValue();
                        // Double l = o.doubleValue();
                        // bondM +=l ;
                        //
                        // }
                        // if(bondM >= coupon.getPayLeastMoney().doubleValue()){
                        // flag = false;
                        // }else bondMoney = bondM;
                        // }else if(!productMap.isEmpty()&&comboMap.isEmpty()){
                        // Double bondM = 0D;
                        // for (Entry<String, BigDecimal> entry : productMap
                        // .entrySet()) {
                        // BigDecimal o = entry.getValue();
                        // Double l = o.doubleValue();
                        // bondM += l;
                        // }
                        // if(bondM >= coupon.getPayLeastMoney().doubleValue()){
                        // flag = false;
                        // }else bondMoney = bondM;
                        // }else{
                        // boolean bool1 = true;
                        // boolean bool2 = true;
                        // Double bondM = 0D;
                        // for (Entry<String, BigDecimal> entry : productMap
                        // .entrySet()) {
                        // BigDecimal o = entry.getValue();
                        // Double l = o.doubleValue();
                        // bondM += l;
                        // }
                        // if(bondM >= coupon.getPayLeastMoney().doubleValue()){
                        // bool1 = false;
                        // }else bondMoney = bondM;
                        // Double bondMo = 0D;
                        // for (Entry<BigDecimal, BigDecimal> entry : comboMap
                        // .entrySet()) {
                        // BigDecimal o = entry.getValue();
                        // Double l = o.doubleValue();
                        // bondMo +=l;
                        //
                        // }
                        // if(bondMo >= coupon.getPayLeastMoney().doubleValue()){
                        // bool2 = false;
                        // }else bondMoney = bondM;
                        // if(!bool1||!bool2){
                        // flag = false;
                        // }
                        //
                        // }
                        // if
                        // (flag||(now_date.after(myGrant.getEndTime())||now_date.before(myGrant.getStartTime())))
                        // {
                        // //20150619 update 将添加到不可用优惠券的信息封装成一个方法调用
                        // reList.add(this.putCouponInfoToMap(coupon, myGrant, orderTotlePrice));
                        // }
                        // }
                        // }else if(productRelationIdList.size()==0&&cateRelationIdList.size()>0){
                        // if(cateMap.isEmpty()&&comboCateMap.isEmpty()){
                        // //20150619 update 将添加到不可用优惠券的信息封装成一个方法调用
                        // reList.add(this.putCouponInfoToMap(coupon, myGrant, orderTotlePrice));
                        // }else{
                        // Boolean flag=true;
                        // Double bondMoney=0D;
                        // if(cateMap.isEmpty()&&!comboCateMap.isEmpty()){
                        // Double bondM = 0D;
                        // for (Entry<BigDecimal, BigDecimal> entry : comboCateMap
                        // .entrySet()) {
                        // BigDecimal o = entry.getValue();
                        // Double l = o.doubleValue();
                        // bondM += l;
                        //
                        // }
                        // if (bondM >= coupon.getPayLeastMoney().doubleValue()){
                        // flag=false;
                        // }else bondMoney = bondM;
                        // }else if(!cateMap.isEmpty()&&comboCateMap.isEmpty()){
                        // Double bondM = 0D;
                        // for (Entry<String, BigDecimal> entry : cateMap
                        // .entrySet()) {
                        // BigDecimal o = entry.getValue();
                        // Double l = o.doubleValue();
                        // bondM += l;
                        //
                        // }
                        // if (bondM >= coupon.getPayLeastMoney().doubleValue()){
                        // flag=false;
                        // }else bondMoney = bondM;
                        // }else {
                        // boolean bool1=true;
                        // boolean bool2=true;
                        // Double bondM = 0D;
                        // for (Entry<String, BigDecimal> entry : cateMap
                        // .entrySet()) {
                        // BigDecimal o = entry.getValue();
                        // Double l = o.doubleValue();
                        // bondM += l;
                        //
                        // }
                        // if (bondM >= coupon.getPayLeastMoney().doubleValue()){
                        // bool1=false;
                        // }else bondMoney = bondM;
                        // Double bondMo = 0D;
                        // for (Entry<BigDecimal, BigDecimal> entry : comboCateMap
                        // .entrySet()) {
                        // BigDecimal o = entry.getValue();
                        // Double l = o.doubleValue();
                        // bondMo += l;
                        //
                        // }
                        // if (bondMo >= coupon.getPayLeastMoney().doubleValue()){
                        // bool2=false;
                        // }else bondMoney = bondM;
                        // if(!bool1||!bool2){
                        // flag=false;
                        // }
                        // }
                        //
                        // if
                        // (flag||(now_date.after(myGrant.getEndTime())||now_date.before(myGrant.getStartTime())))
                        // {
                        //
                        // //20150619 update 将添加到不可用优惠券的信息封装成一个方法调用
                        // reList.add(this.putCouponInfoToMap(coupon, myGrant, orderTotlePrice));
                        // }
                        // }
                        // }
                        /********************* 注释end ******************/


                    }
                } else if (rangType == 2) { // 黑名单使用范围
                    // 判断自营和入驻
                    if (coupon.getSupplierType() == 2) {// 自营代销
                        Boolean bool = false;
                        for (OrderVo order : orderList) {
                            Product pro =
                                    couponGrantSetDAO.selectProductBySKU(order.getProductSkuCode());
                            if (!pro.getShopCode().equals("221")) {// 自营商品
                                SuppliersInfo sinfo = suppliersInfoDAO
                                        .selectByPrimaryKey(new Long(pro.getShopCode()));
                                if (sinfo.getSupplierType() != 3) {// 代销
                                    bool = true;

                                    // 20150619 update 将添加到不可用优惠券的信息封装成一个方法调用
                                    reList.add(this.putCouponInfoToMap(coupon, myGrant,
                                            orderTotlePrice));
                                    break;
                                }
                            }

                        }
                        if (bool) {
                            // 20151012 mlq add 筛选重复的couponId
                            noRepeatCouponIdForUnUse.add(couponGrantVo.getCouponId());
                            continue;
                        }
                    } else if (coupon.getSupplierType() == 3) {// 入驻商家
                        Boolean bool = false;
                        for (OrderVo order : orderList) {
                            Product pro =
                                    couponGrantSetDAO.selectProductBySKU(order.getProductSkuCode());
                            if (pro.getShopCode().equals("221")) {// 自营商品
                                bool = true;
                                // 20150619 update 将添加到不可用优惠券的信息封装成一个方法调用
                                reList.add(
                                        this.putCouponInfoToMap(coupon, myGrant, orderTotlePrice));
                                break;
                            } else {
                                SuppliersInfo sinfo = suppliersInfoDAO
                                        .selectByPrimaryKey(new Long(pro.getShopCode()));
                                if (sinfo.getSupplierType() == 3) {// 代销
                                    bool = true;
                                    // 20150619 update 将添加到不可用优惠券的信息封装成一个方法调用
                                    reList.add(this.putCouponInfoToMap(coupon, myGrant,
                                            orderTotlePrice));
                                    break;
                                }
                            }

                        }
                        if (bool) {
                            // 20151012 mlq add 筛选重复的couponId
                            noRepeatCouponIdForUnUse.add(couponGrantVo.getCouponId());
                            continue;
                        }
                    }
                    List<String> productRelationIdList =
                            couponProductdao.selectRelationByCouponId(coupon.getCouponId());
                    List<String> cateRelationIdList =
                            couponProductdao.selectCateRelationByCouponId(coupon.getCouponId());
                    String suppliers =
                            coupondao.selectByPrimaryKey(coupon.getCouponId()).getShopCode();

                    // 20150608 maliqun add begin 将优惠券规则制定的入驻商家分隔开来
                    Map<String, String> shopCodeForCouponSingle = new HashMap<String, String>();
                    // 分隔规则所对应的指定入驻商家
                    if (suppliers != null && !suppliers.isEmpty()) {
                        StringTokenizer st = new StringTokenizer(suppliers, ",");
                        while (st.hasMoreTokens()) {
                            String temp = st.nextToken();
                            shopCodeForCouponSingle.put(temp, temp);
                        }
                    }

                    // 如果当前传入的商家不在券所指定的商家内,则该规则不可用
                    if ((!shopCodeForCouponSingle.isEmpty()
                            && !shopCodeForCouponSingle.containsKey(String.valueOf(supplierId)))
                            || (now_date.after(myGrant.getEndTime())
                                    || now_date.before(myGrant.getStartTime()))) {
                        // 20150619 update 将添加到不可用优惠券的信息封装成一个方法调用
                        reList.add(this.putCouponInfoToMap(coupon, myGrant, orderTotlePrice));
                        // 20151012 mlq add 筛选重复的couponId
                        noRepeatCouponIdForUnUse.add(couponGrantVo.getCouponId());
                        continue;
                    }
                    // 20150608 maliqun add end 当规则的商家不是等于当前传入过来的商家时


                    // 全场通用类型优惠券 20150608 update
                    // 将suppliers.contains(supplierId.toString()))该条件改为从map当中获取,因为如果商家shopCode是4258,而传入进来的supplierId恰好是425,该组合中的一种情况,则判断必定会有误
                    if ((productRelationIdList.size() == 0 && cateRelationIdList.size() == 0
                            && (suppliers == null || suppliers.equals("")))
                            || ((productRelationIdList.size() == 0 && cateRelationIdList.size() == 0
                                    && suppliers != null && !suppliers.equals(""))
                                    && shopCodeForCouponSingle
                                            .containsKey((String.valueOf(supplierId))))) {
                        if (orderTotlePrice.compareTo(coupon.getPayLeastMoney()) == -1
                                || (now_date.after(myGrant.getEndTime())
                                        || now_date.before(myGrant.getStartTime()))) {

                            // 20150619 update 将添加到不可用优惠券的信息封装成一个方法调用
                            reList.add(this.putCouponInfoToMap(coupon, myGrant, orderTotlePrice));
                            // 20151012 mlq add 筛选重复的couponId
                            noRepeatCouponIdForUnUse.add(couponGrantVo.getCouponId());
                        } else {
                            // 20151012 mlq add 筛选重复的couponId
                            noRepeatCouponIdForCanUse.add(couponGrantVo.getCouponId());
                        }
                    } // 有限定范围
                    else {
                        Map<String, BigDecimal> productMap =
                                this.canUseBlackCouponProduct(orderList, productRelationIdList);
                        Map<String, BigDecimal> cateMap = this.canUseBlackCouponCate(orderList,
                                cateRelationIdList, coupon.getShopCode());
                        Map<BigDecimal, BigDecimal> comboMap =
                                this.comboBlackInfo(orderList, productRelationIdList);
                        Map<BigDecimal, BigDecimal> comboCateMap = this.comboBlackCouponCate(
                                orderList, cateRelationIdList, coupon.getShopCode());

                        // 自己改动逻辑 begin 20150619
                        // 优惠券指定的类目和单品都不为空
                        if (productRelationIdList.size() > 0 && cateRelationIdList.size() > 0) {

                            if ((productMap.isEmpty() && comboMap.isEmpty() && cateMap.isEmpty()
                                    && comboCateMap.isEmpty())
                                    || (now_date.after(myGrant.getEndTime())
                                            || now_date.before(myGrant.getStartTime()))) {
                                reList.add(
                                        this.putCouponInfoToMap(coupon, myGrant, orderTotlePrice));
                                // 20151012 mlq add 筛选重复的couponId
                                noRepeatCouponIdForUnUse.add(couponGrantVo.getCouponId());
                            } else {
                                // 判断金额

                                // 20150618 add
                                // (券指定A商品不可用满88可用,同时购买AB商品,但是B商品价格未达到88,应该判断所有可用商品的总额是否大于券的最低可用金额)
                                // begin
                                // 可用单品的总金额
                                double totalPriceForSingleCanUseProduct = 0;

                                // 存储符合的单品,不重复(保证类目和单品种的不重复金额叠加)
                                Map<String, BigDecimal> mapForSingleCanUseProduct =
                                        new HashMap<String, BigDecimal>();
                                // 需要去重复的, productMap当中以及 cateMap当中都有的商品不要累加计算金额
                                if (!productMap.isEmpty() && productMap.size() > 0) {
                                    mapForSingleCanUseProduct.putAll(productMap);
                                }

                                if (!cateMap.isEmpty() && cateMap.size() > 0) {
                                    mapForSingleCanUseProduct.putAll(cateMap);
                                }

                                // 累加可用单品的总金额
                                if (!mapForSingleCanUseProduct.isEmpty()
                                        && mapForSingleCanUseProduct.size() > 0) {
                                    for (Entry<String, BigDecimal> entry : mapForSingleCanUseProduct
                                            .entrySet()) {
                                        BigDecimal o = entry.getValue();
                                        totalPriceForSingleCanUseProduct += o.doubleValue();
                                    }
                                }

                                // 可用套餐中的总金额
                                double totalPriceForCombineCanUseProduct = 0;
                                // 存储符合的套餐,不重复(保持类目中的和单独套餐不要重复)
                                Map<BigDecimal, BigDecimal> mapForCombineCanUseProduct =
                                        new HashMap<BigDecimal, BigDecimal>();
                                // 需要去重复的, productMap当中以及 cateMap当中都有的商品不要累加计算金额


                                if (!comboMap.isEmpty() && comboMap.size() > 0) {
                                    mapForCombineCanUseProduct.putAll(comboMap);
                                }

                                if (!comboCateMap.isEmpty() && comboCateMap.size() > 0) {
                                    mapForCombineCanUseProduct.putAll(comboCateMap);
                                }

                                // 累加可用套餐的总金额
                                if (!mapForCombineCanUseProduct.isEmpty()
                                        && mapForCombineCanUseProduct.size() > 0) {
                                    for (Entry<BigDecimal, BigDecimal> entry : mapForCombineCanUseProduct
                                            .entrySet()) {
                                        BigDecimal o = entry.getValue();
                                        totalPriceForCombineCanUseProduct += o.doubleValue();
                                    }
                                }

                                double totalPriceForProduct = totalPriceForSingleCanUseProduct
                                        + totalPriceForCombineCanUseProduct;

                                // 如果可用商品以及套餐里面的各项商品总金额>=优惠券的最低启用金额则该优惠券可用
                                if (totalPriceForProduct < coupon.getPayLeastMoney().doubleValue()
                                        || (now_date.after(myGrant.getEndTime())
                                                || now_date.before(myGrant.getStartTime()))) {
                                    // 20150619 update 将添加到不可用优惠券的信息封装成一个方法调用
                                    reList.add(this.putCouponInfoToMap(coupon, myGrant,
                                            orderTotlePrice));
                                    // 20151012 mlq add 筛选重复的couponId
                                    noRepeatCouponIdForUnUse.add(couponGrantVo.getCouponId());
                                } else {
                                    // 20151012 mlq add 筛选重复的couponId
                                    noRepeatCouponIdForCanUse.add(couponGrantVo.getCouponId());
                                }
                                // 20150618 add end

                            }
                        } else if (productRelationIdList.size() > 0
                                && cateRelationIdList.size() == 0) {

                            if ((productMap.isEmpty() && comboMap.isEmpty()
                                    || (now_date.after(myGrant.getEndTime())
                                            || now_date.before(myGrant.getStartTime())))) {
                                reList.add(
                                        this.putCouponInfoToMap(coupon, myGrant, orderTotlePrice));
                                // 20151012 mlq add 筛选重复的couponId
                                noRepeatCouponIdForUnUse.add(couponGrantVo.getCouponId());
                            } else {
                                // 20150618 add
                                // begin(券指定A商品不可用满88可用,同时购买AB商品,但是B商品价格未达到88,应该判断所有可用商品的总额是否大于券的最低可用金额)
                                double canUseProductTotalPrice = 0;
                                // 可用单品总金额累加
                                if (!productMap.isEmpty() && productMap.size() > 0) {
                                    for (Entry<String, BigDecimal> entry : productMap.entrySet()) {
                                        BigDecimal o = entry.getValue();
                                        canUseProductTotalPrice += o.doubleValue();
                                    }
                                }

                                // 可用套餐总金额累加
                                if (!comboMap.isEmpty() && comboMap.size() > 0) {
                                    for (Entry<BigDecimal, BigDecimal> entry : comboMap
                                            .entrySet()) {
                                        BigDecimal o = entry.getValue();
                                        canUseProductTotalPrice += o.doubleValue();
                                    }
                                }

                                // 如果可用商品以及套餐里面的各项商品总金额>=优惠券的最低启用金额则该优惠券可用
                                if (canUseProductTotalPrice < coupon.getPayLeastMoney()
                                        .doubleValue()
                                        || (now_date.after(myGrant.getEndTime())
                                                || now_date.before(myGrant.getStartTime()))) {
                                    // 20150619 update 将添加到不可用优惠券的信息封装成一个方法调用
                                    reList.add(this.putCouponInfoToMap(coupon, myGrant,
                                            orderTotlePrice));
                                    // 20151012 mlq add 筛选重复的couponId
                                    noRepeatCouponIdForUnUse.add(couponGrantVo.getCouponId());
                                } else {
                                    // 20151012 mlq add 筛选重复的couponId
                                    noRepeatCouponIdForCanUse.add(couponGrantVo.getCouponId());
                                }
                                // 20150618 add end
                                // (券指定A商品不可用满88可用,同时购买AB商品,但是B商品价格未达到88,应该判断所有可用商品的总额是否大于券的最低可用金额)
                            }


                        } else if (productRelationIdList.size() == 0
                                && cateRelationIdList.size() > 0) {

                            if ((cateMap.isEmpty() && comboCateMap.isEmpty()
                                    || (now_date.after(myGrant.getEndTime())
                                            || now_date.before(myGrant.getStartTime())))) {
                                reList.add(
                                        this.putCouponInfoToMap(coupon, myGrant, orderTotlePrice));
                                // 20151012 mlq add 筛选重复的couponId
                                noRepeatCouponIdForUnUse.add(couponGrantVo.getCouponId());
                            } else {
                                // 判断金额
                                double canUseProductTotalPrice = 0;
                                // 20150618 add begin
                                // (券指定A商品不可用满88可用,同时购买AB商品,但是B商品价格未达到88,应该判断所有可用商品的总额是否大于券的最低可用金额)
                                // 可用单品的总金额
                                if (!cateMap.isEmpty() && cateMap.size() > 0) {
                                    for (Entry<String, BigDecimal> entry : cateMap.entrySet()) {
                                        BigDecimal o = entry.getValue();
                                        canUseProductTotalPrice += o.doubleValue();
                                    }
                                }

                                // 可用套餐的总金额
                                if (!comboCateMap.isEmpty() && comboCateMap.size() > 0) {
                                    for (Entry<BigDecimal, BigDecimal> entry : comboCateMap
                                            .entrySet()) {
                                        BigDecimal o = entry.getValue();
                                        canUseProductTotalPrice += o.doubleValue();
                                    }
                                }
                                // 如果可用商品里面的各项商品总金额>=优惠券的最低启用金额则该优惠券可用

                                // 如果可用商品以及套餐里面的各项商品总金额>=优惠券的最低启用金额则该优惠券可用
                                if (canUseProductTotalPrice < coupon.getPayLeastMoney()
                                        .doubleValue()
                                        || (now_date.after(myGrant.getEndTime())
                                                || now_date.before(myGrant.getStartTime()))) {
                                    // 20150619 update 将添加到不可用优惠券的信息封装成一个方法调用
                                    reList.add(this.putCouponInfoToMap(coupon, myGrant,
                                            orderTotlePrice));
                                    // 20151012 mlq add 筛选重复的couponId
                                    noRepeatCouponIdForUnUse.add(couponGrantVo.getCouponId());
                                } else {
                                    // 20151012 mlq add 筛选重复的couponId
                                    noRepeatCouponIdForCanUse.add(couponGrantVo.getCouponId());
                                }
                                // 20150618 add end
                                // (券指定A商品不可用满88可用,同时购买AB商品,但是B商品价格未达到88,应该判断所有可用商品的总额是否大于券的最低可用金额)
                            }
                        }


                        // 自己改动逻辑 20150619 end



                        /****************** 注释掉begin *********************/
                        // if(productRelationIdList.size()>0&&cateRelationIdList.size()>0){
                        // if(productMap.isEmpty()&&comboMap.isEmpty()&&cateMap.isEmpty()&&comboCateMap.isEmpty()){
                        // //20150619 update 将添加到不可用优惠券的信息封装成一个方法调用
                        // reList.add(this.putCouponInfoToMap(coupon, myGrant, orderTotlePrice));
                        // }else{
                        // Double bondMoney = 0D;
                        // Boolean flag = true;
                        // boolean bool1 = true;
                        // boolean bool2 = true;
                        // boolean bool3 = true;
                        // boolean bool4 = true;
                        // if(!productMap.isEmpty()){
                        // Double bondM = 0D;
                        // for (Entry<String, BigDecimal> entry : productMap
                        // .entrySet()) {
                        // BigDecimal o = entry.getValue();
                        // Double l = o.doubleValue();
                        // bondM += l;
                        // }
                        // if(bondM >= coupon.getPayLeastMoney().doubleValue() ){
                        // bool1 = false;
                        //
                        // }else bondMoney = bondM;
                        // }
                        // if(!comboMap.isEmpty()){
                        // Double bondM = 0D;
                        // for (Entry<BigDecimal, BigDecimal> entry : comboMap
                        // .entrySet()) {
                        // BigDecimal o = entry.getValue();
                        // Double l = o.doubleValue();
                        // bondM += l;
                        //
                        // }
                        // if(bondM >= coupon.getPayLeastMoney().doubleValue()){
                        // bool2 = false;
                        // }else bondMoney = bondM;
                        // }
                        // if(!cateMap.isEmpty()){
                        // Double bondM = 0D;
                        // for (Entry<String, BigDecimal> entry : cateMap
                        // .entrySet()) {
                        // BigDecimal o = entry.getValue();
                        // Double l = o.doubleValue();
                        // bondM += l;
                        // }
                        // if(bondM >= coupon.getPayLeastMoney().doubleValue() ){
                        // bool3 = false;
                        //
                        // }else bondMoney = bondM;
                        // }
                        // if(!comboCateMap.isEmpty()){
                        // Double bondM = 0D;
                        // for (Entry<BigDecimal, BigDecimal> entry : comboCateMap
                        // .entrySet()) {
                        // BigDecimal o = entry.getValue();
                        // Double l = o.doubleValue();
                        // bondM += l;
                        //
                        // }
                        // if(bondM >= coupon.getPayLeastMoney().doubleValue()){
                        // bool4 = false;
                        // }else bondMoney = bondM;
                        // }
                        // if(!bool1||!bool2||!bool3||!bool4){
                        // flag = false;
                        // }
                        // if
                        // (flag||(now_date.after(myGrant.getEndTime())||now_date.before(myGrant.getStartTime())))
                        // {
                        // //20150619 update 将添加到不可用优惠券的信息封装成一个方法调用
                        // reList.add(this.putCouponInfoToMap(coupon, myGrant, orderTotlePrice));
                        // }
                        // }
                        // }else if(productRelationIdList.size()>0&&cateRelationIdList.size()==0){
                        // if(productMap.isEmpty()&&comboMap.isEmpty()){
                        // //20150619 update 将添加到不可用优惠券的信息封装成一个方法调用
                        // reList.add(this.putCouponInfoToMap(coupon, myGrant, orderTotlePrice));
                        // }else{
                        // boolean flag = true;
                        // Double bondMoney = 0D;
                        // if(productMap.isEmpty()&&!comboMap.isEmpty()){
                        // Double bondM = 0D;
                        // for (Entry<BigDecimal, BigDecimal> entry : comboMap
                        // .entrySet()) {
                        // BigDecimal o = entry.getValue();
                        // Double l = o.doubleValue();
                        // bondM +=l;
                        //
                        // }
                        // if(bondM >= coupon.getPayLeastMoney().doubleValue()){
                        // flag = false;
                        // }else bondMoney = bondM;
                        // }else if(!productMap.isEmpty()&&comboMap.isEmpty()){
                        // Double bondM = 0D;
                        // for (Entry<String, BigDecimal> entry : productMap
                        // .entrySet()) {
                        // BigDecimal o = entry.getValue();
                        // Double l = o.doubleValue();
                        // bondM += l;
                        // }
                        // if(bondM >= coupon.getPayLeastMoney().doubleValue()){
                        // flag = false;
                        // }else bondMoney = bondM;
                        // }else{
                        // boolean bool1 = true;
                        // boolean bool2 = true;
                        // Double bondM = 0D;
                        // for (Entry<String, BigDecimal> entry : productMap
                        // .entrySet()) {
                        // BigDecimal o = entry.getValue();
                        // Double l = o.doubleValue();
                        // bondM += l;
                        // }
                        // if(bondM >= coupon.getPayLeastMoney().doubleValue()){
                        // bool1 = false;
                        // }else bondMoney = bondM;
                        // Double bondMo = 0D;
                        // for (Entry<BigDecimal, BigDecimal> entry : comboMap
                        // .entrySet()) {
                        // BigDecimal o = entry.getValue();
                        // Double l = o.doubleValue();
                        // bondMo +=l;
                        //
                        // }
                        // if(bondMo >= coupon.getPayLeastMoney().doubleValue()){
                        // bool2 = false;
                        // }else bondMoney = bondM;
                        // if(!bool1||!bool2){
                        // flag = false;
                        // }
                        //
                        // }
                        // if
                        // (flag||(now_date.after(myGrant.getEndTime())||now_date.before(myGrant.getStartTime())))
                        // {
                        // //20150619 update 将添加到不可用优惠券的信息封装成一个方法调用
                        // reList.add(this.putCouponInfoToMap(coupon, myGrant, orderTotlePrice));
                        // }
                        // }
                        //
                        // }else if(productRelationIdList.size()==0&&cateRelationIdList.size()>0){
                        // if(cateMap.isEmpty()&&comboCateMap.isEmpty()){
                        // //20150619 update 将添加到不可用优惠券的信息封装成一个方法调用
                        // reList.add(this.putCouponInfoToMap(coupon, myGrant, orderTotlePrice));
                        // }else{
                        // Boolean flag=true;
                        // Double bondMoney=0D;
                        // if(cateMap.isEmpty()&&!comboCateMap.isEmpty()){
                        // Double bondM = 0D;
                        // for (Entry<BigDecimal, BigDecimal> entry : comboCateMap
                        // .entrySet()) {
                        // BigDecimal o = entry.getValue();
                        // Double l = o.doubleValue();
                        // bondM += l;
                        // }
                        // if (bondM >= coupon.getPayLeastMoney().doubleValue()){
                        // flag=false;
                        // }else bondMoney = bondM;
                        // }else if(!cateMap.isEmpty()&&comboCateMap.isEmpty()){
                        // Double bondM = 0D;
                        // for (Entry<String, BigDecimal> entry : cateMap
                        // .entrySet()) {
                        // BigDecimal o = entry.getValue();
                        // Double l = o.doubleValue();
                        // bondM += l;
                        //
                        // }
                        // if (bondM >= coupon.getPayLeastMoney().doubleValue()){
                        // flag=false;
                        // }else bondMoney = bondM;
                        // }else {
                        // boolean bool1=true;
                        // boolean bool2=true;
                        // Double bondM = 0D;
                        // for (Entry<String, BigDecimal> entry : cateMap
                        // .entrySet()) {
                        // BigDecimal o = entry.getValue();
                        // Double l = o.doubleValue();
                        // bondM += l;
                        //
                        // }
                        // if (bondM >= coupon.getPayLeastMoney().doubleValue()){
                        // bool1=false;
                        // }else bondMoney = bondM;
                        // Double bondMo = 0D;
                        // for (Entry<BigDecimal, BigDecimal> entry : comboCateMap
                        // .entrySet()) {
                        // BigDecimal o = entry.getValue();
                        // Double l = o.doubleValue();
                        // bondMo += l;
                        //
                        // }
                        // if (bondMo>= coupon.getPayLeastMoney().doubleValue()){
                        // bool2=false;
                        // }else bondMoney = bondM;
                        // if(!bool1||!bool2){
                        // flag=false;
                        // }
                        // }
                        // if
                        // (flag||(now_date.after(myGrant.getEndTime())||now_date.before(myGrant.getStartTime())))
                        // {
                        //
                        // //20150619 update 将添加到不可用优惠券的信息封装成一个方法调用
                        // reList.add(this.putCouponInfoToMap(coupon, myGrant, orderTotlePrice));
                        // }
                        // }
                        // }

                        /****************** 注释掉end *********************/
                    }

                }
                // 如果是积分兑换优惠券类型
                // if (coupon.getCouponGivetypeId() == Long
                // .parseLong(CouponGrantType.POINTEXCUT_COUPONGRANTTYPE
                // .getType())) {
                // int days = coupon.getCouponValidDay().intValue();
                // Calendar ca = Calendar.getInstance();
                // // 得到发放时间
                // ca.setTime(myGrant.getGrantEndtime());
                // ca.add(Calendar.DAY_OF_WEEK, days);
                // Calendar ca_now = Calendar.getInstance();
                // //
                // System.out.println(ca_now.after(ca)+"---"+orderTotlePrice.compareTo(coupon.getPayLeastMoney()));
                // // if (!(!ca_now.after(ca) &&
                // // orderTotlePrice.compareTo(coupon.getPayLeastMoney()) ==
                // // 1))
                // // {
                // // resultList.add(couponGrantVo);
                // // HashMap<String,String> h = new HashMap();
                // //
                // // }
                // if (ca_now.after(ca)
                // || orderTotlePrice.compareTo(coupon
                // .getPayLeastMoney()) == -1) {
                // HashMap<String, String> h = new HashMap();
                // SimpleDateFormat dateformat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                // String endDate=dateformat.format(coupon.getEndtime());
                // h.put("couponName", coupon.getCouponName());
                // h.put("availablePrice", orderTotlePrice.toString());
                // h.put("couponLeastPrice", coupon.getPayLeastMoney()
                // .toString());
                // h.put("couponId", coupon.getCouponId().toString());
                // h.put("endDate", endDate);
                // h.put("orderTotlePrice", orderTotlePrice.toString());
                // h.put("couponMoney", coupon.getCouponMoney().toString());
                // if(ca_now.after(ca)){
                // h.put("isTimeOut", "true");
                // }else{
                // h.put("isTimeOut", "false");
                // }
                // reList.add(h);
                // }
                // } else {
                // //优惠券类型为积分兑换时，结束时间去grant的结束时间
                // if (coupon.getCouponGivetypeId()
                // ==Long.parseLong(CouponGrantType.POINTEXCUT_COUPONGRANTTYPE.getType()))
                // {
                // coupon.setEndtime(myGrant.getGrantEndtime());
                // }

                // }

            }
        } catch (SQLException e) {
            logger.error("查询不可用优惠券失败", e);
        } catch (Exception e) {
            logger.error("查询不可用优惠券失败", e);
        }
        return reList;
    }

    /**
     * 判断传过来的listSKU 是否能够匹配productList,如果匹配，拿出来 true:能 false:不能
     * 
     * @return
     */
    private Map<String, BigDecimal> canUseCouponProduct(List<OrderVo> orderList,
            List<String> couponProductList) throws Exception {
        logger.info(
                "----------------------------canUseCouponProduct：判断传过来的listSKU 是否能够匹配productList,如果匹配，拿出来--------------------");
        Map<String, BigDecimal> map = Maps.newHashMap();
        if (couponProductList != null && couponProductList.size() > 0) {
            for (OrderVo ordervo : orderList) {
                // 当包含产品sku并且商品不在套餐中时才可以将商品计入优惠券范围
                if (couponProductList.contains(ordervo.getProductSkuCode()) && ordervo
                        .getProductType() == CouponProductType.SINGLE.getKey().intValue()) {
                    map.put(ordervo.getProductSkuCode(), ordervo.getProductTotlePrice());
                }
            }
        }
        return map;
    }

    /**
     * 判断传过来的listSKU 是否能够匹配productList,如果匹配，拿出来 true:能 false:不能(黑名单)
     * 
     * @return
     */
    private Map<String, BigDecimal> canUseBlackCouponProduct(List<OrderVo> orderList,
            List<String> couponProductList) throws Exception {
        logger.info(
                "----------------------------canUseCouponProduct：判断传过来的listSKU 是否能够匹配productList,如果匹配，拿出来--------------------");
        System.out.println(
                "----------------------------canUseCouponProduct：判断传过来的listSKU 是否能够匹配productList,如果匹配，拿出来--------------------");

        Map<String, BigDecimal> map = Maps.newHashMap();
        if (couponProductList != null && couponProductList.size() > 0) {
            for (OrderVo ordervo : orderList) {
                // 当包含产品sku并且商品不在套餐中时才可以将商品计入优惠券范围
                if (!couponProductList.contains(ordervo.getProductSkuCode()) && ordervo
                        .getProductType() == CouponProductType.SINGLE.getKey().intValue()) {
                    map.put(ordervo.getProductSkuCode(), ordervo.getProductTotlePrice());
                }
            }
        }
        return map;
    }


    /**
     * 过滤出套餐信息（黑名单）
     * 
     * @param orderList
     * @param couponProductList
     * @return
     */
    private Map<BigDecimal, BigDecimal> comboBlackInfo(List<OrderVo> orderList,
            List<String> couponProductList) {
        Map<BigDecimal, BigDecimal> map = new HashMap<BigDecimal, BigDecimal>();
        // 套餐id对应商品列表
        Map<BigDecimal, List<OrderVo>> mapList = new HashMap<BigDecimal, List<OrderVo>>();
        Set<BigDecimal> setId = new HashSet<BigDecimal>();
        for (OrderVo ordervo : orderList) {
            // 当包含产品sku并且商品不在套餐中时才可以将商品计入优惠券范围
            if (ordervo.getProductType() != CouponProductType.SINGLE.getKey().intValue()) {

                // 如果是套餐，则遍历套餐内所有商品都在优惠券使用内才计入优惠券范围
                BigDecimal comboid = ordervo.getRelationId();// 优惠券ID
                if (!setId.contains(comboid)) {
                    setId.add(ordervo.getRelationId());
                    List<OrderVo> listOrder = new ArrayList<OrderVo>();
                    listOrder.add(ordervo);
                    mapList.put(comboid, listOrder);
                } else {
                    List<OrderVo> listOrder = mapList.get(comboid);
                    listOrder.add(ordervo);
                    mapList.put(comboid, listOrder);
                }
            }
        }
        Iterator<BigDecimal> it = setId.iterator();
        for (; it.hasNext();) {
            BigDecimal bigid = it.next();
            List<OrderVo> listOrder = mapList.get(bigid);
            Boolean bool = true;
            for (OrderVo orderV : listOrder) {
                if (couponProductList.contains(orderV.getProductSkuCode())) {
                    bool = false;
                    break;
                }
            }
            if (bool) {
                map.put(bigid, listOrder.get(0).getRelationPrice());
            }

        }

        return map;
    }

    /**
     * 过滤出套餐信息（白名单）
     * 
     * @param orderList
     * @param couponProductList
     * @return
     */
    private Map<BigDecimal, BigDecimal> comboInfo(List<OrderVo> orderList,
            List<String> couponProductList) {
        Map<BigDecimal, BigDecimal> map = new HashMap<BigDecimal, BigDecimal>();
        // 套餐id对应商品列表
        Map<BigDecimal, List<OrderVo>> mapList = new HashMap<BigDecimal, List<OrderVo>>();
        Set<BigDecimal> setId = new HashSet<BigDecimal>();
        // 商品分套餐
        for (OrderVo ordervo : orderList) {
            // 当包含产品sku并且商品不在套餐中时才可以将商品计入优惠券范围
            if (ordervo.getProductType() != CouponProductType.SINGLE.getKey().intValue()) {

                // 如果是套餐，则遍历套餐内所有商品都在优惠券使用内才计入优惠券范围
                BigDecimal comboid = ordervo.getRelationId();// 套餐ID
                if (!setId.contains(comboid)) {
                    setId.add(ordervo.getRelationId());
                    List<OrderVo> listOrder = new ArrayList<OrderVo>();
                    listOrder.add(ordervo);
                    mapList.put(comboid, listOrder);
                } else {
                    // 包含存入商品
                    List<OrderVo> listOrder = mapList.get(comboid);
                    listOrder.add(ordervo);
                    mapList.put(comboid, listOrder);
                }
            }
        }
        Iterator<BigDecimal> it = setId.iterator();
        for (; it.hasNext();) {
            BigDecimal bigid = it.next();
            List<OrderVo> listOrder = mapList.get(bigid);
            Boolean bool = true;
            for (OrderVo orderV : listOrder) {
                if (!couponProductList.contains(orderV.getProductSkuCode())) {
                    bool = false;
                    break;
                }
            }
            if (bool) {
                map.put(bigid, listOrder.get(0).getRelationPrice());
            }

        }

        return map;
    }

    /**
     * 判断传来的是不是在类型里面(黑名单套餐)
     * 
     * @param orderList
     * @param couponCateList
     * @param shopCodes
     * @return
     * @throws SQLException
     */
    private Map<BigDecimal, BigDecimal> comboBlackCouponCate(List<OrderVo> orderList,
            List<String> couponCateList, String shopCodes) throws SQLException {
        logger.info(
                "----------------------------comboCouponCate：判断传来的是不是在类型里面（黑名单套餐)--------------------");
        Map<BigDecimal, BigDecimal> map = new HashMap<BigDecimal, BigDecimal>();
        Map<BigDecimal, List<OrderVo>> mapList = new HashMap<BigDecimal, List<OrderVo>>();
        Set<BigDecimal> setId = new HashSet<BigDecimal>();
        for (OrderVo ordervo : orderList) {
            // 当包含产品sku并且商品不在套餐中时才可以将商品计入优惠券范围
            if (ordervo.getProductType() != CouponProductType.SINGLE.getKey().intValue()) {

                // 如果是套餐，则遍历套餐内所有商品都在优惠券使用内才计入优惠券范围
                BigDecimal comboid = ordervo.getRelationId();// 优惠券ID
                if (!setId.contains(comboid)) {
                    setId.add(ordervo.getRelationId());
                    List<OrderVo> listOrder = new ArrayList<OrderVo>();
                    listOrder.add(ordervo);
                    mapList.put(comboid, listOrder);
                } else {
                    List<OrderVo> listOrder = mapList.get(comboid);
                    listOrder.add(ordervo);
                    mapList.put(comboid, listOrder);
                }
            }
        }
        Iterator<BigDecimal> it = setId.iterator();
        for (; it.hasNext();) {
            BigDecimal bigid = it.next();
            List<OrderVo> listOrder = mapList.get(bigid);
            Boolean bool = true;
            for (OrderVo orderV : listOrder) {
                ViewProductSkuExample example = new ViewProductSkuExample();
                example.createCriteria().andProductSkuCodeEqualTo(orderV.getProductSkuCode());
                List<ViewProductSku> vList = viewProductSkuDao.selectByExample(example);
                if (vList != null && vList.size() > 0) {
                    ViewProductSku v = vList.get(0);
                    String cateproduct = v.getCategoryId() + "";
                    if (couponCateList != null && couponCateList.size() > 0) {
                        if (couponCateList.contains(cateproduct.trim())) {
                            bool = false;
                            break;
                        }
                    } else {
                        // 当类目为空时，需校验是否在供应商范围内
                        if (shopCodes == null || shopCodes.equals("")) {
                            bool = false;
                            break;
                        }
                    }
                }

            }
            if (bool) {
                map.put(bigid, listOrder.get(0).getRelationPrice());
            }

        }
        return map;
    }

    /**
     * 判断传来的是不是在类型里面(白名单套餐)
     * 
     * @param orderList
     * @param couponCateList
     * @param shopCodes
     * @return
     */
    private Map<BigDecimal, BigDecimal> comboCouponCate(List<OrderVo> orderList,
            List<String> couponCateList, String shopCodes) throws Exception {
        logger.info(
                "----------------------------comboCouponCate：判断传来的是不是在类型里面(白名单套餐)--------------------");
        Map<BigDecimal, BigDecimal> map = new HashMap<BigDecimal, BigDecimal>();
        Map<BigDecimal, List<OrderVo>> mapList = new HashMap<BigDecimal, List<OrderVo>>();
        Set<BigDecimal> setId = new HashSet<BigDecimal>();
        for (OrderVo ordervo : orderList) {
            // 当包含产品sku并且商品不在套餐中时才可以将商品计入优惠券范围
            if (ordervo.getProductType() != CouponProductType.SINGLE.getKey().intValue()) {

                // 如果是套餐，则遍历套餐内所有商品都在优惠券使用内才计入优惠券范围
                BigDecimal comboid = ordervo.getRelationId();// 优惠券ID
                if (!setId.contains(comboid)) {
                    setId.add(ordervo.getRelationId());
                    List<OrderVo> listOrder = new ArrayList<OrderVo>();
                    listOrder.add(ordervo);
                    mapList.put(comboid, listOrder);
                } else {
                    List<OrderVo> listOrder = mapList.get(comboid);
                    listOrder.add(ordervo);
                    mapList.put(comboid, listOrder);
                }
            }
        }
        Iterator<BigDecimal> it = setId.iterator();
        for (; it.hasNext();) {
            BigDecimal bigid = it.next();
            List<OrderVo> listOrder = mapList.get(bigid);
            Boolean bool = true;
            for (OrderVo orderV : listOrder) {
                ViewProductSkuExample example = new ViewProductSkuExample();
                example.createCriteria().andProductSkuCodeEqualTo(orderV.getProductSkuCode());
                List<ViewProductSku> vList = viewProductSkuDao.selectByExample(example);
                if (vList != null && vList.size() > 0) {
                    ViewProductSku v = vList.get(0);
                    String cateproduct = v.getCategoryId() + "";
                    if (couponCateList != null && couponCateList.size() > 0) {
                        if (!couponCateList.contains(cateproduct.trim())) {
                            bool = false;
                            break;
                        }
                    } else {
                        // 当类目为空时，需校验是否在供应商范围内
                        if (shopCodes == null || shopCodes.equals("")) {
                            bool = false;
                            break;
                        }
                    }
                }

            }
            if (bool) {
                map.put(bigid, listOrder.get(0).getRelationPrice());
            }

        }

        return map;
    }

    /**
     * 判断传来的是不是在类型里面
     * 
     * @param orderList
     * @param couponCateList
     * @return
     * @throws Exception
     */
    private Map<String, BigDecimal> canUseCouponCate(List<OrderVo> orderList,
            List<String> couponCateList, String shopCodes) throws Exception {
        logger.info(
                "----------------------------canUseCouponCate：判断传来的是不是在类型里面--------------------");

        Map<String, BigDecimal> map = Maps.newHashMap();


        // 20150619 add begin 将优惠券所指定的商家shopCodes拆分放入map中,避免用indexOf操作出现 1258 包含258 类似的组合情况
        Map<String, String> shopCodeForCouponSingle = new HashMap<String, String>();
        // 分隔规则所对应的指定入驻商家
        if (shopCodes != null && !shopCodes.isEmpty()) {
            StringTokenizer st = new StringTokenizer(shopCodes, ",");
            while (st.hasMoreTokens()) {
                String temp = st.nextToken();
                shopCodeForCouponSingle.put(temp, temp);
            }
        }
        // 20150619 add end 将优惠券所指定的商家shopCodes拆分放入map中,避免用indexOf操作出现 1258 包含258 类似的组合情况

        for (OrderVo ordervo : orderList) {
            // 当产品不在套餐中时才继续判断是否在优惠券使用范围内
            if (ordervo.getProductType().equals(CouponProductType.SINGLE.getKey())) {
                ViewProductSkuExample example = new ViewProductSkuExample();
                example.createCriteria().andProductSkuCodeEqualTo(ordervo.getProductSkuCode());
                List<ViewProductSku> vList = viewProductSkuDao.selectByExample(example);
                if (vList != null && vList.size() > 0) {
                    ViewProductSku v = vList.get(0);
                    Product p = productDao.selectByPrimaryKey(v.getProductId());
                    String cateproduct = v.getCategoryId() + "";
                    // 当存在类目时
                    if (couponCateList != null && couponCateList.size() > 0) {
                        if (couponCateList.contains(cateproduct.trim())) {
                            // 当既存在类目范围又存在供应商范围时，取其交集校验

                            // 20150619 update 将 indexOf操作改为containsKey比较
                            if (!shopCodeForCouponSingle.isEmpty()
                                    && shopCodeForCouponSingle.containsKey(p.getShopCode())) {
                                map.put(ordervo.getProductSkuCode(),
                                        ordervo.getProductTotlePrice());
                            } else {
                                // 当存在类目不存在供应商范围时
                                map.put(ordervo.getProductSkuCode(),
                                        ordervo.getProductTotlePrice());
                            }
                            // 注释开始
                            // if(shopCodes!=null&&!shopCodes.equals("")){
                            // if(shopCodes.indexOf(p.getShopCode().trim())!=-1){
                            // map.put(ordervo.getProductSkuCode(), ordervo.getProductTotlePrice());
                            // }
                            // }else{
                            // //当存在类目不存在供应商范围时
                            // map.put(ordervo.getProductSkuCode(), ordervo.getProductTotlePrice());
                            // } 注释掉 end
                            // 20150619 update end

                        }
                    } else {

                        // 20150619 update 将原先的indexOf操作变为containkey操作 begin
                        if (!shopCodeForCouponSingle.isEmpty()
                                && shopCodeForCouponSingle.containsKey(p.getShopCode())) {
                            map.put(ordervo.getProductSkuCode(), ordervo.getProductTotlePrice());
                        }
                        // 20150619 update end

                        // 20150619 annotation begin (将indexof操作换成map中的containskey,这样避免 4258 出现258
                        // 类似组合的情况)
                        // 当类目为空时，需校验是否在供应商范围内
                        // if(shopCodes!=null&&!shopCodes.equals("")){
                        // if(shopCodes.indexOf(p.getShopCode().trim())!=-1){
                        // map.put(ordervo.getProductSkuCode(), ordervo.getProductTotlePrice());
                        // }
                        // }
                        // 20150619 annotation begin
                    }
                } ;
            }

        }
        return map;
    }

    /**
     * 判断传来的是不是在类型里面
     * 
     * @param orderList
     * @param couponCateList
     * @return
     * @throws Exception
     */
    private Map<String, BigDecimal> canUseBlackCouponCate(List<OrderVo> orderList,
            List<String> couponCateList, String shopCodes) throws Exception {
        logger.info(
                "----------------------------canUseCouponCate：判断传来的是不是在类型里面--------------------");



        // 20150619 add begin 将优惠券所指定的商家shopCodes拆分放入map中,避免用indexOf操作出现 1258 包含258 类似的组合情况
        Map<String, String> shopCodeForCouponSingle = new HashMap<String, String>();
        // 分隔规则所对应的指定入驻商家
        if (shopCodes != null && !shopCodes.isEmpty()) {
            StringTokenizer st = new StringTokenizer(shopCodes, ",");
            while (st.hasMoreTokens()) {
                String temp = st.nextToken();
                shopCodeForCouponSingle.put(temp, temp);
            }
        }
        // 20150619 add end 将优惠券所指定的商家shopCodes拆分放入map中,避免用indexOf操作出现 1258 包含258 类似的组合情况


        Map<String, BigDecimal> map = Maps.newHashMap();

        for (OrderVo ordervo : orderList) {
            // 当产品不在套餐中时才继续判断是否在优惠券使用范围内
            if (ordervo.getProductType().equals(CouponProductType.SINGLE.getKey())) {
                ViewProductSkuExample example = new ViewProductSkuExample();
                example.createCriteria().andProductSkuCodeEqualTo(ordervo.getProductSkuCode());
                List<ViewProductSku> vList = viewProductSkuDao.selectByExample(example);
                if (vList != null && vList.size() > 0) {
                    ViewProductSku v = vList.get(0);
                    Product p = productDao.selectByPrimaryKey(v.getProductId());
                    String cateproduct = v.getCategoryId() + "";
                    // 当存在类目时
                    if (couponCateList != null && couponCateList.size() > 0) {
                        if (!couponCateList.contains(cateproduct.trim())) {
                            // 当既存在类目范围又存在供应商范围时，取其交集校验

                            // 20150619 将indexOf操作改为containsKey操作
                            if (!shopCodeForCouponSingle.isEmpty()
                                    && shopCodeForCouponSingle.containsKey(p.getShopCode())) {
                                map.put(ordervo.getProductSkuCode(),
                                        ordervo.getProductTotlePrice());
                            } else {
                                // 当存在类目不存在供应商范围时
                                map.put(ordervo.getProductSkuCode(),
                                        ordervo.getProductTotlePrice());
                            }

                            // if(shopCodes!=null&&!shopCodes.equals("")){
                            // if(shopCodes.indexOf(p.getShopCode().trim())!=-1){
                            // map.put(ordervo.getProductSkuCode(), ordervo.getProductTotlePrice());
                            // }
                            // }else{
                            // //当存在类目不存在供应商范围时
                            // map.put(ordervo.getProductSkuCode(), ordervo.getProductTotlePrice());
                            // }
                        }
                    } else {
                        // 当类目为空时，需校验是否在供应商范围内
                        // if(shopCodes!=null&&!shopCodes.equals("")){
                        // if(shopCodes.indexOf(p.getShopCode().trim())!=-1){
                        // map.put(ordervo.getProductSkuCode(), ordervo.getProductTotlePrice());
                        // }
                        // }
                        // 20150619 将indexOf操作改为containsKey操作
                        if (!shopCodeForCouponSingle.isEmpty()
                                && shopCodeForCouponSingle.containsKey(p.getShopCode())) {
                            map.put(ordervo.getProductSkuCode(), ordervo.getProductTotlePrice());
                        }
                    }
                } ;
            }

        }
        return map;
    }

    /**
     * 判断传来的是不是在类型里面,商品为单品
     * 
     * @param orderList 订单商品,单品
     * @param couponCateList 优惠券规则选中的类目信息
     * @param listProduct 订单商品的产品信息（包含产品的类目）
     * @return
     * @throws Exception
     */
    private Map<String, BigDecimal> canUseCouponCates(List<OrderVo> orderList,
            List<String> cateRelationIdList, HashMap<String, Product> mapProduct) throws Exception {
        logger.info(
                "----------------------------canUseCouponCate：判断传来的是不是在类型里面--------------------");

        Map<String, BigDecimal> map = Maps.newHashMap();
        if (cateRelationIdList != null && cateRelationIdList.size() > 0) {
            Product curProduct = new Product();
            String categoryId = "";
            for (OrderVo ordervo : orderList) {
                curProduct = mapProduct.get(ordervo.getProductSkuCode());
                categoryId = curProduct.getCategoryId() + "";
                if (cateRelationIdList.contains(categoryId.trim())) {
                    map.put(ordervo.getProductSkuCode(), ordervo.getProductTotlePrice());
                }
            }
        }
        return map;
    }

    /**
     * 返回商品中为套餐的商品
     * 
     * @param orderList 订单商品信息
     * @return
     */
    private Map<String, Map<BigDecimal, List<OrderVo>>> getComboInfoAndSingleList(
            List<OrderVo> orderList) {
        Map<String, Map<BigDecimal, List<OrderVo>>> comboAndSingleList =
                new HashMap<String, Map<BigDecimal, List<OrderVo>>>();
        // 单品
        Map<BigDecimal, List<OrderVo>> singleMapList = new HashMap<BigDecimal, List<OrderVo>>();
        List<OrderVo> singleList = new ArrayList<OrderVo>();
        // 套餐id对应商品列表
        Map<BigDecimal, List<OrderVo>> mapList = new HashMap<BigDecimal, List<OrderVo>>();
        Set<BigDecimal> setId = new HashSet<BigDecimal>();
        // 商品分套餐
        for (OrderVo ordervo : orderList) {
            // 商品未套餐
            if (ordervo.getProductType() != CouponProductType.SINGLE.getKey().intValue()) {
                BigDecimal comboid = ordervo.getRelationId();// 套餐ID
                if (!setId.contains(comboid)) {
                    setId.add(ordervo.getRelationId());
                    List<OrderVo> listOrder = new ArrayList<OrderVo>();
                    listOrder.add(ordervo);
                    mapList.put(comboid, listOrder);
                } else {
                    // 包含存入商品
                    List<OrderVo> listOrder = mapList.get(comboid);
                    listOrder.add(ordervo);
                    mapList.put(comboid, listOrder);
                }
            } else {
                singleList.add(ordervo);
            }
        }
        // 套餐商品
        comboAndSingleList.put("combo", mapList);
        // 单品
        singleMapList.put(BigDecimal.ZERO, singleList);
        comboAndSingleList.put("single", singleMapList);
        return comboAndSingleList;
    }

    /**
     * 过滤出套餐信息（白名单），遍历套餐内所有商品都在优惠券使用内才计入优惠券范围
     * 
     * @param combos 套餐商品
     * @param couponProductList 规则所选的产品范围
     * @return
     */
    private Map<BigDecimal, BigDecimal> canUseCouponComboInfo(Map<BigDecimal, List<OrderVo>> combos,
            List<String> couponProductList) {
        Map<BigDecimal, BigDecimal> map = Maps.newHashMap();
        Iterator<BigDecimal> it = combos.keySet().iterator();
        if (couponProductList != null && couponProductList.size() > 0) {
            BigDecimal bigid = null;
            List<OrderVo> listCombos = null;
            Boolean bool = true;
            for (; it.hasNext();) {
                bigid = it.next();
                listCombos = combos.get(bigid);
                bool = true;
                for (OrderVo orderV : listCombos) {
                    if (!couponProductList.contains(orderV.getProductSkuCode())) {
                        bool = false;
                        break;
                    }
                }
                if (bool) {
                    map.put(bigid, listCombos.get(0).getRelationPrice());
                }
            }
        }

        return map;
    }

    /**
     * 过滤出套餐信息（黑名单），返回黑名单下不可用的商品，只有套餐内所有商品都在优惠券使用范围内才计入优惠券范围
     * 
     * @param combos 套餐商品
     * @param couponProductList 规则所选的产品范围
     * @return
     */
    private Map<BigDecimal, BigDecimal> canUnUseCouponComboInfo(
            Map<BigDecimal, List<OrderVo>> combos, List<String> couponProductList) {
        Map<BigDecimal, BigDecimal> map = Maps.newHashMap();
        Iterator<BigDecimal> it = combos.keySet().iterator();
        if (couponProductList != null && couponProductList.size() > 0) {
            for (; it.hasNext();) {
                BigDecimal bigid = it.next();
                List<OrderVo> listCombos = combos.get(bigid);
                for (OrderVo orderV : listCombos) {
                    if (couponProductList.contains(orderV.getProductSkuCode())) {
                        map.put(bigid, listCombos.get(0).getRelationPrice());
                        break;
                    }
                }
            }
        }
        return map;
    }

    /**
     * 判断传来的是不是在类型里面(白名单套餐) 遍历套餐内所有商品都在优惠券使用内才计入优惠券范围
     * 
     * @param combos 套餐商品
     * @param couponCateList 规则所选的类目范围
     * @param listProduct 订单商品的产品信息（包含产品的类目）
     * @return
     */
    private Map<BigDecimal, BigDecimal> comboCouponCates(Map<BigDecimal, List<OrderVo>> combos,
            List<String> cateRelationIdList, HashMap<String, Product> mapProduct) throws Exception {
        logger.info(
                "----------------------------comboCouponCate：判断传来的是不是在类型里面(白名单套餐)--------------------");
        Map<BigDecimal, BigDecimal> map = new HashMap<BigDecimal, BigDecimal>();
        Iterator<BigDecimal> it = combos.keySet().iterator();
        if (cateRelationIdList != null && cateRelationIdList.size() > 0) {
            // 当前循环的商品
            Product curProduct = new Product();
            String categoryId = "";
            Boolean bool = true;
            BigDecimal bigid = null;
            List<OrderVo> listCombos = null;
            for (; it.hasNext();) {
                bigid = it.next();
                listCombos = combos.get(bigid);
                bool = true;
                for (OrderVo orderV : listCombos) {
                    curProduct = mapProduct.get(orderV.getProductSkuCode());
                    categoryId = curProduct.getCategoryId() + "";
                    if (!cateRelationIdList.contains(categoryId.trim())) {
                        bool = false;
                        break;
                    }
                }
                if (bool) {
                    map.put(bigid, listCombos.get(0).getRelationPrice());
                }
            }
        }
        return map;
    }

    /**
     * 返回黑名单下不可用的套餐(黑名单套餐) ，只有套餐内所有商品都在优惠券使用内才计入优惠券范围
     * 
     * @param combos 套餐商品
     * @param couponCateList 规则所选的类目范围
     * @param listProduct 订单商品的产品信息（包含产品的类目）
     * @return
     */
    private Map<BigDecimal, BigDecimal> comboUnUseCouponCates(Map<BigDecimal, List<OrderVo>> combos,
            List<String> cateRelationIdList, HashMap<String, Product> mapProduct) throws Exception {
        logger.info(
                "----------------------------comboCouponCate：判断传来的是不是在类型里面(白名单套餐)--------------------");
        Map<BigDecimal, BigDecimal> map = new HashMap<BigDecimal, BigDecimal>();
        Iterator<BigDecimal> it = combos.keySet().iterator();
        if (cateRelationIdList != null && cateRelationIdList.size() > 0) {
            // 当前循环的商品
            Product curProduct = new Product();
            String categoryId = "";
            BigDecimal bigid = null;
            List<OrderVo> listCombos = null;
            for (; it.hasNext();) {
                bigid = it.next();
                listCombos = combos.get(bigid);
                for (OrderVo orderV : listCombos) {
                    curProduct = mapProduct.get(orderV.getProductSkuCode());
                    categoryId = curProduct.getCategoryId() + "";
                    if (cateRelationIdList.contains(categoryId.trim())) {
                        map.put(bigid, listCombos.get(0).getRelationPrice());
                        break;
                    }
                }
            }
        }
        return map;
    }

    /**
     * 判断传来的是不是在类型里面(黑名单),商品为单品
     * 
     * @param orderList 订单商品
     * @param couponCateList 优惠券规则选中的类目信息
     * @param listProduct 订单商品的产品信息（包含产品的类目）
     * @return
     * @throws Exception
     */
    private Map<String, BigDecimal> canUseBlackCouponCate(List<OrderVo> orderList,
            List<String> cateRelationIdList, HashMap<String, Product> mapProduct) throws Exception {
        logger.info(
                "----------------------------canUseCouponCate：判断传来的是不是在类型里面--------------------");
        Map<String, BigDecimal> map = Maps.newHashMap();
        if (cateRelationIdList != null && cateRelationIdList.size() > 0) {
            Product curProduct = new Product();
            String categoryId = null;
            for (OrderVo ordervo : orderList) {
                // 当产品不在套餐中时才继续判断是否在优惠券使用范围内
                curProduct = mapProduct.get(ordervo.getProductSkuCode());
                categoryId = curProduct.getCategoryId() + "";
                if (!cateRelationIdList.contains(categoryId.trim())) {
                    map.put(ordervo.getProductSkuCode(), ordervo.getProductTotlePrice());
                }
            }
        }
        return map;
    }

    /**
     * 过滤出套餐信息（黑名单），遍历套餐内所有商品都在优惠券使用内才计入优惠券范围
     * 
     * @param combos 套餐商品
     * @param couponProductList 规则所选的产品范围
     * @return
     */
    private Map<BigDecimal, BigDecimal> canUseCouponComboBlackInfo(
            Map<BigDecimal, List<OrderVo>> combos, List<String> couponProductList) {
        Map<BigDecimal, BigDecimal> map = Maps.newHashMap();
        Iterator<BigDecimal> it = combos.keySet().iterator();
        if (couponProductList != null && couponProductList.size() > 0) {
            BigDecimal bigid = null;
            List<OrderVo> listCombos = null;
            Boolean bool = true;
            for (; it.hasNext();) {
                bigid = it.next();
                listCombos = combos.get(bigid);
                bool = true;
                for (OrderVo orderV : listCombos) {
                    if (couponProductList.contains(orderV.getProductSkuCode())) {
                        bool = false;
                        break;
                    }
                }
                if (bool) {
                    map.put(bigid, listCombos.get(0).getRelationPrice());
                }
            }
        }
        return map;
    }

    /**
     * 判断传来的是不是在类型里面(黑名单套餐) 遍历套餐内所有商品都在优惠券使用内才计入优惠券范围
     * 
     * @param combos 套餐商品
     * @param couponCateList 规则所选的类目范围
     * @param listProduct 订单商品的产品信息（包含产品的类目）
     * @return
     */
    private Map<BigDecimal, BigDecimal> comboBlackCouponCates(Map<BigDecimal, List<OrderVo>> combos,
            List<String> cateRelationIdList, HashMap<String, Product> mapProduct) throws Exception {
        logger.info(
                "----------------------------comboCouponCate：判断传来的是不是在类型里面(白名单套餐)--------------------");
        Map<BigDecimal, BigDecimal> map = new HashMap<BigDecimal, BigDecimal>();
        Iterator<BigDecimal> it = combos.keySet().iterator();
        if (cateRelationIdList != null && cateRelationIdList.size() > 0) {
            // 当前循环的商品
            Product curProduct = new Product();
            String categoryId = "";
            Boolean bool = true;
            for (; it.hasNext();) {
                BigDecimal bigid = it.next();
                List<OrderVo> listCombos = combos.get(bigid);
                bool = true;
                for (OrderVo orderV : listCombos) {
                    curProduct = mapProduct.get(orderV.getProductSkuCode());
                    categoryId = curProduct.getCategoryId() + "";
                    if (cateRelationIdList.contains(categoryId.trim())) {
                        bool = false;
                        break;
                    }
                }
                if (bool) {
                    map.put(bigid, listCombos.get(0).getRelationPrice());
                }
            }
        }
        return map;
    }

    /**
     * 优惠券发放动作,不管哪种类型的都是 0:优惠券规则不存在 -1:已过期 -2：优惠券id传参为空 -3:已发送过 以下类型通过此接口发放： 订单满足条件发放优惠券
     * 
     * @return
     */
    @Override
    public Long couponGrant(CouponGrant couponGrant) throws Exception {
        logger.info(
                "----------------------------couponGrant：优惠券发放动作--------------------   规则id：{},操作类型：{}",
                couponGrant.getCouponId(), couponGrant.getGrantType());
        // CustomerRemoteService costomerService = (CustomerRemoteService)
        // RemoteTool.getRemote("03",
        // "customerRemoteService");
        CouponGrantExample example = new CouponGrantExample();
        example.createCriteria().andGrantRelatedCodeEqualTo(couponGrant.getGrantRelatedCode())
                .andCouponStatusNotEqualTo(Short.valueOf("4"));
        List<CouponGrant> grantList = couponGrantDAO.selectByExample(example);
        if (couponGrant.getCouponId() != null) {
            Coupon c = coupondao.selectByPrimaryKey(couponGrant.getCouponId());
            if (c == null) {
                logger.info(
                        "----------------------------couponGrant：优惠券发放动作--------------------   规则id：{} ,操作类型：{},错误返回值 0",
                        couponGrant.getCouponId(), couponGrant.getGrantType());
                return 0L;
            } else if (coupondao.couponIsOutDate(c) == false) {
                logger.info(
                        "----------------------------couponGrant：优惠券发放动作--------------------   规则id：{},操作类型：{}, 错误返回值 -1",
                        couponGrant.getCouponId(), couponGrant.getGrantType());
                return -1L;
            } else if ((CouponGrantDetailType.ORDER_RELATEDACTIVITYGRANT.getType()
                    .equals(String.valueOf(couponGrant.getGrantType()))) && grantList.size() > 0) {
                logger.info(
                        "----------------------------couponGrant：优惠券发放动作--------------------   规则id：{} 操作类型：{}, 错误返回值 -3",
                        couponGrant.getCouponId(), couponGrant.getGrantType());
                return -3L;
            } else {
                couponGrant.setGrantCreattime(new Date());
                couponGrant.setGrantEndtime(c.getEndtime());
                couponGrant
                        .setGrantRemark(CouponGrantDetailType.ORDER_RELATEDACTIVITYGRANT.getTitle()
                                + ":关联编号" + couponGrant.getGrantRelatedCode());
                Long grantId = couponGrantdao.insertGrant(couponGrant);
                // 将优惠券规则改为已发放
                Coupon coupon = new Coupon();
                coupon.setStatus(new Long(CouponStatus.HAVEGIVE_COUPONSTATUS.getType()));
                coupon.setCouponId(couponGrant.getCouponId());
                coupondao.updateCouponStatus(coupon);

                // 插入数据到优惠券流水表
                Coupon couponNew = coupondao.selectByPrimaryKey(couponGrant.getCouponId());
                CouponGrantFlow couponGrantFlow = new CouponGrantFlow();
                if (grantId != null) {
                    couponGrantFlow.setCouponGrantId(Long.valueOf(grantId));
                }
                couponGrantFlow.setCreateDate(new Date());
                if (couponNew != null) {
                    couponGrantFlow.setCouponName(couponNew.getCouponName());
                }
                couponGrantFlow.setCouponId(coupon.getCouponId());
                couponGrantFlow.setOperatingPersonName("远程接口调用");

                //
                if (couponGrant.getCustomId() != null) {
                    // 20150618 update 将原先调用用户接口查询用户信息改成自己查询 begin
                    // UserInfoDO userCondition = new UserInfoDO();
                    // userCondition.setLoginId(couponGrant.getCustomId());
                    // List<UserInfoDO> customList = costomerService
                    // .selectByUserInfoDOByObj(userCondition);
                    UserInfoDO userInfo =
                            userInfoDao.queryUserInfo(Long.valueOf(couponGrant.getCustomId()));
                    // 20150618 update 将原先调用用户接口查询用户信息改成自己查询 end
                    couponGrantFlow.setCustomId(Long.valueOf(couponGrant.getCustomId()));
                    // couponGrantFlow.setCustomer(customList.get(0).getLoginAccount());
                    couponGrantFlow.setCustomer(userInfo.getLoginAccount());

                }

                if (couponGrant.getGrantCreateman() != null) {
                    couponGrantFlow
                            .setOperatingPerson(Long.valueOf(couponGrant.getGrantCreateman()));
                }
                couponGrantFlow.setCouponGrantFlowType(
                        Long.valueOf(CouponGrantFlowStatus.GIVE_COUPONFLOWSTATUS.getType()));
                if (CouponGrantDetailType.ORDER_RELATEDACTIVITYGRANT.getType()
                        .equals(String.valueOf(couponGrant.getGrantType()))) {
                    couponGrantFlow.setOrderCode(couponGrant.getGrantRelatedCode());
                    couponGrantFlow.setRemark("订单满足条件发放优惠券 ，优惠券操作类型为:"
                            + CouponGrantFlowStatus.GIVE_COUPONFLOWSTATUS.getTitle() + "  ，"
                            + "优惠券编号：" + couponGrant.getCouponId() + "，  优惠券发放id：" + grantId
                            + "，    订单号：" + couponGrant.getGrantRelatedCode() + "，  操作人："
                            + couponGrantFlow.getOperatingPerson() + "  (COUPONGRANT)");

                } else {
                    couponGrantFlow.setRemark("发放优惠券 ，优惠券操作类型为:"
                            + CouponGrantFlowStatus.GIVE_COUPONFLOWSTATUS.getTitle() + "  ，"
                            + "优惠券编号：" + couponGrant.getCouponId() + "，  优惠券发放id：" + grantId
                            + "，  操作人：" + couponGrantFlow.getOperatingPerson() + "  (COUPONGRANT)");
                }
                // 插入优惠券流水信息
                couponGrantFlowDAO.insert(couponGrantFlow);
                return grantId;
            }
        } else {
            return -2L;
        }
    }

    /**
     * 抽奖奖品发放动作 0:优惠券规则不存在 -1:已过期 -2：优惠券id传参为空 -3：用户id为空
     * 
     * @return
     */
    @Override
    public Long couponGrantLottery(CouponGrant couponGrant) throws Exception {
        logger.info("----------------------------couponGrantLottery：抽奖奖品发放动作--------------------");
        // CustomerRemoteService costomerService = (CustomerRemoteService)
        // RemoteTool.getRemote("03",
        // "customerRemoteService");
        if (couponGrant.getCouponId() != null) {
            Coupon c = coupondao.selectByPrimaryKey(couponGrant.getCouponId());
            if (c == null) {
                return 0L;
            } else if (couponGrant.getCustomId() == null) {
                return -3L;
            } else if (coupondao.couponIsOutDate(c) == false) {
                return -1L;
            } else {
                couponGrant.setGrantCreattime(new Date());
                couponGrant.setGrantEndtime(c.getEndtime());
                couponGrant.setGrantRemark(
                        CouponGrantDetailType.LOTTERY_COUPONGRANTDETAILTYPE.getTitle() + ":关联编号"
                                + couponGrant.getGrantRelatedCode());
                Long grantId = couponGrantdao.insertGrant(couponGrant);
                // 将优惠券规则改为已发放
                Coupon coupon = new Coupon();
                coupon.setStatus(new Long(CouponStatus.HAVEGIVE_COUPONSTATUS.getType()));
                coupon.setCouponId(couponGrant.getCouponId());
                coupondao.updateCouponStatus(coupon);

                // 插入数据到优惠券流水表
                Coupon couponNew = coupondao.selectByPrimaryKey(couponGrant.getCouponId());
                CouponGrantFlow couponGrantFlow = new CouponGrantFlow();
                if (grantId != null) {
                    couponGrantFlow.setCouponGrantId(grantId);
                }
                couponGrantFlow.setCreateDate(new Date());
                if (couponNew != null) {
                    couponGrantFlow.setCouponName(couponNew.getCouponName());
                }

                couponGrantFlow.setCouponId(coupon.getCouponId());
                couponGrantFlow.setOperatingPersonName("远程接口调用");
                if (couponGrant.getCustomId() != null) {
                    // 20150618 update 将原先调用用户接口查询用户信息改成自己查询 begin
                    // UserInfoDO userCondition = new UserInfoDO();
                    // userCondition.setLoginId(couponGrant.getCustomId());
                    // List<UserInfoDO> customList = costomerService
                    // .selectByUserInfoDOByObj(userCondition);
                    // 20150618 update 将原先调用用户接口查询用户信息改成自己查询 end
                    UserInfoDO userInfo =
                            userInfoDao.queryUserInfo(Long.valueOf(couponGrant.getCustomId()));
                    couponGrantFlow.setCustomId(Long.valueOf(couponGrant.getCustomId()));
                    // couponGrantFlow.setCustomer(customList.get(0).getLoginAccount());
                    couponGrantFlow.setCustomer(userInfo.getLoginAccount());
                }

                if (couponGrant.getGrantCreateman() != null) {
                    couponGrantFlow
                            .setOperatingPerson(Long.valueOf(couponGrant.getGrantCreateman()));
                }
                couponGrantFlow.setCouponGrantFlowType(
                        Long.valueOf(CouponGrantFlowStatus.GIVE_COUPONFLOWSTATUS.getType()));
                if (couponGrantFlow.getOperatingPerson() != null) {
                    couponGrantFlow.setRemark("抽奖类型发放优惠券， 优惠券操作类型为:"
                            + CouponGrantFlowStatus.GIVE_COUPONFLOWSTATUS.getTitle() + "，"
                            + "   优惠券编号：" + couponGrant.getCouponId() + "，   发放id" + grantId
                            + "，      操作人：" + couponGrantFlow.getOperatingPerson());
                } else {
                    couponGrantFlow.setRemark("抽奖类型发放优惠券， 优惠券操作类型为:"
                            + CouponGrantFlowStatus.GIVE_COUPONFLOWSTATUS.getTitle() + "，"
                            + "   优惠券编号：" + couponGrant.getCouponId() + "，   发放id" + grantId);
                }
                // 插入优惠券流水信息
                couponGrantFlowDAO.insert(couponGrantFlow);
                return grantId;
            }
        } else {
            return -2L;
        }
    }

    /**
     * 优惠券批量发放动作,不管哪种类型的都是 0:优惠券规则不存在 -1:已过期 -2：优惠券id传参为空
     * 
     * @return
     */
    @Override
    public List<CouponGrant> couponGrantList(List<CouponGrant> couponGrantList) throws Exception {
        logger.info("----------------------------couponGrantList：优惠券批量发放动作--------------------");
        List<CouponGrant> resultList = new ArrayList<CouponGrant>();
        for (CouponGrant couponGrant : couponGrantList) {
            if (couponGrant.getCouponId() != null) {
                Coupon c = coupondao.selectByPrimaryKey(couponGrant.getCouponId());
                if (c == null) {
                    couponGrant.setCouponGrantId(0L);
                } else if (coupondao.couponIsOutDate(c) == false) {
                    couponGrant.setCouponGrantId(-1L);
                } else {
                    couponGrant.setGrantCreattime(new Date());
                    couponGrant.setGrantEndtime(c.getEndtime());
                    couponGrant.setGrantRemark(
                            CouponGrantDetailType.ORDER_RELATEDACTIVITYGRANT.getTitle() + ":关联编号"
                                    + couponGrant.getGrantRelatedCode());
                    Long grantId = couponGrantdao.insertGrant(couponGrant);
                    couponGrant.setCouponGrantId(grantId);

                    // 插入数据到优惠券流水表
                    CouponGrantFlow couponGrantFlow = new CouponGrantFlow();
                    couponGrantFlow.setCouponGrantId(Long.valueOf(grantId));
                    couponGrantFlow.setCreateDate(new Date());
                    couponGrantFlow.setCouponId(c.getCouponId());
                    couponGrantFlow.setCouponName(c.getCouponName());
                    couponGrantFlow.setOperatingPersonName("远程接口调用");
                    if (couponGrant.getCustomId() != null) {
                        couponGrantFlow.setCustomId(Long.valueOf(couponGrant.getCustomId()));
                        couponGrantFlow.setCustomer("操作人id为：" + couponGrant.getCustomId());
                    }
                    if (couponGrant.getGrantRelatedCode() != null) {
                        couponGrantFlow.setOrderCode(couponGrant.getGrantRelatedCode());
                    }
                    if (couponGrant.getGrantCreateman() != null) {
                        couponGrantFlow
                                .setOperatingPerson(Long.valueOf(couponGrant.getGrantCreateman()));
                    }
                    couponGrantFlow.setCouponGrantFlowType(
                            Long.valueOf(CouponGrantFlowStatus.GIVE_COUPONFLOWSTATUS.getType()));
                    couponGrantFlow.setRemark("发放优惠券 优惠券操作类型为:"
                            + CouponGrantFlowStatus.FREEZE_COUPONFLOWSTATUS.getTitle() + "，"
                            + "优惠券编号：" + couponGrant.getCouponId() + "发放id" + grantId + "订单号："
                            + couponGrantFlow.getOrderCode() + "操作人："
                            + couponGrantFlow.getOperatingPerson());
                    // 插入优惠券流水信息
                    couponGrantFlowDAO.insert(couponGrantFlow);
                }
            } else {
                couponGrant.setCouponGrantId(-2L);
            }
            resultList.add(couponGrant);
        }
        return resultList;

    }


    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED,
            rollbackFor = ServiceException.class)
    public boolean activateGiftcoupon(Long couponGrantId, Integer customerId)
            throws ServiceException {
        if (couponGrantId == null || customerId == null) {
            return false;
        }
        boolean flag = false;
        int count = 0;
        try {
            CouponGrant couponGrant = couponGrantdao.selectCouponById(couponGrantId);
            if (couponGrant == null || couponGrant.getCouponId() == null) {
                return false;
            }
            Coupon coupon = coupondao.selectByPrimaryKey(couponGrant.getCouponId());
            if (coupon.getStatus()
                    .longValue() == new Long(CouponStatus.NOTUSE_COUPONSTATUS.getType())
                            .longValue()) {
                coupon.setCouponId(couponGrant.getCouponId());
                coupon.setStatus(new Long(CouponStatus.HAVEGIVE_COUPONSTATUS.getType()));
                count = coupondao.updateByPrimaryKeySelective(coupon);
                if (count < 1) {
                    return false;
                }
            }
            count = coupondao.activateGiftcoupon(couponGrantId, customerId.toString());
            if (count > 0) {
                flag = true;
            }
        } catch (Exception e) {
            logger.error("礼品券激活失败!礼品券ID：{},用户ID：{}", couponGrantId, customerId, e);
            flag = false;
            throw new ServiceException("Gift vouchers activation failure!");
        }
        return flag;
    }

    /**
     * 注册类型发放优惠券+时代首次登陆发放注册类型优惠券
     */
    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED,
            rollbackFor = Exception.class)
    public boolean grantCouponForCommonRegist(UserInfoDO user) throws Exception {
        boolean resultFlag = true;


        if (user.getLoginId() == null || user.getLoginAccount() == null
                || user.getLoginAccount().isEmpty()) {
            logger.error("注册时发放优惠券失败 ----> loginId为空或者loginAccount为空!");
            return false;
        }
        try {
            // 查询所有符合注册发放的发放设置
            List<CouponGrantSeting> setList = couponGrantSetDao.queryGrantSetForRegist();
            if (setList == null || setList.size() < 1) {
                logger.info("注册时发放优惠券  --->尚未找到任何符合注册类型发放的发放设置!");
                return false;
            }

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date now = new Date();

            // 存储符合条件的发放实体
            List<CouponGrant> couponGrantForAdd = new ArrayList<CouponGrant>();

            for (CouponGrantSeting set : setList) {
                CouponGrant couponGrant = new CouponGrant();
                couponGrant.setCouponIssuingId(set.getCouponIssuingId());
                couponGrant.setCouponId(set.getCoupon().getCouponId());
                couponGrant.setCustomId(user.getLoginId().intValue());
                couponGrant.setCustomerName(user.getLoginAccount());
                couponGrant.setCouponName(set.getCoupon().getCouponName());
                couponGrant
                        .setCouponStatus(Long.valueOf(CouponStatus.NOTUSE_COUPONSTATUS.getType()));
                couponGrant.setGrantCreattime(dateFormat.parse(dateFormat.format(now)));
                couponGrant.setGrantType(
                        Long.valueOf(CouponGrantDetailType.REGIST_COUPONGRANTDETAILTYPE.getType()));
                couponGrant.setGrantRemark("注册发放loginAccount为" + user.getLoginAccount());
                couponGrant.setActiveCode("1"); // 1为激活状态
                couponGrant.setActTime(couponGrant.getGrantCreattime());

                // 如果固定天数
                if ("2".equals(set.getCoupon().getTimeType().toString())) {

                    // 限制天数,则开始日期为 当前时间 结束时间为 当前时间加固定天数
                    Calendar cd = Calendar.getInstance();
                    cd.setTime(now);
                    cd.add(Calendar.DATE, set.getCoupon().getCouponValidDay().intValue());

                    couponGrant.setStartTime(dateFormat.parse(dateFormat.format(now)));
                    couponGrant.setEndTime(dateFormat.parse(dateFormat.format(cd.getTime())));

                } else {
                    couponGrant.setStartTime(set.getCoupon().getStarttime());
                    couponGrant.setEndTime(set.getCoupon().getEndtime());
                }

                couponGrantForAdd.add(couponGrant);
            }
            // 开始发放,并且录入流水
            if (couponGrantForAdd.size() > 0) {
                for (CouponGrant grant : couponGrantForAdd) {
                    Long couponGrantId = couponGrantdao.insertGrant(grant);
                    CouponGrantFlow record = new CouponGrantFlow();
                    record.setCouponGrantFlowType(
                            Long.valueOf(CouponGrantFlowStatus.GIVE_COUPONFLOWSTATUS.getType()));
                    record.setCreateDate(dateFormat.parse(dateFormat.format(now)));
                    record.setCouponGrantId(grant.getCouponGrantId());
                    record.setCouponId(grant.getCouponId());
                    record.setCouponGrantId(couponGrantId);
                    record.setCouponName(grant.getCouponName());
                    if (user.getLoginId() != null) {
                        record.setCustomId(Long.valueOf(user.getLoginId()));
                    }
                    record.setCustomer(user.getLoginAccount());
                    record.setOperatingPersonName("远程接口调用");
                    record.setRemark("注册发放,操作类型为:发放,优惠券编号：" + grant.getCouponId() + ",优惠券发放id："
                            + grant.getCouponGrantId() + ",关联号：" + grant.getCustomId() + "， 操作人： "
                            + record.getOperatingPersonName());
                    couponGrantFlowDAO.insert(record);
                }
            }

            logger.info("注册发放优惠券成功!");

        } catch (SQLException e) {
            resultFlag = false;
            logger.error("用户注册发放优惠券失败,用户ID={},用户名:{},具体异常信息为:", user.getLoginId(),
                    user.getLoginAccount(), e);
            throw new ServiceException("注册发放优惠券失败,具体异常信息为:" + e.getMessage());
        } catch (ParseException e) {
            resultFlag = false;
            logger.error("用户注册发放优惠券失败,用户ID={},用户名:{},具体异常信息为:", user.getLoginId(),
                    user.getLoginAccount(), e);
        }
        return resultFlag;
    }

    /**
     * 时代首次登录发放注册类型优惠券
     */
    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED,
            rollbackFor = ServiceException.class)
    public boolean grantCouponForSdFirstLogin(User user) throws Exception {
        boolean resultFlag = true;
        if (user.getLoginId() == null || user.getLoginAccount() == null
                || user.getLoginAccount().isEmpty()) {
            logger.error("注册发放优惠券失败 ----> loginId为空或者loginAccount为空!");
            return false;
        }
        try {
            // 查询所有符合时代首次发放的发放设置（发放注册类型优惠券）
            List<CouponGrantSeting> setList = couponGrantSetDao.queryGrantSetForRegist();
            if (setList == null || setList.size() < 1) {
                logger.info("注册发放优惠券  --->尚未找到任何符合注册类型发放的发放设置!");
                return false;
            }

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date now = new Date();

            // 存储符合条件的发放实体
            List<CouponGrant> couponGrantForAdd = new ArrayList<CouponGrant>();

            for (CouponGrantSeting set : setList) {
                CouponGrant couponGrant = new CouponGrant();
                couponGrant.setCouponIssuingId(set.getCouponIssuingId());
                couponGrant.setCouponId(set.getCoupon().getCouponId());
                couponGrant.setCustomId(user.getLoginId().intValue());
                couponGrant.setCustomerName(user.getLoginAccount());
                couponGrant.setCouponName(set.getCoupon().getCouponName());
                couponGrant
                        .setCouponStatus(Long.valueOf(CouponStatus.NOTUSE_COUPONSTATUS.getType()));
                couponGrant.setGrantCreattime(dateFormat.parse(dateFormat.format(now)));
                couponGrant.setGrantType(
                        Long.valueOf(CouponGrantDetailType.REGIST_COUPONGRANTDETAILTYPE.getType()));
                couponGrant.setGrantRemark("注册账号为" + user.getLoginAccount());
                couponGrant.setActiveCode("1"); // 1为激活状态
                couponGrant.setActTime(couponGrant.getGrantCreattime());

                // 如果固定天数
                if ("2".equals(set.getCoupon().getTimeType().toString())) {

                    // 限制天数,则开始日期为 当前时间 结束时间为 当前时间加固定天数
                    Calendar cd = Calendar.getInstance();
                    cd.setTime(now);
                    cd.add(Calendar.DATE, set.getCoupon().getCouponValidDay().intValue());

                    couponGrant.setStartTime(dateFormat.parse(dateFormat.format(now)));
                    couponGrant.setEndTime(dateFormat.parse(dateFormat.format(cd.getTime())));

                } else {
                    couponGrant.setStartTime(set.getCoupon().getStarttime());
                    couponGrant.setEndTime(set.getCoupon().getEndtime());
                }

                couponGrantForAdd.add(couponGrant);
            }
            // 开始发放,并且录入流水
            if (couponGrantForAdd.size() > 0) {
                for (CouponGrant grant : couponGrantForAdd) {
                    Long couponGrantId = couponGrantdao.insertGrant(grant);
                    CouponGrantFlow record = new CouponGrantFlow();
                    record.setCouponGrantFlowType(
                            Long.valueOf(CouponGrantFlowStatus.GIVE_COUPONFLOWSTATUS.getType()));
                    record.setCreateDate(dateFormat.parse(dateFormat.format(now)));
                    record.setCouponGrantId(grant.getCouponGrantId());
                    record.setCouponId(grant.getCouponId());
                    record.setCouponGrantId(couponGrantId);
                    record.setCouponName(grant.getCouponName());
                    record.setCustomId(user.getLoginId());
                    record.setCustomer(user.getLoginAccount());
                    record.setOperatingPersonName("远程接口调用");
                    record.setRemark("注册发放,操作类型为:发放,优惠券编号：" + grant.getCouponId() + ",优惠券发放id："
                            + grant.getCouponGrantId() + ",关联号：" + grant.getCustomId() + "， 操作人： "
                            + record.getOperatingPersonName());
                    couponGrantFlowDAO.insert(record);
                }
            }

            logger.info("注册发放优惠券成功!");

        } catch (SQLException e) {
            e.printStackTrace();
            resultFlag = false;
            logger.error("注册发放优惠券失败,用户ID={},用户名:{},具体异常信息为:", user.getLoginId(),
                    user.getLoginAccount(), e);
            throw new ServiceException("注册发放优惠券失败,具体异常信息为:" + e.getMessage());
        } catch (ParseException e) {
            e.printStackTrace();
            resultFlag = false;
            logger.error("注册发放优惠券失败,用户ID={},用户名:{},具体异常信息为:", user.getLoginId(),
                    user.getLoginAccount(), e);
        }
        return resultFlag;

    }

    /**
     * 奖品类型发放优惠券
     * 
     */
    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED,
            rollbackFor = ServiceException.class)
    public boolean lotteryGivenCoupon(Integer customId, List<Long> couponIdList)
            throws ServiceException {
        boolean flag = false;
        List<Coupon> couponList = new ArrayList<Coupon>();
        if (customId == null || couponIdList.size() == 0) {
            flag = false;
            return flag;
        }
        try {

            // 20150618 update 将原先调用用户接口查询用户信息改成自己查询 begin
            // CustomerRemoteService costomerService = (CustomerRemoteService)
            // RemoteTool.getRemote("03",
            // "customerRemoteService");
            // UserInfoDO userCondition = new UserInfoDO();
            // userCondition.setLoginId(customId);
            // List<UserInfoDO> customList = costomerService
            // .selectByUserInfoDOByObj(userCondition);
            // UserInfoDO user = customList.get(0);
            UserInfoDO user = userInfoDao.queryUserInfo(Long.valueOf(customId));
            // 20150618 update 将原先调用用户接口查询用户信息改成自己查询 end


            // 发放表
            CouponGrant grantSave = new CouponGrant();
            grantSave.setCustomId(customId);
            grantSave.setCouponStatus(Long.parseLong(CouponStatus.NOTUSE_COUPONSTATUS.getType()));
            grantSave.setGrantCreateman(customId.longValue());
            grantSave.setGrantCreattime(new Date());
            grantSave.setGrantType(Long.parseLong(CouponGrantDetailType.LOTTERY_GRANT.getType()));
            grantSave.setActTime(new Date());
            SimpleDateFormat formate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");



            // 流水表
            CouponGrantFlow grantFlow = new CouponGrantFlow();
            grantFlow.setCouponGrantFlowType(
                    Long.parseLong(CouponGrantFlowStatus.GIVE_COUPONFLOWSTATUS.getType()));
            grantFlow.setCouponGrantFlowId(
                    Long.parseLong(CouponGrantFlowStatus.GIVE_COUPONFLOWSTATUS.getType()));
            grantFlow.setCreateDate(new Date());
            grantFlow.setOperatingPersonName("远程调用接口使用");
            grantFlow.setCustomer(user.getLoginAccount());
            grantFlow.setCustomId(customId.longValue());

            for (Long couponId : couponIdList) {
                if (couponId != null) {
                    Coupon couponNew = coupondao.selectByPrimaryKey(couponId);
                    // 根据优惠券ID 设置优惠券有效时间
                    if (couponNew.getCouponValidDay() != null) {
                        Calendar ca = Calendar.getInstance();
                        grantSave.setStartTime(formate.parse(formate.format(new Date())));
                        ca.add(Calendar.DATE, couponNew.getCouponValidDay().intValue());
                        grantSave.setEndTime(ca.getTime());
                        couponNew.setEndtime(ca.getTime());
                    } else {
                        if (couponNew.getEndtime().before(new Date())) {
                            grantSave.setCouponStatus(Long
                                    .parseLong(CouponStatus.HAVEPASSDATE_COUPONSTATUS.getType()));
                        }
                        grantSave.setStartTime(couponNew.getStarttime());
                        grantSave.setEndTime(couponNew.getEndtime());
                    }
                    grantSave.setCouponId(couponId);
                    Long grantId = couponGrantdao.insertGrant(grantSave);

                    grantFlow.setCouponName(couponNew.getCouponName());

                    grantFlow.setCouponId(couponId);
                    grantFlow.setCouponGrantId(grantId);
                    grantFlow.setRemark("抽奖类型发放优惠券， 优惠券操作类型为:"
                            + CouponGrantFlowStatus.GIVE_COUPONFLOWSTATUS.getTitle() + "，"
                            + "   优惠券编号：" + couponId + "，   发放id" + grantId + "，      操作人："
                            + customId);
                    couponGrantFlowDAO.insert(grantFlow);
                    couponList.add(couponNew);
                    logger.info("给用户{}发放优惠券成功,并且成功插入优惠券流水表:grantID:{},couponID:{}",
                            user.getLoginAccount(), grantId, couponId);
                }
            }
            flag = true;
        } catch (Exception e) {
            logger.error("给用户:{} 发放优惠券失败", customId, e);
            flag = false;
            throw new ServiceException("发放礼品优惠券失败");
        }
        return flag;
    }

    /**
     * 通用发放优惠券
     * 
     * @param couponMap: customId:用户id，couponIds:优惠券id，多个券用,分隔，
     *        couponGrantDetailType:优惠券发放明细类型,值取自类CouponGrantDetailType
     *        couponGrantFlowStatus:优惠券流水操作类型,值取自类couponGrantFlowStatus
     * @return false:发放失败，true:发放成功
     * @throws Exception
     */
    @Override
    public boolean generalGivenCoupon(Map<String, Object> couponMap) throws ServiceException {
        boolean flag = false;
        Long customId = (Long) couponMap.get("customId");
        String couponIds = (String) couponMap.get("couponIds");
        String couponGrantDetailType = (String) couponMap.get("couponGrantDetailType");
        // 优惠券流水操作类型
        String couponGrantFlowStatus = (String) couponMap.get("couponGrantFlowStatus");
        if (couponIds == null || "".equals(couponIds)) {
            return flag;
        }
        String[] couponIdList = couponIds.split(",");
        try {
            UserInfoDO user = userInfoDao.queryUserInfo(customId);
            for (String couponId : couponIdList) {
                Coupon couponNew = coupondao.selectByPrimaryKey(Long.valueOf(couponId));
                // 插入优惠券信息
                couponGrantService.insertCouponInfo(user, couponNew, couponGrantDetailType,
                        couponGrantFlowStatus);
            }
            flag = true;
        } catch (Exception e) {
            logger.error("给用户:{} 发放优惠券失败", customId, e);
            flag = false;
        }
        return flag;
    }



    /**
     * 订单类型发放优惠券
     * 
     */
    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED,
            rollbackFor = ServiceException.class)
    public Map<Long, OrderPreferential> orderGivenCoupon(Integer customId,
            List<OrderPreferential> orderCoupon) throws ServiceException {
        logger.info("订单开始调用了促销系统远程发送优惠券接口");
        Map<Long, OrderPreferential> orderCouponMap = new HashMap<Long, OrderPreferential>();
        List<Coupon> couponList = new ArrayList<Coupon>();
        if (orderCoupon == null || orderCoupon.size() == 0) {
            logger.info("订单参数传过来的优惠券list为空");
            return orderCouponMap;
        }
        try {

            // 20150618 update 将原先调用用户接口查询用户信息改成自己查询 begin

            // CustomerRemoteService costomerService = (CustomerRemoteService)
            // RemoteTool.getRemote("03",
            // "customerRemoteService");
            // UserInfoDO userCondition = new UserInfoDO();
            // userCondition.setLoginId(customId);
            // List<UserInfoDO> customList = costomerService
            // .selectByUserInfoDOByObj(userCondition);
            // UserInfoDO user = customList.get(0);
            UserInfoDO user = userInfoDao.queryUserInfo(Long.valueOf(customId));
            // 20150618 update 将原先调用用户接口查询用户信息改成自己查询 end
            SimpleDateFormat formate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Calendar ca = Calendar.getInstance();


            // 发放表
            CouponGrant grantSave = new CouponGrant();
            grantSave.setCustomId(customId);
            grantSave.setCouponStatus(Long.parseLong(CouponStatus.NOTUSE_COUPONSTATUS.getType()));
            grantSave.setGrantCreateman(customId.longValue());
            grantSave.setGrantCreattime(new Date());
            grantSave.setGrantType(
                    Long.parseLong(CouponGrantDetailType.ORDER_RELATEDACTIVITYGRANT.getType()));
            grantSave.setGrantRelatedCode(orderCoupon.get(0).getOrderCode());
            grantSave.setActTime(new Date());

            // 流水表
            CouponGrantFlow grantFlow = new CouponGrantFlow();
            grantFlow.setCouponGrantFlowType(
                    Long.parseLong(CouponGrantFlowStatus.GIVE_COUPONFLOWSTATUS.getType()));
            grantFlow.setCouponGrantFlowId(
                    Long.parseLong(CouponGrantFlowStatus.GIVE_COUPONFLOWSTATUS.getType()));
            grantFlow.setCreateDate(new Date());
            grantFlow.setOperatingPersonName("远程调用接口使用");
            grantFlow.setCustomer(user.getLoginAccount());
            grantFlow.setCustomId(customId.longValue());
            for (OrderPreferential orderPreFer : orderCoupon) {
                if (orderPreFer != null && orderPreFer.getCouponId() != null) {
                    CouponGrantExample example = new CouponGrantExample();
                    example.createCriteria().andGrantRelatedCodeEqualTo(orderPreFer.getOrderCode())
                            .andCouponStatusNotEqualTo(
                                    Short.valueOf(CouponStatus.HAVEUSE_COUPONSTATUS.getType()))
                            .andCouponIdEqualTo(new BigDecimal(orderPreFer.getCouponId()));
                    // 先校验有没有这个订单的关联优惠券
                    List<CouponGrant> grantList = couponGrantDAO.selectByExample(example);

                    if (grantList.size() == 0) {
                        logger.info("根据couponId:{}和订单code:{}校验，没有对应的发放记录，可以进行发放。",
                                orderPreFer.getCouponId(), orderPreFer.getOrderCode());
                        Coupon couponNew = coupondao
                                .selectByPrimaryKey(Long.parseLong(orderPreFer.getCouponId()));
                        // 根据优惠券ID 设置优惠券有效时间
                        if (couponNew.getCouponValidDay() != null) {
                            grantSave.setStartTime(formate.parse(formate.format(new Date())));
                            ca.add(Calendar.DATE, couponNew.getCouponValidDay().intValue());
                            grantSave.setEndTime(ca.getTime());
                            couponNew.setEndtime(ca.getTime());
                        } else { // 如果是日期类型的优惠券
                            if (couponNew.getEndtime().before(new Date())) { // 判断时间，设置优惠券的状态
                                grantSave.setCouponStatus(Long.parseLong(
                                        CouponStatus.HAVEPASSDATE_COUPONSTATUS.getType()));
                            }
                            grantSave.setStartTime(couponNew.getStarttime());
                            grantSave.setEndTime(couponNew.getEndtime());
                        }
                        grantSave.setCouponId(Long.parseLong(orderPreFer.getCouponId()));
                        Long grantId = couponGrantdao.insertGrant(grantSave);
                        orderPreFer.setGrantId(new BigDecimal(grantId));

                        grantFlow.setCouponName(couponNew.getCouponName());
                        grantFlow.setCouponId(Long.parseLong(orderPreFer.getCouponId()));
                        grantFlow.setCouponGrantId(grantId);
                        grantFlow.setOrderCode(orderPreFer.getOrderCode());
                        grantFlow.setRemark("订单满足条件发放优惠券， 优惠券操作类型为:"
                                + CouponGrantFlowStatus.GIVE_COUPONFLOWSTATUS.getTitle() + "，"
                                + "   优惠券编号：" + orderPreFer.getCouponId() + "，  优惠券发放id" + grantId
                                + "订单号：" + orderPreFer.getOrderCode() + "，      操作人：" + customId);
                        couponGrantFlowDAO.insert(grantFlow);
                        couponList.add(couponNew);
                        orderCouponMap.put(Long.parseLong(orderPreFer.getCouponId()), orderPreFer);
                        logger.info("给用户{}发放优惠券成功,并且成功插入优惠券流水表:grantID:{},couponID:{}",
                                user.getLoginAccount(), grantId, orderPreFer.getCouponId());
                        // 规则属于未发放时修改规则状态为已发放
                        if (Constants.COUPONSTATUSNOUSE.equals(couponNew.getStatus())) {
                            updateCouponStatus(couponNew);
                        }

                    }
                }
            }
        } catch (Exception e) {
            logger.error("订单满送接口，给用户:{} 发放优惠券失败", customId, e);
            throw new ServiceException("订单发放礼品优惠券失败");
        }
        return orderCouponMap;
    }

    /**
     * 
     * @param user
     * @param couponId
     * @param grantName 发放人
     * @param num 发放优惠券数量
     * @return
     * @throws Exception
     */
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED,
            rollbackFor = ServiceException.class)
    @Override
    public boolean rechargeGrantCoupon(User user, Long couponId, Long grantNameId, int num)
            throws ServiceException {
        boolean flag = false;
        try {
            Coupon coupon = new Coupon();
            coupon.setCouponId(couponId);
            Coupon c = coupondao.selectCoupon(coupon);
            if (c.getStatus() == 1) {
                coupon.setStatus(new Long(2));// 已发放
                // 更新规则状态
                CouponService.updateCouponStatus(coupon);
            }
            Date nowDate = new Date();
            Date startDate = null;
            Date endDate = null;
            if (c.getTimeType() == 1) {
                if (nowDate.after(c.getEndtime())) {
                    return false;// 优惠券过期
                }
                startDate = c.getStarttime();
                endDate = c.getEndtime();
            } else {
                startDate = nowDate;
                Calendar cal = Calendar.getInstance();
                cal.setTime(nowDate);
                cal.add(Calendar.DATE, c.getCouponValidDay().intValue());
                Date temp_date = cal.getTime();
                endDate = temp_date;
            }
            CouponGrant cgrant = null;
            for (int i = 0; i < num; i++) {
                cgrant = new CouponGrant();
                cgrant.setCouponId(c.getCouponId());
                cgrant.setCouponStatus(new Long(3));// 未使用
                cgrant.setGrantType(new Long(71));// 充值发放
                cgrant.setCustomId(user.getLoginId().intValue());
                cgrant.setGrantCreateman(grantNameId);
                if (startDate != null) {
                    cgrant.setStartTime(startDate);
                    cgrant.setEndTime(endDate);
                }
                // 创建优惠券
                Long couponGrantId = couponGrantSetService.inserCouponGrantOBJ(cgrant);
                // 开始发放,并且录入流水
                CouponGrantFlow record = new CouponGrantFlow();
                record.setCouponGrantFlowType(
                        Long.valueOf(CouponGrantFlowStatus.GIVE_COUPONFLOWSTATUS.getType()));
                record.setCreateDate(nowDate);
                record.setCouponGrantId(couponGrantId);
                record.setCouponId(cgrant.getCouponId());
                record.setCouponName(c.getCouponName());
                record.setOperatingPersonName("充值发放优惠券");
                record.setRemark("充值发放 ，优惠券操作类型为:发放，    优惠券编号：" + cgrant.getCouponId()
                        + "，     优惠券发放id：" + cgrant.getCouponGrantId() + "，   操作人： "
                        + record.getOperatingPersonName());
                couponGrantFlowDAO.insert(record);
                flag = true;
            }
        } catch (Exception e) {
            logger.error("充值发放优惠券失败", e);
            throw new ServiceException("充值发放优惠券失败");
        }

        return flag;
    }

    /**
     * 将可用优惠券的信息封装成一个HashMap对象 20150619 add
     * 
     * @param coupon
     * @param myGrant
     * @param orderTotlePrice
     * @return
     */
    public HashMap<String, String> putCanUseCouponInfoToMap(Coupon coupon,
            CouponGrant couponGrantVo) {
        HashMap<String, String> couponInfo = Maps.newHashMap();
        couponInfo.put("couponName", coupon.getCouponName());
        couponInfo.put("couponId", couponGrantVo.getCouponGrantId().toString());
        couponInfo.put("useLimitsType",
                coupon.getUseLimitsType() == null ? "" : coupon.getUseLimitsType());
        return couponInfo;
    }

    /**
     * 将添加到不可用优惠券的信息封装成一个方法 20150619 add
     * 
     * @param coupon
     * @param myGrant
     * @param orderTotlePrice
     * @return
     */
    public HashMap<String, String> putCouponInfoToMap(Coupon coupon, CouponGrant myGrant,
            BigDecimal orderTotlePrice) {
        Date now_date = new Date();
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String endDate = dateformat.format(myGrant.getEndTime());
        HashMap<String, String> couponInfo = Maps.newHashMap();
        couponInfo.put("couponName", coupon.getCouponName());
        couponInfo.put("availablePrice", "");
        couponInfo.put("couponLeastPrice", coupon.getPayLeastMoney().toString());
        couponInfo.put("couponId", coupon.getCouponId().toString());
        couponInfo.put("endDate", endDate);
        couponInfo.put("orderTotlePrice", orderTotlePrice.toString());
        couponInfo.put("couponMoney", coupon.getCouponMoney().toString());
        if (now_date.after(myGrant.getEndTime()) || now_date.before(myGrant.getStartTime())) {
            couponInfo.put("isTimeOut", "true");
        } else {
            couponInfo.put("isTimeOut", "false");
        }
        return couponInfo;
    }

    /**
     * 将不可用优惠券分装到HashMap中
     * 
     * @param coupon
     * @param myGrant
     * @param orderTotlePrice
     * @param couponType
     * @return
     */
    public HashMap<String, String> putUnUseCouponInfoToMap(Coupon coupon, CouponGrant myGrant,
            BigDecimal orderTotlePrice, String couponType) {
        Date now_date = new Date();
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String endDate = dateformat.format(myGrant.getEndTime());
        HashMap<String, String> couponInfo = Maps.newHashMap();
        couponInfo.put("couponName", coupon.getCouponName());
        couponInfo.put("availablePrice", "");
        couponInfo.put("couponLeastPrice", coupon.getPayLeastMoney().toString());
        couponInfo.put("couponId", coupon.getCouponId().toString());
        couponInfo.put("endDate", endDate);
        couponInfo.put("orderTotlePrice", orderTotlePrice.toString());
        couponInfo.put("couponMoney", coupon.getCouponMoney().toString());
        // 前端页面优先判断时间范围，isTimeOUt:true，表示优惠券未生效
        // 如果优惠券是生效的，即isTimeOut为false,再判断优惠券是否在使用范围内，isUserRange为true，表示不在使用范围内
        // isUserRange为false,则表示商品总额未达到优惠券消费金额
        if (now_date.after(myGrant.getEndTime()) || now_date.before(myGrant.getStartTime())) {
            couponInfo.put("isTimeOut", "true");
        } else {
            couponInfo.put("isTimeOut", "false");
        }
        // 表示优惠券不在使用范围内
        if (couponType.equals("1")) {
            couponInfo.put("isUseRange", "true");
        } else {
            couponInfo.put("isUseRange", "false");
        }
        return couponInfo;
    }

    @Override
    public boolean grantCouponForRegist(User user) throws ServiceException {
        boolean resultFlag = true;


        if (user.getLoginId() == null || user.getLoginAccount() == null
                || user.getLoginAccount().isEmpty()) {
            logger.error("注册时发放优惠券失败 ----> loginId为空或者loginAccount为空!");
            return false;
        }
        try {
            // 查询所有符合注册发放的发放设置
            List<CouponGrantSeting> setList = couponGrantSetDao.queryGrantSetForRegist();
            if (setList == null || setList.size() < 1) {
                logger.info("注册时发放优惠券  --->尚未找到任何符合注册类型发放的发放设置!");
                return false;
            }

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date now = new Date();

            // 存储符合条件的发放实体
            List<CouponGrant> couponGrantForAdd = new ArrayList<CouponGrant>();

            for (CouponGrantSeting set : setList) {
                CouponGrant couponGrant = new CouponGrant();
                couponGrant.setCouponIssuingId(set.getCouponIssuingId());
                couponGrant.setCouponId(set.getCoupon().getCouponId());
                couponGrant.setCustomId(user.getLoginId().intValue());
                couponGrant.setCustomerName(user.getLoginAccount());
                couponGrant.setCouponName(set.getCoupon().getCouponName());
                couponGrant
                        .setCouponStatus(Long.valueOf(CouponStatus.NOTUSE_COUPONSTATUS.getType()));
                couponGrant.setGrantCreattime(dateFormat.parse(dateFormat.format(now)));
                couponGrant.setGrantType(
                        Long.valueOf(CouponGrantDetailType.REGIST_COUPONGRANTDETAILTYPE.getType()));
                couponGrant.setGrantRemark("注册发放loginAccount为" + user.getLoginAccount());
                couponGrant.setActiveCode("1"); // 1为激活状态
                couponGrant.setActTime(couponGrant.getGrantCreattime());

                // 如果固定天数
                if ("2".equals(set.getCoupon().getTimeType().toString())) {

                    // 限制天数,则开始日期为 当前时间 结束时间为 当前时间加固定天数
                    Calendar cd = Calendar.getInstance();
                    cd.setTime(now);
                    cd.add(Calendar.DATE, set.getCoupon().getCouponValidDay().intValue());

                    couponGrant.setStartTime(dateFormat.parse(dateFormat.format(now)));
                    couponGrant.setEndTime(dateFormat.parse(dateFormat.format(cd.getTime())));

                } else {
                    couponGrant.setStartTime(set.getCoupon().getStarttime());
                    couponGrant.setEndTime(set.getCoupon().getEndtime());
                }

                couponGrantForAdd.add(couponGrant);
            }
            // 开始发放,并且录入流水
            if (couponGrantForAdd.size() > 0) {
                for (CouponGrant grant : couponGrantForAdd) {
                    Long couponGrantId = couponGrantdao.insertGrant(grant);
                    CouponGrantFlow record = new CouponGrantFlow();
                    record.setCouponGrantFlowType(
                            Long.valueOf(CouponGrantFlowStatus.GIVE_COUPONFLOWSTATUS.getType()));
                    record.setCreateDate(dateFormat.parse(dateFormat.format(now)));
                    record.setCouponGrantId(grant.getCouponGrantId());
                    record.setCouponId(grant.getCouponId());
                    record.setCouponGrantId(couponGrantId);
                    record.setCouponName(grant.getCouponName());
                    record.setCustomId(user.getLoginId());
                    record.setCustomer(user.getLoginAccount());
                    record.setOperatingPersonName("远程接口调用");
                    record.setRemark("注册发放,操作类型为:发放,优惠券编号：" + grant.getCouponId() + ",优惠券发放id："
                            + grant.getCouponGrantId() + ",关联号：" + grant.getCustomId() + "， 操作人： "
                            + record.getOperatingPersonName());
                    couponGrantFlowDAO.insert(record);
                }
            }

            logger.info("注册发放优惠券成功!");

        } catch (SQLException e) {
            e.printStackTrace();
            resultFlag = false;
            logger.error("用户注册发放优惠券失败,用户ID={},用户名:{},具体异常信息为:", user.getLoginId(),
                    user.getLoginAccount(), e);
            throw new ServiceException("注册发放优惠券失败,具体异常信息为:" + e.getMessage());
        } catch (ParseException e) {
            resultFlag = false;
            logger.error("用户注册发放优惠券失败,用户ID={},用户名:{},具体异常信息为:", user.getLoginId(),
                    user.getLoginAccount(), e);
        }
        return resultFlag;
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

    public ViewProductSkuDAO getViewProductSkuDao() {
        return viewProductSkuDao;
    }

    public void setViewProductSkuDao(ViewProductSkuDAO viewProductSkuDao) {
        this.viewProductSkuDao = viewProductSkuDao;
    }

    public static int getSUCCESS() {
        return SUCCESS;
    }

    public static void setSUCCESS(int sUCCESS) {
        SUCCESS = sUCCESS;
    }

    public static int getFAIL() {
        return FAIL;
    }

    public static void setFAIL(int fAIL) {
        FAIL = fAIL;
    }

}
