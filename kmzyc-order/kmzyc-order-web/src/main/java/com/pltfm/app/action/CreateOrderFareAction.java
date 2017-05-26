package com.pltfm.app.action;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.promotion.app.vobject.Coupon;
import com.kmzyc.promotion.app.vobject.CouponGrant;
import com.kmzyc.promotion.remote.service.CouponRemoteService;
import com.opensymphony.xwork2.ActionContext;
import com.pltfm.app.entities.OrderMain;
import com.pltfm.app.service.FareService;
import com.pltfm.app.service.OrderDictionaryService;

@Controller("createOrderFareAction")
@Scope("prototype")
@SuppressWarnings("unchecked")
public class CreateOrderFareAction extends BaseAction {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private Logger log = Logger.getLogger(CreateOrderFareAction.class);

    private List typeList;

    public List getTypeList() {
        return typeList;
    }

    public void setTypeList(List typeList) {
        this.typeList = typeList;
    }

    private List deliveryDateTypeList;

    public List getDeliveryDateTypeList() {
        return deliveryDateTypeList;
    }

    public void setDeliveryDateTypeList(List deliveryDateTypeList) {
        this.deliveryDateTypeList = deliveryDateTypeList;
    }

    private List<Coupon> cgrantList = new ArrayList<Coupon>();

    public List<Coupon> getCgrantList() {
        return cgrantList;
    }

    public void setCgrantList(List<Coupon> cgrantList) {
        this.cgrantList = cgrantList;
    }

    @Resource
    private OrderDictionaryService orderDictionaryService;

    private OrderMain order;

    public OrderMain getOrder() {
        return order;
    }

    public void setOrder(OrderMain order) {
        this.order = order;
    }

    @Resource
    private FareService fareService;

    // 产品系统的优惠接口服务类
    @Resource
    private CouponRemoteService couponRemoteService;

    public String getFare() throws ServiceException {
        ActionContext ctx = ActionContext.getContext();
        HttpServletRequest request =
                (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
        String commoditySum = request.getParameter("commoditySum");
        String site = "B2B";

        BigDecimal fare = fareService.getFare(2L, new BigDecimal(commoditySum), new BigDecimal("3"),
                true, site);

        order = new OrderMain();
        order.setFare(fare);
        order.setAmountPayable(new BigDecimal(commoditySum).subtract(fare));
        typeList = orderDictionaryService.getDictionaryByType("Pay_Method");
        deliveryDateTypeList = orderDictionaryService.getDictionaryByType("Delivery_Date_Type");

        String customerID = request.getParameter("customerID");
        List<Integer> customIDList = new ArrayList<Integer>();
        customIDList.add(Integer.parseInt(customerID));
        // 定义接收接口返回的list
        List<CouponGrant> cgList = null;
        try {
            cgList = couponRemoteService.SelectCouponGrantList(customIDList);
        } catch (Exception e) {
            log.error("获取运费发生错误！", e);
        }
        if (cgList != null) {
            for (int i = 0; i < cgList.size(); i++) {
                CouponGrant couponGrant = cgList.get(i);
                List<Coupon> couponlist = couponGrant.getCoupon();
                cgrantList.add(couponlist.get(0));
            }
        }
        return "success";
    }

}
