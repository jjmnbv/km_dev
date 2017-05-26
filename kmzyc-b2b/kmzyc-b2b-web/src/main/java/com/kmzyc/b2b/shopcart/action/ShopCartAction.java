package com.kmzyc.b2b.shopcart.action;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import com.km.framework.action.BaseAction;
import com.kmzyc.b2b.service.ShopCartInfoService;
import com.kmzyc.b2b.shopcart.util.ShopUtil;
import com.kmzyc.b2b.shopcart.vo.SellerShopList;
import com.kmzyc.b2b.shopcart.vo.ShopCart;
import com.kmzyc.b2b.shopcart.vo.ShopCartProduct;
import com.kmzyc.b2b.shopcart.vo.ShopCartUserInfo;
import com.kmzyc.framework.constants.Constants;


@Controller("shopCartAction")
public class ShopCartAction extends BaseAction {
    private static final long serialVersionUID = 9100769576660216802L;
    @Resource
    ShopCartInfoService shopCartInfoService;


    // private static Logger logger = Logger.getLogger(ShopCartAction.class);
    private static Logger logger = LoggerFactory.getLogger(ShopCartAction.class);

    // 购物车显示方法
    public String listShopCart() {

        try {
            HttpServletRequest request = this.getRequest();
            String uid = ShopUtil.getUid(request, getResponse());
            HttpSession session = request.getSession();
            String loginType = (String) session.getAttribute(Constants.SESSION_B2B_OR_ERA);
            boolean isLogin = session.getAttribute(Constants.SESSION_USER_ID) != null;
            request.setAttribute("isLogin", isLogin);
            request.setAttribute("loginType", loginType);
            ShopCartUserInfo info = new ShopCartUserInfo(uid, isLogin, loginType, null);
            ShopCart shopcart = shopCartInfoService.generateSettlement(info);
            if (shopcart != null) {
                Map<String, ShopCartProduct> normalMap = shopcart.getProductMap();
                SellerShopList shopList = shopcart.getSellerShopList();
                request.setAttribute("normalMap", normalMap);

                request.setAttribute("shopList", shopList);
                request.setAttribute("shopcart", shopcart);
                request.setAttribute("productErrorReminder", shopcart.getProductErrorReminder());
                request.setAttribute("fareMap", shopcart.getSupplierFareMap());
                request.setAttribute("giftStockMap", shopcart.getGiftStockMap());

            }

        } catch (Exception e) {
            logger.error("查询购物车异常", e);
            return ERROR;
        }

        return SUCCESS;
    }

}
