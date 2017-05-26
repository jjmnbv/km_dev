package com.pltfm.sys.service;

import java.util.List;

import com.kmzyc.commons.page.Page;
import com.pltfm.sys.model.SysUser;

public interface SysUserRoleService {
  /**
   * 修改员工信息和员工角色信息
   * 
   * @param SysUser,String
   * @return SysUser
   * @exception Exception
   */
  public SysUser updateSysUserRole(SysUser sysuser, String roleListStr) throws Exception;

  /**
   * 删除所选操作员
   * 
   * @param String[]
   * @return void
   * @exception Exception
   */
  public void deleteSysUser(String[] userId) throws Exception;

  /**
   * 检查客户的登录名是否已存在
   * 
   * @param sysuser
   * @return String
   * @exception Exception
   */
  public String checkOnlyName(SysUser sysuser) throws Exception;

  /**
   * 用户登录验证
   * 
   * @param SysUser
   * @return SysUser
   * @exception Exception
   */
  public SysUser sysUserLogin(SysUser sysuser) throws Exception;

  /**
   * 根据vo条件查询列表
   * 
   * @param SysUser
   * @return List
   * @exception Exception
   */
  public List getSysUserList(SysUser vo) throws Exception;

  /**
   * 根据roleId查询userList
   * 
   * @param Integer
   * @return List
   * @exception Exception
   */
  public List getUserListByRoleId(Integer roleId) throws Exception;

  /**
   * 根据vo条件查询分类信息page
   * 
   * @param Page,SysUser
   * @return Page
   * @exception Exception
   */
  public Page searchPageByVo(Page pageParam, SysUser vo) throws Exception;

  /**
   * 根据vo条件查询列表
   * 
   * @param SysUser
   * @return List
   * @exception Exception
   */
  public List searchUserListByDept(SysUser vo) throws Exception;

  /**
   * 根据菜单编号，查询有该菜单权限的人员列表
   * 
   * @param Integer
   * @return List
   * @exception Exception
   */
  public List getRoleUserListByMenuId(Integer menuId) throws Exception;


}
