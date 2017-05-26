package com.kmzyc.b2b.service.member.impl;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.kmzyc.b2b.dao.member.AddressDao;
import com.kmzyc.b2b.model.Address;
import com.kmzyc.b2b.service.member.AddressService;
import com.kmzyc.framework.exception.ServiceException;
import com.kmzyc.user.remote.service.AddressRemoteService;
import com.kmzyc.util.StringUtil;
import com.kmzyc.zkconfig.ConfigurationUtil;
import com.whalin.MemCached.MemCachedClient;

import redis.clients.jedis.JedisCluster;

/**
 * 收货地址service
 * 
 */
@Service("addressServiceImpl")
public class AddressServiceImpl implements AddressService {
    // private static Logger logger = Logger.getLogger(AddressServiceImpl.class);
    private static Logger logger = LoggerFactory.getLogger(AddressServiceImpl.class);
    // private static final String ADDRESS_INFO = ConfigurationUtil.getString("ADDRESS_INFO");

    private static final int SETTLEMENT_TIME_OUT =
            60 * 60 * Integer.parseInt(ConfigurationUtil.getString("SETTLEMENT_TIME_OUT")) * 1000;

    /** redis存入用户收货地址信息列表，后缀加上loginId */
    @Value("${b2b.com.km.user.address}")
    private String userAddress;
    /** redis存入用户默认收货地址信息id */
    @Value("${b2b.com.km.user.address.default.id}")
    private String userDefaultAddressId;

    @Resource(name = "memCachedClient")
    private MemCachedClient memCachedClient;

    @Resource(name = "addressDaoImpl")
    private AddressDao addressDao;

    @Resource(name = "jedisCluster")
    private JedisCluster jedisCluster;

    @Autowired
    public AddressRemoteService addressRemoteService;

    /**
     * 查询用户的收货地址数量
     * 
     * @param id 用户登录id logigId
     */
    @Override
    public Integer queryAddressAccountById(Integer id) throws ServiceException {
        if (logger.isDebugEnabled()) {
            logger.debug("查询用户" + id + "的收货地址数量");
        }
        String addressKey = userAddress.concat(id.toString());
        if (jedisCluster.exists(addressKey)) {
            return jedisCluster.hlen(addressKey).intValue();
        }

        List<Address> list = findByLoginId(id);// list != null
        return list.size();
    }

    /**
     * 根据登录id查询收获地址信息
     */
    @Override
    public List<Address> findByLoginId(Integer id) throws ServiceException {
        return findByLoginId(id, true);
    }

    @Override
    public List<Address> findByLoginId(Integer id, boolean requireDefault) throws ServiceException {
        List<Address> result = Lists.newArrayList();
        boolean existDefaultAddress = false;
        // 默认地址redis key
        String defaultAddressKey = userDefaultAddressId.concat(id.toString());
        String addressKey = userAddress.concat(id.toString());
        List<String> addressList = jedisCluster.hvals(addressKey); // 查询所有
        if (addressList == null || addressList.isEmpty()) {
            if (logger.isDebugEnabled()) {
                logger.debug("查询用户" + id + "的收货地址数量时，从db加载收货地址数据到cache");
            }
            jedisCluster.del(defaultAddressKey);// 移除默认地址
            try {
                result = addressDao.queryAddressByAccountId(id); // 查询db 10条
            } catch (SQLException e) {
                throw new ServiceException(e);
            }
            if (!result.isEmpty()) {
                // 放入cache
                for (Address temp : result) {
                    String field = String.valueOf(temp.getAddressId());
                    if (temp.getStatus() == 0 || temp.getIsChecked()) {
                        existDefaultAddress = true;
                        temp.setIsChecked(true);// 设置选中
                        if (logger.isDebugEnabled()) {
                            logger.debug("从db加载收货地址数据到cache时，设置用户默认地址cache");
                        }
                        jedisCluster.set(defaultAddressKey, field);
                    }
                    temp.setLoginId(id);
                    jedisCluster.hset(addressKey, field, JSONObject.toJSONString(temp));
                }
                if (!existDefaultAddress) {
                    updateDefaultAddress(result.get(0), id);
                }
            }
        } else {
            for (String address : addressList) {
                Address ads = JSONObject.parseObject(address, Address.class);
                if (StringUtil.isEmpty(ads.getArea())) {
                    ads.setArea("未填写");
                }
                if (StringUtil.isEmpty(ads.getCity())) {
                    ads.setCity("未填写");
                }
                if (StringUtil.isEmpty(ads.getProvince())) {
                    ads.setProvince("未填写");
                }
                result.add(ads);
            }

            // 设置默认地址
            String addressId = jedisCluster.get(defaultAddressKey);
            if (requireDefault && addressId == null) {
                updateDefaultAddress(result.get(0), id);
            }
        }

        return result;
    }

    /***
     * 获取默认收货地址
     *
     * @param loginId
     * @return
     * @throws ServiceException
     */
    @Override
    public Address findDefaultAddressByLoginId(Long loginId) throws ServiceException {
        String defaultAddressKey = userDefaultAddressId.concat(loginId.toString());
        String addressKey = userAddress.concat(loginId.toString());
        String addressId = jedisCluster.get(defaultAddressKey);
        if (addressId != null) {
            String address = jedisCluster.hget(addressKey, addressId);
            if (address != null) {
                return JSONObject.parseObject(address, Address.class);
            }
        }

        List<Address> list = findByLoginId(loginId.intValue());// list != null
        // 再次获取
        addressId = jedisCluster.get(defaultAddressKey);
        if (addressId != null) {
            String address = jedisCluster.hget(addressKey, addressId);
            if (address != null) {
                return JSONObject.parseObject(address, Address.class);
            }
        }
        // 不存在从list设置
        if (!list.isEmpty()) {
            for (Address temp : list) {
                if (temp.getStatus() == 0 || temp.getIsChecked()) {
                    temp.setStatus(0);
                    temp.setIsChecked(true);
                    temp.setLoginId(loginId.intValue());
                    return updateDefaultAddress(temp, loginId.intValue());
                }
            }

            return updateDefaultAddress(list.get(0), loginId.intValue());
        }

        return null;
    }

    /**
     * 更新收货默认地址
     * 
     * @param address
     * @param loginId
     */
    private Address updateDefaultAddress(Address address, int loginId) {
        logger.info("设置用户默认地址cache");
        address.setStatus(0);
        address.setIsChecked(true);
        address.setLoginId(loginId);
        // 远程更新默认地址与 列表值
        update(address);

        return address;
    }

    /**
     * 添加收获地址
     */
    @Override
    public Integer save(Address address) throws ServiceException {
        logger.info("调用远程接口更新收货地址，用户登录id=" + address.getLoginId());
        try {
            return addressRemoteService.addAddress(address.transFormToRemoteAddress());
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }


    /**
     * 修改收获地址
     */
    @Override
    public Integer update(Address address) throws ServiceException {
        logger.info(
                "调用远程接口更新收货地址，用户登录id=" + address.getLoginId() + ", 地址id=" + address.getAddressId());
        try {
            return addressRemoteService.updateAddress(address.transFormToRemoteAddress());
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    /**
     * 删除收获地址
     */
    @Override
    public boolean delete(Long loginId, Integer id) throws ServiceException {
        logger.info("调用远程接口更新收货地址，用户登录id=" + loginId + ", 地址id=" + id);
        try {
            return addressRemoteService.deleteAddress(loginId, id);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    /**
     * 查询收货地址明细
     */
    @Override
    public Address findByNAddressID(long loginId, Integer naddressId) throws ServiceException {
        Address address = null;
        String addressKey = userAddress.concat(String.valueOf(loginId));
        String addressJson = jedisCluster.hget(addressKey, naddressId.toString());

        if (StringUtil.isEmpty(addressJson)) {
            // cache not exists, query db
            try {
                address = addressDao.findByNAddressID(naddressId);
            } catch (Exception e) {
                throw new ServiceException(e);
            }
            // set cache
            if (address != null) {
                jedisCluster.hset(addressKey, naddressId.toString(),
                        JSONObject.toJSONString(address));
            }
        } else {
            address = JSONObject.parseObject(addressJson, Address.class);
        }

        return address;
    }

    /**
     * 添加默认收货地址到缓存中
     * 
     * @param loginId
     * @param obj
     * @return
     */
    @Override
    public boolean addAddressTomem(String loginId, Address obj) throws ServiceException {
        String key = ConfigurationUtil.getString("ADDRESS_INFO").concat(loginId);
        Date expDate = new Date(SETTLEMENT_TIME_OUT);
        if (getAddressFromMem(loginId) == null) {
            return memCachedClient.add(key, obj, expDate);
        }
        return memCachedClient.replace(key, obj, expDate);
    }

    /**
     * 从缓存中获取默认的Address对象
     * 
     * @param loginId
     * @return
     */
    @Override
    public Address getAddressFromMem(String loginId) throws ServiceException {
        return (Address) memCachedClient
                .get(ConfigurationUtil.getString("ADDRESS_INFO").concat(loginId));
    }

    /**
     * 删除收货地址
     */
    @Override
    public void deleteAddressFormmem(String loginId) throws ServiceException {
        memCachedClient.delete(ConfigurationUtil.getString("ADDRESS_INFO").concat(loginId));
    }

    /**
     * 查询帐号收货地址
     */
    @Override
    public List<Address> findByNaccountID(Integer NaccountID) throws ServiceException {
        // try {
        //
        // return addressDao.findByNaccountID(NaccountID);
        // } catch (Exception e) {
        // throw new ServiceException(e);
        // }
        return null;
    }

}
