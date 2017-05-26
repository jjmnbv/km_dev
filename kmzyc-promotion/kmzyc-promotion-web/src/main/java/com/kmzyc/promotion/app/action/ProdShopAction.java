package com.kmzyc.promotion.app.action;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.kmzyc.commons.page.Page;
import com.kmzyc.promotion.app.service.SupplierAuditService;
import com.kmzyc.promotion.app.util.PromotionInfoUtil;
import com.kmzyc.promotion.app.vobject.MyMerchantInfoOrSuppliers;
import com.kmzyc.supplier.model.MerchantInfoOrSuppliers;

@Controller("prodShopAction")
@Scope(value = "prototype")
public class ProdShopAction extends BaseAction {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private Page page;
    private String paramsIds;
    private Map<String, String> shopMap;
    MyMerchantInfoOrSuppliers record = null;

    public String getParamsIds() {
        return paramsIds;
    }

    public void setParamsIds(String paramsIds) {
        this.paramsIds = paramsIds;
    }

    public Map<String, String> getShopMap() {
        return shopMap;
    }

    public void setShopMap(Map<String, String> shopMap) {
        this.shopMap = shopMap;
    }

    public String show() {
        getSupplierMap();
        return SUCCESS;
    }

    @Resource
    private SupplierAuditService supplierAuditService;

    public String newshow() {
        if (record == null) {
            record = new MyMerchantInfoOrSuppliers();
        }
        record.setStatus(Short.parseShort("3"));// 审核通过的供应商
        record.setEnterpriseStatus(Short.parseShort("1"));
        record.setSupplierType(Short.parseShort("2"));
        page = super.getPage();
        int pageIndex = page.getPageNo();
        if (pageIndex == 0)
            pageIndex = 1;
        int pageSize = page.getPageSize();
        int skip = (pageIndex - 1) * pageSize + 1;
        int max = pageSize * pageIndex;
        record.setSkip(skip);
        record.setMax(max);
        List<MerchantInfoOrSuppliers> list = null;
        list = supplierAuditService.selectPageSupplierIdAndMerchantName(record);
        page.setRecordCount(supplierAuditService.selectCountSupplierIdAndMerchantName(record));
        page.setDataList(list);
        this.getRequest().setAttribute("page", page);
        this.getRequest().setAttribute("shopCodes", this.getRequest().getParameter("shopCodes"));
        getRequest().setAttribute("shopMap", PromotionInfoUtil.getShopTypeMap());
        return SUCCESS;
    }

    public MyMerchantInfoOrSuppliers getRecord() {
        return record;
    }

    public void setRecord(MyMerchantInfoOrSuppliers record) {
        this.record = record;
    }

}
