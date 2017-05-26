package com.kmzyc.b2b.service.member;

import java.rmi.ServerException;
import java.util.List;
import java.util.Map;

import com.kmzyc.b2b.model.Address;
import com.kmzyc.framework.exception.ServiceException;

/**
 * 收货地址service
 * 
 */
public interface AddressService {

  /**
   * 查询收货地址明细
   */
  public Address findByNAddressID(long loginId, Integer naddressId) throws ServiceException;

  /***
   * 获取默认收货地址
   * @param loginId
   * @return
   */
  public Address findDefaultAddressByLoginId(Long loginId)  throws ServiceException;

  /**
   * 查询用户的收货地址数量
   * @param id 用户登录id
   */
  public Integer queryAddressAccountById(Integer id) throws ServiceException;

  /**
   * 根据登录id查询收获地址信息
   */
  public List<Address> findByLoginId(Integer id) throws ServiceException;
  
  /**
   * 根据登录id查询收获地址信息
   * @param id loginId
   * @param requireDefault   false：不需要匹配出默认地址， true 直接调用findByLoginId(id)
   */
  public List<Address> findByLoginId(Integer id, boolean requireDefault) throws ServiceException;

  /**
   * 添加收获地址
   */
  public Integer save(Address address) throws ServiceException;

  /**
   * 修改收获地址
   */
  public Integer update(Address address) throws ServiceException;

  /**
   * 删除收获地址
   */
  public boolean delete(Long loginId, Integer id) throws ServiceException;

  /**
   * 添加默认收货地址到缓存中
   * 
   * @param loginId
   * @param obj
   * @return
   */
  public boolean addAddressTomem(String loginId, Address obj) throws ServiceException;

  /**
   * 从缓存中获取默认的Address对象
   * 
   * @param loginId
   * @return
   */
  public Address getAddressFromMem(String loginId) throws ServiceException;

  /**
   * 删除收货地址
   */
  public void deleteAddressFormmem(String loginId) throws ServiceException;

  /**
   * 查询帐号收货地址
   */
  @Deprecated
  public List<Address> findByNaccountID(Integer NaccountID) throws ServiceException;


}
