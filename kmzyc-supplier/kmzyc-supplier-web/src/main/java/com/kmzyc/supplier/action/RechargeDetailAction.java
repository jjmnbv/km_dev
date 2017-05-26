package com.kmzyc.supplier.action;


import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.km.framework.page.Pagination;
import com.kmzyc.framework.constants.Constants;
import com.kmzyc.supplier.maps.RechargeDetailStatusMap;
import com.kmzyc.supplier.maps.RechargeDetailTypeMap;
import com.kmzyc.supplier.model.AccountBasics;
import com.kmzyc.supplier.model.AccountInfo;
import com.kmzyc.supplier.model.BnesAcctEnchashment;
import com.kmzyc.supplier.model.RechargeDetails;
import com.kmzyc.supplier.service.AccountService;
import com.kmzyc.supplier.service.BnesAcctTransactionService;
import com.kmzyc.supplier.service.RechargeDetailsService;
import com.kmzyc.supplier.service.SupplierEnchashmentService;

/**
 * 供应商余额明细Action
 *
 * @author yaochao
 * @createDate 2013/01/07
 */
@Controller("rechargeDetailAction")
@Scope("prototype")
public class RechargeDetailAction extends SupplierBaseAction {

    private static Logger logger = LoggerFactory.getLogger(RechargeDetailAction.class);

    @Resource
    private RechargeDetailsService rechargeDetailsService;

    @Resource
    private BnesAcctTransactionService bnesAcctTransactionService;

    @Resource
    private SupplierEnchashmentService supplierEnchashmentService;

    @Resource
    private AccountService accountService;

    private List<RechargeDetails> rechargeDetailsList;

    private RechargeDetails rechargeDetails = new RechargeDetails();

    private BnesAcctEnchashment bnesAcctEnchashment;

    private RechargeDetails rechargeDetailsTemp;

    private AccountBasics accountBasics;

    private String pwdResult = null;

    /**
     * 账号余额明细记录(我的余额-余额明细)
     *
     * @return
     */
    public String queryRechargeDetail() {
        Long userId = getLoginUserId();
        Pagination page = getPagination(Constants.VIEW_PAGE, Integer.parseInt(getPageSize()));
        try {
            AccountInfo accountInfo = accountService.findAccountByUserId(userId);
            rechargeDetailsTemp = rechargeDetails;
            // 加载用户账号信息
            rechargeDetails = rechargeDetailsService.queryRechargeDetailsById(page, userId, rechargeDetailsTemp);
            //余额明细查询
            pagintion = bnesAcctTransactionService.findAcctBalanceByUserId(page, accountInfo.getNaccountId(), rechargeDetailsTemp);
            rechargeDetails.setType(rechargeDetailsTemp.getType());
            rechargeDetails.setAginCreateDate(rechargeDetailsTemp.getAginCreateDate());
            rechargeDetails.setEndCreateDate(rechargeDetailsTemp.getEndCreateDate());
        } catch (Exception e) {
            logger.error("查询账户余额明细出错" + e);
            return ERROR;
        }
        // 账号余额明细充值类型
        getRequest().setAttribute("rechargeDetailTypeMap", RechargeDetailTypeMap.getMap());
        // 账号余额明细充值状态
        getRequest().setAttribute("rechargeDetailStatusMap", RechargeDetailStatusMap.getMap());
        return SUCCESS;
    }

    /**
     * 商家提现申请单
     *
     * @return
     */
    public String queryAccountBasicInfo() {
        Long userId = getLoginUserId();
        Long merchantId = (Long) getSession().getAttribute(Constants.SESSION_MERCHANT_ID);
        try {
            accountBasics = rechargeDetailsService.findAccountBasicsByUserId(merchantId);
            //判断是否有待审核的体现
            bnesAcctEnchashment = supplierEnchashmentService.searchPendingAuditByUserId(merchantId);
            //判断是否设置密码
            AccountInfo accountInfo = accountService.findAccountByUserId(userId);
            if (accountInfo.getPaymentpwd() != null) {
                pwdResult = "havePwd";
            }
        } catch (Exception e) {
            logger.error("查看商家提现申请单出错" + e);
            return ERROR;
        }
        return SUCCESS;
    }

    /**
     * 以下为set和get方法
     *
     * @return
     */
    public List<RechargeDetails> getRechargeDetailsList() {
        return rechargeDetailsList;
    }

    public void setRechargeDetailsList(List<RechargeDetails> rechargeDetailsList) {
        this.rechargeDetailsList = rechargeDetailsList;
    }

    public RechargeDetails getRechargeDetails() {
        return rechargeDetails;
    }

    public void setRechargeDetails(RechargeDetails rechargeDetails) {
        this.rechargeDetails = rechargeDetails;
    }

    public RechargeDetails getRechargeDetailsTemp() {
        return rechargeDetailsTemp;
    }

    public void setRechargeDetailsTemp(RechargeDetails rechargeDetailsTemp) {
        this.rechargeDetailsTemp = rechargeDetailsTemp;
    }

    public AccountBasics getAccountBasics() {
        return accountBasics;
    }

    public BnesAcctEnchashment getBnesAcctEnchashment() {
        return bnesAcctEnchashment;
    }

    public void setBnesAcctEnchashment(BnesAcctEnchashment bnesAcctEnchashment) {
        this.bnesAcctEnchashment = bnesAcctEnchashment;
    }

    public String getPwdResult() {
        return pwdResult;
    }

    public void setPwdResult(String pwdResult) {
        this.pwdResult = pwdResult;
    }

}
