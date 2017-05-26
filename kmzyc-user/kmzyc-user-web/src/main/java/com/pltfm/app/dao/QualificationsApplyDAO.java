package com.pltfm.app.dao;

import com.pltfm.app.vobject.QualificationsApplyDO;

import java.sql.SQLException;
import java.util.List;

/**
 * 数据访问对象接口
 * 
 * @since 2014-07-07
 */
public interface QualificationsApplyDAO {

  /**
   * 插入数据
   * 
   * @param qualificationsApplyDO
   * @return 插入数据的主键
   * @throws SQLException
   */
  public Integer insertQualificationsApplyDO(QualificationsApplyDO qualificationsApplyDO)
      throws SQLException;

  /**
   * 统计记录数
   * 
   * @param qualificationsApplyDO
   * @return 查出的记录数
   * @throws SQLException
   */
  public Integer countQualificationsApplyDOByExample(QualificationsApplyDO qualificationsApplyDO)
      throws SQLException;

  /**
   * 更新记录
   * 
   * @param qualificationsApplyDO
   * @return 受影响的行数
   * @throws SQLException
   */
  public Integer updateQualificationsApplyDO(QualificationsApplyDO qualificationsApplyDO)
      throws SQLException;

  /**
   * 获取对象列表
   * 
   * @param qualificationsApplyDO
   * @return 对象列表
   */
  public List<QualificationsApplyDO> findListByExample(QualificationsApplyDO qualificationsApplyDO)
      throws SQLException;

  /**
   * 根据主键获取qualificationsApplyDO
   * 
   * @param id
   * @return qualificationsApplyDO
   * @throws SQLException
   */
  public QualificationsApplyDO findQualificationsApplyDOByPrimaryKey(Integer id)
      throws SQLException;

  /**
   * 删除记录
   * 
   * @param id
   * @return 受影响的行数
   */
  public Integer deleteQualificationsApplyDOByPrimaryKey(Integer id) throws SQLException;

}
