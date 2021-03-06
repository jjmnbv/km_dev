package com.pltfm.sys.dao.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.kmzyc.commons.page.Page;
import com.pltfm.sys.dao.SysRoleDAO;
import com.pltfm.sys.model.SysRole;
import com.pltfm.sys.model.SysRoleExample;

@Component(value = "sysRoleDAO")
public class SysRoleDAOImpl implements SysRoleDAO {
  @Resource(name = "sqlMapClient")
  /**
   * 数据库操作对象
   */
  private SqlMapClient sqlMapClient;

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table SYS_ROLE
   *
   * @ibatorgenerated Thu Nov 24 14:28:04 CST 2011
   */
  @Override
public int countByExample(SysRoleExample example) throws SQLException {
    Integer count =
        (Integer) sqlMapClient.queryForObject("SYS_ROLE.ibatorgenerated_countByExample", example);
    return count;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table SYS_ROLE
   *
   * @ibatorgenerated Thu Nov 24 14:28:04 CST 2011
   */
  @Override
public int deleteByExample(SysRoleExample example) throws SQLException {
    int rows = sqlMapClient.delete("SYS_ROLE.ibatorgenerated_deleteByExample", example);
    return rows;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table SYS_ROLE
   *
   * @ibatorgenerated Thu Nov 24 14:28:04 CST 2011
   */
  @Override
public int deleteByPrimaryKey(Integer roleId) throws SQLException {
    SysRole key = new SysRole();
    key.setRoleId(roleId);
    int rows = sqlMapClient.delete("SYS_ROLE.ibatorgenerated_deleteByPrimaryKey", key);
    return rows;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table SYS_ROLE
   *
   * @ibatorgenerated Thu Nov 24 14:28:04 CST 2011
   */
  @Override
public Integer insert(SysRole record) throws SQLException {
    Object newKey = sqlMapClient.insert("SYS_ROLE.ibatorgenerated_insert", record);
    return (Integer) newKey;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table SYS_ROLE
   *
   * @ibatorgenerated Thu Nov 24 14:28:04 CST 2011
   */
  @Override
public Integer insertSelective(SysRole record) throws SQLException {
    Object newKey = sqlMapClient.insert("SYS_ROLE.ibatorgenerated_insertSelective", record);
    return (Integer) newKey;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table SYS_ROLE
   *
   * @ibatorgenerated Thu Nov 24 14:28:04 CST 2011
   */
  @Override
@SuppressWarnings("unchecked")
  public List<SysRole> selectByExample(SysRoleExample example) throws SQLException {
    List<SysRole> list =
        sqlMapClient.queryForList("SYS_ROLE.ibatorgenerated_selectByExample", example);
    return list;
  }

  // 角色列表分页
  @Override
public Page searchSysRoleList(Page page, SysRole record) throws SQLException {
    // 获取角色列表总数
    Integer countResult =
        (Integer) sqlMapClient.queryForObject("SYS_ROLE.ibatorgenerated_selectListCount", record);
    int pagecount = 1;
    if (countResult > 1) pagecount = (countResult - 1) / page.getPageSize() + 1; //
    page.setRecordCount(countResult); //
    page.setPageCount(pagecount); //
    // 角色列表分页
    List pageList = sqlMapClient.queryForList("SYS_ROLE.ibatorgenerated_selectList", record);
    page.setDataList(pageList);
    return page;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table SYS_ROLE
   *
   * @ibatorgenerated Thu Nov 24 14:28:04 CST 2011
   */
  @Override
public SysRole selectByPrimaryKey(Integer roleId) throws SQLException {
    SysRole key = new SysRole();
    key.setRoleId(roleId);
    SysRole record =
        (SysRole) sqlMapClient.queryForObject("SYS_ROLE.ibatorgenerated_selectByPrimaryKey", key);
    return record;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table SYS_ROLE
   *
   * @ibatorgenerated Thu Nov 24 14:28:04 CST 2011
   */
  @Override
public int updateByExampleSelective(SysRole record, SysRoleExample example) throws SQLException {
    UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
    int rows = sqlMapClient.update("SYS_ROLE.ibatorgenerated_updateByExampleSelective", parms);
    return rows;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table SYS_ROLE
   *
   * @ibatorgenerated Thu Nov 24 14:28:04 CST 2011
   */
  @Override
public int updateByExample(SysRole record, SysRoleExample example) throws SQLException {
    UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
    int rows = sqlMapClient.update("SYS_ROLE.ibatorgenerated_updateByExample", parms);
    return rows;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table SYS_ROLE
   *
   * @ibatorgenerated Thu Nov 24 14:28:04 CST 2011
   */
  @Override
public int updateByPrimaryKeySelective(SysRole record) throws SQLException {
    int rows = sqlMapClient.update("SYS_ROLE.ibatorgenerated_updateByPrimaryKeySelective", record);
    return rows;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table SYS_ROLE
   *
   * @ibatorgenerated Thu Nov 24 14:28:04 CST 2011
   */
  @Override
public int updateByPrimaryKey(SysRole record) throws SQLException {
    int rows = sqlMapClient.update("SYS_ROLE.ibatorgenerated_updateByPrimaryKey", record);
    return rows;
  }

  /**
   * This class was generated by Apache iBATIS ibator. This class corresponds to the database table
   * SYS_ROLE
   *
   * @ibatorgenerated Thu Nov 24 14:28:04 CST 2011
   */
  private static class UpdateByExampleParms extends SysRoleExample {
    private Object record;

    public UpdateByExampleParms(Object record, SysRoleExample example) {
      super(example);
      this.record = record;
    }

    public Object getRecord() {
      return record;
    }
  }
}
