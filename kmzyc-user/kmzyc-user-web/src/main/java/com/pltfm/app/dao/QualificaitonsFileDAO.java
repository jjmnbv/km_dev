package com.pltfm.app.dao;


import com.pltfm.app.vobject.QualificaitonsFileDO;

import java.sql.SQLException;
import java.util.List;

/**
 * 数据访问对象接口
 * 
 * @since 2014-07-07
 */
public interface QualificaitonsFileDAO {

  /**
   * 插入数据
   * 
   * @param qualificaitonsFileDO
   * @return 插入数据的主键
   * @throws SQLException
   */
  public Integer insertQualificaitonsFileDO(QualificaitonsFileDO qualificaitonsFileDO)
      throws SQLException;

  /**
   * 统计记录数
   * 
   * @param qualificaitonsFileDO
   * @return 查出的记录数
   * @throws SQLException
   */
  public Integer countQualificaitonsFileDOByExample(QualificaitonsFileDO qualificaitonsFileDO)
      throws SQLException;

  /**
   * 更新记录
   * 
   * @param qualificaitonsFileDO
   * @return 受影响的行数
   * @throws SQLException
   */
  public Integer updateQualificaitonsFileDO(QualificaitonsFileDO qualificaitonsFileDO)
      throws SQLException;

  /**
   * 获取对象列表
   * 
   * @param qualificaitonsFileDO
   * @return 对象列表
   * @throws SQLException
   */
  public List<QualificaitonsFileDO> findListByExample(QualificaitonsFileDO qualificaitonsFileDO)
      throws SQLException;

  /**
   * 根据主键获取qualificaitonsFileDO
   * 
   * @param id
   * @return qualificaitonsFileDO
   * @throws SQLException
   */
  public QualificaitonsFileDO findQualificaitonsFileDOByPrimaryKey(Integer id) throws SQLException;

  /**
   * 删除记录
   * 
   * @param id
   * @return 受影响的行数
   * @throws SQLException
   */
  public Integer deleteQualificaitonsFileDOByPrimaryKey(Integer id) throws SQLException;

  public List findListQualificaitonsFile(Integer userId) throws SQLException;



}
