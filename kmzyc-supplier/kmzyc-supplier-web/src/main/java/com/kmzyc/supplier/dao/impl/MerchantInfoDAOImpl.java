package com.kmzyc.supplier.dao.impl;

import com.kmzyc.supplier.model.AccountInfo;
import com.kmzyc.supplier.model.MerchantInfo;
import com.kmzyc.supplier.model.User;
import com.kmzyc.supplier.dao.MerchantInfoDAO;

import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

@Repository("merchantInfoDAO")
public class MerchantInfoDAOImpl extends BaseDAO implements MerchantInfoDAO {

    public Long insertSelective(MerchantInfo record) throws SQLException {
       return (Long)sqlMapClient.insert("MerchantInfo.ibatorgenerated_insertSelective", record);
    }

    public int updateByPrimaryKeySelective(MerchantInfo record) throws SQLException {
        return sqlMapClient.update("MerchantInfo.ibatorgenerated_updateByPrimaryKeySelective", record);
    }

	public User selectByUserName(String selectId,User user) throws SQLException {
		return (User)sqlMapClient.queryForObject(selectId,user);
	}
	
	public AccountInfo findByLoginId(long loginId) throws SQLException {
        return (AccountInfo) sqlMapClient.queryForObject("AccountInfo.findById", loginId);
	}

	public MerchantInfo selectByLoginId(MerchantInfo merchant) throws SQLException {
		return (MerchantInfo)sqlMapClient.queryForObject("MerchantInfo.selectByLoginId",merchant);
	}
	
	public List<MerchantInfo> selectByCompanyName(MerchantInfo merchant)throws SQLException {
		return (List<MerchantInfo>)sqlMapClient.queryForList("MerchantInfo.selectByCompanyName", merchant);
	}
	
	public User queryUserByLoginId(Long userId)throws SQLException{
		User newUser= new User();
		newUser.setLoginId(userId);
		return (User)sqlMapClient.queryForObject("User.queryUserByLoginId", newUser);
	}

	@Override
	public MerchantInfo selectByLoginIdForLogin(MerchantInfo merchant) throws SQLException {
		return (MerchantInfo)sqlMapClient.queryForObject("MerchantInfo.selectByLoginIdForLogin",merchant);
	}
}