package com.pltfm.app.action;

import com.kmzyc.supplier.model.MerchantInfoOrSuppliers;
import com.pltfm.app.dao.SupplierAuditDAO;
import com.pltfm.app.util.PromotionInfoUtil;
import com.pltfm.app.vobject.MyMerchantInfoOrSuppliers;
import com.kmzyc.commons.page.Page;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Controller("prodShopAction")
@Scope(value = "prototype")
public class ProdShopAction extends BaseAction {

    private static final long serialVersionUID = 1L;

    private Page page;

    private String paramsIds;

    private Map<String, String> shopMap;

    MyMerchantInfoOrSuppliers record = null;

    public String show() {
        getSupplierMap();
        return SUCCESS;
    }

    @Resource
    private SupplierAuditDAO supplierAuditDao;

    public String newshow() {
        if (record == null) {
            record = new MyMerchantInfoOrSuppliers();
        }
        record.setStatus(Short.parseShort("3"));//审核通过的供应商
        record.setEnterpriseStatus(Short.parseShort("1"));
        record.setSupplierType(Short.parseShort("2"));
        page = super.getPage();
        int pageIndex = page.getPageNo();
        if (pageIndex == 0) pageIndex = 1;
        int pageSize = page.getPageSize();
        int skip = (pageIndex - 1) * pageSize + 1;
        int max = pageSize * pageIndex;
        record.setSkip(skip);
        record.setMax(max);

        try {
            List<MerchantInfoOrSuppliers> list = supplierAuditDao.selectPageSupplierIdAndMerchantName(record);
            page.setRecordCount(supplierAuditDao.selectCountSupplierIdAndMerchantName(record));
            page.setDataList(list);
            this.getRequest().setAttribute("page", page);
            this.getRequest().setAttribute("shopCodes", this.getRequest().getParameter("shopCodes"));
            getRequest().setAttribute("shopMap", PromotionInfoUtil.getShopTypeMap());
            return SUCCESS;
        } catch (SQLException e) {
            logger.error("newShow  ", e);
            return ERROR;
        }
    }

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

    public MyMerchantInfoOrSuppliers getRecord() {
        return record;
    }

    public void setRecord(MyMerchantInfoOrSuppliers record) {
        this.record = record;
    }

}