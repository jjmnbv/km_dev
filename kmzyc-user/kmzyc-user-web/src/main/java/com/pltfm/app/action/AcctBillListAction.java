package com.pltfm.app.action;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.kmzyc.commons.page.Page;
import com.opensymphony.xwork2.ModelDriven;
import com.pltfm.app.vobject.BnesAcctTransListQuery;


@Component(value = "acctBillListAction")
@Scope(value = "prototype")
public class AcctBillListAction extends BaseAction implements ModelDriven {



  /**
   * 
   */
  private static final long serialVersionUID = -3652710008415536146L;


  /**
   * 电子对账单 action
   * 
   * @author beary
   * @since 2013-07-15
   */
  private BnesAcctTransListQuery bnesAcctTransListQuery;


  /*
   * @Resource(name = "bnesAcctTransactionService") private BnesAcctTransactionService
   * bnesAcctTransactionService;
   * 
   * @Resource(name = "accountInfoService") private AccountInfoService accountInfoService;
   * 
   * private BnesAcctTransactionQuery bnesAcctTransactionQuery;
   * 
   * private List<BnesAcctTransactionQuery> list;
   */
  private List<String> levelId;

  private Page page;

  /**
   * @throws Exception 查询
   * 
   * @param @return String @exception
   */

  /*
   * public String showPayment() throws Exception { try { page =
   * bnesAcctTransactionService.findListByPageExample(page, bnesAcctTransactionQuery); return
   * "list"; } catch (Exception e) { e.printStackTrace(); return "pageInput"; } }
   */
  /**
   * @throws Exception 查询交易记录
   * 
   * @param @return String @exception
   */
  public String showBillList() throws Exception {

    return "billslist";
  }



  @Override
public Page getPage() {
    return page;
  }

  public void setPage(Page page) {
    this.page = page;
  }



  @Override
  public Object getModel() {
    // TODO Auto-generated method stub
    bnesAcctTransListQuery = new BnesAcctTransListQuery();
    return bnesAcctTransListQuery;
  }

  public List<String> getLevelId() {
    return levelId;
  }

  public void setLevelId(List<String> levelId) {
    this.levelId = levelId;
  }

  public BnesAcctTransListQuery getBnesAcctTransListQuery() {
    return bnesAcctTransListQuery;
  }



  public void setBnesAcctTransListQuery(BnesAcctTransListQuery bnesAcctTransListQuery) {
    this.bnesAcctTransListQuery = bnesAcctTransListQuery;
  }


}
