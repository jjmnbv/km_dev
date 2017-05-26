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
import com.pltfm.app.service.NwesVistingService;
import com.pltfm.app.util.ConfigureUtils;
import com.pltfm.app.util.Token;
import com.pltfm.app.vobject.NwesVisting;
import com.pltfm.sys.model.SysUser;

/***
 * 拜访记录Action
 */
@Component(value = "nwesVistingAction")
@Scope(value = "prototype")
public class NwesVistingAction extends BaseAction implements ModelDriven {
    private static final long serialVersionUID = -579941341825593085L;
    /***
     * 拜访记录Service
     */
    @Resource(name = "nwesVistingService")
    private NwesVistingService nwesVistingService;
    @Resource(name = "bnesCustomerTypeService")
    private BnesCustomerTypeService bnesCustomerTypeService;
    /***
     * 拜访记录
     */
    private NwesVisting nwesVisting;

    /***
     * 拜访记录集合
     */
    private List<NwesVisting> nwesVistingList;
    /***
     * 拜访id集合
     */
    private List<Integer> vistingIds;
    /***
     * 拜访id
     */
    private Integer visting_Id;
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
     * 拜访记录信息列表
     */
    public String pageList() {
        try {
            if (page == null) {
                page = new Page();
            }
            operateCustomerType();
            // 设置查询条件默认值 如果有二级 则保证一级二级为默认选中
            if (nwesVisting != null && nwesVisting.getCustomer_son_id() != null) {
                customerId = String.valueOf(nwesVisting.getCustomerTypeId());
                nwesVisting.setCustomerTypeId(nwesVisting.getCustomer_son_id());
                page = nwesVistingService.searchPageByVo(page, nwesVisting);
                nwesVisting.setCustomerTypeId(Integer.valueOf(customerId));
            } else {
                page = nwesVistingService.searchPageByVo(page, nwesVisting);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            this.addActionError(ConfigureUtils.getMessageConfig("visting.query.fail"));
            return "queryFail";
        }
        return "pageSuccess";
    }


    /**
     * 进入添加拜访记录信息页面
     */
    public String preAdd() {

        nwesVisting = new NwesVisting();
        // operateCustomerType();
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
     * 保存拜访记录
     */
    @Token
    public String add() {
        try {
            // 执行更新拜访记录
            if (nwesVisting != null) {
                if (nwesVisting.getVistingId() != null) {
                    update();
                    // 执行添加拜访记录
                } else {
                    if (nwesVisting.getCustomer_son_id() != null) {
                        nwesVisting.setCustomerTypeId(nwesVisting.getCustomer_son_id());
                    }
                    SysUser sysuser = (SysUser) session.get("sysUser");
                    nwesVisting.setLoginId(sysuser.getUserId());// 拜访人，当前登录id
                    nwesVisting.setCreated(sysuser.getUserId());
                    nwesVisting.setCreateDate(new Date());
                    nwesVistingService.insert(nwesVisting);
                    this.addActionMessage(ConfigureUtils.getMessageConfig("visting.add.success"));
                }
            }
            nwesVisting = new NwesVisting();
            return this.pageList();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            this.addActionError(ConfigureUtils.getMessageConfig("visting.operate.fail"));
            return this.preAdd();
        }
    }

    /***
     * 删除拜访记录
     */
    @Token
    public String detele() {
        try {
            nwesVistingService.delete(vistingIds);
            this.addActionMessage(ConfigureUtils.getMessageConfig("visting.delete.success"));
            return this.pageList();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            this.addActionError(ConfigureUtils.getMessageConfig("visting.delete.fail"));
            return this.pageList();
        }
    }

    /***
     * 更新拜访记录
     */
    public String update() {
        try {
            if (nwesVisting.getCustomer_son_id() != null) {
                nwesVisting.setCustomerTypeId(nwesVisting.getCustomer_son_id());
            }
            SysUser sysuser = (SysUser) session.get("sysUser");
            nwesVisting.setModified(sysuser.getUserId());
            nwesVisting.setModifyDate(new Date());
            nwesVistingService.update(nwesVisting);
            this.addActionMessage(ConfigureUtils.getMessageConfig("visting.update.success"));
            nwesVisting = new NwesVisting();
            return this.pageList();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            this.addActionError(ConfigureUtils.getMessageConfig("visting.operate.fail"));
            return preUpdate();
        }
    }

    /**
     * 进入拜访记录信息详细
     */
    public String preUpdate() {
        try {
            nwesVisting = nwesVistingService.selectByPrimaryKey(visting_Id);
      /*
       * BnesCustomerTypeQuery customer =
       * bnesCustomerTypeService.findBnesCustomerTypeDOByPrimaryKey(nwesVisting.getCustomerTypeId())
       * ; if(customer!=null&&customer.getParentId()!=0){
       * nwesVisting.setCustomerTypeId(customer.getParentId());
       * nwesVisting.setCustomer_son_id(customer.getCustomerTypeId()); }
       */
        } catch (Exception e) {
            this.addActionError(ConfigureUtils.getMessageConfig("visting.query.fail"));
            return preAdd();
        }
        return "preAddSuccess";
    }


    /**
     * 查看拜访记录信息详细
     */
    public String preDetail() {
        try {
            nwesVisting = nwesVistingService.selectByPrimaryKey(visting_Id);
        } catch (Exception e) {
            this.addActionError(ConfigureUtils.getMessageConfig("visting.query.fail"));
            return pageList();
        }
        return "preDetailSuccess";
    }

    @Override
    public Object getModel() {
        nwesVisting = new NwesVisting();
        return nwesVisting;
    }

    public NwesVistingService getNwesVistingService() {
        return nwesVistingService;
    }

    public void setNwesVistingService(NwesVistingService nwesVistingService) {
        this.nwesVistingService = nwesVistingService;
    }

    public NwesVisting getNwesVisting() {
        return nwesVisting;
    }

    public void setNwesVisting(NwesVisting nwesVisting) {
        this.nwesVisting = nwesVisting;
    }

    public List<NwesVisting> getNwesVistingList() {
        return nwesVistingList;
    }

    public void setNwesVistingList(List<NwesVisting> nwesVistingList) {
        this.nwesVistingList = nwesVistingList;
    }

    public List<Integer> getVistingIds() {
        return vistingIds;
    }

    public void setVistingIds(List<Integer> vistingIds) {
        this.vistingIds = vistingIds;
    }

    public Integer getVisting_Id() {
        return visting_Id;
    }

    public void setVisting_Id(Integer vistingId) {
        visting_Id = vistingId;
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

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public BnesCustomerTypeService getBnesCustomerTypeService() {
        return bnesCustomerTypeService;
    }

    public void setBnesCustomerTypeService(BnesCustomerTypeService bnesCustomerTypeService) {
        this.bnesCustomerTypeService = bnesCustomerTypeService;
    }

}
