package com.pltfm.app.dao.impl;


import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.pltfm.app.dao.QualificationsApplyDAO;
import com.pltfm.app.vobject.QualificationsApplyDO;

/**
 * 数据访问对象实现类
 * 
 * @since 2014-07-07
 */

@Component(value = "qualificationsApplyDAO")
public class QualificationsApplyDAOImpl implements QualificationsApplyDAO {


  @Resource(name = "sqlMapClient")
  private SqlMapClient sqlMapClient;

  /**
   * 插入数据
   * 
   * @param qualificationsApplyDO
   * @return 插入数据的主键
   * @throws SQLException
   */
  public Integer insertQualificationsApplyDO(QualificationsApplyDO qualificationsApplyDO)
      throws SQLException {
    return (Integer) sqlMapClient.insert("QualificationsApply.insert", qualificationsApplyDO);

  }

  /**
   * 统计记录数
   * 
   * @param qualificationsApplyDO
   * @return 查出的记录数
   * @throws SQLException
   */
  public Integer countQualificationsApplyDOByExample(QualificationsApplyDO qualificationsApplyDO)
      throws SQLException {
    Integer count = (Integer) sqlMapClient.queryForObject("QualificationsApply.countByDOExample",
        qualificationsApplyDO);
    return count;
  }

  /**
   * 更新记录
   * 
   * @param qualificationsApplyDO
   * @return 受影响的行数
   * @throws SQLException
   */
  public Integer updateQualificationsApplyDO(QualificationsApplyDO qualificationsApplyDO)
      throws SQLException {
    int result = sqlMapClient.update("QualificationsApply.update", qualificationsApplyDO);
    return result;
  }

  /**
   * 获取对象列表
   * 
   * @param qualificationsApplyDO
   * @return 对象列表
   * @throws SQLException
   */
  @SuppressWarnings("unchecked")
  public List<QualificationsApplyDO> findListByExample(QualificationsApplyDO qualificationsApplyDO)
      throws SQLException {
    List<QualificationsApplyDO> list =
        sqlMapClient.queryForList("QualificationsApply.findListByDO", qualificationsApplyDO);
    return list;
  }

  /**
   * 根据主键获取qualificationsApplyDO
   * 
   * @param id
   * @return qualificationsApplyDO
   * @throws SQLException
   */
  public QualificationsApplyDO findQualificationsApplyDOByPrimaryKey(Integer id)
      throws SQLException {
    QualificationsApplyDO qualificationsApplyDO = (QualificationsApplyDO) sqlMapClient
        .queryForObject("QualificationsApply.findByPrimaryKey", id);
    return qualificationsApplyDO;
  }

  /**
   * 删除记录
   * 
   * @param id
   * @return 受影响的行数
   * @throws SQLException
   */
  public Integer deleteQualificationsApplyDOByPrimaryKey(Integer id) throws SQLException {
    Integer rows = (Integer) sqlMapClient.delete("QualificationsApply.deleteByPrimaryKey", id);
    return rows;
  }

  public SqlMapClient getSqlMapClient() {
    return sqlMapClient;
  }

  public void setSqlMapClient(SqlMapClient sqlMapClient) {
    this.sqlMapClient = sqlMapClient;
  }



}
