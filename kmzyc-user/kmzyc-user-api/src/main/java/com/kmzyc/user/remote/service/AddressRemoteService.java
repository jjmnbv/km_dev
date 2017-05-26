package com.kmzyc.user.remote.service;

import java.util.List;

import com.pltfm.app.vobject.Address;

/**
 * 地址管理远程接口处理类
 * 
 * @author zhl
 * @since 2013-08-12
 */
public interface AddressRemoteService {
  /**
   * 添加收货地址信息
   * 
   * @param address 收货地址实体
   * @throws Exception 异常
   */
  public Integer addAddress(Address address) throws Exception;

  /**
   * 通过主键删除收货地址
   * 
   * @param addressIds 收货地址主键
   * @return 删除条数
   * @throws Exception 异常
   */
  public Integer deleteAddressList(Long userId, List<String> addressIds) throws Exception;

  /**
   * 单条删除收货地址
   * 
   * @param addressId 地址主键
   * @return 删除条数
   * @throws Exception 异常
   */
  public boolean deleteAddress(Long userId, Integer addressId) throws Exception;

  /**
   * 更新收货地址
   * 
   * @param address 收货地址实体
   * @return 更新数量
   * @throws Exception 异常
   */
  public Integer updateAddress(Address address) throws Exception;

  /**
   * 按照查询条件查询统计收货地址总数
   * 
   * @param address 收货地址实体
   * @return 无异常代表执行成功
   * @throws Exception 异常
   */
  @Deprecated
  public Integer byCountAddress(Address address) throws Exception;

  /**
   * 查询条件查询收货地址信息
   * 
   * @param address 收货地址实体
   * @return 无异常代表执行成功
   * @throws Exception 异常
   */
  @Deprecated
  public List queryListAddress(Address address) throws Exception;
}
