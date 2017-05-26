package com.pltfm.app.action;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.kmzyc.commons.page.Page;
import com.opensymphony.xwork2.ModelDriven;
import com.pltfm.app.service.BnesCustomerTypeService;
import com.pltfm.app.service.LoginInfoService;
import com.pltfm.app.service.UserLevelService;
import com.pltfm.app.util.ConfigureUtils;
import com.pltfm.app.util.ListUtils;
import com.pltfm.app.util.Token;
import com.pltfm.app.vobject.BnesCustomerTypeQuery;
import com.pltfm.app.vobject.UserLevel;

/**
 * 客户等级action处理类
 *
 * @author zhl
 * @since 2013-07-08
 */
@Component(value = "userLevelAction")
@Scope(value = "prototype")
public class UserLevelAction extends BaseAction implements ModelDriven {
    private static final long serialVersionUID = -579941341825593085L;
    private UserLevel userLevel;
    @Resource(name = "userLevelService")
    public UserLevelService userLevelService;
    @Resource(name = "bnesCustomerTypeService")
    private BnesCustomerTypeService bnesCustomerTypeService;
    @Resource(name = "loginInfoService")
    private LoginInfoService loginInfoService;

    /**
     * 查询列表页面集合
     **/
    public List<UserLevel> userLevelList;
    /**
     * 多条删除客户等级id集合
     **/
    public List<String> levelId;
    /**
     * 客户等级主键
     **/
    private String userLevelId;
    /**
     * 客户类别主键
     **/
    private String customerId;
    /**
     * 分页对象
     **/
    private Page page;
    /**
     * 验证编号
     **/
    private String checkCode;
    /**
     * 客户类别集合
     **/
    private List customerTypeList;
    private String actionMessage;

    @Override
    public UserLevel getModel() {
        userLevel = new UserLevel();
        return userLevel;
    }

    /**
     * 查询客户类型
     */
    public void operateCustomerType() {
        try {
            customerTypeList = bnesCustomerTypeService.findParentList(Integer.valueOf("0"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 异步调用客户类别信息
     */
    public void ajaxOperateCustomerType() {
        if (!customerId.equals("")) {
            try {
                super.writeJson(
                        bnesCustomerTypeService.findParentList(Integer.valueOf(customerId)));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 异步验证客户等级编号时候存在
     */
    public void ajaxCheckCode() {
        if (checkCode != null || checkCode != "") {
            userLevel.setCode(checkCode);
            try {
                List list = userLevelService.getUserLevellist(userLevel);
                int value = 0;
                if (ListUtils.isNotEmpty(list)) {
                    if (!userLevelId.equals("")) {
                        UserLevel userLevel = (UserLevel) list.get(0);
                        if (userLevel.getN_level_id().equals(Integer.valueOf(userLevelId))) {
                            value = 0;
                        } else {
                            value = 1;
                        }
                    } else {
                        value = 1;
                    }
                }
                super.writeJson(value);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 异步获取某类客户最大
     */
    public void ajaxOperateExpend() {
        try {
            Integer number = userLevelService.getMaxExpendCustomer(Integer.valueOf(customerId));
            super.writeJson(number);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询等级是否被使用
     */
    public void ajaxOperateLevel() {
        try {
            Integer number = loginInfoService.countLevel(Integer.valueOf(userLevelId));
            super.writeJson(number);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 进入添加客户等级页面
     */
    public String operateAdd() {
        operateCustomerType();
        return "addSuccess";
    }

    /**
     * 添加客户等级信息
     */
    @Token
    public String operateSave() {
        try {
            // 执行更新客户等级
            if (userLevel != null) {
                if (userLevel.getN_level_id() != null) {
                    operateUpdate();
                    // 执行添加客户等级
                } else {
                    if (userLevel.getCustomer_son_id() != null) {
                        userLevel.setN_customer_type_id(userLevel.getCustomer_son_id());
                    }
                    userLevel.setD_create_date(new Date());
                    userLevelService.addUserLevel(userLevel);
                    this.addActionMessage(ConfigureUtils.getMessageConfig("userlevel.add.success"));
                }
            }
            userLevel = new UserLevel();
            return this.queryPageList();
        } catch (Exception e) {
            e.printStackTrace();
            this.addActionError(ConfigureUtils.getMessageConfig("userlevel.operate.fail"));
            return operateAdd();
        }

    }

    /**
     * 进入修改页面
     */
    public String operateEdit() {
        try {
            operateCustomerType();
            userLevel = userLevelService.searchByPrimaryKey(Integer.valueOf(userLevelId));
            BnesCustomerTypeQuery customer = bnesCustomerTypeService
                    .findBnesCustomerTypeDOByPrimaryKey(userLevel.getN_customer_type_id());
            if (customer != null && customer.getParentId() != 0) {
                userLevel.setN_customer_type_id(customer.getParentId());
                userLevel.setCustomer_son_id(customer.getCustomerTypeId());
            }
            return "editSuccess";
        } catch (Exception e) {
            e.printStackTrace();
            this.addActionError(ConfigureUtils.getMessageConfig("userlevel.query.fail"));
            return operateAdd();
        }
    }

    /**
     * 更新客户等级信息
     */
    public String operateUpdate() {
        try {
            if (userLevel.getCustomer_son_id() != null) {
                userLevel.setN_customer_type_id(userLevel.getCustomer_son_id());
            }
            userLevel.setD_modify_date(new Date());
            userLevelService.updateUserLevel(userLevel);
            this.addActionMessage(ConfigureUtils.getMessageConfig("userlevel.update.success"));
            userLevel = new UserLevel();
            return "updateSuccess";
        } catch (Exception e) {
            e.printStackTrace();
            this.addActionError(ConfigureUtils.getMessageConfig("userlevel.operate.fail"));
            return operateAdd();
        }
    }

    /**
     * 查询客户等级信息
     */
    public String queryUserLevelList() {
        try {
            userLevelList = userLevelService.getUserLevellist(userLevel);
            return "querySuccess";
        } catch (Exception e) {
            e.printStackTrace();
            this.addActionError(ConfigureUtils.getMessageConfig("userlevel.query.fail"));
            return "queryFail";
        }
    }

    /**
     * 分页查询客户等级信息
     */
    public String queryPageList() {
        try {
            if (page == null) {
                page = new Page();
            }
            operateCustomerType();
            // 设置查询条件默认值 如果有二级 则保证一级二级为默认选中
            if (userLevel != null && userLevel.getCustomer_son_id() != null) {
                customerId = String.valueOf(userLevel.getN_customer_type_id());
                userLevel.setN_customer_type_id(userLevel.getCustomer_son_id());
                page = userLevelService.queryForPage(userLevel, page);
                userLevel.setN_customer_type_id(Integer.valueOf(customerId));
            } else {
                page = userLevelService.queryForPage(userLevel, page);
            }
            return "pageSuccess";
        } catch (Exception e) {
            e.printStackTrace();
            operateCustomerType();
            this.addActionError(ConfigureUtils.getMessageConfig("userlevel.query.fail"));
            return "queryFail";
        }
    }

    /**
     * 多条删除客户等级信息
     */
    @Token
    public String operateDeleteAll() {
        try {
            userLevelService.deleteUserLevel(levelId);
            this.addActionMessage(ConfigureUtils.getMessageConfig("userlevel.delete.success"));
            return queryPageList();
        } catch (Exception e) {
            e.printStackTrace();
            this.addActionError(ConfigureUtils.getMessageConfig("userlevel.delete.fail"));
            return queryPageList();
        }
    }

    /**
     * 删除客户等级信息
     */
    @Token
    public String operateDelete() {
        try {
            userLevelService.deleteByPrimaryKey(Integer.valueOf(userLevelId));
            this.addActionMessage(ConfigureUtils.getMessageConfig("userlevel.delete.success"));
            return queryPageList();
        } catch (Exception e) {
            e.printStackTrace();
            this.addActionError(ConfigureUtils.getMessageConfig("userlevel.delete.fail"));
            return queryPageList();
        }
    }

    public UserLevel getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(UserLevel userLevel) {
        this.userLevel = userLevel;
    }

    public UserLevelService getUserLevelService() {
        return userLevelService;
    }

    public void setUserLevelService(UserLevelService userLevelService) {
        this.userLevelService = userLevelService;
    }

    public List<UserLevel> getUserLevelList() {
        return userLevelList;
    }

    public void setUserLevelList(List<UserLevel> userLevelList) {
        this.userLevelList = userLevelList;
    }

    public List<String> getLevelId() {
        return levelId;
    }

    public void setLevelId(List<String> levelId) {
        this.levelId = levelId;
    }

    @Override
    public Page getPage() {
        return page;
    }

    @Override
    public void setPage(Page page) {
        this.page = page;
    }

    public String getUserLevelId() {
        return userLevelId;
    }

    public void setUserLevelId(String userLevelId) {
        this.userLevelId = userLevelId;
    }

    public BnesCustomerTypeService getBnesCustomerTypeService() {
        return bnesCustomerTypeService;
    }

    public void setBnesCustomerTypeService(BnesCustomerTypeService bnesCustomerTypeService) {
        this.bnesCustomerTypeService = bnesCustomerTypeService;
    }

    public List getCustomerTypeList() {
        return customerTypeList;
    }

    public void setCustomerTypeList(List customerTypeList) {
        this.customerTypeList = customerTypeList;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCheckCode() {
        return checkCode;
    }

    public LoginInfoService getLoginInfoService() {
        return loginInfoService;
    }

    public void setLoginInfoService(LoginInfoService loginInfoService) {
        this.loginInfoService = loginInfoService;
    }

    public void setCheckCode(String checkCode) {
        this.checkCode = checkCode;
    }

    public String getActionMessage() {
        return actionMessage;
    }

    public void setActionMessage(String actionMessage) {
        this.actionMessage = actionMessage;
    }

}
