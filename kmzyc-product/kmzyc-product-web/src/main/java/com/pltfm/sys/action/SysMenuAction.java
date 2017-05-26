package com.pltfm.sys.action;

import java.util.List;

import org.apache.log4j.Logger;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import com.pltfm.sys.bean.SysMenuBean;
import com.pltfm.sys.model.SysMenu;

public class SysMenuAction extends ActionSupport implements ModelDriven {
	Logger log = Logger.getLogger(this.getClass());
	private SysMenu sysMenu = new SysMenu();    //model
	String RtnMessage;
	
	List dataList ;
	String isHaveSon;
	
	
	
	
    /**
     * 菜单列表
     *
     * @param      
     * @return     String
     * @exception  
     */
	public String gotoList(){
		try{
			SysMenuBean bean=new SysMenuBean();
			dataList=bean.getAllMenuList();
		} catch (Exception e) {
			e.printStackTrace();
			log.warn(e.getMessage());
			return ERROR;
		}
		return SUCCESS;
	}
	


	
	
    /**
     * 详细信息
     *
     * @param      
     * @return     String
     * @exception  
     */
	public String getDetail()throws Exception {
		List sonList = null;
		try {
			SysMenuBean bean=new SysMenuBean();
			sysMenu = bean.getMenuById(sysMenu.getMenuId());
			sonList = bean.searchListByUpId(sysMenu.getMenuId().toString());
		} catch (Exception e) {
			e.printStackTrace();
			log.warn(e.getMessage());
			return ERROR;
		}
		if(sonList!=null&&sonList.size()>0){
			isHaveSon = "1";
		}else{
			isHaveSon = "0";
		}
		return SUCCESS;
	}

	 /**
     * 显示新增
     *
     * @param      
     * @return     String
     * @exception  
     */
	public String gotoAdd() throws Exception {
		try {
			return SUCCESS;
	    } catch (Exception e) {
	    	e.printStackTrace();
			log.warn(e.getMessage());
			return ERROR;
		}
	}




    /**
     * 新增
     *
     * @param      
     * @return     String
     * @exception  
     */
	public void saveMenu() throws Exception {
		try {
	    	//log.warn("================ in SysMenuAction saveMenu()");
	        SysMenuBean bean = new SysMenuBean();
	        bean.addSysMenu(sysMenu);
	        setRtnMessage("addOK");
	    } catch (Exception e) {
			e.printStackTrace();
		}
	}


	
    /**
     * 修改
     *
     * @param      
     * @return     String
     * @exception  
     */
    public String doUpdate() throws Exception {
        //log.warn("================ in SysMenuAction doUpdate() ");
    	try{
	        SysMenuBean bean = new SysMenuBean();
	        bean.updateSysMenu(sysMenu);
		} catch (Exception e) {
			e.printStackTrace();
			log.warn(e.getMessage());
			return ERROR;
		}
        setRtnMessage("updateOK");
        return gotoList();
    }
    


    
    /**
     * 删除
     *
     * @param      
     * @return     String
     * @exception  
     */
    public String doDelete() throws Exception {
    	//log.warn("================ in SysMenuAction doDelete() ");
        try {
	 		SysMenuBean bean = new SysMenuBean();
	 		sysMenu.setIsEnable("0");
	 		bean.updateSysMenu(sysMenu);
        } catch (Exception e) {
	 		e.printStackTrace();
	 		return ERROR;
 		}
 		setRtnMessage("deleteOK");
        return gotoList();
     }
     
    
    
    
//	public String execute() throws Exception {
//		try {
//			return SUCCESS;
//		} catch (Exception e) {
//			return ERROR;
//		}
//	}
	


	public List getDataList() {
		return dataList;
	}

	public void setDataList(List dataList) {
		this.dataList = dataList;
	}

	public String getRtnMessage() {
		return RtnMessage;
	}

	public void setRtnMessage(String rtnMessage) {
		RtnMessage = rtnMessage;
	}
	
	public String getIsHaveSon() {
		return isHaveSon;
	}

	public void setIsHaveSon(String isHaveSon) {
		this.isHaveSon = isHaveSon;
	}
	
	
	public String doDefault() throws Exception{
        return INPUT;
    }

	public Object getModel() {
		return sysMenu;
	}

}
