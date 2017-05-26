package com.pltfm.app.action;

import java.math.BigDecimal;
import java.sql.SQLException;
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
import com.pltfm.app.service.BnesFrozenRecordService;
import com.pltfm.app.service.LoginInfoService;
import com.pltfm.app.util.ConfigureUtils;
import com.pltfm.app.util.Constants;
import com.pltfm.app.util.DateTimeUtils;
import com.pltfm.app.util.Token;
import com.pltfm.app.vobject.AccountInfo;
import com.pltfm.app.vobject.BnesAcctTransactionQuery;
import com.pltfm.app.vobject.BnesFrozenRecord;
import com.pltfm.app.vobject.CommercialTenantBasicInfo;
import com.pltfm.app.vobject.LoginInfo;
import com.pltfm.app.vobject.PersonalBasicInfo;
import com.pltfm.sys.model.SysUser;

/**
 * 冻结登录帐户ACTION
 * 
 */
@Component(value = "bnesFrozenRecordAction")
@Scope(value = "prototype")
public class BnesFrozenRecordAction extends BaseAction implements ModelDriven {
    private static final long serialVersionUID = 1L;
    @Resource(name = "bnesFrozenRecordService")
    private BnesFrozenRecordService bnesFrozenRecordService;
    // 交易流水实体
    private BnesAcctTransactionQuery bnesAcctTransactionQuery = new BnesAcctTransactionQuery();
    @Resource(name = "bnesAcctTransactionService")
    private BnesAcctTransactionService bnesAcctTransactionService;
    /** 余额变化记录 */
    @Resource(name = "bnesAcctTransListService")
    private BnesAcctTransListService bnesAcctTransListService;
    /**
     * 账户信息业务逻辑接口
     */
    @Resource(name = "accountInfoService")
    private AccountInfoService accountInfoService;

    @Resource(name = "loginInfoService")
    private LoginInfoService loginInfoService;

    /** 冻结帐户信息实体 */
    private BnesFrozenRecord bnesFrozenRecord;
    /** 分页类 */
    private Page page;
    /** 冻结帐户主键ID集合 **/
    private List<Integer> frozenRecordIds;
    /** 冻结帐户主键ID **/
    private Integer FRId;
    /** 登录帐户* */
    private List<LoginInfo> liginList;
    /** 账号状态 **/
    private Integer status;
    /** 修改_冻结 **/
    private Integer updateType;
    /** 冻结o类型 **/
    private Integer frozenType;
    /** 公共页面参数 **/
    private Integer showType;
    /** 个人基本信息 **/
    private PersonalBasicInfo personBasicInfo;
    /**
     * 商户信息实体
     */
    private CommercialTenantBasicInfo commercialTenantBasicInfo;
    /** 登录帐户名* */
    private LoginInfo loginInfo;
    /**
     * 登录信息主键
     */
    private Integer loginIn_Id;


    /**
     * 登录帐户冻结信息列表
     * 
     * @return
     */
    public String pageList() {
        try {
            bnesFrozenRecord.setStatus(null);
            page = bnesFrozenRecordService.searchPageByVo(page, bnesFrozenRecord);
            return "pageSuccess";
        } catch (Exception e) {
            this.addActionError(ConfigureUtils.getMessageConfig("frozenRecord.query.fail"));
            return "queryFail";
        }
    }

    /**
     * 进入添加冻结登录帐户信息
     * 
     * @return
     */
    public String preAdd() {
        try {
            /* liginList=bnesFrozenRecordService.getLoginAll(); */
            // 获取当前登录人
            SysUser sysuser = (SysUser) session.get("sysUser");
            bnesFrozenRecord.setSueName(sysuser.getUserName());
            bnesFrozenRecord.setSueDate(new Date());
            if (showType != null) {
                personBasicInfo = bnesFrozenRecordService.getLogin(bnesFrozenRecord.getLoginId());
                loginInfo = bnesFrozenRecordService.getLoginAccount(bnesFrozenRecord.getLoginId());
                commercialTenantBasicInfo =
                        bnesFrozenRecordService.selectloginId(bnesFrozenRecord.getLoginId());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "preAddSuccess";
    }



    /**
     * 保存冻结登录帐户信息
     * 
     * @return
     */

    public String add() {
        try {
            SysUser sysuser = (SysUser) session.get("sysUser");
            bnesFrozenRecord.setOperator(sysuser.getUserId());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String ly_time = sdf.format(new java.util.Date());
            bnesFrozenRecord.setOperatorDate(DateTimeUtils.parseTimestamp(ly_time));
            bnesFrozenRecord.setCreated(sysuser.getUserId());
            bnesFrozenRecord.setCreateDate(new Date());
            bnesFrozenRecord.setStatus(0);
            bnesFrozenRecordService.insert(bnesFrozenRecord);

            LoginInfo login = new LoginInfo();
            login.setN_LoginId(bnesFrozenRecord.getLoginId());
            login.setN_Status(0);
            loginInfoService.udpateLoginInfo(login);

            this.addActionMessage(ConfigureUtils.getMessageConfig("frozenRecord.add.success"));
            if (showType != null) {
                String loninAccount = bnesFrozenRecord.getUsername();
                bnesFrozenRecord = new BnesFrozenRecord();
                bnesFrozenRecord.setUsername(loninAccount);
            } else {
                bnesFrozenRecord = new BnesFrozenRecord();
            }
            return this.pageList();
        } catch (SQLException e) {
            e.printStackTrace();
            this.addActionError(ConfigureUtils.getMessageConfig("frozenRecord.add.fail"));
            return this.preAdd();
        }
    }

    public String preUpdate() {
        try {
            // liginList = bnesFrozenRecordService.getLoginAll();
            if (showType != null) {
                loginInfo = bnesFrozenRecordService.getLoginAccount(bnesFrozenRecord.getLoginId());
                bnesFrozenRecord.setUsername(loginInfo.getLoginAccount());
                bnesFrozenRecord = bnesFrozenRecordService.selectByLoginAccount(bnesFrozenRecord);
                return "frozenSuccess";
            } else {
                bnesFrozenRecord = bnesFrozenRecordService.getFrozenRecordId(FRId);
            }
            if (updateType == 1) {
                return "frozenSuccess";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "updateSuccess";
    }

    /**
     * 更新冻结登录帐户信息
     * 
     * @return
     */
    @Token
    public String update() {
        try {
            SysUser sysuser = (SysUser) session.get("sysUser");
            bnesFrozenRecord.setModified(sysuser.getUserId());
            bnesFrozenRecord.setModifyDate(DateTimeUtils.getCalendarInstance().getTime());
            bnesFrozenRecordService.update(bnesFrozenRecord);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "saveSuccess";
    }

    /**
     * 查询登录账号
     */
    public void checkName() {
        try {
            super.writeJson(bnesFrozenRecordService.getLogin(bnesFrozenRecord.getLoginId()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 进入添加冻结账户页面
     * 
     * @return
     */
    public String operateAdd() {
        return "addSuccess";
    }

    /**
     * 保存账户、金额冻结信息
     * 
     * @return
     */
    @Token
    public String opreateSave() {
        try {
            SysUser sysuser = (SysUser) session.get("sysUser");
            bnesFrozenRecord.setCreated(sysuser.getUserId());
            bnesFrozenRecord.setCreateDate(new Date());
            // 设置投诉账号为当前操作人登录账号
            bnesFrozenRecord.setSueName(sysuser.getUserName());

            // 账户冻结
            if (bnesFrozenRecord.getFrozenType() == 1) {
                AccountInfo accountInfo = new AccountInfo();
                accountInfo.setN_Status(0);
                bnesFrozenRecord.setStatus(0);
                accountInfo.setN_AccountId(bnesFrozenRecord.getAccountId());
                accountInfoService.updateAccountInfo(accountInfo);
                // 账户金额冻结
            } else if (bnesFrozenRecord.getFrozenType() == 2) {
                bnesFrozenRecord.setStatus(0);
                AccountInfo accountInfo =
                        accountInfoService.selectByPrimaryKey(bnesFrozenRecord.getAccountId());
                BigDecimal beforeAmountAvlibal = accountInfo.getAmountAvlibal();
                BigDecimal afterAmountAvlibal =
                        beforeAmountAvlibal.subtract(bnesFrozenRecord.getFrozenNumber());
                accountInfo.setAmountFrozen(accountInfo.getAmountFrozen().add(
                        bnesFrozenRecord.getFrozenNumber()));
                accountInfo.setAmountAvlibal(accountInfo.getAmountAvlibal().subtract(
                        bnesFrozenRecord.getFrozenNumber()));
                accountInfoService.updateAccountInfo(accountInfo);
                // 添加余额冻结流水
                // 添加冻结流水
                bnesAcctTransactionQuery
                        .setAccountId(accountInfo.getN_AccountId());
                bnesAcctTransactionQuery.setType(Constants.TRANSACTION_TYPE_FREEZING);
                bnesAcctTransactionQuery.setContent(accountInfo.getAccountLogin() + "余额冻结:￥"
                        + bnesFrozenRecord.getFrozenNumber().doubleValue());
                bnesAcctTransactionQuery.setAmount(bnesFrozenRecord.getFrozenNumber().negate());
                bnesAcctTransactionQuery.setStatus(Constants.TRANSACTIONSTATUSSUCCESS);
                String number = new SimpleDateFormat("yyyyMMddhhmmssSSS").format(new Date());
                bnesAcctTransactionQuery.setAccountNumber(number);
                // 添加创建时间
                bnesAcctTransactionQuery.setCreateDate(DateTimeUtils.getCalendarInstance()
                        .getTime());
                bnesAcctTransactionQuery.setCreatedId(accountInfo.getN_LoginId());
                Integer accountTransactionId =
                        bnesAcctTransactionService
                                .insertBnesAcctTransactionDO(bnesAcctTransactionQuery);
                // 添加余额交易变化记录
                BnesAcctTransListDO bnesAcctTransListDO = new BnesAcctTransListDO();
                bnesAcctTransListDO.setAccountId(accountInfo.getN_AccountId());
                bnesAcctTransListDO.setAccountTransactionId(accountTransactionId);
                bnesAcctTransListDO.setBeforeAmount(beforeAmountAvlibal);
                bnesAcctTransListDO.setAfterAmount(afterAmountAvlibal);
                bnesAcctTransListDO.setMoneyAmount(bnesAcctTransactionQuery.getAmount());
                bnesAcctTransListDO.setCreateDate(DateTimeUtils.getCalendarInstance().getTime());
                bnesAcctTransListDO.setCreatedId(bnesAcctTransactionQuery.getLoginId());
                bnesAcctTransListDO.setModifieId(bnesAcctTransactionQuery.getLoginId());
                bnesAcctTransListDO.setModifyDate(DateTimeUtils.getCalendarInstance().getTime());
                bnesAcctTransListService.insertBnesAcctTransListDO(bnesAcctTransListDO);
            }
            bnesFrozenRecordService.insert(bnesFrozenRecord);
            this.addActionMessage(ConfigureUtils.getMessageConfig("account.frozen.success"));
            bnesFrozenRecord = new BnesFrozenRecord();
            return queryAccFrozenPageList();
        } catch (Exception e) {
            e.printStackTrace();
            this.addActionError(ConfigureUtils.getMessageConfig("account.frozen.fail"));
            return "addSuccess";
        }
    }

    /**
     * 分页查询账户冻结/解冻信息
     * 
     */
    public String queryAccFrozenPageList() {
        try {
            if (page == null) {
                page = new Page();
            }
            bnesFrozenRecord.setFrozenType(2);// 账户冻结
            page = bnesFrozenRecordService.queryPageAccountFrozen(bnesFrozenRecord, page);
        } catch (Exception e) {
            e.printStackTrace();
            this.addActionError(ConfigureUtils.getMessageConfig("account.frozen.query.fail"));
        }
        return "querySuccess";
    }

    /**
     * 查询登录账号状态
     */
    public void checkStatus() {
        try {
            loginInfo = bnesFrozenRecordService.getLoginAccount(loginIn_Id);
            super.writeJson(loginInfo.getN_Status());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 进入解冻页面
     * 
     * @return
     */
    public String operateThawEdit() {
        try {
            bnesFrozenRecord = bnesFrozenRecordService.getFrozenRecordId(FRId);
            return "editSuccess";
        } catch (SQLException e) {
            e.printStackTrace();
            this.addActionError(ConfigureUtils.getMessageConfig("account.frozen.query.fail"));
            return "addSuccess";
        }
    }

    /**
     * 解冻冻结账户、金额
     * 
     * @return
     */
    @Token
    public String opreateUpdate() {
        try {
            SysUser sysuser = (SysUser) session.get("sysUser");
            bnesFrozenRecord.setOperator(sysuser.getUserId());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String ly_time = sdf.format(new java.util.Date());
            bnesFrozenRecord.setOperatorDate(DateTimeUtils.parseTimestamp(ly_time));
            // 账户冻结
            if (bnesFrozenRecord.getFrozenType() == 1) {
                AccountInfo accountInfo = new AccountInfo();
                accountInfo.setN_Status(1);
                bnesFrozenRecord.setStatus(1);
                accountInfo.setN_AccountId(bnesFrozenRecord.getAccountId());
                accountInfoService.updateAccountInfo(accountInfo);
                // 解冻账户、金额
            } else if (bnesFrozenRecord.getFrozenType() == 2) {
                bnesFrozenRecord.setStatus(1);
                AccountInfo accountInfo =
                        accountInfoService.selectByPrimaryKey(bnesFrozenRecord.getAccountId());
                BigDecimal beforeAmountAvlibal = accountInfo.getAmountAvlibal();
                BigDecimal afterAmountAvlibal =
                        beforeAmountAvlibal.add(bnesFrozenRecord.getFrozenNumber());
                accountInfo.setAmountFrozen(accountInfo.getAmountFrozen().subtract(
                        bnesFrozenRecord.getFrozenNumber()));
                accountInfo.setAmountAvlibal(accountInfo.getAmountAvlibal().add(
                        bnesFrozenRecord.getFrozenNumber()));
                accountInfoService.updateAccountInfo(accountInfo);
                // 添加解冻流水
                bnesAcctTransactionQuery
                        .setAccountId(accountInfo.getN_AccountId());
                bnesAcctTransactionQuery.setType(Constants.TRANSACTION_TYPE_THAW);
                bnesAcctTransactionQuery.setContent(accountInfo.getAccountLogin() + "余额解冻:￥"
                        + bnesFrozenRecord.getFrozenNumber());
                bnesAcctTransactionQuery.setAmount(bnesFrozenRecord.getFrozenNumber());
                bnesAcctTransactionQuery.setStatus(Constants.TRANSACTIONSTATUSSUCCESS);
                String number = new SimpleDateFormat("yyyyMMddhhmmssSSS").format(new Date());
                bnesAcctTransactionQuery.setAccountNumber(number);
                // 添加创建时间
                bnesAcctTransactionQuery.setCreateDate(DateTimeUtils.getCalendarInstance()
                        .getTime());
                bnesAcctTransactionQuery.setCreatedId(accountInfo.getN_LoginId());
                Integer accountTransactionId =
                        bnesAcctTransactionService
                                .insertBnesAcctTransactionDO(bnesAcctTransactionQuery);
                // 添加余额交易变化记录
                BnesAcctTransListDO bnesAcctTransListDO = new BnesAcctTransListDO();
                bnesAcctTransListDO.setAccountId(accountInfo.getN_AccountId());
                bnesAcctTransListDO.setAccountTransactionId(accountTransactionId);
                bnesAcctTransListDO.setBeforeAmount(beforeAmountAvlibal);
                bnesAcctTransListDO.setAfterAmount(afterAmountAvlibal);
                bnesAcctTransListDO.setMoneyAmount(bnesAcctTransactionQuery.getAmount());
                bnesAcctTransListDO.setCreateDate(DateTimeUtils.getCalendarInstance().getTime());
                bnesAcctTransListDO.setCreatedId(bnesAcctTransactionQuery.getLoginId());
                bnesAcctTransListDO.setModifieId(bnesAcctTransactionQuery.getLoginId());
                bnesAcctTransListDO.setModifyDate(DateTimeUtils.getCalendarInstance().getTime());
                bnesAcctTransListService.insertBnesAcctTransListDO(bnesAcctTransListDO);

            }
            bnesFrozenRecordService.update(bnesFrozenRecord);
            this.addActionMessage(ConfigureUtils.getMessageConfig("account.thraw.success"));
            bnesFrozenRecord = new BnesFrozenRecord();
            return this.queryAccFrozenPageList();
        } catch (Exception e) {
            e.printStackTrace();
            this.addActionError(ConfigureUtils.getMessageConfig("account.thraw.fail"));
            return "querySuccess";
        }
    }

    /**
     * 查询已冻结信息
     * 
     */
    @Deprecated
    public String searchFrozenRecord() {
        try {
            if (page == null) {
                page = new Page();
            }
            // bnesFrozenRecord.setStatus(1);
            page = bnesFrozenRecordService.queryPageAccountFrozen(bnesFrozenRecord, page);
        } catch (Exception e) {
            e.printStackTrace();
            this.addActionError(ConfigureUtils.getMessageConfig("account.thraw.query.fail"));
        }
        return "searchSuccess";
    }

    public Integer getFRId() {
        return FRId;
    }

    public void setFRId(Integer fRId) {
        FRId = fRId;
    }

    public Integer getUpdateType() {
        return updateType;
    }

    public void setUpdateType(Integer updateType) {
        this.updateType = updateType;
    }

    public Integer getStatus() {
        return status;
    }

    public LoginInfo getLoginInfo() {
        return loginInfo;
    }

    public void setLoginInfo(LoginInfo loginInfo) {
        this.loginInfo = loginInfo;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<LoginInfo> getLiginList() {
        return liginList;
    }

    public void setLiginList(List<LoginInfo> liginList) {
        this.liginList = liginList;
    }

    public BnesFrozenRecordService getBnesFrozenRecordService() {
        return bnesFrozenRecordService;
    }

    public void setBnesFrozenRecordService(BnesFrozenRecordService bnesFrozenRecordService) {
        this.bnesFrozenRecordService = bnesFrozenRecordService;
    }

    public BnesFrozenRecord getBnesFrozenRecord() {
        return bnesFrozenRecord;
    }

    public void setBnesFrozenRecord(BnesFrozenRecord bnesFrozenRecord) {
        this.bnesFrozenRecord = bnesFrozenRecord;
    }

    @Override
    public Page getPage() {
        return page;
    }

    @Override
    public void setPage(Page page) {
        this.page = page;
    }

    public List<Integer> getFrozenRecordIds() {
        return frozenRecordIds;
    }

    public void setFrozenRecordIds(List<Integer> frozenRecordIds) {
        this.frozenRecordIds = frozenRecordIds;
    }

    @Override
    public Object getModel() {
        bnesFrozenRecord = new BnesFrozenRecord();
        return bnesFrozenRecord;
    }

    public AccountInfoService getAccountInfoService() {
        return accountInfoService;
    }

    public void setAccountInfoService(AccountInfoService accountInfoService) {
        this.accountInfoService = accountInfoService;
    }

    public Integer getFrozenType() {
        return frozenType;
    }

    public void setFrozenType(Integer frozenType) {
        this.frozenType = frozenType;
    }

    public Integer getShowType() {
        return showType;
    }

    public void setShowType(Integer showType) {
        this.showType = showType;
    }

    public PersonalBasicInfo getPersonBasicInfo() {
        return personBasicInfo;
    }

    public void setPersonBasicInfo(PersonalBasicInfo personBasicInfo) {
        this.personBasicInfo = personBasicInfo;
    }

    public CommercialTenantBasicInfo getCommercialTenantBasicInfo() {
        return commercialTenantBasicInfo;
    }

    public void setCommercialTenantBasicInfo(CommercialTenantBasicInfo commercialTenantBasicInfo) {
        this.commercialTenantBasicInfo = commercialTenantBasicInfo;
    }

    public Integer getLoginIn_Id() {
        return loginIn_Id;
    }

    public void setLoginIn_Id(Integer loginInId) {
        loginIn_Id = loginInId;
    }
}
