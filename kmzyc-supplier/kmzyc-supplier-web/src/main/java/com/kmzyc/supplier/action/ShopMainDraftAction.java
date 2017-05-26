package com.kmzyc.supplier.action;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.km.framework.page.Pagination;
import com.kmzyc.framework.constants.Constants;
import com.kmzyc.supplier.model.ShopMainDraft;
import com.kmzyc.supplier.service.ShopMainDraftService;


@Controller("shopMainDraftAction")
@Scope("prototype")
public class ShopMainDraftAction extends SupplierBaseAction {

    private static Logger logger = LoggerFactory.getLogger(ShopMainDraftAction.class);

    @Resource
    private ShopMainDraftService shopMainDraftService;

    private ShopMainDraft shopMainDraft;

    private ShopMainDraft shopMainDraftSearch;

    private Long shopId;

    public String shopMainDraftList() {
        try {
            Pagination page = getPagination(Constants.VIEW_PAGE, Integer.parseInt(getPageSize()));
            if (shopMainDraftSearch == null) {
                shopMainDraftSearch = new ShopMainDraft();
            }
            shopMainDraftSearch.setSupplierId(getSupplyId());
            pagintion = shopMainDraftService.searchPage(shopMainDraftSearch, page);
        } catch (Exception e) {
            logger.error("店铺列表查询错误：", e);
            return ERROR;
        }
        setShopMainStatusMap();
        setShopAuditStatusMap();
        return SUCCESS;
    }

    public String toEditShopMainDraft() {
        try {
            shopMainDraft = shopMainDraftService.findByShopId(shopId);
        } catch (Exception e) {
            logger.error("toEditShopMain Error:", e);
            return ERROR;
        }
        setShopMainServiceTypeMap();
        setShopMainTypeMap();
        return SUCCESS;
    }

    public String toViewShopMainDraft() {
        try {
            shopMainDraft = shopMainDraftService.findByShopId(shopId);
        } catch (Exception e) {
            logger.error("toViewShopMainDraft:" + e.getMessage(), e);
            return ERROR;
        }
        setShopMainServiceTypeMap();
        setShopMainTypeMap();
        return SUCCESS;
    }

    public ShopMainDraft getShopMainDraft() {
        return shopMainDraft;
    }

    public void setShopMainDraft(ShopMainDraft shopMainDraft) {
        this.shopMainDraft = shopMainDraft;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public ShopMainDraft getShopMainDraftSearch() {
        return shopMainDraftSearch;
    }

    public void setShopMainDraftSearch(ShopMainDraft shopMainDraftSearch) {
        this.shopMainDraftSearch = shopMainDraftSearch;
    }

}