package com.pltfm.sys.action;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.pltfm.sys.model.SysMenu;
import com.pltfm.sys.service.SysMenuService;

public class SysMenuAction extends ActionSupport implements ModelDriven {
  Logger log = LoggerFactory.getLogger(this.getClass());
  @Resource(name = "sysMenuService")
  private SysMenuService sysMenuService;
  private SysMenu sysMenu = new SysMenu(); // model
  String RtnMessage;

  List dataList;
  String isHaveSon;



  /**
   * 菜单列表
   *
   * @param @return String @exception
   */
  public String gotoList() {
    try {
      dataList = sysMenuService.getAllMenuList();
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
   * @param @return String @exception
   */
  public String getDetail() throws Exception {
    List sonList = null;
    try {
      sysMenu = sysMenuService.getMenuById(sysMenu.getMenuId());
      sonList = sysMenuService.searchListByUpId(sysMenu.getMenuId().toString());
    } catch (Exception e) {
      e.printStackTrace();
      log.warn(e.getMessage());
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
   * @param @return String @exception
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
   * @param @return String @exception
   */
  public void saveMenu() throws Exception {
    try {
      // log.warn("================ in SysMenuAction saveMenu()");
      sysMenuService.addSysMenu(sysMenu);
      setRtnMessage("addOK");
    } catch (Exception e) {
      log.error("添加顶级菜单失败", e);
    }
  }



  /**
   * 修改
   *
   * @param @return String @exception
   */
  public String doUpdate() throws Exception {
    // log.warn("================ in SysMenuAction doUpdate() ");
    try {
      sysMenuService.updateSysMenu(sysMenu);
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
   * @param @return String @exception
   */
  public String doDelete() throws Exception {
    // log.warn("================ in SysMenuAction doDelete() ");
    try {
      sysMenu.setIsEnable("0");
      sysMenuService.updateSysMenu(sysMenu);
    } catch (Exception e) {
      e.printStackTrace();
      return ERROR;
    }
    setRtnMessage("deleteOK");
    return gotoList();
  }



  // public String execute() throws Exception {
  // try {
  // return SUCCESS;
  // } catch (Exception e) {
  // return ERROR;
  // }
  // }



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
