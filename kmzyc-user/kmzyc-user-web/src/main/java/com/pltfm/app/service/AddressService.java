package com.pltfm.app.service;

import java.util.List;
import java.util.Map;

import com.kmzyc.commons.page.Page;
import com.pltfm.app.vobject.Address;
import com.pltfm.app.vobject.AddressExample;

/**
 * 收货地址业务逻辑处理接口
 * 
 * @author zhl
 * @since 2013-07-11
 */
public interface AddressService {
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
   * @return
   * @throws Exception 异常
   */
  public Integer deleteAddressList(Long userId, List<String> addressIds) throws Exception;

  /**
   * 单条删除收货地址
   * 
   * @param addressId 地址主键
   * @return
   * @throws Exception 异常
   */
  public boolean deleteAddress(Long userId, Integer addressId) throws Exception;

  /**
   * 更新收货地址
   * 
   * @param address 收货地址实体
   * @return
   * @throws Exception 异常
   */
  public Integer updateAddress(Address address) throws Exception;

  /**
   * 多条件查询收货地址信息
   * 
   * @param example
   * @return
   * @throws Exception
   */
  @Deprecated
  public List queryAddressList(AddressExample example) throws Exception;

  /**
   * 通过主键获取收货地址信息
   * 
   * @param addressId 收货地址实体
   * @return
   * @throws Exception 异常
   */
  public Address queryByPrimaryKey(Long loginId, Integer addressId) throws Exception;

  /**
   * 分页查询收货地址信息
   * 
   * @param address 收货地址信息
   * @param page 分页对象
   * @return
   * @throws Exception 异常
   */
  public Page queryForPageList(Address address, Page page) throws Exception;

  /**
   * 按照查询条件查询统计收货地址总数
   * 
   * @param address 收货地址实体
   * @return 无异常代表执行成功
   * @throws Exception 异常
   */
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
  
  /**
   * 用于清理缓存中错误的地址信息
   * @param paramPageSize 传入的页面大小
   * @param threadCount 线程池线程数
   * @return
   * @throws Exception
   */
  Map<String,String> clearIncorrectCacheAddress(int paramPageSize,int threadCount,String mobile) throws Exception ;
  
  
  Integer queryAddressCountByLoginId(Integer loginId) throws Exception;
  
}
