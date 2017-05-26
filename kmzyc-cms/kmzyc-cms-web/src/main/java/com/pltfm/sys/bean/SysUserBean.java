package com.pltfm.sys.bean;

import com.pltfm.sys.dao.SysRoleMenuDAOImpl;
import com.pltfm.sys.dao.SysUserDAO;
import com.pltfm.sys.dao.SysUserDAOImpl;
import com.pltfm.sys.dao.SysUserRoleDAOImpl;
import com.pltfm.sys.model.SysRole;
import com.pltfm.sys.model.SysRoleMenu;
import com.pltfm.sys.model.SysRoleMenuExample;
import com.pltfm.sys.model.SysUser;
import com.pltfm.sys.model.SysUserExample;
import com.pltfm.sys.model.SysUserRole;
import com.pltfm.sys.model.SysUserRoleExample;
import com.pltfm.sys.util.DatetimeUtil;
import com.kmzyc.commons.page.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/**
 * 类 SysUserBean 操作员类
 *
 * @author
 * @version 2.1
 * @since JDK1.5
 */
public class SysUserBean extends BaseBean {
	private static Logger logger = LoggerFactory.getLogger(SysUserBean.class);
    private static SysUserBean instance;

    public static SysUserBean instance(){
        if(instance==null)
            instance=new SysUserBean();
        return instance;
    }

    private SysUserBean() {
        super();    //加载总的sqlmap配置文件
    }


    /**
     * 新增操作员
     *
     * @return Integer
     */
    public Integer addSysUser(SysUser sysuser) throws Exception {
        try {
            SysUserDAOImpl dao = SysUserDAOImpl.instance(sqlMap);
            sysuser.setCreateDate(DatetimeUtil.getCalendarInstance().getTime());
            sysuser.setUserLasttime(DatetimeUtil.getCalendarInstance().getTime());
            dao.insert(sysuser);
        } catch (SQLException e) {
            logger.error("SysUserBean.addSysUser异常：" + e.getMessage(), e);
            
            throw e;
        }
        return sysuser.getUserId();
    }


    /**
     * 新增操作员同时添加系统角色
     *
     * @return Integer
     */
    public Integer addSysUserRole(SysUser sysuser, String roleListStr) throws Exception {
        try {
            sqlMap.startTransaction();
            // 添加员工
            SysUserDAO dao = SysUserDAOImpl.instance(sqlMap);
            sysuser.setCreateDate(DatetimeUtil.getCalendarInstance().getTime());
            Integer userid = dao.insertSelective(sysuser);
            logger.warn("----------------------------------------userid=" + userid);
            logger.warn("----------------------------------------sysuser.userid=" + sysuser.getUserId());
            // 添加员工角色关系
            String[] roleids = roleListStr.split(",");
            if (roleids.length > 0) {
                for (int i = 0; i < roleids.length; i++) {
                    SysUserRoleDAOImpl urdao = SysUserRoleDAOImpl.instance(sqlMap);
                    SysUserRole urvo = new SysUserRole();
                    urvo.setUserId(userid);
                    urvo.setRoleId(Integer.valueOf(roleids[i]));
                    urdao.insert(urvo);
                }
            }
            sqlMap.commitTransaction();
        } catch (SQLException e) {
            logger.error("SysUserBean.addSysUserRole异常：" + e.getMessage(), e);
            
            throw e;
        } finally {
            try {
                sqlMap.endTransaction();
            } catch (SQLException e) {
                logger.error("SysUserBean.addSysUserRole异常：" + e.getMessage(), e);
            }
        }
        return sysuser.getUserId();
    }


    /**
     * 删除操作员同时删除系统角色
     *
     * @return Integer
     */
    public Integer delSysUserRole(SysUser sysuser) throws Exception {
        try {
            sqlMap.startTransaction();
            // 添加员工
            SysUserDAO dao = SysUserDAOImpl.instance(sqlMap);
            SysUserRoleDAOImpl urdao = SysUserRoleDAOImpl.instance(sqlMap);
            sysuser.setCreateDate(DatetimeUtil.getCalendarInstance().getTime());
            List<SysUserRole> roleList = dao.selectByUser(sysuser);

            // 删除员工角色关系
            if (roleList != null && roleList.size() > 0) {
                for (int i = 0; i < roleList.size(); i++) {
                    urdao.deleteByPrimaryKey(roleList.get(i).getUserroleId());
                }
            }
            sqlMap.commitTransaction();
        } catch (SQLException e) {
            logger.error("SysUserBean.delSysUserRole异常：" + e.getMessage(), e);
            
            throw e;
        } finally {
            try {
                sqlMap.endTransaction();
            } catch (SQLException e) {
                logger.error("SysUserBean.delSysUserRole异常：" + e.getMessage(), e);
            }
        }
        return sysuser.getUserId();
    }


    /**
     * 根据userId查询某个操作员
     *
     * @return SysUser
     */
    public SysUser getSysUserDetail(Integer userId) throws Exception {
        SysUser sysuser = null;
        try {
            SysUserDAOImpl dao = SysUserDAOImpl.instance(sqlMap);
            sysuser = dao.selectByPrimaryKey(userId);
        } catch (SQLException e) {
            logger.error("SysUserBean.getSysUserDetail异常：" + e.getMessage(), e);
            
            throw e;
        }
        return sysuser;
    }


    /**
     * 根据userid获取user信息及其所有角色串
     *
     * @return SysUser
     */
    public SysUser getSysUserRoleDetail(Integer userId) throws Exception {
        SysUser sysuser = null;
        try {
            sqlMap.startTransaction();
            // 获取user信息
            SysUserDAOImpl dao = SysUserDAOImpl.instance(sqlMap);
            sysuser = dao.selectByPrimaryKey(userId);
            // 获取roles串信息
            List rolesList = dao.selectUserRolesList(userId);
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
            sqlMap.commitTransaction();
        } catch (SQLException e) {
            logger.error("SysUserBean.getSysUserRoleDetail异常：" + e.getMessage(), e);
            
            throw e;
        } finally {
            try {
                sqlMap.endTransaction();
            } catch (SQLException e) {
                logger.error("SysUserBean.getSysUserRoleDetail异常：" + e.getMessage(), e);
            }
        }
        return sysuser;
    }


    /**
     * 修改操作员
     *
     * @return SysUser
     */
    public SysUser updateSysUser(SysUser sysuser) throws Exception {
        try {
            SysUserDAOImpl dao = SysUserDAOImpl.instance(sqlMap);
            sysuser.setUpdateDate(DatetimeUtil.getCalendarInstance().getTime());
            dao.updateByPrimaryKeySelective(sysuser);
        } catch (SQLException e) {
            logger.error("SysUserBean.updateSysUser异常：" + e.getMessage(), e);
            
            throw e;
        }
        return sysuser;
    }


    /**
     * 简单修改用户信息，所有修改项都在action中组装
     *
     * @return SysUser
     */
    public SysUser simpleUpdateSysUser(SysUser sysuser) throws Exception {
    	logger.warn("================== SysUserBean.simpleUpdateSysUser()");
        try {
            SysUserDAOImpl dao = SysUserDAOImpl.instance(sqlMap);
            dao.updateByPrimaryKeySelective(sysuser);
        } catch (SQLException e) {
            logger.error("SysUserBean.simpleUpdateSysUser异常：" + e.getMessage(), e);
            
            throw e;
        }
        return sysuser;
    }


    /**
     * 修改员工信息和员工角色信息
     *
     * @return SysUser
     */
    public SysUser updateSysUserRole(SysUser sysuser, String roleListStr) throws Exception {
        try {
            sqlMap.startTransaction();
            // 修改员工信息
            SysUserDAOImpl dao = SysUserDAOImpl.instance(sqlMap);
            sysuser.setUpdateDate(DatetimeUtil.getCalendarInstance().getTime());
            dao.updateByPrimaryKeySelective(sysuser);
            // 修改员工所属角色信息
            SysUserRoleDAOImpl urdao = SysUserRoleDAOImpl.instance(sqlMap);
            SysUserRoleExample exam = new SysUserRoleExample();
            exam.createCriteria().andUserIdEqualTo(sysuser.getUserId());
            urdao.deleteByExample(exam); // 将该会员的所有角色关系删除
            String[] roleids = roleListStr.split(",");
            if (roleids.length > 0) {
                for (int i = 0; i < roleids.length; i++) {
                    SysUserRole urvo = new SysUserRole();
                    urvo.setUserId(sysuser.getUserId());
                    urvo.setRoleId(Integer.valueOf(roleids[i]));
                    urdao.insert(urvo); // 再添加新的角色关系
                }
            }
            sqlMap.commitTransaction();
        } catch (SQLException e) {
            logger.error("SysUserBean.updateSysUserRole异常：" + e.getMessage(), e);
            
            throw e;
        } finally {
            try {
                sqlMap.endTransaction();
            } catch (SQLException e) {
                logger.error("SysUserBean.updateSysUserRole异常：" + e.getMessage(), e);
            }
        }
        return sysuser;
    }


    /**
     * 删除所选操作员
     *
     * @return void
     */
    public void deleteSysUser(String[] userId) throws Exception {
        try {
            sqlMap.startTransaction();
            SysUserDAOImpl dao = SysUserDAOImpl.instance(sqlMap);
            // 开始循环删除
            if (userId != null && userId.length > 0) {
                for (int i = 0; i < userId.length; i++) {
                    dao.deleteByPrimaryKey(Integer.valueOf(userId[i]));
                }
            }
            sqlMap.commitTransaction();
        } catch (SQLException e) {
            logger.error("SysUserBean.deleteSysUser异常：" + e.getMessage(), e);
            
            throw e;
        } finally {
            try {
                sqlMap.endTransaction();
            } catch (SQLException e) {
                logger.error("SysUserBean.deleteSysUser异常：" + e.getMessage(), e);
            }
        }
    }


    /**
     * 检查客户的登录名是否已存在
     *
     * @return String
     */
    public String checkOnlyName(SysUser sysuser) throws Exception {
        String rtnMessage = null;
        List userList = null;
        try {
            SysUserDAOImpl dao = SysUserDAOImpl.instance(sqlMap);
            SysUserExample exam = new SysUserExample();
            // 首先检查用户名是否重复
            if (sysuser.getUserId() == null) {// 新增时
                exam.createCriteria().andUserNameEqualTo(sysuser.getUserName());
            } else {// 修改时
                exam.createCriteria().andUserNameEqualTo(sysuser.getUserName())
                        .andUserIdNotEqualTo(sysuser.getUserId());
            }
            userList = dao.selectByExample(exam);
            if (userList != null && userList.size() > 0) {
                rtnMessage = "登录名已存在,操作终止!";
                return rtnMessage;
            }
        } catch (SQLException e) {
            logger.error("SysUserBean.checkOnlyName异常：" + e.getMessage(), e);
            
            throw e;
        }
        return null;
    }


    /**
     * 用户登录验证
     *
     * @return SysUser
     */
    public SysUser sysUserLogin(SysUser sysuser) throws Exception {
    	logger.warn("================== SysUserBean.sysUserLogin()");
        SysUser rtnSysUser = null;
        try {
            SysUserDAOImpl dao = SysUserDAOImpl.instance(sqlMap);
            SysUserExample exam = new SysUserExample();
            exam.createCriteria().andUserNameEqualTo(sysuser.getUserName())
//					.andUserPwdEqualTo(MD5.md5crypt(sysuser.getUserPwd()))
                    .andIsEnableEqualTo("1");
            List userList = dao.selectByExample(exam);
            if (userList != null && userList.size() > 0) {
                rtnSysUser = (SysUser) userList.get(0);
            }
        } catch (SQLException e) {
            logger.error("SysUserBean.sysUserLogin异常：" + e.getMessage(), e);
            
            throw e;
        }
        return rtnSysUser;
    }


    /**
     * 根据vo条件查询列表
     *
     * @return List
     */
    public List getSysUserList(SysUser vo) throws Exception {
        List dataList;
        try {
            SysUserDAOImpl dao = SysUserDAOImpl.instance(sqlMap);
            SysUserExample exam = new SysUserExample(); // 组装where查询条件的对象
            // 组合条件 like
            String userName = vo.getUserName(); // 名称
            if (userName != null && !userName.equals(""))
                userName = "%" + userName + "%"; // like statement
            else
                userName = "%%";
            exam.createCriteria().andUserNameLike(userName);
            dataList = dao.selectByExample(exam);
        } catch (SQLException e) {
            logger.error("SysUserBean.getSysUserList异常：" + e.getMessage(), e);
            
            throw e;
        }
        return dataList;
    }


    /**
     * 根据roleId查询userList
     *
     * @return List
     */
    public List getUserListByRoleId(Integer roleId) throws Exception {
        List dataList;
        try {
            SysUserDAO dao = SysUserDAOImpl.instance(sqlMap);
            dataList = dao.getUserListByRoleId(roleId);
        } catch (SQLException e) {
            logger.error("SysUserBean.getUserListByRoleId异常：" + e.getMessage(), e);
            
            throw e;
        }
        return dataList;
    }


    /**
     * 根据vo条件查询分类信息page
     *
     * @return Page
     */
    public Page searchPageByVo(Page pageParam, SysUser vo) throws Exception {
        int pageNo = pageParam.getPageNo();// 当前查询第几页
        if (pageNo == 0)
            pageNo = 1;// 首次查询第一页
        int pageSize = pageParam.getPageSize(); // 每页显示记录数
        int skip = (pageNo - 1) * pageSize + 1;
        int max = (pageNo - 1) * pageSize + pageSize;
        Page page = null;
        try {
            SysUserDAOImpl dao = SysUserDAOImpl.instance(sqlMap);
            // 组合条件 like
            String userReal = vo.getUserReal(); // 姓名
            if (userReal != null && !userReal.equals(""))
                vo.setUserReal("%" + userReal + "%"); // like statement

            String userName = vo.getUserName(); // 用户名
            if (userName != null && !userName.equals(""))
                vo.setUserName("%" + userName + "%"); // like statement
            vo.setSkip(skip);
            vo.setMax(max);
            page = dao.selectPageByVo(pageParam, vo);
            page.setPageNo(pageNo);// 当前查询第几页
            vo.setUserReal(userReal);
            vo.setUserName(userName);

        } catch (SQLException e) {
            logger.error("SysUserBean.searchPageByVo异常：" + e.getMessage(), e);
            
            throw e;
        }
        return page;
    }


    /**
     * 根据vo条件查询列表
     *
     * @return List
     */
    public List searchUserListByDept(SysUser vo) throws Exception {
        List dataList;
        try {
            SysUserDAOImpl dao = SysUserDAOImpl.instance(sqlMap);
            // 组合条件 like
            String userName = vo.getUserName(); // 用户名
            if (userName != null && !userName.equals(""))
                vo.setUserName("%" + userName + "%");
            String userReal = vo.getUserReal(); // 姓名
            if (userReal != null && !userReal.equals(""))
                vo.setUserReal("%" + userReal + "%"); // like statement

            dataList = dao.selectListByVo(vo);

            vo.setUserReal(userReal);
            vo.setUserName(userName);
        } catch (SQLException e) {
            logger.error("SysUserBean.searchUserListByDept异常：" + e.getMessage(), e);
            
            throw e;
        }
        return dataList;
    }


    /**
     * 根据菜单编号，查询有该菜单权限的人员列表
     *
     * @return List
     */
    public List getRoleUserListByMenuId(Integer menuId) throws Exception {
        List dataList = new ArrayList();
        try {
            sqlMap.startTransaction();
            SysRoleMenuDAOImpl roleMenuDao = SysRoleMenuDAOImpl.instance(sqlMap);
            SysRoleMenuExample roleMenuExam = new SysRoleMenuExample();
            roleMenuExam.createCriteria().andMenuIdEqualTo(menuId);
            List roleMenuList = roleMenuDao.selectByExample(roleMenuExam);// 得到有该菜单权限的所有角色
            List rmList = new ArrayList();

            if (roleMenuList != null && roleMenuList.size() > 0) {
                for (Object aRoleMenuList : roleMenuList) {
                    SysRoleMenu srm = (SysRoleMenu) aRoleMenuList;
                    Integer roleId = srm.getRoleId();
                    rmList.add(roleId);
                }

                SysUserRoleDAOImpl userroleDao = SysUserRoleDAOImpl.instance(sqlMap);
                SysUserRoleExample userroleExam = new SysUserRoleExample();
                userroleExam.createCriteria().andRoleIdIn(rmList);
                List userroleList = userroleDao.selectByExample(userroleExam);// 得到有该角色的所有人
                List urList = new ArrayList();

                if (userroleList != null && userroleList.size() > 0) {
                    for (Object anUserroleList : userroleList) {
                        SysUserRole sur = (SysUserRole) anUserroleList;
                        Integer userId = sur.getUserId();
                        urList.add(userId);
                    }

                    SysUserDAOImpl userDao = SysUserDAOImpl.instance(sqlMap);
                    SysUserExample userExam = new SysUserExample();
                    userExam.createCriteria().andUserIdIn(urList);
                    dataList = userDao.selectByExample(userExam);

                }
            }
            sqlMap.commitTransaction();
        } catch (SQLException e) {
            logger.error("SysUserBean.getRoleUserListByMenuId异常：" + e.getMessage(), e);
            
            throw e;
        } finally {
            try {
                sqlMap.endTransaction();
            } catch (SQLException e) {
                logger.error("SysUserBean.getRoleUserListByMenuId异常：" + e.getMessage(), e);
            }
        }
        return dataList;
    }

}
