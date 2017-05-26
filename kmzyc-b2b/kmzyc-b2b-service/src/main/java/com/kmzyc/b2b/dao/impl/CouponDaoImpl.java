package com.kmzyc.b2b.dao.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.kmzyc.b2b.dao.CouponDao;
import com.kmzyc.b2b.model.Coupon;
import com.kmzyc.b2b.model.CouponGrant;
import com.kmzyc.b2b.model.CouponGrantFlow;
import com.kmzyc.b2b.model.CouponIssuingSetting;
import com.km.framework.persistence.impl.DaoImpl;

@Repository
@SuppressWarnings("unchecked")
public class CouponDaoImpl extends DaoImpl implements CouponDao {
    @javax.annotation.Resource(name = "sqlMapClient")
    private com.ibatis.sqlmap.client.SqlMapClient sqlMapClient;

    /**
     * 获取用户下所有可用的券
     * 
     * @param loginId
     * @return
     */

    @Override
    public List<Coupon> queryCoupons(Map<String, Object> map) throws SQLException {
        return (List<Coupon>) this.sqlMapClient.queryForList("AccountInfo.findCoupons", map);
    }

    @Override
    public List<Coupon> findcouponsBygrantIds(List<Long> list) throws SQLException {
        return (List<Coupon>) this.sqlMapClient.queryForList("AccountInfo.findcouponsBygrantIds",
                list);
    }

    @Override
    public List<CouponGrant> findCouponGrants(Map<String, Object> map) throws SQLException {
        return (List<CouponGrant>) this.sqlMapClient.queryForList("CouponGrant.findCouponGrants",
                map);
    }

    public BigDecimal getcouponMoneyByCouponId(Long couponId) throws SQLException {
        Object obj = this.getCouponByCouponId(couponId);
        if (null == obj) {
            return new BigDecimal(0);
        } else {
            Coupon coupon = (Coupon) obj;
            return coupon.getCouponMoney();
        }
    }

    @Override
    public Coupon getCouponByCouponId(Long couponId) throws SQLException {

        Coupon coupon = new Coupon();
        coupon.setCouponId(couponId);
        return (Coupon) this.sqlMapClient.queryForObject(
                "COUPON.ibatorgenerated_selectByPrimaryKey", coupon);
    }

    @Override
    public CouponGrant queryCouponGrantByCouponId(Map<String, String> paramMap) throws SQLException {
        return (CouponGrant) this.sqlMapClient.queryForObject("COUPON.queryCouponGrantByCouponId",
                paramMap);
    }

    public BigDecimal getcouponMoneyByCouponGrantId(Long couponGrantId) throws SQLException {
        Coupon coupon =
                (Coupon) this.sqlMapClient.queryForObject("COUPON.queryCouponMoneyByCouponGrantId",
                        couponGrantId);
        return coupon.getCouponMoney();
    }

    public Coupon getCouponByCouponGrantId(Long couponGrantId) throws SQLException {
        return (Coupon) this.sqlMapClient.queryForObject("COUPON.queryCouponMoneyByCouponGrantId",
                couponGrantId);
    }

    @Override
    public CouponGrant getCouponGrantByUerIdAndCouponId(Map<String, Object> map)
            throws SQLException {

        return (CouponGrant) this.sqlMapClient.queryForObject(
                "CouponGrant.getCouponGrantByUserIdAndCouponId", map);
    }

    @Override
    public CouponGrant getCouponGrantByVo(CouponGrant couponGrant) throws SQLException {
        return (CouponGrant) this.sqlMapClient.queryForObject("CouponGrant.getCouponGrantByVo",
                couponGrant);
    }

    public CouponGrant getcouponByGrantCode(String grantCode) throws SQLException {
        return (CouponGrant) this.sqlMapClient.queryForObject("CouponGrant.getCouponbyGrantcode",
                grantCode);
    }

    @Override
    public Long insertCouponGrant(CouponGrant couponGrant) throws SQLException {
        return (Long) this.sqlMapClient.insert("CouponGrant.insertCouponGrant", couponGrant);
    }

    @Override
    public Long insertCouponIssuingSetting(CouponIssuingSetting setting) throws SQLException {
        return (Long) this.sqlMapClient.insert("COUPON.insertCouponIssuingSetting", setting);
    }

    @Override
    public Long insertCouponGrantFlow(CouponGrantFlow flow) throws SQLException {
        return (Long) this.sqlMapClient.insert("COUPON.insertCouponGrantFlow", flow);
    }

    @Override
    public Map couponGrantInfo(int CouponGrantId) throws SQLException {

        return (Map<String, BigDecimal>) sqlMapClient.queryForObject("COUPON.youkuCouponGrantInfo",
                CouponGrantId);
    }

    @Override
    public int updateCouponGrant(Map pMap) throws SQLException {
        int rows = sqlMapClient.update("COUPON.ibatorgenerated_updateCouponGrantById", pMap);
        return rows;
    }
}
