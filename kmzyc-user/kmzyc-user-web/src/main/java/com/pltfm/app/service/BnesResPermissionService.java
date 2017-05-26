package com.pltfm.app.service;



import java.util.List;

import org.springframework.dao.DataAccessException;

import com.pltfm.app.dataobject.BnesResPermissionDO;
import com.pltfm.app.vobject.BnesResPermissionQuery;

/**
 * 数据访问对象接口
 * 
 * @since 2013-07-17
 */
public interface BnesResPermissionService {

  /**
   * 插入数据
   * 
   * @param bnesResPermissionDO
   * @return 插入数据的主键
   */
  Integer insertBnesResPermissionDO(BnesResPermissionQuery bnesResPermissionQuery)
      throws DataAccessException;

  /**
   * 删除记录
   * 
   * @param resPermissionId
   * @return 受影响的行数
   */
  Integer deleteBnesResPermissionDOByPrimaryKey(Integer customerTypeId) throws DataAccessException;

  /**
   * 获取对象列表
   * 
   * @param bnesResPermissionQuery
   * @return 对象列表
   */
  List<BnesResPermissionQuery> findListByExample(Integer customerTypeId) throws DataAccessException;

  // --------------------------------------------
  /**
   * 统计记录数
   * 
   * @param bnesResPermissionDO
   * @return 查出的记录数
   */
  public Integer countBnesResPermissionDOByExample(BnesResPermissionDO bnesResPermissionDO)
      throws DataAccessException;

  /**
   * 统计记录数
   * 
   * @param bnesResPermissionQuery
   * @return 查出的记录数
   */
  public Integer countBnesResPermissionQueryByExample(BnesResPermissionQuery bnesResPermissionQuery)
      throws DataAccessException;

  /**
   * 更新记录
   * 
   * @param bnesResPermissionDO
   * @return 受影响的行数
   */
  public Integer updateBnesResPermissionDO(BnesResPermissionDO bnesResPermissionDO)
      throws DataAccessException;

  /**
   * 获取对象列表
   * 
   * @param bnesResPermissionDO
   * @return 对象列表
   */
  public List<BnesResPermissionDO> findListByExample(BnesResPermissionDO bnesResPermissionDO)
      throws DataAccessException;



  /**
   * 根据主键获取bnesResPermissionDO
   * 
   * @param resPermissionId
   * @return bnesResPermissionDO
   */
  public BnesResPermissionDO findBnesResPermissionDOByPrimaryKey(Integer resPermissionId)
      throws DataAccessException;



}
