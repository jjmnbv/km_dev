package com.pltfm.app.action;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.kmzyc.commons.page.Page;
import com.opensymphony.xwork2.ModelDriven;
import com.pltfm.app.service.BnesAcctTransactionService;
import com.pltfm.app.vobject.BnesAcctTransactionQuery;

@Component(value = "reserverTransaction")
public class ReserverTransaction extends BaseAction implements ModelDriven {
  private static final long serialVersionUID = 8749201342443258401L;
  // 日志类
  private Logger logger = LoggerFactory.getLogger(ReserverTransaction.class);

  @Resource(name = "bnesAcctTransactionService")
  private BnesAcctTransactionService BnesAcctTransactionService;

  // 分页对象
  private Page page;
  // 交易实体
  private BnesAcctTransactionQuery bnesAcctTransactionQuery;

  /*删除预备金相关 public String PageList() {
    if (page == null) {
      page = new Page();
    }
    if (bnesAcctTransactionQuery == null) {
      bnesAcctTransactionQuery = new BnesAcctTransactionQuery();
    }
    try {
      // 设置交易对面为预备金
      bnesAcctTransactionQuery.setTrasObject(4);
      page = BnesAcctTransactionService.findListByPageExample(page, bnesAcctTransactionQuery);
    } catch (Exception e) {
      logger.error("获取预备金交易记录失败" + e.getMessage(), e);
    }
    return "pageSuccess";
  }*/

  // 测试
  public String test() {
    return null;
  }


  @Override
public Page getPage() {
    return page;
  }

  @Override
public void setPage(Page page) {
    this.page = page;
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
    return null;
  }

}
