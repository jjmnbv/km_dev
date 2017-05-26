package com.kmzyc.supplier.ajax;

import com.kmzyc.supplier.model.SupplierFare;
import com.kmzyc.supplier.action.SupplierBaseAction;
import com.kmzyc.supplier.service.SupplierFareService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

@Controller("supplierFareAjaxAction")
@Scope("prototype")
public class SupplierFareAjaxAction extends SupplierBaseAction {

    private static Logger logger = LoggerFactory.getLogger(SupplierFareAjaxAction.class);

    @Resource
    private SupplierFareService supplierFareService;

    private SupplierFare supplierFare;

    private Long fareId;

    private String newName;

    /**
     * 添加运费规则
     *
     * @return
     */
    public String addSupperFare() {
        String result = "0";
        try {
            Long supplierId = getSupplyId();
            int count = supplierFareService.countFareBySupplierId(supplierId);
            if (count == 0) {
                supplierFare.setSupplierId(supplierId);
                supplierFareService.insertFare(supplierFare);
            } else {
                result = "1";
            }
        } catch (Exception e) {
            logger.error("新增运费规则错误：" + e.getMessage(), e);
            result = "1";
        }
        writeStr(result);
        return null;
    }

    public String updateSupplerFare() {
        String result = "0";
        try {
            supplierFare.setSupplierId(getSupplyId());
            int fareId = supplierFareService.updateFare(supplierFare);
            if (fareId > 0) {
                result = "0";
            } else {
                result = "1";
            }
        } catch (Exception e) {
            logger.error("修改运费规则错误：" + e.getMessage(), e);
            result = "1";
        }

        writeStr(result);
        return null;
    }

    public String deleteFare() {
        Map<String, Object> jsonMap = new HashMap();
        try {
            Integer count = supplierFareService.deleteFare(fareId);
            boolean haveDone = count > 0;
            if (haveDone) {

            }
            jsonMap.put("flag", haveDone);
            jsonMap.put("msg", haveDone?"删除成功!":"删除失败!");
            jsonMap.put("fareId", fareId);
        } catch (Exception e) {
            logger.error("删除运费规则错误：" + e.getMessage(), e);
            jsonMap.put("flag", false);
            jsonMap.put("msg", "删除运费规则失败!");
        }

        writeJson(jsonMap);
        return null;
    }

    public String cktest() {
        try {
            int count = supplierFareService.countFareBySupplierId(getSupplyId());
            boolean haveDone = count == 0;
            writeStr(haveDone ? "0" : "1");
        } catch (Exception e) {
            writeStr("0");
            logger.error("根据供应商id和站点查询运费信息出现异常：" + e.getMessage(), e);
        }
        return null;
    }

    public SupplierFare getSupplierFare() {
        return supplierFare;
    }

    public void setSupplierFare(SupplierFare supplierFare) {
        this.supplierFare = supplierFare;
    }

    public Long getFareId() {
        return fareId;
    }

    public void setFareId(Long fareId) {
        this.fareId = fareId;
    }

    public String getNewName() {
        return newName;
    }

    public void setNewName(String newName) {
        this.newName = newName;
    }

}
