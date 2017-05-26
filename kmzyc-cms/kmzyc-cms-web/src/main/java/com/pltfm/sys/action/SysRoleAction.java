package com.pltfm.sys.action;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.pltfm.sys.bean.SysMenuBean;
import com.pltfm.sys.bean.SysRoleBean;
import com.pltfm.sys.model.SysRole;
import com.kmzyc.commons.page.Page;
import com.pltfm.sys.util.XiuSystemConstant;

import java.util.List;


public class SysRoleAction extends ActionSupport implements ModelDriven {
	private static Logger logger = LoggerFactory.getLogger(SysRoleAction.class);
    SysRole sysrole = new SysRole();    //model
    String rtnMessage;

    List dataList;
    Page page = new Page();
    int paramPageSize = XiuSystemConstant.PARAM_PAGE_SIZE;
    String[] delId;


    /**
     * 获取列表
     *
     * @return String
     */
    public String listSysRole() throws Exception {
        //log.warn("================ in SysRoleAction listSysRole()");
        try {
            SysRoleBean bean = SysRoleBean.instance();
            dataList = bean.getSysRoleList(sysrole);
        } catch (Exception e) {
        	logger.error("SysRoleAction.listSysRole异常：" + e.getMessage(), e);
            return ERROR;
        }
        return SUCCESS;
    }


    /**
     * 新增
     *
     * @return String
     */
    public String save() throws Exception {
        //log.warn("================ in SysRoleAction save() ");
        try {
            SysRoleBean bean = SysRoleBean.instance();
            bean.addSysRole(sysrole);
        } catch (Exception e) {
        	logger.error("SysRoleAction.save异常：" + e.getMessage(), e);
            return ERROR;
        }
        this.setRtnMessage("saveOk");
        sysrole = new SysRole();
        return listSysRole();
    }


    /**
     * 详细信息
     *
     * @return String
     */
    public String getDetail() throws Exception {
        //log.warn("================ in SysRoleAction getDetail()");
        Integer id = sysrole.getRoleId();
        try {
            SysRoleBean bean = SysRoleBean.instance();
            this.sysrole = bean.getSysRoleDetail(id);
        } catch (Exception e) {
        	logger.error("SysRoleAction.getDetail异常：" + e.getMessage(), e);
            return ERROR;
        }
        return SUCCESS;
    }


    /**
     * 修改
     *
     * @return String
     */
    public String doUpdate() throws Exception {
        //log.warn("================ in SysRoleAction doUpdate() ");
        try {
            SysRoleBean bean = SysRoleBean.instance();
            sysrole = bean.updateSysRole(sysrole);
        } catch (Exception e) {
        	logger.error("SysRoleAction.doUpdate异常：" + e.getMessage(), e);
            return ERROR;
        }
        setRtnMessage("updateOK");
        sysrole = new SysRole();
        return this.listSysRole();
    }


    /**
     * 通过系统角色获取菜单
     *
     * @return String
     */
    public String getMenuListByRole() throws Exception {
        //log.warn("================ in SysRoleAction getMenuListByRole()==sysrole.getRoleName()="+sysrole.getRoleName());
        try {
            SysMenuBean bean = SysMenuBean.instance();
            dataList = bean.getMenuListByRoleId(sysrole.getRoleId());
            SysRoleBean rBean = SysRoleBean.instance();
            sysrole = rBean.getSysRoleDetail(sysrole.getRoleId());
        } catch (Exception e) {
        	logger.error("SysRoleAction.getMenuListByRole异常：" + e.getMessage(), e);
            return ERROR;
        }
        //log.warn("------- menuList.size() =  "+(dataList!=null?String.valueOf(dataList.size()):"null"));
        return SUCCESS;
    }


    /**
     * 保存角色菜单关系
     *
     * @return String
     */
    public String saveRoleMenu() throws Exception {
        //log.warn("================ in SysRoleAction saveRoleMenu()");
        try {
            SysMenuBean bean = SysMenuBean.instance();
            bean.deleteAndsaveRoleMenu(sysrole.getRoleId(), delId);
        } catch (Exception e) {
        	logger.error("SysRoleAction.saveRoleMenu异常：" + e.getMessage(), e);
            return ERROR;
        }
        setRtnMessage("saveOk");
        return getMenuListByRole();
    }


    /**
     * 删除
     *
     * @return String
     */
    public String doDelete() throws Exception {
        //log.warn("================ in SysRoleAction doDelete()");
        try {
            SysRoleBean sysRoleBean = SysRoleBean.instance();
            sysRoleBean.deleteSysRole(delId);
        } catch (Exception e) {
        	logger.error("SysRoleAction.doDelete异常：" + e.getMessage(), e);
            return ERROR;
        }
        this.setRtnMessage("deleteOK");
        return listSysRole();
    }


    public String getRtnMessage() {
        return rtnMessage;
    }

    public void setRtnMessage(String rtnMessage) {
        this.rtnMessage = rtnMessage;
    }

    public List getDataList() {
        return dataList;
    }

    public void setDataList(List dataList) {
        this.dataList = dataList;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public int getParamPageSize() {
        return paramPageSize;
    }

    public void setParamPageSize(int paramPageSize) {
        this.paramPageSize = paramPageSize;
    }

    public String[] getDelId() {
        return delId;
    }

    public void setDelId(String[] delId) {
        this.delId = delId;
    }


    public String doDefault() throws Exception {
        return INPUT;
    }

    public Object getModel() {
        return sysrole;
    }
}
