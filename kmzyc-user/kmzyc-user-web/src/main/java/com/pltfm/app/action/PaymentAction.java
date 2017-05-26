package com.pltfm.app.action;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.kmzyc.commons.page.Page;
import com.opensymphony.xwork2.ModelDriven;
import com.pltfm.app.dataobject.BnesAcctTransListDO;
import com.pltfm.app.service.AccountInfoService;
import com.pltfm.app.service.BnesAcctTransListService;
import com.pltfm.app.service.BnesAcctTransactionService;
import com.pltfm.app.util.DateTimeUtils;
import com.pltfm.app.util.Token;
import com.pltfm.app.vobject.AccountInfo;
import com.pltfm.app.vobject.BnesAcctTransactionQuery;
import com.pltfm.sys.model.SysUser;

@Component(value = "paymentAction")
@Scope(value = "prototype")
public class PaymentAction extends BaseAction implements ModelDriven {

  /**
   * 充值action
   * 
   * @author beary
   * @since 2013-07-15
   */
  private static final long serialVersionUID = -1465713453028977343L;

  @Resource(name = "bnesAcctTransactionService")
  private BnesAcctTransactionService bnesAcctTransactionService;

  @Resource(name = "accountInfoService")
  private AccountInfoService accountInfoService;
  @Resource(name = "bnesAcctTransListService")
  private BnesAcctTransListService bnesAcctTransListService;

  private BnesAcctTransactionQuery bnesAcctTransactionQuery;

  private List<BnesAcctTransactionQuery> list;
  private List<String> levelId;

  private Page page;

  /**
   * @throws Exception 查询
   * 
   * @param @return String @exception
   */

  public String showPayment() throws Exception {
    bnesAcctTransactionQuery.setType(1);
    try {
      page = bnesAcctTransactionService.findListByPageExample(page, bnesAcctTransactionQuery);
      return "list";
    } catch (Exception e) {
      e.printStackTrace();
      return "pageInput";
    }
  }

  /**
   * @throws Exception 顯示修改充值信息
   * 
   * @param @return String @exception
   */
  public String addPayment() {
    try {
      bnesAcctTransactionQuery =
          bnesAcctTransactionService.findById(bnesAcctTransactionQuery.getAccountTransactionId());
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    return "updatePayment";
  }

  /**
   * @throws Exception 修改充值
   * 
   * @param @return String @exception
   */
  public String editPayement() throws Exception {
    bnesAcctTransactionQuery.setModifyDate(DateTimeUtils.getCalendarInstance().getTime());
     bnesAcctTransactionService.updateBnesAcctTransactionDO(bnesAcctTransactionQuery);
    bnesAcctTransactionQuery = new BnesAcctTransactionQuery();
    return this.showPayment();
  }

  /**
   * @throws Exception 增加充值
   * 
   * @param @return String @exception
   */
  public String addPayments() throws Exception {
    bnesAcctTransactionQuery = new BnesAcctTransactionQuery();
    String number = new SimpleDateFormat("yyyyMMddhhmmssSSS").format(new Date());
    bnesAcctTransactionQuery.setAccountNumber(number);


    return "addPayment";
  }

  /**
   * @throws Exception 保存充值信息
   * 
   * @param @return String @exception
   */
  @Token
  public String savePaymet() throws Exception {
    SysUser sysuser = (SysUser) session.get("sysUser");

    bnesAcctTransactionQuery.setType(1);
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
    return this.showPayment();
  }

  /**
   * @throws Exception @throws Exception 删除充值信息
   * 
   * @param @return String @exception
   */
  public String deletePayment() throws Exception {
    int deletNum = 0;
    bnesAcctTransactionService.deleteBnesAcctTransactionDOByPrimaryKey(
        bnesAcctTransactionQuery.getAccountTransactionId());

    bnesAcctTransactionQuery = new BnesAcctTransactionQuery();
    return this.showPayment();
  }

  public String deleteAllPayment() throws Exception {

   bnesAcctTransactionService.deleteAll(levelId);

    return this.showPayment();
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

  public List<BnesAcctTransactionQuery> getList() {
    return list;
  }

  public void setList(List<BnesAcctTransactionQuery> list) {
    this.list = list;
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

  public BnesAcctTransListService getBnesAcctTransListService() {
    return bnesAcctTransListService;
  }

  public void setBnesAcctTransListService(BnesAcctTransListService bnesAcctTransListService) {
    this.bnesAcctTransListService = bnesAcctTransListService;
  }

}
