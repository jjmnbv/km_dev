package com.pltfm.sys.action;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.pltfm.sys.bean.SysMenuBean;
import com.pltfm.sys.model.SysMenu;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class SysMenuAction extends ActionSupport implements ModelDriven {
	private static Logger logger = LoggerFactory.getLogger(SysMenuAction.class);
    private SysMenu sysMenu = new SysMenu();    //model
    String RtnMessage;

    List dataList;
    String isHaveSon;


    /**
     * 菜单列表
     *
     * @return String
     */
    public String gotoList() {
        try {
            SysMenuBean bean = SysMenuBean.instance();
            dataList = bean.getAllMenuList();
        } catch (Exception e) {
        	logger.error("SysMenuAction.gotoList异常：" + e.getMessage(), e);
            return ERROR;
        }
        return SUCCESS;
    }


    /**
     * 详细信息
     *
     * @return String
     */
    public String getDetail() throws Exception {
        List sonList = null;
        try {
            SysMenuBean bean = SysMenuBean.instance();
            sysMenu = bean.getMenuById(sysMenu.getMenuId());
            sonList = bean.searchListByUpId(sysMenu.getMenuId().toString());
        } catch (Exception e) {
        	logger.error("SysMenuAction.getDetail异常：" + e.getMessage(), e);
            return ERROR;
        }
        if (sonList != null && sonList.size() > 0) {
            isHaveSon = "1";
        } else {
            isHaveSon = "0";
        }
        return SUCCESS;
    }

    /**
     * 显示新增
     *
     * @return String
     */
    public String gotoAdd() throws Exception {
        try {
            return SUCCESS;
        } catch (Exception e) {
        	logger.error("SysMenuAction.gotoAdd异常：" + e.getMessage(), e);
            return ERROR;
        }
    }


    /**
     * 新增
     *
     * @return String
     */
    public void saveMenu() throws Exception {
        try {
            //log.warn("================ in SysMenuAction saveMenu()");
            SysMenuBean bean = SysMenuBean.instance();
            bean.addSysMenu(sysMenu);
            setRtnMessage("addOK");
        } catch (Exception e) {
        	logger.error("SysMenuAction.saveMenu异常：" + e.getMessage(), e);
        }
    }


    /**
     * 修改
     *
     * @return String
     */
    public String doUpdate() throws Exception {
        //log.warn("================ in SysMenuAction doUpdate() ");
        try {
            SysMenuBean bean = SysMenuBean.instance();
            bean.updateSysMenu(sysMenu);
        } catch (Exception e) {
        	logger.error("SysMenuAction.doUpdate异常：" + e.getMessage(), e);
            return ERROR;
        }
        setRtnMessage("updateOK");
        return gotoList();
    }


    /**
     * 删除
     *
     * @return String
     */
    public String doDelete() throws Exception {
        //log.warn("================ in SysMenuAction doDelete() ");
        try {
            SysMenuBean bean = SysMenuBean.instance();
            sysMenu.setIsEnable("0");
            bean.updateSysMenu(sysMenu);
        } catch (Exception e) {
        	logger.error("SysMenuAction.doDelete异常：" + e.getMessage(), e);
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


    public String doDefault() throws Exception {
        return INPUT;
    }

    public Object getModel() {
        return sysMenu;
    }

}
