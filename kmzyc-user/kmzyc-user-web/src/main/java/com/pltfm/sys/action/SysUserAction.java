package com.pltfm.sys.action;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.jasig.cas.client.util.AssertionHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kmzyc.commons.page.Page;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;
import com.pltfm.app.util.ConfigureUtils;
import com.pltfm.app.util.MD5;
import com.pltfm.sys.model.SysDept;
import com.pltfm.sys.model.SysMenu;
import com.pltfm.sys.model.SysUser;
import com.pltfm.sys.service.SysDeptService;
import com.pltfm.sys.service.SysMenuService;
import com.pltfm.sys.service.SysRoleService;
import com.pltfm.sys.service.SysUserService;
import com.pltfm.sys.service.impl.SysDeptServiceImpl;
import com.pltfm.sys.util.XiuSystemConstant;

/**
 * 类 SysUserAction 操作员Action类
 * 
 * @author
 * @version 2.1
 * @since JDK1.5
 */

@SuppressWarnings({"rawtypes", "serial"})
public class SysUserAction extends BaseAction implements ModelDriven {
  @Resource(name = "sysMenuService")
  private SysMenuService sysMenuService;
  @Resource(name = "sysRoleService")
  private SysRoleService sysRoleService;

  @Resource(name = "sysUserService")
  private SysUserService sysUserService;
  @Resource(name = "sysDeptService")
  private SysDeptService sysDeptService;
  @Resource
  private SysDeptServiceImpl sysDeptServiceImpl;

  Logger log = LoggerFactory.getLogger(this.getClass());
  private SysUser sysuser = new SysUser(); // model
  String rtnMessage;

  Page page = new Page(); // 分页变量
  int paramPageSize = XiuSystemConstant.PARAM_PAGE_SIZE; // 接受页面中传过来的每页显示条数的参数
  String[] delId; // 删除操作id

  private List dataList;
  private String deptName;
  private SysDept sysdept;
  String roleListStr;
  String topMenuId;
  String topMenuName;
  String newPwd1;
  String userIdStr;


  String userNameStr;
  private Map<SysMenu, List<SysMenu>> dataMap = new LinkedHashMap<SysMenu, List<SysMenu>>();

  public Map<SysMenu, List<SysMenu>> getDataMap() {
    return dataMap;
  }


  private List deptList;

  public void setDataMap(Map<SysMenu, List<SysMenu>> dataMap) {
    this.dataMap = dataMap;
  }

  /**
   * 操作员登录 update by lijianjun
   * 
   * @param @return String @exception
   */
  @SuppressWarnings({"unchecked"})
  public String loginSysUser() {
    String loginName = AssertionHolder.getAssertion().getPrincipal().getName();
    log.info("------------------------------------loginName：" + loginName);
    String topMenuId = this.httpServletRequest.getParameter("topMenuId");
    if (loginName == null) {
      rtnMessage = "login";
      return "login";
    }
    try {
      // 获取登录人基本信息
      sysuser.setUserName(loginName);
      sysuser = sysUserService.sysUserLogin(sysuser);
      if (topMenuId != null) sysuser.setHeadMenuId(topMenuId);
      if (sysuser == null) {
        rtnMessage = "loginError";
        return "loginError";
      }
    } catch (Exception e) {
      log.error("登陆异常" + e.getMessage(), e);
      return ERROR;
    }
    // 设置session
    Map session = ActionContext.getContext().getSession();
    if (session.get("sysUser") != null) {
      session.clear();
    }
    session.put("sysUser", sysuser);
    // 设置登陆状态
    rtnMessage = "loginOk";
    return SUCCESS;
  }

  /**
   * 获取当前登陆用户权限内头部Menu信息 update by lijianjun
   * 
   * @param @return String @exception
   */
  public String getSysTop() {
    Map session = ActionContext.getContext().getSession();
    SysUser sessionUser = (SysUser) session.get("sysUser");
    try {
      // 获取登录人菜单权限信息
      dataList = sysMenuService.getUpMenuListByUserId(sessionUser.getUserId());
    } catch (Exception e) {
      log.error("获取当前用户权限头部菜单信息失败" + e.getMessage(), e);
      return ERROR;
    }
    return SUCCESS;
  }

  /**
   * 点击header菜单刷新左侧菜单 update by lijainjun
   * 
   * @param @return String @exception
   */
  /*
   * @SuppressWarnings({ "rawtypes", "unchecked"}) public String changeSysHeaderMenu() { Map session
   * = ActionContext.getContext().getSession(); try { //获取登陆缓存用户 SysUser sessionUser = (SysUser)
   * session.get("sysUser"); sessionUser.setHeadMenuId(topMenuId); session.put("sysUser",
   * sessionUser); SysMenu sMenu = null; sMenu =
   * sysMenuService.getMenuById(Integer.valueOf(topMenuId)); topMenuName = ""; if (sMenu != null) {
   * topMenuName = sMenu.getMenuName(); } } catch (Exception e) {
   * log.error("刷新左侧菜单失败"+e.getMessage(), e); return "login"; } return SUCCESS; }
   */

  /**
   * 加载获取左侧导航菜单 update by lijianjun
   * 
   * @param @return String @exception
   */
  public String getSysLeftMenu() {
    Map session = ActionContext.getContext().getSession();
    SysUser sessionUser = (SysUser) session.get("sysUser");
    if(null == sessionUser){
        log.error("记载左侧菜单类目失败,sessionUser为空!");
        return ERROR; 
    }
    // 获取头菜单序号
    if (StringUtils.isBlank(topMenuId)) {
      topMenuId = sessionUser.getHeadMenuId() != null ? sessionUser.getHeadMenuId() : "-1";
    }
    try {
      // 获取当前登陆用户有权限的左侧菜单列表
      dataList = sysMenuService.getSubMenuListByUserId(sessionUser.getUserId(),
          Integer.valueOf(topMenuId));
      Integer userId = Integer.valueOf("0");
      if (sessionUser != null) {
        userId = sessionUser.getUserId();
      }
      if (dataList != null && dataList.size() > 0) {
        for (int i = 0; i < dataList.size(); i++) {
          SysMenu sysMenu = (SysMenu) dataList.get(i);

          String headMenuId = sysMenu.getMenuId().toString();

          // 获取左边菜单的子菜单目录
          List<SysMenu> subList = sysMenuService.getSubMenuListByUserId(userId,
              Integer.valueOf(headMenuId));
          dataMap.put(sysMenu, subList);
        }
      }
    } catch (Exception e) {
      log.error("记载左侧菜单类目失败" + e.getMessage(), e);
      return ERROR;
    }
    return SUCCESS;
  }

  /**
   * 用户退出登录
   * 
   * @param @return String @exception
   */

  public String logoutSysUser() {
    Map session = ActionContext.getContext().getSession();
    session.clear();
    return SUCCESS;
  }

  /**
   * 操作员分页列表
   * 
   * @param @return String @exception
   */
  public String listSysUser() throws Exception {
    try {
      this.page.setPageSize(this.getParamPageSize()); // 设置每页显示条数
      sysuser.setUserSt("1");
      Page returnPage = sysUserService.searchPageByVo(this.getPage(), sysuser);
      this.setPage(returnPage);
      // 获取部门
      deptList = sysDeptService.getSysDeptList(new SysDept());
    } catch (Exception e) {
      log.error("初始化操作员列表失败" + e.getMessage(), e);
      return ERROR;
    }
    return SUCCESS;
  }

  /**
   * 新增
   * 
   * @param @return String @exception
   */
  /*
   * public String save() throws Exception { // log.warn("================ in SysUserAction save() "
   * ); Map session = ActionContext.getContext().getSession(); SysUser su = (SysUser)
   * session.get("sysUser"); try { sysuser.setCreateUser(su.getUserId());
   * sysUserService.addSysUser(sysuser); } catch (Exception e) { e.printStackTrace(); return ERROR;
   * } return listSysUser(); }
   */

  /**
   * 新增操作员提交（员工同时新增员工角色关系）
   * 
   * @param @return String @exception
   */

  public String saveUserRole() throws Exception {
    Map session = ActionContext.getContext().getSession();
    SysUser su = (SysUser) session.get("sysUser");
    sysuser.setCreateUser(su.getUserId());
    sysuser.setUserPwd(MD5.md5crypt(sysuser.getUserPwd()));
    try {
      sysUserService.addSysUserRole(sysuser, roleListStr);
      this.addActionMessage(ConfigureUtils.getMessageConfig("sysUser.save.success"));
    } catch (Exception e) {
      log.error("新增操作员失败" + e.getMessage(), e);
      return ERROR;
    }
    sysuser = new SysUser();
    return listSysUser();
  }

  /**
   * 初始化操作员详细信息
   * 
   * @param @return String @exception
   */
  public String getDetail() throws Exception {
    Integer userid = sysuser.getUserId();
    try {
      // 获取用户信息
      this.sysuser = sysUserService.getSysUserRoleDetail(userid);
      sysuser.setUserPwd("");
      // 获取部门信息
      sysdept = sysDeptServiceImpl.getSysDeptDetail(sysuser.getDeptId());
    } catch (Exception e) {
      log.error("获取操作员信息失败" + e.getMessage(), e);
      return ERROR;
    }
    return SUCCESS;
  }

  /**
   * 获取操作员角色信息
   * 
   * @param @return String @exception
   */
  /*
   * public String getSysUserRoleDetail() throws Exception { // log.warn(
   * "================ in SysUserAction getSysUserRoleDetail()"); Integer userid =
   * sysuser.getUserId(); try { // 获取用户和角色信息 this.sysuser =
   * sysUserService.getSysUserRoleDetail(userid); } catch (Exception e) { e.printStackTrace();
   * return ERROR; } return SUCCESS; }
   */

  /**
   * 修改
   * 
   * @param @return String @exception
   */
  /*
   * public String doUpdate() throws Exception { // log.warn(
   * "================ in SysUserAction doUpdate() "); try { // 修改客户资料 sysuser =
   * sysUserService.updateSysUser(sysuser); } catch (Exception e) { e.printStackTrace(); return
   * ERROR; } setRtnMessage("updateOK"); return SUCCESS; }
   */
  /**
   * 修改操作员提交
   * 
   * @param @return String @exception
   */
  public String doUpdateUserRole() throws Exception {
    try {
      newPwd1 = newPwd1.trim();
      if (newPwd1 != null && !newPwd1.equals("")) {
        sysuser.setUserPwd(MD5.md5crypt(newPwd1));
      }
      sysuser = sysUserService.updateSysUserRole(sysuser, roleListStr);
      sysuser = sysUserService.getSysUserRoleDetail(sysuser.getUserId());
      this.addActionMessage(ConfigureUtils.getMessageConfig("sysUser.update.success"));
    } catch (Exception e) {
      log.error("修改操作员信息失败" + e.getMessage(), e);
      return ERROR;
    }
    sysuser = new SysUser();
    return listSysUser();
  }

  /**
   * 头部查找sysuser内容，不包括部门和角色信息
   * 
   * @param @return String @exception
   */
  public String getSysUserDetailOnly() throws Exception {
    Integer userid = sysuser.getUserId();
    try {
      // 获取用户信息
      this.sysuser = sysUserService.getSysUserDetail(userid);
    } catch (Exception e) {
      log.error("获取用户登录信息失败" + e.getMessage(), e);
      return ERROR;
    }
    return SUCCESS;
  }

  /**
   * 头部查找sysuser内容,包括部门和角色信息
   * 
   * @param @return String @exception
   */
  public String getSysUserDetail() throws Exception {
    Integer userid = sysuser.getUserId();
    try {
      // 获取用户信息,包括角色
      this.sysuser = sysUserService.getSysUserRoleDetail(userid);
      // 获取部门信息
      this.deptName = sysDeptServiceImpl.getSysDeptDetail(sysuser.getDeptId()).getDeptName();
    } catch (Exception e) {
      log.error("获取操作员信息及其部门失败" + e.getMessage(), e);
      return ERROR;
    }
    return SUCCESS;
  }

  /**
   * 头部修改密码
   * 
   * @param @return String @exception
   */
  public String doSysUserPwdModify() throws Exception {
    Integer userid = sysuser.getUserId();
    try {
      SysUser su = sysUserService.getSysUserDetail(sysuser.getUserId());
      if ((su != null) && (su.getUserPwd().equals(MD5.md5crypt(sysuser.getUserPwd())))) {
        sysuser.setUserPwd(MD5.md5crypt(newPwd1));
        sysUserService.updateSysUser(sysuser); // 修改密码
        this.addActionMessage(ConfigureUtils.getMessageConfig("sysUser.updatePwd.success"));
        setRtnMessage("updateOK");
      } else {
        this.addActionMessage(ConfigureUtils.getMessageConfig("sysUser.updatePwd.False"));
        setRtnMessage("updateFalse");
      }
      this.sysuser = sysUserService.getSysUserDetail(userid);
    } catch (Exception e) {
      log.error("密码修改异常" + e.getMessage(), e);
      return ERROR;
    }
    return SUCCESS;
  }

  /**
   * 修改我的资料
   * 
   * @param @return String @exception
   */
  public String doUpdateMyInfo() throws Exception {
    try {
      // 修改客户资料
      sysuser = sysUserService.updateSysUser(sysuser);
    } catch (Exception e) {
      log.error("资料修改异常" + e.getMessage(), e);
      return ERROR;
    }
    setRtnMessage("updateOK");
    return getDetail();
  }

  /**
   * 删除所选操作员
   * 
   * @param @return String @exception
   */
  public String deleteSysuser() throws Exception {
    try {
      SysUser user;
      if (delId != null && delId.length > 0) {
        user = sysUserService.getSysUserDetail(Integer.valueOf(delId[0]));
        sysUserService.delSysUserRole(user);
      }
      sysUserService.deleteSysUser(delId);
      this.addActionMessage(ConfigureUtils.getMessageConfig("sysUser.delete.success"));
    } catch (Exception e) {
      log.error("删除操作员失败" + e.getMessage(), e);
      return ERROR;
    }
    return this.listSysUser();
  }

  /**
   * 弹出选人页面（准备所有部门数据）选择操作员
   * 
   * @param @return String @exception
   */
  public String popSearchUser() throws Exception {
    try {
      // 查询所有部门
      dataList = sysDeptServiceImpl.getAllDeptList();
    } catch (Exception e) {
      log.error("初始化操作员选择失败" + e.getMessage(), e);
      return ERROR;
    }
    return SUCCESS;
  }

  /**
   * ajax获取所有部门
   * 
   * @param @return String @exception
   */
  public String ajaxGetUsersByDept() throws Exception {
    SysUser vo = new SysUser();
    vo.setDeptId(sysuser.getDeptId());
    try {
      dataList = sysUserService.searchUserListByDept(vo);
    } catch (Exception e) {
      log.error("ajax获取部门信息异常" + e.getMessage(), e);
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



  public List getDeptList() {
    return deptList;
  }


  public void setDeptList(List deptList) {
    this.deptList = deptList;
  }

  public SysUser getSysuser() {
    return sysuser;
  }

  public void setSysuser(SysUser sysuser) {
    this.sysuser = sysuser;
  }


  public SysDept getSysdept() {
    return sysdept;
  }

  public void setSysdept(SysDept sysdept) {
    this.sysdept = sysdept;
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
