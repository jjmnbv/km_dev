package com.kmzyc.b2b.service.impl;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.kmzyc.b2b.dao.AccountInfoDao;
import com.kmzyc.b2b.dao.CouponDao;
import com.kmzyc.b2b.model.AccountInfo;
import com.kmzyc.b2b.model.Coupon;
import com.kmzyc.b2b.model.CouponGrant;
import com.kmzyc.b2b.model.InvoiceInfo;
import com.kmzyc.b2b.service.AccountInfoService;
import com.kmzyc.b2b.vo.PayMoney;
import com.kmzyc.framework.exception.ServiceException;
import com.kmzyc.promotion.app.vobject.OrderVo;
import com.kmzyc.promotion.remote.service.CouponRemoteService;
import com.kmzyc.zkconfig.ConfigurationUtil;
import com.whalin.MemCached.MemCachedClient;

// import com.km.framework.common.util.RemoteTool;

@Service
@SuppressWarnings("unchecked")
public class AccountInfoServiceImp implements AccountInfoService {

    public static final String ACCOUNT_INFO = "b2b_com_km_b2b_model_Account_ID_";
    // 账号信息缓存40分钟,比session时间（30min）稍微长一点
    private static final int ACCOUNT_INFO_TIME_OUT = 40 * 60 * 1000;
    private static final int SETTLEMENT_TIME_OUT =
            60 * 60 * Integer.parseInt(ConfigurationUtil.getString("SETTLEMENT_TIME_OUT")) * 1000;

    // private static Logger logger = Logger.getLogger(AccountInfoServiceImp.class);
    private static Logger logger = LoggerFactory.getLogger(AccountInfoServiceImp.class);

    @Resource(name = "accountInfoDaoImpl")
    private AccountInfoDao accontInfodao;

    @Resource(name = "memCachedClient")
    private MemCachedClient memCachedClient;

    @Resource(name = "couponDaoImpl")
    private CouponDao couponDao;

    @Resource
    private CouponRemoteService couponRemoteService;

    @Override
    public AccountInfo findByLoginId(Long NaccountID) throws ServiceException {

        try {
            return accontInfodao.findByLoginId(NaccountID);
        } catch (SQLException e) {
            throw new ServiceException(0, "查询账户信息出错", e);
        }
    }

    /**
     * 获取不可用优惠券
     */
    @Override
    public List<HashMap<String, String>> getUnavailableCoupon(Map<String, Object> map)
            throws ServiceException {
        List<HashMap<String, String>> couponList;
        try {
            // CouponRemoteService couponRemoteService =
            // (CouponRemoteService) RemoteTool.getRemote(Constants.PROMOTION_SYSTEM_CODE,
            // "couponService", 120000l, 120000l);
            couponList = couponRemoteService.getUnUseCoupon((List<OrderVo>) map.get("orderList"),
                    map.get("customId") + "", new BigDecimal(map.get("moneyCount") + ""),
                    Long.parseLong(map.get("checkSellerId") + ""));
        } catch (Exception e) {
            throw new ServiceException(e);
        }
        return couponList;
    }

    @Override
    public Coupon getCouponByCouponGrantId(Long couponId) {
        Coupon coupon = null;
        try {
            // String key = "coupon_" + couponId;
            // coupon = (Coupon) memCachedClient.get(key);
            // if (coupon == null) {

            coupon = this.couponDao.getCouponByCouponGrantId(couponId);
            // if (coupon != null) {
            // memCachedClient.set(key, coupon, expiry);
            // }
            // }
        } catch (SQLException e) {
            logger.error("查询优惠券异常", e);
        }
        return coupon;
    }

    /**
     * 查询用户可用优费券
     */
    @Override
    public List<Coupon> findCouponGrants(Map<String, Object> map, String usessionId)
            throws ServiceException {

        List<Coupon> couponList;
        try {
            List<CouponGrant> couponGrantId = this.couponDao.findCouponGrants(map);
            if (couponGrantId == null || couponGrantId.size() == 0) {
                return new ArrayList<>();
            }
            List<com.kmzyc.promotion.app.vobject.CouponGrant> couponGrantList = new ArrayList<>();
            for (CouponGrant grant : couponGrantId) {
                couponGrantList.add(grant.transFormToRemoteAddress());
            }
            // CouponRemoteService couponRemoteService =
            // (CouponRemoteService) RemoteTool.getRemote(Constants.PROMOTION_SYSTEM_CODE,
            // "couponService", 120000l, 120000l);
            Map<Long, com.kmzyc.promotion.app.vobject.CouponCanUse> couponCanUseMap =
                    couponRemoteService.canuseCoupon(couponGrantList,
                            (List<OrderVo>) map.get("orderList"),
                            new BigDecimal(map.get("moneyCount") + ""),
                            Long.parseLong(map.get("checkSellerId") + ""));
            if (couponCanUseMap == null || couponCanUseMap.size() == 0)
                return new ArrayList<>();
            Set<Long> set = couponCanUseMap.keySet();
            List<Long> list = new ArrayList<>(set);
            couponList = this.couponDao.findcouponsBygrantIds(list);
            saveCouponBondPriceTomem(couponCanUseMap, usessionId);
        } catch (Exception e) {
            logger.error("", e);
            throw new ServiceException(0, "查询优惠券信息失败");
        }
        return couponList;
    }

    /**
     * 保存优惠券信息
     */
    private void saveCouponBondPriceTomem(
            Map<Long, com.kmzyc.promotion.app.vobject.CouponCanUse> map, String loginId)
            throws ServiceException {
        memCachedClient.set(ConfigurationUtil.getString("COUPON_BOND_PRICE").concat(loginId), map);
    }

    /**
     * 保存可用不可用优惠券信息
     */
    @Override
    public void savaCouponsListCached(HashMap<String, List<HashMap<String, String>>> map,
            String sessionId) {
        memCachedClient.set(ConfigurationUtil.getString("COUPON_BOND_PRICE").concat(sessionId),
                map);
    }

    /**
     * 获取可用不可用优惠券信息
     */
    @Override
    public HashMap<String, List<HashMap<String, String>>> getCouponsListCached(String sessionId) {
        return (HashMap<String, List<HashMap<String, String>>>) this.memCachedClient
                .get(ConfigurationUtil.getString("COUPON_BOND_PRICE").concat(sessionId));
    }

    /**
     * 删除cached中的优惠券信息
     */
    @Override
    public void delCouponsListCached(String sessionId) {
        this.memCachedClient
                .delete(ConfigurationUtil.getString("COUPON_BOND_PRICE").concat(sessionId));
    }


    /**
     * 获取支付信息
     */
    @Override
    public Map<String, String> getPaymodelDeliveryInfoFromMemcache(String usessionId)
            throws ServiceException {
        return (Map<String, String>) memCachedClient
                .get(ConfigurationUtil.getString("PAYMENT_MODEL_CACHEDID").concat(usessionId));
    }

    /**
     * 保存配送和支付信息到memcache
     */
    @Override
    public void savePaymodelDeliveryInfo(String usessionId, Object obj) throws ServiceException {
        String key = ConfigurationUtil.getString("PAYMENT_MODEL_CACHEDID").concat(usessionId);
        Map<String, String> map = (Map<String, String>) this.memCachedClient.get(key);
        if (null != map && map.size() > 0) {
            this.memCachedClient.replace(key, obj, new Date(SETTLEMENT_TIME_OUT));
        } else {
            this.memCachedClient.add(key, obj, new Date(SETTLEMENT_TIME_OUT));
        }
    }

    /**
     * 从缓存中读取发票信息
     */
    @Override
    public InvoiceInfo getInvoiceInfoFormMemcache(String usessionId) throws ServiceException {
        return (InvoiceInfo) memCachedClient
                .get(ConfigurationUtil.getString("INVOICE_INFO").concat(usessionId));
    }

    /**
     * 持久化发票信息到缓存中
     */
    @Override
    public void saveInvoiceInfoToMemcache(String usessionId, InvoiceInfo invoiceInfo)
            throws ServiceException {
        String key = ConfigurationUtil.getString("INVOICE_INFO").concat(usessionId);
        Object obj = this.memCachedClient.get(key);
        Date expDate = new Date(SETTLEMENT_TIME_OUT);
        if (null != obj) {
            memCachedClient.replace(key, invoiceInfo, expDate);
        } else {
            memCachedClient.add(key, invoiceInfo, expDate);
        }
    }

    /**
     * 从缓存中移除发票信息
     */
    @Override
    public void removeInvoiceInfoToMemcache(String usessionId) throws ServiceException {
        memCachedClient.delete(ConfigurationUtil.getString("INVOICE_INFO").concat(usessionId));
    }

    /**
     * 清除支付信息
     */
    @Override
    public void clearMemcache(String usessionId) throws ServiceException {
        this.memCachedClient.delete(ConfigurationUtil.getString("PAYMONEY").concat(usessionId));
        this.memCachedClient
                .delete(ConfigurationUtil.getString("PAYMENT_MODEL_CACHEDID").concat(usessionId));
        this.memCachedClient.delete(ConfigurationUtil.getString("INVOICE_INFO").concat(usessionId));
        this.memCachedClient
                .delete(ConfigurationUtil.getString("ADDPRODUCTTOCARDTO").concat(usessionId));
        this.memCachedClient
                .delete(ConfigurationUtil.getString("ACCOUNT_BALANCE").concat(usessionId));
        this.memCachedClient.delete(ConfigurationUtil.getString("ADDRESS_INFO").concat(usessionId));
        this.memCachedClient
                .delete(ConfigurationUtil.getString("SHOPCAR_FOR_CHECK").concat(usessionId));
    }

    /**
     * 保存付款使用的金额到缓存中
     */
    @Override
    public void savePayMoneyToMemCache(String usessionId, PayMoney paymoney)
            throws ServiceException {
        String key = ConfigurationUtil.getString("PAYMONEY").concat(usessionId);
        boolean ok = this.memCachedClient.set(key, paymoney, new Date(SETTLEMENT_TIME_OUT));
        if (!ok) {
            throw new ServiceException(500, "系统异常，保存paymoney到memcache失败");
        }
    }

    /**
     * 获取付款使用的金额
     */
    @Override
    public PayMoney getPayMoneyFormMemCache(String usessionId) throws ServiceException {
        return (PayMoney) this.memCachedClient
                .get(ConfigurationUtil.getString("PAYMONEY").concat(usessionId));
    }

    @Override
    public void updatePayMoneyToMemCache(String usessionId, Coupon coupon) throws ServiceException {
        String key = ConfigurationUtil.getString("PAYMONEY").concat(usessionId);
        PayMoney pm = (PayMoney) this.memCachedClient.get(key);
        pm.setCouponid(coupon.getCouponId().toString());
        pm.setCouponMoney(coupon.getCouponMoney().doubleValue());
        pm.setCouponName(coupon.getCouponName());
        pm.setBalanceMoney(0.0);
        pm.setUseLimitsType(coupon.getUseLimitsType());
        this.memCachedClient.replace(key, pm, new Date(SETTLEMENT_TIME_OUT));
    }

    /**
     * 更新支付信息
     */
    @Override
    public void updatePayMoneyToMemCache(String usessionId, String fieldName, Object val)
            throws ServiceException {
        Class<?> objectClass = PayMoney.class;
        String key = ConfigurationUtil.getString("PAYMONEY").concat(usessionId);
        PayMoney pm = (PayMoney) this.memCachedClient.get(key);
        try {
            Class<?>[] parameterTypes = new Class[1];
            Field field = objectClass.getDeclaredField(fieldName);
            parameterTypes[0] = field.getType();
            String sb = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
            Method method = objectClass.getMethod(sb, parameterTypes);
            method.invoke(pm, new Object[] {val});
        } catch (Exception e) {
            logger.error("", e);
        }
        this.memCachedClient.replace(key, pm, new Date(SETTLEMENT_TIME_OUT));
    }

    /**
     * 根据登录ID查询帐号信息，结算用
     */
    @Override
    public AccountInfo getAccountInfoByLoginIdForSett(Long loginId) throws ServiceException {
        try {

            AccountInfo ai = accontInfodao.findAccountInfoByLoginIdForSett(loginId);
            String[] org;
            if (null != ai.getOrganCode() && (org = ai.getOrganCode().split(",")).length == 3) {
                ai.setOrganCode(org[0]);
                ai.setOrganDes(org[1]);
                ai.setPromId(org[2].length() > 0 ? Long.parseLong(org[2]) : null);
            }
            return ai;
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    /**
     * 将用户信息保存到memCached中，供查询优惠券时使用
     */
    @Override
    public void savaAccountInfoCached(AccountInfo accountInfo, String userId) {
        String key = ACCOUNT_INFO.concat(userId);
        Object obj = this.memCachedClient.get(key);
        Date expDateAccount = new Date(ACCOUNT_INFO_TIME_OUT);
        if (null != obj) {
            memCachedClient.replace(key, accountInfo, expDateAccount);
        } else {
            memCachedClient.add(key, accountInfo, expDateAccount);
        }
    }

    /**
     * 查询用户可用优费券
     */
    @Override
    public HashMap<String, List<Coupon>> findCouponGrants(List<OrderVo> orderVoList, String uid,
            BigDecimal culateMoney, Long checkSellerId) throws ServiceException {
        try {
            // CouponRemoteService couponRemoteService =
            // (CouponRemoteService) RemoteTool.getRemote(Constants.PROMOTION_SYSTEM_CODE,
            // "couponService", 120000l, 120000l);
            HashMap<String, List<HashMap<String, String>>> couponsMap =
                    couponRemoteService.getCanUseAndUnUseCoupon(orderVoList, uid, culateMoney,
                            Long.parseLong(checkSellerId + ""));
            List<Long> couponIds;
            HashMap<String, List<Coupon>> couponMap = new HashMap<>();
            if (couponsMap != null && !couponsMap.isEmpty()) {
                // 将优惠券数据缓存到memCached中，以sessionId为维度,提交订单的时候判断选择的优惠券是否在可以使用范围内
                this.savaCouponsListCached(couponsMap, uid);
                couponIds = new ArrayList<>();
                // 获取可用优惠券
                List<HashMap<String, String>> couponList = couponsMap.get("canUseCouponList");
                for (HashMap<String, String> map : couponList) {
                    if (null != map && !map.isEmpty()) {
                        couponIds.add(Long.parseLong(map.get("couponId")));
                    }
                }

                couponMap.put("canUseCouponList",
                        !couponIds.isEmpty() ? couponDao.findcouponsBygrantIds(couponIds) : null);
                // 获取不可用优惠券
                // outUseCoupon = couponsMap.get("unUseCouponList");
            }
            return couponMap;
        } catch (Exception e) {
            logger.error("", e);
            throw new ServiceException(0, "查询优惠券信息失败");
        }
    }

    /**
     * 结算页提交订单时，检验优惠券是否可用,传入的优惠券发放id与缓存中用户可以优惠券比较,true表示优惠券可以
     */
    @Override
    public boolean checkCoupon(String couponId, String longId) {
        // 从memCached查询用户优惠券数据
        HashMap<String, List<HashMap<String, String>>> couponsMap =
                this.getCouponsListCached(longId);
        boolean canUseFlag = false;
        if (couponsMap != null) {
            // 获取可用优惠券
            List<HashMap<String, String>> couponList = couponsMap.get("canUseCouponList");
            String canUseCouponId;
            for (HashMap<String, String> aCouponList : couponList) {
                canUseCouponId = aCouponList.get("couponId");
                if (canUseCouponId.equals(couponId)) {
                    canUseFlag = true;
                    break;
                }
            }
        }
        return canUseFlag;
    }
}
