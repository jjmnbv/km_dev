package com.pltfm.app.action;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.kmzyc.commons.page.Page;
import com.opensymphony.xwork2.ModelDriven;
import com.pltfm.app.vobject.BnesPasswordQuery;


@Component(value = "acctPasswordAction")
@Scope(value = "prototype")
public class AcctPasswordAction extends BaseAction implements ModelDriven {



  /**
   * 
   */
  private static final long serialVersionUID = -1591448379430136511L;



  /**
   * 交易记录action
   * 
   * @author beary
   * @since 2013-07-15
   */
  private BnesPasswordQuery bnesPasswordQuery;



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
   * @throws Exception 账户找回密码信息
   * 
   * @param @return String @exception
   */
  public String showPasswordList() throws Exception {

    return "passwordlist";
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
    bnesPasswordQuery = new BnesPasswordQuery();
    return bnesPasswordQuery;
  }

  public List<String> getLevelId() {
    return levelId;
  }

  public void setLevelId(List<String> levelId) {
    this.levelId = levelId;
  }



}
