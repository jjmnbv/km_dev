package com.kmzyc.supplier.service.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import com.kmzyc.commons.exception.ServiceException;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.km.framework.page.Pagination;
import com.kmzyc.supplier.dao.CouponDAO;
import com.kmzyc.supplier.service.CouponService;
import com.kmzyc.promotion.app.vobject.Coupon;

@Service("couponService")
public class CouponServiceImpl implements CouponService {

    @Resource
    private CouponDAO couponDAO;

    @Override
    public Pagination queryCouponList(Pagination page, Coupon coupon) throws ServiceException {
        Map<String, Object> condition = new HashMap();
        if (StringUtils.isNotBlank(coupon.getCouponName())) {
            condition.put("couponName", coupon.getCouponName().trim());
        }
        if (coupon.getCouponGivetypeId() != null) {
            condition.put("couponGivetypeId", coupon.getCouponGivetypeId());
        }
        if (StringUtils.isNotBlank(coupon.getShopCode())) {
            condition.put("shopCode", coupon.getShopCode().trim());
        }
        page.setObjCondition(condition);
        try {
            return couponDAO.findCouponListByPage(page);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

}