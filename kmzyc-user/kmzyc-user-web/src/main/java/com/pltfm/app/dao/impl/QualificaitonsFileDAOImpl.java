package com.pltfm.app.dao.impl;


import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.pltfm.app.dao.QualificaitonsFileDAO;
import com.pltfm.app.vobject.QualificaitonsFileDO;

/**
 * 数据访问对象实现类
 * 
 * @since 2014-07-07
 */

@Component(value = "qualificaitonsFileDAO")
public class QualificaitonsFileDAOImpl implements QualificaitonsFileDAO {


  @Resource(name = "sqlMapClient")
  private SqlMapClient sqlMapClient;

  /**
   * 插入数据
   * 
   * @param qualificaitonsFileDO
   * @return 插入数据的主键
   * @throws SQLException
   */
  public Integer insertQualificaitonsFileDO(QualificaitonsFileDO qualificaitonsFileDO)
      throws SQLException {
    return (Integer) sqlMapClient.insert("QualificaitonsFile.insert", qualificaitonsFileDO);

  }

  /**
   * 统计记录数
   * 
   * @param qualificaitonsFileDO
   * @return 查出的记录数
   * @throws SQLException
   */
  public Integer countQualificaitonsFileDOByExample(QualificaitonsFileDO qualificaitonsFileDO)
      throws SQLException {
    Integer count = (Integer) sqlMapClient.queryForObject("QualificaitonsFile.countByDOExample",
        qualificaitonsFileDO);
    return count;
  }

  /**
   * 更新记录
   * 
   * @param qualificaitonsFileDO
   * @return 受影响的行数
   * @throws SQLException
   */
  public Integer updateQualificaitonsFileDO(QualificaitonsFileDO qualificaitonsFileDO)
      throws SQLException {
    int result = sqlMapClient.update("QualificaitonsFile.update", qualificaitonsFileDO);
    return result;
  }

  /**
   * 获取对象列表
   * 
   * @param qualificaitonsFileDO
   * @return 对象列表
   * @throws SQLException
   */
  @SuppressWarnings("unchecked")
  public List<QualificaitonsFileDO> findListByExample(QualificaitonsFileDO qualificaitonsFileDO)
      throws SQLException {
    List<QualificaitonsFileDO> list =
        sqlMapClient.queryForList("QualificaitonsFile.findListByDO", qualificaitonsFileDO);
    return list;
  }

  /**
   * 根据主键获取qualificaitonsFileDO
   * 
   * @param id
   * @return qualificaitonsFileDO
   * @throws SQLException
   */
  public QualificaitonsFileDO findQualificaitonsFileDOByPrimaryKey(Integer id) throws SQLException {
    QualificaitonsFileDO qualificaitonsFileDO = (QualificaitonsFileDO) sqlMapClient
        .queryForObject("QualificaitonsFile.findByPrimaryKey", id);
    return qualificaitonsFileDO;
  }

  /**
   * 删除记录
   * 
   * @param id
   * @return 受影响的行数
   * @throws SQLException
   */
  public Integer deleteQualificaitonsFileDOByPrimaryKey(Integer id) throws SQLException {
    Integer rows = (Integer) sqlMapClient.delete("QualificaitonsFile.deleteByPrimaryKey", id);
    return rows;
  }

  public SqlMapClient getSqlMapClient() {
    return sqlMapClient;
  }

  public void setSqlMapClient(SqlMapClient sqlMapClient) {
    this.sqlMapClient = sqlMapClient;
  }

  @Override
  public List findListQualificaitonsFile(Integer userId) throws SQLException {
    // TODO Auto-generated method stub
    return sqlMapClient.queryForList("QualificaitonsFile.findListQualificaitonsFile", userId);
  }



}
