package com.pltfm.app.dao.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.pltfm.app.dao.AddressDAO;
import com.pltfm.app.vobject.Address;
import com.pltfm.app.vobject.AddressExample;

/**
 * 收货地址数据处理实现类
 * 
 * @author zhl
 * @since 2013-07-11
 */
@Component(value = "addressDAO")
public class AddressDAOImpl implements AddressDAO {
  @Resource(name = "sqlMapClient")
  private SqlMapClient sqlMapClient;

  /**
   * 添加收货地址信息
   */
  public Integer insert(Address address) throws SQLException {
    Integer addressId = (Integer) sqlMapClient.insert("ADDRESS.abatorgenerated_insert", address);
    return addressId;
  }

  /**
   * 通过主键修改收货地址信息
   */
  public int updateByPrimaryKey(Address address) throws SQLException {
    int rows = sqlMapClient.update("ADDRESS.abatorgenerated_updateByPrimaryKey", address);
    return rows;
  }

  /**
   * 多条件查询收货地址信息
   */
  public List selectByExample(AddressExample example) throws SQLException {
    List list = sqlMapClient.queryForList("ADDRESS.abatorgenerated_selectByExample", example);
    return list;
  }

  /**
   * 通过主键获取收货地址信息
   * 
   * @param nAddressId 收货地址主键
   * @return
   * @throws SQLException 异常
   */
  public Address selectByPrimaryKey(Integer nAddressId) throws SQLException {
    Address key = new Address();
    key.setN_addressId(nAddressId);
    Address record =
        (Address) sqlMapClient.queryForObject("ADDRESS.abatorgenerated_selectByPrimaryKey", key);
    return record;
  }

  /**
   * 多条件查询删除收货地址信息
   */
  public int deleteByExample(AddressExample example) throws SQLException {
    int rows = sqlMapClient.delete("ADDRESS.abatorgenerated_deleteByExample", example);
    return rows;
  }

  /**
   * 通过主键删除收货地址信息
   */
  public int deleteByPrimaryKey(Integer nAddressId) throws SQLException {
    Address key = new Address();
    key.setN_addressId(nAddressId);
    int rows = sqlMapClient.delete("ADDRESS.abatorgenerated_deleteByPrimaryKey", key);
    return rows;
  }

  /**
   * 多条件查询统计收货地址总数
   * 
   * @param address 收货地址实体
   * @return
   * @throws SQLException 异常
   */
  public int countByAddress(Address address) throws SQLException {
    Integer count =
        (Integer) sqlMapClient.queryForObject("ADDRESS.abatorgenerated_countByAddress", address);
    return count.intValue();
  }

  /**
   * 分页查询收货地址信息
   * 
   * @param address 收货地址实体
   * @return
   * @throws SQLException 异常
   */
  public List queryForPage(Address address) throws SQLException {
    return sqlMapClient.queryForList("ADDRESS.abatorgenerated_pageByAddress", address);
  }

  /**
   * 通过账号更新收货地址默认状态
   */
  public void updateStatusByAccount(Address address) throws SQLException {
    sqlMapClient.update("ADDRESS.abatorgenerated_updateByAccount", address);
  }

  public SqlMapClient getSqlMapClient() {
    return sqlMapClient;
  }

  public void setSqlMapClient(SqlMapClient sqlMapClient) {
    this.sqlMapClient = sqlMapClient;
  }

  @Override
  public List<Address> queryListByLoginId(Integer n_LoginId) throws SQLException {
    List list = sqlMapClient.queryForList("ADDRESS.selectByLoginID", n_LoginId);
    return list;
  }
}
