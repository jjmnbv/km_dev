package com.pltfm.app.service.impl;


import java.util.List;

import org.springframework.dao.DataAccessException;

import com.pltfm.app.dataobject.BnesPasswordDO;
import com.pltfm.app.service.BnesPasswordService;
import com.pltfm.app.vobject.BnesPasswordQuery;

/**
 * 数据访问对象接口
 * 
 * @since 2013-07-26
 */
public class BnesPasswordServiceImpl implements BnesPasswordService {

  /**
   * 插入数据
   * 
   * @param bnesPasswordDO
   * @return 插入数据的主键
   */
  public void insertBnesPasswordDO(BnesPasswordDO bnesPasswordDO) throws DataAccessException {

  }

  /**
   * 统计记录数
   * 
   * @param bnesPasswordDO
   * @return 查出的记录数
   */
  public Integer countBnesPasswordDOByExample(BnesPasswordDO bnesPasswordDO)
      throws DataAccessException {
    return null;
  }

  /**
   * 统计记录数
   * 
   * @param bnesPasswordQuery
   * @return 查出的记录数
   */
  public Integer countBnesPasswordQueryByExample(BnesPasswordQuery bnesPasswordQuery)
      throws DataAccessException {
    return null;
  }

  /**
   * 获取对象列表
   * 
   * @param bnesPasswordDO
   * @return 对象列表
   */
  public List<BnesPasswordDO> findListByExample(BnesPasswordDO bnesPasswordDO)
      throws DataAccessException {
    return null;
  }

  /**
   * 获取对象列表
   * 
   * @param bnesPasswordQuery
   * @return 对象列表
   */
  public List<BnesPasswordQuery> findListByExample(BnesPasswordQuery bnesPasswordQuery)
      throws DataAccessException {
    return null;
  }

}
