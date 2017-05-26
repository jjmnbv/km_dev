package com.kmzyc.b2b.action.member;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.km.framework.action.BaseAction;
import com.kmzyc.b2b.model.AccountInfo;
import com.kmzyc.b2b.model.RechargeDetails;
import com.kmzyc.b2b.service.BnesAcctTransactionService;
import com.kmzyc.b2b.service.member.AccountService;
import com.kmzyc.b2b.service.member.RechargeDetailsService;
import com.kmzyc.framework.constants.Constants;
import com.kmzyc.framework.constants.RechargeDetailStatusMap;
import com.kmzyc.framework.constants.RechargeDetailTypeMap;
import com.km.framework.page.Pagination;

/**
 * 会员中心余额明细入口
 * 
 * @author Administrator
 */
@Controller("rechargeDetailsAction")
@Scope("prototype")
public class RechargeDetailsAction extends BaseAction {
  private static final long serialVersionUID = 5543775073789693006L;
  // private static Logger logger = Logger.getLogger(RechargeDetailsAction.class);
  private static Logger logger = LoggerFactory.getLogger(RechargeDetailsAction.class);
  @Resource(name = "rechargeDetailsServiceImpl")
  private RechargeDetailsService rechargeDetailsService;

  @Resource(name = "bnesAcctTransactionServiceImpl")
  private BnesAcctTransactionService bnesAcctTransactionService;

  @Resource
  private AccountService accountService;

  private List<RechargeDetails> rechargeDetailsList;
  private RechargeDetails rechargeDetails = new RechargeDetails();

  private Date startDate;
  private Date endDate;

  /**
   * 账号余额明细记录(我的余额-余额明细) luoyi 2013/11/07 initRechargeDetails queryRechargeDetail
   * 
   * @return
   */
  public String queryRechargeDetail() {
    // 登录的用户
    Long userId = (Long) getSession().getAttribute(Constants.SESSION_USER_ID);
    logger.info("查询账户余额明细记录，参数：userID：" + userId);
    // 查询余额需要用到account_id,根据loginId进行查找
    try {
      AccountInfo accountInfo = accountService.findAccountByUserId(userId.intValue());
      // 每页显示10条。
      Pagination page = this.getPagination(5, 10);
      // sql查询条件对象
      Map<String, Object> objContion = new HashMap<String, Object>();
      objContion.put("loginId", userId.intValue());
      if (StringUtils.isNotBlank(startDate + "")) {
        objContion.put("startDate", startDate);
      }

      if (StringUtils.isNotBlank(endDate + "")) {
        objContion.put("endDate", endDate);
      }
      // 设置交易类型为余额交易类型
      List statusList =
          Arrays.asList(Constants.TRANSACTION_TYPE_ONLINE, Constants.TRANSACTION_TYPE_STAGEBACK,
              Constants.TRANSACTION_TYPE_PAY, Constants.TRANSACTION_TYPE_CANCEL,
              Constants.TRANSACTION_TYPE_BACK, Constants.TRANSACTION_TYPE_REW,
              Constants.TRANSACTION_TYPE_REWARD, Constants.TRANSACTION_TYPE_FREEZING,
              Constants.TRANSACTION_TYPE_THAW, Constants.TRANSACTION_TYPE_CONSUMPTION,
              Constants.TRANSACTION_TYPE_SALE, Constants.TRANSACTION_TYPE_DISTRIBUTION,
              Constants.TRANSACTION_TYPE_ENCHASHMENT, Constants.TRANSACTION_TYPE_MERCHANT);
      objContion.put("arry", StringUtils.join(statusList, ","));
      // 设置查询条件
      page.setObjCondition(objContion);
      // 加载用户账号信息
      rechargeDetails = rechargeDetailsService.queryRechargeDetailsById(page);
      // 加载用户充值明细记录,此处要用account_id
      objContion.put("loginId", accountInfo.getNaccountId());
      this.pagintion = bnesAcctTransactionService.findAcctBalanceByUserId(page);
    } catch (SQLException e) {
      logger.error("查询账户余额明细出错" + e.getMessage(), e);
      return ERROR;
    } catch (Exception e) {
      logger.error("查询账户余额明细出错" + e.getMessage(), e);
      return ERROR;
    }
    // 账号余额明细充值类型
    getRequest().setAttribute("rechargeDetailTypeMap", RechargeDetailTypeMap.getMap());
    // 账号余额明细充值状态
    getRequest().setAttribute("rechargeDetailStatusMap", RechargeDetailStatusMap.getMap());

    return SUCCESS;
  }

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

  public Date getStartDate() {
    return startDate;
  }

  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }

  public Date getEndDate() {
    return endDate;
  }

  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }

}
