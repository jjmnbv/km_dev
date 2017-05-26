package com.kmzyc.promotion.sys.dao;

import java.sql.SQLException;
import java.util.List;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.kmzyc.promotion.sys.model.SysMenu;
import com.kmzyc.promotion.sys.model.SysMenuExample;
import com.kmzyc.promotion.sys.model.SysUser;

@SuppressWarnings( { "unchecked", "unused" })
public class SysMenuDAOImpl implements SysMenuDAO {
	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds
	 * to the database table SYS_MENU
	 * 
	 * @ibatorgenerated Thu Nov 24 14:28:04 CST 2011
	 */
	private SqlMapClient sqlMapClient;

	/**
	 * This method was generated by Apache iBATIS ibator. This method
	 * corresponds to the database table SYS_MENU
	 * 
	 * @ibatorgenerated Thu Nov 24 14:28:04 CST 2011
	 */
	public SysMenuDAOImpl(SqlMapClient sqlMapClient) {
		super();
		this.sqlMapClient = sqlMapClient;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method
	 * corresponds to the database table SYS_MENU
	 * 
	 * @ibatorgenerated Thu Nov 24 14:28:04 CST 2011
	 */
	public int countByExample(SysMenuExample example) throws SQLException {
		Integer count = (Integer) sqlMapClient.queryForObject("SYS_MENU.ibatorgenerated_countByExample", example);
		return count;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method
	 * corresponds to the database table SYS_MENU
	 * 
	 * @ibatorgenerated Thu Nov 24 14:28:04 CST 2011
	 */
	public int deleteByExample(SysMenuExample example) throws SQLException {
		int rows = sqlMapClient.delete("SYS_MENU.ibatorgenerated_deleteByExample", example);
		return rows;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method
	 * corresponds to the database table SYS_MENU
	 * 
	 * @ibatorgenerated Thu Nov 24 14:28:04 CST 2011
	 */
	public int deleteByPrimaryKey(Integer menuId) throws SQLException {
		SysMenu key = new SysMenu();
		key.setMenuId(menuId);
		int rows = sqlMapClient.delete("SYS_MENU.ibatorgenerated_deleteByPrimaryKey", key);
		return rows;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method
	 * corresponds to the database table SYS_MENU
	 * 
	 * @ibatorgenerated Thu Nov 24 14:28:04 CST 2011
	 */
	public Integer insert(SysMenu record) throws SQLException {
		Object newKey = sqlMapClient.insert("SYS_MENU.ibatorgenerated_insert", record);
		return (Integer) newKey;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method
	 * corresponds to the database table SYS_MENU
	 * 
	 * @ibatorgenerated Thu Nov 24 14:28:04 CST 2011
	 */
	public Integer insertSelective(SysMenu record) throws SQLException {
		Object newKey = sqlMapClient.insert("SYS_MENU.ibatorgenerated_insertSelective", record);
		return (Integer) newKey;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method
	 * corresponds to the database table SYS_MENU
	 * 
	 * @ibatorgenerated Thu Nov 24 14:28:04 CST 2011
	 */
	public List<SysMenu> selectByExample(SysMenuExample example) throws SQLException {
		List<SysMenu> list = sqlMapClient.queryForList("SYS_MENU.ibatorgenerated_selectByExample", example);
		return list;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method
	 * corresponds to the database table SYS_MENU
	 * 
	 * @ibatorgenerated Thu Nov 24 14:28:04 CST 2011
	 */
	public SysMenu selectByPrimaryKey(Integer menuId) throws SQLException {
		SysMenu key = new SysMenu();
		key.setMenuId(menuId);
		SysMenu record = (SysMenu) sqlMapClient.queryForObject("SYS_MENU.ibatorgenerated_selectByPrimaryKey", key);
		return record;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method
	 * corresponds to the database table SYS_MENU
	 * 
	 * @ibatorgenerated Thu Nov 24 14:28:04 CST 2011
	 */
	public int updateByExampleSelective(SysMenu record, SysMenuExample example) throws SQLException {
		UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
		int rows = sqlMapClient.update("SYS_MENU.ibatorgenerated_updateByExampleSelective", parms);
		return rows;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method
	 * corresponds to the database table SYS_MENU
	 * 
	 * @ibatorgenerated Thu Nov 24 14:28:04 CST 2011
	 */
	public int updateByExample(SysMenu record, SysMenuExample example) throws SQLException {
		UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
		int rows = sqlMapClient.update("SYS_MENU.ibatorgenerated_updateByExample", parms);
		return rows;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method
	 * corresponds to the database table SYS_MENU
	 * 
	 * @ibatorgenerated Thu Nov 24 14:28:04 CST 2011
	 */
	public int updateByPrimaryKeySelective(SysMenu record) throws SQLException {
		int rows = sqlMapClient.update("SYS_MENU.ibatorgenerated_updateByPrimaryKeySelective", record);
		return rows;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method
	 * corresponds to the database table SYS_MENU
	 * 
	 * @ibatorgenerated Thu Nov 24 14:28:04 CST 2011
	 */
	public int updateByPrimaryKey(SysMenu record) throws SQLException {
		int rows = sqlMapClient.update("SYS_MENU.ibatorgenerated_updateByPrimaryKey", record);
		return rows;
	}

	/**
	 * This class was generated by Apache iBATIS ibator. This class corresponds
	 * to the database table SYS_MENU
	 * 
	 * @ibatorgenerated Thu Nov 24 14:28:04 CST 2011
	 */
	private static class UpdateByExampleParms extends SysMenuExample {
		private Object record;

		public UpdateByExampleParms(Object record, SysMenuExample example) {
			super(example);
			this.record = record;
		}

		public Object getRecord() {
			return record;
		}
	}

	/******************* select header menu by userId **********************************/
	public List selectUpMenuListByUser(Integer userId) throws SQLException {
		SysUser key = new SysUser();
		key.setUserId(userId);
		List list = sqlMapClient.queryForList("SYS_MENU.selectUpMenuListByUser", key);
		return list;
	}

	/******************* select sub menu by userId and upMenuId **********************************/
	public List selectSubMenuList(Integer userId, Integer upMenuId) throws SQLException {
		SysUser key = new SysUser();
		key.setUserId(userId);
		key.setHeadMenuId(upMenuId.toString());
		List list = sqlMapClient.queryForList("SYS_MENU.selectSubMenuList", key);
		return list;
	}
}