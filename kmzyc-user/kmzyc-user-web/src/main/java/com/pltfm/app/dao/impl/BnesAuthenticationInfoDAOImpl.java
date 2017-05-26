package com.pltfm.app.dao.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.pltfm.app.dao.BnesAuthenticationInfoDAO;
import com.pltfm.app.vobject.BnesAuthenticationInfo;
import com.pltfm.app.vobject.BnesAuthenticationInfoExample;
import com.pltfm.sys.model.SysModelUtil;

/**
 * 实名认证信息处理类
 * 
 * @author cjm
 * @since 2013-8-1
 */
@Component(value = "bnesAuthenticationInfoDAO")
public class BnesAuthenticationInfoDAOImpl implements BnesAuthenticationInfoDAO {

  /**
   * 数据库操作对象
   */
  @Resource(name = "sqlMapClient")
  private SqlMapClient sqlMapClient;



  /**
   * 添加实名认证信息
   * 
   * @param record 实名认证信息实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  public Integer insert(BnesAuthenticationInfo record) throws SQLException {
    Object newKey = sqlMapClient.insert("BNES_AUTHENTICATION_INFO.abatorgenerated_insert", record);
    return (Integer) newKey;
  }

  /**
   * 修改实名认证信息
   * 
   * @param record 实名认证信息实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  public int updateByPrimaryKey(BnesAuthenticationInfo record) throws SQLException {
    int rows =
        sqlMapClient.update("BNES_AUTHENTICATION_INFO.abatorgenerated_updateByPrimaryKey", record);
    return rows;
  }

  /**
   * 动态修改实名认证信息
   * 
   * @param record 实名认证信息实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  public int updateByPrimaryKeySelective(BnesAuthenticationInfo record) throws SQLException {
    int rows = sqlMapClient
        .update("BNES_AUTHENTICATION_INFO.abatorgenerated_updateByPrimaryKeySelective", record);
    return rows;
  }

  /**
   * 根据实名认证主键查询单条实名认证信息
   * 
   * @param nCommercialTenantId 实名认证主键
   * @return 返回值
   * @throws SQLException sql异常
   */
  public List selectByExample(BnesAuthenticationInfoExample example) throws SQLException {
    List list = sqlMapClient
        .queryForList("BNES_AUTHENTICATION_INFO.abatorgenerated_selectByExample", example);
    return list;
  }

  /**
   * 根据实名认证主键查询单条实名认证信息
   * 
   * @param nCommercialTenantId 实名认证主键
   * @return 返回值
   * @throws SQLException sql异常
   */
  public BnesAuthenticationInfo selectByPrimaryKey(Integer authenticationId) throws SQLException {
    BnesAuthenticationInfo key = new BnesAuthenticationInfo();
    key.setAuthenticationId(authenticationId);
    BnesAuthenticationInfo record = (BnesAuthenticationInfo) sqlMapClient
        .queryForObject("BNES_AUTHENTICATION_INFO.abatorgenerated_selectByPrimaryKey", key);
    return record;
  }

  /**
   * 根据实名认证主键查询单条实名认证信息
   * 
   * @param nCommercialTenantId 实名认证主键
   * @return 返回值
   * @throws SQLException sql异常
   */
  public int deleteByExample(BnesAuthenticationInfoExample example) throws SQLException {
    int rows =
        sqlMapClient.delete("BNES_AUTHENTICATION_INFO.abatorgenerated_deleteByExample", example);
    return rows;
  }

  /**
   * 按实名认证信息条件进行删除
   * 
   * @param example 实名认证信息条件
   * @return 返回值
   * @throws SQLException sql异常
   */
  public int deleteByPrimaryKey(Integer authenticationId) throws SQLException {
    BnesAuthenticationInfo key = new BnesAuthenticationInfo();
    key.setAuthenticationId(authenticationId);
    int rows =
        sqlMapClient.delete("BNES_AUTHENTICATION_INFO.abatorgenerated_deleteByPrimaryKey", key);
    return rows;
  }

  /**
   * 根据实名认证主键删除实名认证信息
   * 
   * @param nCommercialTenantId 实名认证主键
   * @return 返回值
   * @throws SQLException sql异常
   */
  public int countByExample(BnesAuthenticationInfoExample example) throws SQLException {
    Integer count = (Integer) sqlMapClient
        .queryForObject("BNES_AUTHENTICATION_INFO.abatorgenerated_countByExample", example);
    return count.intValue();
  }

  /**
   * 按实名认证信息条件查询总条数
   * 
   * @param example 实名认证信息条件
   * @return 返回值
   * @throws SQLException sql异常
   */
  public int updateByExampleSelective(BnesAuthenticationInfo record,
      BnesAuthenticationInfoExample example) throws SQLException {
    UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
    int rows = sqlMapClient
        .update("BNES_AUTHENTICATION_INFO.abatorgenerated_updateByExampleSelective", parms);
    return rows;
  }

  /**
   * 动态按实名认证信息条件进行修改
   * 
   * @param record 实名认证信息实体
   * @param example 实名认证信息条件
   * @return 返回值
   * @throws SQLException sql异常
   */
  public int updateByExample(BnesAuthenticationInfo record, BnesAuthenticationInfoExample example)
      throws SQLException {
    UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
    int rows =
        sqlMapClient.update("BNES_AUTHENTICATION_INFO.abatorgenerated_updateByExample", parms);
    return rows;
  }

  /**
   * 按条件查询账户信息总数量
   * 
   * @param vo
   * @return 返回值
   */
  @Override
  public int selectCountByVo(BnesAuthenticationInfo vo) throws SQLException {
    List list = sqlMapClient.queryForList("BNES_AUTHENTICATION_INFO.getAccountInfoCount", vo);

    SysModelUtil countResult = (SysModelUtil) list.get(0);
    // 总条数
    int recs = countResult.getTheCount().intValue();
    return recs;
  }

  /**
   * 根据vo条件查询分类信息page
   * 
   * @param page 分页类
   * @param vo 个人个性信息实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  @Override
  public List selectPageByVo(BnesAuthenticationInfo vo) throws SQLException {
    List pageList = sqlMapClient.queryForList("BNES_AUTHENTICATION_INFO.searchPageByVo", vo);
    return pageList;
  }


  /**
   * This class was generated by Abator for iBATIS. This class corresponds to the database table
   * BNES_AUTHENTICATION_INFO
   * 
   * @abatorgenerated Thu Aug 01 14:24:09 CST 2013 修改条件参数类
   */
  private static class UpdateByExampleParms extends BnesAuthenticationInfoExample {
    private Object record;

    public UpdateByExampleParms(Object record, BnesAuthenticationInfoExample example) {
      super(example);
      this.record = record;
    }

    public Object getRecord() {
      return record;
    }
  }

  public SqlMapClient getSqlMapClient() {
    return sqlMapClient;
  }

  public void setSqlMapClient(SqlMapClient sqlMapClient) {
    this.sqlMapClient = sqlMapClient;
  }
}
