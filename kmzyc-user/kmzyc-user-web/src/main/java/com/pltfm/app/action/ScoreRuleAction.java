package com.pltfm.app.action;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.kmzyc.commons.page.Page;
import com.opensymphony.xwork2.ModelDriven;
import com.pltfm.app.service.BnesCustomerTypeService;
import com.pltfm.app.service.ScoreRuleService;
import com.pltfm.app.util.ConfigureUtils;
import com.pltfm.app.util.JavaScriptExpress;
import com.pltfm.app.util.ListUtils;
import com.pltfm.app.util.Token;
import com.pltfm.app.vobject.BnesCustomerTypeQuery;
import com.pltfm.app.vobject.ScoreRule;
import com.pltfm.app.vobject.ScoreRuleExample;

/**
 * 客户积分规则action处理类
 *
 * @author zhl
 * @since 2013-07-10
 */
@SuppressWarnings("unchecked")
@Component(value = "scoreRuleAction")
@Scope(value = "prototype")
public class ScoreRuleAction extends BaseAction implements ModelDriven {
    private static final long serialVersionUID = -4795375234896808483L;
    private ScoreRule scoreRule;
    @Resource(name = "scoreRuleService")
    private ScoreRuleService scoreRuleService;
    @Resource(name = "bnesCustomerTypeService")
    private BnesCustomerTypeService bnesCustomerTypeService;
    private Page page;
    /**
     * 客户积分规则主键集合
     **/
    private List<String> ruleIds;
    /**
     * 客户积分规则主键
     **/
    private String scoreRuleId;
    /**
     * 客户类别主键
     **/
    private String customerId;
    /**
     * 客户类别集合
     **/
    private List customerTypeList;
    /**
     * 验证编号或计算表达式
     **/
    private String checkCode;

    @Override
    public Object getModel() {
        scoreRule = new ScoreRule();
        return scoreRule;
    }

    /**
     * 分页查询客户积分规则信息
     */
    public String queryPageList() {
        try {
            if (page == null) {
                page = new Page();
            }
            operateCustomerType();
            // 设置查询条件默认值 如果有二级 则保证一级二级为默认选中
            if (scoreRule != null && scoreRule.getCustomer_son_id() != null) {
                customerId = String.valueOf(scoreRule.getClientType());
                scoreRule.setClientType(scoreRule.getCustomer_son_id());
                page = scoreRuleService.queryForPageList(scoreRule, page);
                scoreRule.setClientType(Integer.valueOf(customerId));
            } else {
                page = scoreRuleService.queryForPageList(scoreRule, page);
            }
            return "querySuccess";
        } catch (Exception e) {
            e.printStackTrace();
            operateCustomerType();
            this.addActionError(ConfigureUtils.getMessageConfig("scorerule.query.fail"));
            return "querySuccess";
        }
    }

    /**
     * 进入添加客户积分规则页面
     */
    public String operateAdd() {
        try {
            operateCustomerType();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "addSuccess";
    }

    /**
     * 保存客户积分规则信息
     */
    @Token
    public String operateSave() {
        try {
            if (scoreRule != null) {
                if (scoreRule.getN_scoreRuleId() != null) {
                    operateUpdate();
                } else {
                    // 如何有二级菜单，设置保存客户类别id为二级菜单的id
                    if (scoreRule != null && scoreRule.getCustomer_son_id() != null) {
                        scoreRule.setClientType(scoreRule.getCustomer_son_id());
                    }
                    scoreRule.setD_createDate(new Date());
                    scoreRule.setStatus(0);
                    scoreRule.setClientType(1);
                    scoreRule.setExpireLimit(0);
                    scoreRuleService.addScoreRule(scoreRule);
                    this.addActionMessage(
                            ConfigureUtils.getMessageConfig("scorerule.save.success"));
                }
            }
            scoreRule = new ScoreRule();
            return queryPageList();
        } catch (Exception e) {
            e.printStackTrace();
            this.addActionError(ConfigureUtils.getMessageConfig("scorerule.operate.fail"));
            return operateAdd();
        }
    }

    /**
     * 多条删除客户积分规则信息
     */
    public String operateDeleteAll() {
        try {
            scoreRuleService.deleteScoreRule(ruleIds);
            this.addActionMessage(ConfigureUtils.getMessageConfig("scorerule.delete.success"));
            return queryPageList();
        } catch (Exception e) {
            e.printStackTrace();
            this.addActionError(ConfigureUtils.getErrorConfig("scorerule.delete.fail"));
            return queryPageList();
        }
    }

    /**
     * 多条删除客户积分规则信息
     */
    public String operateDelete() {
        try {
            scoreRuleService.deleteByPrimaryKey(Integer.valueOf(scoreRuleId));
            this.addActionMessage(ConfigureUtils.getMessageConfig("scorerule.delete.success"));
            return queryPageList();
        } catch (Exception e) {
            this.addActionError(ConfigureUtils.getErrorConfig("scorerule.delete.fail"));
            return queryPageList();
        }
    }

    /**
     * 进入修改客户积分规则页面
     */
    public String operateEdit() {
        try {
            operateCustomerType();
            scoreRule = scoreRuleService.queryByPrimaryKey(Integer.valueOf(scoreRuleId));
            BnesCustomerTypeQuery customer = bnesCustomerTypeService
                    .findBnesCustomerTypeDOByPrimaryKey(scoreRule.getClientType());
            if (customer != null && customer.getParentId() != 0) {
                scoreRule.setClientType(customer.getParentId());
                scoreRule.setCustomer_son_id(customer.getCustomerTypeId());
            }
            return "editSuccess";
        } catch (Exception e) {
            e.printStackTrace();
            this.addActionError(ConfigureUtils.getMessageConfig("scorerule.query.fail"));
            return operateAdd();
        }
    }

    /**
     * 更新客户积分规则信息
     */
    public String operateUpdate() {
        try {
            // 如何有二级菜单，设置保存客户类别id为二级菜单的id
            if (scoreRule != null) {
                if (scoreRule.getCustomer_son_id() != null) {
                    scoreRule.setClientType(scoreRule.getCustomer_son_id());
                }
                scoreRule.setD_modifyDate(new Date());
                scoreRuleService.updateScoreRule(scoreRule);
            }
            this.addActionMessage(ConfigureUtils.getMessageConfig("scorerule.update.succsss"));
            return "updateSuccess";
        } catch (Exception e) {
            e.printStackTrace();
            this.addActionMessage(ConfigureUtils.getMessageConfig("scorerule.operate.fail"));
            return operateAdd();
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
     * 异步验证计算表达式是否正确
     */
    public void ajaxCheckExpress() {
        if (StringUtils.isNotEmpty(checkCode)) {
            String value = "yes";
            if (JavaScriptExpress.calcExpress(checkCode, BigDecimal.ONE) == null) {
                value = "no";
            } else {
                value = "yes";
            }
            super.writeJson(value);
        }
    }

    /**
     * 异步验证规则编号是否重复
     */
    public void ajaxCheckCode() {
        if (checkCode != null || checkCode != "") {
            ScoreRuleExample example = new ScoreRuleExample();
            example.createCriteria().andCodeEqualTo(checkCode);
            try {
                List list = scoreRuleService.queryScoreRule(example);
                int value = 0;
                if (ListUtils.isNotEmpty(list)) {
                    if (!scoreRuleId.equals("")) {
                        ScoreRule rule = (ScoreRule) list.get(0);
                        if (rule.getN_scoreRuleId().equals(Integer.valueOf(scoreRuleId))) {
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

    public ScoreRule getScoreRule() {
        return scoreRule;
    }

    public void setScoreRule(ScoreRule scoreRule) {
        this.scoreRule = scoreRule;
    }

    public ScoreRuleService getScoreRuleService() {
        return scoreRuleService;
    }

    public void setScoreRuleService(ScoreRuleService scoreRuleService) {
        this.scoreRuleService = scoreRuleService;
    }

    @Override
    public Page getPage() {
        return page;
    }

    @Override
    public void setPage(Page page) {
        this.page = page;
    }

    public List<String> getRuleIds() {
        return ruleIds;
    }

    public void setRuleIds(List<String> ruleIds) {
        this.ruleIds = ruleIds;
    }

    public String getScoreRuleId() {
        return scoreRuleId;
    }

    public void setScoreRuleId(String scoreRuleId) {
        this.scoreRuleId = scoreRuleId;
    }

    public List getCustomerTypeList() {
        return customerTypeList;
    }

    public void setCustomerTypeList(List customerTypeList) {
        this.customerTypeList = customerTypeList;
    }

    public BnesCustomerTypeService getBnesCustomerTypeService() {
        return bnesCustomerTypeService;
    }

    public void setBnesCustomerTypeService(BnesCustomerTypeService bnesCustomerTypeService) {
        this.bnesCustomerTypeService = bnesCustomerTypeService;
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

    public void setCheckCode(String checkCode) {
        this.checkCode = checkCode;
    }

}
