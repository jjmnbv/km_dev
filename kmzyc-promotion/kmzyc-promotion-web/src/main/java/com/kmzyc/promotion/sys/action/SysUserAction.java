package com.kmzyc.promotion.sys.action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.jasig.cas.client.util.AssertionHolder;

import com.kmzyc.commons.page.Page;
import com.kmzyc.promotion.app.util.MD5;
import com.kmzyc.promotion.sys.bean.SysMenuBean;
import com.kmzyc.promotion.sys.bean.SysUserBean;
import com.kmzyc.promotion.sys.model.SysMenu;
import com.kmzyc.promotion.sys.model.SysUser;
import com.kmzyc.promotion.sys.util.XiuSystemConstant;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 类 SysUserAction 操作员Action类
 * 
 * @author
 * @version 2.1
 * @since JDK1.5
 */
@SuppressWarnings({"unchecked", "unused"})
public class SysUserAction extends com.kmzyc.promotion.app.action.BaseAction implements ModelDriven {
    private static final long serialVersionUID = 1L;
    Logger log = Logger.getLogger(this.getClass());
    private SysUser sysuser = new SysUser(); // model
    String rtnMessage;

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
    String retrunUrl;
    String loginUrl;

    /**
     * 操作员登录
     * 
     * @param @return String @exception
     */
    public String loginSysUser() {
        log.warn("================== SysUserAction.loginSysUser()");
        String loginName = AssertionHolder.getAssertion().getPrincipal().getName();

        log.warn("===================------------------------------------loginName-----"
                + loginName);
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
            e.printStackTrace();
            return ERROR;
        }
        // 设置session
        Map session = ActionContext.getContext().getSession();
        if (session.get("sysUser") != null) {
            session.clear();
        }
        HttpServletRequest request = (HttpServletRequest) ActionContext.getContext()
                .get(ServletActionContext.HTTP_REQUEST);
        String topMenuId = request.getParameter("topMenuId");
        if (topMenuId != null)
            sysuser.setHeadMenuId(topMenuId);
        session.put("sysUser", sysuser);

        rtnMessage = "loginOk";
        if (loginUrl == null || "".equals(loginUrl))
            return SUCCESS;
        else {
            HttpServletResponse response = ServletActionContext.getResponse();
            try {
                // this.getRequest().getRequestDispatcher(loginUrl).forward(getRequest(),
                // getResponse());
                this.getResponse().sendRedirect(loginUrl);
            } catch (Exception e) {

                e.printStackTrace();
            }
            return null;
        }
    }

    /**
     * 获取用户权限内头部Menu信息
     * 
     * @param @return String @exception
     */
    public String getSysTop() {
        Map session = ActionContext.getContext().getSession();
        SysUser sessionUser = (SysUser) session.get("sysUser");
        try {
            // 获取登录人菜单权限信息
            SysMenuBean mbean = new SysMenuBean();
            dataList = mbean.getUpMenuListByUserId(sessionUser.getUserId());
        } catch (Exception e) {
            e.printStackTrace();
            return ERROR;
        }
        return SUCCESS;
    }

    /**
     * 点击header菜单刷新左侧菜单
     * 
     * @param @return String @exception
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
            e.printStackTrace();
            return "login";
        }
        return SUCCESS;
    }

    /**
     * 获取左侧导航菜单
     * 
     * @param @return String @exception
     */
    public String getSysLeftMenu() {
        Map session = ActionContext.getContext().getSession();
        SysUser sessionUser = (SysUser) session.get("sysUser");
        if (topMenuId == null || topMenuId.equals("")) {
            topMenuId = sessionUser.getHeadMenuId() != null ? sessionUser.getHeadMenuId() : "-1";
        }
        try {
            SysMenuBean bean = new SysMenuBean();
            dataList = bean.getSubMenuListByUserId(sessionUser.getUserId(),
                    Integer.valueOf(topMenuId));
        } catch (Exception e) {
            e.printStackTrace();
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
        // log.warn("================== logout!!!");
        return SUCCESS;
    }


    /**
     * 修改密码
     * 
     * @param @return String @exception
     */
    public String doSysUserPwdModify() throws Exception {
        Integer userid = sysuser.getUserId();
        try {
            SysUserBean bean = new SysUserBean();
            SysUser su = new SysUser();
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
            e.printStackTrace();
            return ERROR;
        }
        return SUCCESS;
    }

    /**
     * 记录返回url并跳转登录页面
     */
    public String gotoSysUserLogin() {
        if (retrunUrl != null) {
            loginUrl = retrunUrl;
        }
        return SUCCESS;
    }

    /**
     * 查找sysuser内容，不包括部门和角色信息
     * 
     * @param @return String @exception
     */
    public String getSysUserDetailOnly() throws Exception {
        Integer userid = sysuser.getUserId();
        try {
            // 获取用户信息
            SysUserBean bean = new SysUserBean();
            this.sysuser = bean.getSysUserDetail(userid);
        } catch (Exception e) {
            e.printStackTrace();
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

    public String getRetrunUrl() {
        return retrunUrl;
    }

    public void setRetrunUrl(String retrunUrl) {
        this.retrunUrl = retrunUrl;
    }

    public String getLoginUrl() {
        return loginUrl;
    }

    public void setLoginUrl(String loginUrl) {
        this.loginUrl = loginUrl;
    }
}
