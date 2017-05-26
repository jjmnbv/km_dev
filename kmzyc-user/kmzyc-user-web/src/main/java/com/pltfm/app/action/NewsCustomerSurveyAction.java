package com.pltfm.app.action;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.kmzyc.commons.page.Page;
import com.opensymphony.xwork2.ModelDriven;
import com.pltfm.app.service.BnesCustomerTypeService;
import com.pltfm.app.service.NewsCustomerSurveyService;
import com.pltfm.app.util.ConfigureUtils;
import com.pltfm.app.util.Token;
import com.pltfm.app.vobject.BnesCustomerTypeQuery;
import com.pltfm.app.vobject.NewsCustomerSurvey;
import com.pltfm.sys.model.SysUser;

/***
 * 调查记录Action
 */
@Component(value = "newsCustomerSurveyAction")
@Scope(value = "prototype")
public class NewsCustomerSurveyAction extends BaseAction implements ModelDriven {
    /***
     * 调查记录Service
     */
    @Resource(name = "newsCustomerSurveyService")
    private NewsCustomerSurveyService newsCustomerSurveyService;
    @Resource(name = "bnesCustomerTypeService")
    private BnesCustomerTypeService bnesCustomerTypeService;
    /***
     * 调查记录实体
     */
    private NewsCustomerSurvey newsCustomerSurvey;
    /***
     * 调查记录信息集合
     */
    private List<NewsCustomerSurvey> newsCustomerSurveyList;
    /***
     * 调查记录主键id集合
     */
    private List<Integer> customerSurveyIds;
    /***
     * 调查记录主键id
     */
    private Integer surveyId;


    /**
     * 分页类
     */
    private Page page;
    /**
     * 客户类别集合
     **/
    private List customerTypeList;

    /**
     * 客户类别主键
     **/
    private String customerId;


    /**
     * 调查记录信息列表
     */
    public String pageList() {
        try {
            if (page == null) {
                page = new Page();
            }
            operateCustomerType();
            // 设置查询条件默认值 如果有二级 则保证一级二级为默认选中
            if (newsCustomerSurvey != null && newsCustomerSurvey.getCustomer_son_id() != null) {
                customerId = String.valueOf(newsCustomerSurvey.getCustomerTypeId());
                newsCustomerSurvey.setCustomerTypeId(newsCustomerSurvey.getCustomer_son_id());
                page = newsCustomerSurveyService.searchPageByVo(page, newsCustomerSurvey);
                newsCustomerSurvey.setCustomerTypeId(Integer.valueOf(customerId));
            } else {
                page = newsCustomerSurveyService.searchPageByVo(page, newsCustomerSurvey);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            this.addActionError(ConfigureUtils.getMessageConfig("customerSurvey.query.fail"));
            return "queryFail";
        }
        return "pageSuccess";
    }


    /**
     * 进入添加调查记录信息页面
     */
    public String preAdd() {

        newsCustomerSurvey = new NewsCustomerSurvey();
        operateCustomerType();
        return "preAddSuccess";
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

    /****
     * 保存调查记录
     */
    @Token
    public String add() {
        try {
            // 执行更新调查记录
            if (newsCustomerSurvey != null) {
                if (newsCustomerSurvey.getCustomerSurveyId() != null) {
                    update();
                    // 执行添加调查记录
                } else {
                    if (newsCustomerSurvey.getCustomer_son_id() != null) {
                        newsCustomerSurvey
                                .setCustomerTypeId(newsCustomerSurvey.getCustomer_son_id());
                    }
                    SysUser sysuser = (SysUser) session.get("sysUser");
                    newsCustomerSurvey.setLoginId(sysuser.getUserId());// 调查人，当前登录id
                    newsCustomerSurvey.setCreated(sysuser.getUserId());
                    newsCustomerSurvey.setCreateDate(new Date());
                    newsCustomerSurveyService.insert(newsCustomerSurvey);
                    this.addActionMessage(
                            ConfigureUtils.getMessageConfig("customerSurvey.add.success"));
                }
            }
            newsCustomerSurvey = new NewsCustomerSurvey();
            return this.pageList();
        } catch (SQLException e) {
            e.printStackTrace();
            this.addActionError(ConfigureUtils.getMessageConfig("customerSurvey.operate.fail"));
            return this.preAdd();
        }
    }

    /***
     * 删除调查记录
     */
    @Token
    public String detele() {
        try {
            newsCustomerSurveyService.delete(customerSurveyIds);
            this.addActionMessage(ConfigureUtils.getMessageConfig("customerSurvey.delete.success"));
            return this.pageList();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            this.addActionError(ConfigureUtils.getMessageConfig("customerSurvey.delete.fail"));
            return this.pageList();
        }
    }

    /***
     * 更新调查记录
     */
    public String update() {
        try {
            if (newsCustomerSurvey.getCustomer_son_id() != null) {
                newsCustomerSurvey.setCustomerTypeId(newsCustomerSurvey.getCustomer_son_id());
            }
            SysUser sysuser = (SysUser) session.get("sysUser");
            newsCustomerSurvey.setModified(sysuser.getUserId());
            newsCustomerSurvey.setModifyDate(new Date());
            newsCustomerSurveyService.updateByPrimaryKeySelective(newsCustomerSurvey);
            this.addActionMessage(ConfigureUtils.getMessageConfig("customerSurvey.update.success"));
            newsCustomerSurvey = new NewsCustomerSurvey();
            return this.pageList();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            this.addActionError(ConfigureUtils.getMessageConfig("customerSurvey.operate.fail"));
            return preUpdate();
        }
    }

    /**
     * 进入调查记录信息详细
     */
    public String preUpdate() {
        try {
            newsCustomerSurvey = newsCustomerSurveyService.selectByPrimaryKey(surveyId);
            BnesCustomerTypeQuery customer = bnesCustomerTypeService
                    .findBnesCustomerTypeDOByPrimaryKey(newsCustomerSurvey.getCustomerTypeId());
            if (customer != null && customer.getParentId() != 0) {
                newsCustomerSurvey.setCustomerTypeId(customer.getParentId());
                newsCustomerSurvey.setCustomer_son_id(customer.getCustomerTypeId());
            }
        } catch (Exception e) {
            this.addActionError(ConfigureUtils.getMessageConfig("customerSurvey.query.fail"));
            return preAdd();
        }
        return "preAddSuccess";
    }

    /**
     * 查看调查记录信息详细
     */
    public String preDetail() {
        try {
            newsCustomerSurvey = newsCustomerSurveyService.selectByPrimaryKey(surveyId);
        } catch (Exception e) {
            this.addActionError(ConfigureUtils.getMessageConfig("customerSurvey.query.fail"));
            return pageList();
        }
        return "preDetailSuccess";
    }


    @Override
    public Object getModel() {
        newsCustomerSurvey = new NewsCustomerSurvey();
        return newsCustomerSurvey;
    }

    public NewsCustomerSurveyService getNewsCustomerSurveyService() {
        return newsCustomerSurveyService;
    }

    public void setNewsCustomerSurveyService(NewsCustomerSurveyService newsCustomerSurveyService) {
        this.newsCustomerSurveyService = newsCustomerSurveyService;
    }

    public NewsCustomerSurvey getNewsCustomerSurvey() {
        return newsCustomerSurvey;
    }

    public void setNewsCustomerSurvey(NewsCustomerSurvey newsCustomerSurvey) {
        this.newsCustomerSurvey = newsCustomerSurvey;
    }

    public List<NewsCustomerSurvey> getNewsCustomerSurveyList() {
        return newsCustomerSurveyList;
    }

    public void setNewsCustomerSurveyList(List<NewsCustomerSurvey> newsCustomerSurveyList) {
        this.newsCustomerSurveyList = newsCustomerSurveyList;
    }

    public List<Integer> getCustomerSurveyIds() {
        return customerSurveyIds;
    }

    public void setCustomerSurveyIds(List<Integer> customerSurveyIds) {
        this.customerSurveyIds = customerSurveyIds;
    }

    @Override
    public Page getPage() {
        return page;
    }


    @Override
    public void setPage(Page page) {
        this.page = page;
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

    public Integer getSurveyId() {
        return surveyId;
    }


    public void setSurveyId(Integer surveyId) {
        this.surveyId = surveyId;
    }


    public String getCustomerId() {
        return customerId;
    }


    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
}
