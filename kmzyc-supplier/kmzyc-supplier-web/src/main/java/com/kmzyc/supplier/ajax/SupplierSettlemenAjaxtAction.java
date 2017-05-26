package com.kmzyc.supplier.ajax;

import com.kmzyc.order.remote.SupplierSettlementService;
import com.kmzyc.supplier.model.AccountInfo;
import com.kmzyc.supplier.action.SupplierBaseAction;
import com.kmzyc.supplier.service.AccountService;
import com.kmzyc.supplier.util.StringUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

/**
 * @author YC
 */
@Controller("supplierSettlemenAjaxtAction")
@Scope(value = "prototype")
public class SupplierSettlemenAjaxtAction extends SupplierBaseAction {

    private static Logger logger = LoggerFactory.getLogger(SupplierSettlemenAjaxtAction.class);

    //结算单号
    private String settlementNo;

    //商家会员
    private String sellerId;

    //商家意见
    private String sellerConfirmation;

    @Resource
    private AccountService accountService;

    @Resource
    private SupplierSettlementService supplierSettlementService;

    public String merchantConfirmation() {
        Map<String, Object> jsonMap = new HashMap();
        boolean haveDone = false;
        try {

            // 登录的用户
            Long userId = getLoginUserId();
            AccountInfo accountInfo = accountService.findAccountByUserId(userId);
            settlementNo = StringUtil.formatScript(settlementNo);
            sellerConfirmation = StringUtil.formatScript(sellerConfirmation);
            int tag = supplierSettlementService.updateSettlementSellerConfirmation(settlementNo,
                    sellerId, sellerConfirmation, accountInfo.getAccountLogin());
            haveDone = tag == 1;
        } catch (Exception e) {
            logger.error("商家确认错误：" + e.getMessage(), e);
            haveDone = false;
        }

        jsonMap.put("flag", haveDone);
        jsonMap.put("msg", haveDone ? "商家已确认" : "商家确认失败");
        writeJson(jsonMap);
        return null;
    }

    public String getSettlementNo() {
        return settlementNo;
    }

    public void setSettlementNo(String settlementNo) {
        this.settlementNo = settlementNo;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public String getSellerConfirmation() {
        return sellerConfirmation;
    }

    public void setSellerConfirmation(String sellerConfirmation) {
        this.sellerConfirmation = sellerConfirmation;
    }


}
