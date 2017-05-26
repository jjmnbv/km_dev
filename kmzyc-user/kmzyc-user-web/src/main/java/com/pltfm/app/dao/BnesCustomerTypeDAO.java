package com.pltfm.app.dao;


import com.pltfm.app.vobject.BnesCustomerTypeQuery;

import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * 数据访问对象接口
 * 
 * @since 2013-07-17
 */
public interface BnesCustomerTypeDAO {

  /**
   * 客戶類別查詢
   * 
   * @param BnesCustomerTypeQuery
   * @return list
   */


  List<BnesCustomerTypeQuery> findListByExample(BnesCustomerTypeQuery bnesCustomerTypeQuery)
      throws DataAccessException;

  /**
   * 根据主键获取查询客户类别下所有成员
   * 
   * @param customerTypeId
   * @return List<BnesCustomerTypeQuery>
   */

  List<BnesCustomerTypeQuery> findListBnesCustTypeById(Integer customerTypeId)
      throws DataAccessException;

  /**
   * 客戶類別明细
   * 
   * @param bnesCustomerTypeDO
   * @return BnesCustomerTypeQuery
   */
  BnesCustomerTypeQuery findBnesCustomerTypeDOByPrimaryKey(Integer customerTypeId)
      throws DataAccessException;

  /**
   * 修改客户
   * 
   * @param BnesCustomerTypeQuery
   * @return Integer受影响的行数
   */

  Integer updateBnesCustomerTypeDO(BnesCustomerTypeQuery bnesCustomerTypeQuery)
      throws DataAccessException;

  /**
   * 查询客户类别是否有客户成员
   * 
   * @param BnesCustomerTypeQuery
   * @return List
   */

  List<BnesCustomerTypeQuery> findParentList(Integer customerTypeId) throws DataAccessException;


  /**
   * 刪除客户成员
   * 
   * @param customerTypeId
   * @return 受影响的行数
   */

  Integer deleteBnesCustomerTypeDOByPrimaryKey(Integer customerTypeId) throws DataAccessException;


  /**
   * 新增客户類別成员
   * 
   * @param customerTypeId
   * @return 受影响的行数
   */

  Integer insertBnesCustomerTypeDO(BnesCustomerTypeQuery bnesCustomerTypeQuery)
      throws DataAccessException;

}
