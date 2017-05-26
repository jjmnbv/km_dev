package com.pltfm.sys.bean;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.pltfm.sys.util.DatetimeUtil;
import com.kmzyc.commons.page.Page;

import com.pltfm.sys.dao.SysRoleMenuDAOImpl;
import com.pltfm.sys.dao.SysUserDAO;
import com.pltfm.sys.dao.SysUserDAOImpl;
import com.pltfm.sys.dao.SysUserRoleDAO;
import com.pltfm.sys.dao.SysUserRoleDAOImpl;
import com.pltfm.sys.model.SysRole;
import com.pltfm.sys.model.SysRoleMenu;
import com.pltfm.sys.model.SysRoleMenuExample;
import com.pltfm.sys.model.SysUser;
import com.pltfm.sys.model.SysUserExample;
import com.pltfm.sys.model.SysUserRole;
import com.pltfm.sys.model.SysUserRoleExample;


/**
 * 类 SysUserRoleBean 操作员类
 *
 * @author  
 * @version 2.1
 * @since   JDK1.5
 */
public class SysUserRoleBean extends BaseBean {

	Logger log = Logger.getLogger(this.getClass());
	
	public SysUserRoleBean() {
		super();    //加载总的sqlmap配置文件
	}





	
	
	/**
	 * 修改员工信息和员工角色信息
	 * 
	 * @param    sysuser
	 * @return   SysUser
	 * @exception    Exception
	 */
	public SysUser updateSysUserRole(SysUser sysuser, String roleListStr) throws Exception {
		try {
			sqlMap.startTransaction();
			// 修改员工信息
			SysUserDAOImpl dao = new SysUserDAOImpl(sqlMap);
			sysuser.setUpdateDate(DatetimeUtil.getCalendarInstance().getTime());
			dao.updateByPrimaryKeySelective(sysuser);
			// 修改员工所属角色信息
			SysUserRoleDAOImpl urdao = new SysUserRoleDAOImpl(sqlMap);
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
			e.printStackTrace();
			log.warn(e.getMessage());
			throw e;
		} finally {
			try {
				sqlMap.endTransaction();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return sysuser;
	}

	
	
	
	/**
	 * 删除所选操作员
	 * 
	 * @param    userId
	 * @return   void
	 * @exception    Exception
	 */
	public void deleteSysUser(String[] userId) throws Exception {
		try {
			sqlMap.startTransaction();
			SysUserDAOImpl dao = new SysUserDAOImpl(sqlMap);
			// 开始循环删除
			if (userId != null && userId.length > 0) {
				for (int i = 0; i < userId.length; i++) {
					dao.deleteByPrimaryKey(Integer.valueOf(userId[i]));
				}
			}
			sqlMap.commitTransaction();
		} catch (SQLException e) {
			e.printStackTrace();
			log.warn(e.getMessage());
			throw e;
		} finally {
			try {
				sqlMap.endTransaction();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	
	
	
	/**
	 * 检查客户的登录名是否已存在
	 * 
	 * @param     sysuser
	 * @return    String
	 * @exception    Exception
	 */
	public String checkOnlyName(SysUser sysuser) throws Exception {
		String rtnMessage = null;
		List userList = null;
		try {
			SysUserDAOImpl dao = new SysUserDAOImpl(sqlMap);
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
			e.printStackTrace();
			log.warn(e.getMessage());
			throw e;
		}
		return null;
	}

	
	
	
	/**
	 * 用户登录验证
	 * 
	 * @param   sysuser
	 * @return  SysUser
	 * @exception    Exception
	 */
	public SysUser sysUserLogin(SysUser sysuser) throws Exception {
		log.warn("================== SysUserBean.sysUserLogin()");
		SysUser rtnSysUser = null;
		try {
			SysUserDAOImpl dao = new SysUserDAOImpl(sqlMap);
			SysUserExample exam = new SysUserExample();
			exam.createCriteria().andUserNameEqualTo(sysuser.getUserName())
					.andUserPwdEqualTo(sysuser.getUserPwd())
					.andIsEnableEqualTo("1");
			List userList = dao.selectByExample(exam);
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
	 * @param    vo
	 * @return   List
	 * @exception    Exception
	 */
	public List getSysUserList(SysUser vo) throws Exception {
		try {
			SysUserDAOImpl dao = new SysUserDAOImpl(sqlMap);
			SysUserExample exam = new SysUserExample(); // 组装where查询条件的对象
			// 组合条件 like
			String userName = vo.getUserName(); // 名称
			if (userName != null && !userName.equals(""))
				userName = "%" + userName + "%"; // like statement
			else
				userName = "%%";
			exam.createCriteria().andUserNameLike(userName);
            return dao.selectByExample(exam);
		} catch (SQLException e) {
			e.printStackTrace();
			log.warn(e.getMessage());
			throw e;
		}
	}

	
	
	/**
	 * 根据roleId查询userList
	 * 
	 * @param    roleId
	 * @return   List
	 * @exception    Exception
	 */
	public List getUserListByRoleId(Integer roleId) throws Exception {
		try {
			SysUserDAO dao = new SysUserDAOImpl(sqlMap);
            return dao.getUserListByRoleId(roleId);
		} catch (SQLException e) {
			e.printStackTrace();
			log.warn(e.getMessage());
			throw e;
		}
	}
	
	
	
	
	/**
	 * 根据vo条件查询分类信息page
	 * 
	 * @param    vo
	 * @return   Page
	 * @exception    Exception
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
			SysUserDAOImpl dao = new SysUserDAOImpl(sqlMap);
			// 组合条件 like
			String userReal = vo.getUserReal(); // 姓名
			if (userReal != null && !userReal.equals(""))
				vo.setUserReal("%" + userReal + "%"); // like statement
			vo.setSkip(skip);
			vo.setMax(max);
			page = dao.selectPageByVo(pageParam, vo);
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
	 * @param    vo
	 * @return   List
	 * @exception    Exception
	 */
	public List searchUserListByDept(SysUser vo) throws Exception {
		try {
			SysUserDAOImpl dao = new SysUserDAOImpl(sqlMap);
			// 组合条件 like
			String userName = vo.getUserName(); // 用户名
			if (userName != null && !userName.equals(""))
				vo.setUserName("%" + userName + "%");
			String userReal = vo.getUserReal(); // 姓名
			if (userReal != null && !userReal.equals(""))
				vo.setUserReal("%" + userReal + "%"); // like statement


			vo.setUserReal(userReal);
			vo.setUserName(userName);
            return dao.selectListByVo(vo);
		} catch (SQLException e) {
			e.printStackTrace();
			log.warn(e.getMessage());
			throw e;
		}
	}

	
	
	
	/**
	 * 根据菜单编号，查询有该菜单权限的人员列表
	 * 
	 * @param    menuId
	 * @return   List
	 * @exception    Exception
	 */
	public List getRoleUserListByMenuId(Integer menuId) throws Exception {
		List dataList = new ArrayList();
		try {
			sqlMap.startTransaction();
			SysRoleMenuDAOImpl roleMenuDao = new SysRoleMenuDAOImpl(sqlMap);
			SysRoleMenuExample roleMenuExam = new SysRoleMenuExample();
			roleMenuExam.createCriteria().andMenuIdEqualTo(menuId);
			List roleMenuList = roleMenuDao.selectByExample(roleMenuExam);// 得到有该菜单权限的所有角色
			List rmList = new ArrayList();

			if (roleMenuList != null && roleMenuList.size() > 0) {
				for (int i = 0; i < roleMenuList.size(); i++) {
					SysRoleMenu srm = (SysRoleMenu) roleMenuList.get(i);
					Integer roleId = srm.getRoleId();
					rmList.add(roleId);
				}

				SysUserRoleDAOImpl userroleDao = new SysUserRoleDAOImpl(sqlMap);
				SysUserRoleExample userroleExam = new SysUserRoleExample();
				userroleExam.createCriteria().andRoleIdIn(rmList);
				List userroleList = userroleDao.selectByExample(userroleExam);// 得到有该角色的所有人
				List urList = new ArrayList();

				if (userroleList != null && userroleList.size() > 0) {
					for (int j = 0; j < userroleList.size(); j++) {
						SysUserRole sur = (SysUserRole) userroleList.get(j);
						Integer userId = sur.getUserId();
						urList.add(userId);
					}

					SysUserDAOImpl userDao = new SysUserDAOImpl(sqlMap);
					SysUserExample userExam = new SysUserExample();
					userExam.createCriteria().andUserIdIn(urList);
					dataList = userDao.selectByExample(userExam);

				}
			}
			sqlMap.commitTransaction();
		} catch (SQLException e) {
			e.printStackTrace();
			log.warn(e.getMessage());
			throw e;
		} finally {
			try {
				sqlMap.endTransaction();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return dataList;
	}

}
