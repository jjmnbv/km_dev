package com.pltfm.remote.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kmzyc.user.remote.service.AddressRemoteService;
import com.pltfm.app.service.AddressService;
import com.pltfm.app.vobject.Address;
import com.pltfm.app.vobject.AddressExample;

/**
 * 添加收货地址远程接口实现类
 * 
 * @author zhl
 * @since 2013-08-12
 * @modify 2016-04-13 xuyuliang 调整使用redis cache 收货地址信息
 */
@Component(value = "addressRemoteService")
public class AddressRemoteServiceImpl implements AddressRemoteService {

  @Autowired
  private AddressService addressService;

  /**
   * 添加收货地址信息
   * 
   * @param address 收货地址实体
   * @throws Exception 异常
   */
  public Integer addAddress(Address address) throws Exception {
    return addressService.addAddress(address);
  }

  /**
   * 通过主键删除收货地址
   * 
   * @param addressIds 收货地址主键
   * @return
   * @throws Exception 异常
   */
  public Integer deleteAddressList(Long userId, List<String> addressIds) throws Exception {
    return addressService.deleteAddressList(userId, addressIds);
  }

  /**
   * 单条删除收货地址
   * 
   * @param addressId 地址主键
   * @return
   * @throws Exception 异常
   */
  public boolean deleteAddress(Long userId, Integer addressId) throws Exception {
    return addressService.deleteAddress(userId, addressId);
  }

  /**
   * 更新收货地址
   * 
   * @param address 收货地址实体
   * @return
   * @throws Exception 异常
   */
  public Integer updateAddress(Address address) throws Exception {
    return addressService.updateAddress(address);
  }

  /**
   * 多条件查询收货地址信息
   * 
   * @param example
   * @return
   * @throws Exception
   */
  public List queryAddressList(AddressExample example) throws Exception {
    return null;// addressService.queryAddressList(example);
  }

  /**
   * 按照插叙条件查询收货地址总条数
   */
  public Integer byCountAddress(Address address) throws Exception {
    return null;// addressService.byCountAddress(address);
  }

  /**
   * 按照查询条件查询收货地址信息
   */
  public List queryListAddress(Address address) throws Exception {
    return null;// addressService.queryListAddress(address);
  }

  protected AddressService getAddressService() {
    return addressService;
  }

  protected void setAddressService(AddressService addressService) {
    this.addressService = addressService;
  }
}
