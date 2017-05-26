package com.kmzyc.b2b.action.member;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.km.framework.action.BaseAction;
import com.km.framework.page.Pagination;
import com.kmzyc.b2b.model.AccountInfo;
import com.kmzyc.b2b.service.member.AccountService;
import com.kmzyc.b2b.third.util.ThirdConstant;
import com.kmzyc.framework.constants.Constants;

/**
 * Description:会员中心-账户信息、余额、消费明细等的访问入口 User: lishiming Date: 13-10-15 下午3:45 Since: 1.0
 */
@SuppressWarnings("serial")
@Controller
@Scope("prototype")
public class AccountAction extends BaseAction {
    // private static Logger logger = Logger.getLogger(AccountAction.class);
    private static Logger logger = LoggerFactory.getLogger(AccountAction.class);

    @Resource(name = "accountServiceImpl")
    private AccountService accountService;

    private AccountInfo accountInfo;

    /**
     * 近一年消费总金额
     */
    private BigDecimal consumptionSum;

    /**
     * 查询会员的消费明细
     * 
     * @return
     */
    @SuppressWarnings("unchecked")
    public String queryConsumptionDetail() {
        HttpServletRequest request = ServletActionContext.getRequest();
        Long userId = (Long) request.getSession().getAttribute(Constants.SESSION_USER_ID);
        logger.info("查询会员消费明细，参数：userID:" + userId);
        Pagination page = this.getPagination(5, 10);
        // 页面传入的查询条件
        Map<String, String> objContion = (Map<String, String>) page.getObjCondition();
        // sql查询条件对象
        Map<String, Object> newConditon = new HashMap<String, Object>();
        // 解析并组装查询条件
        newConditon.put("userId", userId);
        try {
            // 获取账户余额
            accountInfo = accountService.findAccountByUserId(userId);
            // 获取近一年实际消费金额,废弃
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.add(Calendar.YEAR, -1);
            // 获取近三个月的消费明细
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            if (objContion != null) {
                if (StringUtils.isNotBlank(objContion.get("startDate"))) {
                    newConditon.put("startDate", sdf.parse(objContion.get("startDate")));
                } else {// 若无起始日期的查询条件，则默认为三个月前
                    calendar.setTime(new Date());
                    calendar.add(Calendar.MONTH, -3);
                    newConditon.put("startDate", calendar.getTime());
                }
                if (StringUtils.isNotBlank(objContion.get("endDate"))) {
                    calendar.setTime(sdf.parse(objContion.get("endDate")));
                    calendar.add(Calendar.DAY_OF_MONTH, 1);
                    newConditon.put("endDate", calendar.getTime());
                }
            } else {// 若无起始日期的查询条件，则默认为三个月前
                calendar.setTime(new Date());
                calendar.add(Calendar.MONTH, -3);
                newConditon.put("startDate", calendar.getTime());
            }
            // 设置查询条件
            page.setObjCondition(newConditon);
            this.pagintion = accountService.findConsumptionDetailByPage(page);

        } catch (ParseException e) {
            logger.error("日期格式转换出错：" + e.getMessage(), e);
            return ERROR;
        } catch (Exception e) {
            logger.error("查询会员的消费列表信息出错：" + e.getMessage(), e);
            return ERROR;
        }
        return SUCCESS;
    }

    /**
     * 查询会员的账户信息
     * 
     * @return
     */
    public String queryAccountInfo() {
        HttpServletRequest request = ServletActionContext.getRequest();
        Long userId = (Long) request.getSession().getAttribute(Constants.SESSION_USER_ID);
        logger.info("查询会员账户信息，参数：userID：" + userId);
        /*** 第三方和直销绑定验证 ***/
        if ("Y".equals(request.getSession().getAttribute(ThirdConstant.THIRD_ISTEMP_MEMBER))) {
            logger.info("未完善资料的第三方帐号在访问安全中心！ userId:" + userId);
            return "thirdError";
        }
        if ((String) request.getSession().getAttribute(Constants.SESSION_Zx_USER_NAME) == null
                && Constants.LOGINTYPE
                        .equals(request.getSession().getAttribute(Constants.SESSION_B2B_OR_ERA))) {
            logger.info("未完善资料的直销帐号在访问安全中心！ userId:" + userId);
            return "zxError";
        }
        /******/
        try {
            // 获取账户信息
            accountInfo = accountService.findAccountByUserId(userId);
        } catch (SQLException e) {
            logger.error("查询会员的账户信息sql异常：" + e.getMessage(), e);
        } catch (Exception e) {
            logger.error("查询会员的账户信息出错：" + e.getMessage(), e);
            return ERROR;
        }
        return SUCCESS;
    }

    public AccountInfo getAccountInfo() {
        return accountInfo;
    }

    public void setAccountInfo(AccountInfo accountInfo) {
        this.accountInfo = accountInfo;
    }

    public BigDecimal getConsumptionSum() {
        return consumptionSum;
    }

    public void setConsumptionSum(BigDecimal consumptionSum) {
        this.consumptionSum = consumptionSum;
    }

}
