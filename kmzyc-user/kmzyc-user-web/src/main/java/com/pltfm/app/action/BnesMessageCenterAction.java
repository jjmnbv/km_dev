package com.pltfm.app.action;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.kmzyc.commons.page.Page;
import com.opensymphony.xwork2.ModelDriven;
import com.pltfm.app.service.BnesCustomerTypeService;
import com.pltfm.app.service.BnesMessageCenterService;
import com.pltfm.app.util.ConfigureUtils;
import com.pltfm.app.util.Token;
import com.pltfm.app.vobject.BnesInfoPrompt;
import com.pltfm.app.vobject.BnesMessageCenter;
import com.pltfm.app.vobject.LoginInfo;

@SuppressWarnings("unchecked")
@Component(value = "bnesMessageCenterAction")
@Scope(value = "prototype")
public class BnesMessageCenterAction extends BaseAction implements ModelDriven {
    private static final long serialVersionUID = -579941341825593085L;
    @Resource(name = "bnesMessageCenterService")
    private BnesMessageCenterService bnesMessageCenterService;
    @Resource(name = "bnesCustomerTypeService")
    private BnesCustomerTypeService bnesCustomerTypeService;
    /** 公共页面返回列表类型 **/
    private Integer showType;
    private Integer viewOnly;
    /**
     * 消息实体
     * 
     */
    private BnesMessageCenter bnesMessageCenter;

    /**
     * 分页类
     */
    private Page page;
    /**
     * 消息主键ID集合
     */
    private List<Integer> messageIds;
    /**
     * 消息主键ID
     */
    private Integer messageId;
    /**
     * 登录帐户
     */
    private List<LoginInfo> liginList;


    /**
     * 消息标题
     */
    private List<BnesInfoPrompt> titleList;
    /** 客户类别集合 **/
    private List customerTypeList;

    /** 客户类别主键 **/
    private String customerId;
    private String flag;

    /**
     * 消息信息列表
     * 
     * @return
     */
    public String pageList() {
        try {
            String isMenu = this.getRequest().getParameter("isMenu");
            if (!"true".equals(isMenu)) {
                if (page == null) {
                    page = new Page();
                }
                operateCustomerType();
                // 设置查询条件默认值 如果有二级 则保证一级二级为默认选中
                if (bnesMessageCenter != null && bnesMessageCenter.getCustomer_son_id() != null) {
                    customerId = String.valueOf(bnesMessageCenter.getCustomerTypeId());
                    bnesMessageCenter.setCustomerTypeId(bnesMessageCenter.getCustomer_son_id());
                    page = bnesMessageCenterService.searchPageByVo(page, bnesMessageCenter);
                    bnesMessageCenter.setCustomerTypeId(Integer.valueOf(customerId));
                } else {
                    if (null == bnesMessageCenter) {
                        bnesMessageCenter = new BnesMessageCenter();
                    }
                    if (showType != null) {
                        LoginInfo loginInfo =
                                bnesMessageCenterService.getLoginName(bnesMessageCenter
                                        .getAccountId());
                        bnesMessageCenter.setLoginAccount(loginInfo.getLoginAccount());
                    }
                    page = bnesMessageCenterService.searchPageByVo(page, bnesMessageCenter);
                }
                if ("1".equals(flag)) {
                    return "homePage";
                }
                return "pageSuccess";
            } else {
                flag = "0";
                return "pageSuccess";
            }
        } catch (Exception e) {
            this.addActionError(ConfigureUtils.getMessageConfig("messageCenter.query.fail"));
            return "queryFail";
        }
    }

    @Token
    public String detele() {
        try {
            bnesMessageCenterService.delete(messageIds);
            this.addActionMessage(ConfigureUtils.getMessageConfig("messageCenter.delete.success"));
            return this.pageList();
        } catch (SQLException e) {
            e.printStackTrace();
            this.addActionError(ConfigureUtils.getMessageConfig("messageCenter.delete.fail"));
            return this.pageList();
        }
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
        try {
            super.writeJson(bnesCustomerTypeService.findParentList(Integer.valueOf(customerId)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 进入添加消息信息列表
     * 
     * @return
     */
    public String preAdd() {
        try {
            bnesMessageCenterService.insertAll(22);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "preAddSuccess";
    }

    public String add() {
        try {
            bnesMessageCenterService.insert(bnesMessageCenter);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "saveSuccess";
    }

    public String preUpdate() {
        try {
            bnesMessageCenter = bnesMessageCenterService.getMessageId(messageId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "updateSuccess";
    }

    public String update() {
        try {
            bnesMessageCenterService.update(bnesMessageCenter);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "saveSuccess";
    }

    public List<LoginInfo> getLiginList() {
        return liginList;
    }

    public void setLiginList(List<LoginInfo> liginList) {
        this.liginList = liginList;
    }

    public List<BnesInfoPrompt> getTitleList() {
        return titleList;
    }

    public void setTitleList(List<BnesInfoPrompt> titleList) {
        this.titleList = titleList;
    }

    public List<Integer> getMessageIds() {
        return messageIds;
    }

    public void setMessageIds(List<Integer> messageIds) {
        this.messageIds = messageIds;
    }

    public Integer getMessageId() {
        return messageId;
    }

    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    @Override
    public Page getPage() {
        return page;
    }

    @Override
    public void setPage(Page page) {
        this.page = page;
    }


    public BnesMessageCenterService getBnesMessageCenterService() {
        return bnesMessageCenterService;
    }

    public void setBnesMessageCenterService(BnesMessageCenterService bnesMessageCenterService) {
        this.bnesMessageCenterService = bnesMessageCenterService;
    }

    public BnesMessageCenter getBnesMessageCenter() {
        return bnesMessageCenter;
    }

    public void setBnesMessageCenter(BnesMessageCenter bnesMessageCenter) {
        this.bnesMessageCenter = bnesMessageCenter;
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

    @Override
    public Object getModel() {
        bnesMessageCenter = new BnesMessageCenter();
        return bnesMessageCenter;
    }

    public Integer getShowType() {
        return showType;
    }

    public void setShowType(Integer showType) {
        this.showType = showType;
    }

    public Integer getViewOnly() {
        return viewOnly;
    }

    public void setViewOnly(Integer viewOnly) {
        this.viewOnly = viewOnly;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }
}
