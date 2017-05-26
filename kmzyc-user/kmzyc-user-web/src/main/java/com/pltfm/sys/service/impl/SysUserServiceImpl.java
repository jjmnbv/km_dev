package com.pltfm.sys.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.kmzyc.commons.page.Page;
import com.pltfm.sys.dao.SysRoleMenuDAO;
import com.pltfm.sys.dao.SysUserDAO;
import com.pltfm.sys.dao.SysUserRoleDAO;
import com.pltfm.sys.model.SysRole;
import com.pltfm.sys.model.SysRoleMenu;
import com.pltfm.sys.model.SysRoleMenuExample;
import com.pltfm.sys.model.SysUser;
import com.pltfm.sys.model.SysUserExample;
import com.pltfm.sys.model.SysUserRole;
import com.pltfm.sys.model.SysUserRoleExample;
import com.pltfm.sys.service.SysUserService;
import com.pltfm.sys.util.DatetimeUtil;

@Component(value = "sysUserService")
public class SysUserServiceImpl implements SysUserService {
  Logger log = LoggerFactory.getLogger(this.getClass());
  @Resource(name = "sqlMapClient")
  /**
   * 数据库操作对象
   */
  private SqlMapClient sqlMapClient;

  @Resource(name = "sysUserDAO")
  private SysUserDAO sysUserDAO;
  @Resource(name = "sysRoleMenuDAO")
  private SysRoleMenuDAO sysRoleMenuDAO;
  @Resource(name = "sysUserRoleDAO")
  private SysUserRoleDAO sysUserRoleDAO;

  @Override
  public Integer addSysUser(SysUser sysuser) throws Exception {
    try {
      sysuser.setCreateDate(DatetimeUtil.getCalendarInstance().getTime());
      sysuser.setUserLasttime(DatetimeUtil.getCalendarInstance().getTime());
      sysUserDAO.insert(sysuser);
    } catch (SQLException e) {
      e.printStackTrace();
      log.warn(e.getMessage());
      throw e;
    }
    return sysuser.getUserId();
  }

  @Override
  public Integer addSysUserRole(SysUser sysuser, String roleListStr) throws Exception {
    try {
      sqlMapClient.startTransaction();
      // 添加员工
      sysuser.setCreateDate(DatetimeUtil.getCalendarInstance().getTime());
      Integer userid = sysUserDAO.insertSelective(sysuser);
      log.warn("----------------------------------------userid=" + userid);
      log.warn("----------------------------------------sysuser.userid=" + sysuser.getUserId());
      // 添加员工角色关系
      String[] roleids = roleListStr.split(",");
      if (roleids.length > 0) {
        for (int i = 0; i < roleids.length; i++) {
          SysUserRole urvo = new SysUserRole();
          urvo.setUserId(userid);
          urvo.setRoleId(Integer.valueOf(roleids[i]));
          sysUserRoleDAO.insert(urvo);
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
    return sysuser.getUserId();
  }

  /**
   * 删除操作员同时删除系统角色
   *
   * @param SysUser,String
   * @return Integer
   * @exception Exception
   */
  @Override
  public Integer delSysUserRole(SysUser sysuser) throws Exception {
    try {
      sqlMapClient.startTransaction();
      // 添加员工
      sysuser.setCreateDate(DatetimeUtil.getCalendarInstance().getTime());
      List<SysUserRole> roleList = sysUserDAO.selectByUser(sysuser);

      // 删除员工角色关系
      if (roleList != null && roleList.size() > 0) {
        for (int i = 0; i < roleList.size(); i++) {
          sysUserRoleDAO.deleteByPrimaryKey(roleList.get(i).getUserroleId());
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
    return sysuser.getUserId();
  }

  /**
   * 根据userId查询某个操作员
   *
   * @param Integer
   * @return SysUser
   * @exception Exception
   */
  @Override
  public SysUser getSysUserDetail(Integer userId) throws Exception {
    SysUser sysuser = null;
    try {
      sysuser = sysUserDAO.selectByPrimaryKey(userId);
    } catch (SQLException e) {
      e.printStackTrace();
      log.warn(e.getMessage());
      throw e;
    }
    return sysuser;
  }

  /**
   * 根据userid获取user信息及其所有角色串
   * 
   * @param Integer
   * @return SysUser
   * @exception Exception
   */
  @Override
  public SysUser getSysUserRoleDetail(Integer userId) throws Exception {
    SysUser sysuser = null;
    try {
      sqlMapClient.startTransaction();
      // 获取user信息
      sysuser = sysUserDAO.selectByPrimaryKey(userId);
      // 获取roles串信息
      List rolesList = sysUserDAO.selectUserRolesList(userId);
      if (rolesList != null && rolesList.size() > 0) {
        String roleidStr = "";
        String roleName = "";
        for (int i = 0; i < rolesList.size(); i++) {
          SysRole role = (SysRole) rolesList.get(i);
          roleidStr += role.getRoleId().toString() + ",";
          roleName += role.getRoleName().toString() + ",";
        }
        roleidStr = roleidStr.substring(0, roleidStr.lastIndexOf(","));
        roleName = roleName.substring(0, roleName.lastIndexOf(","));
        sysuser.setRoleIdsStr(roleidStr);
        sysuser.setRoleName(roleName);
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
    return sysuser;
  }

  /**
   * 修改操作员
   * 
   * @param SysUser
   * @return SysUser
   * @exception Exception
   */
  @Override
  public SysUser updateSysUser(SysUser sysuser) throws Exception {
    try {
      sysuser.setUpdateDate(DatetimeUtil.getCalendarInstance().getTime());
      sysUserDAO.updateByPrimaryKeySelective(sysuser);
    } catch (SQLException e) {
      e.printStackTrace();
      log.warn(e.getMessage());
      throw e;
    }
    return sysuser;
  }

  /**
   * 简单修改用户信息，所有修改项都在action中组装
   * 
   * @param SysUser
   * @return SysUser
   * @exception Exception
   */
  @Override
  public SysUser simpleUpdateSysUser(SysUser sysuser) throws Exception {
    log.warn("================== SysUserBean.simpleUpdateSysUser()");
    try {
      sysUserDAO.updateByPrimaryKeySelective(sysuser);
    } catch (SQLException e) {
      e.printStackTrace();
      log.warn(e.getMessage());
      throw e;
    }
    return sysuser;
  }

  /**
   * 修改员工信息和员工角色信息
   * 
   * @param SysUser,String
   * @return SysUser
   * @exception Exception
   */
  @Override
  public SysUser updateSysUserRole(SysUser sysuser, String roleListStr) throws Exception {
    try {
      sqlMapClient.startTransaction();
      // 修改员工信息
      sysuser.setUpdateDate(DatetimeUtil.getCalendarInstance().getTime());
      sysUserDAO.updateByPrimaryKeySelective(sysuser);
      // 修改员工所属角色信息
      SysUserRoleExample exam = new SysUserRoleExample();
      exam.createCriteria().andUserIdEqualTo(sysuser.getUserId());
      sysUserRoleDAO.deleteByExample(exam); // 将该会员的所有角色关系删除
      String[] roleids = roleListStr.split(",");
      if (roleids.length > 0) {
        for (int i = 0; i < roleids.length; i++) {
          SysUserRole urvo = new SysUserRole();
          urvo.setUserId(sysuser.getUserId());
          urvo.setRoleId(Integer.valueOf(roleids[i]));
          sysUserRoleDAO.insert(urvo); // 再添加新的角色关系
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
    return sysuser;
  }

  /**
   * 删除所选操作员
   * 
   * @param String[]
   * @return void
   * @exception Exception
   */
  @Override
  public void deleteSysUser(String[] userId) throws Exception {
    try {
      sqlMapClient.startTransaction();
      // 开始循环删除
      if (userId != null && userId.length > 0) {
        for (int i = 0; i < userId.length; i++) {
          sysUserDAO.deleteByPrimaryKey(Integer.valueOf(userId[i]));
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

  /**
   * 检查客户的登录名是否已存在
   * 
   * @param sysuser
   * @return String
   * @exception Exception
   */
  @Override
  public String checkOnlyName(SysUser sysuser) throws Exception {
    String rtnMessage = null;
    List userList = null;
    try {
      SysUserExample exam = new SysUserExample();
      // 首先检查用户名是否重复
      if (sysuser.getUserId() == null) {// 新增时
        exam.createCriteria().andUserNameEqualTo(sysuser.getUserName());
      } else {// 修改时
        exam.createCriteria().andUserNameEqualTo(sysuser.getUserName())
            .andUserIdNotEqualTo(sysuser.getUserId());
      }
      userList = sysUserDAO.selectByExample(exam);
      if (userList != null && userList.size() > 0) {
        rtnMessage = "登录名已存在,操作终止!";
        return rtnMessage;
      }
    } catch (SQLException e) {
      e.printStackTrace();
      log.warn(e.getMessage());
      throw e;
    }
    return null;
  }

  /**
   * 用户登录验证
   * 
   * @param SysUser
   * @return SysUser
   * @exception Exception
   */
  @Override
  public SysUser sysUserLogin(SysUser sysuser) throws Exception {
    log.warn("================== SysUserBean.sysUserLogin()");
    SysUser rtnSysUser = null;
    try {
      SysUserExample exam = new SysUserExample();
      exam.createCriteria().andUserNameEqualTo(sysuser.getUserName())
          // .andUserPwdEqualTo(MD5.md5crypt(sysuser.getUserPwd()))
          .andIsEnableEqualTo("1");
      List userList = sysUserDAO.selectByExample(exam);
      if (userList != null && userList.size() > 0) {
        rtnSysUser = (SysUser) userList.get(0);
      }
    } catch (SQLException e) {
      e.printStackTrace();
      log.warn(e.getMessage());
      throw e;
    }
    return rtnSysUser;
  }

  /**
   * 根据vo条件查询列表
   * 
   * @param SysUser
   * @return List
   * @exception Exception
   */
  @Override
  public List getSysUserList(SysUser vo) throws Exception {
    try {
      SysUserExample exam = new SysUserExample(); // 组装where查询条件的对象
      // 组合条件 like
      String userName = vo.getUserName(); // 名称
      if (userName != null && !userName.equals(""))
        userName = "%" + userName + "%"; // like statement
      else
        userName = "%%";
      exam.createCriteria().andUserNameLike(userName);
      return sysUserDAO.selectByExample(exam);
    } catch (SQLException e) {
      log.warn(e.getMessage());
      throw e;
    }
  }

  /**
   * 根据roleId查询userList
   * 
   * @param roleId
   * @return List
   * @exception Exception
   */
  @Override
  public List getUserListByRoleId(Integer roleId) throws Exception {
    try {
      return sysUserDAO.getUserListByRoleId(roleId);
    } catch (SQLException e) {
      log.warn(e.getMessage());
      throw e;
    }
  }

  /**
   * 根据vo条件查询分类信息page
   * 
   * @param Page,SysUser
   * @return Page
   * @exception Exception
   */
  @Override
  public Page searchPageByVo(Page pageParam, SysUser vo) throws Exception {
    int pageNo = pageParam.getPageNo();// 当前查询第几页
    if (pageNo == 0) pageNo = 1;// 首次查询第一页
    int pageSize = pageParam.getPageSize(); // 每页显示记录数
    int skip = (pageNo - 1) * pageSize + 1;
    int max = (pageNo - 1) * pageSize + pageSize;
    Page page = null;
    try {
      // 组合条件 like
      String userReal = vo.getUserReal(); // 姓名
      if (userReal != null && !userReal.equals("")) vo.setUserReal("%" + userReal + "%"); // like
                                                                                          // statement
      vo.setSkip(skip);
      vo.setMax(max);
      page = sysUserDAO.selectPageByVo(pageParam, vo);
      page.setPageNo(pageNo);// 当前查询第几页
      vo.setUserReal(userReal);

    } catch (SQLException e) {
      e.printStackTrace();
      log.warn(e.getMessage());
      throw e;
    }
    return page;
  }

  /**
   * 根据vo条件查询列表
   * 
   * @param SysUser
   * @return List
   * @exception Exception
   */
  @Override
  public List searchUserListByDept(SysUser vo) throws Exception {
    try {
      // 组合条件 like
      String userName = vo.getUserName(); // 用户名
      if (userName != null && !userName.equals("")) vo.setUserName("%" + userName + "%");
      String userReal = vo.getUserReal(); // 姓名
      if (userReal != null && !userReal.equals("")) vo.setUserReal("%" + userReal + "%"); // like
      vo.setUserReal(userReal);
      vo.setUserName(userName);                                                                              // statement

      return sysUserDAO.selectListByVo(vo);


    } catch (SQLException e) {
      e.printStackTrace();
      log.warn(e.getMessage());
      throw e;
    }
  }

  /**
   * 根据菜单编号，查询有该菜单权限的人员列表
   * 
   * @param menuId
   * @return List
   * @exception Exception
   */
  @Override
  public List getRoleUserListByMenuId(Integer menuId) throws Exception {
    List dataList = new ArrayList();
    try {
      sqlMapClient.startTransaction();
      SysRoleMenuExample roleMenuExam = new SysRoleMenuExample();
      roleMenuExam.createCriteria().andMenuIdEqualTo(menuId);
      List roleMenuList = sysRoleMenuDAO.selectByExample(roleMenuExam);// 得到有该菜单权限的所有角色
      List rmList = new ArrayList();

      if (roleMenuList != null && roleMenuList.size() > 0) {
        for (int i = 0; i < roleMenuList.size(); i++) {
          SysRoleMenu srm = (SysRoleMenu) roleMenuList.get(i);
          Integer roleId = srm.getRoleId();
          rmList.add(roleId);
        }

        SysUserRoleExample userroleExam = new SysUserRoleExample();
        userroleExam.createCriteria().andRoleIdIn(rmList);
        List userroleList = sysUserRoleDAO.selectByExample(userroleExam);// 得到有该角色的所有人
        List urList = new ArrayList();

        if (userroleList != null && userroleList.size() > 0) {
          for (int j = 0; j < userroleList.size(); j++) {
            SysUserRole sur = (SysUserRole) userroleList.get(j);
            Integer userId = sur.getUserId();
            urList.add(userId);
          }

          SysUserExample userExam = new SysUserExample();
          userExam.createCriteria().andUserIdIn(urList);
          dataList = sysUserDAO.selectByExample(userExam);

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
    return dataList;
  }

}
