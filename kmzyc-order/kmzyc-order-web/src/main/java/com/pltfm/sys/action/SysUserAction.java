package com.pltfm.sys.action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.jasig.cas.client.util.AssertionHolder;

import com.kmzyc.commons.page.Page;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;
import com.pltfm.app.util.MD5;
import com.pltfm.sys.bean.SysDeptBean;
import com.pltfm.sys.bean.SysMenuBean;
import com.pltfm.sys.bean.SysUserBean;
import com.pltfm.sys.model.SysDept;
import com.pltfm.sys.model.SysMenu;
import com.pltfm.sys.model.SysUser;
import com.pltfm.sys.util.XiuSystemConstant;

/**
 * 类 SysUserAction 操作员Action类
 * 
 * @author
 * @version 2.1
 * @since JDK1.5
 */
@SuppressWarnings("unchecked")
public class SysUserAction extends com.pltfm.app.action.BaseAction implements ModelDriven {
  private static final long serialVersionUID = 1L;
  Logger log = Logger.getLogger(this.getClass());
  private SysUser sysuser = new SysUser(); // model
  String rtnMessage;

  Page page = new Page(); // 分页变量
  int paramPageSize = XiuSystemConstant.PARAM_PAGE_SIZE; // 接受页面中传过来的每页显示条数的参数
  String[] delId; // 删除操作id
  List dataList;
  String deptName;
  String roleListStr;
  String topMenuId;
  String topMenuName;
  String newPwd1;
  String userIdStr;
  String userNameStr;

  /**
   * 操作员登录
   * 
   * @param
   * @return String
   * @exception
   */
  public String loginSysUser() {
    log.warn("================== SysUserAction.loginSysUser()");

    String loginName = AssertionHolder.getAssertion().getPrincipal().getName();

    log.warn("===================------------------------------------loginName-----" + loginName);
    if (loginName == null) {
      rtnMessage = "login";
      return "login";
    }
    try {
      // 获取登录人基本信息
      sysuser.setUserName(loginName);
      SysUserBean bean = new SysUserBean();
      sysuser = bean.sysUserLogin(sysuser);
      if (sysuser == null) {
        rtnMessage = "loginError";
        return "loginError";
      }
      // 设置最后登录时间
      // SysUser su=new SysUser();
      // su.setUserId(sysuser.getUserId());
      // su.setUserLasttime(DatetimeUtil.getCalendarInstance().getTime());
      // bean.simpleUpdateSysUser(su);
    } catch (Exception e) {
      log.error(e);
      return ERROR;
    }
    // 设置session
    Map session = ActionContext.getContext().getSession();
    if (session.get("sysUser") != null) {
      session.clear();
    }
    HttpServletRequest request =
        (HttpServletRequest) ActionContext.getContext().get(ServletActionContext.HTTP_REQUEST);
    String topMenuId = request.getParameter("topMenuId");
    if (topMenuId != null) sysuser.setHeadMenuId(topMenuId);
    session.put("sysUser", sysuser);

    rtnMessage = "loginOk";
    return SUCCESS;
  }

  /**
   * 获取用户权限内头部Menu信息
   * 
   * @param
   * @return String
   * @exception
   */
  public String getSysTop() {
    Map session = ActionContext.getContext().getSession();
    SysUser sessionUser = (SysUser) session.get("sysUser");
    try {
      // 获取登录人菜单权限信息
      SysMenuBean mbean = new SysMenuBean();
      dataList = mbean.getUpMenuListByUserId(sessionUser.getUserId());
    } catch (Exception e) {
      log.error(e);
      return ERROR;
    }
    return SUCCESS;
  }

  /**
   * 点击header菜单刷新左侧菜单
   * 
   * @param
   * @return String
   * @exception
   */
  public String changeSysHeaderMenu() {
    Map session = ActionContext.getContext().getSession();
    try {
      SysUser sessionUser = (SysUser) session.get("sysUser");
      sessionUser.setHeadMenuId(topMenuId);
      session.put("sysUser", sessionUser);
      SysMenu sMenu = null;

      SysMenuBean bean = new SysMenuBean();
      sMenu = bean.getMenuById(Integer.valueOf(topMenuId));
      topMenuName = "";
      if (sMenu != null) {
        topMenuName = sMenu.getMenuName();
      }

    } catch (Exception e) {
      log.error(e);
      return "login";
    }
    return SUCCESS;
  }

  /**
   * 获取左侧导航菜单
   * 
   * @param
   * @return String
   * @exception
   */
  public String getSysLeftMenu() {
    Map session = ActionContext.getContext().getSession();
    SysUser sessionUser = (SysUser) session.get("sysUser");
    if (topMenuId == null || topMenuId.equals("")) {
      topMenuId = sessionUser.getHeadMenuId() != null ? sessionUser.getHeadMenuId() : "-1";
    }
    try {
      SysMenuBean bean = new SysMenuBean();
      dataList = bean.getSubMenuListByUserId(sessionUser.getUserId(), Integer.valueOf(topMenuId));
    } catch (Exception e) {
      log.error(e);
      return ERROR;
    }
    return SUCCESS;
  }

  /**
   * 用户退出登录
   * 
   * @param
   * @return String
   * @exception
   */
  public String logoutSysUser() {
    Map session = ActionContext.getContext().getSession();
    session.clear();
    // log.warn("================== logout!!!");
    return SUCCESS;
  }

  /**
   * 分页列表
   * 
   * @param
   * @return String
   * @exception
   */
  public String listSysUser() throws Exception {
    // log.warn("================ in SysUserAction searchSysUserPage()");
    try {
      SysUserBean bean = new SysUserBean();
      this.page.setPageSize(this.getParamPageSize()); // 设置每页显示条数
      sysuser.setUserSt("1");
      Page returnPage = bean.searchPageByVo(this.getPage(), sysuser);
      log.warn("----------length=" + returnPage.getDataList().size());
      this.setPage(returnPage);
    } catch (Exception e) {
      log.error(e);
      return ERROR;
    }
    return SUCCESS;
  }

  /**
   * 新增
   * 
   * @param
   * @return String
   * @exception
   */
  public String save() throws Exception {
    // log.warn("================ in SysUserAction save() ");
    Map session = ActionContext.getContext().getSession();
    SysUser su = (SysUser) session.get("sysUser");
    try {
      SysUserBean bean = new SysUserBean();
      sysuser.setCreateUser(su.getUserId());
      bean.addSysUser(sysuser);
    } catch (Exception e) {
      log.error(e);
      return ERROR;
    }
    return listSysUser();
  }

  /**
   * 新增（员工同时新增员工角色关系）
   * 
   * @param
   * @return String
   * @exception
   */
  public String saveUserRole() throws Exception {
    // log.warn("================ in SysUserAction saveUserRole() ");
    Map session = ActionContext.getContext().getSession();
    SysUser su = (SysUser) session.get("sysUser");
    sysuser.setCreateUser(su.getUserId());
    sysuser.setUserPwd(MD5.md5crypt(sysuser.getUserPwd()));
    try {
      SysUserBean bean = new SysUserBean();
      bean.addSysUserRole(sysuser, roleListStr);
    } catch (Exception e) {
      log.error(e);
      return ERROR;
    }
    this.setRtnMessage("saveOk");
    sysuser = new SysUser();
    return listSysUser();
  }

  /**
   * 详细信息
   * 
   * @param
   * @return String
   * @exception
   */
  public String getDetail() throws Exception {
    // log.warn("================ in SysUserAction getDetail()");
    Integer userid = sysuser.getUserId();
    try {
      // 获取用户信息
      SysUserBean bean = new SysUserBean();
      this.sysuser = bean.getSysUserRoleDetail(userid);
      sysuser.setUserPwd("");
      // 获取部门信息
      SysDeptBean deptbean = new SysDeptBean();
      SysDept sysdept = deptbean.getSysDeptDetail(sysuser.getDeptId());
      this.deptName = "";
      if (sysdept != null && sysdept.getDeptName() != null) {
        this.deptName = sysdept.getDeptName();
      }
    } catch (Exception e) {
      log.error(e);
      return ERROR;
    }
    return SUCCESS;
  }

  /**
   * 获取操作员角色信息
   * 
   * @param
   * @return String
   * @exception
   */
  public String getSysUserRoleDetail() throws Exception {
    // log.warn("================ in SysUserAction getSysUserRoleDetail()");
    Integer userid = sysuser.getUserId();
    try {
      // 获取用户和角色信息
      SysUserBean bean = new SysUserBean();
      this.sysuser = bean.getSysUserRoleDetail(userid);
    } catch (Exception e) {
      log.error(e);
      return ERROR;
    }
    return SUCCESS;
  }

  /**
   * 修改
   * 
   * @param
   * @return String
   * @exception
   */
  public String doUpdate() throws Exception {
    // log.warn("================ in SysUserAction doUpdate() ");
    try {
      SysUserBean bean = new SysUserBean();
      // 修改客户资料
      sysuser = bean.updateSysUser(sysuser);
    } catch (Exception e) {
      log.error(e);
      return ERROR;
    }
    setRtnMessage("updateOK");
    return SUCCESS;
  }

  /**
   * 修改操作员角色
   * 
   * @param
   * @return String
   * @exception
   */
  public String doUpdateUserRole() throws Exception {
    // log.warn("================ in SysUserAction doUpdateUserRole() ");
    try {
      SysUserBean bean = new SysUserBean();
      // 修改客户资料

      // sysuser.setUserPwd(MD5.md5crypt(sysuser.getUserPwd()));
      newPwd1 = newPwd1.trim();
      if (newPwd1 != null && !newPwd1.equals("")) {
        sysuser.setUserPwd(MD5.md5crypt(newPwd1));
      }
      sysuser = bean.updateSysUserRole(sysuser, roleListStr);
      sysuser = bean.getSysUserRoleDetail(sysuser.getUserId());
    } catch (Exception e) {
      log.error(e);
      return ERROR;
    }
    sysuser = new SysUser();
    setRtnMessage("updateOK");
    return listSysUser();
  }

  /**
   * 查找sysuser内容，不包括部门和角色信息
   * 
   * @param
   * @return String
   * @exception
   */
  public String getSysUserDetailOnly() throws Exception {
    Integer userid = sysuser.getUserId();
    try {
      // 获取用户信息
      SysUserBean bean = new SysUserBean();
      this.sysuser = bean.getSysUserDetail(userid);
    } catch (Exception e) {
      log.error(e);
      return ERROR;
    }
    return SUCCESS;
  }

  /**
   * 查找sysuser内容,包括部门和角色信息
   * 
   * @param
   * @return String
   * @exception
   */
  public String getSysUserDetail() throws Exception {
    Integer userid = sysuser.getUserId();
    try {
      // 获取用户信息,包括角色
      SysUserBean bean = new SysUserBean();
      this.sysuser = bean.getSysUserRoleDetail(userid);
      // 获取部门信息
      SysDeptBean deptBean = new SysDeptBean();
      this.deptName = deptBean.getSysDeptDetail(sysuser.getDeptId()).getDeptName();

    } catch (Exception e) {
      log.error(e);
      return ERROR;
    }
    return SUCCESS;
  }

  /**
   * 修改密码
   * 
   * @param
   * @return String
   * @exception
   */
  public String doSysUserPwdModify() throws Exception {
    Integer userid = sysuser.getUserId();
    try {
      SysUserBean bean = new SysUserBean();
      SysUser // su = new SysUser();
      su = bean.getSysUserDetail(sysuser.getUserId());
      if ((su != null) && (su.getUserPwd().equals(MD5.md5crypt(sysuser.getUserPwd())))) {
        sysuser.setUserPwd(MD5.md5crypt(newPwd1));
        bean.updateSysUser(sysuser); // 修改密码
        setRtnMessage("updateOK");
      } else {
        setRtnMessage("updateFalse");
      }
      this.sysuser = bean.getSysUserDetail(userid);
    } catch (Exception e) {
      log.error(e);
      return ERROR;
    }
    return SUCCESS;
  }

  /**
   * 修改我的资料
   * 
   * @param
   * @return String
   * @exception
   */
  public String doUpdateMyInfo() throws Exception {
    // log.warn("================ in SysUserAction doUpdateMyInfo() ");
    try {
      SysUserBean bean = new SysUserBean();
      // 修改客户资料
      sysuser = bean.updateSysUser(sysuser);
    } catch (Exception e) {
      log.error(e);
      return ERROR;
    }
    setRtnMessage("updateOK");
    return getDetail();
  }

  /**
   * 删除所选操作员
   * 
   * @param
   * @return String
   * @exception
   */
  public String deleteSysuser() throws Exception {
    try {
      SysUserBean bean = new SysUserBean();
      SysUser user;
      if (delId != null && delId.length > 0) {
        user = bean.getSysUserDetail(Integer.valueOf(delId[0]));
        bean.delSysUserRole(user);
      }

      bean.deleteSysUser(delId);
    } catch (Exception e) {
      log.error(e);
      return ERROR;
    }
    this.setRtnMessage("deleteOK");
    return this.listSysUser();
  }

  /**
   * 弹出选人页面（准备所有部门数据）
   * 
   * @param
   * @return String
   * @exception
   */
  public String popSearchUser() throws Exception {
    log.warn("================ in SysUserAction popSearchUser()");
    try {
      // 查询所有部门
      SysDeptBean bean = new SysDeptBean();
      dataList = bean.getAllDeptList();
    } catch (Exception e) {
      log.error(e);
      log.warn(e.getMessage());
      return ERROR;
    }
    return SUCCESS;
  }

  /**
   * 获取所有部门
   * 
   * @param
   * @return String
   * @exception
   */
  public String ajaxGetUsersByDept() throws Exception {
    log.warn("================ in SysUserAction ajaxGetUsersByDept()");
    SysUser vo = new SysUser();
    vo.setDeptId(sysuser.getDeptId());
    log.warn("------------ deptId=" + sysuser.getDeptId());
    try {
      SysUserBean bean = new SysUserBean();
      dataList = bean.searchUserListByDept(vo);
    } catch (Exception e) {
      log.error(e);
      log.warn(e.getMessage());
      return ERROR;
    }
    return SUCCESS;
  }

  /**
   * getter and setter
   * 
   */

  public String getRtnMessage() {
    return rtnMessage;
  }

  public void setRtnMessage(String rtnMessage) {
    this.rtnMessage = rtnMessage;
  }

  @Override
public Page getPage() {
    return page;
  }

  @Override
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

  public List getDataList() {
    return dataList;
  }

  public void setDataList(List dataList) {
    this.dataList = dataList;
  }

  public String getDeptName() {
    return deptName;
  }

  public void setDeptName(String deptName) {
    this.deptName = deptName;
  }

  public String getRoleListStr() {
    return roleListStr;
  }

  public void setRoleListStr(String roleListStr) {
    this.roleListStr = roleListStr;
  }

  public String getTopMenuId() {
    return topMenuId;
  }

  public void setTopMenuId(String topMenuId) {
    this.topMenuId = topMenuId;
  }

  public String getTopMenuName() {
    return topMenuName;
  }

  public void setTopMenuName(String topMenuName) {
    this.topMenuName = topMenuName;
  }

  public String getNewPwd1() {
    return newPwd1;
  }

  public void setNewPwd1(String newPwd1) {
    this.newPwd1 = newPwd1;
  }

  public String getUserIdStr() {
    return userIdStr;
  }

  public void setUserIdStr(String userIdStr) {
    this.userIdStr = userIdStr;
  }

  public String getUserNameStr() {
    return userNameStr;
  }

  public void setUserNameStr(String userNameStr) {
    this.userNameStr = userNameStr;
  }

  /**
   * struts2 Action default and model driven method
   * 
   */
  public String doDefault() throws Exception {
    return INPUT;
  }

  @Override
public Object getModel() {
    return sysuser;
  }
}
