package com.pltfm.app.dao;

import com.pltfm.app.vobject.QualificationsDO;

import java.sql.SQLException;
import java.util.List;

/**
 * 数据访问对象接口
 * 
 * @since 2014-07-07
 */
public interface QualificationsDAO {

  /**
   * 插入数据
   * 
   * @param qualificationsDO
   * @return 插入数据的主键
   * @throws SQLException
   */
  public Integer insertQualificationsDO(QualificationsDO qualificationsDO) throws SQLException;

  /**
   * 统计记录数
   * 
   * @param qualificationsDO
   * @return 查出的记录数
   * @throws SQLException
   */
  public Integer countQualificationsDOByExample(QualificationsDO qualificationsDO)
      throws SQLException;

  /**
   * 更新记录
   * 
   * @param qualificationsDO
   * @return 受影响的行数
   */
  public Integer updateQualificationsDO(QualificationsDO qualificationsDO) throws SQLException;

  /**
   * 获取对象列表
   * 
   * @param qualificationsDO
   * @return 对象列表
   */
  public List<QualificationsDO> findListByExample(QualificationsDO qualificationsDO)
      throws SQLException;

  /**
   * 根据主键获取qualificationsDO
   * 
   * @param id
   * @return qualificationsDO
   */
  public QualificationsDO findQualificationsDOByPrimaryKey(Integer id) throws SQLException;

  /**
   * 删除记录
   * 
   * @param id
   * @return 受影响的行数
   */
  public Integer deleteQualificationsDOByPrimaryKey(Integer id) throws SQLException;

  public List qualificaitonsList(QualificationsDO qualificationsDO) throws SQLException;

  public Integer qualificaitonsEdit(QualificationsDO qualificaitonsDO) throws SQLException;

  public List getQualificaitonsList(QualificationsDO qualificaitonsDO) throws SQLException;



}
