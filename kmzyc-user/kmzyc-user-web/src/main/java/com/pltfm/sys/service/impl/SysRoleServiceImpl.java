package com.pltfm.sys.service.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.kmzyc.commons.page.Page;
import com.pltfm.sys.dao.SysRoleDAO;
import com.pltfm.sys.model.SysRole;
import com.pltfm.sys.model.SysRoleExample;
import com.pltfm.sys.service.SysRoleService;

@Component(value = "sysRoleService")
public class SysRoleServiceImpl implements SysRoleService {
  Logger log = LoggerFactory.getLogger(this.getClass());

  @Resource(name = "sqlMapClient")
  /**
   * 数据库操作对象
   */
  private SqlMapClient sqlMapClient;
  @Resource(name = "sysRoleDAO")
  private SysRoleDAO sysRoleDAO;

  // 角色列表分页
  @Override
public Page searchSysRoleList(Page pageParam, SysRole vo) throws Exception {
    int pageNo = pageParam.getPageNo();// 当前查询第几页
    if (pageNo == 0) pageNo = 1;// 首次查询第一页
    int pageSize = pageParam.getPageSize(); // 每页显示记录数
    int skip = (pageNo - 1) * pageSize + 1;
    int max = (pageNo - 1) * pageSize + pageSize;
    Page page = null;
    // 组合条件 like
    String roleName = vo.getRoleName(); // 名称
    if (roleName != null && !roleName.equals("")) {
      vo.setRoleName("%" + roleName + "%"); // like statement
    }
    vo.setSkip(skip);
    vo.setMax(max);
    page = sysRoleDAO.searchSysRoleList(pageParam, vo);
    page.setPageNo(pageNo);// 当前查询第几页
    vo.setRoleName(roleName);
    return page;
  };

  /**
   * 根据vo条件查询列表
   * 
   * @param SysRole
   * @return List
   * @exception Exception
   */
  @Override
  public List getSysRoleList(SysRole vo) throws Exception {
    try {
      SysRoleExample exam = new SysRoleExample(); // 组装where查询条件的对象
      // 组合条件 like
      String roleName = vo.getRoleName(); // 名称
      if (roleName != null && !roleName.equals(""))
        roleName = "%" + roleName + "%"; // like statement
      else
        roleName = "%%";

      exam.createCriteria().andRoleNameLike(roleName);
      return sysRoleDAO.selectByExample(exam);
    } catch (SQLException e) {
      log.warn(e.getMessage());
      throw e;
    }
  }

  /**
   * 新增系统角色
   * 
   * @param SysRole
   * @return Integer
   * @exception Exception
   */
  @Override
  public Integer addSysRole(SysRole vo) throws Exception {
    try {
      sysRoleDAO.insert(vo);
    } catch (SQLException e) {
      e.printStackTrace();
      log.warn(e.getMessage());
      throw e;
    }
    return vo.getRoleId();
  }

  /**
   * 根据roleId查询系统角色
   * 
   * @param Integer
   * @return SysRole
   * @exception Exception
   */
  @Override
  public SysRole getSysRoleDetail(Integer id) throws Exception {
    SysRole sysrole = null;
    try {
      sysrole = sysRoleDAO.selectByPrimaryKey(id);
    } catch (SQLException e) {
      e.printStackTrace();
      log.warn(e.getMessage());
      throw e;
    }
    return sysrole;
  }

  /**
   * 修改角色
   * 
   * @param SysRole
   * @return SysRole
   * @exception Exception
   */
  @Override
  public SysRole updateSysRole(SysRole sysrole) throws Exception {
    try {
      sysRoleDAO.updateByPrimaryKeySelective(sysrole);
    } catch (SQLException e) {
      e.printStackTrace();
      log.warn(e.getMessage());
      throw e;
    }
    return sysrole;
  }

  /**
   * 删除所选角色
   * 
   * @param String[]
   * @return void
   * @exception Exception
   */
  @Override
  public void deleteSysRole(String[] SysRole) throws Exception {
    try {
      sqlMapClient.startTransaction();
      // 开始循环删除
      if (SysRole != null && SysRole.length > 0) {
        for (int i = 0; i < SysRole.length; i++) {
          sysRoleDAO.deleteByPrimaryKey(Integer.valueOf(SysRole[i]));
        }
      }
      sqlMapClient.commitTransaction();
    } catch (SQLException e) {
      e.printStackTrace();
      log.warn(e.getMessage());
      throw e;
    } finally {
      try {
        sqlMapClient.endTransaction();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }

  }

}
