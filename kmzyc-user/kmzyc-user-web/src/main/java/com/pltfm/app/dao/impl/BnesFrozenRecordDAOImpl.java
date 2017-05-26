package com.pltfm.app.dao.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.pltfm.app.dao.BnesFrozenRecordDAO;
import com.pltfm.app.vobject.BnesFrozenRecord;

/**
 * 账户冻结
 * 
 * @author gwl
 * @since 2013-07-08
 */
@Component(value = "bnesFrozenRecordDAO")
public class BnesFrozenRecordDAOImpl implements BnesFrozenRecordDAO {
  @Resource(name = "sqlMapClient")
  private SqlMapClient sqlMapClient;

  public SqlMapClient getSqlMapClient() {
    return sqlMapClient;
  }

  public void setSqlMapClient(SqlMapClient sqlMapClient) {
    this.sqlMapClient = sqlMapClient;
  }

  /**
   * 
   * 
   * 添加账户冻结
   * 
   */
  public Integer insert(BnesFrozenRecord record) throws SQLException {
    Object newKey = sqlMapClient.insert("BNES_FROZEN_RECORD.ibatorgenerated_insert", record);
    return (Integer) newKey;
  }

  /**
   * 
   * 
   * 删除账户冻结
   * 
   */
  public int delete(BnesFrozenRecord record) throws SQLException {
    return sqlMapClient.delete("BNES_FROZEN_RECORD.ibatorgenerated_delete", record);
  }

  /**
   * 
   * 
   * 修改账户冻结
   * 
   */
  public int update(BnesFrozenRecord record) throws SQLException {
    return sqlMapClient.update("BNES_FROZEN_RECORD.ibatorgenerated_update", record);
  }

  /**
   * 按条件查询账户冻结信息总数量
   * 
   * @param vo
   * @return 返回值
   */
  public int selectCountByVo(BnesFrozenRecord bnesFrozenRecord) throws SQLException {
    Integer rows = (Integer) sqlMapClient.queryForObject("BNES_FROZEN_RECORD.selectCountByVo",
        bnesFrozenRecord);
    return rows;
  }

  /**
   * 根据vo条件查询分类信息page
   * 
   * @param page 分页类
   * @param vo 信息实体
   * @return 返回值
   * @throws SQLException sql异常
   */

  public List selectPageByVo(BnesFrozenRecord bnesFrozenRecord) throws SQLException {
    List pageList =
        sqlMapClient.queryForList("BNES_FROZEN_RECORD.searchPageByVo", bnesFrozenRecord);
    return pageList;
  }

  /**
   * 跟据账户冻结id查询账户冻结信息
   * 
   * @param Rank 账户冻结信息实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  public BnesFrozenRecord getFrozenRecordId(Integer frozenRecordId) throws SQLException {
    BnesFrozenRecord bnesFrozenRecord = (BnesFrozenRecord) sqlMapClient
        .queryForObject("BNES_FROZEN_RECORD.ibatorgenerated_getFrozenRecordId", frozenRecordId);
    return bnesFrozenRecord;
  }

  /**
   * 通过账户冻结实体查询账户冻结信息
   * 
   * @param bnesFrozenRecord 账户冻结解冻实体
   * @return
   * @throws SQLException 异常
   */
  public Integer selectCountByAccount(BnesFrozenRecord bnesFrozenRecord) throws SQLException {
    Integer rows = (Integer) sqlMapClient.queryForObject("BNES_FROZEN_RECORD.selectCountByAccount",
        bnesFrozenRecord);
    return rows;
  }

  /**
   * 分页查询账户冻结解冻信息
   * 
   * @param bnesFrozenRecord 账户冻结解冻实体
   * @return
   * @throws SQLException 异常
   */
  public List selectPageByAccount(BnesFrozenRecord bnesFrozenRecord) throws SQLException {
    return sqlMapClient.queryForList("BNES_FROZEN_RECORD.selectPageByAccount", bnesFrozenRecord);
  }

  /**
   * 通过登录账号查询登录账户冻结解冻记录信息
   * 
   * @param bnesFrozenRecord
   * @return
   * @throws SQLException
   */
  public BnesFrozenRecord selectByLoginAccount(BnesFrozenRecord bnesFrozenRecord)
      throws SQLException {
    BnesFrozenRecord record = (BnesFrozenRecord) sqlMapClient.queryForObject(
        "BNES_FROZEN_RECORD.iabatorgenerated_selectByLoginAccount", bnesFrozenRecord);
    return record;
  }
}
