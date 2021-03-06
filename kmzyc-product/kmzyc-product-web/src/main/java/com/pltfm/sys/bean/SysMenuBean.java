package com.pltfm.sys.bean;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;




import com.pltfm.sys.model.SysRoleMenu;
import com.pltfm.sys.model.SysMenu;
import com.pltfm.sys.dao.SysRoleMenuDAOImpl;
import com.pltfm.sys.dao.SysMenuDAOImpl;
import com.pltfm.sys.model.SysMenuExample;
import com.pltfm.sys.model.SysRoleMenuExample;
import com.pltfm.sys.util.DatetimeUtil;

/**
 * 类 SysMenuBean 菜单类
 *
 * @author  
 * @version 2.1
 * @since   JDK1.5
 */
public class SysMenuBean extends BaseBean {
	
	Logger log = Logger.getLogger(this.getClass());
	
	public SysMenuBean() {
		super();    //加载总的sqlmap配置文件
	}
	
	
	
	/**
	 * 根据level查询菜单List
	 * 
	 * @param    level
	 * @return   List
	 * @exception    Exception
	 */
	public List getMenuListbyLevel(String level) throws Exception {
		try {
			SysMenuDAOImpl dao = new SysMenuDAOImpl(sqlMap);
			SysMenuExample exam = new SysMenuExample();
			exam.createCriteria().andMenuLvEqualTo(level).andIsEnableEqualTo("1");
			exam.setOrderByClause(" sortno asc");
            return dao.selectByExample(exam);
		} catch (SQLException e) {
			e.printStackTrace();
			log.warn(e.getMessage());
			throw e;
		}
	}
	
	
	
	
	/**
	 * 得到所有菜单List
	 * 
	 * @param    
	 * @return    List
	 * @exception    Exception
	 */
	public List getAllMenuList() throws Exception {
		try {
			SysMenuDAOImpl dao = new SysMenuDAOImpl(sqlMap);
			SysMenuExample exam = new SysMenuExample();
			exam.createCriteria().andIsEnableEqualTo("1");
			exam.setOrderByClause(" menu_upid asc ,sortno asc");
            return dao.selectByExample(exam);
		} catch (SQLException e) {
			e.printStackTrace();
			log.warn(e.getMessage());
			throw e;
		}
	}
	
	
	
	
	/**
	 * 获取所有的顶层菜单
	 * 
	 * @param    
	 * @return    List
	 * @exception    Exception
	 */
	public List getAllUpMenuList()  throws Exception {
		try {
			SysMenuDAOImpl dao = new SysMenuDAOImpl(sqlMap);
			SysMenuExample exam = new SysMenuExample();    //组装where查询条件的对象
			exam.createCriteria().andMenuLvEqualTo("1");
			exam.setOrderByClause("sortno asc");
            return dao.selectByExample(exam);
		} catch (SQLException e) {
			e.printStackTrace();
			log.warn(e.getMessage());
			throw e;
		}
	}
	
	
	
	
    /**
	 * 新增菜单
	 * 
	 * @param    SysMenu
	 * @return   Integer
	 * @exception    Exception
	 */
    public Integer addSysMenu(SysMenu sysMenu) throws Exception {
		try {
			SysMenuDAOImpl dao = new SysMenuDAOImpl(sqlMap);
			sysMenu.setCreateDate(DatetimeUtil.getCalendarInstance().getTime());
			dao.insert(sysMenu);
		} catch (SQLException e) {
			e.printStackTrace();
			log.warn(e.getMessage());
			throw e;
		}
        return sysMenu.getMenuId();
	}
    
    
    
    
    /**
	 * 删除所选菜单
	 * 
	 * @param    SysMenu
	 * @return   void
	 * @exception    Exception
	 */
	public void deleteSysMenu(SysMenu sysMenu) throws Exception {
		try {
			SysMenuDAOImpl dao = new SysMenuDAOImpl(sqlMap);
			dao.deleteByPrimaryKey(sysMenu.getMenuId());
		} catch (SQLException e) {
			e.printStackTrace();
			log.warn(e.getMessage());
			throw e;
		}
	}
	
	
	
	
	/**
	 * 根据上级menuId查询list
	 * 
	 * @param    String
	 * @return   List
	 * @exception    Exception
	 */
	public List searchListByUpId(String upId) throws Exception {
		try {
			SysMenuDAOImpl dao = new SysMenuDAOImpl(sqlMap);
			SysMenuExample exam = new SysMenuExample();    //组装where查询条件的对象
			exam.createCriteria().andMenuUpidEqualTo(Integer.valueOf(upId)).andIsEnableEqualTo("1");
			exam.setOrderByClause("sortno asc");
            return dao.selectByExample(exam);
		} catch (SQLException e) {
			e.printStackTrace();
			log.warn(e.getMessage());
			throw e;
		}
	}
	
	
	
	
	/**
	 * 根据menuId得到menu
	 * 
	 * @param    menuId
	 * @return   SysMenu
	 * @exception    Exception
	 */
	public SysMenu getMenuById(Integer menuId) throws Exception {
		SysMenu vo=null;
		try {
			SysMenuDAOImpl dao = new SysMenuDAOImpl(sqlMap);
			vo = dao.selectByPrimaryKey(menuId);
		} catch (SQLException e) {
			e.printStackTrace();
			log.warn(e.getMessage());
			throw e;
		}
		return vo;
	}
	
	
	
	
    /**
	 * 修改菜单
	 * 
	 * @param    SysMenu
	 * @return   SysMenu
	 * @exception    Exception
	 */
    public SysMenu updateSysMenu(SysMenu sysMenu) throws Exception {
		try {
			SysMenuDAOImpl dao = new SysMenuDAOImpl(sqlMap);
			sysMenu.setUpdateDate(DatetimeUtil.getCalendarInstance().getTime());
			//dao.updateByPrimaryKeySelective(sysMenu);
			dao.updateByPrimaryKey(sysMenu);
		} catch (SQLException e) {
			e.printStackTrace();
			log.warn(e.getMessage());
			throw e;
		}
        return sysMenu;
	}
    
    
    
    
	/**
	 * 根据角色id获取该角色的授权菜单列表
	 * 
	 * @param    roleId
	 * @return   List
	 * @exception    Exception
	 */
	public List getMenuListByRoleId(Integer roleId) throws Exception {
		try {
			SysRoleMenuDAOImpl dao = new SysRoleMenuDAOImpl(sqlMap);
			SysRoleMenuExample exam = new SysRoleMenuExample();    //组装where查询条件的对象
			exam.createCriteria().andRoleIdEqualTo(roleId);
            return dao.selectByExample(exam);
		} catch (SQLException e) {
			e.printStackTrace();
			log.warn(e.getMessage());
			throw e;
		}
	}
	
	
	
	
	/**
	 * 根据角色id获取该角色的授权header菜单列表
	 * 
	 * @param    userId
	 * @return   List
	 * @exception    Exception
	 */
	public List getUpMenuListByUserId(Integer userId) throws Exception {
		try {
			SysMenuDAOImpl dao = new SysMenuDAOImpl(sqlMap);
            return dao.selectUpMenuListByUser(userId);
		} catch (SQLException e) {
			e.printStackTrace();
			log.warn(e.getMessage());
			throw e;
		}
	}
	
	
	
	
	/**
	 * 根据角色id,upMenuId获取该角色的授权sub菜单列表
	 * 
	 * @param    userId
	 * @return   List
	 * @exception    Exception
	 */
	public List getSubMenuListByUserId(Integer userId, Integer upMenuId) throws Exception {
		try {
			SysMenuDAOImpl dao = new SysMenuDAOImpl(sqlMap);
			return dao.selectSubMenuList(userId,upMenuId);
		} catch (SQLException e) {
			e.printStackTrace();
			log.warn(e.getMessage());
			throw e;
		}
	}
	
	
	
	
    /**
	 * 先删除该角色的所有授权菜单，然后再添加新的授权菜单
	 * 
	 * @param    roleId
	 * @return   void
	 * @exception    Exception
	 */
	public void deleteAndsaveRoleMenu(Integer roleId, String[] menuId) throws Exception {
		try {
			sqlMap.startTransaction();
			SysRoleMenuDAOImpl dao = new SysRoleMenuDAOImpl(sqlMap);
			//开始删除
			SysRoleMenuExample exam = new SysRoleMenuExample();    //组装where查询条件的对象
			exam.createCriteria().andRoleIdEqualTo(roleId);
            dao.deleteByExample(exam);
			//开始循环添加
	    	if(menuId!=null  &&  menuId.length>0){
	    		for(int i=0; i<menuId.length; i++){
	    			SysRoleMenu vo = new SysRoleMenu();
	    			vo.setMenuId(Integer.valueOf(menuId[i]));
	    			vo.setRoleId(roleId);
			        dao.insert(vo);
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

	
}