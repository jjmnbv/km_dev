package com.pltfm.sys.dao;

import com.pltfm.sys.model.SysDept;
import com.pltfm.sys.model.SysDeptExample;

import java.sql.SQLException;
import java.util.List;

public interface SysDeptDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_DEPT
     *
     * @ibatorgenerated Thu Nov 24 14:28:04 CST 2011
     */
    int countByExample(SysDeptExample example) throws SQLException;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_DEPT
     *
     * @ibatorgenerated Thu Nov 24 14:28:04 CST 2011
     */
    int deleteByExample(SysDeptExample example) throws SQLException;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_DEPT
     *
     * @ibatorgenerated Thu Nov 24 14:28:04 CST 2011
     */
    int deleteByPrimaryKey(Integer deptId) throws SQLException;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_DEPT
     *
     * @ibatorgenerated Thu Nov 24 14:28:04 CST 2011
     */
    Integer insert(SysDept record) throws SQLException;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_DEPT
     *
     * @ibatorgenerated Thu Nov 24 14:28:04 CST 2011
     */
    Integer insertSelective(SysDept record) throws SQLException;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_DEPT
     *
     * @ibatorgenerated Thu Nov 24 14:28:04 CST 2011
     */
    List<SysDept> selectByExample(SysDeptExample example) throws SQLException;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_DEPT
     *
     * @ibatorgenerated Thu Nov 24 14:28:04 CST 2011
     */
    SysDept selectByPrimaryKey(Integer deptId) throws SQLException;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_DEPT
     *
     * @ibatorgenerated Thu Nov 24 14:28:04 CST 2011
     */
    int updateByExampleSelective(SysDept record, SysDeptExample example) throws SQLException;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_DEPT
     *
     * @ibatorgenerated Thu Nov 24 14:28:04 CST 2011
     */
    int updateByExample(SysDept record, SysDeptExample example) throws SQLException;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_DEPT
     *
     * @ibatorgenerated Thu Nov 24 14:28:04 CST 2011
     */
    int updateByPrimaryKeySelective(SysDept record) throws SQLException;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_DEPT
     *
     * @ibatorgenerated Thu Nov 24 14:28:04 CST 2011
     */
    int updateByPrimaryKey(SysDept record) throws SQLException;
}