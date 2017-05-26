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
import com.pltfm.app.service.NwesMaintenaceService;
import com.pltfm.app.util.ConfigureUtils;
import com.pltfm.app.util.Token;
import com.pltfm.app.vobject.NwesMaintenace;
import com.pltfm.sys.model.SysUser;

/***
 * 维护记录Action
 */
@Component(value = "nwesMaintenaceAction")
@Scope(value = "prototype")
public class NwesMaintenaceAction extends BaseAction implements ModelDriven {
    private static final long serialVersionUID = -579941341825593085L;
    /***
     * 维护记录Service
     */
    @Resource(name = "nwesMaintenaceService")
    private NwesMaintenaceService nwesMaintenaceService;
    @Resource(name = "bnesCustomerTypeService")
    private BnesCustomerTypeService bnesCustomerTypeService;


    /***
     * 维护记录实体
     */
    private NwesMaintenace nwesMaintenace;
    /***
     * 维护记录集合
     */
    private List<NwesMaintenace> nwesMaintenaceList;
    /***
     * 维护id集合
     */
    private List<Integer> maintenaceIds;
    /***
     * 维护id
     */
    private Integer maintenace_Id;

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
     * 维护记录信息列表
     */
    public String pageList() {
        try {
            if (page == null) {
                page = new Page();
            }
            // operateCustomerType();
            // 设置查询条件默认值 如果有二级 则保证一级二级为默认选中
            if (nwesMaintenace != null && nwesMaintenace.getCustomer_son_id() != null) {
                customerId = String.valueOf(nwesMaintenace.getCustomerTypeId());
                nwesMaintenace.setCustomerTypeId(nwesMaintenace.getCustomer_son_id());
                page = nwesMaintenaceService.searchPageByVo(page, nwesMaintenace);
                nwesMaintenace.setCustomerTypeId(Integer.valueOf(customerId));
            } else {
                page = nwesMaintenaceService.searchPageByVo(page, nwesMaintenace);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            this.addActionError(ConfigureUtils.getMessageConfig("maintenace.query.fail"));
            return "queryFail";
        }
        return "pageSuccess";
    }


    /**
     * 进入添加维护记录信息页面
     */
    public String preAdd() {

        nwesMaintenace = new NwesMaintenace();
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
     * 保存维护记录
     */
    @Token
    public String add() {
        try {
            // 执行更新维护记录
            if (nwesMaintenace != null) {
                if (nwesMaintenace.getMaintenaceId() != null) {
                    update();
                    // 执行添加维护记录
                } else {
                    if (nwesMaintenace.getCustomer_son_id() != null) {
                        nwesMaintenace.setCustomerTypeId(nwesMaintenace.getCustomer_son_id());
                    }
                    SysUser sysuser = (SysUser) session.get("sysUser");
                    nwesMaintenace.setLoginId(sysuser.getUserId());// 维护人，当前登录id
                    nwesMaintenace.setCreated(sysuser.getUserId());
                    nwesMaintenace.setCreateDate(new Date());
                    nwesMaintenaceService.insert(nwesMaintenace);
                    this.addActionMessage(
                            ConfigureUtils.getMessageConfig("maintenace.add.success"));
                }
            }
            nwesMaintenace = new NwesMaintenace();
            return this.pageList();
        } catch (SQLException e) {
            e.printStackTrace();
            this.addActionError(ConfigureUtils.getMessageConfig("maintenace.operate.fail"));
            return this.preAdd();
        }
    }

    /***
     * 删除维护记录
     */
    @Token
    public String detele() {
        try {
            nwesMaintenaceService.delete(maintenaceIds);
            this.addActionMessage(ConfigureUtils.getMessageConfig("maintenace.delete.success"));
            return this.pageList();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            this.addActionError(ConfigureUtils.getMessageConfig("maintenace.delete.fail"));
            return this.pageList();
        }
    }

    /***
     * 更新维护记录
     */
    public String update() {
        try {
            if (nwesMaintenace.getCustomer_son_id() != null) {
                nwesMaintenace.setCustomerTypeId(nwesMaintenace.getCustomer_son_id());
            }
            SysUser sysuser = (SysUser) session.get("sysUser");
            nwesMaintenace.setModified(sysuser.getUserId());
            nwesMaintenace.setModifyDate(new Date());
            nwesMaintenaceService.update(nwesMaintenace);
            this.addActionMessage(ConfigureUtils.getMessageConfig("maintenace.update.success"));
            nwesMaintenace = new NwesMaintenace();
            return this.pageList();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            this.addActionError(ConfigureUtils.getMessageConfig("maintenace.operate.fail"));
            return preUpdate();
        }
    }

    /**
     * 进入维护记录信息详细
     */
    public String preUpdate() {
        try {
            nwesMaintenace = nwesMaintenaceService.selectByPrimaryKey(maintenace_Id);
      /*
       * BnesCustomerTypeQuery customer =
       * bnesCustomerTypeService.findBnesCustomerTypeDOByPrimaryKey(nwesMaintenace.getCustomerTypeId
       * ()); if(customer!=null&&customer.getParentId()!=0){
       * nwesMaintenace.setCustomerTypeId(customer.getParentId());
       * nwesMaintenace.setCustomer_son_id(customer.getCustomerTypeId()); }
       */
        } catch (Exception e) {
            // TODO Auto-generated catch block
            this.addActionError(ConfigureUtils.getMessageConfig("maintenace.query.fail"));
            return preAdd();
        }
        return "preAddSuccess";
    }

    /**
     * 查看维护记录信息详细
     */
    public String preDetail() {
        try {
            nwesMaintenace = nwesMaintenaceService.selectByPrimaryKey(maintenace_Id);
        } catch (Exception e) {
            this.addActionError(ConfigureUtils.getMessageConfig("maintenace.query.fail"));
            return pageList();
        }
        return "preDetailSuccess";
    }


    public BnesCustomerTypeService getBnesCustomerTypeService() {
        return bnesCustomerTypeService;
    }

    public void setBnesCustomerTypeService(BnesCustomerTypeService bnesCustomerTypeService) {
        this.bnesCustomerTypeService = bnesCustomerTypeService;
    }

    public NwesMaintenaceService getNwesMaintenaceService() {
        return nwesMaintenaceService;
    }

    public void setNwesMaintenaceService(NwesMaintenaceService nwesMaintenaceService) {
        this.nwesMaintenaceService = nwesMaintenaceService;
    }

    public NwesMaintenace getNwesMaintenace() {
        return nwesMaintenace;
    }

    public void setNwesMaintenace(NwesMaintenace nwesMaintenace) {
        this.nwesMaintenace = nwesMaintenace;
    }

    public List<NwesMaintenace> getNwesMaintenaceList() {
        return nwesMaintenaceList;
    }

    public void setNwesMaintenaceList(List<NwesMaintenace> nwesMaintenaceList) {
        this.nwesMaintenaceList = nwesMaintenaceList;
    }

    public List<Integer> getMaintenaceIds() {
        return maintenaceIds;
    }

    public void setMaintenaceIds(List<Integer> maintenaceIds) {
        this.maintenaceIds = maintenaceIds;
    }

    public Integer getMaintenace_Id() {
        return maintenace_Id;
    }

    public void setMaintenace_Id(Integer maintenaceId) {
        maintenace_Id = maintenaceId;
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

    @Override
    public Object getModel() {
        nwesMaintenace = new NwesMaintenace();
        return nwesMaintenace;
    }

}
