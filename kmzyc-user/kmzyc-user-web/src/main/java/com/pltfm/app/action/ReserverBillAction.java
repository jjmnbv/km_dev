package com.pltfm.app.action;



import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.kmzyc.commons.page.Page;
import com.opensymphony.xwork2.ModelDriven;
import com.pltfm.app.service.ReserverBillService;
import com.pltfm.app.service.ReserverInfoService;
import com.pltfm.app.vobject.BnesAcctTransactionQuery;
import com.pltfm.app.vobject.ReserverBill;
import com.pltfm.app.vobject.ReserverInfo;


@SuppressWarnings("rawtypes")
@Component(value = "reserverBillAction")
@Scope(value = "prototype")
public class ReserverBillAction extends BaseAction implements ModelDriven {
  // 日志类
  private Logger logger = LoggerFactory.getLogger(ReserverBillAction.class);
  private static final long serialVersionUID = -6614522368100622858L;

  // 业务处理service
  @Resource(name = "reserverBillService")
  private ReserverBillService reserverBillService;
  // 账单实体
  private ReserverBill reserverBill;
  // 流水实体
  private BnesAcctTransactionQuery bnesAcctTransactionQuery;
  // 分页对象
  private Page page;
  // 预备金业务实体
  private ReserverInfo reserverInfo;
  // 操作业务实体
  @Resource(name = "reserverInfoService")
  private ReserverInfoService reserverInfoService;

  // 分页显示账单列表
 /* 删除预备金相关
    public String PageList() {
    if (page == null) {
      page = new Page();
    }
    if (reserverBill == null) {
      reserverBill = new ReserverBill();
    }
    try {
      page = reserverBillService.queryReserverBillAll(page, reserverBill);
    } catch (Exception e) {
      logger.error("分页获取账单列表失败" + e.getMessage(), e);
    }
    return "pageSuccess";
  }

  // 手动生成弹出页面
  public String clickAddBill() {
    return "clickAddBill";
  }

  // 获取开通了的预备金账户
  public String chooseReserver() {
    if (page == null) {
      page = new Page();
    }
    if (reserverInfo == null) {
      reserverInfo = new ReserverInfo();
    }
    try {
      page = reserverInfoService.selectReserverInfoAll(page, reserverInfo);
    } catch (Exception e) {
      logger.error("获取预备金列表失败" + e.getMessage(), e);
    }
    return "chooseReserver";
  }

  // 手动生成账单
  public String billAdd() {
    // 手动生成账单
    // 传入bill对象时间段
    if (reserverBill == null) {
      reserverBill = new ReserverBill();
    }
    Date startTime = reserverBill.getStartDateStarttime();
    Date endTime = reserverBill.getStartDateEndtime();
    Map<String, Object> map = new HashMap<String, Object>();
    map.put("startTime", startTime);
    map.put("endTime", endTime);
    map.put("accountLogin", reserverBill.getAccountLogin());
    try {
      reserverBillService.insert(map);
      // 绑定消息展示内容
      this.addActionMessage(ConfigureUtils.getMessageConfig("reserverBill.add.success"));
    } catch (Exception e) {
      logger.error("手动生成账单失败" + e.getMessage(), e);
    }
    return this.PageList();
  }
*/


  public ReserverInfo getReserverInfo() {
    return reserverInfo;
  }

  public void setReserverInfo(ReserverInfo reserverInfo) {
    this.reserverInfo = reserverInfo;
  }

  public BnesAcctTransactionQuery getBnesAcctTransactionQuery() {
    return bnesAcctTransactionQuery;
  }

  public void setBnesAcctTransactionQuery(BnesAcctTransactionQuery bnesAcctTransactionQuery) {
    this.bnesAcctTransactionQuery = bnesAcctTransactionQuery;
  }

  @Override
public Page getPage() {
    return page;
  }

  @Override
public void setPage(Page page) {
    this.page = page;
  }

  public ReserverBill getReserverBill() {
    return reserverBill;
  }

  public void setReserverBill(ReserverBill reserverBill) {
    this.reserverBill = reserverBill;
  }

  @Override
  public Object getModel() {

    return null;
  }



}
