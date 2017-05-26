package com.pltfm.app.dao;

import com.pltfm.app.vobject.CommercialTenantBasicInfo;
import com.pltfm.app.vobject.CommercialTenantBasicInfoExample;
import com.pltfm.app.vobject.Customer;
import com.pltfm.app.vobject.LoginRoseRelQuery;

import java.sql.SQLException;
import java.util.List;

/**
 * 商户基本信息处理接口
 * 
 * @author cjm
 * @since 2013-7-9
 */
public interface CommercialTenantBasicInfoDAO {

  /**
   * 添加商户基本信息
   * 
   * @param record 商户基本信息实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  Integer insert(CommercialTenantBasicInfo record) throws SQLException;

  /**
   * 修改商户基本信息
   * 
   * @param record 商户基本信息实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  int updateByPrimaryKey(CommercialTenantBasicInfo record) throws SQLException;

  /**
   * 动态修改商户基本信息
   * 
   * @param record 商户基本信息实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  int updateByPrimaryKeySelective(CommercialTenantBasicInfo record) throws SQLException;

  /**
   * 按商户基本信息条件查询
   * 
   * @param example 商户基本信息条件
   * @return 返回值
   * @throws SQLException sql异常
   */
  List selectByExample(CommercialTenantBasicInfoExample example) throws SQLException;

  /**
   * 按商户ID查询商户信息
   * 
   * @param customer
   * @return
   * @throws SQLException
   */
  Customer selectByCustomer(Customer customer) throws SQLException;

  /**
   * 根据商户主键查询单条商户基本信息
   * 
   * @param nCommercialTenantId 商户主键
   * @return 返回值
   * @throws SQLException sql异常
   */
  CommercialTenantBasicInfo selectByPrimaryKey(Integer nCommercialTenantId) throws SQLException;


  /**
   * 根据登录主键查询单条商户基本信息
   * 
   * @param nCommercialTenantId 登录主键
   * @return 返回值
   * @throws SQLException sql异常
   */
  CommercialTenantBasicInfo selectByPrimaryLoginInfo(Integer nLoginId) throws SQLException;


  /**
   * 按商户基本信息条件进行删除
   * 
   * @param example 商户基本信息条件
   * @return 返回值
   * @throws SQLException sql异常
   */
  int deleteByExample(CommercialTenantBasicInfoExample example) throws SQLException;

  /**
   * 根据商户主键删除商户基本信息
   * 
   * @param nCommercialTenantId 商户主键
   * @return 返回值
   * @throws SQLException sql异常
   */
  int deleteByPrimaryKey(Integer nCommercialTenantId) throws SQLException;

  /**
   * 按商户基本信息条件查询总条数
   * 
   * @param example 商户基本信息条件
   * @return 返回值
   * @throws SQLException sql异常
   */
  int countByExample(CommercialTenantBasicInfoExample example) throws SQLException;

  /**
   * 动态按商户基本信息条件进行修改
   * 
   * @param record 商户基本信息实体
   * @param example 商户基本信息条件
   * @return 返回值
   * @throws SQLException sql异常
   */
  int updateByExampleSelective(CommercialTenantBasicInfo record,
      CommercialTenantBasicInfoExample example) throws SQLException;

  /**
   * 按商户基本信息条件进行修改
   * 
   * @param record 商户基本信息实体
   * @param example 商户基本信息条件
   * @return 返回值
   * @throws SQLException sql异常
   */
  int updateByExample(CommercialTenantBasicInfo record, CommercialTenantBasicInfoExample example)
      throws SQLException;

  /**
   * 按条件查询商户信息总数量
   * 
   * @param vo 商户信息实体
   * @return 返回值
   */
  int selectCountByVo(CommercialTenantBasicInfo vo) throws SQLException;

  /**
   * 根据vo条件查询分类信息page
   * 
   * @param page 分页类
   * @param vo 商户信息实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  List selectPageByVo(CommercialTenantBasicInfo vo) throws SQLException;


  void verifyPass(CommercialTenantBasicInfo commercialTenantBasicInfo) throws SQLException;

  void notPass(CommercialTenantBasicInfo commercialTenantBasicInfo) throws SQLException;

  int updateByerStaus(CommercialTenantBasicInfo commercialTenantBasicInfo) throws SQLException;

  int updateByer(CommercialTenantBasicInfo commercialTenantBasicInfo) throws SQLException;

  LoginRoseRelQuery selectByNcustomerId(Integer nCommercialTenantId) throws SQLException;
}
