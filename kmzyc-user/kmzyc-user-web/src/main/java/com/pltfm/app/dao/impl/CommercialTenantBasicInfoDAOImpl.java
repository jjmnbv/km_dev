package com.pltfm.app.dao.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.pltfm.app.dao.CommercialTenantBasicInfoDAO;
import com.pltfm.app.vobject.CommercialTenantBasicInfo;
import com.pltfm.app.vobject.CommercialTenantBasicInfoExample;
import com.pltfm.app.vobject.Customer;
import com.pltfm.app.vobject.LoginRoseRelQuery;
import com.pltfm.sys.model.SysModelUtil;

/**
 * 商户基本信息处理类
 * 
 * @author cjm
 * @since 2013-7-9
 */
@Component(value = "commercialTenantBasicInfoDAO")
public class CommercialTenantBasicInfoDAOImpl implements CommercialTenantBasicInfoDAO {
  /**
   * 数据库操作对象
   */
  @Resource(name = "sqlMapClient")
  private SqlMapClient sqlMapClient;


  /**
   * 添加商户基本信息
   * 
   * @param record 商户基本信息实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  public Integer insert(CommercialTenantBasicInfo record) throws SQLException {
    Object newKey =
        sqlMapClient.insert("COMMERCIAL_TENANT_BASIC_INFO.abatorgenerated_insert", record);
    return (Integer) newKey;
  }

  /**
   * 修改商户基本信息
   * 
   * @param record 商户基本信息实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  public int updateByPrimaryKey(CommercialTenantBasicInfo record) throws SQLException {
    int rows = sqlMapClient
        .update("COMMERCIAL_TENANT_BASIC_INFO.abatorgenerated_updateByPrimaryKey", record);
    return rows;
  }

  /**
   * 动态修改商户基本信息
   * 
   * @param record 商户基本信息实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  public int updateByPrimaryKeySelective(CommercialTenantBasicInfo record) throws SQLException {
    int rows = sqlMapClient
        .update("COMMERCIAL_TENANT_BASIC_INFO.abatorgenerated_updateByPrimaryKeySelective", record);
    return rows;
  }

  /**
   * 按商户基本信息条件查询
   * 
   * @param example 商户基本信息条件
   * @return 返回值
   * @throws SQLException sql异常
   */
  public List selectByExample(CommercialTenantBasicInfoExample example) throws SQLException {
    List list = sqlMapClient
        .queryForList("COMMERCIAL_TENANT_BASIC_INFO.abatorgenerated_selectByExample", example);
    return list;
  }

  /**
   * 根据商户主键查询单条商户基本信息
   * 
   * @param nCommercialTenantId 商户主键
   * @return 返回值
   * @throws SQLException sql异常
   */
  public CommercialTenantBasicInfo selectByPrimaryKey(Integer nCommercialTenantId)
      throws SQLException {
    CommercialTenantBasicInfo key = new CommercialTenantBasicInfo();
    key.setN_CommercialTenantId(nCommercialTenantId);
    CommercialTenantBasicInfo record = (CommercialTenantBasicInfo) sqlMapClient
        .queryForObject("COMMERCIAL_TENANT_BASIC_INFO.abatorgenerated_selectByPrimaryKey", key);
    return record;
  }

  /**
   * 按商户基本信息条件进行删除
   * 
   * @param example 商户基本信息条件
   * @return 返回值
   * @throws SQLException sql异常
   */
  public int deleteByExample(CommercialTenantBasicInfoExample example) throws SQLException {
    int rows = sqlMapClient.delete("COMMERCIAL_TENANT_BASIC_INFO.abatorgenerated_deleteByExample",
        example);
    return rows;
  }

  /**
   * 根据商户主键删除商户基本信息
   * 
   * @param nCommercialTenantId 商户主键
   * @return 返回值
   * @throws SQLException sql异常
   */
  public int deleteByPrimaryKey(Integer nCommercialTenantId) throws SQLException {
    CommercialTenantBasicInfo key = new CommercialTenantBasicInfo();
    key.setN_CommercialTenantId(nCommercialTenantId);
    int rows =
        sqlMapClient.delete("COMMERCIAL_TENANT_BASIC_INFO.abatorgenerated_deleteByPrimaryKey", key);
    return rows;
  }

  /**
   * 按商户基本信息条件查询总条数
   * 
   * @param example 商户基本信息条件
   * @return 返回值
   * @throws SQLException sql异常
   */
  public int countByExample(CommercialTenantBasicInfoExample example) throws SQLException {
    Integer count = (Integer) sqlMapClient
        .queryForObject("COMMERCIAL_TENANT_BASIC_INFO.abatorgenerated_countByExample", example);
    return count.intValue();
  }

  /**
   * 动态按商户基本信息条件进行修改
   * 
   * @param record 商户基本信息实体
   * @param example 商户基本信息条件
   * @return 返回值
   * @throws SQLException sql异常
   */
  public int updateByExampleSelective(CommercialTenantBasicInfo record,
      CommercialTenantBasicInfoExample example) throws SQLException {
    UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
    int rows = sqlMapClient
        .update("COMMERCIAL_TENANT_BASIC_INFO.abatorgenerated_updateByExampleSelective", parms);
    return rows;
  }

  /**
   * 按商户基本信息条件进行修改
   * 
   * @param record 商户基本信息实体
   * @param example 商户基本信息条件
   * @return 返回值
   * @throws SQLException sql异常
   */
  public int updateByExample(CommercialTenantBasicInfo record,
      CommercialTenantBasicInfoExample example) throws SQLException {
    UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
    int rows =
        sqlMapClient.update("COMMERCIAL_TENANT_BASIC_INFO.abatorgenerated_updateByExample", parms);
    return rows;
  }

  /**
   * 更新注册采购商审核状态
   * 
   * @param commercialTenantBasicInfo
   * @return 返回值
   * @throws SQLException sql异常
   */
  public int updateByerStaus(CommercialTenantBasicInfo commercialTenantBasicInfo)
      throws SQLException {
    int rows = sqlMapClient.update("COMMERCIAL_TENANT_BASIC_INFO.updateBuerStaus",
        commercialTenantBasicInfo);
    return rows;
  }

  public int updateByer(CommercialTenantBasicInfo commercialTenantBasicInfo) throws SQLException {

    int rows =
        sqlMapClient.update("COMMERCIAL_TENANT_BASIC_INFO.updateBuer", commercialTenantBasicInfo);
    return rows;
  }

  /**
   * 按条件查询商户信息总数量
   * 
   * @param vo 商户信息实体
   * @return 返回值
   */
  @Override
  public int selectCountByVo(CommercialTenantBasicInfo vo) throws SQLException {
    List list = sqlMapClient
        .queryForList("COMMERCIAL_TENANT_BASIC_INFO.getCommercialTenantBasicInfoCount", vo);

    SysModelUtil countResult = (SysModelUtil) list.get(0);
    // 总条数
    int recs = countResult.getTheCount().intValue();
    return recs;
  }

  /**
   * 根据vo条件查询分类信息page
   * 
   * @param page 分页类
   * @param vo 商户信息实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  @Override
  public List selectPageByVo(CommercialTenantBasicInfo vo) throws SQLException {
    List pageList = sqlMapClient.queryForList("COMMERCIAL_TENANT_BASIC_INFO.searchPageByVo", vo);
    return pageList;
  }

  /**
   * 根据登录主键查询单条商户信息
   * 
   * @param nLoginId 登录信息ID
   * @return 返回值
   * @throws Exception 异常
   */
  @Override
  public CommercialTenantBasicInfo selectByPrimaryLoginInfo(Integer nLoginId) throws SQLException {
    CommercialTenantBasicInfo key = new CommercialTenantBasicInfo();
    key.setN_LoginId(nLoginId);
    CommercialTenantBasicInfo record = (CommercialTenantBasicInfo) sqlMapClient.queryForObject(
        "COMMERCIAL_TENANT_BASIC_INFO.abatorgenerated_selectByPrimaryLoginInfo", key);
    return record;
  }

  /**
   * 按商户ID查询商户信息
   * 
   * @param customer
   * @return
   * @throws SQLException
   */
  @Override
  public Customer selectByCustomer(Customer customer) throws SQLException {
    Customer record = (Customer) sqlMapClient
        .queryForObject("COMMERCIAL_TENANT_BASIC_INFO.abatorgenerated_selectByCustomer", customer);
    return record;
  }


  /**
   * This class was generated by Abator for iBATIS. This class corresponds to the database table
   * COMMERCIAL_TENANT_BASIC_INFO 修改条件参数类
   * 
   * @abatorgenerated Tue Jul 09 16:33:05 CST 2013
   */
  private static class UpdateByExampleParms extends CommercialTenantBasicInfoExample {
    private Object record;

    public UpdateByExampleParms(Object record, CommercialTenantBasicInfoExample example) {
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

  @Override
  public void verifyPass(CommercialTenantBasicInfo commercialTenantBasicInfo) throws SQLException {
    // TODO Auto-generated method stub
    sqlMapClient.update("COMMERCIAL_TENANT_BASIC_INFO.updateVerifyPass", commercialTenantBasicInfo);
  }

  @Override
  public void notPass(CommercialTenantBasicInfo commercialTenantBasicInfo) throws SQLException {

    sqlMapClient.update("COMMERCIAL_TENANT_BASIC_INFO.updateNotPass", commercialTenantBasicInfo);
  }

  @Override
  public LoginRoseRelQuery selectByNcustomerId(Integer commercialTenantId) throws SQLException {
    // TODO Auto-generated method stub
    return (LoginRoseRelQuery) sqlMapClient
        .queryForObject("COMMERCIAL_TENANT_BASIC_INFO.selectByNcustomerId", commercialTenantId);
  }


}
