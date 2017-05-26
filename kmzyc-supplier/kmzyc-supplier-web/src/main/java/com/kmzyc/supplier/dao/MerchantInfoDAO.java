package com.kmzyc.supplier.dao;

import com.kmzyc.supplier.model.AccountInfo;
import com.kmzyc.supplier.model.MerchantInfo;
import com.kmzyc.supplier.model.User;

import java.sql.SQLException;
import java.util.List;

public interface MerchantInfoDAO {

    Long insertSelective(MerchantInfo record) throws SQLException;

    int updateByPrimaryKeySelective(MerchantInfo record) throws SQLException;

    /**
     *查询供应商是否为会员
     * @return
     * @throws SQLException
     */
    User selectByUserName(String selectId,User user) throws SQLException;
    
    /**
     * 查询会员是否设置了支付密码
     */
    AccountInfo findByLoginId(long loginId) throws SQLException;
    
    /**
     * 根据登录id查询商户信息
     */
    MerchantInfo selectByLoginId(MerchantInfo merchant)throws SQLException;
    
    /**
     * 根据登录id查询商户信息(登录)
     */
    MerchantInfo selectByLoginIdForLogin(MerchantInfo merchant)throws SQLException;
    	
    /**
	 * 根据公司名称查询
	 * @return
	 */
	List<MerchantInfo> selectByCompanyName(MerchantInfo merchant)throws SQLException;
	
	/**
	 * 根据用户id查询用户id
	 * @param userId
	 * @return
	 * @throws SQLException
	 */
	User queryUserByLoginId(Long userId)throws SQLException;
    
}