package com.pltfm.app.action;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.kmzyc.commons.page.Page;
import com.opensymphony.xwork2.ModelDriven;
import com.pltfm.app.service.AccountInfoService;
import com.pltfm.app.service.BnesAcctTransactionService;
import com.pltfm.app.service.ReserverInfoService;
import com.pltfm.app.vobject.AccountInfo;
import com.pltfm.app.vobject.BnesAcctTransactionQuery;
import com.pltfm.app.vobject.ReserverInfo;

@SuppressWarnings("rawtypes")
@Component(value = "reserverInfoAction")
@Scope(value = "prototype")
public class ReserverInfoAction extends BaseAction implements ModelDriven {
  private static final long serialVersionUID = -1179832644539288719L;
  // 日志类
  private Logger logger = LoggerFactory.getLogger(ReserverInfoAction.class);
  // 操作业务实体
  @Resource(name = "reserverInfoService")
  private ReserverInfoService reserverInfoService;
  @Resource(name = "accountInfoService")
  private AccountInfoService accountInfoService;
  @Resource(name = "bnesAcctTransactionService")
  private BnesAcctTransactionService bnesAcctTransactionService;

  // 分页对象
  private Page page;
  // 预备金Id参数
  private String reserveId;
  // 用户登陆id
  private String userLoginId;
  // 交易流水交易类型
  private Integer type;
  // 交易流水交易内容
  private String content;
  // 交易发生金额
  private String changePay;
  // 交易流水交易对象 4 预备金
  private Integer typeObj = 4;
  // 交易状态 1成功
  private Integer status = 1;
  // 短信发送方式 2通知
  private Integer sendType = 2;
  // 短信发送方 2 中药材
  private Integer sended = 2;
  // 预备金业务实体
  private ReserverInfo reserverInfo;
  // 注册账户实体
  private AccountInfo accountInfo;
  // 交易流水实体
  private BnesAcctTransactionQuery bnesAcctTransactionQuery = new BnesAcctTransactionQuery();
  // 操作编号 1详情 ,修改2开通 3在开通4调整
  private String opeaderId;
  // 详情 1 修改 2调整3开通4
  private String editId;


  // 初始化预备金列表
  /*删除预备金相关
  public String PageList() {
    if (page == null) {
      page = new Page();
    }
    if (reserverInfo == null) {
      reserverInfo = new ReserverInfo();
    }
    try {
      page = reserverInfoService.selectReserverInfo(page, reserverInfo);
    } catch (Exception e) {
      logger.error("获取预备金列表失败" + e.getMessage(), e);
    }
    return "pageSuccess";
  }

  // 预备金编辑提交
  public String submitInfo() {
    BigDecimal changeTrans = BigDecimal.ZERO;
    DecimalFormat df1 = new DecimalFormat("####.00");
    try {
      if (reserverInfo == null) {
        reserverInfo = new ReserverInfo();
      }
      reserverInfo.setLoginId(new BigDecimal(userLoginId));
      ReserverInfo reserver = reserverInfoService.selectByPrimaryKey(reserverInfo);
      // 初次开通
      if (reserver == null && ("2").equals(opeaderId)) {
        // 没有就添加预备金账户
        reserverInfo.setDescription("后台录入");
        reserverInfo.setIsAvailable((short) 1);
        reserverInfo.setLoginId(new BigDecimal(userLoginId));
        reserverInfo.setRemainLimit(reserverInfo.getTotalLimit());
        // 添加创建时间
        reserverInfo.setOpenDate(DateTimeUtils.getCalendarInstance().getTime());
        // 添加预备金账户
        reserverInfoService.insertSelective(reserverInfo);
        // 绑定消息展示内容
        this.addActionMessage(ConfigureUtils.getMessageConfig("reserverInfo.open.success"));
        // 设置交易流水类型8 预备金开通
        type = 8;
        // 设置交易流水内容
        content = "预备金开通";
        // 设置流水金额
        changeTrans = reserverInfo.getTotalLimit();
      }
      // 在开通
      if (opeaderId == "3" || ("3").equals(opeaderId)) {
        // 设置交易流水类型8 预备金开通
        type = 8;
        content = "预备金再次开通,总额度:￥" + df1.format(reserverInfo.getTotalLimit());
        // 再次开通
        reserverInfo.setIsAvailable((short) 1);
        // 绑定消息展示内容
        this.addActionMessage(ConfigureUtils.getMessageConfig("reserverInfo.open.success"));
      }
      // 异常调整
      if (opeaderId == "4" || ("4").equals(opeaderId)) {
        // 获取当前预备金账户
        reserverInfo = reserverInfoService.selectByPrimaryKey(reserverInfo);
        // 异常调整 调整可用余额
        BigDecimal change = reserverInfo.getRemainLimit().add(new BigDecimal(changePay));
        if (change.compareTo(new BigDecimal(0)) == -1) {
          logger.error("预备金异常调整失败");
          // 可用小于0
          // 绑定消息展示内容
          this.addActionMessage(ConfigureUtils.getMessageConfig("reserverInfo.change.false"));
          return this.PageList();
        } else {
          reserverInfo.setRemainLimit(change);
          // 设置交易流水类型 15预备金调整
          type = 15;
          content = "预备金调整";
          // 设置流水金额
          changeTrans = new BigDecimal(Double.parseDouble(changePay));
          // 绑定消息展示内容
          this.addActionMessage(ConfigureUtils.getMessageConfig("reserverInfo.change.success"));
        }
      }
      // 详情修改
      if ("1".equals(opeaderId)) {
        // 获取当前预备金账户
        ReserverInfo info = reserverInfoService.selectByPrimaryKey(reserverInfo);
        // 设置可用额度
        BigDecimal changeMout =
            reserverInfo.getTotalLimit().subtract(info.getTotalLimit()).add(info.getRemainLimit());
        // 下调额度时判断可用额度是否小于0
        if (changeMout.compareTo(BigDecimal.ZERO) == -1) {
          logger.info("预备金审核失败");
          // 绑定消息展示内容
          this.addActionMessage(ConfigureUtils.getMessageConfig("reserverInfo.update.false"));
          return PageList();
        }
        // 设置可用额度
        reserverInfo.setRemainLimit(changeMout);
        // 设置交易流水类型 9 变更
        type = 9;
        content = "预备金总额度变更到￥" + df1.format(reserverInfo.getTotalLimit());
        // 绑定消息展示内容
        this.addActionMessage(ConfigureUtils.getMessageConfig("reserverInfo.update.success"));
      }
      if (StringUtils.isNotBlank(reserveId)) {
        reserverInfo.setReserveId(new BigDecimal(reserveId));
        reserverInfoService.updateByPrimaryKey(reserverInfo);
      }
      // 编辑完预备金插入交易流水并发送短信
      // 交易流水赋值
      // 获取accountId
      accountInfo = accountInfoService.selectByPrimaryLoginInfo(Integer.valueOf(userLoginId));
      bnesAcctTransactionQuery.setAccountId(Integer.valueOf(accountInfo.getN_AccountId()));
      bnesAcctTransactionQuery.setType(type);
      bnesAcctTransactionQuery.setContent(content);
      bnesAcctTransactionQuery.setAmount(changeTrans);
      bnesAcctTransactionQuery.setStatus(status);
      String number = new SimpleDateFormat("yyyyMMddhhmmssSSS").format(new Date());
      bnesAcctTransactionQuery.setAccountNumber(number);
      // 添加创建时间
      bnesAcctTransactionQuery.setCreateDate(DateTimeUtils.getCalendarInstance().getTime());
      bnesAcctTransactionQuery.setCreatedId(Integer.valueOf(userLoginId));
      bnesAcctTransactionQuery.setTrasObject(typeObj);
      // 添加变更后插入流水
      bnesAcctTransactionService.insertBnesAcctTransactionDO(bnesAcctTransactionQuery);
      // 调用短信接口
      List<Long> uidList = new ArrayList<Long>();
      List<String> phoneList = new ArrayList<String>();
      uidList.add(Long.valueOf(userLoginId));
      phoneList.add(reserverInfo.getPhone());
      messageRemoteService.sendMsgBySpread(uidList, phoneList,
          "您的预备金开通/变更已经生效了，请进入“我的预备金”查看您最新的账户信息。", sendType, sended);
    } catch (Exception e) {
      logger.error("预备金编辑失败" + e.getMessage(), e);
    }
    return this.PageList();
  }

  // 关闭预备金账户
  public String reserverClose() {
    try {
      if (reserverInfo == null) {
        reserverInfo = new ReserverInfo();
      }
      // 设置预备金账户停用
      reserverInfo.setIsAvailable((short) 2);
      reserverInfo.setReserveId(new BigDecimal(reserveId));
      // 修改预备金账户
      reserverInfoService.updateByPrimaryKey(reserverInfo);
      // 绑定消息展示内容
      this.addActionMessage(ConfigureUtils.getMessageConfig("reserverInfo.close.success"));
      // 生成预备金关闭关闭的交易明细
      type = 10;
      content = "预备金关闭";
      // 获取accountId
      accountInfo = accountInfoService.selectByPrimaryLoginInfo(Integer.valueOf(userLoginId));
      bnesAcctTransactionQuery.setAccountId(Integer.valueOf(accountInfo.getN_AccountId()));
      bnesAcctTransactionQuery.setType(type);
      bnesAcctTransactionQuery.setContent(content);
      bnesAcctTransactionQuery.setStatus(status);
      String number = new SimpleDateFormat("yyyyMMddhhmmssSSS").format(new Date());
      bnesAcctTransactionQuery.setAccountNumber(number);
      // 添加创建时间
      bnesAcctTransactionQuery.setCreateDate(DateTimeUtils.getCalendarInstance().getTime());
      bnesAcctTransactionQuery.setCreatedId(Integer.valueOf(userLoginId));
      bnesAcctTransactionQuery.setTrasObject(typeObj);
      // 添加变更后插入流水
      bnesAcctTransactionService.insertBnesAcctTransactionDO(bnesAcctTransactionQuery);
    } catch (Exception e) {
      logger.error("关闭预备金失败" + e.getMessage(), e);
    }
    reserverInfo = new ReserverInfo();
    return PageList();
  }

  public String edit() {
    // 根据reserverId判断是否有预备金账户 在开通/详情
    try {
      if (StringUtils.isNotBlank(reserveId)) {
        // 获取老的预备金账户信息
        if (reserverInfo == null) {
          reserverInfo = new ReserverInfo();
        }
        reserverInfo.setReserveId(new BigDecimal(reserveId));
        reserverInfo = reserverInfoService.selectByPrimaryKey(reserverInfo);
        accountInfo =
            accountInfoService.selectAccountAndCnameByLoginId(reserverInfo.getLoginId().intValue());
      } else {
        accountInfo =
            accountInfoService.selectAccountAndCnameByLoginId(Integer.valueOf(userLoginId));
      }
    } catch (Exception e) {
      logger.error("获取预备金信息失败" + e.getMessage(), e);
    }
    return "edit";
  }
*/


  public String getEditId() {
    return editId;
  }

  public void setEditId(String editId) {
    this.editId = editId;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public String getChangePay() {
    return changePay;
  }

  public void setChangePay(String changePay) {
    this.changePay = changePay;
  }

  public String getOpeaderId() {
    return opeaderId;
  }

  public void setOpeaderId(String opeaderId) {
    this.opeaderId = opeaderId;
  }

  public ReserverInfo getReserverInfo() {
    return reserverInfo;
  }

  public AccountInfo getAccountInfo() {
    return accountInfo;
  }

  public void setAccountInfo(AccountInfo accountInfo) {
    this.accountInfo = accountInfo;
  }

  public void setReserverInfo(ReserverInfo reserverInfo) {
    this.reserverInfo = reserverInfo;
  }

  public String getUserLoginId() {
    return userLoginId;
  }

  public void setUserLoginId(String userLoginId) {
    this.userLoginId = userLoginId;
  }

  public String getReserveId() {
    return reserveId;
  }

  public void setReserveId(String reserveId) {
    this.reserveId = reserveId;
  }

  @Override
public Page getPage() {
    return page;
  }

  @Override
public void setPage(Page page) {
    this.page = page;
  }

  @Override
  public Object getModel() {
    return null;
  }

}
