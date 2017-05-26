package com.pltfm.sys.service;

import java.util.List;

import com.kmzyc.commons.page.Page;
import com.pltfm.sys.model.SysRole;

public interface SysRoleService {
  /**
   * 角色列表分页
   * 
   * @param page
   * @param vo
   * @return
   * @throws Exception
   */
  Page searchSysRoleList(Page page, SysRole vo) throws Exception;

  /**
   * 根据vo条件查询列表
   * 
   * @param SysRole
   * @return List
   * @exception Exception
   */
  public List getSysRoleList(SysRole vo) throws Exception;

  /**
   * 新增系统角色
   * 
   * @param SysRole
   * @return Integer
   * @exception Exception
   */
  public Integer addSysRole(SysRole vo) throws Exception;

  /**
   * 根据roleId查询系统角色
   * 
   * @param Integer
   * @return SysRole
   * @exception Exception
   */
  public SysRole getSysRoleDetail(Integer id) throws Exception;

  /**
   * 修改角色
   * 
   * @param SysRole
   * @return SysRole
   * @exception Exception
   */
  public SysRole updateSysRole(SysRole sysrole) throws Exception;

  /**
   * 删除所选角色
   * 
   * @param String[]
   * @return void
   * @exception Exception
   */
  public void deleteSysRole(String[] SysRole) throws Exception;


}
