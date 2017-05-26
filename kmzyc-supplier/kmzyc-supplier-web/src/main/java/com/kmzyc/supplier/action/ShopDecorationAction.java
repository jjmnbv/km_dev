package com.kmzyc.supplier.action;


import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.kmzyc.supplier.model.ShopMain;
import com.kmzyc.supplier.service.ShopMainService;

@Scope("prototype")
@Controller("shopDecorationAction")
public class ShopDecorationAction extends SupplierBaseAction {

    private static Logger logger = LoggerFactory.getLogger(ShopDecorationAction.class);

    @Resource
    private ShopMainService shopMainService;

    private ShopMain shopMain;

    private String shopId;

    public String templetChoice() {
        try {
            shopMain = shopMainService.findShopMainBySupplierId(getSupplyId());
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            return ERROR;
        }
        return SUCCESS;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public ShopMain getShopMain() {
        return shopMain;
    }

    public void setShopMain(ShopMain shopMain) {
        this.shopMain = shopMain;
    }
}
