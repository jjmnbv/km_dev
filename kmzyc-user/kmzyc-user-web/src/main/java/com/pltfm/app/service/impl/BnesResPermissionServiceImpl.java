package com.pltfm.app.service.impl;



import java.util.List;

import javax.annotation.Resource;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.pltfm.app.dao.BnesResPermissionDAO;
import com.pltfm.app.dataobject.BnesResPermissionDO;
import com.pltfm.app.service.BnesResPermissionService;
import com.pltfm.app.vobject.BnesResPermissionQuery;

/**
 * 数据访问对象实现类
 * 
 * @since 2013-07-17
 */
@Service(value = "bnesResPermissionService")
public class BnesResPermissionServiceImpl implements BnesResPermissionService {


  @Resource(name = "bnesResPermissionDAO")
  private BnesResPermissionDAO bnesResPermissionDAO;

  @Override
  public Integer insertBnesResPermissionDO(BnesResPermissionQuery bnesResPermissionQuery)
      throws DataAccessException {
    // TODO Auto-generated method stub


    return bnesResPermissionDAO.insertBnesResPermissionDO(bnesResPermissionQuery);
  }

  @Override
  public Integer deleteBnesResPermissionDOByPrimaryKey(Integer customerTypeId)
      throws DataAccessException {
    // TODO Auto-generated method stub
    return bnesResPermissionDAO.deleteBnesResPermissionDOByPrimaryKey(customerTypeId);
  }

  @Override
  public List<BnesResPermissionQuery> findListByExample(Integer customerTypeId)
      throws DataAccessException {
    // TODO Auto-generated method stub
    BnesResPermissionQuery bnesResPermissionQuery = new BnesResPermissionQuery();
    bnesResPermissionQuery.setCustomerTypeId(customerTypeId);
    return bnesResPermissionDAO.findListByExample(bnesResPermissionQuery);
  }

  // --------------------------------------------
  @Override
  public Integer countBnesResPermissionDOByExample(BnesResPermissionDO bnesResPermissionDO)
      throws DataAccessException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Integer countBnesResPermissionQueryByExample(BnesResPermissionQuery bnesResPermissionQuery)
      throws DataAccessException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Integer updateBnesResPermissionDO(BnesResPermissionDO bnesResPermissionDO)
      throws DataAccessException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<BnesResPermissionDO> findListByExample(BnesResPermissionDO bnesResPermissionDO)
      throws DataAccessException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public BnesResPermissionDO findBnesResPermissionDOByPrimaryKey(Integer resPermissionId)
      throws DataAccessException {
    // TODO Auto-generated method stub
    return null;
  }



  public BnesResPermissionDAO getBnesResPermissionDAO() {
    return bnesResPermissionDAO;
  }

  public void setBnesResPermissionDAO(BnesResPermissionDAO bnesResPermissionDAO) {
    this.bnesResPermissionDAO = bnesResPermissionDAO;
  }

}
