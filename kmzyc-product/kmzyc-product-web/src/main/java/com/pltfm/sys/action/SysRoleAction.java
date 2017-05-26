package com.pltfm.sys.action;


import java.util.List;

import org.apache.log4j.Logger;

import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.ActionSupport;

import com.kmzyc.commons.page.Page;
import com.pltfm.sys.util.XiuSystemConstant;
import com.pltfm.sys.model.SysRole;
import com.pltfm.sys.bean.SysRoleBean;
import com.pltfm.sys.bean.SysMenuBean;


public class SysRoleAction extends ActionSupport implements ModelDriven {
	Logger log = Logger.getLogger(this.getClass());
    SysRole sysrole = new SysRole();    //model
    String rtnMessage;
    
	List dataList;
    Page page = new Page();    
    int paramPageSize = XiuSystemConstant.PARAM_PAGE_SIZE;    
    String[] delId;   
    
    
    
    
    /**
     * 获取列表
     *
     * @param      
     * @return     String
     * @exception  
     */
    public String listSysRole() throws Exception {
    	//log.warn("================ in SysRoleAction listSysRole()");
    	try{
	    	SysRoleBean bean = new SysRoleBean();
	    	dataList = bean.getSysRoleList(sysrole);
        } catch (Exception e) {
	 		e.printStackTrace();
	 		return ERROR;
 		}
    	return SUCCESS;
    }
    
    
    
    
    /**
     * 新增
     *
     * @param      
     * @return     String
     * @exception  
     */
    public String save() throws Exception {
        //log.warn("================ in SysRoleAction save() ");
        try{
	        SysRoleBean bean = new SysRoleBean();
	        bean.addSysRole(sysrole);
        } catch (Exception e) {
	 		e.printStackTrace();
	 		return ERROR;
 		}
        this.setRtnMessage("saveOk");
        sysrole = new SysRole();
        return listSysRole();
    }
    
    
    
    
    /**
     * 详细信息
     *
     * @param      
     * @return     String
     * @exception  
     */
    public String getDetail() throws Exception {
    	//log.warn("================ in SysRoleAction getDetail()");
		Integer id = sysrole.getRoleId();
		try{
			SysRoleBean bean = new SysRoleBean();
	        this.sysrole = bean.getSysRoleDetail(id);
        } catch (Exception e) {
	 		e.printStackTrace();
	 		return ERROR;
 		}
    	return SUCCESS;
    }
    
    
    
    /**
     * 修改
     *
     * @param      
     * @return     String
     * @exception  
     */
    public String doUpdate() throws Exception {
        //log.warn("================ in SysRoleAction doUpdate() ");
    	try{
	        SysRoleBean bean = new SysRoleBean();
	        sysrole = bean.updateSysRole(sysrole);
        } catch (Exception e) {
	 		e.printStackTrace();
	 		return ERROR;
 		}
        setRtnMessage("updateOK");
        sysrole = new SysRole();
        return this.listSysRole();
    }
    
    
    
    
    /**
     * 通过系统角色获取菜单
     *
     * @param      
     * @return     String
     * @exception  
     */
    public String getMenuListByRole() throws Exception {
    	//log.warn("================ in SysRoleAction getMenuListByRole()==sysrole.getRoleName()="+sysrole.getRoleName());
    	try{
	    	SysMenuBean bean = new SysMenuBean();
	    	dataList = bean.getMenuListByRoleId(sysrole.getRoleId());
	    	SysRoleBean rBean=new SysRoleBean();
	    	sysrole=rBean.getSysRoleDetail(sysrole.getRoleId());
        } catch (Exception e) {
	 		e.printStackTrace();
	 		return ERROR;
 		}
    	//log.warn("------- menuList.size() =  "+(dataList!=null?String.valueOf(dataList.size()):"null"));
    	return SUCCESS;
    }
    
    
    
    /**
     * 保存角色菜单关系
     *
     * @param      
     * @return     String
     * @exception  
     */
    public String saveRoleMenu() throws Exception {
    	//log.warn("================ in SysRoleAction saveRoleMenu()");
    	try{
	    	SysMenuBean bean = new SysMenuBean();
	    	bean.deleteAndsaveRoleMenu(sysrole.getRoleId(),delId);
        } catch (Exception e) {
	 		e.printStackTrace();
	 		return ERROR;
 		}
    	setRtnMessage("saveOk");
    	return getMenuListByRole();
    }
    
    

    /**
     * 删除
     *
     * @param      
     * @return     String
     * @exception  
     */
	 public String doDelete() throws Exception {
		//log.warn("================ in SysRoleAction doDelete()");
        try {
        	SysRoleBean sysRoleBean = new SysRoleBean();
        	sysRoleBean.deleteSysRole(delId);
		} catch (Exception e) {
			e.printStackTrace();
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

    
    public String doDefault() throws Exception{
        return INPUT;
    }

	public Object getModel() {
		return sysrole;
	}
}