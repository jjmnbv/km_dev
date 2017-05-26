package com.kmzyc.supplier.action;


import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.km.framework.page.Pagination;
import com.kmzyc.framework.constants.Constants;
import com.kmzyc.promotion.app.maps.CouponGrantTypeMap;
import com.kmzyc.promotion.app.maps.CouponStatusMap;
import com.kmzyc.promotion.app.vobject.Coupon;
import com.kmzyc.supplier.service.CouponService;
import com.kmzyc.supplier.service.ProductSkuService;
import com.pltfm.app.enums.IsValidStatus;
import com.pltfm.app.vobject.ViewProductSku;

@Controller("popupChoiceAction")
@Scope("prototype")
public class PopupChoiceAction extends SupplierBaseAction {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private ProductSkuService productSkuService;

    @Resource
    private CouponService couponService;

    private String spanId;

    private ViewProductSku viewProductSku;

    private String promotionId;

    // 优惠券实体
    private Coupon coupon;

    private String promotionType;

    private String divIndex;

    //SKU选择列表
    public String findAllProductMainSku() {
        try {
            if (viewProductSku == null) viewProductSku = new ViewProductSku();
            Pagination page = this.getPagination(Constants.VIEW_PAGE, Integer.parseInt(super.getPageSize()));
            viewProductSku.setSkuStatus(IsValidStatus.VALID.getStatus());
            pagintion = productSkuService.searchPage(getSupplyId(), viewProductSku, page);

            getAllCategoryList(viewProductSku.getBCategoryId(), viewProductSku.getMCategoryId());
        } catch (Exception e) {
            logger.error("商品SKU列表查询错误：", e);
            return ERROR;
        }

        setProductStatusMap();
        return SUCCESS;
    }

    /**
     * 优惠券查询方法
     */
    public String gotoQueryCouponList() {
        try {
            if (coupon == null) coupon = new Coupon();
            coupon.setShopCode(getSupplyId().toString());//供应商平台中，默认当前登陆供应商ID
            Pagination page = this.getPagination(Constants.VIEW_PAGE, Integer.parseInt(super.getPageSize()));
            getRequest().setAttribute("couponGrantType", CouponGrantTypeMap.getMap());
            getRequest().setAttribute("couponStatus", CouponStatusMap.getMap());
            this.pagintion = couponService.queryCouponList(page, coupon);
        } catch (Exception e) {
            logger.error("优惠券查询方法错误：", e);
            return ERROR;
        }

        return SUCCESS;
    }

    public ViewProductSku getViewProductSku() {
        return viewProductSku;
    }

    public void setViewProductSku(ViewProductSku viewProductSku) {
        this.viewProductSku = viewProductSku;
    }

    public Coupon getCoupon() {
        return coupon;
    }

    public void setCoupon(Coupon coupon) {
        this.coupon = coupon;
    }

    public String getSpanId() {
        return spanId;
    }

    public void setSpanId(String spanId) {
        this.spanId = spanId;
    }

    public String getPromotionId() {
        return promotionId;
    }

    public void setPromotionId(String promotionId) {
        this.promotionId = promotionId;
    }

    public String getPromotionType() {
        return promotionType;
    }

    public void setPromotionType(String promotionType) {
        this.promotionType = promotionType;
    }

    public String getDivIndex() {
        return divIndex;
    }

    public void setDivIndex(String divIndex) {
        this.divIndex = divIndex;
    }

}
