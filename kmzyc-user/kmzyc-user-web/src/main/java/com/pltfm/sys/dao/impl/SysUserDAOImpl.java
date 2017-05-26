package com.pltfm.sys.dao.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.kmzyc.commons.page.Page;
import com.pltfm.sys.dao.SysUserDAO;
import com.pltfm.sys.model.SysModelUtil;
import com.pltfm.sys.model.SysUser;
import com.pltfm.sys.model.SysUserExample;
import com.pltfm.sys.model.SysUserRole;

@Component(value = "sysUserDAO")
public class SysUserDAOImpl implements SysUserDAO {
  @Resource(name = "sqlMapClient")
  /**
   * 数据库操作对象
   */
  private SqlMapClient sqlMapClient;

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table SYS_USER
   *
   * @ibatorgenerated Thu Nov 24 14:28:04 CST 2011
   */
  @Override
public int countByExample(SysUserExample example) throws SQLException {
    Integer count =
        (Integer) sqlMapClient.queryForObject("SYS_USER.ibatorgenerated_countByExample", example);
    return count;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table SYS_USER
   *
   * @ibatorgenerated Thu Nov 24 14:28:04 CST 2011
   */
  @Override
public int deleteByExample(SysUserExample example) throws SQLException {
    int rows = sqlMapClient.delete("SYS_USER.ibatorgenerated_deleteByExample", example);
    return rows;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table SYS_USER
   *
   * @ibatorgenerated Thu Nov 24 14:28:04 CST 2011
   */
  @Override
public int deleteByPrimaryKey(Integer userId) throws SQLException {
    SysUser key = new SysUser();
    key.setUserId(userId);
    int rows = sqlMapClient.delete("SYS_USER.ibatorgenerated_deleteByPrimaryKey", key);
    return rows;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table SYS_USER
   *
   * @ibatorgenerated Thu Nov 24 14:28:04 CST 2011
   */
  @Override
public Integer insert(SysUser record) throws SQLException {
    Object newKey = sqlMapClient.insert("SYS_USER.ibatorgenerated_insert", record);
    return (Integer) newKey;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table SYS_USER
   *
   * @ibatorgenerated Thu Nov 24 14:28:04 CST 2011
   */
  @Override
public Integer insertSelective(SysUser record) throws SQLException {
    Object newKey = sqlMapClient.insert("SYS_USER.ibatorgenerated_insertSelective", record);
    return (Integer) newKey;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table SYS_USER
   *
   * @ibatorgenerated Thu Nov 24 14:28:04 CST 2011
   */
  @Override
@SuppressWarnings("unchecked")
  public List<SysUser> selectByExample(SysUserExample example) throws SQLException {
    List<SysUser> list =
        sqlMapClient.queryForList("SYS_USER.ibatorgenerated_selectByExample", example);
    return list;
  }

  @Override
@SuppressWarnings("unchecked")
  public List<SysUserRole> selectByUser(SysUser example) throws SQLException {
    List<SysUserRole> list =
        sqlMapClient.queryForList("SYS_USER_ROLE.ibatorgenerated_selectByUserId", example);
    return list;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table SYS_USER
   *
   * @ibatorgenerated Thu Nov 24 14:28:04 CST 2011
   */
  @Override
public SysUser selectByPrimaryKey(Integer userId) throws SQLException {
    SysUser key = new SysUser();
    key.setUserId(userId);
    SysUser record =
        (SysUser) sqlMapClient.queryForObject("SYS_USER.ibatorgenerated_selectByPrimaryKey", key);
    return record;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table SYS_USER
   *
   * @ibatorgenerated Thu Nov 24 14:28:04 CST 2011
   */
  @Override
public int updateByExampleSelective(SysUser record, SysUserExample example) throws SQLException {
    UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
    int rows = sqlMapClient.update("SYS_USER.ibatorgenerated_updateByExampleSelective", parms);
    return rows;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table SYS_USER
   *
   * @ibatorgenerated Thu Nov 24 14:28:04 CST 2011
   */
  @Override
public int updateByExample(SysUser record, SysUserExample example) throws SQLException {
    UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
    int rows = sqlMapClient.update("SYS_USER.ibatorgenerated_updateByExample", parms);
    return rows;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table SYS_USER
   *
   * @ibatorgenerated Thu Nov 24 14:28:04 CST 2011
   */
  @Override
public int updateByPrimaryKeySelective(SysUser record) throws SQLException {
    int rows = sqlMapClient.update("SYS_USER.ibatorgenerated_updateByPrimaryKeySelective", record);
    return rows;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table SYS_USER
   *
   * @ibatorgenerated Thu Nov 24 14:28:04 CST 2011
   */
  @Override
public int updateByPrimaryKey(SysUser record) throws SQLException {
    int rows = sqlMapClient.update("SYS_USER.ibatorgenerated_updateByPrimaryKey", record);
    return rows;
  }

  /**
   * This class was generated by Apache iBATIS ibator. This class corresponds to the database table
   * SYS_USER
   *
   * @ibatorgenerated Thu Nov 24 14:28:04 CST 2011
   */
  private static class UpdateByExampleParms extends SysUserExample {
    private Object record;

    public UpdateByExampleParms(Object record, SysUserExample example) {
      super(example);
      this.record = record;
    }

    public Object getRecord() {
      return record;
    }
  }



  /******************* get user list **********************/
  @Override
public List selectListByVo(SysUser vo) throws SQLException {
    List list = sqlMapClient.queryForList("sys_user.searchListByVo", vo);
    return list;
  }


  /******************* getuser page **********************/
  @Override
public Page selectPageByVo(Page page, SysUser vo) throws SQLException {
    // System.out.println("************** begin get usercount...");
    SysModelUtil countResult =
        (SysModelUtil) sqlMapClient.queryForObject("SYS_USER.getSysUserCount", vo);
    int recs = countResult.getTheCount().intValue();
    // System.out.println("*************** recs="+recs);
    int pagecount = 1;
    if (recs > 1) pagecount = (recs - 1) / page.getPageSize() + 1; //
    page.setRecordCount(recs); //
    page.setPageCount(pagecount); //
    // System.out.println("************** begin get page...");
    List pageList = sqlMapClient.queryForList("SYS_USER.searchPageByVo", vo);
    // System.out.println("***************************** page size()="+pageList.size());
    page.setDataList(pageList);
    return page;
  }


  /******************* select user's roleList by userId **********************************/
  @Override
public List selectUserRolesList(Integer userId) throws SQLException {
    SysUser key = new SysUser();
    key.setUserId( Integer.valueOf(userId));
    List list = sqlMapClient.queryForList("SYS_USER.selectUsersRoleList", key);
    return list;
  }



  /******************* select userList by roleId **********************************/
  @Override
public List getUserListByRoleId(Integer roleId) throws SQLException {
    List list = sqlMapClient.queryForList("SYS_USER.getUserListByRoleId", roleId);
    return list;
  }
}