package com.pltfm.sys.dao;

import java.sql.SQLException;
import java.util.List;

import com.pltfm.sys.model.SysUserRole;
import com.pltfm.sys.model.SysUserRoleExample;

public interface SysUserRoleDAO {
  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table SYS_USER_ROLE
   * 
   * @ibatorgenerated Thu Nov 24 14:28:04 CST 2011
   */
  int countByExample(SysUserRoleExample example) throws SQLException;

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table SYS_USER_ROLE
   * 
   * @ibatorgenerated Thu Nov 24 14:28:04 CST 2011
   */
  int deleteByExample(SysUserRoleExample example) throws SQLException;

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table SYS_USER_ROLE
   * 
   * @ibatorgenerated Thu Nov 24 14:28:04 CST 2011
   */
  int deleteByPrimaryKey(Integer userroleId) throws SQLException;

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table SYS_USER_ROLE
   * 
   * @ibatorgenerated Thu Nov 24 14:28:04 CST 2011
   */
  Integer insert(SysUserRole record) throws SQLException;

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table SYS_USER_ROLE
   * 
   * @ibatorgenerated Thu Nov 24 14:28:04 CST 2011
   */
  Integer insertSelective(SysUserRole record) throws SQLException;

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table SYS_USER_ROLE
   * 
   * @ibatorgenerated Thu Nov 24 14:28:04 CST 2011
   */
  List<SysUserRole> selectByExample(SysUserRoleExample example) throws SQLException;

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table SYS_USER_ROLE
   * 
   * @ibatorgenerated Thu Nov 24 14:28:04 CST 2011
   */
  SysUserRole selectByPrimaryKey(Integer userroleId) throws SQLException;

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table SYS_USER_ROLE
   * 
   * @ibatorgenerated Thu Nov 24 14:28:04 CST 2011
   */
  int updateByExampleSelective(SysUserRole record, SysUserRoleExample example) throws SQLException;

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table SYS_USER_ROLE
   * 
   * @ibatorgenerated Thu Nov 24 14:28:04 CST 2011
   */
  int updateByExample(SysUserRole record, SysUserRoleExample example) throws SQLException;

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table SYS_USER_ROLE
   * 
   * @ibatorgenerated Thu Nov 24 14:28:04 CST 2011
   */
  int updateByPrimaryKeySelective(SysUserRole record) throws SQLException;

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table SYS_USER_ROLE
   * 
   * @ibatorgenerated Thu Nov 24 14:28:04 CST 2011
   */
  int updateByPrimaryKey(SysUserRole record) throws SQLException;
}
