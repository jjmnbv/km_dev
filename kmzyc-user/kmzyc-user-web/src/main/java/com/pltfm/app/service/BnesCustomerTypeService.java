package com.pltfm.app.service;



import java.util.List;

import com.pltfm.app.vobject.BnesCustomerTypeQuery;

/**
 * 数据访问对象接口
 * 
 * @since 2013-07-17
 */
public interface BnesCustomerTypeService {

  /**
   * 查询
   * 
   * @param BnesCustomerTypeQuery
   * @return 插入数据的主键
   */
  List<BnesCustomerTypeQuery> findListByExample(BnesCustomerTypeQuery bnesCustomerTypeQuery)
      throws Exception;


  /**
   * 根据主键获取查询客户类别BnesCustomerTypeQuery
   * 
   * @param customerTypeId
   * @return BnesCustomerTypeQuery
   */
  BnesCustomerTypeQuery findBnesCustomerTypeDOByPrimaryKey(Integer customerTypeId) throws Exception;

  /**
   * 根据主键获取查询客户类别下所有成员
   * 
   * @param customerTypeId
   * @return List<BnesCustomerTypeQuery>
   */

  List<BnesCustomerTypeQuery> findListBnesCustTypeById(Integer customerTypeId) throws Exception;

  /**
   * 修改客户
   * 
   * @param BnesCustomerTypeQuery
   * @return Integer
   */
  Integer updateBnesCustomerTypeDO(BnesCustomerTypeQuery bnesCustomerTypeQuery) throws Exception;

  /**
   * 查询客户类别是否有客户成员
   * 
   * @param customerTypeId
   * @return List
   */

  List<BnesCustomerTypeQuery> findParentList(Integer customerTypeId) throws Exception;

  /**
   * 刪除客户成员
   * 
   * @param customerTypeId
   * @return 受影响的行数
   */
  Integer deleteBnesCustomerTypeDOByPrimaryKey(Integer customerTypeId) throws Exception;

  /**
   * 新增客户類別成员
   * 
   * @param customerTypeId
   * @return 受影响的行数
   */
  Integer insertBnesCustomerTypeDO(BnesCustomerTypeQuery bnesCustomerTypeQuery) throws Exception;

  /**
   * 查询客户
   * 
   * @param customerTypeId
   * @return 受影响的行数
   */
  // Integer checkCustomerType(BnesCustomerTypeQuery bnesCustomerTypeQuery) throws Exception;

}
