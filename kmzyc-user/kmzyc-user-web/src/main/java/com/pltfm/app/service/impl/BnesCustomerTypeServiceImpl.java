package com.pltfm.app.service.impl;



import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.pltfm.app.dao.BnesCustomerTypeDAO;
import com.pltfm.app.service.BnesCustomerTypeService;
import com.pltfm.app.util.DateTimeUtils;
import com.pltfm.app.vobject.BnesCustomerTypeQuery;

/**
 * 数据访问对象实现类
 * 
 * @since 2013-07-17
 */
@Service(value = "bnesCustomerTypeService")
public class BnesCustomerTypeServiceImpl implements BnesCustomerTypeService {
  @Resource(name = "bnesCustomerTypeDAO")
  private BnesCustomerTypeDAO bnesCustomerTypeDAO;


  /**
   * 客户类别查询
   * 
   * @param BnesCustomerTypeQuery
   * @return
   */

  @Override
  public List<BnesCustomerTypeQuery> findListByExample(BnesCustomerTypeQuery bnesCustomerTypeQuery)
      throws Exception {
    // TODO Auto-generated method stub
    return bnesCustomerTypeDAO.findListByExample(bnesCustomerTypeQuery);
  }

  /**
   * 根据主键获取查询客户类别下所有成员
   * 
   * @param customerTypeId
   * @return List<BnesCustomerTypeQuery>
   */

  public List<BnesCustomerTypeQuery> findListBnesCustTypeById(Integer customerTypeId) {
    return bnesCustomerTypeDAO.findListBnesCustTypeById(customerTypeId);
  }

  /**
   * 客户类别明细
   * 
   * @param bnesCustomerTypeDO
   * @return
   */

  @Override
  public BnesCustomerTypeQuery findBnesCustomerTypeDOByPrimaryKey(Integer customerTypeId)
      throws Exception {
    // TODO Auto-generated method stub
    return bnesCustomerTypeDAO.findBnesCustomerTypeDOByPrimaryKey(customerTypeId);
  }

  /**
   * 修改客户
   * 
   * @param BnesCustomerTypeQuery
   * @return Integer
   */
  @Override
  public Integer updateBnesCustomerTypeDO(BnesCustomerTypeQuery bnesCustomerTypeQuery)
      throws Exception {
    bnesCustomerTypeQuery.setModifyDate(DateTimeUtils.getCalendarInstance().getTime());

    // TODO Auto-generated method stub
    return bnesCustomerTypeDAO.updateBnesCustomerTypeDO(bnesCustomerTypeQuery);
  }


  /**
   * 查询客户类别是否有客户成员
   * 
   * @param BnesCustomerTypeQuery
   * @return List
   */
  @Override
  public List<BnesCustomerTypeQuery> findParentList(Integer customerTypeId) throws Exception {

    return bnesCustomerTypeDAO.findParentList(customerTypeId);
  }


  /**
   * 刪除客户成员
   * 
   * @param customerTypeId
   * @return 受影响的行数
   */
  @Override
  public Integer deleteBnesCustomerTypeDOByPrimaryKey(Integer customerTypeId) throws Exception {

    return bnesCustomerTypeDAO.deleteBnesCustomerTypeDOByPrimaryKey(customerTypeId);
  }

  /**
   * 新增客户類別成员
   * 
   * @param customerTypeId
   * @return 受影响的行数
   */
  @Override
  public Integer insertBnesCustomerTypeDO(BnesCustomerTypeQuery bnesCustomerTypeQuery)
      throws Exception {
    bnesCustomerTypeQuery.setIsEnable(1);
    bnesCustomerTypeQuery.setCreateDate(DateTimeUtils.getCalendarInstance().getTime());
    bnesCustomerTypeQuery.setModifyDate(DateTimeUtils.getCalendarInstance().getTime());
    return bnesCustomerTypeDAO.insertBnesCustomerTypeDO(bnesCustomerTypeQuery);
  }


  public BnesCustomerTypeDAO getBnesCustomerTypeDAO() {
    return bnesCustomerTypeDAO;
  }

  public void setBnesCustomerTypeDAO(BnesCustomerTypeDAO bnesCustomerTypeDAO) {
    this.bnesCustomerTypeDAO = bnesCustomerTypeDAO;
  }



}
