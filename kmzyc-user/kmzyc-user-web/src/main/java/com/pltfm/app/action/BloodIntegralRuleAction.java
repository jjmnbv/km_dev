package com.pltfm.app.action;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.kmzyc.commons.page.Page;
import com.opensymphony.xwork2.ModelDriven;
import com.pltfm.app.service.BloodIntegralRuleService;
import com.pltfm.app.util.ConfigureUtils;
import com.pltfm.app.util.ListUtils;
import com.pltfm.app.util.Token;
import com.pltfm.app.vobject.BloodIntegralRule;
import com.pltfm.app.vobject.BnesCustomerTypeQuery;
import com.pltfm.sys.model.SysUser;

/***
 * 经验规则Action
 */
@Component(value = "bloodIntegralRuleAction")
@Scope(value = "prototype")
public class BloodIntegralRuleAction extends BaseAction implements ModelDriven {
    private static final long serialVersionUID = -579941341825593085L;
    /**
     * 经验规则bloodIntegralRuleService
     */
    @Resource(name = "bloodIntegralRuleService")
    private BloodIntegralRuleService bloodIntegralRuleService;
    /***
     * 经验规则实体
     */
    private BloodIntegralRule bloodIntegralRule;
    /**
     * 经验值规则 ID
     **/
    private Integer integralRule_Id;
    /**
     * 经验值规则 ID集合
     **/
    private List<Integer> integralRuleIds;
    /**
     * 专家下的子级类别集合
     */
    private List<BnesCustomerTypeQuery> customerTypeList;
    /**
     * 分页类
     */
    private Page page;
    private int customerTypeId; // 客户类型;
    /**
     * 验证编号
     **/
    private String checkCode;
    private String irId;


    /**
     * 经验规则列表
     */
    public String queryPageList() {
        try {
            if (page == null) {
                page = new Page();
            }
            // customerTypeList=bloodIntegralRuleService.queryByComm();
            page = bloodIntegralRuleService.searchPageByVo(page, bloodIntegralRule);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            this.addActionError(ConfigureUtils.getMessageConfig("integralRule.query.fail"));
            return "queryFail";
        }
        return "pageSuccess";
    }


    /**
     * 进入添加经验规则页面
     */
    public String operateAdd() {
        try {
            String codes;
            bloodIntegralRule = bloodIntegralRuleService.maxIntegralRuleId();
            if (bloodIntegralRule != null) {
                String code = bloodIntegralRule.getCode();
                int count = Integer.parseInt(code.substring(5)) + 1;
                codes = "RULE_" + count;
            } else {
                codes = "RULE_1";
            }
            bloodIntegralRule = new BloodIntegralRule();
            bloodIntegralRule.setCode(codes);
        } catch (SQLException e) {
            // TODO Auto-generated catch blocks
            e.printStackTrace();
        }

        return "preAddSuccess";
    }

    /****
     * 保存经验规则
     */
    @Token
    public String add() {
        try {
            // 执行更新经验规则
            if (bloodIntegralRule != null) {
                if (bloodIntegralRule.getIntegralRuleId() != null) {
                    update();
                    // 执行添加经验规则
                } else {
                    SysUser sysuser = (SysUser) session.get("sysUser");
                    bloodIntegralRule.setCreated(sysuser.getUserId());
                    bloodIntegralRule.setCreateDate(new Date());
                    bloodIntegralRuleService.insert(bloodIntegralRule);
                    this.addActionMessage(
                            ConfigureUtils.getMessageConfig("integralRule.add.success"));
                }
            }
            bloodIntegralRule = new BloodIntegralRule();
            return this.queryPageList();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            this.addActionError(ConfigureUtils.getMessageConfig("integralRule.operate.fail"));
            return this.operateAdd();
        }
    }

    /**
     * 异步验证规则编号是否重复
     */
    public void ajaxCheckCode() {
        if (checkCode != null || checkCode != "") {
            bloodIntegralRule.setCode(checkCode);
            try {
                List list = bloodIntegralRuleService.queryIntegralRule(bloodIntegralRule);
                int value = 0;
                if (ListUtils.isNotEmpty(list)) {
                    if (!irId.equals("")) {
                        BloodIntegralRule bloodIntegralRule = (BloodIntegralRule) list.get(0);
                        if (bloodIntegralRule.getIntegralRuleId().equals(Integer.valueOf(irId))) {
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

    /***
     * 删除经验规则
     */
    @Token
    public String detele() {
        try {
            bloodIntegralRuleService.delete(integralRuleIds);
            this.addActionMessage(ConfigureUtils.getMessageConfig("integralRule.delete.success"));
            return this.queryPageList();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            this.addActionError(ConfigureUtils.getMessageConfig("integralRule.delete.fail"));
            return this.queryPageList();
        }
    }

    /***
     * 更新经验规则
     */
    @Token
    public String update() {
        try {

            SysUser sysuser = (SysUser) session.get("sysUser");
            bloodIntegralRule.setModified(sysuser.getUserId());
            bloodIntegralRule.setModifyDate(new Date());
            bloodIntegralRuleService.update(bloodIntegralRule);
            this.addActionMessage(ConfigureUtils.getMessageConfig("integralRule.update.success"));
            bloodIntegralRule = new BloodIntegralRule();
            return this.queryPageList();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            this.addActionError(ConfigureUtils.getMessageConfig("integralRule.operate.fail"));
            return operateAdd();
        }
    }

    /**
     * 进入经验规则
     */
    public String preUpdate() {
        try {
            // customerTypeList=bloodIntegralRuleService.queryByComm();
            bloodIntegralRule = bloodIntegralRuleService.selectByPrimaryKey(integralRule_Id);
        } catch (Exception e) {
            e.printStackTrace();
            this.addActionError(ConfigureUtils.getMessageConfig("integralRule.query.fail"));
            return operateAdd();
        }
        return "preAddSuccess";
    }


    public BloodIntegralRuleService getBloodIntegralRuleService() {
        return bloodIntegralRuleService;
    }

    public void setBloodIntegralRuleService(BloodIntegralRuleService bloodIntegralRuleService) {
        this.bloodIntegralRuleService = bloodIntegralRuleService;
    }

    public BloodIntegralRule getBloodIntegralRule() {
        return bloodIntegralRule;
    }

    public void setBloodIntegralRule(BloodIntegralRule bloodIntegralRule) {
        this.bloodIntegralRule = bloodIntegralRule;
    }

    public Integer getIntegralRule_Id() {
        return integralRule_Id;
    }

    public void setIntegralRule_Id(Integer integralRuleId) {
        integralRule_Id = integralRuleId;
    }

    public List<Integer> getIntegralRuleIds() {
        return integralRuleIds;
    }

    public void setIntegralRuleIds(List<Integer> integralRuleIds) {
        this.integralRuleIds = integralRuleIds;
    }

    public List<BnesCustomerTypeQuery> getCustomerTypeList() {
        return customerTypeList;
    }

    public void setCustomerTypeList(List<BnesCustomerTypeQuery> customerTypeList) {
        this.customerTypeList = customerTypeList;
    }

    @Override
    public Page getPage() {
        return page;
    }

    @Override
    public void setPage(Page page) {
        this.page = page;
    }

    public int getCustomerTypeId() {
        return customerTypeId;
    }

    public void setCustomerTypeId(int customerTypeId) {
        this.customerTypeId = customerTypeId;
    }

    public String getCheckCode() {
        return checkCode;
    }

    public String getIrId() {
        return irId;
    }


    public void setIrId(String irId) {
        this.irId = irId;
    }

    public void setCheckCode(String checkCode) {
        this.checkCode = checkCode;
    }

    @Override
    public Object getModel() {
        bloodIntegralRule = new BloodIntegralRule();
        return bloodIntegralRule;
    }
}
