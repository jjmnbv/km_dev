package com.pltfm.app.dao.impl;


import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.pltfm.app.dao.QualificationsDAO;
import com.pltfm.app.vobject.QualificationsDO;

/**
 * 数据访问对象实现类
 * 
 * @since 2014-07-07
 */
@Component(value = "qualificationsDAO")
public class QualificationsDAOImpl implements QualificationsDAO {


  @Resource(name = "sqlMapClient")
  private SqlMapClient sqlMapClient;

  /**
   * 插入数据
   * 
   * @param qualificationsDO
   * @return 插入数据的主键
   * @throws SQLException
   */
  public Integer insertQualificationsDO(QualificationsDO qualificationsDO) throws SQLException {
    return (Integer) sqlMapClient.insert("Qualifications.insert", qualificationsDO);
  }



  /**
   * 更新记录
   * 
   * @param qualificationsDO
   * @return 受影响的行数
   */
  public Integer updateQualificationsDO(QualificationsDO qualificationsDO) throws SQLException {
    int result = sqlMapClient.update("Qualifications.update", qualificationsDO);
    return result;
  }

  /**
   * 获取对象列表
   * 
   * @param qualificationsDO
   * @return 对象列表
   */
  @SuppressWarnings("unchecked")
  public List<QualificationsDO> findListByExample(QualificationsDO qualificationsDO)
      throws SQLException {
    List<QualificationsDO> list =
        sqlMapClient.queryForList("Qualifications.findListByDO", qualificationsDO);
    return list;
  }

  @Override
  public List qualificaitonsList(QualificationsDO qualificationsDO) throws SQLException {
    // TODO Auto-generated method stub
    return sqlMapClient.queryForList("Qualifications.findListByQualificaitons", qualificationsDO);
  }


  @Override
  public Integer countQualificationsDOByExample(QualificationsDO qualificationsDO)
      throws SQLException {
    // TODO Auto-generated method stub
    return (Integer) sqlMapClient.queryForObject("Qualifications.countByDOExample",
        qualificationsDO);
  }



  /**
   * 根据主键获取qualificationsDO
   * 
   * @param id
   * @return qualificationsDO
   */
  public QualificationsDO findQualificationsDOByPrimaryKey(Integer id) throws SQLException {
    QualificationsDO qualificationsDO =
        (QualificationsDO) sqlMapClient.queryForObject("Qualifications.findByPrimaryKey", id);
    return qualificationsDO;
  }

  /**
   * 删除记录
   * 
   * @param id
   * @return 受影响的行数
   */
  public Integer deleteQualificationsDOByPrimaryKey(Integer userId) throws SQLException {
    Integer rows = (Integer) sqlMapClient.delete("Qualifications.deleteByPrimaryKey", userId);
    return rows;
  }

  public SqlMapClient getSqlMapClient() {
    return sqlMapClient;
  }

  public void setSqlMapClient(SqlMapClient sqlMapClient) {
    this.sqlMapClient = sqlMapClient;
  }



  @Override
  public Integer qualificaitonsEdit(QualificationsDO qualificaitonsDO) throws SQLException {
    // TODO Auto-generated method stub
    int result = sqlMapClient.update("Qualifications.updateQualificaitonsDO", qualificaitonsDO);
    return result;
  }



  @Override
  public List getQualificaitonsList(QualificationsDO qualificaitonsDO) throws SQLException {
    // TODO Auto-generated method stub
    return sqlMapClient.queryForList("Qualifications.getQualificaitonsList", qualificaitonsDO);
  }



}
