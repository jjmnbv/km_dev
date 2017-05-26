package com.pltfm.sys.action;


import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kmzyc.commons.page.Page;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.pltfm.app.util.ConfigureUtils;
import com.pltfm.sys.model.SysMenu;
import com.pltfm.sys.model.SysRole;
import com.pltfm.sys.model.SysRoleMenu;
import com.pltfm.sys.service.SysMenuService;
import com.pltfm.sys.service.SysRoleService;
import com.pltfm.sys.util.XiuSystemConstant;


@SuppressWarnings({"serial", "rawtypes"})
public class SysRoleAction extends ActionSupport implements ModelDriven {
  @Resource(name = "sysMenuService")
  private SysMenuService sysMenuService;
  @Resource(name = "sysRoleService")
  private SysRoleService sysRoleService;

  Logger log = LoggerFactory.getLogger(this.getClass());
  SysRole sysrole = new SysRole(); // model
  String rtnMessage;
  private Map<SysMenu, String> dataMap = new LinkedHashMap<SysMenu, String>();

  public Map<SysMenu, String> getDataMap() {
    return dataMap;
  }

  public void setDataMap(Map<SysMenu, String> dataMap) {
    this.dataMap = dataMap;
  }



  List dataList;
  private List roleList = new ArrayList();
  List menuList;
  private List upmenuList;
  Page page = new Page();
  int paramPageSize = XiuSystemConstant.PARAM_PAGE_SIZE;
  String[] delId;



  public List getUpmenuList() {
    return upmenuList;
  }



  public void setUpmenuList(List upmenuList) {
    this.upmenuList = upmenuList;
  }



  /**
   * 分页获取列表
   *
   * @param @return String @exception
   */
  public String selectListSysRole() throws Exception {
    try {
      this.page.setPageSize(this.getParamPageSize()); // 设置每页显示条数
      Page returnPage = sysRoleService.searchSysRoleList(page, sysrole);
      this.setPage(returnPage);
    } catch (Exception e) {
      log.error("初始化角色列表失败" + e.getMessage(), e);
      return ERROR;
    }
    return SUCCESS;
  }

  /**
   * 获取列表
   *
   * @param @return String @exception
   */
  public String listSysRole() throws Exception {
    try {
      dataList = sysRoleService.getSysRoleList(sysrole);
    } catch (Exception e) {
      log.error("初始化角色列表失败" + e.getMessage(), e);
      return ERROR;
    }
    return SUCCESS;
  }



  /**
   * 新增
   *
   * @param @return String @exception
   */
  public String save() throws Exception {
    try {
      sysRoleService.addSysRole(sysrole);
    } catch (Exception e) {
      log.error("新增操作员失败" + e.getMessage(), e);
      return ERROR;
    }
    this.addActionMessage(ConfigureUtils.getMessageConfig("sysRole.add.success"));
    sysrole = new SysRole();
    return selectListSysRole();
  }



  /**
   * 详细信息
   *
   * @param @return String @exception
   */
  public String getDetail() throws Exception {
    Integer id = sysrole.getRoleId();
    try {
      this.sysrole = sysRoleService.getSysRoleDetail(id);
    } catch (Exception e) {
      log.error("获取角色信息失败" + e.getMessage(), e);
      return ERROR;
    }
    return SUCCESS;
  }



  /**
   * 修改角色提交
   *
   * @param @return String @exception
   */
  public String doUpdate() throws Exception {
    try {
      sysrole = sysRoleService.updateSysRole(sysrole);
      this.addActionMessage(ConfigureUtils.getMessageConfig("sysRole.update.success"));
    } catch (Exception e) {
      log.error("修改角色失败" + e.getMessage(), e);
      return ERROR;
    }
    sysrole = new SysRole();
    return this.selectListSysRole();
  }



  /**
   * 通过系统角色获取菜单
   *
   * @param @return String @exception
   */
  public String getMenuListByRole() throws Exception {
    try {
      // 获取所有的角色
      roleList = sysRoleService.getSysRoleList(new SysRole());
      // 获取菜单
      menuList = sysMenuService.getAllUpMenuList();
      // 获取授权的菜单
      dataList = sysMenuService.getMenuListByRoleId(sysrole.getRoleId());
      sysrole = sysRoleService.getSysRoleDetail(sysrole.getRoleId());
      dataMap = dataMapByList(menuList, dataList);
    } catch (Exception e) {
      log.error("初始化角色菜单授权失败" + e.getMessage(), e);
      return ERROR;
    }
    return SUCCESS;
  }



  /**
   * 保存角色菜单关系(角色授权)
   *
   * @param @return String @exception
   */
  public String saveRoleMenu() throws Exception {
    try {
      sysMenuService.deleteAndsaveRoleMenu(sysrole.getRoleId(), delId);
      this.addActionMessage(ConfigureUtils.getMessageConfig("sysRole.save.success"));
    } catch (Exception e) {
      log.error("添加角色失败" + e.getMessage(), e);
      return ERROR;
    }
    return getMenuListByRole();
  }



  /**
   * 删除所选角色
   *
   * @param @return String @exception
   */
  public String doDelete() throws Exception {
    try {
      sysRoleService.deleteSysRole(delId);
    } catch (Exception e) {
      log.error("删除所选角色失败" + e.getMessage(), e);
      return ERROR;
    }
    this.addActionMessage(ConfigureUtils.getMessageConfig("sysRole.delete.success"));
    return selectListSysRole();
  }

  /**
   * 角色菜单授权 目前只支持4级菜单
   * 
   * @return 20150115
   */
  public String gotoRoleMenuEdit() {
    // 获取该角色已有的授权菜单列表
    // 获取所有系统角色
    SysRole sysrole = new SysRole();
    try {
      roleList = sysRoleService.getSysRoleList(sysrole);
      // 获取菜单
      upmenuList = sysMenuService.getAllUpMenuList();
      dataMap = dataMapByList(upmenuList, null);
    } catch (Exception e) {
      log.error("获取菜单失败", e);
      e.printStackTrace();
    }


    return SUCCESS;
  }

  private Map<SysMenu, String> dataMapByList(List<SysMenu> listSys, List<SysRoleMenu> listSysRole)
      throws Exception {
    Map<SysMenu, String> map = new LinkedHashMap<SysMenu, String>();
    ArrayList rMenuIdList = new ArrayList();
    if (listSysRole != null && listSysRole.size() > 0) {
      for (int i = 0; i < listSysRole.size(); i++) {
        rMenuIdList.add(listSysRole.get(i).getMenuId());
      }
    }
    if (listSys != null && listSys.size() > 0) {
      for (int i = 0; i < listSys.size(); i++) {
        SysMenu sysMenu = listSys.get(i);

        List submenuList = sysMenuService.searchListByUpId(sysMenu.getMenuId().toString());
        Map submenuMap = new LinkedHashMap<SysMenu, String>();
        for (int j = 0; j < submenuList.size(); j++) {
          SysMenu sysMenu1 = (SysMenu) submenuList.get(j);
          List subsubmenuList = sysMenuService.searchListByUpId(sysMenu1.getMenuId().toString());
          Map subsubmenuMap = new LinkedHashMap<SysMenu, String>();
          for (int k = 0; k < subsubmenuList.size(); k++) {
            SysMenu sysMenu2 = (SysMenu) subsubmenuList.get(k);
            List subsubsubmenuList =
                sysMenuService.searchListByUpId(sysMenu2.getMenuId().toString());
            Map subsubsubmenuMap = new LinkedHashMap<SysMenu, String>();
            for (int l = 0; l < subsubsubmenuList.size(); l++) {
              SysMenu sysMenu3 = (SysMenu) subsubmenuList.get(l);
              if (rMenuIdList.contains(sysMenu3.getMenuId())) {
                subsubsubmenuMap.put(sysMenu3, "checked");// 表示菜单被选中
              } else {
                subsubsubmenuMap.put(sysMenu3, "");
              }
            }
            sysMenu2.setMenuMap(subsubsubmenuMap);
            if (rMenuIdList.contains(sysMenu2.getMenuId())) {
              subsubmenuMap.put(sysMenu2, "checked");
            } else
              subsubmenuMap.put(sysMenu2, "");
          }
          sysMenu1.setMenuMap(subsubmenuMap);
          if (rMenuIdList.contains(sysMenu1.getMenuId()))
            submenuMap.put(sysMenu1, "checked");
          else
            submenuMap.put(sysMenu1, "");
        }
        sysMenu.setMenuMap(submenuMap);
        if (rMenuIdList.contains(sysMenu.getMenuId()))
          map.put(sysMenu, "checked");
        else
          map.put(sysMenu, "");
      }
    }
    return map;
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



  public List getRoleList() {
    return roleList;
  }



  public void setRoleList(List roleList) {
    this.roleList = roleList;
  }



  public List getMenuList() {
    return menuList;
  }



  public void setMenuList(List menuList) {
    this.menuList = menuList;
  }



  @Override
public Object getModel() {
    return sysrole;
  }
}
