package com.pltfm.app.service.impl;



import java.util.List;

import javax.annotation.Resource;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.pltfm.app.dao.BnesFunctionsDAO;
import com.pltfm.app.dataobject.BnesFunctionsDO;
import com.pltfm.app.service.BnesFunctionsService;
import com.pltfm.app.vobject.BnesFunctionsQuery;

/**
 * 数据访问对象实现类
 * 
 * @since 2013-07-17
 */
@Service(value = "bnesFunctionsService")
public class BnesFunctionsServiceImpl implements BnesFunctionsService {
  /**
   * 获取业务功能表
   * 
   * @param bnesFunctionsQuery
   * @return 对象列表
   */
  @Resource(name = "bnesFunctionsDAO")
  private BnesFunctionsDAO bnesFunctionsDAO;


  @Override
  public List<BnesFunctionsQuery> findListByExample(BnesFunctionsQuery bnesFunctionsQuery)
      throws DataAccessException {
    // TODO Auto-generated method stub
    return bnesFunctionsDAO.findListByExample(bnesFunctionsQuery);
  }

  /**
   * 根据主键获取获取子功能列表信息
   * 
   * @param binesFunctionId
   * @return 对象列表
   */
  @Override
  public List<BnesFunctionsQuery> findBnesFunctionsDOByPrimaryKey(Integer binesFunctionId)
      throws DataAccessException {
    // TODO Auto-generated method stub
    return bnesFunctionsDAO.findBnesFunctionsDOByPrimaryKey(binesFunctionId);
  }

  /**
   * ===================================================================
   */

  @Override
  public Integer insertBnesFunctionsDO(BnesFunctionsDO bnesFunctionsDO) throws DataAccessException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Integer countBnesFunctionsDOByExample(BnesFunctionsDO bnesFunctionsDO)
      throws DataAccessException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Integer countBnesFunctionsQueryByExample(BnesFunctionsQuery bnesFunctionsQuery)
      throws DataAccessException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Integer updateBnesFunctionsDO(BnesFunctionsDO bnesFunctionsDO) throws DataAccessException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<BnesFunctionsDO> findListByExample(BnesFunctionsDO bnesFunctionsDO)
      throws DataAccessException {
    // TODO Auto-generated method stub
    return null;
  }



  @Override
  public Integer deleteBnesFunctionsDOByPrimaryKey(Integer binesFunctionId)
      throws DataAccessException {
    // TODO Auto-generated method stub
    return null;
  }


  public BnesFunctionsDAO getBnesFunctionsDAO() {
    return bnesFunctionsDAO;
  }

  public void setBnesFunctionsDAO(BnesFunctionsDAO bnesFunctionsDAO) {
    this.bnesFunctionsDAO = bnesFunctionsDAO;
  }

}
