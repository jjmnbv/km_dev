package com.pltfm.app.dao;

import com.pltfm.app.vobject.Address;
import com.pltfm.app.vobject.AddressExample;

import java.sql.SQLException;
import java.util.List;

/**
 * 收货地址数据处理接口
 * 
 * @author zhl
 * @since 2013-07-11
 */
public interface AddressDAO {
  /**
   * 添加收货地址信息
   * 
   * @param address 收货地址实体
   * @throws SQLException 异常
   */
  public Integer insert(Address address) throws SQLException;

  /**
   * 通过收货地址主键更新收货地址信息
   * 
   * @param address 收货地址实体
   * @return
   * @throws SQLException 异常
   */
  public int updateByPrimaryKey(Address address) throws SQLException;

  /**
   * 多条件查询收货地址信息
   * 
   * @param example 收货地址查询实体
   * @return
   * @throws SQLException 异常
   */
  public List selectByExample(AddressExample example) throws SQLException;

  /**
   * 通过主键查询收货地址信息
   * 
   * @param nAddressId 收货地址主键
   * @return address 收货地址实体
   * @throws SQLException 异常
   */
  public Address selectByPrimaryKey(Integer nAddressId) throws SQLException;

  /**
   * 多条件查询删除收货地址信息
   * 
   * @param example 查询实体
   * @return
   * @throws SQLException 异常
   */
  public int deleteByExample(AddressExample example) throws SQLException;

  /**
   * 通过主键删除收货地址信息
   * 
   * @param nAddressId 收货地址主键
   * @return
   * @throws SQLException 异常
   */
  public int deleteByPrimaryKey(Integer nAddressId) throws SQLException;

  /**
   * 多条件统计收货地址信息总数
   * 
   * @param address 收货地址实体
   * @return
   * @throws SQLException 异常
   */
  public int countByAddress(Address address) throws SQLException;

  /**
   * 分页查询收货地址信息
   * 
   * @param address 收货地址实体
   * @return
   * @throws SQLException 异常
   */
  public List queryForPage(Address address) throws SQLException;

  /**
   * 通过账号更新收货地址默认状态
   * 
   * @param address
   * @throws SQLException
   */
  public void updateStatusByAccount(Address address) throws SQLException;

  public List<Address> queryListByLoginId(Integer n_LoginId) throws SQLException;
}
