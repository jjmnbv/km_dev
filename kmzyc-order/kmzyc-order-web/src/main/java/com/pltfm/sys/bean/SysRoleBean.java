package com.pltfm.sys.bean;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.pltfm.sys.dao.SysRoleDAOImpl;
import com.pltfm.sys.model.SysRole;
import com.pltfm.sys.model.SysRoleExample;

/**
 * 类 SysRoleBean 系统角色类
 * 
 * @author
 * @version 2.1
 * @since JDK1.5
 */
@SuppressWarnings("unchecked")
public class SysRoleBean extends BaseBean {

  Logger log = Logger.getLogger(this.getClass());

  public SysRoleBean() {
    super(); // 加载总的sqlmap配置文件
  }

  /**
   * 根据vo条件查询列表
   * 
   * @param SysRole
   * @return List
   * @exception Exception
   */
  public List getSysRoleList(SysRole vo) throws Exception {
    List dataList = new ArrayList();
    try {
      SysRoleDAOImpl dao = new SysRoleDAOImpl(sqlMap);
      SysRoleExample exam = new SysRoleExample(); // 组装where查询条件的对象
      // 组合条件 like
      String roleName = vo.getRoleName(); // 名称
      if (roleName != null && !roleName.equals(""))
        roleName = "%" + roleName + "%"; // like statement
      else
        roleName = "%%";

      exam.createCriteria().andRoleNameLike(roleName);
      dataList = dao.selectByExample(exam);
    } catch (SQLException e) {
      log.error(e);
      log.warn(e.getMessage());
      throw e;
    }
    return dataList;
  }

  /**
   * 新增系统角色
   * 
   * @param SysRole
   * @return Integer
   * @exception Exception
   */
  public Integer addSysRole(SysRole vo) throws Exception {
    try {
      SysRoleDAOImpl dao = new SysRoleDAOImpl(sqlMap);
      dao.insert(vo);
    } catch (SQLException e) {
      log.error(e);
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
  public SysRole getSysRoleDetail(Integer id) throws Exception {
    SysRole sysrole = null;
    try {
      SysRoleDAOImpl dao = new SysRoleDAOImpl(sqlMap);
      sysrole = dao.selectByPrimaryKey(id);
    } catch (SQLException e) {
      log.error(e);
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
  public SysRole updateSysRole(SysRole sysrole) throws Exception {
    try {
      SysRoleDAOImpl dao = new SysRoleDAOImpl(sqlMap);
      dao.updateByPrimaryKeySelective(sysrole);
    } catch (SQLException e) {
      log.error(e);
      log.warn(e.getMessage());
      throw e;
    }
    return sysrole;
  }

  /**
   * 删除所选角色
   * 
   * @param String []
   * @return void
   * @exception Exception
   */
  public void deleteSysRole(String[] SysRole) throws Exception {
    try {
      sqlMap.startTransaction();
      SysRoleDAOImpl dao = new SysRoleDAOImpl(sqlMap);
      // 开始循环删除
      if (SysRole != null && SysRole.length > 0) {
        for (int i = 0; i < SysRole.length; i++) {
          dao.deleteByPrimaryKey(Integer.valueOf(SysRole[i]));
        }
      }
      sqlMap.commitTransaction();
    } catch (SQLException e) {
      log.error(e);
      log.warn(e.getMessage());
      throw e;
    } finally {
      try {
        sqlMap.endTransaction();
      } catch (SQLException e) {
        log.error(e);
      }
    }
  }

}
