package com.pltfm.app.dao.impl;


import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.pltfm.app.dao.CommercialTenantBasicCopyDAO;
import com.pltfm.app.dataobject.CommercialTenantBasicCopyDO;
import com.pltfm.app.vobject.AreaDict;

/**
 * 数据访问对象实现类
 * 
 * @since 2014-07-21
 */
@Component(value = "commercialTenantBasicCopyDAO")
public class CommercialTenantBasicCopyDAOImpl implements CommercialTenantBasicCopyDAO {

  @Resource(name = "sqlMapClient")
  private SqlMapClient sqlMapClient;



  /**
   * 插入数据
   * 
   * @param commercialTenantBasicCopyDO
   * @return 插入数据的主键
   */
  public BigDecimal insertCommercialTenantBasicCopyDO(
      CommercialTenantBasicCopyDO commercialTenantBasicCopyDO) {
    Object N_COMMERCIAL_COPY_ID = null;
    try {
      N_COMMERCIAL_COPY_ID =
          sqlMapClient.insert("CommercialTenantBasicCopy.insert", commercialTenantBasicCopyDO);
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return (BigDecimal) N_COMMERCIAL_COPY_ID;
  }

  /* *//**
        * 统计记录数
        * 
        * @param commercialTenantBasicCopyDO
        * @return 查出的记录数
        */
  /*
   * public Integer countCommercialTenantBasicCopyDOByExample(CommercialTenantBasicCopyDO
   * commercialTenantBasicCopyDO) { Integer count = (Integer)
   * getSqlMapClientTemplate().queryForObject("CommercialTenantBasicCopy.countByDOExample",
   * commercialTenantBasicCopyDO); return count; }
   * 
   *//**
     * 更新记录
     * 
     * @param commercialTenantBasicCopyDO
     * @return 受影响的行数
     */
  /*
   * public Integer updateCommercialTenantBasicCopyDO(CommercialTenantBasicCopyDO
   * commercialTenantBasicCopyDO) { int result =
   * getSqlMapClientTemplate().update("CommercialTenantBasicCopy.update",
   * commercialTenantBasicCopyDO); return result; }
   * 
   *//**
     * 获取对象列表
     * 
     * @param commercialTenantBasicCopyDO
     * @return 对象列表
     */
  /*
   * @SuppressWarnings("unchecked") public List<CommercialTenantBasicCopyDO>
   * findListByExample(CommercialTenantBasicCopyDO commercialTenantBasicCopyDO) {
   * List<CommercialTenantBasicCopyDO> list =
   * getSqlMapClientTemplate().queryForList("CommercialTenantBasicCopy.findListByDO",
   * commercialTenantBasicCopyDO); return list; }
   * 
   *//**
     * 根据主键获取commercialTenantBasicCopyDO
     * 
     * @param nCommercialCopyId
     * @return commercialTenantBasicCopyDO
     */

  /*
   * public CommercialTenantBasicCopyDO findCommercialTenantBasicCopyDOByPrimaryKey(BigDecimal
   * nCommercialCopyId) { CommercialTenantBasicCopyDO commercialTenantBasicCopyDO =
   * (CommercialTenantBasicCopyDO)
   * getSqlMapClientTemplate().queryForObject("CommercialTenantBasicCopy.findByPrimaryKey",
   * nCommercialCopyId); return commercialTenantBasicCopyDO; }
   * 
   *//**
     * 删除记录
     * 
     * @param nCommercialCopyId
     * @return 受影响的行数
     */
  public Integer deleteCommercialTenantBasicCopyDOByPrimaryKey(BigDecimal commercialTenantId) {
    Integer rows = 0;
    try {
      rows = (Integer) sqlMapClient.delete("CommercialTenantBasicCopy.deleteByPrimaryKey",
          commercialTenantId);
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return rows;
  }


  public SqlMapClient getSqlMapClient() {
    return sqlMapClient;
  }

  public void setSqlMapClient(SqlMapClient sqlMapClient) {
    this.sqlMapClient = sqlMapClient;
  }

  public List selectListCommercialTenantBasicCopy(
      CommercialTenantBasicCopyDO commercialTenantBasicCopyDO) throws SQLException {
    // TODO Auto-generated method stub
    List<CommercialTenantBasicCopyDO> list = sqlMapClient
        .queryForList("CommercialTenantBasicCopy.findListByDO", commercialTenantBasicCopyDO);
    return list;
  }

  public Integer selectListCommercialTenantBasicCopyCount(
      CommercialTenantBasicCopyDO commercialTenantBasicCopyDO) throws SQLException {
    // TODO Auto-generated method stub
    Integer count = (Integer) sqlMapClient
        .queryForObject("CommercialTenantBasicCopy.countByDOExample", commercialTenantBasicCopyDO);
    return count;
  }

  @Override
  public CommercialTenantBasicCopyDO queryCommercialTenantBasicCopyDO(
      BigDecimal getnCommercialCopyId) throws SQLException {
    // TODO Auto-generated method stub
    CommercialTenantBasicCopyDO commercialTenantBasicCopyDO =
        (CommercialTenantBasicCopyDO) sqlMapClient
            .queryForObject("CommercialTenantBasicCopy.findByPrimaryKey", getnCommercialCopyId);
    return commercialTenantBasicCopyDO;
  }

  @Override
  public void updateBasicStatus(CommercialTenantBasicCopyDO commercialTenantBasicCopyDO)
      throws SQLException {
    // TODO Auto-generated method stub
    sqlMapClient.update("CommercialTenantBasicCopy.update", commercialTenantBasicCopyDO);
  }

  /**
   * 更改采购商变更审核信息
   *
   *
   */
  @Override
  public Integer updateCopyStatus(CommercialTenantBasicCopyDO commercialTenantBasicCopyDO)
      throws SQLException {
    return sqlMapClient.update("CommercialTenantBasicCopy.copyUpdate", commercialTenantBasicCopyDO);
  }

  @Override
  public void updatePass(CommercialTenantBasicCopyDO commercialTenantBasicCopyDO)
      throws SQLException {


    sqlMapClient.update("CommercialTenantBasicCopy.updateCommercialTenantBasic",
        commercialTenantBasicCopyDO);

  }

  @Override
  public List<AreaDict> selectAreaByAreaId(Integer areaId) throws SQLException {
    // TODO Auto-generated method stub
    return sqlMapClient.queryForList("CommercialTenantBasicCopy.selectArea", areaId);
  }


}
