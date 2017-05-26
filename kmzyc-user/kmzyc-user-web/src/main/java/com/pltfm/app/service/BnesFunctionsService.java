package com.pltfm.app.service;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.pltfm.app.dataobject.BnesFunctionsDO;
import com.pltfm.app.vobject.BnesFunctionsQuery;

/**
 * 数据访问对象接口
 * 
 * @since 2013-07-17
 */
public interface BnesFunctionsService {
  /**
   * 获取业务功能表
   * 
   * @param bnesFunctionsQuery
   * @return 对象列表
   */
  List<BnesFunctionsQuery> findListByExample(BnesFunctionsQuery bnesFunctionsQuery)
      throws DataAccessException;

  /**
   * 根据主键获取获取子功能列表信息
   * 
   * @param binesFunctionId
   * @return 对象列表
   */
  List<BnesFunctionsQuery> findBnesFunctionsDOByPrimaryKey(Integer binesFunctionId)
      throws DataAccessException;

  /**
   * ===================================================================
   */

  /**
   * 插入数据
   * 
   * @param bnesFunctionsDO
   * @return 插入数据的主键
   */
  public Integer insertBnesFunctionsDO(BnesFunctionsDO bnesFunctionsDO) throws DataAccessException;

  /**
   * 统计记录数
   * 
   * @param bnesFunctionsDO
   * @return 查出的记录数
   */
  public Integer countBnesFunctionsDOByExample(BnesFunctionsDO bnesFunctionsDO)
      throws DataAccessException;

  /**
   * 统计记录数
   * 
   * @param bnesFunctionsQuery
   * @return 查出的记录数
   */
  public Integer countBnesFunctionsQueryByExample(BnesFunctionsQuery bnesFunctionsQuery)
      throws DataAccessException;

  /**
   * 更新记录
   * 
   * @param bnesFunctionsDO
   * @return 受影响的行数
   */
  public Integer updateBnesFunctionsDO(BnesFunctionsDO bnesFunctionsDO) throws DataAccessException;

  /**
   * 获取对象列表
   * 
   * @param bnesFunctionsDO
   * @return 对象列表
   */
  public List<BnesFunctionsDO> findListByExample(BnesFunctionsDO bnesFunctionsDO)
      throws DataAccessException;



  /**
   * 删除记录
   * 
   * @param binesFunctionId
   * @return 受影响的行数
   */
  public Integer deleteBnesFunctionsDOByPrimaryKey(Integer binesFunctionId)
      throws DataAccessException;

}
