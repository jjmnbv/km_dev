package com.kmzyc.supplier.ajax;

import com.kmzyc.b2b.vo.UserBaseInfo;
import com.kmzyc.supplier.model.AccountInfo;
import com.kmzyc.user.remote.service.BnesAcctEnchashmentRemoteService;
import com.kmzyc.user.remote.service.CustomerRemoteService;
import com.kmzyc.supplier.action.SupplierBaseAction;
import com.kmzyc.supplier.service.AccountService;
import com.kmzyc.supplier.util.StringUtil;
import com.pltfm.app.vobject.BnesAcctEnchashment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

/**
 * 供应商结算
 *
 * @author YaoChao
 */
@Controller("supplierMentAction")
@Scope(value = "prototype")
public class SupplierMentAction extends SupplierBaseAction {

    private static Logger logger = LoggerFactory.getLogger(SupplierMentAction.class);

    private BnesAcctEnchashment bnesAcctEnchashment;

    @Resource
    private AccountService accountService;

    @Resource
    private BnesAcctEnchashmentRemoteService bnesAcctEnchashmentRemoteService;

    @Resource
    private CustomerRemoteService customerRemoteService;

    private String bankAccountName;

    private String bankOfDepositBranchName;

    private String companyAccount;

    private BigDecimal amountAvlibal;

    private String paymentpwd;

    private String enchashmentDesict;

    /**
     * 效验支付
     *
     * @return
     */
    public String accoutPass() {
        Map<String, Object> jsonMap = new HashMap();
        boolean wasRight = false;
        String msg = null;

        try {
            //1.获取用户信息
            Long userId = getLoginUserId();
            AccountInfo accountInfo = accountService.findAccountByUserId(userId);
            if (accountInfo == null || accountInfo.getPaymentpwd() == null) {
                jsonMap.put("flag", false);
                jsonMap.put("msg", "未设置支付密码");
                writeJson(jsonMap);
                return null;
            }

            // 2.校验
            UserBaseInfo userBaseInfo = new UserBaseInfo();
            userBaseInfo.setLoginId(userId);
            userBaseInfo.setPassword(paymentpwd);
            wasRight = customerRemoteService.checkUserPayPassword(userBaseInfo);
            msg = wasRight ? "验证正确！" : "密码输入错误";
        } catch (Exception e) {
            logger.error("商家确认错误：" + e.getMessage(), e);
            msg = "操作异常,请联系管理员";
            wasRight = false;
        }

        jsonMap.put("flag", wasRight);
        jsonMap.put("msg", msg);
        writeJson(jsonMap);
        return null;
    }

    /**
     * 商家提现申请
     *
     * @return
     */
    public String addEnchashment() {
        Map jsonMap = new HashMap();
        boolean flag = false;
        int intFlag = 0;
        try {
            // 登录的用户
            Long userId = getLoginUserId();
            AccountInfo accountInfo = accountService.findAccountByUserId(userId);

            bnesAcctEnchashment = new BnesAcctEnchashment();
            bnesAcctEnchashment.setnLoginId(new BigDecimal(accountInfo.getLoginId()));
            bnesAcctEnchashment.setLoginAccount(accountInfo.getAccountLogin());
            bnesAcctEnchashment.setAccountId(new BigDecimal(accountInfo.getNaccountId()));
            bnesAcctEnchashment.setEnchashmentStatus((short) 0);
            bnesAcctEnchashment.setCreateDate(new Date());
            bnesAcctEnchashment.setEnchashmentAmount(amountAvlibal);
            bnesAcctEnchashment.setEnchashmentFrom(accountInfo.getName());
            bnesAcctEnchashment.setEnchashmentType(BigDecimal.ZERO);
            bnesAcctEnchashment.setEnchashmentResource(BigDecimal.ZERO);
            bnesAcctEnchashment.setEnchashmentDepict(StringUtil.formatScript(enchashmentDesict));
            BigDecimal intIsFlag = bnesAcctEnchashmentRemoteService.insertBnesAcctEnchashment(bnesAcctEnchashment);
            intFlag = intIsFlag.intValue();
            flag = true;
        } catch (Exception e) {
            logger.error("提现申请错误：" + e.getMessage(), e);
            flag = false;
        }
        String msg = null;
        if (intFlag == -1) {
            msg = "已提交申请";
        } else if (intFlag == 0) {
            msg = "申请提交失败";
        } else {
            msg = "申请提交成功";
        }

        jsonMap.put("flag", flag);
        jsonMap.put("msg", msg);
        writeJson(jsonMap);
        return null;
    }

    public BnesAcctEnchashment getBnesAcctEnchashment() {
        return bnesAcctEnchashment;
    }

    public void setBnesAcctEnchashment(BnesAcctEnchashment bnesAcctEnchashment) {
        this.bnesAcctEnchashment = bnesAcctEnchashment;
    }

    public String getBankAccountName() {
        return bankAccountName;
    }

    public void setBankAccountName(String bankAccountName) {
        this.bankAccountName = bankAccountName;
    }

    public String getBankOfDepositBranchName() {
        return bankOfDepositBranchName;
    }

    public void setBankOfDepositBranchName(String bankOfDepositBranchName) {
        this.bankOfDepositBranchName = bankOfDepositBranchName;
    }

    public String getCompanyAccount() {
        return companyAccount;
    }

    public void setCompanyAccount(String companyAccount) {
        this.companyAccount = companyAccount;
    }

    public BigDecimal getAmountAvlibal() {
        return amountAvlibal;
    }

    public void setAmountAvlibal(BigDecimal amountAvlibal) {
        this.amountAvlibal = amountAvlibal;
    }

    public String getPaymentpwd() {
        return paymentpwd;
    }

    public void setPaymentpwd(String paymentpwd) {
        this.paymentpwd = paymentpwd;
    }

    public String getEnchashmentDesict() {
        return enchashmentDesict;
    }

    public void setEnchashmentDesict(String enchashmentDesict) {
        this.enchashmentDesict = enchashmentDesict;
    }

}