package com.pltfm.sys.action;

import com.kmzyc.zkconfig.ConfigurationUtil;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.pltfm.app.util.MD5;
import com.pltfm.cms.dao.CmsUserSiteDAO;
import com.pltfm.cms.service.CmsSiteService;
import com.pltfm.cms.service.CmsUserSiteService;
import com.pltfm.cms.vobject.CmsUserSite;
import com.pltfm.sys.bean.SysDeptBean;
import com.pltfm.sys.bean.SysMenuBean;
import com.pltfm.sys.bean.SysUserBean;
import com.pltfm.sys.model.SysDept;
import com.pltfm.sys.model.SysMenu;
import com.pltfm.sys.model.SysUser;
import com.kmzyc.commons.page.Page;
import com.pltfm.sys.util.XiuSystemConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.StrutsStatics;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 类 SysUserAction 操作员Action类
 *
 * @author
 * @version 2.1
 * @since JDK1.5
 */
public class SysUserAction extends ActionSupport implements ModelDriven {
	private static Logger logger = LoggerFactory.getLogger(SysUserAction.class);
    private SysUser sysuser = new SysUser();                        // model
    String rtnMessage;
    Integer siteId;
    @Resource(name = "cmsUserSiteDAO")
    private CmsUserSiteDAO cmsUserSiteDAO;
    @Resource(name = "cmsUserSiteService")
    private CmsUserSiteService cmsUserSiteService;

    @Resource(name = "cmsSiteService")
    private CmsSiteService cmsSiteService;
    /**
     * 登录页面
     */
    private String loginSysPath;

    Page page = new Page();                            // 分页变量
    int paramPageSize = XiuSystemConstant.PARAM_PAGE_SIZE;    // 接受页面中传过来的每页显示条数的参数
    String[] delId;                                                    // 删除操作id
    List dataList;
    String deptName;
    String roleListStr;
    String topMenuId;
    String topMenuName;
    String newPwd1;
    String userIdStr;
    String userNameStr;
    List<CmsUserSite> cmsUserSiteList;                                        // 站点用户集合

    /**
     * 操作员登录
     *
     * @return String
     */
    public String loginSysUser() {

        Subject currUser = SecurityUtils.getSubject();
        Object principal = currUser.getPrincipal();
        String loginName = null;
        if (null != principal) {
            loginName = principal.toString();
        }

        if (null != loginName) {
            currUser.getSession().setAttribute("loginName", loginName);
        } else {
            Object o = currUser.getSession().getAttribute("loginName");
            if (null != o) loginName = o.toString();
        }
        loginSysPath = ConfigurationUtil.getString("shiro.loginUrl");
        logger.warn("===================------------------------------------loginName-----"
                + loginName);
        if (loginName == null) {
            rtnMessage = "login";
            return "login";
        }
        try {
            // 获取登录人基本信息
            SysUserBean bean = SysUserBean.instance();
            sysuser.setUserName(loginName);
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
        	logger.error("SysUserAction.loginSysUser异常：" + e.getMessage(), e);
            return ERROR;
        }
        // 设置session
        // Map session = ActionContext.getContext().getSession();

        // if (currUser.getSession().getAttribute("sysUser") != null)
        // {
        // currUser.getSession().clear();
        // }

        HttpServletRequest request = (HttpServletRequest) ActionContext
                .getContext().get(StrutsStatics.HTTP_REQUEST);
        String topMenuId = request.getParameter("topMenuId");
        if (topMenuId != null) sysuser.setHeadMenuId(topMenuId);
        currUser.getSession().setAttribute("sysUser", sysuser);
        currUser.getSession().removeAttribute("siteId");
        if (siteId == null) siteId = 1;
        currUser.getSession().setAttribute("siteId", siteId);
        currUser.getSession().setAttribute("sysUser", sysuser);
        try {
            List list = cmsUserSiteDAO.selectByUserId(sysuser.getUserId());
            /*
             * CmsUserSite cms_user_site=(CmsUserSite)list.get(0);
			 * System.out.println(cms_user_site.toString());
			 */
            currUser.getSession().setAttribute("UserSiteList", list);
        } catch (SQLException e) {
        	logger.error("SysUserAction.loginSysUser异常：" + e.getMessage(), e);
        }

        rtnMessage = "loginOk";
        return SUCCESS;
    }

    /**
     * 获取用户权限内头部Menu信息
     *
     * @return String
     */
    public String getSysTop() {
        Map session = ActionContext.getContext().getSession();
        SysUser sessionUser = (SysUser) session.get("sysUser");

        try {
            // 获取登录人菜单权限信息
            SysMenuBean mbean = SysMenuBean.instance();

            dataList = mbean.getUpMenuListByUserId(sessionUser.getUserId());

        } catch (Exception e) {
        	logger.error("SysUserAction.getSysTop异常：" + e.getMessage(), e);
            return ERROR;
        }
        return SUCCESS;
    }

    /**
     * 点击header菜单刷新左侧菜单
     *
     * @return String
     */
    public String changeSysHeaderMenu() {
        Map session = ActionContext.getContext().getSession();
        try {
            SysUser sessionUser = (SysUser) session.get("sysUser");
            sessionUser.setHeadMenuId(topMenuId);

            session.put("sysUser", sessionUser);
            session.remove("siteId");
            session.put("siteId", siteId);
            SysMenu sMenu = null;

            SysMenuBean bean = SysMenuBean.instance();
            sMenu = bean.getMenuById(Integer.valueOf(topMenuId));
            topMenuName = "";
            if (sMenu != null) {
                topMenuName = sMenu.getMenuName();
            }

        } catch (Exception e) {
        	logger.error("SysUserAction.changeSysHeaderMenu异常：" + e.getMessage(), e);
            return "login";
        }
        return SUCCESS;
    }

    /**
     * 获取左侧导航菜单
     *
     * @return String
     */
    public String getSysLeftMenu() {
        Map session = ActionContext.getContext().getSession();
        SysUser sessionUser = (SysUser) session.get("sysUser");
        if (topMenuId == null || topMenuId.equals("")) {
            topMenuId = sessionUser.getHeadMenuId() != null ? sessionUser
                    .getHeadMenuId() : "-1";
        }
        try {
            SysMenuBean bean = SysMenuBean.instance();
            dataList = bean.getSubMenuListByUserId(sessionUser.getUserId(),
                    Integer.valueOf(topMenuId));
        } catch (Exception e) {
        	logger.error("SysUserAction.getSysLeftMenu异常：" + e.getMessage(), e);
            return ERROR;
        }
        return SUCCESS;
    }

    /**
     * 用户退出登录
     *
     * @return String
     */
    public String logoutSysUser() {
        Map session = ActionContext.getContext().getSession();
        session.clear();
        HttpServletRequest request = (HttpServletRequest) ActionContext
                .getContext().get(StrutsStatics.HTTP_REQUEST);
        request.getSession().removeAttribute("sysUser");
        // log.warn("================== logout!!!");
        return SUCCESS;
    }

    /**
     * 分页列表
     *
     * @return String
     */
    public String listSysUser() throws Exception {
        // log.warn("================ in SysUserAction searchSysUserPage()");
        try {
            SysUserBean bean = SysUserBean.instance();
            this.page.setPageSize(this.getParamPageSize()); // 设置每页显示条数
            sysuser.setUserSt("1");
            Page returnPage = bean.searchPageByVo(this.getPage(), sysuser);
            logger.warn("----------length=" + returnPage.getDataList().size());
            this.setPage(returnPage);
        } catch (Exception e) {
        	logger.error("SysUserAction.listSysUser异常：" + e.getMessage(), e);
            return ERROR;
        }
        return SUCCESS;
    }

    /**
     * 站点选择用户的分页列表
     *
     * @return String
     */
    public String listSysUserPop() throws Exception {
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            if (request.getParameter("pageNo1") != null) {
                if (Integer
                        .parseInt(request.getParameter("pageNo1").toString()) == 1) {
                    this.page.setPageNo(1);
                }
            }
            SysUserBean bean = SysUserBean.instance();
            this.page.setPageSize(this.getParamPageSize()); // 设置每页显示条数
            sysuser.setUserSt("1");
            cmsUserSiteList = cmsUserSiteService.selUse(siteId);
            Page returnPage = bean.searchPageByVo(this.getPage(), sysuser);
            for (int j = 0; j < returnPage.getDataList().size(); j++) {
                SysUser sys = (SysUser) returnPage.getDataList().get(j);
                for (int i = 0; i < cmsUserSiteList.size(); i++) {
                    CmsUserSite c = cmsUserSiteList.get(i);

                    if (c.getUserId().equals(sys.getUserId())) {

                        sys.setFlagUser(1);

                        break;
                    }

                }

            }

            this.setPage(returnPage);
        } catch (Exception e) {
        	logger.error("SysUserAction.listSysUserPop异常：" + e.getMessage(), e);
            return ERROR;
        }
        return SUCCESS;
    }

    /**
     * 新增
     *
     * @return String
     */
    public String save() throws Exception {
        // log.warn("================ in SysUserAction save() ");
        Map session = ActionContext.getContext().getSession();
        SysUser su = (SysUser) session.get("sysUser");
        try {
            SysUserBean bean = SysUserBean.instance();
            sysuser.setCreateUser(su.getUserId());
            bean.addSysUser(sysuser);
        } catch (Exception e) {
        	logger.error("SysUserAction.save异常：" + e.getMessage(), e);
            return ERROR;
        }
        return listSysUser();
    }

    /**
     * 新增（员工同时新增员工角色关系）
     *
     * @return String
     */
    public String saveUserRole() throws Exception {
        // log.warn("================ in SysUserAction saveUserRole() ");
        Map session = ActionContext.getContext().getSession();
        SysUser su = (SysUser) session.get("sysUser");
        sysuser.setCreateUser(su.getUserId());
        sysuser.setUserPwd(MD5.md5crypt(sysuser.getUserPwd()));
        try {
            SysUserBean bean = SysUserBean.instance();

            bean.addSysUserRole(sysuser, roleListStr);
        } catch (Exception e) {
        	logger.error("SysUserAction.saveUserRole异常：" + e.getMessage(), e);
            return ERROR;
        }
        this.setRtnMessage("saveOk");
        sysuser = new SysUser();
        return listSysUser();
    }

    /**
     * 详细信息
     *
     * @return String
     */
    public String getDetail() throws Exception {
        // log.warn("================ in SysUserAction getDetail()");
        Integer userid = sysuser.getUserId();
        try {

            // 获取用户信息
            SysUserBean bean = SysUserBean.instance();
            this.sysuser = bean.getSysUserRoleDetail(userid);
            // 获取部门信息
            SysDeptBean deptbean = SysDeptBean.instance();
            SysDept sysdept = deptbean.getSysDeptDetail(sysuser.getDeptId());
            this.deptName = "";
            if (sysdept != null && sysdept.getDeptName() != null) {
                this.deptName = sysdept.getDeptName();
            }

        } catch (Exception e) {
        	logger.error("SysUserAction.getDetail异常：" + e.getMessage(), e);
            return ERROR;
        }
        return SUCCESS;
    }

    /**
     * 获取操作员角色信息
     *
     * @return String
     */
    public String getSysUserRoleDetail() throws Exception {
        // log.warn("================ in SysUserAction getSysUserRoleDetail()");
        Integer userid = sysuser.getUserId();
        try {
            // 获取用户和角色信息
            SysUserBean bean = SysUserBean.instance();
            this.sysuser = bean.getSysUserRoleDetail(userid);

        } catch (Exception e) {
        	logger.error("SysUserAction.getSysUserRoleDetail异常：" + e.getMessage(), e);
            return ERROR;
        }
        return SUCCESS;
    }

    /**
     * 修改
     *
     * @return String
     */
    public String doUpdate() throws Exception {
        // log.warn("================ in SysUserAction doUpdate() ");
        try {
            SysUserBean bean = SysUserBean.instance();
            // 修改客户资料
            sysuser = bean.updateSysUser(sysuser);
        } catch (Exception e) {
        	logger.error("SysUserAction.doUpdate异常：" + e.getMessage(), e);
            return ERROR;
        }
        setRtnMessage("updateOK");
        return SUCCESS;
    }

    /**
     * 修改操作员角色
     *
     * @return String
     */
    public String doUpdateUserRole() throws Exception {
        // log.warn("================ in SysUserAction doUpdateUserRole() ");
        try {
            SysUserBean bean = SysUserBean.instance();
            // 修改客户资料
            newPwd1 = newPwd1.trim();
            if (newPwd1 != null && !newPwd1.equals("")) {
                sysuser.setUserPwd(MD5.md5crypt(newPwd1));
            }
            sysuser = bean.updateSysUserRole(sysuser, roleListStr);
            sysuser = bean.getSysUserRoleDetail(sysuser.getUserId());
        } catch (Exception e) {
        	logger.error("SysUserAction.doUpdateUserRole异常：" + e.getMessage(), e);
            return ERROR;
        }
        sysuser = new SysUser();
        setRtnMessage("updateOK");
        return listSysUser();
    }

    /**
     * 查找sysuser内容，不包括部门和角色信息
     *
     * @return String
     */
    public String getSysUserDetailOnly() throws Exception {
        Integer userid = sysuser.getUserId();
        try {
            // 获取用户信息
            SysUserBean bean = SysUserBean.instance();
            this.sysuser = bean.getSysUserDetail(userid);
        } catch (Exception e) {
        	logger.error("SysUserAction.getSysUserDetailOnly异常：" + e.getMessage(), e);
            return ERROR;
        }
        return SUCCESS;
    }

    /**
     * 查找sysuser内容,包括部门和角色信息
     *
     * @return String
     */
    public String getSysUserDetail() throws Exception {
        Integer userid = sysuser.getUserId();
        try {
            // 获取用户信息,包括角色
            SysUserBean bean = SysUserBean.instance();
            this.sysuser = bean.getSysUserRoleDetail(userid);
            // 获取部门信息
            SysDeptBean deptBean = SysDeptBean.instance();
            this.deptName = deptBean.getSysDeptDetail(sysuser.getDeptId())
                    .getDeptName();

        } catch (Exception e) {
        	logger.error("SysUserAction.getSysUserDetail异常：" + e.getMessage(), e);
            return ERROR;
        }
        return SUCCESS;
    }

    /**
     * 修改密码
     *
     * @return String
     */
    public String doSysUserPwdModify() throws Exception {
        Integer userid = sysuser.getUserId();
        try {
            SysUserBean bean = SysUserBean.instance();
            SysUser su = bean.getSysUserDetail(sysuser.getUserId());
            // MD5.md5crypt(sysuser.getUserPwd())
            if ((su != null)
                    && (su.getUserPwd().equals(MD5.md5crypt(sysuser
                    .getUserPwd())))) {
                sysuser.setUserPwd(MD5.md5crypt(newPwd1));
                bean.updateSysUser(sysuser); // 修改密码
                setRtnMessage("updateOK");
            } else {
                setRtnMessage("updateFalse");
            }
            this.sysuser = bean.getSysUserDetail(userid);
        } catch (Exception e) {
        	logger.error("SysUserAction.doSysUserPwdModify异常：" + e.getMessage(), e);
            return ERROR;
        }
        return SUCCESS;
    }

    /**
     * 修改我的资料
     *
     * @return String
     */
    public String doUpdateMyInfo() throws Exception {
        // log.warn("================ in SysUserAction doUpdateMyInfo() ");
        try {
            SysUserBean bean = SysUserBean.instance();
            // 修改客户资料
            sysuser = bean.updateSysUser(sysuser);
        } catch (Exception e) {
        	logger.error("SysUserAction.doUpdateMyInfo异常：" + e.getMessage(), e);
            return ERROR;
        }
        setRtnMessage("updateOK");
        return getDetail();
    }

    /**
     * 删除所选操作员
     *
     * @return String
     */
    public String deleteSysuser() throws Exception {
        try {
            SysUserBean bean = SysUserBean.instance();
            SysUser user;
            if (delId != null && delId.length > 0) {
                user = bean.getSysUserDetail(Integer.valueOf(delId[0]));
                bean.delSysUserRole(user);
            }

            bean.deleteSysUser(delId);
        } catch (Exception e) {
        	logger.error("SysUserAction.deleteSysuser异常：" + e.getMessage(), e);
            return ERROR;
        }
        this.setRtnMessage("deleteOK");
        return this.listSysUser();
    }

    /**
     * 弹出选人页面（准备所有部门数据）
     *
     * @return String
     */
    public String popSearchUser() throws Exception {
    	logger.warn("================ in SysUserAction popSearchUser()");
        try {
            // 查询所有部门
            SysDeptBean bean = SysDeptBean.instance();
            dataList = bean.getAllDeptList();
        } catch (Exception e) {
        	logger.error("SysUserAction.popSearchUser异常：" + e.getMessage(), e);
            return ERROR;
        }
        return SUCCESS;
    }

    /**
     * 获取所有部门
     *
     * @return String
     */
    public String ajaxGetUsersByDept() throws Exception {
    	logger.warn("================ in SysUserAction ajaxGetUsersByDept()");
        SysUser vo = new SysUser();
        vo.setDeptId(sysuser.getDeptId());
        logger.warn("------------ deptId=" + sysuser.getDeptId());
        try {
            SysUserBean bean = SysUserBean.instance();
            dataList = bean.searchUserListByDept(vo);
        } catch (Exception e) {
        	logger.error("SysUserAction.ajaxGetUsersByDept异常：" + e.getMessage(), e);
            return ERROR;
        }
        return SUCCESS;
    }

    public String b2bAuthen() {
        try {
            Subject currUser = SecurityUtils.getSubject();
            // 对用户名和密码进行验证
            UsernamePasswordToken token = new UsernamePasswordToken();
            String username = ServletActionContext.getRequest().getParameter(
                    "username");
            String password = ServletActionContext.getRequest().getParameter(
                    "password");
            token.setPassword(password.toCharArray());
            token.setUsername(username);
            currUser.login(token);
            if (currUser.isAuthenticated()) { // 认证通过，重定向到静态资源
                http:
                // 10.1.0.213/visualization/supply/index_541.html
                return SUCCESS;
            }
            return ERROR;
        } catch (Exception e) {
        	logger.error("SysUserAction.b2bAuthen异常：" + e.getMessage(), e);
            return ERROR;
        }
    }

    /**
     * getter and setter
     */

    public String getRtnMessage() {
        return rtnMessage;
    }

    public void setRtnMessage(String rtnMessage) {
        this.rtnMessage = rtnMessage;
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
     */

    public String doDefault() throws Exception {
        return INPUT;
    }


    public Object getModel() {
        return sysuser;
    }

    public CmsUserSiteDAO getCmsUserSiteDAO() {
        return cmsUserSiteDAO;
    }

    public void setCmsUserSiteDAO(CmsUserSiteDAO cmsUserSiteDAO) {
        this.cmsUserSiteDAO = cmsUserSiteDAO;
    }

    public Integer getSiteId() {
        return siteId;
    }

    public void setSiteId(Integer siteId) {
        this.siteId = siteId;
    }

    public CmsUserSiteService getCmsUserSiteService() {
        return cmsUserSiteService;
    }

    public void setCmsUserSiteService(CmsUserSiteService cmsUserSiteService) {
        this.cmsUserSiteService = cmsUserSiteService;
    }

    public CmsSiteService getCmsSiteService() {
        return cmsSiteService;
    }

    public void setCmsSiteService(CmsSiteService cmsSiteService) {
        this.cmsSiteService = cmsSiteService;
    }

    public List<CmsUserSite> getCmsUserSiteList() {
        return cmsUserSiteList;
    }

    public void setCmsUserSiteList(List<CmsUserSite> cmsUserSiteList) {
        this.cmsUserSiteList = cmsUserSiteList;
    }

    public String getLoginSysPath() {
        return loginSysPath;
    }

    public void setLoginSysPath(String loginSysPath) {
        this.loginSysPath = loginSysPath;
    }
}
