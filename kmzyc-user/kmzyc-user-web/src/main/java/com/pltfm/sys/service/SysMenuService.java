package com.pltfm.sys.service;

import java.util.List;

import com.pltfm.sys.model.SysMenu;

public interface SysMenuService {
  /**
   * 根据level查询菜单List
   * 
   * @param String
   * @return List
   * @exception Exception
   */
  public List getMenuListbyLevel(String level) throws Exception;

  /**
   * 得到所有菜单List
   * 
   * @param
   * @return List
   * @exception Exception
   */
  public List getAllMenuList() throws Exception;

  /**
   * 获取所有的顶层菜单
   * 
   * @param
   * @return List
   * @exception Exception
   */
  public List getAllUpMenuList() throws Exception;

  /**
   * 新增菜单
   * 
   * @param SysMenu
   * @return Integer
   * @exception Exception
   */
  public Integer addSysMenu(SysMenu sysMenu) throws Exception;

  /**
   * 删除所选菜单
   * 
   * @param SysMenu
   * @return void
   * @exception Exception
   */
  public void deleteSysMenu(SysMenu sysMenu) throws Exception;

  /**
   * 根据上级menuId查询list
   * 
   * @param String
   * @return List
   * @exception Exception
   */
  public List searchListByUpId(String upId) throws Exception;

  /**
   * 根据menuId得到menu
   * 
   * @param Integer
   * @return SysMenu
   * @exception Exception
   */
  public SysMenu getMenuById(Integer menuId) throws Exception;

  /**
   * 修改菜单
   * 
   * @param SysMenu
   * @return SysMenu
   * @exception Exception
   */
  public SysMenu updateSysMenu(SysMenu sysMenu) throws Exception;

  /**
   * 根据角色id获取该角色的授权菜单列表
   * 
   * @param Integer
   * @return List
   * @exception Exception
   */
  public List getMenuListByRoleId(Integer roleId) throws Exception;

  /**
   * 根据角色id获取该角色的授权header菜单列表
   * 
   * @param Integer
   * @return List
   * @exception Exception
   */
  public List getUpMenuListByUserId(Integer userId) throws Exception;

  /**
   * 根据角色id,upMenuId获取该角色的授权sub菜单列表
   * 
   * @param Integer,Integer
   * @return List
   * @exception Exception
   */
  public List getSubMenuListByUserId(Integer userId, Integer upMenuId) throws Exception;

  /**
   * 先删除该角色的所有授权菜单，然后再添加新的授权菜单
   * 
   * @param Integer,String[]
   * @return void
   * @exception Exception
   */
  public void deleteAndsaveRoleMenu(Integer roleId, String[] menuId) throws Exception;



}
