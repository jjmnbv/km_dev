package com.pltfm.app.action;

import java.math.BigDecimal;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.kmzyc.commons.page.Page;
import com.opensymphony.xwork2.ModelDriven;
import com.pltfm.app.service.AccountInfoService;
import com.pltfm.app.service.BnesAcctTransactionService;
import com.pltfm.app.service.ReserverApplyInfoServce;
import com.pltfm.app.service.ReserverInfoService;
import com.pltfm.app.vobject.AccountInfo;
import com.pltfm.app.vobject.BnesAcctTransactionQuery;
import com.pltfm.app.vobject.ReserverApplyInfo;
import com.pltfm.app.vobject.ReserverInfo;

@SuppressWarnings("rawtypes")
@Component(value = "reserverApplyInfoAction")
@Scope(value = "prototype")
public class ReserverApplyInfoAction extends BaseAction implements ModelDriven {
    private static final long serialVersionUID = 877699866685686397L;
    // 日志类
    private Logger logger = LoggerFactory.getLogger(ReserverApplyInfoAction.class);

    @Resource(name = "reserverApplyInfoServce")
    private ReserverApplyInfoServce reserverApplyInfoServce;
    @Resource(name = "reserverInfoService")
    private ReserverInfoService reserverInfoService;
    @Resource(name = "accountInfoService")
    private AccountInfoService accountInfoService;
    @Resource(name = "bnesAcctTransactionService")
    private BnesAcctTransactionService bnesAcctTransactionService;

    // 分页类
    private Page page;
    // 预备金审核实体
    private ReserverApplyInfo reserverApplyInfo;
    // 预备金审核Id
    private BigDecimal applyNotesId;
    // 预备金实体
    private ReserverInfo reserverInfo;
    // 注册账户实体
    private AccountInfo accountInfo = new AccountInfo();
    // 交易流水实体
    private BnesAcctTransactionQuery bnesAcctTransactionQuery = new BnesAcctTransactionQuery();
    // 审核编号
    private String operateId;
    // 编辑代号
    private String editId;
    // 短信发送方式 2通知
    private Integer sendType = 2;
    // 短信发送方 2 中药材
    private Integer sended = 2;
    // 交易流水交易类型
    private Integer type;
    // 交易流水交易内容
    private String content;
    // 交易流水交易对象 4 预备金
    private Integer typeObj = 4;
    // 交易状态 1成功
    private Integer status = 1;

    // 分页查询预备金审核列表
    /*
     * 删除预备金相关 public String PageList() { if (page == null) { page = new Page(); } if
     * (reserverApplyInfo == null) { reserverApplyInfo = new ReserverApplyInfo(); } try { page =
     * reserverApplyInfoServce.queryReserverApplyInfoList(page, reserverApplyInfo); } catch
     * (Exception e) { logger.error("分页查询预备金审核列表失败" + e.getMessage(), e); return ERROR; } return
     * "pageSuccess"; }
     * 
     * // 根据预备金审核ID回去预备金审核详情信息 public String queryServerApplyDetail() { try { ReserverApplyInfo
     * record = new ReserverApplyInfo(); record.setApplyNotesId(applyNotesId); // 变通或者开通信息
     * List<ReserverApplyInfo> reserverApplyInfoList =
     * reserverApplyInfoServce.selectByPrimaryKey(record); reserverApplyInfo =
     * reserverApplyInfoList.get(0); // 根据登陆id获取注册信息
     * accountInfo.setN_LoginId(reserverApplyInfo.getLoginId().intValue()); accountInfo =
     * accountInfoService.showAccountInfo(accountInfo); // 变通 需要获取之前的开通信息 if
     * (reserverApplyInfo.getApplyType() == 2) { reserverInfo = new ReserverInfo();
     * reserverInfo.setLoginId(reserverApplyInfo.getLoginId()); reserverInfo =
     * reserverInfoService.selectByPrimaryKey(reserverInfo); } } catch (SQLException e) {
     * logger.error("获取预备金申请详情sql异常" + e.getMessage(), e); } catch (Exception e) {
     * logger.error("获取预备金申请详情失败" + e.getMessage(), e); } return "queryServerApplyDetail"; }
     * 
     * // 申请信息审核 public String submitApplyInfo() { BigDecimal changeTrans = BigDecimal.ZERO; if
     * (StringUtils.isNotBlank(operateId)) { try { // 1保存2通过3拒绝 if (operateId == "1" ||
     * ("1").equals(operateId)) { // 绑定消息展示内容
     * this.addActionMessage(ConfigureUtils.getMessageConfig("reserverApplyInfo.save.success")); }
     * else if (operateId == "2" || ("2").equals(operateId)) { // 通过 保存修改和修改状态
     * reserverApplyInfo.setStatus((short) 2); // 开通或者变更预备金账号 // 查询是否存在预备金用户loginId if (reserverInfo
     * == null) { reserverInfo = new ReserverInfo(); }
     * reserverInfo.setLoginId(reserverApplyInfo.getLoginId()); reserverInfo =
     * reserverInfoService.selectByPrimaryKey(reserverInfo); if (reserverInfo == null) {
     * reserverInfo = new ReserverInfo(); reserverInfo.setContact(reserverApplyInfo.getContact());
     * reserverInfo.setDescription(reserverApplyInfo.getDescription());
     * reserverInfo.setIsAvailable((short) 1);
     * reserverInfo.setLoginId(reserverApplyInfo.getLoginId()); // 添加创建时间
     * reserverInfo.setOpenDate(DateTimeUtils.getCalendarInstance().getTime());
     * reserverInfo.setPayType(reserverApplyInfo.getSettlementType());
     * reserverInfo.setPhone(reserverApplyInfo.getPhone());
     * reserverInfo.setTotalLimit(reserverApplyInfo.getApplyLimit());
     * reserverInfo.setRemainLimit(reserverApplyInfo.getApplyLimit()); // 添加预备金账户
     * reserverInfoService.insertSelective(reserverInfo); // 设置交易流水类型8 预备金开通 type = 8; changeTrans =
     * reserverApplyInfo.getApplyLimit(); // 设置交易流水内容 content = "预备金开通"; } else { // 变革预备金
     * 需要设置申请记录的原本额度 reserverApplyInfo.setOriginalLimit(reserverInfo.getTotalLimit()); // 赋值
     * reserverInfo.setDescription(reserverApplyInfo.getDescription()); // 设置可用额度 BigDecimal
     * changeMout = reserverApplyInfo.getApplyLimit()
     * .subtract(reserverInfo.getTotalLimit()).add(reserverInfo.getRemainLimit()); //
     * 下调额度时判断可用额度是否小于0 if (changeMout.compareTo(new BigDecimal(0)) == -1) { logger.info("预备金审核失败");
     * // 绑定消息展示内容 this.addActionMessage(
     * ConfigureUtils.getMessageConfig("reserverApplyInfo.submit.pleaseRefuse")); return PageList();
     * } else { reserverInfo.setRemainLimit(changeMout);
     * reserverInfo.setTotalLimit(reserverApplyInfo.getApplyLimit());
     * reserverInfo.setPayType(reserverApplyInfo.getSettlementType());
     * reserverInfo.setPhone(reserverApplyInfo.getPhone()); // 修改变更
     * reserverInfoService.updateByPrimaryKey(reserverInfo); // 设置交易流水类型 9 变更 type = 9;
     * DecimalFormat df1 = new DecimalFormat("####.00"); content = "预备金总额度变更到￥" +
     * df1.format(reserverApplyInfo.getApplyLimit()); } } // 绑定消息展示内容 this.addActionMessage(
     * ConfigureUtils.getMessageConfig("reserverApplyInfo.submit.success")); // 交易流水赋值 //
     * 获取accountId accountInfo = accountInfoService
     * .selectByPrimaryLoginInfo(reserverApplyInfo.getLoginId().intValue());
     * bnesAcctTransactionQuery.setAccountId(Integer.valueOf(accountInfo.getN_AccountId()));
     * bnesAcctTransactionQuery.setType(type); bnesAcctTransactionQuery.setContent(content);
     * bnesAcctTransactionQuery.setAmount(changeTrans); bnesAcctTransactionQuery.setStatus(status);
     * String number = new SimpleDateFormat("yyyyMMddhhmmssSSS").format(new Date());
     * bnesAcctTransactionQuery.setAccountNumber(number); // 添加创建时间
     * bnesAcctTransactionQuery.setCreateDate(DateTimeUtils.getCalendarInstance().getTime());
     * bnesAcctTransactionQuery.setCreatedId(reserverApplyInfo.getLoginId().intValue());
     * bnesAcctTransactionQuery.setTrasObject(typeObj); // 添加变更后插入流水
     * bnesAcctTransactionService.insertBnesAcctTransactionDO(bnesAcctTransactionQuery); // 调用短信接口
     * List<Long> uidList = new ArrayList<Long>(); List<String> phoneList = new ArrayList<String>();
     * uidList.add(reserverApplyInfo.getLoginId().longValue());
     * phoneList.add(reserverApplyInfo.getPhone()); messageRemoteService.sendMsgBySpread(uidList,
     * phoneList, "您提交的预备金开通/变更申请已经审核通过并生效了，请进入“我的预备金”查看您最新的账户信息。", sendType, sended); } else if
     * (operateId == "3" || ("3").equals(operateId)) { // 查询是否存在预备金用户loginId if (reserverInfo ==
     * null) { reserverInfo = new ReserverInfo(); }
     * reserverInfo.setLoginId(reserverApplyInfo.getLoginId()); reserverInfo =
     * reserverInfoService.selectByPrimaryKey(reserverInfo); if (reserverInfo != null) { // 变革预备金
     * 需要设置申请记录的原本额度 reserverApplyInfo.setOriginalLimit(reserverInfo.getTotalLimit()); } // 拒绝只要修改状态
     * reserverApplyInfo.setStatus((short) 3); // 调用短信接口 List<Long> uidList = new ArrayList<Long>();
     * List<String> phoneList = new ArrayList<String>();
     * uidList.add(reserverApplyInfo.getLoginId().longValue());
     * phoneList.add(reserverApplyInfo.getPhone()); messageRemoteService.sendMsgBySpread(uidList,
     * phoneList, "很遗憾，您提交的预备金开通/变更申请被拒绝了，具体原因请联系客服人员。", sendType, sended); // 绑定消息展示内容
     * this.addActionMessage(ConfigureUtils.getMessageConfig("reserverApplyInfo.submit.false")); }
     * // 修改申请预备金记录 reserverApplyInfoServce.updateByPrimaryKey(reserverApplyInfo); } catch
     * (Exception e) { logger.error("审核失败" + e.getMessage(), e); } } return PageList(); }
     */


    public String getEditId() {
        return editId;
    }

    public void setEditId(String editId) {
        this.editId = editId;
    }

    public BnesAcctTransactionQuery getBnesAcctTransactionQuery() {
        return bnesAcctTransactionQuery;
    }

    public void setBnesAcctTransactionQuery(BnesAcctTransactionQuery bnesAcctTransactionQuery) {
        this.bnesAcctTransactionQuery = bnesAcctTransactionQuery;
    }

    public BigDecimal getApplyNotesId() {
        return applyNotesId;
    }

    public void setApplyNotesId(BigDecimal applyNotesId) {
        this.applyNotesId = applyNotesId;
    }

    @Override
    public Page getPage() {
        return page;
    }

    @Override
    public void setPage(Page page) {
        this.page = page;
    }

    public ReserverApplyInfo getReserverApplyInfo() {
        return reserverApplyInfo;
    }

    public void setReserverApplyInfo(ReserverApplyInfo reserverApplyInfo) {
        this.reserverApplyInfo = reserverApplyInfo;
    }

    public ReserverInfo getReserverInfo() {
        return reserverInfo;
    }

    public void setReserverInfo(ReserverInfo reserverInfo) {
        this.reserverInfo = reserverInfo;
    }


    public AccountInfo getAccountInfo() {
        return accountInfo;
    }

    public void setAccountInfo(AccountInfo accountInfo) {
        this.accountInfo = accountInfo;
    }

    public String getOperateId() {
        return operateId;
    }

    public void setOperateId(String operateId) {
        this.operateId = operateId;
    }

    @Override
    public Object getModel() {
        return null;
    }

}
