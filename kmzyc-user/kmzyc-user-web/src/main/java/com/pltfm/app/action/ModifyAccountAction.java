package com.pltfm.app.action;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.kmzyc.commons.page.Page;
import com.opensymphony.xwork2.ModelDriven;
import com.pltfm.app.dataobject.BnesAcctTransListDO;
import com.pltfm.app.service.AccountInfoService;
import com.pltfm.app.service.BnesAcctTransListService;
import com.pltfm.app.service.BnesAcctTransactionService;
import com.pltfm.app.service.LoginInfoService;
import com.pltfm.app.util.DateTimeUtils;
import com.pltfm.app.util.Token;
import com.pltfm.app.vobject.AccountInfo;
import com.pltfm.app.vobject.BnesAcctTransactionQuery;
import com.pltfm.app.vobject.LoginInfo;
import com.pltfm.sys.model.SysUser;
/**
 * 账户金额修改action
 * 
 * @author beary
 * @since 2013-07-15
 */
@Component(value = "modifyAccountAction")
@Scope(value = "prototype")
public class ModifyAccountAction extends BaseAction implements ModelDriven {
  
    /**
     *UID 
     */
  private static final long serialVersionUID = 1574025880629195294L;

  Logger logger = LoggerFactory.getLogger(ModifyAccountAction.class);

  @Resource(name = "bnesAcctTransactionService")
  private BnesAcctTransactionService bnesAcctTransactionService;

  @Resource(name = "accountInfoService")
  private AccountInfoService accountInfoService;
  @Resource(name = "bnesAcctTransListService")
  private BnesAcctTransListService bnesAcctTransListService;
  @Resource(name = "loginInfoService")
  private LoginInfoService loginInfoService;

  private BnesAcctTransactionQuery bnesAcctTransactionQuery;

  private List<BnesAcctTransactionQuery> lists;
  private List<String> levelId;
  private AccountInfo accountInfo;
  private List<LoginInfo> loginInfoList;
  

  private Page page;



  /**
   * @throws Exception 查询賬戶信息
   * 
   * @param @return String @exception
   */

  public String showAcotAmout() throws Exception {
    try {
      page = accountInfoService.searchPageByVo(page, accountInfo);
      return "list";
    } catch (Exception e) {
      e.printStackTrace();
      return "pageInput";
    }
  }

  /**
   * @throws Exception @throws Exception 修改賬戶金額
   * 
   * @param @return String @exception
   */
  public String editAcotAmout() throws Exception {
    String number = new SimpleDateFormat("yyyyMMddhhmmssSSS").format(new Date());

    AccountInfo accountInfo =
        accountInfoService.selectByPrimaryKey(bnesAcctTransactionQuery.getAccountId());
    bnesAcctTransactionQuery.setAccountLogin(accountInfo.getAccountLogin());
    bnesAcctTransactionQuery.setAccountAmount(accountInfo.getN_AccountAmount());
    bnesAcctTransactionQuery.setAmountFrozen(accountInfo.getAmountFrozen());
    bnesAcctTransactionQuery.setAmountAvlibal(accountInfo.getAmountAvlibal());
    bnesAcctTransactionQuery.setMobile(accountInfo.getMobile());
    bnesAcctTransactionQuery.setAccountNumber(number);
    bnesAcctTransactionQuery.setType(2);
    return "editAcoutAmout";
  }

  /**
   * @throws Exception @throws Exception 保存修改修改賬戶金額
   * 
   * @param @return String @exception
   */
  @Token
  public String saveAcoutAmout() throws Exception {
    SysUser sysuser = (SysUser) session.get("sysUser");

    bnesAcctTransactionQuery.setType(2);
    bnesAcctTransactionQuery.setStatus(1);

    bnesAcctTransactionQuery.setCreatedId(sysuser.getUserId());
    bnesAcctTransactionQuery.setCreateDate(DateTimeUtils.getCalendarInstance().getTime());
    bnesAcctTransactionQuery.setModifieId(sysuser.getUserId());
    bnesAcctTransactionQuery.setModifyDate(DateTimeUtils.getCalendarInstance().getTime());

    Integer accountTransactionId = 0;
    Integer accountId = bnesAcctTransactionQuery.getAccountId();
    // 保存
    accountTransactionId =
        bnesAcctTransactionService.insertBnesAcctTransactionDO(bnesAcctTransactionQuery);

    AccountInfo accountInfo = accountInfoService.selectByPrimaryKey(accountId);
    BigDecimal beforeAmount = BigDecimal.ZERO;
    if (accountInfo.getAmountAvlibal() != null) {
      beforeAmount = accountInfo.getAmountAvlibal();
    } else {
      beforeAmount = BigDecimal.ZERO;
    }
    BigDecimal amout = bnesAcctTransactionQuery.getAmount();
    BigDecimal afterAmount = beforeAmount.add(amout);
    accountInfo.setAmountAvlibal(afterAmount);
    //判断总金额是否为空，如果为空则设置为zero
    if(accountInfo.getN_AccountAmount()==null){
        accountInfo.setN_AccountAmount(BigDecimal.ZERO);
    }
    accountInfo.setN_AccountAmount(accountInfo.getN_AccountAmount().add(amout));
    accountInfoService.updateAccountInfo(accountInfo);
    // 保存交易记录
    BnesAcctTransListDO bnesAcctTransListDO = new BnesAcctTransListDO();
    bnesAcctTransListDO.setAccountId(accountId);
    bnesAcctTransListDO.setAccountTransactionId(accountTransactionId);
    bnesAcctTransListDO.setBeforeAmount(beforeAmount);
    bnesAcctTransListDO.setAfterAmount(afterAmount);
    bnesAcctTransListDO.setMoneyAmount(amout);
    bnesAcctTransListService.insertBnesAcctTransListDO(bnesAcctTransListDO);
    bnesAcctTransactionQuery = new BnesAcctTransactionQuery();
    
    return showAcotAmout();
  }
  
  /**
   * @throws Exception @throws Exception @throws Exception 查询賬戶金額记录
   * 
   * @param @return String @exception
   */
  public String showModifyHisory() throws Exception {
    HttpServletRequest request = ServletActionContext.getRequest();
    request.setAttribute("accountLogin", request.getParameter("accountLogin"));
    bnesAcctTransactionQuery.setType(2);
    page = bnesAcctTransactionService.findListByPageExample(page, bnesAcctTransactionQuery);
    return "editAcoutAmoutList";
  }

  public BnesAcctTransListService getBnesAcctTransListService() {
    return bnesAcctTransListService;
  }

  public void setBnesAcctTransListService(BnesAcctTransListService bnesAcctTransListService) {
    this.bnesAcctTransListService = bnesAcctTransListService;
  }

  @Override
public Page getPage() {
    return page;
  }

  @Override
public void setPage(Page page) {
    this.page = page;
  }

  public BnesAcctTransactionService getBnesAcctTransactionService() {
    return bnesAcctTransactionService;
  }

  public void setBnesAcctTransactionService(BnesAcctTransactionService bnesAcctTransactionService) {
    this.bnesAcctTransactionService = bnesAcctTransactionService;
  }

  public BnesAcctTransactionQuery getBnesAcctTransactionQuery() {
    return bnesAcctTransactionQuery;
  }

  public void setBnesAcctTransactionQuery(BnesAcctTransactionQuery bnesAcctTransactionQuery) {
    this.bnesAcctTransactionQuery = bnesAcctTransactionQuery;
  }


  @Override
  public Object getModel() {
    // TODO Auto-generated method stub
    bnesAcctTransactionQuery = new BnesAcctTransactionQuery();
    return bnesAcctTransactionQuery;
  }

  public List<String> getLevelId() {
    return levelId;
  }

  public void setLevelId(List<String> levelId) {
    this.levelId = levelId;
  }

  public AccountInfoService getAccountInfoService() {
    return accountInfoService;
  }

  public void setAccountInfoService(AccountInfoService accountInfoService) {
    this.accountInfoService = accountInfoService;
  }

  public List<BnesAcctTransactionQuery> getLists() {
    return lists;
  }


  public void setLists(List<BnesAcctTransactionQuery> lists) {
    this.lists = lists;
  }


  public AccountInfo getAccountInfo() {
    return accountInfo;
  }


  public void setAccountInfo(AccountInfo accountInfo) {
    this.accountInfo = accountInfo;
  }


  public List<LoginInfo> getLoginInfoList() {
    return loginInfoList;
  }


  public void setLoginInfoList(List<LoginInfo> loginInfoList) {
    this.loginInfoList = loginInfoList;
  }

}
