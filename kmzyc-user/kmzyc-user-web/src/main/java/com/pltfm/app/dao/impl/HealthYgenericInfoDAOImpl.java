package com.pltfm.app.dao.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.pltfm.app.dao.HealthYgenericInfoDAO;
import com.pltfm.app.vobject.HealthYgenericInfo;
import com.pltfm.app.vobject.HealthYgenericInfoExample;

/**
 * 健康信息处理类
 * 
 * @author cjm
 * @since 2013-7-18
 */
@Component(value = "healthYgenericInfoDAO")
public class HealthYgenericInfoDAOImpl implements HealthYgenericInfoDAO {
  /**
   * 数据库操作对象
   */
  @Resource(name = "sqlMapClient")
  private SqlMapClient sqlMapClient;

  public SqlMapClient getSqlMapClient() {
    return sqlMapClient;
  }

  public void setSqlMapClient(SqlMapClient sqlMapClient) {
    this.sqlMapClient = sqlMapClient;
  }

  /**
   * 记录分布信息的总数
   * 
   * @param userLevel 健康实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  public int countByExample(HealthYgenericInfoExample example) throws SQLException {
    Integer count = (Integer) sqlMapClient
        .queryForObject("HEALTH_YGENERIC_INFO.ibatorgenerated_countByExample", example);
    return count.intValue();
  }

  /**
   * 删除健康信息
   * 
   * @param userLevel 健康实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  public int deleteByExample(HealthYgenericInfoExample example) throws SQLException {
    int rows = sqlMapClient.delete("HEALTH_YGENERIC_INFO.ibatorgenerated_deleteByExample", example);
    return rows;
  }

  /**
   * 跟据主键删除健康信息
   * 
   * @param userLevel 健康实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  public int deleteHealthYgenericId(Integer n_HealthYgenericId) throws SQLException {
    int rows = sqlMapClient.delete("HEALTH_YGENERIC_INFO.ibatorgenerated_deleteHealthYgenericId",
        n_HealthYgenericId);
    return rows;
  }

  /**
   * 跟据id删除健康信息
   * 
   * @param userLevel 健康实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  public int deleteByPrimaryKey(int nHealthYgenericId) throws SQLException {
    HealthYgenericInfo key = new HealthYgenericInfo();
    key.setN_HealthYgenericId(nHealthYgenericId);
    int rows = sqlMapClient.delete("HEALTH_YGENERIC_INFO.ibatorgenerated_deleteByPrimaryKey", key);
    return rows;
  }

  /**
   * 添加健康信息
   * 
   * @param userLevel 健康实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  public void insert(HealthYgenericInfo record) throws SQLException {
    sqlMapClient.insert("HEALTH_YGENERIC_INFO.ibatorgenerated_insert", record);
  }

  /**
   * 添加健康信息
   * 
   * @param userLevel 健康实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  public void insertSelective(HealthYgenericInfo record) throws SQLException {
    sqlMapClient.insert("HEALTH_YGENERIC_INFO.ibatorgenerated_insertSelective", record);
  }

  /**
   * 健康信息列表
   * 
   * @param userLevel 健康实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  public List selectByExample(HealthYgenericInfoExample example) throws SQLException {
    List list =
        sqlMapClient.queryForList("HEALTH_YGENERIC_INFO.ibatorgenerated_selectByExample", example);
    return list;
  }


  /**
   * 查询健康信息
   * 
   * @param userLevel 健康实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  public HealthYgenericInfo selectByPrimaryKey(int nHealthYgenericId) throws SQLException {
    HealthYgenericInfo key = new HealthYgenericInfo();
    key.setN_HealthYgenericId(nHealthYgenericId);
    HealthYgenericInfo record = (HealthYgenericInfo) sqlMapClient
        .queryForObject("HEALTH_YGENERIC_INFO.ibatorgenerated_selectByPrimaryKey", key);
    return record;
  }

  /**
   * 跟据登录id查询健康信息
   * 
   * @param userLevel 健康实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  public HealthYgenericInfo selectByPrimaryFk(int nLoginId) throws SQLException {
    HealthYgenericInfo key = new HealthYgenericInfo();
    key.setN_LoginId(nLoginId);
    HealthYgenericInfo record = (HealthYgenericInfo) sqlMapClient
        .queryForObject("HEALTH_YGENERIC_INFO.ibatorgenerated_selectByPrimaryFk", key);
    return record;
  }

  /**
   * 修改健康信息
   * 
   * @param userLevel 健康实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  public int updateByPrimaryKeySelective(HealthYgenericInfo record) throws SQLException {
    int rows = sqlMapClient
        .update("HEALTH_YGENERIC_INFO.ibatorgenerated_updateByPrimaryKeySelective", record);
    return rows;
  }

  public int insertHealthInfo(HealthYgenericInfo record) throws SQLException {
    int row = (Integer) sqlMapClient.insert("HEALTH_YGENERIC_INFO.ibatorgenerated_insert", record);
    return row;
  }

  /**
   * 修改健康信息
   * 
   * @param userLevel 健康实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  public int updateByPrimaryKey(HealthYgenericInfo record) throws SQLException {
    int rows =
        sqlMapClient.update("HEALTH_YGENERIC_INFO.ibatorgenerated_updateByPrimaryKey", record);
    return rows;
  }

  /**
   * 修改健康信息
   * 
   * @param userLevel 健康实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  private static class UpdateByExampleParms extends HealthYgenericInfoExample {
    private Object record;

    public UpdateByExampleParms(Object record, HealthYgenericInfoExample example) {
      super(example);
      this.record = record;
    }

    public Object getRecord() {
      return record;
    }
  }

}
