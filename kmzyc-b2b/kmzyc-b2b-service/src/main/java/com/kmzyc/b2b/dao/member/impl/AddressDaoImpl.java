package com.kmzyc.b2b.dao.member.impl;

import java.rmi.ServerException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.kmzyc.b2b.dao.member.AddressDao;
import com.kmzyc.b2b.model.Address;
import com.km.framework.persistence.impl.DaoImpl;
import com.km.framework.persistence.util.DBContextHolder;

@Component
@SuppressWarnings("unchecked")
public class AddressDaoImpl extends DaoImpl implements AddressDao {

    @javax.annotation.Resource(name = "sqlMapClient")
    private SqlMapClient sqlMapClient;

    /**
     * 根据addressId获取loginId
     */
    public Integer findLoginIdByAddressId(Integer addressId) throws SQLException {
        return (Integer) sqlMapClient.queryForObject("Address.queryLoginIdByAddressId", addressId);
    }

    /**
     * 初次获取accountId
     */
    public Integer findAccountId(Integer loginId) throws SQLException {
        return (Integer) sqlMapClient.queryForObject("Address.findAccountId", loginId);
    }

    /**
     * 根据用户id查询收货地址数量
     */
    @Deprecated
    public Integer queryAddressCountById(Integer id) throws SQLException {

        return (Integer) sqlMapClient.queryForObject("Address.findCountById", id);
    }

    /**
     * 根据用户id查询用户的收货地址信息
     */
    public List<Address> queryAddressByAccountId(Integer AccounId) throws SQLException {

        return sqlMapClient.queryForList("Address.findById", AccounId);
    }

    /**
     * 根据收货地址id查询单个收货地址信息
     */
    public Address queryByAddressId(Integer id) throws SQLException {
        return (Address) sqlMapClient.queryForObject("Address.findAddressById", id);
    }

    @Override
    public List<Address> findByNaccountID(Integer naccountid) throws SQLException {
        return (List<Address>) sqlMapClient.queryForList("AddressInfo.findByN_AccountId",
                naccountid);
    }

    @Override
    public Address findByNAddressID(Integer naddressId) throws SQLException {

        return (Address) sqlMapClient.queryForObject("AddressInfo.findAddressbyNaddressId",
                naddressId);
    }

    /**
     * 获取默认的收货地址
     * 
     * @param naddressId
     * @return
     * @throws SQLException
     */
    @Override
    public boolean checkExistDefaultAddressByLoginID(long loginId) throws SQLException {
        int account =
                (Integer) sqlMapClient.queryForObject("AccountInfo.findDefaultAddressByLoginId",
                        loginId);
        return account > 0;
    }

    @Override
    public Address findDefaultAddressByLoginId(Integer loginId) throws SQLException {
        return (Address) sqlMapClient.queryForObject("AddressInfo.findDefaultAddressByLoginId",
                loginId);
    }

    /**
     * 查询非某类型的用户收货地址
     * 
     * @return
     * @throws ServerException
     */
    public List<Address> queryUserAddress(Long uid, Integer tourist) throws SQLException {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("uid", uid);
        params.put("tourist", tourist);
        return (List<Address>) sqlMapClient.queryForList("Address.SQL_QUERY_USER_ADDRESS", params);
    }

    /**
     * 根据登录ID查询收货地址
     * 
     * @return
     * @throws SQLException
     */
    public List<Address> queryAddressByLoginId(Long loginId) throws SQLException {
        return (List<Address>) sqlMapClient.queryForList(
                "AddressInfo.SQL_QUERY_ADDRESS_BY_LOGIN_ID", loginId);
    }

    /**
     * 删除我的收货地址
     * 
     * @param params
     * @return
     * @throws SQLException
     */
    @Override
    public boolean deleteMyAddress(Map<String, Long> params) throws SQLException {
        return sqlMapClient.delete("AddressInfo.SQL_DELETE_MY_UN_DEF_ADDRESS", params) > 0;
    }

    /**
     * 根据登录ID/地址ID查询收货地址
     * 
     * @return
     * @throws SQLException
     */
    @Override
    public Address queryAddress(Map<String, Long> params) throws SQLException {
        return (Address) sqlMapClient.queryForObject("AddressInfo.SQL_QUERY_MY_ADDRESS", params);
    }

    @Override
    public Long addOrUpdateOutAddress(Address addrParam) throws SQLException {
        return (Long) sqlMapClient.insert("Address.sql_addOrUpdate_outAddress", addrParam);
    }
}
