package com.pltfm.sys.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.pltfm.sys.dao.SysMenuDAO;
import com.pltfm.sys.dao.SysRoleMenuDAO;
import com.pltfm.sys.model.SysMenu;
import com.pltfm.sys.model.SysMenuExample;
import com.pltfm.sys.model.SysRoleMenu;
import com.pltfm.sys.model.SysRoleMenuExample;
import com.pltfm.sys.service.SysMenuService;
import com.pltfm.sys.util.DatetimeUtil;

@Component(value = "sysMenuService")
public class SysMenuServiceImpl implements SysMenuService {
  Logger log = LoggerFactory.getLogger(this.getClass());

  @Resource(name = "sqlMapClient")
  /**
   * 数据库操作对象
   */
  private SqlMapClient sqlMapClient;

  @Resource(name = "sysMenuDAO")
  private SysMenuDAO sysMenuDAO;

  @Resource(name = "sysRoleMenuDAO")
  private SysRoleMenuDAO sysRoleMenuDAO;

  @Override
  public List getMenuListbyLevel(String level) throws Exception {
    try {
      SysMenuExample exam = new SysMenuExample();
      exam.createCriteria().andMenuLvEqualTo(level).andIsEnableEqualTo("1");
      exam.setOrderByClause(" sortno asc");
      return sysMenuDAO.selectByExample(exam);
    } catch (SQLException e) {
      log.warn(e.getMessage());
      throw e;
    }
  }

  @Override
  public List getAllMenuList() throws Exception {
    try {
      SysMenuExample exam = new SysMenuExample();
      exam.createCriteria().andIsEnableEqualTo("1");
      exam.setOrderByClause(" menu_upid asc ,sortno asc");
      return sysMenuDAO.selectByExample(exam);
    } catch (SQLException e) {
      log.warn(e.getMessage());
      throw e;
    }
  }

  @Override
  public List getAllUpMenuList() throws Exception {
    try {
      SysMenuExample exam = new SysMenuExample(); // 组装where查询条件的对象
      exam.createCriteria().andMenuLvEqualTo("1");
      exam.setOrderByClause("sortno asc");
      return sysMenuDAO.selectByExample(exam);
    } catch (SQLException e) {
      log.warn(e.getMessage());
      throw e;
    }
  }

  @Override
  public Integer addSysMenu(SysMenu sysMenu) throws Exception {
    try {
      sysMenu.setCreateDate(DatetimeUtil.getCalendarInstance().getTime());
      sysMenuDAO.insert(sysMenu);
    } catch (SQLException e) {
      e.printStackTrace();
      log.warn(e.getMessage());
      throw e;
    }
    return sysMenu.getMenuId();
  }

  @Override
  public void deleteSysMenu(SysMenu sysMenu) throws Exception {
    try {
      sysMenuDAO.deleteByPrimaryKey(sysMenu.getMenuId());
    } catch (SQLException e) {
      e.printStackTrace();
      log.warn(e.getMessage());
      throw e;
    }
  }

  @Override
  public List searchListByUpId(String upId) throws Exception {
    List dataList = new ArrayList();
    try {
      SysMenuExample exam = new SysMenuExample(); // 组装where查询条件的对象
      exam.createCriteria().andMenuUpidEqualTo(Integer.valueOf(upId)).andIsEnableEqualTo("1");
      exam.setOrderByClause("sortno asc");
      dataList = sysMenuDAO.selectByExample(exam);
    } catch (SQLException e) {
      e.printStackTrace();
      log.warn(e.getMessage());
      throw e;
    }
    return dataList;
  }

  @Override
  public SysMenu getMenuById(Integer menuId) throws Exception {
    try {
      return sysMenuDAO.selectByPrimaryKey(menuId);
    } catch (SQLException e) {
      log.warn(e.getMessage());
      throw e;
    }
  }

  /**
   * 修改菜单
   */
  @Override
  public SysMenu updateSysMenu(SysMenu sysMenu) throws Exception {
    try {
      sysMenu.setUpdateDate(DatetimeUtil.getCalendarInstance().getTime());
      // dao.updateByPrimaryKeySelective(sysMenu);
      sysMenuDAO.updateByPrimaryKey(sysMenu);
    } catch (SQLException e) {
      log.warn(e.getMessage());
      throw e;
    }
    return sysMenu;
  }

  @Override
  public List getMenuListByRoleId(Integer roleId) throws Exception {
    List dataList = new ArrayList();
    try {
      SysRoleMenuExample exam = new SysRoleMenuExample(); // 组装where查询条件的对象
      exam.createCriteria().andRoleIdEqualTo(roleId);
      dataList = sysRoleMenuDAO.selectByExample(exam);
    } catch (SQLException e) {
      e.printStackTrace();
      log.warn(e.getMessage());
      throw e;
    }
    return dataList;
  }

  /**
   * 根据角色id获取该角色的授权header菜单列表
   * 
   * @param Integer
   * @return List
   * @exception Exception
   */
  @Override
  public List getUpMenuListByUserId(Integer userId) throws Exception {
    try {
      return sysMenuDAO.selectUpMenuListByUser(userId);
    } catch (SQLException e) {
      log.warn(e.getMessage());
      throw e;
    }
  }

  /**
   * 根据角色id,upMenuId获取该角色的授权sub菜单列表
   * 
   * @param Integer,Integer
   * @return List
   * @exception Exception
   */
  @Override
  public List getSubMenuListByUserId(Integer userId, Integer upMenuId) throws Exception {
    try {
      return sysMenuDAO.selectSubMenuList(userId, upMenuId);
    } catch (SQLException e) {
      log.warn(e.getMessage());
      throw e;
    }
  }

  @Override
  public void deleteAndsaveRoleMenu(Integer roleId, String[] menuId) throws Exception {
    try {
      sqlMapClient.startTransaction();
      // 开始删除
      SysRoleMenuExample exam = new SysRoleMenuExample(); // 组装where查询条件的对象
      exam.createCriteria().andRoleIdEqualTo(roleId);
      sysRoleMenuDAO.deleteByExample(exam);
      // 开始循环添加
      if (menuId != null && menuId.length > 0) {
        for (int i = 0; i < menuId.length; i++) {
          SysRoleMenu vo = new SysRoleMenu();
          vo.setMenuId(Integer.valueOf(menuId[i]));
          vo.setRoleId(roleId);
          sysRoleMenuDAO.insert(vo);
        }
      }
      sqlMapClient.commitTransaction();
    } catch (SQLException e) {
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
