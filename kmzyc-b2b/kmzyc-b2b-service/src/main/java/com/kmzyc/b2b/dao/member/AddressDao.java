package com.kmzyc.b2b.dao.member;

import java.rmi.ServerException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.km.framework.persistence.Dao;
import com.kmzyc.b2b.model.Address;

/**
 * 该DAO已过期，不要在其他的service中调用，或者使用自己的dao来做地址相关的查询 新的实现方式地址信息都放入redis，具体查询 AddressService 实现，
 * 如需使用地址，请在sevice层或者Action层引入 AddressService
 * 
 * 当前类唯一作用：将老用户的数据在再次购买或个人地址管理处，将数据从db load redis cache
 * 
 * @author xuyuliang 2016-05-09
 * 
 * @see com.kmzyc.b2b.service.member.AddressService
 */
@Deprecated
public interface AddressDao extends Dao {
    /**
     * 根据addressId获取loginId
     * 
     * @param addressId
     * @return
     * @throws SQLException
     */
    Integer findLoginIdByAddressId(Integer addressId) throws SQLException;

    /**
     * 初次添加收货地址查询accountId
     * 
     * @param loginId
     * @return
     * @throws SQLException
     */
    Integer findAccountId(Integer loginId) throws SQLException;

    /**
     * 根据用户id查询收获地址数量 从cache中获取收件地址信息 的长度
     * 
     * @param id
     * @return
     * @throws SQLException
     */
    @Deprecated
    public Integer queryAddressCountById(Integer id) throws SQLException;

    /**
     * 根据用户id查询收获地址信息
     * 
     * @param id 用户loginId
     * @return
     * @throws SQLException
     */
    public List<Address> queryAddressByAccountId(Integer id) throws SQLException;

    /**
     * 根据收货地址di查询单个收货地址
     * 
     * @param id
     * @return
     * @throws SQLException
     */
    public Address queryByAddressId(Integer id) throws SQLException;

    public List<Address> findByNaccountID(Integer naccountid) throws SQLException;

    public Address findByNAddressID(Integer naddressId) throws SQLException;

    /**
     * 获取默认的收货地址
     * 
     * @param naddressId
     * @return
     * @throws SQLException
     */
    public boolean checkExistDefaultAddressByLoginID(long loginId) throws SQLException;

    public Address findDefaultAddressByLoginId(Integer loginId) throws SQLException;

    /**
     * 查询非某类型的用户收货地址
     * 
     * @return
     * @throws ServerException
     */
    public List<Address> queryUserAddress(Long uid, Integer tourist) throws SQLException;

    /**
     * 根据登录ID查询收货地址
     * 
     * @return
     * @throws SQLException
     */
    public List<Address> queryAddressByLoginId(Long loginId) throws SQLException;

    /**
     * 删除我的收货地址
     * 
     * @param params
     * @return
     * @throws SQLException
     */
    public boolean deleteMyAddress(Map<String, Long> params) throws SQLException;

    /**
     * 根据登录ID/地址ID查询收货地址
     * 
     * @return
     * @throws SQLException
     */
    public Address queryAddress(Map<String, Long> params) throws SQLException;


    /**
     * 新增或更新外部地址
     * 
     * @param addrParam
     * @return
     * @throws SQLException
     */
    public Long addOrUpdateOutAddress(Address addrParam) throws SQLException;
}
