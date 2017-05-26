package com.pltfm.app.action;

import java.sql.SQLException;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.kmzyc.commons.page.Page;
import com.opensymphony.xwork2.ModelDriven;
import com.pltfm.app.service.BnesAcctAppealInfoService;
import com.pltfm.app.service.BnesAcctHandleComplaintsService;
import com.pltfm.app.util.ConfigureUtils;
import com.pltfm.app.util.Token;
import com.pltfm.app.vobject.BnesAcctAppealInfo;
import com.pltfm.app.vobject.BnesAcctAppealInfoQry;
import com.pltfm.app.vobject.BnesAcctHandleComplaints;
import com.pltfm.sys.model.SysUser;

@Component(value = "acctAppealInfoAction")
@Scope(value = "prototype")
public class AcctAppealInfoAction extends BaseAction implements ModelDriven {
    @Resource(name = "bnesAcctAppealInfoService")
    BnesAcctAppealInfoService bnesAcctAppealInfoService;

    @Resource(name = "bnesAcctHandleComplaintsService")
    BnesAcctHandleComplaintsService bnesAcctHandleComplaintsService;
    // 申诉对象
    BnesAcctAppealInfo bnesAcctAppealInfo;
    BnesAcctAppealInfoQry bnesAcctAppealInfoQry;
    // 申诉处理对象
    BnesAcctHandleComplaints bnesAcctHandleComplaints;



    private Page page;

    // 进入添加申诉
    public String preAdd() {
        return "add";
    }

    // 添加申诉
    @Token
    public String add() {
        try {
            // 暂时写成后台用户
            SysUser sysuser = (SysUser) session.get("sysUser");
            // bnesAcctAppealInfo.setAccountId(sysuser.getUserId());
            bnesAcctAppealInfo.setAppealCreateDate(new Date());
            bnesAcctAppealInfo.setCreatedId(sysuser.getUserId());
            bnesAcctAppealInfo.setCreateDate(new Date());
            bnesAcctAppealInfoService.insert(bnesAcctAppealInfo);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "refreshList";
    }

    // 申诉列表
    public String list() {
        try {
            if (page == null) {
                page = new Page();
            }
            page = bnesAcctAppealInfoService.queryForPage(bnesAcctAppealInfoQry, page);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "list";
    }

    // 进入申诉处理页面
    public String preEdit() {
        try {


            bnesAcctAppealInfo =
                    bnesAcctAppealInfoService.selectByPrimaryKey(bnesAcctAppealInfo
                            .getAccountAppealId());

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "edit";
    }

    // 进入申诉查看页面
    public String preView() {
        try {


            bnesAcctAppealInfo =
                    bnesAcctAppealInfoService.selectByPrimaryKey(bnesAcctAppealInfo
                            .getAccountAppealId());

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "view";
    }

    // 处理申诉
    @Token
    public String edit() {
        try {
            SysUser sysuser = (SysUser) session.get("sysUser");
            bnesAcctHandleComplaints.setAccountAppealId(bnesAcctAppealInfo.getAccountAppealId());
            // bnesAcctHandleComplaints.setAccountId(bnesAcctAppealInfo.getAccountId());
            bnesAcctHandleComplaints.setCreateDate(new Date());
            bnesAcctHandleComplaints.setCreatedId(sysuser.getUserId());
            bnesAcctHandleComplaints.setDisposeDate(new Date());
            bnesAcctHandleComplaints.setDisposePersonId(sysuser.getUserId());
            bnesAcctHandleComplaints.setModifieId(sysuser.getUserId());
            bnesAcctHandleComplaints.setModifyDate(new Date());
            bnesAcctHandleComplaintsService.insert(bnesAcctHandleComplaints);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            this.addActionMessage(ConfigureUtils.getMessageConfig("appealInfo.update.fail"));
        }
        this.addActionMessage(ConfigureUtils.getMessageConfig("appealInfo.update.succ"));
        return list();
    }

    @Override
    public Object getModel() {
        bnesAcctAppealInfo = new BnesAcctAppealInfo();
        bnesAcctAppealInfoQry = new BnesAcctAppealInfoQry();
        return bnesAcctAppealInfo;
    }

    public BnesAcctAppealInfoService getBnesAcctAppealInfoService() {
        return bnesAcctAppealInfoService;
    }

    public void setBnesAcctAppealInfoService(BnesAcctAppealInfoService bnesAcctAppealInfoService) {
        this.bnesAcctAppealInfoService = bnesAcctAppealInfoService;
    }

    public BnesAcctAppealInfo getBnesAcctAppealInfo() {
        return bnesAcctAppealInfo;
    }

    public void setBnesAcctAppealInfo(BnesAcctAppealInfo bnesAcctAppealInfo) {
        this.bnesAcctAppealInfo = bnesAcctAppealInfo;
    }

    @Override
    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public BnesAcctHandleComplaints getBnesAcctHandleComplaints() {
        return bnesAcctHandleComplaints;
    }

    public void setBnesAcctHandleComplaints(BnesAcctHandleComplaints bnesAcctHandleComplaints) {
        this.bnesAcctHandleComplaints = bnesAcctHandleComplaints;
    }

    public BnesAcctHandleComplaintsService getBnesAcctHandleComplaintsService() {
        return bnesAcctHandleComplaintsService;
    }

    public void setBnesAcctHandleComplaintsService(
            BnesAcctHandleComplaintsService bnesAcctHandleComplaintsService) {
        this.bnesAcctHandleComplaintsService = bnesAcctHandleComplaintsService;
    }

    public BnesAcctAppealInfoQry getBnesAcctAppealInfoQry() {
        return bnesAcctAppealInfoQry;
    }

    public void setBnesAcctAppealInfoQry(BnesAcctAppealInfoQry bnesAcctAppealInfoQry) {
        this.bnesAcctAppealInfoQry = bnesAcctAppealInfoQry;
    }



}
